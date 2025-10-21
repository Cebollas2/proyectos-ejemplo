package com.magnolia.cione.controller.secure;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.dto.MisConsumosDTO;
import com.magnolia.cione.dto.MisConsumosQueryParamsDTO;
import com.magnolia.cione.service.ExportService;
import com.magnolia.cione.service.MisConsumosService;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.context.MgnlContext;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;


//@Api(value = "/private/export/v1")
@Path("/private/export/v1")
public class ExportEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(ExportEndpoint.class);
	
	@Inject
	private MisConsumosService misConsumosService;	
	
	@Inject
	private ExportService exportService;

	@Inject
	protected ExportEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	@POST
	@Path("/xls")
	@Consumes("application/x-www-form-urlencoded")	
	@Produces("application/vnd.ms-excel")
	public Response export(@FormParam("export-data") String data) {
		try {			
			ObjectMapper mapper = new ObjectMapper();
			ArrayList list = mapper.readValue(data, ArrayList.class);			
			HSSFWorkbook xls = exportService.exportToExcel(list);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			xls.write(baos);
			ResponseBuilder response = Response.ok(baos.toByteArray());
			response.header("Content-disposition", "attachment; filename=export.xls");
			return response.build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
	}
	
	@POST
	@Path("/consumo")
	@Produces("application/vnd.ms-excel")
	public Response getTalleres() {
		try {
				
			MisConsumosDTO misConsumosDTO = MgnlContext.getAttribute(CioneConstants.MIS_CONSUMOS_SESSION_NAME+CioneUtils.getIdCurrentClientERP());
			
			if (misConsumosDTO == null)
			{
				String fechaDesde = MgnlContext.getParameter("fechaIni");
				String fechaHasta = MgnlContext.getParameter("fechaFin");
				MisConsumosQueryParamsDTO misConsumosQueryParamsDTO = new MisConsumosQueryParamsDTO();
				misConsumosQueryParamsDTO.setFechaDesde(fechaDesde);
				misConsumosQueryParamsDTO.setFechaHasta(fechaHasta);
				
				misConsumosDTO = misConsumosService.getMisConsumos(CioneUtils.getIdCurrentClientERP(),
						misConsumosQueryParamsDTO);
			}
			
			HSSFWorkbook xls = exportService.exportConsumoToExcel(misConsumosDTO);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			xls.write(baos);
			
			ResponseBuilder response = Response.ok(baos.toByteArray());
			response.header("Content-disposition", "attachment; filename=export.xls");
			
			return response.build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	

}
