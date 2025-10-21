package com.magnolia.cione.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.commons.lang.RandomStringUtils;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.LensFilterDTO;
import com.magnolia.cione.dto.CT.variants.VariantsAttributes;
import com.magnolia.cione.service.LensService;
import com.magnolia.cione.utils.CioneUtils;
import com.magnolia.cione.utils.MyShopUtils;

import info.magnolia.cms.security.SecuritySupport;
import info.magnolia.context.MgnlContext;
import info.magnolia.objectfactory.Components;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class ConfiguradorLentesModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {
	
	private List<String> cilindros = new ArrayList<>();
	
	@Inject
	private LensService lensservice;
	
	@Inject
	private TemplatingFunctions templatingFunctions;
	
	
	public ConfiguradorLentesModel(Node content, RD definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
	}
	
	@Override
	public String execute() {
		
		if (!templatingFunctions.isEditMode()) {
			Map<String, String[]> filters = getAllFilters();
			cilindros = lensservice.getCilindros(filters);
		}
		
		return super.execute();
	}

	private Map<String, String[]> getAllFilters() {
		
		Map<String, String[]> filters = new HashMap<>();
		
		if (MgnlContext.getParameterValues(MyshopConstants.FF_PROVEEDOR) != null && MgnlContext.getParameterValues(MyshopConstants.FF_PROVEEDOR).length > 0) {
			filters.put(MyshopConstants.FF_PROVEEDOR, MgnlContext.getParameterValues(MyshopConstants.FF_PROVEEDOR));
		}
		
		if (MgnlContext.getParameterValues(MyshopConstants.FF_MATERIAL) != null && MgnlContext.getParameterValues(MyshopConstants.FF_MATERIAL).length > 0) {
			filters.put(MyshopConstants.FF_MATERIAL, MgnlContext.getParameterValues(MyshopConstants.FF_MATERIAL));
		}
		
		return filters;
	}
	
	public List<String> getCilindros() {
		return cilindros;
	}
	
	public String getRefTaller() {
		return CioneUtils.generateRef();
	}
    
    public String getUuid() {
    	String uuid ="";
    	String id = MgnlContext.getUser().getName();
		// impersonate
		if (MgnlContext.getUser().hasRole(CioneConstants.ROLE_CIONE_SUPERUSER) || MgnlContext.getUser().hasRole(CioneConstants.ROLE_OPTOFIVE_SUPERUSER)) {
			String idToSimulate = MgnlContext.getUser().getProperty(CioneConstants.IMPERSONATE_FIELD_ID_SOCIO);
			if (idToSimulate != null && !idToSimulate.isEmpty()) {
				id = idToSimulate;
			}
		}
    	uuid = Components.getComponent(SecuritySupport.class).getUserManager().getUser(id).getIdentifier();
    	return uuid;
    }
    
    public String getUserName() {
    	String userName = MgnlContext.getUser().getName();
		// impersonate
		if (MgnlContext.getUser().hasRole(CioneConstants.ROLE_CIONE_SUPERUSER) || MgnlContext.getUser().hasRole(CioneConstants.ROLE_OPTOFIVE_SUPERUSER)) {
			String idToSimulate = MgnlContext.getUser().getProperty(CioneConstants.IMPERSONATE_FIELD_ID_SOCIO);
			if (idToSimulate != null && !idToSimulate.isEmpty()) {
				userName = idToSimulate;
			}
		}
		return userName;
    }
	

}
