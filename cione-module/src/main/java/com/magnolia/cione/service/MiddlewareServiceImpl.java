package com.magnolia.cione.service;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.time.DateFormatUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.AbonoQueryParamsDTO;
import com.magnolia.cione.dto.AcuerdosDTO;
import com.magnolia.cione.dto.AlbaranAudioQueryParamsDTO;
import com.magnolia.cione.dto.AudioProveedoresDTO;
import com.magnolia.cione.dto.AudioSubfamiliasDTO;
import com.magnolia.cione.dto.BonosDTO;
import com.magnolia.cione.dto.CompraMinimaDTO;
import com.magnolia.cione.dto.CompraMinimaRequestDTO;
import com.magnolia.cione.dto.CondicionesDTO;
import com.magnolia.cione.dto.ConsumosCLDTO;
import com.magnolia.cione.dto.CuotasDTO;
import com.magnolia.cione.dto.DatosComercialesDTO;
import com.magnolia.cione.dto.DeudaDTO;
import com.magnolia.cione.dto.DeudaQueryParamsDTO;
import com.magnolia.cione.dto.DeudasDTO;
import com.magnolia.cione.dto.DireccionesDTO;
import com.magnolia.cione.dto.EnvioQueryParamsDTO;
import com.magnolia.cione.dto.EurocioneDTO;
import com.magnolia.cione.dto.FormaPagoDTO;
import com.magnolia.cione.dto.GestorDTO;
import com.magnolia.cione.dto.GestoresDTO;
import com.magnolia.cione.dto.HerramientasDTO;
import com.magnolia.cione.dto.InteraccionQueryParamsDTO;
import com.magnolia.cione.dto.KpisDTO;
import com.magnolia.cione.dto.LineaAbonoDTO;
import com.magnolia.cione.dto.LineaCondicionDTO;
import com.magnolia.cione.dto.LineaEnvioDTO;
import com.magnolia.cione.dto.LineaFacturaDTO;
import com.magnolia.cione.dto.LineaPedidoDTO;
import com.magnolia.cione.dto.LineaTallerDTO;
import com.magnolia.cione.dto.LineasAbonoDTO;
import com.magnolia.cione.dto.LineasAlbaranAudioDTO;
import com.magnolia.cione.dto.LineasCondicionesDTO;
import com.magnolia.cione.dto.LineasEnvioDTO;
import com.magnolia.cione.dto.LineasFacturaDTO;
import com.magnolia.cione.dto.LineasPedidoDTO;
import com.magnolia.cione.dto.LineasTallerDTO;
import com.magnolia.cione.dto.LookticDTO;
import com.magnolia.cione.dto.LookticsDTO;
import com.magnolia.cione.dto.MotivosDTO;
import com.magnolia.cione.dto.MyShopStockArticuloDTO;
import com.magnolia.cione.dto.PaginaAbonosDTO;
import com.magnolia.cione.dto.PaginaAlbaranesAudioDTO;
import com.magnolia.cione.dto.PaginaDevolucionesDTO;
import com.magnolia.cione.dto.PaginaEnviosDTO;
import com.magnolia.cione.dto.PaginaGestionDevolucionesDTO;
import com.magnolia.cione.dto.PaginaInteraccionesDTO;
import com.magnolia.cione.dto.PaginaPedidosDTO;
import com.magnolia.cione.dto.PaginaTalleresDTO;
import com.magnolia.cione.dto.PagoPendienteDTO;
import com.magnolia.cione.dto.PagosPendientesDTO;
import com.magnolia.cione.dto.PagosPendientesQueryParamsDTO;
import com.magnolia.cione.dto.PedidoQueryParamsDTO;
import com.magnolia.cione.dto.PlanesFidelizaDTO;
import com.magnolia.cione.dto.RutaLuzDTO;
import com.magnolia.cione.dto.StockMgnlDTO;
import com.magnolia.cione.dto.TallerQueryParamsDTO;
import com.magnolia.cione.dto.TipoAlbaranDTO;
import com.magnolia.cione.dto.TransporteDTO;
import com.magnolia.cione.dto.TransportesDTO;
import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.setup.CioneEcommerceConectionProvider;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.cms.security.SecuritySupport;
import info.magnolia.cms.security.User;
import info.magnolia.cms.security.UserManager;
import info.magnolia.context.MgnlContext;

//import io.netty.handler.codec.base64.Base64Encoder;

public class MiddlewareServiceImpl implements MiddlewareService {
	
