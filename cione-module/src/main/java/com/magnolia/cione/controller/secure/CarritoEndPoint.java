package com.magnolia.cione.controller.secure;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import java.io.File;
import java.security.Key;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.FileUtils;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CustomLineItem;
import com.commercetools.api.models.cart.LineItem;
import com.commercetools.api.models.common.CentPrecisionMoney;
import com.commercetools.api.models.common.Money;
import com.commercetools.api.models.common.TypedMoney;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.magnolia.cione.dto.AgrupadorDTO;
import com.magnolia.cione.dto.CartParamsDTO;
import com.magnolia.cione.dto.CartParamsDevolucionDTO;
import com.magnolia.cione.dto.DireccionParamsDTO;
import com.magnolia.cione.dto.FileUploadForm;
import com.magnolia.cione.dto.LensParamsDTO;
import com.magnolia.cione.service.CartService;
import com.magnolia.cione.service.CommercetoolsService;
import com.magnolia.cione.service.CustomerService;
import com.magnolia.cione.service.PDFGeneratorConfigurador;
import com.magnolia.cione.setup.CioneEcommerceConectionProvider;
import com.magnolia.cione.utils.CioneUtils;
import com.magnolia.cione.utils.MyShopUtils;

import info.magnolia.ecommerce.EcommerceConnectionProvider;
import info.magnolia.ecommerce.cart.CartProvider;
import info.magnolia.ecommerce.common.Connection;
import info.magnolia.ecommerce.common.Customer;
import info.magnolia.ecommerce.common.EcommerceDefinition;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;
import io.vrap.rmf.base.client.utils.json.JsonUtils;

//@Api(value = "/private/carrito/v1")
@Path("/private/carrito/v1")
public class CarritoEndPoint <D extends EndpointDefinition> extends AbstractEndpoint<D> {
	
	private static final Logger log = LoggerFactory.getLogger(CarritoEndPoint.class);
	@Inject
	private CartService cartService;
	
	@Inject
	private CustomerService customerService;

	@Inject
	private  EcommerceConnectionProvider ecommerceConnectionProvider;
	
	@Inject
	private CioneEcommerceConectionProvider cioneEcommerceConectionProvider;
	
	@Inject
	private  Set<CartProvider> cartProviders;
	
	@Inject
	private PDFGeneratorConfigurador pdfGeneratorConfigurador;
	
	@Inject
	private CommercetoolsService commerceToolsService;
	
	private static final String PATH_SEPARATOR="/";
	
	private ObjectMapper objectMapper;

	
	@Inject
	protected CarritoEndPoint(D endpointDefinition) {
		super(endpointDefinition);
		this.objectMapper = JsonUtils.createObjectMapper();
		this.objectMapper.registerModule(new JavaTimeModule());
	}
	
	/*private String getBackwardCompatibilityCartAsString(Cart cart) throws JsonProcessingException {
	    Map<String, Object> cartMap = (Map<String, Object>)this.objectMapper.convertValue(cart, 
	    		new TypeReference<Map<String, Object>>() {
	    });
	    cartMap.put("currencyCode", cart.getTotalPrice().getCurrencyCode());
	    return JsonUtils.toJsonString(cartMap);
	}*/
	
