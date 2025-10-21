package com.magnolia.cione.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.client.ClientBuilder;
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

import com.magnolia.cione.dto.AlbaranQueryParamsDTO;
import com.magnolia.cione.dto.ConfigCione;
import com.magnolia.cione.dto.FacturaQueryParamsDTO;
import com.magnolia.cione.dto.LineaAlbaranDTO;
import com.magnolia.cione.dto.LineasAlbaranDTO;
import com.magnolia.cione.dto.PaginaAlbaranesDTO;
import com.magnolia.cione.dto.PaginaFacturasDTO;
import com.magnolia.cione.exceptions.CioneRuntimeException;
import com.magnolia.cione.utils.CioneUtils;

public class ElasticServiceImpl implements ElasticService{

	private ConfigCione configService;
	private static final Logger _log = LoggerFactory.getLogger(ElasticServiceImpl.class);
	//ResteasyClient client = new ResteasyClientBuilder().build();
	ResteasyClient client;
	
	public ElasticServiceImpl() {
		initConfig();
		//client = (ResteasyClient)ClientBuilder.newClient();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		cm.setMaxTotal(200); // Increase max total connection to 200
		cm.setDefaultMaxPerRoute(20); // Increase default max connection per route to 20
		ApacheHttpClient43Engine myEngine = new ApacheHttpClient43Engine(httpClient);
		client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(myEngine).build();
	}
	
	public PaginaAlbaranesDTO getAlbaranes(String idClient, AlbaranQueryParamsDTO filters) {
		
		//Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		ResteasyWebTarget target = client.target(configService.getApiElasticPath() + "/albaran?idsocio=" + idClient);
		Map<String, String> params = CioneUtils.buildQueryParams(filters);
		
		for (String key: params.keySet()){		
			String value = params.get(key);
			target = target.queryParam(key, value);  
		}
		Response response = target.request().get();
		return response.readEntity(PaginaAlbaranesDTO.class);
	}
	
	public File getDocAlbaran(String url) {
		
		//Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		ResteasyWebTarget target = client.target(configService.getApiElasticPath() + "/pdf?url=" + url);
		Response response = target.request().get();
		return response.readEntity(File.class);
	}
	
	public List<LineaAlbaranDTO> getLineaAlbaran(String idClient, String numAlbaran) {
		
		//Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		ResteasyWebTarget target = client.target(configService.getApiElasticPath() + "/albaran?idsocio=" + idClient + "&numAlbaran="+ numAlbaran);
		
		Response response = target.request().get();
		LineasAlbaranDTO lineasAlbaranDTO = response.readEntity(LineasAlbaranDTO.class);
		return lineasAlbaranDTO.getLineasAlbaran();
	}
	
	public PaginaFacturasDTO getFacturas(String idClient, FacturaQueryParamsDTO filters) {
		ResteasyWebTarget target = client.target(configService.getApiElasticPath() + "/factura?codsocio=" + idClient);
		Map<String, String> params = CioneUtils.buildQueryParams(filters);
		
		for (String key: params.keySet()){		
			String value = params.get(key);
			target = target.queryParam(key, value);  
		}
		Response response = target.request().get();
		return response.readEntity(PaginaFacturasDTO.class);
	}
	
	public void initConfig() {
		Properties prop = new Properties();	
		configService = new ConfigCione();
		try {
			_log.debug("Load configuration from properties");
			prop.load(ConfigServiceImpl.class.getClassLoader().getResourceAsStream("config.properties"));			
			configService.setAuthTestMode(Boolean.valueOf(prop.getProperty(ConfigCione.AUTH_TEST_MODE)));
			configService.setAuthTestEmail(prop.getProperty(ConfigCione.AUTH_TEST_EMAIL));
			configService.setAuthSenderEmail(prop.getProperty(ConfigCione.AUTH_SENDER_EMAIL));
			configService.setApiMiddlewarePath(prop.getProperty(ConfigCione.API_MIDDLEWARE_PATH));
			configService.setApiMiddlewareUser(prop.getProperty(ConfigCione.API_MIDDLEWARE_USER));
			configService.setApiMiddlewarePwd(prop.getProperty(ConfigCione.API_MIDDLEWARE_PWD));
			configService.setInvoiceDocsPath(prop.getProperty(ConfigCione.INVOICE_DOCS_PATH));
			configService.setApiElasticPath(prop.getProperty(ConfigCione.API_ELASTIC_PATH));
			_log.debug("ElasticPath -" + ConfigCione.API_ELASTIC_PATH);
		} catch (IOException e) {		
			_log.error(e.getMessage());
			throw new CioneRuntimeException("Error getting configuration of cione-module (properties)");			
		}
	}
	
	/*private String getAuth() {
		String userPwd = configService.getApiElasticUser() + ":" + configService.getApiElasticPwd();
		return "Basic " + Base64.getEncoder().encodeToString(userPwd.getBytes());		
	}*/

}
