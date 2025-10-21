package com.magnolia.cione.controller.secure;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.QualitySurveyDTO;
import com.magnolia.cione.dto.QualitySurveyQueryParamsDTO;
import com.magnolia.cione.service.QualitySurveyService;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;


//@Api(value = "/private/quality-surveys/v1")
@Path("/private/quality-surveys/v1")
public class QualitySurveyEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(QualitySurveyEndpoint.class);
	
	@Inject
	private QualitySurveyService service;

	@Inject
	protected QualitySurveyEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}
	
	
	@GET
	@Path("/do-question")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getLineasAbono(){
		try {
			boolean doQuestion = service.doQuestion();
			ResponseBuilder rb= Response.ok(doQuestion);
			Response response = rb.build();
			return response;
		}catch(Exception e) {
			log.error(e.getMessage());			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@POST
	@Path("/despublicar")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response despublicar() {
		try {
			service.unpublishSurveyLocal();
			return Response.ok(true).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	@POST
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response delete() {
		try {
			service.deleteSurveyLocal();
			return Response.ok(true).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getUsers(@Valid QualitySurveyDTO dto) {
		try {
			 service.saveSurvey(dto);
			return Response.ok(true).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	

	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response search(@Valid QualitySurveyQueryParamsDTO dto) {
		try {
			List<QualitySurveyDTO> surveys = service.search(dto);
			return Response.ok(surveys).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}


}
