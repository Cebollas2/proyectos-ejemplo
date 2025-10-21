package com.magnolia.cione.model;

import javax.inject.Inject;
import javax.jcr.Node;

import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class TalleresModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	private String numPedido;

	@Inject
	public TalleresModel(Node content, RD definition, RenderingModel<?> parent,
			TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
		// this.templatingFunctions = templatingFunctions;
	}

	public String getNumPedido() {
		return numPedido;
	}

	public void setNumPedido(String numPedido) {
		this.numPedido = numPedido;
	}

	

}
