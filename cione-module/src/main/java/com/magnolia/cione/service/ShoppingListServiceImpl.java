package com.magnolia.cione.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartDraft;
import com.commercetools.api.models.cart.CartPagedQueryResponse;
import com.commercetools.api.models.cart.CartReference;
import com.commercetools.api.models.cart.CartReferenceBuilder;
import com.commercetools.api.models.cart.CartSetCustomLineItemCustomFieldAction;
import com.commercetools.api.models.cart.CartSetCustomLineItemCustomFieldActionBuilder;
import com.commercetools.api.models.cart.CartSetCustomTypeAction;
import com.commercetools.api.models.cart.CartSetCustomTypeActionBuilder;
import com.commercetools.api.models.cart.CartSetDeleteDaysAfterLastModificationActionBuilder;
import com.commercetools.api.models.cart.CartSetLineItemCustomFieldAction;
import com.commercetools.api.models.cart.CartSetLineItemCustomFieldActionBuilder;
import com.commercetools.api.models.cart.CartUpdate;
import com.commercetools.api.models.cart.CartUpdateAction;
import com.commercetools.api.models.cart.CartUpdateBuilder;
import com.commercetools.api.models.cart.CustomLineItem;
import com.commercetools.api.models.cart.LineItem;
import com.commercetools.api.models.cart.ReplicaCartDraft;
import com.commercetools.api.models.cart.ReplicaCartDraftBuilder;
import com.commercetools.api.models.common.CentPrecisionMoney;
import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.common.Money;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.type.FieldContainer;
import com.commercetools.api.models.type.FieldContainerBuilder;
import com.commercetools.api.models.type.Type;
import com.commercetools.api.models.type.TypeResourceIdentifier;
import com.commercetools.api.models.type.TypeResourceIdentifierBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.ShoppingListDTO;
import com.magnolia.cione.dto.TransporteDTO;
import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.setup.CioneEcommerceConectionProvider;
import com.magnolia.cione.utils.CioneUtils;
import com.magnolia.cione.utils.MyShopUtils;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import info.magnolia.context.MgnlContext;
import info.magnolia.ecommerce.common.Connection;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.init.MagnoliaConfigurationProperties;
import io.vrap.rmf.base.client.ApiHttpResponse;
import io.vrap.rmf.base.client.utils.json.JsonUtils;
public class ShoppingListServiceImpl  implements ShoppingListService {

	private static final Logger log = LoggerFactory.getLogger(ShoppingListServiceImpl.class);
	
	//private final SphereClientProvider clientProvider;
	
	@Inject
	private CioneEcommerceConectionProvider cioneEcommerceConectionProvider;
	
	@Inject
	private MagnoliaConfigurationProperties mcp;
	
	@Inject
	CartServiceImpl cartServiceImpl;
	
	@Inject
	private CustomerService customerService;
	
	@Inject
	private CommercetoolsService commercetoolsService;
	
	@Inject
	private LensService lensservice;
	
	@Inject
	private MiddlewareService middlewareService; 
	
	private final SimpleTranslator i18n;
	
	private final ObjectMapper objectMapper;

    @Inject
    public ShoppingListServiceImpl(SimpleTranslator i18n) {
    	this.objectMapper = JsonUtils.createObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.i18n = i18n;
    }
    
