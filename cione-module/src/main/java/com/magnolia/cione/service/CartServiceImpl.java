package com.magnolia.cione.service;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.jcr.ItemNotFoundException;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;
import javax.naming.Context;
import javax.naming.InitialContext;
//import javax.money.MonetaryAmount;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartAddCustomLineItemAction;
import com.commercetools.api.models.cart.CartAddCustomLineItemActionBuilder;
import com.commercetools.api.models.cart.CartAddLineItemAction;
import com.commercetools.api.models.cart.CartAddLineItemActionBuilder;
import com.commercetools.api.models.cart.CartChangeCustomLineItemQuantityAction;
import com.commercetools.api.models.cart.CartChangeCustomLineItemQuantityActionBuilder;
import com.commercetools.api.models.cart.CartDraft;
import com.commercetools.api.models.cart.CartRecalculateAction;
import com.commercetools.api.models.cart.CartRecalculateActionBuilder;
import com.commercetools.api.models.cart.CartRemoveCustomLineItemAction;
import com.commercetools.api.models.cart.CartRemoveCustomLineItemActionBuilder;
import com.commercetools.api.models.cart.CartResourceIdentifier;
import com.commercetools.api.models.cart.CartResourceIdentifierBuilder;
import com.commercetools.api.models.cart.CartSetCustomFieldAction;
import com.commercetools.api.models.cart.CartSetCustomFieldActionBuilder;
import com.commercetools.api.models.cart.CartSetCustomLineItemCustomFieldAction;
import com.commercetools.api.models.cart.CartSetCustomLineItemCustomFieldActionBuilder;
import com.commercetools.api.models.cart.CartSetCustomTypeAction;
import com.commercetools.api.models.cart.CartSetCustomTypeActionBuilder;
import com.commercetools.api.models.cart.CartSetCustomerIdAction;
import com.commercetools.api.models.cart.CartSetCustomerIdActionBuilder;
import com.commercetools.api.models.cart.CartSetLineItemCustomFieldAction;
import com.commercetools.api.models.cart.CartSetLineItemCustomFieldActionBuilder;
import com.commercetools.api.models.cart.CartSetShippingAddressAction;
import com.commercetools.api.models.cart.CartSetShippingAddressActionBuilder;
import com.commercetools.api.models.cart.CartSetShippingMethodAction;
import com.commercetools.api.models.cart.CartSetShippingMethodActionBuilder;
import com.commercetools.api.models.cart.CartUpdate;
import com.commercetools.api.models.cart.CartUpdateAction;
import com.commercetools.api.models.cart.CartUpdateBuilder;
import com.commercetools.api.models.cart.CustomLineItem;
import com.commercetools.api.models.cart.LineItem;
import com.commercetools.api.models.category.Category;
import com.commercetools.api.models.category.CategoryReference;
import com.commercetools.api.models.common.Address;
import com.commercetools.api.models.common.AddressBuilder;
import com.commercetools.api.models.common.CentPrecisionMoney;
import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.common.Money;
import com.commercetools.api.models.common.Price;
import com.commercetools.api.models.common.TypedMoney;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.order.Order;
import com.commercetools.api.models.order.OrderFromCartDraft;
import com.commercetools.api.models.order.OrderFromCartDraftBuilder;
import com.commercetools.api.models.order.OrderSetOrderNumberAction;
import com.commercetools.api.models.order.OrderSetOrderNumberActionBuilder;
import com.commercetools.api.models.order.OrderUpdate;
import com.commercetools.api.models.order.OrderUpdateBuilder;
import com.commercetools.api.models.product.ProductProjection;
import com.commercetools.api.models.product.ProductVariant;
import com.commercetools.api.models.product_type.AttributePlainEnumValue;
import com.commercetools.api.models.shipping_method.ShippingMethodResourceIdentifier;
import com.commercetools.api.models.shipping_method.ShippingMethodResourceIdentifierBuilder;
import com.commercetools.api.models.tax_category.TaxCategory;
import com.commercetools.api.models.type.CustomFieldsDraft;
import com.commercetools.api.models.type.CustomFieldsDraftBuilder;
import com.commercetools.api.models.type.FieldContainer;
import com.commercetools.api.models.type.FieldContainerBuilder;
import com.commercetools.api.models.type.Type;
import com.commercetools.api.models.type.TypeResourceIdentifier;
import com.commercetools.api.models.type.TypeResourceIdentifierBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.magnolia.cione.beans.GiftProduct;
import com.magnolia.cione.beans.MarcaProveedor;
import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dao.ConsultaMarcasDao;
import com.magnolia.cione.dao.PromocionesDao;
import com.magnolia.cione.dto.AgrupadorDTO;
import com.magnolia.cione.dto.CartParamsDTO;
import com.magnolia.cione.dto.CartParamsDevolucionDTO;
import com.magnolia.cione.dto.CompraMinimaDTO;
import com.magnolia.cione.dto.CustomerCT;
import com.magnolia.cione.dto.DevolucionDTO;
import com.magnolia.cione.dto.DireccionDTO;
import com.magnolia.cione.dto.DireccionParamsDTO;
import com.magnolia.cione.dto.DireccionesDTO;
import com.magnolia.cione.dto.LensParamsDTO;
import com.magnolia.cione.dto.PromocionesDTO;
import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.dto.VariantDTO;
import com.magnolia.cione.exceptions.CioneException;
import com.magnolia.cione.pur.NodeUtilities;
import com.magnolia.cione.setup.CategoryUtils;
import com.magnolia.cione.setup.CioneEcommerceConectionProvider;
import com.magnolia.cione.utils.CioneUtils;
import com.magnolia.cione.utils.MyShopUtils;

import info.magnolia.context.MgnlContext;
import info.magnolia.ecommerce.commercetools.SphereClientProvider;
import info.magnolia.ecommerce.commercetools.cart.CommercetoolsCartProvider;
import info.magnolia.ecommerce.common.Connection;
import info.magnolia.i18nsystem.SimpleTranslator;
import io.vrap.rmf.base.client.ApiHttpResponse;
import io.vrap.rmf.base.client.utils.json.JsonUtils;

public class CartServiceImpl implements CartService {
	
	private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);
	
//	@Inject
//	private  EcommerceConnectionProvider ecommerceConnectionProvider;
	
	@Inject
	private CioneEcommerceConectionProvider cioneEcommerceConectionProvider;
    
	//@Inject
	//private  Set<CartProvider> cartProviders;
	
	@Inject
	private CommercetoolsService commercetoolsService;
	
	@Inject
	private DetalleService detalleService;
	
	@Inject
	private CommercetoolsServiceAux commercetoolsServiceAux;
	
	@Inject
	private PromocionesDao promocionesDao;
	
	@Inject
	private CategoryService categoryService;

    private final SphereClientProvider clientProvider;
    
    private final CommercetoolsCartProvider commercetoolsCartProvider;
    
	@Inject
	private MiddlewareService middlewareService;
	
	@Inject
	private ProductService productService;
	
	@Inject
	private EmailService emailService;
	
	@Inject
	private ConsultaMarcasDao consultaMarcasDao;
	
	@Inject
	private CategoryUtils categoryUtils;
	
	@Inject
	private NodeUtilities nodeUtilities;
	
	@Inject
	private ConfigService configService;
    
    private static final String PATH_SEPARATOR="/";
    
    private final SimpleTranslator i18n;
    
    private final ObjectMapper objectMapper;
    

    @Inject
    public CartServiceImpl(CommercetoolsCartProvider commercetoolsCartProvider, SphereClientProvider clientProvider, SimpleTranslator i18n) {
        this.clientProvider = clientProvider;
        this.objectMapper = JsonUtils.createObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.commercetoolsCartProvider = commercetoolsCartProvider;
        this.i18n = i18n;
    }
    
	
	public Response addCartCustomLine(LensParamsDTO lensParamsDTO) {
		
		Customer customer = commercetoolsService.getCustomerSDK();
		String idCliente = customer.getId();
		
		Cart cart = getCart(customer.getId());
		
		if (cart == null) {
			cart = createCart(customer, MyshopConstants.deleteDaysAfterLastModification);
		}
		
		final Map<String, Object> fields = new HashMap<>();

		fields.put("SKU", lensParamsDTO.getSku());
		fields.put("EYE", lensParamsDTO.getOjo());
		fields.put("DESCRIPTION", lensParamsDTO.getDescription());
		fields.put("CYL", lensParamsDTO.getCyl());
		fields.put("SPH", lensParamsDTO.getSph());
		fields.put("CRIB", lensParamsDTO.getCrib());
		fields.put("nivel1", lensParamsDTO.getNivel1());
		fields.put("nivel2", lensParamsDTO.getNivel2());
		fields.put("refTaller", lensParamsDTO.getReftaller());
		fields.put("refCliente", lensParamsDTO.getRefcliente());
		fields.put("LMATTYPE", lensParamsDTO.getLmattype());
		fields.put("aTaller", lensParamsDTO.isaTaller());
		Integer cantidad = lensParamsDTO.getCantidad();
		String pvo = lensParamsDTO.getPvo();

		TypedMoney pvoDtoMonetary = MyShopUtils.getMonetaryAmount(pvo);
		LocalizedString name = LocalizedString.of(MyshopConstants.esLocale, lensParamsDTO.getName()); 
		String slug = lensParamsDTO.getSlug(); 
		TaxCategory taxCategory = commercetoolsService.getTaxCategory();
		
		TypeResourceIdentifier customType = TypeResourceIdentifierBuilder.of().key("customlineitem-stock-lenses").build();
		FieldContainer fieldContainer = FieldContainerBuilder.of().values(fields).build();
		CustomFieldsDraft customFieldDraft = CustomFieldsDraftBuilder.of().type(customType).fields(fieldContainer).build();
		
		List<CartUpdateAction> actions = new ArrayList<>();
		CartAddCustomLineItemAction customLineItem = 
				CartAddCustomLineItemActionBuilder.of()
				.name(name)
				.slug(slug)
				.money(moneyBuilder -> moneyBuilder.currencyCode("EUR").centAmount(pvoDtoMonetary.getCentAmount()))
				//.money(money)
				.taxCategory(taxcategory -> taxcategory.id(taxCategory.getId()))
				.custom(customFieldDraft)
				.quantity(Long.valueOf(cantidad))
				.build();
		actions.add(customLineItem);
		actions.add(CartSetCustomerIdActionBuilder.of().customerId(idCliente).build());
		
		CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(actions).build();
		cart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartUpdate).executeBlocking().getBody().get();	
		
		//final CustomFieldsDraft custom = CustomFieldsDraft.ofTypeKeyAndObjects("customlineitem-stock-lenses", fields);
		
