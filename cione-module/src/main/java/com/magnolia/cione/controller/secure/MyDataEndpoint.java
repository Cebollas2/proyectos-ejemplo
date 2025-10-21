package com.magnolia.cione.controller.secure;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.ChangePasswordDTO;
import com.magnolia.cione.exceptions.CioneException;
import com.magnolia.cione.service.AuthService;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;


//@Api(value = "/private/my-data/v1")
@Path("/private/my-data/v1")
public class MyDataEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {
	
	private static final Logger log = LoggerFactory.getLogger(MyDataEndpoint.class);

	@Inject
	private AuthService authService;

	@Inject
	protected MyDataEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	@POST
	@Path("/login_foro")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response loginForo(@QueryParam("url") String url, @QueryParam("user") String userId) throws CioneException {
		try {
			return authService.loginForo(url, userId);
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}

	
	@POST
	@Path("/change-password")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response registerUser(@Valid ChangePasswordDTO changePasswordDTO) throws CioneException {
		try {
			authService.changePassword(changePasswordDTO);
			return Response.ok(true).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
}
