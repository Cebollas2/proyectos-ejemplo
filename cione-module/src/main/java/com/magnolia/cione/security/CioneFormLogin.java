package com.magnolia.cione.security;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Set;

import javax.inject.Inject;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.client.error.BadRequestException;
import com.commercetools.api.models.customer.Customer;
import com.google.common.collect.ImmutableSet;
import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.constants.CioneRoles;
import com.magnolia.cione.dto.DireccionesDTO;
import com.magnolia.cione.dto.EmployeeDTO;
import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.exceptions.CioneRuntimeException;
import com.magnolia.cione.service.AuthService;
import com.magnolia.cione.service.CommercetoolsService;
import com.magnolia.cione.service.MiddlewareService;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.cms.security.SecuritySupport;
import info.magnolia.cms.security.SecuritySupportBase;
import info.magnolia.cms.security.User;
import info.magnolia.cms.security.auth.callback.CredentialsCallbackHandler;
import info.magnolia.cms.security.auth.callback.PlainTextCallbackHandler;
import info.magnolia.cms.security.auth.login.LoginHandler;
import info.magnolia.cms.security.auth.login.LoginHandlerBase;
import info.magnolia.cms.security.auth.login.LoginResult;
import info.magnolia.objectfactory.Components;

/**
 * Uses the the {@value #PARAMETER_USER_ID} and {@value #PARAMETER_PSWD}
 * parameters to login.
 */
public class CioneFormLogin extends LoginHandlerBase implements LoginHandler {

	private static final Logger log = LoggerFactory.getLogger(CioneFormLogin.class);

	public static final String PARAMETER_USER_ID = "mgnlUserIdCione";

	public static final String PARAMETER_PSWD = "mgnlUserPSWD";

	public static final String PARAMETER_REALM = "mgnlRealm";

	public static final String PARAMETER_RETURN_TO = "mgnlReturnTo";

	public static final Set<String> AUTHENTICATION_ATTRIBUTES = ImmutableSet.of(PARAMETER_USER_ID, PARAMETER_REALM,
			PARAMETER_PSWD);

	@Inject
	private AuthService authService;

	@Inject
	private MiddlewareService middlewareService;

	@Inject
	private CommercetoolsService commercetoolsService;

	/**
	 * The JAAS chain/module to use.
	 */
	private String jaasChain = SecuritySupportBase.DEFAULT_JAAS_LOGIN_CHAIN;

	private void activateUserInPur(String userid, boolean active) {
		log.info("user does not exist or not active in ERP");
		if (authService.existsUserInPur(userid)) {
			// deactivate user from pur
			log.info("deactivate user in PUR");
			authService.setActivateUserInPUR(userid, active);
		}
	}

