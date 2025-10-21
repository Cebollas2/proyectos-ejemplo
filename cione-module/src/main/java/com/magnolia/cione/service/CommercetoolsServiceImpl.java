package com.magnolia.cione.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.client.ByProjectKeyProductProjectionsSearchGet;
import com.commercetools.api.client.error.BadRequestException;
import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CustomLineItem;
import com.commercetools.api.models.cart.LineItem;
import com.commercetools.api.models.category.Category;
import com.commercetools.api.models.category.CategoryReference;
import com.commercetools.api.models.category.CategoryTree;
import com.commercetools.api.models.common.CentPrecisionMoney;
import com.commercetools.api.models.common.Image;
import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.common.Price;
import com.commercetools.api.models.common.TypedMoney;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerChangeEmailAction;
import com.commercetools.api.models.customer.CustomerChangeEmailActionBuilder;
import com.commercetools.api.models.customer.CustomerDraft;
import com.commercetools.api.models.customer.CustomerSetCustomFieldAction;
import com.commercetools.api.models.customer.CustomerSetCustomFieldActionBuilder;
import com.commercetools.api.models.customer.CustomerUpdate;
import com.commercetools.api.models.customer.CustomerUpdateAction;
import com.commercetools.api.models.customer.CustomerUpdateBuilder;
import com.commercetools.api.models.customer_group.CustomerGroup;
import com.commercetools.api.models.customer_group.CustomerGroupResourceIdentifier;
import com.commercetools.api.models.product.Attribute;
import com.commercetools.api.models.product.FacetRange;
import com.commercetools.api.models.product.FacetResult;
import com.commercetools.api.models.product.FacetResults;
import com.commercetools.api.models.product.ProductProjection;
import com.commercetools.api.models.product.ProductProjectionPagedSearchResponse;
import com.commercetools.api.models.product.ProductProjectionPagedSearchResponseImpl;
import com.commercetools.api.models.product.ProductVariant;
import com.commercetools.api.models.product.RangeFacetResult;
import com.commercetools.api.models.product.TermFacetResult;
import com.commercetools.api.models.product_type.AttributePlainEnumValue;
import com.commercetools.api.models.tax_category.TaxCategory;
import com.commercetools.api.models.type.CustomFieldsDraft;
import com.commercetools.api.models.type.FieldContainer;
import com.commercetools.api.models.type.TypeResourceIdentifier;
import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dao.PackDao;
import com.magnolia.cione.dao.PromocionesDao;
import com.magnolia.cione.dto.AgrupadorDTO;
import com.magnolia.cione.dto.CategoryNameDTO;
import com.magnolia.cione.dto.ColorDTO;
import com.magnolia.cione.dto.CustomFieldCTDTO;
import com.magnolia.cione.dto.CustomerCT;
import com.magnolia.cione.dto.CustomersQueryCTDTO;
import com.magnolia.cione.dto.DireccionDTO;
import com.magnolia.cione.dto.DireccionesDTO;
import com.magnolia.cione.dto.EmployeeDTO;
import com.magnolia.cione.dto.InfoPackGenericoDTO;
import com.magnolia.cione.dto.MasterProductFrontDTO;
import com.magnolia.cione.dto.PackInfoDTO;
import com.magnolia.cione.dto.ProductFrontDTO;
import com.magnolia.cione.dto.PromocionesDTO;
import com.magnolia.cione.dto.StockMgnlDTO;
import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.dto.VariantDTO;
import com.magnolia.cione.dto.CT.ProductSearchCT;
import com.magnolia.cione.dto.CT.VariantsFilterCT;
import com.magnolia.cione.dto.CT.variants.VariantsAttributes;
import com.magnolia.cione.setup.CategoryUtils;
import com.magnolia.cione.setup.CioneEcommerceConectionProvider;
import com.magnolia.cione.utils.CioneUtils;
import com.magnolia.cione.utils.MyShopUtils;

import info.magnolia.context.MgnlContext;
import io.vrap.rmf.base.client.ApiHttpResponse;

public class CommercetoolsServiceImpl implements CommercetoolsService {
	
	private static final Logger log = LoggerFactory.getLogger(CommercetoolsServiceImpl.class);
	
	@Inject
	private MiddlewareService middlewareService;

	@Inject
	private CioneEcommerceConectionProvider cioneEcommerceConectionProvider;
	
	@Inject
	private CommercetoolsServiceAux commercetoolsServiceAux;
	
	@Inject
	private PromocionesDao promocionesDao;
	
	@Inject
	private CategoryUtils categoryUtils;
	
	@Inject
	private CustomerService customerService;
	
	@Inject
	private PackDao packDao;
	
	public CommercetoolsServiceImpl() {
		super();
		/*PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		cm.setMaxTotal(200); // Increase max total connection to 200
		cm.setDefaultMaxPerRoute(20); // Increase default max connection per route to 20
		
		myEngine = new ApacheHttpClient43Engine(httpClient);*/
		
		//cm.getTotalStats();
		//cm.closeExpiredConnections();
		
		//log.info("create CommercetoolsService instance");
//		if (cioneEcommerceConectionProvider != null)
//			client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(cioneEcommerceConectionProvider.getMyEngine()).build();
//		if (cioneEcommerceConectionProvider == null) {
//			cm = new PoolingHttpClientConnectionManager();
//			CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
//			cm.setMaxTotal(200); // Increase max total connection to 200
//			cm.setDefaultMaxPerRoute(20); // Increase default max connection per route to 20
//			
//			ApacheHttpClient43Engine myEngine = new ApacheHttpClient43Engine(httpClient);
//			client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(myEngine).build();
//		} else {
//			client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(cioneEcommerceConectionProvider.getMyEngine()).build();
//		}
	}
	
	public void destroy() {
		System.out.println("destroy");
	}

	
	@Override
	public String getCustomerGroupByKey(String key) {
		String result = null;
		if(StringUtils.isNotEmpty(key)) {
			String realkey = CioneConstants.equivalenciaEKONCommerceTools.get(key);
			CustomerGroup customerGroup = 
				cioneEcommerceConectionProvider.getApiRoot()
					.customerGroups()
					.withKey(realkey)
					.get()
					.executeBlocking()
					.getBody()
					.get();
			if (customerGroup != null) {
				result = customerGroup.getId();
			}
		}
		return result;
	}
	
	@Override
    public String getGrupoPrecioCommerce() {
		//llamada recurente, lo almacenamos en sesion
		String grupoCommerceTools = "";
		HttpSession session = MgnlContext.getWebContext().getRequest().getSession();
		if ((session.getAttribute(MyshopConstants.ATTR_PRICE_GROUP_SESSION) != null) 
				&& (!session.getAttribute(MyshopConstants.ATTR_PRICE_GROUP_SESSION).toString().isEmpty())) {
			grupoCommerceTools = session.getAttribute(MyshopConstants.ATTR_PRICE_GROUP_SESSION).toString();
		} else {
			UserERPCioneDTO user =middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
	    	if (user != null) {
		    	String grupoEkon = user.getGrupoPrecio();
		    	grupoCommerceTools = CioneConstants.equivalenciaEKONCommerceTools.get(grupoEkon);
		    	session.setAttribute(MyshopConstants.ATTR_PRICE_GROUP_SESSION, grupoCommerceTools);
	    	}
		}
    	return grupoCommerceTools;
    }
	
	@Override
	//revisar como lo migramos al SDK
	public String getCustomFieldIdByKey(String key) {
		
		String res = "";
		
		if (StringUtils.isNotEmpty(key)) {
			
			ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
					.target(cioneEcommerceConectionProvider.getConfigService().getConfig().getApiCommercetoolsPath() + 
							"/" + cioneEcommerceConectionProvider.getConfigService().getConfig().getApiCommercetoolsProjectKey() +
							"/types/key=" + key);
			
			Response response = target.request().header(HttpHeaders.AUTHORIZATION, MyshopConstants.BEARER + cioneEcommerceConectionProvider.getAuthToken()).get();
			
			CustomFieldCTDTO customFieldCTDTO = response.readEntity(CustomFieldCTDTO.class);
			
			if (customFieldCTDTO != null) {
				res = customFieldCTDTO.getId();
			}
			
			response.close();
			
		}
		
		return res;
	}
	
	@Override
	//PROBAR BIEN!
	public Customer createCustomer(EmployeeDTO employeeDTO) throws BadRequestException{
		
		Customer customer = null;
		
		if(employeeDTO != null) {
			
			/*CustomerDTO customerDTO = new CustomerDTO();
			EmployeeCustomerGroupDTO employeeCustomerGroupDTO = new EmployeeCustomerGroupDTO();
			CustomCTDTO customCTDTO = new CustomCTDTO();
			TypeCTDTO typeCTDTO = new TypeCTDTO();
			FieldsCTDTO fieldsCTDTO = new FieldsCTDTO();
			
			customerDTO.setEmail(employeeDTO.getId().concat("@cione.es"));
			customerDTO.setCustomerNumber(employeeDTO.getId());
			customerDTO.setFirstName(getFirstName(employeeDTO)); 
			customerDTO.setLastName(getLastName(employeeDTO));
			customerDTO.setPassword(cioneEcommerceConectionProvider.getConfigService().getConfig().getApiCommercetoolsCustomerPwd());
			
			employeeCustomerGroupDTO.setId(getCustomerGroupByKey(getCustomerGroup(employeeDTO)));
			employeeCustomerGroupDTO.setTypeId("customer-group");
			
			customerDTO.setCustomerGroup(employeeCustomerGroupDTO);
			
			typeCTDTO.setId(getCustomFieldIdByKey("customFields-forCustomers"));
			typeCTDTO.setTypeId("type");
			customCTDTO.setType(typeCTDTO);
			fieldsCTDTO.setCodDirDefault(employeeDTO.getAddress());
			customCTDTO.setFields(fieldsCTDTO);
			
			customerDTO.setCustom(customCTDTO);
			ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
					.target(cioneEcommerceConectionProvider.getConfigService().getConfig().getApiCommercetoolsPath() + 
							"/" + cioneEcommerceConectionProvider.getConfigService().getConfig().getApiCommercetoolsProjectKey() + 
							"/customers");
			
			Response response = target.request().header(HttpHeaders.AUTHORIZATION, MyshopConstants.BEARER + cioneEcommerceConectionProvider.getAuthToken()).post(Entity.entity(customerDTO, "application/json"));
				
			if (response.getStatus() == 201) {
				customerCTDTO = response.readEntity(CustomerCTDTO.class);
			}
			
			response.close();
			*/
			
			CustomerGroupResourceIdentifier customerGroupResourceIdentifier = CustomerGroupResourceIdentifier.builder()
	                .id(getCustomerGroupByKey(getCustomerGroup(employeeDTO)))
	                .build();
			TypeResourceIdentifier typeResourceIdentifier = 
					TypeResourceIdentifier
					.builder()
					.id(cioneEcommerceConectionProvider.getMapTypes().get("customFields-forCustomers").getId())
					.build();
			
			FieldContainer fieldContainer = FieldContainer.builder().addValue("codDirDefault", employeeDTO.getAddress()).build();
			
			CustomFieldsDraft customFieldsDraft = 
					CustomFieldsDraft
					.builder()
					.type(typeResourceIdentifier)
					.fields(fieldContainer)
					.build();
			
			String email = employeeDTO.getId().concat("@cione.es");
			/*el patron para todos (socios y empleados) en commercetools es idsocio@cione.es
			 "ese patron es el unico q podiamos tener para evitar problemas"
			*/
			//if ((employeeDTO.getMail()!= null) && !employeeDTO.getMail().isEmpty())
				//email = employeeDTO.getMail(); //solo para empleados
			CustomerDraft customerDraft = CustomerDraft.builder()
					.email(email)
					//.email(employeeDTO.getMail())
					.customerNumber(employeeDTO.getId())
					.firstName(getFirstName(employeeDTO))
					.lastName(getLastName(employeeDTO))
					.password(cioneEcommerceConectionProvider.getConfigService().getConfig().getApiCommercetoolsCustomerPwd())
					.customerGroup(customerGroupResourceIdentifier)
					.custom(customFieldsDraft)
					.build();
			
			customer = 
					cioneEcommerceConectionProvider
					.getApiRoot()
					.customers()
					.post(customerDraft)
					.executeBlocking()
					.getBody()
					.getCustomer();
			
		}
		
		return customer;
	}
	
	/**
	 * 
	 * Devuelve el nombre de la tienda a la que pertenece un empleado
	 * y sera el nombre del empleado en CT
	 * 
	 * @param employeeDTO empleado no puede ser nulo
	 * @return nombre de la optica a la que pertenece el empleado
	 * 
	 */
	private String getFirstName(EmployeeDTO employeeDTO) {
		
		String res = "";
		
		DireccionesDTO direccionesDTO = middlewareService.getDirecciones(CioneUtils.getidClienteFromNumSocio(employeeDTO.getId()));
		
		for(DireccionDTO direccion : direccionesDTO.getTransportes()) {
			
			if(direccion.getId_localizacion().equals(employeeDTO.getAddress())) {
				res = direccion.getNombre();
			}
		}
		
		return res;
	}
	
	/**
	 * 
	 * Devuelve los ultimos digitos del ID del empleado
	 * para formar el apellido en CT. 
	 * 
	 * @param employeeDTO empleado no puede ser nulo
	 * @return string que formara el apellido del empleado en CT
	 * 
	 */
	private String getLastName(EmployeeDTO employeeDTO) {
		
		String res = employeeDTO.getId().length() > 2 ? employeeDTO.getId().substring(employeeDTO.getId().length() - 2) : employeeDTO.getId();
		
		if (res.equals("00")) {
			res = "";
		}
		
		return res;
	}
	
	private String getCustomerGroup(EmployeeDTO employeeDTO) {
		
		String res = "";
		
		UserERPCioneDTO socio = middlewareService.getUserFromERP(CioneUtils.getidClienteFromNumSocio(employeeDTO.getId()));
		
		if (socio != null) {
			res = socio.getGrupoPrecio();
		}
		
		return res;
	}
	
	@Override
	//PROBAR BIEN!
	public Customer updateCustomerEmployee(EmployeeDTO employeeDTO) throws BadRequestException {
		Customer customer = customerService.getCustomerByCustomerNumber(employeeDTO.getId());
		String email = employeeDTO.getId().concat("@cione.es");
		
		CustomerChangeEmailAction changeEmailAction = CustomerChangeEmailActionBuilder.of()
				.email(email)
		        //.email(employeeDTO.getMail()) // ver si tenemos que actualizar con employeeDTO.getId().concat("@cione.es");
		        .build();
		
		List<CustomerUpdateAction> actions = new ArrayList<>();
		actions.add(changeEmailAction);
		
		CustomerSetCustomFieldAction customerSetCustomFieldAction = 
				CustomerSetCustomFieldActionBuilder
				.of()
				.name("codDirDefault")
				.value(employeeDTO
				.getAddress())
				.build();
		
		actions.add(customerSetCustomFieldAction);

		CustomerUpdate customerUpdate = 
				CustomerUpdateBuilder
				.of()
				.version(customer.getVersion())
				.actions(actions)
				.build();
		
		customer = cioneEcommerceConectionProvider.getApiRoot()
				.customers()
				.withId(customer.getId())
				.post(customerUpdate)
				.executeBlocking()
				.getBody();

		return customer;
	}
	
	/*@Override
	public CustomerCTDTO updateCustomer(EmployeeDTO employeeDTO) {
		
		CustomerCTDTO customerCTDTO = new CustomerCTDTO();
		
		if(employeeDTO != null) {
			
			// hacer la llamada para traernos el cliente con la query
			// de esta llamada nos tenemos que traer el id del cliente a actulizar y la version
			CustomersQueryCTDTO customersQueryCTDTO = new CustomersQueryCTDTO();
			
			ResteasyWebTarget targetquery = cioneEcommerceConectionProvider.getRestEasyClient()
					.target(cioneEcommerceConectionProvider.getConfigService().getConfig().getApiCommercetoolsPath() + 
							"/" + cioneEcommerceConectionProvider.getConfigService().getConfig().getApiCommercetoolsProjectKey() + 
							"/customers?where=customerNumber%3D%22" + employeeDTO.getId() + "%22");
			
			Response responsequery = targetquery.request().header(HttpHeaders.AUTHORIZATION, MyshopConstants.BEARER + cioneEcommerceConectionProvider.getAuthToken()).get();
			
			if (responsequery.getStatus() == 200) {
				
				customersQueryCTDTO = responsequery.readEntity(CustomersQueryCTDTO.class);
				
				if (customersQueryCTDTO.getResults() != null && !customersQueryCTDTO.getResults().isEmpty()) {
					
					UpdateAddressCustomerCTDTO updateAddressCustomerCTDTO = new UpdateAddressCustomerCTDTO();
					ActionCTDTO actionCTDTO = new ActionCTDTO();
					
					List<CustomerCT> results = customersQueryCTDTO.getResults();
					CustomerCT customerCT = results.get(0);
					
					updateAddressCustomerCTDTO.setVersion(customerCT.getVersion());
					actionCTDTO.setAction("setCustomField");
					actionCTDTO.setName("codDirDefault");
					actionCTDTO.setValue(employeeDTO.getAddress());
					List<ActionCTDTO> actions = Arrays.asList(actionCTDTO);
					updateAddressCustomerCTDTO.setActions(actions);
					
					// siguiente llamda con el DTO creado, con lo cambios, para enviarlo a la API
					ResteasyWebTarget targetupdate = cioneEcommerceConectionProvider.getRestEasyClient()
							.target(cioneEcommerceConectionProvider.getConfigService().getConfig().getApiCommercetoolsPath() + 
									"/" + cioneEcommerceConectionProvider.getConfigService().getConfig().getApiCommercetoolsProjectKey() + 
									"/customers/" + customerCT.getId());
					
					Response responseupdate = targetupdate.request().header(HttpHeaders.AUTHORIZATION, MyshopConstants.BEARER + cioneEcommerceConectionProvider.getAuthToken()).post(Entity.entity(updateAddressCustomerCTDTO, "application/json"));
					
					if (responseupdate.getStatus() == 200) {
						CustomerCT customerupdate = responseupdate.readEntity(CustomerCT.class);
						customerCTDTO.setCustomer(customerupdate);
						
					}
					
					responsequery.close();
				}
			}
			
			responsequery.close();
		}
		
		return customerCTDTO;
	}*/
	
	@Override
	public String getKeyOfCustomerGroupById(String id) {
		
		CustomerGroup customerGroup = 
				cioneEcommerceConectionProvider.getApiRoot()
				.customerGroups()
				.withId(id)
				.get()
				.executeBlocking()
				.getBody()
				.get();
		
		if (customerGroup != null)
			return customerGroup.getKey();
		else
			return null;
	}

	@Override
	//MIGRAR
	public String getIdOfCustomerGroupByCostumerId(String id) {
		
		CustomersQueryCTDTO customersQueryCTDTO = new CustomersQueryCTDTO();
		
		ResteasyWebTarget targetquery = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(cioneEcommerceConectionProvider.getConfigService().getConfig().getApiCommercetoolsPath() + 
						"/" + cioneEcommerceConectionProvider.getConfigService().getConfig().getApiCommercetoolsProjectKey() + 
						"/customers?where=customerNumber%3D%22" + id + "%22");
		
		Response responsequery = targetquery.request().header(HttpHeaders.AUTHORIZATION, MyshopConstants.BEARER + cioneEcommerceConectionProvider.getAuthToken()).get();
		
		if (responsequery.getStatus() == 200) {
			
			customersQueryCTDTO = responsequery.readEntity(CustomersQueryCTDTO.class);
			
			if (customersQueryCTDTO.getResults() != null && !customersQueryCTDTO.getResults().isEmpty()) {
				
				List<CustomerCT> results = customersQueryCTDTO.getResults();
				CustomerCT customerCT = results.get(0);
				
				if (customerCT != null) {
					return customerCT.getCustomerGroup().getId();
				}
			}
		}
		
		return null;
	}
	
	@Override
	public String getKeyOfCustomerGroupEKONByRoles(Collection<String> userroles) {
		
		for (String userrol: userroles) {
			if (CioneConstants.equivalenciaRolMagnoliaEkon.containsKey(userrol)) {
				return CioneConstants.equivalenciaRolMagnoliaEkon.get(userrol);
			}
		}
		
		return null;
	}
	
	@Override
	public ProductProjection getProductBysku(String sku, String grupoPrecio) {
		ProductProjection product = null;
		List<String> filters = new ArrayList<>();
		filters.add("variants.sku:\"" + sku + "\"");
		if ((grupoPrecio != null) && !grupoPrecio.isEmpty())
			filters.add("variants.attributes.gruposPrecio:\""+ grupoPrecio +"\"");
		List<ProductProjection> products = cioneEcommerceConectionProvider.getApiRoot().productProjections()
				.search().get()
                .withMarkMatchingVariants(true)
                .withStaged(false) // Para obtener solo los productos publicados
                //.withFilterQuery(f -> f.variants().sku().is(sku)) // Filtra por SKU
                .withFilter(filters)
                .executeBlocking()
                .getBody()
                .getResults();
		if ((products!=null) && !products.isEmpty()) {
			product = products.get(0);
		}
		return product;
	}
	
	public ProductVariant getProductVariantBysku(String sku, String priceGroup) {
		ProductProjection product = getProductBysku(sku, priceGroup);
		if (product == null) {
			log.error("El producto con sku " + sku + " no existe para el grupo de precio "+ priceGroup);
			return null;
		}else
			return findVariantBySku(product, sku);
	}
	
