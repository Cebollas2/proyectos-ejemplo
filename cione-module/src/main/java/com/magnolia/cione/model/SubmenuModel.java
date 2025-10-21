package com.magnolia.cione.model;

import javax.inject.Inject;
import javax.jcr.Node;

import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.service.AuthService;
import com.magnolia.cione.service.MiddlewareService;
import com.magnolia.cione.utils.CioneUtils;
import com.magnolia.cione.utils.MyShopUtils;

import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class SubmenuModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	private String userid;
	
	@Inject
	private TemplatingFunctions templatingFunctions;
	
	@Inject
	private MiddlewareService middlewareService;
	
	@Inject
	private AuthService authService;
	
	
	public SubmenuModel(Node content, RD definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
	}
	
	@Override
	public String execute() {
		
		if (!templatingFunctions.isEditMode()) {
			
			userid = MyShopUtils.getUserName();
			
		}else {
			userid = "";
		}
		
		return super.execute();
	}
	
	public String getUserId() {
		
		return userid.isEmpty() ? "" : userid.substring(0, userid.length() - 2);
	}
	
	public UserERPCioneDTO getUserFromERP() {
		
		String idUser = CioneUtils.getIdCurrentClient();
		if (!idUser.equals("superuser")) {
			if (idUser.endsWith("00"))
				return middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
			else {
				//empleado
				return middlewareService.getUserFromERPEmployee(idUser);
			}
		} 
		//es usuario superuser o alguno de magnolia
		return null;
	}

	public String getNumSocioEncrypt() {
		
		return authService.getNumSocioEncrypt();
	}

}

