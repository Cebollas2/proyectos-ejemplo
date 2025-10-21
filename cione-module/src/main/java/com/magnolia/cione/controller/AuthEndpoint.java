package com.magnolia.cione.controller;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.EmployeeDTO;
import com.magnolia.cione.dto.TermsDTO;
import com.magnolia.cione.dto.UserAndNewRoleDTO;
import com.magnolia.cione.service.AuthService;
import com.magnolia.cione.utils.CioneUtils;
import com.magnolia.cione.utils.CypherUtils;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;

/**
 * The Class PUREndpoint.
 *
 * @param <D> the generic type
 */
//@Api(value = "/auth/v1")
@Path("/auth/v1")
public class AuthEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {
	
	private static final Logger log = LoggerFactory.getLogger(AuthEndpoint.class);
	
	private static final String SECRET_KEY = "clave-secreta";

	@Inject
	private AuthService authService;

	@Inject
	protected AuthEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	//@POST
	@GET
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response registerUser(@QueryParam("id") String id){
		try {
			authService.registerUser(id, CioneUtils.getidClienteFromNumSocio(id));
			return Response.ok(true).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
		
	}
	
	@GET
	@Path("/recovery-password")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response recoveryPassword(@QueryParam("id") String id){
		try {
			authService.recoveryPassword(id);
			return Response.ok(true).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	@GET
	@Path("/updateAuditory")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response updateAuditory(@QueryParam("id") String id, @QueryParam("newsId") String newsId){
		try {
//			
			//authService.recoveryPassword(id);
			authService.updateAuditory(id, newsId);
			
			return Response.ok(true).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	@POST
	@Path("/employee/register")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	//@ApiOperation(value = "Register employee")
	public Response registerOrUpdateEmployee(@Valid EmployeeDTO employeeDTO){
		// Comprobamos que el token es correcto y asi evitar que se altere el id de usuario desde cliente
		if(employeeDTO.getToken().equals(CypherUtils.encode(employeeDTO.getId().substring(0, employeeDTO.getId().length() - 2)))) {
			return authService.registerOrUpdateEmployee(employeeDTO);
		}
		return Response.status(503).build();
	}
	
	@POST
	@Path("/employee/remove")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	//@ApiOperation(value = "Remove employee")
	public Response removeEmployee(@Valid EmployeeDTO employeeDTO){
		
		// Comprobamos que el token es correcto y asi evitar que se altere el id de usuario desde cliente
		if(employeeDTO.getToken().equals(CypherUtils.encode(employeeDTO.getId().substring(0,7)))) {
			authService.removeEmployee(employeeDTO);
			return Response.ok(true).build();
		}
		
		return Response.status(503).build();
	}
	
	@POST
	@Path("/employee/addrol")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	//@ApiOperation(value = "Add rol to employee")
	public Response addUserRol(@Valid UserAndNewRoleDTO userAndNewRoleDTO){
		
		try {
			authService.addUserRol(userAndNewRoleDTO.getNumSocio(), userAndNewRoleDTO.getNewRol());
			return Response.ok(true).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@POST
	@Path("/employee/removerol")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	//@ApiOperation(value = "Add rol to employee")
	public Response removeUserRol(@Valid UserAndNewRoleDTO userAndNewRoleDTO){
		
		try {
			authService.removeUserRol(userAndNewRoleDTO.getNumSocio(), userAndNewRoleDTO.getNewRol());
			return Response.ok(true).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/updateRol")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	//@ApiOperation(value = "Update Rol")
	public Response updateRol(@QueryParam("numSocio") String numSocio, @QueryParam("oldRol") String oldRol, @QueryParam("newRol") String newRol){
		Response response = null;
		try {
			response = authService.updateUserRol(numSocio, oldRol, newRol);
			 
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		
		return response;
		
	}
	
	@POST
	@Path("/acceptTerms")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response acceptTerms(@Valid TermsDTO termsDTO){
		
		try {
			if (termsDTO.getCurrentUser() == null) {
				termsDTO.setCurrentUser(CioneUtils.getIdCurrentClient());
			}
			authService.submitTerms(termsDTO);
			return Response.ok(true).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/ssoServicios")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	//@ApiOperation(value = "Update Rol")
	public Response getTokenServicios(){
		try {
			JSONObject jsonRes = new JSONObject();
			String urlSSO = authService.getSSOServices();
			jsonRes.put("url", urlSSO);
			return generateResponseBuilder(jsonRes.toString(), Response.Status.OK).build();
			//return Response.status(200).entity(url).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/*private String generateToken(String username, String password) {
	       Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

	        return JWT.create()
	                .withClaim("username", username)
	                .withClaim("password", password)
	                .sign(algorithm);
	}
	
	
	@GET
	@Path("/autologin")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	//@ApiOperation(value = "Update Rol")
	public Response autologin(@QueryParam("token") String token){
		try {
			if (token == null || token.isEmpty()) {
	            return Response.status(Response.Status.BAD_REQUEST)
	                           .entity("Missing token")
	                           .build();
	        }

	        try {
	        	
//	            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET_KEY))
//	                                .build()
//	                                .verify(token);
//
//	            String username = jwt.getClaim("username").asString();
//	            String password = jwt.getClaim("password").asString();
	            
	            String username="000260100";
	            String password="a";

	            if (username == null || password == null) {
	                return Response.status(Response.Status.BAD_REQUEST)
	                               .entity("Invalid token data")
	                               .build();
	            }

	            // Generar formulario HTML que se autolanza
	            String html = "<html><body onload='document.forms[0].submit()'>"
	                    + "<form method='post' id='loginForm'>"
	            		+ "<input type='hidden' name='mgnlRealm' value='public'/>"
	                    + "<input type='hidden' id='username' name='mgnlUserIdCione' value='" + username + "'/>"
	                    + "<input type='hidden' id='mgnlUserPSWD' name='mgnlUserPSWD' value='" + password + "'/>"
	                    + "<input type='hidden' name='mgnlReturnTo' value='/cione/private/home'/>"
	                    + "</form></body></html>";

	            return Response.ok(html, MediaType.TEXT_HTML).build();

	        } catch (JWTVerificationException e) {
	            return Response.status(Response.Status.UNAUTHORIZED)
	                           .entity("Token verification failed")
	                           .build();
	        }
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}*/
	
	private ResponseBuilder generateResponseBuilder(String res, Status status) {

		return Response.status(status)
				.type(MediaType.APPLICATION_JSON + "; charset=utf-8")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8"))
				.header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
				.header(HttpHeaders.EXPIRES, "0")
				.entity(res);

	}
	
}
