package com.magnolia.cione.model;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.jcr.Node;

import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.LensFilterDTO;
import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.dto.CT.VariantsFilterCT;
import com.magnolia.cione.dto.CT.variants.VariantsAttributes;
import com.magnolia.cione.service.CommercetoolsService;
import com.magnolia.cione.service.LensService;
import com.magnolia.cione.service.MiddlewareService;
import com.magnolia.cione.utils.CioneUtils;
import com.magnolia.cione.utils.MyShopUtils;

import info.magnolia.context.MgnlContext;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class FiltroLentesModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	private LensFilterDTO facets;

	@Inject
	private CommercetoolsService commercetoolsservice;
	
	@Inject
	private MiddlewareService middlewareService;

	@Inject
	private LensService lensservice;
	
	@Inject
	private TemplatingFunctions templatingFunctions;
	
	
	public FiltroLentesModel(Node content, RD definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
	}
	
	@Override
	public String execute() {
		
		if (!templatingFunctions.isEditMode()) {
			
			Map<String, String[]> filters = getAllFilters();
			facets = getFacets(filters);
		}
		
		return super.execute();
	}

	private LensFilterDTO getFacets(Map<String, String[]> filters) {
		
		//String keycustomergroup = commercetoolsservice.getKeyOfCustomerGroupEKONByRoles(MgnlContext.getUser().getAllRoles());
		UserERPCioneDTO user =middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
		String keycustomergroup = "";
		if (user != null)
			keycustomergroup = user.getGrupoPrecio();
		
		
		if (keycustomergroup != null && keycustomergroup.isEmpty()) {
			String customerid = commercetoolsservice.getIdOfCustomerGroupByCostumerId(MyShopUtils.getUserName());
			//creo que esto hay que cambiarlo para que sea el customerGroup de Ekon
			keycustomergroup = commercetoolsservice.getKeyOfCustomerGroupById(customerid);
		}
		
		return lensservice.getFacets(filters, keycustomergroup);
	}

	private Map<String, String[]> getAllFilters() {
		
		Map<String, String[]> filters = new HashMap<>();
		
		/*if (MgnlContext.getParameterValues(MyshopConstants.FF_PROVEEDOR) != null && MgnlContext.getParameterValues(MyshopConstants.FF_PROVEEDOR).length > 0) {
			filters.put(MyshopConstants.FF_PROVEEDOR, MgnlContext.getParameterValues(MyshopConstants.FF_PROVEEDOR));
		}*/
		
		if (MgnlContext.getParameterValues(MyshopConstants.FF_MATERIAL) != null && MgnlContext.getParameterValues(MyshopConstants.FF_MATERIAL).length > 0) {
			filters.put(MyshopConstants.FF_MATERIAL, MgnlContext.getParameterValues(MyshopConstants.FF_MATERIAL));
		}
		
		return filters;
	}
	
	public LensFilterDTO getFacets() {
		return facets;
	}
	
	public boolean hasFacets() {
		return hasTerms(facets.getMateriales())         || hasTerms(facets.getProveedores());
	}
	
	private boolean hasTerms(VariantsAttributes varAttr) {
		return varAttr != null && varAttr.hasTerms();
		
	}

}
