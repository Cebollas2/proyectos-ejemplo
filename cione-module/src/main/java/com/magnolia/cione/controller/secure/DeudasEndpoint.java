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

import com.magnolia.cione.dto.DeudaQueryParamsDTO;
import com.magnolia.cione.dto.DeudasDTO;
import com.magnolia.cione.service.MiddlewareService;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;


//@Api(value = "/private/deudas/v1")
@Path("/private/deudas/v1")
public class DeudasEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(DeudasEndpoint.class);
	
	@Inject
	private MiddlewareService service;

	@Inject
	protected DeudasEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	@POST
	@Path("/deudas")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getEnvios(@Valid DeudaQueryParamsDTO filter) {
		try {
			DeudasDTO deudas = service.getDeudas(CioneUtils.getIdCurrentClientERP(), filter);
			if(!CioneUtils.isEmptyOrNull(filter.getFechaInicio()) || !CioneUtils.isEmptyOrNull(filter.getFechaFin())) {
				//obtener el total
				filter.setFechaInicio(null);
				filter.setFechaFin(null);
				DeudasDTO deudasTotal = service.getDeudas(CioneUtils.getIdCurrentClientERP(), filter);
				deudas.setTotal(deudasTotal.getTotal());
			}
			return Response.ok(deudas).build();
		}catch(Exception e) {
			log.error(e.getMessage(),e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	
	

}
