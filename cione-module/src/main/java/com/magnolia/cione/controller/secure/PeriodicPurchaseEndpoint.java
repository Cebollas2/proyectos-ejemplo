package com.magnolia.cione.controller.secure;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
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

import com.commercetools.api.models.cart.Cart;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.PeriodicPurchaseDTO;
import com.magnolia.cione.service.PeriodicPurchaseService;

import info.magnolia.ecommerce.EcommerceConnectionProvider;
import info.magnolia.ecommerce.common.Connection;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;
import io.vrap.rmf.base.client.utils.json.JsonUtils;

//@Api(value = "/private/periodicpurchase")
@Path("/private/periodicpurchase")
public class PeriodicPurchaseEndpoint  <D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(PeriodicPurchaseEndpoint.class);
	
	@Inject
	private PeriodicPurchaseService periodicPurchaseService;

	@Inject
	private EcommerceConnectionProvider ecommerceConnectionProvider;
	
	private final ObjectMapper objectMapper;
	
	@Inject
	protected PeriodicPurchaseEndpoint(D endpointDefinition) {
		super(endpointDefinition);
		this.objectMapper = JsonUtils.createObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
	}
	
	@POST
	@Path("/newaddCartToPeriodicPurchase")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response newAddCartToShoppingList(@Valid PeriodicPurchaseDTO periodicPurchaseDTO) {
		
		if (periodicPurchaseDTO.getId() != null && !periodicPurchaseDTO.getId().isEmpty()) {
			
			if (periodicPurchaseService.isUserCart(periodicPurchaseDTO.getId())) {
			
				try {
					
					Cart updatedCart = periodicPurchaseService.addCartToPeriodicPurchase(periodicPurchaseDTO);
					
					if (updatedCart != null) {
						return Response.ok(getBackwardCompatibilityCartAsString(updatedCart)).build();
					} else {
						return Response.serverError().build();
					}
					
				} catch(Exception e) {
					log.error(e.getMessage());
					return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
				}
			
			}else {
				return Response.status(Response.Status.FORBIDDEN).build();
			}
			
		}else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}	
    }
	
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response addCartToPeriodicPurchase(@QueryParam("id") String id) {
		
		if (periodicPurchaseService.isUserCart(id)) {
		
			try {
				Cart carr = periodicPurchaseService.addCartToPeriodicPurchase(id);
				return Response.ok(getBackwardCompatibilityCartAsString(carr)).build();
			} catch(Exception e) {
				log.error(e.getMessage());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
			}
			
		}else {
			return Response.status(Response.Status.FORBIDDEN).build();
		}	
    }
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getAll(@QueryParam("date") String date, 
						   @QueryParam("customermail") String customermail, 
						   @QueryParam("numbudget") String numbudget,
						   @QueryParam("numcliente") String numcliente,
						   @QueryParam("page") int page) {
		try {
			
			Response shoppingList = periodicPurchaseService.getPeriodicPurchases(date,customermail,numbudget,numcliente,page);
//			Cart cart = null;
//			String startdate = cart.getCustom().getFields().values().get("dateIniPurcharse")//.custom.fields.dateIniPurcharse;
			
			return shoppingList;
			
		} catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
    }
	
	@GET
	@Path("/allmodal")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getAllModal() {
		try {
			return periodicPurchaseService.getAllPeriodicPurchases();
			
		} catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	@GET
	@Path("/retrieve")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getCartByPeriodicPurchaseAndRetrieve(@QueryParam("id") String id) {
		
		if (periodicPurchaseService.isUserCart(id)) {
		
			try {
				Cart carr = periodicPurchaseService.getCartByPeriodicPurchaseAndRetrieve(id);
//				Cart carr = periodicPurchaseService.getPeriodicPurchaseById(id); Check de envio mail
//				periodicPurchaseService.checkMail(carr, "msanchezp@knowmadmood.com");
				
				return Response.ok(getBackwardCompatibilityCartAsString(carr)).build();
			} catch(Exception e) {
				log.error(e.getMessage());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
			}
			
		}else {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
    }
	
	@GET
	@Path("/status")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getStatus() {
		
		JSONObject jsonRes = new JSONObject();
		
		try {
			
			String status = periodicPurchaseService.getStatus(
					getConnection(MyshopConstants.CT, MyshopConstants.CON).orElse(null));
			jsonRes.put("status", status);
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.OK).build();
		} catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
    }
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response deleteById(@QueryParam("id") String id) {
		
		if (id != null && !id.isEmpty()) {
			
			if (periodicPurchaseService.isUserCart(id)) {
				
				Cart deletecart = periodicPurchaseService.removePeriodicPurchase(
						getConnection(MyshopConstants.CT, MyshopConstants.CON).orElse(null),id);
				
				if (deletecart != null) {
					try {
						return Response.ok(getBackwardCompatibilityCartAsString(deletecart)).build();
					} catch (JsonProcessingException e) {
						log.error(e.getMessage(), e);
						return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
					}
				}else {
					return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
				}
				
			}else {
				return Response.status(Response.Status.FORBIDDEN).build();
			}
			
		}else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
    }
	
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response addCartToShoppingList(@Valid PeriodicPurchaseDTO periodicPurchaseDTO) {
		
		
		if (periodicPurchaseDTO.getId() != null && !periodicPurchaseDTO.getId().isEmpty()) {
			
			if (periodicPurchaseService.isUserCart(periodicPurchaseDTO.getId())) {
			
				try {
					
					Cart updatedCart = periodicPurchaseService.updatePeriodicPurchase(periodicPurchaseDTO);
					
					if (updatedCart != null) {
						return Response.ok(getBackwardCompatibilityCartAsString(updatedCart)).build();
					} else {
						return Response.serverError().build();
					}
					
				} catch(Exception e) {
					log.error(e.getMessage());
					return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
				}
			
			}else {
				return Response.status(Response.Status.FORBIDDEN).build();
			}
			
		}else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
    }

    private Optional<Connection> getConnection(String definitionName, String connectionName) {
        return ecommerceConnectionProvider.getConnection(definitionName, connectionName);
    }
	
	private ResponseBuilder generateResponseBuilder(String res, Status status) {

		return Response.status(status)
				.type(MediaType.APPLICATION_JSON + "; charset=utf-8")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8"))
				.header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
				.header(HttpHeaders.EXPIRES, "0")
				.entity(res);

	}
	
	private String getBackwardCompatibilityCartAsString(Cart cart) throws JsonProcessingException {
	    Map<String, Object> cartMap = (Map<String, Object>)this.objectMapper.convertValue(cart, 
	    		new TypeReference<Map<String, Object>>() {
	    });
	    cartMap.put("currencyCode", cart.getTotalPrice().getCurrencyCode());
	    return JsonUtils.toJsonString(cartMap);
	}

}
