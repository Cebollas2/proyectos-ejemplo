package com.magnolia.cione.model;

import java.util.Calendar;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class MisConsumosCioneLoversModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	private String idsocio;
	private String dateinterval;
	@Inject
	private TemplatingFunctions templatingFunctions;

	@Inject
	public MisConsumosCioneLoversModel(Node content, RD definition, RenderingModel<?> parent) {
		super(content, definition, parent);
	}
	
	@Override
	public String execute() {
		
		if (!templatingFunctions.isEditMode()) {
			idsocio = CioneUtils.getIdCurrentClientERP();
		}
		
		try {
			
			StringBuilder str = new StringBuilder();
			Calendar datestart = content.getProperty(MyshopConstants.CL_DATESTART).getDate();
			Calendar dateend = content.getProperty(MyshopConstants.CL_DATEEND).getDate();
			
			if (datestart != null && dateend != null) {
				str.append(MyshopConstants.CL_OPEN_BRACKET);
				str.append(datestart.get(Calendar.MONTH)+1); 
				str.append(MyshopConstants.CL_SLASH); 
				str.append(datestart.get(Calendar.YEAR)); 
				str.append(MyshopConstants.CL_SPACE_HYPHEN); 
				str.append(dateend.get(Calendar.MONTH)+1); 
				str.append(MyshopConstants.CL_SLASH); 
				str.append(dateend.get(Calendar.YEAR));  
				str.append(MyshopConstants.CL_CLOSE_BRACKET);
				dateinterval = str.toString();
			}
			
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		
		return super.execute();
	}

	public String getIdsocio() {
		return idsocio;
	}

	public String getDateinterval() {
		return dateinterval;
	}

}