	private static final Logger log = LoggerFactory.getLogger(MiddlewareServiceImpl.class);

	@Inject
	private ConfigService configService;
	
	@Inject
	private CioneEcommerceConectionProvider cioneEcommerceConectionProvider;
	
	@Inject
	private SecuritySupport securitySupport;

	//ResteasyClient client = new ResteasyClientBuilder().build();
	//private static ResteasyClient client;
	//private static ApacheHttpClient43Engine myEngine;
	
	public MiddlewareServiceImpl() {
//		super();
//		ResteasyClientBuilder restEasyClientBuilder = new ResteasyClientBuilder(); 
//		restEasyClientBuilder = restEasyClientBuilder.connectionPoolSize(20);
//		client = restEasyClientBuilder.build();
		
	    /*PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
	    CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
	    cm.setMaxTotal(200);
	    cm.setDefaultMaxPerRoute(20);
	    ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(httpClient);*/
		
		/*PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		cm.setMaxTotal(200); // Increase max total connection to 200
		cm.setDefaultMaxPerRoute(20); // Increase default max connection per route to 20
		
		myEngine = new ApacheHttpClient43Engine(httpClient);
		log.info("create MiddlewareService instance");
		

		client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(myEngine).build();*/
		
		//https://www.baeldung.com/resteasy-client-tutorial
	}
	public void destroy() {
		System.out.println("destroy");
	}

	private String getAuth() {
		// return "Basic YXRzaXN0ZW1hczozMjM4OEIyRjE3ODg5MUNEREM5QTlEOTk1OTc1MzAzMQ==";
		String userPwd = configService.getConfig().getApiMiddlewareUser() + ":"
				+ configService.getConfig().getApiMiddlewarePwd();
		return "Basic " + Base64.getEncoder().encodeToString(userPwd.getBytes());
	}

	
	private UserERPCioneDTO getUserFromERPMiddleware(String idClient) {
		UserERPCioneDTO result = null;
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/datossocio?codSocio=" + idClient);
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		UserERPCioneDTO userERPCioneDTO = response.readEntity(UserERPCioneDTO.class);
		response.close();
		if (userERPCioneDTO.getSocio() != null && userERPCioneDTO.getCliente() != null && userERPCioneDTO.getActivo()) {
			userERPCioneDTO.setNumSocio(CioneUtils.getNumSocioFromCliente(userERPCioneDTO.getCliente()));
			result = userERPCioneDTO;
		}
		return result;
	}
	
	@Override
	public UserERPCioneDTO getUserFromERP(String idClient) {
		UserERPCioneDTO userERPCioneDTO = null;
		
		//llamada recurente, lo almacenamos en sesion
		HttpSession session = MgnlContext.getWebContext().getRequest().getSession();
		if ((session.getAttribute(MyshopConstants.ATTR_CUSTOMER_SESSION) != null) 
				&& !((UserERPCioneDTO) session.getAttribute(MyshopConstants.ATTR_CUSTOMER_SESSION)).equals("anonymous")) {
			userERPCioneDTO = (UserERPCioneDTO) session.getAttribute(MyshopConstants.ATTR_CUSTOMER_SESSION);
			
			if (!userERPCioneDTO.getCliente().equals(idClient))
				userERPCioneDTO = getUserFromERPMiddleware(idClient);
			
			
		} else {
			userERPCioneDTO = getUserFromERPMiddleware(idClient);//AQUI idClient
	    	session.setAttribute(MyshopConstants.ATTR_CUSTOMER_SESSION, userERPCioneDTO);
		}
    	return userERPCioneDTO;
    }
	
