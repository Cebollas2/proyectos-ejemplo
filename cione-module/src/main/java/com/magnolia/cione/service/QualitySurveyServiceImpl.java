package com.magnolia.cione.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.InvalidQueryException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.time.DateFormatUtils;
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
import com.magnolia.cione.dto.QualitySurveyDTO;
import com.magnolia.cione.dto.QualitySurveyQueryParamsDTO;
import com.magnolia.cione.exceptions.CioneException;
import com.magnolia.cione.exceptions.CioneRuntimeException;
import com.magnolia.cione.pur.CommandsUtils;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.cms.util.QueryUtil;
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.PropertyUtil;

public class QualitySurveyServiceImpl implements QualitySurveyService {

	private static final Logger log = LoggerFactory.getLogger(QualitySurveyServiceImpl.class);

	private static final String FIELD_COD_SOCIO = "codSocio";
	private static final String FIELD_SOCIO = "socio";
	private static final String FIELD_PUNTUACION = "puntuacion";
	private static final String FIELD_COMENTARIO = "comentario";
	private static final String FIELD_PREGUNTA = "pregunta";
	private static final String FIELD_FECHA = "fecha";

	private static final String QUALITY_SURVEY_WS = "quality-surveys";
	private static final String QUALITY_SURVEY_NODETYPE = "quality-survey";
	
	@Inject
	private ConfigService configService;
	
	@Inject
	private CommandsUtils commandsUtils;
	
	ResteasyClient client;
	
	public QualitySurveyServiceImpl() {
		//client = (ResteasyClient)ClientBuilder.newClient();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		cm.setMaxTotal(200); // Increase max total connection to 200
		cm.setDefaultMaxPerRoute(20); // Increase default max connection per route to 20
		ApacheHttpClient43Engine myEngine = new ApacheHttpClient43Engine(httpClient);
		client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(myEngine).build();
	}

	@Override
	public boolean doQuestion() throws InvalidQueryException, RepositoryException {
		return !existSurveyForCurrentMonth();
	}

	private boolean existSurveyForCurrentMonth() throws InvalidQueryException, RepositoryException {
		boolean result = false;

		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);

		Date firstDayOfMonth = new Date();
		firstDayOfMonth.setDate(c.getActualMinimum(Calendar.DAY_OF_MONTH));
		Date lastDayOfMonth = new Date();
		lastDayOfMonth.setDate(c.getActualMaximum(Calendar.DAY_OF_MONTH));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00.000'Z'");
		SimpleDateFormat sdfFin = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59.999'Z'");

		/*select * from [quality-survey] where [codSocio] = '000260100' 
			    and [fecha] >= CAST('2023-04-01T00:00:00.000Z' AS DATE) 
			    and [fecha] <= CAST('2023-04-30T23:59:59.999Z' AS DATE)*/
		
		String query = "select * from [quality-survey] where";
		query += " [codSocio] = '" + MgnlContext.getUser().getName() + "'";
		query += " and [fecha] >= CAST('" + sdf.format(firstDayOfMonth) + "' AS DATE)";
		query += " and [fecha] <= CAST('" + sdfFin.format(lastDayOfMonth) + "' AS DATE)";
		NodeIterator resultQuery = QueryUtil.search(QUALITY_SURVEY_WS, query);
		while (resultQuery.hasNext()) {
			result = true;
			break;
		}

