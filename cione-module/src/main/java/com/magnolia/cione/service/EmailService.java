package com.magnolia.cione.service;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.exceptions.CioneException;

import info.magnolia.module.mail.templates.MailAttachment;

@ImplementedBy(EmailServiceMagnoliaImpl.class)
public interface EmailService {

	public void sendEmail(String subject, String text, String email) throws CioneException;
	
	public void sendTemplateEmail(String subject, String email,  Map<String, Object> templateValues, List<MailAttachment> attachments) throws CioneException;
	
	public void sendTemplateEmail(String subject, String email,  Map<String, Object> templateValues, List<MailAttachment> attachments, Boolean pedido) throws CioneException;

}
