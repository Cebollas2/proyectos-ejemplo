package com.magnolia.cione.setup;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient43Engine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.defaultconfig.ApiRootBuilder;
import com.commercetools.api.defaultconfig.ServiceRegion;
import com.commercetools.api.json.ApiModuleOptions;
import com.commercetools.api.models.category.Category;
import com.commercetools.api.models.category.CategoryTree;
import com.commercetools.api.models.customer_group.CustomerGroup;
import com.commercetools.api.models.shipping_method.ShippingMethod;
import com.commercetools.api.models.tax_category.TaxCategory;
import com.commercetools.api.models.type.Type;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.TokenAuthCT;
import com.magnolia.cione.service.ConfigService;
import com.magnolia.cione.service.ConfigServiceImpl;

import io.vrap.rmf.base.client.ResponseSerializer;
import io.vrap.rmf.base.client.oauth2.ClientCredentials;
import io.vrap.rmf.base.client.utils.json.JsonUtils;

@Singleton
public class CioneEcommerceConectionProvider {

	private static final Logger log = LoggerFactory.getLogger(CioneEcommerceConectionProvider.class);
 
	private static ProjectApiRoot apiRoot;
	public static String flag;
	private Cache<String, Object> cache;
	private Cache<String, Object> cache_long;
	private Cache<String, Object> cacheAuthToken;
	private Cache<String, CategoryTree> cacheCategories;
	private Cache<String, Map<String, CustomerGroup>> cacheCustomerGroups;
	private ConfigService configService;
	public static ResteasyClient restClient;
	public static ResteasyClient restClientFit;
	private String authToken;
	private TaxCategory taxCategory;
	private Map<String, Type> mapTypes;
	private Map<String, ShippingMethod> mapShippingMethods;
	private CategoryTree categoryTree;
	private Map<String, CustomerGroup> customerGroupList;
	
	private static ApacheHttpClient43Engine myEngine;
	private static PoolingHttpClientConnectionManager cm;

	CioneEcommerceConectionProvider() {
		try {
			apiRoot = createCommerceToolsClient();
			
			ClientBuilder builder = ClientBuilder.newBuilder()
			        .connectTimeout(5000, TimeUnit.MILLISECONDS)
			        .readTimeout(5000, TimeUnit.MILLISECONDS);
			CioneEcommerceConectionProvider.restClientFit = (ResteasyClient)builder.build(); //cliente para fitting-box
			
			CioneEcommerceConectionProvider.flag="CioneEcommerceProvider";
			CioneEcommerceConectionProvider.restClient = (ResteasyClient)ClientBuilder.newClient(); //cliente para peticiones http a commercetools
			this.configService = new ConfigServiceImpl(); 
			this.authToken = getAuthTokenCommerceTools();
			this.categoryTree = initCategoryTree();
			this.customerGroupList = initCustomerGroupsList();
			this.taxCategory = getTaxCategoryCommerceTools();
			this.cache = Caffeine.newBuilder()
                    .expireAfterWrite(30, TimeUnit.SECONDS)
                    .build();
			this.cache_long = Caffeine.newBuilder()
                    .expireAfterWrite(24, TimeUnit.HOURS)
                    .build();
			this.cache_long.put("taxCategory", this.taxCategory);
			this.cacheAuthToken = Caffeine.newBuilder()
                    .expireAfterWrite(60, TimeUnit.MINUTES)
                    .build();
			this.cacheAuthToken.put("authToken", this.authToken);
			
			this.cacheCategories = Caffeine.newBuilder()
                    .expireAfterWrite(30, TimeUnit.MINUTES)
                    .build();
			this.cacheCustomerGroups = Caffeine.newBuilder()
                    .expireAfterWrite(120, TimeUnit.MINUTES)
                    .build();
			
			this.cacheCategories.put("categoryTree", this.categoryTree);
			
			
			this.cacheCustomerGroups.put("customerGroups", this.customerGroupList);
			
			
			myEngine = createEngine();
			
			mapTypes = getAllTypes();
			
			mapShippingMethods = getAllShippingMethods();
			
			
		} catch (Exception e) {
			log.error("No se ha podido crear el cliente", e);
		}
	}
	
	
	private ProjectApiRoot createCommerceToolsClient() throws IOException {
		Properties properties = loadPropertiesFromClasspath("commercetools.properties");
		String clientId = (String) properties.get("clientId");
		String clientSecret = (String) properties.get("clientSecret");
		String projectKey = (String) properties.get("projectKey");
		
		ApiModuleOptions options = ApiModuleOptions.of()
		        .withDateAttributeAsString(true)
		        .withDateCustomFieldAsString(true);
		ObjectMapper mapper = JsonUtils.createObjectMapper(options);
		
		return ApiRootBuilder.of()
		.withSerializer(ResponseSerializer.of(mapper))
        .defaultClient(
            ClientCredentials
                .of()
                .withClientId(clientId)
                .withClientSecret(clientSecret)
                .build(),
            ServiceRegion.GCP_EUROPE_WEST1.getOAuthTokenUrl(),
            ServiceRegion.GCP_EUROPE_WEST1.getApiUrl()
        )
        .build(projectKey);
		
		 
	}
	
