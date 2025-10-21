package com.magnolia.cione.controller.secure;

import java.io.File;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.models.cart.Cart;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.ShoppingListDTO;
import com.magnolia.cione.service.ShoppingListService;

import info.magnolia.ecommerce.EcommerceConnectionProvider;
import info.magnolia.ecommerce.common.Connection;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;
import io.vrap.rmf.base.client.utils.json.JsonUtils;

//@Api(value = "/private/shoppinglist")
@Path("/private/shoppinglist")
public class ShoppingListEndpoint  <D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(ShoppingListEndpoint.class);
	
	@Inject
	private ShoppingListService shoppingListService;

	@Inject
	private  EcommerceConnectionProvider ecommerceConnectionProvider;
	
	private final ObjectMapper objectMapper;
	
	@Inject
	protected ShoppingListEndpoint(D endpointDefinition) {
		super(endpointDefinition);
		this.objectMapper = JsonUtils.createObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
	}
	
	@POST
	@Path("/addCartToShoppingList")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response addCartToShoppingList() {
		
		try {
			
			Cart updatedCart = shoppingListService.addCartToShoppingList();
			String cartResponse = getBackwardCompatibilityCartAsString(updatedCart);
			if (updatedCart != null) {
				return Response.ok(cartResponse).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se ha podido generar el carrito").build();
			}
			
		} catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
    }
	
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response addCartToShoppingList(@Valid ShoppingListDTO shoppingListDTO) {

		if (shoppingListDTO.getId() != null && !shoppingListDTO.getId().isEmpty()) {
			
			if (shoppingListService.isUserCart(shoppingListDTO.getId())) {
			
				try {
					
					Cart updatedCart = shoppingListService.updateShoppingList(
							getConnection(MyshopConstants.CT, MyshopConstants.CON).orElse(null), 
							shoppingListDTO);
					
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
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getAll(@QueryParam("date") String date, 
						   @QueryParam("customermail") String customermail, 
						   @QueryParam("numbudget") String numbudget,
						   @QueryParam("numcliente") String numcliente,
						   @QueryParam("page") int page) {
		try {
//			PagedQueryResult<Cart> list = shoppingListService.getShoppingLists(
//					date,customermail,numbudget,numcliente,page);
			
			return shoppingListService.getShoppingLists(
					date,customermail,numbudget,numcliente,page);
			
		} catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	@GET
	@Path("/retrieve")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getCartByShoopingListAndRetrieve(@QueryParam("id") String id) {
		
		if (shoppingListService.isUserCart(id)) {
		
			try {
				Cart carr = shoppingListService.getCartByShoppingListAndRetrieve(
						getConnection(MyshopConstants.CT, MyshopConstants.CON).orElse(null), id);
				return Response.ok(getBackwardCompatibilityCartAsString(carr)).build();
			} catch(Exception e) {
				log.error(e.getMessage());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
			}
			
		}else {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
    }
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response deleteById(@QueryParam("id") String id) {
		
		if (id != null && !id.isEmpty()) {
			
			try {
				if (shoppingListService.isUserCart(id)) {
					
					Cart deletecart = shoppingListService.removeShoppingList(id);
					
					if (deletecart != null) {
						return Response.ok(getBackwardCompatibilityCartAsString(deletecart)).build();
					}else {
						return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
					}
					
				}else {
					return Response.status(Response.Status.FORBIDDEN).build();
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
			
		}else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
    }

	@POST
	@Path("/updateCustomField/{updatedField}")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response updateCustomField(@Valid ShoppingListDTO shoppingListDTO, @PathParam("updatedField") String updatedField) {
		
		try {
			
			Response res = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			
			if (shoppingListDTO.getId() != null && !shoppingListDTO.getId().isEmpty()) {
				
				if (shoppingListService.isUserCart(shoppingListDTO.getId())) {
					
					if(updatedField.equals(MyshopConstants.IMPORTEVENTA)) {
						
						Cart updatedCart = shoppingListService.updateCustomField(
								getConnection(MyshopConstants.CT, MyshopConstants.CON).orElse(null), 
								shoppingListDTO, updatedField);
						
						if(updatedCart != null) {
							res = Response.ok(getBackwardCompatibilityCartAsString(updatedCart)).build();
						}
						
					}else{
						res = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
								MyshopConstants.ERRORUPDATECUSTOMFIELD + updatedField + 
								". Campo desconocido.").build();
					}
					
				}else {
					return Response.status(Response.Status.FORBIDDEN).build();
				}
				
			}else {
				res = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						MyshopConstants.ERRORUPDATECUSTOMFIELD + updatedField).build();
			}
			
			return res;
			
		} catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
    }
	
	@POST
	@Path("/updateCustomFieldCLI/{updatedField}")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response updateCustomFieldCLI(@Valid ShoppingListDTO shoppingListDTO, 
			@PathParam("updatedField") String updatedField) {
		
		try {
			
			Response res = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			
			if (shoppingListDTO.getId() != null && !shoppingListDTO.getId().isEmpty()) {
				
				if (shoppingListService.isUserCart(shoppingListDTO.getId())) {
					
					if(updatedField.equals(MyshopConstants.IMPORTEVENTA)) {
						
						Cart updatedCart = shoppingListService.updateCustomFieldCLI(
								getConnection(MyshopConstants.CT, 
								MyshopConstants.CON).orElse(null), 
								shoppingListDTO, updatedField);
						
						if(updatedCart != null) {
							res = Response.ok(getBackwardCompatibilityCartAsString(updatedCart)).build();
						}
						
					}else{
						res = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
								MyshopConstants.ERRORUPDATECUSTOMFIELDCLI + 
								updatedField + ". Campo desconocido.").build();
					}
				
				}else {
					return Response.status(Response.Status.FORBIDDEN).build();
				}
				
			}else {
				res = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						MyshopConstants.ERRORUPDATECUSTOMFIELDCLI + updatedField).build();
			}
			
			return res;
			
		} catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		
    }
	
	@GET
	@Path("/pdfbudget")
	@Produces("application/pdf")
	public Response getPDFEnvios(@QueryParam("id") String id){
		
		if (id != null && !id.isEmpty()) {
			
			if (shoppingListService.isUserCart(id)) {
				
				try {
					
					File file = shoppingListService.getBudgetPDF(
							getConnection(MyshopConstants.CT, MyshopConstants.CON).orElse(null), 
							id);
					
					if ((file != null) && file.exists()) {
						ResponseBuilder response = Response.ok((Object) file);
						response.header("Content-Disposition", "attachment; filename=\""+ file.getName() + "\"");
						return response.build();
					} else {	
						return Response.status(Response.Status.NOT_FOUND).entity("El documento no esta disponible").build();
					}
				
				}catch(Exception e) {
					log.error(e.getMessage());			
					return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
				}
				
			}else {
				return Response.status(Response.Status.FORBIDDEN).build();
			}
			
		}else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}

    private Optional<Connection> getConnection(String definitionName, String connectionName) {
        return ecommerceConnectionProvider.getConnection(definitionName, connectionName);
    }
    
	private String getBackwardCompatibilityCartAsString(Cart cart) throws JsonProcessingException {
	    Map<String, Object> cartMap = (Map<String, Object>)this.objectMapper.convertValue(cart, 
	    		new TypeReference<Map<String, Object>>() {
	    });
	    cartMap.put("currencyCode", cart.getTotalPrice().getCurrencyCode());
	    return JsonUtils.toJsonString(cartMap);
	}

}
