package com.magnolia.cione.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.utils.CioneUtils;
import com.magnolia.cione.utils.CypherUtils;

import info.magnolia.cms.security.SecuritySupport;
import info.magnolia.cms.security.User;
import info.magnolia.cms.security.UserManager;
import info.magnolia.context.MgnlContext;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.repository.RepositoryConstants;
import info.magnolia.templating.functions.TemplatingFunctions;

public class ListEmployeeModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	private SecuritySupport securitySupport;
	private static final Logger log = LoggerFactory.getLogger(ListEmployeeModel.class);
	
	private List<User> employees;
	
	@Inject
	public ListEmployeeModel(Node content, RD definition, RenderingModel<?> parent,
			TemplatingFunctions templatingFunctions, SecuritySupport securitySupport) {
		super(content, definition, parent);
		this.securitySupport = securitySupport;
	}
	
	@Override
	public String execute() {
		
		employees = new ArrayList<>();
		UserManager um = securitySupport.getUserManager("public");
		
		String admin = getAdminUserString();
		
		// Buscamos todos los empleados que estan asociados al usuario actual y los incluimos en una lista
		String query = "SELECT * FROM [mgnl:user] WHERE associatedTo = '" + admin + "' order by name";
		try {
			Session session = MgnlContext.getJCRSession(RepositoryConstants.USERS);
			Query q = session.getWorkspace().getQueryManager().createQuery(query, javax.jcr.query.Query.JCR_SQL2);
			//q.setLimit(params.getSize());
			//q.setOffset(params.getPagina());
			QueryResult queryResult = q.execute();
			NodeIterator nodes = queryResult.getNodes();
			while (nodes.hasNext()) {
				Node userNode = nodes.nextNode();
				User user = um.getUser(userNode.getProperty("name").getString());
				if (user != null)
					employees.add(user);
			}
		} catch (RepositoryException e) {
			log.error("Error al buscar los empleados asociados al usuario actual", e);
		} 
		
		return super.execute();
	}
	
	/**
	 * Genera un token cifrando el id de cliente, para evitar que se envien identificadores de otros socios
	 * desde la parte front
	 * @return Un token cifrado con el id del cliente
	 */
	public String getToken() {
		return CypherUtils.encode(CioneUtils.getidClienteFromNumSocio(getAdminUserString()));
	}
	
	public List<User> getEmployees(){
		return employees;
	}
	
	private String getAdminUserString() {
		
		String res = MgnlContext.getUser().getName();
		
		String associatedTo = MgnlContext.getUser().getProperty("associatedTo");
		
		if(!StringUtils.isEmpty(associatedTo) && !res.endsWith("00") && MgnlContext.getUser().hasRole("empleado_cione_perfil_admin")) {
			res = associatedTo;
		}
		
		// impersonate
		if (MgnlContext.getUser().hasRole(CioneConstants.ROLE_CIONE_SUPERUSER) || MgnlContext.getUser().hasRole(CioneConstants.ROLE_OPTOFIVE_SUPERUSER)) {
			String idToSimulate = MgnlContext.getUser().getProperty(CioneConstants.IMPERSONATE_FIELD_ID_SOCIO);
			if (idToSimulate != null && !idToSimulate.isEmpty()) {
				res = idToSimulate;
			}
		}
		
		return res;
		
	}

}
