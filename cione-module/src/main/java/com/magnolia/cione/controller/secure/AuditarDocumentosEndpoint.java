package com.magnolia.cione.controller.secure;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.AuditarDocumentoDTO;
import com.magnolia.cione.dto.AuditarDocumentosQueryParamsDTO;
import com.magnolia.cione.dto.PaginaAuditarDocumentosDTO;
import com.magnolia.cione.service.AuditarDocumentosService;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;

//@Api(value = "/private/auditar-documentos/v1")
@Path("/private/auditar-documentos/v1")
public class AuditarDocumentosEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(AuditarDocumentosEndpoint.class);
	
	@Inject
	private AuditarDocumentosService service;

	@Inject
	protected AuditarDocumentosEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	@POST
	@Path("/registrar")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response registrarDescargaDocumento(@Valid AuditarDocumentosQueryParamsDTO auditarDocumentosQueryParamsDTO) {
		try {
			service.registrarDescargaDocumento(CioneUtils.getIdCurrentClientERP(), auditarDocumentosQueryParamsDTO);
			return Response.ok().build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
	
	@POST
	@Path("/auditorias")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getAuditorias(@Valid AuditarDocumentosQueryParamsDTO auditarDocumentosQueryParamsDTO) {
		try {
			PaginaAuditarDocumentosDTO auditorias = service.getAuditorias(auditarDocumentosQueryParamsDTO);
			return Response.ok(auditorias).build();
		}catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
}
