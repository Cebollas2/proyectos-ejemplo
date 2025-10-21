package com.magnolia.cione.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.KpisDTO;
import com.magnolia.cione.service.MiddlewareService;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.cms.util.QueryUtil;
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.ContentMap;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.jcr.util.SessionUtil;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class ComunicacionModel <RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	private static final Logger log = LoggerFactory.getLogger(ComunicacionModel.class);
	//private final TemplatingFunctions templatingFunctions;
	private static final String CHECK_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	@Inject
	private MiddlewareService middlewareService; 
	
	@Inject
	public ComunicacionModel(Node content, RD definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
		//this.templatingFunctions = templatingFunctions;
	}

	 
	public KpisDTO getKpis() {	
		KpisDTO data = middlewareService.getKpis();
		if(data == null) {
			data = new KpisDTO();
		}
		return data; 
	}
	
	public boolean checkFechaInicio(String fechaInicioAsString) {
		boolean result = true;
		Date fechaInicio = CioneUtils.parseStringToDate(fechaInicioAsString, CHECK_DATE_FORMAT);
		if (fechaInicio != null && fechaInicio.after(new Date())) {
			result = false;
		}
		return result;
	}

	public boolean checkFechaFin(String fechaFinAsString) {
		boolean result = true;
		Date fechaFin = CioneUtils.parseStringToDate(fechaFinAsString, CHECK_DATE_FORMAT);
		if (fechaFin != null && !fechaFin.after(new Date())) {
			result = false;
		}
		return result;
	}
	
	public ArrayList<String> getFolders() {
		Property pp;
		ArrayList<String> nodes = new ArrayList<String>();
		try {
			pp = content.getProperty("carpeta");
			String query = "SELECT * from [nt:base] AS t WHERE ISDESCENDANTNODE('/descargas')";
			NodeIterator resultQuery = QueryUtil.search("rsc-documentos", query);
			while (resultQuery.hasNext()) {
				Node node = resultQuery.nextNode();
				String name = node.getPrimaryNodeType().getName();
				if (name.equals("mgnl:folder"))
					nodes.add(node.getIdentifier());
			}
			Collections.reverse(nodes);
		} catch (PathNotFoundException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return nodes;
	}
	
	public boolean hasPermissions(ContentMap contentRolesAllowed) {
		boolean result = false;
		try {

			// el superusuario siempre lo puede ver porque estará en edición
			if (MgnlContext.getUser().hasRole("superuser")) {
				return true;
			}

			List<String> rolesAllowedForComponent = getRolesAllowed(contentRolesAllowed);
			boolean enc = false;
			int i = 0;
			while (!enc && i < rolesAllowedForComponent.size()) {
				if (MgnlContext.getUser().hasRole(rolesAllowedForComponent.get(i))) {
					enc = true;
				}
				i++;
			}
			if (enc) {
				result = true;
			}
		} catch (RepositoryException e) {
			log.error("Error obteniendo permisos para el componente", e);
		}
		return result;
	}
	public List<String> getRolesAllowed(ContentMap contentRolesAllowed) throws RepositoryException {
		List<String> result = new ArrayList<>();
		Node nodeRolesAllowed = contentRolesAllowed.getJCRNode();
		List<Node> childrenList = NodeUtil.asList(NodeUtil.getNodes(nodeRolesAllowed));
		for (Node node : childrenList) {
			Property roleAllowedProperty = PropertyUtil.getPropertyOrNull(node, "rol");
			if (roleAllowedProperty != null) {
				Node role = SessionUtil.getNodeByIdentifier("userroles", roleAllowedProperty.getString());
				// existe el rol (no se ha borrado ni nada deso)
				if (role != null) {
					result.add(role.getName());
				}
			}
		}
		return result;
	}

	public String getRolesAsString(ContentMap contentRolesAllowed) throws RepositoryException {
		List<String> roles = getRolesAllowed(contentRolesAllowed);
		return String.join(",", roles);
	}
	 
}
