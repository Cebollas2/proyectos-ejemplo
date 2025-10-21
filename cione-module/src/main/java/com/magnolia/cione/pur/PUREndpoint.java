package com.magnolia.cione.pur;

import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import info.magnolia.cms.security.SecuritySupport;
import info.magnolia.context.MgnlContext;
import info.magnolia.module.form.processors.FormProcessorFailedException;
import info.magnolia.objectfactory.Components;
import info.magnolia.repository.RepositoryConstants;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;

/**
 * The Class PUREndpoint.
 *
 * @param <D> the generic type
 */
//@Api(value = "/users/v1")
@Path("/users/v1")
public class PUREndpoint <D extends EndpointDefinition> extends AbstractEndpoint<D> {
	
	@Inject
	private PURService purService;
	
	@Inject
	private ResponseUtils responseUtils;
	
	@Inject
	private ResponseService responseService;
	
	@Inject
	private CommandsUtils commandsUtils;
	
	/**
	 * Instantiates a new PUR endpoint.
	 *
	 * @param endpointDefinition the endpoint definition
	 */
	@Inject
	protected PUREndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}
	
	/**
	 * PUR endpoint method for create a new public user in the system. This method send a mail to the user with a link to activate it.
	 * 
	 * Method: POST
	 * Method uri: /.rest/users/v1
	 *
	 * @param userDTO the body JSON parsed to UserDTO
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	//@ApiOperation(value = "PUR endpoint operation")
	public Response newUser(@Valid UserDTO userDTO) {
		return purService.createUser(userDTO);
	}
	
	/**
	 * PUR endpoint method to activate a user.
	 * 
	 * Method: PUT
	 * Method uri: /.rest/users/v1/activateUser/{userId}/{regstamp}
	 *
	 * @param userId the user ID that has been sent in the mail
	 * @param regstamp the registry stamp that has been sent in the mail
	 * @return the response
	 * @throws FormProcessorFailedException the form processor failed exception
	 */
	@PUT
	@Path("/activate/{userId}/{regstamp}")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	//@ApiOperation(value = "PUR endpoint operation")
	public Response activateUser(@PathParam("userId") String userId, @PathParam("regstamp") String regstamp) throws FormProcessorFailedException {
		return purService.activateUser(userId, regstamp);
	}
	
	/**
	 * PUR endpoint method to activate a user.
	 * 
	 * Method: PUT
	 * Method uri: /.rest/users/v1/verify/{username}
	 *
	 * @param username the username (email) in the system
	 * @return the response
	 * @throws RepositoryException the repository exception
	 */
	@PUT
	@Path("/verify/{username}")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	//@ApiOperation(value = "PUR endpoint operation")
	public Response verifyUser(@PathParam("userId") String username) throws RepositoryException {
		if(purService.verifyUser(username)) {
			String userID = Components.getComponent(SecuritySupport.class).getUserManager().getUser(username).getIdentifier();
			String userPath = MgnlContext.getJCRSession(RepositoryConstants.USERS).getNodeByIdentifier(userID).getPath();
			return commandsUtils.publishNodeWithWorkflow(userPath, RepositoryConstants.USERS);
		} else {
			return responseUtils.generateResponseBuilder(responseService.generateKoResponse()).build();
		}
	}
	
	/**
	 * PUR endpoint method to activate a user.
	 * 
	 * Method: PUT
	 * Method uri: /.rest/users/v1/verify/{username}
	 *
	 * @param username the username (email) in the system
	 * @return the response
	 * @throws RepositoryException the repository exception
	 */
	@PUT
	@Path("/verify/special/{username}")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	//@ApiOperation(value = "PUR endpoint operation")
	public Response verifySpecialUser(@PathParam("userId") String username) throws RepositoryException {
		if(purService.verifyUser(username)) {
			String userID = Components.getComponent(SecuritySupport.class).getUserManager().getUser(username).getIdentifier();
			String userPath = MgnlContext.getJCRSession(RepositoryConstants.USERS).getNodeByIdentifier(userID).getPath();
			return commandsUtils.publishNodeWithoutWorkflow(userPath, RepositoryConstants.USERS);
		} else {
			return responseUtils.generateResponseBuilder(responseService.generateKoResponse()).build();
		}
	}
	
	/**
	 * Update user.
	 * 
	 * Method: PUT
	 * Method uri: /.rest/users/v1
	 *
	 * @param auth the authentication string that have been passed in the header
	 * @param userDTO the body JSON parsed to UserDTO
	 * @return the response
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	//@ApiOperation(value = "PUR endpoint operation")
	public Response updateUser(@HeaderParam("authorization") String auth, @Valid UserDTO userDTO) {
		RestLoginUtils restLoginUtils = new RestLoginUtils(auth);
		if(restLoginUtils.doLogin()) {
			return purService.updateUser(userDTO, restLoginUtils.getCurrentUser().getName());
		}
		return responseUtils.generateResponseBuilder(responseService.generateKoResponse()).build();
	}
	
	@PUT
	@Path("/special/{username}")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	//@ApiOperation(value = "PUR endpoint operation")
	public Response updateUserSpecial(@HeaderParam("authorization") String auth, @Valid UserDTO userDTO, @PathParam("username") String username) {
		RestLoginUtils restLoginUtils = new RestLoginUtils(auth);
		if(restLoginUtils.doLogin()) {
			return commandsUtils.executeCommand("workflow", "publishUserSpecial", null, null, false, username, userDTO, null);
		}
		return responseUtils.generateResponseBuilder(responseService.generateKoResponse()).build();
	}
	
	/**
	 * PUR endpoint method to generate a token to permit change user's password.
	 * 
	 * Method: PUT
	 * Method uri: /.rest/users/v1/generateToken
	 *
	 * @param passwordDTO the body JSON parsed to ForgottenPasswordDTO
	 * @return the response
	 */
	@PUT
	@Path("/generateEmailToken")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	//@ApiOperation(value = "PUR endpoint operation")
	public Response generateEmailToken(@Valid ForgottenPasswordDTO passwordDTO) {
		return purService.generateToken(passwordDTO);
	}
	
	/**
	 * PUR endpoint method for change the user password.
	 * 
	 * Method: PUT
	 * Method uri: /.rest/users/v1/changePassword
	 *
	 * @param passwordDTO the body JSON parsed to ChangePasswordDTO
	 * @return the response
	 */
	@PUT
	@Path("/changePassword")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	//@ApiOperation(value = "PUR endpoint operation")
	public Response changePassword(@Valid ChangePasswordDTO passwordDTO) {
		return purService.changePassword(passwordDTO);
	}
	
	
	
	@DELETE
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	//@ApiOperation(value = "PUR endpoint operation")
	public Response deleteUser(@HeaderParam("authorization") String auth, @PathParam("username") String username) {
		RestLoginUtils restLogin = new RestLoginUtils(auth);
		if(restLogin.doLogin()) {
			return purService.deleteUser(Components.getComponent(SecuritySupport.class).getUserManager().getUser(username));
		}
		return responseUtils.generateResponseBuilder(responseService.generateKoResponse()).build();
	}
	
	@PUT
	@Path("/assignrole/{username}/{rolename}")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	//@ApiOperation(value = "PUR endpoint operation")
	public Response assignRole(@HeaderParam("authorization") String auth, @PathParam("username") String username, @PathParam("rolename") String rolename) {
		RestLoginUtils restLogin = new RestLoginUtils(auth);
		if(restLogin.doLogin()) {
			if(purService.assignRole(username, rolename))
				return responseUtils.generateResponseBuilder(responseService.generateOkResponseWithMessage("Assign role" + rolename + " to user " + username + " successful!")).build();
		}
		return responseUtils.generateResponseBuilder(responseService.generateKoResponse()).build();
	}
	
	@PUT
	@Path("/unassignrole/{username}/{rolename}")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	//@ApiOperation(value = "PUR endpoint operation")
	public Response unassignRole(@HeaderParam("authorization") String auth, @PathParam("username") String username, @PathParam("rolename") String rolename) {
		RestLoginUtils restLogin = new RestLoginUtils(auth);
		if(restLogin.doLogin()) {
			if(purService.unassignRole(username, rolename))
				return responseUtils.generateResponseBuilder(responseService.generateOkResponseWithMessage("Unassign role" + rolename + " to user " + username + " successful!")).build();
		}
		return responseUtils.generateResponseBuilder(responseService.generateKoResponse()).build();
	}
	
}
