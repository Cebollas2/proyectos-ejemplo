package com.magnolia.cione.security;

import info.magnolia.cms.security.auth.callback.AbstractHttpClientCallback;
import info.magnolia.cms.util.ServletUtil;
import info.magnolia.context.MgnlContext;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class CioneRedirectClientCallback extends AbstractHttpClientCallback {
    private final static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CioneRedirectClientCallback.class);

    private String location = "/.magnolia";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        String target;
        
        if (location.startsWith("/")) {
            target = request.getContextPath() + location;
        } else {
            target = location;
        }
        //MgnlContext.getWebContext().getRequest().getSession().setAttribute("sessionurlprivada", request.getRequestURI());
        if ((request.getAttribute("isOptofive") != null) && (boolean) request.getAttribute("isOptofive")) {
        	//target = request.getContextPath() + "/optofive";
        	log.debug("es optofive");
        	target = request.getContextPath() + "/optofive";
        } 
        
        if ((request.getAttribute("isOpticapro") != null) && (boolean) request.getAttribute("isOpticapro")) {
        	//target = request.getContextPath() + "/optofive";
        	log.debug("es opticapro");
        	target = request.getContextPath() + "/opticapro";
        } 
        
        target = target.replaceAll("\\{\\s*}", "{0}");
        
        if (ServletUtil.stripPathParameters(request.getRequestURI()).equals(target)) {
            log.debug("Unauthorized, can't redirect further, we're already at {}", target);
            return;
        }
        //log.debug("Unauthorized, will redirect to {}", target);

        try {
            // formats the target location with the request url, to allow passing it has a parameter, for instance.
            String url = request.getRequestURL().toString();
            
            if (MgnlContext.getParameters() != null && !MgnlContext.getParameters().isEmpty()) {
                Set<String> keys = MgnlContext.getParameters().keySet();
                String parameterString = "";
                String[] values;
                for (String key : keys) {
                    // we don't want to pass along the mgnlLogut parameter on a
                    // login action
                    if (!key.equals("mgnlLogout")) {
                        values = MgnlContext.getParameterValues(key);
                        for (int i = 0; i < values.length; i++) {
                            parameterString += key + "=" + values[i] + "&";
                        }
                    }
                }
                if (StringUtils.isNotBlank(parameterString)) {
                    // cut off trailing "&"
                    url += "?" + StringUtils.substringBeforeLast(parameterString, "&");
                    target += "?" + StringUtils.substringBeforeLast(parameterString, "&");
                }
            }
            final String encodedUrl = URLEncoder.encode(url, "UTF-8");


            final String formattedTarget = MessageFormat.format(target, encodedUrl);
            response.sendRedirect(formattedTarget);
        } catch (IOException e) {
            throw new RuntimeException("Can't redirect to " + target + " : " + e.getMessage(), e);
        }
    }

    // ------- configuration methods

    /**
     * The location field as sent to the browser. If the value starts with a /, it is preceded
     * by the context path of the current request. The default value is "/.magnolia".
     * If you need to the current request location in an external login form, you can use the {0} tag:
     * a value of "http://sso.mycompany.com/login/?backto={0}" will pass the current request url as the "backto"
     * parameter to the location url.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    protected String getLocation() {
        return location;
    }
}