	@Override
	public Response getShoppingLists(String date, String customermail, String numbudget, String numcliente, int page) {
		CartPagedQueryResponse actualCarts = null;
		
		List<String> querys = new ArrayList<>();
		String email = MyShopUtils.getUserName() + "@cione.es";
		final int offset = calcOffset(page);	
		final int limit = 12;
		
		try {
			querys.add("customerEmail=\"" + email + "\"");
			querys.add("cartState=\"Active\"");
			querys.add("custom(fields(cartType=\"" + MyshopConstants.BUDGET + "\"))");
			
			if (customermail != null && !customermail.isEmpty()) {
				querys.add("custom(fields(mailCliente=\"" + customermail.trim() + "\"))");
			}
			if (numbudget != null && !numbudget.isEmpty()) {
				querys.add("custom(fields(" + MyshopConstants.IDPRESUPUESTO + "=\"" + numbudget.trim() + "\"))");
			}
			if (numcliente != null && !numcliente.isEmpty()) {
				querys.add("custom(fields(numCliente=\"" + numcliente.trim() + "\"))");
			}
			if (date != null && !date.isEmpty()) {
				try {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate localDate = LocalDate.parse(date, formatter);
					LocalDateTime zonedate = LocalDateTime.of(localDate, LocalDateTime.now().toLocalTime());
					
					LocalDate today = LocalDate.now();
					LocalDateTime zonedateend = LocalDateTime.of(today, LocalDateTime.now().toLocalTime());
					log.debug(zonedate.toString());
					
					querys.add("createdAt>=\"" + zonedate + "\"");
					querys.add("createdAt<=\"" + zonedateend + "\"");
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				//Date dateparse = parseStringtoDate(date);
				//Calendar c = Calendar.getInstance(); 
				//c.setTime(dateparse); 
				//c.add(Calendar.DATE, 1);
				//Date dateparseplusone = c.getTime();
				//ZonedDateTime zonedate = ZonedDateTime.ofInstant(dateparse.toInstant(), ZoneId.systemDefault());
				//ZonedDateTime zonedateend = ZonedDateTime.ofInstant(dateparseplusone.toInstant(), ZoneId.systemDefault());
			}
			
			actualCarts =  cioneEcommerceConectionProvider
			.getApiRoot()
			.carts()
			//.withCustomerId(customer.getId())
			.get()
			.withWhere(querys)
			.withLimit(limit)
			.withOffset(offset)
			.executeBlocking().getBody();
			
			/*actualCarts = 
				cioneEcommerceConectionProvider
				.getApiRoot()
				.carts()
				//.withCustomerId(customer.getId())
				.get()
				.withWhere(querys)
				.withLimit(limit)
				.withOffset(offset)
				.executeBlocking()
				.getBody()
				.getResults();*/
			
			String cartResponse = getBackwardCompatibilityCartResponseAsString(actualCarts);
			return Response.ok(cartResponse).build();
			
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	

	private String getBackwardCompatibilityCartResponseAsString(CartPagedQueryResponse carts) throws JsonProcessingException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("count", carts.getCount());
		resultMap.put("total", carts.getTotal());
		
		List<Map<String, Object>> cartsList = new ArrayList<>();
		for (Cart cart : carts.getResults()) {
			Map<String, Object> cartMap = (Map<String, Object>)this.objectMapper.convertValue(cart, 
		    		new TypeReference<Map<String, Object>>() {
		    });
			cartMap.put("currencyCode", cart.getTotalPrice().getCurrencyCode());
			cartsList.add(cartMap);
		}
		
		resultMap.put("results", cartsList);
		
	    return JsonUtils.toJsonString(resultMap);
	}
	

	@Override
	public Cart addCartToShoppingList() {
		
		//crea un carrito replica del actual
		Cart replicatedCart = null;
		try {
			Cart originalCart = customerService.getUserCart();
			
			CartReference reference = CartReferenceBuilder.of().id(originalCart.getId()).build();
			
			ReplicaCartDraft replicaCartDraft = ReplicaCartDraftBuilder.of()
			        .reference(reference)
			        .build();
			replicatedCart = cioneEcommerceConectionProvider
					.getApiRoot()
					.carts()
					.replicate()
					.post(replicaCartDraft)
			        .executeBlocking()
			        .getBody();
			
			//setea como custonFields el importe y el tipo carrito budget
			String importeTotal = getCarritoTotal(replicatedCart);
			String idPresupuesto = getIdPresupuesto();
			String cartType = MyshopConstants.BUDGET;
			Map<String, Object> obj = new HashMap<>();
			obj.put("importeTotal", importeTotal);
			obj.put("cartType", cartType);
			obj.put(MyshopConstants.IDPRESUPUESTO, idPresupuesto);
			
			Integer daysbydefault = getDaysByDefault();
			
			List<CartUpdateAction> actions = new ArrayList<>();
			FieldContainer fieldContainer = FieldContainerBuilder.of().values(obj).build();
			TypeResourceIdentifier customType = TypeResourceIdentifierBuilder.of().key("customFields-forOrders").build();
			CartSetCustomTypeAction cartSetCustomTypeAction = CartSetCustomTypeActionBuilder.of()
					.type(customType)
					.fields(fieldContainer)
					.build();
			
			actions.add(cartSetCustomTypeAction);
			//fija el periodo de vigencia del presupuesto
			actions.add(CartSetDeleteDaysAfterLastModificationActionBuilder.of().deleteDaysAfterLastModification(daysbydefault).build());
			
			
			for (LineItem lineItem : replicatedCart.getLineItems()) {
				CartSetLineItemCustomFieldAction setcustomfield = 
						CartSetLineItemCustomFieldActionBuilder.of().lineItemId(lineItem.getId()).name("numDocEspecial").value(idPresupuesto).build();
				actions.add(setcustomfield);
			}
			
			for (CustomLineItem customlineItem : replicatedCart.getCustomLineItems()) {
				CartSetCustomLineItemCustomFieldAction setcustomfield = 
						CartSetCustomLineItemCustomFieldActionBuilder.of().customLineItemId(customlineItem.getId()).name("numDocEspecial").value(idPresupuesto).build();
				actions.add(setcustomfield);
			}
			
			CartUpdate cartUpdate = CartUpdateBuilder.of().version(replicatedCart.getVersion()).actions(actions).build();
			replicatedCart = cioneEcommerceConectionProvider
					.getApiRoot()
					.carts()
					.withId(replicatedCart.getId())
					.post(cartUpdate).executeBlocking()
					.getBody();
			
			//borramos el carrito original
			ApiHttpResponse<Cart> respones = cioneEcommerceConectionProvider
			.getApiRoot()
			.carts()
			.withId(originalCart.getId())
			.delete()
			.withVersion(originalCart.getVersion())
			.executeBlocking();
			if (respones.getStatusCode() == 200)
				//creamos un carrito de nuevo
				cartServiceImpl.createCart(commercetoolsService.getCustomerSDK(), MyshopConstants.deleteDaysAfterLastModification);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return replicatedCart;
		
	}
	
	
	/*public Cart addCartToShoppingList(Connection connection) {
	
		Cart updatedCart = null;
		
		try {
			
			CartReplicationDraft cartReplicationDraft = CartReplicationDraftBuilder.of(customerService.getUserCart().toReference()).build();
	        final Cart replicatedCart = clientProvider.get(connection).executeBlocking(CartReplicationCommand.of(cartReplicationDraft));
	        
	        List<UpdateAction<Cart>> actions = new ArrayList<>();
	        Map<String, Object> obj = new HashMap<>();
	        
	        String importeTotal = getCarritoTotal(replicatedCart);
	        String idPresupuesto = getIdPresupuesto();
	        String cartType = MyshopConstants.BUDGET;
		    
	        obj.put("importeTotal", importeTotal);
			obj.put("cartType", cartType);
			obj.put(MyshopConstants.IDPRESUPUESTO, idPresupuesto);
			
			Integer daysbydefault = getDaysByDefault();
			
		    actions.add(SetCustomType.ofTypeKeyAndObjects("customFields-forOrders", obj));
		    updatedCart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(replicatedCart, actions));
		    updatedCart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(updatedCart, SetDeleteDaysAfterLastModification.of(daysbydefault)));
		    cioneEcommerceConectionProvider.getClient().executeBlocking(CartDeleteCommand.of(customerService.getUserCart(), true));
		    
		    List<LineItem> linesItem = updatedCart.getLineItems();
		    for (LineItem line : linesItem) {
		    	updatedCart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(updatedCart, SetLineItemCustomField.ofObject("numDocEspecial", idPresupuesto, line.getId())));
		    }
		    
		    List<CustomLineItem> customLinesItem = updatedCart.getCustomLineItems();
		    for (CustomLineItem line : customLinesItem) {
		    	updatedCart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(updatedCart, SetCustomLineItemCustomField.ofObject("numDocEspecial", idPresupuesto, line.getId())));
		    }
		    
		    //volvemos a crear un carrito para que este disponible en cioneLab
		    CustomerCT customer = commercetoolsService.getCustomer();
		    cartServiceImpl.createCart(customer, MyshopConstants.deleteDaysAfterLastModification);
		    
		    return updatedCart;
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return updatedCart;
		}

	}*/
	
	private Integer getDaysByDefault() {
		
		Date now = new Date();
		Date futureDate = DateUtils.addMonths(new Date(), 6);
		long diff = futureDate.getTime() - now.getTime();
		
		return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}

	@Override
	public Cart removeShoppingList(String id) {
	
		Cart deletecart = null;
		
		try {
			
			Cart cart = cioneEcommerceConectionProvider
					.getApiRoot()
					.carts()
					.withId(id).get().executeBlocking().getBody();
			
			deletecart = cioneEcommerceConectionProvider
			.getApiRoot()
			.carts()
			.withId(id)
			.delete()
			.withVersion(cart.getVersion())
			.executeBlocking()
			.getBody();
				
			/*QueryPredicate<Cart> predicateId = CartQueryModel.of().id().is(id);
			Query<Cart> query = CartQuery.of().withPredicates(predicateId);
			CompletionStage<PagedQueryResult<Cart>> cartquery = cioneEcommerceConectionProvider.getClient().execute(query);
			PagedQueryResult<Cart> actualCarts = cartquery.toCompletableFuture().join();
			
			if (actualCarts.getCount() > 0) {
				
				Cart car = actualCarts.head().get();
				
				Customer customerCar = getCustormerById(car.getCustomerId());
				
				if (customerCar != null) {
					deletecart = clientProvider.get(connection).executeBlocking(CartDeleteCommand.of(car));
					return deletecart;
				}
				
			}*/
			
			return deletecart;
		
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			return deletecart;
		}
	}
	
	@Override
	public boolean isUserCart(String id) {
		
		boolean res = false;
		
		Cart cart = 
				cioneEcommerceConectionProvider
				.getApiRoot()
				.carts()
				.withId(id)
				.get()
				.executeBlocking()
				.getBody();
		
		if (cart!= null && MyShopUtils.getUserName().equals(getCustomerNumberFromCustomerMail(cart.getCustomerEmail()))) {
			res = true;
		}
		return res;
		
		/*QueryPredicate<Cart> predicateId = CartQueryModel.of().id().is(id);
		Query<Cart> query = CartQuery.of().withPredicates(predicateId);
		CompletionStage<PagedQueryResult<Cart>> cartquery = cioneEcommerceConectionProvider.getClient().execute(query);
		PagedQueryResult<Cart> actualCarts = cartquery.toCompletableFuture().join();
		
		if (actualCarts.getCount() > 0) {
			
			Cart car = actualCarts.head().get();
			Customer customerCar = getCustormerById(car.getCustomerId());
			
			if (customerCar != null && MyShopUtils.getUserName().equals(customerCar.getCustomerNumber())) {
				res = true;
			}
		}
		
		return res;*/
	}
	
	private String getCustomerNumberFromCustomerMail(String mail) {
		int pos = mail.indexOf('@');
		return mail.substring(0, pos);
	}

	@Override
	public Cart getShoppingListById(Connection connection, String id) {
		
		if (id != null && !id.isEmpty()) {
			Cart cart = 
					cioneEcommerceConectionProvider
					.getApiRoot()
					.carts()
					.withId(id)
					.get()
					.executeBlocking()
					.getBody();
			if (cart!= null && MyShopUtils.getUserName().equals(getCustomerNumberFromCustomerMail(cart.getCustomerEmail())))
				return cart;
			else
				return null;
		} else
			return null;
			
			
		/*QueryPredicate<Cart> predicateId = CartQueryModel.of().id().is(id);
		Query<Cart> query = CartQuery.of().withPredicates(predicateId);
		CompletionStage<PagedQueryResult<Cart>> cartquery = cioneEcommerceConectionProvider.getClient().execute(query);
		PagedQueryResult<Cart> actualCarts = cartquery.toCompletableFuture().join();
		
		if (actualCarts.getCount() > 0) {
			
			Cart car = actualCarts.head().get();
			Customer customerCar = getCustormerById(car.getCustomerId());
			
			if (customerCar != null && MyShopUtils.getUserName().equals(customerCar.getCustomerNumber())) {
				return car;
			}else {
				return null;
			}
			
		}else {
			return null;
		}*/
		
	}

	@Override
	public Cart getCartByShoppingListAndRetrieve(Connection connection, String id) {
		
		if (id != null && !id.isEmpty()) {
			
			Cart presupuesto = getShoppingListById(connection, id);
			Cart carrActive = cartServiceImpl.getCart(presupuesto.getCustomerId());
			
			if (carrActive == null) {
				carrActive = createCart(presupuesto.getCustomerId(), presupuesto.getCustomerEmail());
			}
			
			Customer customerCar = getCustormerById(presupuesto.getCustomerId());
			
			if (MyShopUtils.getUserName().equals(customerCar.getCustomerNumber())) {
				
				for(LineItem item: presupuesto.getLineItems()) {
					carrActive = cartServiceImpl.addCartLineItem(carrActive, item, null, null, null);
				}
				
				for(CustomLineItem item: presupuesto.getCustomLineItems()) {
					carrActive = cartServiceImpl.addCartCustomLine(carrActive, item, null, null);
				}
				
			}
			
		    return carrActive;
			
		}
		
		return null;
	}

	@Override
	public Cart updateShoppingList(Connection connection, ShoppingListDTO shoppingListDTO) {
			
		Cart carttoupdate = getShoppingListById(connection, shoppingListDTO.getId());
		
		if (carttoupdate.getCustom() != null 
				&& carttoupdate.getCustom().getFields().values().get(MyshopConstants.IDPRESUPUESTO) != null 
				&& !((String) carttoupdate.getCustom().getFields().values().get(MyshopConstants.IDPRESUPUESTO)).isEmpty()) {
			
			List<CartUpdateAction> actions = getActions(shoppingListDTO,carttoupdate);
			
			if (shoppingListDTO.getEffectivedate() != null && !shoppingListDTO.getEffectivedate().isEmpty()) {
				Integer deleteDaysAfterLastModification = getDaysByDate(shoppingListDTO.getEffectivedate());
				actions.add(CartSetDeleteDaysAfterLastModificationActionBuilder.of().deleteDaysAfterLastModification(deleteDaysAfterLastModification).build());
			}
			
			CartUpdate cartUpdate = CartUpdateBuilder.of().version(carttoupdate.getVersion()).actions(actions).build();
			Cart updatedCart = cioneEcommerceConectionProvider
					.getApiRoot()
					.carts()
					.withId(carttoupdate.getId())
					.post(cartUpdate).executeBlocking()
					.getBody()
					.get();
			
			return updatedCart;
			
		}else {
			log.error("El presupuesto a actualizar no tiene de id.");
			return null;
		}
	}

	@Override
	public Cart updateCustomField(Connection connection, ShoppingListDTO shoppingListDTO, String updatedField) {
		
		Cart updatedCart = null;
		
		if (!StringUtils.isBlank(updatedField) && !StringUtils.isBlank(shoppingListDTO.getLineItemId())) {
		
			try {
				
				Cart cart = getShoppingListById(connection, shoppingListDTO.getId());
				List<CartUpdateAction> actions = new ArrayList<>();
				
				actions.add(CartSetLineItemCustomFieldActionBuilder.of()
						.lineItemId(shoppingListDTO.getLineItemId())
						.name(updatedField)
						.value(getStringDecimal(shoppingListDTO.getImporteVenta())).build());
				CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(actions).build();
				
				/*actions.add(SetLineItemCustomField.ofObject(updatedField, 
															getStringDecimal(shoppingListDTO.getImporteVenta()), 
															shoppingListDTO.getLineItemId()));*/
				
				updatedCart = cioneEcommerceConectionProvider
				.getApiRoot()
				.carts()
				.withId(cart.getId())
				.post(cartUpdate).executeBlocking()
				.getBody();
				//updatedCart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(cart, actions));
				
				return updatedCart;
				
			}catch(Exception e) {
				log.error(e.getMessage(), e);
				return updatedCart;
			}
		}
		
		return updatedCart;
    	
	}

	@Override
	public Cart updateCustomFieldCLI(Connection connection, ShoppingListDTO shoppingListDTO, String updatedField) {
		
		Cart updatedCart = null;
		
		if (!StringUtils.isBlank(updatedField) && !StringUtils.isBlank(shoppingListDTO.getLineItemId())) {
			
			try {
				
				Cart cart = getShoppingListById(connection, shoppingListDTO.getId());
				List<CartUpdateAction> actions = new ArrayList<>();
				
				actions.add(CartSetCustomLineItemCustomFieldActionBuilder.of()
						.customLineItemId(shoppingListDTO.getLineItemId())
						.name(updatedField)
						.value(getStringDecimal(shoppingListDTO.getImporteVenta())).build());
				CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(actions).build();
				updatedCart = cioneEcommerceConectionProvider
						.getApiRoot()
						.carts()
						.withId(cart.getId())
						.post(cartUpdate).executeBlocking()
						.getBody();
//				actions.add(SetCustomLineItemCustomField.ofObject(updatedField, 
//																  getStringDecimal(shoppingListDTO.getImporteVenta()), 
//																  shoppingListDTO.getLineItemId()));
//				updatedCart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(cart, actions));
				
				return updatedCart;
				
			}catch(Exception e) {
				log.error(e.getMessage(), e);
				return updatedCart;
			}
		}
		
		return updatedCart;
    	
	}
	
	@Override
	public File getBudgetPDF(Connection connection, String id) {
		
		Cart cart = getShoppingListById(connection, id);
		String pdfname = (String) cart.getCustom().getFields().values().get("idPresupuesto");
		
		createHtml(cart);
		
		return new File(cioneEcommerceConectionProvider.getConfigService().getConfig().getPdfGeneratorBudgetPath() + pdfname + ".pdf");
	}

	private Customer getCustormerById(String id) {
		return cioneEcommerceConectionProvider
			.getApiRoot()
			.customers()
			.withId(id)
			.get()
			.executeBlocking()
			.getBody();
	}
	
	private Integer getDaysByDate(String effectivedate) {
		
		Integer deleteDaysAfterLastModification = 30;
		
	    try {
	    	
	    	SimpleDateFormat sdf = new SimpleDateFormat(MyshopConstants.DATEFORMATJS);
	    	Date dateIn = sdf.parse(effectivedate);
	    	Date dateNow = new Date();
	    	
	    	if(dateIn.getTime() > dateNow.getTime()) {
	    		long diff = dateIn.getTime() - dateNow.getTime();
				return (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	    	}else {
	    		return deleteDaysAfterLastModification;
	    	}
			
		} catch (Exception e) {
			e.printStackTrace();
			return deleteDaysAfterLastModification;
		}  
	}
	
	private List<CartUpdateAction> getActions(ShoppingListDTO shoppingListDTO, Cart carttoupdate){
		
		List<CartUpdateAction> actions = new ArrayList<>();
        Map<String, Object> obj = new HashMap<>();
        
        if (shoppingListDTO.getCustomer() != null && !shoppingListDTO.getCustomer().isEmpty()) {
        	obj.put(MyshopConstants.NUMCLIENTE, shoppingListDTO.getCustomer());
        }
        
		if (shoppingListDTO.getEffectivedate() != null && !shoppingListDTO.getEffectivedate().isEmpty()) {
			obj.put(MyshopConstants.VIGENCIA, shoppingListDTO.getEffectivedate());
		}
		
		if (shoppingListDTO.getMailconsumer() != null && !shoppingListDTO.getMailconsumer().isEmpty()) {
			obj.put(MyshopConstants.MAILCLIENTE, shoppingListDTO.getMailconsumer());
		}
		
		if (shoppingListDTO.getTotal() != null && !shoppingListDTO.getTotal().isEmpty()) {
			obj.put(MyshopConstants.IMPORTETOTAL, getStringDecimal(shoppingListDTO.getTotal()));
		}
		
		String cartType = MyshopConstants.BUDGET;
		obj.put(MyshopConstants.CARTTYPE, cartType);
		
		String idPresupuesto = (String) carttoupdate.getCustom().getFields().values().get(MyshopConstants.IDPRESUPUESTO);
		obj.put(MyshopConstants.IDPRESUPUESTO, idPresupuesto);
		
		FieldContainer fieldContainer = FieldContainerBuilder.of().values(obj).build();
		TypeResourceIdentifier customType = TypeResourceIdentifierBuilder.of().key("customFields-forOrders").build();
		CartSetCustomTypeAction cartSetCustomTypeAction = CartSetCustomTypeActionBuilder.of()
				.type(customType)
				.fields(fieldContainer)
				.build();
		
		actions.add(cartSetCustomTypeAction);
		
		return actions;
	}
	
	private String getStringDecimal(String str) {
		
		String impv = str.replace(",", ".");
		Float decimalofstring = Float.parseFloat(impv);
		DecimalFormat df = new DecimalFormat("0.00");
		df.setMaximumFractionDigits(2);
		return df.format(decimalofstring);
		
	}
	
	private String parseDatetoString(ZonedDateTime zonedDateTime) {
		
		return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(zonedDateTime);
		
	}
	
	private Date parseStringtoDate(String datestr) {
		
		Date date = new Date();
		
		if (datestr != null && !datestr.isEmpty()) {
			try {
				DateFormat format = new SimpleDateFormat(MyshopConstants.DATEFORMATJS, Locale.ENGLISH);
				date = format.parse(datestr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return date;
	}
	
	private String getCarritoTotal(Cart carrito) {
		
		if (carrito == null) {
			return "0";
		} else {
			
			//MonetaryAmount total = Money.of(BigDecimal.ZERO, MyshopConstants.EUR);
			Money total = MyShopUtils.getMoney(0);
			
			for (LineItem item: carrito.getLineItems()) {
				
				boolean enDeposito = false;
				
				if (item.getCustom().getFields().values().get(MyshopConstants.ENDEPOSITO) != null) {
					enDeposito = (boolean) item.getCustom().getFields().values().get(MyshopConstants.ENDEPOSITO);
				}
				
				if(!enDeposito) {
					if (MyShopUtils.hasAttribute(MyshopConstants.PVPRECOMENDADO, item.getVariant().getAttributes())) {
						CentPrecisionMoney money = (CentPrecisionMoney) MyShopUtils.findAttribute(MyshopConstants.PVPRECOMENDADO, item.getVariant().getAttributes()).getValue();
						Long suma = (money.getCentAmount() * item.getQuantity()) + total.getCentAmount();
						total.setCentAmount(suma);
					} else {
						Long suma = item.getTotalPrice().getCentAmount() + total.getCentAmount();
						total.setCentAmount(suma);
					}
				}
			}
			
			for (CustomLineItem item: carrito.getCustomLineItems()) {
				
				if (isStockLenses(item)) {
					String skuCustom = (String) item.getCustom().getFields().values().get(MyshopConstants.SKUUPPERCASE);//item.getCustom().getFieldAsString(MyshopConstants.SKUUPPERCASE); 
					Money pvp = getPvp(skuCustom);
					
					//if (pvp != null && pvp != Money.of(new BigDecimal(0), MyshopConstants.EUR)) {
					if (pvp != null && (pvp.getCentAmount() != 0)) {
						Long suma = total.getCentAmount() + pvp.getCentAmount();
						total.setCentAmount(suma);
					}else {
						Long suma = item.getTotalPrice().getCentAmount() + total.getCentAmount();
						total.setCentAmount(suma);
					}
				} else { //esCioneLab
					CentPrecisionMoney pvp = (CentPrecisionMoney) item.getCustom().getFields().values().get(MyshopConstants.PVP);//item.getCustom().getFieldAsString(MyshopConstants.SKUUPPERCASE); 
					
					//if (pvp != null && pvp != Money.of(new BigDecimal(0), MyshopConstants.EUR)) {
					if (pvp != null && (pvp.getCentAmount() != 0)) {
						Long suma = total.getCentAmount() + pvp.getCentAmount();
						total.setCentAmount(suma);
					}else {
						Long suma = item.getTotalPrice().getCentAmount() + total.getCentAmount();
						total.setCentAmount(suma);
					}
				}
				
			}
			return MyShopUtils.formatMoney(total);
			//return MyShopUtils.formatMoney(total);
		}
	}
	
	private String getIdPresupuesto() {
		
		String res = "";
		
		Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
        
        res =  "PR" + CioneUtils.getIdCurrentClientERP() + "_" + timestamp.getTime();
		
		return res;
		
	}

    private Money getPvp(String sku) {
    	
    	String pvp = "0";
    	Money money = MyShopUtils.getMoney(0);
    	//MonetaryAmount monetaryAmount = Money.of(new BigDecimal(pvp), MyshopConstants.EUR);
    	
    	try {
			Map<String, String> price = lensservice.getPriceBySku(sku);
			pvp = price.get(MyshopConstants.PVP);
			if (pvp != null) {
				money = MyShopUtils.getMoney(pvp);
				//monetaryAmount = Money.of(new BigDecimal(pvp), MyshopConstants.EUR);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
    	
    	return money;
    }
	
    private Cart createCart(String id, String email) {
		Cart cart = null;
		
		CartDraft cartDraft = CartDraft.builder()
				.customerId(id)
				.customerEmail(email)
				.currency("EUR")
				.deleteDaysAfterLastModification(MyshopConstants.deleteDaysAfterLastModification)
				.build();
		cart = cioneEcommerceConectionProvider.getApiRoot().carts().post(cartDraft).executeBlocking().getBody();
		
		return cart;
	}
	
	private boolean isCioneLab(CustomLineItem item) {
		boolean isCioneLab = false;
		String keyType = "";
		String typeId = item.getCustom().getType().getId();
		Type type = cioneEcommerceConectionProvider.getApiRoot().types().withId(typeId).get().executeBlocking().getBody();
		if (type != null) {
			keyType = type.getKey();
		}
		switch(keyType) {
			case "customlineitem-cionelab-jobs":
				isCioneLab = true;
				break;
			case "customlineitem-cionelab-lenses":
				isCioneLab = true;
				break;
			default:
				isCioneLab = true;
				break;
		}
		return isCioneLab;
	}
	
	private boolean isStockLenses(CustomLineItem item) {
		try {
			String typeId = item.getCustom().getType().getId();
			Type type = cioneEcommerceConectionProvider.getApiRoot().types().withId(typeId).get().executeBlocking().getBody();
			if ((type != null) && (type.getKey().equals("customlineitem-stock-lenses")))
				return true;
			return false;
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return false;
		}
	}
	
	/*private String getTypeCustom(String name) {
		
		String res = "";
		
		switch(name) {
		
			case "TYPJOB":
				res = "customlineitem-cionelab-jobs";
				break;
			case "LNAM_R":
				res = "customlineitem-cionelab-lenses";
				break;
			case "LNAM_L":
				res = "customlineitem-cionelab-lenses";
				break;
			case "CYL":
				res = "customlineitem-stock-lenses";
				break;
			default:
				res = "";
				break;
		}
		
		return res;
	}*/
	
	//revisar si podemos obtener el tipo de linea de Item o la familia del producto
	/*private String getType(String name) {
		
		String res = "";
		switch (name) {
		case "colorAudifono":
			res = "lineitem-audifonos";
			break;
		case "enDeposito":
			res = "lineitem-audifonos";
			break;
		case "color_audifono_R":
			res = "lineitem-audiolab";
			break;
		default:
			break;
		}
		
		return res;
	}*/
	

	

	
	private String formatDate(java.time.LocalDate date) {
		try {
			//Date stringdate = new SimpleDateFormat(MyshopConstants.DATEFORMATJS).parse(datestr);
			DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			return date.format(pattern);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return "00/00/0000";
		}
	}
	
	private void createHtml(Cart cart) {
		
		String originalBrowserURL = MgnlContext.getWebContext().getRequest().getRequestURL().toString();
		URL url;
		String https = CioneUtils.getURLHttps();
		URL urlhttps;
		
		try {
			
			urlhttps = new URL(https);
			url = new URL(originalBrowserURL);
			String contextPath = MgnlContext.getContextPath();
			String html = readFile("cione-module/templates/pdf/pdf-presupuesto.html", StandardCharsets.UTF_8);
			
			UserERPCioneDTO data = middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
			String budgetdate = formatDate((java.time.LocalDate) cart.getCustom().getFields().values().get(MyshopConstants.VIGENCIA));
			List<TransporteDTO> transportes = middlewareService.getTransportes(CioneUtils.getIdCurrentClientERP());
			String namecom = !transportes.isEmpty() ? transportes.get(0).getNombre() : "";
			String name = data.getRazonSocial();
			String dir = data.getDireccion() + ", " + data.getPoblacion() + " (" + data.getCodigoPostal() + ") "; 
			//cart.getCustom().getFieldAsString(MyshopConstants.IMPORTETOTAL);
			String budgettotal = (String) cart.getCustom().getFields().values().get(MyshopConstants.IMPORTETOTAL);
			//cart.getCustom().getFieldAsString(MyshopConstants.IDPRESUPUESTO);
			String budgetid = (String) cart.getCustom().getFields().values().get(MyshopConstants.IDPRESUPUESTO);
			String budgetcreate = parseDatetoString(cart.getCreatedAt());
			
			html = html.replace("$host", urlhttps.getProtocol() + "://" +  url.getAuthority() + contextPath);
			
			html = html.replace("$title", budgetid);
			html = html.replace("$presupuestoid", budgetid);
			html = html.replace("$createdat", budgetcreate);
			html = html.replace("$nombrecomercial", namecom);
			html = html.replace("$nombre", name);
			html = html.replace("$direccion", dir);
			html = html.replace("$vigencia", budgetdate);
			html = html.replace("$total", budgettotal);
			
			Customer customerCar = getCustormerById(cart.getCustomerId());
			StringBuilder itemshtml = new StringBuilder();
			
			if (MyShopUtils.getUserName().equals(customerCar.getCustomerNumber())) {
				
				int cont = 0;
				
				for(LineItem item: cart.getLineItems()) {
					
					String price = "";
					String nombreArticulo = "";
					
					if (item.getCustom().getFields().values().get(MyshopConstants.IMPORTEVENTA) != null &&
						!((String) item.getCustom().getFields().values().get(MyshopConstants.IMPORTEVENTA)).isEmpty()) {
						price = (String) item.getCustom().getFields().values().get(MyshopConstants.IMPORTEVENTA);
					} else {
						long q = item.getQuantity();
						double pvp = 0.0;
						if (MyShopUtils.hasAttribute("pvpRecomendado", item.getVariant().getAttributes())) {
							CentPrecisionMoney pvpRecomendado = (CentPrecisionMoney) MyShopUtils.findAttribute("pvpRecomendado", item.getVariant().getAttributes()).getValue();
							pvp = MyShopUtils.formatMoneyDouble(pvpRecomendado);
						}
						price = Double.toString(q * pvp);
					}
					
					if (MyShopUtils.hasAttribute("nombreArticulo", item.getVariant().getAttributes())) {
						nombreArticulo = (String) MyShopUtils.findAttribute("nombreArticulo", item.getVariant().getAttributes()).getValue();
					}else {
						nombreArticulo = item.getName().get(MyshopConstants.esLocale);
					}
					
					itemshtml.append("<tr>");
					itemshtml.append("<td width=\"400\">" + nombreArticulo + "</td>");
					itemshtml.append("<td width=\"600\">" + getDataItem(item) + "</td>");
					itemshtml.append("<td width=\"20\" style=\"text-align: center;\">" + item.getQuantity().toString() + "</td>");
					itemshtml.append("<td width=\"100\" style=\"text-align: right;\">" + price + " € </td>");
					itemshtml.append("</tr>");
					
					cont++;
					if (cont == 27) {
						cont = 0;
						itemshtml.append("<br><br>");
					}
					
				}
				
				for(CustomLineItem item: cart.getCustomLineItems()) {
					
					String price = "";
					
					if (item.getCustom().getFields().values().get(MyshopConstants.IMPORTEVENTA) != null &&
						!((String) item.getCustom().getFields().values().get(MyshopConstants.IMPORTEVENTA)).isEmpty()) {
						price = (String) item.getCustom().getFields().values().get(MyshopConstants.IMPORTEVENTA);
					}else {
						if (item.getCustom().getFields().values().get(MyshopConstants.LMATTYPE) != null &&
							!((String) item.getCustom().getFields().values().get(MyshopConstants.LMATTYPE)).isEmpty()) {
							price = getPvpItemCustom((String) item.getCustom().getFields().values().get(MyshopConstants.SKUUPPERCASE));
						}if (item.getCustom().getFields().values().get(MyshopConstants.PVP_R) != null &&
							!((String) item.getCustom().getFields().values().get(MyshopConstants.PVP_R)).isEmpty()) {
							price = getPvpItemCustom((String) item.getCustom().getFields().values().get(MyshopConstants.PVP_R));
						}else if (item.getCustom().getFields().values().get(MyshopConstants.PVP_L) != null &&
							!((String) item.getCustom().getFields().values().get(MyshopConstants.PVP_L)).isEmpty()) {
							price = getPvpItemCustom((String) item.getCustom().getFields().values().get(MyshopConstants.PVP_L));
						}else if (item.getCustom().getFields().values().get(MyshopConstants.PVP_UPPERCASE) != null &&
							!((String) item.getCustom().getFields().values().get(MyshopConstants.PVP_UPPERCASE)).isEmpty()) {
							long q = item.getQuantity();
							CentPrecisionMoney pvp_upper = (CentPrecisionMoney) item.getCustom().getFields().values().get(MyshopConstants.PVP_UPPERCASE);
							double pvp = MyShopUtils.formatMoneyDouble(pvp_upper);
									//item.getCustom().getFieldAsMoney(MyshopConstants.PVP_UPPERCASE).getNumber().doubleValue();
							price = Double.toString(q * pvp);
						}
					}
					
					itemshtml.append("<tr>");
					itemshtml.append("<td width=\"400\">" + item.getName().get("es") + "</td>");
					itemshtml.append("<td width=\"600\">" + getDataItem(item) + "</td>");
					itemshtml.append("<td width=\"20\" style=\"text-align: center;\">" + item.getQuantity().toString() + "</td>");
					itemshtml.append("<td width=\"100\" style=\"text-align: right;\">" + price + " € </td>");
					itemshtml.append("</tr>");
					
					cont++;
					if (cont == 27) {
						cont = 0;
						itemshtml.append("<br><br>");
					}
					
				}
				
			}
			
			html = html.replace("$listado", itemshtml.toString());
		    htmlToPdf(html, budgetid);
			
		} catch (MalformedURLException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}
	
	private String getPvpItemCustom(String sku) {
		String pvp ="0";
    	try {
			Map<String, String> price = lensservice.getPriceBySku(sku);
			pvp = price.get(MyshopConstants.PVP);
			if (pvp != null) {
				DecimalFormat decimalFormat = new DecimalFormat("#00.00");
				pvp = decimalFormat.format(Double.valueOf(pvp).doubleValue());
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
    	return pvp;
	}

	private String getDataItem(CustomLineItem itemc) {
		
		StringBuilder res = new StringBuilder();
		
		setDescriptionProperty(res,
				i18n.translate(MyshopConstants.I18N_CILINDRO),
				(String)itemc.getCustom().getFields().values().get(MyshopConstants.CYL));
		
		setDescriptionProperty(res,
				i18n.translate(MyshopConstants.I18N_CILINDRO),
				(String)itemc.getCustom().getFields().values().get(MyshopConstants.CYL_L));
		
		setDescriptionProperty(res,
				i18n.translate(MyshopConstants.I18N_CILINDRO),
				(String)itemc.getCustom().getFields().values().get(MyshopConstants.CYL_R));
		
		setDescriptionProperty(res,
				i18n.translate(MyshopConstants.I18N_ESFERA),
				(String)itemc.getCustom().getFields().values().get(MyshopConstants.SPH));
		
		setDescriptionProperty(res,
				i18n.translate(MyshopConstants.I18N_ESFERA),
				(String)itemc.getCustom().getFields().values().get(MyshopConstants.SPH_L));
		
		setDescriptionProperty(res,
				i18n.translate(MyshopConstants.I18N_ESFERA),
				(String)itemc.getCustom().getFields().values().get(MyshopConstants.SPH_R));
		
		setDescriptionProperty(res,
				i18n.translate(MyshopConstants.I18N_DIAMETRO),
				(String)itemc.getCustom().getFields().values().get(MyshopConstants.CRIB));
		
		setDescriptionProperty(res,
				i18n.translate(MyshopConstants.I18N_DIAMETRO),
				(String)itemc.getCustom().getFields().values().get(MyshopConstants.CRIB_L));
		
		setDescriptionProperty(res,
				i18n.translate(MyshopConstants.I18N_DIAMETRO),
				(String)itemc.getCustom().getFields().values().get(MyshopConstants.CRIB_R));
		
		setDescriptionProperty(res,
				i18n.translate(MyshopConstants.I18N_EJE),
				(String)itemc.getCustom().getFields().values().get(MyshopConstants.AX_L));
		
		setDescriptionProperty(res,
				i18n.translate(MyshopConstants.I18N_EJE),
				(String)itemc.getCustom().getFields().values().get(MyshopConstants.AX_R));
		
		setDescriptionProperty(res,
				i18n.translate(MyshopConstants.I18N_ADICION),
				(String)itemc.getCustom().getFields().values().get(MyshopConstants.ADD_L));
		
		setDescriptionProperty(res,
				i18n.translate(MyshopConstants.I18N_ADICION),
				(String)itemc.getCustom().getFields().values().get(MyshopConstants.ADD_R));
		
		String ojo = (String)itemc.getCustom().getFields().values().get(MyshopConstants.EYE);
		String LNAM_L = (String)itemc.getCustom().getFields().values().get(MyshopConstants.LNAM_L);
		String LNAM_R = (String)itemc.getCustom().getFields().values().get(MyshopConstants.LNAM_L);
		if ((ojo !=null) && !(StringUtils.isBlank(ojo))) {
			
			
			if (ojo.equals("D") || ojo.equals("R")) {
				setTitleAndContent(res, 
						i18n.translate(MyshopConstants.I18N_OJO), 
						i18n.translate(MyshopConstants.I18N_OJOD));
			}else {
				setTitleAndContent(res, 
						i18n.translate(MyshopConstants.I18N_OJO), 
						i18n.translate(MyshopConstants.I18N_OJOI));
			}
		}else if ((LNAM_L !=null) && !(StringUtils.isBlank(LNAM_L))) {
			setTitleAndContent(res, 
					i18n.translate(MyshopConstants.I18N_OJO), 
					i18n.translate(MyshopConstants.I18N_OJOI));
		}else if ((LNAM_R !=null) && !(StringUtils.isBlank(LNAM_R))) {
			setTitleAndContent(res, 
					i18n.translate(MyshopConstants.I18N_OJO), 
					i18n.translate(MyshopConstants.I18N_OJOD));
		}
		
		return res.toString();
	}

	private void setDescriptionProperty(StringBuilder res, String title, String content) {

		if (!StringUtils.isBlank(content)) {
			setTitleAndContent(res, title, content);
		}
		
	}
	
	private void setTitleAndContent(StringBuilder res, String title, String content) {
		
		if (!StringUtils.isBlank(title)) {
			
			if (res.length() != 0){
				res.append(MyshopConstants.COMMASPACE);
			}
			
			res.append(title);
			res.append(":" + StringUtils.SPACE);
			res.append(content);
		}
	}

	private String getDataItem(LineItem item) {
		
		StringBuilder res = new StringBuilder();
		
		try {
			if (MyShopUtils.hasAttribute(MyshopConstants.TAMANIOS, item.getVariant().getAttributes())) {
				setTitleAndContent(res, 
					i18n.translate(MyshopConstants.I18N_TAMANIO), 
					((LocalizedString) MyShopUtils.findAttribute(MyshopConstants.TAMANIOS, item.getVariant().getAttributes()).getValue()).get("es"));
			}
			
			if (MyShopUtils.hasAttribute(MyshopConstants.DIMENSIONES_ANCHO_OJO, item.getVariant().getAttributes())) {
				setTitleAndContent(res, 
					i18n.translate(MyshopConstants.I18N_CALIBRE), 
					((Long) MyShopUtils.findAttribute(MyshopConstants.DIMENSIONES_ANCHO_OJO, item.getVariant().getAttributes()).getValue()).toString());
			}
			
			if (MyShopUtils.hasAttribute(MyshopConstants.GRADUACION, item.getVariant().getAttributes())) {
				setTitleAndContent(res, 
					i18n.translate(MyshopConstants.I18N_GRADUACION), 
					(String) MyShopUtils.findAttribute(MyshopConstants.GRADUACION, item.getVariant().getAttributes()).getValue());
			}
			
			String ojo = (String)item.getCustom().getFields().values().get(MyshopConstants.LC_OJO);
			
			if (!StringUtils.isBlank(ojo)) {
				
				if (ojo.equals("D") || ojo.equals("R")) {
					setTitleAndContent(res, 
							i18n.translate(MyshopConstants.I18N_OJO), 
							i18n.translate(MyshopConstants.I18N_OJOD));
				}else {
					setTitleAndContent(res, 
							i18n.translate(MyshopConstants.I18N_OJO), 
							i18n.translate(MyshopConstants.I18N_OJOI));
				}
			}
			
			if (item.getCustom().getFields().values().get(MyshopConstants.LC_DISENO) != null) 
				setDescriptionProperty(res,
					i18n.translate(MyshopConstants.I18N_DESIGN),
					(String)item.getCustom().getFields().values().get(MyshopConstants.LC_DISENO));
			
			if (item.getCustom().getFields().values().get(MyshopConstants.LC_ESFERA) != null) 
				setDescriptionProperty(res,
					i18n.translate(MyshopConstants.I18N_ESFERA),
					(String)item.getCustom().getFields().values().get(MyshopConstants.LC_ESFERA));
			
			if (item.getCustom().getFields().values().get(MyshopConstants.LC_CILINDRO) != null) 
				setDescriptionProperty(res,
					i18n.translate(MyshopConstants.I18N_CILINDRO),
					(String)item.getCustom().getFields().values().get(MyshopConstants.LC_CILINDRO));
			
			if (item.getCustom().getFields().values().get(MyshopConstants.LC_EJE) != null) 
				setDescriptionProperty(res,
					i18n.translate(MyshopConstants.I18N_EJE),
					(String)item.getCustom().getFields().values().get(MyshopConstants.LC_EJE));
			
			if (item.getCustom().getFields().values().get(MyshopConstants.LC_DIAMETRO) != null) 
				setDescriptionProperty(res,
					i18n.translate(MyshopConstants.I18N_DIAMETRO),
					(String)item.getCustom().getFields().values().get(MyshopConstants.LC_DIAMETRO));
			
			if (item.getCustom().getFields().values().get(MyshopConstants.LC_CURVABASE) != null) 
				setDescriptionProperty(res,
					i18n.translate(MyshopConstants.I18N_CURVABASE),
					(String)item.getCustom().getFields().values().get(MyshopConstants.LC_CURVABASE));
			
			if (item.getCustom().getFields().values().get(MyshopConstants.LC_ADICION) != null) 
				setDescriptionProperty(res,
					i18n.translate(MyshopConstants.I18N_ADICION),
					(String)item.getCustom().getFields().values().get(MyshopConstants.LC_ADICION));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return res.toString();
	}

	private String readFile(String path, Charset encoding) throws IOException {
		
		InputStream inputStreamFile = getClass().getClassLoader().getResourceAsStream(path);
		byte[] encoded = IOUtils.toByteArray(inputStreamFile);
		
		return new String(encoded, encoding);
	}
	
	private void htmlToPdf(String inputHTML, String idPresupuesto) throws IOException {
		
		Document doc = html5ParseDocument(inputHTML);
		String outputPdf = cioneEcommerceConectionProvider.getConfigService().getConfig().getPdfGeneratorBudgetPath() + idPresupuesto + ".pdf";
		//String originalBrowserURL = MgnlContext.getWebContext().getRequest().getRequestURL().toString();
//		String contextPath = MgnlContext.getContextPath();
//		String logoPath = "/.resources/cione-theme/webresources/fonts";
//		URL url;
		
		try {
			
			//String https = CioneUtils.getURLHttps();
			PdfRendererBuilder builder = new PdfRendererBuilder();
			//URL urlhttps = new URL(https);
			OutputStream os = new FileOutputStream(outputPdf);
			
			//url = new URL(originalBrowserURL);
			
			//String fontPath = urlhttps.getProtocol() + "://" +  url.getAuthority() + contextPath + logoPath;
			//String fontPath = CioneUtils.getURLHttps() + logoPath;
			//log.debug("FUENTE :"+ fontPath);
			//String fontPath = "C:\\pdfs\\opt\\data";
			//String baseUri = fontPath+"\\Lato-Regular.ttf";
			//(/file:/opt/tomcatpublic/webapps/ROOT/WEB-INF/lib/cione-module-1.65.jar!/cione-module/fonts/Lato-Regular.ttf)
			String mgnlHome = mcp.getProperty("magnolia.home");
			String baseUri = mgnlHome + "/WEB-INF/fonts/Lato-Regular.ttf";
			log.debug(baseUri);
			builder.withUri(outputPdf);
			builder.toStream(os);
			builder.useFastMode();
			
			//builder.useFont(new File(fontPath+"\\Lato-Regular.ttf"), "Lato");
			builder.useFont(new File(baseUri), "Lato");
			builder.withW3cDocument(doc, baseUri);
			
			builder.run();
			log.debug("DESPUES DEL RUN");
		    os.close();
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Document html5ParseDocument(String inputHTML){
		
		org.jsoup.nodes.Document doc;
		doc = Jsoup.parse(inputHTML, "UTF-8");
		return new W3CDom().fromJsoup(doc);
	}
	
	private int calcOffset(int page) {
		
		int res = 0;
		
		if (page>=1) {
			res = (int) page*12;
		}
		
		return res;
	}
}
