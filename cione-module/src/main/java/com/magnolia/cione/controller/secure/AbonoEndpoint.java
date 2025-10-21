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
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.AbonoQueryParamsDTO;
import com.magnolia.cione.dto.LineaAbonoDTO;
import com.magnolia.cione.dto.PaginaAbonosDTO;
import com.magnolia.cione.service.AbonoService;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;


//@Api(value = "/private/abonos/v1")
@Path("/private/abonos/v1")
public class AbonoEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(AbonoEndpoint.class);
	
	@Inject
	private AbonoService abonoService;

	@Inject
	protected AbonoEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	@POST
	@Path("/abonos")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getAbonos(@Valid AbonoQueryParamsDTO abonoQueryParamsDTO) {
		try {
			PaginaAbonosDTO paginaAbonos = abonoService.getAbonos(abonoQueryParamsDTO);
			return Response.ok(paginaAbonos).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	
	@GET
	@Path("/info-abono")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getLineasAbono(@QueryParam("numAbono") String numAbono, @QueryParam("historico") String historico){
		try {
			List<LineaAbonoDTO> lineasAbono = abonoService.getLineasAbono(numAbono, historico);
			
			ResponseBuilder rb= Response.ok(lineasAbono);
			Response response = rb.build();
			
			return response;
		}catch(Exception e) {
			log.error(e.getMessage());			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

}
