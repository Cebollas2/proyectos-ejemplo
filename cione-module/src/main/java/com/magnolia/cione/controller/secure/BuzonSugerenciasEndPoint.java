package com.magnolia.cione.controller.secure;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.FileUploadForm;
import com.magnolia.cione.service.BuzonSugerenciasService;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;


//@Api(value = "/private/buzon-sugerencias/v1")
@Path("/private/buzon-sugerencias/v1")
public class BuzonSugerenciasEndPoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(BuzonSugerenciasEndPoint.class);

	@Inject
	protected BuzonSugerenciasEndPoint(D endpointDefinition) {
		super(endpointDefinition);
	}
	
	@Inject
	private BuzonSugerenciasService buzonSugerenciasService;

	@POST
	@Path("/submit")
	@Consumes("multipart/form-data;charset=utf-8")
	public Response buzon(@MultipartForm FileUploadForm form, @Context HttpServletRequest request,
			@Context HttpHeaders headers, @QueryParam("tema") String tema,
			@QueryParam("sugerencia") String sugerencia, @QueryParam("from") String from) throws URISyntaxException {				
		String referer = headers.getRequestHeader("referer").get(0);
		if(referer.contains("?")) {
			referer = referer.split("\\?")[0];
		}
		try {									
			form.setTema(tema);
			form.setSugerencia(sugerencia);
			form.setFrom(from);
			buzonSugerenciasService.procesarFormulario(form);
			return Response.temporaryRedirect(new URI(referer +"?success=true")).build();
		}catch(Exception e) {
			log.error(e.getMessage());			
			return Response.temporaryRedirect(new URI(referer +"?success=false")).build();
		}		
	}
	
	
}
