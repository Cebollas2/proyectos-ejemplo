package com.magnolia.cione.model;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.dto.DireccionesDTO;
import com.magnolia.cione.service.MiddlewareService;
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

public class RegisterUpdateEmployeeModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	private static final Logger log = LoggerFactory.getLogger(RegisterUpdateEmployeeModel.class);
	
	private SecuritySupport securitySupport;

	@Inject
	private MiddlewareService middlewareService;
	
	private User employee;
	private String nextid;
	
	@Inject
	public RegisterUpdateEmployeeModel(Node content, RD definition, RenderingModel<?> parent,
			TemplatingFunctions templatingFunctions, SecuritySupport securitySupport) {
		super(content, definition, parent);
		this.securitySupport = securitySupport;
	}
	
	@Inject
	private TemplatingFunctions templatingFunctions;
	
	@Override
	public String execute() {
		
		if (!templatingFunctions.isEditMode()) {
			
			String id = MgnlContext.getParameter("id");
			
			if(StringUtils.isNotEmpty(id)) {
				
				UserManager um = securitySupport.getUserManager("public");
				User employeeUrl = um.getUser(id);
				
				if (employeeUrl != null) {
					
					if (isValidAdmin(employeeUrl)) {
						employee = employeeUrl;
					}
				}
				
			}else {
				nextid = getNextEmployee();
			}
		}
		
		return super.execute();
	}
	
	public DireccionesDTO getDirecciones() {
		
		DireccionesDTO data = middlewareService.getDirecciones(CioneUtils.getIdCurrentClientERP());
		
		if (data == null) {
			data = new DireccionesDTO();
		} 
		
		return data;
	}
	
	/**
	 * Comprueba que el usuario que se esta intentando editar, pertenece al socio actual logado.
	 * Tambien devuelve verdadero si es una creacion en lugar de edicion
	 * @return Verdadero si es un empleado asociado al usuario actual logado o no hay parametro en la url
	 */
	public boolean isValidEmployee() {
		boolean result = true;
		
		String id = MgnlContext.getParameter("id");
		if(id != null && employee == null) {
			result = false;
		}
		
		if(employee != null) {
			
			// No se puede editar un socio, solo empleados
			if(CioneUtils.isSocio(employee.getName())) {
				result = false;
				
			} else {
				// El identificador base de empleado y socio debe coincidir
				String userId = CioneUtils.getidClienteFromNumSocio(getAdminUserString());
				String employeeId = employee.getName().substring(0, userId.length());

				if(!userId.equals(employeeId)) {
					result = false;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Genera un token cifrando el id de cliente, para evitar que se envien identificadores de otros socios
	 * desde la parte front
	 * @return Un token cifrado con el id del cliente
	 */
	public String getToken() {
		return CypherUtils.encode(CioneUtils.getidClienteFromNumSocio(getAdminUserString()));
	}

	public User getEmployee() {
		return employee;
	}

	public String getNextid() {
		return nextid;
	}
	
	private String getNextEmployee() {
		
		String actualID = CioneUtils.getIdCurrentClientERP();
		String res = "";
		//UserManager um = securitySupport.getUserManager("public");
		
		String numSocio = getAdminUserString(); 
		if (!CioneUtils.isSocio(getAdminUserString())){
			numSocio = CioneUtils.getNumSocioFromCliente(CioneUtils.getidClienteFromNumSocio(getAdminUserString()));
		}
		
		String query = "SELECT * FROM [mgnl:user] WHERE associatedTo = '" + numSocio + "' order by name";
		
		try {
			
			Session session = MgnlContext.getJCRSession(RepositoryConstants.USERS);
			Query q = session.getWorkspace().getQueryManager().createQuery(query, javax.jcr.query.Query.JCR_SQL2);
			QueryResult queryResult = q.execute();
			NodeIterator nodes = queryResult.getNodes();
			String lastUser = null;
			
			while (nodes.hasNext()) {
				Node userNode = nodes.nextNode();
				lastUser = userNode.getName();
			}
			
			String twolastUser = lastUser != null  && lastUser.length() > 2 ? lastUser.substring(lastUser.length() - 2) : lastUser;
			String twolastMain = getAdminUserString().length() > 2 ? getAdminUserString().substring(getAdminUserString().length() - 2) : getAdminUserString();
			
			int userNum = lastUser != null ? Integer.parseInt(twolastUser) : Integer.parseInt(twolastMain);
			userNum++;
			String subNum = String.valueOf(userNum).length() == 2 ? String.valueOf(userNum) : "0".concat(String.valueOf(userNum));
			res = actualID.concat(subNum);
			
		} catch (RepositoryException e) {
			log.error("RegisterUpdateEmployeeModel: Error al buscar los empleados asociados al usuario actual", e);
		}
		
		return res;
	}
	
	private boolean isValidAdmin(User u) {
		
		boolean res = false;
		
		String asociateTo = u.getProperty("associatedTo");
		
		// El socio es el que esta editando
		if(asociateTo.equals(getAdminUserString())) {
			res = true;
		}else {
			
			// Es un empleado administrado
			String idinicialsocio = getAdminUserString().substring(0, getAdminUserString().length() - 2);
			String idinicialempleado = u.getName().substring(0, getAdminUserString().length() - 2);
			
			if(idinicialsocio.equals(idinicialempleado)) {
				if(MgnlContext.getUser().hasRole("empleado_cione_perfil_admin")) {
					res = true;
				}
			}
			
		}
		
		return res;
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
	
	public boolean isOptofive() {
		boolean result = false;
		try {
			String idUsuario = CioneUtils.getIdCurrentClient();
			UserManager um = securitySupport.getUserManager("public");
			User employeeUrl = um.getUser(idUsuario);
			if (employeeUrl.hasRole(CioneConstants.OPTCAN) 
				|| employeeUrl.hasRole(CioneConstants.CLIENTE_MONTURAS))
				result = true;
		} catch (Exception e) {
			log.error("Error al buscar el rol de usuario ");
			return false;
		}
		return result;
	}
	
	public boolean isOpticaPro() {
		boolean result = false;
		try {
			String idUsuario = CioneUtils.getIdCurrentClient();
			UserManager um = securitySupport.getUserManager("public");
			User employeeUrl = um.getUser(idUsuario);
			if (employeeUrl.hasRole(CioneConstants.OPTICAPRO))
				result = true;
		} catch (Exception e) {
			log.error("Error al buscar el rol de usuario ");
			return false;
		}
		return result;
	}
	
	public String getMail() {
		String mail ="";
		try {
			mail = employee.getProperty("email");
		} catch (Exception e) {
			log.debug("No tiene mail");
		}
		
		return mail;
	}

}
