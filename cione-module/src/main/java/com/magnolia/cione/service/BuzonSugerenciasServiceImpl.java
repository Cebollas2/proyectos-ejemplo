package com.magnolia.cione.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.FileUploadForm;
import com.magnolia.cione.exceptions.CioneException;
import com.magnolia.cione.exceptions.CioneRuntimeException;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.dam.templating.functions.DamTemplatingFunctions;
import info.magnolia.module.mail.MailTemplate;
import info.magnolia.module.mail.templates.MailAttachment;

public class BuzonSugerenciasServiceImpl implements BuzonSugerenciasService{
	
	private static final Logger log = LoggerFactory.getLogger(BuzonSugerenciasServiceImpl.class);
	
	@Inject
	private DamTemplatingFunctions damTemplatingFunctions;
	
	
	
	@Inject
	private EmailService emailService;

	@Override
	public void procesarFormulario(FileUploadForm form) {
		try {			
			if(form.getTo().length() != Integer.parseInt(form.getChecksum())) {
				throw new CioneException("Error en el checksum");
			}
		
			List<MailAttachment> attachmentList = null;
			if(form.getData() != null && form.getData().length != 0) {
				File tempFile = File.createTempFile("buzon_sugerencias_", form.getNameFile());
				FileUtils.writeByteArrayToFile(tempFile, form.getData());
				MailAttachment attachment = new MailAttachment(tempFile, form.getNameFile(), "cione", "cione");
				attachmentList = new ArrayList<>();
				attachmentList.add(attachment);				
			}			
			emailService.sendTemplateEmail("Buzón de sugerencias -  MyCione", form.getTo(),
					buildTemplateEmailForBuzon(form), attachmentList);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CioneRuntimeException("Erro al procesar el formulario");
		}		
	}
	
	private Map<String, Object> buildTemplateEmailForBuzon(FileUploadForm form) {
		Map<String, Object> templateValues = new HashMap<>();

		templateValues.put(MailTemplate.MAIL_TEMPLATE_FILE, "cione-module/templates/mail/buzon-sugerencias-mail.ftl");	
		templateValues.put("logoHeader", getImageLink("/cione/templates/emails/logo-header.png"));
		templateValues.put("tema", form.getTema());
		templateValues.put("usuario", form.getFrom());
		templateValues.put("sugerencia", form.getSugerencia());
		

		return templateValues;

	}
	
	
	private String getImageLink(String path) {
		String link = "";
		if (damTemplatingFunctions.getAsset("jcr", path) != null) {
			link = CioneUtils.getURLHttps() + damTemplatingFunctions.getAsset("jcr", path).getLink();
		}
		return link;
	}

	

}