	@Override
	public UserERPCioneDTO getUserFromERPEmployee(String idUser) {
		UserERPCioneDTO empleado = new UserERPCioneDTO();
		try {
			UserManager um = securitySupport.getUserManager("public");
			User user = um.getUser(idUser);
			
			empleado.setNumSocio(idUser);
			
			String email = user.getProperty("email");
			if ((email == null) || (email.isEmpty()))
				email = idUser + "@cione.es";
			
			empleado.setEmail(email);
			empleado.setPersonaContacto(user.getProperty("title"));
			
			String idSocio = CioneUtils.getidClienteFromNumSocio(idUser);
			UserERPCioneDTO socio = getUserFromERP(idSocio);
			if (socio != null) {
				empleado.setNivelOM(socio.getNivelOM());
				empleado.setNombreComercial(socio.getNombreComercial());
			}
			empleado.setPersonaContacto(user.getProperty("title"));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return empleado;
	}

	@Override
	public List<LineaAbonoDTO> getLineasAbono(String idClient, String numAbono, String historico) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath()
				+ "/infoabono?codSocio=" + idClient + "&numAbono=" + numAbono + "&historico=" + historico);
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		LineasAbonoDTO lineasAbonoDTO = response.readEntity(LineasAbonoDTO.class);
		response.close();
		return lineasAbonoDTO.getLineasAbono();
	}

	@Override
	public PaginaAbonosDTO getAbonos(String idClient, AbonoQueryParamsDTO filter) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/abonos?codSocio=" + idClient);

		String fecIni = filter.getFechaIni();
		String fecFin = filter.getFechaFin();

		if (fecIni != null && !fecIni.isEmpty()) {
			filter.setFechaIni(CioneUtils.changeDateFormat(filter.getFechaIni()));
		}

		if (fecFin != null && !fecFin.isEmpty()) {
			filter.setFechaFin(CioneUtils.changeDateFormat(filter.getFechaFin()));
		}

		Map<String, String> params = CioneUtils.buildQueryParams(filter);

		for (String key : params.keySet()) {
			String value = params.get(key);
			target = target.queryParam(key, value);
		}

		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		PaginaAbonosDTO result = response.readEntity(PaginaAbonosDTO.class);
		response.close();
		return result;
	}

	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	@Override
	public UserERPCioneDTO getUserFromERPForLogin(String idClient) {
		UserERPCioneDTO result = null;
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/datossocio?codSocio=" + idClient);
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		result = response.readEntity(UserERPCioneDTO.class);
		response.close();
		// result.setActivo(false);
		
		return result;
	}

	@Override
	public List<LineaPedidoDTO> getLineasPedido(String idClient, String numPedido, String historico) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath()
				+ "/lineaspedido?codSocio=" + idClient + "&numPedido=" + numPedido
				+ "&historico=" + historico);
		
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		LineasPedidoDTO lineasPedidoDTO = response.readEntity(LineasPedidoDTO.class);
		response.close();
		return lineasPedidoDTO.getLineasPedido();
	}

	@Override
	public List<LineaTallerDTO> getLineasTaller(String idClient, String idServicio, String numPedido, String historico) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath()
				+ "/lineasserviciotaller?codSocio=" + idClient + "&idServicio=" + idServicio + "&numPedido=" + numPedido);
		
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		LineasTallerDTO lineasTallerDTO = response.readEntity(LineasTallerDTO.class);
		response.close();
		return lineasTallerDTO.getLineasServicioTaller();
	}
	
	@Override
	public List<LineaFacturaDTO> getLineasFactura(String idClient, String numFactura) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/lineasfactura?codSocio="
						+ idClient + "&numFactura=" + numFactura);
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		LineasFacturaDTO lineasFacturaDTO = response.readEntity(LineasFacturaDTO.class);
		response.close();
		return lineasFacturaDTO.getLineasFactura();
	}
	
	@Override
	public List<LineaCondicionDTO> getCondicionesLinea(String idClient, String linea) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/condicionesDetalle?codSocio="
						+ idClient + "&idAcuerdo=" + linea);
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		LineasCondicionesDTO lineasCondicionesDTO = response.readEntity(LineasCondicionesDTO.class);
		response.close();
		return lineasCondicionesDTO.getCondicionesDetalle();
		
		/*CondicionesDTO condicionesMock = new CondicionesDTO();
		CondicionDTO condicionMock = new CondicionDTO();
		//condicionMock.setCionesAcumulados("12344");
		condicionMock.setRappelActual("Rappel Actual");
		condicionMock.setSigRappel("Rappel Sig");
		ArrayList<CondicionDTO> list = new ArrayList<CondicionDTO>();
		list.add(condicionMock);
		
		condicionesMock.setCondiciones(list);
		
		List<CondicionesDTO> result = new ArrayList<CondicionesDTO>();
		result.add(condicionesMock);
		
		result.get(0).getCondiciones().get(0).getRappelActual();
		
		return result;*/
	}

	@Override
	public PaginaPedidosDTO getPedidos(String idClient, PedidoQueryParamsDTO filter) {
		
		log.debug("Pedidos getPedidos " + idClient);
		
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/pedidos?codSocio="
				+ idClient);

		String fecIni = filter.getFechaIni();
		String fecFin = filter.getFechaFin();

		if (fecIni != null && !fecIni.isEmpty()) {
			filter.setFechaIni(CioneUtils.changeDateFormat(filter.getFechaIni()));
		}

		if (fecFin != null && !fecFin.isEmpty()) {
			filter.setFechaFin(CioneUtils.changeDateFormat(filter.getFechaFin()));
		}

		Map<String, String> params = CioneUtils.buildQueryParams(filter);

		for (String key : params.keySet()) {
			String value = params.get(key);
			target = target.queryParam(key, value);
		}

		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		PaginaPedidosDTO result = response.readEntity(PaginaPedidosDTO.class);
		return result;
	}
	
	@Override
	public PaginaDevolucionesDTO getDevoluciones(String idClient, PedidoQueryParamsDTO filter) {
		log.debug("Pedidos getDevoluciones " + idClient);
//		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
//				.target(configService.getConfig().getApiMiddlewarePath() + "/consultadevoluciones");
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/gestiondevoluciones");
		Map<String, String> params = CioneUtils.buildQueryParams(filter);
		params.put("codSocio", idClient);
		Form form = new Form();
		for (String key : params.keySet()) {
			String value = params.get(key);
			form.param(key, value);
		}
		
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).post(Entity.form(form));
		PaginaDevolucionesDTO result = response.readEntity(PaginaDevolucionesDTO.class);
		response.close();
		result.setMotivos(getMotivos().getMotivos());
		
		return result;
	}
	
	@Override
	public PaginaDevolucionesDTO getDevolucionesAvanzadas(String idClient, PedidoQueryParamsDTO filter) {
		log.debug("Pedidos getDevolucionesAvanzadas " + idClient);
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/devoluciones-avanzadas");
		Map<String, String> params = CioneUtils.buildQueryParams(filter);
		params.put("codSocio", idClient);
		Form form = new Form();
		for (String key : params.keySet()) {
			String value = params.get(key);
			form.param(key, value);
		}
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).post(Entity.form(form));
		PaginaDevolucionesDTO result = response.readEntity(PaginaDevolucionesDTO.class);
		response.close();
		result.setMotivos(getMotivos().getMotivos());
		return result;
	}
	
	@Override
	public PaginaGestionDevolucionesDTO getGestionDevoluciones(String idClient, PedidoQueryParamsDTO filter) {
		log.debug("Pedidos getGestionDevoluciones " + idClient);
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/consultadevoluciones");
		Map<String, String> params = CioneUtils.buildQueryParams(filter);
		params.put("codSocio", idClient);
		Form form = new Form();
		for (String key : params.keySet()) {
			String value = params.get(key);
			form.param(key, value);
		}
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).post(Entity.form(form));
		PaginaGestionDevolucionesDTO result = response.readEntity(PaginaGestionDevolucionesDTO.class);
		response.close();
		return result;
	}
	
	@Override
	public MotivosDTO getMotivos() {
		MotivosDTO result = new MotivosDTO();
		log.debug("Pedidos getMotivos ");
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/motivos");
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		result = response.readEntity(MotivosDTO.class);
		response.close();
		return result;
	}
	
	@Override
	public PaginaTalleresDTO getTalleres(String idClient, TallerQueryParamsDTO filter) {
		
		log.debug("Talleres getTalleres " + idClient);
		
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/serviciostaller?codSocio=" + idClient);
		
		String fecIni = filter.getFechaIni();
		String fecFin = filter.getFechaFin();

		if (fecIni != null && !fecIni.isEmpty()) {
			filter.setFechaIni(CioneUtils.changeDateFormat(filter.getFechaIni()));
		}

		if (fecFin != null && !fecFin.isEmpty()) {
			filter.setFechaFin(CioneUtils.changeDateFormat(filter.getFechaFin()));
		} 

		Map<String, String> params = CioneUtils.buildQueryParams(filter);

		for (String key : params.keySet()) {
			String value = params.get(key);
			target = target.queryParam(key, value);
		}
		
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		PaginaTalleresDTO result = response.readEntity(PaginaTalleresDTO.class);
		return result;
	}

	@Override
	public List<LineaEnvioDTO> getLineasEnvio(String idClient, String numAlbaran, String trackingPedido) {
		/*ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/contenidoEnvio?codSocio="
						+ idClient + "&trackingPedido=" + trackingPedido + "&albaranEnvio=" + numEnvio);*/
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/contenidoEnvio?codSocio="
						+ idClient + "&trackingPedido=" + trackingPedido);
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		LineasEnvioDTO lineasEnvioDTO = response.readEntity(LineasEnvioDTO.class);
		response.close();
		
		if ((numAlbaran != null) && (!numAlbaran.isEmpty()) ) {
			LineasEnvioDTO lineasEnvioDTOAux = new LineasEnvioDTO();
			
			for (LineaEnvioDTO envio: lineasEnvioDTO.getContenidoEnvios()) {
				if (envio.getAlbaran().equals(numAlbaran)) {
					lineasEnvioDTOAux.getContenidoEnvios().add(envio);
				}
			}
			return lineasEnvioDTOAux.getContenidoEnvios();
			
		} else
			return lineasEnvioDTO.getContenidoEnvios();
	}

	@Override
	public PaginaEnviosDTO getEnvios(String idClient, EnvioQueryParamsDTO filter) {
		log.debug("Envios getEnvios " + idClient);
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/envios?codSocio="
				+ idClient);

		String fecIni = filter.getFechaIni();
		String fecFin = filter.getFechaFin();

		if (fecIni != null && !fecIni.isEmpty()) {
			filter.setFechaIni(CioneUtils.changeDateFormat(filter.getFechaIni()));
		}

		if (fecFin != null && !fecFin.isEmpty()) {
			filter.setFechaFin(CioneUtils.changeDateFormat(filter.getFechaFin()));
		}

		Map<String, String> params = CioneUtils.buildQueryParams(filter);

		for (String key : params.keySet()) {
			String value = params.get(key);
			target = target.queryParam(key, value);
		}

		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		PaginaEnviosDTO result = response.readEntity(PaginaEnviosDTO.class);
		response.close();
		return result;
	}

	@Override
	public DatosComercialesDTO getDatosComerciales(String idClient) {
		DatosComercialesDTO result = null;
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/datossociocomerciales");
		Form form = new Form().param("codSocio", idClient);
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).post(Entity.form(form));
		result = response.readEntity(DatosComercialesDTO.class);
		response.close();

		return result;
	}

	@Override
	public List<GestorDTO> getGestores(String idClient) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/datossociogestores");
		Form form = new Form().param("codSocio", idClient);
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).post(Entity.form(form));
		GestoresDTO gestores = response.readEntity(GestoresDTO.class);
		response.close();

		return gestores.getGestores();
	}

	@Override
	public CuotasDTO getCuotas(String idClient) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/datossociocuotas");
		Form form = new Form().param("codSocio", idClient);
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).post(Entity.form(form));
		CuotasDTO cuotas = response.readEntity(CuotasDTO.class);
		response.close();

		return cuotas;
	}

	@Override
	public FormaPagoDTO getFormaPago(String idClient) {
		FormaPagoDTO result = null;
		try {
			ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
					.target(configService.getConfig().getApiMiddlewarePath() + "/datossociofpago");
			Form form = new Form().param("codSocio", idClient);
			Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).post(Entity.form(form));
			result = response.readEntity(FormaPagoDTO.class);
			response.close();
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return result;
	}

	@Override
	public List<TransporteDTO> getTransportes(String idClient) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/datossociotransporte");
		Form form = new Form().param("codSocio", idClient);
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).post(Entity.form(form));
		TransportesDTO transportes = response.readEntity(TransportesDTO.class);
		response.close();

		return transportes.getTransportes();
	}

	@Override
	public List<LookticDTO> getLooktics(String idClient) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/datossociolooktic");
		Form form = new Form().param("codSocio", idClient);
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).post(Entity.form(form));
		LookticsDTO looktics = response.readEntity(LookticsDTO.class);
		response.close();

		return looktics.getLooktic();
	}

	@Override
	public RutaLuzDTO getRutaLuz(String idClient) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/datossociorutaluz");
		Form form = new Form().param("codSocio", idClient);
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).post(Entity.form(form));
		RutaLuzDTO result = response.readEntity(RutaLuzDTO.class);
		response.close();
		return result;
	}

	@Override
	public DeudasDTO getDeudas(String idClient, DeudaQueryParamsDTO filter) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/informedeuda");
		Form form = new Form().param("codSocio", idClient);
		form.param("fechaInicio", prepareDateToSendMiddleware(filter.getFechaInicio()));
		form.param("fechaFin", prepareDateToSendMiddleware(filter.getFechaFin()));
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).post(Entity.form(form));
		DeudasDTO result = response.readEntity(DeudasDTO.class);
		response.close();
		double total = 0.0;
		for (DeudaDTO deuda : result.getDeudas()) {
			total += Double.valueOf(deuda.getDeuda());
		}
		result.setTotal(total);
		return result;
	}

	@Override
	public PagosPendientesDTO getPagosPendientes(String idClient, PagosPendientesQueryParamsDTO filter) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/informepagosfactura");
		Form form = new Form().param("codSocio", idClient);
		form.param("fechaInicio", prepareDateToSendMiddleware(filter.getFechaInicio()));
		form.param("fechaFin", prepareDateToSendMiddleware(filter.getFechaFin()));
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).post(Entity.form(form));
		PagosPendientesDTO result = response.readEntity(PagosPendientesDTO.class);
		response.close();
		Date minDate = null;
		Date maxDate = null;
		// obtener fecha mínima y máxima
		for (PagoPendienteDTO pago : result.getPagosFacturas()) {
			Date fecha = CioneUtils.parseStringToDate(pago.getFecha(), "dd/MM/yyyy");
			if (minDate != null) {
				if (fecha.before(minDate)) {
					minDate = fecha;
				}
			} else {
				minDate = fecha;
			}
			if (maxDate != null) {
				if (fecha.after(maxDate)) {
					maxDate = fecha;
				}
			} else {
				maxDate = fecha;
			}
		}

		if (!CioneUtils.isEmptyOrNull(filter.getFechaInicio())) {
			result.setMinDate(filter.getFechaInicio());
		} else {
			if (minDate != null) {
				result.setMinDate(DateFormatUtils.format(minDate, CioneConstants.DATE_FORMAT_MM_YYYY));
			}
		}
		if (!CioneUtils.isEmptyOrNull(filter.getFechaFin())) {
			result.setMaxDate(filter.getFechaFin());
		} else {
			if (maxDate != null) {
				result.setMaxDate(DateFormatUtils.format(maxDate, CioneConstants.DATE_FORMAT_MM_YYYY));
			}

		}

		return result;
	}

	private String prepareDateToSendMiddleware(String date) {
		String result = null;
		if (!CioneUtils.isEmptyOrNull(date)) {
			result = CioneUtils.changeDateFormat(date, "MM-yy", "01/MM/yyyy");
		}
		return result;
	}

	@Override
	public EurocioneDTO getEurociones(String idClient) {
		return (EurocioneDTO) sendRequest(idClient, EurocioneDTO.class, "datoseurocione");
	}

	@Override
	public PlanesFidelizaDTO getPlanesFideliza(String idClient) {
		return (PlanesFidelizaDTO) sendRequest(idClient, PlanesFidelizaDTO.class, "planfideliza");
	}

	@Override
	public CondicionesDTO getCondiciones(String idClient) {
		return (CondicionesDTO) sendRequest(idClient, CondicionesDTO.class, "condiciones");
	}

	@Override
	public BonosDTO getBonos(String idClient) {
		return (BonosDTO) sendRequest(idClient, BonosDTO.class, "bonos");
	}

	@Override
	public AcuerdosDTO getAcuerdos(String idClient) {
		return (AcuerdosDTO) sendRequest(idClient, AcuerdosDTO.class, "acuerdos");
	}

	@Override
	public HerramientasDTO getHerramientas(String idClient) {
		return (HerramientasDTO) sendRequest(idClient, HerramientasDTO.class, "herramientas");
	}

	private Object sendRequest(String idClient, Class<?> classObject, String subPath) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/" + subPath);
		Form form = new Form().param("codSocio", idClient);
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).post(Entity.form(form));
		Object result = response.readEntity(classObject);
		response.close();
		return result;
	}

	@Override
	public KpisDTO getKpis() {
		KpisDTO result = null;
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/kpis");
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		result = response.readEntity(KpisDTO.class);
		response.close();
		
		//client.close();
		return result;
	}

	@Override
	public PaginaInteraccionesDTO getInteracciones(String idClient,InteraccionQueryParamsDTO filter) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/interacciones?codSocio=" + idClient);
		Map<String, String> params = CioneUtils.buildQueryParams(filter);
		for (String key : params.keySet()) {
			String value = params.get(key);
			target = target.queryParam(key, value);
		}
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		PaginaInteraccionesDTO result = response.readEntity(PaginaInteraccionesDTO.class);
		response.close();
		return result;
	}
	
	@Override
	public DireccionesDTO getDirecciones(String idClient) {
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/myshopdatostransporte");
		Form form = new Form().param("codSocio", idClient);
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).post(Entity.form(form));
		DireccionesDTO direcciones = response.readEntity(DireccionesDTO.class);
		response.close();

		return direcciones;
	}

	@Override
	public StockMgnlDTO getStockDTO(String aliasEkon) {
		
		StockMgnlDTO stockMgnlDTO = new StockMgnlDTO();
		
		try {
			
			ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
					.target(configService.getConfig().getApiMiddlewarePath() + "/myshopstockarticulo");
			Form form = new Form().param("aliasEkon", aliasEkon);
			Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).post(Entity.form(form));
			MyShopStockArticuloDTO stock = response.readEntity(MyShopStockArticuloDTO.class);
			
			String tipostock = MyshopConstants.STOCKCTRAL;
			
			if (MgnlContext.getWebContext().getRequest().getSession().getAttribute(MyshopConstants.ATTR_STOCK_SESSION) != null) {
				tipostock = MgnlContext.getWebContext().getRequest().getSession().getAttribute(MyshopConstants.ATTR_STOCK_SESSION).toString();
			}else {
				UserERPCioneDTO user = getUserFromERP(CioneUtils.getIdCurrentClientERP());
				HttpSession session = MgnlContext.getWebContext().getRequest().getSession();
				
				if (user != null &&  user.getAlmacenDefault() != null && !user.getAlmacenDefault().isEmpty() && user.getAlmacenDefault().equals(MyshopConstants.ALMACENDEFAULTCANAR)) {
					session.setAttribute(MyshopConstants.ATTR_STOCK_SESSION, MyshopConstants.STOCKCANAR);
					tipostock = MyshopConstants.STOCKCANAR;
				}else {
					session.setAttribute(MyshopConstants.ATTR_STOCK_SESSION, MyshopConstants.STOCKCTRAL);
				}
			}
			
			if ((stock != null) && (stock.getMyShopStockArticulo() != null)) {
				
				stockMgnlDTO.setAlmacen(tipostock);
				
				if (tipostock.equals(MyshopConstants.STOCKCANAR)) {
					stockMgnlDTO.setStock(stock.getMyShopStockArticulo().getStockCANAR());
				}else {
					stockMgnlDTO.setStock(stock.getMyShopStockArticulo().getStockCTRAL());
				}
				
				stockMgnlDTO.setStockCTRAL(stock.getMyShopStockArticulo().getStockCTRAL());
				stockMgnlDTO.setStockCANAR(stock.getMyShopStockArticulo().getStockCANAR());
			}
			
			response.close();
			
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		
		
		return stockMgnlDTO;
	}
	
	@Override
	public PaginaAlbaranesAudioDTO getAlbaranesAudio(AlbaranAudioQueryParamsDTO filter) {
		PaginaAlbaranesAudioDTO result = new PaginaAlbaranesAudioDTO();
		
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/audioalbaranes?codSocio="
				+ CioneUtils.getIdCurrentClientERP());

		String fecIni = filter.getFechaIni();
		String fecFin = filter.getFechaFin();

		if (fecIni != null && !fecIni.isEmpty()) {
			filter.setFechaIni(CioneUtils.changeDateFormat(filter.getFechaIni()));
		}

		if (fecFin != null && !fecFin.isEmpty()) {
			filter.setFechaFin(CioneUtils.changeDateFormat(filter.getFechaFin()));
		}

		Map<String, String> params = CioneUtils.buildQueryParams(filter);

		for (String key : params.keySet()) {
			String value = params.get(key);
			target = target.queryParam(key, value);
		}