	public ProductVariant findVariantBySku(ProductProjection product, String sku) {
		Optional<ProductVariant> productVariant= null;
		
		if (product.getMasterVariant().getSku().equals(sku)) {
			productVariant = Optional.of(product.getMasterVariant());
		} else {
			productVariant = product.getVariants().stream().filter(v -> sku.equals(v.getSku())).findFirst();
		}
		
		return productVariant.get();
	}
	
	@Override
	public String getKeyOfCustomerGroupByRoles(Collection<String> userroles) {
		
		for (String userrol: userroles) {
			if (CioneConstants.equivalenciaRolMagnoliaCommerceTools.containsKey(userrol)) {
				return CioneConstants.equivalenciaRolMagnoliaCommerceTools.get(userrol);
			}
		}
		
		return null;
	}
	
    public static Double getCentAmountDouble(Long value, int fraction) {
        return value / Math.pow(10, fraction);
    }
	
	@Override
	public List<MasterProductFrontDTO> getProductsFrontByCategory(String category, String keycustomergroup, String customerId) {
		
		String[] splitcategory = category.replace("\"", "").split(",");
		List<String> categoryList = Arrays.asList(splitcategory);
		//filtro para cualquier categoria del listado
		/*String categoryFilter = categoryList.stream()
				.map(id -> "categories(id=\"" + id + "\")")
                .collect(Collectors.joining(" or "));*/
		
		String categoryFilter = "categories.id:" + categoryList.stream()
				.map(id -> "\"" + id + "\"")
                .collect(Collectors.joining(" , "));
		
		ProductProjectionPagedSearchResponse product_search = 
				cioneEcommerceConectionProvider
				.getApiRoot()
				.productProjections()
				.search()
				.get()
				.withMarkMatchingVariants(true)
				.withStaged(false)
				.withFilter(categoryFilter) //PROBAR BIEN!! revisar si lo hace bien
				.addFilter("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
				.withSort("variants.attributes.homeSN desc")
				.addSort("variants.attributes.orderField asc")
				.executeBlocking()
				.getBody();

		
		List<MasterProductFrontDTO> res = new ArrayList<>();
		
		if(product_search.getCount() > 0) { //puede ser getTotal()?
			
			List<String> aliasEKON = new ArrayList<>();
			List<String> codigosCentral = new ArrayList<>();
			
			for(ProductProjection product: product_search.getResults()) {
				
				ProductFrontDTO p = new ProductFrontDTO();
				MasterProductFrontDTO productfront = new MasterProductFrontDTO();
				List<String> colors = new ArrayList<>();
				List<String> calibers = new ArrayList<>();
				List<String> calibrations = new ArrayList<>();
				List<String> tamanios = new ArrayList<>();
				p.setImages2(product.getMasterVariant().getImages());
				p.setSku(product.getMasterVariant().getSku());
				p.setName(product.getName().get("ES"));
				
				if(!product.getMasterVariant().getPrices().isEmpty()) {
					Price price = getPriceBycustomerGroup(keycustomergroup, product.getMasterVariant().getPrices());
					
					if (price != null) {
						/*log.debug("PRECIO PARA EL USUARIO= " + customerId + " CON GRUPO DE PRECIO= " 
							+ keycustomergroup + " Y PRECIO ="
							+ price.getValue().getCentAmount()/100); //paso de centimos a euros (damos por hecho fractionDigit =2*/
						
						p.setPvo(getCentAmountDouble(price.getValue().getCentAmount(), price.getValue().getFractionDigits())); //PROBAR BIEN!!
					}
				}
				
				for(Attribute attr: product.getMasterVariant().getAttributes()) {
					
					if(attr.getName().equals(MyshopConstants.TIENEPROMOCIONES)) {
						p.setPromo((Boolean) attr.getValue());
					}
					
					if(attr.getName().equals(MyshopConstants.TIENERECARGO)) {
						p.setRecargo((Boolean) attr.getValue());
					}
					
					if(attr.getName().equals(MyshopConstants.TIPOPRODUCTO)) {
						p.setTipoproducto(((LocalizedString) attr.getValue()).get(MyshopConstants.esLocale));
						//este else sobra si todos los productos tiene contribuida la familia
						if (p.getFamiliaproducto() == null) 
							p.setFamiliaproducto(MyShopUtils.getFamiliaProducto(p.getTipoproducto()));
					}
					
					if (attr.getName().equals(MyshopConstants.PLANTILLA))
						p.setFamiliaproducto(((AttributePlainEnumValue) (attr.getValue())).getKey());
					
					
					
					if(attr.getName().equals(MyshopConstants.COLECCION)) {
						p.setColeccion(((LocalizedString) attr.getValue()).get(MyshopConstants.esLocale));
					}
					
					if(attr.getName().equals(MyshopConstants.CODIGOCENTRAL)) {
						p.setCodigocentral((String) attr.getValue());
					}
					
					if(attr.getName().equals(MyshopConstants.ALIASEKON)) {
						p.setAliasEKON((String) attr.getValue());
					}
					
					if(attr.getName().equals(MyshopConstants.COLORICONO)) {
						p.setColor((String) attr.getValue());
						//setList(colors,attr.getValueAsString());
					}	
					
					if(attr.getName().equals(MyshopConstants.CODIGOCOLOR)) {
						p.setCodigocolor((String) attr.getValue());
					}
					
					if(attr.getName().equals(MyshopConstants.COLORMONTURA)) {
						p.setColorMontura(((LocalizedString) attr.getValue()).get(MyshopConstants.esLocale));
					}	
					
					if(attr.getName().equals(MyshopConstants.DIMENSIONES_ANCHO_OJO)) {
						p.setCalibre(((Long) attr.getValue()).toString());
						setList(calibers,p.getCalibre());
					}				
					
					if(attr.getName().equals(MyshopConstants.GRADUACION)) {
						p.setCalibration((String) attr.getValue());
						setList(calibrations,(String) attr.getValue());
					}
					
					if(attr.getName().equals(MyshopConstants.PVPRECOMENDADO)) {
						CentPrecisionMoney pvp = (CentPrecisionMoney) attr.getValue();
						p.setPvp(getCentAmountDouble(pvp.getCentAmount(), pvp.getFractionDigits()));
					}
					
					if(attr.getName().equals(MyshopConstants.PLAZOENTREGAPROVEEDOR)) {
						p.setDelivery(((Long)attr.getValue()).intValue());
					}
					
					if(attr.getName().equals(MyshopConstants.TAMANIO)) {
						p.setTamanio(((LocalizedString) attr.getValue()).get(MyshopConstants.esLocale));
						setList(tamanios, (((LocalizedString) attr.getValue()).get(MyshopConstants.esLocale)));
					}
					
					if(attr.getName().equals(MyshopConstants.COLOR)) {
						ArrayList<String> coloresAudio = (ArrayList<String>) attr.getValue();
						p.setColorsAudio(coloresAudio);
					}
					if(attr.getName().equals(MyshopConstants.AMEDIDA)) {
						p.setaMedida((boolean) attr.getValue());
					}
					
					if (attr.getName().equals(MyshopConstants.PVOSINPACK)) {
						CentPrecisionMoney pvoSinPack = (CentPrecisionMoney) attr.getValue();
						p.setPvoSinPack(getCentAmountDouble(pvoSinPack.getCentAmount(), pvoSinPack.getFractionDigits()));
					}
					if(attr.getName().equals(MyshopConstants.STATUSEKON)) {
						p.setStatusEkon((String) attr.getValue());
					}
					if(attr.getName().equals(MyshopConstants.OFERTAFLASH)) {
						p.setOfertaFlash((Boolean) attr.getValue());
					}
					if(attr.getName().equals(MyshopConstants.NOMBREARTICULO)) {
						//pvar.setName((String) attr.getValue());
						p.setNombreArticulo((String) attr.getValue());
					}
					if(attr.getName().equals(MyshopConstants.GESTIONSTOCK)) {
						p.setGestionStock((Boolean) attr.getValue());
					}
					
				}
				
				if (p.isaMedida()) {
					p.setFamiliaproducto("audiolab");
				}
				
				if ((p.getFamiliaproducto() != null) && (p.getFamiliaproducto().equals("pack-generico"))) { //control navegacion para productos de tipo packgenerico 
					p.setPackNavegacionDetalle(true);
					p.setDescripcionPack(getName(product.getDescription()));
				}
				
				setList(aliasEKON,p.getAliasEKON());
				setList(codigosCentral,p.getCodigocentral());
				
				//p.setStock(getStock(p.getAliasEKON()));
				productfront.setMaster(p);
				
				String finalColor = getCodigoAndColor(p);
				
				if (finalColor != null && !finalColor.isEmpty()) {
					p.setColor(finalColor);
					setList(colors,finalColor);
				}
				
				List<ProductFrontDTO> variants = new ArrayList<>();
				
				//Map <String, VariantDTO> promociones = new HashMap<String, VariantDTO>();
				
				for(ProductVariant variant: product.getVariants()) {
					
					ProductFrontDTO pvar = new ProductFrontDTO();
					pvar.setSku(variant.getSku());
					pvar.setImages2(variant.getImages());
					pvar.setName(product.getName().get("ES")); 
					
					if(!variant.getPrices().isEmpty()) {
						Price price = getPriceBycustomerGroup(keycustomergroup, product.getMasterVariant().getPrices());
						if (price != null) {
							TypedMoney priceMoney = price.getValue();
							pvar.setPvo(getCentAmountDouble(priceMoney.getCentAmount(), priceMoney.getFractionDigits()));
						}
					}
					
					for(Attribute attr: variant.getAttributes()) {
						
						if(attr.getName().equals(MyshopConstants.TIENEPROMOCIONES)) {
							pvar.setPromo((Boolean) attr.getValue());
						}
						
						if(attr.getName().equals(MyshopConstants.TIPOPRODUCTO)) {
							pvar.setTipoproducto(((LocalizedString) attr.getValue()).get(MyshopConstants.esLocale));
							//este else sobra si todos los productos tiene contribuida la familia
							if (pvar.getFamiliaproducto() == null) 
								pvar.setFamiliaproducto(MyShopUtils.getFamiliaProducto(pvar.getTipoproducto()));
						}
						if (attr.getName().equals(MyshopConstants.PLANTILLA))
							pvar.setFamiliaproducto(((AttributePlainEnumValue) (attr.getValue())).getKey());
						
						if(attr.getName().equals(MyshopConstants.COLECCION)) {
							pvar.setColeccion(((LocalizedString) attr.getValue()).get(MyshopConstants.esLocale));
						}
						
						if(attr.getName().equals(MyshopConstants.CODIGOCENTRAL)) {
							pvar.setCodigocentral((String) attr.getValue());
						} 
							
						if(attr.getName().equals(MyshopConstants.ALIASEKON)) {
							pvar.setAliasEKON((String) attr.getValue());
						}
						
						if(attr.getName().equals(MyshopConstants.NOMBREARTICULO)) {
							//pvar.setName((String) attr.getValue());
							pvar.setNombreArticulo((String) attr.getValue());
						}
						
						if(attr.getName().equals(MyshopConstants.COLORICONO)) {
							pvar.setColor((String) attr.getValue());
							setList(colors,(String) attr.getValue());
						}				
						
						if(attr.getName().equals(MyshopConstants.DIMENSIONES_ANCHO_OJO)) {
							pvar.setCalibre(((Long) attr.getValue()).toString());
							setList(calibers,pvar.getCalibre());
						}				
						
						if(attr.getName().equals(MyshopConstants.GRADUACION)) {
							pvar.setCalibration((String) attr.getValue());
							setList(calibrations,(String) attr.getValue());
						}
						
						if(attr.getName().equals(MyshopConstants.PVPRECOMENDADO)) {
							CentPrecisionMoney pvp = (CentPrecisionMoney) attr.getValue();
							pvar.setPvp(getCentAmountDouble(pvp.getCentAmount(), pvp.getFractionDigits()));
						}
						
						if(attr.getName().equals(MyshopConstants.PLAZOENTREGAPROVEEDOR)) {
							pvar.setDelivery(((Long)attr.getValue()).intValue());
						}
						
						if(attr.getName().equals(MyshopConstants.TAMANIO)) {
							pvar.setTamanio(((LocalizedString) attr.getValue()).get(MyshopConstants.esLocale));
							setList(tamanios, (((LocalizedString) attr.getValue()).get(MyshopConstants.esLocale)));
						}
						if(attr.getName().equals(MyshopConstants.AMEDIDA)) {
							pvar.setaMedida((boolean) attr.getValue());
						}
						if (attr.getName().equals(MyshopConstants.PVOSINPACK)) {
							CentPrecisionMoney pvoSinPack = (CentPrecisionMoney) attr.getValue();
							pvar.setPvoSinPack(getCentAmountDouble(pvoSinPack.getCentAmount(), pvoSinPack.getFractionDigits()));
						}
						if(attr.getName().equals(MyshopConstants.STATUSEKON)) {
							pvar.setStatusEkon((String) attr.getValue());
						}
						if(attr.getName().equals(MyshopConstants.OFERTAFLASH)) {
							pvar.setOfertaFlash((Boolean) attr.getValue());
						}
						if(attr.getName().equals(MyshopConstants.GESTIONSTOCK)) {
							pvar.setGestionStock((Boolean) attr.getValue());
						}
						
					}
					
					setList(aliasEKON,pvar.getAliasEKON());
					setList(codigosCentral,pvar.getCodigocentral());
					
					if (p.isaMedida()) {
						p.setFamiliaproducto("audiolab");
					}
					//pvar.setStock(getStock(pvar.getAliasEKON()));
					
					String finalColorVar = getCodigoAndColor(pvar);
					
					if (finalColorVar != null && !finalColorVar.isEmpty()) {
						pvar.setColor(finalColorVar);
						setList(colors,finalColorVar);
						
						
					}
					
					variants.add(pvar);
					
					//promociones.put(product.getMasterVariant().getSku(), getPromocionesByVariant(variant));
					
				}
				List<ColorDTO> colorsDto = getInfoListColors(product, null);
				productfront.setColorsdto(colorsDto);
				productfront.setVariants(variants);
				productfront.setCalibers(calibers);
				productfront.setCalibrations(calibrations);
				productfront.setColors(colors);
				productfront.setTamanios(tamanios);
				
				//productfront.setPromociones(promociones);
				
				
				if (productfront.getMaster().getFamiliaproducto() != null) 
					res.add(productfront);
				else
					log.error("producto sin Familia " + productfront.getMaster().getSku());
			}
			
			addPromotionalProducts(aliasEKON, codigosCentral ,keycustomergroup,res);
			return res;
		}
		
		return Collections.emptyList();
	}
	
	public VariantDTO getPromocionesByVariant(ProductVariant variante) {
		VariantDTO result = new VariantDTO();
		
		result.setGruposPrecio(getGrupoPrecioCommerce());
		if (MyShopUtils.hasAttribute("tipoProducto", variante.getAttributes()))
			result.setTipoProducto(((LocalizedString) MyShopUtils.findAttribute("tipoProducto", variante.getAttributes()).getValue()).get(MyshopConstants.esLocale)); 
		
		if (MyShopUtils.hasAttribute("codigoCentral", variante.getAttributes()))
			result.setAliasEkon((String) MyShopUtils.findAttribute("codigoCentral", variante.getAttributes()).getValue());
		
		if (MyShopUtils.hasAttribute("aliasEkon", variante.getAttributes()))
			result.setAliasEkon((String) MyShopUtils.findAttribute("aliasEkon", variante.getAttributes()).getValue());
		
		if (MyShopUtils.hasAttribute("tienePromociones", variante.getAttributes()))
			result.setTienePromociones((Boolean) MyShopUtils.findAttribute("tienePromociones", variante.getAttributes()).getValue());
		
		if (MyShopUtils.hasAttribute("tieneRecargo", variante.getAttributes()))
			result.setTieneRecargo((Boolean) MyShopUtils.findAttribute("tieneRecargo", variante.getAttributes()).getValue());
		
		result.setTipoPromocion("sin-promo");
		Price price = getPriceBycustomerGroup(result.getGruposPrecio(), variante.getPrices());
		//for (Price price : prices) {
		if(price.getCustomerGroup() == null) {
			result.setPvo(MyShopUtils.formatTypedMoney(price.getValue()));
			
			if(result.isTienePromociones() || result.isTieneRecargo()) {
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
					
					
						/*PromocionesDTO promo = listPromos.get(0);
						double pvodto = 0;
						if(promo.getTipo_descuento() == MyshopConstants.dtoPorcentaje) {
							result.setTipoPromocion("porcentaje");
							double dto = ((100 - promo.getValor()) / 100);
							pvodto = Double.valueOf(result.getPvo().replace(',', '.')) * dto;
							result.setValorDescuento(String.valueOf(promo.getValor()).replace('.', ','));
						}
						pvodto = MyShopUtils.round(pvodto, 2);
						if(promo.getValor() < 0) { //es un incremento, no lo marcamos como promocion pero actualizamos su pvo con el incremento
							result.setPvo(String.valueOf(pvodto).replace('.', ','));
							result.setTienePromociones(false);
						} else {
							result.setPvoDto(String.valueOf(pvodto).replace('.', ','));
						}*/
				} else { //si no hay promociones para ese grupo de precio 
					result.setTienePromociones(false);
				}
			}
		}
		//}
		
		return result;
	}

	@Override
	public int getStock(String sku) {
		
		float stock = middlewareService.getStock(sku);
		
		return (int) stock;
	}

	@Override
	public StockMgnlDTO getStockDTO(String sku) {
		
		return middlewareService.getStockDTO(sku);
	}
	
	@Override
	public int getStockWithCart(String sku, String aliasEkon) {
		float stock = 0;
		if ((aliasEkon == null) || (aliasEkon.isEmpty()))
			stock = middlewareService.getStock(sku);
		else
			stock = middlewareService.getStock(aliasEkon);
		String customerId = getCustomerId();
		if (customerId != null) {
			Cart cart = getCart(customerId);
			 if (cart != null) { 
				List<LineItem> listItems = cart.getLineItems();
				//CartQueryBuilder
				for (LineItem item: listItems) {
					if (item.getVariant().getSku().equals(sku)) {
						stock = stock - item.getQuantity();
						return (int) stock;
					}
				}
			 }
		}
		return (int) stock;
	}
	
	@Override
	public int getUnitsCart(String sku) {
		
		float units = 0;
		
		CustomerCT customer = getCustomer();
		if (customer != null) {
			Cart cart = getCart(customer.getId());
			 if (cart != null) { 
				List<LineItem> listItems = cart.getLineItems();
				//CartQueryBuilder
				if(!listItems.isEmpty()) {
					for (LineItem item: listItems) {
						
						String LC_sku = (String) item.getCustom().getFields().values().get("LC_sku");
						if (LC_sku != null 
							&& !LC_sku.isEmpty()
							&& LC_sku.equals(sku) 
							|| item.getVariant().getSku().equals(sku)) {
							units += item.getQuantity();
						}
					}
				}
				
				List<CustomLineItem> customListItems = cart.getCustomLineItems();
				
				if(!customListItems.isEmpty()) {
					for (CustomLineItem customitem: customListItems) {
						String customSku = (String) customitem.getCustom().getFields().values().get("SKU");
						if (customSku != null && customSku.equals(sku)) {
							units += customitem.getQuantity();
						}
					}
				}
			 }
		}
		
		return (int) units;
	}

	private void setList(List<String> list, String str) {
		
		if (!list.contains(str)) {
			list.add(str);
		}
		
	}
	
	/*private Double getPVP(String str) {
		
		Value val = new Value();
		String s = str.replace("{", "");
		String strtoSplit = s.replace("}", "");
		
		String[] pairs = strtoSplit.split(",");
		
		for (int i=0; i<pairs.length; i++) {
		    String pair = pairs[i];
		    String[] keyValue = pair.split("=");
		    
		    if (keyValue[0].trim().equals(MyshopConstants.TYPE))
		    	val.setType(keyValue[1]);
		    
		    if (keyValue[0].trim().equals(MyshopConstants.CURRENCYCODE))
		    	val.setCurrencyCode(keyValue[1]);
		    
	    	if (keyValue[0].trim().equals(MyshopConstants.CENTAMOUNT))
	    		val.setCentAmount(Integer.parseInt(keyValue[1]));
		    
	    	if (keyValue[0].trim().equals(MyshopConstants.FRACTIONDIGITS))
	    		val.setFractionDigits(Integer.parseInt(keyValue[1]));
		}
		
		return val.toDouble();
			
	}*/
	
