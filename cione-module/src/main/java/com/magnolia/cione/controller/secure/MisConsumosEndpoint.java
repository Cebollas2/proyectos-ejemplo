package com.magnolia.cione.controller.secure;

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

import com.magnolia.cione.dto.ConsumosCLDTO;
import com.magnolia.cione.dto.MisConsumosDTO;
import com.magnolia.cione.dto.MisConsumosQueryParamsDTO;
import com.magnolia.cione.service.MiddlewareService;
import com.magnolia.cione.service.MisConsumosService;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;

//@Api(value = "/private/mis-consumos/v1")
@Path("/private/mis-consumos/v1")
public class MisConsumosEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(MisConsumosEndpoint.class);

	@Inject
	private MisConsumosService misConsumosService;

	@Inject
	private MiddlewareService middlewareService;

	@Inject
	protected MisConsumosEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	@POST
	@Path("/consumos")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getConsumos(@Valid MisConsumosQueryParamsDTO misConsumosQueryParamsDTO) {
		try {
			MisConsumosDTO misConsumos = misConsumosService.getMisConsumos(CioneUtils.getIdCurrentClientERP(),
					misConsumosQueryParamsDTO);
			return Response.ok(misConsumos).build();
		} catch (Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/consumoscl")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getConsumos(@QueryParam("idClient") String idClient) {
		try {
			ConsumosCLDTO consumosCLDTO = middlewareService.getConsumosCioneLovers(idClient);
			return Response.ok(consumosCLDTO).build();
		} catch (Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/consumosclbygrouptwo")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getConsumos(@QueryParam("idClient") String idClient, @QueryParam("grouptwo") String grouptwo) {
		try {
			ConsumosCLDTO consumosCLDTO = middlewareService.getConsumosCioneLoversFilterByGroupTwo(idClient, grouptwo);
			return Response.ok(consumosCLDTO).build();
		} catch (Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

}