	@Override
	public LoginResult handle(HttpServletRequest request, HttpServletResponse response) {
		String userid = request.getParameter(PARAMETER_USER_ID);
		UserERPCioneDTO userERPCioneDTO = null;
		
		// consulto al ERP por el numSocion y almaceno el codigo largo en vez del corto
		if (userid != null && userid.length() > 2) {
			
			log.debug("handle login CIONE for {}", userid);
			if (StringUtils.isNotEmpty(userid)) {
				
				String pswd = StringUtils.defaultString(request.getParameter(PARAMETER_PSWD));
				String realm = StringUtils.defaultString(request.getParameter(PARAMETER_REALM));
				
				// Obtenemos el usuario consultado al ERP
				String numsocioERP = CioneUtils.getidClienteFromNumSocio(userid);
				
				
				try {
					userERPCioneDTO = middlewareService.getUserFromERPForLogin(numsocioERP);
				} catch (Exception e) {
					LoginException loginException = new LoginException("No se puede acceder al servicio middleware");
					log.error("No se puede acceder al servicio middleware", e);
					return new LoginResult(LoginResult.STATUS_FAILED, loginException);
				}
				if ((userERPCioneDTO == null) || (userERPCioneDTO.getSocio() == null)){
					LoginException loginException = new LoginException("No se puede acceder al servicio middleware");
					log.error("No se puede acceder al servicio middleware o usuario no encontrado en el ERP " + numsocioERP);
					return new LoginResult(LoginResult.STATUS_FAILED, loginException);
				}
				
				// user deactivate or not exist into ERP
				if (userERPCioneDTO.getCliente() == null) {
					log.info("user does not exist in ERP");
					//activateUserInPur(userid, false);
					LoginException loginException = new LoginException("1##not-user-in-erp");
					return new LoginResult(LoginResult.STATUS_FAILED, loginException);
					
				} else if (!userERPCioneDTO.getActivo()) {
					log.info("user does not exist in ERP");
					activateUserInPur(userid, false);
					LoginException loginException = new LoginException("2##user-inactive");
					return new LoginResult(LoginResult.STATUS_FAILED, loginException);
					
				} else {
					try {
						User user = authService.getUserFromPUR(userid);
						if (isNotEmployee(userid)) {
							if (user != null && user.getProperty("enabled") != null && user.getProperty("enabled").equals("false")) {
								if (user.getProperty("enabled") != null && user.getProperty("enabled").equals("false")) {
									activateUserInPur(userid, true);
								}
							}
						}
					} catch (Exception e) {
						log.error("Error al activar el usuario " + userid, e);
					}
				}
				
				
				//Consulto si ha cambiado el grupo de precio, si es así lo actualizo en Magnolia y CommerceTools
				User user = authService.getUserFromPUR(userid);
				
				if (user == null) {
					LoginException loginException = new LoginException("3##user-not-registry");
					return new LoginResult(LoginResult.STATUS_FAILED, loginException);
				}
				
				boolean impersonate = false;
				if (user.hasRole(CioneConstants.ROLE_CIONE_SUPERUSER) || user.hasRole(CioneConstants.ROLE_OPTOFIVE_SUPERUSER)) {
					String idToSimulate = user.getProperty(CioneConstants.IMPERSONATE_FIELD_ID_SOCIO);
					if (idToSimulate == null || idToSimulate.isEmpty()) {
						impersonate = false;
					} else {
						impersonate = true;
					}
				}
				
				String rolMagnoliaEkon = getGrupoPrecioEkon(user.getAllRoles()); //Es el grupo de Precio de Magnolia con nomenclatura de Ekon xj "VCO"
				String rolEkon = userERPCioneDTO.getGrupoPrecio(); //Es el grupo de Precio almacenado en Ekon (el que deberia tener) xj "1"
				
				//Si es distinto actualizo el grupo de precio tanto en Magnolia como CommerceTools
				if (!rolMagnoliaEkon.equals(userERPCioneDTO.getGrupoPrecio())) {
					//comprobamos primero que no este simulando el usuario
				
					if (user.hasRole(CioneConstants.ROLE_CIONE_SUPERUSER) || user.hasRole(CioneConstants.ROLE_OPTOFIVE_SUPERUSER)) {
						String idToSimulate = user.getProperty(CioneConstants.IMPERSONATE_FIELD_ID_SOCIO);
						if (idToSimulate == null || idToSimulate.isEmpty()) {
							String rolMagnolia = getGrupoPrecioMagnolia(user.getAllRoles()); // "socio_vision_co"
							String newRol = CioneConstants.equivalenciaEKONMagnolia.get(rolEkon); // "cione"
							authService.updateUserRol(userid, rolMagnolia, newRol);
							user = authService.getUserFromPUR(userid);
						}
					} else {
						String rolMagnolia = getGrupoPrecioMagnolia(user.getAllRoles()); // "socio_vision_co"
						String newRol = CioneConstants.equivalenciaEKONMagnolia.get(rolEkon); // "cione"
						authService.updateUserRol(userid, rolMagnolia, newRol);
						user = authService.getUserFromPUR(userid);
					}
					
					
				}
				if (!impersonate) {
					if (userERPCioneDTO.getConnecta() != null && userERPCioneDTO.getConnecta()) {
						//si contiene connecta entonces anadimos el rol audiology_access
						if (!user.hasRole(CioneConstants.AUDIOLOGY_ACCESS)) {
							authService.addUserRol(userid, CioneConstants.AUDIOLOGY_ACCESS);
						}
						
						//si tiene el rol connecta pero el grupo de precio no es connecta eliminamos el rol
						
						String rolMgnl = CioneConstants.equivalenciaEKONMagnolia.get(userERPCioneDTO.getGrupoPrecio());
						
						if (user.hasRole(CioneRoles.CONNECTA) && !rolMgnl.equals(CioneRoles.CONNECTA)) {
							authService.removeUserRol(userid, CioneRoles.CONNECTA);
						}
					}else {
						if (user.hasRole(CioneConstants.AUDIOLOGY_ACCESS)) {
							authService.removeUserRol(userid, CioneConstants.AUDIOLOGY_ACCESS);
						}
					}
					
					//actualizacion nivel OM en caso de modificacion
					if (userERPCioneDTO.getNivelOM() != null) {
						String nivelOmEkon = getRolOMEkonMagnoliaFormat(userERPCioneDTO.getNivelOM());
						String rolOMMagnolia = getRolOMMagnolia(user);
						if (!nivelOmEkon.equals(rolOMMagnolia)) { //si es distinto lo añado, modifico o borro
							
							switch(userERPCioneDTO.getNivelOM()){
							   
							   case CioneConstants.OM90://añado y quito el anterior en caso de existir
								  authService.addUserRol(userid, CioneConstants.OM_90);
							      break; 
							   
							   case CioneConstants.OM180://añado y quito el anterior en caso de existir
								  authService.addUserRol(userid, CioneConstants.OM_180);
							      break; 
							   case CioneConstants.OM180PLUS://añado y quito el anterior en caso de existir OM180 y OM180+ tienen el mismo rol
								   if (!rolOMMagnolia.equals(CioneConstants.OM180)) {
									   authService.addUserRol(userid, CioneConstants.OM_180);
									   break;
								   } 
							   case CioneConstants.OM360://añado y quito el anterior en caso de existir
								  authService.addUserRol(userid, CioneConstants.OM_360);
							      break;
							   case "": //si no tiene rol en OM en EKon se lo quito en Magnolia en caso de existir
								   if (!rolOMMagnolia.equals("")) 
									   authService.removeUserRol(userid, rolOMMagnolia);
								   break;
							   default:
								   break;
							      
							}
						}
					} else {
						String rolOMMagnolia = getRolOMMagnolia(user);
						if (!rolOMMagnolia.equals("")) 
							authService.removeUserRol(userid, rolOMMagnolia);
					}
				}

				CredentialsCallbackHandler callbackHandler = new PlainTextCallbackHandler(userid, pswd.toCharArray(),
						realm);
				LoginResult result = authenticate(callbackHandler, getJaasChain());

				if (result.getStatus() == LoginResult.STATUS_SUCCEEDED && requiresRedirect(request)) {
					
					try {
						//Alta en commercetools si no existe
						if (!commercetoolsService.customerExists(userid)) {
							
							log.info("El usuario " + userid + " no existe en CommerceTools");
							
							EmployeeDTO employeeDTO = new EmployeeDTO();
							
							employeeDTO.setId(userid);
							
							DireccionesDTO data = middlewareService.getDirecciones(CioneUtils.getidClienteFromNumSocio(userid));
							
							if (!data.getTransportes().isEmpty()) {
								employeeDTO.setAddress(data.getTransportes().get(0).getId_localizacion());
								Customer customer = null;
								try {
									customer = commercetoolsService.createCustomer(employeeDTO);
								} catch (BadRequestException e) {
									LoginException loginException = new LoginException("Ya existe un usuario con el mismo mail");
									log.error("Error al dar de alta al usuario " + userid + " en CommerceTools", e);
									return new LoginResult(LoginResult.STATUS_FAILED, loginException);
								}
								log.debug(customer == null  ? "ERROR: No se ha podido crear el empleado en CT" : "INFO: Se ha creado el empleado en CT");
							}else {
								log.debug("ERROR: No se ha podido crear el empleado en CT (Servicio de direcciones).");
							}
							log.info("El usuario " + userid + " registrado en CommerceTools correctamente");
						}
					} catch (Exception e) {
						LoginException loginException = new LoginException("Error al dar de alta al usuario " + userid + " en CommerceTools");
						log.error("Error al dar de alta al usuario " + userid + " en CommerceTools", e);
						return new LoginResult(LoginResult.STATUS_FAILED, loginException);
					}
					
					return new LoginResult(LoginResult.STATUS_SUCCEEDED_REDIRECT_REQUIRED, result.getSubject());
				}
				return result;
			}
		}
		return LoginResult.NOT_HANDLED;
	}
	
