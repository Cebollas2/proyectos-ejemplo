package com.magnolia.cione.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.QueryResult;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartAddCustomLineItemAction;
import com.commercetools.api.models.cart.CartAddCustomLineItemActionBuilder;
import com.commercetools.api.models.cart.CartAddLineItemAction;
import com.commercetools.api.models.cart.CartAddLineItemActionBuilder;
import com.commercetools.api.models.cart.CartDraft;
import com.commercetools.api.models.cart.CartPagedQueryResponse;
import com.commercetools.api.models.cart.CartRecalculateAction;
import com.commercetools.api.models.cart.CartRecalculateActionBuilder;
import com.commercetools.api.models.cart.CartReference;
import com.commercetools.api.models.cart.CartReferenceBuilder;
import com.commercetools.api.models.cart.CartSetCustomLineItemCustomFieldAction;
import com.commercetools.api.models.cart.CartSetCustomLineItemCustomFieldActionBuilder;
import com.commercetools.api.models.cart.CartSetCustomTypeAction;
import com.commercetools.api.models.cart.CartSetCustomTypeActionBuilder;
import com.commercetools.api.models.cart.CartSetCustomerIdAction;
import com.commercetools.api.models.cart.CartSetCustomerIdActionBuilder;
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
import com.commercetools.api.models.common.TypedMoney;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.product_type.AttributePlainEnumValue;
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
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.PeriodicPurchaseDTO;
import com.magnolia.cione.exceptions.CioneException;
import com.magnolia.cione.setup.CioneEcommerceConectionProvider;
import com.magnolia.cione.utils.CioneUtils;
import com.magnolia.cione.utils.MyShopUtils;

import info.magnolia.cms.security.SecuritySupport;
import info.magnolia.cms.security.User;
import info.magnolia.cms.security.UserManager;
import info.magnolia.context.MgnlContext;
import info.magnolia.dam.templating.functions.DamTemplatingFunctions;
import info.magnolia.ecommerce.common.Connection;
import info.magnolia.module.ModuleRegistry;
import info.magnolia.module.mail.MailModule;
import info.magnolia.module.mail.MailTemplate;
import info.magnolia.module.mail.MgnlMailFactory;
import info.magnolia.module.mail.templates.MgnlEmail;
import info.magnolia.repository.RepositoryConstants;
import io.vrap.rmf.base.client.utils.json.JsonUtils;

public class PeriodicPurchaseServiceImpl implements PeriodicPurchaseService {

	private static final Logger log = LoggerFactory.getLogger(PeriodicPurchaseServiceImpl.class);
	
	@Inject
	private ConfigService configService;
	
//	private final SphereClientProvider clientProvider;
//	
//	private final SimpleTranslator i18n;
	
	private Locale locale = new Locale("es");
	
	private ResourceBundle rbundle = ResourceBundle.getBundle("cione-module/i18n/module-cione-module-messages", locale);
	
	@Inject
	private DamTemplatingFunctions damTemplatingFunctions;
	
	@Inject
	private SecuritySupport securitySupport;
	
	@Inject
	private CioneEcommerceConectionProvider cioneEcommerceConectionProvider;
	
	@Inject
	private CommercetoolsService commercetoolsService;
	
	@Inject
	private CustomerService customerService;
	
	@Inject
	private CartServiceImpl cartServiceImpl;
	
//	@Inject
//	private LensService lensservice;
	
	private final ObjectMapper objectMapper;

    @Inject
    //public PeriodicPurchaseServiceImpl(SphereClientProvider clientProvider, SimpleTranslator i18n) {
    public PeriodicPurchaseServiceImpl() {
        //this.clientProvider = clientProvider;
        this.objectMapper = JsonUtils.createObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        //this.i18n = i18n;
        if (MgnlContext.getUser() != null) {
        	if (MgnlContext.getUser().hasRole("cliente_portugal_vco") 
        			|| MgnlContext.getUser().hasRole("socio_cione_portugal") 
        			|| MgnlContext.getUser().hasRole("cliente_portugal")
        			|| MgnlContext.getUser().hasRole("PORLENS")) {
        		locale = new Locale("pt");
        	}
        }
    }
    
    @Override
    public Cart addCartToPeriodicPurchase(PeriodicPurchaseDTO periodicPurchaseDTO) {
    	
    	Cart updatedCart = null;
		
		try {
			Cart originalCart = customerService.getUserCart();
			
			CartReference reference = CartReferenceBuilder.of().id(originalCart.getId()).build();
			
			ReplicaCartDraft replicaCartDraft = ReplicaCartDraftBuilder.of()
			        .reference(reference)
			        .build();
			Cart replicatedCart = cioneEcommerceConectionProvider
					.getApiRoot()
					.carts()
					.replicate()
					.post(replicaCartDraft)
			        .executeBlocking()
			        .getBody();
			
//			CartReplicationDraft cartReplicationDraft = CartReplicationDraftBuilder.of(customerService.getUserCart().toReference()).build();
//	        final Cart replicatedCart = clientProvider.get(connection).executeBlocking(CartReplicationCommand.of(cartReplicationDraft));
	        
	        
	        String idPurchase = getIdPurchase();
	        
	        
	        
	        List<CartUpdateAction> actions = getNewActions(periodicPurchaseDTO, replicatedCart, idPurchase);
			Integer daysbydefault = getDaysByDefault(periodicPurchaseDTO.getStartdate(), periodicPurchaseDTO.getEnddate());
			
			actions.add(CartSetDeleteDaysAfterLastModificationActionBuilder.of().deleteDaysAfterLastModification(daysbydefault).build());
			//updatedCart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(updatedCart, SetDeleteDaysAfterLastModification.of(daysbydefault)));
			
			//updatedCart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(replicatedCart, actions));
		    
		    
		    for (LineItem lineItem : replicatedCart.getLineItems()) {
				CartSetLineItemCustomFieldAction setcustomfield = 
						CartSetLineItemCustomFieldActionBuilder.of().lineItemId(lineItem.getId()).name("numDocEspecial").value(idPurchase).build();
				actions.add(setcustomfield);
			}
//		    
//		    for (LineItem line : linesItem) {
//		    	updatedCart = cioneEcommerceConectionProvider.getClient().executeBlocking(CartUpdateCommand.of(updatedCart, 
//		    			SetLineItemCustomField.ofObject("numDocEspecial", idPurchase, line.getId())));
//		    }
		    
		    for (CustomLineItem customlineItem : replicatedCart.getCustomLineItems()) {
				CartSetCustomLineItemCustomFieldAction setcustomfield = 
						CartSetCustomLineItemCustomFieldActionBuilder.of().customLineItemId(customlineItem.getId()).name("numDocEspecial").value(idPurchase).build();
				actions.add(setcustomfield);
			}
		    
		    CartUpdate cartUpdate = CartUpdateBuilder.of().version(replicatedCart.getVersion()).actions(actions).build();
			updatedCart = cioneEcommerceConectionProvider
					.getApiRoot()
					.carts()
					.withId(replicatedCart.getId())
					.post(cartUpdate).executeBlocking()
					.getBody()
					.get();
		    
		    
	    	return updatedCart;
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return updatedCart;
		}
    	
    }
	
