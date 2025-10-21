package com.magnolia.cione.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.commons.lang.StringUtils;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.dto.MisConsumosDTO;
import com.magnolia.cione.dto.MisConsumosQueryParamsDTO;
import com.magnolia.cione.service.MisConsumosService;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.cms.security.SecuritySupport;
import info.magnolia.cms.security.User;
import info.magnolia.context.Context;
import info.magnolia.context.MgnlContext;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;

public class MisConsumosModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	@Inject
	private MisConsumosService misConsumosService;

	private MisConsumosDTO misConsumos;
	
	@Inject
	private SecuritySupport securitySupport;

	@Inject
	public MisConsumosModel(Node content, RD definition, RenderingModel<?> parent) {
		super(content, definition, parent);
	}
	
	@Override
	public String execute() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
		
		String fechaDesde = MgnlContext.getParameter("fechaDesde");
		String fechaHasta = MgnlContext.getParameter("fechaHasta");
		String isFirstTime = MgnlContext.getParameter("isFirstTime");
		
		boolean newSearch = false;

		if (StringUtils.isNotEmpty(fechaDesde) || StringUtils.isNotEmpty(fechaHasta)) {
			
			if(StringUtils.isNotEmpty(isFirstTime) && isFirstTime.equals("true")) {
				
				misConsumos = MgnlContext.getAttribute(CioneConstants.MIS_CONSUMOS_SESSION_NAME+CioneUtils.getIdCurrentClientERP());
				
				if(misConsumos == null || !misConsumos.getMinDateView().equals(fechaDesde.replace("-", "/")) || !misConsumos.getMaxDateView().equals(sdf.format(new Date()))) {
					newSearch = true;
				}
				
			} else {
				newSearch = true;
			}
			
			if(newSearch) {
				MisConsumosQueryParamsDTO misConsumosQueryParamsDTO = new MisConsumosQueryParamsDTO();
				misConsumosQueryParamsDTO.setFechaDesde(fechaDesde);
				misConsumosQueryParamsDTO.setFechaHasta(fechaHasta);
				
				misConsumos = misConsumosService.getMisConsumos(CioneUtils.getIdCurrentClientERP(),
						misConsumosQueryParamsDTO);
				
				MgnlContext.setAttribute(CioneConstants.MIS_CONSUMOS_SESSION_NAME+CioneUtils.getIdCurrentClientERP(), misConsumos);
			}
			
			
		}
		
		return super.execute();
	}
	
	public boolean ocultarSecciones() {
		User currentUser = securitySupport.getUserManager("public").getUser(CioneUtils.getIdCurrentClient());
		if (currentUser!= null && currentUser.hasRole(CioneConstants.ROLE_FIDELIZACION)) {
			return true;
		} else {
			return false;
		}
	}

	public MisConsumosDTO getMisConsumos() {
		return misConsumos;
	}

}