//		HttpRequest requestWithTimeout = HttpRequest.newBuilder()
//			    .uri(originalRequest.uri()) // usando la URI del request original
//			    .timeout(Duration.ofSeconds(5)) // estableciendo el timeout
//			    .build();
		
		
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		result = response.readEntity(PaginaAlbaranesAudioDTO.class);
		
		return result;
	}
	
	@Override
	public LineasAlbaranAudioDTO getLineasAlbaranAudio(String numAlbaran) {
		LineasAlbaranAudioDTO result = new LineasAlbaranAudioDTO();
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/audioalbarandetalle?codSocio=" 
						+ CioneUtils.getIdCurrentClientERP() + "&numAlbaran=" + numAlbaran);
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		result = response.readEntity(LineasAlbaranAudioDTO.class);
		
		response.close();
		return result;
	}
	
	@Override
	public AudioSubfamiliasDTO getAudioSubfamilias() {
		AudioSubfamiliasDTO result = new AudioSubfamiliasDTO();
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/audiosubfamilias");
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		result = response.readEntity(AudioSubfamiliasDTO.class);
		
		response.close();
		return result;
	}
	
	@Override
	public AudioProveedoresDTO getProveedores() {
		AudioProveedoresDTO result = new AudioProveedoresDTO();
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/audioproveedores");
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		result = response.readEntity(AudioProveedoresDTO.class);
		
		response.close();
		return result;
	}
	
	@Override
	public TipoAlbaranDTO getTipoAlbaran() {
		TipoAlbaranDTO result = new TipoAlbaranDTO();
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/listipoalbaran");
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		result = response.readEntity(TipoAlbaranDTO.class);
		
		response.close();
		return result;
	}

	@Override
	public float getStock(String aliasEkon) {
		
		float unidades = 0;
		
		try {
			ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
					.target(configService.getConfig().getApiMiddlewarePath() + "/myshopstockarticulo");
			Form form = new Form().param("aliasEkon", aliasEkon);
			Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).post(Entity.form(form));
			MyShopStockArticuloDTO stock = response.readEntity(MyShopStockArticuloDTO.class);
			
			String tipostock = MyshopConstants.STOCKCTRAL;
			
			if (MgnlContext.getWebContext().getRequest().getSession().getAttribute(MyshopConstants.ATTR_STOCK_SESSION) != null) {
				tipostock = MgnlContext.getWebContext().getRequest().getSession().getAttribute(MyshopConstants.ATTR_STOCK_SESSION).toString();
			}else {
				UserERPCioneDTO user = getUserFromERP(CioneUtils.getIdCurrentClientERP());
				HttpSession session = MgnlContext.getWebContext().getRequest().getSession();
				
				if (user != null &&  user.getAlmacenDefault() != null && !user.getAlmacenDefault().isEmpty() && user.getAlmacenDefault().equals(MyshopConstants.ALMACENDEFAULTCANAR)) {
					session.setAttribute(MyshopConstants.ATTR_STOCK_SESSION, MyshopConstants.STOCKCANAR);
				}else {
					session.setAttribute(MyshopConstants.ATTR_STOCK_SESSION, MyshopConstants.STOCKCTRAL);
				}
			}
			
			if ((stock != null) && (stock.getMyShopStockArticulo() != null)) {
				if (tipostock.equals(MyshopConstants.STOCKCANAR)) {
					unidades = stock.getMyShopStockArticulo().getStockCANAR();
				}else {
					unidades = stock.getMyShopStockArticulo().getStockCTRAL();
				}
			}
			
			response.close();
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		return unidades;
	}
	
	@Override
	public ConsumosCLDTO getConsumosCioneLovers(String idClient) {
		
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/consumocionelovers?codSocio=" + idClient);
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		
		ConsumosCLDTO consumosCLDTO = response.readEntity(ConsumosCLDTO.class);
		
		response.close();
		
		return consumosCLDTO;
	}
	
	@Override
	public ConsumosCLDTO getConsumosCioneLoversFilterByGroupTwo(String idClient, String groupname) {
		
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/consumocionelovers?codSocio=" + idClient);
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).get();
		
		ConsumosCLDTO consumosCLDTO = response.readEntity(ConsumosCLDTO.class);
		
		response.close();
		
		ConsumosCLDTO consumosCLDTOres = new ConsumosCLDTO();
		
		consumosCLDTOres.setConsumosCL(consumosCLDTO.getByGroupTwo(groupname));
		
		return consumosCLDTOres;
	}
	
	@Override
	public CompraMinimaDTO getCompraMinima(String codSocio, String grupoPrecio, String marca, String proveedor, String familia) {
		
		CompraMinimaRequestDTO compraMinimaRequestDTO = new CompraMinimaRequestDTO(codSocio, grupoPrecio, marca, proveedor, familia);
		ResteasyWebTarget target = cioneEcommerceConectionProvider.getRestEasyClient()
				.target(configService.getConfig().getApiMiddlewarePath() + "/getCompraMinima");
		Response response = target.request().header(HttpHeaders.AUTHORIZATION, getAuth()).post(Entity.json(compraMinimaRequestDTO));
		CompraMinimaDTO compraMinimaDTO = response.readEntity(CompraMinimaDTO.class);
		response.close();
		
		return compraMinimaDTO;		
	}
	
}