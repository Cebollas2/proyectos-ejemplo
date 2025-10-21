package com.magnolia.cione.model;

import javax.inject.Inject;
import javax.jcr.Node;

import com.magnolia.cione.service.MiddlewareService;

import info.magnolia.context.MgnlContext;
import info.magnolia.context.WebContext;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class AbonoManager <RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	private final TemplatingFunctions templatingFunctions;
	
	@Inject
	private MiddlewareService middlewareService; 
	
	 @Inject
	public AbonoManager(Node content, RD definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
		this.templatingFunctions = templatingFunctions;
	}

	 
		 
}
