package com.magnolia.cione.model;

import javax.inject.Inject;
import javax.jcr.Node;

import com.magnolia.cione.service.AuthService;

import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class QualitySurveyModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	@Inject
	private TemplatingFunctions templatingFunctions;
	
	@Inject
	private AuthService authService;
	
	private boolean flagCondiciones;
	
	public QualitySurveyModel(Node content, RD definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
	}

	@Override
	public String execute() {
		
		if (!templatingFunctions.isEditMode()) {			
			flagCondiciones = authService.getTerms();
		} else {
			flagCondiciones = true;
		}
		
		return super.execute();
	}
	
	public boolean getFlagCondiciones() {
		return flagCondiciones;
	}

	public void setFlagCondiciones(boolean flagCondiciones) {
		this.flagCondiciones = flagCondiciones;
	}

}
