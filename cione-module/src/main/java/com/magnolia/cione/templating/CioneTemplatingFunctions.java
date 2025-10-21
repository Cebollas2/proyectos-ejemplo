package com.magnolia.cione.templating;

import javax.inject.Inject;
import javax.inject.Provider;

import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.service.MiddlewareService;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.cms.i18n.I18nContentSupport;
import info.magnolia.context.MgnlContext;
import info.magnolia.context.WebContext;
import info.magnolia.rendering.template.type.TemplateTypeHelper;
import info.magnolia.templating.functions.TemplatingFunctions;

public class CioneTemplatingFunctions extends TemplatingFunctions {
	
	@Inject
	private MiddlewareService middlewareService;
	
	private UserERPCioneDTO socio;

	@Inject
	public CioneTemplatingFunctions(TemplateTypeHelper templateTypeFunctions,
			Provider<I18nContentSupport> i18nContentSupport, Provider<WebContext> webContextProvider) {
		super(templateTypeFunctions, i18nContentSupport, webContextProvider);
	}
	
	public UserERPCioneDTO getUserERPCioneDTO() {
		UserERPCioneDTO socio = null;
		if (isUser(MgnlContext.getUser().getName()))
			socio = middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
		
		return socio;
	}
	
	public UserERPCioneDTO getSocio() {
		if (isUser(MgnlContext.getUser().getName())) 
			socio = middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
		return socio;
	}
	
	private boolean isUser(String username) {
		return username.matches("\\d+"); 
	}

}
