package com.magnolia.cione.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import com.magnolia.cione.dto.RepuestoPostDTO;
import com.magnolia.cione.exceptions.CioneException;
import com.magnolia.cione.service.EmailService;

import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.module.mail.MailTemplate;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;

//@Api(value = "/repuestos")
@Path("/repuestos")
public class RepuestosEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final String MESSAGE = "message";

	@Inject
	private EmailService emailService;
	
	private final SimpleTranslator i18n;
	
	@Inject
	protected RepuestosEndpoint(D endpointDefinition, SimpleTranslator i18n) {
		super(endpointDefinition);
		this.i18n = i18n;
	}

	@POST
	@Path("/submitRepuestosForm")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response submitRepuestosForm(@Valid RepuestoPostDTO dto) {
		JSONObject jsonRes = new JSONObject();
		if (dto != null) {

				
			try {
				emailService.sendTemplateEmail("Solicitud de repuestos -  MyShop", dto.getDestinatarios(),
						buildTemplateEmail(dto), null, true);
				
				jsonRes.put(MESSAGE, i18n.translate("cione-module.templates.repuestos-form-component.success"));
				String res = jsonRes.toString();
				return generateResponseBuilder(res, Response.Status.OK).build();
				
			} catch (CioneException e) {
				jsonRes.put(MESSAGE, i18n.translate("cione-module.templates.repuestos-form-component.error"));
				String res = jsonRes.toString();
				return generateResponseBuilder(res, Response.Status.INTERNAL_SERVER_ERROR).build();
			}

		} else {
			jsonRes.put(MESSAGE, i18n.translate("cione-module.templates.configuracion-precios-component.invalid.params"));
			String res = jsonRes.toString();
			return generateResponseBuilder(res, Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	private Map<String, Object> buildTemplateEmail(RepuestoPostDTO dto) {
		Map<String, Object> templateValues = new HashMap<>();

		templateValues.put(MailTemplate.MAIL_TEMPLATE_FILE, "cione-module/templates/mail/repuestos-mail.ftl");	
		templateValues.put("varillaDerecha", dto.isVarillaDerecha());
		templateValues.put("varillaIzquierda", dto.isVarillaIzquierda());
		templateValues.put("varillaFrente", dto.isVarillaFrente());
		templateValues.put("marca", dto.getMarca());
		templateValues.put("modelo", dto.getModelo());
		templateValues.put("color", dto.getColor());
		templateValues.put("usuario", dto.getUsuario());
		templateValues.put("calibre", dto.getCalibre());
		templateValues.put("comentarios", dto.getComentarios().replace("\n", "<br>"));
		

		return templateValues;

	}
	
	private ResponseBuilder generateResponseBuilder(String res, Status status) {

		return Response.status(status)
				.type(MediaType.APPLICATION_JSON + "; charset=utf-8")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8"))
				.header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
				.header(HttpHeaders.EXPIRES, "0")
				.entity(res);

	}
}
