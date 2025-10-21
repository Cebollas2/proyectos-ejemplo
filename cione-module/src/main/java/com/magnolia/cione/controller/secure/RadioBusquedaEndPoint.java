package com.magnolia.cione.controller.secure;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.RadioBusquedaDTO;
import com.magnolia.cione.service.RadioBusquedaService;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;


//@Api(value = "/private/radio-busqueda/v1")
@Path("/private/radio-busqueda/v1")
public class RadioBusquedaEndPoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(RadioBusquedaEndPoint.class);
	
	@Inject
	private RadioBusquedaService radioBusquedaService;

	@Inject
	protected RadioBusquedaEndPoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	@POST
	@Path("/radiobusquedas")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getUsers(@Valid RadioBusquedaDTO dto) {
		try {
			 radioBusquedaService.saveRadioBusqueda(dto);
			return Response.ok(true).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	@GET
	@Path("/total")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getTotalAnuncios() {
		try {
			long total = radioBusquedaService.getTotalAnuncios();
			return Response.ok(total).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}

}
