package com.magnolia.cione.model;

import javax.jcr.Node;

import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class ListadoShoppingListModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	//private static final Logger log = LoggerFactory.getLogger(ListadoShoppingListModel.class);

	public ListadoShoppingListModel(Node content, RD definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
	}
	
}