	private String getRolOMEkonMagnoliaFormat(String nivelOmEkon) {
		switch(nivelOmEkon){
		   
		   case CioneConstants.OM90:
			   return CioneConstants.OM_90;
		   case CioneConstants.OM180:
			   return CioneConstants.OM_180; 
		   case CioneConstants.OM360:
			   return CioneConstants.OM_360; 
		   default:
			   return nivelOmEkon; 
		}
	}
	
	private boolean hasRoleOM(String nivelOmEkon, User user) {
		switch(nivelOmEkon){
		   
		   case CioneConstants.OM90:
			  if (user.hasRole(CioneConstants.OM_90))
				  return true; 
		   
		   case CioneConstants.OM180:
			   if (user.hasRole(CioneConstants.OM_180))
				  return true; 
		   case CioneConstants.OM180PLUS:
			   if (user.hasRole(CioneConstants.OM_180))
				  return true;    
		   case CioneConstants.OM360:
			   if (user.hasRole(CioneConstants.OM_360))
				  return true; 
		   case "":
			   if (!user.hasRole(CioneConstants.OM_90) 
					   && !user.hasRole(CioneConstants.OM_180) 
					   && !user.hasRole(CioneConstants.OM_360))
				   return true;
		   default:
			   return false; 
		}
		
	}
	
