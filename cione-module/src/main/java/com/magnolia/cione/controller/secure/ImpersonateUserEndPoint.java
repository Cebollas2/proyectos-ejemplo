package com.magnolia.cione.controller.secure;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.DefaultResponseDTO;
import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.exceptions.CioneException;
import com.magnolia.cione.service.AuthService;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;


//@Api(value = "/private/impersonate/v1")
@Path("/private/impersonate/v1")
public class ImpersonateUserEndPoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(ImpersonateUserEndPoint.class);
	
	@Inject
	private AuthService authService;

	@Inject
	protected ImpersonateUserEndPoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getUsers(@QueryParam("idClient") String idClient, @QueryParam("fullName") String fullName) {
		try {
			 List<UserERPCioneDTO> users = authService.getUsersFromPur(idClient,fullName);
			return Response.ok(users).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	
	@GET
	@Path("/impersonate")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response impersonate(@QueryParam("usernameToImpersonate") String usernameToImpersonate, @QueryParam("usernameImpersonator") String usernameImpersonator) throws CioneException{
		try {
			
			 String result = authService.impersonateUser(usernameToImpersonate, usernameImpersonator);
			 DefaultResponseDTO responseDTO = new DefaultResponseDTO();
			 responseDTO.setTxt(result);
			return Response.ok(responseDTO).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	@GET
	@Path("/delete-impersonate")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response impersonate(@QueryParam("usernameImpersonator") String usernameImpersonator) throws CioneException{
		try {
			 String result = authService.removeImpersonateUser(usernameImpersonator);
			 DefaultResponseDTO responseDTO = new DefaultResponseDTO();
			 responseDTO.setTxt(result);
			return Response.ok(responseDTO).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	
	@GET
	@Path("/activate-user")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response activateUser(@QueryParam("id") String id, @QueryParam("activate") Boolean activate){			
		try {			
			authService.setActivateUserInPUR(id, activate);
			return Response.ok(true).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}

}
