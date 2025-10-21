package com.magnolia.cione.controller.secure;

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

import com.magnolia.cione.dto.LineaTallerDTO;
import com.magnolia.cione.dto.PaginaTalleresDTO;
import com.magnolia.cione.dto.TallerQueryParamsDTO;
import com.magnolia.cione.service.TallerService;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;


//@Api(value = "/private/talleres/v1")
@Path("/private/talleres/v1")
public class TallerEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(TallerEndpoint.class);
	private static final String NO_HIST = "0";
	private static final String HIST = "1";
	private static final String B_HIST = "true";
	
	@Inject
	private TallerService tallerService;

	@Inject
	protected TallerEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	@POST
	@Path("/talleres")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getTalleres(@Valid TallerQueryParamsDTO tallerQueryParamsDTO) {
		try {
			PaginaTalleresDTO paginaTalleres = tallerService.getTalleres(tallerQueryParamsDTO);
			return Response.ok(paginaTalleres).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	@GET
	@Path("/info-taller")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getLineasTaller(@QueryParam("idServicio") String idServicio, @QueryParam("numPedido") String numPedido, @QueryParam("historico") String historico){
		try {
			List<LineaTallerDTO> lineasTaller = null;
			
			if (historico.equals(B_HIST)) {
				lineasTaller = tallerService.getLineasTaller(idServicio, numPedido, HIST);
			} else {
				lineasTaller = tallerService.getLineasTaller(idServicio, numPedido, NO_HIST);
			}
			
			return Response.ok(lineasTaller).build();
		}catch(Exception e) {
			log.error(e.getMessage());			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

}
