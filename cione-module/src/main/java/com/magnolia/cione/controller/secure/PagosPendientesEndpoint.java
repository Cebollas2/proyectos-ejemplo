package com.magnolia.cione.controller.secure;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.PagosPendientesDTO;
import com.magnolia.cione.dto.PagosPendientesQueryParamsDTO;
import com.magnolia.cione.service.MiddlewareService;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;


//@Api(value = "/private/pagos-pendientes/v1")
@Path("/private/pagos-pendientes/v1")
public class PagosPendientesEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(PagosPendientesEndpoint.class);
	
	@Inject
	private MiddlewareService service;

	@Inject
	protected PagosPendientesEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	@POST
	@Path("/pagos-pendientes")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getData(@Valid PagosPendientesQueryParamsDTO filter) {
		try {
			PagosPendientesDTO pagosPendientes = service.getPagosPendientes(CioneUtils.getIdCurrentClientERP(), filter);
			return Response.ok(pagosPendientes).build();
		}catch(Exception e) {
			log.error(e.getMessage(),e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	
	

}
