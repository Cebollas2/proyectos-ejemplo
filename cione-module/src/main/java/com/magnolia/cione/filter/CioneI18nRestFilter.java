package com.magnolia.cione.filter;

import java.io.IOException;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.magnolia.cms.filters.AbstractMgnlFilter;
import info.magnolia.cms.i18n.I18nContentSupport;
import info.magnolia.context.MgnlContext;
import java.util.Optional;

public class CioneI18nRestFilter extends AbstractMgnlFilter {

	private static final Logger log = LoggerFactory.getLogger(CioneI18nRestFilter.class);

	private static final String LANG_PARAMETER = "lang";
	
	private final I18nContentSupport i18nContentSupport;

	@Inject
	public CioneI18nRestFilter(I18nContentSupport i18nContentSupport) {
		this.i18nContentSupport = i18nContentSupport;
	}

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//log.info("filter: CioneI18nRestFilter");
		String lang = request.getParameter(LANG_PARAMETER);
		if (lang != null) {
			MgnlContext.setLocale(new Locale(lang));
		} 
		//MgnlContext.setLocale(getSupportedLanguage());
		
		chain.doFilter(request, response);
	}

	private Locale getSupportedLanguage() {
		Locale locale = this.i18nContentSupport.getLocale();
		return locale;
	}

}
