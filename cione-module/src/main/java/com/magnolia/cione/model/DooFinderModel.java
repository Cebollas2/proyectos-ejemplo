package com.magnolia.cione.model;

import javax.jcr.Node;

import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;

public class DooFinderModel <RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {
	
	private String rutaCSV;

	public DooFinderModel(Node content, RD definition, RenderingModel<?> parent) {
		super(content, definition, parent);
	}
	
	@Override
	public String execute() {
		
		
		return super.execute();
	}
	
	public String getRutaCSV() {
		return rutaCSV;
	}

	public void setRutaCSV(String rutaCSV) {
		this.rutaCSV = rutaCSV;
	}

}
