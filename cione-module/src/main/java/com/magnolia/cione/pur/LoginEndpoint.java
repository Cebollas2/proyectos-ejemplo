package com.magnolia.cione.pur;

import javax.inject.Inject;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.security.auth.Subject;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import info.magnolia.cms.security.SecuritySupport;
import info.magnolia.cms.security.SecurityUtil;
import info.magnolia.cms.security.UserManager;
import info.magnolia.cms.security.auth.callback.CredentialsCallbackHandler;
import info.magnolia.context.MgnlContext;
import info.magnolia.context.SystemContext;
import info.magnolia.context.WebContext;
import info.magnolia.jcr.predicate.NodeTypePredicate;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.objectfactory.Components;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;


/**
 * The Class LoginEndpoint.
 *
 * @param <D> the generic type
 */
//@Api(value = "/users/login/v1")
@Path("/users/login/v1")
public class LoginEndpoint <D extends EndpointDefinition> extends AbstractEndpoint<D>{
	
	@Inject
	private ResponseUtils responseUtils;
	
	@Inject
	private ResponseService responseService;
	
	@Inject
	private SecuritySupport securitySupport;

	/**
	 * Instantiates a new login endpoint.
	 *
	 * @param endpointDefinition the endpoint definition
	 */
	@Inject
	protected LoginEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}
	
	/**
	 * Login method.
	 *
	 * Method: POST
	 * Method uri: /.rest/users/login/v1
	 *
	 * @param auth the authentication string that have been passed in the header
	 * @return the response with the status
	 * @throws RepositoryException 
	 * @throws LoginException 
	 */
	@SuppressWarnings("deprecation")
	@POST
	@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	//@ApiOperation(value = "Login endpoint operation")
	public Response doLogin(@HeaderParam("authorization") String auth) throws LoginException, RepositoryException {
		
		WebContext ctx = MgnlContext.getWebContext();
		Session session = ctx.getJCRSession("users");
			
				 Iterable<Node> users = NodeUtil.collectAllChildren(session.getRootNode(), new NodeTypePredicate(NodeTypes.User.NAME));
			
				for (Node node : users) {
					System.out.println(node.getProperties("pswd"));
				}
				
				/*
				users.each() {
					it.
//				    if (it.name != "anonymous" && it.name != "superuser" ) {
//				        it.setProperty("pswd", "")
//				        it.save()
//				        count++
//				    }
				}*/
		
		
		WebContext webContext = MgnlContext.getWebContext();
		UserManager um = securitySupport.getUserManager("public");
		Subject s = SecurityUtil.createSubjectAndPopulate(um.getUser("0050800"));
		Components.getComponent(SecuritySupport.class).authenticate(new CredentialsCallbackHandler("0050800", "x".toCharArray()), null);
		
		
		//System.out.println(SecurityUtil.decrypt(um.getUser("0050800").getPassword()));
		
		RestLoginUtils restLoginUtils = new RestLoginUtils(auth);
		if(true) {
			return responseUtils.generateResponseBuilder(responseService.generateOkResponseWithMessage("Login successfull")).build();
		}
		return responseUtils.generateResponseBuilder(responseService.generateResponseWithCodeAndMessage(HttpCodes.UNAUTHORIZED_CODE, HttpCodes.UNAUTHORIZED_MESSAGE)).build();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	//@ApiOperation(value = "Login endpoint operation")
	public Response logout(@HeaderParam("authorization") String auth) {
		RestLoginUtils restLoginUtils = new RestLoginUtils(auth);
		restLoginUtils.logout();
		return responseUtils.generateResponseBuilder(responseService.generateOkResponseWithMessage("Logout successfull")).build();
	}

}