	private String getRolOMMagnolia(User user) {
		if (user.hasRole(CioneConstants.OM_90)) {
			return CioneConstants.OM_90;
		} else if (user.hasRole(CioneConstants.OM_180)) {
			return CioneConstants.OM_180;
		}else if (user.hasRole(CioneConstants.OM_360)) {
			return CioneConstants.OM_360;
		} else {
			return "";
		}

		
	}

	/*
	 * Recorre los roles del usuario y nos devuelve el grupo de Precio con la nomenclatura de Ekon
	 */
    public static String getGrupoPrecioEkon(Collection<String> roles) {
    	String grupoPrecio="";
    	boolean flag_connecta = false; //audiology_access antes era el rol connecta, pueden quedar usuarios con este rol pero no sería su grupo de precio
    	
    	for (String rol: roles) {
    		String rolMagnolia = CioneConstants.equivalenciaRolMagnoliaEkon.get(rol);
    		if (rolMagnolia != null) {
    			if (!grupoPrecio.isEmpty()) {
    				if (!rol.equals(CioneRoles.CONNECTA)) {
    					grupoPrecio=rolMagnolia;
    				} else {
    					flag_connecta=true;
    				} 
    			}else {
    				grupoPrecio=rolMagnolia;
    			}
    		}
    	}
    	
    	if (flag_connecta && grupoPrecio.isEmpty()) {
    		grupoPrecio = CioneRoles.CONNECTA;
    	}
    	
    	return grupoPrecio;
    }
    
	/*
	 * Recorre los roles del usuario y nos devuelve el rol de Magnolia correspondiente al grupo de Precio en nomenclatura Ekon
	 */
    public static String getGrupoPrecioMagnolia(Collection<String> roles) {
    	
    	String grupoPrecio="";
    	
    	for (String rol: roles) {
    		String rolMagnolia = CioneConstants.equivalenciaRolMagnoliaEkon.get(rol);
    		if (rolMagnolia != null) {
    			if (!grupoPrecio.isEmpty()) {
    				if (!rol.equals(CioneRoles.CONNECTA)) {
    					grupoPrecio=rol;
    				}
    			}else {
    				grupoPrecio=rol;
    			}
    		}
    	}
    	
    	return grupoPrecio;
    }

	private boolean isNotEmployee(String userid) {
		
		String subfijo = userid.length() > 2 ? userid.substring(userid.length() - 2) : userid;
		return subfijo.equals("00") ? true : false;
		
	}

	/**
	 * We can assume that a redirect is needed in case the following criteria is
	 * met:
	 * <ul>
	 * <li>We are dealing with a {@code POST} http request</li>
	 * <li>Request query string does not contain any of the authentication
	 * parameters, because otherwise we are probably dealing with e.g. some
	 * <i>XHR</i> (e.g. a <i>Vaadin</i> request in <i>AdminCentral</i> web-app) and
	 * authentication was triggered just because the attributes leaked into the
	 * request's attribute map via query string => demanding redirect in such case
	 * only might cause some damage.</li>
	 * </ul>
	 */
	protected boolean requiresRedirect(HttpServletRequest request) {
		if (!request.getMethod().equalsIgnoreCase("POST")) {
			return false;
		}

		// Apache changed the logic of URLEncodedUtils#parse(String, Charset), therefore
		// we should be guarded against NPE.
		if (request.getQueryString() == null) {
			return true;
		}

		for (final NameValuePair queryParameter : URLEncodedUtils.parse(request.getQueryString(),
				StandardCharsets.UTF_8)) {
			if (AUTHENTICATION_ATTRIBUTES.contains(queryParameter.getName())) {
				return false;
			}
		}

		return true;
	}

	public String getJaasChain() {
		return this.jaasChain;
	}

	public void setJaasChain(String jaasChain) {
		this.jaasChain = jaasChain;
	}
	
	public boolean assignRole(String username, String rolename) {
		User assignedUser = Components.getComponent(SecuritySupport.class).getUserManager().addRole(Components.getComponent(SecuritySupport.class).getUserManager().getUser(username), rolename);
		return assignedUser != null;
	}
	
	
	public boolean unassignRole(String username, String rolename) {
		User assignedUser = Components.getComponent(SecuritySupport.class).getUserManager().removeRole(Components.getComponent(SecuritySupport.class).getUserManager().getUser(username), rolename);
		return assignedUser != null;
	}

}
