package com.magnolia.cione.service;

import java.util.Collection;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.BasicAuthentication;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.PriceConfigurationPostDTO;
import com.magnolia.cione.pur.CommandsUtils;
import com.magnolia.cione.setup.CioneEcommerceConectionProvider;

import info.magnolia.cms.security.SecuritySupport;
import info.magnolia.cms.security.SecurityUtil;
import info.magnolia.cms.security.User;
import info.magnolia.cms.security.UserManager;
import info.magnolia.context.MgnlContext;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.objectfactory.Components;
import info.magnolia.repository.RepositoryConstants;
import info.magnolia.templating.functions.TemplatingFunctions;

public class UserServiceImpl implements UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Inject
	private ConfigService configService;
	
	@Inject
	private CommandsUtils commandsUtils;
	
	@Inject
	private SimpleTranslator i18n;
	
	@Inject
	private SecuritySupport securitySupport;
	
//	ResteasyClient client;
	
	@Inject
	private TemplatingFunctions templatingFunctions;
	
	@Inject
	private CioneEcommerceConectionProvider cioneEcommerceConectionProvider;
	
	public UserServiceImpl() {


		/*PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		cm.setMaxTotal(200); // Increase max total connection to 200
		cm.setDefaultMaxPerRoute(20); // Increase default max connection per route to 20
		ApacheHttpClient43Engine myEngine = new ApacheHttpClient43Engine(httpClient);
		

		client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(myEngine).build();*/


	}

	@Override
	public Response updateUserPriceConfiguration(PriceConfigurationPostDTO dto) {
		if(configService.getConfig().getIsAuthor()) {
			log.info("SOY AUTHOR INSTANCIA: Modifico el usuario y publico a las instancias registradas");
			return updateUserPriceConfigurationLocal(dto);			
		}else {
			log.info("SOY PUBLIC INSTANCIA: envio el registro al author");
			return sendToAuthorupdateUserPriceConfiguration(dto);			
		}
	}


	private Response sendToAuthorupdateUserPriceConfiguration(PriceConfigurationPostDTO dto) {
		final UserManager userManager =  Components.getComponent(SecuritySupport.class).getUserManager();
		
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getAuthAuthorPath() + "/.rest/price-configuration/submitPriceConfigurationForm")
				.queryParam("lang", MgnlContext.getLocale());
		
		target.register(new BasicAuthentication(userManager.getUserById(dto.getUser()).getName(), dto.getPassword()));
		
		log.info(String.format("enviando peticion: %s", target.getUri()));		
		Response response = target.request().post(Entity.entity(dto, MediaType.APPLICATION_JSON)); 
		String stringResponse = response.readEntity(String.class);
		return generateResponseBuilder(stringResponse, Response.Status.fromStatusCode(response.getStatus())).build();
	}

	private Response updateUserPriceConfigurationLocal(PriceConfigurationPostDTO dto) {
		JSONObject jsonRes = new JSONObject();
		if (dto != null) {
			if (isValidPassword(dto)) {
				
				String rol = getPriceDisplayRol(); //Rol antiguo
				
				try {
					if (setNewConfigurationProperty(dto, rol)) {
						jsonRes.put("message", i18n.translate("cione-module.templates.configuracion-precios-component.success"));
						jsonRes.put("config", dto.getNewConfiguration());
						String res = jsonRes.toString();
						return generateResponseBuilder(res, Response.Status.OK).build();
					}
				} catch (JSONException | RepositoryException e) {
					log.error(e.getMessage(), e);
					jsonRes.put("message", i18n.translate("cione-module.templates.configuracion-precios-component.invalid.params"));
					String res = jsonRes.toString();
					return generateResponseBuilder(res, Response.Status.BAD_REQUEST).build();
				}
				
				jsonRes.put("message", i18n.translate("cione-module.templates.configuracion-precios-component.invalid.configuration"));
				String res = jsonRes.toString();
				return generateResponseBuilder(res, Response.Status.BAD_REQUEST).build();
			}
			jsonRes.put("message", i18n.translate("cione-module.templates.configuracion-precios-component.incorrect.password"));
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.BAD_REQUEST).build();

		} else {
			jsonRes.put("message", i18n.translate("cione-module.templates.configuracion-precios-component.invalid.params"));
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.BAD_REQUEST).build();
		}
		
	}
	
	private boolean setNewConfigurationProperty(PriceConfigurationPostDTO dto, String rol) throws RepositoryException {
        
        boolean result = false;
        
        Session sesion = MgnlContext.getJCRSession(RepositoryConstants.USERS);
        
		Node userNode = sesion.getNodeByIdentifier(dto.getUser());
        
        //Node userNode = templatingFunctions.nodeById(dto.getUser(), CioneConstants.USERS_WORKSPACE);
        Node priceNode = null;
		if(userNode.hasNode(MyshopConstants.VIEWPRICES_USER_NODE_NAME)) {
			priceNode = userNode.getNode(MyshopConstants.VIEWPRICES_USER_NODE_NAME);
			priceNode.setProperty(MyshopConstants.PRICE_CONFIGURATION_PROPERTY, dto.getNewConfiguration());
			result = true;
		} else {
			priceNode = userNode.addNode(MyshopConstants.VIEWPRICES_USER_NODE_NAME, CioneConstants.CONTENT_NODE_TYPE);
			priceNode.setProperty(MyshopConstants.PRICE_CONFIGURATION_PROPERTY, dto.getNewConfiguration());
			
			result = true;
		}
		sesion.save();
		
		long tiempoInicio = System.currentTimeMillis();
		log.debug("publicando el nodo : " + priceNode.getPath());
		commandsUtils.publishNodeWithoutWorkflow(priceNode.getPath(), "users", true);
		long tiempoFin = System.currentTimeMillis();
		log.info("Ha tardado = " + (tiempoFin - tiempoInicio));
		
		
		
		return result;
	}

	private String getPriceDisplayRol() {
		String configuration;
		
		//Collection<String> userRoles = MgnlContext.getUser().getRoles();
		Collection<String> userRoles = getRolesCurrentClient();
		if (userRoles.contains(MyshopConstants.PVP_PVO_ROL)) {
			configuration = MyshopConstants.PVP_PVO;
		} else if (userRoles.contains(MyshopConstants.PVO_ROL)) {
			configuration = MyshopConstants.PVO;
		} else if (userRoles.contains(MyshopConstants.PVP_ROL)) {
			configuration = MyshopConstants.PVP;
		} else if (userRoles.contains(MyshopConstants.HIDDEN_PRICES_ROL)) {
			configuration = MyshopConstants.HIDDEN_PRICES;
		} else {
			configuration = MyshopConstants.PVP_PVO;
		}
		
		return configuration;
	}
	

	private boolean isValidPassword(PriceConfigurationPostDTO dto) {
		//final UserManager userManager =  Components.getComponent(SecuritySupport.class).getUserManager();
		
		//en caso de suplantar usuario tendria que se la password del usuario que suplanta
		String idUser = MgnlContext.getUser().getName();
		UserManager um = securitySupport.getUserManager("public");
		User user = um.getUser(idUser);
		//User user = userManager.getUserById(dto.getUser());
		
		return SecurityUtil.matchBCrypted(dto.getPassword(), user.getPassword());
	}
	
	private ResponseBuilder generateResponseBuilder(String res, Status status) {

		return Response.status(status)
				.type(MediaType.APPLICATION_JSON + "; charset=utf-8")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8"))
				.header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
				.header(HttpHeaders.EXPIRES, "0")
				.entity(res);

	}
	
	public Collection<String> getRolesCurrentClient() {
		Collection<String> roles = MgnlContext.getUser().getAllRoles();
		try {
			if (MgnlContext.getUser().hasRole(CioneConstants.ROLE_CIONE_SUPERUSER) || MgnlContext.getUser().hasRole(CioneConstants.ROLE_OPTOFIVE_SUPERUSER)) {
				String idToSimulate = MgnlContext.getUser().getProperty(CioneConstants.IMPERSONATE_FIELD_ID_SOCIO);
				if (idToSimulate != null && !idToSimulate.isEmpty()) {
					UserManager um = securitySupport.getUserManager("public");
					User impersonator = um.getUser(idToSimulate);
					roles = impersonator.getAllRoles();
				}
			}
		} catch (Exception e) {
			log.info("ERROR al obterner los roles del usuario impersonate");
		}
		
		return roles;
	}
	
	public User getUserCurrentClient() {
		User user = MgnlContext.getUser();
		try {
			if (MgnlContext.getUser().hasRole(CioneConstants.ROLE_CIONE_SUPERUSER) || MgnlContext.getUser().hasRole(CioneConstants.ROLE_OPTOFIVE_SUPERUSER)) {
				String idToSimulate = MgnlContext.getUser().getProperty(CioneConstants.IMPERSONATE_FIELD_ID_SOCIO);
				if (idToSimulate != null && !idToSimulate.isEmpty()) {
					UserManager um = securitySupport.getUserManager("public");
					user = um.getUser(idToSimulate);
				}
			}
			
		} catch (Exception e) {
			log.error("ERROR AL RECUPERAR EL USUARIO SUPLANTADO");
		}
		return user;
	}

}
