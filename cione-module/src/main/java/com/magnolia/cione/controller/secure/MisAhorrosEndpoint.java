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

import com.magnolia.cione.dto.MisAhorrosDTO;
import com.magnolia.cione.dto.MisAhorrosQueryParamsDTO;
import com.magnolia.cione.service.MisAhorrosService;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;


//@Api(value = "/private/mis-ahorros/v1")
@Path("/private/mis-ahorros/v1")
public class MisAhorrosEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(MisAhorrosEndpoint.class);
	
	@Inject
	private MisAhorrosService service;

	@Inject
	protected MisAhorrosEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	@POST
	@Path("/ahorros")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getConsumos(@Valid MisAhorrosQueryParamsDTO misAhorrosQueryParamsDTO) {
		try {
			 MisAhorrosDTO misAhorros = service.getMisAhorros(CioneUtils.getIdCurrentClientERP(), misAhorrosQueryParamsDTO);
			return Response.ok(misAhorros).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	
	

}
