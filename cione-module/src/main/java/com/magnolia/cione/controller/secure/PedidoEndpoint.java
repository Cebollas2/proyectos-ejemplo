package com.magnolia.cione.controller.secure;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.LineaPedidoDTO;
import com.magnolia.cione.dto.MotivosDTO;
import com.magnolia.cione.dto.PaginaDevolucionesDTO;
import com.magnolia.cione.dto.PaginaGestionDevolucionesDTO;
import com.magnolia.cione.dto.PaginaPedidosDTO;
import com.magnolia.cione.dto.PedidoQueryParamsDTO;
import com.magnolia.cione.service.PedidoService;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;


//@Api(value = "/private/pedidos/v1")
@Path("/private/pedidos/v1")
public class PedidoEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(PedidoEndpoint.class);
	private static final String NO_HIST = "0";
	private static final String HIST = "1";
	private static final String B_HIST = "true";
	
	@Inject
	private PedidoService pedidoService;

	@Inject
	protected PedidoEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	@POST
	@Path("/pedidos")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getPedidos(@Valid PedidoQueryParamsDTO pedidoQueryParamsDTO) {
		try {
			PaginaPedidosDTO paginaPedidos = pedidoService.getPedidos(pedidoQueryParamsDTO);
			return Response.ok(paginaPedidos).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	
	@GET
	@Path("/info-pedido")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getLineasPedido(@QueryParam("numPedido") String numPedido, @QueryParam("historico") String historico){
		try {
			List<LineaPedidoDTO> lineasPedido = null;
			
			if (historico.equals(B_HIST)) {
				lineasPedido = pedidoService.getLineasPedido(numPedido, HIST);
			} else {
				lineasPedido = pedidoService.getLineasPedido(numPedido, NO_HIST);
			}
			
			return Response.ok(lineasPedido).build();
		}catch(Exception e) {
			log.error(e.getMessage());			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@POST
	@Path("/devoluciones")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getDevoluciones(@Valid PedidoQueryParamsDTO pedidoQueryParamsDTO) {
		try {
			if ((pedidoQueryParamsDTO.getFechaIni() != null) && !pedidoQueryParamsDTO.getFechaIni().isEmpty()) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			    String parseDate = sdf2.format(sdf.parse(pedidoQueryParamsDTO.getFechaIni()));
			    pedidoQueryParamsDTO.setFechaIni(parseDate);
			}
			if ((pedidoQueryParamsDTO.getFechaFin() != null) && !pedidoQueryParamsDTO.getFechaFin().isEmpty()) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			    String parseDate = sdf2.format(sdf.parse(pedidoQueryParamsDTO.getFechaFin()));
			    pedidoQueryParamsDTO.setFechaFin(parseDate);
			}
			PaginaDevolucionesDTO paginaDevoluciones = pedidoService.getDevoluciones(pedidoQueryParamsDTO);
			return Response.ok(paginaDevoluciones).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	@POST
	@Path("/devoluciones-avanzadas")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getDevolucionesAvanzadas(@Valid PedidoQueryParamsDTO pedidoQueryParamsDTO) {
		try {
			if ((pedidoQueryParamsDTO.getFechaIni() != null) && !pedidoQueryParamsDTO.getFechaIni().isEmpty()) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			    String parseDate = sdf2.format(sdf.parse(pedidoQueryParamsDTO.getFechaIni()));
			    pedidoQueryParamsDTO.setFechaIni(parseDate);
			}
			if ((pedidoQueryParamsDTO.getFechaFin() != null) && !pedidoQueryParamsDTO.getFechaFin().isEmpty()) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			    String parseDate = sdf2.format(sdf.parse(pedidoQueryParamsDTO.getFechaFin()));
			    pedidoQueryParamsDTO.setFechaFin(parseDate);
			}
			PaginaDevolucionesDTO paginaDevoluciones = pedidoService.getDevolucionesAvanzadas(pedidoQueryParamsDTO);
			return Response.ok(paginaDevoluciones).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	@POST
	@Path("/gestion-devoluciones")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getGestionDevoluciones(@Valid PedidoQueryParamsDTO pedidoQueryParamsDTO) {
		try {
			if ((pedidoQueryParamsDTO.getFechaIni() != null) && !pedidoQueryParamsDTO.getFechaIni().isEmpty()) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			    String parseDate = sdf2.format(sdf.parse(pedidoQueryParamsDTO.getFechaIni()));
			    pedidoQueryParamsDTO.setFechaIni(parseDate);
			}
			if ((pedidoQueryParamsDTO.getFechaFin() != null) && !pedidoQueryParamsDTO.getFechaFin().isEmpty()) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			    String parseDate = sdf2.format(sdf.parse(pedidoQueryParamsDTO.getFechaFin()));
			    pedidoQueryParamsDTO.setFechaFin(parseDate);
			}
			PaginaGestionDevolucionesDTO paginaDevoluciones = pedidoService.getGestionDevoluciones(pedidoQueryParamsDTO);
			return Response.ok(paginaDevoluciones).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	@GET
	@Path("/motivos")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getMotivos() {
		try {
			MotivosDTO motivos = pedidoService.getMotivos();
			return Response.ok(motivos).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}

}