	@POST
	@Path("/carts-addItem")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response addCartDetalle(@Valid CartParamsDTO cartQueryParamsDTO) {
		try {	
			
			Response response = cartService.addCart(cartQueryParamsDTO);
			return response;
		}catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	@POST
	@Path("/carts-addtoPack")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response addCartPackGeneric(@Valid CartParamsDTO cartQueryParamsDTO) {
		try {	
			if ((cartQueryParamsDTO.getPvoproductopack()==null) || cartQueryParamsDTO.getPvoproductopack().isEmpty()
				|| cartQueryParamsDTO.getStep()<0) {
				log.error("Producto " + cartQueryParamsDTO.getSku() + " con precio vacio " + cartQueryParamsDTO.getPvoproductopack() + " o el step no es valido " + cartQueryParamsDTO.getStep());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("El Producto " + cartQueryParamsDTO.getSku() + " no es valido para el pack ").build();
			}
			
			return cartService.addCartPack(cartQueryParamsDTO);
		}catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	
	@POST
	@Path("/carts-deleteAllPack")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response deleteAllPack(@Valid CartParamsDTO cartQueryParamsDTO) {
		try {
			if ((cartQueryParamsDTO.getIdCarritoOculto() != null) && !cartQueryParamsDTO.getIdCarritoOculto().isEmpty()) {
				Response request = cartService.deleteCart(cartQueryParamsDTO.getIdCarritoOculto());
				
				
				HashMap<String, List<AgrupadorDTO>> infoPackMap = commerceToolsService.getInfoPackMapSession();

				if ((infoPackMap != null) && (infoPackMap.get(cartQueryParamsDTO.getSkuPackMaster()) != null)) {
				    List<AgrupadorDTO> contenidoPack = infoPackMap.get(cartQueryParamsDTO.getSkuPackMaster());
				    for (AgrupadorDTO elementoPack : contenidoPack) {
				        if (elementoPack.isConfigurado() && elementoPack.getSkuProductoPackPreconfigurado() == null) {
				            // encontrado, lo marcamos como configurado y almacenamos su sku
				            elementoPack.setConfigurado(false);
				            
				            elementoPack.setIdCarritoOculto(null);
				            elementoPack.setLineItemIdOculto(null);
				            
				            elementoPack.setSkuProductoConfigurado(null);
				            elementoPack.setUrlImagen(null);
				            elementoPack.setPvoOrigin(null);
				            elementoPack.setNombreProductoConfigurado(null);
				            
				            elementoPack.setInfoCustomLineItemsCioneLab(null);
				            
				            if (!elementoPack.getTipoPrecioPack().equals("CERRADO")) {
					            if (!elementoPack.getTipoPrecioPack().equals("INDIVIDUAL-DTO")) {
					            	if (elementoPack.getTipoPrecioVariante()== null) {
					            		elementoPack.setPvoPack(null);
					            	} else { 
					            		if (!elementoPack.getTipoPrecioVariante().equals("PVO-CERRADO")) {
					            			elementoPack.setPvoPack(null);
					            		}
					            	}
					            }
				            }
				            
				        }
				        //estamos borrando todo el pack, tenemos que desabilitar la opcion de seleccionar lentes oftalmicas
				        if ((elementoPack.getTipoProductoPackKey()!= null) 
				        	&& (elementoPack.getTipoProductoPackKey().equals("LENOF")))
				        	elementoPack.setHabilitar(false);
				    }
				}
				
				return request;
			} else
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("idCarritoOculto vacio").build();
				
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	
	@POST
	@Path("/carts-deleteProductPack")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response deleteProductPack(@Valid CartParamsDTO cartQueryParamsDTO) {
		try {
			Response request = null;
			String decodedUserId = cartQueryParamsDTO.getUserId();
			Integer cantidadAux = 1;
			
			Customer customer = Customer.of(decodedUserId, decodedUserId);
			/*Response request = request(cartQueryParamsDTO.getDefinitionName(), 
	        		cartQueryParamsDTO.getConnectionName(), 
	        		cartProvider -> cartProvider.removeItem(cartQueryParamsDTO.getIdCarritoOculto(), cartQueryParamsDTO.getLineItemIdOculto(), customer, 
	        				getConnection(cartQueryParamsDTO.getDefinitionName(), cartQueryParamsDTO.getConnectionName()).orElse(null)));*/
			
			//ver si descontamos una unidad o borramos
			
			if ((cartQueryParamsDTO.getSkuProductoConfigurado() == null) || cartQueryParamsDTO.getSkuProductoConfigurado().isEmpty()
					|| (cartQueryParamsDTO.getLineItemIdOculto() == null) || cartQueryParamsDTO.getLineItemIdOculto().isEmpty()) {
				log.error("El SkuProductoConfigurado esta vacio o el LineItemIdOculto"); 
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("El SkuProductoConfigurado esta vacio o el LineItemIdOculto").build();
			}
			
			HashMap<String, List<AgrupadorDTO>> infoPackMap = commerceToolsService.getInfoPackMapSession();
			
			//control para lentes oftalmicas, si estamos borrando la montura borramos todo el carrito
			if ((infoPackMap != null) && (infoPackMap.get(cartQueryParamsDTO.getSkuPackMaster()) != null)) {
			    List<AgrupadorDTO> contenidoPack = infoPackMap.get(cartQueryParamsDTO.getSkuPackMaster());
			    //for (AgrupadorDTO elementoPack : contenidoPack) {
			    AgrupadorDTO elementoPack = contenidoPack.get(cartQueryParamsDTO.getStep());
			    
			    if ((elementoPack.getTipoProductoPackKey()!= null) 
			    		&& (elementoPack.getTipoProductoPackKey().equals("GG"))) {
			    	//comprobamos si es pack de cioneLab  agrupadorIni.getTipoProductoPackKey().equals("LENOF")
			    	for (AgrupadorDTO agrupadorCioneLab: contenidoPack) {
			    		if ((agrupadorCioneLab.getTipoProductoPackKey()!= null) && (agrupadorCioneLab.getTipoProductoPackKey().equals("LENOF"))) {
			    			//es de cioneLab borramos todo
			    			return deleteAllPack(cartQueryParamsDTO);
			    		}
			    	}
			    }
			    	
			}
			
			
			Cart cart = cartService.getCartPackByCustomeridPurchase(customer.getId(), cartQueryParamsDTO.getSkuPackMaster());
			if ((cart != null) && (cart.getLineItems().size() > 0)){
				boolean encontrado = false;
				for (LineItem lineItem: cart.getLineItems()) {
					if (lineItem.getId().equals(cartQueryParamsDTO.getLineItemIdOculto())) {
						encontrado = true;
						//if ((lineItem.getCustom() != null) && (lineItem.getCustom().getFields().values().get("pvoConDescuento") != null)) { !!ESTO ESTA PARA LOS PRODUCTOS PRECIO CERRADO!!
							String pvoConDescuento = MyShopUtils.formatTypedMoney((TypedMoney)lineItem.getCustom().getFields().values().get("pvoConDescuento"));
							//if (pvoConDescuento.equals(cartQueryParamsDTO.getPvoPack())) {
								Long quantity = lineItem.getQuantity() -1;
								cantidadAux = (int) (long) quantity;
								break;
							//}
						//}
						
					}
				} 
				if (encontrado) {
					Integer cantidad = cantidadAux;
					request = request(cartQueryParamsDTO.getDefinitionName(), 
			        		cartQueryParamsDTO.getConnectionName(), 
			        		cartProvider -> cartProvider.updateQuantity(cartQueryParamsDTO.getIdCarritoOculto(), cartQueryParamsDTO.getLineItemIdOculto(), cantidad, "", customer, 
			        				getConnection(cartQueryParamsDTO.getDefinitionName(), cartQueryParamsDTO.getConnectionName()).orElse(null)));
				} else {
					//puede darse si un usuario tiene dos sessiones abiertas, lo tratamos como un warning y lo eliminamos de cache
					request = Response.status(Response.Status.OK).entity("Linea no encontrada, la eliminamos solo de cache").build();
					log.warn("Linea no encontrada (" + cartQueryParamsDTO.getLineItemIdOculto() +"), la eliminamos solo de cache");
				}
			} else {
				//puede darse si un usuario tiene dos sessiones abiertas, lo tratamos como un warning y lo eliminamos de cache
				request = Response.status(Response.Status.OK).entity("Linea no encontrada, la eliminamos solo de cache").build();
				log.warn("Linea no encontrada (" + cartQueryParamsDTO.getLineItemIdOculto() +"), la eliminamos solo de cache");
			}

			if ((infoPackMap != null) && (infoPackMap.get(cartQueryParamsDTO.getSkuPackMaster()) != null)) {
			    List<AgrupadorDTO> contenidoPack = infoPackMap.get(cartQueryParamsDTO.getSkuPackMaster());
			    //for (AgrupadorDTO elementoPack : contenidoPack) {
			    AgrupadorDTO elementoPack = contenidoPack.get(cartQueryParamsDTO.getStep());
		        if (elementoPack.isConfigurado() 
		        	&& (elementoPack.getTipoProductoPack() != null)
		        	&& (elementoPack.getLineItemIdOculto() != null)
		        	&& (elementoPack.getPvoPack() != null)
		        	//&& (elementoPack.getTipoProductoPack().equals(cartQueryParamsDTO.getTipoProducto()))
		        	&& (elementoPack.getLineItemIdOculto().equals(cartQueryParamsDTO.getLineItemIdOculto()))) {
		        	//&& (elementoPack.getPvoPack().equals(cartQueryParamsDTO.getPvoPack()))) { !!ESTO ESTA PARA LOS PRODUCTOS PRECIO CERRADO!!
		            // encontrado, lo marcamos como configurado y almacenamos su sku
		            elementoPack.setConfigurado(false);
		            
		            elementoPack.setIdCarritoOculto(null);
		            elementoPack.setLineItemIdOculto(null);
		            
		            elementoPack.setSkuProductoConfigurado(null);
		            elementoPack.setUrlImagen(null);
		            elementoPack.setPvoOrigin(null);
		            elementoPack.setNombreProductoConfigurado(null);
		            
		            if (((!elementoPack.getTipoPrecioPack().equals("INDIVIDUAL") && (!elementoPack.getTipoPrecioPack().equals("INDIVIDUAL-DTO")))) 
		            	|| ((elementoPack.getTipoPrecioVariante()== null))
		            	|| (!elementoPack.getTipoPrecioVariante().equals("PVO-CERRADO"))) {
		            	if (!elementoPack.getTipoPrecioPack().equals("CERRADO")) {
		            		elementoPack.setPvoPack(null);
		            	}
		            }
		            
		            /*if (elementoPack.getTipoProductoPackKey().equals("GG")) {
			            //control packs lentes + monturas borramos tambien la lente oftalmica
				        contenidoPack.forEach(elemento -> {
				        	if (elemento.getTipoProductoPackKey().equals("LENOF"))
				        		elemento.setHabilitar(false);
				        });
		            }*/
		            
		            //break;
		        } 
			}
			
			return request;
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	
	@POST
	@Path("/carts-addtoUserCartPack")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getCartBySkuAddUserCart(@Valid CartParamsDTO cartQueryParamsDTO) {
		try {
			long tiempoInicio = System.currentTimeMillis();
			if ((cartQueryParamsDTO.getListadoProductosPreconfigurados() != null) && !cartQueryParamsDTO.getListadoProductosPreconfigurados().isEmpty()) {
				Response res = cartService.addCartPackPreconfigurados(cartQueryParamsDTO);
				long tiempoFin = System.currentTimeMillis();
				log.info("Ha tardado = " + (tiempoFin - tiempoInicio));
				
				/*Response res2 = cartService.addtoUserCartPack(cartQueryParamsDTO);
				tiempoFin = System.currentTimeMillis();
				log.info("Ha tardado = " + (tiempoFin - tiempoInicio));*/
				
				return cartService.addtoUserCartPack(cartQueryParamsDTO);
			}else
				return cartService.addtoUserCartPack(cartQueryParamsDTO);
			
			
		}catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		
		/*
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
		}*/
    }
	
	@POST
	@Path("/carts-addItemReturn")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response addDevolucion(@Valid CartParamsDevolucionDTO cartQueryParamsDTO) {
		try {
			if ((cartQueryParamsDTO!= null) 
					&& (cartQueryParamsDTO.getDevoluciones()!= null) 
					&& (!cartQueryParamsDTO.getDevoluciones().isEmpty()) ) {
				return cartService.addCartCustomLineReturn(cartQueryParamsDTO);
			} else {
				return Response.status(Response.Status.OK).entity("Cart Empty").build();
			}
		}catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
		
		
    }
	
	/*private void prueba() {
		
		Cart periodicpurchase = cartService.getCart("b3077503-ced6-43a8-b703-292e5dfba260");
		
		try {
			sendTemplateEmail("Compra periódica", "msanchezp@atsistemas.com", buildTemplateEmail(periodicpurchase));
		} catch (CioneException e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
	}
	
	private Map<String, Object> buildTemplateEmail(Cart periodicpurchase) {
		Map<String, Object> templateValues = new HashMap<>();
		templateValues.put(MailTemplate.MAIL_TEMPLATE_FILE, "cione-module/templates/mail/compra-periodica-mail.ftl");
		templateValues.put("logoHeader", getImageLink("/cione/templates/emails/indice.svg"));
		return templateValues;

	}
	
	private String getImageLink(String path) {
		String link = "";
		try {
			if (damTemplatingFunctions.getAsset("jcr", path) != null) {
				link = CioneUtils.getURLHttps() + damTemplatingFunctions.getAsset("jcr", path).getLink();
			}
		} catch (Exception e) {
			log.error("ERROR al obtener la imagen " + path, e);
		}
		return link;
	}
	
	private void sendTemplateEmail(String subject, String email, Map<String, Object> templateValues) throws CioneException {
		
		@SuppressWarnings("deprecation")
		final MgnlMailFactory mailFactory = ((MailModule) ModuleRegistry.Factory.getInstance().getModuleInstance("mail")).getFactory();
		
        try {
        	log.debug("SEND MAIL TO " + email);
            final MgnlEmail mail = mailFactory.getEmailFromType(templateValues, "freemarker");
            mail.setFrom(configService.getConfig().getAuthSenderEmail());
            mail.setToList(email);
            mail.setSubject(subject);            
            mail.setBodyFromResourceFile();  
            
            mailFactory.getEmailHandler().prepareAndSendMail(mail);
            
        } catch (Exception e) {
        	log.error(e.getMessage(),e);        	
        	throw new CioneException("Se ha producido un error en el envío del email"); 
        }	
		
	}*/
	
	@POST
	@Path("/carts-addCustomItem")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response addCartCustomLine(@Valid LensParamsDTO lensParamsDTO) {
		try {
			if ((lensParamsDTO.getSku() != null) && !lensParamsDTO.getSku().isEmpty()) {
				Response response = cartService.addCartCustomLine(lensParamsDTO);
				return response;
			} else {
				log.error("lensParamsDTO.sku esta vacio, no ha podido formarse con la configurcion seleccionada " +lensParamsDTO.toString());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("").build();
			}
			
		}catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	@POST
	@Path("/carts-addItemList")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response addCartListHome(@Valid CartParamsDTO cartQueryParamsDTO) {
		try {
			Response response = cartService.addCartBySku(cartQueryParamsDTO);
			return response;
		}catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	@POST
	@Path("/cartDeleteItem")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response removeCartLineItem(@Valid CartParamsDTO cartQueryParamsDTO) {
		//PROBAR BIEN!! creo que tendriamos que pasarle el id del usuario logado
		String decodedUserId;
		Response request = null;
        if (cartQueryParamsDTO.getUserId() == null || Objects.equals(cartQueryParamsDTO.getUserIdEncodingDisabled(), "true")) {
            decodedUserId = cartQueryParamsDTO.getUserId();
        } else {
            decodedUserId =  new String(Base64.getDecoder().decode(cartQueryParamsDTO.getUserId()));
        }
        Customer customer = Customer.of(decodedUserId, decodedUserId);//cioneEcommerceConectionProvider.getApiRoot().customers().withId(decodedUserId).get().executeBlocking().getBody().get();//Customer.of(decodedUserId, "");
        if ((cartQueryParamsDTO.getRefPackPromos()!= null) && (!cartQueryParamsDTO.getRefPackPromos().isEmpty())) {
        	Cart cart = cartService.getCart(customer.getId());
        	for (LineItem item : cart.getLineItems()) {
        		if (item.getCustom().getFields().values().get("refPackPromos") != null) {
        			if (cartQueryParamsDTO.getRefPackPromos().equals(item.getCustom().getFields().values().get("refPackPromos"))) {
        				request = request(cartQueryParamsDTO.getDefinitionName(), 
        		        		cartQueryParamsDTO.getConnectionName(), 
        		        		cartProvider -> cartProvider.removeItem(cartQueryParamsDTO.getId(), item.getId(), customer, 
        		        				getConnection(cartQueryParamsDTO.getDefinitionName(), cartQueryParamsDTO.getConnectionName()).orElse(null)));
        			}
        		}
        		
        	}
        	for (CustomLineItem citem: cart.getCustomLineItems()) {
        		if (citem.getCustom().getFields().values().get("refPackPromos") != null) {
        			if (cartQueryParamsDTO.getRefPackPromos().equals(citem.getCustom().getFields().values().get("refPackPromos"))) {
        				request = cartService.removeCLI(cart.getId(), citem.getId(), decodedUserId);
        			}
        		}
        	}
        } else {
        	request = request(cartQueryParamsDTO.getDefinitionName(), 
        		cartQueryParamsDTO.getConnectionName(), 
        		cartProvider -> cartProvider.removeItem(cartQueryParamsDTO.getId(), cartQueryParamsDTO.getLineItemId(), customer, 
        				getConnection(cartQueryParamsDTO.getDefinitionName(), cartQueryParamsDTO.getConnectionName()).orElse(null)));
        }
        return request;
	}
	
	@POST
	@Path("/cartDeleteCLI")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response removeCartCLI(@Valid CartParamsDTO cartQueryParamsDTO) {
		String decodedUserId;
        if (cartQueryParamsDTO.getUserId() == null || Objects.equals(cartQueryParamsDTO.getUserIdEncodingDisabled(), "true")) {
            decodedUserId = cartQueryParamsDTO.getUserId();
        } else {
            decodedUserId =  new String(Base64.getDecoder().decode(cartQueryParamsDTO.getUserId()));
        }
        //Customer customer = Customer.of(decodedUserId, "");
        
        return cartService.removeCLI(cartQueryParamsDTO.getId(), cartQueryParamsDTO.getLineItemId(), decodedUserId);
        
//        return cartService.removeCLI(cartQueryParamsDTO.getId(), cartQueryParamsDTO.getLineItemId(), customer, 
//        		getConnection(cartQueryParamsDTO.getDefinitionName(), cartQueryParamsDTO.getConnectionName()).orElse(null));
	}
	
	@POST
	@Path("/cart/update/{cantidad}")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response updateItemQuantity(@Valid CartParamsDTO cartQueryParamsDTO, @PathParam("cantidad") Integer cantidad) {
		String decodedUserId;
        if (cartQueryParamsDTO.getUserId() == null || Objects.equals(cartQueryParamsDTO.getUserIdEncodingDisabled(), "true")) {
            decodedUserId = cartQueryParamsDTO.getUserId();
        } else {
            decodedUserId =  new String(Base64.getDecoder().decode(cartQueryParamsDTO.getUserId()));
        }
        Customer customer = Customer.of(decodedUserId, "");
        
        Response request = request(cartQueryParamsDTO.getDefinitionName(), 
        		cartQueryParamsDTO.getConnectionName(), 
        		cartProvider -> cartProvider.updateQuantity(cartQueryParamsDTO.getId(), cartQueryParamsDTO.getLineItemId(), cantidad, "", customer, 
        				getConnection(cartQueryParamsDTO.getDefinitionName(), cartQueryParamsDTO.getConnectionName()).orElse(null)));
        
        return request;
	}
	
	@POST
	@Path("/cart/updatecli/{cantidad}")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response updateCLIQuantity(@Valid CartParamsDTO cartQueryParamsDTO, @PathParam("cantidad") Integer cantidad) {
		String decodedUserId;
        if (cartQueryParamsDTO.getUserId() == null || Objects.equals(cartQueryParamsDTO.getUserIdEncodingDisabled(), "true")) {
            decodedUserId = cartQueryParamsDTO.getUserId();
        } else {
            decodedUserId =  new String(Base64.getDecoder().decode(cartQueryParamsDTO.getUserId()));
        }
        Customer customer = Customer.of(decodedUserId, "");
        
        return cartService.updateQuantityCLI(cartQueryParamsDTO.getId(), cartQueryParamsDTO.getLineItemId(), cantidad, "", 
        		customer, getConnection(cartQueryParamsDTO.getDefinitionName(), cartQueryParamsDTO.getConnectionName()).orElse(null));
	}
	
	@POST
	@Path("/cart/updatePlazo/{plazoEntrega}")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response updatePlazoEntrega(@Valid CartParamsDTO cartQueryParamsDTO, @PathParam("plazoEntrega") Integer plazoEntrega) {
		try {
			Response response = cartService.updatePlazoEntrega(cartQueryParamsDTO, plazoEntrega);
			return response;
		} catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	@POST
	@Path("/cart/updatePvoConDescuento/{nuevoPvo}")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response updatePvoConDescuento(@Valid CartParamsDTO cartQueryParamsDTO, @PathParam("nuevoPvo") String nuevoPvo) {
		Money monetaryAmount = MyShopUtils.getMoney(nuevoPvo);//Money.of(new BigDecimal(nuevoPvo), "EUR");
		
		try {
			Response response = cartService.updatePvoConDescuento(cartQueryParamsDTO, monetaryAmount);
			return response;
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	@POST
	@Path("/cart/updateShippingAddress")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response updateShippingAddress(@Valid DireccionParamsDTO addressParams) {
		try {
			Response response = cartService.updateShippingAddress(addressParams);
			return response;
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	@POST
	@Path("/cart/updateCustomField/{updatedField}")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response updateCustomField(@Valid CartParamsDTO cartQueryParamsDTO, @PathParam("updatedField") String updatedField) {
		try {
			Response response = cartService.updateCustomField(cartQueryParamsDTO, updatedField);
			return response;
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	@POST
	@Path("/cart/updateCustomFieldCLIPack/{updatedField}")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response updateCustomFieldCLIPack(@Valid CartParamsDTO cartQueryParamsDTO, @PathParam("updatedField") String updatedField) {
		try {
			Response response = cartService.updateCustomFieldCLI(cartQueryParamsDTO, updatedField);
			
			return response;
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	@POST
	@Path("/cart/updateCustomFieldCLI/{updatedField}")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response updateCustomFieldCLI(@Valid CartParamsDTO cartQueryParamsDTO, @PathParam("updatedField") String updatedField) {
		try {
			Response response = cartService.updateCustomFieldCLI(cartQueryParamsDTO, updatedField);
			return response;
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	@POST
	@Path("/cart/updateSASNote")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response updateSASNoteField(@Valid CartParamsDTO cartQueryParamsDTO) {
		try {
			Response response = cartService.updateSASNoteField(cartQueryParamsDTO);
			return response;
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	@POST
	@Path("/cart/prepareCart")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response prepareCart() {
		try {
			Response response = cartService.prepareCart();
			return response;
			//return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("error").build();
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	@GET
	@Path("/cart/pvoFullCart")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getPvoFullCart() {
		JSONObject jsonRes = new JSONObject();
		try {
			Cart cart = customerService.getUserCart();
			if (cart == null) {
				return generateResponseBuilder(jsonRes.put("pvocart", "0").toString(), Response.Status.OK).build();
			} else {
				
				Money total = MyShopUtils.getMoney(0);
				
				for (LineItem item: cart.getLineItems()) {
					
					boolean enDeposito = false;
					if ((item.getCustom() != null) && (item.getCustom().getFields().values().get("enDeposito") != null)) {
						enDeposito = (boolean) item.getCustom().getFields().values().get("enDeposito");
					}
					
					if(!enDeposito) {
						if ((item.getCustom() != null) && (item.getCustom().getFields().values().get("pvoConDescuento") != null)) {
							Long suma = ((CentPrecisionMoney)item.getCustom().getFields().values().get("pvoConDescuento")).getCentAmount() * item.getQuantity();
							total.setCentAmount(total.getCentAmount() + suma);
						} else {
							total.setCentAmount(total.getCentAmount() + item.getTotalPrice().getCentAmount());
						}
					}
				}
				
				for (CustomLineItem item: cart.getCustomLineItems()) {
					if ((item.getCustom() != null) && (item.getCustom().getFields().values().get("pvoConDescuento_L") != null)) {
						Long suma = ((CentPrecisionMoney)item.getCustom().getFields().values().get("pvoConDescuento_L")).getCentAmount() * item.getQuantity();
						total.setCentAmount(total.getCentAmount() + suma);
					} else if ((item.getCustom() != null) && (item.getCustom().getFields().values().get("pvoConDescuento_R") != null)) {
						Long suma = ((CentPrecisionMoney)item.getCustom().getFields().values().get("pvoConDescuento_R")).getCentAmount() * item.getQuantity();
						total.setCentAmount(total.getCentAmount() + suma);
					} else
						total.setCentAmount(total.getCentAmount() + item.getTotalPrice().getCentAmount());
				}
				
				String total_str = MyShopUtils.formatMoney(total);
				return generateResponseBuilder(jsonRes.put("pvocart", total_str).toString(), Response.Status.OK).build();
			}
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	@GET
	@Path("/cart/pvoFullCartById")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getPvoFullCartById(@QueryParam("idCart") String idCart) {
		JSONObject jsonRes = new JSONObject();
		try {
			Cart cart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(idCart).get().executeBlocking().getBody().get();
			if (cart == null) {
				return generateResponseBuilder(jsonRes.put("pvocart", "0").toString(), Response.Status.OK).build();
			} else {
				
				Money total = cartService.getPvoByCart(cart);
				
				String total_str = MyShopUtils.formatMoney(total);
				return generateResponseBuilder(jsonRes.put("pvocart", total_str).toString(), Response.Status.OK).build();
			}
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	/*@POST
	@Path("/cart/addCartToShoppingList")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response addCartToShoppingList(@Valid CartParamsDTO cartQueryParamsDTO) {
		try {
			Response response = cartService.addCartToShoppingList(getConnection(cartQueryParamsDTO.getDefinitionName(), cartQueryParamsDTO.getConnectionName()).orElse(null));
			return response;
		} catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }*/
	
	@POST
	@Path("/cart/toOrder")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response cartToOrder() {
		try {
			Response response = cartService.cartToOrder();
			return response;
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	@GET
	@Path("/refPack")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getRefPack() {
		
		JSONObject jsonRes = new JSONObject();
		
		try {
			
			String refPack = CioneUtils.generateRef();
			jsonRes.put("refPack", refPack);
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.OK).build();
			
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	@GET
	@Path("/pvo")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getPvo(@QueryParam("sku") String sku) {
		
		JSONObject jsonRes = new JSONObject();
		
		try {
			String pvo = cartService.getPvo(sku);
			jsonRes.put("pvo", pvo);
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.OK).build();
			
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	@GET
	@Path("/encript")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getEncript(@QueryParam("refTaller") String refTaller) {
		
		JSONObject jsonRes = new JSONObject();
		
		try {
			
			String ref = encript(refTaller);
			jsonRes.put("key", ref);
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.OK).build();
			
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	@POST
	@Path("/loadFile")
	@Consumes("multipart/form-data;charset=utf-8")
	public Response loadFile(@MultipartForm FileUploadForm form) {
		try {
			JSONObject jsonRes = new JSONObject();
			if (form.getMyFile() != null) {
				
				String path = createDir(form.getRefTaller());
				if ((path != null) && (form.getNameFile()!=null)) {
					String nameFile = CioneUtils.getIdCurrentClientERP() + "-" + form.getRefTaller() + form.getNameFile();
					form.setNameFile(nameFile);
					File file = new File(path + PATH_SEPARATOR + form.getNameFile());
					if (file != null) {
						file.delete();
					}
					FileUtils.writeByteArrayToFile(file, form.getMyFile());
					file.setReadable(true, false); //read
				    file.setWritable(true, false); //write
				    file.setExecutable(true, false); //execute
					jsonRes.put("path", file.getName());
					return generateResponseBuilder(jsonRes.toString(), Response.Status.OK).build();
				} 
			}
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR uploadFile").build();
		}catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
    }
	
	@POST
	@Path("/createPDF")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response createPdfAudio(@Valid CartParamsDTO cartQueryParamsDTO) {
		try {	
			String idCliente = CioneUtils.getIdCurrentClientERP();
			String nameFile = idCliente + "-" + cartQueryParamsDTO.getRefTaller() + "-audiolab.pdf";
			cartQueryParamsDTO.setNamePdfAudio(nameFile);
            File file = pdfGeneratorConfigurador.getFileSending(cartQueryParamsDTO);
			JSONObject jsonRes = new JSONObject();
			jsonRes.put("namefile", file.getName());
			return generateResponseBuilder(jsonRes.toString(), Response.Status.OK).build();
		}catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	/* Metodo PDF audiologia avanzada */
	@GET
	@Path("/audiolabPDF")
	@Produces("application/pdf")
	public Response getPDFConfigurador(@QueryParam("namefile") String namefile, @QueryParam("refTaller") String refTaller){
		try {
			String idClient = CioneUtils.getIdCurrentClientERP();
			File file = new File(cioneEcommerceConectionProvider.getConfigService().getConfig().getAudioLabConfiguradorPDFPath() 
					+ PATH_SEPARATOR + idClient + PATH_SEPARATOR + refTaller + PATH_SEPARATOR+ namefile);
			if ((file != null) && file.exists()) {
				ResponseBuilder response = Response.ok((Object) file);
				response.header("Content-Disposition", "attachment; filename=\""+ file.getName() + "\"");
				return response.build();
			} else {	
				return Response.status(Response.Status.NOT_FOUND).entity("El documento no está disponible").build();
			}
		}catch(Exception e) {
			log.error(e.getMessage(), e);			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/refreshCache")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response refreshCache(@QueryParam("skuPackMaster") String skuPackMaster) {
		
		JSONObject jsonRes = new JSONObject();
		log.debug("refreshCache");
		
		try {
			HashMap<String, List<AgrupadorDTO>> infoPackMapSession = commerceToolsService.getInfoPackMapSession();
			infoPackMapSession.put(skuPackMaster, null);
			
			jsonRes.put("respuesta", "ok");
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.OK).build();
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/cart/validateCart")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response validateCartMinPurchase(@QueryParam("language") String language, @QueryParam("contentNodePath") String contentNodePath) {
		try {
			return cartService.isValidaCartMinimunPurchase(language, contentNodePath);
			//return generateResponseBuilder(jsonRes.put("isValidCart", minimunPurchase.isValidaCart()).toString(), Response.Status.OK).build();
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
    }
	
	private String createDir(String refTaller) {
		/*try {
			java.nio.file.Path dir2 = Paths.get("/opt/data/impresiones-old");
			Files.walk(dir2)
		    .sorted(Comparator.reverseOrder())
		    .map(java.nio.file.Path::toFile)
		    .forEach(File::delete);
		} catch (IOException e) {
			log.error("/opt/data/impresiones-old no existe");
		}
		
		try {
			java.nio.file.Path dir2 = Paths.get("/opt/data/audiolab-old");
			Files.walk(dir2)
		    .sorted(Comparator.reverseOrder())
		    .map(java.nio.file.Path::toFile)
		    .forEach(File::delete);
		} catch (IOException e) {
			log.error("/opt/data/impresiones-old no existe");
		}*/
		
		String idClient = CioneUtils.getIdCurrentClientERP();
		String dir_primer_nivel = cioneEcommerceConectionProvider.getConfigService().getConfig().getAudioLabImpresionesPath();
		String dir_segundo_nivel = dir_primer_nivel+PATH_SEPARATOR+idClient;
		String dir_tercer_nivel = dir_segundo_nivel+PATH_SEPARATOR+refTaller;
		
		try {
			File files = new File(dir_primer_nivel);
			if (!files.exists()) {
				files.mkdir();
				getGroupPermission(files);
				log.debug("Creado directorio " + dir_primer_nivel);
				File file_second = new File(dir_segundo_nivel);
				file_second.mkdir();
				getGroupPermission(file_second);
				log.debug("Creado directorio " + dir_segundo_nivel);
				File file_third = new File(dir_tercer_nivel);
				file_third.mkdir();
				getGroupPermission(file_third);
				log.debug("Creado directorio " + dir_tercer_nivel);
			} else {
				File file_second = new File(dir_segundo_nivel);
				if (!file_second.exists()) {
					file_second.mkdir();
					getGroupPermission(file_second);
					log.debug("Creado directorio " + dir_segundo_nivel);
			    	File file_third = new File(dir_tercer_nivel);
			    	file_third.mkdir();
			    	getGroupPermission(file_third);
			    	log.debug("Creado directorio " + dir_tercer_nivel);
			    } else {
			    	File file_third = new File(dir_tercer_nivel);
			    	file_third.mkdir();
			    	getGroupPermission(file_third);
			    	log.debug("Creado directorio " + dir_tercer_nivel);
			    }
			}
		} catch (Exception e) {
			log.error("ERROR al crear el directorio " + e.getMessage(), e);
		}
        return dir_tercer_nivel;
	}
	
	private ResponseBuilder generateResponseBuilder(String res, Status status) {

		return Response.status(status)
				.type(MediaType.APPLICATION_JSON + "; charset=utf-8")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8"))
				.header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
				.header(HttpHeaders.EXPIRES, "0")
				.entity(res);

	}
	
    private Response request(String definitionName, String connectionName, Function<CartProvider, Response> function) {
        Optional<Connection> connection = ecommerceConnectionProvider.getConnection(definitionName, connectionName);
        if (!connection.isPresent()) {
            return badRequestForNotPresentConnection();
        }

        return connection
                .map(this::getMatchingProvider)
                .map(Optional::get)
                .map(function)
                .orElse(badRequestForMatchingProvider());
    }

    private Response badRequestForNotPresentConnection() {
        return Response.status(BAD_REQUEST).entity("Corresponding connection couldn't be found.").build();
    }

    private Response badRequestForMatchingProvider() {
        return Response.status(BAD_REQUEST)
                .entity("Either ecommerce type or corresponding cart implementation couldn't not be found.")
                .build();
    }
    private Optional<CartProvider> getMatchingProvider(Connection connection) {
        Optional<String> ecommerceType = getEcommerceType(connection);
        if (!ecommerceType.isPresent()) {
            return Optional.empty();
        }

        List<CartProvider> matchingCartProviders = cartProviders.stream()
                .filter(mapper -> mapper.getType().equalsIgnoreCase(ecommerceType.get()))
                .collect(Collectors.toList());

        if (matchingCartProviders.size() > 1) {
            log.warn("Operate on the first CartProvider but it found [{}] matches, please check your settings", matchingCartProviders.size());
        }

        return matchingCartProviders.stream().findAny();
    }

    private Optional<String> getEcommerceType(Connection connection) {
        return ecommerceConnectionProvider.definition(connection).map(EcommerceDefinition::getType);
    }

    private Optional<Connection> getConnection(String definitionName, String connectionName) {
        return ecommerceConnectionProvider.getConnection(definitionName, connectionName);
    }
    
	private void getGroupPermission(File file) {
		file.setReadable(true, false);
    	file.setWritable(true, false);
    	file.setExecutable(true, false);
	}
	
	private String encript(String refTaller) throws Exception {	
		String ENCRYPT_KEY = "RUmFT2LhZMnTjHLo";
		
		Key aesKey = new SecretKeySpec(ENCRYPT_KEY.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, aesKey);

		byte[] encrypted = cipher.doFinal(refTaller.getBytes());
			
		return Base64.getEncoder().encodeToString(encrypted);
		
	}
	

}