		return result;
	}

	@Override
	public void saveSurvey(QualitySurveyDTO dto) {
		if(configService.getConfig().getIsAuthor()) {
			log.info("SOY AUTHOR INSTANCIA: guardo los datos");
			saveSurveyLocal(dto);			
		}else {
			log.info("SOY PUBLIC INSTANCIA: envio la noticia al author");
			sendToAuthorSaveSurvey(dto);
		}		
	}

	private void saveSurveyLocal(QualitySurveyDTO dto){
		try {	
			if(existSurveyForCurrentMonth()) {
				throw new CioneRuntimeException("Encuesta existente para este mes");
			} 
			
			long tiempoInicio = System.currentTimeMillis();
			Session session = MgnlContext.getJCRSession(QUALITY_SURVEY_WS);
			
			LocalDate date = LocalDate.now(); 
			String month = String.valueOf(date.getMonth().getValue());
			String year = String.valueOf(date.getYear());
			String idUser = dto.getCodSocio().substring(0, 2);
			String pathYear = year;
			String path = "";
			
			Node nodeQuality = session.getRootNode();
			
			if (!nodeQuality.hasNode(year)) {
				nodeQuality.addNode(year, CioneConstants.CONTENT_FOLDER_TYPE);
				nodeQuality.getSession().save();
				path = nodeQuality.getNode(year).getPath();
			}
			nodeQuality = nodeQuality.getNode(pathYear);
			
			//String pathMonth = pathYear + "/" +month;
			if (!nodeQuality.hasNode(month)) {
				nodeQuality.addNode(month, CioneConstants.CONTENT_FOLDER_TYPE);
				nodeQuality.getSession().save();
				if (path.isEmpty())
					path = nodeQuality.getNode(month).getPath();
			}
			
			nodeQuality = nodeQuality.getNode(month);
			
			//String pathUser = pathMonth + "/" + idUser;
			if (!nodeQuality.hasNode(idUser)) {
				nodeQuality.addNode(idUser, CioneConstants.CONTENT_FOLDER_TYPE);
				nodeQuality.getSession().save();
				if (path.isEmpty())
					path = nodeQuality.getNode(idUser).getPath();
			}
			
			nodeQuality = nodeQuality.getNode(idUser);
			if (path.isEmpty())
				path = nodeQuality.getPath();
			//Node node = JcrUtils.getOrAddNode(session.getNode("/"), "survey_" + new Date().getTime() , QUALITY_SURVEY_NODETYPE);
			Node node = JcrUtils.getOrAddNode(nodeQuality, "survey_" + dto.getCodSocio() + "_" + new Date().getTime() , QUALITY_SURVEY_NODETYPE);
			node.setProperty(FIELD_COD_SOCIO, dto.getCodSocio());
			node.setProperty(FIELD_SOCIO, dto.getSocio());	
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			node.setProperty(FIELD_PREGUNTA, dto.getPregunta());
			node.setProperty(FIELD_FECHA, calendar);									
			node.setProperty(FIELD_PUNTUACION, dto.getPuntuacion());
			node.setProperty(FIELD_COMENTARIO, dto.getComentario());
			node.getSession().save();	
			//commandsUtils.publishNodeWithoutWorkflow(node.getPath(), QUALITY_SURVEY_WS ,true);
			commandsUtils.publishNodeWithoutWorkflow(path, QUALITY_SURVEY_WS ,true);
			long tiempoFin = System.currentTimeMillis();
			log.info("Ha tardado = " + (tiempoFin - tiempoInicio));
		}catch(Exception e) {
			log.error(e.getMessage(), e);
			throw new CioneRuntimeException("No se ha podido acceder al workspace");
		}		
	}
	
	public void unpublishSurveyLocal(){
		try {	
			String query = "select * from [quality-survey] where [fecha] >= CAST('2019-01-01T00:00:00.000Z' AS DATE) and [fecha] <= CAST('2019-12-31T23:59:59.999Z' AS DATE)";
			//query += " and [fecha] >= CAST('2020-12-01T00:00:00.000Z' AS DATE)";
			//query += " and [fecha] <= CAST('2020-12-31T23:59:59.999Z' AS DATE)";
			NodeIterator resultQuery = QueryUtil.search(QUALITY_SURVEY_WS, query);
			while (resultQuery.hasNext()) {
				Node node = resultQuery.nextNode();
				commandsUtils.unpublishNodeWithoutWorkflow(node.getPath(), QUALITY_SURVEY_WS);
			}

		}catch(Exception e) {
			log.error(e.getMessage(), e);
			throw new CioneRuntimeException("No se ha podido acceder al workspace");
		}		
	}
	
	public void deleteSurveyLocal(){
		try {	
			String query = "select * from [quality-survey] where [codSocio] = '012790000' and [fecha] >= CAST('2019-01-01T00:00:00.000Z' AS DATE) and [fecha] <= CAST('2019-12-31T23:59:59.999Z' AS DATE)";
			NodeIterator resultQuery = QueryUtil.search(QUALITY_SURVEY_WS, query);
			while (resultQuery.hasNext()) {
				Node node = resultQuery.nextNode();
				commandsUtils.deleteNodeWithoutWorkflow(node.getPath(), QUALITY_SURVEY_WS);
			}

		}catch(Exception e) {
			log.error(e.getMessage(), e);
			throw new CioneRuntimeException("No se ha podido acceder al workspace");
		}		
	}
	
	
	private void sendToAuthorSaveSurvey(QualitySurveyDTO dto){
		ResteasyWebTarget target = client
				.target(configService.getConfig().getAuthAuthorPath() + "/.rest/private/quality-surveys/v1/save")				
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
	public List<QualitySurveyDTO> search(QualitySurveyQueryParamsDTO params) {
		 
		List<QualitySurveyDTO> result = new ArrayList<>();
		try {
			String query = "select * from [quality-survey] where codSocio is not null";
			
			if (params.getSocio() != null && !params.getSocio().isEmpty()) {
				query += String.format(" and (LOWER(" + FIELD_SOCIO +") like '%s'", "%" + params.getSocio().toLowerCase() + "%");
				query += String.format(" or LOWER(" + FIELD_COD_SOCIO +") like '%s')", "%" + params.getSocio().toLowerCase() + "%");
			}
			
			if (params.getPuntuacion() != null) {
				query += String.format(" and " + FIELD_PUNTUACION + " = '%s'", params.getPuntuacion());
			}
			
			Date fechaDesde = CioneUtils.parseStringToDate(params.getFechaDesde(), "MM-yyyy");
			Date fechaHasta = CioneUtils.parseStringToDate(params.getFechaHasta(), "MM-yyyy");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00.000'Z'");
			if (fechaDesde != null) {
				query += " and [fecha] >= CAST('" + sdf.format(fechaDesde) + "' AS DATE)";
			}
			
			if (fechaHasta != null) {
				Calendar c = Calendar.getInstance();
				c.setTime(fechaHasta);
				int lastDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
				fechaHasta.setDate(lastDayOfMonth);
				query += " and [fecha] <= CAST('" + sdf.format(fechaHasta) + "' AS DATE)";
			}
			
			query += " order by LOWER(socio),fecha";

			Session session = MgnlContext.getJCRSession (QUALITY_SURVEY_WS);
			Query q = session.getWorkspace().getQueryManager().createQuery(query, javax.jcr.query.Query.JCR_SQL2);
			q.setLimit(params.getSize());
			q.setOffset(params.getPagina());
			QueryResult queryResult = q.execute();
			NodeIterator nodes = queryResult.getNodes();
			 while (nodes.hasNext()) {
			    Node node = nodes.nextNode();			     
				QualitySurveyDTO dto = new QualitySurveyDTO();
				dto.setCodSocio(PropertyUtil.getString(node, FIELD_COD_SOCIO));
				dto.setSocio(PropertyUtil.getString(node, FIELD_SOCIO));
				dto.setComentario(PropertyUtil.getString(node, FIELD_COMENTARIO));				
				dto.setPuntuacion(PropertyUtil.getString(node, FIELD_PUNTUACION));
				Calendar date = PropertyUtil.getDate(node,FIELD_FECHA);
				dto.setFecha(DateFormatUtils.format(date.getTime(), CioneConstants.DATE_FORMAT_MM_YYYY));				
				result.add(dto);
			 }

		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new CioneRuntimeException("Error al realizar consulta");
			
		}
		return result;
	}
	
	
}