//		CustomLineItemDraft customLine = CustomLineItemDraft.of(name, slug, pvoDtoMonetary, taxCategory, cantidad.longValue(), custom);
//		final AddCustomLineItem addCustomLineItemAction = AddCustomLineItem.of(customLine);
//        
//		final List<UpdateAction<Cart>> updateActions =
//				Arrays.asList(addCustomLineItemAction, SetCustomerId.of(idCliente), Recalculate.of().withUpdateProductData(true));
//        final Cart updatedCart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(cart, updateActions));
		try {
			String cartResponse = getBackwardCompatibilityCartAsString(cart);
			return Response.ok(cartResponse).build();
		}catch (Exception e) {
			return Response.ok(cart).build();
		}
	}
	
	public Cart getCartPackByCustomeridPurchase(String customerId, String skuPackMaster) {
		Cart cart = null;
		try {
			List<String> querys = new ArrayList<>();
			querys.add("customerId=\"" + customerId + "\"");
			querys.add("cartState=\"Active\"");
			querys.add("custom(fields(cartType=\"" + MyshopConstants.GENERIC_PACK + "\"))");
			querys.add("custom(fields(idPurchase=\"" + skuPackMaster + "\"))");
			
			List<Cart> carts =  cioneEcommerceConectionProvider
					.getApiRoot()
					.carts()
					.get()
					.withWhere(querys)
					.executeBlocking().getBody().getResults();
			if (!carts.isEmpty())
				cart = carts.get(0);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
		return cart;
	}
	
	public Response addCartPack(CartParamsDTO cartQueryParamsDTO) throws Exception {
		
		Customer customer = commercetoolsService.getCustomerSDK();
		String skuPackMaster = cartQueryParamsDTO.getSkuPackMaster();
		Cart cart = getCartPackByCustomeridPurchase(customer.getId(), skuPackMaster);
		//obtenemos el carrito del usuario con pack
		
		//creamos un carrito con caducidad de 1 dia, en cartType seteamos el tipo "pack" y en idPurchase skuPackMaster para que nos sirva para identificar el pack creado
		
		if (cart==null) {
			Map<String, Object> fieldsCart = new HashMap<>();
			fieldsCart.put(MyshopConstants.CARTTYPE, "pack");
			fieldsCart.put(MyshopConstants.PURCHASE, skuPackMaster);
			cart = createCart(customer, MyshopConstants.deleteDaysAfterLastModificationPack,fieldsCart);
		}		
		cart = addLineItemPack(cart, cartQueryParamsDTO);
		try {
	    	String cartResponse = getBackwardCompatibilityCartAsString(cart);
			return Response.ok(cartResponse).build();
    	}catch (Exception e) {
    		return Response.ok(cart).build();
		}
		
	}
	
	public Response addCartPackPreconfigurados(CartParamsDTO cartQueryParamsDTO) {
		
		Cart cart = null;
		try {
			Customer customer = commercetoolsService.getCustomerSDK();
			String skuPackMaster = cartQueryParamsDTO.getSkuPackMaster();
			cart = getCartPackByCustomeridPurchase(customer.getId(), skuPackMaster);
			
			//creamos un carrito con caducidad de 1 dia, en cartType seteamos el tipo "pack" y en idPurchase skuPackMaster para que nos sirva para identificar el pack creado
			
			if (cart==null) {
				Map<String, Object> fieldsCart = new HashMap<>();
				fieldsCart.put(MyshopConstants.CARTTYPE, "pack");
				fieldsCart.put(MyshopConstants.PURCHASE, skuPackMaster);
				cart = createCart(customer, MyshopConstants.deleteDaysAfterLastModificationPack,fieldsCart);
			}
			
			HashMap<String, List<AgrupadorDTO>> infoPackMapSession = commercetoolsService.getInfoPackMapSession();
			List<AgrupadorDTO> listaAgrupadores = infoPackMapSession.get(skuPackMaster);
			
			int step=0;
			List<CartUpdateAction> actions = new ArrayList<>();
			String grupoPrecioCommerce = commercetoolsService.getGrupoPrecioCommerce();
			ProductProjection productPack = commercetoolsServiceAux.getProductBySkuFilter(skuPackMaster, grupoPrecioCommerce);
			for (AgrupadorDTO agrupador : listaAgrupadores) {
				if (agrupador.getSkuProductoPackPreconfigurado() != null) {
					CartParamsDTO cartQueryParamsDTOProductoPreConfigurado = new CartParamsDTO();
					
					cartQueryParamsDTOProductoPreConfigurado.setSku(agrupador.getSkuProductoPackPreconfigurado());
					cartQueryParamsDTOProductoPreConfigurado.setSkuPackMaster(skuPackMaster);
					cartQueryParamsDTOProductoPreConfigurado.setFamiliaProducto(agrupador.getTipoProductoPack());
					cartQueryParamsDTOProductoPreConfigurado.setTipoPrecioPack(agrupador.getTipoPrecioPack());
					cartQueryParamsDTOProductoPreConfigurado.setPvoproductopack(agrupador.getPvoPack());
					cartQueryParamsDTOProductoPreConfigurado.setStep(step);
					
					cartQueryParamsDTOProductoPreConfigurado.setCantidad(agrupador.getUnidadesProductoPack());
					
					
					final Map<String, Object> fields = new HashMap<>();
					TypeResourceIdentifier typeResourceIdentifier = null;
					FieldContainer fieldContainer = null;
					CustomFieldsDraft customFieldsDraft = null;
					
					fields.put("promoAplicada", "pack-universal-"+cartQueryParamsDTOProductoPreConfigurado.getSkuPackMaster());
					
					
					
					fields.put("descPackPromos", productPack.getName().get(MyshopConstants.esLocale));
					fields.put("tipoPrecioPack", cartQueryParamsDTOProductoPreConfigurado.getTipoPrecioPack());
					
					fields.put("step", String.valueOf(step));
					
					//comprobar el stock
					float stock = 1;
					ProductProjection product = commercetoolsServiceAux.getProductBySkuFilter(cartQueryParamsDTOProductoPreConfigurado.getSku(), grupoPrecioCommerce);
					ProductVariant variant = commercetoolsServiceAux.findVariantBySku(product, cartQueryParamsDTOProductoPreConfigurado.getSku());
					if (MyShopUtils.hasAttribute("aliasEkon", variant.getAttributes())) {
						String aliasEkon = (String) MyShopUtils.findAttribute("aliasEkon", variant.getAttributes()).getValue();
						stock = commercetoolsService.getStockWithCart(variant.getSku(), aliasEkon);
					}
					if (stock >0) { //metemos las unidades de una en una
						fields.put("plazoEntrega", Integer.valueOf(2));
					} else {
						if (MyShopUtils.hasAttribute("plazoEntregaProveedor", variant.getAttributes())) {
							fields.put("plazoEntrega",  2 + ((Long) MyShopUtils.findAttribute("plazoEntregaProveedor", variant.getAttributes()).getValue()).intValue());
						} else {
							fields.put("plazoEntrega", Integer.valueOf(2));
						}
					}
					
					
					//fields.put("tipoProductoPack", ((LocalizedString) MyShopUtils.findAttribute("tipoProducto", variant.getAttributes()).getValue()).get(MyshopConstants.esLocale));
					fields.put("tipoProductoPack",cartQueryParamsDTOProductoPreConfigurado.getFamiliaProducto());
					
					//AQUI PVOCONDESCUENTO
					TypedMoney pvoDtoMonetary = MyShopUtils.getMonetaryAmount(cartQueryParamsDTOProductoPreConfigurado.getPvoproductopack());
					
					if (pvoDtoMonetary == null) {
						throw new Exception("El pvoProductoPack no puede ser null, revisar configuracion");
					}
					
					fields.put("pvoConDescuento", pvoDtoMonetary);
					typeResourceIdentifier = 
							TypeResourceIdentifier
							.builder()
							.id(cioneEcommerceConectionProvider.getMapTypes().get("customFields-forCartLines").getId())
							.build();
					fieldContainer = FieldContainerBuilder.of().values(fields).build();
					customFieldsDraft = CustomFieldsDraftBuilder.of().type(typeResourceIdentifier).fields(fieldContainer).build();
	
					
					
					CartAddLineItemAction cartAction = CartAddLineItemActionBuilder.of()
		            .sku(agrupador.getSkuProductoPackPreconfigurado())
		            .quantity(Long.valueOf(agrupador.getUnidadesProductoPack()))
		            .custom(customFieldsDraft)
		            .build();
					
					actions.add(cartAction);
				}
				
				//cart = addLineItemPack(cart, cartQueryParamsDTOProductoPreConfigurado);
				step++;
			}
			CartUpdate cartupdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(actions).build();
			cart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartupdate).executeBlocking().getBody();
			
			/*StringTokenizer listadoProductosPreconfigurados = new StringTokenizer(cartQueryParamsDTO.getListadoProductosPreconfigurados(),"&");
			while (listadoProductosPreconfigurados.hasMoreElements()) {
				String skuProductoPreconfigurado = listadoProductosPreconfigurados.nextToken();
				
				HashMap<String, List<AgrupadorDTO>> infoPackMapSession = commercetoolsService.getInfoPackMapSession();
				if ((infoPackMapSession != null) && (infoPackMapSession.get(skuPackMaster) != null)) {
					List<AgrupadorDTO> listaAgrupadores = infoPackMapSession.get(skuPackMaster);
					int step=0;
					for (AgrupadorDTO agrupador : listaAgrupadores) {
						if (skuProductoPreconfigurado.equals(agrupador.getSkuProductoConfigurado())) {
							//es el producto a configurar
							
							CartParamsDTO cartQueryParamsDTOProductoPreConfigurado = new CartParamsDTO();
							
							cartQueryParamsDTOProductoPreConfigurado.setSku(skuProductoPreconfigurado);
							cartQueryParamsDTOProductoPreConfigurado.setSkuPackMaster(skuPackMaster);
							cartQueryParamsDTOProductoPreConfigurado.setFamiliaProducto(agrupador.getTipoProductoPack());
							cartQueryParamsDTOProductoPreConfigurado.setTipoPrecioPack(agrupador.getTipoPrecioPack());
							cartQueryParamsDTOProductoPreConfigurado.setPvoproductopack(agrupador.getPvoPack());
							cartQueryParamsDTOProductoPreConfigurado.setStep(step);
							
							cartQueryParamsDTOProductoPreConfigurado.setCantidad(agrupador.getUnidadesProductoPack());
							
							cart = addLineItemPack(cart, cartQueryParamsDTOProductoPreConfigurado);
							
							break; //lo hemos encontrado, no seguimos recorriendo el for
						}
						step++;
					}
				}
			}*/
		} catch (Exception e) {
			
			log.error(e.getMessage(),e);
		}
		
		
		
		try {
	    	String cartResponse = getBackwardCompatibilityCartAsString(cart);
			return Response.ok(cartResponse).build();
    	}catch (Exception e) {
    		return Response.ok(cart).build();
		}
		
	}
	
	public Response addtoUserCartPack(CartParamsDTO cartQueryParamsDTO) {
		String skuPackMaster = cartQueryParamsDTO.getSkuPackMaster();
		Customer customer = commercetoolsService.getCustomerSDK();
		Cart cartPack = getCartPackByCustomeridPurchase(customer.getId(), skuPackMaster);
		Cart customerCart = getCart(customer.getId());
		if (customerCart == null)
			customerCart = createCart(customer, MyshopConstants.deleteDaysAfterLastModification);
		if (cartPack == null) {
			log.error("Error addtoUserCartPack, cartPack with skuPackMaster " + skuPackMaster + " and customerId = " + customer.getId() + " not Found");
			return Response.status(Response.Status.NOT_FOUND).entity("Error addtoUserCartPack, cartPack with skuPackMaster " + skuPackMaster + " and customerId = " + customer.getId() + " not Found").build();
		}

		List<CartUpdateAction> actions = new ArrayList<>();
		for(LineItem item: cartPack.getLineItems()) {
			actions.add(addCartLineItemActions(customerCart, item, cartQueryParamsDTO.getRefPackPromos(), cartQueryParamsDTO.getRefTaller(), cartQueryParamsDTO.getRefCliente()));
			//customerCart = addCartLineItem(customerCart, item, cartQueryParamsDTO.getRefPackPromos(), cartQueryParamsDTO.getRefTaller(), cartQueryParamsDTO.getRefCliente());
		}
		if ((cartPack.getLineItems() != null) && (cartPack.getLineItems().size() >0)) {
			CartRecalculateAction cartRecalculateAction = CartRecalculateActionBuilder.of().updateProductData(true).build();
			actions.add(cartRecalculateAction);
			
			CartUpdate cartUpdate = CartUpdateBuilder.of().version(customerCart.getVersion()).actions(actions).build();
			customerCart = cioneEcommerceConectionProvider
				.getApiRoot()
				.carts()
				.withId(customerCart.getId())
				.post(cartUpdate).executeBlocking()
				.getBody();
		}
		
		for(CustomLineItem item: cartPack.getCustomLineItems()) {
			ProductProjection productPack = commercetoolsServiceAux.getProductBySkuFilter(cartQueryParamsDTO.getSkuPackMaster(), commercetoolsService.getGrupoPrecioCommerce());
			String descPackPromos = productPack.getName().get(MyshopConstants.esLocale);
			customerCart = addCartCustomLine(customerCart, item, descPackPromos, cartQueryParamsDTO.getRefPackPromos());
		}
		
		
		//borramos el carrito con el Pack
		cioneEcommerceConectionProvider.getApiRoot().carts()
			.withId(cartPack.getId())
	        .delete()
	        .withVersion(cartPack.getVersion())
	        .executeBlocking();
		
		
		//borramos el carrito de session
		HttpSession session = MgnlContext.getWebContext().getRequest().getSession();
		HashMap<String, List<AgrupadorDTO>> infoPackMapSession = commercetoolsService.getInfoPackMapSession();
		infoPackMapSession.remove(skuPackMaster);
		session.setAttribute(MyshopConstants.ATTR_PACK_SESSION, infoPackMapSession);

		//y lo inicializamos a partir de la funcionalidad de detalle (simulamos en loader de la página de detalle para inicializar todo)
		try {
			detalleService.getInfoVariantPack(skuPackMaster);
		} catch (Exception e1) {
			log.error(e1.getMessage(), e1);
		}
		
		try {
	    	String cartResponse = getBackwardCompatibilityCartAsString(customerCart);
			return Response.ok(cartResponse).build();
    	}catch (Exception e) {
    		return Response.ok(customerCart).build();
		} 
	}
	
	private CartAddLineItemAction addCartLineItemActions(Cart cart, LineItem item, String refPackPromos, String refTaller, String refCliente) {
		//List<CartUpdateAction> actions = new ArrayList<>();
		
		final Map<String, Object> fields = new HashMap<>();
		
		@NotNull Map<String, Object> customs = item.getCustom().getFields().values();
		String customtype = "";
		String typeId = item.getCustom().getType().getId();
		Type type = cioneEcommerceConectionProvider.getApiRoot().types().withId(typeId).get().executeBlocking().getBody();
		if (type != null) {
			customtype = type.getKey();
		} else
			customtype = "customFields-forCartLines";
		for (Map.Entry<String,Object> entry : customs.entrySet()) {
			fields.put(entry.getKey(),entry.getValue());
		}
		if (refPackPromos != null)
			fields.put("refPackPromos",refPackPromos);
		
		if ((refTaller != null) && (!refTaller.isEmpty()))
			fields.put("refTaller",refTaller);
		
		if ((refCliente != null) && (!refCliente.isEmpty()))
			fields.put("refCliente", refCliente);
		
		TypeResourceIdentifier customType = TypeResourceIdentifierBuilder.of().key(customtype).build();
		FieldContainer fieldContainer = FieldContainerBuilder.of().values(fields).build();		
		CustomFieldsDraft customFieldDraft = CustomFieldsDraftBuilder.of().type(customType).fields(fieldContainer).build();
		
		
		CartAddLineItemAction lineItemAction = 
			CartAddLineItemActionBuilder.of()
				.productId(item.getProductId())
				.variantId(item.getVariant().getId())
				.custom(customFieldDraft)
				.quantity(item.getQuantity())
				.build();
		
		return lineItemAction;
	}
	
	public Cart addCartLineItem(Cart cart, LineItem item, String refPackPromos, String refTaller, String refCliente) {
		
		final Map<String, Object> fields = new HashMap<>();
		
		@NotNull Map<String, Object> customs = item.getCustom().getFields().values();
		String customtype = "";
		String typeId = item.getCustom().getType().getId();
		Type type = cioneEcommerceConectionProvider.getApiRoot().types().withId(typeId).get().executeBlocking().getBody();
		if (type != null) {
			customtype = type.getKey();
		} else
			customtype = "customFields-forCartLines";
		for (Map.Entry<String,Object> entry : customs.entrySet()) {
			fields.put(entry.getKey(),entry.getValue());
		}
		if (refPackPromos != null)
			fields.put("refPackPromos",refPackPromos);
		
		if ((refTaller != null) && (!refTaller.isEmpty()))
			fields.put("refTaller",refTaller);
		
		if ((refCliente != null) && (!refCliente.isEmpty()))
			fields.put("refCliente", refCliente);
		
//		if(StringUtils.isBlank(customtype)) 
//			customtype = "customFields-forCartLines";
		
		TypeResourceIdentifier customType = TypeResourceIdentifierBuilder.of().key(customtype).build();
		FieldContainer fieldContainer = FieldContainerBuilder.of().values(fields).build();		
		CustomFieldsDraft customFieldDraft = CustomFieldsDraftBuilder.of().type(customType).fields(fieldContainer).build();
		
		//final CustomFieldsDraft custom = CustomFieldsDraft.ofTypeKeyAndObjects(customtype, fields);
		
		List<CartUpdateAction> actions = new ArrayList<>();
		CartAddLineItemAction lineItemAction = 
			CartAddLineItemActionBuilder.of()
				.productId(item.getProductId())
				.variantId(item.getVariant().getId())
				.custom(customFieldDraft)
				.quantity(item.getQuantity())
				.build();
		actions.add(lineItemAction);
		CartRecalculateAction cartRecalculateAction = CartRecalculateActionBuilder.of().updateProductData(true).build();
		actions.add(cartRecalculateAction);
		
		CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(actions).build();
		final Cart updatedCart = cioneEcommerceConectionProvider
			.getApiRoot()
			.carts()
			.withId(cart.getId())
			.post(cartUpdate).executeBlocking()
			.getBody();
		  
//		LineItemDraft lineItemDraft = LineItemDraft.of(item.getProductId(), item.getVariant().getId(), (int) (long)item.getQuantity())
//				.withCustom(custom)
//				.newBuilder().build();
		
		//final LineItemDraft builtLineItemDraft = LineItemDraftBuilder.of(lineItemDraft).build();
		
//		final List<UpdateAction<Cart>> updateActions =
//				Arrays.asList(AddLineItem.of(builtLineItemDraft), Recalculate.of().withUpdateProductData(true));
//        
//        final Cart updatedCart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(cart, updateActions));
		
		return updatedCart;
	}
	
	public Cart addCartCustomLine(Cart cart, CustomLineItem item, String descPackPromos, String refPackPromos) {
		Cart updatedCart = null;
		try {
			String idCliente = cart.getCustomerId();
			final Map<String, Object> fields = new HashMap<>();
			Integer cantidad = (int)(long)item.getQuantity();
			
			//String pvo = String.valueOf(item.getMoney().getNumber().doubleValueExact()).replace(".", ",");
			//String pvo = MyShopUtils.formatMoney(item.getMoney());
			@NotNull @Valid TypedMoney money = item.getMoney();
			//MonetaryAmount pvoDtoMonetary = MyShopUtils.getMonetaryAmount(pvo);
			
			LocalizedString name = LocalizedString.of(MyshopConstants.esLocale, item.getName().get("es")); 
			String slug = item.getSlug(); 
			TaxCategory taxCategory = commercetoolsService.getTaxCategory();
			
			Map<String, Object> customs = item.getCustom().getFields().values();
			String customtype = getTypeCustom(item);
			
			for (Map.Entry<String,Object> entry : customs.entrySet()) {
				fields.put(entry.getKey(),entry.getValue());
			}
			if (refPackPromos != null)
				fields.put("refPackPromos",refPackPromos);
			if ((descPackPromos != null) && (!descPackPromos.isEmpty()))
				fields.put("descPackPromos",descPackPromos);
			
			//if ((refCliente != null) && (!refCliente.isEmpty()))
				//fields.put("refCliente", refCliente);
			
			if(StringUtils.isBlank(customtype))
				customtype = "customlineitem-stock-lenses";
			
			TypeResourceIdentifier customTypeIdentifier = TypeResourceIdentifierBuilder.of().key(customtype).build();
			FieldContainer fieldContainer = FieldContainerBuilder.of().values(fields).build();
			CustomFieldsDraft customFieldDraft = CustomFieldsDraftBuilder.of().type(customTypeIdentifier).fields(fieldContainer).build();
			
			//final CustomFieldsDraft custom = CustomFieldsDraft.ofTypeKeyAndObjects(customtype, fields);
			List<CartUpdateAction> actions = new ArrayList<>();
			CartAddCustomLineItemAction customLineItem = 
					CartAddCustomLineItemActionBuilder.of()
					.name(name)
					.slug(slug)
					.money(moneyBuilder -> moneyBuilder.currencyCode("EUR").centAmount(money.getCentAmount()))
					//.money(money)
					.taxCategory(taxcategory -> taxcategory.id(taxCategory.getId()))
					.custom(customFieldDraft)
					.quantity(cantidad.longValue())
					.build();
			
			actions.add(customLineItem);
			CartRecalculateAction cartRecalculateAction = CartRecalculateActionBuilder.of().updateProductData(true).build();
			actions.add(cartRecalculateAction);
			CartSetCustomerIdAction customerAction = CartSetCustomerIdActionBuilder.of().customerId(idCliente).build();
			actions.add(customerAction);
			
			//CustomLineItemDraft customLine = CustomLineItemDraft.of(name, slug, pvoDtoMonetary, taxCategory, cantidad.longValue(), custom);
			//final AddCustomLineItem addCustomLineItemAction = AddCustomLineItem.of(customLine);
			
			CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(actions).build();
			updatedCart = cioneEcommerceConectionProvider
				.getApiRoot()
				.carts()
				.withId(cart.getId())
				.post(cartUpdate).executeBlocking()
				.getBody();
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
        
//		final List<UpdateAction<Cart>> updateActions =
//				Arrays.asList(addCustomLineItemAction, SetCustomerId.of(idCliente), Recalculate.of().withUpdateProductData(true));
//        final Cart updatedCart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(cart, updateActions));
		
		return updatedCart;
	}
	
	private String getTypeCustom(CustomLineItem item) {
		String keyType = "";
		String typeId = item.getCustom().getType().getId();
		Type type = cioneEcommerceConectionProvider.getApiRoot().types().withId(typeId).get().executeBlocking().getBody();
		if (type != null) {
			keyType = type.getKey();
		}
		return keyType;
	}
	
	public Response addCartBySku(CartParamsDTO cartQueryParamsDTO) {
		Customer customer = commercetoolsService.getCustomerSDK();
		String sku = cartQueryParamsDTO.getSku();
		Integer cantidad = cartQueryParamsDTO.getCantidad();
		if (cantidad == null)
			cantidad = 1;
		String refcliente = cartQueryParamsDTO.getRefCliente();
		String coloraudio = cartQueryParamsDTO.getColorAudio();
		//productos sustitutivos
		if ((cartQueryParamsDTO.getSustitutiveProduct() != null)
				&& (!cartQueryParamsDTO.getSustitutiveProduct().isEmpty())) {
			//al agregar un porducto sustitutivo modificamos todos los valores que calculamos en el front
			sku = cartQueryParamsDTO.getSustitutiveProduct();
			
		}
		if (customer != null) {
		    Cart cart = getCart(customer.getId());
		    if (cart == null) { //carrito vacio
		    	cart = createCart(customer, MyshopConstants.deleteDaysAfterLastModification);
		    	if (cart != null)
			    	cart = addLineItemsBySku(cart, sku, cantidad, refcliente, coloraudio);
			    else
			    	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error addCart/addLineItems").build();
			} else {
				cart = addLineItemsBySku(cart, sku, cantidad, refcliente, coloraudio);
			}
		    if (cart != null) {
		    	try {
			    	String cartResponse = getBackwardCompatibilityCartAsString(cart);
					return Response.ok(cartResponse).build();
		    	}catch (Exception e) {
		    		return Response.ok(cart).build();
				}
		    } else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error addCart/addLineItems").build();
		    }
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error addCart/addLineItems, customer doesn't exist").build();
		}
	}
	
	
	private Cart createCart(Customer customer, Long expiration, Map<String, Object> fields) {
		Cart cart = null;
		TypeResourceIdentifier typeResourceIdentifier = 
				TypeResourceIdentifier
				.builder()
				.id(cioneEcommerceConectionProvider.getMapTypes().get("customFields-forOrders").getId())
				.build();
		FieldContainer fieldContainer = FieldContainerBuilder.of().values(fields).build();
		
		CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.of().type(typeResourceIdentifier).fields(fieldContainer).build();
		
//		Long expire = Long.valueOf(expiration);
		CartDraft cartDraft = CartDraft.builder()
				.customerId(customer.getId())
				.customerEmail(customer.getEmail())
				.currency("EUR")
				.custom(customFieldsDraft)
				.deleteDaysAfterLastModification(expiration)
				.build();
		
		cart = cioneEcommerceConectionProvider.getApiRoot().carts().post(cartDraft).executeBlocking().getBody();
		
		/*CustomFieldsDraft customFieldsDraft = 
				CustomFieldsDraft.ofTypeKeyAndObjects("customFields-forOrders", fields);
		//CountryCode countryCode = CountryCode.ES;
		try {
			CurrencyUnit EUR = Monetary.getCurrency("EUR");
			CartDraft cartDraft = CartDraft.of(EUR)
					.withDeleteDaysAfterLastModification(expiration)
					.withCustomerId(customer.getId())
					.withCustomerEmail(customer.getEmail())
					.withCustom(customFieldsDraft);
			CompletionStage<Cart> request = cioneEcommerceConectionProvider.getClient().execute(CartCreateCommand.of(cartDraft));
			cart = request.toCompletableFuture().join();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}*/
		return cart;
	}
	
	@Override
	public Cart createCart(Customer customer, Long expiration) {
		Cart cart = null;
		//Long expire = Long.valueOf(expiration);
		CartDraft cartDraft = CartDraft.builder()
				.customerId(customer.getId())
				.customerEmail(customer.getEmail())
				.currency("EUR")
				.deleteDaysAfterLastModification(expiration)
				.build();
		
		cart = cioneEcommerceConectionProvider.getApiRoot().carts().post(cartDraft).executeBlocking().getBody();
		return cart;
	}
	
	
	private Cart addLineItemPack(Cart cart, CartParamsDTO cartQueryParamsDTO) throws Exception {
		Cart updatedCart = null;
		String grupoPrecioCommerce = commercetoolsService.getGrupoPrecioCommerce();
		
		ProductProjection product = commercetoolsServiceAux.getProductBySkuFilter(cartQueryParamsDTO.getSku(), grupoPrecioCommerce);
		ProductVariant variant = commercetoolsServiceAux.findVariantBySku(product, cartQueryParamsDTO.getSku());
		String tipoProducto = "";
		String nombreProductoConfigurado = product.getName().get(MyshopConstants.esLocale);
		if (MyShopUtils.hasAttribute("tipoProducto", variant.getAttributes()))
			tipoProducto= ((LocalizedString) MyShopUtils.findAttribute("tipoProducto", variant.getAttributes()).getValue()).get(MyshopConstants.esLocale);
		if (MyShopUtils.hasAttribute("nombreArticulo", variant.getAttributes()))
			nombreProductoConfigurado= ((String) MyShopUtils.findAttribute("nombreArticulo", variant.getAttributes()).getValue());
		
		String productId = product.getId();
		Long variantId = variant.getId();
		
		String familia = cartQueryParamsDTO.getFamiliaProducto();
		final Map<String, Object> fields = new HashMap<>();
		
		TypeResourceIdentifier typeResourceIdentifier = null;
		FieldContainer fieldContainer = null;
		CustomFieldsDraft customFieldsDraft = null;
		
		switch (familia) {
		case "audifonos":
			if ((cartQueryParamsDTO.getColorAudio() != null) && (!cartQueryParamsDTO.getColorAudio().isEmpty()))
				fields.put("colorAudifono", cartQueryParamsDTO.getColorAudio());
			typeResourceIdentifier = 
					TypeResourceIdentifier
					.builder()
					.id(cioneEcommerceConectionProvider.getMapTypes().get("lineitem-audifonos").getId())
					.build();
			fieldContainer = FieldContainerBuilder.of().values(fields).build();
			customFieldsDraft = CustomFieldsDraftBuilder.of().type(typeResourceIdentifier).fields(fieldContainer).build();
			break;
		case "audiolab":
			if ((cartQueryParamsDTO.getColorAudio() != null) && (!cartQueryParamsDTO.getColorAudio().isEmpty()))
				fields.put("colorAudifono", cartQueryParamsDTO.getColorAudio());
			typeResourceIdentifier = 
					TypeResourceIdentifier
					.builder()
					.id(cioneEcommerceConectionProvider.getMapTypes().get("lineitem-audiolab").getId())
					.build();
			fieldContainer = FieldContainerBuilder.of().values(fields).build();
			customFieldsDraft = CustomFieldsDraftBuilder.of().type(typeResourceIdentifier).fields(fieldContainer).build();
			break;
		default:
			fields.put("promoAplicada", "pack-universal-"+cartQueryParamsDTO.getSkuPackMaster());
			
			ProductProjection productPack = commercetoolsServiceAux.getProductBySkuFilter(cartQueryParamsDTO.getSkuPackMaster(), grupoPrecioCommerce);
			
			if (MyShopUtils.hasAttribute("tipoPackGenerico", productPack.getMasterVariant().getAttributes())) {
				String key = ((AttributePlainEnumValue) MyShopUtils.findAttribute("tipoPackGenerico", productPack.getMasterVariant().getAttributes()).getValue()).getKey();
				if (key.equals(MyshopConstants.TIPOPACKGENERICO_MONTURAS_LENTES)) {
					fields.put("aTaller", true);
				}
			}
			
			fields.put("descPackPromos", productPack.getName().get(MyshopConstants.esLocale));
			fields.put("tipoPrecioPack", cartQueryParamsDTO.getTipoPrecioPack());
			
			if (cartQueryParamsDTO.getStep() >= 0) {
				fields.put("step", String.valueOf(cartQueryParamsDTO.getStep()));
			}
			
			//comprobar el stock
			float stock = 1;
			if (MyShopUtils.hasAttribute("aliasEkon", variant.getAttributes())) {
				String aliasEkon = (String) MyShopUtils.findAttribute("aliasEkon", variant.getAttributes()).getValue();
				stock = commercetoolsService.getStockWithCart(variant.getSku(), aliasEkon);
			}
			if (stock >0) { //metemos las unidades de una en una
				fields.put("plazoEntrega", Integer.valueOf(2));
			} else {
				if (MyShopUtils.hasAttribute("plazoEntregaProveedor", variant.getAttributes())) {
					fields.put("plazoEntrega",  2 + ((Long) MyShopUtils.findAttribute("plazoEntregaProveedor", variant.getAttributes()).getValue()).intValue());
				} else {
					fields.put("plazoEntrega", Integer.valueOf(2));
				}
			}
			fields.put("tipoProductoPack", tipoProducto);
			
			//AQUI PVOCONDESCUENTO
			TypedMoney pvoDtoMonetary = MyShopUtils.getMonetaryAmount(cartQueryParamsDTO.getPvoproductopack());
			
			if (pvoDtoMonetary == null) {
				throw new Exception("El pvoProductoPack no puede ser null, revisar configuracion");
			}
			
			fields.put("pvoConDescuento", pvoDtoMonetary);
			typeResourceIdentifier = 
					TypeResourceIdentifier
					.builder()
					.id(cioneEcommerceConectionProvider.getMapTypes().get("customFields-forCartLines").getId())
					.build();
			fieldContainer = FieldContainerBuilder.of().values(fields).build();
			customFieldsDraft = CustomFieldsDraftBuilder.of().type(typeResourceIdentifier).fields(fieldContainer).build();
			break;
		}
		
		List<CartUpdateAction> actions = new ArrayList<>();
		
		Long quantity = Long.valueOf(1);
		if (cartQueryParamsDTO.getCantidad()!= null)
			quantity = (Long.valueOf(cartQueryParamsDTO.getCantidad()));
		
		CartAddLineItemAction cartAddLineItemAction = CartAddLineItemActionBuilder.of()
				.productId(productId)
				.variantId(variantId)
				.quantity(quantity)
				.custom(customFieldsDraft)
				.build();
		actions.add(cartAddLineItemAction);
		
		//en caso de que no actualice las unidades del carrito generar metodo para recalcular las unidades
		CartUpdate cartupdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(actions).build();
		updatedCart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartupdate).executeBlocking().getBody();
		
		String lineItemId = updatedCart.getLineItems().stream()
		        .filter(lineItem -> lineItem.getProductId().equals(productId) && lineItem.getVariant().getId().equals(variantId))
		        .map(LineItem::getId)
		        .findFirst()
		        .orElse(null);
		
		HashMap<String, List<AgrupadorDTO>> infoPackMap = commercetoolsService.getInfoPackMapSession();

		if ((infoPackMap != null) && (infoPackMap.get(cartQueryParamsDTO.getSkuPackMaster()) != null)) {
		    List<AgrupadorDTO> contenidoPack = infoPackMap.get(cartQueryParamsDTO.getSkuPackMaster());
		    //for (AgrupadorDTO elementoPack : contenidoPack) {
		    AgrupadorDTO elementoPack = contenidoPack.get(cartQueryParamsDTO.getStep());
	        //if (!elementoPack.isConfigurado() && (elementoPack.getTipoProductoPack().equals(tipoProducto)) && containsAgrupador(elementoPack, variant)) {
	        	
	            // encontrado, lo marcamos como configurado y almacenamos su sku
	            elementoPack.setConfigurado(true);
	            
	            elementoPack.setIdCarritoOculto(updatedCart.getId());
	            elementoPack.setLineItemIdOculto(lineItemId);	            
	            elementoPack.setSkuProductoConfigurado(variant.getSku());
	            
	            Price price = commercetoolsService.getPriceBycustomerGroup(grupoPrecioCommerce, variant.getPrices());	
	            
	            boolean tieneRecargo = false;
	            if (MyShopUtils.hasAttribute(MyshopConstants.TIENERECARGO, variant.getAttributes()))
	            	tieneRecargo = (Boolean) MyShopUtils.findAttribute(MyshopConstants.TIENERECARGO, variant.getAttributes()).getValue();
	            if (tieneRecargo && MyShopUtils.isSocioPortugal())
	            	elementoPack.setPvoOrigin(commercetoolsService.getPvoOriginForPortugal(variant, grupoPrecioCommerce, price));
	            else
            		elementoPack.setPvoOrigin(MyShopUtils.formatTypedMoney(price.getValue()));
	            
	            elementoPack.setPvoPack(cartQueryParamsDTO.getPvoproductopack());
	            elementoPack.setTipoPrecioPack(cartQueryParamsDTO.getTipoPrecioPack());
	            elementoPack.setNombreProductoConfigurado(nombreProductoConfigurado);
	            
	            String urlImagen = CioneUtils.getURLHttps() + "/" + MyshopConstants.defaultProductLogo(MgnlContext.getLocale());
	            if ( (variant.getImages()!= null) && (variant.getImages().size() > 0) ) 
	            	urlImagen = variant.getImages().get(0).getUrl();
	            elementoPack.setUrlImagen(urlImagen);
	            //break;
	        //}
	          
	        //control packs lentes + monturas
	        contenidoPack.forEach(elemento -> {
	        	if ((elemento.getTipoProductoPackKey()!= null) && (elemento.getTipoProductoPackKey().equals("LENOF")))
	        		elemento.setHabilitar(true);
	        });
		    
		    // Actualizamos la lista en el HashMap para asegurarnos de que los cambios persisten
		    infoPackMap.put(cartQueryParamsDTO.getSkuPackMaster(), contenidoPack);

		    // Guardamos el HashMap modificado de nuevo en la sesión
		    HttpSession session = MgnlContext.getWebContext().getRequest().getSession();
			session.setAttribute(MyshopConstants.ATTR_PACK_SESSION, infoPackMap);
		}
		
		
		return updatedCart;
	}
	

	//comrpueba que el producto seleccionado cumple ademas alguno de los filtros del pack
	private boolean containsAgrupador(AgrupadorDTO elementoPack, ProductVariant variant) {
		List<String> agrupadores = elementoPack.getAgrupadores();
		for (String agrupador: agrupadores) {
			//pueden ser de tres tipos: category= , variants.sku=, variants.attributes.
			if (agrupador.contains("variants.sku=")) {
				if (agrupador.contains(variant.getSku()))
					return true;
				
				/*List<String> listSkus = listSkuFromProduct(product);
				for (String sku: listSkus) {
					if (sku.equals(skuAgrupador))
						return true;
				}*/
				
				
			}else if(agrupador.contains("variants.attributes")) {
				return true;
			}
		}
		return false;
	}
	
	
	//funcion que me devuelve el listado de skus de un producto (master y variantes)
	private List<String> listSkuFromProduct(ProductProjection product) {
		List<String> listSkus = new ArrayList<>();
		listSkus.add(product.getMasterVariant().getSku());
		for (ProductVariant variant: product.getVariants()) {
			listSkus.add(variant.getSku());
		}
		return listSkus;
	}
	
	/*
	 * Damos de alta una linea de pedido solo con el sku y unidades
	 */
	//PROBAR BIEN!! probar si recalcula las unidades del carrito
	private Cart addLineItemsBySku(Cart cart, String sku, Integer cantidad, String refcliente, String coloraudio) {
		Cart updatedCart = null;
		try {
			String grupoPrecioCommerce = commercetoolsService.getGrupoPrecioCommerce();
			ProductProjection product = commercetoolsServiceAux.getProductBySkuFilter(sku, grupoPrecioCommerce);
			
			int plazo = 2;
			double pvodto =0;
			String promoAplicada="";
			String productId="";
			Long variantId=Long.valueOf(1);
			String familia = "";
			//nos devuelve un unico registro
			
			ProductVariant variant = commercetoolsServiceAux.findVariantBySku(product, sku);
			
				//ProductVariant variant = product.findVariantBySku(sku).get();
				productId = product.getId();
				variantId = variant.getId();
				//PlazoEntrega
				String aliasEkon = "";
				String codigoCentral = "";
				//if ((variant.getAttribute("aliasEkon") != null) || ((variant.getAttribute("codigoCentral") != null))) {
				
				if (MyShopUtils.hasAttribute("aliasEkon", variant.getAttributes()))
					aliasEkon = (String) MyShopUtils.findAttribute("aliasEkon", variant.getAttributes()).getValue();
				
				if (MyShopUtils.hasAttribute("codigoCentral", variant.getAttributes()))
					codigoCentral = (String) MyShopUtils.findAttribute("codigoCentral", variant.getAttributes()).getValue();
				
				if (MyShopUtils.hasAttribute(MyshopConstants.PLANTILLA, variant.getAttributes()))
					familia = ((AttributePlainEnumValue) MyShopUtils.findAttribute(MyshopConstants.PLANTILLA, variant.getAttributes()).getValue()).getKey();
				//este else sobra si todos los productos tiene contribuida la familia
				else {
					if (MyShopUtils.hasAttribute("tipoProducto", variant.getAttributes())) {
						String tipoP = ((LocalizedString) MyShopUtils.findAttribute("tipoProducto", variant.getAttributes()).getValue()).get(MyshopConstants.esLocale);
						if (MyShopUtils.getFamiliaProducto(tipoP) != null) 
							familia = MyShopUtils.getFamiliaProducto(tipoP);
						if (MyShopUtils.hasAttribute("aMedida", variant.getAttributes())) {
							boolean aMedida = (Boolean) MyShopUtils.findAttribute("aMedida", variant.getAttributes()).getValue();
							if (aMedida) {
								familia = "audiolab";
							}
						}
					}
				}
				
				if ((familia != null) && !familia.equals("audifonos") && !familia.equals("audiolab")) {
					//float stock = middlewareService.getStock(aliasEkon);
					float stock = commercetoolsService.getStockWithCart(sku, aliasEkon);
					if (stock < cantidad) {
						if (MyShopUtils.hasAttribute("plazoEntregaProveedor", variant.getAttributes())) {
							int plazoEntregaProveedor = ((Long) MyShopUtils.findAttribute("plazoEntregaProveedor", variant.getAttributes()).getValue()).intValue();
							plazo += plazoEntregaProveedor;
						} else {
							plazo += 2;//si no hay plazo de entrega proveedor añadimos 2 dias
						}
					}
				}
			
				boolean hasPromo = false;
				if (MyShopUtils.hasAttribute("tienePromociones", variant.getAttributes()))
					hasPromo = (boolean) MyShopUtils.findAttribute("tienePromociones", variant.getAttributes()).getValue(); //variant.getAttribute("tienePromociones").getValueAsBoolean().booleanValue();
				
				boolean hasRecargo = false;
				if (MyShopUtils.hasAttribute("tieneRecargo", variant.getAttributes()))
					hasRecargo = (boolean) MyShopUtils.findAttribute("tieneRecargo", variant.getAttributes()).getValue();
				
				if (hasPromo || hasRecargo) {
					Price price = commercetoolsService.getPriceBycustomerGroup(grupoPrecioCommerce, variant.getPrices());
					//nos devuelve un unico registro ya que no van a discriminar precios por grupo de usuarios
					//for (Price price : prices) {
					//if (price.getCustomerGroup() == null) { //del listado de precios nos quedamos con el señalado con Any, es decir el que no tiene asignado grupo
						//PvoConDescuento
						VariantDTO variantDto = new VariantDTO();
						variantDto.setGruposPrecio(grupoPrecioCommerce);
						
						if (MyShopUtils.hasAttribute("tipoProducto", variant.getAttributes())) {
							variantDto.setTipoProducto(((LocalizedString) MyShopUtils.findAttribute("tipoProducto", variant.getAttributes()).getValue()).get(MyshopConstants.esLocale));
						}
						variantDto.setCodigoCentral(codigoCentral);
						variantDto.setAliasEkon(aliasEkon);
						
						List<PromocionesDTO> listPromos = promocionesDao.getPromociones(variantDto);
						if (listPromos.size() == 1) { //
							PromocionesDTO promo = listPromos.get(0);
							if (promo.getTipo_descuento() == 1) { //promo por %
								double porcentajeDTO = promocionesDao.getPrecioEscalado(grupoPrecioCommerce, aliasEkon, codigoCentral, cantidad);
								//ver si este precio esta bien, (devuelve el "any")
								//MonetaryAmount pvoOrigen = variant.getPrice().getValue();
								TypedMoney pvoOrigen = price.getValue();
								double dto= ((100-porcentajeDTO)/100);
								double pvoOrigenDouble = MyShopUtils.formatMoneyDouble(pvoOrigen);
								pvodto = pvoOrigenDouble * dto;
    							pvodto = MyShopUtils.round(pvodto, 2);
							} else {
								TypedMoney pvoOrigen = price.getValue();
								pvodto = MyShopUtils.formatMoneyDouble(pvoOrigen) - promo.getValor();
    							pvodto = MyShopUtils.round(pvodto, 2);
							}
							//Promo Aplicada
							promoAplicada = sku + "#" +grupoPrecioCommerce;
						} else if (listPromos.size() < 1){
							//no promo para ese grupo de precios
							hasPromo=false;
						} else if (listPromos.size() > 1){
							double dto = promoescalado(cart, sku, cantidad, listPromos);
							TypedMoney pvoOrigen = price.getValue();
							pvodto = MyShopUtils.formatMoneyDouble(pvoOrigen) * dto;
							pvodto = MyShopUtils.round(pvodto, 2);
						}
					//}
					//}
				}
				//}
			
			
			final Map<String, Object> fields = new HashMap<>();
			if ((refcliente != null) && (!refcliente.isEmpty()))
				fields.put("refCliente", refcliente);
			
			if (pvodto != 0) {
				String strPvoDto = String.valueOf(pvodto);
				TypedMoney pvoDtoMonetary = MyShopUtils.getMonetaryAmount(strPvoDto);
				fields.put("pvoConDescuento", pvoDtoMonetary);
			}
			FieldContainer fieldContainer = null;
			CustomFieldsDraft customFieldsDraft = null;
			TypeResourceIdentifier typeResourceIdentifier = null;
			switch (familia) {
			case "audifonos":
				if ((coloraudio != null) && (!coloraudio.isEmpty()))
					fields.put("colorAudifono", coloraudio);
				typeResourceIdentifier = 
						TypeResourceIdentifier
						.builder()
						.id(cioneEcommerceConectionProvider.getMapTypes().get("lineitem-audifonos").getId())
						.build();
				fieldContainer = FieldContainerBuilder.of().values(fields).build();
				customFieldsDraft = CustomFieldsDraftBuilder.of().type(typeResourceIdentifier).fields(fieldContainer).build();
				break;
			case "audiolab":
				if ((coloraudio != null) && (!coloraudio.isEmpty()))
					fields.put("colorAudifono", coloraudio);
				typeResourceIdentifier = 
						TypeResourceIdentifier
						.builder()
						.id(cioneEcommerceConectionProvider.getMapTypes().get("lineitem-audiolab").getId())
						.build();
				fieldContainer = FieldContainerBuilder.of().values(fields).build();
				customFieldsDraft = CustomFieldsDraftBuilder.of().type(typeResourceIdentifier).fields(fieldContainer).build();
				break;
			default:
				if (!promoAplicada.isEmpty()) 
					fields.put("promoAplicada", promoAplicada);
				fields.put("plazoEntrega", Integer.valueOf(plazo));
				typeResourceIdentifier = 
						TypeResourceIdentifier
						.builder()
						.id(cioneEcommerceConectionProvider.getMapTypes().get("customFields-forCartLines").getId())
						.build();
				fieldContainer = FieldContainerBuilder.of().values(fields).build();
				customFieldsDraft = CustomFieldsDraftBuilder.of().type(typeResourceIdentifier).fields(fieldContainer).build();
				break;
			}
			
			CartAddLineItemAction cartAddLineItemAction = CartAddLineItemActionBuilder.of()
					.productId(productId)
					.variantId(variantId)
					.quantity(Long.valueOf(cantidad))
					.custom(customFieldsDraft)
					.build();
			
			//en caso de que no actualice las unidades del carrito generar metodo para recalcular las unidades
			CartUpdate cartupdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(cartAddLineItemAction).build();
			updatedCart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartupdate).executeBlocking().getBody();
			
//			final List<UpdateAction<Cart>> updateActions =
//					Arrays.asList(AddLineItem.of(lineItemDraft), Recalculate.of().withUpdateProductData(true));
//			updatedCart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(cart, updateActions));
			
			
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		return updatedCart;
	}
	
	public double promoescalado(Cart cart, String sku, Integer cantidad, List<PromocionesDTO> listPromos) throws NamingException  {
		double dtoini = listPromos.get(0).getValor();
		double dto= ((100-dtoini)/100);
		List<LineItem> listItems = cart.getLineItems();
		for (LineItem item: listItems) {
			if (item.getVariant().getSku().equals(sku)) {
				String grupoPrecio = commercetoolsService.getGrupoPrecioCommerce();
				String aliasEkon = "";
				if (MyShopUtils.hasAttribute("aliasEkon", item.getVariant().getAttributes()))
					aliasEkon = (String) MyShopUtils.findAttribute("aliasEkon", item.getVariant().getAttributes()).getValue();
				
				String codigoCentral = "";
				if (MyShopUtils.hasAttribute("codigoCentral", item.getVariant().getAttributes()))
					codigoCentral = (String) MyShopUtils.findAttribute("codigoCentral", item.getVariant().getAttributes()).getValue();
				
				String lineItemId = item.getId();
				Integer ite = (int) (long)item.getQuantity();
				Integer cantidadTotal = ite + cantidad;
				double porcentajeDTO = promocionesDao.getPrecioEscalado(grupoPrecio, aliasEkon, codigoCentral, cantidadTotal.intValue());
				//ver si este precio esta bien, (devuelve el "any")
				
				Price price = commercetoolsService.getPriceBycustomerGroup(grupoPrecio, item.getVariant().getPrices());
			
				TypedMoney pvoOrigen = price.getValue();
				
				dto= ((100-porcentajeDTO)/100);
				double pvodto = MyShopUtils.formatMoneyDouble(pvoOrigen) * dto;
				pvodto = MyShopUtils.round(pvodto, 2);
				
				TypedMoney nuevoPvo = MyShopUtils.getMonetaryAmount(pvodto);
				if (nuevoPvo != null) {
					
					//PROBAR BIEN!!
					//FieldContainer field = FieldContainerBuilder.of().addValue("pvoConDescuento", nuevoPvo).build();
					CartSetLineItemCustomFieldAction setcustomfield = CartSetLineItemCustomFieldActionBuilder.of().lineItemId(lineItemId).name("pvoConDescuento").value(nuevoPvo).build();
					CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(setcustomfield).build();
					cart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartUpdate).executeBlocking().getBody().get();	
							

					//actualizar precio
//					cart = cioneEcommerceConectionProvider.getClient()
//			    			.executeBlocking(CartUpdateCommand.of(cart, SetLineItemCustomField.ofObject("pvoConDescuento", nuevoPvo, lineItemId)));
				}
					
				
				
					
				
				//nos devuelve un unico registro ya que no van a discriminar precios por grupo de usuarios
//				for (Price price : prices) {
//					if (price.getCustomerGroup() == null) {
//						MonetaryAmount pvoOrigen = price.getValue();
//						
//						dto= ((100-porcentajeDTO)/100);
//						double pvodto = pvoOrigen.getNumber().doubleValue() * dto;
//						pvodto = MyShopUtils.round(pvodto, 2);
//						
//						MonetaryAmount nuevoPvo = MyShopUtils.getMonetaryAmount(pvodto);
//						if (nuevoPvo != null) 
//						cart = cioneEcommerceConectionProvider.getClient()
//				    			.executeBlocking(CartUpdateCommand.of(cart, SetLineItemCustomField.ofObject("pvoConDescuento", nuevoPvo, lineItemId)));
//					}
//				}
			}
		}
		return dto;
	}
	
	public Response addCart(CartParamsDTO cartQueryParamsDTO) {
		Customer customer = commercetoolsService.getCustomerSDK();
		if ((cartQueryParamsDTO.getSustitutiveProduct() != null)
				&& (!cartQueryParamsDTO.getSustitutiveProduct().isEmpty())) {
			//al agregar un porducto sustitutivo modificamos todos los valores que calculamos en el front
			cartQueryParamsDTO.setSku(cartQueryParamsDTO.getSustitutiveProduct());
			ProductProjection psustitutive = 
				commercetoolsService.getProductBysku(cartQueryParamsDTO.getSustitutiveProduct(), commercetoolsService.getGrupoPrecioCommerce());
			ProductVariant variantSus = commercetoolsServiceAux.findVariantBySku(psustitutive, cartQueryParamsDTO.getSustitutiveProduct());
			//ProductVariant variantSus = psustitutive.findVariantBySku(cartQueryParamsDTO.getSustitutiveProduct()).get();
			
			cartQueryParamsDTO.setProductId(psustitutive.getId());
			cartQueryParamsDTO.setVariantId(variantSus.getId().intValue());
			String aliasEkon = null;
			
			if (MyShopUtils.hasAttribute("aliasEkon", variantSus.getAttributes())) 
				aliasEkon = (String) MyShopUtils.findAttribute("aliasEkon", variantSus.getAttributes()).getValue();
			
			int stock = commercetoolsService.getStockWithCart(cartQueryParamsDTO.getSustitutiveProduct(), aliasEkon);
			cartQueryParamsDTO.setStock(stock);
			
			if (MyShopUtils.hasAttribute("plazoEntregaProveedor", variantSus.getAttributes())) 
				cartQueryParamsDTO.setPlazoEntregaProveedor(((Long) MyShopUtils.findAttribute("plazoEntregaProveedor", variantSus.getAttributes()).getValue()).toString());
			else
				cartQueryParamsDTO.setPlazoEntregaProveedor("2"); //si no tiene plazo de entrega marcamos un minimo de 2 dias
			if (stock >= cartQueryParamsDTO.getCantidad()) {
				cartQueryParamsDTO.setPlazoEntrega("2");
			} else {
				int plazo =Integer.valueOf(cartQueryParamsDTO.getPlazoEntregaProveedor()) +2;
				cartQueryParamsDTO.setPlazoEntrega(String.valueOf(plazo));
			}
		}
		if (customer != null) {
			cartQueryParamsDTO.setCustomerGroup(customer.getCustomerGroup().getId());
		    Cart cart = getCart(customer.getId());
		    if (cart == null) { //carrito vacio
		    	cart = createCart(customer, MyshopConstants.deleteDaysAfterLastModification);
		    	if (cart != null)
			    	cart = addLineItems(cart, cartQueryParamsDTO);
			    else
			    	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error addCart/addLineItems").build();
			} else {
				//comprobar si ya habian unidades de ese producto en el carrito, 
				//si es asi lo actualizamos (borramos y creamos despues) salvo en el caso de que tenga distinta referencia de cliente
				
				List<LineItem> listItems = cart.getLineItems();
				for (LineItem item: listItems) {
					if (item.getVariant().getSku().equals(cartQueryParamsDTO.getSku())) {
						if ((cartQueryParamsDTO.getTipoPromocion() != null) && (cartQueryParamsDTO.getTipoPromocion().equals("escalado"))) {
							TypedMoney nuevoPvo = MyShopUtils.getMonetaryAmount(cartQueryParamsDTO.getPvoConDescuento());
							//MonetaryAmount nuevoPvo = MyShopUtils.getMonetaryAmount(cartQueryParamsDTO.getPvoConDescuento());
							if (nuevoPvo != null) {
								//PROBAR BIEN!!
								//FieldContainer field = FieldContainerBuilder.of().addValue("pvoConDescuento", nuevoPvo).build();
								CartSetLineItemCustomFieldAction setcustomfield = CartSetLineItemCustomFieldActionBuilder.of().lineItemId(item.getId()).name("pvoConDescuento").value(nuevoPvo).build();
								CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(setcustomfield).build();
								cart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartUpdate).executeBlocking().getBody().get();	
//								cart = cioneEcommerceConectionProvider.getClient()
//						    			.executeBlocking(CartUpdateCommand.of(cart, SetLineItemCustomField.ofObject("pvoConDescuento", nuevoPvo, item.getId())));
							}
						}
							
					}
				}
				cart = addLineItems(cart, cartQueryParamsDTO);
					
			}
		    
		    if (cart != null) {
		    	try {
					String cartResponse = getBackwardCompatibilityCartAsString(cart);
					return Response.ok(cartResponse).build();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					return Response.ok(cart).build();
				}
				
		    } else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error addCart/addLineItems").build();
		    }
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error addCart/addLineItems, customer doesn't exist").build();
		}
	}
	
	/*private Cart getCartById(String id) {
		return cioneEcommerceConectionProvider.getClient().executeBlocking(CartByIdGet.of(id));
	}*/
	//PROBAR BIEN!! SOBRETODO SI SE ACTUALIZAN LAS UNIDADES
	public Response addCartCustomLineReturn(CartParamsDevolucionDTO cartQueryParamsDTO) {
		Response response = null;
		try {
			Customer customer = commercetoolsService.getCustomerSDK();
			Map<String, Object> fieldsCart = new HashMap<>();
			fieldsCart.put(MyshopConstants.CARTTYPE, "payment");
			Cart cart = createCart(customer, Long.valueOf(1), fieldsCart);
			TaxCategory taxCategory = commercetoolsService.getTaxCategory();
			for (DevolucionDTO devolucion: cartQueryParamsDTO.getDevoluciones() ) {
//				MonetaryAmount pvoDtoMonetary = Monetary.getDefaultAmountFactory()
//						.setCurrency("EUR")
//						.setNumber(0)
//						.create();
				TypedMoney pvoDtoMonetary = MyShopUtils.getMonetaryAmount(0);
				long unidades = Double.valueOf(devolucion.getUnidades()).longValue();
				final Map<String, Object> fields = new HashMap<>();
				fields.put("aliasEkon", devolucion.getAliasEkon());
				fields.put("albaran", devolucion.getKeyAlbaran());
				fields.put("nLinAlbaran", Integer.valueOf(devolucion.getNlinAlbaran()));
				if ((devolucion.getPvo() != null) && (devolucion.getUnidadesTotalesLinea() != null)) {
					Double pvo = Double.valueOf(devolucion.getPvo().replace(',', '.'))/Integer.valueOf(devolucion.getUnidadesTotalesLinea()) ;
					fields.put("importeUnitario", MyShopUtils.getMonetaryAmount(String.valueOf(pvo)));
					//String totalPrice = String.valueOf(pvo * unidades);
					String totalPrice = String.valueOf(pvo);
					pvoDtoMonetary = MyShopUtils.getMonetaryAmount(totalPrice);
				}
				fields.put("motivo", devolucion.getMotivo());
				if ((devolucion.getObservaciones() != null) && (!devolucion.getObservaciones().isEmpty()))
					fields.put("observaciones", devolucion.getObservaciones());
				//fields.put("descripcion", devolucion.getDescripcion());
				
				LocalizedString name = LocalizedString.of(MyshopConstants.esLocale, devolucion.getDescripcion());
				String slug = devolucion.getDescripcion().replaceAll(" ", "-").toLowerCase()+"-"+devolucion.getKeyAlbaran()+"-"+devolucion.getNlinAlbaran();
				
				String customTypeKey = "customlineitem-abonos";
				TypeResourceIdentifier customType = TypeResourceIdentifierBuilder.of().key(customTypeKey).build();
				FieldContainer fieldContainer = FieldContainerBuilder.of().values(fields).build();
				CustomFieldsDraft customFieldDraft = CustomFieldsDraftBuilder.of().type(customType).fields(fieldContainer).build();
				final Long centAmount = pvoDtoMonetary.getCentAmount();
				CartAddCustomLineItemAction customLineItem = 
						CartAddCustomLineItemActionBuilder.of()
						.name(name)
						.slug(slug)
						.money(moneyBuilder -> moneyBuilder.currencyCode("EUR").centAmount(centAmount))
						//.money(money)
						.taxCategory(taxcategory -> taxcategory.id(taxCategory.getId()))
						.custom(customFieldDraft)
						.quantity(Long.valueOf(unidades))
						.build();
				CartUpdate cartupdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(customLineItem).build();
				cart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartupdate).executeBlocking().getBody();
				
//				final CustomFieldsDraft custom = CustomFieldsDraft.ofTypeKeyAndObjects("customlineitem-abonos", fields);
//				CustomLineItemDraft customLine = CustomLineItemDraft.of(name, slug, pvoDtoMonetary, taxCategory, unidades, custom);
//				final AddCustomLineItem addCustomLineItemAction = AddCustomLineItem.of(customLine);
//				final List<UpdateAction<Cart>> updateActions =
//						Arrays.asList(addCustomLineItemAction, SetCustomerId.of(customer.getId()), Recalculate.of().withUpdateProductData(true));
//			    final Cart updatedCart = cioneEcommerceConectionProvider.getClient()
//			    		.executeBlocking(CartUpdateCommand.of(cart, updateActions));
//			    //response = Response.ok(updatedCart).build();
//			    cart = updatedCart;
			}
			
			response = abonoOrder(cart, customer);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
		
		return response;
	}
	
	
	//PROBAR BIEN!!!
	public Response abonoOrder(Cart cart, Customer customer) {
		Response response = null;
		UserERPCioneDTO userERP = middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
	    
		List<CartUpdateAction> actions = new ArrayList<>();
		//List<UpdateAction> actions = new ArrayList<>();
		String codusuario = CioneUtils.getIdCurrentClient();
		//String codEnvio = customer.getCustom().getFields().getCodDirDefault();	
		String codEnvio = (String) customer.getCustom().getFields().values().get("codDirDefault");
		
		DireccionesDTO direcciones = middlewareService.getDirecciones(CioneUtils.getIdCurrentClientERP());
		DireccionDTO direccionUsuario = new DireccionDTO();
		
		if (!direcciones.getTransportes().isEmpty()) {
			direccionUsuario = direcciones.getTransportes().get(0);//la primera sera la direccion por defecto
			codEnvio = direccionUsuario.getId_localizacion(); 
					
			//si es empleado utilizamos la direccion de envio seteada en commerceTools
			if (CioneUtils.isEmpleado(codusuario)) {
				codEnvio = (String) customer.getCustom().getFields().values().get("codDirDefault");  //seteamos la direccion de envio seteada en commerceTools
				boolean encontrada = false; //flag por si un empleado tiene desctualizada el codigo de direccion por defecto
				for (DireccionDTO direccion : direcciones.getTransportes()) {
					if (direccion.getId_localizacion().equals(codEnvio)) {
						encontrada = true;
						direccionUsuario = direccion;
					}
				}
				if (!encontrada)
					codEnvio = direccionUsuario.getId_localizacion(); 
			}
			
			
		} else {
			log.error("El usuario no tiene direccion de envio en el servicio /myshopdatostransporte");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("El usuario no tiene direccion de envio en el servicio /myshopdatostransporte").build();
		}
		
	    if(cart != null) {
	    	//Address dir = AddressBuilder.of(CountryCode.ES).build();
	    	Address dir = AddressBuilder.of()
	    			.firstName(direccionUsuario.getNombre())
	    			.streetName(direccionUsuario.getDireccion())
	    			.city(direccionUsuario.getLocalidad())
	    			.region(direccionUsuario.getProvincia())
	    			.postalCode(direccionUsuario.getCod_postal())
	    			.email(userERP.getEmail())
	    			.country("ES").build();
	    	
	    	
	    	if (direccionUsuario.getPais().equals("Portugal")) {
	    		dir.setCountry("PT"); 
	    	}
	    	
	    	//PROBAR QUE SE AÑADE, ES POSIBLE QUE SE TENGA QUE CAMBIAR POR CartSetShippingAddressAction
	    	
	    	CartSetShippingAddressAction cartSetShippingAddressAction  = CartSetShippingAddressActionBuilder.of().address(dir).build();
	    	actions.add(cartSetShippingAddressAction);
	    	
	    	//PROBAR QUE SE AÑADE
	    	String idShippingMethod = cioneEcommerceConectionProvider.getMapShippingMethods().get("rma").getId();
	    	ShippingMethodResourceIdentifier shippingMethod = ShippingMethodResourceIdentifierBuilder.of().id(idShippingMethod).build();
	    	CartSetShippingMethodAction cartSetShippingMethodAction = CartSetShippingMethodActionBuilder.of().shippingMethod(shippingMethod).build();
	    	actions.add(cartSetShippingMethodAction);

			Map<String, Object> obj = new HashMap<>();
			obj.put("customerNumber", codusuario);
			obj.put("codSocio", CioneUtils.getIdCurrentClientERP());
			//obj.put("pvoFinalAcumulado", pvoFinalAcumulado);
			obj.put("RegisteredBy", MgnlContext.getUser().getName());//seteamos el usuario que lo ha registrado para saber si se realiza mediante simulacion de usuario
			obj.put("codDirEnvio", codEnvio);
			String rma = generateRMA();
			if (!rma.isEmpty())
				obj.put("numRMA", rma);
			else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al generar el codigo de devolución").build();
			}
			
			FieldContainer fieldContainer = FieldContainerBuilder.of().values(obj).build();
			TypeResourceIdentifier customType = TypeResourceIdentifierBuilder.of().key("customFields-forOrders").build();
			CartSetCustomTypeAction cartSetCustomTypeAction = CartSetCustomTypeActionBuilder.of()
					.type(customType)
					.fields(fieldContainer)
					.build();
			
			actions.add(cartSetCustomTypeAction);
			
			CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(actions).build();
			ApiHttpResponse<Cart> responseUpdatedCart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartUpdate).executeBlocking();
			
			if (responseUpdatedCart.getStatusCode() != 200) {
				log.error("ERROR AL ACTUALIZAR EL CARRITO", responseUpdatedCart.getMessage());
				response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating order from cart").build();
			} else {
				cart = responseUpdatedCart.getBody().get();
				String orderNumber = generateOrderNumber("MYSAB");
				CartResourceIdentifier cartId = CartResourceIdentifierBuilder.of().id(cart.getId()).key(cart.getKey()).build();
				OrderFromCartDraft orderFromCartDraft = OrderFromCartDraftBuilder.of()
		                .cart(cartId)
		                .orderNumber(orderNumber)
		                .version(cart.getVersion())
		                .build();
				try {
					Order order = cioneEcommerceConectionProvider.getApiRoot().orders().post(orderFromCartDraft).executeBlocking().getBody();
					String orderResponse = getBackwardCompatibilityOrderAsString(order);
					response = Response.ok(orderResponse).build();
				} catch (Exception ex) {
			    	//reintentamos de forma asincrona por si el servicio no esta disponible
			    	log.error(ex.getMessage(), ex);
			    	String subject = "Error al setear orderNumber";
			    	String mailto = cioneEcommerceConectionProvider.getConfigService().getConfig().getAuthTestEmail();
			    	String texto = "Se ha producido un error al setear el orderNumer: " + orderNumber + " de la devolucion al pedido del socio: " + CioneUtils.getIdCurrentClient();
			    	texto += "</br>";
			    	texto += "</br>";
			    	texto += "ERROR:";
			    	texto += "</br>";
			    	texto += ex.getMessage();
			    	try {
						emailService.sendEmail(subject, texto, mailto);
					} catch (CioneException e) {
						log.error("ERROR en el envio de mail");
					}
			    	
			    	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating order from cart").build();
				}
				
			}
	
		    //Cart updatedCart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(cart, actions));
			
			/*String orderNumber = generateOrderNumber("MYSAB");
			OrderFromCartDraft orderFromCartDraft = OrderFromCartDraftBuilder.of()
	                .id(cart.getId())
	                .version(cart.getVersion())
	                .build();
			cioneEcommerceConectionProvider.getApiRoot().orders().withOrderNumber(orderNumber).post(orderFromCartDraft)
		    Order order = cioneEcommerceConectionProvider.getClient().executeBlocking(OrderFromCartCreateCommand.of(updatedCart));
		    
		    
		    
		    
		    Order orderCheck = cioneEcommerceConectionProvider.getClient().executeBlocking(OrderByOrderNumberGet.of(orderNumber));
		    if (orderCheck != null) {
		    	log.info("EXISTE UN PEDIDO " + orderCheck.getId() + " con el orderNumber " + orderNumber);
		    	orderNumber = generateOrderNumber("MYSAB");
		    	log.info("GENERADO orderNumber " + orderNumber);
		    }
		    
		    //boolean orderNumberSet = false;
		    try {
		    	order = cioneEcommerceConectionProvider.getClient().executeBlocking(OrderUpdateCommand.of(order, SetOrderNumber.of(orderNumber)));
		    	//orderNumberSet = true;
		    } catch (Exception ex) {
		    	
		    	//reintentamos de forma asincrona por si el servicio no esta disponible
		    	log.error(ex.getMessage(), ex);
		    	String subject = "Error al setear orderNumber";
		    	String mailto = cioneEcommerceConectionProvider.getConfigService().getConfig().getAuthTestEmail();
		    	String texto = "Se ha producido un error al setear el orderNumer: " + orderNumber + " de la devolucion al pedido del socio: " + CioneUtils.getIdCurrentClient() + " del pedido: " + order.getId();
		    	texto += "</br>";
		    	texto += "</br>";
		    	texto += "ERROR:";
		    	texto += "</br>";
		    	texto += ex.getMessage();
		    	try {
					emailService.sendEmail(subject, texto, mailto);
				} catch (CioneException e) {
					log.error("ERROR en el envio de mail");
				}
		    }
		    
//		    while(!orderNumberSet) {
//		    	randomString = String.format("%03d", secureRandom.nextInt());
//	    		orderNumber = orderNumber.substring(0, orderNumber.length() - 3) + randomString.substring(randomString.length() - 3);
//	    		try {
//	    			order = cioneEcommerceConectionProvider.getClient().executeBlocking(OrderUpdateCommand.of(order, SetOrderNumber.of(orderNumber)));
//			    	orderNumberSet = true;
//			    } catch (Exception ex) {}
//		    }*/
//		    
		    /*JSONObject jsonRes = new JSONObject();
		    jsonRes.put("coddevolucion", rma);
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.OK).build();*/
		    
	    	
	    } else
	    	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating order from cart").build();
	    
	    return response;
	}
	
	/*private void sendTemplateEmail(String subject, String email, Map<String, Object> templateValues) throws CioneException {
		
		@SuppressWarnings("deprecation")
		final MgnlMailFactory mailFactory = ((MailModule) ModuleRegistry.Factory.getInstance().getModuleInstance("mail")).getFactory();
		
        try {
        	log.debug("SEND MAIL TO " + email);
            final MgnlEmail mail = mailFactory.getEmailFromType(templateValues, "freemarker");
            mail.setFrom(cioneEcommerceConectionProvider.getConfigService().getConfig().getAuthSenderEmail());
            mail.setToList(email);
            mail.setSubject(subject);            
            mail.setBodyFromResourceFile();  
            
            mailFactory.getEmailHandler().prepareAndSendMail(mail);
            
        } catch (Exception e) {
        	log.error(e.getMessage(),e);        	
        	throw new CioneException("Se ha producido un error en el envío del email"); 
        }	
		
	}*/
	
	private String generateRMA() {
		String rma = "";
		try {
			String cod = CioneUtils.getIdCurrentClient();
			CustomerCT customer = commercetoolsService.getCustomer();
			
			List<String> querys = new ArrayList<>();
			querys.add("customerId=\""+ customer.getId() + "\"");
			querys.add("custom(fields(numRMA is defined))");
			
			List<Order> pedidos = cioneEcommerceConectionProvider.getApiRoot().orders().get().withWhere(querys).withSort("createdAt desc").executeBlocking().getBody().getResults();
			
			
			/*QueryPredicate<Order> predicate1 = OrderQueryModel.of().customerId().is(customer.getId());
			QueryPredicate<Order> predicate2 = OrderQueryModel.of().custom().fields().ofString("numRMA").isPresent();
			
			
			Query<Order> queryOrder = OrderQuery.of().withPredicates(predicate1.and(predicate2)).withSort(QuerySort.of("createdAt desc"));
			CompletionStage<PagedQueryResult<Order>> order_query = cioneEcommerceConectionProvider.getClient().execute(queryOrder);
			PagedQueryResult<Order> actualOrders = order_query.toCompletableFuture().join();
			List<Order> pedidos = actualOrders.getResults();*/
			if (pedidos.isEmpty())
				rma = cod+"-2";
			else {
				String rma_str = (String) pedidos.get(0).getCustom().getFields().values().get("numRMA");
				//String rma_str = pedidos.get(0).getCustom().getFieldAsString("numRMA");
				Integer next = Integer.valueOf(rma_str.substring(cod.length()+1, rma_str.length()));
				next++;
				rma = cod+"-" + next;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return rma;	
	}
	

	/*
	 * Damos de alta una linea de pedido con un CartParamsDTO completo
	 */
	//PROBAR BIEN
	private Cart addLineItems(Cart cart, CartParamsDTO cartQueryParamsDTO) {
		
		Cart updatedCart = null;
		String grupoPrecio = commercetoolsService.getGrupoPrecioCommerce();
		try {
			if (cartQueryParamsDTO.getCantidad() == null)
				cartQueryParamsDTO.setCantidad(1);
			if (cartQueryParamsDTO.getPvoConDescuento() != null) {
				String daux = cartQueryParamsDTO.getPvoConDescuento().replace(',', '.');
				cartQueryParamsDTO.setPvoConDescuentoMonetary(MyShopUtils.getMoney(daux));
				cartQueryParamsDTO.setPromoAplicada(cartQueryParamsDTO.getSku() + "#" + grupoPrecio);
				
			}
			if (cartQueryParamsDTO.getPvoPackUD() != null) {
				String daux = cartQueryParamsDTO.getPvoPackUD().replace(',', '.');
				cartQueryParamsDTO.setPvoConDescuentoMonetary(MyShopUtils.getMoney(daux));
				cartQueryParamsDTO.setPromoAplicada(cartQueryParamsDTO.getSkuPackPadre() + "#" + grupoPrecio);
			}
			
			final Map<String, Object> fields = new HashMap<>();
			final Map<String, Object> fieldsAudioLab = new HashMap<>();
			if ((cartQueryParamsDTO.getRefTaller() != null) && (!cartQueryParamsDTO.getRefTaller().isEmpty()) )
				fields.put("refTaller", cartQueryParamsDTO.getRefTaller());
			/*else {
				if (cartQueryParamsDTO.getFamiliaProducto()!= null && cartQueryParamsDTO.getFamiliaProducto().equals("contactologia")) {
					fields.put("refTaller", CioneUtils.generateRef());
				}
			}*/
			if ((cartQueryParamsDTO.getRefCliente() != null) && (!cartQueryParamsDTO.getRefCliente().isEmpty()))
				fields.put("refCliente", cartQueryParamsDTO.getRefCliente());
			if (cartQueryParamsDTO.getPromoAplicada() != null && !cartQueryParamsDTO.getFamiliaProducto().equals("audifonos")) 
				fields.put("promoAplicada", cartQueryParamsDTO.getPromoAplicada());
			
			//calculamos el plazo entrega salvo para los casos que no tienen stock 
			if ((cartQueryParamsDTO.getFamiliaProducto() != null) 
					&& (!cartQueryParamsDTO.getFamiliaProducto().equals("audifonos"))
					&& (!cartQueryParamsDTO.getFamiliaProducto().equals("audiolab"))) {
				if (cartQueryParamsDTO.getPlazoEntrega() != null)
					fields.put("plazoEntrega", Integer.valueOf(cartQueryParamsDTO.getPlazoEntrega()));
				else {
					if (cartQueryParamsDTO.getLcsku() != null) { //lentes de contacto
						int stock = commercetoolsService.getStockWithCart(cartQueryParamsDTO.getLcsku(), null);
						if (cartQueryParamsDTO.getCantidad() <= stock) {
							fields.put("plazoEntrega", 2);
						} else {
							ProductVariant pv = 
									commercetoolsService.getProductVariantBysku(cartQueryParamsDTO.getSku(), grupoPrecio);
							//if ((pv != null) && (pv.getAttribute("plazoEntregaProveedor") != null)) {
							if ((pv != null) && (MyShopUtils.hasAttribute("plazoEntregaProveedor", pv.getAttributes()))) {
								fields.put("plazoEntrega", 2 + ((Long) MyShopUtils.findAttribute("plazoEntregaProveedor", pv.getAttributes()).getValue()).intValue());
							}
						}
					} else {
						ProductVariant pv = commercetoolsService.getProductVariantBysku(cartQueryParamsDTO.getSku(), grupoPrecio);
						if ( (pv != null) && (MyShopUtils.hasAttribute("aliasEkon", pv.getAttributes())) ) {
							int stock = commercetoolsService.getStockWithCart(cartQueryParamsDTO.getLcsku(), 
								(String) MyShopUtils.findAttribute("aliasEkon", pv.getAttributes()).getValue());
							if (cartQueryParamsDTO.getCantidad() <= stock)
								fields.put("plazoEntrega", 2);
							else {
								if ((pv != null) && (MyShopUtils.hasAttribute("plazoEntregaProveedor", pv.getAttributes()))) {
									fields.put("plazoEntrega", 2 + ((Long) MyShopUtils.findAttribute("plazoEntregaProveedor", pv.getAttributes()).getValue()).intValue());
								}
							}
								
						}
						
					}
				}
			}
			
			//if (cartQueryParamsDTO.getPlazoEntrega() != null) 
				//fields.put("plazoEntrega", Integer.valueOf(cartQueryParamsDTO.getPlazoEntrega()));
			if (cartQueryParamsDTO.getRefPackPromos() != null) 
				fields.put("refPackPromos", cartQueryParamsDTO.getRefPackPromos());
			if (cartQueryParamsDTO.getPackName() != null)
				fields.put("descPackPromos", cartQueryParamsDTO.getPackName());
			if (cartQueryParamsDTO.getPvoConDescuentoMonetary() != null) 
				fields.put("pvoConDescuento", cartQueryParamsDTO.getPvoConDescuentoMonetary());
			if (cartQueryParamsDTO.getLcojo() != null) 
				fields.put("LC_ojo", cartQueryParamsDTO.getLcojo());
			if (cartQueryParamsDTO.getLcdiseno() != null) 
				fields.put("LC_diseno", cartQueryParamsDTO.getLcdiseno());
			if (cartQueryParamsDTO.getLcsku() != null) 
				fields.put("LC_sku", cartQueryParamsDTO.getLcsku());
			if (cartQueryParamsDTO.getLcesfera() != null) 
				fields.put("LC_esfera", cartQueryParamsDTO.getLcesfera());
			if (cartQueryParamsDTO.getLccilindro() != null) 
				fields.put("LC_cilindro", cartQueryParamsDTO.getLccilindro());
			if (cartQueryParamsDTO.getLceje() != null) 
				fields.put("LC_eje", cartQueryParamsDTO.getLceje());
			if (cartQueryParamsDTO.getLcdiametro() != null) 
				fields.put("LC_diametro", cartQueryParamsDTO.getLcdiametro());
			if (cartQueryParamsDTO.getLccurvaBase() != null) 
				fields.put("LC_curvaBase", cartQueryParamsDTO.getLccurvaBase());
			if (cartQueryParamsDTO.getLccolor() != null) 
				fields.put("LC_color", cartQueryParamsDTO.getLccolor());
			if (cartQueryParamsDTO.getLcdesccolor() != null) 
				fields.put("LC_descColor", cartQueryParamsDTO.getLcdesccolor());
			if (cartQueryParamsDTO.getLcadicion() != null) 
				fields.put("LC_adicion", cartQueryParamsDTO.getLcadicion());
			
			if ((cartQueryParamsDTO.getFamiliaProducto() != null) 
				&& (cartQueryParamsDTO.getFamiliaProducto().equals("audiolab"))) {
				if ((cartQueryParamsDTO.getRefTaller() != null) && (!cartQueryParamsDTO.getRefTaller().isEmpty()) )
					fieldsAudioLab.put("refTaller", cartQueryParamsDTO.getRefTaller());
				String fileName = CioneUtils.getIdCurrentClientERP() + "-" + cartQueryParamsDTO.getRefTaller() + "-audiolab.pdf";
				fieldsAudioLab.put("url_pdf",fileName);
				if ((cartQueryParamsDTO.getSide() != null) && (!cartQueryParamsDTO.getSide().isEmpty()))
					fieldsAudioLab.put("lado", cartQueryParamsDTO.getSide());
				if ((cartQueryParamsDTO.getReferencia() != null) && (!cartQueryParamsDTO.getReferencia().isEmpty()))
					fieldsAudioLab.put("refCliente", cartQueryParamsDTO.getReferencia());
				if ((cartQueryParamsDTO.getGabinete() != null) && (!cartQueryParamsDTO.getGabinete().isEmpty()))
					fieldsAudioLab.put("inf_gabinete", cartQueryParamsDTO.getGabinete());
				if ((cartQueryParamsDTO.getFormato() != null) && (!cartQueryParamsDTO.getFormato().isEmpty()))
					fieldsAudioLab.put("formato", cartQueryParamsDTO.getFormato());
				
				if ((cartQueryParamsDTO.getRpotencia() != null) && (!cartQueryParamsDTO.getRpotencia().isEmpty()))
					fieldsAudioLab.put("potencia_R", cartQueryParamsDTO.getRpotencia());
				if ((cartQueryParamsDTO.getLpotencia() != null) && (!cartQueryParamsDTO.getLpotencia().isEmpty()))
					fieldsAudioLab.put("potencia_L", cartQueryParamsDTO.getLpotencia());
				
				if ((cartQueryParamsDTO.getLlon() != null) && (!cartQueryParamsDTO.getLlon().isEmpty()))
					fieldsAudioLab.put("longitud_canal_L", cartQueryParamsDTO.getLlon());
				if ((cartQueryParamsDTO.getRlon() != null) && (!cartQueryParamsDTO.getRlon().isEmpty()))
					fieldsAudioLab.put("longitud_canal_R", cartQueryParamsDTO.getRlon());
				if ((cartQueryParamsDTO.getRfiltro() != null) && (!cartQueryParamsDTO.getRfiltro().isEmpty()))
					fieldsAudioLab.put("filtro_cerumen_R", cartQueryParamsDTO.getRfiltro());
				if ((cartQueryParamsDTO.getLfiltro() != null) && (!cartQueryParamsDTO.getLfiltro().isEmpty()))
					fieldsAudioLab.put("filtro_cerumen_L", cartQueryParamsDTO.getLfiltro());
				
				fieldsAudioLab.put("telebobina_L", cartQueryParamsDTO.isLtelebobina());
				fieldsAudioLab.put("telebobina_R", cartQueryParamsDTO.isRtelebobina());
				
				fieldsAudioLab.put("pulsador_L", cartQueryParamsDTO.isLpulsador());
				fieldsAudioLab.put("control_L", cartQueryParamsDTO.isLvolumen());
				fieldsAudioLab.put("pulsador_R", cartQueryParamsDTO.isRpulsador());
				fieldsAudioLab.put("control_R", cartQueryParamsDTO.isRvolumen());
				
				
				fieldsAudioLab.put("hilo_R", cartQueryParamsDTO.isRhilo());
				fieldsAudioLab.put("hilo_L", cartQueryParamsDTO.isLhilo());
				if ((cartQueryParamsDTO.getRtipoventing() != null) && (!cartQueryParamsDTO.getRtipoventing().isEmpty()))
					fieldsAudioLab.put("tipo_venting_R", cartQueryParamsDTO.getRtipoventing());
				if ((cartQueryParamsDTO.getLtipoventing() != null) && (!cartQueryParamsDTO.getLtipoventing().isEmpty()))
					fieldsAudioLab.put("tipo_venting_L", cartQueryParamsDTO.getLtipoventing());
				if ((cartQueryParamsDTO.getRmodventing() != null) && (!cartQueryParamsDTO.getRmodventing().isEmpty()))
					fieldsAudioLab.put("mod_venting_R", cartQueryParamsDTO.getRmodventing());
				if ((cartQueryParamsDTO.getLmodventing() != null) && (!cartQueryParamsDTO.getLmodventing().isEmpty()))
					fieldsAudioLab.put("mod_venting_L", cartQueryParamsDTO.getLmodventing());
				if ((cartQueryParamsDTO.getRcarcasa() != null) && (!cartQueryParamsDTO.getRcarcasa().isEmpty()))
					fieldsAudioLab.put("color_audifono_R", cartQueryParamsDTO.getRcarcasa());
				if ((cartQueryParamsDTO.getLcarcasa() != null) && (!cartQueryParamsDTO.getLcarcasa().isEmpty()))
					fieldsAudioLab.put("color_audifono_L", cartQueryParamsDTO.getLcarcasa());
				if ((cartQueryParamsDTO.getRplato() != null) && (!cartQueryParamsDTO.getRplato().isEmpty()))
					fieldsAudioLab.put("color_plato_R", cartQueryParamsDTO.getRplato());
				if ((cartQueryParamsDTO.getLplato() != null) && (!cartQueryParamsDTO.getLplato().isEmpty()))
					fieldsAudioLab.put("color_plato_L", cartQueryParamsDTO.getLplato());
				if ((cartQueryParamsDTO.getRcodo() != null) && (!cartQueryParamsDTO.getRcodo().isEmpty()))
					fieldsAudioLab.put("color_codo_R", cartQueryParamsDTO.getRcodo());
				if ((cartQueryParamsDTO.getLcolorCodo() != null) && (!cartQueryParamsDTO.getLcolorCodo().isEmpty()))
					fieldsAudioLab.put("color_codo_L", cartQueryParamsDTO.getLcodo());
				if ((cartQueryParamsDTO.getRnumSerie() != null) && (!cartQueryParamsDTO.getRnumSerie().isEmpty()))
					fieldsAudioLab.put("num_serie_R", cartQueryParamsDTO.getRnumSerie());
				if ((cartQueryParamsDTO.getLnumSerie() != null) && (!cartQueryParamsDTO.getLnumSerie().isEmpty()))
					fieldsAudioLab.put("num_serie_L", cartQueryParamsDTO.getLnumSerie());
				if ((cartQueryParamsDTO.getOtoscan() != null) && (!cartQueryParamsDTO.getOtoscan().isEmpty()))
					fieldsAudioLab.put("otocloud_ID", cartQueryParamsDTO.getOtoscan());
				if (cartQueryParamsDTO.getLenviocorreo())
					fieldsAudioLab.put("enviocorreo_R", Boolean.valueOf(true)); 
				if (cartQueryParamsDTO.getRenviocorreo())
					fieldsAudioLab.put("enviocorreo_R", Boolean.valueOf(true)); 
				if ((cartQueryParamsDTO.getRpathscan() != null) && (!cartQueryParamsDTO.getRpathscan().isEmpty()))
					fieldsAudioLab.put("impresion_R", cartQueryParamsDTO.getRpathscan());
				if ((cartQueryParamsDTO.getLpathscan() != null) && (!cartQueryParamsDTO.getLpathscan().isEmpty()))
					fieldsAudioLab.put("impresion_L", cartQueryParamsDTO.getLpathscan());
				if ((cartQueryParamsDTO.getRinstrucciones() != null) && (!cartQueryParamsDTO.getRinstrucciones().isEmpty()))
					fieldsAudioLab.put("instrucciones_R", cartQueryParamsDTO.getRinstrucciones());
				if ((cartQueryParamsDTO.getLinstrucciones() != null) && (!cartQueryParamsDTO.getLinstrucciones().isEmpty()))
					fieldsAudioLab.put("instrucciones_L", cartQueryParamsDTO.getLinstrucciones());
				if ((cartQueryParamsDTO.getRaudiogram() != null) && (!cartQueryParamsDTO.getRaudiogram().isEmpty())){
					JSONObject json = new JSONObject(cartQueryParamsDTO.getRaudiogram());
					fieldsAudioLab.put("audiograma_R", json.toString());
				}
				if ((cartQueryParamsDTO.getLaudiogram() != null) && (!cartQueryParamsDTO.getLaudiogram().isEmpty())){
					JSONObject json = new JSONObject(cartQueryParamsDTO.getLaudiogram());
					fieldsAudioLab.put("audiograma_L", json.toString());
				}
				
				if (cartQueryParamsDTO.getNamePdfAudio() != null) {
					String idClient = CioneUtils.getIdCurrentClient();
					String pathFile = cioneEcommerceConectionProvider.getConfigService().getConfig().getAudioLabConfiguradorPDFPath() 
							+ PATH_SEPARATOR + idClient + PATH_SEPARATOR + cartQueryParamsDTO.getNamePdfAudio();
					fieldsAudioLab.put("url_pdf", pathFile);
				}
			}
			
			TypeResourceIdentifier customType = null;
			FieldContainer fieldContainer = null;
			String familia = "";
			if (cartQueryParamsDTO.getFamiliaProducto()!=null) {
				familia = cartQueryParamsDTO.getFamiliaProducto();
			}
			switch (familia) {
			case "audifonos":
				if ((cartQueryParamsDTO.getColorAudio() != null) && !cartQueryParamsDTO.getColorAudio().isEmpty()) {
					fields.put("colorAudifono", cartQueryParamsDTO.getColorAudio());
				}
				if ((cartQueryParamsDTO.getColorCodo() != null) && !cartQueryParamsDTO.getColorCodo().isEmpty()) {
					fields.put("colorCodo", cartQueryParamsDTO.getColorCodo());
				}
				if (cartQueryParamsDTO.isDeposito()) 
					fields.put("enDeposito", true);
				else 
					fields.put("enDeposito", false);
				customType = TypeResourceIdentifierBuilder.of().key("lineitem-audifonos").build();
				fieldContainer = FieldContainerBuilder.of().values(fields).build();
				//customFieldsDraft = CustomFieldsDraft.ofTypeKeyAndObjects("lineitem-audifonos", fields);
				break;
			case "audiolab":
				customType = TypeResourceIdentifierBuilder.of().key("lineitem-audiolab").build();
				fieldContainer = FieldContainerBuilder.of().values(fieldsAudioLab).build();
				//customFieldsDraft = CustomFieldsDraft.ofTypeKeyAndObjects("lineitem-audiolab", fieldsAudioLab);
				break;
			default:
				customType = TypeResourceIdentifierBuilder.of().key("customFields-forCartLines").build();
				fieldContainer = FieldContainerBuilder.of().values(fields).build();
				//customFieldsDraft = CustomFieldsDraft.ofTypeKeyAndObjects("customFields-forCartLines", fields);
				break;
			}
			
			CustomFieldsDraft customFieldDraft = CustomFieldsDraftBuilder.of().type(customType).fields(fieldContainer).build();
			CartUpdate cartupdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(
					CartAddLineItemActionBuilder.of()
						.productId(cartQueryParamsDTO.getProductId())
						.custom(customFieldDraft)
						.variantId(Long.valueOf(cartQueryParamsDTO.getVariantId()))
						.quantity(Long.valueOf(cartQueryParamsDTO.getCantidad()))
						.build())
					.build();
			updatedCart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartupdate).executeBlocking().getBody();	
			/*LineItemDraft lineItemDraft = LineItemDraft.of(cartQueryParamsDTO.getProductId(), cartQueryParamsDTO.getVariantId(), cartQueryParamsDTO.getCantidad())
					.withCustom(customFieldsDraft)
					.newBuilder().build();
			
			final LineItemDraft builtLineItemDraft = LineItemDraftBuilder.of(lineItemDraft).build();
			
			
			final List<UpdateAction<Cart>> updateActions =
					Arrays.asList(AddLineItem.of(builtLineItemDraft), Recalculate.of().withUpdateProductData(true));
					
			updatedCart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(cart, updateActions));*/
			
			Map<String,GiftProduct> productosRegalo = new HashMap<>();
			if (cartQueryParamsDTO.getSkuPackPadre() != null && !cartQueryParamsDTO.getSkuPackPadre().isEmpty()) {
				productosRegalo = getGiftList(cartQueryParamsDTO, grupoPrecio);
			}	
			
			if ((cartQueryParamsDTO.getAuricular() != null) && !cartQueryParamsDTO.getAuricular().isEmpty()) {	
				if (cartQueryParamsDTO.getAuricular().contains(",")) {
					updatedCart = comprarAccesorioExtraListado(updatedCart, cartQueryParamsDTO, cartQueryParamsDTO.getAuricular().split(","), MyshopConstants.AURICULARES, productosRegalo);
				} else {
					updatedCart = comprarAccesorioExtra(updatedCart, cartQueryParamsDTO, cartQueryParamsDTO.getAuricular(), MyshopConstants.AURICULARES, productosRegalo);
				}
			}
			if ((cartQueryParamsDTO.getAcoplador() != null) && !cartQueryParamsDTO.getAcoplador().isEmpty()) {
				if (cartQueryParamsDTO.getAcoplador().contains(",")) {
					updatedCart = comprarAccesorioExtraListado(updatedCart, cartQueryParamsDTO, cartQueryParamsDTO.getAcoplador().split(","), MyshopConstants.ACOPLADORES, productosRegalo);
				} else {
					updatedCart = comprarAccesorioExtra(updatedCart, cartQueryParamsDTO, cartQueryParamsDTO.getAcoplador(), MyshopConstants.ACOPLADORES, productosRegalo);
				}
			}
			
			if ((cartQueryParamsDTO.getCargador() != null) && !cartQueryParamsDTO.getCargador().isEmpty()) {
				if (cartQueryParamsDTO.getCargador().contains(",")) {
					updatedCart = comprarAccesorioExtraListado(updatedCart, cartQueryParamsDTO, cartQueryParamsDTO.getCargador().split(","), MyshopConstants.CARGADORES, productosRegalo);
				} else {
					updatedCart = comprarAccesorioExtra(updatedCart, cartQueryParamsDTO, cartQueryParamsDTO.getCargador(), MyshopConstants.CARGADORES, productosRegalo);
				}
			}
			
			if ((cartQueryParamsDTO.getAccesorioinalambrico() != null) && !cartQueryParamsDTO.getAccesorioinalambrico().isEmpty()) {
				if (cartQueryParamsDTO.getAccesorioinalambrico().contains(",")) {
					updatedCart = comprarAccesorioExtraListado(updatedCart, cartQueryParamsDTO, cartQueryParamsDTO.getAccesorioinalambrico().split(","), MyshopConstants.ACCESORIOS, productosRegalo);
				} else {
					updatedCart = comprarAccesorioExtra(updatedCart, cartQueryParamsDTO, cartQueryParamsDTO.getAccesorioinalambrico(), MyshopConstants.ACCESORIOS, productosRegalo);
				}
			}
			
			if ((cartQueryParamsDTO.getTubosFinos() != null) && !cartQueryParamsDTO.getTubosFinos().isEmpty()) {
				if (cartQueryParamsDTO.getTubosFinos().contains(",")) {
					updatedCart = comprarAccesorioExtraListado(updatedCart, cartQueryParamsDTO, cartQueryParamsDTO.getTubosFinos().split(","), MyshopConstants.TUBOSFINOS, productosRegalo);
				} else {
					updatedCart = comprarAccesorioExtra(updatedCart, cartQueryParamsDTO, cartQueryParamsDTO.getTubosFinos(), MyshopConstants.TUBOSFINOS, productosRegalo);
				}
			}
			
			if ((cartQueryParamsDTO.getSujecionesDeportivas() != null) && !cartQueryParamsDTO.getSujecionesDeportivas().isEmpty()) {
				if (cartQueryParamsDTO.getSujecionesDeportivas().contains(",")) {
					updatedCart = comprarAccesorioExtraListado(updatedCart, cartQueryParamsDTO, cartQueryParamsDTO.getSujecionesDeportivas().split(","), MyshopConstants.SUJECCIONESDEPORTIVAS, productosRegalo);
				} else {
					updatedCart = comprarAccesorioExtra(updatedCart, cartQueryParamsDTO, cartQueryParamsDTO.getSujecionesDeportivas(), MyshopConstants.SUJECCIONESDEPORTIVAS, productosRegalo);
				}
			}
			
			if ((cartQueryParamsDTO.getFiltros() != null) && !cartQueryParamsDTO.getFiltros().isEmpty()) {
				if (cartQueryParamsDTO.getFiltros().contains(",")) {
					updatedCart = comprarAccesorioExtraListado(updatedCart, cartQueryParamsDTO, cartQueryParamsDTO.getFiltros().split(","), MyshopConstants.FILTROS, productosRegalo);
				} else {
					updatedCart = comprarAccesorioExtra(updatedCart, cartQueryParamsDTO, cartQueryParamsDTO.getFiltros(), MyshopConstants.FILTROS, productosRegalo);
				}
			}
			
			if ((cartQueryParamsDTO.getGarantia() != null) && !cartQueryParamsDTO.getGarantia().isEmpty()) {
				
				updatedCart = comprarAccesorioExtra(updatedCart, cartQueryParamsDTO, cartQueryParamsDTO.getGarantia(), MyshopConstants.GARANTIA, productosRegalo);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return updatedCart;
	}
	
	private Cart comprarAccesorioExtra(Cart cart, CartParamsDTO cartQueryParamsDTO, String sku, String type, Map<String,GiftProduct> productosRegalo) throws Exception {
		long cantidad = 1;
		final Map<String, Object> fieldsExtra = new HashMap<>();
		if (cartQueryParamsDTO.getRefTaller() != null) 
			fieldsExtra.put("refTaller", cartQueryParamsDTO.getRefTaller());
		if (cartQueryParamsDTO.getRefCliente() != null) 
			fieldsExtra.put("refCliente", cartQueryParamsDTO.getRefCliente());
		if (productosRegalo.get(type) != null) {
			GiftProduct gift = productosRegalo.get(type);
			fieldsExtra.put("pvoConDescuento", MyShopUtils.getMonetaryAmount(gift.getPvo()));
			fieldsExtra.put("refPackPromos", cartQueryParamsDTO.getRefPackPromos());
			cantidad = gift.getUnits();
			if (cartQueryParamsDTO.getPackName() != null)
				fieldsExtra.put("descPackPromos", cartQueryParamsDTO.getPackName());
		}
		if (type.equals(MyshopConstants.GARANTIA)) {
			if (cartQueryParamsDTO.isDeposito()) 
				fieldsExtra.put("enDeposito", true);
			else
				fieldsExtra.put("enDeposito", false);
			
			if ((cartQueryParamsDTO.getSide() != null) && (cartQueryParamsDTO.getSide().equals("binaural"))) {
				fieldsExtra.put("refGarantia", "OI");
				cart = updateCartWithFields(cart, fieldsExtra, sku, cantidad);
				
				fieldsExtra.put("refGarantia", "OD");
				return updateCartWithFields(cart, fieldsExtra, sku, cantidad);
				
			} else if ((cartQueryParamsDTO.getSide() != null) && (cartQueryParamsDTO.getSide().equals("derecho"))) {
				fieldsExtra.put("refGarantia", "OD");
				return updateCartWithFields(cart, fieldsExtra, sku, cantidad);
			} else if ((cartQueryParamsDTO.getSide() != null) && (cartQueryParamsDTO.getSide().equals("izquierdo"))) {
				fieldsExtra.put("refGarantia", "OI");
				return updateCartWithFields(cart, fieldsExtra, sku, cantidad);
			} else {
				return updateCartWithFields(cart, fieldsExtra, sku, cantidad);
			}
			
		}
		

		return updateCartWithFields(cart, fieldsExtra, sku, cantidad);
		
	}
	
	private Cart updateCartWithFields(Cart cart, Map<String, Object> fieldsExtra, String sku, long cantidad) {
		Cart updatedCart = cart;
		TypeResourceIdentifier customType = TypeResourceIdentifierBuilder.of().key("customFields-forCartLines").build();
		FieldContainer fieldContainer = FieldContainerBuilder.of().values(fieldsExtra).build();		
		CustomFieldsDraft customFieldDraft = CustomFieldsDraftBuilder.of().type(customType).fields(fieldContainer).build();
		
		/*CustomFieldsDraft customFieldsDraftExtra =
		        CustomFieldsDraft.ofTypeKeyAndObjects("customFields-forCartLines", fieldsExtra);*/
		ProductProjection product = commercetoolsServiceAux.getProductBySkuFilter(sku, commercetoolsService.getGrupoPrecioCommerce());
		
		
		
		if (product != null) {
			CartUpdate cartupdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(
					CartAddLineItemActionBuilder.of()
						.productId(product.getId())
						.custom(customFieldDraft)
						.variantId(product.getMasterVariant().getId())
						.quantity(cantidad)
						.build())
					.build();
			
			updatedCart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartupdate).executeBlocking().getBody();
			/*LineItemDraft lineItemDraftExtra = LineItemDraft.of(product.getId(), product.getMasterVariant().getId(), cantidad)
					.withCustom(customFieldsDraftExtra)
					.newBuilder().build();
			
			final LineItemDraft builtLineItemDraftExtra = LineItemDraftBuilder.of(lineItemDraftExtra).build();
			
			
			final List<UpdateAction<Cart>> updateActionsExtra =
					Arrays.asList(AddLineItem.of(builtLineItemDraftExtra), Recalculate.of().withUpdateProductData(true));
			
			updatedCart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(cart, updateActionsExtra));*/
		}
		
		return updatedCart;
	}
	
	private Cart comprarAccesorioExtraListado(Cart cart, CartParamsDTO cartQueryParamsDTO, String skus[], String type, Map<String,GiftProduct> productosRegalo) throws Exception {
		for (int i = 0; i < skus.length; i++) {
			
			long cantidad = 1;
			final Map<String, Object> fieldsExtra = new HashMap<>();
			if (cartQueryParamsDTO.getPackName() != null)
				fieldsExtra.put("descPackPromos", cartQueryParamsDTO.getPackName());
			if (cartQueryParamsDTO.getRefTaller() != null) 
				fieldsExtra.put("refTaller", cartQueryParamsDTO.getRefTaller());
			if (cartQueryParamsDTO.getRefCliente() != null) 
				fieldsExtra.put("refCliente", cartQueryParamsDTO.getRefCliente());
			if (productosRegalo.get(type) != null) {
				GiftProduct gift = productosRegalo.get(type);
				fieldsExtra.put("pvoConDescuento", MyShopUtils.getMoney(gift.getPvo()));//  getMonetaryAmount());
				fieldsExtra.put("refPackPromos", cartQueryParamsDTO.getRefPackPromos());
				if (i >= gift.getUnits()) {
					break;
				}
			}
			if (type.equals(MyshopConstants.GARANTIA)) {
				if (cartQueryParamsDTO.isDeposito()) 
					fieldsExtra.put("enDeposito", true);
				else
					fieldsExtra.put("enDeposito", false);
			}
			
			TypeResourceIdentifier customType = TypeResourceIdentifierBuilder.of().key("customFields-forCartLines").build();
			FieldContainer fieldContainer = FieldContainerBuilder.of().values(fieldsExtra).build();		
			CustomFieldsDraft customFieldDraft = CustomFieldsDraftBuilder.of().type(customType).fields(fieldContainer).build();
			
			/*CustomFieldsDraft customFieldsDraftExtra =
			        CustomFieldsDraft.ofTypeKeyAndObjects("customFields-forCartLines", fieldsExtra);*/
			
			ProductProjection product = commercetoolsServiceAux.getProductBySkuFilter(skus[i], commercetoolsService.getGrupoPrecioCommerce());
		
			/*ProductProjectionSearch search = ProductProjectionSearch
					.ofCurrent()
					.bySku(skus[i])
					.plusQueryFilters(m -> m.allVariants().attribute().ofString("gruposPrecio").is(commercetoolsService.getGrupoPrecioCommerce()))
					.withMarkingMatchingVariants(true);
			CompletionStage<PagedSearchResult<ProductProjection>> product_search = cioneEcommerceConectionProvider.getClient().execute(search);
			PagedSearchResult<ProductProjection> searchresult = product_search.toCompletableFuture().join();		
			List<ProductProjection> productsSearch = searchresult.getResults();*/
			
			if (product != null) {
			//if (productsSearch.size() >=1) {
				CartUpdate cartupdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(
						CartAddLineItemActionBuilder.of()
							.productId(product.getId())
							.custom(customFieldDraft)
							.variantId(product.getMasterVariant().getId())
							.quantity(cantidad)
							.build())
						.build();
				
				cart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartupdate).executeBlocking().getBody();
				
				/*
				LineItemDraft lineItemDraftExtra = LineItemDraft.of(product.getId(), product.getMasterVariant().getId(), cantidad)
						.withCustom(customFieldsDraftExtra)
						//.withSku(cartQueryParamsDTO.getSku())
						.newBuilder().build();
				
				final LineItemDraft builtLineItemDraftExtra = LineItemDraftBuilder.of(lineItemDraftExtra).build();
				
				
				final List<UpdateAction<Cart>> updateActionsExtra =
						Arrays.asList(AddLineItem.of(builtLineItemDraftExtra), Recalculate.of().withUpdateProductData(true));
				
				cart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(cart, updateActionsExtra));*/
			}
			
		}
		return cart;
		
	}
	
	/*private Cart comprarAccesorioExtra(Cart cart, Map<String, Object> fieldsExtra, String sku, long cantidad) throws Exception {
		Cart updatedCart = cart;
		CustomFieldsDraft customFieldsDraftExtra =
		        CustomFieldsDraft.ofTypeKeyAndObjects("customFields-forCartLines", fieldsExtra);
		
		ProductProjectionSearch search = ProductProjectionSearch
				.ofCurrent()
				.bySku(sku)
				.plusQueryFilters(m -> m.allVariants().attribute().ofString("gruposPrecio").is(commercetoolsService.getGrupoPrecioCommerce()))
				.withMarkingMatchingVariants(true);
		CompletionStage<PagedSearchResult<ProductProjection>> product_search = cioneEcommerceConectionProvider.getClient().execute(search);
		PagedSearchResult<ProductProjection> searchresult = product_search.toCompletableFuture().join();		
		List<ProductProjection> productsSearch = searchresult.getResults();
		
		if (productsSearch.size() >=1) {
			ProductProjection product = productsSearch.get(0);
			
			LineItemDraft lineItemDraftExtra = LineItemDraft.of(product.getId(), product.getMasterVariant().getId(), cantidad)
					.withCustom(customFieldsDraftExtra)
					//.withSku(cartQueryParamsDTO.getSku())
					.newBuilder().build();
			
			final LineItemDraft builtLineItemDraftExtra = LineItemDraftBuilder.of(lineItemDraftExtra).build();
			
			
			final List<UpdateAction<Cart>> updateActionsExtra =
					Arrays.asList(AddLineItem.of(builtLineItemDraftExtra), Recalculate.of().withUpdateProductData(true));
			
			updatedCart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(cart, updateActionsExtra));
		}
		return updatedCart;
		
	}*/

/*	@Override
	public Response getCarrito(CartParamsDTO cartQueryParamsDTO, String decodedUserId) {
        Customer customer = Customer.of(decodedUserId, "");
        Response request = request(cartQueryParamsDTO.getDefinitionName(), 
        		cartQueryParamsDTO.getConnectionName(), 
        		cartProvider -> cartProvider.getByCustomer(customer, 
        				getConnection(cartQueryParamsDTO.getDefinitionName(), cartQueryParamsDTO.getConnectionName()).orElse(null)));
        
        
        return request;
	}*/
	public Cart getCart(String customerId) {
		Cart cart = null;
		try {
			
			List<String> querys = new ArrayList<>();
			querys.add("customerId=\"" + customerId + "\"");
			querys.add("cartState=\"Active\"");
			querys.add("custom(fields(cartType!=\"" + MyshopConstants.PERIODIC_PURCHASE + "\"))");
			querys.add("custom(fields(cartType!=\"" + MyshopConstants.BUDGET + "\"))");
			querys.add("custom(fields(cartType!=\"" + MyshopConstants.GENERIC_PACK + "\"))");
			List<Cart> carts = 
				cioneEcommerceConectionProvider
				.getApiRoot()
				.carts()
				//.withCustomerId(customer.getId())
				.get()
				.withWhere(querys)
				.executeBlocking()
				.getBody()
				.getResults();
			if (carts.isEmpty()) {
				return null;
			} else
				cart = carts.get(0);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return cart;
	}
	
	public Cart getCartById(String id) {
		Cart cart = null;
		try {
			cart = 
				cioneEcommerceConectionProvider
				.getApiRoot()
				.carts()
				.withId(id)
				.get()
				.executeBlocking()
				.getBody();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return cart;
	}
	
	public Response deleteCart(String idCart) {
		try {
			Cart cart = cioneEcommerceConectionProvider
					.getApiRoot()
					.carts()
					.withId(idCart).get().executeBlocking().getBody();
			if (cart != null) {
				Cart deletecart = cioneEcommerceConectionProvider
				.getApiRoot()
				.carts()
				.withId(idCart)
				.delete()
				.withVersion(cart.getVersion())
				.executeBlocking()
				.getBody().get();
				
				String cartResponse = getBackwardCompatibilityCartAsString(deletecart);
				return Response.ok(cartResponse).build();
			} else
				return Response.status(Response.Status.NOT_FOUND).entity("Carrito no encontrado").build();
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al borrar el carrito").build();
		}
	}
	
	public Response updatePlazoEntrega(CartParamsDTO cartQueryParamsDTO, Integer plazoEntrega) {
		Cart updatedCart = null;
		String lineItemId = cartQueryParamsDTO.getLineItemId();
		
		CustomerCT customer = commercetoolsService.getCustomer();
		cartQueryParamsDTO.setCustomerGroup(customer.getCustomerGroup().getId());
	    Cart cart = getCart(customer.getId());
	    
	    if(cart != null) {
	    	//FieldContainer field = FieldContainerBuilder.of().addValue("plazoEntrega", plazoEntrega).build();
	    	CartSetLineItemCustomFieldAction setcustomfield = CartSetLineItemCustomFieldActionBuilder.of().lineItemId(lineItemId).name("plazoEntrega").value(plazoEntrega).build();
	    	CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(setcustomfield).build();
	    	updatedCart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartUpdate).executeBlocking().getBody().get();	
	    	
	    	/*updatedCart = cioneEcommerceConectionProvider.getClient()
	    			.executeBlocking(CartUpdateCommand.of(cart, SetLineItemCustomField.ofObject("plazoEntrega", plazoEntrega, lineItemId)));*/
	    	try {
				String cartResponse = getBackwardCompatibilityCartAsString(updatedCart);
				return Response.ok(cartResponse).build();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return Response.ok(updatedCart).build();
			}
	    } else
	    	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updatePlazoEntrega").build();
	}
	
	public VariantDTO getPromocionesByVariant(ProductVariant variante) {
		VariantDTO result = new VariantDTO();
		
		result.setGruposPrecio(commercetoolsService.getGrupoPrecioCommerce());
		
		if (MyShopUtils.hasAttribute("tipoProducto", variante.getAttributes())) 
			result.setTipoProducto(((LocalizedString)MyShopUtils.findAttribute("tipoProducto", variante.getAttributes()).getValue()).get(MyshopConstants.esLocale));
		
		if (MyShopUtils.hasAttribute("codigoCentral", variante.getAttributes())) 
			result.setCodigoCentral((String)MyShopUtils.findAttribute("codigoCentral", variante.getAttributes()).getValue());
		
		if (MyShopUtils.hasAttribute("aliasEkon", variante.getAttributes())) 
			result.setAliasEkon((String)MyShopUtils.findAttribute("aliasEkon", variante.getAttributes()).getValue());
		
		if (MyShopUtils.hasAttribute("tienePromociones", variante.getAttributes())) 
			result.setTienePromociones((Boolean)MyShopUtils.findAttribute("tienePromociones", variante.getAttributes()).getValue());
		
		if (MyShopUtils.hasAttribute("tieneRecargo", variante.getAttributes()))
			result.setTieneRecargo((Boolean) MyShopUtils.findAttribute("tieneRecargo", variante.getAttributes()).getValue());
		
		Price price = commercetoolsService.getPriceBycustomerGroup(result.getGruposPrecio(), variante.getPrices());
		
		result.setTipoPromocion("sin-promo");
		
		
		result.setPvo(MyShopUtils.formatTypedMoney(price.getValue()));
		
		if(result.isTienePromociones()) {
			String pvoOrigin = MyShopUtils.formatTypedMoney(price.getValue());
			
			List<PromocionesDTO> listPromos = new ArrayList<>();
			try {
				listPromos = promocionesDao.getPromociones(result);
			} catch (NamingException e) {
				log.error(e.getMessage(), e);
			}
			
			List<PromocionesDTO> listPromosAux = new ArrayList<PromocionesDTO>();
			
			if(listPromos.size() > 1) {
				//aplicamos escalado
				result.setPvoDto(result.getPvo());
				result.setTipoPromocion("escalado");
				for(PromocionesDTO promo: listPromos) {
					double pvodto = 0;
					if(promo.getTipo_descuento() == MyshopConstants.dtoPorcentaje) {
						double dto = ((100 - promo.getValor()) / 100);
						pvodto = Double.valueOf(result.getPvo().replace(',', '.')) * dto;
						result.setValorDescuento(String.valueOf(promo.getValor()).replace('.', ','));
					}
					promo.setPvo(pvoOrigin);
					pvodto = MyShopUtils.round(pvodto, 2);
					promo.setPvoDto(String.valueOf(pvodto).replace('.', ','));
					listPromosAux.add(promo);
					
					if(promo.getCantidad_desde() == 1) {
						result.setPvoDto(promo.getPvoDto());
					}
				}
				result.setListPromos(listPromosAux);
			} else if(listPromos.size() == 1) {
				//si devuelve un unico resultado solo hay una promo
				PromocionesDTO promo = listPromos.get(0);
				double pvodto =0;
				if (promo.getTipo_descuento() == MyshopConstants.dtoPorcentaje) { // si es por porcentaje
					result.setTipoPromocion("porcentaje");
					double dto= ((100-promo.getValor())/100);
					pvodto = Double.valueOf(result.getPvo().replace(',', '.')) * dto;
					result.setValorDescuento(String.valueOf(promo.getValor()).replace('.', ','));
				} else {
					result.setTipoPromocion("valor");
					pvodto = Double.valueOf(result.getPvo().replace(',', '.')) - promo.getValor();
					result.setValorDescuento(String.valueOf(promo.getValor()).replace('.', ','));
				}
				pvodto = MyShopUtils.round(pvodto, 2);
				result.setPvoDto(String.valueOf(pvodto).replace('.', ','));
				if (promo.getValor() < 0) { //es un incremento, no lo marcamos como promocion pero actualizamos su pvo con el incremento
					result.setPvo(String.valueOf(pvodto).replace('.', ','));
					result.setTienePromociones(false);
					result.setPvoIncremento(result.getPvo());
				} 
			} else { //si no hay promociones para ese grupo de precio 
				result.setTienePromociones(false);
			}
		}
		
		return result;
	}

	public Response updatePvoConDescuento(CartParamsDTO cartQueryParamsDTO, Money nuevoPvo) {
		Cart updatedCart = null;
		String lineItemId = cartQueryParamsDTO.getLineItemId();
		
		CustomerCT customer = commercetoolsService.getCustomer();
		cartQueryParamsDTO.setCustomerGroup(customer.getCustomerGroup().getId());
	    Cart cart = getCart(customer.getId());
	    
	    if(cart != null) {
	    	
	    	//FieldContainer field = FieldContainerBuilder.of().addValue("pvoConDescuento", nuevoPvo).build();
	    	CartSetLineItemCustomFieldAction setcustomfield = CartSetLineItemCustomFieldActionBuilder.of().lineItemId(lineItemId).name("pvoConDescuento").value(nuevoPvo).build();
	    	CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(setcustomfield).build();
	    	updatedCart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartUpdate).executeBlocking().getBody().get();	
	    	
	    	/*updatedCart = cioneEcommerceConectionProvider.getClient()
	    			.executeBlocking(CartUpdateCommand.of(cart, SetLineItemCustomField.ofObject("pvoConDescuento", nuevoPvo, lineItemId)));*/
	    	try {
				String cartResponse = getBackwardCompatibilityCartAsString(updatedCart);
				return Response.ok(cartResponse).build();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return Response.ok(updatedCart).build();
			}
	    } else
	    	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updatePlazoEntrega").build();
	}
	
	public Response updateShippingAddress(DireccionParamsDTO addressParams) {
		
		List<CartUpdateAction> actions = new ArrayList<>();
		
		Cart updatedCart = null;
		//CustomerCT customer = commercetoolsService.getCustomer();
		Customer customer = commercetoolsService.getCustomerSDK();
		
	    Cart cart = getCart(customer.getId());
	    Address dir = AddressBuilder.of()
    			.streetName(addressParams.getDireccion())
    			.city(addressParams.getPoblacion())
    			.region(addressParams.getProvincia())
    			.postalCode(addressParams.getCod_postal())
    			.country("ES").build();
		
		String shippingMethodKey;
		
		if(addressParams.getNombre() != null) {
			//envio a cliente
			dir.setEmail(addressParams.getEmail());
			dir.setMobile(addressParams.getMovil());
			dir.setFirstName(addressParams.getNombre());
			
			if(!addressParams.getSegundo_apellido().equals(""))
				dir.setLastName(addressParams.getPrimer_apellido() + " " + addressParams.getSegundo_apellido());
			else
				dir.setLastName(addressParams.getPrimer_apellido());
			
			shippingMethodKey = "cliente"; //key de shippingMethod 'cliente'
		
		} else {
			//envio a tienda
			dir.setEmail(customer.getEmail());
			dir.setFirstName(customer.getFirstName());
			dir.setLastName(customer.getLastName());
			
			String codDir = addressParams.getRadio();
			if ((codDir == null) || (codDir.isEmpty())) { //control incidencia codDir vacio
				DireccionesDTO direcciones = middlewareService.getDirecciones(CioneUtils.getIdCurrentClientERP());
				
				if (!direcciones.getTransportes().isEmpty()) {
					DireccionDTO direccionUsuario = direcciones.getTransportes().get(0);//la primera sera la direccion por defecto
					codDir = direccionUsuario.getId_localizacion(); 
					String codusuario = CioneUtils.getIdCurrentClient();
					//si es empleado utilizamos la direccion de envio seteada en commerceTools
					if (CioneUtils.isEmpleado(codusuario)) {
						codDir = (String) customer.getCustom().getFields().values().get("codDirDefault");  //seteamos la direccion de envio seteada en commerceTools
						boolean encontrada = false; //flag por si un empleado tiene desctualizada el codigo de direccion por defecto
						for (DireccionDTO direccion : direcciones.getTransportes()) {
							if (direccion.getId_localizacion().equals(codDir)) {
								encontrada = true;
								direccionUsuario = direccion;
							}
						}
						if (!encontrada)
							codDir = direccionUsuario.getId_localizacion(); 
					}
				}
			}
			
			if(cart != null) {
				//REVISAR BIEN!! no termino de ver la diferencia entre setear con el type o sin el
				if(cart.getCustom() == null) {
					Map<String, Object> obj = new HashMap<>();
					obj.put("codDirEnvio", codDir);
					FieldContainer fieldContainer = FieldContainerBuilder.of().values(obj).build();
					TypeResourceIdentifier customType = TypeResourceIdentifierBuilder.of().key("customFields-forOrders").build();
					CartSetCustomTypeAction cartSetCustomTypeAction = CartSetCustomTypeActionBuilder.of()
							.type(customType)
							.fields(fieldContainer)
							.build();
					
					actions.add(cartSetCustomTypeAction);
					
					//actions.add(SetCustomType.ofTypeKeyAndObjects("customFields-forOrders", obj));

				} else {
					CartSetCustomFieldAction cartSetCustomFieldAction = CartSetCustomFieldActionBuilder.of().name("codDirEnvio").value(codDir).build();
					actions.add(cartSetCustomFieldAction);
				}
			}
			
			shippingMethodKey = "socio"; //key de shippingMethod 'socio'
		}
		
		if(cart != null) {
			CartSetShippingAddressAction cartSetShippingAddressAction  = CartSetShippingAddressActionBuilder.of().address(dir).build();
	    	actions.add(cartSetShippingAddressAction);
			//actions.add(SetShippingAddress.of(dir));
			
	    	String idShippingMethod = cioneEcommerceConectionProvider.getMapShippingMethods().get(shippingMethodKey).getId();
	    	ShippingMethodResourceIdentifier shippingMethod = ShippingMethodResourceIdentifierBuilder.of().id(idShippingMethod).build();
	    	CartSetShippingMethodAction cartSetShippingMethodAction = CartSetShippingMethodActionBuilder.of().shippingMethod(shippingMethod).build();
	    	actions.add(cartSetShippingMethodAction);
	    	
			/*final ShippingMethodByKeyGet getByKey = ShippingMethodByKeyGet.of(shippingMethodKey);
		    final ShippingMethod shippingMethod = cioneEcommerceConectionProvider.getClient().executeBlocking(getByKey);
			actions.add(SetShippingMethod.of(shippingMethod.toResourceIdentifier()));*/
			
	    	CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(actions).build();
	    	updatedCart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartUpdate).executeBlocking().getBody().get();
	    	/*updatedCart = cioneEcommerceConectionProvider.getClient()
	    			.executeBlocking(CartUpdateCommand.of(cart, actions));*/
	    	
	    	try {
				String cartResponse = getBackwardCompatibilityCartAsString(updatedCart);
				return Response.ok(cartResponse).build();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return Response.ok(updatedCart).build();
			}
	    }
		
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updateShippingAddress").build();
	}
	
	//PROBAR BIEN!!
	public Response updateCustomField(CartParamsDTO cartQueryParamsDTO, String updatedField) {
		try {
			Cart updatedCart = null;
			String lineItemId = cartQueryParamsDTO.getLineItemId();
			
			CustomerCT customer = commercetoolsService.getCustomer();
			cartQueryParamsDTO.setCustomerGroup(customer.getCustomerGroup().getId());
			Cart cart = getCart(customer.getId());
			
			String nuevoValue = "";
			boolean nuevoValueBoolean = false;
			List<CartUpdateAction> actions = new ArrayList<>();
			//List<UpdateAction<Cart>> actions = new ArrayList<>();
			
			if(updatedField.equals("refCliente"))
				nuevoValue = cartQueryParamsDTO.getRefCliente();
			else if(updatedField.equals("refTaller"))
				nuevoValue = cartQueryParamsDTO.getRefTaller();
			else if(updatedField.equals("descPack"))
				nuevoValue = cartQueryParamsDTO.getDescPack();
			else if(updatedField.equals("refPack"))
				nuevoValue = cartQueryParamsDTO.getRefPack();
			else if(updatedField.equals("aTaller"))
				nuevoValueBoolean = cartQueryParamsDTO.isaTaller();
			else if(updatedField.equals("all")) {
				
			    //Map<String, Object> obj = new HashMap<>();
			    if (cartQueryParamsDTO.getRefCliente()!= null) {
			    	//FieldContainer field = FieldContainerBuilder.of().addValue("refCliente", cartQueryParamsDTO.getRefCliente()).build();
			    	actions.add(CartSetLineItemCustomFieldActionBuilder.of().lineItemId(lineItemId).name("refCliente").value(cartQueryParamsDTO.getRefCliente()).build());
			    	//actions.add(SetLineItemCustomField.ofObject("refCliente", cartQueryParamsDTO.getRefCliente(), lineItemId));
			    }
			    if (cartQueryParamsDTO.getRefTaller()!= null) {
			    	actions.add(CartSetLineItemCustomFieldActionBuilder.of().lineItemId(lineItemId).name("refTaller").value(cartQueryParamsDTO.getRefTaller()).build());
			    	//actions.add(SetLineItemCustomField.ofObject("refTaller", cartQueryParamsDTO.getRefTaller(), lineItemId));
			    }
			    if (cartQueryParamsDTO.getDescPack()!= null) {
			    	actions.add(CartSetLineItemCustomFieldActionBuilder.of().lineItemId(lineItemId).name("descPack").value(cartQueryParamsDTO.getDescPack()).build());
			    	//actions.add(SetLineItemCustomField.ofObject("descPack", cartQueryParamsDTO.getDescPack(), lineItemId));
			    }
			    actions.add(CartSetLineItemCustomFieldActionBuilder.of().lineItemId(lineItemId).name("esPack").value(cartQueryParamsDTO.isEsPack()).build());
			    //actions.add(SetLineItemCustomField.ofObject("esPack", cartQueryParamsDTO.isEsPack(), lineItemId));
			    
			    actions.add(CartSetLineItemCustomFieldActionBuilder.of().lineItemId(lineItemId).name("aTaller").value(cartQueryParamsDTO.isaTaller()).build());
			    //actions.add(SetLineItemCustomField.ofObject("aTaller", cartQueryParamsDTO.isaTaller(), lineItemId));
			    
			    if (cartQueryParamsDTO.getRefPack()!= null) {
			    	actions.add(CartSetLineItemCustomFieldActionBuilder.of().lineItemId(lineItemId).name("refPack").value(cartQueryParamsDTO.getRefPack()).build());
			    	//actions.add(SetLineItemCustomField.ofObject("refPack", cartQueryParamsDTO.getRefPack(), lineItemId));
			    	//obj.put("aTaller", true);
			    } else {
			    	//obj.put("aTaller", false);
			    }
			    
			    
			}
			
			if(cart != null) {
				if(!updatedField.equals("all") && !updatedField.equals("aTaller")) {
					CartSetLineItemCustomFieldAction action = 
						CartSetLineItemCustomFieldActionBuilder.of().lineItemId(lineItemId).name(updatedField).value(nuevoValue).build();
					CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(action).build();
					updatedCart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartUpdate).executeBlocking().getBody().get();
//		    	updatedCart = cioneEcommerceConectionProvider.getClient()
//		    			.executeBlocking(CartUpdateCommand.of(cart, SetLineItemCustomField.ofObject(updatedField, nuevoValue, lineItemId)));
				}else if(updatedField.equals("aTaller")) {
					CartSetLineItemCustomFieldAction action = 
			    			CartSetLineItemCustomFieldActionBuilder.of().lineItemId(lineItemId).name(updatedField).value(nuevoValueBoolean).build();
			    		CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(action).build();
			    		updatedCart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartUpdate).executeBlocking().getBody().get();
//	    		updatedCart = cioneEcommerceConectionProvider.getClient()
//		    			.executeBlocking(CartUpdateCommand.of(cart, SetLineItemCustomField.ofObject(updatedField, nuevoValueBoolean, lineItemId)));
				}else { //all
					CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(actions).build();
					updatedCart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartUpdate).executeBlocking().getBody().get();
//	    		updatedCart = cioneEcommerceConectionProvider.getClient()
//		    			.executeBlocking(CartUpdateCommand.of(cart, actions));
				}
				try {
					String cartResponse = getBackwardCompatibilityCartAsString(updatedCart);
					return Response.ok(cartResponse).build();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					return Response.ok(updatedCart).build();
				}
			} else
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updateCustomField " + updatedField).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updateCustomField " + updatedField).build();
		}
	}
	
	public Response updateCustomFieldCLI(CartParamsDTO cartQueryParamsDTO, String updatedField) {
		Cart updatedCart = null;
		try {
			String lineItemId = cartQueryParamsDTO.getLineItemId();
			
			CustomerCT customer = commercetoolsService.getCustomer();
			cartQueryParamsDTO.setCustomerGroup(customer.getCustomerGroup().getId());
			
			Cart cart = null;
			if ((cartQueryParamsDTO.getIdCarritoOculto() != null) && !cartQueryParamsDTO.getIdCarritoOculto().isEmpty()) {
				cart = getCartById(cartQueryParamsDTO.getIdCarritoOculto());
			} else
				cart = getCart(customer.getId());
			
			List<CartUpdateAction> actions = new ArrayList<>();
			//List<UpdateAction<Cart>> actions = new ArrayList<>();
			String nuevoValue = "";
			
			if(updatedField.equals("refCliente") || (updatedField.equals("_REFSOCIO")))
				nuevoValue = cartQueryParamsDTO.getRefCliente();
			else if(updatedField.equals("refTaller") || (updatedField.equals("_REFTALLER")))
				nuevoValue = cartQueryParamsDTO.getRefTaller();
			else if(updatedField.equals("descPack"))
				nuevoValue = cartQueryParamsDTO.getDescPack();
			else if(updatedField.equals("refPack"))
				nuevoValue = cartQueryParamsDTO.getRefPack();
			else if(updatedField.equals("all")) {
			    if (cartQueryParamsDTO.getRefCliente()!= null) {
			    	actions.add(CartSetCustomLineItemCustomFieldActionBuilder.of()
			    			.customLineItemId(lineItemId)
			    			.name("refCliente")
			    			.value(cartQueryParamsDTO.getRefCliente())
			    			.build());
			    	//actions.add(SetCustomLineItemCustomField.ofObject("refCliente", cartQueryParamsDTO.getRefCliente(), lineItemId));
			    }
			    if (cartQueryParamsDTO.getRefTaller()!= null) {
			    	actions.add(CartSetCustomLineItemCustomFieldActionBuilder.of()
			    			.customLineItemId(lineItemId)
			    			.name("refTaller")
			    			.value(cartQueryParamsDTO.getRefTaller())
			    			.build());
			    	//actions.add(SetCustomLineItemCustomField.ofObject("refTaller", cartQueryParamsDTO.getRefTaller(), lineItemId));
			    }
			    actions.add(CartSetCustomLineItemCustomFieldActionBuilder.of()
						.customLineItemId(lineItemId)
						.name("esPack")
						.value(cartQueryParamsDTO.isEsPack())
						.build());
			    //actions.add(SetCustomLineItemCustomField.ofObject("esPack", cartQueryParamsDTO.isEsPack(), lineItemId));
			    
			    if (cartQueryParamsDTO.getDescPack()!= null) {
			    	actions.add(CartSetCustomLineItemCustomFieldActionBuilder.of()
			    			.customLineItemId(lineItemId)
			    			.name("descPack")
			    			.value(cartQueryParamsDTO.getDescPack())
			    			.build());
			    	//actions.add(SetCustomLineItemCustomField.ofObject("descPack", cartQueryParamsDTO.getDescPack(), lineItemId));
			    }
			    if (cartQueryParamsDTO.getRefPack()!= null) {
			    	actions.add(CartSetCustomLineItemCustomFieldActionBuilder.of()
			    			.customLineItemId(lineItemId)
			    			.name("refPack")
			    			.value(cartQueryParamsDTO.getRefPack())
			    			.build());
			    	//actions.add(SetCustomLineItemCustomField.ofObject("refPack", cartQueryParamsDTO.getRefPack(), lineItemId));
			    }
			}
			
			if(cart != null) {
				if(!updatedField.equals("all")) {
					CartSetCustomLineItemCustomFieldAction action = CartSetCustomLineItemCustomFieldActionBuilder.of()
					.customLineItemId(lineItemId)
					.name(updatedField)
					.value(nuevoValue)
					.build();
					
					CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(action).build();
					updatedCart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartUpdate).executeBlocking().getBody().get();	
//		    	updatedCart = cioneEcommerceConectionProvider.getClient()
//		    			.executeBlocking(CartUpdateCommand.of(cart, SetCustomLineItemCustomField.ofObject(updatedField, nuevoValue, lineItemId)));
				} else if (updatedField.equals("step")) { 
					//actualizamos el campo step de todas sus customLineItems
					if (cartQueryParamsDTO.getIdCarritoOculto() == null)
						return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updateCustomFieldCLI no identificado el idCarrito").build();
					
					cart = getCartById(cartQueryParamsDTO.getIdCarritoOculto());
					actions = cart.getCustomLineItems().stream()
			            .map(customLineItem -> CartSetCustomFieldActionBuilder.of()
			                .name("step")
			                .value(String.valueOf(cartQueryParamsDTO.getStep()))
			                .build()
			            )
			            .collect(Collectors.toList());
					CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(actions).build();
					updatedCart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartUpdate).executeBlocking().getBody().get();	
				} else {
					CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(actions).build();
					updatedCart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartUpdate).executeBlocking().getBody().get();	
//	    		updatedCart = cioneEcommerceConectionProvider.getClient()
//	    			.executeBlocking(CartUpdateCommand.of(cart, actions));
				}
				
				try {
					String cartResponse = getBackwardCompatibilityCartAsString(updatedCart);
					return Response.ok(cartResponse).build();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					return Response.ok(updatedCart).build();
				}
			} else
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updateCustomField " + updatedField).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updateCustomField " + updatedField).build();
		}
	}
	
	public Response updateSASNoteField(CartParamsDTO cartQueryParamsDTO) {
		
		Cart updatedCart = null;
		
		CustomerCT customer = commercetoolsService.getCustomer();
		cartQueryParamsDTO.setCustomerGroup(customer.getCustomerGroup().getId());
	    Cart cart = getCart(customer.getId());
	    
	    String notaSAS = cartQueryParamsDTO.getNotaSAS();
	    List<CartUpdateAction> actions = new ArrayList<>();
	    //List<UpdateAction<Cart>> actions = new ArrayList<>();
	    Map<String, Object> obj = new HashMap<>();
		obj.put("notaSAS", notaSAS);
		
		TypeResourceIdentifier customType = TypeResourceIdentifierBuilder.of().key("customFields-forOrders").build();
		FieldContainer fieldContainer = FieldContainerBuilder.of().values(obj).build();
		CartSetCustomTypeAction cartSetCustomTypeAction = CartSetCustomTypeActionBuilder.of().type(customType).fields(fieldContainer).build();
		actions.add(cartSetCustomTypeAction);
		
		CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(actions).build();
		
	    //actions.add(SetCustomType.ofTypeKeyAndObjects("customFields-forOrders", obj));
	    
	    //if(cart != null) {
	    	updatedCart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartUpdate).executeBlocking().getBody().get();	
	    	//updatedCart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(cart, actions));
	    	try {
				String cartResponse = getBackwardCompatibilityCartAsString(updatedCart);
				return Response.ok(cartResponse).build();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return Response.ok(updatedCart).build();
			}
	    //} else
	    	//return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updateSASNoteField").build();
	}
	
	public Response prepareCart() {
		
		Cart updatedCart = null;
		List<CartUpdateAction> actions = new ArrayList<>();
		//List<UpdateAction<Cart>> actions = new ArrayList<>();
		
		CustomerCT customer = commercetoolsService.getCustomer();
		Cart cart = getCart(customer.getId());
		
		
		boolean validCart = isValidCart(cart);
		if (!validCart) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(i18n.translate(MyshopConstants.I18N_ERROR_PACK_MONTURAS)).build();
		}
		
	    
	    if(cart == null)
	    	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error preparing cart for an order").build();
	    
	    //actualiza las posibles modificaciones del carrito
	    CartRecalculateAction cartRecalculateAction = CartRecalculateActionBuilder.of().updateProductData(true).build();
	    CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(cartRecalculateAction).build();
	    cart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartUpdate).executeBlocking().getBody().get();
	    
	    //cart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(cart, Recalculate.of().withUpdateProductData(true)));
	    
	    for(LineItem item: cart.getLineItems()) {
	    	if (item.getCustom().getFields().values().get("tipoPrecioPack") == null) { //si no es un pack generico compruebo
		    	VariantDTO promocionItem = getPromocionesByVariant(item.getVariant());
		    	if(promocionItem.isTienePromociones() || promocionItem.getPvoIncremento()!= null) {
		    		
		    		TypedMoney precioEnCarrito;
					
					if (item.getCustom().getFields().values().get("pvoConDescuento") != null)
						precioEnCarrito = (TypedMoney)item.getCustom().getFields().values().get("pvoConDescuento");
	//				
	//				if(item.getCustom().getFieldAsMoney("pvoConDescuento") != null)
	//					precioEnCarrito = item.getCustom().getFieldAsMoney("pvoConDescuento");
					else {
						
						precioEnCarrito = item.getPrice().getValue();//MyShopUtils.getMoney(item.getPrice().getValue().getCentAmount());
					}
						
					
					String precio = promocionItem.getPvoDto() != null ? promocionItem.getPvoDto() : promocionItem.getPvo();
					
					Money precioReal =  MyShopUtils.getMoney(precio);//Money.of(new BigDecimal(precio.replace(",", ".")), "EUR");
					if(promocionItem.getTipoPromocion().equals("escalado")) {
						for(PromocionesDTO promo: promocionItem.getListPromos()) {
							if(promo.getCantidad_desde() <= item.getQuantity() && item.getQuantity() <= promo.getCantidad_hasta())
								precioReal = MyShopUtils.getMoney(promo.getPvoDto());
								//precioReal = Money.of(new BigDecimal(promo.getPvoDto().replace(",", ".")), "EUR");
						}
					}
					
					if (precioReal.getCentAmount().equals(precioEnCarrito.getCentAmount())) //PROBAR BIEN, igual hay que hacer el precioReal.getCentAmount
						actions.add(CartSetLineItemCustomFieldActionBuilder.of().lineItemId(item.getId()).name("pvoConDescuento").value(precioReal).build());
					//if(!precioReal.isEqualTo(precioEnCarrito))
						//actions.add(SetLineItemCustomField.ofObject("pvoConDescuento", precioReal, item.getId()));
		    	} 
		    	//este else elimina la promocion salvo que sea un pack
		    	else
		    		
		    		
		    		if (item.getCustom().getFields().values().get("refPackPromos") == null)
		    			if (item.getCustom().getFields().values().get("pvoConDescuento") != null)
		    				actions.add(CartSetLineItemCustomFieldActionBuilder.of().lineItemId(item.getId()).name("pvoConDescuento").value(null).build());  
	//	    		if (item.getCustom().getFieldAsString("refPackPromos") == null) 
	//	    			if(item.getCustom().getFieldAsMoney("pvoConDescuento") != null)
	//	    				actions.add(SetLineItemCustomField.ofUnset("pvoConDescuento", item.getId()));
	    	}
	    }
	    
	    if(actions.size() > 0) {
	    	log.debug("Actualizamos el carrito con los nuevos precios");
	    	CartUpdate cartUpdatePVOS = CartUpdateBuilder.of().version(cart.getVersion()).actions(actions).build();
	    	updatedCart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartUpdatePVOS).executeBlocking().getBody().get();	
	    	//updatedCart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(cart, actions));
	    } else
	    	updatedCart = cart;
	    
	    try {
			String cartResponse = getBackwardCompatibilityCartAsString(updatedCart);
			return Response.ok(cartResponse).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Response.ok(updatedCart).build();
		}
	}
	
	private boolean isValidCart(Cart cart) {
		boolean packLente = false;
		boolean montura = false;
		//List<CustomLineItem> customLineItems = cart.getCustomLineItems();
		
		//compruebo si ha marcado el check Pack montura+lentes
		for (LineItem lineItem: cart.getLineItems()) {
			if (lineItem.getCustom().getFields().values().get("esPack") != null && ((Boolean)lineItem.getCustom().getFields().values().get("esPack"))) {
				packLente = true;
				break;
			}
		}		
		for (CustomLineItem customlineItem: cart.getCustomLineItems()) {
			if (customlineItem.getCustom().getFields().values().get("esPack") != null && ((Boolean)customlineItem.getCustom().getFields().values().get("esPack"))) {
				packLente = true;
				break;
			}
			
		}
		
		if (packLente) {
			List<String> skus = new ArrayList<>();
			int lentes=0;
			int trabajos=0;
			List<LineItem> lineItems = cart.getLineItems();
			lineItems.forEach(lineItem -> skus.add(lineItem.getVariant().getSku()));
			
			if (!skus.isEmpty()) {
				List<ProductProjection> products = commercetoolsService.getProductProjectionBySkuList(skus);
				
				for (ProductProjection product : products) {
					List<CategoryReference> refCategories = product.getCategories();
					for(CategoryReference ref: refCategories) {
						Category category = categoryService.getCategoryById(ref.getId());
						if ((category.getKey().equals(MyshopConstants.C_GAFAS_GRADUADAS)) 
								|| (category.getKey().equals(MyshopConstants.C_GAFAS_SOL))) {
							//comprueba que sea un producto de categoria gafas graduadas y tenga marcada la opcion check
							for (LineItem lineItem : lineItems) {
								if (lineItem.getCustom().getFields().values().get("esPack") != null && ((Boolean) lineItem.getCustom().getFields().values().get("esPack"))) {
									montura = true;
									break;
								}
							}							
						}
					}
				}
				
				
				List<CustomLineItem> customlineItems = cart.getCustomLineItems();
				for (CustomLineItem custom : customlineItems) {
					String idtype = custom.getCustom().getType().getId();
					String key = cioneEcommerceConectionProvider.getApiRoot().types().withId(idtype).get().executeBlocking().getBody().getKey();
						
					//Type fetchedType = cioneEcommerceConectionProvider.getClient().executeBlocking(TypeByIdGet.of(idtype));
					switch (key) {
					case "customlineitem-cionelab-lenses":
						if (custom.getCustom().getFields().values().get("esPack") != null && ((Boolean)custom.getCustom().getFields().values().get("esPack")))
							lentes++;
						break;
					case "customlineitem-cionelab-jobs":
						if (custom.getCustom().getFields().values().get("esPack") != null && ((Boolean)custom.getCustom().getFields().values().get("esPack")))
							trabajos++;
						break;
					default:
						break;
					}
				}
			}
			
			if (!montura || trabajos <4 || lentes<2) {
				return false;
			}
			
		}
		
		
		return true;
	}
	
	
	/*public void updateOrder() {
		String id= "d810f06a-ea4a-489b-906a-3ebe6a3993c8";
		Order orderSET = cioneEcommerceConectionProvider.getClient().executeBlocking(OrderByIdGet.of(id));
		if (orderSET != null) {
			String orderNumber = generateOrderNumber("MYS");
			try {
				orderSET = cioneEcommerceConectionProvider.getClient().executeBlocking(OrderUpdateCommand.of(orderSET, SetOrderNumber.of(orderNumber)));
		    } catch (Exception ex) {
		    	log.error("Error al actualiar el pedido");
		    }
		}
		
	}
	
	//metodo para setear el orderNumber de manera manual en caso de error
	public void updateOrderNumber() {
		try {
			
			
			Order orderSET = cioneEcommerceConectionProvider.getClient().executeBlocking(OrderByIdGet.of("b6a45db8-3b24-4e3a-b062-77bd24f1b772"));
			log.debug(orderSET.getCustomerId());
			orderSET = cioneEcommerceConectionProvider.getClient().executeBlocking(OrderUpdateCommand.of(orderSET, SetOrderNumber.of("MYS230424185982")));
		} catch (Exception e) {
			log.error("No se ha podido cambiar el orderNumber");
		}
	}*/


	public Response cartToOrder() {
		//MonetaryAmount pvoFinalAcumulado = Money.of(BigDecimal.ZERO, "EUR");
		Money pvoFinalAcumulado = MyShopUtils.getMoney(0);
		
		List<CartUpdateAction> actions = new ArrayList<>();
		//List<UpdateAction<Cart>> actions = new ArrayList<>();
		
		Cart updatedCart = null;
		Order order = null;
		
		Customer customer = commercetoolsService.getCustomerSDK();
	    Cart cart = getCart(customer.getId());
	    
	    String customerNumber = customer.getCustomerNumber();
	    
	    if(cart != null) {
	    	
	    	for (LineItem item: cart.getLineItems()) {
	    		
	    		
	    		
				if (item.getCustom().getFields().values().get("pvoConDescuento") != null) {
					CentPrecisionMoney pvoConDescuento = (CentPrecisionMoney)item.getCustom().getFields().values().get("pvoConDescuento");
					pvoFinalAcumulado.setCentAmount(pvoFinalAcumulado.getCentAmount() + pvoConDescuento.getCentAmount() * item.getQuantity());
				} else
					pvoFinalAcumulado.setCentAmount(pvoFinalAcumulado.getCentAmount() + item.getTotalPrice().getCentAmount());
					//pvoFinalAcumulado = pvoFinalAcumulado.add(item.getTotalPrice());
			}
	    	
	    	
	    	for (CustomLineItem item: cart.getCustomLineItems()) {
	    		if (item.getCustom().getFields().values().get("pvoConDescuento_L") != null) {
					CentPrecisionMoney pvoConDescuento = (CentPrecisionMoney)item.getCustom().getFields().values().get("pvoConDescuento_L");
					pvoFinalAcumulado.setCentAmount(pvoFinalAcumulado.getCentAmount() + pvoConDescuento.getCentAmount() * item.getQuantity());
	    		} else if (item.getCustom().getFields().values().get("pvoConDescuento_R") != null) {
					CentPrecisionMoney pvoConDescuento = (CentPrecisionMoney)item.getCustom().getFields().values().get("pvoConDescuento_R");
					pvoFinalAcumulado.setCentAmount(pvoFinalAcumulado.getCentAmount() + pvoConDescuento.getCentAmount() * item.getQuantity());
	    		} else
	    			pvoFinalAcumulado.setCentAmount(pvoFinalAcumulado.getCentAmount() + item.getTotalPrice().getCentAmount());
	    		
	    		
			}
	    	
		    if(cart.getCustom() == null) {
				Map<String, Object> obj = new HashMap<>();
				obj.put("customerNumber", customerNumber);
				obj.put("codSocio", customerNumber.substring(0, customerNumber.length() - 2));
				obj.put("pvoFinalAcumulado", pvoFinalAcumulado);
				obj.put("RegisteredBy", MgnlContext.getUser().getName());
				
				TypeResourceIdentifier customType = TypeResourceIdentifierBuilder.of().key("customFields-forOrders").build();
				FieldContainer fieldContainer = FieldContainerBuilder.of().values(obj).build();
				CartSetCustomTypeAction cartSetCustomTypeAction = CartSetCustomTypeActionBuilder.of().type(customType).fields(fieldContainer).build();
				actions.add(cartSetCustomTypeAction);
				
				//actions.add(SetCustomType.ofTypeKeyAndObjects("customFields-forOrders", obj));
	
			} else {
				
				actions.add(CartSetCustomFieldActionBuilder.of().name("customerNumber").value(customerNumber).build());
				//actions.add(SetCustomField.ofObject("customerNumber", customerNumber));
			    
				actions.add(CartSetCustomFieldActionBuilder.of().name("codSocio").value(customerNumber.substring(0, customerNumber.length() - 2)).build());
			    //actions.add(SetCustomField.ofObject("codSocio", customerNumber.substring(0, customerNumber.length() - 2)));
			    
				actions.add(CartSetCustomFieldActionBuilder.of().name("pvoFinalAcumulado").value(pvoFinalAcumulado).build());
			    //actions.add(SetCustomField.ofObject("pvoFinalAcumulado", pvoFinalAcumulado));
			    
				actions.add(CartSetCustomFieldActionBuilder.of().name("RegisteredBy").value(MgnlContext.getUser().getName()).build());
			    //actions.add(SetCustomField.ofObject("RegisteredBy", MgnlContext.getUser().getName()));
			}
		    
		    CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(actions).build();
		    updatedCart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cart.getId()).post(cartUpdate).executeBlocking().getBody().get();	
		    //updatedCart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(cart, actions));
		    
		    CartResourceIdentifier cartId = CartResourceIdentifierBuilder.of().id(updatedCart.getId()).key(updatedCart.getKey()).build();
		    OrderFromCartDraft orderFromCartDraft = OrderFromCartDraftBuilder.of()
	                .cart(cartId)
	                .version(updatedCart.getVersion())
	                .build();
			order = cioneEcommerceConectionProvider.getApiRoot().orders().post(orderFromCartDraft).executeBlocking().getBody();
		    //order = cioneEcommerceConectionProvider.getClient().executeBlocking(OrderFromCartCreateCommand.of(updatedCart));
		    
		    String orderNumber = generateOrderNumber("MYS");
		    /*Order orderCheck = cioneEcommerceConectionProvider.getClient().executeBlocking(OrderByOrderNumberGet.of(orderNumber));
		    if (orderCheck != null) {
		    	log.info("EXISTE UN PEDIDO " + orderCheck.getId() + " con el orderNumber " + orderNumber);
		    	orderNumber = generateOrderNumber("MYS");
		    	log.info("GENERADO orderNumber " + orderNumber);
		    }*/
		    
		    
		    
		    String id = order.getId();
		    try {
		    	OrderSetOrderNumberAction orderNumberAction = OrderSetOrderNumberActionBuilder.of().orderNumber(orderNumber).build();
		    	OrderUpdate orderUpdate = OrderUpdateBuilder.of().version(order.getVersion()).actions(orderNumberAction).build();
		    	order = cioneEcommerceConectionProvider.getApiRoot().orders().withId(id).post(orderUpdate).executeBlocking().getBody().get();
		    	//order = cioneEcommerceConectionProvider.getClient().executeBlocking(OrderUpdateCommand.of(order, SetOrderNumber.of(orderNumber)));
		    } catch (Exception ex) {
		    	boolean second = true;
		    	try {
		    		orderNumber = generateOrderNumber("MYS");
		    		OrderSetOrderNumberAction orderNumberAction = OrderSetOrderNumberActionBuilder.of().orderNumber(orderNumber).build();
			    	OrderUpdate orderUpdate = OrderUpdateBuilder.of().version(order.getVersion()).actions(orderNumberAction).build();
			    	order = cioneEcommerceConectionProvider.getApiRoot().orders().withId(id).post(orderUpdate).executeBlocking().getBody().get();
			    	
		    		//CompletionStage<Order> orderAsync = cioneEcommerceConectionProvider.getClient().execute(OrderUpdateCommand.of(order, SetOrderNumber.of(orderNumber)));
		    		//order = orderAsync.toCompletableFuture().join();
		    		second = false;
		    	} catch (Exception ex2) {
		    		log.error(ex.getMessage(), ex2);
			    	String subject = "Error al setear orderNumber de forma asincrona (reintento)";
			    	String mailto = "msanchezp@knowmadmood.com;";
			    	String texto = "Se ha producido un error al setear el orderNumer: " + orderNumber + " del pedido del socio: " + CioneUtils.getIdCurrentClient() + " del pedido: " + order.getId();
			    	texto += "</br>";
			    	texto += "</br>";
			    	texto += "ERROR:";
			    	texto += "</br>";
			    	texto += ex2.getMessage();
			    	try {
						emailService.sendEmail(subject, texto, mailto);
					} catch (CioneException e) {
						log.error("ERROR en el envio de mail");
					}
		    	}
		    	log.error(ex.getMessage(), ex);
		    	//si tampoco lo puede ejecutar de manera asincrona mandamos el aviso
		    	if (second) {
			    	String subject = "Error al setear orderNumber";
			    	String mailto = "msanchezp@knowmadmood.com;juan.ara@cione.es;miguel.delahoz@cione.es;";
			    	String texto = "Se ha producido un error al setear el orderNumer: " + orderNumber + " al pedido del socio: " + CioneUtils.getIdCurrentClient() 
			    		+ " del pedido con id: " + id;
			    	texto += "</br>";
			    	texto += "</br>";
			    	texto += "ERROR:";
			    	texto += "</br>";
			    	texto += ex.getMessage();
			    	try {
						emailService.sendEmail(subject, texto, mailto);
					} catch (CioneException e) {
						log.error("ERROR en el envio de mail");
					}
		    	}
		    }
		    
		    /*while(!orderNumberSet) {
		    	randomString = String.format("%03d", secureRandom.nextInt());
	    		orderNumber = orderNumber.substring(0, orderNumber.length() - 3) + randomString.substring(randomString.length() - 3);
	    		try {
	    			order = cioneEcommerceConectionProvider.getClient().executeBlocking(OrderUpdateCommand.of(order, SetOrderNumber.of(orderNumber)));
			    	orderNumberSet = true;
			    } catch (Exception ex) {}
		    }*/
		    
		    log.debug("Pedido " + orderNumber + " con id " + order.getId() + " generado para el cliente " + customerNumber);
		    
		    //volvemos a crear un carrito para que este disponible en cioneLab
		    createCart(customer, MyshopConstants.deleteDaysAfterLastModification);
		    
		    try {
				String orderResponse = getBackwardCompatibilityOrderAsString(order);
				return Response.ok(orderResponse).build();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return Response.ok(order).build();
			}
	    	
	    } else
	    	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating order from cart").build();
	}
	
	private String generateOrderNumber(String type) {
	    String orderNumber = orderNumber(type);
	    boolean encontrado = false;
	    int reintentos = 0;
	    
	    while ((encontrado) && (reintentos < 3)){
	    	Order order = cioneEcommerceConectionProvider.getApiRoot().orders().withOrderNumber(orderNumber).get().executeBlocking().getBody().get();
	    	if (order == null) {
		    	return orderNumber;
		    } else {
		    	reintentos ++;
		    }
	    }
	   
	    return orderNumber;
	}
	
	private String orderNumber(String type) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	    String timestampTime = String.valueOf(timestamp.getTime());
	    
	    String randomString = generateSecureRandomString(4);
	    
	    SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
	    String orderNumber = type + format.format(new Date()) + timestampTime.substring(timestampTime.length() - 4) + randomString;
	    
	    return orderNumber;
	}
	
	private static String generateSecureRandomString(int length) {
		String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALPHANUMERIC.length());
            char randomChar = ALPHANUMERIC.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }


	@Override
	public Response updateQuantityCLI(String cartId, String lineItemId, Integer quantity, String quoteId, info.magnolia.ecommerce.common.Customer customer, Connection connection) {
        try {
        	return commercetoolsCartProvider.updateQuantity(cartId, lineItemId, quantity, quoteId, customer, connection);
            //return Response.ok(cart).build();
        } catch (Exception e) {
            log.error("Couldn't update quantity in cart with id {}.", cartId, e);
            clientProvider.closeClient(connection);
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
	}
	
	public Response updateQuantityCLICM(String cartId, String lineItemId, Integer quantity, String quoteId, com.commercetools.api.models.customer.Customer customer, Connection connection) {
        try {
        	
        	Long cartVersion = getCart(customer.getId()).getVersion();
 
        	CartChangeCustomLineItemQuantityAction cartChangeQuantityAction = 
        		CartChangeCustomLineItemQuantityActionBuilder
        		.of()
        		.customLineItemId(lineItemId)
        		.quantity(Long.valueOf(quantity.longValue()))
        		.build();
        	
        	CartUpdate cartupdate = CartUpdateBuilder.of().actions(new CartUpdateAction[] { (CartUpdateAction)cartChangeQuantityAction }).version(cartVersion).build();
        	Cart cart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cartId).post(cartupdate).executeBlocking().getBody().get();
    		try {
    			String cartResponse = getBackwardCompatibilityCartAsString(cart);
    			return Response.ok(cartResponse).build();
    		}catch (Exception e) {
    			return Response.ok(cart).build();
    		}
        } catch (Exception e) {
            log.error("Couldn't update quantity in cart with id {}.", cartId, e);
            clientProvider.closeClient(connection);
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
	}
	
	@Override
	public Money getPvoByCart(Cart cart) {
		Money total = MyShopUtils.getMoney(0);
		
		for (LineItem item: cart.getLineItems()) {
			
			boolean enDeposito = false;
			
			if (item.getCustom().getFields().values().get(MyshopConstants.ENDEPOSITO) != null) {
				enDeposito = (boolean) item.getCustom().getFields().values().get(MyshopConstants.ENDEPOSITO);
			}
			
			if(!enDeposito) {
				
				if ((item.getCustom() != null) && (item.getCustom().getFields().values().get("pvoConDescuento") != null)) {
					Long suma = (((CentPrecisionMoney)item.getCustom().getFields().values().get("pvoConDescuento")).getCentAmount() * item.getQuantity()) + total.getCentAmount();;
					total.setCentAmount(suma);
					
				} else {
					Long suma = (item.getTotalPrice().getCentAmount()) + total.getCentAmount();
					total.setCentAmount(suma);
					
				}
			}
		}
		
		for (CustomLineItem item: cart.getCustomLineItems()) {
			Long suma = (item.getTotalPrice().getCentAmount()) + total.getCentAmount();
			if (item.getCustom().getFields().values().get("pvoConDescuento_L")!= null) {
				suma = (((CentPrecisionMoney)item.getCustom().getFields().values().get("pvoConDescuento_L")).getCentAmount()) + total.getCentAmount();
			} else if (item.getCustom().getFields().values().get("pvoConDescuento_R")!= null) {
				suma = (((CentPrecisionMoney)item.getCustom().getFields().values().get("pvoConDescuento_R")).getCentAmount()) + total.getCentAmount();
			}
			total.setCentAmount(suma);
		}
		
		return total;
	}

	/*@Override
	public Response addCartToShoppingList(Connection connection) {
		
		final String sku = "AMADRID370";
        final io.sphere.sdk.shoppinglists.LineItemDraftDsl lineItemBySku = io.sphere.sdk.shoppinglists.LineItemDraftBuilder.ofSku(sku, 1L).build();
        
        final LocalizedString name = LocalizedString.of(MyshopConstants.esLocale, "test");
        ShoppingListDraftBuilder shoppinglistdraftbuilder = ShoppingListDraftBuilder.of(name);
        
        final ShoppingListDraft shoppingListDraft = shoppinglistdraftbuilder
                .key("demo-shopping-list-key")
                .plusLineItems(lineItemBySku)
                .build();
        
        final ShoppingList shoppingList = clientProvider.get(connection).executeBlocking(ShoppingListCreateCommand.of(shoppingListDraft).withExpansionPaths(m -> m.lineItems()));
        clientProvider.get(connection).executeBlocking(ShoppingListDeleteCommand.of(shoppingList));

        return Response.status(Response.Status.OK).entity("OK").build();
	}*/
	
	private String getBackwardCompatibilityCartAsString(Cart cart) throws JsonProcessingException {
	    Map<String, Object> cartMap = (Map<String, Object>)this.objectMapper.convertValue(cart, 
	    		new TypeReference<Map<String, Object>>() {
	    });
	    cartMap.put("currencyCode", cart.getTotalPrice().getCurrencyCode());
	    return JsonUtils.toJsonString(cartMap);
	}
	
	private String getBackwardCompatibilityOrderAsString(Order order) throws JsonProcessingException {
	    Map<String, Object> cartMap = (Map<String, Object>)this.objectMapper.convertValue(order, 
	    		new TypeReference<Map<String, Object>>() {
	    });
	    cartMap.put("currencyCode", order.getTotalPrice().getCurrencyCode());
	    return JsonUtils.toJsonString(cartMap);
	}

	@Override
	public Response removeCLI(String cartId, String customLineItemId, String customer) {
		try {
			
			@NotNull Long version = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cartId).get().executeBlocking().getBody().get().getVersion();
			
			CartRemoveCustomLineItemAction action = CartRemoveCustomLineItemActionBuilder.of().customLineItemId(customLineItemId).build();
			CartUpdate cartUpdate = CartUpdateBuilder.of().version(version).actions(action).build();
			Cart cart =cioneEcommerceConectionProvider.getApiRoot().carts().withId(cartId).post(cartUpdate).executeBlocking().getBody().get();
			try {
				String cartResponse = getBackwardCompatibilityCartAsString(cart);
				return Response.ok(cartResponse).build();
			}catch (Exception e) {
				return Response.ok(cart).build();
			}
			//return commercetoolsCartProvider.removeItem(cartId, lineItemId, customer, connection);

        } catch (Exception e) {
            log.error("Couldn't remove item from cart with id {}.", cartId, e);
            //commercetoolsCartProvider.closeClient(connection);
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
//        try {
//        	
//        	Cart cart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cartId).get().executeBlocking().getBody().get();
//        	
//        	CartRemoveCustomLineItemAction cartRemoveCustomLineItemAction = CartRemoveCustomLineItemActionBuilder.of().customLineItemId(lineItemId).build();
//        	CartUpdate cartupdate = CartUpdateBuilder.of().actions(new CartUpdateAction[] { (CartUpdateAction)cartRemoveCustomLineItemAction }).version(cart.getVersion()).build();
//        	cart = cioneEcommerceConectionProvider.getApiRoot().carts().withId(cartId).post(cartupdate).executeBlocking().getBody().get();
//            return Response.ok(cart).build();
//        } catch (Exception e) {
//            log.error("Couldn't remove item from cart with id {}.", cartId, e);
//            clientProvider.closeClient(connection);
//            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
//        }
	}
	
	private Map<String, GiftProduct> getGiftList(CartParamsDTO cartQueryParamsDTO, String grupoPrecio) {
		Map<String,GiftProduct> productosRegalo = new HashMap<>();
		ProductProjection productPack = commercetoolsService.getProductBysku(cartQueryParamsDTO.getSkuPackPadre(), grupoPrecio);
		
		ProductVariant variant = commercetoolsServiceAux.findVariantBySku(productPack, cartQueryParamsDTO.getSkuPackPadre());
		//ProductVariant variant = productPack.findVariantBySku(cartQueryParamsDTO.getSkuPackPadre()).get();
		if (MyShopUtils.hasAttribute("listadoProductosRegalo", variant.getAttributes())) {
		
			ArrayList<String> listRegalos = (ArrayList<String>) MyShopUtils.findAttribute("listadoProductosRegalo", variant.getAttributes()).getValue();
					
				
				if (listRegalos != null) {
					for (String tipoRegalo: listRegalos) {
						VariantDTO regalo = new VariantDTO();
						StringTokenizer it = new StringTokenizer(tipoRegalo, "#");
						if (it.hasMoreElements()) {
							regalo.setTipoRegalo(it.nextToken());
						}
						if (it.hasMoreElements()) {
							regalo.setUnidadesPack(Integer.valueOf(it.nextToken()));
						}else {
							regalo.setUnidadesPack(1);
						}
						regalo.setNombreArticulo(regalo.getTipoRegalo());
						regalo.setPvoPackUD("0.00");
						regalo.setPvo("0.00");
						
						GiftProduct gift = new GiftProduct();
						gift.setPvo(regalo.getPvoPackUD());
						gift.setType(regalo.getTipoRegalo().toLowerCase());
						gift.setUnits(regalo.getUnidadesPack());
						
						productosRegalo.put(gift.getType(), gift);
					}
				}
			
		}
		return productosRegalo;
	}


	@Override
	public String getPvo(String sku) {
		String[] skus = sku.split(",");
		TypedMoney total =MyShopUtils.getMonetaryAmount(0);
		//MonetaryAmount total = MyShopUtils.getMonetaryAmount(0);
		for (String value : skus) {
			ProductVariant variant = productService.getVariantBySku(value);
			if (variant != null) {
				Price price = commercetoolsService.getPriceBycustomerGroup(commercetoolsService.getGrupoPrecioCommerce(), variant.getPrices());
				TypedMoney amount = price.getValue();
				//total = total.add(amount);
				Long suma = amount.getCentAmount() + total.getCentAmount();
				total.setCentAmount(suma);
			}
		}
		return MyShopUtils.formatTypedMoney(total);
	}
	
	/*
	 * A partir de los productos que tiene en el carrito el usuario comprobamos si estan marcados con tieneCompraMinima
	 * Si tieneCompraMinima los agrupamos por marca en listFamiliaMarca y por proveedor listFamiliaProveedor, contabilizando las unidades de 
	 * productos agrupados para validar si cumplen la condicion de compra minima que nos devuelve el servicio del middleware
	 * */
	@Override
	public Response isValidaCartMinimunPurchase(String language, String contentNodePath) {
		CustomerCT customer = commercetoolsService.getCustomer();
		Cart cart = getCart(customer.getId());
		
		HashMap<String,MarcaProveedor> listFamiliaProveedor = new HashMap<String, MarcaProveedor>();
		HashMap<String,MarcaProveedor> listFamiliaMarca = new HashMap<String, MarcaProveedor>();
		HashMap<String,MarcaProveedor> listFamiliaMarcaProveedor = new HashMap<String, MarcaProveedor>();
		for (LineItem lineItem: cart.getLineItems()) {
			if (MyShopUtils.hasAttribute("tieneCompraMinima", lineItem.getVariant().getAttributes())) {
    			boolean tieneCompraMinima = (boolean) MyShopUtils.findAttribute("tieneCompraMinima", lineItem.getVariant().getAttributes()).getValue();
    			if (tieneCompraMinima) {
    				String codFamilia = null;
    				if (MyShopUtils.hasAttribute("codFamilia", lineItem.getVariant().getAttributes())) {
    	    			codFamilia = (String) MyShopUtils.findAttribute("codFamilia", lineItem.getVariant().getAttributes()).getValue();
    	    		}
    				if (MyShopUtils.hasAttribute("codMarca", lineItem.getVariant().getAttributes()) 
    						&& MyShopUtils.hasAttribute("codProveedor", lineItem.getVariant().getAttributes())
    						&& MyShopUtils.hasAttribute("proveedor", lineItem.getVariant().getAttributes()) ) {
    					String marca = (String) MyShopUtils.findAttribute("codMarca", lineItem.getVariant().getAttributes()).getValue();
    	    			String proveedor = (String) MyShopUtils.findAttribute("codProveedor", lineItem.getVariant().getAttributes()).getValue();
    	    			String descProveedor = (String) MyShopUtils.findAttribute("proveedor", lineItem.getVariant().getAttributes()).getValue();
    	    			
    	    			//formamos el map con el conjunto de par codfamilia+proveedor y las unidades de productos en el carrito que 
    	    			String keyproveedor = codFamilia+"|"+proveedor;
    	    			if (listFamiliaProveedor.get(keyproveedor) != null) {
    	    				MarcaProveedor familiaProveedor = listFamiliaProveedor.get(keyproveedor);
    	    				familiaProveedor.setCantidad(familiaProveedor.getCantidad() + lineItem.getQuantity().intValue());
    	    				listFamiliaProveedor.put(keyproveedor , familiaProveedor);
    	    			} else
    	    				listFamiliaProveedor.put(keyproveedor , new MarcaProveedor(codFamilia, marca, proveedor, descProveedor, lineItem.getQuantity().intValue()));
    	    			
    	    			String keymarca = codFamilia+"|"+marca;
    	    			if (listFamiliaMarca.get(keymarca) != null) {
    	    				MarcaProveedor familiaMarcaProveedor = listFamiliaMarca.get(keymarca);
    	    				familiaMarcaProveedor.setCantidad(familiaMarcaProveedor.getCantidad() + lineItem.getQuantity().intValue());
    	    				listFamiliaMarca.put(keymarca , familiaMarcaProveedor);
    	    			} else
    	    				listFamiliaMarca.put(keymarca , new MarcaProveedor(codFamilia, marca, proveedor, descProveedor, lineItem.getQuantity().intValue()));
    	    			
    	    			String key = codFamilia+"|"+marca+"|"+proveedor;
    	    			if (listFamiliaMarcaProveedor.get(key) != null) {
    	    				MarcaProveedor familiaMarcaProveedor = listFamiliaMarcaProveedor.get(key);
    	    				familiaMarcaProveedor.setCantidad(familiaMarcaProveedor.getCantidad() + lineItem.getQuantity().intValue());
    	    				listFamiliaMarcaProveedor.put(key , familiaMarcaProveedor);
    	    			} else
    	    				listFamiliaMarcaProveedor.put(key , new MarcaProveedor(codFamilia, marca, proveedor, descProveedor, lineItem.getQuantity().intValue()));
    	    		}
    				
    			}
    		}
		}
		
		String codSocio = CioneUtils.getIdCurrentClientERP();
		String grupoPrecio = commercetoolsService.getGrupoPrecioCommerce();
		
		JSONObject jsonRes = new JSONObject();
		
		boolean flag = true;
		List<String> mensajesMarca = new ArrayList<>();
        List<String> mensajesProveedor = new ArrayList<>();
        
        Locale locale = new Locale(language);
        ResourceBundle rbundle = ResourceBundle.getBundle("cione-module/i18n/module-cione-module-messages", locale);
        
		for (Map.Entry<String, MarcaProveedor> set : listFamiliaMarcaProveedor.entrySet()) {
			MarcaProveedor marcaProveedor = set.getValue();			
			CompraMinimaDTO cm = middlewareService.getCompraMinima(codSocio, grupoPrecio, marcaProveedor.getMarca(), marcaProveedor.getProveedor(), marcaProveedor.getFamilia());
			//CompraMinimaDTO cm = middlewareService.getCompraMinima("0040300", "OPTICAPRO", marcaProveedor.getMarca(), marcaProveedor.getProveedor(), marcaProveedor.getFamilia());
			if (cm.getResultado() == 0)
				return Response.status(Response.Status.BAD_REQUEST).entity(cm.getMensaje()).build();
			
			if (cm.getTipoRestriccion().equals(MyshopConstants.RESTRICCION_COMPRAMINIMA_PROVEEDOR)) {
				MarcaProveedor proveedor = listFamiliaProveedor.get(marcaProveedor.getFamilia()+"|"+marcaProveedor.getProveedor());
				if (proveedor != null) {
					if (cm.isBoolCompraMinima() && (cm.getUdCompraMinima() > proveedor.getCantidad()) ){
						if (flag)
							jsonRes.put("isValidCart", false);
						flag = false;
						
						String category = "";
						if (marcaProveedor.getFamilia().equals("MONT")) {
							if (categoryUtils.getCategoryByKey("monturas") != null) 
								category = categoryUtils.getCategoryByKey("monturas").getId();
						}
						String path = getPathCM(contentNodePath, marcaProveedor.getFamilia());
						if(configService.getConfig().getIsAuthor()) {
							path = "/magnoliaAuthor" + path;
						}
						String href = path + "?category=" + category 
								+ "&variants.attributes.codProveedor=" + marcaProveedor.getProveedor()
								+ "&mandatory=true";
						
						String msnP = rbundle.getString("cione-module.cart.compra-minima.proveedor").replace("?supplier", marcaProveedor.getDescProveedor()).replace("?units", String.valueOf(cm.getUdCompraMinima()));
						String msnPhref = rbundle.getString("cione-module.cart.compra-minima.proveedor-4").replace("?href", href);
						
						mensajesProveedor.add(
							msnP
							+ "<ul>"
								+ "<li>" + rbundle.getString("cione-module.cart.compra-minima.proveedor-2") + "</li>"
								+ "<li>" + rbundle.getString("cione-module.cart.compra-minima.proveedor-3") 
									+ " [" + consultaMarcasDao.getListBrands(proveedor.getProveedor()) + "]" + "</li>"
								+ "<li>"+ msnPhref+ "</li>"
								+ "<li>" 
									+ rbundle.getString("cione-module.cart.compra-minima.marca-2") 
								+ "</li>"
							+ "</ul>");
					}
					listFamiliaProveedor.remove(marcaProveedor.getFamilia()+"|"+marcaProveedor.getProveedor());
				}
			} else { //restriccion por marca
				MarcaProveedor marca = listFamiliaMarca.get(marcaProveedor.getFamilia()+"|"+marcaProveedor.getMarca());
				if (marca != null) {
					if (cm.isBoolCompraMinima() && (cm.getUdCompraMinima() > marca.getCantidad()) ){
						if (flag)
							jsonRes.put("isValidCart", false);
						flag = false;
						
						String category = "";
						if (marca.getFamilia().equals("MONT")) {
							if (categoryUtils.getCategoryByKey("monturas") != null) 
								category = categoryUtils.getCategoryByKey("monturas").getId();
						}
						String path = getPathCM(contentNodePath, marca.getFamilia());
						if(configService.getConfig().getIsAuthor()) {
							path = "/magnoliaAuthor" + path;
						}
						String hrefMarca = path + "?category=" + category 
								+ "&variants.attributes.codMarca=" + marca.getMarca()
								+ "&mandatory=true";
						
						String msnBrand = rbundle.getString("cione-module.cart.compra-minima.marca").replace("?brand", marcaProveedor.getMarca()).replace("?units", String.valueOf(cm.getUdCompraMinima()));
						String msnBrandhref = rbundle.getString("cione-module.cart.compra-minima.marca-4").replace("?href", hrefMarca);
						
						mensajesMarca.add(
							msnBrand
							+ "<ul>"
								+ "<li>"+ msnBrandhref+ "</li>"
								+ "<li>" 
									+ rbundle.getString("cione-module.cart.compra-minima.marca-2") 
								+ "</li>"
							+ "</ul>");
						
					}
					listFamiliaMarca.remove(marcaProveedor.getFamilia()+"|"+marcaProveedor.getMarca());
				}
			}
		}
		if (flag) 
			jsonRes.put("isValidCart", true);
		else {
			jsonRes.put("cabecera", rbundle.getString("cione-module.cart.compra-minima.cabecera"));
			jsonRes.put("proveedor", new JSONArray(mensajesProveedor));
			jsonRes.put("marca", new JSONArray(mensajesMarca));
		}
		String res = jsonRes.toString();
		return generateResponseBuilder(res, Response.Status.OK).build();
	}
	
	private String getPathCM(String contentNodePath, String familia) {
		String path ="";
		try {
			Session session = MgnlContext.getJCRSession(CioneConstants.PAGES_WORKSPACE);
			Node content = nodeUtilities.getNodeIfPossible(contentNodePath, CioneConstants.PAGES_WORKSPACE);
			path = findPath(content, session, familia);
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
		}
		return path;
	}
	
	private String findPath(Node content, Session session, String familia) {
	    try {
			NodeIterator children = content.getNodes();

			while (children.hasNext()) {
			    Node child = children.nextNode();
			    String name = child.getName();

			    // Si cumple el patrón listadoEnlaceFiltrosCompraMinimaX
			    if (name.startsWith("listadoEnlaceFiltrosCompraMinima")
			            && child.hasProperty("tipo")
			            && familia.equals(child.getProperty("tipo").getString())
			            && child.hasProperty("enlace")) {

			        String uuid = child.getProperty("enlace").getString();
			        try {
			            Node pagina = session.getNodeByIdentifier(uuid);
			            return pagina.getPath();
			        } catch (ItemNotFoundException e) {
			        	log.error(e.getMessage(), e);
						return "";
			        }
			    }

			    // Si no es match, seguimos recorriendo recursivamente
			    String found = findPath(child, session, familia);
			    if ((found != null) && !found.isEmpty()) {
			        return found;
			    }
			}
		} catch (ValueFormatException e) {
			log.error(e.getMessage(), e);
			return "";
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			return "";
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			return "";
		}

        return null;
	}
	
	private ResponseBuilder generateResponseBuilder(String res, Status status) {

		return Response.status(status)
				.type(MediaType.APPLICATION_JSON + "; charset=utf-8")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8"))
				.header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
				.header(HttpHeaders.EXPIRES, "0")
				.entity(res);

	}
	
    /*private Response request(String definitionName, String connectionName, Function<CartProvider, Response> function) {
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
	private ResponseBuilder generateResponseBuilder(String res, Status status) {

		return Response.status(status)
				.type(MediaType.APPLICATION_JSON + "; charset=utf-8")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8"))
				.header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
				.header(HttpHeaders.EXPIRES, "0")
				.entity(res);

	}*/


}
