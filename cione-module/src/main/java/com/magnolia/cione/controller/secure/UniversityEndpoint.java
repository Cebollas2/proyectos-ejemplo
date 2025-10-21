package com.magnolia.cione.controller.secure;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.service.AuthService;
import com.magnolia.cione.service.ConfigService;
import com.magnolia.cione.service.MiddlewareService;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.cms.security.User;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;

//@Api(value = "/private/login-university/v1")
@Path("/private/login-university/v1")
public class UniversityEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(UniversityEndpoint.class);

	@Inject
	protected UniversityEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}
	
	@Inject
	private MiddlewareService middlewareService;
	
	@Inject
	private ConfigService configService;
	
	@Inject
	private AuthService authService;
	
	
	@GET
	@Path("/get_token")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getToken(@QueryParam("url") String domain){
		int code;
		String token;
		try {
			String authKey = configService.getConfig().getUniversityAuthKey();
			
			String idUsuario = CioneUtils.getIdCurrentClient();
			String peticionUrl = "/auth/autologin/get_token.php?username=" + idUsuario + "&AUTH_KEY=" + authKey;
			
			ResteasyClient client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).disableTrustManager().build();
			WebTarget target = client.target(domain + peticionUrl);
			Response response = target.request(MediaType.APPLICATION_JSON).get();
			code = response.getStatus();
			token = response.readEntity(String.class);
			response.close();
			client.close();
			
			return Response.status(code).entity(token).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		
		
		
	}
	
	@POST
	@Path("/get_user")
	@Produces(MediaType.APPLICATION_XML + "; charset=utf-8")
	public Response getUser(@QueryParam("url") String domain){
		
		String wstoken = configService.getConfig().getUniversityWstoken();
		
		String idUsuario = CioneUtils.getIdCurrentClient(); //wstoken = a638f53c65cbffb7f567c899daccf11b
		String peticionUrl = "/webservice/rest/server.php?wstoken=" + wstoken + "&wsfunction=auth_autologin_get_user";
		
		ResteasyClient client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).disableTrustManager().build();
		WebTarget target = client.target(domain + peticionUrl);
		MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
		params.add("username", idUsuario);
		Response response = target.request().post(Entity.form(params));
		int code = response.getStatus();
		String respuesta = response.readEntity(String.class);
		response.close();
		client.close();
		
		return Response.status(code).entity(respuesta).build();
		
	}
	
	@POST
	@Path("/create_user")
	@Produces(MediaType.APPLICATION_XML + "; charset=utf-8")
	public Response createUser(@QueryParam("url") String domain){
		
		String idUsuario = CioneUtils.getIdCurrentClient();
		if (idUsuario.endsWith("00")) {
			return createSocio(idUsuario, domain);
		} else {
			return createEmpleado(idUsuario, domain);
		}
		
		
	}
	
	public Response createSocio(String idUsuario, String domain) {
		String wstoken = configService.getConfig().getUniversityWstoken();
		String peticionUrl = "/webservice/rest/server.php?wstoken=" + wstoken + "&wsfunction=auth_autologin_create_user";
		
		UserERPCioneDTO userERP = getUserFromERP();
		String respuesta = "";
		int code = 200;
		if (userERP != null) {
			ResteasyClient client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).disableTrustManager().build();
			WebTarget target = client.target(domain + peticionUrl);
			MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
			params.add("username", idUsuario);
			params.add("first_name", userERP.getNombreComercial());
			params.add("last_name", "");
			params.add("email", userERP.getEmail());
			Response response = target.request().post(Entity.form(params));
			code = response.getStatus();
			respuesta = response.readEntity(String.class);
			response.close();
			client.close();
		}
		return Response.status(code).entity(respuesta).build();
	}
	
	public Response createEmpleado(String idUsuario, String domain) {
		String wstoken = configService.getConfig().getUniversityWstoken();
		String peticionUrl = "/webservice/rest/server.php?wstoken=" + wstoken + "&wsfunction=auth_autologin_create_user";
		
		User user = authService.getUserFromPUR(idUsuario);
		String respuesta = "";
		int code = 200;
		if (user != null) {
			ResteasyClient client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).disableTrustManager().build();
			WebTarget target = client.target(domain + peticionUrl);
			MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
			params.add("username", idUsuario);
			params.add("first_name", user.getProperty("title"));
			params.add("last_name", "");
			params.add("email", idUsuario+"@cione.es");
			Response response = target.request().post(Entity.form(params));
			code = response.getStatus();
			respuesta = response.readEntity(String.class);
			response.close();
			client.close();
		}
		return Response.status(code).entity(respuesta).build();
	}
	
	private UserERPCioneDTO getUserFromERP() {
		String idUser = CioneUtils.getIdCurrentClient();
		if (idUser.endsWith("00"))
			return middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
		else {
			UserERPCioneDTO empleado = middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
			if (empleado != null)
				empleado.setNumSocio(idUser);
			else {
				log.debug("usuario superuser o del sistema" + idUser);
				empleado = new UserERPCioneDTO();
			}
			return empleado;
		}
		
	}

}