	public void invalidateCache(String key) {
		this.cache.invalidate(key);
	}
	
	public void invalidateAllCache() {
		this.cache.invalidateAll();
	}
	
	//cache corta (30 segundos), usada para cachear el listado de productos de la home
	public Cache<String, Object> getCache() {
		return this.cache;
	}
	
	public Cache<String, Object> getCacheAuthToken() {
		return this.cacheAuthToken;
	}
	
	//cache larga (34 horas), usada para cachear las taxcategories y todo aquello que no es susceptible de ser modificado
	public Cache<String, Object> getCache_long() {
		return cache_long;
	}
	
	public void closeConnection() {
		apiRoot.close();
	}
	
	public void resetConnection() {
		apiRoot.close();
		try {
			apiRoot = createCommerceToolsClient();
		} catch (IOException e) {
			log.error("ERROR al crear el cliente de conexion a commercetools", e);
		}
	}
	
	public ConfigService getConfigService() {
		if (this.configService== null)
			this.configService= new ConfigServiceImpl();
		return configService;
	}
	
	/**
	 * 
	 * Con los datos de configuracion obtenemos el token de acceso
	 * 
	 * @return token de acceso a la API de CommerceTools
	 */
	private String getAuthTokenCommerceTools() {
		long startTime = System.nanoTime();
		String result = null;
		
		ResteasyWebTarget target = CioneEcommerceConectionProvider.restClient
				.target(configService.getConfig().getApiCommercetoolsAuthPath() + 
						"/oauth/token?grant_type=client_credentials&scope=manage_project:" +
						configService.getConfig().getApiCommercetoolsProjectKey());
		
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).post(Entity.text(""));
		
		TokenAuthCT tokenauthct = response.readEntity(TokenAuthCT.class);
		
		if(tokenauthct != null) {
			result = tokenauthct.getAccess_token();
		}
		
		response.close();
		
		long endTime = System.nanoTime();
		log.debug("getAuthToken " + String.valueOf(endTime - startTime) + "-" + String.valueOf((endTime - startTime)/1000000000));
		
