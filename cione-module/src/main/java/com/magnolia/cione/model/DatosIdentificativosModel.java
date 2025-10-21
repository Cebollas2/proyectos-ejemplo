package com.magnolia.cione.model;

import javax.inject.Inject;
import javax.jcr.Node;

import com.magnolia.cione.constants.CioneRoles;
import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.service.MiddlewareService;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.context.MgnlContext;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class DatosIdentificativosModel <RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	//private final TemplatingFunctions templatingFunctions;
	
	@Inject
	private MiddlewareService middlewareService; 
	
	 @Inject
	public DatosIdentificativosModel(Node content, RD definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
		//this.templatingFunctions = templatingFunctions;
	}

	 
	public UserERPCioneDTO getData() {	
		UserERPCioneDTO data = middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
		if(data == null) {
			data = new UserERPCioneDTO();
		}
		return data; 
	} 
	
	/**
	 * Devuelve si posee el rol comunicacion o no. Esto se usa para que en caso
	 * de que un usuario tenga este rol significa que <strong>NO tendra acceso al apartado
	 * de comunicaciones</strong> y por tanto en la cabecera no se deben mostrar notificaciones
	 * de las noticias.
	 * 
	 * @return true si tiene el rol de comunicacion, false en caso contrario
	 */
	public boolean isPartner() {
		
		return CioneUtils.isSocio(MgnlContext.getUser().getName());
		
	}
	 
}
