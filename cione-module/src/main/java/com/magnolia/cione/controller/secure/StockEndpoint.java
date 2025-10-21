package com.magnolia.cione.controller.secure;

import java.util.Map;

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

import com.commercetools.api.models.product.ProductProjection;
import com.commercetools.api.models.product.ProductVariant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.magnolia.cione.dto.StockMgnlDTO;
import com.magnolia.cione.service.CommercetoolsService;
import com.magnolia.cione.service.CommercetoolsServiceAux;

import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;

//@Api(value = "/private/stock")
@Path("/private/stock")
public class StockEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {
	
	private static final Logger log = LoggerFactory.getLogger(StockEndpoint.class);
	
	@Inject
	private CommercetoolsService commercetoolsservice;
	
	@Inject
	private CommercetoolsServiceAux commercetoolsserviceaux;
	
	private final SimpleTranslator i18n;
	
	@Inject
	protected StockEndpoint(D endpointDefinition, SimpleTranslator i18n) {
		super(endpointDefinition);
		this.i18n = i18n;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response stock(@QueryParam("sku") String sku){
		
		try {
			
			StockMgnlDTO stockMgnlDTO = commercetoolsservice.getStockDTO(sku);
			return Response.ok(stockMgnlDTO).build();
			
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}

	@GET
	@Path("/withcart")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response stockWithCart(@QueryParam("sku") String sku, @QueryParam("aliasEkon") String aliasEkon){
		
		JSONObject jsonRes = new JSONObject();
		
		try {
			int stock = commercetoolsservice.getStockWithCart(sku, aliasEkon);
			jsonRes.put("stock", stock);
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.OK).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}

	@GET
	@Path("/unitscart")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response unitsCart(@QueryParam("sku") String sku, @QueryParam("aliasEkon") String aliasEkon){
		
		JSONObject jsonRes = new JSONObject();
		
		try {
			int units = commercetoolsservice.getUnitsCart(sku);
			jsonRes.put("units", units);
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.OK).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	@GET
	@Path("/replacements")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getReplacements(@QueryParam("sku") String sku, @QueryParam("cant") int cant){
		try {	
			ProductProjection psustitutive = 
					commercetoolsservice.getProductBysku(sku, commercetoolsservice.getGrupoPrecioCommerce());
			ProductVariant variantSus = commercetoolsserviceaux.findVariantBySku(psustitutive, sku);
					//psustitutive.findVariantBySku(sku).get();
			Map<String, String> map = commercetoolsservice.getSustituveReplacement(variantSus, cant);
			JSONObject json = new JSONObject();
			json.put("replacements", map);
			return generateResponseBuilder(json.toString(), Response.Status.OK).build();
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