		return result;
		
	}
	
	/**
	 * 
	 * Con los datos de configuracion obtenemos el token de acceso
	 * a FittingBox
	 * 
	 * @return token de acceso a la API de FittingBox
	 
	private String getAuthTokenAPIFittingBox() {
		
		String result = null;
			try {
				FittingBoxClientCredentialsDTO fittingBoxClientCredentialsDTO = new FittingBoxClientCredentialsDTO();
				fittingBoxClientCredentialsDTO.setGrant_type("client_credentials");
				fittingBoxClientCredentialsDTO.setClient_id(configService.getConfig().getApiFittingBoxClientId());
				fittingBoxClientCredentialsDTO.setClient_secret(configService.getConfig().getApiFittingBoxClientSecret());
				
				String aux = configService.getConfig().getApiFittingBoxPath() + 
						   "/oauth/token";
				log.debug("URL FITTING = " +aux);
				
				
				ResteasyWebTarget target = CioneEcommerceConectionProvider.restClientFit
										   .target(configService.getConfig().getApiFittingBoxPath() + 
										   "/oauth/token");
				
				Response response = target.request().post(Entity.json(fittingBoxClientCredentialsDTO));
				
				TokenAuthFittingBox tokenauthct = response.readEntity(TokenAuthFittingBox.class);
				
				if(tokenauthct != null) {
					result = tokenauthct.getAccess_token();
				}
				
				response.close();
			} catch (Exception e) {
				log.debug(e.getMessage(), e);
			}
		
		return result;
		
	}*/
	
	/**
	 * 
	 * Obtenemos la basic auth para obtener el token de acceso
	 * 
	 * @return basic auth
	 */
	private String getAuth() {
		String userPwd = configService.getConfig().getApiCommercetoolsClientId() + ":"
				+ configService.getConfig().getApiCommercetoolsClientSecret();
		return MyshopConstants.BASIC + Base64.getEncoder().encodeToString(userPwd.getBytes());
	}
	
	/*
	 * Metodo que almacena y cachea el token de autenticacion de commercetools*/
	public String getAuthToken() {
		String authTokenCache = (String) this.cacheAuthToken.getIfPresent("authToken");
		if (authTokenCache == null) 
			this.authToken = getAuthTokenCommerceTools();
		this.cacheAuthToken.put("authToken", this.authToken);
		return this.authToken;
	}
	
    private Properties loadPropertiesFromClasspath(final String path) throws IOException {
    	Properties prop = new Properties();
    	prop.load(CioneEcommerceConectionProvider.class.getClassLoader().getResourceAsStream(path));
        return prop;
    }
    
	public CategoryTree initCategoryTree() {
		List<Category> categories = apiRoot.categories().get().withLimit(500).executeBlocking().getBody().getResults();
		return CategoryTree.of(categories);
	}
	
	public CategoryTree getCategoryTree() {
		CategoryTree tree =  this.cacheCategories.getIfPresent("categoryTree");
		//log.debug("SIZE CATEGORIA TREE" + cacheCategories.stats());
		
		if (tree == null) {
			log.debug("ALMACENA EN MEMORIA EL ARBOL DE CATEGORIAS");
			tree = initCategoryTree();
			this.cacheCategories.put("categoryTree", tree);
		}
		return tree;
	}
	
	public Map<String, CustomerGroup> initCustomerGroupsList() {
		Map<String, CustomerGroup> customerGroupList = new HashMap<>();
		List<CustomerGroup> listCustomerGroup = apiRoot.customerGroups().get().withLimit(200).executeBlocking().getBody().getResults();
		listCustomerGroup.forEach(customerGroup -> customerGroupList.put(customerGroup.getKey(), customerGroup));
		return customerGroupList;
		
	}
	
	public Map<String, CustomerGroup> getCustomerGroupList() {
		Map<String, CustomerGroup> customerGroupList = this.cacheCustomerGroups.getIfPresent("customerGroups");
		if (customerGroupList == null) {
			customerGroupList = initCustomerGroupsList();
			this.cacheCustomerGroups.put("customerGroups", customerGroupList);
		}
		return customerGroupList;
	}
	
	public CustomerGroup getCustomerByKey(String key) {
		return getCustomerGroupList().get(key);
	}
	
	private TaxCategory getTaxCategoryCommerceTools() {
		return apiRoot.taxCategories().withKey(configService.getConfig().getTaxCategoryKey()).get().executeBlocking().getBody().get();
	}
	
	public TaxCategory getTaxCategory() {
		TaxCategory taxCategory = (TaxCategory) this.cache_long.getIfPresent("taxCategory");
		if (taxCategory == null) {
			taxCategory = apiRoot.taxCategories().withKey(configService.getConfig().getTaxCategoryKey()).get().executeBlocking().getBody().get();
			this.cache_long.put("taxCategory", taxCategory);
		}
		return taxCategory;
	}
	
	public Map<String, Type> getMapTypes() {
		if (mapTypes.isEmpty())
			mapTypes = getAllTypes();
		return mapTypes;
	}
	
	public Map<String, ShippingMethod> getMapShippingMethods() {
		if (mapShippingMethods.isEmpty())
			mapShippingMethods = getAllShippingMethods();
		return mapShippingMethods;
	}
	
	private Map<String, Type> getAllTypes() {
		Map<String, Type> mapTypes = new HashMap<>();
		List<Type> types = apiRoot.types().get().withLimit(200).executeBlocking().getBody().getResults();
		types.forEach(type -> mapTypes.put(type.getKey(), type));
		return mapTypes;
	}
	
	private Map<String, ShippingMethod> getAllShippingMethods() {
		Map<String, ShippingMethod> mapTypesAux = new HashMap<>();
		List<ShippingMethod> types = apiRoot.shippingMethods().get().withLimit(200).executeBlocking().getBody().getResults();
		types.forEach(type -> mapTypesAux.put(type.getKey(), type));
		return mapTypesAux;
	}
	
	
    
	private ApacheHttpClient43Engine createEngine() {
		cm = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		cm.setMaxTotal(200); // Increase max total connection to 200
		cm.setDefaultMaxPerRoute(20); // Increase default max connection per route to 20
		
		myEngine = new ApacheHttpClient43Engine(httpClient);
		
		return myEngine;
	}
	
	public ResteasyClient getRestEasyClient() {
		//log.info("Engine stats =" + cm.getTotalStats().toString());
		cm.closeExpiredConnections();
		//System.out.println("Cerrando conexiones antiguas");
		if (myEngine == null) {
			System.out.println("Creando engine");
			myEngine = createEngine();
		}
		ResteasyClient cliente = ((ResteasyClientBuilder)ClientBuilder.newBuilder())
				.connectionCheckoutTimeout(2, TimeUnit.SECONDS).connectTimeout(5, TimeUnit.SECONDS)
				.httpEngine(myEngine).build();
		return cliente;
	}
    
    
	public ProjectApiRoot getApiRoot() {
		return apiRoot;
	}
	
	
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		CioneEcommerceConectionProvider.flag = flag;
	}

}

