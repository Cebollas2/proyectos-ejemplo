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

import com.magnolia.cione.dto.EnvioQueryParamsDTO;
import com.magnolia.cione.dto.LineaEnvioDTO;
import com.magnolia.cione.dto.PaginaEnviosDTO;
import com.magnolia.cione.service.EnvioService;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;


//@Api(value = "/private/envios/v1")
@Path("/private/envios/v1")
public class EnvioEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(EnvioEndpoint.class);
	
	@Inject
	private EnvioService envioService;

	@Inject
	protected EnvioEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	@POST
	@Path("/envios")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getEnvios(@Valid EnvioQueryParamsDTO envioQueryParamsDTO) {
		try {
			PaginaEnviosDTO paginaEnvios = envioService.getEnvios(envioQueryParamsDTO);
			return Response.ok(paginaEnvios).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	
	@GET
	@Path("/info-envio")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getLineasEnvio(@QueryParam("trackingPedido") String trackingPedido, @QueryParam("numAlbaran") String numAlbaran){
		try {			

			List<LineaEnvioDTO> lineasEnvio = envioService.getLineasEnvio(numAlbaran, trackingPedido);
			return Response.ok(lineasEnvio).build();
		
		}catch(Exception e) {
			log.error(e.getMessage());			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

}
