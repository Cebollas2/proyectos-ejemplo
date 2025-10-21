package com.magnolia.cione.service;

import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient43Engine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.PepperAccessTokenDTO;
import com.magnolia.cione.dto.PepperUrlDTO;

public class PepperServiceImpl implements PepperService {

	private static final Logger log = LoggerFactory.getLogger(PepperServiceImpl.class);

	@Inject
	private ConfigService configService;

	ResteasyClient client;

	public PepperServiceImpl() {

		//client = (ResteasyClient)ClientBuilder.newClient();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		cm.setMaxTotal(200); // Increase max total connection to 200
		cm.setDefaultMaxPerRoute(20); // Increase default max connection per route to 20
		ApacheHttpClient43Engine myEngine = new ApacheHttpClient43Engine(httpClient);
		client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(myEngine).build();		
	}

	@Override
	public PepperAccessTokenDTO getAccessToken(String usuarioPepper) {

		PepperAccessTokenDTO result = null;
		
		String url = configService.getConfig().getPepperApiHost() + "/v1/security/token";
		
		Form connectRequest = new Form()
			    .param("username", usuarioPepper)
			    .param("password", configService.getConfig().getPepperPassword())
			    .param("grant_type", "password");
		
		Response response = client.target(url).request().header(HttpHeaders.AUTHORIZATION, configService.getConfig().getPepperToken())
				.post(Entity.entity(connectRequest, MediaType.APPLICATION_FORM_URLENCODED));
		result = response.readEntity(PepperAccessTokenDTO.class);

		return result;
	}

	@Override
	public PepperUrlDTO getUrl(String accessToken) {
		
		PepperUrlDTO result = null;
		
		String url = configService.getConfig().getPepperApiHost() + "/v2/dealers/web_access/dashboard";
		
		Response response = client.target(url).request().header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.post(null);
		response.getStatus();
		result = response.readEntity(PepperUrlDTO.class);
		
		return result;
	}

}
