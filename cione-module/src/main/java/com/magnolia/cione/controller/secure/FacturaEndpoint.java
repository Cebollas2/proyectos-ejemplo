package com.magnolia.cione.controller.secure;

import java.io.File;
import java.util.List;
import java.util.StringTokenizer;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

import com.magnolia.cione.dto.FacturaDocQueryParamsDTO;
import com.magnolia.cione.dto.FacturaQueryParamsDTO;
import com.magnolia.cione.dto.LineaCondicionDTO;
import com.magnolia.cione.dto.LineaFacturaDTO;
import com.magnolia.cione.dto.PaginaFacturaDocDTO;
import com.magnolia.cione.dto.PaginaFacturasDTO;
import com.magnolia.cione.service.ConfigService;
import com.magnolia.cione.service.FacturaService;
import com.magnolia.cione.service.MiddlewareService;
import com.magnolia.cione.service.PDFGeneratorService;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;

//@Api(value = "/private/facturas/v1")
@Path("/private/facturas/v1")
public class FacturaEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {
	
	private static final Logger log = LoggerFactory.getLogger(FacturaEndpoint.class);
	
	@Inject
	private FacturaService facturaService;
	
	@Inject
	private ConfigService configService;
	
	@Inject
	private PDFGeneratorService pdfGeneratorService;
	
	@Inject
	private MiddlewareService middlewareService;
	
	@Inject
	protected FacturaEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}
	
	@POST
	@Path("/otros-documentos")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getFacturaDocs(FacturaDocQueryParamsDTO facturaDocQueryParamsDTO) {
		try {
			PaginaFacturaDocDTO paginaFacturas = facturaService.getDocumentos(facturaDocQueryParamsDTO);
			if (paginaFacturas!= null && paginaFacturas.getDocumentos() != null && paginaFacturas.getDocumentos().size()>1) {
				log.debug(paginaFacturas.getDocumentos().get(0).getNombreDoc());
			}
			return Response.ok(paginaFacturas).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	@GET
	@Path("/pdf/{fileName}")
	@Produces("application/pdf")
	public Response getFacturaDocsPDF(@PathParam("fileName") String fileName) {
		try {
			File file = facturaService.getFileInPDFFormat(fileName);
			
			if (file != null) {
				ResponseBuilder response = Response.ok((Object) file);
				response.header("Content-Disposition", "attachment; filename=\""+ fileName + "\"");
				
				return response.build();
			} else {			
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
			
		}		
	}
	
	
	@GET
	@Path("/pdfEnvios")
	@Produces("application/pdf")
	public Response getPDFEnvios(@QueryParam("trackingPedido") String trackingPedido, 
			@QueryParam("agencia") String agencia,
			@QueryParam("tipoEnvio") String tipoEnvio){
		try {
			File file = pdfGeneratorService.getFileSending(trackingPedido, agencia, tipoEnvio);
			if ((file != null) && file.exists()) {
				ResponseBuilder response = Response.ok((Object) file);
				response.header("Content-Disposition", "attachment; filename=\""+ file.getName() + "\"");
				return response.build();
			} else {	
				return Response.status(Response.Status.NOT_FOUND).entity("El documento no esta disponible").build();
			}
		
		}catch(Exception e) {
			log.error(e.getMessage());			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	
	@POST
	@Path("/facturas")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getFacturas(@Valid FacturaQueryParamsDTO facturaQueryParamsDTO) {
		try {
			PaginaFacturasDTO paginaFacturas = facturaService.getFacturas(facturaQueryParamsDTO);
			return Response.ok(paginaFacturas).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	@POST
	@Path("/info-factura")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getLineasFactura(@QueryParam("numFactura") String numFactura){
		try {
			List<LineaFacturaDTO> lineasFactura = facturaService.getLineasFactura(numFactura);
			ResponseBuilder rb= Response.ok(lineasFactura);
			Response response = rb.build();
			return response;
		}catch(Exception e) {
			log.error(e.getMessage());			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/info-condiciones")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getLineasCondiciones(@QueryParam("linea") String linea){
		try {
			List<LineaCondicionDTO> condiciones = facturaService.getLineasCondiciones(linea);
			ResponseBuilder rb= Response.ok(condiciones);
			Response response = rb.build();
			return response;
		}catch(Exception e) {
			log.error(e.getMessage());			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/pdf")
	@Produces("application/pdf")
	public Response getFacturaPDF(@QueryParam("url") String url) {
		try {
			StringTokenizer st = new StringTokenizer(url,"/");
			boolean flag=false;
			while (st.hasMoreElements() && !flag) {
				if (st.nextToken().equals(CioneUtils.getIdCurrentClientERP())){
					flag = true;
				}
			}
			if (flag) {
				File file = new File(configService.getConfig().getPathFacturas() +url);
				if ((file != null) && file.exists()) {
					ResponseBuilder response = Response.ok((Object) file);
					response.header("Content-Disposition", "attachment; filename=\""+ file.getName() + "\"");
					return response.build();
				} else {	
					return Response.status(Response.Status.NOT_FOUND).entity("El documento no esta disponible").build();
				}
			} else {
				log.error("El usuario intenta consultar un documento que no le pertenece");
				return Response.status(Response.Status.FORBIDDEN).entity("No tiene permisos para descargar el pdf").build();
			}
		
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	@GET
	@Path("/checkPdf")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getcheckfactura(@QueryParam("url") String url) {
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

				File file = new File(configService.getConfig().getPathFacturas() +url);
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
	@Path("/pdfDocumentos")
	@Produces("application/pdf")
	public Response getDocumentoPDF(@QueryParam("url") String url) {
		try {
			boolean flag = false;
			if (url.contains(CioneUtils.getIdCurrentClientERP()) || url.contains(middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP()).getNif())){
				flag = true;
			}
			if (flag) {
				File file = new File(url);
				if ((file != null) && file.exists()) {
					ResponseBuilder response = Response.ok((Object) file);
					response.header("Content-Disposition", "attachment; filename=\""+ file.getName() + "\"");
					return response.build();
				} else {	
					return Response.status(Response.Status.NOT_FOUND).entity("El documento no esta disponible").build();
				}
			} else {
				log.error("El usuario intenta consultar un documento que no le pertenece");
				return Response.status(Response.Status.FORBIDDEN).entity("No tiene permisos para descargar el pdf").build();
			}
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	@GET
	@Path("/checkPdfDocumentos")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getcheckDocumentos(@QueryParam("url") String url) {
		try {
			boolean flag = false;
			if (url.contains(CioneUtils.getIdCurrentClientERP()) || url.contains(middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP()).getNif())){
				flag = true;
			}
			if (flag) {
				File file = new File(url);
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
	
	private ResponseBuilder generateResponseBuilder(String res, Status status) {

		return Response.status(status)
				.type(MediaType.APPLICATION_JSON + "; charset=utf-8")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8"))
				.header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
				.header(HttpHeaders.EXPIRES, "0")
				.entity(res);

	}
}
