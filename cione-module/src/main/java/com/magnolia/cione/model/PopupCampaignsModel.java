package com.magnolia.cione.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.Property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.constants.CioneRoles;
import com.magnolia.cione.service.AuthService;

import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.ContentMap;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.jcr.util.SessionUtil;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class PopupCampaignsModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {
	
	private static final Logger log = LoggerFactory.getLogger(PopupCampaignsModel.class);
	
	@Inject
	private TemplatingFunctions templatingFunctions;
	
	@Inject
	private AuthService authService;
	
	private boolean flagCondiciones;

	public PopupCampaignsModel(Node content, RD definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
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
	
	public boolean hasPermissionsRoles(ContentMap contentRolesAllowed) {
		boolean result = false;
		
		try {
			if (MgnlContext.getUser().hasRole(CioneRoles.EMPLEADO_CIONE_BANNER))
				return false;
			if (MgnlContext.getUser().hasRole("superuser")) {
				return true;
			}
			
			List<String> listado = new ArrayList<>();
			Node nodeRolesAllowed = contentRolesAllowed.getJCRNode();
			List<Node> childrenList = NodeUtil.asList(NodeUtil.getNodes(nodeRolesAllowed));
			for (Node node : childrenList) {
				Property roleAllowedProperty = PropertyUtil.getPropertyOrNull(node, "rol");
				if (roleAllowedProperty != null) {
					Node role = SessionUtil.getNodeByIdentifier("userroles", roleAllowedProperty.getString());
					// existe el rol (no se ha borrado ni nada deso)
					if (role != null) {
						listado.add(role.getName());
						log.debug("ROL = " + role.getName());
						if (MgnlContext.getUser().hasRole(role.getName())) {
							result = true;
						}
					}
				}
			}
			
		} catch (Exception e) {
			log.debug(e.getMessage());
			//log.debug("rolFilter field is empty");
			result = false;
		}
		
		return result;
	}
	
	public boolean hasPermissionsCampana(String campana) {
		boolean result = false;
		String flag = "es";
		//comprobamos si no tiene acceso por rol de empleado
		if (MgnlContext.getUser().hasRole(CioneRoles.EMPLEADO_CIONE_BANNER))
			return false;
		if (MgnlContext.getUser().hasRole("superuser")) {
			return true;
		}
		
		
		//campañas
		if (campana.equals("all")) {
			if (MgnlContext.getUser().hasRole(CioneRoles.OPTCAN) 
				|| MgnlContext.getUser().hasRole(CioneRoles.OPTMAD)
				|| MgnlContext.getUser().hasRole(CioneRoles.CLIENTE_MONTURAS)
				|| MgnlContext.getUser().hasRole(CioneRoles.OPTICAPRO)) {
				return false;
			} else {
				return true;
			}
		}
		
		if (MgnlContext.getUser().hasRole(CioneRoles.SICIO_CIONE_PORTUGAL)
				|| MgnlContext.getUser().hasRole(CioneRoles.CLIENTES_PORTUGAL)
				|| MgnlContext.getUser().hasRole(CioneRoles.CLIENTE_PORTUGAL_VCO)
				|| MgnlContext.getUser().hasRole(CioneRoles.PORLENS)) {
			flag = "pt";
		} else if (MgnlContext.getUser().hasRole(CioneRoles.OPTCAN)
				|| MgnlContext.getUser().hasRole(CioneRoles.OPTMAD)
				|| MgnlContext.getUser().hasRole(CioneRoles.CLIENTE_MONTURAS)) {
			flag = "optofive";
		} else if (MgnlContext.getUser().hasRole(CioneRoles.OPTICAPRO)) {
			flag = "opticapro";
		}
		if (campana.equals(flag)) {
			return true;
		}

		return result;
	}

}
