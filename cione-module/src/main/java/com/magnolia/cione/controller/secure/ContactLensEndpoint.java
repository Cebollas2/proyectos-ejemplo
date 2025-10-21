package com.magnolia.cione.controller.secure;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
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

import com.magnolia.cione.service.ContactLensService;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;

//@Api(value = "/private/contactlens")
@Path("/private/contactlens")
public class ContactLensEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {
	
	private static final Logger log = LoggerFactory.getLogger(ContactLensEndpoint.class);
	
	@Inject
	private ContactLensService contactlensservice;
	
	@Inject
	protected ContactLensEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	@GET
	@Path("/spheres")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getSpheresByCentraCodeAndDesign(@QueryParam("codigocentral") String codigocentral, @QueryParam("diseno") String diseno){
		
		JSONObject jsonRes = new JSONObject();
		
		try {
			List<String> spheres = contactlensservice.getSpheresByCentralCodeAndDesign(codigocentral,diseno);
			jsonRes.put("spheres", spheres);
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.OK).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}

	@GET
	@Path("/cylinders")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getCylinders(@QueryParam("codigocentral") String codigocentral,
								 @DefaultValue("")
								 @QueryParam("diseno") String diseno,
								 @DefaultValue("")
								 @QueryParam("esfera") String esfera){
		
		JSONObject jsonRes = new JSONObject();
		
		try {
			List<String> cylinders = contactlensservice.getCylinders(codigocentral,diseno,esfera);
			jsonRes.put("cylinders", cylinders);
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.OK).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}

	@GET
	@Path("/axis")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getAxis(@QueryParam("codigocentral") String codigocentral,
						    @DefaultValue("")
						    @QueryParam("diseno") String diseno,
						    @DefaultValue("")
						    @QueryParam("esfera") String esfera,
						    @DefaultValue("")
						    @QueryParam("cilindro") String cilindro){
		
		JSONObject jsonRes = new JSONObject();
		
		try {
			List<String> axis = contactlensservice.getAxis(codigocentral,diseno,esfera,cilindro);
			jsonRes.put("axis", axis);
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.OK).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}

	@GET
	@Path("/diameters")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getDiameters(@QueryParam("codigocentral") String codigocentral,
						    @DefaultValue("")
						    @QueryParam("diseno") String diseno,
						    @DefaultValue("")
						    @QueryParam("esfera") String esfera,
						    @DefaultValue("")
						    @QueryParam("cilindro") String cilindro,
						    @DefaultValue("")
						    @QueryParam("eje") String eje){
		
		JSONObject jsonRes = new JSONObject();
		
		try {
			List<String> diameters = contactlensservice.getDiameters(codigocentral,diseno,esfera,cilindro,eje);
			jsonRes.put("diameters", diameters);
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.OK).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}

	@GET
	@Path("/radios")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getRadios(@QueryParam("codigocentral") String codigocentral,
						    @DefaultValue("")
						    @QueryParam("diseno") String diseno,
						    @DefaultValue("")
						    @QueryParam("esfera") String esfera,
						    @DefaultValue("")
						    @QueryParam("cilindro") String cilindro,
						    @DefaultValue("")
						    @QueryParam("eje") String eje,
						    @DefaultValue("")
						    @QueryParam("diametro") String diametro){
		
		JSONObject jsonRes = new JSONObject();
		
		try {
			List<String> radios = contactlensservice.getRadios(codigocentral,diseno,esfera,cilindro,eje,diametro);
			jsonRes.put("radios", radios);
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.OK).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}

	@GET
	@Path("/additions")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getAdditions(@QueryParam("codigocentral") String codigocentral,
						    @DefaultValue("")
						    @QueryParam("diseno") String diseno,
						    @DefaultValue("")
						    @QueryParam("esfera") String esfera,
						    @DefaultValue("")
						    @QueryParam("cilindro") String cilindro,
						    @DefaultValue("")
						    @QueryParam("eje") String eje,
						    @DefaultValue("")
						    @QueryParam("diametro") String diametro,
						    @DefaultValue("")
						    @QueryParam("radio") String radio){
		
		JSONObject jsonRes = new JSONObject();
		
		try {
			List<String> additions = contactlensservice.getAdditions(codigocentral,diseno,esfera,cilindro,eje,diametro,radio);
			jsonRes.put("additions", additions);
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.OK).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}

	@GET
	@Path("/colors")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getColors(@QueryParam("codigocentral") String codigocentral,
						      @DefaultValue("")
							  @QueryParam("diseno") String diseno,
						      @DefaultValue("")
						      @QueryParam("esfera") String esfera,
						      @DefaultValue("")
						      @QueryParam("cilindro") String cilindro,
						      @DefaultValue("")
						      @QueryParam("eje") String eje,
						      @DefaultValue("")
						      @QueryParam("diametro") String diametro,
						      @DefaultValue("")
						      @QueryParam("radio") String radio,
						      @DefaultValue("")
						      @QueryParam("adicion") String adicion){
		
		JSONObject jsonRes = new JSONObject();
		
		try {
			List<String> colors = contactlensservice.getColors(codigocentral,diseno,esfera,cilindro,eje,diametro,radio,adicion);
			jsonRes.put("colors", colors);
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.OK).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}

	@GET
	@Path("/sku")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getSku(@QueryParam("codigocentral") String codigocentral,
						      @DefaultValue("")
							  @QueryParam("diseno") String diseno,
						      @DefaultValue("")
						      @QueryParam("esfera") String esfera,
						      @DefaultValue("")
						      @QueryParam("cilindro") String cilindro,
						      @DefaultValue("")
						      @QueryParam("eje") String eje,
						      @DefaultValue("")
						      @QueryParam("diametro") String diametro,
						      @DefaultValue("")
						      @QueryParam("radio") String radio,
						      @DefaultValue("")
						      @QueryParam("adicion") String adicion,
						      @DefaultValue("")
						      @QueryParam("color") String color){
		
		JSONObject jsonRes = new JSONObject();
		
		try {
			String sku = contactlensservice.getSku(codigocentral,diseno,esfera,cilindro,eje,diametro,radio,adicion,color);
			jsonRes.put("sku", sku);
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.OK).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}

	private ResponseBuilder generateResponseBuilder(String res, Status status) {

		return Response.status(status)
				.type(MediaType.APPLICATION_JSON + "; charset=utf-8")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8"))
				.header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
				.header(HttpHeaders.EXPIRES, "0")
				.entity(res);

	}
}
