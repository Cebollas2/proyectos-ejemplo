package com.magnolia.cione.controller.secure;

import java.util.List;

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

import com.magnolia.cione.dto.InteraccionQueryParamsDTO;
import com.magnolia.cione.dto.LineaAlbaranDTO;
import com.magnolia.cione.dto.PaginaInteraccionesDTO;
import com.magnolia.cione.service.MiddlewareService;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;


//@Api(value = "/private/interacciones/v1")
@Path("/private/interacciones/v1")
public class InteraccionEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(InteraccionEndpoint.class);
	
	@Inject
	private MiddlewareService middlewareService;

	@Inject
	protected InteraccionEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	@POST
	@Path("/interacciones")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getInteracciones(@Valid InteraccionQueryParamsDTO params) {
		try {
			 PaginaInteraccionesDTO paginaInteracciones = middlewareService.getInteracciones(CioneUtils.getIdCurrentClientERP(),params);
			return Response.ok(paginaInteracciones).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	

}
