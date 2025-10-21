package com.magnolia.cione.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.client.error.BadRequestException;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerSetCustomerGroupAction;
import com.commercetools.api.models.customer.CustomerUpdate;
import com.commercetools.api.models.customer_group.CustomerGroup;
import com.commercetools.api.models.customer_group.CustomerGroupResourceIdentifier;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.constants.CioneGroupCode;
import com.magnolia.cione.constants.CioneRoles;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.ChangePasswordDTO;
import com.magnolia.cione.dto.ConfigCione;
import com.magnolia.cione.dto.CustomerCT;
import com.magnolia.cione.dto.DefaultResponseDTO;
import com.magnolia.cione.dto.DireccionesDTO;
import com.magnolia.cione.dto.EmployeeDTO;
import com.magnolia.cione.dto.ServiciosDTO;
import com.magnolia.cione.dto.TermsDTO;
import com.magnolia.cione.dto.UserAndNewRoleDTO;
import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.exceptions.CioneException;
import com.magnolia.cione.exceptions.CioneRuntimeException;
import com.magnolia.cione.pur.CommandsUtils;
import com.magnolia.cione.pur.NodeUtilities;
import com.magnolia.cione.pur.RestLoginUtils;
import com.magnolia.cione.setup.CioneEcommerceConectionProvider;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.cms.security.SecuritySupport;
import info.magnolia.cms.security.User;
import info.magnolia.cms.security.UserManager;
import info.magnolia.cms.util.QueryUtil;
import info.magnolia.context.MgnlContext;
import info.magnolia.dam.templating.functions.DamTemplatingFunctions;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.jcr.util.NodeNameHelper;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.module.form.processors.FormProcessorFailedException;
import info.magnolia.module.mail.MailTemplate;
import info.magnolia.module.publicuserregistration.PublicUserRegistrationConfig;
import info.magnolia.module.publicuserregistration.UserProfile;
import info.magnolia.module.publicuserregistration.processors.RegistrationProcessor;
import info.magnolia.objectfactory.Components;
import info.magnolia.repository.RepositoryConstants;
import info.magnolia.repository.RepositoryManager;
import info.magnolia.repository.definition.WorkspaceMappingDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class AuthServiceImpl implements AuthService {

	private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	//private static final int STATUS_PUBLISHED = 2;
	
	//private final SimpleTranslator i18n; 

	@Inject
	private SecuritySupport securitySupport;

	@Inject
	private RegistrationProcessor pur;

	@Inject
	private MiddlewareService middlewareService;
	
	@Inject
	private CommercetoolsService commercetoolsService;

	@Inject
	private Provider<PublicUserRegistrationConfig> publicUserRegistrationConfigProvider;

	@Inject
	private ConfigService configService;

	@Inject
	private EmailService emailService;
	
	@Inject
	private CommandsUtils commandsUtils;

	@Inject
	private DamTemplatingFunctions damTemplatingFunctions;
	
	@Inject
	private TemplatingFunctions templatingFunctions;
	
	@Inject
	private NodeNameHelper nodeNameHelper;
	
	@Inject
	private NodeUtilities nodeUtilities;
	
	//private ResteasyClient client = new ResteasyClientBuilder().build();
	//ResteasyClient client;
	
	@Inject
	private CioneEcommerceConectionProvider cioneEcommerceConectionProvider;
	
	public AuthServiceImpl() {
//		super();
//		ResteasyClientBuilder restEasyClientBuilder = new ResteasyClientBuilder(); 
//		restEasyClientBuilder = restEasyClientBuilder.connectionPoolSize(20);
//		client = restEasyClientBuilder.build();


		/*PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		cm.setMaxTotal(200); // Increase max total connection to 200
		cm.setDefaultMaxPerRoute(20); // Increase default max connection per route to 20
		ApacheHttpClient43Engine myEngine = new ApacheHttpClient43Engine(httpClient);
		

		client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(myEngine).build();*/
		//client = (ResteasyClient)ClientBuilder.newClient();

	}

	@Override
	public void registerUser(String numSocio, String idClientERP) throws CioneException {
		if(configService.getConfig().getIsAuthor()) {
			log.info("SOY AUTHOR INSTANCIA: activo/desactivo el usuario y publico a las instancias registradas");
			registerUserLocal(numSocio,idClientERP);			
		}else {
			log.info("SOY PUBLIC INSTANCIA: envio el registro al author");
			sendToAuthorRegisterUser(numSocio, idClientERP);			
		}
	}
	
	/*
	 * Modifica un rol de un usuario
	*/
	@Override
	public Response updateUserRol(String numSocio, String oldRol, String newRol) {
		if(configService.getConfig().getIsAuthor()) {
			log.info("SOY AUTHOR INSTANCIA: Modifico el usuario y publico a las instancias registradas");
			return updateUserRolLocal(numSocio, oldRol, newRol);			
		}else {
			log.info("SOY PUBLIC INSTANCIA: envio el registro al author");
			return sendUpdateUserRol(numSocio, oldRol, newRol);				
		}
	}
	
	
	/*Actualiza el rol tanto en Magnolia como CommerceTools*/
	private Response updateUserRolLocal(String numSocio, String oldRol, String newRol) {
		Response response = null;
		
		Components.getComponent(SecuritySupport.class).getUserManager().
				removeRole(Components.getComponent(SecuritySupport.class).getUserManager().getUser(numSocio), oldRol);
		
		Components.getComponent(SecuritySupport.class).getUserManager().
				addRole(Components.getComponent(SecuritySupport.class).getUserManager().getUser(numSocio), newRol);
		
		UserManager um = securitySupport.getUserManager("public");
		User user = um.getUser(numSocio);
		Session sesion;
		JSONObject jsonRes = new JSONObject();
		try {
			sesion = MgnlContext.getJCRSession(RepositoryConstants.USERS);
			Node currentNode = sesion.getNodeByIdentifier(user.getIdentifier());
			long tiempoInicio = System.currentTimeMillis();
			commandsUtils.publishNodeWithoutWorkflow(currentNode.getNode("roles").getPath(), RepositoryConstants.USERS, true);
			long tiempoFin = System.currentTimeMillis();
			log.info("Ha tardado = " + (tiempoFin - tiempoInicio));
			log.info("Actualizado el rol del usuario " + numSocio + " del rol " + oldRol + " al rol " + newRol + " en Magnolia");
			//actualiza en commerceTools
			if (commercetoolsService.customerExists(numSocio)) {
				
				Customer customer = getCustomerbyNumber(numSocio);
				String customerGroupKey = CioneConstants.equivalenciaRolMagnoliaCommerceTools.get(newRol);
				CustomerGroup customerGroup2 = getCustomerGroupbyKey(customerGroupKey);
				
				
				
				Customer customerInGroup = updateCustomer(customer, customerGroup2.getId());
						//cioneEcommerceConectionProvider.getClient().executeBlocking(CustomerUpdateCommand.of(customer, SetCustomerGroup.of(customerGroup2)));
				if (customerInGroup != null)
					log.info("Actualizado el rol del usuario " + numSocio + " del rol " + oldRol + " al rol " + newRol + " en Magnolia");
				//Customer updateCustomer = client().executeBlocking(CustomerUpdateCommand.of(customer, SetCustomerGroup.of(customerGroup)));
			}
			
			jsonRes.put("message", "El rol de usuario se ha modificado");
			String res = jsonRes.toString();
			response = generateResponseBuilder(res, Response.Status.OK).build();
		} catch (RepositoryException e) {
			e.printStackTrace();
			jsonRes.put("message", "Error al crear el usuario " + e.getCause());
			String res = jsonRes.toString();
			response = generateResponseBuilder(res, Response.Status.BAD_REQUEST).build();
		}
		
		
		return response;
	}
	
	private Customer updateCustomer(Customer customer, String customerGroupId) {
		
		CustomerGroupResourceIdentifier customerGroupResourceIdentifier = CustomerGroupResourceIdentifier.builder()
                .id(customerGroupId)
                .build();
		CustomerSetCustomerGroupAction setCustomerGroupAction = CustomerSetCustomerGroupAction.builder()
                .customerGroup(customerGroupResourceIdentifier)
                .build();
		CustomerUpdate customerUpdate = CustomerUpdate.builder()
                .actions(Collections.singletonList(setCustomerGroupAction))
                .version(Long.valueOf(customer.getVersion())) 
                .build();
		customer = cioneEcommerceConectionProvider
				.getApiRoot().customers()
                .withId(customer.getId())
                .post(customerUpdate)
                .executeBlocking()
                .getBody();

		return customer;
	}
	
	private Customer getCustomerbyNumber(String customerNumber) {
		Customer customer = null;
		List<Customer> listCustomers = 
			cioneEcommerceConectionProvider.getApiRoot().customers().get()
				.withWhere("customerNumber=\"" + customerNumber + "\"")
				.executeBlocking()
				.getBody()
				.getResults();
		if ((listCustomers != null) && (!listCustomers.isEmpty())) {
			customer = listCustomers.get(0);
		}
		
		return customer;
	}
	
	private CustomerGroup getCustomerGroupbyKey(String key) {
		CustomerGroup customerGroup = null;
		customerGroup = 
			cioneEcommerceConectionProvider
			.getApiRoot()
			.customerGroups()
			.withKey(key)
			.get()
			.executeBlocking()
			.getBody()
			.get();
		
		return customerGroup;
	}
	
	private Response sendUpdateUserRol(String numSocio, String oldRol, String newRol) {
		//Mandamos la peticion a la author y publicamos desde ahi
		Response response = null;
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getAuthAuthorPath() + "/.rest/auth/v1/updateRol")
				.queryParam("numSocio", numSocio)
				.queryParam("oldRol", oldRol)
				.queryParam("newRol", newRol);
		log.info(String.format("enviando peticion: %s", target.getUri()));
		try {
			response = target.request().get();
			String stringResponse = response.readEntity(String.class);
			if(stringResponse == null || !stringResponse.equals("true")) {
				return response;
			}
			response.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return response;
		}
		
		return response;
	}
	
	private void registerUserLocal(String numSocio, String idClientERP) throws CioneException {
		log.info(numSocio, idClientERP);
		UserERPCioneDTO userERPCioneDTO = middlewareService.getUserFromERP(idClientERP);
		if (userERPCioneDTO != null) {
			if (existsUserInPur(numSocio)) {
				// throw new CioneException("Ya existe un usuario en el sistema con ese
				// username");
				throw new CioneException(translate("cione-module.global.user-already-exits"));
			}

			// generate password
			userERPCioneDTO.setPassword(CioneUtils.generatePassword());

			// añadimos al pur
			try {
				addUserToPur(userERPCioneDTO);
				UserManager um = securitySupport.getUserManager("public");
				User user = um.getUser(userERPCioneDTO.getNumSocio());	
				Session sesion = MgnlContext.getJCRSession(RepositoryConstants.USERS);
				Node currentNode = sesion.getNodeByIdentifier(user.getIdentifier());
				sesion.save();
				if (currentNode.getParent().getNodes().getSize() > 1)
					commandsUtils.publishNodeWithoutWorkflow(currentNode.getPath(), RepositoryConstants.USERS, true);
				else
					commandsUtils.publishNodeWithoutWorkflow(currentNode.getParent().getPath(), RepositoryConstants.USERS, true);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new CioneException(translate("cione-module.global.user-without-email"));
			}
			
			//añadimos el nodo a cione-users
			
			
				
			createNodeCioneUsers(numSocio);
				
			
			
			// enviamos el email
			String email = userERPCioneDTO.getEmail();
			userERPCioneDTO.setNumSocio(numSocio);
			User user = getUserFromPUR(numSocio);
			
			if (email != null && !email.isEmpty()) {
				ConfigCione configCione = configService.getConfig();
				if (configCione.getAuthTestMode()) {
					email = configCione.getAuthTestEmail();
				}
				try {
					if (user.hasRole(CioneRoles.OPTCAN) || user.hasRole(CioneRoles.CLIENTE_MONTURAS)) {
						email = email + ";info@optofive.es" + ";joseantonio.desantos@cione.es";
					} else if (user.hasRole(CioneRoles.OPTICAPRO)) {
						email = email + ";customer.service@opticapro.net";
						
					}
					emailService.sendTemplateEmail(translate("cione-module.mails.register-user.subject"), email,
							buildTemplateEmailForRegisterUser(userERPCioneDTO.getNombreComercial(), numSocio,
									userERPCioneDTO.getPassword(), userERPCioneDTO.getGrupoPrecio()),null);
					
				} catch (Exception e) {
					log.error("ERROR en el envio de mail");
				}
			}
			
		} else {
			// throw new CioneException("El Id de usuario no existe en el sistema");
			throw new CioneException(translate("cione-module.global.user-does-not-exist") + numSocio);
		}
	}

	
	private void createNodeCioneUsers(String numSocio) {
		try {
			
			String firstLevel = numSocio.substring(0,1);
			String secondLevel = numSocio.substring(0,2);
			RepositoryManager repositoryManager = Components.getComponent(RepositoryManager.class);
			WorkspaceMappingDefinition mappingUsers = repositoryManager.getWorkspaceMapping(CioneConstants.CIONE_USERS_WORKSPACE);
			
			Session sessionUsers = MgnlContext.getJCRSession(CioneConstants.CIONE_USERS_WORKSPACE);
			//Session sessionUsers = repositoryManager.getSession(mappingUsers.getLogicalWorkspaceName(), new SimpleCredentials("anonymous", "anonymous".toCharArray()));
			
			//Session sessionUsers = MgnlContext.getJCRSession(CioneConstants.CIONE_USERS_WORKSPACE);
			//Node userNode = templatingFunctions.nodeByPath("/", CioneConstants.CIONE_USERS_WORKSPACE);
			Node nodeRoot = sessionUsers.getRootNode();
			
			//Node newsNode = sessionUsers.getNodeByIdentifier(numSocio);
			String path = nodeRoot.getPath();
			if (!nodeRoot.hasNode(CioneConstants.PUBLIC_NODE)) {
				nodeRoot = nodeRoot.addNode(CioneConstants.PUBLIC_NODE, CioneConstants.CONTENT_NODE_TYPE);
				path = nodeRoot.getPath();
			} else {
				nodeRoot = nodeRoot.getNode(CioneConstants.PUBLIC_NODE);
				path = nodeRoot.getPath() + "/" +firstLevel;
			}
			
			
			if (!nodeRoot.hasNode(firstLevel)) {
				nodeRoot = nodeRoot.addNode(firstLevel, CioneConstants.CONTENT_NODE_TYPE);
			} else {
				nodeRoot = nodeRoot.getNode(firstLevel);
				path = nodeRoot.getPath() + "/" +secondLevel;
			}
			
			if (!nodeRoot.hasNode(secondLevel)) {
				nodeRoot = nodeRoot.addNode(secondLevel, CioneConstants.CONTENT_NODE_TYPE);
			} else {
				nodeRoot = nodeRoot.getNode(secondLevel);
				path = nodeRoot.getPath()+"/"+numSocio;
			}
			
			if (!nodeRoot.hasNode(numSocio)) {
				nodeRoot = nodeRoot.addNode(numSocio, CioneConstants.CONTENT_NODE_TYPE);
			}
			nodeRoot.setProperty("legal", false);
			
			sessionUsers.save();
			
			long tiempoInicio = System.currentTimeMillis();
			log.debug("publicando el nodo : " + path);
			commandsUtils.publishNodeWithoutWorkflow(path, CioneConstants.CIONE_USERS_WORKSPACE, true);
			long tiempoFin = System.currentTimeMillis();
			log.info("Ha tardado = " + (tiempoFin - tiempoInicio));
			
		} catch (Exception e) {
			log.error("Error al crear el nodo en cione-users");
			String subject = "Error al crear el nodo en cione-users";
	    	String mailto = "msanchezp@atsistemas.com";
	    	String texto = "Se ha producido un error al añadir al usuario: " + numSocio + " al workspace cione-users para aceptar condiciones comerciales";
	    	try {
				emailService.sendEmail(subject, texto, mailto);
			} catch (CioneException ex) {
				log.error("ERROR en el envio de mail");
			}
		}
	}
	
	public boolean getTerms() {
		boolean result = true;
		String numSocio = CioneUtils.getIdCurrentClient();
		if (CioneUtils.isSocio(numSocio)){ //si es empleado no necesita aceptarlos
			try {
				
				
				/*String firstLevel = numSocio.substring(0,1);
				String secondLevel = numSocio.substring(0,2);
				
				RepositoryManager repositoryManager = Components.getComponent(RepositoryManager.class);
				WorkspaceMappingDefinition mappingUsers = repositoryManager.getWorkspaceMapping(CioneConstants.CIONE_USERS_WORKSPACE);
				Session sessionUsers = repositoryManager.getSession(mappingUsers.getLogicalWorkspaceName(), new SimpleCredentials("anonymous", "anonymous".toCharArray()));
				
				//Session sessionUsers = MgnlContext.getJCRSession(CioneConstants.CIONE_USERS_WORKSPACE);
				//Node userNode = templatingFunctions.nodeByPath("/", CioneConstants.CIONE_USERS_WORKSPACE);
				Node nodeRoot = sessionUsers.getRootNode();
				
				//Node newsNode = sessionUsers.getNodeByIdentifier(numSocio);
				String path = nodeRoot.getPath();
				if (nodeRoot.hasNode(CioneConstants.PUBLIC_NODE)) {
					nodeRoot = nodeRoot.getNode(CioneConstants.PUBLIC_NODE);
					path = nodeRoot.getPath() + "/" +firstLevel;
				}
				if (nodeRoot.hasNode(firstLevel)) {
					nodeRoot = nodeRoot.getNode(firstLevel);
					path = nodeRoot.getPath() + "/" +secondLevel;
				}
				
				if (nodeRoot.hasNode(secondLevel)) {
					nodeRoot = nodeRoot.getNode(secondLevel);
					path = nodeRoot.getPath()+"/"+numSocio;
				}
				
				if (nodeRoot.hasNode(numSocio)) {
					nodeRoot = nodeRoot.getNode(numSocio);
				}
				result = nodeRoot.getProperty("legal").getBoolean();
				
				String pathRoot = "/public/0/00/000470200";
				
				Session sessionUsers2 = MgnlContext.getJCRSession(CioneConstants.CIONE_USERS_WORKSPACE);
				Node node = sessionUsers2.getNode(pathRoot);
				
				if (node != null) {
					result = node.getProperty("legal").getBoolean();
				}
				sessionUsers2.save();
				
				
				*/
				String firstLevel = numSocio.substring(0,1);
				String secondLevel = numSocio.substring(0,2);
				String path = "/"+CioneConstants.PUBLIC_NODE+ "/" + firstLevel+ "/" + secondLevel+"/"+numSocio;
				Session sessionUsers = MgnlContext.getJCRSession(CioneConstants.CIONE_USERS_WORKSPACE);
				if (sessionUsers.nodeExists(path)) {
					Node node = sessionUsers.getNode(path);
					
					if ((node != null) && (node.hasProperty("legal"))) {
						result = node.getProperty("legal").getBoolean();
					}
				}
				
				
				sessionUsers.save();
			} catch (PathNotFoundException e) {
				log.debug(e.getMessage(), e);
				envioMail(numSocio);
			} catch (RepositoryException e) {
				log.debug(e.getMessage(), e);
				envioMail(numSocio);
			}
		}
		return result;
	}
	@Override
	public Response submitTerms(TermsDTO termsDTO) {
		if(configService.getConfig().getIsAuthor()) {
			log.info("SOY AUTHOR INSTANCIA: activo/desactivo el usuario y publico a las instancias registradas");
			return submitTermsLocal(termsDTO);			
		}else {
			log.info("SOY PUBLIC INSTANCIA: envio el registro al author");
			return sendToAuthorSubmitTerms(termsDTO);			
		}
		
	}
	
	private Response sendToAuthorSubmitTerms(TermsDTO termsDTO){
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getAuthAuthorPath() + "/.rest/auth/v1/acceptTerms");
		log.info(String.format("enviando peticion: %s", target.getUri()));		
		Response response = null;
		try {
			termsDTO.setCurrentUser(CioneUtils.getIdCurrentClient());
			response = target.request().post(Entity.entity(termsDTO, MediaType.APPLICATION_JSON));
			String stringResponse = response.readEntity(String.class);
			if(stringResponse == null || !stringResponse.equals("true")) {
				throw  new CioneException(stringResponse);
			}
			response.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response = generateResponseBuilder(e.getMessage(), Response.Status.BAD_REQUEST).build();
			throw new CioneRuntimeException(e.getMessage());
		}
		
		return response;
	}
	
	
	public Response submitTermsLocal(TermsDTO termsDTO) {
		Response response = null;
	
		try {
			String numSocio = termsDTO.getCurrentUser();
			String numEkon = numSocio.substring(0, numSocio.length()-2);
			UserERPCioneDTO user = middlewareService.getUserFromERP(numEkon);
			String firstLevel = numSocio.substring(0,1);
			String secondLevel = numSocio.substring(0,2);
			String path = "/"+CioneConstants.PUBLIC_NODE+ "/" + firstLevel+ "/" + secondLevel+"/"+numSocio;
			Session sessionUsers = MgnlContext.getJCRSession(CioneConstants.CIONE_USERS_WORKSPACE);

			Node node = sessionUsers.getNode(path);
			
			if (node != null) {
				
				node.setProperty("legal", true);
				
				sessionUsers.save();
				
				long tiempoInicio = System.currentTimeMillis();
				log.debug("publicando el nodo : " + path);
				commandsUtils.publishNodeWithoutWorkflow(path, CioneConstants.CIONE_USERS_WORKSPACE, true);
				long tiempoFin = System.currentTimeMillis();
				log.info("Ha tardado = " + (tiempoFin - tiempoInicio));
				
				response = generateResponseBuilder("Nodo actualizado", Response.Status.OK).build();
				String to = termsDTO.getMail() + ";" + user.getEmail();
				if (configService.getConfig().getAuthTestMode()) {
					to = termsDTO.getMail();
				}
				
				String email = termsDTO.getMail();
				if (email != null && !email.isEmpty()) {
					try {
						String asunto = getAsunto(user);
						emailService.sendTemplateEmail(asunto, to,
								buildTemplateEmail(termsDTO, user), null, true);
						
					} catch (Exception e) {
						log.error("ERROR en el envio de mail" + e.getMessage(), e);
					}
				}
			} else {
				sessionUsers.save();
			}
		
		} catch (RepositoryException e) {
			response = generateResponseBuilder(e.getMessage(), Response.Status.BAD_REQUEST).build();
			log.error(e.getMessage(), e);
		}
		
		return response;
	}
	
	private String getAsunto(UserERPCioneDTO user) {
		//Map<String, Object> templateValues = new HashMap<>();
		Locale locale = new Locale("es");

		if (user.getGrupoPrecio().equals("VCOCLIP") || user.getGrupoPrecio().equals("PORCLI") 
				|| user.getGrupoPrecio().equals("PORSO") || user.getGrupoPrecio().equals("PORLENS"))
			locale = new Locale("pt");
		
		ResourceBundle rbundle = ResourceBundle.getBundle("cione-module/i18n/module-cione-module-messages", locale);
		if (user.getGrupoPrecio().equals("OPTICAPRO")) 
			return rbundle.getString("cione-module.mails.terms-form.asunto-opticapro");
		else
			return rbundle.getString("cione-module.mails.terms-form.asunto");
	}
	
	private Map<String, Object> buildTemplateEmail(TermsDTO termsDTO, UserERPCioneDTO user) {
		Map<String, Object> templateValues = new HashMap<>();
		Locale locale = new Locale("es");

		if (user.getGrupoPrecio().equals("VCOCLIP") || user.getGrupoPrecio().equals("PORCLI") 
				|| user.getGrupoPrecio().equals("PORSO") || user.getGrupoPrecio().equals("PORLENS"))
			locale = new Locale("pt");
		
		ResourceBundle rbundle = ResourceBundle.getBundle("cione-module/i18n/module-cione-module-messages", locale);
		templateValues.put(MailTemplate.MAIL_TEMPLATE_FILE, "cione-module/templates/mail/terms-form.ftl");	
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		templateValues.put("fecha", dtf.format(now));
		//templateValues.put("logoHeader", CioneUtils.getURLHttps() + "/.resources/cione-theme/webresources/img/logo_cione.jpg");
		
		if (user.getGrupoPrecio().equals("OPTICAPRO")) {
			templateValues.put("logoHeader", getImageLink("/opticapro/logo-opticapro.png"));
			templateValues.put("info1", rbundle.getString("cione-module.mails.terms-form.info1-opticapro"));
			templateValues.put("infoNumSocio", rbundle.getString("cione-module.mails.terms-form.info1-nsocio"));
		} else if (user.getGrupoPrecio().equals("OPTCAN") || user.getGrupoPrecio().equals("CLIENTEMON")) {
			templateValues.put("logoHeader", getImageLink("/optofive/logo-header-optofive.png"));
			templateValues.put("info1", rbundle.getString("cione-module.mails.terms-form.info1-optofive"));
			templateValues.put("infoNumSocio", rbundle.getString("cione-module.mails.terms-form.info1-ncliente"));
		} else {
			templateValues.put("logoHeader", getImageLink("/cione/templates/emails/LOGO-CIONE-OK.png"));
			templateValues.put("info1", rbundle.getString("cione-module.mails.terms-form.info1"));
			templateValues.put("infoNumSocio", rbundle.getString("cione-module.mails.terms-form.info1-ncliente"));
		}
		
		templateValues.put("nombreUsuario", user.getPersonaContacto());
		
		templateValues.put("saludation", rbundle.getString("cione-module.mails.terms-form.saludation"));
		
		if (user.getGrupoPrecio().equals("OPTICAPRO")) {
			templateValues.put("info2", rbundle.getString("cione-module.mails.terms-form.info2-opticapro"));
			templateValues.put("info3", rbundle.getString("cione-module.mails.terms-form.info3-opticapro"));
		}else {
			templateValues.put("info2", rbundle.getString("cione-module.mails.terms-form.info2"));
			templateValues.put("info3", rbundle.getString("cione-module.mails.terms-form.info3"));
		}
		if (user.getGrupoPrecio().equals("OPTICAPRO"))
			templateValues.put("info4", "");
		else
			templateValues.put("info4", rbundle.getString("cione-module.mails.terms-form.info4"));
		templateValues.put("numSocio", user.getCliente());
		templateValues.put("direccion", user.getDireccion());
		templateValues.put("codigoPostal", user.getCodigoPostal());
		templateValues.put("poblacion", user.getPoblacion());
		templateValues.put("provincia", user.getProvincia());

		return templateValues;

	} 
	
	private void envioMail(String numSocio) {
		String email = "msanchezp@atsistemas.com";
		if (email != null && !email.isEmpty()) {
			ConfigCione configCione = configService.getConfig();
			if (configCione.getAuthTestMode()) {
				email = configCione.getAuthTestEmail();
			}
			try {
				emailService.sendEmail(translate("cione-module.mails.register-user.subject"), 
						email, "El socio "+ numSocio +" no ha podido actualizar las condiciones comerciales");
			} catch (Exception e) {
				log.error("ERROR en el envio de mail");
			}
		}
	}
	
	
	/**
	 * Obtenemos si hay que publicar desde el abuelo, padre o solo el hijo recursivamente
	 * @param node
	 * @return
	 * @throws AccessDeniedException
	 * @throws ItemNotFoundException
	 * @throws RepositoryException
	 
	private Node getNodeToPublish(Node node) throws AccessDeniedException, ItemNotFoundException, RepositoryException {
		//hijo
		Node result = node;
		//abuelo
		if(NodeTypes.Activatable.getActivationStatus(node.getParent().getParent()) != STATUS_PUBLISHED) {
			result =  node.getParent().getParent();
		//padre	
		}else if (NodeTypes.Activatable.getActivationStatus(node.getParent()) != STATUS_PUBLISHED){
			result = node.getParent();
		}
		return result;
	}*/
	
	
	private void sendToAuthorRegisterUser(String numSocio, String idCLient){
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getAuthAuthorPath() + "/.rest/auth/v1/register")
				.queryParam("id", numSocio)
				.queryParam("lang", MgnlContext.getLocale());
		log.info(String.format("enviando peticion: %s", target.getUri()));		
		Response response;
		try {
			response = target.request().get();
			String stringResponse = response.readEntity(String.class);
			if(stringResponse == null || !stringResponse.equals("true")) {
				throw  new CioneException(stringResponse);
			}
			response.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CioneRuntimeException(e.getMessage());
		}
	}
	
	public void sendToAuthorAuditoryUser(String numSocio, String newsId){
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getAuthAuthorPath() + "/.rest/auth/v1/updateAuditory")
				.queryParam("id", numSocio)
				.queryParam("newsId", newsId);
				
				
		log.info(String.format("enviando peticion: %s", target.getUri()));		
		Response response;
		try {
			
			response =  target.request().get();
			
			
			String stringResponse = response.readEntity(String.class);
			if(stringResponse == null || !stringResponse.equals("true")) {
				throw  new CioneException(stringResponse);
			}
			response.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CioneRuntimeException(e.getMessage());
		}

	}
	

	public boolean existsUserInPur(String username) {
		boolean result = false;
		User currentUser = securitySupport.getUserManager("public").getUser(username);
		if (currentUser != null) {
			result = true;
		}
		return result;
	}

	/**
	 * 
	 * @param userERPCioneDTO
	 * @throws FormProcessorFailedException
	 */
	private void addUserToPur(UserERPCioneDTO userERPCioneDTO) throws FormProcessorFailedException {
		// la activacion se hace sola con la configuracion del pur (configuracion por
		// defecto del modulo)
		if (!validateUser(userERPCioneDTO))
			throw new CioneRuntimeException("Cione Group code does not exist");
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("newUser", true);
		parameters.put(UserProfile.FULLNAME, userERPCioneDTO.getNombreComercial());
		parameters.put(UserProfile.EMAIL, userERPCioneDTO.getEmail());
		parameters.put(UserProfile.PASSWORD, userERPCioneDTO.getPassword());
		// parameters.put(UserProfile.USERNAME, userERPCioneDTO.getCliente());
		parameters.put(UserProfile.USERNAME, userERPCioneDTO.getNumSocio());
		pur.setEnabled(true);
		pur.process(null, parameters);
		pur.setEnabled(false);
		

		// add access role to web
		if (!userERPCioneDTO.getGrupoPrecio().equals(CioneRoles.OPTICAPRO)) { //OPTICAPRO tiene configuracion propia de acceso al pur
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.CIONE_PUR);
		}

		switch (userERPCioneDTO.getGrupoPrecio()) {
		case CioneGroupCode.CODE_1:
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.SOCIO_CIONE);
			break;
		case CioneGroupCode.CODE_1001:
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.SOCIO_CIONE_VCO_ACOCENTRO);
			break;
		case CioneGroupCode.CODE_1002:
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.SOCIO_CIONE_VCO_ACOCORNER);
			break;
		case CioneGroupCode.CODE_1020:
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.SOCIO_CIONE_CLUB_MARKETING);
			break;
		case CioneGroupCode.CODE_1103:
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.SOCIO_CIONE_ACO_CENTRO);
			break;
		case CioneGroupCode.CODE_1104:
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.SOCIO_CIONE_ACO_CORNER);
			break;
		case CioneGroupCode.CODE_1200:
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.CLIENTE);
			break;
		case CioneGroupCode.CODE_2:
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.PERSONAL_OTROS);
			break;
		case CioneGroupCode.CLIENTEMON:
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.CLIENTE_MONTURAS);
			break;
		case CioneGroupCode.PORCLI:
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.CLIENTES_PORTUGAL);
			break;
		case CioneGroupCode.PORLENS:
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.PORLENS);
			break;
		case CioneGroupCode.PORSO:
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.SICIO_CIONE_PORTUGAL);
			break;
		case CioneGroupCode.PRI:
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.CIOSA_PRMERA);
			break;
		case CioneGroupCode.VCO:
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.SOCIO_VISION_CO);
			break;
		case CioneGroupCode.VCOCLIP:
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.CLIENTE_PORTUGAL_VCO);
			break;
		case CioneGroupCode.CONNECTA:
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.CONNECTA);
			break;
		case CioneGroupCode.OPTMAD:
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.OPTMAD);
			break;
		case CioneGroupCode.OPTCAN:
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.OPTCAN);
			break;
		case CioneGroupCode.OPTICAPRO:
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.OPTICAPRO);
			break;
		case CioneGroupCode.TALLERMAD:
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.TALLERMAD);
			break;
		default:
			log.error("Cione Group code does not exist");
			throw new CioneRuntimeException("Cione Group code does not exist");
		}

		if (userERPCioneDTO.getRutaLuz() != null && userERPCioneDTO.getRutaLuz()) {
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.RUTA_LUZ);
		}

		if (userERPCioneDTO.getConnecta() != null && userERPCioneDTO.getConnecta()) {
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.AUDIOLOGY_ACCESS);
		}

		if (userERPCioneDTO.getLooktic() != null && userERPCioneDTO.getLooktic()) {
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.LOOKTIC);
		}

		if (userERPCioneDTO.getLookticPRO() != null && userERPCioneDTO.getLookticPRO()) {
			assignRole(userERPCioneDTO.getNumSocio(), CioneRoles.LOOKTIC_PRO);
		}
		
		//nivel OM
		
		switch(userERPCioneDTO.getNivelOM()){
		   
		   case CioneConstants.OM90:
			  assignRole(userERPCioneDTO.getNumSocio(), CioneConstants.OM_90);
		      break; 
		   case CioneConstants.OM180:
			   assignRole(userERPCioneDTO.getNumSocio(), CioneConstants.OM_180);
			   break; 
		   case CioneConstants.OM180PLUS://añado OM180 (este rol se tratara igual que el 180)
			   assignRole(userERPCioneDTO.getNumSocio(), CioneConstants.OM_180);
			   break; 
		   case CioneConstants.OM360://añado y quito el anterior en caso de existir
			   assignRole(userERPCioneDTO.getNumSocio(), CioneConstants.OM_360);
			   break; 
		   
		   default:
			   break;
				      
		}

	}
	
	private boolean validateUser(UserERPCioneDTO userERPCioneDTO) {
		boolean validUser = false;
		
		switch (userERPCioneDTO.getGrupoPrecio()) {
		case CioneGroupCode.CODE_1:
			validUser = true;
			break;
		case CioneGroupCode.CODE_1001:
			validUser = true;
			break;
		case CioneGroupCode.CODE_1002:
			validUser = true;
			break;
		case CioneGroupCode.CODE_1020:
			validUser = true;
			break;
		case CioneGroupCode.CODE_1103:
			validUser = true;
			break;
		case CioneGroupCode.CODE_1104:
			validUser = true;
			break;
		case CioneGroupCode.CODE_1200:
			validUser = true;
			break;
		case CioneGroupCode.CODE_2:
			validUser = true;
			break;
		case CioneGroupCode.CLIENTEMON:
			validUser = true;
			break;
		case CioneGroupCode.PORCLI:
			validUser = true;
			break;
		case CioneGroupCode.PORLENS:
			validUser = true;
			break;
		case CioneGroupCode.PORSO:
			validUser = true;
			break;
		case CioneGroupCode.PRI:
			validUser = true;
			break;
		case CioneGroupCode.VCO:
			validUser = true;
			break;
		case CioneGroupCode.VCOCLIP:
			validUser = true;
			break;
		case CioneGroupCode.CONNECTA:
			validUser = true;
			break;
		case CioneGroupCode.OPTMAD:
			validUser = true;
			break;
		case CioneGroupCode.OPTCAN:
			validUser = true;
			break;
		case CioneGroupCode.OPTICAPRO:
			validUser = true;
			break;
		case CioneGroupCode.TALLERMAD:
			validUser = true;
			break;
		case CioneGroupCode.EMPTY:
			log.error("El socio " + userERPCioneDTO.getNumSocio() + " no tiene grupo de precio asociado");
			break;
		default:
			log.error("El socio " + userERPCioneDTO.getNumSocio() 
				+ " tiene asignado en EKON el grupo de precio " 
				+ userERPCioneDTO.getGrupoPrecio() + " que no tiene coorelacion en Magnolia");
		}
		
		return validUser;
		
	}

	private boolean assignRole(String username, String rolename) {
		/*User assignedUser = Components.getComponent(SecuritySupport.class).getUserManager()
				.addRole(Components.getComponent(SecuritySupport.class).getUserManager().getUser(username), rolename);*/
		
		
		try {
			UserManager um = Components.getComponent(SecuritySupport.class).getUserManager();
			um.addRole(um.getUser(username), rolename);
		} catch (UnsupportedOperationException e) {
			log.error(e.getMessage(), e);
			return false;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		
		return true;
	}

	
	
	@Override
	public void setActivateUserInPUR(String idClient, Boolean active){
		if(configService.getConfig().getIsAuthor()) {
			log.info("SOY AUTHOR INSTANCIA: activo/desactivo el usuario y publico a las instancias registradas");
			setActivateUserFromPURLocal(idClient,active);			
		}else {
			log.info("SOY PUBLIC INSTANCIA: envio el registro al author");
			sendToAuthorActivateUserInPur(idClient, active);			
		}
	}
	
	//local
	private void setActivateUserFromPURLocal(String idClient, Boolean active) {
		if (log.isInfoEnabled()) {
			log.info(String.format("Change enable user to [%s]", active));
		}

		final String realm = publicUserRegistrationConfigProvider.get().getConfiguration().getRealmName();
		UserManager um = securitySupport.getUserManager(realm);
		User user = um.getUser(idClient);
		um.setProperty(user, "enabled", active.toString());
		
		try {
			Session sesion = MgnlContext.getJCRSession(RepositoryConstants.USERS);
			Node currentNode = sesion.getNodeByIdentifier(user.getIdentifier());
			commandsUtils.publishNodeWithoutWorkflow(currentNode.getPath(), RepositoryConstants.USERS,true);
		} catch (Exception e) {
			log.error("Error realizando la publicación del nodo",e);
		}
		
	}
	
	//send to author
	private void sendToAuthorActivateUserInPur(String idClient, Boolean active)  {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getAuthAuthorPath() + "/.rest/private/impersonate/v1/activate-user")
				.queryParam("id", idClient)
				.queryParam("activate", active);		
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, CioneUtils.getAuthToSync()).get();
		if(response.getStatus() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
			String errorMessage = "Se ha producido un error";
			if(response.getEntity() != null) {
				 errorMessage = response.readEntity(String.class);
			}			
			throw new CioneRuntimeException(errorMessage);
		}		
		response.close();		
	}

	
	@Override
	public void recoveryPassword(String idClient) throws CioneException {
		if(configService.getConfig().getIsAuthor()) {
			log.info("SOY AUTHOR INSTANCIA: realizo la recuperación de password y publico a las instancias registradas");
			recoveryPasswordLocal(idClient);			
		}else {
			log.info("SOY PUBLIC INSTANCIA: envío solicitu de recuperación de password al author");
			sendToAuthorRecoveryPassword(idClient);			
		}		
	}
	
	private void recoveryPasswordLocal(String idClient) throws CioneException {
		if (log.isInfoEnabled()) {
			log.info(String.format("Recovery password for user [%s]", idClient));
		}

		UserERPCioneDTO userERPCioneDTO = middlewareService
				.getUserFromERP(CioneUtils.getidClienteFromNumSocio(idClient));
		if (userERPCioneDTO == null) {
			throw new CioneException(translate("cione-module.global.user-does-not-exist" + idClient));
		}

		UserManager um = securitySupport.getUserManager("public");
		User user = securitySupport.getUserManager("public").getUser(idClient);
		if (user == null) {
			throw new CioneException(translate("cione-module.global.user-does-not-exist" + idClient));
		}
		ConfigCione configCione = configService.getConfig();
		String newPassword = CioneUtils.generatePassword();
		um.changePassword(user, newPassword);
		
		try {
			Session sesion = MgnlContext.getJCRSession(RepositoryConstants.USERS);
			Node currentNode = sesion.getNodeByIdentifier(user.getIdentifier());
			commandsUtils.publishNodeWithoutWorkflow(currentNode.getPath(), RepositoryConstants.USERS ,true);
		} catch (Exception e) {
			log.error("Error realizando la publicación del nodo",e);
		}

		String email = user.getProperty(UserProfile.EMAIL);
		String name = user.getProperty(UserProfile.TITLE);
		if (configCione.getAuthTestMode()) {
			email = configCione.getAuthTestEmail();
		}

		emailService.sendTemplateEmail(translate("cione-module.mails.recovery-pwd.subject"), email,
				buildTemplateEmailForRecoveryPwd(name, newPassword, userERPCioneDTO.getGrupoPrecio()),null);
	}
	
	private void sendToAuthorRecoveryPassword(String idClient) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getAuthAuthorPath() + "/.rest/auth/v1/recovery-password")
				.queryParam("id", idClient)
				.queryParam("lang", MgnlContext.getLocale());
		log.info(String.format("enviando peticion: %s", target.getUri()));		
		Response response;
		try {
			response = target.request().get();
			String stringResponse = response.readEntity(String.class);
			if(stringResponse == null || !stringResponse.equals("true")) {
				throw  new CioneException(stringResponse);
			}
			response.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CioneRuntimeException(e.getMessage());
		}
	}
	

	@Override
	public void changePassword(ChangePasswordDTO changePasswordDTO) throws CioneException {
		if(configService.getConfig().getIsAuthor()) {
			log.info("SOY AUTHOR INSTANCIA: cambio la pwd del usuario y publico a las instancias registradas");
			changePasswordLocal(changePasswordDTO);			
		}else {
			log.info("SOY PUBLIC INSTANCIA: envio la petición de cambiar la password a Author");
			sendToAuthorChangePassword(changePasswordDTO);			
		}
	}
	
	private void changePasswordLocal(ChangePasswordDTO changePasswordDTO) throws CioneException {
		if (!isValidPasswordForUser(changePasswordDTO.getUsername(),changePasswordDTO.getOldPassword())) {
			throw new CioneException("1##" + translate(
					"cione-module.templates.components.datos-identificativos-component.bad-current-password"));
		} else if (!CioneUtils.validatePassword(changePasswordDTO.getNewPassword())) {
			throw new CioneException("2##" + translate(
					"cione-module.templates.components.datos-identificativos-component.bad-format-password"));
		} else if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
			throw new CioneException("2##" + translate(
					"cione-module.templates.components.datos-identificativos-component.passwords-do-not-match"));
		} else {				
			if(!CioneUtils.validUserToSycn(changePasswordDTO.getUsername())) {
				throw new CioneException(String.format("El usuario [%s] no tiene permiso para realizar esta acción", changePasswordDTO.getUsername()));
			}			
			UserManager um = securitySupport.getUserManager("public");
			User user = um.getUser(changePasswordDTO.getUsername());
			um.changePassword(user, changePasswordDTO.getNewPassword());
			try {
				Session sesion = MgnlContext.getJCRSession(RepositoryConstants.USERS);
				Node currentNode = sesion.getNodeByIdentifier(user.getIdentifier());
				commandsUtils.publishNodeWithoutWorkflow(currentNode.getPath(), RepositoryConstants.USERS ,true);
			} catch (Exception e) {
				log.error("Error realizando la publicación del nodo",e);
			}		
		}
	}
	
	private void sendToAuthorChangePassword(ChangePasswordDTO changePasswordDTO) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getAuthAuthorPath() + "/.rest/private/my-data/v1/change-password")				
				.queryParam("lang", MgnlContext.getLocale());
		log.info(String.format("enviando peticion: %s", target.getUri()));		
		Response response = null;
		try {			
			response = target.request().header(HttpHeaders.AUTHORIZATION, CioneUtils.getAuthToSync())
					.post(Entity.json(changePasswordDTO));
			String stringResponse = response.readEntity(String.class);
			if(stringResponse == null || !stringResponse.equals("true")) {
				throw  new CioneException(stringResponse);
			}			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CioneRuntimeException(e.getMessage());
		} finally {
			if(response != null) {
				response.close();
			}
		}
	}
	
	
	private boolean isValidPasswordForUser(String username,String pwd) {
		boolean result = false;
		try {
			RestLoginUtils restLoginUtils = new RestLoginUtils(
					CioneUtils.getHttpBasicAuth(username, pwd));
			return restLoginUtils.doLogin();
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	private String translate(String key, Object... args) {
		final SimpleTranslator translator = Components.getComponent(SimpleTranslator.class);
		return translator.translate(key, args);
	}

	private Map<String, Object> buildTemplateEmailForRegisterUser(String name, String idClient, String pwd, String grupoPrecio) {
		Map<String, Object> templateValues = new HashMap<>();

		templateValues.put(MailTemplate.MAIL_TEMPLATE_FILE, "cione-module/templates/mail/register-user-mail.ftl");
		templateValues.put("line1", translate("cione-module.mails.register-user.content.line-1"));
		templateValues.put("line2a", translate("cione-module.mails.register-user.content.line-2-a"));
		templateValues.put("line2b", translate("cione-module.mails.register-user.content.line-2-b"));
		templateValues.put("line2c", translate("cione-module.mails.register-user.content.line-2-c"));
		templateValues.put("line3a", translate("cione-module.mails.register-user.content.line-3-a"));
		templateValues.put("line3b", translate("cione-module.mails.register-user.content.line-3-b"));
		
		templateValues.put("line6", translate("cione-module.mails.register-user.content.line-6"));
		templateValues.put("line7a", translate("cione-module.mails.register-user.content.line-7-a"));
		templateValues.put("line7b", translate("cione-module.mails.register-user.content.line-7-b"));
		templateValues.put("line8", translate("cione-module.mails.register-user.content.line-8"));
		templateValues.put("line9", translate("cione-module.mails.register-user.content.line-9"));
		templateValues.put("line10", translate("cione-module.mails.register-user.content.line-10"));
		templateValues.put("line11", translate("cione-module.mails.register-user.content.line-11"));
		templateValues.put("line12", translate("cione-module.mails.register-user.content.line-12"));
		templateValues.put("line13", translate("cione-module.mails.register-user.content.line-13"));
		templateValues.put("line14", translate("cione-module.mails.register-user.content.line-14"));
		templateValues.put("line15", translate("cione-module.mails.register-user.content.line-15"));
		templateValues.put("line16", translate("cione-module.mails.register-user.content.line-16"));

		
		if (grupoPrecio.equals("OPTCAN") || grupoPrecio.equals("CLIENTEMON")) {
			templateValues.put("logoHeader", getImageLink("/optofive/logo-header-optofive.png"));
			templateValues.put("logo", getImageLink("/optofive/logo-optofive.jpg"));
			templateValues.put("line4", translate("cione-module.mails.register-user.content.line-4-optofive"));
			templateValues.put("line5", translate("cione-module.mails.register-user.content.line-5-optofive"));
			templateValues.put("footer", translate("cione-module.mails.register-user.content.footer-optofive"));
			templateValues.put("tel", translate("cione-module.mails.register-user.content.tel-optofive"));
			templateValues.put("mail", translate("cione-module.mails.register-user.content.mail-optofive"));
			templateValues.put("mycioneonline", "la plataforma online de Optofive");
			templateValues.put("pathLogin", CioneUtils.getURLHttps() + "/optofive");
		} else if (grupoPrecio.equals("OPTICAPRO")) {
				templateValues.put("logoHeader", getImageLink("/opticapro/logo-opticapro.png"));
				templateValues.put("logo", getImageLink("/opticapro/logo-opticapro.png"));
				templateValues.put("line4", translate("cione-module.mails.register-user.content.line-4-opticapro"));
				templateValues.put("line5", translate("cione-module.mails.register-user.content.line-5-opticapro"));
				templateValues.put("footer", translate("cione-module.mails.register-user.content.footer-opticapro"));
				templateValues.put("tel", translate("cione-module.mails.register-user.content.tel-opticapro"));
				templateValues.put("mail", translate("cione-module.mails.register-user.content.mail-opticapro"));
				templateValues.put("mycioneonline", "la plataforma online de Opticapro");
				templateValues.put("pathLogin", CioneUtils.getURLHttps() + "/opticapro");
		} else {
			templateValues.put("logoHeader", getImageLink("/cione/templates/emails/LOGO-CIONE-OK.png"));
			templateValues.put("logo", getImageLink("/cione/templates/emails/LOGO-CIONE-OK.png"));
			templateValues.put("line4", translate("cione-module.mails.register-user.content.line-4"));
			templateValues.put("line5", translate("cione-module.mails.register-user.content.line-5"));
			templateValues.put("footer", translate("cione-module.mails.register-user.footer"));
			templateValues.put("tel", translate("cione-module.mails.register-user.content.tel"));
			templateValues.put("mail", translate("cione-module.mails.register-user.content.mail"));
			templateValues.put("mycioneonline", "MyCione online");
			templateValues.put("pathLogin", CioneUtils.getURLHttps() + "/cione");
		}
		
		templateValues.put("banner", getImageLink("/cione/templates/emails/banner-register-user.jpg"));
		
		templateValues.put("name", name);
		templateValues.put("idClient", idClient);
		templateValues.put("pwd", pwd.trim());

		return templateValues;

	}

	private Map<String, Object> buildTemplateEmailForRecoveryPwd(String name, String pwd, String grupoPrecio) {
		Map<String, Object> templateValues = new HashMap<>();

		templateValues.put(MailTemplate.MAIL_TEMPLATE_FILE, "cione-module/templates/mail/recovery-password-mail.ftl");
		templateValues.put("line1", translate("cione-module.mails.recovery-pwd.content.line-1"));
		templateValues.put("line2", translate("cione-module.mails.recovery-pwd.content.line-2"));
		templateValues.put("line3", translate("cione-module.mails.recovery-pwd.content.line-3"));
		templateValues.put("line4", translate("cione-module.mails.recovery-pwd.content.line-4"));
		templateValues.put("line5", translate("cione-module.mails.recovery-pwd.content.line-5"));
		templateValues.put("line6", translate("cione-module.mails.recovery-pwd.content.line-6"));
		templateValues.put("line7", translate("cione-module.mails.recovery-pwd.content.line-7"));
		templateValues.put("line8", translate("cione-module.mails.recovery-pwd.content.line-8"));
		templateValues.put("line9", translate("cione-module.mails.recovery-pwd.content.line-9"));
		templateValues.put("line12", translate("cione-module.mails.recovery-pwd.content.line-10"));
		templateValues.put("line15", translate("cione-module.mails.recovery-pwd.content.line-11"));
		templateValues.put("line16", translate("cione-module.mails.register-user.content.line-16"));
		
		if (grupoPrecio.equals("OPTCAN") || grupoPrecio.equals("CLIENTEMON")) {
			templateValues.put("logoHeader", getImageLink("/optofive/logo-header-optofive.png"));
			templateValues.put("logo", getImageLink("/optofive/logo-optofive.jpg"));
			templateValues.put("footer", translate("cione-module.mails.register-user.content.footer-optofive"));
			templateValues.put("tel", translate("cione-module.mails.register-user.content.tel-optofive"));
			templateValues.put("mail", translate("cione-module.mails.register-user.content.mail-optofive"));
			templateValues.put("pathLogin", CioneUtils.getURLHttps() + "/optofive");
		} else if (grupoPrecio.equals("OPTICAPRO")) {
				templateValues.put("logoHeader", getImageLink("/opticapro/logo-opticapro.png"));
				templateValues.put("logo", getImageLink("/opticapro/logo-opticapro.png"));
				templateValues.put("footer", translate("cione-module.mails.register-user.content.footer-opticapro"));
				templateValues.put("tel", translate("cione-module.mails.register-user.content.tel-opticapro"));
				templateValues.put("mail", translate("cione-module.mails.register-user.content.mail-opticapro"));
				templateValues.put("pathLogin", CioneUtils.getURLHttps() + "/opticapro");
		} else {
			templateValues.put("logoHeader", getImageLink("/cione/templates/emails/LOGO-CIONE-OK.png"));
			templateValues.put("logo", getImageLink("/cione/templates/emails/LOGO-CIONE-OK.png"));
			templateValues.put("footer", translate("cione-module.mails.register-user.footer"));
			templateValues.put("tel", translate("cione-module.mails.register-user.content.tel"));
			templateValues.put("mail", translate("cione-module.mails.register-user.content.mail"));
			templateValues.put("pathLogin", CioneUtils.getURLHttps() + "/cione");
		}
		
		
		templateValues.put("banner", getImageLink("/cione/templates/emails/banner-recovery-pwd.jpg"));		
		//templateValues.put("pathLogin", CioneUtils.getURLBase() + MgnlContext.getContextPath() + "/cione"); CioneUtils.getURLHttps()
		
		templateValues.put("name", name);
		templateValues.put("pwd", pwd.trim());

		return templateValues;
	}

	private String getImageLink(String path) {
		String link = "";
		try {
			if (damTemplatingFunctions.getAsset("jcr", path) != null) {
				link = CioneUtils.getURLHttps() + damTemplatingFunctions.getAsset("jcr", path).getLink();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el asset " + path);
		}
//		try {
//			Asset asset = damTemplatingFunctions.getAssetForAbsolutePath("jcr", path);
//			if (asset != null) {
//				link = asset.getLink();
//				log.debug(link);
//			}
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
		
//		Asset asset33 = damTemplatingFunctions.getAsset("jcr:ac1eb3b8-afe8-4c55-a0df-884f0f0bf918");
//		
//		String asset2 = damTemplatingFunctions.getAssetLink("jcr:ac1eb3b8-afe8-4c55-a0df-884f0f0bf918");
//		
//		Asset asset4 = damTemplatingFunctions.getAssetForPath("/magnoliaAuthor/dam/jcr:ac1eb3b8-afe8-4c55-a0df-884f0f0bf918/LOGO-CIONE-OK.png");
//		
//		if (damTemplatingFunctions.getAssetLink("jcr:ac1eb3b8-afe8-4c55-a0df-884f0f0bf918") != null) {
//			link = CioneUtils.getURLHttps() + damTemplatingFunctions.getAssetForPath(path);
//		}
		return link;
	}
	
	public String getAssetIdByPath(String workspace, String assetPath) {
        try {
        	Session session = MgnlContext.getJCRSession(workspace);
        	Node assetNode = session.getNode(assetPath);
        	if (assetNode != null) {
        		return assetNode.getIdentifier();
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

	
	@Override
	public List<UserERPCioneDTO> getUsersFromPur() {
		List<UserERPCioneDTO> userList = new ArrayList<>();
		UserManager um = securitySupport.getUserManager("public");
		for (User user : um.getAllUsers()) {
			UserERPCioneDTO userDTO = new UserERPCioneDTO();
			// userDTO.setCliente(user.getName());
			userDTO.setCliente(CioneUtils.getidClienteFromNumSocio(user.getName()));
			userDTO.setNumSocio(user.getName());
			userList.add(userDTO);
		}
		return userList;

	}
	
	
	

	@Override
	public String impersonateUser(String usernameToImpersonate, String usernameImpersonator) throws CioneException {
		if(configService.getConfig().getIsAuthor()) {
			log.info("SOY AUTHOR INSTANCIA: realizo la suplantanción de usaurio y publico a las instancias registradas");
			return impersonateUserLocal(usernameToImpersonate,usernameImpersonator);			
		}else {
			log.info("SOY PUBLIC INSTANCIA: envio la petición de suplantar usuario al author");
			return sendToAuthorImpersonateUser(usernameToImpersonate, usernameImpersonator);			
		}
	}
	
	
	private String impersonateUserLocal(String usernameToImpersonate, String usernameImpersonator) {
		try {			
			if (!MgnlContext.getUser().hasRole(CioneConstants.ROLE_CIONE_SUPERUSER) && !MgnlContext.getUser().hasRole(CioneConstants.ROLE_OPTOFIVE_SUPERUSER)) {
				CioneRuntimeException ex = new CioneRuntimeException(
						"Para acceder a esta funcionalidad tiene que tener role [cione_superuser]");
				log.error(ex.getMessage(), ex);
				throw ex;
			}

			UserManager um = securitySupport.getUserManager("public");
			User impersonator = um.getUser(usernameImpersonator);
			User userToImpersonate = um.getUser(usernameToImpersonate);

			String error;
			if (impersonator == null) {
				error = String.format("El usuario (suplantador) [%s] no existe", usernameImpersonator);
				log.error(error);
				throw new CioneRuntimeException(error);
			}
			
			if (userToImpersonate == null) {
				error = String.format("El usuario (a suplantar) [%s] no existe", userToImpersonate);
				log.error(error);
				throw new CioneRuntimeException(error);
			}
					
			if(!CioneUtils.isEmptyOrNull(impersonator.getProperty(CioneConstants.IMPERSONATE_FIELD_ID_SOCIO))) {
				error = String.format("El usuario (suplantador) [%s] ya está suplantando a otro usuario.", usernameImpersonator);
				log.error(error);
				throw new CioneRuntimeException(error);
			}

			// update fullname
			um.setProperty(impersonator, CioneConstants.IMPERSONATE_FIELD_ID_SOCIO, userToImpersonate.getName());
			um.setProperty(impersonator, CioneConstants.IMPERSONATE_FIELD_NAME_SOCIO,
					userToImpersonate.getProperty(UserProfile.TITLE));
			um.setProperty(impersonator, CioneConstants.IMPERSONATE_FIELD_BACKUP_ROLES,
					String.join(",", impersonator.getAllRoles()));
			
			boolean isOptofive = impersonator.hasRole(CioneConstants.ROLE_OPTOFIVE_SUPERUSER);
			boolean isOpticaPRO = userToImpersonate.hasRole(CioneConstants.OPTICAPRO);

			// clear impersonate user
			for (String role : impersonator.getAllRoles()) {
				um.removeRole(impersonator, role);
			}

			// add roles to impersonate user
			for (String role : getBaseRolesForImpersonateUser(isOptofive, isOpticaPRO)) {
				um.addRole(impersonator, role);
			}
			for (String role : userToImpersonate.getAllRoles()) {
				um.addRole(impersonator, role);
			}	
			
			
			//publish
			Session sesion = MgnlContext.getJCRSession(RepositoryConstants.USERS);
			Node currentNode = sesion.getNodeByIdentifier(impersonator.getIdentifier());
			
			long tiempoInicio = System.currentTimeMillis();
			commandsUtils.publishNodeWithoutWorkflow(currentNode.getPath(), RepositoryConstants.USERS, true);
			long tiempoFin = System.currentTimeMillis();
			log.info("Ha tardado = " + (tiempoFin - tiempoInicio));
			
			/*long tiempoInicio = System.currentTimeMillis();
			String currentNodeId = currentNode.getPath() + "/" + CioneConstants.IMPERSONATE_FIELD_ID_SOCIO;
			commandsUtils.publishNodeWithoutWorkflow(currentNodeId, RepositoryConstants.USERS, true);
			String currentNodeName = currentNode.getPath() + "/" + CioneConstants.IMPERSONATE_FIELD_NAME_SOCIO;
			commandsUtils.publishNodeWithoutWorkflow(currentNodeName, RepositoryConstants.USERS, true);
			String currentNodeBackUp = currentNode.getPath() + "/" + CioneConstants.IMPERSONATE_FIELD_BACKUP_ROLES;
			commandsUtils.publishNodeWithoutWorkflow(currentNodeBackUp, RepositoryConstants.USERS, true);
			
			commandsUtils.publishNodeWithoutWorkflow(currentNode.getNode("roles").getPath(), RepositoryConstants.USERS, true);
			
			long tiempoFin = System.currentTimeMillis();
			log.info("Ha tardado = " + (tiempoFin - tiempoInicio));*/
			
			CustomerCT customer = commercetoolsService.getCustomer(usernameToImpersonate);
			if (customer == null) {
				String userid = usernameToImpersonate;
				//El usuario no esta dado de alta en CommerceTools, lo damos de alta
				log.info("El usuario " + userid + " no existe en CommerceTools");
				
				EmployeeDTO employeeDTO = new EmployeeDTO();
				
				employeeDTO.setId(userid);
				
				DireccionesDTO data = middlewareService.getDirecciones(CioneUtils.getidClienteFromNumSocio(userid));
				
				if (!data.getTransportes().isEmpty()) {
					employeeDTO.setAddress(data.getTransportes().get(0).getId_localizacion());
					Customer customercreate = null;
					try {
						customercreate = commercetoolsService.createCustomer(employeeDTO);
					} catch (BadRequestException e) {
						log.error(e.getMessage(), e);
						throw new CioneRuntimeException("Ya existe un usuario con el mismo mail");
					}
					if (customercreate == null) {
						log.debug("ERROR: No se ha podido crear el empleado en CT");
						throw new CioneRuntimeException("Se ha producido un error al dar de alta el usuario en CommerceTools");
					} else {
						log.debug("INFO: Se ha creado el empleado en CT");
					}
				}else {
					throw new CioneRuntimeException("Se ha producido un error al dar de alta el usuario en CommerceTools");
				}
				log.info("El usuario " + userid + " registrado en CommerceTools correctamente");
			}
			
			
			return translate("cione-module.templates.components.simulate-user.sucess-simulate", usernameToImpersonate);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CioneRuntimeException("Se ha producido un error al suplantar usuario");
		}
	}
	
	public String sendToAuthorImpersonateUser(String usernameToImpersonate, String usernameImpersonator) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getAuthAuthorPath() + "/.rest/private/impersonate/v1/impersonate")
				.queryParam("usernameToImpersonate", usernameToImpersonate)
				.queryParam("usernameImpersonator", usernameImpersonator);		
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, CioneUtils.getAuthToSync()).get();
		if(response.getStatus() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {				
			String errorMessage = response.readEntity(String.class);
			return errorMessage;						
		}else {
			DefaultResponseDTO success = response.readEntity(DefaultResponseDTO.class);
			response.close();
			return success.getTxt();
		}					
	}

	/**
	 * Roles básicos para el usuario suplantador
	 * 
	 * @return
	 */
	private List<String> getBaseRolesForImpersonateUser(boolean isOpto, boolean isOpticaPRO) {
		ArrayList<String> roles = new ArrayList<>();
		roles.add("anonymous");
		if (isOpto)
			roles.add("optofive_superuser");
		else
			roles.add("cione_superuser");
		
		if (!isOpticaPRO) 
			roles.add("cione_pur");
		return roles;
	}

	@Override
	public List<UserERPCioneDTO> getUsersFromPur(String username, String fullName) {
		
//		String userImpersonate = MgnlContext.getUser().getName();
		Boolean isOptoImpersonate = MgnlContext.getUser().hasRole(CioneConstants.ROLE_OPTOFIVE_SUPERUSER);
//		if (isOptoImpersonate) {
//			String query = "and rol";
//		}

		List<UserERPCioneDTO> result = new ArrayList<>();
		try {
			// query = "select * from [mgnl:user] where LOWER(name) like '005%' and
			// LOWER(title) like 'bra%'";
			String query = "select * from [mgnl:user]";
			if (username != null) {
				query += String.format(" where LOWER(name) like '%s'", username.toLowerCase() + "%");
			}
			if (fullName != null) {
				query += (username != null) ? " and" : " where";
				query += String.format(" LOWER(title) like '%s'", fullName.toLowerCase() + "%");
			}
			
			//evitar que busque al propio usuario
			query += String.format(" and name <> '%s'", MgnlContext.getUser().getName());
			

			NodeIterator resultQuery = QueryUtil.search("users", query);
			while (resultQuery.hasNext()) {
				Node node = resultQuery.nextNode();
				UserERPCioneDTO userERPCioneDTO = new UserERPCioneDTO();
				// userERPCioneDTO.setCliente(PropertyUtil.getString(node, "name"));
				String numSocio = PropertyUtil.getString(node, "name"); // cliente+00
				userERPCioneDTO.setCliente(CioneUtils.getidClienteFromNumSocio(numSocio));
				userERPCioneDTO.setNumSocio(PropertyUtil.getString(node, "name"));
				userERPCioneDTO.setNombreComercial(PropertyUtil.getString(node, "title"));
				if (isOptoImpersonate) {
					User user = getUserFromPUR(numSocio);
					if (user.hasRole("OPTCAN")) {
						result.add(userERPCioneDTO);
					}
				} else {
					result.add(userERPCioneDTO);
				}
				
			}
		} catch (Exception e) {
			CioneException cioneException = new CioneException("Error al realizar consulta");
			log.error(cioneException.getMessage());
		}
		return result;
	}

	@Override
	public User getUserFromPUR(String idClient) {
		return securitySupport.getUserManager("public").getUser(idClient);
	}

	@Override
	public boolean isCioneSuperUser(String username) {
		boolean result = false;
		if (securitySupport.getUserManager("public") != null) {
			User user = securitySupport.getUserManager("public").getUser(username);
			if (user != null && (user.hasRole(CioneConstants.ROLE_CIONE_SUPERUSER) || user.hasRole(CioneConstants.ROLE_OPTOFIVE_SUPERUSER))) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public String removeImpersonateUser(String usernameImpersonator) throws CioneException {
		if(configService.getConfig().getIsAuthor()) {
			log.info("SOY AUTHOR INSTANCIA: elimino la suplantanción de usaurio y publico a las instancias registradas");
			return removeImpersonateUserLocal(usernameImpersonator);			
		}else {
			log.info("SOY PUBLIC INSTANCIA: envio la petición de eliminar suplantar usuario al author");
			return sendToAuthorRemoveImpersonateUser(usernameImpersonator);			
		}
	}
	
	public String removeImpersonateUserLocal(String usernameImpersonator) throws CioneException {
		UserManager um = securitySupport.getUserManager("public");
		User impersonator = um.getUser(usernameImpersonator);
		String error;
		if (impersonator == null) {
			error = String.format("El usuario (suplantador) [%s] no existe", impersonator);
			log.error(error);
			throw new CioneRuntimeException(error);
		}

		String backupRoles = impersonator.getProperty(CioneConstants.IMPERSONATE_FIELD_BACKUP_ROLES);
		//List<String> originalRoles = Arrays.asList(backupRoles);
		List<String> originalRoles = Arrays.asList(backupRoles.split("\\s*,\\s*"));

		boolean isOptofive = impersonator.hasRole(CioneConstants.ROLE_OPTOFIVE_SUPERUSER);
		boolean isOpticaPRO = impersonator.hasRole(CioneConstants.OPTICAPRO);
		
		// clear impersonate user
		for (String role : impersonator.getAllRoles()) {
			um.removeRole(impersonator, role);
		}

		// add roles to impersonate user
		for (String role : getBaseRolesForImpersonateUser(isOptofive, isOpticaPRO)) {
			um.addRole(impersonator, role);
		}
		
		for (String role : originalRoles) {
			System.out.println(role);
			um.addRole(impersonator, role);
		}

		// update fullname
		um.setProperty(impersonator, CioneConstants.IMPERSONATE_FIELD_ID_SOCIO, "");
		um.setProperty(impersonator, CioneConstants.IMPERSONATE_FIELD_NAME_SOCIO,"");
		um.setProperty(impersonator, CioneConstants.IMPERSONATE_FIELD_BACKUP_ROLES,"");
		
		//publish
		try {
			Session sesion = MgnlContext.getJCRSession(RepositoryConstants.USERS);
			Node currentNode = sesion.getNodeByIdentifier(impersonator.getIdentifier());
			long tiempoInicio = System.currentTimeMillis();
			commandsUtils.publishNodeWithoutWorkflow(currentNode.getPath(), RepositoryConstants.USERS, true);
			long tiempoFin = System.currentTimeMillis();
			log.info("Ha tardado = " + (tiempoFin - tiempoInicio));
			
			/*long tiempoInicio = System.currentTimeMillis();
			
			String currentNodeId = currentNode.getPath() + "/" + CioneConstants.IMPERSONATE_FIELD_ID_SOCIO;
			commandsUtils.publishNodeWithoutWorkflow(currentNodeId, RepositoryConstants.USERS, true);
			String currentNodeName = currentNode.getPath() + "/" + CioneConstants.IMPERSONATE_FIELD_NAME_SOCIO;
			commandsUtils.publishNodeWithoutWorkflow(currentNodeName, RepositoryConstants.USERS, true);
			String currentNodeBackUp = currentNode.getPath() + "/" + CioneConstants.IMPERSONATE_FIELD_BACKUP_ROLES;
			commandsUtils.publishNodeWithoutWorkflow(currentNodeBackUp, RepositoryConstants.USERS, true);
			
			commandsUtils.publishNodeWithoutWorkflow(currentNode.getNode("roles").getPath(), RepositoryConstants.USERS, true);
			long tiempoFin = System.currentTimeMillis();
			log.info("Ha tardado = " + (tiempoFin - tiempoInicio));*/
		}catch(Exception e) {
			log.error("Error publicando nodo",e);
		}
		

		return translate("cione-module.templates.components.simulate-user.logout-sucess-simulate");
	}
	
	public String sendToAuthorRemoveImpersonateUser(String usernameImpersonator) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getAuthAuthorPath() + "/.rest/private/impersonate/v1/delete-impersonate")
				.queryParam("usernameImpersonator", usernameImpersonator);					
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, CioneUtils.getAuthToSync()).get();
		if(response.getStatus() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
			String errorMessage = "Se ha producido un error";
			if(response.getEntity() != null) {
				 errorMessage = response.readEntity(String.class);
			}			
			throw new CioneRuntimeException(errorMessage);
		}
		String success = response.readEntity(String.class);
		response.close();
		return success;			
	}
	
	public void updateAuditory(String userId, String newsId) throws AccessDeniedException, ItemExistsException, ReferentialIntegrityException, ConstraintViolationException, InvalidItemStateException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException {
		
		LocalDateTime fecha = LocalDateTime.now();
		RepositoryManager repositoryManager = Components.getComponent(RepositoryManager.class);
		WorkspaceMappingDefinition mappingNews = repositoryManager.getWorkspaceMapping(CioneConstants.NOTICIAS_WORKSPACE);
		Session sessionNews = repositoryManager.getSession(mappingNews.getLogicalWorkspaceName(), new SimpleCredentials("admin", "admin".toCharArray()));
		
		/*WorkspaceMappingDefinition mappingUsers = repositoryManager.getWorkspaceMapping(CioneConstants.CIONE_USERS_WORKSPACE);
		Session sessionUsers = repositoryManager.getSession(mappingUsers.getLogicalWorkspaceName(), new SimpleCredentials("admin", "admin".toCharArray()));*/
		
		Node newsNode = sessionNews.getNodeByIdentifier(newsId);
		Node user = templatingFunctions.nodeById(userId, CioneConstants.USERS_WORKSPACE);
		/*Node userNode = sessionUsers.getNodeByIdentifier(userId);
		
		if(userNode != null && newsNode != null) {
		
			Node auditoriaNode = null;
			if(userNode.hasNode(CioneConstants.AUDITORIA_USER_NODE_NAME)) {
				auditoriaNode = userNode.getNode(CioneConstants.AUDITORIA_USER_NODE_NAME);
				
				boolean hasNode = alreadyHasNew(newsId, auditoriaNode);
				
				if(!hasNode) {
					Node readNewsNode = auditoriaNode.addNode(nodeNameHelper.getUniqueName(auditoriaNode, "0"), CioneConstants.CONTENT_NODE_TYPE);
					readNewsNode.setProperty(CioneConstants.AUDITORIA_ID_PROPERTY, newsNode.getIdentifier());
					sessionUsers.save();
					commandsUtils.publishNodeWithoutWorkflow(auditoriaNode.getPath(), RepositoryConstants.USERS, true);
				}
			} else {
				auditoriaNode = userNode.addNode(CioneConstants.AUDITORIA_USER_NODE_NAME, CioneConstants.CONTENT_NODE_TYPE);
				Node readNewsNode = auditoriaNode.addNode(nodeNameHelper.getUniqueName(auditoriaNode, "0"), CioneConstants.CONTENT_NODE_TYPE);
				readNewsNode.setProperty(CioneConstants.AUDITORIA_ID_PROPERTY, newsNode.getIdentifier());
				sessionUsers.save();
				commandsUtils.publishNodeWithoutWorkflow(auditoriaNode.getPath(), RepositoryConstants.USERS, true);
			}
		}
		
	*/
		
		String numSocio = user.getName();
		String firstLevel = numSocio.substring(0,1);
		String secondLevel = numSocio.substring(0,2);
		String path = "/"+CioneConstants.PUBLIC_NODE+ "/" + firstLevel+ "/" + secondLevel+"/"+numSocio;
		String pathFlag = "/";
		Session sessionUsers = MgnlContext.getJCRSession(CioneConstants.CIONE_USERS_WORKSPACE);
		boolean flag = false;
		Node nodeRoot = sessionUsers.getRootNode();
		if (!sessionUsers.nodeExists(path)) {
			
			pathFlag = nodeRoot.getPath();
			if (!nodeRoot.hasNode(CioneConstants.PUBLIC_NODE)) {
				nodeRoot = nodeRoot.addNode(CioneConstants.PUBLIC_NODE, CioneConstants.CONTENT_NODE_TYPE);
				pathFlag = nodeRoot.getPath();
			} else {
				nodeRoot = nodeRoot.getNode(CioneConstants.PUBLIC_NODE);
				pathFlag = nodeRoot.getPath() + "/" +firstLevel;
			}
			if (!nodeRoot.hasNode(firstLevel)) {
				nodeRoot = nodeRoot.addNode(firstLevel, CioneConstants.CONTENT_NODE_TYPE);
			} else {
				nodeRoot = nodeRoot.getNode(firstLevel);
				pathFlag = nodeRoot.getPath() + "/" +secondLevel;
			}
			
			if (!nodeRoot.hasNode(secondLevel)) {
				nodeRoot = nodeRoot.addNode(secondLevel, CioneConstants.CONTENT_NODE_TYPE);
			} else {
				nodeRoot = nodeRoot.getNode(secondLevel);
				pathFlag = nodeRoot.getPath()+"/"+numSocio;
			}
			
			if (!nodeRoot.hasNode(numSocio)) {
				nodeRoot = nodeRoot.addNode(numSocio, CioneConstants.CONTENT_NODE_TYPE);
			}
			
			//publicar todo el usuario
			flag = true;
			
		}
		
		Node node = sessionUsers.getNode(path);
		if (node != null) {
			Node auditoriaNode = null;
			Node yearNode = null;
			if(node.hasNode(CioneConstants.AUDITORIA_USER_NODE_NAME)) {
				auditoriaNode = node.getNode(CioneConstants.AUDITORIA_USER_NODE_NAME);
				boolean hasNode = alreadyHasNew(newsId, auditoriaNode);
				if(!hasNode) {
					if (auditoriaNode.hasNode(String.valueOf(fecha.getYear()))) {
						yearNode = auditoriaNode.getNode(String.valueOf(fecha.getYear()));
					} else {
						yearNode = auditoriaNode.addNode(String.valueOf(fecha.getYear()), CioneConstants.CONTENT_NODE_TYPE);
					}
					
					Node readNewsNode = yearNode.addNode(nodeNameHelper.getUniqueName(yearNode, "0"), CioneConstants.CONTENT_NODE_TYPE);
					readNewsNode.setProperty(CioneConstants.AUDITORIA_ID_PROPERTY, newsNode.getIdentifier());
					sessionUsers.save();
					if (flag)
						commandsUtils.publishNodeWithoutWorkflow(pathFlag, CioneConstants.CIONE_USERS_WORKSPACE, true);
					else
						commandsUtils.publishNodeWithoutWorkflow(auditoriaNode.getPath(), CioneConstants.CIONE_USERS_WORKSPACE, true);
				}
			} else {
				auditoriaNode = node.addNode(CioneConstants.AUDITORIA_USER_NODE_NAME, CioneConstants.CONTENT_NODE_TYPE);
				yearNode = auditoriaNode.addNode(String.valueOf(fecha.getYear()), CioneConstants.CONTENT_NODE_TYPE);
				Node readNewsNode = yearNode.addNode(nodeNameHelper.getUniqueName(yearNode, "0"), CioneConstants.CONTENT_NODE_TYPE);
				readNewsNode.setProperty(CioneConstants.AUDITORIA_ID_PROPERTY, newsNode.getIdentifier());
				sessionUsers.save();
				if (flag)
					commandsUtils.publishNodeWithoutWorkflow(pathFlag, CioneConstants.CIONE_USERS_WORKSPACE, true);
				else
					commandsUtils.publishNodeWithoutWorkflow(auditoriaNode.getPath(), CioneConstants.CIONE_USERS_WORKSPACE, true);
			}
		}
		sessionNews.logout();
		sessionUsers.logout();
		
		//sessionUsers.save();
		
	}
	
	private boolean alreadyHasNew(String newId, Node auditoriaNode) {
		
		try {
		
			boolean hasNode = false;
			NodeIterator nIt = auditoriaNode.getNodes();
			while(nIt.hasNext() && !hasNode) {
				Node current = nIt.nextNode();
				if(current.hasProperty(CioneConstants.AUDITORIA_ID_PROPERTY) && current.getProperty(CioneConstants.AUDITORIA_ID_PROPERTY).getString().equals(newId)) {
					hasNode = true;
				}
			}
			return hasNode;
			
		} catch(Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	@Override
	public Response registerOrUpdateEmployee(EmployeeDTO employeeDTO) {
		if(configService.getConfig().getIsAuthor()) {
			log.info("SOY AUTHOR INSTANCIA: activo el empleado y publico a las instancias registradas");
			return registerEmployeeLocal(employeeDTO);
		}else {
			log.info("SOY PUBLIC INSTANCIA: envio el registro al author");
			return sendToAuthorRegisterEmployee(employeeDTO);
		}
	}
	
	private boolean existMail(String email) throws RepositoryException {
		
		
		String query = "select * from [mgnl:user]";
		if (email != null) {
			query += String.format(" where email = '" +email + "'");
		}

		NodeIterator resultQuery = QueryUtil.search("users", query);
		
		if (resultQuery.hasNext())
			return true;
		else
			return false;
	}
	
	private boolean existMail(EmployeeDTO employeeDTO) throws RepositoryException {
		
		
		String query = "select * from [mgnl:user]";
		if (employeeDTO.getMail() != null) {
			query += String.format(" where email = '" +employeeDTO.getMail() + "'");
		}
		
		if (employeeDTO.getId() != null) {
			query += String.format(" and name <> '%s'", employeeDTO.getId());
		}

		NodeIterator resultQuery = QueryUtil.search("users", query);
		
		if (resultQuery.hasNext())
			return true;
		else
			return false;
	}
	
	/**
	 * 
	 * Crea un nuevo empleado o actualiza uno existente con los datos que vengan en el DTO de empleado. 
	 * Luego lo publica a las instancias publicas existentes
	 * 
	 * @param employeeDTO Datos del empleados
	 * @throws CioneException 
	 * 
	 */
	private Response registerEmployeeLocal(EmployeeDTO employeeDTO) {
		
		log.debug("ENTRA EN AUTHOR");
		
		if(employeeDTO.getId() != null && StringUtils.isNotEmpty(employeeDTO.getId())) {
			log.debug("id=" + employeeDTO.getId());
			UserManager um = securitySupport.getUserManager("public");
			
			
			
			//Customer customer = Customer.builder().build();
			Customer customer = null;
			boolean flagAPICT = true;
			
			// Creacion o actualizacion en CommerceTools
			if ((um.getUser(employeeDTO.getId()) == null)) { //si no existe en Magnolia
				log.debug("no existe en Magnolia");
				/*try {
					if (existMail(employeeDTO.getMail())) {
						return Response.status(Response.Status.PRECONDITION_FAILED)
								.entity("Ya existe un usuario con el mismo mail").build();
					}
				} catch (RepositoryException e1) {
					log.error("Error al obtener el mail ", e1);
				}*/
				
				
				if (!commercetoolsService.customerExists(employeeDTO.getId())) {
					try {
						customer = commercetoolsService.createCustomer(employeeDTO);
					} catch (BadRequestException e) {
						log.error(e.getMessage(), e);
						return Response.status(Response.Status.PRECONDITION_FAILED)
								.entity("Ya existe un usuario con el mismo mail").build();
					}
					flagAPICT = customer == null ? false : true;
					if (customer == null) {
						log.debug("ERROR: No se ha podido crear el empleado en CT"); 
						return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
								.entity("No se ha podido crear el empleado en CT").build();
					} else {
						log.debug("INFO: Se ha creado el empleado en CT"); 
					}
					
				} else {
					//aqui podemos llegar porque el usuario no exista en Magnolia pero si en commercetools
					//si es el caso lo actualizamos
					try {
						customer = commercetoolsService.updateCustomerEmployee(employeeDTO);
					} catch (BadRequestException e) {
						log.error(e.getMessage(), e);
						return Response.status(Response.Status.PRECONDITION_FAILED)
								.entity("Ya existe un usuario con el mismo mail").build();
					}
					flagAPICT = customer == null ? false : true;
					log.debug("Actualizar en CT.");
				}
				
				
			} else {
				log.debug("SI existe en Magnolia");
				
				try {
					if (existMail(employeeDTO)) {
						return Response.status(Response.Status.PRECONDITION_FAILED)
								.entity("Ya existe un usuario con el mismo mail").build();
					}
				} catch (RepositoryException e1) {
					log.error("Error al obtener el mail ", e1);
				}
				
				//si existe lo actualizamos
				User user = um.getUser(employeeDTO.getId());
				
				try {
					
					Node userNode = NodeUtil.getNodeByIdentifier(RepositoryConstants.USERS, user.getIdentifier());
					String address = userNode.getProperty("address").getString();
					
					//if (!employeeDTO.getAddress().equals(address)) {
						try {
							customer = commercetoolsService.updateCustomerEmployee(employeeDTO);
						} catch (BadRequestException e) {
							log.error(e.getMessage(), e);
							return Response.status(Response.Status.PRECONDITION_FAILED)
									.entity("Ya existe un usuario con el mismo mail").build();
						}
						flagAPICT = customer == null ? false : true;
						log.debug("Actualizar en CT.");
					//}
					
				} catch (RepositoryException e) {
					log.error(e.getMessage(), e);
					return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
							.entity(e.getMessage()).build();
				}
			}
			// Creacion o actualizacion en Magnolia
			if (flagAPICT) {
				
				try {
					if (existMail(employeeDTO)) {
						log.error("Ya existe un usuario con el mismo mail " + employeeDTO.getId() + " | " + employeeDTO.getMail());
						return Response.status(Response.Status.PRECONDITION_FAILED)
								.entity("Ya existe un usuario con el mismo mail").build();
					}
				} catch (RepositoryException e1) {
					log.error("Error al obtener el mail ", e1);
				}
				
				// Obtenemos el socio al que estara asociado el empleado
				User partner = um.getUser(CioneUtils.getIdSocioFromEmpleado(employeeDTO.getId()));
				
				if(partner != null && isValidEmployee(employeeDTO)) {
					
					// Obtenemos el usuario si ya existe (para actualizar), o lo creamos si no
					User user = um.getUser(employeeDTO.getId());
					
					if(user == null) {
						user = um.createUser(employeeDTO.getId(), employeeDTO.getPsw());
					}
					
					if(StringUtils.isNotEmpty(employeeDTO.getPsw())) {
						um.changePassword(user, employeeDTO.getPsw());
					}
					
					um.setProperty(user, UserProfile.TITLE, employeeDTO.getName());
					um.setProperty(user, "name", employeeDTO.getId());
					um.setProperty(user, "associatedTo", partner.getName());
					um.setProperty(user, "address", employeeDTO.getAddress());
					um.setProperty(user, "email", employeeDTO.getMail());
					
					// si un usuario tiene un configuracion de visualizacion de precios
					// en pantalla que ahora, dado el rol que se le va a asignar,
					// puede que no pueda ver dichos precios asi que se borrara y el 
					// usuario tendra que volver a poner la configuracion que el pueda/desee
					if (um.getUser(employeeDTO.getId()) != null) {
						
						//comentamos este if dado que necesitamos setear el precio seleccionado, es el que tenemos en cuenta
						//a la hora de mostrar los precios
						//if(user.hasRole(employeeDTO.getRolprecio())) { 
							try {
								Node userNode = NodeUtil.getNodeByIdentifier(RepositoryConstants.USERS, user.getIdentifier());
								
								if(userNode.hasNode(MyshopConstants.VIEWPRICES_USER_NODE_NAME)) {
									Node priceNode = userNode.getNode(MyshopConstants.VIEWPRICES_USER_NODE_NAME);
									priceNode.setProperty(MyshopConstants.PRICE_CONFIGURATION_PROPERTY, getTypePrice(employeeDTO.getRolprecio()));
								} else {
									Node priceNode = userNode.addNode(MyshopConstants.VIEWPRICES_USER_NODE_NAME, CioneConstants.CONTENT_NODE_TYPE);
									priceNode.setProperty(MyshopConstants.PRICE_CONFIGURATION_PROPERTY, getTypePrice(employeeDTO.getRolprecio()));
								}
								
							} catch (RepositoryException e) {
								return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
										.entity(e.getMessage()).build();
							}
						//}
					}
					
					sanitizeRoles(user, um);
					// Aniadimos el rol del perfil indicado
					if(employeeDTO.getProfile() != null && StringUtils.isNotEmpty(employeeDTO.getProfile())) {
						if(!employeeDTO.getProfile().equals("cione_superuser") && !employeeDTO.getProfile().equals("superuser")) {
							um.addRole(user, employeeDTO.getProfile());
						}
					}
					
					if(employeeDTO.getRolpreciopantalla() != null && StringUtils.isNotEmpty(employeeDTO.getRolpreciopantalla())) {
						if(!employeeDTO.getRolpreciopantalla().equals("cione_superuser") && !employeeDTO.getRolpreciopantalla().equals("superuser")) {
							um.addRole(user, employeeDTO.getRolpreciopantalla());
						}
					}
					// Aniadimos el rol del precio
					if(employeeDTO.getRolprecio() != null && StringUtils.isNotEmpty(employeeDTO.getRolprecio())) {
						if(!employeeDTO.getRolprecio().equals("cione_superuser") && !employeeDTO.getRolprecio().equals("superuser")) {
							um.addRole(user, employeeDTO.getRolprecio());
						}
					}
					// Aniadimos los mismos roles que tenga el socio al que se asocia, excluyendo el de superuser
					for(String roleName : partner.getRoles()) {
						if(!roleName.equals("cione_superuser") && !roleName.equals("superuser"))
							um.addRole(user, roleName);
					}
					//um.addRole(user, "acceso-area-privada"); // Rol base para empleados que limita accesos
					
					// Aniadimos los roles que vengan del formulario
					for(String roleName : employeeDTO.getRoles()) {
						if(!roleName.equals("cione_superuser") && !roleName.equals("superuser"))
							um.addRole(user, roleName);
					}
					// Publicar el nuevo usuario
					try {
						
						Session sesion = MgnlContext.getJCRSession(RepositoryConstants.USERS);
						Node userNode = NodeUtil.getNodeByIdentifier("users", user.getIdentifier());
						sesion.save();
						commandsUtils.publishNodeWithoutWorkflow(userNode.getPath(), "users", true);
				
					} catch (RepositoryException e) {
						log.error("Error al publicar el nuevo usuario", e);
						return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
								.entity(e.getMessage()).build();
					}
					
				} else {
					return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
							.entity("Error al obtener el partner").build();
				}
				
			}else {
				log.error("Error al crear el usuario en Commerce Tools.");
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity("Error al crear el usuario en Commerce Tools").build();
			}
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Error al obtener el id del empleado").build();
		}
		
		return Response.ok(true).build();
	}
	
	private String getTypePrice(String rol) {
		switch (rol) {
		case MyshopConstants.HIDDEN_PRICES_ROL:
			return "hidden";
		case MyshopConstants.PVP_ROL:
			return "pvp";
		case MyshopConstants.PVO_ROL:
			return "pvo";
		case MyshopConstants.PVP_PVO_ROL:
			return "pvp-pvo";
		default :
			return "hidden";
		}
	}
	
	/**
	 * 
	 * Envia una peticion a la instancia de author para que cree o actualice un nuevo empleado
	 * 
	 * @param employeeDTO Datos del empleado que se creara/actualizara
	 * 
	 */
	private Response sendToAuthorRegisterEmployee(EmployeeDTO employeeDTO) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getAuthAuthorPath() + "/.rest/auth/v1/employee/register")
				.queryParam("lang", MgnlContext.getLocale());
		log.info(String.format("enviando peticion: %s", target.getUri()));		
		Response response;
		int status = 503;
		try {
			response = target.request().post(Entity.entity(employeeDTO, MediaType.APPLICATION_JSON));
			status = response.getStatus();
			String stringResponse = response.readEntity(String.class);
			log.debug("!!!" + stringResponse);
			if(stringResponse == null || !stringResponse.equals("true")) {
				throw  new CioneException(stringResponse);
			}
			response.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(status).entity(e.getMessage()).build();
		}
		return Response.ok(true).build();
	}
	
	@Override
	public void removeEmployee(EmployeeDTO employeeDTO) {
		if(configService.getConfig().getIsAuthor()) {
			log.info("SOY AUTHOR INSTANCIA: elimino el empleado y publico a las instancias registradas");
			removeEmployeeLocal(employeeDTO);			
		}else {
			log.info("SOY PUBLIC INSTANCIA: envio el registro al author");
			sendToAuthorRemoveEmployee(employeeDTO);			
		}
	}
	
	/**
	 * 
	 * Elimina un usuario empleado
	 * 
	 * @param employeeDTO Datos del empleado en el que se encuentra su identificador
	 * 
	 */
	private void removeEmployeeLocal(EmployeeDTO employeeDTO) {
		
		UserManager um = securitySupport.getUserManager("public");
		User partner = um.getUser(CioneUtils.getIdSocioFromEmpleado(employeeDTO.getId()));
		
		if(partner != null) {
			
			User user = um.getUser(employeeDTO.getId());
			
			try {
				
				Session sesion = MgnlContext.getJCRSession(RepositoryConstants.USERS);
				Node userNode = sesion.getNodeByIdentifier(user.getIdentifier());
				
				boolean value = false;
				
				if (userNode.hasProperty("enabled") && userNode.getProperty("enabled").getBoolean() == false) {
					value = true;
				}
				
				userNode.setProperty("enabled", value);
				sesion.save();
				commandsUtils.publishNodeWithoutWorkflow(userNode.getPath(), RepositoryConstants.USERS, true);
				
			} catch (LoginException e) {
				e.printStackTrace();
			} catch (RepositoryException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void sendToAuthorRemoveEmployee(EmployeeDTO employeeDTO) {
		
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getAuthAuthorPath() + "/.rest/auth/v1/employee/remove")
				.queryParam("lang", MgnlContext.getLocale());
		log.info(String.format("enviando peticion: %s", target.getUri()));		
		Response response;
		
		try {
			response = target.request().post(Entity.entity(employeeDTO, MediaType.APPLICATION_JSON));
			String stringResponse = response.readEntity(String.class);
			if(stringResponse == null || !stringResponse.equals("true")) {
				throw  new CioneException(stringResponse);
			}
			response.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CioneRuntimeException(e.getMessage());
		}
	}
	
	/**
	 * 
	 * Borra todos los roles de un usuario
	 * 
	 * @param user usario sobre el que se le va a aplicar
	 * @param um manager para el borrado
	 * 
	 */
	private void sanitizeRoles(User user, UserManager um) {
		
		// Borramos todos los roles que tenga para aniadirlo los que vengan
		for(String roleName : user.getRoles()) {
			if(user.hasRole(roleName))
				um.removeRole(user, roleName);
		}
		
	}
	
	/**
	 * 
	 * Nos permite determinad si lo que viene desde front es correcto
	 * antes de realizar operaciones sobre el empleado
	 * 
	 * @param employeeDTO empleado a validar
	 * @return true si es valido, false en caso contrario
	 * 
	 */
	private boolean isValidEmployee(EmployeeDTO employeeDTO) {
		
		boolean res = false;
		UserManager um = securitySupport.getUserManager("public");
		
		
		if (employeeDTO != null) {
			
			User partner = um.getUser(CioneUtils.getIdSocioFromEmpleado(employeeDTO.getId()));
			User user = um.getUser(employeeDTO.getId());
			
			if(partner != null) {
				
				if(user == null) {
					
					// si el usuario no existe se va a crear entonces debe tener todos los datos
					if(employeeDTO.getProfile() != null && StringUtils.isNotEmpty(employeeDTO.getProfile()) &&
					   employeeDTO.getName() != null && StringUtils.isNotEmpty(employeeDTO.getName()) &&
					   employeeDTO.getAddress() != null && StringUtils.isNotEmpty(employeeDTO.getAddress()) &&
					   employeeDTO.getPsw() != null && StringUtils.isNotEmpty(employeeDTO.getPsw()) &&
					   employeeDTO.getRolprecio() != null && StringUtils.isNotEmpty(employeeDTO.getRolprecio())) {
						
						res = true;
					}
					
				}else {
					
					// en modo edicion se usaran todos los valores pero no la password dado que si no se
					// modifica se mantendra la misma
					if(employeeDTO.getProfile() != null && StringUtils.isNotEmpty(employeeDTO.getProfile()) &&
					   employeeDTO.getName() != null && StringUtils.isNotEmpty(employeeDTO.getName()) &&
					   employeeDTO.getAddress() != null && StringUtils.isNotEmpty(employeeDTO.getAddress()) &&
					   employeeDTO.getRolprecio() != null && StringUtils.isNotEmpty(employeeDTO.getRolprecio())) {
						
						res = true;
					}
				}
			}
		}
		
		return res;
	}
	
	private ResponseBuilder generateResponseBuilder(String res, Status status) {

		return Response.status(status)
				.type(MediaType.APPLICATION_JSON + "; charset=utf-8")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8"))
				.header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
				.header(HttpHeaders.EXPIRES, "0")
				.entity(res);

	}

	@Override
	public void addUserRol(String numSocio, String newRol) {
		if(configService.getConfig().getIsAuthor()) {
			log.info("SOY AUTHOR INSTANCIA: Modifico el usuario y publico a las instancias registradas");
			addUserRolLocal(numSocio, newRol);			
		}else {
			log.info("SOY PUBLIC INSTANCIA: envio el registro al author");
			sendToAuthorAddUserRolLocal(numSocio, newRol);
		}
	}
	
//	@Override
//	public void removeOM(User user, String userid) {
//		if(configService.getConfig().getIsAuthor()) {
//			log.info("SOY AUTHOR INSTANCIA: Elimino el rol de OM");
//			removeUserRol(userid, roleOM(user));
//			//removeOMLocal(user, userid);			
//		}else {
//			log.info("SOY PUBLIC INSTANCIA: envio el registro al author");
//			sendToAuthorRemoveOMLocal(user, userid);
//		}
//	}
//	
//	private String roleOM(User user) {
//		if (user.hasRole(CioneConstants.OM_90)) {
//			return CioneConstants.OM_90;
//		} else if (user.hasRole(CioneConstants.OM_180)) {
//			return CioneConstants.OM_180;
//		} else if (user.hasRole(CioneConstants.OM_360)) {
//			return CioneConstants.OM_360;
//		} else {
//			return "";
//		}
//	}
	

	private void addUserRolLocal(String numSocio, String newRol) {
		
		UserManager um = securitySupport.getUserManager("public");
		User user = um.getUser(numSocio);
		
		if (newRol.equals(CioneConstants.OM_90) || newRol.equals(CioneConstants.OM_180) || newRol.equals(CioneConstants.OM_360)) {
			//vengo de modificar el nivel de OM lo elimino y despues añade el nuevo
			if (user.hasRole(CioneConstants.OM_90)) {
				um.removeRole(user, CioneConstants.OM_90);
			}
			if (user.hasRole(CioneConstants.OM_180)) {
				um.removeRole(user, CioneConstants.OM_180);
			}
			if (user.hasRole(CioneConstants.OM_360)) {
				um.removeRole(user, CioneConstants.OM_360);
			}
		}
		
		um.addRole(user, newRol);
		
		try {
			Session sesion = MgnlContext.getJCRSession(RepositoryConstants.USERS);
			Node userNode = sesion.getNodeByIdentifier(user.getIdentifier()).getNode("roles");
			sesion.save();
			commandsUtils.publishNodeWithoutWorkflow(userNode.getPath(), RepositoryConstants.USERS, true);
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}
	
	
	private void sendToAuthorAddUserRolLocal(String numSocio, String newRol) {
		
		Response response = null;
		//UserManager um = securitySupport.getUserManager("public");
		
		UserAndNewRoleDTO userandrol = new UserAndNewRoleDTO();
		userandrol.setNewRol(newRol);
		userandrol.setNumSocio(numSocio);
		
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getAuthAuthorPath() + "/.rest/auth/v1/employee/addrol");
				//.queryParam("lang", MgnlContext.getLocale());
		
		log.info(String.format("Enviando peticion: %s", target.getUri()));
		
		try {
			response = target.request().post(Entity.json(userandrol));
			log.info(String.format("Peticion recibida: %s", response.getStatus()));
			response.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
	}

	@Override
	public void removeUserRol(String numSocio, String rol) {
		if(configService.getConfig().getIsAuthor()) {
			log.info("SOY AUTHOR INSTANCIA: Modifico el usuario y publico a las instancias registradas");
			removeUserRolLocal(numSocio, rol);			
		}else {
			log.info("SOY PUBLIC INSTANCIA: envio el registro al author");
			sendToAuthorRemoveUserRolLocal(numSocio, rol);
		}
	}
	
	private void removeUserRolLocal(String numSocio, String rol) {
		
		UserManager um = securitySupport.getUserManager("public");
		User user = um.getUser(numSocio);
		
		um.removeRole(user, rol);
		
		try {
			Session sesion = MgnlContext.getJCRSession(RepositoryConstants.USERS);
			Node userNode = sesion.getNodeByIdentifier(user.getIdentifier()).getNode("roles");
			sesion.save();
			
			commandsUtils.publishNodeWithoutWorkflow(userNode.getPath(), RepositoryConstants.USERS, true);
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}
	
	private void sendToAuthorRemoveUserRolLocal(String numSocio, String newRol) {
		
		Response response = null;
		
		UserAndNewRoleDTO userandrol = new UserAndNewRoleDTO();
		userandrol.setNewRol(newRol);
		userandrol.setNumSocio(numSocio);
		
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getAuthAuthorPath() + "/.rest/auth/v1/employee/removerol");
		
		log.info(String.format("Enviando peticion: %s", target.getUri()));
		
		try {
			response = target.request().post(Entity.json(userandrol));
			log.info(String.format("Peticion recibida: %s", response.getStatus()));
			response.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
	}
	
	private User getUserMgnl() {
		String idClient = MgnlContext.getUser().getName();
		// impersonate
		if (MgnlContext.getUser().hasRole(CioneConstants.ROLE_CIONE_SUPERUSER) || MgnlContext.getUser().hasRole(CioneConstants.ROLE_OPTOFIVE_SUPERUSER)) {
			String idToSimulate = MgnlContext.getUser().getProperty(CioneConstants.IMPERSONATE_FIELD_ID_SOCIO);
			if (idToSimulate != null && !idToSimulate.isEmpty()) {
				idClient = idToSimulate;
			}
		}
		
		return securitySupport.getUserManager("public").getUser(idClient);
	}
	
	private String encriptar(String idUsuario, String mail) {
        try {
            String key = "1234567812345678";
            String initVector = "flarumPKPassInit";//
            long expirationTime = System.currentTimeMillis() + (1000 * 60 * 60); // 1 hora desde ahora
            String valueToEncrypt = idUsuario +"," + mail + "," + expirationTime; // idUsuario,mail, timestamp

            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(valueToEncrypt.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }
	
	private String desencriptar(String idUsuarioEncrypt) {
		try {
			String key = "1234567812345678";
	        String initVector = "flarumPKPassInit";//
	        
	        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	        
	        byte[] original = cipher.doFinal(Base64.getDecoder().decode(idUsuarioEncrypt.getBytes()));
	        
	        String decryptedString = new String(original);
	        return decryptedString;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
            return null;
        }
	}
	
	public Response loginForo(String url, String userId) {
		User user = getUserFromPUR(userId);
		String mail = user.getProperty("email");
		String encriptado = encriptar(userId, mail);
		if (encriptado != null) {
			JSONObject jsonRes = new JSONObject();
			jsonRes.put("token", encriptado);
			return generateResponseBuilder(encriptado, Response.Status.OK).build();
		} else {
			return generateResponseBuilder("No se ha podido desencriptar", Response.Status.BAD_REQUEST).build();
		}
	}
	
	public String getNumSocioEncrypt() {
		
		String numSocioEncrypt ="";
		try {
			String idUser = CioneUtils.getIdCurrentClient();
			User user = getUserFromPUR(idUser);
			if (user != null) {
				String mail = user.getProperty("email");
				String encrypt = encriptar(idUser, mail);
				numSocioEncrypt = CioneUtils.encodeURIComponent(encrypt);
				log.debug("Desencriptado: " + desencriptar(encrypt));
				log.debug("Decode Encriptado: " + numSocioEncrypt);
			}
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
		return numSocioEncrypt;
	}
	
	/*public String getSSOServices(String url) {
		String urlSSO ="";
		try {
			String urlGetToken = url + "?rest_route=/simple-jwt-login/v1/auth";
			
			Form form = new Form();
			form.param("username", "cione");
			form.param("password", "43QUR6THRe4ayDJ8A8abwIiXT");
			form.param("cione_auth", "pdHMk34-XeDjiF8WJ8RbvvuY9Vu63UDbB4U");
			
			ResteasyClient client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).disableTrustManager().build();
			
			//ResteasyClient client = CioneEcommerceConectionProvider.restClientServicios;
			WebTarget target = client.target(urlGetToken);
			Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.form(form));
			
			ServiciosDTO respuesta = response.readEntity(ServiciosDTO.class);
			
			log.debug(respuesta.getData().getJwt());
			String jwtToken = respuesta.getData().getJwt();
			
			urlSSO = url + "?rest_route=/simple-jwt-login/v1/autologin&JWT="+jwtToken+"&cione_auth=pdHMk34-XeDjiF8WJ8RbvvuY9Vu63UDbB4U";
			
			response.close();
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
		return urlSSO;
	}*/
	
		/**
	 * 
	 * Con los datos de configuracion obtenemos el token de acceso
	 * 
	 * @return token de acceso a web de servicios para hacer el sso
	 */
	public String getSSOServices() {
		String urlSSO = null;
		String tokenJWT = (String) cioneEcommerceConectionProvider.getCacheAuthToken().getIfPresent("JWT_services");
		if ( tokenJWT != null) {
			//comprobar si es valido
			
			String urlValidateToken = configService.getConfig().getApiUrlServices()
					+"?rest_route=/simple-jwt-login/v1/auth/validate&JWT="+tokenJWT;
			Form form = new Form();
			form.param("username", configService.getConfig().getApiUserServices());
			form.param("password", configService.getConfig().getApiPasswordServices());
			
			ResteasyWebTarget target = CioneEcommerceConectionProvider.restClient.target(urlValidateToken);
			
			Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.form(form));
			
			if (response.getStatus() == 200) {
				try {
					String jsonResponse = response.readEntity(String.class);

	                // Parsear el JSON
	                ObjectMapper objectMapper = new ObjectMapper();
	                JsonNode rootNode = objectMapper.readTree(jsonResponse);
	                boolean success = rootNode.path("success").asBoolean();
	                
	                if (success) {
	                	return configService.getConfig().getApiUrlServices()
	                		+"?rest_route=/simple-jwt-login/v1/autologin&JWT="
	                		+tokenJWT+"&cione_auth="
	                		+configService.getConfig().getApiCioneAuthServices();
	                } else {
	                	return generateTokenServicios();
	                }
	                
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
			response.close();
			
		} else {
			return generateTokenServicios();
		}
		
		return urlSSO;
		
	}
	
	private String generateTokenServicios() {
		String urlSSO= "";
		String urlGetToken = configService.getConfig().getApiUrlServices()+"?rest_route=/simple-jwt-login/v1/auth";
		Form form = new Form();
		form.param("username", configService.getConfig().getApiUserServices());
		form.param("password", configService.getConfig().getApiPasswordServices());
		form.param("cione_auth", configService.getConfig().getApiCioneAuthServices());
		try {
			ResteasyWebTarget target = CioneEcommerceConectionProvider.restClient
					.target(urlGetToken);
			Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.form(form));
			ServiciosDTO respuesta = response.readEntity(ServiciosDTO.class);
			log.debug(respuesta.getData().getJwt());
			String jwtToken = respuesta.getData().getJwt();
			cioneEcommerceConectionProvider.getCacheAuthToken().put("JWT_services", jwtToken);
			
			urlSSO = configService.getConfig().getApiUrlServices()
				+"?rest_route=/simple-jwt-login/v1/autologin&JWT="
				+jwtToken+"&cione_auth="
				+configService.getConfig().getApiCioneAuthServices();
			response.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return urlSSO;
	}
	
	
	
/*	public Response loginForo(String url, String userId) {
		
		encriptar(userId);
		desencriptar(userId);
		
		Response response = null;
		
		User user = getUserMgnl();
		
		try {
			log.debug("LOGIN FORO");
			ResteasyClient client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).disableTrustManager().build();
			WebTarget target = client.target(url + "/login-test.php");
			MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
			params.putSingle("user", user.getName());
			//params.add("user", user.getName());
			//String pass1 = SecurityUtil.getBCrypt(user.getPassword());
			String pass= user.getName(); //password generada hasta resolver el sso
			//String pass= user.getName();
			params.putSingle("pass", pass);
			params.putSingle("email", user.getName()+"@cione.es");
			log.debug(params.toString());
			
			response = target.request(MediaType.APPLICATION_JSON).post(Entity.form(params));
			int code = response.getStatus();
			log.debug("codigo de respuesta" + code);
			String respuesta = response.readEntity(String.class);
			log.debug("Respuesta" + respuesta);
			response.close();
			client.close();
			
			return Response.status(code).entity(respuesta).build();
		} catch (SecurityException ex) {
			log.error("ERROR " + ex.getMessage(), ex);
		} catch (Exception e) {
			log.error("ERROR " + e.getMessage(), e);
		}
		
		return response;
	}*/

}