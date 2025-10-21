package com.magnolia.cione.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.constants.CioneConstants;

import info.magnolia.audit.AuditLoggingUtil;
import info.magnolia.cms.filters.MgnlFilterChain;
import info.magnolia.cms.filters.OncePerRequestAbstractMgnlFilter;
import info.magnolia.cms.security.LogoutFilter;
import info.magnolia.context.Context;
import info.magnolia.context.MgnlContext;
import info.magnolia.context.UserContext;

public class CioneLogoutFilter extends OncePerRequestAbstractMgnlFilter {
    private static final Logger log = LoggerFactory.getLogger(LogoutFilter.class);

    public static final String PARAMETER_LOGOUT = "mgnlLogout";

    private ServletContext servletContext;
    

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.servletContext = filterConfig.getServletContext();
    }

    /**
     * Check if a request parameter PARAMETER_LOGOUT is set. If so logout user,
     * unset the context and restart the filter chain.
     */
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (null != request.getParameter(PARAMETER_LOGOUT)) {
        	log.debug("Filtro de logout");
        	if (MgnlContext.getUser().hasRole(CioneConstants.OPTMAD) 
        			|| MgnlContext.getUser().hasRole(CioneConstants.OPTCAN)) {
        		request.setAttribute("isOptofive", true);
        	} else if (MgnlContext.getUser().hasRole(CioneConstants.OPTICAPRO)) {
        		request.setAttribute("isOpticapro", true);
        	}
            Context ctx = MgnlContext.getInstance();
            if (ctx instanceof UserContext) {
                // log before actual op, to preserve username for logging
                AuditLoggingUtil.log((UserContext) ctx);
                ((UserContext) ctx).logout();
            }

            if (request.getSession(false) != null) {
                request.getSession().invalidate();
            }
            if (chain instanceof MgnlFilterChain) {
                ((MgnlFilterChain) chain).reset();
            }

            response.sendRedirect(resolveLogoutRedirectLink(request));
            
        }

        chain.doFilter(request, response);
    }

    protected String resolveLogoutRedirectLink(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }
}
