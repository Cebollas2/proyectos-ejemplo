package com.magnolia.cione.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.exceptions.CioneException;

import info.magnolia.module.ModuleRegistry;
import info.magnolia.module.mail.MailModule;
import info.magnolia.module.mail.MgnlMailFactory;
import info.magnolia.module.mail.templates.MailAttachment;
import info.magnolia.module.mail.templates.MgnlEmail;


public class EmailServiceMagnoliaImpl implements EmailService{
	
	private static final Logger log = LoggerFactory.getLogger(EmailServiceMagnoliaImpl.class);
	
	
	@Inject
	private ConfigService configService;
	
	

	public void sendEmail(String subject, String text, String email) throws CioneException{
		final MgnlMailFactory mailFactory = ((MailModule) ModuleRegistry.Factory.getInstance().getModuleInstance("mail")).getFactory();
        try {            
        	Map<String, Object> templateValues = new HashMap<>();
        	//final SimpleTranslator translator = Components.getComponent(SimpleTranslator.class);        	
            MgnlEmail mail = mailFactory.getEmail(templateValues);
            mail.setFrom(configService.getConfig().getAuthSenderEmail());
            mail.setToList(email);
            mail.setSubject(subject);
            mail.setBody(text);
            mailFactory.getEmailHandler().prepareAndSendMail(mail);
        } catch (Exception e) {
        	log.error(e.getMessage(),e);     
        	throw new CioneException("Se ha producido un error en el envío del email"); 
        }
	}

	@Override
	public void sendTemplateEmail(String subject, String email, Map<String, Object> templateValues, List<MailAttachment> attachments)
			throws CioneException {
			sendTemplateEmail(subject, email, templateValues, attachments, false);
	}

	@Override
	public void sendTemplateEmail(String subject, String email, Map<String, Object> templateValues,
			List<MailAttachment> attachments, Boolean pedido) throws CioneException {
		final MgnlMailFactory mailFactory = ((MailModule) ModuleRegistry.Factory.getInstance().getModuleInstance("mail")).getFactory();
        try {                    	                	
            final MgnlEmail mail = mailFactory.getEmailFromType(templateValues, "freemarker"); 
            if (Boolean.TRUE.equals(pedido)) {
            	mail.setFrom(configService.getConfig().getAuthSenderEmailPedidos());
            }else {
            	mail.setFrom(configService.getConfig().getAuthSenderEmail());            	
            }
            mail.setToList(email);
            mail.setSubject(subject);            
            mail.setBodyFromResourceFile();            
            if(attachments != null) {
            	mail.setAttachments(attachments);
            }            
            mailFactory.getEmailHandler().prepareAndSendMail(mail);
        } catch (Exception e) {
        	log.error(e.getMessage(),e);        	
        	throw new CioneException("Se ha producido un error en el envío del email"); 
        }	
		
	}
	
	

	

}