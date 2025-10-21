package com.magnolia.cione.utils;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jboss.resteasy.client.jaxrs.ClientHttpEngine;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient43Engine;
import org.jboss.resteasy.client.jaxrs.internal.ClientInvocation;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Configuration;
import java.time.Duration;

public class RestEasyUtils {
	
	public static final String SOCKET_TIMEOUT = "SOCKET_TIMEOUT";
	public static final String CONNECT_TIMEOUT = "CONNECT_TIMEOUT";
	public static final String CONNECTION_REQUEST_TIMEOUT = "CONNECTION_REQUEST_TIMEOUT";
	
		public static Client newClient(){
		return newClient(10);
	}
	
	public static Client newClient(int poolSize){


		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		cm.setMaxTotal(200); // Increase max total connection to 200
		cm.setDefaultMaxPerRoute(20); // Increase default max connection per route to 20
		ApacheHttpClient43Engine myEngine = new ApacheHttpClient43Engine(httpClient);
		

		return ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(myEngine).build();
	}

	static Integer parseIntegerOrNull(Configuration conf, String key) {
		final Object property = conf.getProperty(key);
		if(property == null){
			return null;
		}
		return Integer.parseInt(String.valueOf(property));
	}

	/**
	 * Allow to set timeout per request
	 * @see #CONNECTION_REQUEST_TIMEOUT
	 * @see #CONNECT_TIMEOUT
	 * @see #SOCKET_TIMEOUT
	 */
	static ClientHttpEngine withPerRequestTimeout(HttpClient httpClient) {
		final ApacheHttpClient43Engine engine = new ApacheHttpClient43Engine(httpClient) {
			@Override
			protected void loadHttpMethod(ClientInvocation request, HttpRequestBase httpMethod) throws Exception {
				super.loadHttpMethod(request, httpMethod);
				final RequestConfig.Builder reqConf = httpMethod.getConfig() != null ? RequestConfig.copy(httpMethod.getConfig()) : RequestConfig.custom();
				final Configuration conf = request.getConfiguration();

				final Integer connectionRequestTimeout = parseIntegerOrNull(conf, RestEasyUtils.CONNECTION_REQUEST_TIMEOUT);
				if (connectionRequestTimeout != null) {
					reqConf.setConnectionRequestTimeout(connectionRequestTimeout);
				}

				final Integer connectTimeout = parseIntegerOrNull(conf, RestEasyUtils.CONNECT_TIMEOUT);
				if (connectTimeout != null) {
					reqConf.setConnectTimeout(connectTimeout);
				}

				final Integer socketTimeout = parseIntegerOrNull(conf, RestEasyUtils.SOCKET_TIMEOUT);
				if (socketTimeout != null) {
					reqConf.setSocketTimeout(socketTimeout);
				}
				httpMethod.setConfig(reqConf.build());
			}
		};
		//engine.setFollowRedirects(true);
		return engine;
	}

}