	private void addPromotionalProducts(List<String> aliasEKON, List<String> codigosCentral, String grupoPrecio, List<MasterProductFrontDTO> products) {
		
		if (aliasEKON.isEmpty() || codigosCentral.isEmpty()){
			log.error("Error al obtener los descuentos o recargos en la BBDD AliasEkon isEmpty ");
		} else {
			try {
				
				boolean isPack= false;
				int step = -1;
				if (MgnlContext.getParameter("skuPackMaster") != null) {
					isPack=true;
					String step_str = MgnlContext.getWebContext().getRequest().getParameter("step");
					try {
						step = Integer.valueOf(step_str).intValue();
					} catch (NumberFormatException e) {
						log.error(e.getMessage(), e);
					}
				}
				
				Context initContext = new InitialContext();
				DataSource ds = (DataSource) initContext.lookup(MyshopConstants.CONN);
				
				try (Connection con = ds.getConnection();
					PreparedStatement ps = createPreparedStatementDescuentos(con, aliasEKON, codigosCentral, grupoPrecio);
					ResultSet rs = ps.executeQuery()) {
	
						while (rs.next()) {	
							
							String aliasEkon = rs.getString(MyshopConstants.ALIASEKON);
							/*if (aliasEkon.equals("*")) {
								aliasEkon = rs.getString(MyshopConstants.CODIGO_CENTRAL);
							}*/
							String descripcion = rs.getString(MyshopConstants.DESCRIPCION);
							String codigocentral = rs.getString(MyshopConstants.CODIGO_CENTRAL);
							Float dtoporcentaje = rs.getFloat(MyshopConstants.VALOR);
							int desde = rs.getInt(MyshopConstants.CANT_DESDE);
							int tipo_descuento = rs.getInt(MyshopConstants.TIPO_DESCUENTO);
							//log.info("_____________" + aliasEkon + " : " + descripcion);
							if (descripcion == null) {
								descripcion = "";
							}
							
							for(MasterProductFrontDTO product: products) {
								if (dtoporcentaje < 0) {
									product.setSurchargeByAlias(aliasEkon, codigocentral, dtoporcentaje, tipo_descuento);	
									
									if (isPack) {
										try {
											product.getMaster().setPvoPack(getTipoPvoPackListadoFilter(product.getMaster().getPvo(), product.getMaster().getTipoPrecioPack(), product.getMaster().getTipoproducto(), step));
											
											for(ProductFrontDTO variant : product.getVariants()) {
												variant.setPvoPack(getTipoPvoPackListadoFilter(variant.getPvo(), variant.getTipoPrecioPack(), variant.getTipoproducto(), step));
											}
										} catch (Exception e) {
											log.error(e.getMessage(),e);
										}
									}
									
								}else {
									product.setDiscontByAlias(aliasEkon, codigocentral, dtoporcentaje, tipo_descuento, desde, descripcion);
									if (isPack) {
										try {
											product.getMaster().setPvoPack(getTipoPvoPackListadoFilter(product.getMaster().getPvo(), product.getMaster().getTipoPrecioPack(), product.getMaster().getTipoproducto(), step));
											
											for(ProductFrontDTO variant : product.getVariants()) {
												variant.setPvoPack(getTipoPvoPackListadoFilter(variant.getPvo(), variant.getTipoPrecioPack(), variant.getTipoproducto(), step));
											}
										} catch (Exception e) {
											log.error(e.getMessage(),e);
										}
									}
								}
							}
						}
		
				} catch (SQLException e) {
					log.error("Error al obtener los descuentos o recargos en la BBDD", e);
				}
				
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	}
	
	public PreparedStatement createPreparedStatementDescuentos(Connection con, List<String> aliasEKON, List<String> codigosCentral, String grupoPrecio)
			throws SQLException {
		
		if (!aliasEKON.isEmpty()) {
			
			Date today = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formatter.format(today);
			
			String sql = "SELECT aliasEkon, codigo_central, descripcion, fecha_desde, tipo_descuento, fecha_hasta, valor, cantidad_desde " + 
						 "FROM tbl_descuentos " + 
						 "WHERE grupo_precio = '" + grupoPrecio;	
			
			
			StringBuilder bldsql = new StringBuilder(sql);
			
			if (!aliasEKON.isEmpty()) {
				bldsql.append("' AND (");
				Iterator<String> iteratorAliasEkon = aliasEKON.iterator();
				while (iteratorAliasEkon.hasNext()) {
					
			        String alias = iteratorAliasEkon.next();
			        if (alias== null)
			        	alias="*";
			        
			        if (!iteratorAliasEkon.hasNext()) {
			        	bldsql.append("aliasEkon='" + alias + "')");
			        }else {
			        	bldsql.append("aliasEkon='" + alias + "' OR ");
			        }
			     }
			}
			
			if (!codigosCentral.isEmpty()) {
				bldsql.append(" AND (");
				Iterator<String> iteratorCodigoCentral = codigosCentral.iterator();
				while (iteratorCodigoCentral.hasNext()) {
					
			        String codigo = iteratorCodigoCentral.next();
			        
			        if (!iteratorCodigoCentral.hasNext()) {
			        	bldsql.append("codigo_central='" + codigo + "')");
			        }else {
			        	bldsql.append("codigo_central='" + codigo + "' OR ");
			        }
			     }
			}
			
			
			Date fechaHoy = new Date();
			bldsql.append(" AND fecha_desde <= '" + new java.sql.Date(fechaHoy.getTime()) + "'");
			bldsql.append(" AND fecha_hasta >= '" + new java.sql.Date(fechaHoy.getTime()) + "'");
			
			//bldsql.append("AND (now() BETWEEN fecha_desde AND fecha_hasta)");

			return con.prepareStatement(bldsql.toString());
		}
		
		return con.prepareStatement("");
		
	}


	@Override
	public String getCategoryTitleById(String categoryid, String locale) {
		
		String res = "";
		
		ResteasyWebTarget targetquery = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(cioneEcommerceConectionProvider.getConfigService().getConfig().getApiCommercetoolsPath() + 
						"/" + cioneEcommerceConectionProvider.getConfigService().getConfig().getApiCommercetoolsProjectKey() + 
						"/categories/" + categoryid);
		
		Response response = targetquery.request().header(HttpHeaders.AUTHORIZATION, MyshopConstants.BEARER + cioneEcommerceConectionProvider.getAuthToken()).get();
		
		if (response.getStatus() == 200) {
			
			CategoryNameDTO categoryName = response.readEntity(CategoryNameDTO.class);
			
			if (locale.isEmpty()) {
				res = categoryName.name.getEs();
			}else {
				switch(locale) {
					case "en":
						return categoryName.name.getEn();
					case "pt":
						return categoryName.name.getPt();
					default:
						return categoryName.name.getEs();
				}
			}
			
		}
		
		return res;
	}
	
	private List<String> getCategoryIdsById(String categoryid) {
		
		List<String> listCategories = new ArrayList<String>();
		if (categoryid != null) {
			CategoryTree tree = categoryUtils.getCategoryTreeById(categoryid);
			if (tree != null) {
				List<Category> listado= tree.getAllAsFlatList();
				for (Category categoryChild: listado) {
					listCategories.add(categoryChild.getId());
				}
			} else {
				listCategories.add(categoryid);
			}
			
			//REFACTOR DE CATEGORIAS
	        
	//        QueryPredicate<Category> predicate = CategoryQueryModel.of().parent().id().is(categoryid);
	//        Query<Category> querymarcas = CategoryQuery.of().withPredicates(predicate);
	//        
	//        CompletionStage<PagedQueryResult<Category>> marcasquery = cioneEcommerceConectionProvider.getClient().execute(querymarcas);
	//        PagedQueryResult<Category> actual = marcasquery.toCompletableFuture().join();
	//        
	//        List<Category> categories = actual.getResults();
	//        
	//        List<String> listCategories = categories.stream().map(Category::getId).collect(Collectors.toList());
	//        listCategories.add(categoryid);
		}
        
        return listCategories;
	}
	
	private VariantsAttributes getVariantsAttributes(FacetResult fr) {
		
		VariantsAttributes vres = new VariantsAttributes();
		
		if (fr != null) {
			TermFacetResult tfr = (TermFacetResult) fr;
			vres.setTermsStats(tfr.getTerms());
		}
		
		return vres;
		
	}
	
	@Override
	public VariantsFilterCT getFacetsGenerico(String categoryid, String keycustomergroup, Map<String, String[]> filters, ArrayList<String>facetas) {
		
		Iterable<String> listMonturas = getCategoryIdsById(categoryid);
		
		List<String> filtros = addFilters(filters);
		ByProjectKeyProductProjectionsSearchGet actualProject = cioneEcommerceConectionProvider.getApiRoot().productProjections()
				.search()
				.get()
				.withStaged(false)
				.withFilterQuery("categories.id:\"" + String.join("\",\"", listMonturas) + "\"")
				.addFilterQuery("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
				.addFilterQuery(filtros)
				.withPriceCurrency("EUR")
				//fijamos un rango minimo y un maximo
				.addFacet(MyshopConstants.FF_PRICE + MyshopConstants.FF_ALL_RANGES);
		
		ByProjectKeyProductProjectionsSearchGet actualProjectAux= new ByProjectKeyProductProjectionsSearchGet(actualProject);
		for (String faceta: facetas) {
			actualProjectAux = new ByProjectKeyProductProjectionsSearchGet(actualProjectAux).addFacet(faceta);
		}
				
		ProductProjectionPagedSearchResponse actual = actualProjectAux
		.withSort("createdAt asc")
		.executeBlocking()
		.getBody();
		
		@NotNull @Valid FacetResults facets = actual.getFacets();
		
		VariantsFilterCT fres = new VariantsFilterCT();
		Map<String, FacetResult> facetsMap = facets.values();
		fres.setVariantsAttributes(facetsMap);
		
		for (Entry<String, FacetResult> entry : facetsMap.entrySet()) {
			
			switch(entry.getKey()) {
				case MyshopConstants.FF_PRICE:
					fres.setMax(getMaxPriceFacet(facets.values().get(MyshopConstants.FF_PRICE)));
					fres.setMin(getMinPriceFacet(facets.values().get(MyshopConstants.FF_PRICE)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_PRICE)) {
						fres.setActiveprice(true);
					}
					break;
				default:
					break;
			}
		}
		
		return fres;

	}
	
	@Override
	public VariantsFilterCT getFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters) {
		
		Iterable<String> listMonturas = getCategoryIdsById(categoryid);
		
		List<String> filtros = addFilters(filters);
		ProductProjectionPagedSearchResponse actual = cioneEcommerceConectionProvider.getApiRoot().productProjections()
				.search()
				.get()
				.withStaged(false)
				//.withFilter(f -> f.categories().id().in(listMonturas)) 
				.withFilterQuery("categories.id:\"" + String.join("\",\"", listMonturas) + "\"")
				.addFilterQuery("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
				.addFilterQuery(filtros)
				//.withFacet(f -> f.price().allTerms())
				.withFacet(MyshopConstants.FF_GAMA_COLOR_MONTURA)
				.withPriceCurrency("EUR")
				//fijamos un rango minimo y un maximo
				.addFacet(MyshopConstants.FF_PRICE + MyshopConstants.FF_ALL_RANGES)
				.addFacet(MyshopConstants.FF_MARCA)
				.addFacet(MyshopConstants.FF_TIPO_PRODUCTO)
				.addFacet(MyshopConstants.FF_FAMILIA_EKON)
				.addFacet(MyshopConstants.FF_GRADUACION)
				.addFacet(MyshopConstants.FF_DIMENSIONES_ANCHO_OJO)
				.addFacet(MyshopConstants.FF_MATERIAL)
				.addFacet(MyshopConstants.FF_TARGET)
				.addFacet(MyshopConstants.FF_COLECCION)
				.addFacet(MyshopConstants.FF_TAMANIOS)
				.addFacet(MyshopConstants.FF_DIMENSIONES_LARGO_VARILLA)
				.addFacet(MyshopConstants.FF_STATUS)
				.addFacet(MyshopConstants.FF_PRUEBA_VIRTUAL)
				//.withSort(m -> m.createdAt().sort().asc())
				.withSort("createdAt asc")
				.executeBlocking()
				.getBody();
		
		
		@NotNull @Valid FacetResults facets = actual.getFacets();
		
		VariantsFilterCT fres = new VariantsFilterCT();
		Map<String, FacetResult> facetsMap = facets.values();
		
		for (Entry<String, FacetResult> entry : facetsMap.entrySet()) {
			switch(entry.getKey()) {
			
				case MyshopConstants.FF_GAMA_COLOR_MONTURA:
					fres.setVariantsAttributesGamaColorMonturaEs(getVariantsAttributes(facets.values().get(MyshopConstants.FF_GAMA_COLOR_MONTURA)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_GAMA_COLOR_MONTURA)) {
						fres.getVariantsAttributesGamaColorMonturaEs().setSelected(filters.get(MyshopConstants.FF_GAMA_COLOR_MONTURA));
					}
					break;
				case MyshopConstants.FF_MARCA:
					fres.setVariantsAttributesMarca(getVariantsAttributes(facets.values().get(MyshopConstants.FF_MARCA)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_MARCA)) {
						fres.getVariantsAttributesMarca().setSelected(filters.get(MyshopConstants.FF_MARCA));
					}
					break;
				case MyshopConstants.FF_TIPO_PRODUCTO:
					fres.setVariantsAttributesTipoProductoEs(getVariantsAttributes(facets.values().get(MyshopConstants.FF_TIPO_PRODUCTO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_TIPO_PRODUCTO)) {
						fres.getVariantsAttributesTipoProductoEs().setSelected(filters.get(MyshopConstants.FF_TIPO_PRODUCTO));
					}
					break;
				case MyshopConstants.FF_FAMILIA_EKON:
					fres.setVariantsAttributesFamiliaEkon(getVariantsAttributes(facets.values().get(MyshopConstants.FF_FAMILIA_EKON)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_FAMILIA_EKON)) {
						fres.getVariantsAttributesFamiliaEkon().setSelected(filters.get(MyshopConstants.FF_FAMILIA_EKON));
					}
					break;
				case MyshopConstants.FF_GRADUACION:
					fres.setVariantsAttributesGraduacion(getVariantsAttributes(facets.values().get(MyshopConstants.FF_GRADUACION)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_GRADUACION)) {
						fres.getVariantsAttributesGraduacion().setSelected(filters.get(MyshopConstants.FF_GRADUACION));
					}
					break;
				case MyshopConstants.FF_DIMENSIONES_ANCHO_OJO:
					fres.setVariantsAttributesDimensionesAnchoOjo(getVariantsAttributes(facets.values().get(MyshopConstants.FF_DIMENSIONES_ANCHO_OJO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_DIMENSIONES_ANCHO_OJO)) {
						fres.getVariantsAttributesDimensionesAnchoOjo().setSelected(filters.get(MyshopConstants.FF_DIMENSIONES_ANCHO_OJO));
					}
					break;
				case MyshopConstants.FF_MATERIAL:
					fres.setVariantsAttributesMaterial(getVariantsAttributes(facets.values().get(MyshopConstants.FF_MATERIAL)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_MATERIAL)) {
						fres.getVariantsAttributesMaterial().setSelected(filters.get(MyshopConstants.FF_MATERIAL));
					}
					break;
				case MyshopConstants.FF_TARGET:
					fres.setVariantsAttributesTargetEs(getVariantsAttributes(facets.values().get(MyshopConstants.FF_TARGET)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_TARGET)) {
						fres.getVariantsAttributesTargetEs().setSelected(filters.get(MyshopConstants.FF_TARGET));
					}
					break;
				case MyshopConstants.FF_COLECCION:
					fres.setVariantsAttributesColeccionEs(getVariantsAttributes(facets.values().get(MyshopConstants.FF_COLECCION)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_COLECCION)) {
						fres.getVariantsAttributesColeccionEs().setSelected(filters.get(MyshopConstants.FF_COLECCION));
					}
					break;
				case MyshopConstants.FF_TAMANIOS:
					fres.setVariantsAttributesTamaniosEs(getVariantsAttributes(facets.values().get(MyshopConstants.FF_TAMANIOS)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_TAMANIOS)) {
						fres.getVariantsAttributesTamaniosEs().setSelected(filters.get(MyshopConstants.FF_TAMANIOS));
					}
					break;
				case MyshopConstants.FF_DIMENSIONES_LARGO_VARILLA :
					fres.setVariantsAttributesDimensionesLargoVarilla(getVariantsAttributes(facets.values().get(MyshopConstants.FF_DIMENSIONES_LARGO_VARILLA )));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_DIMENSIONES_LARGO_VARILLA)) {
						fres.getVariantsAttributesDimensionesLargoVarilla().setSelected(filters.get(MyshopConstants.FF_DIMENSIONES_LARGO_VARILLA));
					}
					break;
				case MyshopConstants.FF_PRICE:
					fres.setMax(getMaxPriceFacet(facets.values().get(MyshopConstants.FF_PRICE)));
					fres.setMin(getMinPriceFacet(facets.values().get(MyshopConstants.FF_PRICE)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_PRICE)) {
						fres.setActiveprice(true);
					}
					break;
				case MyshopConstants.FF_STATUS:
					fres.setVariantsAttributesStatusEkon(getVariantsAttributes(facets.values().get(MyshopConstants.FF_STATUS )));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_STATUS)) {
						fres.getVariantsAttributesStatusEkon().setSelected(filters.get(MyshopConstants.FF_STATUS));
					}
					break;
				case MyshopConstants.FF_PRUEBA_VIRTUAL:
					fres.setVariantsAttributesPruebaVirtual(getVariantsAttributes(facets.values().get(MyshopConstants.FF_PRUEBA_VIRTUAL )));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_PRUEBA_VIRTUAL)) {
						fres.getVariantsAttributesPruebaVirtual().setSelectedBoolean(filters.get(MyshopConstants.FF_PRUEBA_VIRTUAL));
					}
					break;
				default:
					break;
			}
		}
		
