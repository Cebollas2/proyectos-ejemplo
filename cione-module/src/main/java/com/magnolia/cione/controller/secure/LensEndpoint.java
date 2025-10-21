package com.magnolia.cione.controller.secure;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
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

import com.magnolia.cione.service.LensService;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;

//@Api(value = "/private/lens")
@Path("/private/lens")
public class LensEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {
	
	private static final Logger log = LoggerFactory.getLogger(LensEndpoint.class);
	
	@Inject
	private LensService lensservice;
	
	@Inject
	protected LensEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	@GET
	@Path("/spheres")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getSphereByCylinder(@QueryParam("cylinder") int cylinder){
		
		JSONObject jsonRes = new JSONObject();
		
		try {
			List<String> spheres = lensservice.getSpheresByCylinder(cylinder);
			jsonRes.put("spheres", spheres);
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.OK).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}

	@GET
	@Path("/lens")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getSphereByCylinder(@QueryParam("cylinder") int cylinder, @QueryParam("sphere") int sphere){
	
		try {
			LinkedHashMap<String, String> spheres = lensservice.getLensByCylinderAndSphere(cylinder, sphere);
			
			//como el map ordena de manera random formamos el json a mano
//			JSONObject jsonRes = new JSONObject(spheres);
//			jsonRes.put("lens", spheres);
//			String res = jsonRes.toString();
			
			
			String res = "{\"lens\":{";
			Set<String> keys = spheres.keySet();
			
			for (String key : keys) {
				res = res.concat("\"" + key + "\":\"" + spheres.get(key)+"\",");
	        }
			if (!spheres.isEmpty())
				res = res.substring(0, res.length()-1); //eliminamos la ultima posicion
			res = res.concat("}}");
			
			
			return generateResponseBuilder(res, Response.Status.OK).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}

	@GET
	@Path("/diameters")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getDiametersByLens(@QueryParam("cylinder") int cylinder, @QueryParam("sphere") int sphere, @QueryParam("lens") int lens){
		
		JSONObject jsonRes = new JSONObject();
		
		try {
			List<String> diameters = lensservice.getDiametersByLens(cylinder,sphere,lens);
			jsonRes.put("diameters", diameters);
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
	public Response getSku(@QueryParam("cylinder") int cylinder, @QueryParam("sphere") int sphere, @QueryParam("lens") int lens, @QueryParam("diameter") int diameter){
		
		JSONObject jsonRes = new JSONObject();
		
		try {
			String sku = lensservice.getSkuLens(cylinder, sphere, lens, diameter);
			jsonRes.put("sku", sku);
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.OK).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}

	@GET
	@Path("/price")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getPrice(@QueryParam("sku") String sku){
		
		JSONObject jsonRes = new JSONObject();
		
		try {
			Map<String, String> price = lensservice.getPriceBySku(sku);
			jsonRes.put("pvo", price.get("pvo"));
			jsonRes.put("pvp", price.get("pvp"));
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.OK).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}

	@GET
	@Path("/linedatainfo")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getLineDataInfo(@QueryParam("cylinder") int cylinder, @QueryParam("sphere") int sphere, @QueryParam("lens") int lens, @QueryParam("diameter") int diameter){
		
		JSONObject jsonRes = new JSONObject();
		
		try {
			
			Map<String, String> info = lensservice.getLineDataInfo(cylinder,sphere,lens,diameter);
			jsonRes.put("nivel1", info.get("nivel1"));
			jsonRes.put("nivel2", info.get("nivel2"));
			jsonRes.put("descripcion", info.get("descripcion"));
			jsonRes.put("name", info.get("name"));
			jsonRes.put("sku", info.get("sku"));
			jsonRes.put("pvo", info.get("pvo"));
			jsonRes.put("pvp", info.get("pvp"));
			jsonRes.put("lmattype", info.get("lmattype"));
			
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
