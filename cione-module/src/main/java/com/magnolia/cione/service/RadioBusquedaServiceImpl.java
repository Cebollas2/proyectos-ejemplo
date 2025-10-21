package com.magnolia.cione.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.InvalidQueryException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.jackrabbit.commons.JcrUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient43Engine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.dto.RadioBusquedaDTO;
import com.magnolia.cione.exceptions.CioneException;
import com.magnolia.cione.exceptions.CioneRuntimeException;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.context.MgnlContext;
import info.magnolia.dam.templating.functions.DamTemplatingFunctions;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.module.mail.MailTemplate;

public class RadioBusquedaServiceImpl implements RadioBusquedaService{
	
	private static final Logger log = LoggerFactory.getLogger(RadioBusquedaServiceImpl.class);
	
	private static final String FIELD_TITLE = "titulo";
	private static final String FIELD_TEXT = "texto";
	private static final String FIELD_DATE = "fecha";
	private static final String FIELD_EMAIL = "email";
	private static final String RADIO_BUSQUEDA_WS = "radiobusquedas";
	private static final String RADIO_BUSQUEDA_NODETYPE = "radiobusqueda";
	
	@Inject
	private ConfigService configService;
	
	@Inject
	private EmailService emailService;
	
	
	@Inject
	private DamTemplatingFunctions damTemplatingFunctions;
	
	
	ResteasyClient client;

	
	public RadioBusquedaServiceImpl() {
		//client = (ResteasyClient)ClientBuilder.newClient();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		cm.setMaxTotal(200); // Increase max total connection to 200
		cm.setDefaultMaxPerRoute(20); // Increase default max connection per route to 20
		ApacheHttpClient43Engine myEngine = new ApacheHttpClient43Engine(httpClient);
		client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(myEngine).build();
	}

	
	
	@Override
	public void saveRadioBusqueda(RadioBusquedaDTO dto) {
		if(configService.getConfig().getIsAuthor()) {
			log.info("SOY AUTHOR INSTANCIA: guardo los datos");
			saveRadioBusquedaLocal(dto);			
		}else {
			log.info("SOY PUBLIC INSTANCIA: envio la noticia al author");
			sendToAuthorSaveRadioBusqueda(dto);
		}		
	}
	
	private void saveRadioBusquedaLocal(RadioBusquedaDTO dto){
		try {			
			Session session = MgnlContext.getJCRSession(RADIO_BUSQUEDA_WS);
			Node node = JcrUtils.getOrAddNode(session.getNode("/"), "anuncio_" + new Date().getTime() , RADIO_BUSQUEDA_NODETYPE);
			node.setProperty(FIELD_TITLE, dto.getTitulo());
			node.setProperty(FIELD_TEXT, dto.getTexto());	
			Date date =  CioneUtils.parseStringToDate(dto.getFecha(), "dd-mm-yyyy");			
			if(date != null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				node.setProperty(FIELD_DATE, calendar);
			}			
			node.setProperty(FIELD_EMAIL, dto.getEmail());			
			node.getSession().save();			
			if(dto.getTo()==null || dto.getTo().isEmpty()) {
				dto.setTo(CioneConstants.DEFAULT_ADMIN_MAIL);
			}
			log.info("Envio de mail " + dto.getTo());
			//emailService.sendEmail("Nuevo anuncio", String.format("Se ha añadido un nuevo anuncio de %s", dto.getEmail()), "hjmendez@atsistemas.com");
			emailService.sendTemplateEmail("Radio Búsquedas -  MyCione", dto.getTo(),
					buildTemplateEmailForRadioBusquedas(dto), null);
		}catch(Exception e) {
			throw new CioneRuntimeException("No se ha podido acceder al workspace");
		}		
	}
	
	
	private void sendToAuthorSaveRadioBusqueda(RadioBusquedaDTO dto){
		ResteasyWebTarget target = client
				.target(configService.getConfig().getAuthAuthorPath() + "/.rest/private/radio-busqueda/v1/radiobusquedas")				
				.queryParam("lang", MgnlContext.getLocale());
		log.info(String.format("enviando peticion: %s", target.getUri()));		
		Response response = null;
		try {			
			response = target.request().header(HttpHeaders.AUTHORIZATION, CioneUtils.getAuthToSync())
					.post(Entity.json(dto));
			String stringResponse = response.readEntity(String.class);
			if(stringResponse == null || !stringResponse.equals("true")) {
				throw  new CioneException(stringResponse);
			}			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CioneRuntimeException(e.getMessage());
		} finally {
			if(response != null) {
				response.close();
			}
		}		
	}

	@Override
	//TODO: optimize
	public long getTotalAnuncios() throws InvalidQueryException, RepositoryException {
		Session session = MgnlContext.getJCRSession(RADIO_BUSQUEDA_WS);
		Node node = session.getNode("/");
		Iterable<Node> childrenIterable = NodeUtil.getNodes(node, RADIO_BUSQUEDA_NODETYPE);//				
		List<Node> childrenList = NodeUtil.asList(childrenIterable);
		/*
		for (Node childNode : childrenList) {
			System.out.println(childNode.getName());
		}*/
		return childrenList.size();		
	}
	
	
	private Map<String, Object> buildTemplateEmailForRadioBusquedas(RadioBusquedaDTO dto) {
		Map<String, Object> templateValues = new HashMap<>();

		templateValues.put(MailTemplate.MAIL_TEMPLATE_FILE, "cione-module/templates/mail/radio-busquedas-mail.ftl");	
		templateValues.put("logoHeader", getImageLink("/cione/templates/emails/logo-header.png"));
		templateValues.put("titulo", "Radio Búsquedas");
		templateValues.put("texto", String.format("Se ha añadido un nuevo anuncio de %s", dto.getEmail()));

		return templateValues;

	}
	
	
	private String getImageLink(String path) {
		String link = "";
		if (damTemplatingFunctions.getAsset("jcr", path) != null) {
			link = CioneUtils.getURLHttps() + damTemplatingFunctions.getAsset("jcr", path).getLink();
		}
		return link;
	}
	

	

}