		return fres;

	}
	
	@Override
	public VariantsFilterCT getSolutionsFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters) {
		
		Iterable<String> listMonturas = getCategoryIdsById(categoryid);
		
		String proveedor = MyshopConstants.FF_PROVEEDOR;
		String subTipoProducto = MyshopConstants.FF_SUBTIPOPRODUCTO;
		String lineaNegocio = MyshopConstants.FF_LINEANEGOCIO;
		String tipoPack = MyshopConstants.FF_TIPOPACK;
		String tienePromociones = MyshopConstants.FF_TIENEPROMOCIONES;
		
		List<String> filtros = addFilters(filters);
		
		ProductProjectionPagedSearchResponse actual = cioneEcommerceConectionProvider.getApiRoot().productProjections()
				.search()
				.get()
				.withStaged(false)
				//.withFilter(f -> f.categories().id().in(listMonturas)) 
				.withFilterQuery("categories.id:\"" + String.join("\",\"", listMonturas) + "\"")
				.addFilterQuery("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
				.addFilterQuery(filtros)
				//.withFacet(f -> f.price().allTerms())
				.addFacet(MyshopConstants.FF_PRICE + MyshopConstants.FF_ALL_RANGES)
				.addFacet(proveedor)
				.addFacet(subTipoProducto)
				.addFacet(lineaNegocio)
				.addFacet(tipoPack)
				.addFacet(tienePromociones)
				//.withSort(m -> m.createdAt().sort().asc())
				.withSort("createdAt asc")
				.executeBlocking()
				.getBody();

		@NotNull @Valid FacetResults facets = actual.getFacets();
		
		VariantsFilterCT fres = new VariantsFilterCT();
		Map<String, FacetResult> facetsMap = facets.values();
		
		for (Entry<String, FacetResult> entry : facetsMap.entrySet()) {
			switch(entry.getKey()) {
			
				case MyshopConstants.FF_PROVEEDOR:
					fres.setVariantsAttributesProveedor(getVariantsAttributes(facets.values().get(MyshopConstants.FF_PROVEEDOR)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_PROVEEDOR)) {
						fres.getVariantsAttributesProveedor().setSelected(filters.get(MyshopConstants.FF_PROVEEDOR));
					}
					break;
				case MyshopConstants.FF_SUBTIPOPRODUCTO:
					fres.setVariantsAttributesSubTipoProducto(getVariantsAttributes(facets.values().get(MyshopConstants.FF_SUBTIPOPRODUCTO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_SUBTIPOPRODUCTO)) {
						fres.getVariantsAttributesSubTipoProducto().setSelected(filters.get(MyshopConstants.FF_SUBTIPOPRODUCTO));
					}
					break;
				case MyshopConstants.FF_LINEANEGOCIO:
					fres.setVariantsAttributesLineaNegocio(getVariantsAttributes(facets.values().get(MyshopConstants.FF_LINEANEGOCIO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_LINEANEGOCIO)) {
						fres.getVariantsAttributesLineaNegocio().setSelected(filters.get(MyshopConstants.FF_LINEANEGOCIO));
					}
					break;
				case MyshopConstants.FF_TIPOPACK:
					fres.setVariantsAttributesTipoPack(getVariantsAttributes(facets.values().get(MyshopConstants.FF_TIPOPACK)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_TIPOPACK)) {
						fres.getVariantsAttributesTipoPack().setSelected(filters.get(MyshopConstants.FF_TIPOPACK));
					}
					break;
				case MyshopConstants.FF_TIENEPROMOCIONES:
					fres.setVariantsAttributesTienePromociones(getVariantsAttributes(facets.values().get(MyshopConstants.FF_TIENEPROMOCIONES)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_TIENEPROMOCIONES)) {
						fres.getVariantsAttributesTienePromociones().setSelectedBoolean(filters.get(MyshopConstants.FF_TIENEPROMOCIONES));
					}
					break;
				default:
					break;
			}
		}
		
		return fres;
	}
	
	@Override
	public VariantsFilterCT getAudiologiaCompletaFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters) {
		
		Iterable<String> listMonturas = getCategoryIdsById(categoryid);
		
		String marca = MyshopConstants.FF_MARCA;
		String familiaAudio = MyshopConstants.FF_FAMILIAAUDIO;
		String segmento = MyshopConstants.FF_SEGMENTO;
		String subtipoProducto = MyshopConstants.FF_SUBTIPOPRODUCTO;
		String size = MyshopConstants.FF_SIZE;
		String prestaciones = MyshopConstants.FF_PRESTACIONES;
		String color = MyshopConstants.FF_COLOR;
		String modelo = MyshopConstants.FF_MODELO;
		String gama = MyshopConstants.FF_GAMA;
		String promo = MyshopConstants.FF_TIENEPROMOCIONES;
		String formato = MyshopConstants.FF_FORMATOAUDIO;
		
		List<String> filtros = addFilters(filters);
		
		ProductProjectionPagedSearchResponse actual = cioneEcommerceConectionProvider.getApiRoot().productProjections()
				.search()
				.get()
				.withStaged(false)
				//.withFilter(f -> f.categories().id().in(listMonturas)) 
				.withFilterQuery("categories.id:\"" + String.join("\",\"", listMonturas) + "\"")
				.addFilterQuery("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
				.addFilterQuery(filtros)
				//.withFacet(f -> f.price().allTerms())
				.withFacet(MyshopConstants.FF_PRICE + MyshopConstants.FF_ALL_RANGES)
				.addFacet(marca)
				.addFacet(familiaAudio)
				.addFacet(segmento)
				.addFacet(subtipoProducto)
				.addFacet(size)
				.addFacet(prestaciones)
				.addFacet(color)
				.addFacet(modelo)
				.addFacet(gama)
				.addFacet(promo)
				.addFacet(formato)
				//.withSort(m -> m.createdAt().sort().asc())
				.withSort("createdAt asc")
				.executeBlocking()
				.getBody();

		@NotNull @Valid FacetResults facets = actual.getFacets();
		
		VariantsFilterCT fres = new VariantsFilterCT();
		Map<String, FacetResult> facetsMap = facets.values();
		
		for (Entry<String, FacetResult> entry : facetsMap.entrySet()) {

			switch(entry.getKey()) {
			
				case MyshopConstants.FF_MARCA:
					fres.setVariantsAttributesMarca(getVariantsAttributes(facets.values().get(MyshopConstants.FF_MARCA)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_MARCA)) {
						fres.getVariantsAttributesMarca().setSelected(filters.get(MyshopConstants.FF_MARCA));
					}
					break;
				case MyshopConstants.FF_FAMILIAAUDIO:
					fres.setVariantsAttributesFamiliaAudio(getVariantsAttributes(facets.values().get(MyshopConstants.FF_FAMILIAAUDIO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_FAMILIAAUDIO)) {
						fres.getVariantsAttributesFamiliaAudio().setSelected(filters.get(MyshopConstants.FF_FAMILIAAUDIO));
					}
					break;
				case MyshopConstants.FF_SEGMENTO:
					fres.setVariantsAttributesSegmento(getVariantsAttributes(facets.values().get(MyshopConstants.FF_SEGMENTO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_SEGMENTO)) {
						fres.getVariantsAttributesSegmento().setSelected(filters.get(MyshopConstants.FF_SEGMENTO));
					}
					break;
				case MyshopConstants.FF_SUBTIPOPRODUCTO:
					fres.setVariantsAttributesSubTipoProducto(getVariantsAttributes(facets.values().get(MyshopConstants.FF_SUBTIPOPRODUCTO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_SUBTIPOPRODUCTO)) {
						fres.getVariantsAttributesSubTipoProducto().setSelected(filters.get(MyshopConstants.FF_SUBTIPOPRODUCTO));
					}
					break;
				case MyshopConstants.FF_SIZE:
					fres.setVariantsAttributesSize(getVariantsAttributes(facets.values().get(MyshopConstants.FF_SIZE)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_SIZE)) {
						fres.getVariantsAttributesSize().setSelected(filters.get(MyshopConstants.FF_SIZE));
					}
					break;
				case MyshopConstants.FF_PRESTACIONES:
					fres.setVariantsAttributesPrestaciones(getVariantsAttributes(facets.values().get(MyshopConstants.FF_PRESTACIONES)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_PRESTACIONES)) {
						fres.getVariantsAttributesPrestaciones().setSelected(filters.get(MyshopConstants.FF_PRESTACIONES));
					}
					break;
				case MyshopConstants.FF_COLOR:
					fres.setVariantsAttributesColorAudio(getVariantsAttributes(facets.values().get(MyshopConstants.FF_COLOR)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_COLOR)) {
						fres.getVariantsAttributesColorAudio().setSelected(filters.get(MyshopConstants.FF_COLOR));
					}
					break;
				case MyshopConstants.FF_MODELO:
					fres.setVariantsAttributesModelo(getVariantsAttributes(facets.values().get(MyshopConstants.FF_MODELO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_MODELO)) {
						fres.getVariantsAttributesModelo().setSelected(filters.get(MyshopConstants.FF_MODELO));
					}
					break;
				case MyshopConstants.FF_GAMA:
					fres.setVariantsAttributesGama(getVariantsAttributes(facets.values().get(MyshopConstants.FF_GAMA)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_GAMA)) {
						fres.getVariantsAttributesGama().setSelected(filters.get(MyshopConstants.FF_GAMA));
					}
					break;
				case MyshopConstants.FF_PRICE:
					fres.setMax(getMaxPriceFacet(facets.values().get(MyshopConstants.FF_PRICE)));
					fres.setMin(getMinPriceFacet(facets.values().get(MyshopConstants.FF_PRICE)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_PRICE)) {
						fres.setActiveprice(true);

					}
					break;
				case MyshopConstants.FF_TIENEPROMOCIONES:
					fres.setVariantsAttributesTienePromociones(getVariantsAttributes(facets.values().get(MyshopConstants.FF_TIENEPROMOCIONES)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_TIENEPROMOCIONES)) {
						fres.getVariantsAttributesTienePromociones().setSelected(filters.get(MyshopConstants.FF_TIENEPROMOCIONES));
					}
					break;
				case MyshopConstants.FF_FORMATOAUDIO:
					fres.setVariantsAttributesFormatosAudio(getVariantsAttributes(facets.values().get(MyshopConstants.FF_FORMATOAUDIO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_FORMATOAUDIO)) {
						fres.getVariantsAttributesFormatosAudio().setSelected(filters.get(MyshopConstants.FF_FORMATOAUDIO));
					}
					break;
				default:
					break;
			}
		}
		
		return fres;
		
	}
	
	@Override
	public VariantsFilterCT getAudiologiaFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters, String mandatoryfilter) {
		//forzamos el filtro obligatorio
		boolean encontrado = false;
		if (!filters.isEmpty()) {
			String[] filtroF = filters.get(MyshopConstants.FF_SUBTIPOPRODUCTO);
			if (filtroF != null) {
				for(String str: filtroF) {
					if (str.equals(mandatoryfilter)) {
						encontrado = true;
					}
				}
			}
		}
		if (!encontrado) {
			String[] str = {mandatoryfilter};
			filters.put(MyshopConstants.FF_SUBTIPOPRODUCTO, str);
		}
		
		List<String> listMonturas = getCategoryIdsById(categoryid);
		
		String marca = MyshopConstants.FF_MARCA;
		String familiaAudio = MyshopConstants.FF_FAMILIAAUDIO;
		String segmento = MyshopConstants.FF_SEGMENTO;
		String subtipoProducto = MyshopConstants.FF_SUBTIPOPRODUCTO;
		String size = MyshopConstants.FF_SIZE;
		String prestaciones = MyshopConstants.FF_PRESTACIONES;
		String color = MyshopConstants.FF_COLOR;
		String modelo = MyshopConstants.FF_MODELO;
		String gama = MyshopConstants.FF_GAMA;
		String promo = MyshopConstants.FF_TIENEPROMOCIONES;
		String formato = MyshopConstants.FF_FORMATOAUDIO;
		
		List<String> filtros = addFilters(filters);
		
		ProductProjectionPagedSearchResponse actual = cioneEcommerceConectionProvider.getApiRoot().productProjections()
				.search()
				.get()
				.withStaged(false)
				//.withFilter(f -> f.categories().id().in(listMonturas)) 
				.withFilterQuery("categories.id:\"" + String.join("\",\"", listMonturas) + "\"")
				.addFilterQuery("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
				.addFilterQuery(filtros)
				//.withFacet(f -> f.price().allTerms())
				.withFacet(MyshopConstants.FF_PRICE + MyshopConstants.FF_ALL_RANGES)
				.addFacet(marca)
				.addFacet(familiaAudio)
				.addFacet(segmento)
				.addFacet(subtipoProducto)
				.addFacet(size)
				.addFacet(prestaciones)
				.addFacet(color)
				.addFacet(modelo)
				.addFacet(gama)
				.addFacet(promo)
				.addFacet(formato)
				//.withSort(m -> m.createdAt().sort().asc())
				.withSort("createdAt asc")
				.executeBlocking()
				.getBody();

		@NotNull @Valid FacetResults facets = actual.getFacets();
		
		VariantsFilterCT fres = new VariantsFilterCT();
		Map<String, FacetResult> facetsMap = facets.values();
		
		for (Entry<String, FacetResult> entry : facetsMap.entrySet()) {

			switch(entry.getKey()) {
			
				case MyshopConstants.FF_MARCA:
					fres.setVariantsAttributesMarca(getVariantsAttributes(facets.values().get(MyshopConstants.FF_MARCA)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_MARCA)) {
						fres.getVariantsAttributesMarca().setSelected(filters.get(MyshopConstants.FF_MARCA));
					}
					break;
				case MyshopConstants.FF_FAMILIAAUDIO:
					fres.setVariantsAttributesFamiliaAudio(getVariantsAttributes(facets.values().get(MyshopConstants.FF_FAMILIAAUDIO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_FAMILIAAUDIO)) {
						fres.getVariantsAttributesFamiliaAudio().setSelected(filters.get(MyshopConstants.FF_FAMILIAAUDIO));
					}
					break;
				case MyshopConstants.FF_SEGMENTO:
					fres.setVariantsAttributesSegmento(getVariantsAttributes(facets.values().get(MyshopConstants.FF_SEGMENTO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_SEGMENTO)) {
						fres.getVariantsAttributesSegmento().setSelected(filters.get(MyshopConstants.FF_SEGMENTO));
					}
					break;
				case MyshopConstants.FF_SUBTIPOPRODUCTO:
					fres.setVariantsAttributesSubTipoProducto(getVariantsAttributes(facets.values().get(MyshopConstants.FF_SUBTIPOPRODUCTO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_SUBTIPOPRODUCTO)) {
						fres.getVariantsAttributesSubTipoProducto().setSelected(filters.get(MyshopConstants.FF_SUBTIPOPRODUCTO));
					}
					break;
				case MyshopConstants.FF_SIZE:
					fres.setVariantsAttributesSize(getVariantsAttributes(facets.values().get(MyshopConstants.FF_SIZE)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_SIZE)) {
						fres.getVariantsAttributesSize().setSelected(filters.get(MyshopConstants.FF_SIZE));
					}
					break;
				case MyshopConstants.FF_PRESTACIONES:
					fres.setVariantsAttributesPrestaciones(getVariantsAttributes(facets.values().get(MyshopConstants.FF_PRESTACIONES)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_PRESTACIONES)) {
						fres.getVariantsAttributesPrestaciones().setSelected(filters.get(MyshopConstants.FF_PRESTACIONES));
					}
					break;
				case MyshopConstants.FF_COLOR:
					fres.setVariantsAttributesColorAudio(getVariantsAttributes(facets.values().get(MyshopConstants.FF_COLOR)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_COLOR)) {
						fres.getVariantsAttributesColorAudio().setSelected(filters.get(MyshopConstants.FF_COLOR));
					}
					break;
				case MyshopConstants.FF_MODELO:
					fres.setVariantsAttributesModelo(getVariantsAttributes(facets.values().get(MyshopConstants.FF_MODELO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_MODELO)) {
						fres.getVariantsAttributesModelo().setSelected(filters.get(MyshopConstants.FF_MODELO));
					}
					break;
				case MyshopConstants.FF_GAMA:
					fres.setVariantsAttributesGama(getVariantsAttributes(facets.values().get(MyshopConstants.FF_GAMA)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_GAMA)) {
						fres.getVariantsAttributesGama().setSelected(filters.get(MyshopConstants.FF_GAMA));
					}
					break;
				case MyshopConstants.FF_PRICE:
					fres.setMax(getMaxPriceFacet(facets.values().get(MyshopConstants.FF_PRICE)));
					fres.setMin(getMinPriceFacet(facets.values().get(MyshopConstants.FF_PRICE)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_PRICE)) {
						fres.setActiveprice(true);

					}
					break;
				case MyshopConstants.FF_TIENEPROMOCIONES:
					fres.setVariantsAttributesTienePromociones(getVariantsAttributes(facets.values().get(MyshopConstants.FF_TIENEPROMOCIONES)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_TIENEPROMOCIONES)) {
						fres.getVariantsAttributesTienePromociones().setSelected(filters.get(MyshopConstants.FF_TIENEPROMOCIONES));
					}
					break;
				case MyshopConstants.FF_FORMATOAUDIO:
					fres.setVariantsAttributesFormatosAudio(getVariantsAttributes(facets.values().get(MyshopConstants.FF_FORMATOAUDIO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_FORMATOAUDIO)) {
						fres.getVariantsAttributesFormatosAudio().setSelected(filters.get(MyshopConstants.FF_FORMATOAUDIO));
					}
					break;
				default:
					break;
			}
		}
		
		return fres;
	}
	

	@Override
	public VariantsFilterCT getAccessoriesFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters) {
		
		List<String> listMonturas = getCategoryIdsById(categoryid);
		
		String tipoProducto = MyshopConstants.FF_TIPO_PRODUCTO;
		String subtipoProducto = MyshopConstants.FF_SUBTIPOPRODUCTO; //categoria
		String composicion = MyshopConstants.FF_COMPOSICION;
		String gamaColorMontura = MyshopConstants.FF_COLOR_MONTURA;
		
		List<String> filtros = addFilters(filters);
		
		ProductProjectionPagedSearchResponse actual = cioneEcommerceConectionProvider.getApiRoot().productProjections()
				.search()
				.get()
				.withStaged(false)
				//.withFilter(f -> f.categories().id().in(listMonturas)) 
				.withFilterQuery("categories.id:\"" + String.join("\",\"", listMonturas) + "\"")
				.addFilterQuery("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
				.addFilterQuery(filtros)
				//.withFacet(f -> f.price().allTerms())
				.withFacet(MyshopConstants.FF_PRICE + MyshopConstants.FF_ALL_RANGES)
				.addFacet(tipoProducto)
				.addFacet(subtipoProducto)
				.addFacet(composicion)
				.addFacet(gamaColorMontura)
				//.withSort(m -> m.createdAt().sort().asc())
				.withSort("createdAt asc")
				.executeBlocking()
				.getBody();

		@NotNull @Valid FacetResults facets = actual.getFacets();
		
		VariantsFilterCT fres = new VariantsFilterCT();
		Map<String, FacetResult> facetsMap = facets.values();
		
		for (Entry<String, FacetResult> entry : facetsMap.entrySet()) {
			
			switch(entry.getKey()) {
			
				case MyshopConstants.FF_COMPOSICION:
					fres.setVariantsAttributescComposicion(getVariantsAttributes(facets.values().get(MyshopConstants.FF_COMPOSICION)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_COMPOSICION)) {
						fres.getVariantsAttributescComposicion().setSelected(filters.get(MyshopConstants.FF_COMPOSICION));
					}
					break;
				case MyshopConstants.FF_TIPO_PRODUCTO:
					fres.setVariantsAttributesTipoProductoEs(getVariantsAttributes(facets.values().get(MyshopConstants.FF_TIPO_PRODUCTO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_TIPO_PRODUCTO)) {
						fres.getVariantsAttributesTipoProductoEs().setSelected(filters.get(MyshopConstants.FF_TIPO_PRODUCTO));
					}
					break;
				case MyshopConstants.FF_SUBTIPOPRODUCTO:
					fres.setVariantsAttributesSubTipoProducto(getVariantsAttributes(facets.values().get(MyshopConstants.FF_SUBTIPOPRODUCTO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_SUBTIPOPRODUCTO)) {
						fres.getVariantsAttributesSubTipoProducto().setSelected(filters.get(MyshopConstants.FF_SUBTIPOPRODUCTO));
					}
					break;
				case MyshopConstants.FF_COLOR_MONTURA:
					fres.setVariantsAttributesColorMonturaEs(getVariantsAttributes(facets.values().get(MyshopConstants.FF_COLOR_MONTURA)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_COLOR_MONTURA)) {
						fres.getVariantsAttributesColorMonturaEs().setSelected(filters.get(MyshopConstants.FF_COLOR_MONTURA));
					}
					break;
				default:
					break;
			}
		}
		
		return fres;
		
	}

	@Override
	public VariantsFilterCT getTaponesFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters) {
		
		Iterable<String> listMonturas = getCategoryIdsById(categoryid);
		
		String tipoProducto = MyshopConstants.FF_TIPO_PRODUCTO;
		String marca = MyshopConstants.FF_MARCA;
		String subtipoProducto = MyshopConstants.FF_SUBTIPOPRODUCTO;
		String promo = MyshopConstants.FF_TIENEPROMOCIONES;
		
		List<String> filtros = addFilters(filters);
		
		ProductProjectionPagedSearchResponse actual = cioneEcommerceConectionProvider.getApiRoot().productProjections()
				.search()
				.get()
				.withStaged(false)
				//.withFilter(f -> f.categories().id().in(listMonturas)) 
				.withFilterQuery("categories.id:\"" + String.join("\",\"", listMonturas) + "\"")
				.addFilterQuery("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
				.addFilterQuery(filtros)
				//.withFacet(f -> f.price().allTerms())
				.withFacet(MyshopConstants.FF_PRICE + MyshopConstants.FF_ALL_RANGES)
				.addFacet(tipoProducto)
				.addFacet(marca)
				.addFacet(subtipoProducto)
				.addFacet(promo)
				//.withSort(m -> m.createdAt().sort().asc())
				.withSort("createdAt asc")
				.executeBlocking()
				.getBody();

		@NotNull @Valid FacetResults facets = actual.getFacets();
		
		VariantsFilterCT fres = new VariantsFilterCT();
		Map<String, FacetResult> facetsMap = facets.values();
		
		for (Entry<String, FacetResult> entry : facetsMap.entrySet()) {
			
			switch(entry.getKey()) {
				case MyshopConstants.FF_TIPO_PRODUCTO:
					fres.setVariantsAttributesTipoProductoEs(getVariantsAttributes(facets.values().get(MyshopConstants.FF_TIPO_PRODUCTO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_TIPO_PRODUCTO)) {
						fres.getVariantsAttributesTipoProductoEs().setSelected(filters.get(MyshopConstants.FF_TIPO_PRODUCTO));
					}
					break;
				case MyshopConstants.FF_MARCA:
					fres.setVariantsAttributesMarca(getVariantsAttributes(facets.values().get(MyshopConstants.FF_MARCA)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_MARCA)) {
						fres.getVariantsAttributesMarca().setSelected(filters.get(MyshopConstants.FF_MARCA));
					}
					break;
				case MyshopConstants.FF_SUBTIPOPRODUCTO:
					fres.setVariantsAttributesSubTipoProducto(getVariantsAttributes(facets.values().get(MyshopConstants.FF_SUBTIPOPRODUCTO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_SUBTIPOPRODUCTO)) {
						fres.getVariantsAttributesSubTipoProducto().setSelected(filters.get(MyshopConstants.FF_SUBTIPOPRODUCTO));
					}
					break;
				case MyshopConstants.FF_TIENEPROMOCIONES:
					fres.setVariantsAttributesTienePromociones(getVariantsAttributes(facets.values().get(MyshopConstants.FF_TIENEPROMOCIONES)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_TIENEPROMOCIONES)) {
						fres.getVariantsAttributesTienePromociones().setSelected(filters.get(MyshopConstants.FF_TIENEPROMOCIONES));
					}
					break;
				case MyshopConstants.FF_PRICE:
					fres.setMax(getMaxPriceFacet(facets.values().get(MyshopConstants.FF_PRICE)));
					fres.setMin(getMinPriceFacet(facets.values().get(MyshopConstants.FF_PRICE)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_PRICE)) {
						fres.setActiveprice(true);

					}
					break;
				default:
					break;
			}
		}
		
		return fres;
		
	}

	@Override
	public VariantsFilterCT getBateriasFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters) {
		
		Iterable<String> listMonturas = getCategoryIdsById(categoryid);
		
		String tipoProducto = MyshopConstants.FF_TIPO_PRODUCTO;
		String marca = MyshopConstants.FF_MARCA;
		String subtipoProducto = MyshopConstants.FF_SUBTIPOPRODUCTO;
		String promo = MyshopConstants.FF_TIENEPROMOCIONES;
		
		List<String> filtros = addFilters(filters);
		
		ProductProjectionPagedSearchResponse actual = cioneEcommerceConectionProvider.getApiRoot().productProjections()
				.search()
				.get()
				.withStaged(false)
				//.withFilter(f -> f.categories().id().in(listMonturas)) 
				.withFilterQuery("categories.id:\"" + String.join("\",\"", listMonturas) + "\"")
				.addFilterQuery("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
				.addFilterQuery(filtros)
				//.withFacet(f -> f.price().allTerms())
				.withFacet(MyshopConstants.FF_PRICE + MyshopConstants.FF_ALL_RANGES)
				.addFacet(tipoProducto)
				.addFacet(marca)
				.addFacet(subtipoProducto)
				.addFacet(promo)
				//.withSort(m -> m.createdAt().sort().asc())
				.withSort("createdAt asc")
				.executeBlocking()
				.getBody();

		@NotNull @Valid FacetResults facets = actual.getFacets();
		
		VariantsFilterCT fres = new VariantsFilterCT();
		Map<String, FacetResult> facetsMap = facets.values();
		
		for (Entry<String, FacetResult> entry : facetsMap.entrySet()) {
			
			switch(entry.getKey()) {
				case MyshopConstants.FF_TIPO_PRODUCTO:
					fres.setVariantsAttributesTipoProductoEs(getVariantsAttributes(facets.values().get(MyshopConstants.FF_TIPO_PRODUCTO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_TIPO_PRODUCTO)) {
						fres.getVariantsAttributesTipoProductoEs().setSelected(filters.get(MyshopConstants.FF_TIPO_PRODUCTO));
					}
					break;
				case MyshopConstants.FF_MARCA:
					fres.setVariantsAttributesMarca(getVariantsAttributes(facets.values().get(MyshopConstants.FF_MARCA)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_MARCA)) {
						fres.getVariantsAttributesMarca().setSelected(filters.get(MyshopConstants.FF_MARCA));
					}
					break;
				case MyshopConstants.FF_SUBTIPOPRODUCTO:
					fres.setVariantsAttributesSubTipoProducto(getVariantsAttributes(facets.values().get(MyshopConstants.FF_SUBTIPOPRODUCTO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_SUBTIPOPRODUCTO)) {
						fres.getVariantsAttributesSubTipoProducto().setSelected(filters.get(MyshopConstants.FF_SUBTIPOPRODUCTO));
					}
					break;
				case MyshopConstants.FF_TIENEPROMOCIONES:
					fres.setVariantsAttributesTienePromociones(getVariantsAttributes(facets.values().get(MyshopConstants.FF_TIENEPROMOCIONES)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_TIENEPROMOCIONES)) {
						fres.getVariantsAttributesTienePromociones().setSelected(filters.get(MyshopConstants.FF_TIENEPROMOCIONES));
					}
					break;
				case MyshopConstants.FF_PRICE:
					fres.setMax(getMaxPriceFacet(facets.values().get(MyshopConstants.FF_PRICE)));
					fres.setMin(getMinPriceFacet(facets.values().get(MyshopConstants.FF_PRICE)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_PRICE)) {
						fres.setActiveprice(true);

					}
					break;
				default:
					break;
			}
		}
		
		return fres;
		
	}
	
	@Override
	public VariantsFilterCT getComplementosFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters) {
		
		Iterable<String> listMonturas = getCategoryIdsById(categoryid);
		
		String tipoProducto = MyshopConstants.FF_TIPO_PRODUCTO;
		String marca = MyshopConstants.FF_MARCA;
		String subtipoProducto = MyshopConstants.FF_SUBTIPOPRODUCTO;
		String promo = MyshopConstants.FF_TIENEPROMOCIONES;
		
		List<String> filtros = addFilters(filters);
		
		ProductProjectionPagedSearchResponse actual = cioneEcommerceConectionProvider.getApiRoot().productProjections()
				.search()
				.get()
				.withStaged(false)
				//.withFilter(f -> f.categories().id().in(listMonturas)) 
				.withFilterQuery("categories.id:\"" + String.join("\",\"", listMonturas) + "\"")
				.addFilterQuery("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
				.addFilterQuery(filtros)
				//.withFacet(f -> f.price().allTerms())
				.withFacet(MyshopConstants.FF_PRICE + MyshopConstants.FF_ALL_RANGES)
				.addFacet(tipoProducto)
				.addFacet(marca)
				.addFacet(subtipoProducto)
				.addFacet(promo)
				//.withSort(m -> m.createdAt().sort().asc())
				.withSort("createdAt asc")
				.executeBlocking()
				.getBody();

		@NotNull @Valid FacetResults facets = actual.getFacets();
		
		VariantsFilterCT fres = new VariantsFilterCT();
		Map<String, FacetResult> facetsMap = facets.values();
		
		for (Entry<String, FacetResult> entry : facetsMap.entrySet()) {
			
			switch(entry.getKey()) {
				case MyshopConstants.FF_TIPO_PRODUCTO:
					fres.setVariantsAttributesTipoProductoEs(getVariantsAttributes(facets.values().get(MyshopConstants.FF_TIPO_PRODUCTO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_TIPO_PRODUCTO)) {
						fres.getVariantsAttributesTipoProductoEs().setSelected(filters.get(MyshopConstants.FF_TIPO_PRODUCTO));
					}
					break;
				case MyshopConstants.FF_MARCA:
					fres.setVariantsAttributesMarca(getVariantsAttributes(facets.values().get(MyshopConstants.FF_MARCA)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_MARCA)) {
						fres.getVariantsAttributesMarca().setSelected(filters.get(MyshopConstants.FF_MARCA));
					}
					break;
				case MyshopConstants.FF_SUBTIPOPRODUCTO:
					fres.setVariantsAttributesSubTipoProducto(getVariantsAttributes(facets.values().get(MyshopConstants.FF_SUBTIPOPRODUCTO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_SUBTIPOPRODUCTO)) {
						fres.getVariantsAttributesSubTipoProducto().setSelected(filters.get(MyshopConstants.FF_SUBTIPOPRODUCTO));
					}
					break;
				case MyshopConstants.FF_TIENEPROMOCIONES:
					fres.setVariantsAttributesTienePromociones(getVariantsAttributes(facets.values().get(MyshopConstants.FF_TIENEPROMOCIONES)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_TIENEPROMOCIONES)) {
						fres.getVariantsAttributesTienePromociones().setSelected(filters.get(MyshopConstants.FF_TIENEPROMOCIONES));
					}
					break;
				case MyshopConstants.FF_PRICE:
					fres.setMax(getMaxPriceFacet(facets.values().get(MyshopConstants.FF_PRICE)));
					fres.setMin(getMinPriceFacet(facets.values().get(MyshopConstants.FF_PRICE)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_PRICE)) {
						fres.setActiveprice(true);

					}
					break;
				default:
					break;
			}
		}
		
		return fres;
		
	}
	
	@Override
	public VariantsFilterCT getAccesoriosInalambricosFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters) {
		
		Iterable<String> listMonturas = getCategoryIdsById(categoryid);
		
		String tipoProducto = MyshopConstants.FF_TIPO_PRODUCTO;
		String marca = MyshopConstants.FF_MARCA;
		String subtipoProducto = MyshopConstants.FF_SUBTIPOPRODUCTO;
		String promo = MyshopConstants.FF_TIENEPROMOCIONES;
		
		List<String> filtros = addFilters(filters);
		
		ProductProjectionPagedSearchResponse actual = cioneEcommerceConectionProvider.getApiRoot().productProjections()
				.search()
				.get()
				.withStaged(false)
				//.withFilter(f -> f.categories().id().in(listMonturas)) 
				.withFilterQuery("categories.id:\"" + String.join("\",\"", listMonturas) + "\"")
				.addFilterQuery("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
				.addFilterQuery(filtros)
				//.withFacet(f -> f.price().allTerms())
				.withFacet(MyshopConstants.FF_PRICE + MyshopConstants.FF_ALL_RANGES)
				.addFacet(tipoProducto)
				.addFacet(marca)
				.addFacet(subtipoProducto)
				.addFacet(promo)
				//.withSort(m -> m.createdAt().sort().asc())
				.withSort("createdAt asc")
				.executeBlocking()
				.getBody();

		@NotNull @Valid FacetResults facets = actual.getFacets();
		
		VariantsFilterCT fres = new VariantsFilterCT();
		Map<String, FacetResult> facetsMap = facets.values();
		
		for (Entry<String, FacetResult> entry : facetsMap.entrySet()) {
			
			switch(entry.getKey()) {
				case MyshopConstants.FF_TIPO_PRODUCTO:
					fres.setVariantsAttributesTipoProductoEs(getVariantsAttributes(facets.values().get(MyshopConstants.FF_TIPO_PRODUCTO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_TIPO_PRODUCTO)) {
						fres.getVariantsAttributesTipoProductoEs().setSelected(filters.get(MyshopConstants.FF_TIPO_PRODUCTO));
					}
					break;
				case MyshopConstants.FF_MARCA:
					fres.setVariantsAttributesMarca(getVariantsAttributes(facets.values().get(MyshopConstants.FF_MARCA)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_MARCA)) {
						fres.getVariantsAttributesMarca().setSelected(filters.get(MyshopConstants.FF_MARCA));
					}
					break;
				case MyshopConstants.FF_SUBTIPOPRODUCTO:
					fres.setVariantsAttributesSubTipoProducto(getVariantsAttributes(facets.values().get(MyshopConstants.FF_SUBTIPOPRODUCTO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_SUBTIPOPRODUCTO)) {
						fres.getVariantsAttributesSubTipoProducto().setSelected(filters.get(MyshopConstants.FF_SUBTIPOPRODUCTO));
					}
					break;
				case MyshopConstants.FF_TIENEPROMOCIONES:
					fres.setVariantsAttributesTienePromociones(getVariantsAttributes(facets.values().get(MyshopConstants.FF_TIENEPROMOCIONES)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_TIENEPROMOCIONES)) {
						fres.getVariantsAttributesTienePromociones().setSelected(filters.get(MyshopConstants.FF_TIENEPROMOCIONES));
					}
					break;
				case MyshopConstants.FF_PRICE:
					fres.setMax(getMaxPriceFacet(facets.values().get(MyshopConstants.FF_PRICE)));
					fres.setMin(getMinPriceFacet(facets.values().get(MyshopConstants.FF_PRICE)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_PRICE)) {
						fres.setActiveprice(true);

					}
					break;
				default:
					break;
			}
		}
		
		return fres;
		
	}

	@Override
	public VariantsFilterCT getContactologiaFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters) {
		
		Iterable<String> listMonturas = getCategoryIdsById(categoryid);
		
		String blisterocaja = MyshopConstants.FF_BLISTEROCAJA;
		String reemplazo = MyshopConstants.FF_REEMPLAZO;
		String isContacLab = MyshopConstants.FF_ISCONTACTLAB;
		String materialbase = MyshopConstants.FF_MATERIALBASE;
		String geometria = MyshopConstants.FF_GEOMETRIA;
		String lineaNegocio = MyshopConstants.FF_LINEANEGOCIO;
		String gama = MyshopConstants.FF_GAMA;
		String proveedor = MyshopConstants.FF_PROVEEDOR;
		String bproteccionSolar = MyshopConstants.FF_BPROTECCIONSOLAR;
		String disponibilidad = MyshopConstants.FF_DISPONIBILIDAD;
		
		List<String> filtros = addFilters(filters);
		
		ProductProjectionPagedSearchResponse actual = cioneEcommerceConectionProvider.getApiRoot().productProjections()
				.search()
				.get()
				.withStaged(false)
				//.withFilter(f -> f.categories().id().in(listMonturas)) 
				.withFilterQuery("categories.id:\"" + String.join("\",\"", listMonturas) + "\"")
				.addFilterQuery("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
				.addFilterQuery(filtros)
				//.withFacet(f -> f.price().allTerms())
				.withFacet(MyshopConstants.FF_PRICE + MyshopConstants.FF_ALL_RANGES)
				.addFacet(blisterocaja)
				.addFacet(reemplazo)
				.addFacet(isContacLab)
				.addFacet(materialbase)
				.addFacet(geometria)
				.addFacet(lineaNegocio)
				.addFacet(gama)
				.addFacet(proveedor)
				.addFacet(bproteccionSolar)
				.addFacet(disponibilidad)
				//.withSort(m -> m.createdAt().sort().asc())
				.withSort("createdAt asc")
				.executeBlocking()
				.getBody();

		@NotNull @Valid FacetResults facets = actual.getFacets();
		
		VariantsFilterCT fres = new VariantsFilterCT();
		Map<String, FacetResult> facetsMap = facets.values();
		
		for (Entry<String, FacetResult> entry : facetsMap.entrySet()) {
			
			switch(entry.getKey()) {
			
				case MyshopConstants.FF_BLISTEROCAJA:
					fres.setVariantsAttributesBlisterocaja(getVariantsAttributes(facets.values().get(MyshopConstants.FF_BLISTEROCAJA)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_BLISTEROCAJA)) {
						fres.getVariantsAttributesBlisterocaja().setSelected(filters.get(MyshopConstants.FF_BLISTEROCAJA));
					}
					break;
				case MyshopConstants.FF_REEMPLAZO:
					fres.setVariantsAttributesReemplazo(getVariantsAttributes(facets.values().get(MyshopConstants.FF_REEMPLAZO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_REEMPLAZO)) {
						fres.getVariantsAttributesReemplazo().setSelected(filters.get(MyshopConstants.FF_REEMPLAZO));
					}
					break;
				case MyshopConstants.FF_ISCONTACTLAB:
					fres.setVariantsAttributesisContactLab(getVariantsAttributes(facets.values().get(MyshopConstants.FF_ISCONTACTLAB)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_ISCONTACTLAB)) {
						fres.getVariantsAttributesisContactLab().setSelected(filters.get(MyshopConstants.FF_ISCONTACTLAB));
					}
					break;
				case MyshopConstants.FF_MATERIALBASE:
					fres.setVariantsAttributesMaterialbase(getVariantsAttributes(facets.values().get(MyshopConstants.FF_MATERIALBASE)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_MATERIALBASE)) {
						fres.getVariantsAttributesMaterialbase().setSelected(filters.get(MyshopConstants.FF_MATERIALBASE));
					}
					break;
				case MyshopConstants.FF_GEOMETRIA:
					fres.setVariantsAttributesGeometria(getVariantsAttributes(facets.values().get(MyshopConstants.FF_GEOMETRIA)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_GEOMETRIA)) {
						fres.getVariantsAttributesGeometria().setSelected(filters.get(MyshopConstants.FF_GEOMETRIA));
					}
					break;
				case MyshopConstants.FF_LINEANEGOCIO:
					fres.setVariantsAttributesLineaNegocio(getVariantsAttributes(facets.values().get(MyshopConstants.FF_LINEANEGOCIO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_LINEANEGOCIO)) {
						fres.getVariantsAttributesLineaNegocio().setSelected(filters.get(MyshopConstants.FF_LINEANEGOCIO));
					}
					break;
				case MyshopConstants.FF_GAMA:
					fres.setVariantsAttributesGama(getVariantsAttributes(facets.values().get(MyshopConstants.FF_GAMA)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_GAMA)) {
						fres.getVariantsAttributesGama().setSelected(filters.get(MyshopConstants.FF_GAMA));
					}
					break;
				case MyshopConstants.FF_PROVEEDOR:
					fres.setVariantsAttributesProveedor(getVariantsAttributes(facets.values().get(MyshopConstants.FF_PROVEEDOR)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_PROVEEDOR)) {
						fres.getVariantsAttributesProveedor().setSelected(filters.get(MyshopConstants.FF_PROVEEDOR));
					}
					break;
				case MyshopConstants.FF_BPROTECCIONSOLAR:
					fres.setVariantsAttributesBproteccionSolar(getVariantsAttributes(facets.values().get(MyshopConstants.FF_BPROTECCIONSOLAR)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_BPROTECCIONSOLAR)) {
						fres.getVariantsAttributesBproteccionSolar().setSelectedBoolean(filters.get(MyshopConstants.FF_BPROTECCIONSOLAR));
					}
					break;
				case MyshopConstants.FF_DISPONIBILIDAD:
					fres.setVariantsAttributesDisponibilidad(getVariantsAttributes(facets.values().get(MyshopConstants.FF_DISPONIBILIDAD)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_DISPONIBILIDAD)) {
						fres.getVariantsAttributesDisponibilidad().setSelected(filters.get(MyshopConstants.FF_DISPONIBILIDAD));
					}
					break;
				default:
					break;
			}
		}
		
		return fres;
		
	}
	
	@Override
	public VariantsFilterCT getMarketingFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters) {
		//Iterable<String> listMonturas = getCategoryIdsById(categoryid);
		
		CategoryTree tree = categoryUtils.getCategoryTreeById(categoryid);
		List<String> listCategories = new ArrayList<String>();
		if (tree != null) {
			List<Category> listado= tree.getAllAsFlatList();
			for (Category categoryChild: listado) {
				log.debug(categoryChild.getName().get(MyshopConstants.esLocale));
				listCategories.add(categoryChild.getId());
			}
		} else {
			listCategories.add(categoryid);
		}
			
		
		String tipo = MyshopConstants.FF_TIPO_PRODUCTO;
		String coleccion = MyshopConstants.FF_COLECCION;
		String marca = MyshopConstants.FF_MARCA;
		String subTipoProducto = MyshopConstants.FF_SUBTIPOPRODUCTO;
		String target = MyshopConstants.FF_TARGET;
		String color = MyshopConstants.FF_COLOR_MONTURA;
		String tamanio = MyshopConstants.FF_TAMANIOS;
		String modelo = MyshopConstants.FF_MODELO;
		
		List<String> filtros = addFilters(filters);
		
		ProductProjectionPagedSearchResponse actual = cioneEcommerceConectionProvider.getApiRoot().productProjections()
				.search()
				.get()
				.withStaged(false)
				//.withFilter(f -> f.categories().id().in(listMonturas)) 
				.withFilterQuery("categories.id:\"" + String.join("\",\"", listCategories) + "\"")
				.addFilterQuery("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
				.addFilterQuery(filtros)
				//.withFacet(f -> f.price().allTerms())
				.withFacet(MyshopConstants.FF_PRICE + MyshopConstants.FF_ALL_RANGES)
				.addFacet(tipo)
				.addFacet(coleccion)
				.addFacet(marca)
				.addFacet(subTipoProducto)
				.addFacet(target)
				.addFacet(color)
				.addFacet(tamanio)
				.addFacet(modelo)
				//.withSort(m -> m.createdAt().sort().asc())
				.withSort("createdAt asc")
				.executeBlocking()
				.getBody();

		@NotNull @Valid FacetResults facets = actual.getFacets();
		
		VariantsFilterCT fres = new VariantsFilterCT();
		Map<String, FacetResult> facetsMap = facets.values();
		
		for (Entry<String, FacetResult> entry : facetsMap.entrySet()) {
			
			switch(entry.getKey()) {
			
				case MyshopConstants.FF_TIPO_PRODUCTO:
					fres.setVariantsAttributesTipoProductoEs(getVariantsAttributes(facets.values().get(MyshopConstants.FF_TIPO_PRODUCTO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_TIPO_PRODUCTO)) {
						fres.getVariantsAttributesTipoProductoEs().setSelected(filters.get(MyshopConstants.FF_TIPO_PRODUCTO));
					}
					break;
				case MyshopConstants.FF_COLECCION:
					fres.setVariantsAttributesColeccionEs(getVariantsAttributes(facets.values().get(MyshopConstants.FF_COLECCION)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_COLECCION)) {
						fres.getVariantsAttributesColeccionEs().setSelected(filters.get(MyshopConstants.FF_COLECCION));
					}
					break;
				case MyshopConstants.FF_MARCA:
					fres.setVariantsAttributesMarca(getVariantsAttributes(facets.values().get(MyshopConstants.FF_MARCA)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_MARCA)) {
						fres.getVariantsAttributesMarca().setSelected(filters.get(MyshopConstants.FF_MARCA));
					}
					break;
				case MyshopConstants.FF_SUBTIPOPRODUCTO:
					fres.setVariantsAttributesSubTipoProducto(getVariantsAttributes(facets.values().get(MyshopConstants.FF_SUBTIPOPRODUCTO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_SUBTIPOPRODUCTO)) {
						fres.getVariantsAttributesSubTipoProducto().setSelected(filters.get(MyshopConstants.FF_SUBTIPOPRODUCTO));
					}
					break;
				case MyshopConstants.FF_TARGET:
					fres.setVariantsAttributesTargetEs(getVariantsAttributes(facets.values().get(MyshopConstants.FF_TARGET)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_TARGET)) {
						fres.getVariantsAttributesTargetEs().setSelected(filters.get(MyshopConstants.FF_TARGET));
					}
					break;
				case MyshopConstants.FF_COLOR_MONTURA:
					fres.setVariantsAttributesColorMonturaEs(getVariantsAttributes(facets.values().get(MyshopConstants.FF_COLOR_MONTURA)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_COLOR_MONTURA)) {
						fres.getVariantsAttributesColorMonturaEs().setSelected(filters.get(MyshopConstants.FF_COLOR_MONTURA));
					}
					break;
				case MyshopConstants.FF_TAMANIOS:
					fres.setVariantsAttributesTamaniosEs(getVariantsAttributes(facets.values().get(MyshopConstants.FF_TAMANIOS)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_TAMANIOS)) {
						fres.getVariantsAttributesTamaniosEs().setSelected(filters.get(MyshopConstants.FF_TAMANIOS));
					}
					break;
				case MyshopConstants.FF_MODELO:
					fres.setVariantsAttributesModelo(getVariantsAttributes(facets.values().get(MyshopConstants.FF_MODELO)));
					if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_MODELO)) {
						fres.getVariantsAttributesModelo().setSelected(filters.get(MyshopConstants.FF_MODELO));
					}
					break;
				default:
					break;
			}
		}
		
		return fres;
	}
	
	/**
	 * 
	 * Metodo para obtener el precio minimo de un FaceResult
	 * 
	 * @param facetResult faceta de la que obtendremos el
	 * precio minimo
	 * @return un entero con el precio minimo a mostrar
	 * 
	 */
	private int getMinPriceFacet(FacetResult facetResult) {
		
		if (facetResult instanceof RangeFacetResult) {
			RangeFacetResult rfc = (RangeFacetResult) facetResult;
			
			FacetRange rs = rfc.getRanges().get(rfc.getRanges().size() - 1);
			return rs.getMin().intValue(); 

//            // Obtener el rango con el valor mínimo
//            BigDecimal minimumPrice = rangeFacet.getRanges().stream()
//                .map(range -> range.getMin())
//                .min(BigDecimal::compareTo)
//                .orElse(null);
//
//            if (minimumPrice != null) {
//                return minimumPrice.divide(new BigDecimal(100)); // Convertir a unidades de moneda
//            }
		} else {
			return 0;
		}
		
//		RangeFacetResult rfc = (RangeFacetResult) facetResult;
//		RangeStats rs = rfc.getRanges().get(rfc.getRanges().size() - 1);
//		return sanitizePrice(rs.getMin());
	}

	/**
	 * 
	 * Metodo para obtener el precio maximo de un FaceResult
	 * 
	 * @param facetResult faceta de la que obtendremos el
	 * precio maximo
	 * @return un entero con el precio minimo a mostrar
	 * 
	 */
	private int getMaxPriceFacet(FacetResult facetResult) {
		if (facetResult instanceof RangeFacetResult) {
			RangeFacetResult rfc = (RangeFacetResult) facetResult;
			
			FacetRange rs = rfc.getRanges().get(rfc.getRanges().size() - 1);
			return rs.getMax().intValue(); 
		} else {
			return 0;
		}
//		RangeFacetResult rfc = (RangeFacetResult) facetResult;
//		RangeStats rs = rfc.getRanges().get(rfc.getRanges().size() - 1);
//		return sanitizePrice(rs.getMax());
	}
	
	/**
	 * 
	 * Metodo que nos devuelve el precio sin decimales
	 * a partir de un string
	 * 
	 * @param s representacion decimal en string
	 * @return un entero con la parte entera de la representacion
	 * 
	 */
	private int sanitizePrice(String s) {
	
		String splitDecimal = s.split("\\.")[0];
		int res = 0;
		
		if (splitDecimal != null && splitDecimal.length() >= 3) {
			res = Integer.parseInt(splitDecimal.substring(0, splitDecimal.length()-2));
		}else if (splitDecimal != null && splitDecimal.length() == 1) {
			res = Integer.parseInt(splitDecimal);
		}
		
		return res;
	}
	
	/**
	 * 
	 * Limpieza del filtro de precio que viene en
	 * la url
	 * 
	 * @param rango de precios
	 * @return string correcto para aplicar
	 */
	private String sanitizePriceFilter(String s) {
		
		String res = s.replace("range(", "");
		res = res.replace(")", "");
		res =  res.replaceAll("\\s", "");
		
		String[] spl = res.split("to");
		
		int min = 0;
		int max = 0;	
		
		if (spl.length == 2) {
			min = Integer.parseInt(spl[0]);
			max = Integer.parseInt(spl[1]);
			min = min*100;
			max = max*100;
		}
		
		return "range( " + min + " to " + max + " )";
	}
	
	/**
	 * 
	 * Dado los filtros obtenidos de la url debemos crear un listado
	 * del tipo compatible con la consulta a la API a traves del modulo
	 * de commercetools
	 * 
	 * @param filters filtros de la URL
	 * @return Listado de filtros
	 * 
	 
	public List<FilterExpression<ProductProjection>> addFilters(Map<String, String[]> filters) {
	
		List<FilterExpression<ProductProjection>> filtros = new ArrayList<>();
		
		if (!filters.isEmpty()) {
			for (Entry<String, String[]> entry : filters.entrySet()) {
				
				if (entry.getValue().length == 1) {
					
					final String filtro = createFilter(entry.getValue()[0],entry.getKey());
					final FilterExpression<ProductProjection> filterExpr = FilterExpression.of(entry.getKey() + ":" + filtro);
					filtros.add(filterExpr);
					
				}else if(entry.getValue().length > 1){
					
					String filtro = "";
					if (!entry.getKey().equals(MyshopConstants.FF_PRICE)) {
						filtro = createFilter(entry.getValue());
						final FilterExpression<ProductProjection> filterExpr = FilterExpression.of(entry.getKey() + ":" + filtro);
						filtros.add(filterExpr);
					}
					
				}
				
	        }
		}
		
		return filtros;
	}*/
	
	public List<String> addFilters(Map<String, String[]> filters) {
		List<String> filtros = new ArrayList<>();
		if (!filters.isEmpty()) {
			for (Entry<String, String[]> entry : filters.entrySet()) {
				String filtro = createFilter(entry.getValue());
				String filterExpr = entry.getKey() + ":" + filtro;
				filtros.add(filterExpr);
			}
		}
		return filtros;
	}
	
	/**
	 * 
	 * Dado los filtros obtenidos de la url debemos crear un listado
	 * del tipo compatible con la consulta a la API a traves del modulo
	 * de commercetools
	 * 
	 * @param filters 
	 * @return Listado de filtros
	 * 
	 
	public List<FilterExpression<ProductProjection>> addFiltersSingle(Map<String, String> filters) {
	
		List<FilterExpression<ProductProjection>> filtros = new ArrayList<>();
		
		if (!filters.isEmpty()) {
			for (Entry<String, String> entry : filters.entrySet()) {
				
				//final String filtro = createFilter(entry.getValue(),entry.getKey());
				final FilterExpression<ProductProjection> filterExpr = FilterExpression.of(entry.getKey() + ":" + entry.getValue());
				filtros.add(filterExpr);
				
	        }
		}
		
		return filtros;
	}*/
	
	/**
	 * 
	 * Dado el valor y la key de un filtro, creamos
	 * el filtro aplicable en la consulta
	 * 
	 * @param value valor del filtro
	 * @param key nombre del filtro usado para diferenciar en caso del precio
	 * @return filtro aplicable
	 */
	private String createFilter(String value, String key) {
		
		String filter = "";
		
		if (key != null && !key.isEmpty() && key.equals(MyshopConstants.FF_PRICE)) {
			filter =  sanitizePriceFilter(value);
		}else {
			filter = "\"" + value + "\"";
		}
		
		return filter;
	}

	/**
	 *  Dado los valores de un filtro creamos 
	 *  uno aplicable a la consula
	 * 
	 * @param value valores
	 * @return filtro aplicable
	 */
	private String createFilter(String[] value) {
		
		String res = "";
		
		for(String s : value) {
			String filtro;
			if (!s.contains("range")) {
				filtro = "\"" + s + "\"";
			} else {
				filtro = s;
			}
			res = res.isEmpty() ? res.concat(filtro) : res.concat(",").concat(filtro);
		}
		
		return res;
	}
	
	//PORBAR BIEN!! -sobretodo las ordenaciones
	/**
	 * 
	 * Segun el orden que deseemos apliar la consulta sera diferente para cada
	 * caso. 
	 * 
	 * @param listCategories lista de las categorias de sobre las que obtendremos los productos
	 * @param keycustomergroup customer group del usuario (CIONE, PORSO, VCO...)
	 * @param filtros filtros aplicados a la consulta
	 * @param strsorting metodo de ordenacion
	 * @param offset 
	 * @param limit
	 * @return nos devuelve la consulta completa
	 * 
	 */
	private ProductProjectionPagedSearchResponse getProductProjectionSearch(Iterable<String> listCategories, String keycustomergroup, 
				List<String> filtros, List<String> skus, String strsorting, int offset, int limit, boolean removeVariants) {
		//ProductProjectionSearch result;
		//ProductProjectionSearchBuilder builder;
		ProductProjectionPagedSearchResponse products = null;
		ByProjectKeyProductProjectionsSearchGet builder = null;
		
		
		switch(strsorting) {
			case MyshopConstants.ORDERNEW:
				builder = cioneEcommerceConectionProvider.getApiRoot().productProjections().search().get()
						.withFilter("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
						.addFilter(filtros)
						.withSort("variants.attributes.homeSN desc")
						.addSort("variants.attributes.orderField asc")
						.addSort("createdAt asc")
						.withOffset(offset)
						.withLimit(limit);
				if (!((Collection<String>) listCategories).isEmpty()) {
					//builder.plusQueryFilters(m -> m.categories().id().containsAny(listCategories));
					builder = builder.addFilter("categories.id:\"" + String.join("\",\"", listCategories) + "\"");
				}
				break;
			case MyshopConstants.ORDERNEWALTAEKON:
				builder = cioneEcommerceConectionProvider.getApiRoot().productProjections().search().get()
						.withFilter("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
						.addFilter(filtros)
						//.addFilter("categories.id:\"" + String.join("\",\"", listCategories) + "\"")
						.withSort("variants.attributes.homeSN desc")
						.addSort("variants.attributes.orderField asc")
						.addSort("variants.attributes.fechaAltaEkon desc")
						.withOffset(offset)
						.withLimit(limit);
				if (!((Collection<String>) listCategories).isEmpty()) {
					//builder.plusQueryFilters(m -> m.categories().id().containsAny(listCategories));
					
					builder = builder.addFilter("categories.id:\"" + String.join("\",\"", listCategories) + "\"");
				}
				break;
			case MyshopConstants.ORDERALFABETICO:
				builder = cioneEcommerceConectionProvider.getApiRoot().productProjections().search().get()
						.withFilter("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
						.addFilter(filtros)
						.withSort("variants.attributes.homeSN desc")
						.addSort("variants.attributes.orderField asc")
						.addSort("name.es asc")
						.withOffset(offset)
						.withLimit(limit);
				if (!((Collection<String>) listCategories).isEmpty()) {
					//builder.plusQueryFilters(m -> m.categories().id().containsAny(listCategories));
					builder = builder.addFilter("categories.id:\"" + String.join("\",\"", listCategories) + "\"");
				}
				break;
			case MyshopConstants.ORDERLOWTOHIGH:
				builder = cioneEcommerceConectionProvider.getApiRoot().productProjections().search().get()
						.withFilter("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
						.addFilter(filtros)
						.withSort("variants.attributes.homeSN desc")
						.addSort("variants.attributes.orderField asc")
						.addSort("price asc")
						.withOffset(offset)
						.withLimit(limit);
				if (!((Collection<String>) listCategories).isEmpty()) {
					//builder.plusQueryFilters(m -> m.categories().id().containsAny(listCategories));
					builder = builder.addFilter("categories.id:\"" + String.join("\",\"", listCategories) + "\"");
				}
				break;
			case MyshopConstants.ORDERHIGHTOLOW:
				builder = cioneEcommerceConectionProvider.getApiRoot().productProjections().search().get()
						.withFilter("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
						.addFilter(filtros)
						.withSort("variants.attributes.homeSN desc")
						.addSort("variants.attributes.orderField asc")
						.addSort("price desc")
						.withOffset(offset)
						.withLimit(limit);
				if (!((Collection<String>) listCategories).isEmpty()) {
					//builder.plusQueryFilters(m -> m.categories().id().containsAny(listCategories));
					builder = builder.addFilter("categories.id:\"" + String.join("\",\"", listCategories) + "\"");
				}
				break; 
			default:
				builder = cioneEcommerceConectionProvider.getApiRoot().productProjections().search().get()
						.withFilter("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
						.addFilter(filtros)
						.withSort("variants.attributes.homeSN desc")
						.addSort("variants.attributes.orderField asc")
						.addSort("createdAt desc")
						.withOffset(offset)
						.withLimit(limit);
				if (!((Collection<String>) listCategories).isEmpty()) {
					//builder.plusQueryFilters(m -> m.categories().id().containsAny(listCategories));
					builder = builder.addFilter("categories.id:\"" + String.join("\",\"", listCategories) + "\"");
				}
				break;
		}
		
		if ((skus!=null) && (!skus.isEmpty())) {
			String skusString = skus.stream().map(sku -> "\"" + sku + "\"").collect(Collectors.joining(", "));
			builder = builder.addFilter("variants.sku:" + skusString);
			//result.plusQueryFilters(m -> m.allVariants().sku().containsAny(skus));
		}
		
		products = builder.executeBlocking().getBody();
		

		
		if (removeVariants) {
			ProductProjectionPagedSearchResponse response = new ProductProjectionPagedSearchResponseImpl();
			List<ProductProjection> resultsRemove = removeVariants(builder, filtros);
			response.setResults(resultsRemove);
			response.setCount(products.getCount());
			response.setTotal(products.getTotal());
			
			
			return response;
		}
		else
			return products;
	}
	
	private List<ProductProjection> removeVariants(ByProjectKeyProductProjectionsSearchGet builder, List<String> filtros) {
		/* Control para eliminar variantes que no cumplan un determinado filtro */
		ApiHttpResponse<ProductProjectionPagedSearchResponse> response = builder.executeBlocking();
		ProductProjectionPagedSearchResponse results = response.getBody();		
		List<ProductProjection> filteredProducts = results.getResults().stream()
                .map(product -> filterVariants(product, filtros))
                //no es necesario filtrar productos sin variantes válidas ya que al aplicar el filtro 1 de sus variantes tiene que tener ya alguna variante valida
                //.filter(product -> !product.getVariants().isEmpty()) // Excluir productos sin variantes válidas
                .collect(Collectors.toList());
		
		
		/*results.getResults().forEach(product -> {
			System.out.println("Producto SIN FILTRO: " + product.getName().get("es"));
            product.getVariants().forEach(variant -> {
                System.out.println("Variante SIN FILTRO SKU: " + variant.getSku());
            });
		});*/
		
		filteredProducts.forEach(product -> {
            //System.out.println("Producto: " + product.getName().get("es"));
            product.getVariants().forEach(variant -> {
                log.debug("Variante SKU: " + variant.getSku());
            });
        });
		
		return filteredProducts;
	}
	
    private ProductProjection filterVariants(ProductProjection product, List<String> filtros) {
        // Filtrar variantes que cumplen con todos los filtros
        List<ProductVariant> filteredVariants = product.getVariants().stream()
                .filter(variant -> filtros.stream().allMatch(filtro -> variantMatchesFilter(variant, filtro)))
                .collect(Collectors.toList());
        
        //Filtramos la master
        boolean addMaster = filtros.stream().allMatch(filtro -> variantMatchesFilter(product.getMasterVariant(), filtro));
        if (addMaster) {
        	//log.debug("MASTER CUMPLE LOS FILTROS " + product.getMasterVariant().getSku());
        	return ProductProjection.builder()
                    .id(product.getId())
                    .key(product.getKey())
                    .version(product.getVersion())
                    .createdAt(product.getCreatedAt())
                    .lastModifiedAt(product.getLastModifiedAt())
                    .productType(product.getProductType())
                    .categories(product.getCategories())
                    .name(product.getName())
                    .slug(product.getSlug())
                    .variants(filteredVariants)
                    .masterVariant(product.getMasterVariant())
                    .build();
        } else {
        	ProductVariant newMaster = filteredVariants.get(0);
        	//log.debug("VARIANTE CUMPLE LOS FILTROS " + newMaster.getSku());
        	filteredVariants.remove(0);
        	return ProductProjection.builder()
                    .id(product.getId())
                    .key(product.getKey())
                    .version(product.getVersion())
                    .createdAt(product.getCreatedAt())
                    .lastModifiedAt(product.getLastModifiedAt())
                    .productType(product.getProductType())
                    .categories(product.getCategories())
                    .name(product.getName())
                    .slug(product.getSlug())
                    .variants(filteredVariants)
                    .masterVariant(newMaster)
                    .build();
        }
        
    }
    
    public boolean variantMatchesFilter(ProductVariant variant, String filtro) {
        // Lógica para validar si la variante cumple con el filtro
        // Aquí puedes implementar lógica para evaluar variantes.attributes según el filtro
        // Esto dependerá del formato de los filtros y los datos de las variantes

        // Ejemplo: Si el filtro es "variants.attributes.gamaColorMontura.es:\"ROJOS\""  o "variants.attributes.gamaColorMontura.es="ROJOS"
    	
    	String attributeName = "";
    	String value = "";
    	if (filtro.contains(":")) {
    		attributeName = filtro.split(":")[0].replace("variants.attributes.", "").replace(".es","").replace(".key","");
    		value = filtro.split(":")[1].replace("\"", "").replace("\\", "");
    	} else if (filtro.contains("=")) {
    		attributeName = filtro.split("=")[0].replace("variants.attributes.", "").replace(".es","").replace(".key","");
        	value = filtro.split("=")[1].replace("\"", "").replace("\\", "");
    	} else
        	return false;
        //log.debug("Comprobamos producto: " + variant.getSku());
        
        Attribute attributeVariant = MyShopUtils.findAttribute(attributeName, variant.getAttributes());
        if (attributeVariant == null)
        	return false;
        else {
	        if (attributeVariant.getValue() instanceof String) {
	        	String attributeVariantStr = (String) attributeVariant.getValue();
	        	return value.contains(attributeVariantStr);
	//        	return variant.getAttributes().stream()
	//                    .anyMatch(attr -> attr.getName().equals(attributeName) && attributeVariantStr.contains(value));
	        } else if(attributeVariant.getValue() instanceof LocalizedString) {
	        	LocalizedString attributeVariantLoc = (LocalizedString) attributeVariant.getValue();
	        	return value.contains(attributeVariantLoc.get(MyshopConstants.esLocale));
	//        	return variant.getAttributes().stream()
	//                    .anyMatch(attr -> attr.getName().equals(attributeName) && attributeVariantLoc.get(MyshopConstants.esLocale).contains(value));
	        } else if(attributeVariant.getValue() instanceof Boolean) {
	        	Boolean attributeVariantBol = (Boolean) attributeVariant.getValue();
	        	return attributeVariantBol.toString().equals(value);
	//        	return variant.getAttributes().stream()
	//                    .anyMatch(attr -> attr.getName().equals(attributeName) && attributeVariantBol.toString().equals(value));
	        } else
	        	return false;
        }
    }
    
	
	@Override
	public List<MasterProductFrontDTO> getProductBySkuList(List<String> skus) {
		List<MasterProductFrontDTO> result = new ArrayList<MasterProductFrontDTO>();
		String skusString = skus.stream().map(sku -> "\"" + sku + "\"").collect(Collectors.joining(", "));
		List<ProductProjection> productlist = 
				cioneEcommerceConectionProvider.getApiRoot().productProjections().search().get()
				.withFilter("variants.sku:" + skusString)
				.executeBlocking()
				.getBody()
				.getResults();
		
		for (ProductProjection product : productlist) {
			MasterProductFrontDTO master = new MasterProductFrontDTO();
			ProductFrontDTO pfront = new ProductFrontDTO();
			pfront.setImages(getImages(product.getMasterVariant()));
			pfront.setSku(product.getMasterVariant().getSku());
			pfront.setName(getName(product.getName()));
			
			if(!product.getMasterVariant().getPrices().isEmpty()) {
				Price price = getPriceBycustomerGroup(getGrupoPrecioCommerce(), product.getMasterVariant().getPrices());
				if (price != null) {
					//devolverlo en formatoDouble
					pfront.setPvo(MyShopUtils.formatMoneyDouble(price.getValue()));
				}
			}
			master.setMaster(pfront);
			result.add(master);
		}
		return result;
	}
	
	@Override
	public List<ProductProjection> getProductProjectionBySkuList(List<String> skus) {
		
		String skusString = skus.stream().map(sku -> "\"" + sku + "\"").collect(Collectors.joining(", "));
		List<ProductProjection> productlist = 
				cioneEcommerceConectionProvider.getApiRoot().productProjections().search().get()
				.withFilter("variants.sku:" + skusString)
				.executeBlocking()
				.getBody()
				.getResults();
		return productlist;
	}
	
	@Override
	public Price getPriceBycustomerGroup(String keycustomergroup, List<Price> prices) {
		
		CustomerGroup customerGroup = cioneEcommerceConectionProvider.getCustomerByKey(keycustomergroup);
		
		Price priceGroup = null;
		
		//prices.stream().anyMatch(price -> Optional.ofNullable(price.getCustomerGroup()).orElse(customerGroupReference -> customerGroupReference.getCustomerGroup().getId().equals(customerGroup.getId())));
		if (customerGroup != null) {
			for (Price price : prices) {
				if (price.getCustomerGroup() == null) //es any
					priceGroup = price;
				else {
					if (price.getCustomerGroup().getId().equals(customerGroup.getId())) {
						priceGroup = price;
						break;
					}
				}
					
			}
		}	
		
		return priceGroup;
	}
	
	
	
	@Override
	public List<MasterProductFrontDTO> getProductWithFilters(String categoryid, String keycustomergroup, Map<String, String[]> filters, List<String> skus, ProductSearchCT productsearchct, int page, int pagination, int count, String strsorting) {
		
		Iterable<String> listCategories = getCategoryIdsById(categoryid);
		
		
		List<String> filtros = addFilters(filters);
		final int offset = count;	
		final int limit = pagination;
		
//		ProductProjectionSearch actual = getProductProjectionSearch(listCategories, keycustomergroup, filtros, skus, strsorting, offset, limit);
//		
//		CompletionStage<PagedSearchResult<ProductProjection>> productsearch = cioneEcommerceConectionProvider.getClient().execute(actual);
//		PagedSearchResult<ProductProjection> searchresult = productsearch.toCompletableFuture().join();
		boolean removeVariants = false;
		if (MgnlContext.getWebContext().getRequest().getParameter("skuPackMaster") != null)
			removeVariants = true;
		ProductProjectionPagedSearchResponse response = getProductProjectionSearch(listCategories, keycustomergroup, filtros, skus, strsorting, offset, limit, removeVariants);
		
		List<String> aliasEKON = new ArrayList<>();
		List<String> codigosCentral = new ArrayList<>();
		productsearchct.setTotal(response.getTotal()); 
		productsearchct.setCount(response.getCount());
		
		List<MasterProductFrontDTO> res = new ArrayList<>();
		
		try {
			Category categoryRepuestos = getCategoryIdByName(MyshopConstants.esLocale,"REPUESTOS");
			
			for(ProductProjection product: response.getResults()) {
				
				ProductFrontDTO p = new ProductFrontDTO();
				MasterProductFrontDTO productfront = new MasterProductFrontDTO();
				List<String> colors = new ArrayList<>();
				List<String> calibers = new ArrayList<>();
				List<String> calibrations = new ArrayList<>();
				List<String> tamanios = new ArrayList<>();
				
				p.setImages(getImages(product.getMasterVariant()));
				p.setSku(product.getMasterVariant().getSku());
				p.setName(getName(product.getName()));
				
				if(!product.getMasterVariant().getPrices().isEmpty()) {
					
					Price price = getPriceBycustomerGroup(keycustomergroup, product.getMasterVariant().getPrices());
					if (price != null)
						p.setPvo(MyShopUtils.getCentAmountDouble(price.getValue()));
				}
			
				List<CategoryReference> refCategories = product.getCategories();
				boolean flagRepuesto = false;
				
				for(CategoryReference ref: refCategories) {
					if (ref.getId().equals(categoryRepuestos.getId()))
						flagRepuesto = true;
				}
				p.setRepuesto(flagRepuesto);
				for(Attribute attr: product.getMasterVariant().getAttributes()) {
					if(attr.getName().equals(MyshopConstants.REPUESTOALTERNATIVO)) {
						p.setHasSubstitute(true);
					}
					if(attr.getName().equals(MyshopConstants.TIENEPROMOCIONES)) {
						p.setPromo((Boolean) attr.getValue());
					}
					
					if(attr.getName().equals(MyshopConstants.TIENERECARGO)) {
						p.setRecargo((Boolean) attr.getValue());
					}
					
					if(attr.getName().equals(MyshopConstants.NOMBREARTICULO)) {
						p.setNombreArticulo((String) attr.getValue());
					}
					
					if(attr.getName().equals(MyshopConstants.COLECCION)) {
						p.setColeccion(((LocalizedString) attr.getValue()).get(MyshopConstants.esLocale));
					}
					
					if(attr.getName().equals(MyshopConstants.ALIASEKON)) {
						p.setAliasEKON((String) attr.getValue());
					}
					
					if(attr.getName().equals(MyshopConstants.TIPOPRODUCTO)) {
						p.setTipoproducto(((LocalizedString) attr.getValue()).get(MyshopConstants.esLocale));
						if (p.getFamiliaproducto() == null) //este else sobra si todos los productos tiene contribuida la familia
							p.setFamiliaproducto(MyShopUtils.getFamiliaProducto(p.getTipoproducto()));
					}
					
					if (attr.getName().equals(MyshopConstants.PLANTILLA))
						p.setFamiliaproducto(((AttributePlainEnumValue) (attr.getValue())).getKey());
					
					if(attr.getName().equals(MyshopConstants.CODIGOCENTRAL)) {
						p.setCodigocentral((String) attr.getValue());
					} 
					
					if(attr.getName().equals(MyshopConstants.COLORICONO)) {
						p.setColor((String) attr.getValue());
						//setList(colors,attr.getValueAsString());
					}	
					
					if(attr.getName().equals(MyshopConstants.CODIGOCOLOR)) {
						p.setCodigocolor((String) attr.getValue());
					}
					
					if(attr.getName().equals(MyshopConstants.COLORMONTURA)) {
						p.setColorMontura(((LocalizedString) attr.getValue()).get(MyshopConstants.esLocale));
					}	
					
					if(attr.getName().equals(MyshopConstants.DIMENSIONES_ANCHO_OJO)) {
						p.setCalibre(((Long) attr.getValue()).toString());
						setList(calibers,p.getCalibre());
					}				
					
					if(attr.getName().equals(MyshopConstants.GRADUACION)) {
						p.setCalibration((String) attr.getValue());
						setList(calibrations,(String) attr.getValue());
					}
					
					if(attr.getName().equals(MyshopConstants.PVPRECOMENDADO)) {
						CentPrecisionMoney pvp = (CentPrecisionMoney) attr.getValue();
						p.setPvp(getCentAmountDouble(pvp.getCentAmount(), pvp.getFractionDigits()));
					}
					
					if(attr.getName().equals(MyshopConstants.PLAZOENTREGAPROVEEDOR)) {
						p.setDelivery(((Long)attr.getValue()).intValue());
					}
					
					if(attr.getName().equals(MyshopConstants.TAMANIO)) {
						p.setTamanio(((LocalizedString) attr.getValue()).get(MyshopConstants.esLocale));
						setList(tamanios, p.getTamanio());
					}
				
					if(attr.getName().equals(MyshopConstants.COLOR)) {
						List<String> list = (List<String>) attr.getValue();
						ArrayList<String> coloresAudio = new ArrayList<String>();
						for (String color: list) {
							coloresAudio.add(color);
						}
						p.setColorsAudio(coloresAudio);
					}
					if (attr.getName().equals(MyshopConstants.PVOSINPACK)) {
						CentPrecisionMoney pvp = (CentPrecisionMoney) attr.getValue();
						p.setPvoSinPack(getCentAmountDouble(pvp.getCentAmount(), pvp.getFractionDigits()));
					}
					if(attr.getName().equals(MyshopConstants.STATUSEKON)) {
						p.setStatusEkon((String) attr.getValue());
					}
					
					if(attr.getName().equals(MyshopConstants.OFERTAFLASH)) {
						p.setOfertaFlash((Boolean) attr.getValue());
					}
					
					if(attr.getName().equals(MyshopConstants.GESTIONSTOCK)) {
						p.setGestionStock((Boolean) attr.getValue());
					}
						
				}
					
				boolean aMedida = false;
				if (MyShopUtils.hasAttribute("aMedida", product.getMasterVariant().getAttributes())) {
					aMedida = (boolean) MyShopUtils.findAttribute("aMedida", product.getMasterVariant().getAttributes()).getValue();
				}
				if (aMedida) {
					p.setFamiliaproducto("audiolab");
				}
				boolean isVariantPack=false;
					
				if ((p.getFamiliaproducto() != null)){
					String familia = p.getFamiliaproducto();
					switch (familia) {
						case "packs": {
							//es pack por bbdd
							List<PackInfoDTO> list = packDao.getPackInfoFromSku(p.getSku());
							double descuento = 0;
							for (PackInfoDTO contenido: list) {
								descuento += Double.valueOf(contenido.getPvoPackUD().replace(',', '.')) * contenido.getUnidadesPack();
								log.debug("DESCUENTO " + descuento);
								p.setPvo(descuento);
							}
							break;
						}
						case "packs-audio": {
							List<String> listaFiltros = (List<String>) MyShopUtils.findAttribute("listadoFiltros", product.getMasterVariant().getAttributes()).getValue();
							//List<String> listaFiltros = new ArrayList<String>(list);
							p.setFiltros(listaFiltros);
							p.setPackNavegacion(true); //deprecated
							break;
						}
						case "pack-generico": {
							//List<String> listaFiltros = (List<String>) MyShopUtils.findAttribute("agrupadores", product.getMasterVariant().getAttributes()).getValue();
							//List<String> listaFiltros = new ArrayList<String>(list);
							//p.setFiltros(listaFiltros);
							p.setPackNavegacion(false); //deprecated
							p.setPackNavegacionDetalle(true);
							isVariantPack=true;
							
							//info pack generico
							//String tipoPrecioPack = (String) MyShopUtils.findAttribute("tipoPrecioPack", product.getMasterVariant().getAttributes()).getValue();
							InfoPackGenericoDTO infoPack = new InfoPackGenericoDTO(product.getMasterVariant().getAttributes());
							productfront.setInfoPack(infoPack);
							
							setPreciosListadoFiltros(p, keycustomergroup, infoPack, product);
							p.setDescripcionPack(getName(product.getDescription()));
							
							break;
						}
					}
				}
					
				setList(aliasEKON,p.getAliasEKON());
				setList(codigosCentral,p.getCodigocentral());
				
				//p.setStock(getStock(p.getAliasEKON()));
				productfront.setMaster(p);
				
				//INFO PARA LISTADOS DE UN PRODUCTO PACK GENERICO
				if (MgnlContext.getParameter("skuPackMaster") != null) {					
					if (skus.isEmpty() || (skus.contains(productfront.getMaster().getSku())))
						productfront.getMaster().setIncluidoEnPack(true);
					else {
						
						//comprobamos que la master cumple los filtros dados
						
						productfront.getMaster().setExcludeMasterProductFront(true);
						
						
					}
					try {
						//añadimos la info necesaria si es un producto que estamos seleccionando a traves de un pack generico
						productfront.getMaster().setTipoPrecioPack(getTipoPrecioPackListadoFilter());
						
						int step = -1;
						if ((MgnlContext.getWebContext().getRequest().getParameter("step") != null) 
								&& !MgnlContext.getWebContext().getRequest().getParameter("step").isEmpty()) {
							String step_str = MgnlContext.getWebContext().getRequest().getParameter("step");
							try {
								step = Integer.valueOf(step_str).intValue();
							} catch (NumberFormatException e) {
								log.error(e.getMessage(), e);
							}
						}
						
						productfront.getMaster().setPvoPack(getTipoPvoPackListadoFilter(productfront.getMaster().getPvo(), productfront.getMaster().getTipoPrecioPack(), productfront.getMaster().getTipoproducto(), step));
					} catch (Exception e) {
						log.error(e.getMessage(), e);
						productfront.getMaster().setExcludeMasterProductFront(true);
					}

				}
				
				
				String finalColor = getCodigoAndColor(p);
				if (finalColor != null && !finalColor.isEmpty()) {
					p.setColor(finalColor);
					setList(colors,finalColor);
					List<ColorDTO> colorsDto = getInfoListColors(product, skus);
					productfront.setColorsdto(colorsDto);
				}
				
				//añadimos las informacion de la variantes
				List<ProductFrontDTO> variants = getProductFiltersVariants(product,aliasEKON,codigosCentral,colors,calibers,calibrations,tamanios,isVariantPack, skus);
				
				productfront.setVariants(variants);
				productfront.setCalibers(calibers);
				productfront.setCalibrations(calibrations);
				productfront.setColors(colors);
				productfront.setTamanios(tamanios);

				if (productfront.getMaster().getFamiliaproducto()!= null) 
					res.add(productfront);
				else
					log.error("producto sin Familia " + productfront.getMaster().getSku());
				/*if (addListProductFront)
					res.add(productfront);
				else {
					
				}*/
				
			}
				
			addPromotionalProducts(aliasEKON,codigosCentral,keycustomergroup,res);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return res;
		
	}
	
	public String getTipoPrecioPackListadoFilter() {
		String tipoPrecioPack = null;
		HashMap<String, List<AgrupadorDTO>> infoPackMapSession = getInfoPackMapSession();
		if ((infoPackMapSession != null) && (infoPackMapSession.get(MgnlContext.getParameter("skuPackMaster")) != null)) {
			List<AgrupadorDTO> productosPackGenerico = infoPackMapSession.get(MgnlContext.getParameter("skuPackMaster"));
			if ((productosPackGenerico != null) && !productosPackGenerico.isEmpty()) {
				AgrupadorDTO productoPackMaster = productosPackGenerico.get(0); //es a nivel sameforAll, nos quedamos con la master
				tipoPrecioPack = productoPackMaster.getTipoPrecioPack();
			}
		}
		return tipoPrecioPack;	
	}
	
	public String getTipoPvoPackListadoFilter(Double pvo, String tipoPrecioPack, String tipoProducto, int index) throws Exception {
		String pvoPack = null;
		
		HashMap<String, List<AgrupadorDTO>> infoPackMapSession = getInfoPackMapSession();
		if ((infoPackMapSession != null) && (infoPackMapSession.get(MgnlContext.getParameter("skuPackMaster")) != null)) {
			List<AgrupadorDTO> productosPackGenerico = infoPackMapSession.get(MgnlContext.getParameter("skuPackMaster"));
			
			/*if (index < 0) { //no viene apuntando la posicion, lo buscamos por tipo producto
				for (int i=0; i<productosPackGenerico.size(); i++) {
					AgrupadorDTO productoPack = productosPackGenerico.get(i);
					if ( !productoPack.isConfigurado() && (productoPack.getTipoProductoPack().equals(tipoProducto)) ) {
						index = i;
					}
				}
			}*/
			
			if (index < 0) {
				throw new Exception("No se ha podido calcular el precio del producto dentro del pack");
			}
			
			switch (tipoPrecioPack) {
			/*case "CERRADO": { //deprecated
				//for (AgrupadorDTO productoPack : productosPackGenerico) {
				AgrupadorDTO productoPack = productosPackGenerico.get(index);
					//el primer elemento sin configurar y que sea del mismo tipoProducto será el seleccionado
					if ( !productoPack.isConfigurado() && (productoPack.getTipoProductoPack().equals(tipoProducto)) ) {
						//es el elemento del pack a configurar, cogemos su pvoPack
						pvoPack = productoPack.getPvoPack();
						break;
					} else {
						throw new Exception("No se ha podido calcular el precio del producto dentro del pack");
						
					}
				//}
					//break;
			}*/
			case "GLOBAL": {
				//aplicamos el porcentaje o valor
				//nos quedamos con la master ya que es un atributo sameforall (el valor esta en descuentoGlobal)
				AgrupadorDTO productoPackMaster = productosPackGenerico.get(index);
				if (productoPackMaster.isPorcentaje()) {
					double descuento = (pvo * productoPackMaster.getValorDescuentoGlobal()) /100;
					double pvoRound = pvo - descuento;
					pvoPack = String.valueOf(MyShopUtils.round(pvoRound, 2));
				} else {
					pvoPack = String.valueOf(pvo - productoPackMaster.getValorDescuentoGlobal());
				}
				break;
			}
			case "INDIVIDUAL-CERRADO": { //deprecated
				//for (AgrupadorDTO productoPack : productosPackGenerico) {
				AgrupadorDTO productoPack = productosPackGenerico.get(index);
					//el primer elemento sin configurar y que sea del mismo tipoProducto será el seleccionado
					//if ( !productoPack.isConfigurado() && (productoPack.getTipoProductoPack().equals(tipoProducto)) ) {
					if ( !productoPack.isConfigurado() ) {
						pvoPack = productoPack.getPvoPack();
						break;
					} else {
						throw new Exception("No se ha podido calcular el precio del producto dentro del pack");
					}
				//}
				//break;
			}
			case "INDIVIDUAL": {
				//for (AgrupadorDTO productoPack : productosPackGenerico) {
				AgrupadorDTO productoPack = productosPackGenerico.get(index);
					//el primer elemento sin configurar y que sea del mismo tipoProducto será el seleccionado
					//if ( !productoPack.isConfigurado() && (productoPack.getTipoProductoPack().equals(tipoProducto)) ) {
					if ( !productoPack.isConfigurado() ) {
						
						//miramos si tenemos que aplicar PVO-CERRADO o DESCUENTO
						
						String tipoPrecioVariante = productoPack.getTipoPrecioVariante();
						if (productoPack.getTipoPrecioVariante()!= null) {
							switch (tipoPrecioVariante) {
							case "PVO-CERRADO":
								pvoPack = productoPack.getPvoPack();
								break;
							case "DESCUENTO": 
								if (productoPack.isPorcentajeIndividual()) {
									double descuento = (pvo * productoPack.getValorDescuentoVariante()) /100;
									double pvoRound = pvo - descuento;
									pvoPack = String.valueOf(MyShopUtils.round(pvoRound, 2));
								} else {
									double pvoRound = pvo - productoPack.getValorDescuentoVariante();
									pvoPack = String.valueOf(MyShopUtils.round(pvoRound, 2));
								}
								break;
							case "CIONELAB":
								break; //se delega el calculo a cioneLab
							default:
								log.error("tipoPrecioPack no esperado : " + tipoPrecioPack);
								throw new Exception("tipoPrecioPack no esperado : " + tipoPrecioPack);
								
							}
						} else {
							log.error("tipoPrecioPack no informado para el producto : " + MgnlContext.getParameter("skuPackMaster"));
							throw new Exception("tipoPrecioPack no informado para el producto : " + MgnlContext.getParameter("skuPackMaster"));
						}
						break; //si lo hemos encontrado, no seguimos con la iteracion del for
					} else {
						throw new Exception("No se ha podido calcular el precio del producto " + productoPack.toString() + " dentro del pack ");
					}
				//}
				//break;
			}
			default:
				throw new Exception("Tipo precio pack no valido");
			}
		}
		
		return pvoPack;
	}
	
	private void setPreciosListadoFiltros(ProductFrontDTO producto, String keycustomergroup, InfoPackGenericoDTO infoPack, ProductProjection product) {
		if (infoPack.getTipoPrecioPack() == null)
			log.error("El producto " + product.getMasterVariant().getSku() + " no tiene unformado el tipoPrecioPack");
		else {
			switch (infoPack.getTipoPrecioPack()) {
			/*case "CERRADO": {  //deprecated
				
				//setear pvoPackCerrado
				CentPrecisionMoney money = null;
				if (MyShopUtils.hasAttribute("pvoPackCerrado", product.getMasterVariant().getAttributes())) {
					money = (CentPrecisionMoney) MyShopUtils.findAttribute("pvoPackCerrado", product.getMasterVariant().getAttributes()).getValue();
	    		}
				if (money != null) {
					producto.setPvo(getCentAmountDouble(money.getCentAmount(), money.getFractionDigits()));
					producto.setPvoConDescuentoPack(getCentAmountDouble(money.getCentAmount(), money.getFractionDigits()));//el precio lo lleva la master, el resto van a 0
				}
				break;
			}*/
			case "GLOBAL": {
				//en los listados mostramos los mensajes pack con x% de descuento en todos los productos del pack o pack con x€ de descuento en todos los productos del pack
				//el pvo ya se setean en new InfoPackGenericoDTO(product.getMasterVariant().getAttributes());
				producto.setPvoConDescuentoPack(infoPack.getValorDescuentoGlobal().doubleValue());
				
				break;
			}
			case "INDIVIDUAL-CERRADO": { //deprecated
				Price price = getPriceBycustomerGroup(keycustomergroup, product.getMasterVariant().getPrices());
				double precioTotal = 0.0;
				if (price != null)
					precioTotal = MyShopUtils.getCentAmountDouble(price.getValue());
				//recorremos las variantes y lo sumamos
				List<ProductVariant> variantesPack = product.getVariants();
				for (ProductVariant variantPack : variantesPack) {
					price = getPriceBycustomerGroup(keycustomergroup, variantPack.getPrices());
					precioTotal += MyShopUtils.getCentAmountDouble(price.getValue());
				}
				producto.setPvo(precioTotal);
				producto.setPvoConDescuentoPack(precioTotal);
				break;
			}
			case "INDIVIDUAL": {
				String tipoPrecioVariante = infoPack.getTipoPrecioVariante();
				if (tipoPrecioVariante != null) {
					switch (tipoPrecioVariante) {
					case "PVO-CERRADO":
						//no ponemos precio, va en el nombre del producto pack
						break;
					case "DESCUENTO":
						//no ponemos precio, va en el nombre del producto pack
						break;
					case "CIONELAB":
						break;
					default:
						log.error("tipoPrecioVariante mal informada para el producto " + producto.getSku(), new IllegalArgumentException("tipoPrecioVariante mal informada para el producto " + producto.getSku()));
					}
					//PONEMOS LA DESCRIPCION DEL PACK
				} else {
					log.error("tipoPrecioVariante no informada para el producto " + producto.getSku(), new IllegalArgumentException("tipoPrecioVariante mal informada para el producto " + producto.getSku()));
				}
				break;
			}		
			default:
				break;
			
			}
		}
	}
	
	
	private List<ColorDTO> getInfoListColors(ProductProjection product, List<String> skus) {
		List<ColorDTO> colorsDto = new ArrayList<ColorDTO>();
		ProductVariant master = product.getMasterVariant();
		ColorDTO masterColorDTO = new ColorDTO();
		
		masterColorDTO.setSelected(true);
		masterColorDTO.setSku(master.getSku());
		
		boolean excludeMasterProductFront = false;
		if (skus != null && !skus.isEmpty() && (!skus.contains(master.getSku()))) {
			excludeMasterProductFront = true;
			masterColorDTO.setSelected(false);
		}
		
		if (MyShopUtils.hasAttribute("colorMontura", master.getAttributes()))
			masterColorDTO.setColorMontura(((LocalizedString)MyShopUtils.findAttribute("colorMontura", master.getAttributes()).getValue()).get(MyshopConstants.esLocale));
		
		if (MyShopUtils.hasAttribute("colorIcono", master.getAttributes()))
			masterColorDTO.setColorIcono((String) MyShopUtils.findAttribute("colorIcono", master.getAttributes()).getValue());
//		else
//			masterColorDTO.setColorIcono("linear-gradient(-45deg, white 12px, black 15px, black 15px, white 17px );");
		if (MyShopUtils.hasAttribute("codigoColor", master.getAttributes()))
			masterColorDTO.setCodigoColor((String) MyShopUtils.findAttribute("codigoColor", master.getAttributes()).getValue());
		if (!excludeMasterProductFront)
			colorsDto.add(masterColorDTO);
		
		
		for(ProductVariant variant: product.getVariants()) {
			if (skus == null || skus.isEmpty() || (skus.contains(variant.getSku()))) {
				ColorDTO variantColorDTO = new ColorDTO();
				variantColorDTO.setSku(variant.getSku());
				if (MyShopUtils.hasAttribute("colorMontura", variant.getAttributes()))
					variantColorDTO.setColorMontura(((LocalizedString)MyShopUtils.findAttribute("colorMontura", variant.getAttributes()).getValue()).get(MyshopConstants.esLocale));
				
				if (MyShopUtils.hasAttribute("colorIcono", variant.getAttributes()))
					variantColorDTO.setColorIcono((String) MyShopUtils.findAttribute("colorIcono", variant.getAttributes()).getValue());
	//			else
	//				variantColorDTO.setColorIcono("linear-gradient(-45deg, white 12px, black 15px, black 15px, white 17px );");
				if (MyShopUtils.hasAttribute("codigoColor", variant.getAttributes()))
					variantColorDTO.setCodigoColor((String) MyShopUtils.findAttribute("codigoColor", variant.getAttributes()).getValue());
				boolean encontrado = false;
				//las distintas variantes de color que tienen que aparecer nos solo son por colorIcono sino tambien por codigo de color
				for (ColorDTO color: colorsDto) {
					try {
						if ((color.getColorIcono() == null)&&(variantColorDTO.getCodigoColor() == null)
								|| ((color.getColorIcono() != null) && color.getColorIcono().equals(variantColorDTO.getColorIcono()) 
										&& color.getCodigoColor().equals(variantColorDTO.getCodigoColor()))) {
							encontrado = true;
								
						}
					} catch (Exception e) {
						log.error("ERROR EN el producto " + variantColorDTO.getSku() + " con el listado" + colorsDto.toString());
					}
				}
				if (!encontrado)
					colorsDto.add(variantColorDTO);
			}
		}
		return colorsDto;
	}

	private String getCodigoAndColor(ProductFrontDTO p) {
		
		String color = "";
		String codigo = "";
		
		if (p.getCodigocolor() != null && !p.getCodigocolor().isEmpty()) {
			codigo = p.getCodigocolor();
		}
		
		if (p.getColor() != null && !p.getColor().isEmpty()) {
			color = p.getColor();
		}
		
		return codigo.concat(color);
	}
	
	private Category getCategoryIdByName(Locale locale, String name) {
		Category result =null;
		List<Category> categorias = 
				cioneEcommerceConectionProvider
				.getApiRoot()
				.categories()
				.get()
				.withWhere("name("+locale.getLanguage()+"=\""+ name+"\")")
				.executeBlocking()
				.getBody()
				.getResults();
		if ((categorias!= null) && (!categorias.isEmpty()))
			result = categorias.get(0);
		return result;
	}

	/**
	 * 
	 * Metodo para obtener las variantes segun un ProductProjection. Este metodo esta 
	 * dentro del proceso de liberar todo la informacion que no es necesaria en el front
	 * 
	 * @param product producto del tipo ProductoProjection
	 * @param aliasEKON
	 * @param colors listado de colores del producto
	 * @param calibers listado de calibres del producto
	 * @param calibrations listado de graduaciones de un producto
	 * @return listado de variantes del master variant
	 * 
	 */
	private List<ProductFrontDTO> getProductFiltersVariants(ProductProjection product, List<String> aliasEKON, List<String> codigosCentral,
			List<String> colors, List<String> calibers, List<String> calibrations, List<String> tamanios, boolean isVariantPack, List<String> skus) {
		
		List<ProductFrontDTO> variants = new ArrayList<>();
		
		for(ProductVariant variant: product.getVariants()) {
			
			ProductFrontDTO pvar = new ProductFrontDTO();
			pvar.setSku(variant.getSku());
			pvar.setImages(getImages(variant));
			pvar.setName(getName(product.getName()));
			
			if(!variant.getPrices().isEmpty()) {
				Price price = getPriceBycustomerGroup(getGrupoPrecioCommerce(), variant.getPrices());
				if (price != null)
					pvar.setPvo(getCentAmountDouble(price.getValue().getCentAmount(), price.getValue().getFractionDigits()));
			}
			
			for(Attribute attr: variant.getAttributes()) {
				pvar.setVartiantPack(isVariantPack);
				if(attr.getName().equals(MyshopConstants.REPUESTOALTERNATIVO)) {
					pvar.setHasSubstitute(true);
				}
				
				if(attr.getName().equals(MyshopConstants.TIENEPROMOCIONES)) {
					pvar.setPromo((Boolean) attr.getValue());
				}
				
				if(attr.getName().equals(MyshopConstants.TIENERECARGO)) {
					pvar.setRecargo((Boolean) attr.getValue());
				}
				
				if(attr.getName().equals(MyshopConstants.COLECCION)) {
					pvar.setColeccion(((LocalizedString) attr.getValue()).get(MyshopConstants.esLocale));
				}
				
				if(attr.getName().equals(MyshopConstants.ALIASEKON)) {
					pvar.setAliasEKON((String) attr.getValue());
				}
				
				if(attr.getName().equals(MyshopConstants.CODIGOCENTRAL)) {
					pvar.setCodigocentral((String) attr.getValue());
				}
				
				if(attr.getName().equals(MyshopConstants.NOMBREARTICULO)) {
					//pvar.setName((String) attr.getValue());
					pvar.setNombreArticulo((String) attr.getValue());
				}
				
				if(attr.getName().equals(MyshopConstants.COLORICONO)) {
					pvar.setColor((String) attr.getValue());
					//setList(colors,attr.getValueAsString());
				}		
				
				if(attr.getName().equals(MyshopConstants.CODIGOCOLOR)) {
					pvar.setCodigocolor((String) attr.getValue());
				}
				
				if(attr.getName().equals(MyshopConstants.COLORMONTURA)) {
					pvar.setColorMontura(((LocalizedString) attr.getValue()).get(MyshopConstants.esLocale));
				}	
				
				if(attr.getName().equals(MyshopConstants.DIMENSIONES_ANCHO_OJO)) {
					pvar.setCalibre(((Long) attr.getValue()).toString());
					setList(calibers,pvar.getCalibre());
				}				
				
				if(attr.getName().equals(MyshopConstants.GRADUACION)) {
					pvar.setCalibration((String) attr.getValue());
					setList(calibrations,(String) attr.getValue());
				}
				
				if(attr.getName().equals(MyshopConstants.PVPRECOMENDADO)) {
					CentPrecisionMoney pvp = (CentPrecisionMoney) attr.getValue();
					pvar.setPvp(getCentAmountDouble(pvp.getCentAmount(), pvp.getFractionDigits()));
				}
				
				if(attr.getName().equals(MyshopConstants.PLAZOENTREGAPROVEEDOR)) {
					pvar.setDelivery(((Long)attr.getValue()).intValue());
				}
				
				if(attr.getName().equals(MyshopConstants.TAMANIO)) {
					pvar.setTamanio(((LocalizedString) attr.getValue()).get(MyshopConstants.esLocale));
					setList(tamanios,pvar.getTamanio());
				}
				
				if(attr.getName().equals(MyshopConstants.TIPOPRODUCTO)) {
					pvar.setTipoproducto(((LocalizedString) attr.getValue()).get(MyshopConstants.esLocale));
					if (pvar.getFamiliaproducto() == null)
						pvar.setFamiliaproducto(MyShopUtils.getFamiliaProducto(pvar.getTipoproducto()));
				}
				
				if (attr.getName().equals(MyshopConstants.PLANTILLA))
					pvar.setFamiliaproducto(((AttributePlainEnumValue) (attr.getValue())).getKey());
				
				if(attr.getName().equals(MyshopConstants.COLOR)) {
					ArrayList<String> list = (ArrayList<String>) attr.getValue();
					ArrayList<String> coloresAudio = new ArrayList<String>();
					for (String color: list) {
						coloresAudio.add(color);
					}
					pvar.setColorsAudio(coloresAudio);
				}
				if (attr.getName().equals(MyshopConstants.PVOSINPACK)) {
					CentPrecisionMoney pvp = (CentPrecisionMoney) attr.getValue();
					pvar.setPvoSinPack(getCentAmountDouble(pvp.getCentAmount(), pvp.getFractionDigits()));
				}
				if(attr.getName().equals(MyshopConstants.STATUSEKON)) {
					pvar.setStatusEkon((String) attr.getValue());
				}
				if(attr.getName().equals(MyshopConstants.OFERTAFLASH)) {
					pvar.setOfertaFlash((Boolean) attr.getValue());
				}
				if(attr.getName().equals(MyshopConstants.GESTIONSTOCK)) {
					pvar.setGestionStock((Boolean) attr.getValue());
				}
				
			}
			setList(aliasEKON,pvar.getAliasEKON());
			setList(codigosCentral,pvar.getCodigocentral());
			
			//pvar.setStock(getStock(pvar.getAliasEKON()));
			
			String finalColor = getCodigoAndColor(pvar);
			
			if (finalColor != null && !finalColor.isEmpty()) {
				pvar.setColor(finalColor);
				setList(colors,finalColor);
			}
			
			boolean addListProductFront = true;
			if (skus.isEmpty() || (skus.contains(pvar.getSku())))
				pvar.setIncluidoEnPack(true);
			else
				addListProductFront = false;
			
			if (MgnlContext.getParameter("skuPackMaster") != null) {
				//añadimos la info necesaria si es un producto que estamos seleccionando a traves de un pack generico
				try {
					pvar.setTipoPrecioPack(getTipoPrecioPackListadoFilter());
					
					int step = -1;
					if ((MgnlContext.getWebContext().getRequest().getParameter("step") != null) 
							&& !MgnlContext.getWebContext().getRequest().getParameter("step").isEmpty()) {
						String step_str = MgnlContext.getWebContext().getRequest().getParameter("step");
						try {
							step = Integer.valueOf(step_str).intValue();
						} catch (NumberFormatException e) {
							log.error(e.getMessage(), e);
						}
					}
					
					pvar.setPvoPack(getTipoPvoPackListadoFilter(pvar.getPvo(), pvar.getTipoPrecioPack(), pvar.getTipoproducto(), step));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					addListProductFront = false;
				}
			}
			
			if (addListProductFront)
				variants.add(pvar);
			
		}
		
		return variants;
	}

	/**
	 * Metodo para el calculo de offset teniendo en cuenta
	 * que el listado devolvera siempre <b>12 elementos</b>.
	 * 
	 * @param page pagina en la que se encuentra el usuario
	 * 
	 * @return offset que aplicar a la consulta
	 
	private long calcOffset(int page) {
		
		long res = 0;
		
		if (page>=1) {
			res = (long) page*12;
		}
		
		return res;
	}*/

	/**
	 * 
	 * Metodo para obtener el nombre del producto o
	 * la variante. Se ha separado para que en el futuro
	 * simplemente tengamos que modificar este metodo para
	 * que las consultas sean en base al idioma seleccionado
	 * en magnolia
	 * 
	 * @param name nombre del producto segun LocalizedString
	 * @return nombre del producto directo (String)
	 */
	private String getName(LocalizedString name) {
		
		if (name != null) {
			Optional<String> s = name.find(new Locale("es"));
			return s.isPresent() ? s.get() : null;
		}
		
		return null;
	}

	/**
	 * 
	 * Metodo que nos devuelve una lista de imagenes lista
	 * para el front a partir de una variante
	 * 
	 * @param masterVariant producto del que obtener las variantes
	 * @return lista de imagenes
	 */
	private List<com.magnolia.cione.beans.Image> getImages(ProductVariant masterVariant) {
		
		List<com.magnolia.cione.beans.Image> images = new ArrayList<>();
		
		if (masterVariant != null) {
			for(Image i: masterVariant.getImages()) {
				com.magnolia.cione.beans.Image image = new com.magnolia.cione.beans.Image();
				image.setUrl(i.getUrl());
				images.add(image);
			}
		}
		
		return images.isEmpty() ? Collections.emptyList() : images;
	}

	@Override
	public CustomerCT getCustomer() {

		String numUsuario = MyShopUtils.getUserName();
		
		return getCustomer(numUsuario);
	}
	
	public String getCustomerId() {
		HttpSession session = MgnlContext.getWebContext().getRequest().getSession();
		if (session.getAttribute(MyshopConstants.ATTR_CUSTOMERID_SESSION) != null)
			return session.getAttribute(MyshopConstants.ATTR_CUSTOMERID_SESSION).toString();
		else {
			String numUsuario = MyShopUtils.getUserName();
			CustomerCT customerCT = getCustomer(numUsuario);
			if (customerCT != null) {
				session.setAttribute(MyshopConstants.ATTR_CUSTOMERID_SESSION, customerCT.getId());
				return customerCT.getId();
			}				
			else
				return null;
		}
	}
	
	@Override
	public Customer getCustomerSDK() {
		String customerNumber = MyShopUtils.getUserName();
		Customer customer = null;
		List<Customer> listCustomers = 
			cioneEcommerceConectionProvider.getApiRoot().customers().get()
				.withWhere("customerNumber=\"" + customerNumber + "\"")
				.executeBlocking()
				.getBody()
				.getResults();
		if ((listCustomers != null) && (!listCustomers.isEmpty())) {
			customer = listCustomers.get(0);
		}
		
		return customer;
	}

	@Override
	public CustomerCT getCustomer(String numUsuario) {
		
		CustomerCT customerCT = null;
		ResteasyWebTarget targetquery = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(cioneEcommerceConectionProvider.getConfigService().getConfig().getApiCommercetoolsPath() + 
						"/" + cioneEcommerceConectionProvider.getConfigService().getConfig().getApiCommercetoolsProjectKey() + 
						"/customers?where=customerNumber%3D%22" + numUsuario + "%22");
		
		Response responsequery = targetquery.request().header(HttpHeaders.AUTHORIZATION, "Bearer " + cioneEcommerceConectionProvider.getAuthToken()).get();
		
		if (responsequery.getStatus() == 200) {
			
			CustomersQueryCTDTO customersQueryCTDTO = responsequery.readEntity(CustomersQueryCTDTO.class);
			List<CustomerCT> listCustomer = customersQueryCTDTO.getResults();
			if (listCustomer.size() >=1)
				customerCT = listCustomer.get(0); //debe devolver un unico registros ya que el customerNumber es unico
		}
		responsequery.close();
		return customerCT;
	}

	@Override
	public boolean customerExists(String numUsuario) {
		
		return getCustomer(numUsuario) != null;
	}
	
	@Override
	public TaxCategory getTaxCategory() {
		/*TaxCategory taxCategory = (TaxCategory) cioneEcommerceConectionProvider.getCache_long().getIfPresent("taxCategory");
		if (taxCategory == null) 
			taxCategory = cioneEcommerceConectionProvider.getClient().executeBlocking(TaxCategoryByKeyGet.of(
					cioneEcommerceConectionProvider.getConfigService().getConfig().getTaxCategoryKey()));
		cioneEcommerceConectionProvider.getCache_long().put("taxCategory", taxCategory);
		return taxCategory;*/
		return cioneEcommerceConectionProvider.getTaxCategory();
	}
	
	
	//PROBAR BIEN!! SOBRETODO POR SI ES NECESARIO INCLUIR EL PREDICADO QUE QUITA COMPRAS PERIODICAS Y PRESUPUESTOS
	private Cart getCart(String customerId) {
		Cart cart = null;
		
		try {
			cart = cioneEcommerceConectionProvider.getApiRoot()
				.carts()
				.withCustomerId(customerId)
				.get()
				.executeBlocking()
				.getBody()
				.get();
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return cart;
	}
	
	public Map<String, String> getSustituveReplacement(ProductVariant variant, Integer cant) {
		
		HashMap<String, String> replacementsAux = new HashMap<String, String>();
		String priceGroup = getGrupoPrecioCommerce();
		
			if (MyShopUtils.hasAttribute(MyshopConstants.REPUESTOALTERNATIVO, variant.getAttributes())) {
				ArrayList<String> sustitutives = (ArrayList<String>) MyShopUtils.findAttribute(MyshopConstants.REPUESTOALTERNATIVO, variant.getAttributes()).getValue();
//				List<String> sustitutives = new ArrayList<String>(set);
				for (String sku : sustitutives) {
				if (!sku.isEmpty()) {
					ProductProjection product = getProductBysku(sku, priceGroup);
					
					
					
					ProductVariant variantsustitutive = commercetoolsServiceAux.findVariantBySku(product, sku);//product.findVariantBySku(sku).get();
					String aliaEkon = null;
					if (MyShopUtils.hasAttribute("aliasEkon", variantsustitutive.getAttributes())) {
						String aliasEkonAt = (String) MyShopUtils.findAttribute("aliasEkon", variantsustitutive.getAttributes()).getValue();
						if (!aliasEkonAt.isEmpty()) {
							aliaEkon = aliasEkonAt;
						}
					}
					
					int stock = getStockWithCart(sku, aliaEkon);
					if (stock >= cant) {
						String name = product.getName().get(MyshopConstants.esLocale);
						if (MyShopUtils.hasAttribute("nombreArticulo", variantsustitutive.getAttributes())) {
							name = (String) MyShopUtils.findAttribute("nombreArticulo", variantsustitutive.getAttributes()).getValue();
						
							VariantDTO infovariant = getPromocionesByVariant(variantsustitutive);
							if (infovariant.getPvoDto()!= null)
								name = name + " (" + infovariant.getPvoDto() + " €)";
							else 
								name = name + " (" + infovariant.getPvo() + " €)";
							replacementsAux.put(sku, name);
						}
					}
				}
			}
		}
		return MyShopUtils.sortByValue(replacementsAux);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, List<AgrupadorDTO>> getInfoPackMapSession(){
		return (HashMap<String, List<AgrupadorDTO>>) MgnlContext.getWebContext().getRequest().getSession().getAttribute(MyshopConstants.ATTR_PACK_SESSION);
	}
	
	
	public String getPvoOriginForPortugal(ProductVariant variant, String grupoPrecio, Price price) {
		String pvoOrigin = MyShopUtils.formatTypedMoney(price.getValue());
		try {
			Context initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup(MyshopConstants.CONN);
			
			String aliasEKON = "";
			String codigoCentral = "";
			if (MyShopUtils.hasAttribute(MyshopConstants.ALIASEKON, variant.getAttributes())) {
				aliasEKON = (String) MyShopUtils.findAttribute(MyshopConstants.ALIASEKON, variant.getAttributes()).getValue();
			}
			
			if (MyShopUtils.hasAttribute(MyshopConstants.CODIGOCENTRAL, variant.getAttributes())) {
				codigoCentral = (String) MyShopUtils.findAttribute(MyshopConstants.CODIGOCENTRAL, variant.getAttributes()).getValue();
			}
			
			try (java.sql.Connection con = ds.getConnection();
				PreparedStatement ps = createPreparedStatement(con, aliasEKON, codigoCentral, grupoPrecio);
				ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {	
						double valor = rs.getDouble("valor");
						if (valor >0) //es un descuento, no aplicamos un incremento de 5% al precio origen ya que no esta contemplado en bbdd 
							valor = 0.00;
						int tipodescuento = rs.getInt("tipo_descuento");
						if (tipodescuento == MyshopConstants.dtoPorcentaje) { // si es por porcentaje
							
							double dto= ((100-valor)/100);
							double pvodto = Double.valueOf(MyShopUtils.formatTypedMoney(price.getValue()).replace(',', '.')) * dto;
							
							pvoOrigin = MyShopUtils.formatDoubleString(pvodto);
						} else {
							double pvodto = Double.valueOf(MyShopUtils.formatTypedMoney(price.getValue()).replace(',', '.')) - valor;
							pvoOrigin = MyShopUtils.formatDoubleString(pvodto);
						}
					}
			} catch (SQLException e) {
				log.error("Error al obtener el recargo en la BBDD para el producto " + variant.getSku(), e);
			}
		} catch (NumberFormatException e) {
			log.error("Error al obtener el recargo en la BBDD para el producto " + variant.getSku(), e);
		} catch (NamingException e) {
			log.error("Error al obtener el recargo en la BBDD para el producto " + variant.getSku(), e);
		}
		return pvoOrigin;
	}
	
	public double getPvoOriginForPortugalDouble(ProductVariant variant, String grupoPrecio, Price price) {
		double pvoOrigin = MyShopUtils.formatMoneyDouble(price.getValue());
		try {
			Context initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup(MyshopConstants.CONN);
			
			String aliasEKON = "";
			String codigoCentral = "";
			if (MyShopUtils.hasAttribute(MyshopConstants.ALIASEKON, variant.getAttributes())) {
				aliasEKON = (String) MyShopUtils.findAttribute(MyshopConstants.ALIASEKON, variant.getAttributes()).getValue();
			}
			
			if (MyShopUtils.hasAttribute(MyshopConstants.CODIGOCENTRAL, variant.getAttributes())) {
				codigoCentral = (String) MyShopUtils.findAttribute(MyshopConstants.CODIGOCENTRAL, variant.getAttributes()).getValue();
			}
			
			try (java.sql.Connection con = ds.getConnection();
				PreparedStatement ps = createPreparedStatement(con, aliasEKON, codigoCentral, grupoPrecio);
				ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {	
						double valor = rs.getDouble("valor");
						if (valor >0) //es un descuento, aplicamos un incremento de 5%
							valor = -5.00;
						int tipodescuento = rs.getInt("tipo_descuento");
						if (tipodescuento == MyshopConstants.dtoPorcentaje) { // si es por porcentaje
							
							double dto= ((100-valor)/100);
							pvoOrigin = MyShopUtils.formatMoneyDouble(price.getValue()) * dto;
						} else {
							pvoOrigin = MyShopUtils.formatMoneyDouble(price.getValue()) - valor;
						}
					}
			} catch (SQLException e) {
				log.error("Error al obtener el recargo en la BBDD para el producto " + variant.getSku(), e);
			}
		} catch (NumberFormatException e) {
			log.error("Error al obtener el recargo en la BBDD para el producto " + variant.getSku(), e);
		} catch (NamingException e) {
			log.error("Error al obtener el recargo en la BBDD para el producto " + variant.getSku(), e);
		}
		return pvoOrigin;
	}
	
	private PreparedStatement createPreparedStatement(java.sql.Connection con, String aliasEkon, String codigo_central, String grupoPrecio)
			throws SQLException {
		Date fechaHoy = new Date();
		//AliasEkon puede ser nulo, sustituir por *
		if ( aliasEkon == null)
			aliasEkon = "*";
		
		String sql = "select * from mycione.tbl_descuentos where aliasEkon = ?"
				+ " and codigo_central = ?"
				+ " and grupo_precio = ?"; 
		if (fechaHoy != null) {
			sql += " and fecha_desde <= ?";
		}
		if (fechaHoy != null) {
			sql += " and fecha_hasta >= ?";
		}
		sql += " order by cantidad_desde";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, aliasEkon);
		ps.setString(2, codigo_central);
		ps.setString(3, grupoPrecio);
		ps.setDate(4, new java.sql.Date(fechaHoy.getTime()));
		ps.setDate(5, new java.sql.Date(fechaHoy.getTime()));

		return ps;
	} 
}
