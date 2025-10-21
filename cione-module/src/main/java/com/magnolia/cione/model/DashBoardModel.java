package com.magnolia.cione.model;

import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;
import javax.jcr.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.DashBoardDTO;
import com.magnolia.cione.service.DashBoardService;

import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class DashBoardModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	private static final Logger log = LoggerFactory.getLogger(DashBoardModel.class);
	
	private final TemplatingFunctions templatingFunctions;
	
	@Inject
	private DashBoardService dashBoardService;
	
	private DashBoardDTO dashBoardDTO;

	@Inject
	public DashBoardModel(Node content, RD definition, RenderingModel<?> parent,
			TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
		this.templatingFunctions = templatingFunctions;
	}
	
	@Override
	public String execute() {
		
		Locale locale = new Locale(templatingFunctions.language());
		
		if (!templatingFunctions.isEditMode()) 
			dashBoardDTO = dashBoardService.getDashBoard(locale);
		return super.execute();
	}

	public DashBoardDTO getDashBoardDTO() {
		return dashBoardDTO;
	}
	
	public String getDashBoardName() {
		String name = "Dashboard ";
		try {
			Locale locale = new Locale(templatingFunctions.language());
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, -1);
			String mont = c.getDisplayName(Calendar.MONTH, Calendar.LONG, locale);
			name = "Dashboard " + mont + " " + c.get(Calendar.YEAR);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return name;
	}
	
	public String getMonth() {
		String nameParser = "";
		try {
			Locale locale = new Locale(templatingFunctions.language());
			Calendar c = Calendar.getInstance();
			String name = c.getDisplayName(Calendar.MONTH, Calendar.LONG, locale);
			
			nameParser = name.substring(0, 1).toUpperCase() + name.substring(1, name.length()) ;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return nameParser;
	}
	
	public String datosInicializadosConsumosLabels() {
		String labels = "[\"\",\"\"]";
		return labels;
	}
	
	public String datosInicializadosConsumosDataSet() {
		String labels = "[{\"backgroundColor\":[\"#f5f5f5\",\"#f5f5f5\"],\"data\":[0.0,0.001],\"hoverBackgroundColor\":[\"#f5f5f5\",\"#f5f5f5\"]}]";
		return labels;
	}

}
