package com.magnolia.cione.controller.secure;

import java.io.File;
import java.util.List;
import java.util.StringTokenizer;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.AlbaranAudioQueryParamsDTO;
import com.magnolia.cione.dto.AlbaranQueryParamsDTO;
import com.magnolia.cione.dto.AudioProveedoresDTO;
import com.magnolia.cione.dto.AudioSubfamiliasDTO;
import com.magnolia.cione.dto.LineaAlbaranDTO;
import com.magnolia.cione.dto.LineasAlbaranAudioDTO;
import com.magnolia.cione.dto.PaginaAlbaranesAudioDTO;
import com.magnolia.cione.dto.PaginaAlbaranesDTO;
import com.magnolia.cione.dto.TipoAlbaranDTO;
import com.magnolia.cione.service.AlbaranService;
import com.magnolia.cione.service.ConfigService;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;


//@Api(value = "/private/albaranes/v1")
@Path("/private/albaranes/v1")
public class AlbaranEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(AlbaranEndpoint.class);
	
	@Inject
	private ConfigService configService;
	
	@Inject
	private AlbaranService albaranService;

	@Inject
	protected AlbaranEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	@POST
	@Path("/albaranes")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getAlbaranes(@Valid AlbaranQueryParamsDTO albaranQueryParamsDTO) {
		try {
			PaginaAlbaranesDTO paginaAlbaranes = albaranService.getAlbaranes(albaranQueryParamsDTO);
			return Response.ok(paginaAlbaranes).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	
	@GET
	@Path("/info-albaran")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getLineasAlbaran(@QueryParam("numAlbaran") String numAlbaran){
		try {
			List<LineaAlbaranDTO> lineasAlbaran = albaranService.getLineasAlbaran(numAlbaran);
			
			ResponseBuilder rb= Response.ok(lineasAlbaran);
			Response response = rb.build();
			
			return response;
		}catch(Exception e) {
			log.error(e.getMessage());			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/checkPdf")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getcheckalbaran(@QueryParam("url") String url) {
		try {
			StringTokenizer st = new StringTokenizer(url,"/");
			boolean flag=false;
			while (st.hasMoreElements() && !flag) {
				if (st.nextToken().equals(CioneUtils.getIdCurrentClientERP())){
					flag = true;
				}
				
			}
			if (flag) {
				//long startTime = System.currentTimeMillis();

				File file = new File(configService.getConfig().getPathAlbaranes() +url);
				//File file = albaranService.getDocAlbaran(configService.getConfig().getPathAlbaranes() +url); //llamada a traves del servicio rest
				
				if ((file != null) && file.exists()){
					JSONObject jsonRes = new JSONObject();
					jsonRes.put("respuesta", "ok");
					return generateResponseBuilder(jsonRes.toString(), Response.Status.OK).build();
				} else {			
					return Response.status(Response.Status.NOT_FOUND).entity("Documento no disponible, contacte con el SAS por favor").build();
				}
				

			} else {
				log.error("El usuario intenta consultar un documento que no le pertenece");
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No tiene permisos para descargar el pdf").build();
			}
		
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	@GET
	@Path("/pdf")
	@Produces("application/pdf")
	public Response getAlbaranDocsPDF(@QueryParam("url") String url) {
		try {
			StringTokenizer st = new StringTokenizer(url,"/");
			boolean flag=false;
			while (st.hasMoreElements() && !flag) {
				if (st.nextToken().equals(CioneUtils.getIdCurrentClientERP())){
					flag = true;
				}
				
			}
			if (flag) {
				//long startTime = System.currentTimeMillis();

				File file = new File(configService.getConfig().getPathAlbaranes() +url);
				//File file = albaranService.getDocAlbaran(configService.getConfig().getPathAlbaranes() +url); //llamada a traves del servicio rest
				
				if ((file != null) && file.exists()){
					ResponseBuilder response = Response.ok((Object) file);
					response.header("Content-Disposition", "attachment; filename=\""+ file.getName() + "\"");
					//long endTime = System.currentTimeMillis();
					//long total = endTime - startTime;
					//log.debug("Tiempo " + String.valueOf(total));
					return response.build();
				} else {			
					return Response.status(Response.Status.NOT_FOUND).entity("El documento no esta disponible").build();
				}
				

			} else {
				log.error("El usuario intenta consultar un documento que no le pertenece");
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No tiene permisos para descargar el pdf").build();
			}
		
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	private ResponseBuilder generateResponseBuilder(String res, Status status) {

		return Response.status(status)
				.type(MediaType.APPLICATION_JSON + "; charset=utf-8")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8"))
				.header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
				.header(HttpHeaders.EXPIRES, "0")
				.entity(res);

	}
	
	@POST
	@Path("/albaranesaudio")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getAlbaranesAudio(@Valid AlbaranAudioQueryParamsDTO albaranAudioQueryParamsDTO) {
		try {
			PaginaAlbaranesAudioDTO paginaAlbaranes = albaranService.getAlbaranesAudio(albaranAudioQueryParamsDTO);
			//int numReg = (20 * paginaAlbaranes.getUltimaPagina());
			//paginaAlbaranes.setNumRegistros(numReg);
			return Response.ok(paginaAlbaranes).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	
	@GET
	@Path("/albaranaudioinfo")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getLineasAlbaranAudio(@QueryParam("numAlbaran") String numAlbaran){
		try {
			LineasAlbaranAudioDTO lineasAlbaranaudio = albaranService.getLineasAlbaranAudio(numAlbaran);
			
			ResponseBuilder rb= Response.ok(lineasAlbaranaudio);
			Response response = rb.build();
			
			return response;
		}catch(Exception e) {
			log.error(e.getMessage());			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/audiosubfamilias")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getAudioSubfamilias() {
		try {
			AudioSubfamiliasDTO audiosubfamilias = albaranService.getAudioSubfamilias();
			return Response.ok(audiosubfamilias).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	
	@GET
	@Path("/audioproveedores")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getAudioProveedores(){
		try {
			AudioProveedoresDTO audioproveedores = albaranService.getAudioProveedores();
			return Response.ok(audioproveedores).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
	}
	
	@GET
	@Path("/listipoalbaran")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getLisTipoAlbaran(){
		try {
			TipoAlbaranDTO listipoalbaran = albaranService.getTipoAlbaran();
			return Response.ok(listipoalbaran).build();
		}catch(Exception e) {
			log.error(e.getMessage());			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	

}