	public Cart removePeriodicPurchase(Connection connection, String id) {
		
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
			
			return deletecart;
		
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			return deletecart;
		}
	}
	
	@Override
	public Response getPeriodicPurchases(String date, String customermail, String numbudget, String numcliente, int page) {
		
		CartPagedQueryResponse actualCarts = null;
		List<String> querys = new ArrayList<>();
		String email = MyShopUtils.getUserName() + "@cione.es";
		final int offset = calcOffset(page);	
		final int limit = 12;		
		
		try {
			
			querys.add("customerEmail=\"" + email + "\"");
			querys.add("cartState=\"Active\"");
			querys.add("custom(fields(cartType=\"" + MyshopConstants.PERIODIC_PURCHASE + "\"))");
			
			if (customermail != null && !customermail.isEmpty()) {
				querys.add("custom(fields(" + MyshopConstants.MAILCLIENTE + "=\"" + customermail.trim() + "\"))");
			}
			if (numbudget != null && !numbudget.isEmpty()) {
				querys.add("custom(fields(" + MyshopConstants.PURCHASE + "=\"" + numbudget.trim() + "\"))");
			}
			if (numcliente != null && !numcliente.isEmpty()) {
				querys.add("custom(fields(" + MyshopConstants.NUMCLIENTE + "=\"" + numcliente.trim() + "\"))");
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
			}
			actualCarts =  cioneEcommerceConectionProvider
				.getApiRoot()
				.carts()
				.get()
				.withWhere(querys)
				.withLimit(limit)
				.withOffset(offset)
				.executeBlocking().getBody();
			
			
			String cartResponse = getBackwardCompatibilityCartResponseAsString(actualCarts);
			return Response.ok(cartResponse).build();
		
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	@Override
	public Response getAllPeriodicPurchases() {
		
		String userName = MyShopUtils.getUserName();
		String email = userName + "@cione.es";
		try {
			/*CartByCustomerIdGet predicate = CartByCustomerIdGet.of(customer.getId());
			CompletionStage<Cart> request = cioneEcommerceConectionProvider.getClient().execute(predicate);
			cart = request.toCompletableFuture().join();*/
			
			
			List<String> querys = new ArrayList<>();
			querys.add("customerEmail=\"" + email + "\"");
			querys.add("cartState=\"Active\"");
			querys.add("custom(fields(cartType=\"" + MyshopConstants.PERIODIC_PURCHASE + "\"))");
			CartPagedQueryResponse carts = 
				cioneEcommerceConectionProvider
				.getApiRoot()
				.carts()
				//.withCustomerId(customer.getId())
				.get()
				.withWhere(querys)
				.executeBlocking()
				.getBody();
			
			String cartResponse = getBackwardCompatibilityCartResponseAsString(carts);
			return Response.ok(cartResponse).build();
		
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		
	}
	
	private List<Cart> getAllPeriodicPurchasesListCarts() {
		
		String userName = MyShopUtils.getUserName();
		String email = userName + "@cione.es";
		List<Cart> carts = new ArrayList<>();
		try {
			/*CartByCustomerIdGet predicate = CartByCustomerIdGet.of(customer.getId());
			CompletionStage<Cart> request = cioneEcommerceConectionProvider.getClient().execute(predicate);
			cart = request.toCompletableFuture().join();*/
			
			
			List<String> querys = new ArrayList<>();
			querys.add("customerEmail=\"" + email + "\"");
			querys.add("cartState=\"Active\"");
			querys.add("custom(fields(cartType=\"" + MyshopConstants.PERIODIC_PURCHASE + "\"))");
			return cioneEcommerceConectionProvider
				.getApiRoot()
				.carts()
				//.withCustomerId(customer.getId())
				.get()
				.withWhere(querys)
				.executeBlocking()
				.getBody()
				.getResults();
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return carts;
		}
		
	}

	@Override
	public Cart getPeriodicPurchaseById(String id) {
		
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
	}
	
	private String getCustomerNumberFromCustomerMail(String mail) {
		int pos = mail.indexOf('@');
		return mail.substring(0, pos);
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
	}

	@Override
	public Cart updatePeriodicPurchase(PeriodicPurchaseDTO periodicPurchaseDTO) {
			
		Cart carttoupdate = getPeriodicPurchaseById(periodicPurchaseDTO.getId());
		
		if (carttoupdate.getCustom() != null 
				&& carttoupdate.getCustom().getFields().values().get(MyshopConstants.PURCHASE) != null 
				&& !((String) carttoupdate.getCustom().getFields().values().get(MyshopConstants.PURCHASE)).isEmpty()) {
			
			List<CartUpdateAction> actions = getActions(periodicPurchaseDTO,carttoupdate);
			CartUpdate cartUpdate = CartUpdateBuilder.of().version(carttoupdate.getVersion()).actions(actions).build();
			
			Cart updatedCart = cioneEcommerceConectionProvider
					.getApiRoot()
					.carts()
					.withId(carttoupdate.getId())
					.post(cartUpdate).executeBlocking()
					.getBody();
			
			return updatedCart;
			
		}else {
			log.error("La compra periodica no se ha podido actualizar no tiene de id.");
			return null;
		}
	}
	
	public Cart addCartToPeriodicPurchase(String id) {
		
		if (id != null && !id.isEmpty()) {
			
			Cart periodicpurchase = getPeriodicPurchaseById(id);
			Cart carrActive = cartServiceImpl.getCart(periodicpurchase.getCustomerId());
			
			Customer customerCar = getCustormerById(periodicpurchase.getCustomerId());
			
			if (MyShopUtils.getUserName().equals(customerCar.getCustomerNumber())) {
				
				for(LineItem item: carrActive.getLineItems()) {
					periodicpurchase = addCartLineItem(periodicpurchase, item);
				}
				
				for(CustomLineItem item: carrActive.getCustomLineItems()) {
					periodicpurchase = addCartCustomLine(periodicpurchase, item);
				}
				
			}
			
		    return carrActive;
			
		}
		
		return null;
	}
	
	private List<String> getPeriodicPurchase(){
		Set<String> uniqueUserNames = new HashSet<>();
		
		List<String> querys = new ArrayList<>();
		querys.add("cartState=\"Active\"");
		querys.add("custom(fields(cartType=\"" + MyshopConstants.PERIODIC_PURCHASE + "\"))");
		List<Cart> periodicPurchase = cioneEcommerceConectionProvider
			.getApiRoot()
			.carts()
			//.withCustomerId(customer.getId())
			.get()
			.withWhere(querys)
			.withLimit(500)
			.withSort("createdAt desc")
			.executeBlocking()
			.getBody()
			.getResults();
		
		periodicPurchase.forEach((carrito) -> {
			String customerEmail = carrito.getCustomerEmail();
			int pos = customerEmail.indexOf("@");
			String customerNumber = customerEmail.substring(0, pos);
			uniqueUserNames.add(customerNumber);
		});
		
		List<String> listPPActive = new ArrayList<>(uniqueUserNames);
		
		return listPPActive;
	}
	
	@Override
	public void setActivePeriodicPurchase() {
		
		List<User> consumers = getAllConsumers();
		
		List<String> listPPActive = getPeriodicPurchase();
		
		consumers.removeIf(user -> !listPPActive.contains(user.getName()));
		
		consumers.stream().forEach(user -> checkUserWithPeriodicPurchase(user));
	
	}
	
	@Override
	public String getStatus(Connection connection) {
		
		String res = "";
		List<Cart> all = getAllPeriodicPurchasesListCarts();
		for(Cart cart : all) {
			if ((cart.getCustom().getFields().values().get(MyshopConstants.STATUS_PURCHASE) !=  null) 
					&& (!StringUtils.isBlank((String)cart.getCustom().getFields().values().get(MyshopConstants.STATUS_PURCHASE)))) {
				
				if (((String)cart.getCustom().getFields().values().get(MyshopConstants.STATUS_PURCHASE)).equals(MyshopConstants.RED_STATUS)){
					res = MyshopConstants.RED_STATUS;
					break;
				}
				
				if (((String)cart.getCustom().getFields().values().get(MyshopConstants.STATUS_PURCHASE)).equals(MyshopConstants.YELLOW_STATUS)){
					res = MyshopConstants.YELLOW_STATUS;
				}
				
				if (((String)cart.getCustom().getFields().values().get(MyshopConstants.STATUS_PURCHASE)).equals(MyshopConstants.GREEN_STATUS)
						&& !res.equals(MyshopConstants.YELLOW_STATUS)){
					res = MyshopConstants.GREEN_STATUS;
				}
			}
		}
		return res;
	}

	@Override
	public Cart getCartByPeriodicPurchaseAndRetrieve(String id) {
		
		if (id != null && !id.isEmpty()) {
			
			Cart periodicpurchase = getPeriodicPurchaseById(id);
			Cart carrActive = cartServiceImpl.getCart(periodicpurchase.getCustomerId());
			
			if (carrActive == null) {
				carrActive = createCart(periodicpurchase.getCustomerId(), periodicpurchase.getCustomerEmail());
			}
			
			Customer customerCar = getCustormerById(periodicpurchase.getCustomerId());
			
			if (MyShopUtils.getUserName().equals(customerCar.getCustomerNumber())) {
				
				for(LineItem item: periodicpurchase.getLineItems()) {
					carrActive = addCartLineItem(carrActive, item);
				}
				
				for(CustomLineItem item: periodicpurchase.getCustomLineItems()) {
					carrActive = addCartCustomLine(carrActive, item);
				}
				
			}
			
			LocalDate today = LocalDate.now();
			//LocalDate today = LocalDate.now().minusMonths(3);
			setStatusPeriodicPurchase(periodicpurchase,MyshopConstants.GREEN_STATUS, today);
		    return carrActive;
			
		}
		
		return null;
	}
	
	private LocalDate formatToLocalDate(String date) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
	}

	private List<CartUpdateAction> getActions(PeriodicPurchaseDTO periodicPurchaseDTO, Cart carttoupdate){
		
		List<CartUpdateAction> actions = new ArrayList<>();
        Map<String, Object> obj = new HashMap<>();
        LocalDate startdate = null; //"YYYY-MM-DD"
        LocalDate enddate = null; //"YYYY-MM-DD"
        String frecuencia = "";
        
        if (periodicPurchaseDTO.getPeriodicity() != null && !periodicPurchaseDTO.getPeriodicity().isEmpty()) {
        	obj.put(MyshopConstants.PERIODICITY_PURCHARSE, periodicPurchaseDTO.getPeriodicity());
        	frecuencia = periodicPurchaseDTO.getPeriodicity();
        }else {
        	if ((carttoupdate.getCustom()!= null) && (carttoupdate.getCustom().getFields().values().get(MyshopConstants.PERIODICITY_PURCHARSE) != null)) {
        		String periodicityPurcharse = (String)carttoupdate.getCustom().getFields().values().get(MyshopConstants.PERIODICITY_PURCHARSE);
            	//String periodicityPurcharse = carttoupdate.getCustom().getFieldAsString(MyshopConstants.PERIODICITY_PURCHARSE);
            	obj.put(MyshopConstants.PERIODICITY_PURCHARSE, periodicityPurcharse);
            	frecuencia = periodicityPurcharse;
        	}
        }
        
		if (periodicPurchaseDTO.getStartdate() != null && !periodicPurchaseDTO.getStartdate().isEmpty()) {
			obj.put(MyshopConstants.DATEINI_PURCHARSE, formatToLocalDate(periodicPurchaseDTO.getStartdate()));
			startdate = formatToLocalDate(periodicPurchaseDTO.getStartdate());
		}else {
			if ((carttoupdate.getCustom()!= null) && (carttoupdate.getCustom().getFields().values().get(MyshopConstants.DATEINI_PURCHARSE) != null)) {
				LocalDate dateIniPurcharse = (LocalDate)carttoupdate.getCustom().getFields().values().get(MyshopConstants.DATEINI_PURCHARSE);
        		obj.put(MyshopConstants.DATEINI_PURCHARSE, dateIniPurcharse);
            	startdate = dateIniPurcharse;
        	}
        }
		
		if (periodicPurchaseDTO.getEnddate() != null && !periodicPurchaseDTO.getEnddate().isEmpty()) {
			obj.put(MyshopConstants.DATEDFIN_PURCHARSE, periodicPurchaseDTO.getEnddate());
			enddate = formatToLocalDate(periodicPurchaseDTO.getEnddate());
		}else {
			if ((carttoupdate.getCustom()!= null) && (carttoupdate.getCustom().getFields().values().get(MyshopConstants.DATEDFIN_PURCHARSE) != null)) {
				LocalDate datedFinPurcharse = (LocalDate)carttoupdate.getCustom().getFields().values().get(MyshopConstants.DATEDFIN_PURCHARSE);
            	obj.put(MyshopConstants.DATEDFIN_PURCHARSE, datedFinPurcharse);
            	enddate = datedFinPurcharse;
        	}
        }
        
        if (periodicPurchaseDTO.getCustomer() != null && !periodicPurchaseDTO.getCustomer().isEmpty()) {
        	obj.put(MyshopConstants.NUMCLIENTE, periodicPurchaseDTO.getCustomer());
        }else {
        	if ((carttoupdate.getCustom()!= null) && (carttoupdate.getCustom().getFields().values().get(MyshopConstants.NUMCLIENTE) != null)) {
        		String numcliente = (String)carttoupdate.getCustom().getFields().values().get(MyshopConstants.NUMCLIENTE);
            	obj.put(MyshopConstants.NUMCLIENTE, numcliente);
        	}
        }
        
		if (periodicPurchaseDTO.getMailconsumer() != null && !periodicPurchaseDTO.getMailconsumer().isEmpty()) {
			obj.put(MyshopConstants.MAILCLIENTE, periodicPurchaseDTO.getMailconsumer());
		}else {
			if ((carttoupdate.getCustom()!= null) && (carttoupdate.getCustom().getFields().values().get(MyshopConstants.MAILCLIENTE) != null)) {
        		String mailcliente = (String)carttoupdate.getCustom().getFields().values().get(MyshopConstants.MAILCLIENTE);
            	obj.put(MyshopConstants.MAILCLIENTE, mailcliente);
        	}
        }
		
		//if (!StringUtils.isEmpty(startdate) && !StringUtils.isEmpty(enddate) && !StringUtils.isEmpty(frecuencia)) {
		if ((startdate != null) && (enddate != null) && !StringUtils.isEmpty(frecuencia)) {
			obj.put(MyshopConstants.STATUS_PURCHASE, getStatusColor(startdate,enddate,frecuencia));
		}
		
		String cartType = MyshopConstants.PERIODIC_PURCHASE;
		obj.put(MyshopConstants.CARTTYPE, cartType);
		String idPurchase = (String)carttoupdate.getCustom().getFields().values().get(MyshopConstants.PURCHASE);
		obj.put(MyshopConstants.PURCHASE, idPurchase);
		FieldContainer fieldContainer = FieldContainerBuilder.of().values(obj).build();
		TypeResourceIdentifier customType = TypeResourceIdentifierBuilder.of().key("customFields-forOrders").build();
		CartSetCustomTypeAction cartSetCustomTypeAction = CartSetCustomTypeActionBuilder.of()
				.type(customType)
				.fields(fieldContainer)
				.build();
		
		
		actions.add(cartSetCustomTypeAction);
		
		return actions;
	}

	private List<CartUpdateAction> getNewActions(PeriodicPurchaseDTO periodicPurchaseDTO, Cart carttoupdate, String idPurchase){
		
		List<CartUpdateAction> actions = new ArrayList<>();
        Map<String, Object> obj = new HashMap<>();
        LocalDate startdate = null;
        LocalDate enddate = null;
        String frecuencia = "";
        
        if (periodicPurchaseDTO.getPeriodicity() != null && !periodicPurchaseDTO.getPeriodicity().isEmpty()) {
        	obj.put(MyshopConstants.PERIODICITY_PURCHARSE, periodicPurchaseDTO.getPeriodicity());
        	frecuencia = periodicPurchaseDTO.getPeriodicity();
        }else {
        	if ((carttoupdate.getCustom()!= null) && (carttoupdate.getCustom().getFields().values().get(MyshopConstants.PERIODICITY_PURCHARSE) != null)) {
        		String periodicityPurcharse = (String)carttoupdate.getCustom().getFields().values().get(MyshopConstants.PERIODICITY_PURCHARSE);
            	//String periodicityPurcharse = carttoupdate.getCustom().getFieldAsString(MyshopConstants.PERIODICITY_PURCHARSE);
            	obj.put(MyshopConstants.PERIODICITY_PURCHARSE, periodicityPurcharse);
            	frecuencia = periodicityPurcharse;
        	}
        }
        
		if (periodicPurchaseDTO.getStartdate() != null && !periodicPurchaseDTO.getStartdate().isEmpty()) {
			obj.put(MyshopConstants.DATEINI_PURCHARSE, formatToLocalDate(periodicPurchaseDTO.getStartdate()));
			startdate = formatToLocalDate(periodicPurchaseDTO.getStartdate());
		}else {
			if ((carttoupdate.getCustom()!= null) && (carttoupdate.getCustom().getFields().values().get(MyshopConstants.DATEINI_PURCHARSE) != null)) {
				LocalDate dateIniPurcharse = (LocalDate)carttoupdate.getCustom().getFields().values().get(MyshopConstants.DATEINI_PURCHARSE);
        		//String dateIniPurcharse = carttoupdate.getCustom().getFieldAsString(MyshopConstants.DATEINI_PURCHARSE);
        		obj.put(MyshopConstants.DATEINI_PURCHARSE, dateIniPurcharse);
            	startdate = dateIniPurcharse;
        	}
        }
		
		if (periodicPurchaseDTO.getEnddate() != null && !periodicPurchaseDTO.getEnddate().isEmpty()) {
			obj.put(MyshopConstants.DATEDFIN_PURCHARSE, periodicPurchaseDTO.getEnddate());
			enddate = formatToLocalDate(periodicPurchaseDTO.getEnddate());
		}else {
			if ((carttoupdate.getCustom()!= null) && (carttoupdate.getCustom().getFields().values().get(MyshopConstants.DATEDFIN_PURCHARSE) != null)) {
				LocalDate datedFinPurcharse = (LocalDate)carttoupdate.getCustom().getFields().values().get(MyshopConstants.DATEDFIN_PURCHARSE);
        		//String datedFinPurcharse = carttoupdate.getCustom().getFieldAsString(MyshopConstants.DATEDFIN_PURCHARSE);
            	obj.put(MyshopConstants.DATEDFIN_PURCHARSE, datedFinPurcharse);
            	enddate = datedFinPurcharse;
        	}
        }
        
        if (periodicPurchaseDTO.getCustomer() != null && !periodicPurchaseDTO.getCustomer().isEmpty()) {
        	obj.put(MyshopConstants.NUMCLIENTE, periodicPurchaseDTO.getCustomer());
        }else {
        	if ((carttoupdate.getCustom()!= null) && (carttoupdate.getCustom().getFields().values().get(MyshopConstants.NUMCLIENTE) != null)) {
        		String numcliente = (String)carttoupdate.getCustom().getFields().values().get(MyshopConstants.NUMCLIENTE);
        		//String numcliente = carttoupdate.getCustom().getFieldAsString(MyshopConstants.NUMCLIENTE);
            	obj.put(MyshopConstants.NUMCLIENTE, numcliente);
        	}
        }
        
		if (periodicPurchaseDTO.getMailconsumer() != null && !periodicPurchaseDTO.getMailconsumer().isEmpty()) {
			obj.put(MyshopConstants.MAILCLIENTE, periodicPurchaseDTO.getMailconsumer());
		}else {
			if ((carttoupdate.getCustom()!= null) && (carttoupdate.getCustom().getFields().values().get(MyshopConstants.MAILCLIENTE) != null)) {
        		String mailcliente = (String)carttoupdate.getCustom().getFields().values().get(MyshopConstants.MAILCLIENTE);
        		//String mailcliente = carttoupdate.getCustom().getFieldAsString(MyshopConstants.MAILCLIENTE);
            	obj.put(MyshopConstants.MAILCLIENTE, mailcliente);
        	}
        }
		
		//if (!StringUtils.isEmpty(startdate) && !StringUtils.isEmpty(enddate) && !StringUtils.isEmpty(frecuencia)) {
		if ((startdate != null) && (enddate != null) && !StringUtils.isEmpty(frecuencia)) {
			obj.put(MyshopConstants.STATUS_PURCHASE, getStatusColor(startdate,enddate,frecuencia));
		}
		
		String cartType = MyshopConstants.PERIODIC_PURCHASE;
		obj.put(MyshopConstants.CARTTYPE, cartType);
		obj.put(MyshopConstants.PURCHASE, idPurchase);
		
		FieldContainer fieldContainer = FieldContainerBuilder.of().values(obj).build();
		TypeResourceIdentifier customType = TypeResourceIdentifierBuilder.of().key("customFields-forOrders").build();
		CartSetCustomTypeAction cartSetCustomTypeAction = CartSetCustomTypeActionBuilder.of()
				.type(customType)
				.fields(fieldContainer)
				.build();
		
		
		actions.add(cartSetCustomTypeAction);
		
		return actions;
	}
	
	//REVISAR, CREO QUE TENDRIA QUE TENER EN CUENTA LA FECHA DE FIN
	private String getStatusColor(LocalDate startdate, LocalDate enddate, String frecuencia) {
		
		String res = MyshopConstants.RED_STATUS;
		int months = getMonthsByFrequency(frecuencia);
        DateTime startDate = DateTime.parse(startdate.toString(), DateTimeFormat.forPattern(MyshopConstants.DATE_PATTERN));
        DateTime nowDate = new DateTime(new Date());
        DateTime futureDate = startDate.plusMonths(months);
        DateTime futureDateMinusTwoDays = futureDate.minusDays(2);
        
        if (nowDate.withTimeAtStartOfDay().isAfter(futureDate.withTimeAtStartOfDay()) || nowDate.withTimeAtStartOfDay().isEqual(futureDate.withTimeAtStartOfDay())) {
        	res = MyshopConstants.RED_STATUS;
        }
        
        Interval interval = new Interval(futureDateMinusTwoDays.withTimeAtStartOfDay(), futureDate.withTimeAtStartOfDay());
        boolean intervalContainsNow = interval.contains(nowDate.withTimeAtStartOfDay());
        
        if (intervalContainsNow) {
        	res = MyshopConstants.YELLOW_STATUS;
        }
        
        if (nowDate.withTimeAtStartOfDay().isBefore(futureDateMinusTwoDays.withTimeAtStartOfDay())) {
        	res = MyshopConstants.GREEN_STATUS;
        }
        
		return res;
	}
	
	private int getMonthsByFrequency(String frecuencia) {
		
		int months = 1;
		
		switch(frecuencia) {
			case MyshopConstants.MENSUAL:
				months = 1;
				break;
			case MyshopConstants.BIMENSUAL:
				months = 2;
				break;
			case MyshopConstants.TRIMESTRAL:
				months = 3;
				break;
			case MyshopConstants.SEMESTRAL:
				months = 6;
				break;
			case MyshopConstants.ANUAL:
				months = 12;
				break;
			default:
				months = 1;
		}
		
		return months;
	}
	
	

	private Cart setStatusPeriodicPurchase(Cart periodicpurchase, String status, LocalDate today) {
		
		List<CartUpdateAction> actions = getActionsAndUpdateStatus(periodicpurchase, status, today);
		CartUpdate cartUpdate = CartUpdateBuilder.of().version(periodicpurchase.getVersion()).actions(actions).build();
		
		return cioneEcommerceConectionProvider
				.getApiRoot()
				.carts()
				.withId(periodicpurchase.getId())
				.post(cartUpdate).executeBlocking()
				.getBody()
				.get();
	}
	
	private List<CartUpdateAction> getActionsAndUpdateStatus(Cart carttoupdate, String status, LocalDate date){
		
		List<CartUpdateAction> actions = new ArrayList<>();
        Map<String, Object> obj = new HashMap<>();
       
    	String periodicityPurcharse = (String)carttoupdate.getCustom().getFields().values().get(MyshopConstants.PERIODICITY_PURCHARSE);
    	obj.put(MyshopConstants.PERIODICITY_PURCHARSE, periodicityPurcharse);
		
    	//LocalDate dateIniPurcharse = (LocalDate)carttoupdate.getCustom().getFields().values().get(MyshopConstants.DATEINI_PURCHARSE);
    	//obj.put(MyshopConstants.DATEINI_PURCHARSE, dateIniPurcharse);
    	obj.put(MyshopConstants.DATEINI_PURCHARSE, date);
		
    	LocalDate datedFinPurcharse = (LocalDate)carttoupdate.getCustom().getFields().values().get(MyshopConstants.DATEDFIN_PURCHARSE);
    	obj.put(MyshopConstants.DATEDFIN_PURCHARSE, datedFinPurcharse);
        
    	String numcliente = (String)carttoupdate.getCustom().getFields().values().get(MyshopConstants.NUMCLIENTE);
    	obj.put(MyshopConstants.NUMCLIENTE, numcliente);
		
    	String mailcliente = (String)carttoupdate.getCustom().getFields().values().get(MyshopConstants.MAILCLIENTE);
    	obj.put(MyshopConstants.MAILCLIENTE, mailcliente);
		
		obj.put(MyshopConstants.STATUS_PURCHASE, status);
		
		String cartType = MyshopConstants.PERIODIC_PURCHASE;
		obj.put(MyshopConstants.CARTTYPE, cartType);
		String idPurchase = (String)carttoupdate.getCustom().getFields().values().get(MyshopConstants.PURCHASE);
		obj.put(MyshopConstants.PURCHASE, idPurchase);
		
		FieldContainer fieldContainer = FieldContainerBuilder.of().values(obj).build();
		TypeResourceIdentifier customType = TypeResourceIdentifierBuilder.of().key("customFields-forOrders").build();
		CartSetCustomTypeAction cartSetCustomTypeAction = CartSetCustomTypeActionBuilder.of()
				.type(customType)
				.fields(fieldContainer)
				.build();
		
		
		actions.add(cartSetCustomTypeAction);
		
		return actions;
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
	
	private Integer getDaysByDefault(String startstr, String endstr) {
		
		DateTime startDate = DateTime.parse(startstr, DateTimeFormat.forPattern(MyshopConstants.DATE_PATTERN));
		DateTime endDate = DateTime.parse(endstr, DateTimeFormat.forPattern(MyshopConstants.DATE_PATTERN));
		
		Days.daysBetween(startDate.toLocalDate(), endDate.toLocalDate()).getDays();
		
		Date now = new Date();
		Date futureDate = DateUtils.addDays(now, Days.daysBetween(startDate.toLocalDate(), endDate.toLocalDate()).getDays());
		long diff = futureDate.getTime() - now.getTime();
		
		return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	private String getIdPurchase() {
		
		String res = "";
		
		Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
        
        res =  "CP" + CioneUtils.getIdCurrentClientERP() + "_" + timestamp.getTime();
		
		return res;
		
	}
	
	private int calcOffset(int page) {
		int res = 0;
		if (page>=1) {
			res = (int) page*12;
		}
		return res;
	}

	private Cart addCartLineItem(Cart cart, LineItem item) {
		
		Cart updatedCart = null;
		try {
			final Map<String, Object> fields = new HashMap<>();
			List<CartUpdateAction> actions = new ArrayList<>();
			Map<String, Object> customs = item.getCustom().getFields().values();
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
			
			if (cart.getCustom() != null 
					&& cart.getCustom().getFields().values().get(MyshopConstants.PURCHASE) != null
					&& !((String)cart.getCustom().getFields().values().get(MyshopConstants.PURCHASE)).isEmpty()) {
				
				String idPurchase = (String)cart.getCustom().getFields().values().get(MyshopConstants.PURCHASE);
				idPurchase = idPurchase.startsWith("CP") ? idPurchase : "CP"  + idPurchase;
				
				fields.put("numDocEspecial", idPurchase);
			}
			
			if(StringUtils.isBlank(customtype)) {customtype = "customFields-forCartLines";}
			
			//final CustomFieldsDraft custom = CustomFieldsDraft.ofTypeKeyAndObjects(customtype, fields);
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
			actions.add(lineItemAction);
			
			CartRecalculateAction cartRecalculateAction = CartRecalculateActionBuilder.of().updateProductData(true).build();
			actions.add(cartRecalculateAction);
			CartUpdate cartUpdate = CartUpdateBuilder.of().version(cart.getVersion()).actions(actions).build();
			updatedCart = cioneEcommerceConectionProvider
				.getApiRoot()
				.carts()
				.withId(cart.getId())
				.post(cartUpdate).executeBlocking()
				.getBody();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return cart;
		}
		
		
		return updatedCart;
	}
	
	private Cart addCartCustomLine(Cart cart, CustomLineItem item) {
		
		Cart updatedCart = null;
		try {
			String idCliente = cart.getCustomerId();
			final Map<String, Object> fields = new HashMap<>();
			Integer cantidad = (int)(long)item.getQuantity();
			
			//String pvo = String.valueOf(item.getMoney().getNumber().doubleValueExact()).replace(".", ",");
			//String pvo = MyShopUtils.formatTypedMoney(item.getMoney());
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
    
    private CartPagedQueryResponse getAllPeriodicPurchasesByUser(String userid) {
		
		String email = userid + "@cione.es";
		
		try {
			List<String> querys = new ArrayList<>();
			querys.add("customerEmail=\"" + email + "\"");
			querys.add("cartState=\"Active\"");
			querys.add("custom(fields(cartType=\"" + MyshopConstants.PERIODIC_PURCHASE + "\"))");
			return cioneEcommerceConectionProvider
				.getApiRoot()
				.carts()
				//.withCustomerId(customer.getId())
				.get()
				.withWhere(querys)
				.withLimit(500)
				.withSort("createdAt desc")
				.executeBlocking()
				.getBody();
		
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
		
		
	}

	private void checkUserWithPeriodicPurchase(User user) {
		
		CartPagedQueryResponse all = getAllPeriodicPurchasesByUser(user.getName());
		if (configService.getConfig().getAuthTestMode()) {
			if (all != null && all.getTotal() != 0 && !StringUtils.isBlank(user.getProperty("email")) && validationMail(user.getProperty("email"))) {
				all.getResults().stream().forEach(cart -> checkPeriodicPurchase(cart,configService.getConfig().getAuthTestEmail()));
			}
		} else {
			
			if (all != null && all.getTotal() != 0 && !StringUtils.isBlank(user.getProperty("email")) && validationMail(user.getProperty("email"))) {
				all.getResults().stream().forEach(cart -> checkPeriodicPurchase(cart,user.getProperty("email")));
			}
		}
	}
	
	public void checkMail(Cart periodicpurchase, String mail) {
		sendPeriodicPurchaseMail(periodicpurchase,mail);
	}
	
	private void checkPeriodicPurchase(Cart periodicpurchase, String mail) {
		
		String actualstatus = (String)periodicpurchase.getCustom().getFields().values().get(MyshopConstants.STATUS_PURCHASE);
		
		if (!actualstatus.contentEquals(MyshopConstants.RED_STATUS)) {
			
			LocalDate startdate = (LocalDate)periodicpurchase.getCustom().getFields().values().get(MyshopConstants.DATEINI_PURCHARSE);
			LocalDate enddate = (LocalDate)periodicpurchase.getCustom().getFields().values().get(MyshopConstants.DATEDFIN_PURCHARSE);
			String frecuencia = (String)periodicpurchase.getCustom().getFields().values().get(MyshopConstants.PERIODICITY_PURCHARSE);
			
			String newstatus = getStatusColor(startdate, enddate, frecuencia);
			
			if (actualstatus.contentEquals(MyshopConstants.GREEN_STATUS) && newstatus.contentEquals(MyshopConstants.YELLOW_STATUS)) {
				setStatusPeriodicPurchase(periodicpurchase, MyshopConstants.YELLOW_STATUS, startdate);
				sendPeriodicPurchaseMail(periodicpurchase,mail);
			}
			
			if (actualstatus.contentEquals(MyshopConstants.GREEN_STATUS) && newstatus.contentEquals(MyshopConstants.RED_STATUS)) {
				setStatusPeriodicPurchase(periodicpurchase, MyshopConstants.YELLOW_STATUS,startdate);
				sendPeriodicPurchaseMail(periodicpurchase,mail);
			}
			
			if (actualstatus.contentEquals(MyshopConstants.YELLOW_STATUS) && newstatus.contentEquals(MyshopConstants.RED_STATUS)){
				setStatusPeriodicPurchase(periodicpurchase, MyshopConstants.RED_STATUS, startdate);
			}
			
		}
		
	}

	private void sendPeriodicPurchaseMail(Cart periodicpurchase, String mail) {
		
		try {
			sendTemplateEmail(rbundle.getString(MyshopConstants.SUBJECT_COMPRA_PERIODICA), mail, buildTemplateEmail(periodicpurchase));
		} catch (CioneException e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		
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
		
	}
	
	private Map<String, Object> buildTemplateEmail(Cart periodicpurchase) {
		
		Map<String, Object> templateValues = new HashMap<>();

		templateValues.put(MailTemplate.MAIL_TEMPLATE_FILE, "cione-module/templates/mail/compra-periodica-mail.ftl");	
		
		templateValues.put("logoHeader", getImageLink("/cione/templates/emails/indice.svg"));
		
		
		if (periodicpurchase.getCustom() != null) {
			templateValues.put("idpurchase", (String)periodicpurchase.getCustom().getFields().values().get(MyshopConstants.PURCHASE));
		}
		
		
		
		StringBuilder itemshtml = new StringBuilder();
		
		for(LineItem item: periodicpurchase.getLineItems()) {
			itemshtml.append(getHTMLLI(item));
		}
		
		for(CustomLineItem item: periodicpurchase.getCustomLineItems()) {
			itemshtml.append(getHTMLCLI(item));
		}
			
		templateValues.put("listado", itemshtml.toString());
		templateValues.put("total", getCarritoTotal(periodicpurchase));

		return templateValues;

	}
	
	private StringBuilder getHTMLCLItem(CustomLineItem item, String fieldname, String title) {
		
		StringBuilder itemhtml = new StringBuilder();
		
		String fieldcontent = getFieldCLI(item, fieldname);
		if(!StringUtils.isEmpty(fieldcontent)) {
			itemhtml.append("<tr>");
			itemhtml.append("<td width=\"20%\" class=\"b2b-email-subtitle\">" + title + "</td>");
			itemhtml.append("<td width=\"80%\">" + fieldcontent + "</td></tr>");
		}
		
		return itemhtml;
		
	}
	
	private StringBuilder getHTMLItem(LineItem item, String fieldname, String title) {
		
		StringBuilder itemhtml = new StringBuilder();
		
		String fieldcontent = getField(item, fieldname);
		if(!StringUtils.isEmpty(fieldcontent)) {
			itemhtml.append("<tr>");
			itemhtml.append("<td width=\"20%\" class=\"b2b-email-subtitle\">" + title + "</td>");
			itemhtml.append("<td width=\"80%\">" + fieldcontent + "</td></tr>");
		}
		
		return itemhtml;
		
	}
	
	private StringBuilder getHTMLLI(LineItem item) {
		
		StringBuilder itemshtml = new StringBuilder();
		
		try {
			String nombreArticulo = "";
			String familiaProducto = getFamilia(item);
			
			if (MyShopUtils.hasAttribute("nombreArticulo", item.getVariant().getAttributes())) {
				nombreArticulo = (String) MyShopUtils.findAttribute("nombreArticulo", item.getVariant().getAttributes()).getValue();
			}else {
				nombreArticulo = item.getName().get(MyshopConstants.esLocale);
			}
			
			itemshtml.append("<section class=\"b2b-email-wrapper-column\">");
			itemshtml.append("<br>&nbsp;<br>");
			itemshtml.append("<div class=\"b2b-email-title\">");
			itemshtml.append(nombreArticulo);
			itemshtml.append("</div>");
			itemshtml.append("<div class=\"b2b-email-container\">");
			itemshtml.append("<table class=\"b2b-email-w-100\">");
			
			//Color
			String color = getColor(item);
			if(!StringUtils.isEmpty(color)) {
				
				itemshtml.append("<tr>");
				itemshtml.append("<td width=\"20%\" class=\"b2b-email-subtitle\">Color y variante</td>");
				itemshtml.append("<td width=\"80%\"><div class=\"b2b-email-color\" style=\"background-color: ");
				itemshtml.append(color);
				itemshtml.append(";\"></div></td>");
				itemshtml.append("</tr>");
			}
			
			//ColorAudifono
			itemshtml.append(getHTMLItem(item, MyshopConstants.COLORAUDIFONO, rbundle.getString(MyshopConstants.I18N_COLOR)));
			
			//Cantidad
			String cantidad = item.getQuantity().toString();
			if(!StringUtils.isEmpty(cantidad)) {
				
				itemshtml.append("<tr>");
				itemshtml.append("<td width=\"20%\" class=\"b2b-email-subtitle\">Cantidad</td>");
				itemshtml.append("<td width=\"80%\">");
				itemshtml.append(cantidad);
				itemshtml.append("</td>");
				itemshtml.append("</tr>");
			}
			
			//Tamanios
			if(familiaProducto.equals("liquidos")) {

				String tamanios = getTamanios(item);
				if(!StringUtils.isEmpty(tamanios)) {
					
					itemshtml.append("<tr>");
					itemshtml.append("<td width=\"20%\" class=\"b2b-email-subtitle\">Tama&ntilde;o</td>");
					itemshtml.append("<td width=\"80%\">");
					itemshtml.append(tamanios);
					itemshtml.append("</td>");
					itemshtml.append("</tr>");
				}
			}
			
			//dimensiones ancho ojo
			itemshtml.append(getHTMLItem(item, MyshopConstants.DIMENSIONES_ANCHO_OJO, rbundle.getString(MyshopConstants.I18N_DIMENSIONESANCHOOJO)));
			
			//graduacion
			itemshtml.append(getHTMLItem(item, MyshopConstants.GRADUACION, rbundle.getString(MyshopConstants.I18N_GRADUACION)));
			
			//LC_ojo
			String lcojo = getField(item, MyshopConstants.LC_OJO);
			if(!StringUtils.isEmpty(lcojo)) {
				String lcojotitle = rbundle.getString(MyshopConstants.I18N_OJO);
				itemshtml.append("<tr>");
				itemshtml.append("<td width=\"20%\" class=\"b2b-email-subtitle\">" + lcojotitle + "</td>");
				
				if (lcojo.equals("D")) {
					lcojo = rbundle.getString(MyshopConstants.I18N_OJOD);
				}else {
					lcojo = rbundle.getString(MyshopConstants.I18N_OJOI);
				}
				
				itemshtml.append("<td width=\"80%\">" + lcojo + "</td></tr>");
			}
			
			//LC_DISENO
			itemshtml.append(getHTMLItem(item, MyshopConstants.LC_DISENO, rbundle.getString(MyshopConstants.I18N_DESIGN)));
			
			//LC_ESFERA
			itemshtml.append(getHTMLItem(item, MyshopConstants.LC_ESFERA, rbundle.getString(MyshopConstants.I18N_ESFERA)));
			
			//LC_CILINDRO
			itemshtml.append(getHTMLItem(item, MyshopConstants.LC_CILINDRO, rbundle.getString(MyshopConstants.I18N_CILINDRO)));
			
			//LC_EJE
			itemshtml.append(getHTMLItem(item, MyshopConstants.LC_EJE, rbundle.getString(MyshopConstants.I18N_EJE)));
			
			//LC_DIAMETRO
			itemshtml.append(getHTMLItem(item, MyshopConstants.LC_DIAMETRO, rbundle.getString(MyshopConstants.I18N_DIAMETRO)));
			
			//LC_CURVABASE
			itemshtml.append(getHTMLItem(item, MyshopConstants.LC_CURVABASE, rbundle.getString(MyshopConstants.I18N_CURVABASE)));
			
			//LC_ADICION
			itemshtml.append(getHTMLItem(item, MyshopConstants.LC_ADICION, rbundle.getString(MyshopConstants.I18N_ADICION)));
			
			//PVO
			itemshtml.append(getHTMLPVOLineItem(item));
			
			itemshtml.append("</table>");
			itemshtml.append("</div>");
			itemshtml.append("</section>");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return itemshtml;
		
	}
	
	private StringBuilder getHTMLCLI(CustomLineItem item) {
		
		StringBuilder itemshtml = new StringBuilder();
		
		try {
			String nombreArticulo = getNombreItemCustom(item);
			
			itemshtml.append("<section class=\"b2b-email-wrapper-column\">");
			itemshtml.append("<br>&nbsp;<br>");
			itemshtml.append("<div class=\"b2b-email-title\">");
			itemshtml.append(nombreArticulo);
			itemshtml.append("</div>");
			itemshtml.append("<div class=\"b2b-email-container\">");
			itemshtml.append("<table class=\"b2b-email-w-100\">");
			
			//Ojo CioneLab
			String lcojo = getFieldCLI(item, MyshopConstants.LC_OJO);
			if(!StringUtils.isEmpty(lcojo)) {
				
				if (lcojo.equals("R")) {
					lcojo = rbundle.getString(MyshopConstants.I18N_OJOD);
				}else {
					lcojo = rbundle.getString(MyshopConstants.I18N_OJOI);
				}
				
				String lcojotitle = rbundle.getString(MyshopConstants.I18N_OJO);
				itemshtml.append("<tr>");
				itemshtml.append("<td width=\"20%\" class=\"b2b-email-subtitle\">" + lcojotitle + "</td>");
				itemshtml.append("<td width=\"80%\">" + lcojo + "</td></tr>");
			}
			
			//Ojo
			String ojo = getFieldCLI(item, MyshopConstants.EYE);
			if(!StringUtils.isEmpty(ojo)) {
				if (ojo.equals("R")) {
					ojo = rbundle.getString(MyshopConstants.I18N_OJOD);
				}else {
					ojo = rbundle.getString(MyshopConstants.I18N_OJOI);
				}
				String lcojotitle = rbundle.getString(MyshopConstants.I18N_OJO);
				itemshtml.append("<tr>");
				itemshtml.append("<td width=\"20%\" class=\"b2b-email-subtitle\">" + lcojotitle + "</td>");
				itemshtml.append("<td width=\"80%\">" + ojo + "</td></tr>");
			}
			
			//SPH
			itemshtml.append(getHTMLCLItem(item, MyshopConstants.SPH, rbundle.getString(MyshopConstants.I18N_ESFERA)));
			
			//SPH_L
			itemshtml.append(getHTMLCLItem(item, MyshopConstants.SPH_L, rbundle.getString(MyshopConstants.I18N_ESFERA)));
			
			//SPH_R
			itemshtml.append(getHTMLCLItem(item, MyshopConstants.SPH_R, rbundle.getString(MyshopConstants.I18N_ESFERA)));
			
			//CYL
			itemshtml.append(getHTMLCLItem(item, MyshopConstants.CYL, rbundle.getString(MyshopConstants.I18N_CILINDRO)));
			
			//CYL_L
			itemshtml.append(getHTMLCLItem(item, MyshopConstants.CYL_L, rbundle.getString(MyshopConstants.I18N_CILINDRO)));
			
			//CYL_R
			itemshtml.append(getHTMLCLItem(item, MyshopConstants.CYL_R, rbundle.getString(MyshopConstants.I18N_CILINDRO)));
			
			//AX_L
			itemshtml.append(getHTMLCLItem(item, MyshopConstants.AX_L, rbundle.getString(MyshopConstants.I18N_EJE)));
			
			//AX_R
			itemshtml.append(getHTMLCLItem(item, MyshopConstants.AX_R, rbundle.getString(MyshopConstants.I18N_EJE)));
			
			//ADD_L
			itemshtml.append(getHTMLCLItem(item, MyshopConstants.ADD_L, rbundle.getString(MyshopConstants.I18N_EJE)));
			
			//ADD_R
			itemshtml.append(getHTMLCLItem(item, MyshopConstants.ADD_R, rbundle.getString(MyshopConstants.I18N_ADICION)));
			
			//CRIB
			itemshtml.append(getHTMLCLItem(item, MyshopConstants.CRIB, rbundle.getString(MyshopConstants.I18N_DIAMETRO)));
			
			//CRIB_L
			itemshtml.append(getHTMLCLItem(item, MyshopConstants.CRIB_L, rbundle.getString(MyshopConstants.I18N_DIAMETRO)));
			
			//CRIB_R
			itemshtml.append(getHTMLCLItem(item, MyshopConstants.CRIB_R, rbundle.getString(MyshopConstants.I18N_DIAMETRO)));
			
			//COLR_L
			itemshtml.append(getHTMLCLItem(item, MyshopConstants.COLR_L, rbundle.getString(MyshopConstants.I18N_COLOR)));
			
			//COLR_R
			itemshtml.append(getHTMLCLItem(item, MyshopConstants.COLR_R, rbundle.getString(MyshopConstants.I18N_COLOR)));
			
			//INF_R
			itemshtml.append(getHTMLCLItem(item, MyshopConstants.INF_R, rbundle.getString(MyshopConstants.I18N_INF_R)));
			
			//PVO
			String pvo = getPVOCLI(item);
			if(!StringUtils.isEmpty(pvo)) {
				String pvotitle = rbundle.getString(MyshopConstants.I18N_PVO);
				itemshtml.append("<tr>");
				itemshtml.append("<td width=\"20%\" class=\"b2b-email-subtitle\">" + pvotitle + "</td>");
				itemshtml.append("<td width=\"80%\">" + pvo + " €</td></tr>");
			}
			
			itemshtml.append("</table>");
			itemshtml.append("</div>");
			itemshtml.append("</section>");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return itemshtml;
		
	}
	
	private String getNombreItemCustom(CustomLineItem item) {
		
		String res = "";
		
		if (getFieldCLI(item,"LNAM_DESC_R") != null) {
			res = getFieldCLI(item,"LNAM_DESC_R");
		}else if (getFieldCLI(item,"LNAM_DESC_L") != null){
			res = getFieldCLI(item,"LNAM_DESC_L");
		}else if(getFieldCLI(item,"DESCRIPTION") != null) {
			res = getFieldCLI(item,"DESCRIPTION");
		}else {
			res = item.getName().get("es");
		}
		
		return res;
		
	}
	
	private StringBuilder getHTMLPVOLineItem(LineItem item) {
		
		StringBuilder itemshtml = new StringBuilder();
		
		Boolean isDeposit = getBooleanField(item, MyshopConstants.ENDEPOSITO);
		if (!isDeposit) {
			
			
			CentPrecisionMoney pvoDescuento = getPVOField(item);
			TypedMoney pvoOrigen = item.getPrice().getValue();//item.getPrice().getValue();
			String pvotitle = rbundle.getString(MyshopConstants.I18N_PVO);
			
			
			if (pvoDescuento != null && (pvoDescuento.getCentAmount() > pvoOrigen.getCentAmount())) {
				
				itemshtml.append("<tr>");
				itemshtml.append("<td width=\"20%\" class=\"b2b-email-subtitle\">" + pvotitle + "</td>");
				itemshtml.append("<td width=\"80%\">" + MyShopUtils.formatTypedMoney(pvoDescuento) + " €</td></tr>");
				
			}else {
				
				itemshtml.append("<tr>");
				itemshtml.append("<td width=\"20%\" class=\"b2b-email-subtitle\">" + pvotitle + "</td>");
				itemshtml.append("<td width=\"80%\">" + MyShopUtils.formatTypedMoney(pvoOrigen) + " €</td></tr>");
			}
				
			
		}else{
			itemshtml.append("<tr>");
			itemshtml.append("<td width=\"20%\" class=\"b2b-email-subtitle\">En Depósito</td>");
			itemshtml.append("<td width=\"80%\">Sí</td></tr>");
		}
		
		return itemshtml;
		
	}
	
	private String getPVOCLI(CustomLineItem item) {
		
		String res = "";
		
		if (getFieldCLI(item,"PVO_R") != null) {
			res = getFieldCLI(item,"PVO_R");
		}else if (getFieldCLI(item,"PVO_L") != null){
			res = getFieldCLI(item,"PVO_L");
		}else {
			res = MyShopUtils.formatTypedMoney(item.getMoney());
			//res = String.valueOf(item.getMoney().getNumber().floatValue()).replace(".", ",");
		}
		
		return res;
		
	}
	
	private String getColor(LineItem item) {
		
		String res = null;
		
		if (MyShopUtils.hasAttribute("colorIcono", item.getVariant().getAttributes())) {
			res =  (String) MyShopUtils.findAttribute("colorIcono", item.getVariant().getAttributes()).getValue();
		}else {
			String familiaProducto = getFamilia(item);
			if (familiaProducto.equals("monturas")) {
				res = "#ffffff";
			}
		}
		return res;
	}

	private String getFieldCLI(CustomLineItem item, String field) {
		
		String res = null;
		
		String filedSTR = (String)item.getCustom().getFields().values().get(field);
		
		if(!StringUtils.isEmpty(filedSTR)) {
			res = filedSTR;
		}
		
		return res;
	}

	private String getField(LineItem item, String field) {
		
		String res = null;
		String filedSTR = (String)item.getCustom().getFields().values().get(field);
		
		if(!StringUtils.isEmpty(filedSTR)) {
			res = filedSTR;
		}
		
		return res;
	}

	private CentPrecisionMoney getPVOField(LineItem item) {
		
		CentPrecisionMoney res = null;
		
		if ((item.getCustom() != null) && (item.getCustom().getFields().values().get("pvoConDescuento") != null)) {
			res = (CentPrecisionMoney)item.getCustom().getFields().values().get("pvoConDescuento");
		}
		
		return res;
	}
	
	@Override
	public String getCarritoTotal(Cart carrito) {
		
		if (carrito == null) {
			return "0";
		} else {
			
			Money total = cartServiceImpl.getPvoByCart(carrito);
			/*Money total = MyShopUtils.getMoney(0);
			
			for (LineItem item: carrito.getLineItems()) {
				
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
			
			for (CustomLineItem item: carrito.getCustomLineItems()) {
				Long suma = (item.getTotalPrice().getCentAmount()) + total.getCentAmount();
				total.setCentAmount(suma);
			}*/
			return MyShopUtils.formatMoney(total);
		}
	}

	private Boolean getBooleanField(LineItem item, String field) {
		if ((item.getCustom() != null) && (item.getCustom().getFields().values().get(field) != null))
			return (Boolean)item.getCustom().getFields().values().get(field);
		else
			return false;
		
	}

	private String getTamanios(LineItem item) {
		
		String res = null;
		
		if (MyShopUtils.hasAttribute("tamanios", item.getVariant().getAttributes()))
			res = ((LocalizedString) MyShopUtils.findAttribute("tamanios", item.getVariant().getAttributes()).getValue()).get(MyshopConstants.esLocale);
		
		return res;
	}
	
	public String getFamilia(LineItem linea) {
		String familia=null;
		if (MyShopUtils.hasAttribute(MyshopConstants.PLANTILLA, linea.getVariant().getAttributes()))
			familia = ((AttributePlainEnumValue) MyShopUtils.findAttribute(MyshopConstants.PLANTILLA, linea.getVariant().getAttributes()).getValue()).getKey();
		//este else sobra si todos los productos tiene contribuida la familia
		else if (MyShopUtils.hasAttribute("tipoProducto", linea.getVariant().getAttributes())) {
				LocalizedString tipoProducto = (LocalizedString) MyShopUtils.findAttribute("tipoProducto", linea.getVariant().getAttributes()).getValue();
				familia = MyShopUtils.getFamiliaProducto(tipoProducto.get(MyshopConstants.esLocale));
		}
		if (familia == null)
			familia = "monturas";
		return familia;
	}

	private List<User> getAllConsumers(){
		
		List<User> consumers = new ArrayList<>();
		UserManager um = securitySupport.getUserManager("public");
		String query = "select * from [mgnl:user] as t where ISDESCENDANTNODE('/public')";
		
		try {
			Session session = MgnlContext.getJCRSession(RepositoryConstants.USERS);
			javax.jcr.query.Query q = session.getWorkspace().getQueryManager().createQuery(query, javax.jcr.query.Query.JCR_SQL2);
			QueryResult queryResult = q.execute();
			NodeIterator nodes = queryResult.getNodes();
			
			while (nodes.hasNext()) {
				Node userNode = nodes.nextNode();
				User user = um.getUser(userNode.getProperty("name").getString());
				consumers.add(user);
			}
		} catch (RepositoryException e) {
			log.error("Error al buscar los consumidores.", e);
		}
		
		return consumers;
		
	}
	
	private boolean validationMail(String emailAddress) {
		String regexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	    return Pattern.compile(regexPattern)
	      .matcher(emailAddress)
	      .matches();
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
	
}
