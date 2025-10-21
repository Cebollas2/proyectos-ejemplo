package com.magnolia.cione.controller.secure;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.CampaignsQueryParamsDTO;
import com.magnolia.cione.dto.PaginaCampaignsDTO;
import com.magnolia.cione.service.CampaignsService;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;

//@Api(value = "/private/campaigns/v1")
@Path("/private/campaigns/v1")
public class CampaignsEndpoint <D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(CampaignsEndpoint.class);
	
	@Inject
	private CampaignsService service;
	
	@Inject
	protected CampaignsEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	@POST
	@Path("/registrar")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response registrarCampaign(@Valid CampaignsQueryParamsDTO campaignsQueryParamsDTO) {
		try {
			campaignsQueryParamsDTO.setCodSocio(CioneUtils.getIdCurrentClientERP());
			service.registrarCampaign(campaignsQueryParamsDTO);
			JSONObject jsonRes = new JSONObject();
			jsonRes.put("respuesta", "ok");
			return Response.ok(jsonRes.toString()).build();
		}catch(Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	@POST
	@Path("/campaigns")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getCampaigns(@Valid CampaignsQueryParamsDTO campaignsQueryParamsDTO) {
		try {
			PaginaCampaignsDTO campaigns = service.getCampaigns(campaignsQueryParamsDTO);
			return Response.ok(campaigns).build();
		}catch(Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	@POST
	@Path("/exists")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response existsCampaign(@Valid CampaignsQueryParamsDTO campaignsQueryParamsDTO) {
		try {
			campaignsQueryParamsDTO.setCodSocio(CioneUtils.getIdCurrentClientERP());
			PaginaCampaignsDTO campaigns = service.getSingleCampaign(campaignsQueryParamsDTO);
			return Response.ok(campaigns.getNumRegistros() > 0).build();
		}catch(Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
}
