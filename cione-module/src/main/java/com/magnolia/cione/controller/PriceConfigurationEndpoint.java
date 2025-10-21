package com.magnolia.cione.controller;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.magnolia.cione.dto.PriceConfigurationPostDTO;
import com.magnolia.cione.service.UserService;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;

//@Api(value = "/price-configuration")
@Path("/price-configuration")
public class PriceConfigurationEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {
	
	@Inject
	private UserService userService;
	
	@Inject
	protected PriceConfigurationEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	@POST
	@Path("/submitPriceConfigurationForm")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response submitPriceConfigurationForm(@Valid PriceConfigurationPostDTO dto) {
		return userService.updateUserPriceConfiguration(dto);
	}

	
}
