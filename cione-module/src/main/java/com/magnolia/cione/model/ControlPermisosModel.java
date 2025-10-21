package com.magnolia.cione.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;

import org.apache.commons.lang.StringUtils;
import org.apache.jackrabbit.commons.predicate.NodeTypePredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.constants.CioneRoles;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.service.AuthService;
import com.magnolia.cione.service.MiddlewareService;
import com.magnolia.cione.utils.CioneUtils;
import com.magnolia.cione.utils.MyShopUtils;

import info.magnolia.cms.security.SecuritySupport;
import info.magnolia.cms.security.User;
import info.magnolia.cms.security.UserManager;
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.predicate.NodeNamePredicate;
import info.magnolia.jcr.util.ContentMap;
import info.magnolia.jcr.util.NodeNameHelper;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.jcr.util.SessionUtil;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class ControlPermisosModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	private static final Logger log = LoggerFactory.getLogger(ControlPermisosModel.class);
	private static final String CHECK_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	private String userid;
	
	private final TemplatingFunctions templatingFunctions;
	
	@Inject
	private MiddlewareService middlewareService;
	
	@Inject
	private SecuritySupport securitySupport;
	
	@Inject
	private AuthService authService;
	
	@Inject
	NodeNameHelper nodeNameHelper;

	@Inject
	public ControlPermisosModel(Node content, RD definition, RenderingModel<?> parent,
			TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
		this.templatingFunctions = templatingFunctions;
	}
	
	@Override
	public String execute() {
		
		if (!templatingFunctions.isEditMode()) {
			
			userid = MyShopUtils.getUserName();
			
			/*if (MgnlContext.getWebContext().getRequest().getSession().getAttribute("sessionurlprivada") != null) 
				log.debug(MgnlContext.getWebContext().getRequest().getSession().getAttribute("sessionurlprivada").toString());
			else
				log.debug("VACIO");*/
			
		}else {
			userid = "";
		}
		
		return super.execute();
	}
	
	public String getUserId() {
		
		return userid.isEmpty() ? "" : userid.substring(0, userid.length() - 2);
	}

	public List<String> getRolesAllowed(ContentMap contentRolesAllowed) throws RepositoryException {
		List<String> result = new ArrayList<>();
		Node nodeRolesAllowed = contentRolesAllowed.getJCRNode();
		List<Node> childrenList = NodeUtil.asList(NodeUtil.getNodes(nodeRolesAllowed));
		for (Node node : childrenList) {
			Property roleAllowedProperty = PropertyUtil.getPropertyOrNull(node, "rol");
			if (roleAllowedProperty != null) {
				try {
					Node role = SessionUtil.getNodeByIdentifier("userroles", roleAllowedProperty.getString());
					// existe el rol (no se ha borrado ni nada deso)
					if (role != null) {
						result.add(role.getName());
					}
				} catch (ValueFormatException e) {
					log.error(e.getMessage() + " idRol" + roleAllowedProperty.getString(), e);
				} catch (RepositoryException e) {
					log.error(e.getMessage() + " idRol" + roleAllowedProperty.getString(), e);
				} catch (Exception e) {
					log.error(e.getMessage() + " idRol" + roleAllowedProperty.getString(), e);
				}
			}
		}
		return result;
	}

	public String getRolesAsString(ContentMap contentRolesAllowed) throws RepositoryException {
		List<String> roles = getRolesAllowed(contentRolesAllowed);
		return String.join(",", roles);
	}
	
	/**
	 * Devuelve si posee el rol comunicacion o no. Esto se usa para que en caso
	 * de que un usuario tenga este rol significa que <strong>NO tendra acceso al apartado
	 * de comunicaciones</strong> y por tanto en la cabecera no se deben mostrar notificaciones
	 * de las noticias.
	 * 
	 * @return true si tiene el rol de comunicacion, false en caso contrario
	 */
	public boolean hasRoleComunication() {
		
		return MgnlContext.getUser().hasRole(CioneRoles.EMPLEADO_CIONE_COMUNICACION) ? true : false;
		
	}

	public boolean hasPermissions(ContentMap contentRolesAllowed) {
		boolean enc = false;
		try {

			// el superusuario siempre lo puede ver porque estará en edición
			if (MgnlContext.getUser().hasRole("superuser")) {
				return true;
			}
			if (contentRolesAllowed == null)
				return true;
			List<String> rolesAllowedForComponent = getRolesAllowed(contentRolesAllowed);
			int i = 0;
			while (!enc && i < rolesAllowedForComponent.size()) {
				if (MgnlContext.getUser().hasRole(rolesAllowedForComponent.get(i))) {
					enc = true;
				}
				i++;
			}
			
		} catch (RepositoryException e) {
			log.error("Error obteniendo permisos para el componente", e);
		}
		return enc;
	}
	
/*	public boolean hasPermissionsRoles2(Value[] values) {
		boolean result = false;
		try {
			List<String> listado = new ArrayList<>();
			for (Value value: values) {
				Node role = SessionUtil.getNodeByIdentifier("userroles", value.getString());
				if (role != null) {
					log.debug(role.getName());
					listado.add(role.getName());
				}
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			//log.debug("rolFilter field is empty");
			result = true;
		}
		return result;
	}*/
	
	public boolean hasPermissionsRoles(ContentMap contentRolesAllowed) {
		boolean result = false;
		
		try {
			/*if (MgnlContext.getUser().hasRole(CioneRoles.EMPLEADO_CIONE_BANNER))
				return false;*/
			if (MgnlContext.getUser().hasRole("superuser")) {
				return true;
			}
			
			List<String> listado = new ArrayList<>();
			Node nodeRolesAllowed = contentRolesAllowed.getJCRNode();
			List<Node> childrenList = NodeUtil.asList(NodeUtil.getNodes(nodeRolesAllowed));
			if (childrenList.isEmpty()) //no tiene roles asignados
				return true;
			for (Node node : childrenList) {
				Property roleAllowedProperty = PropertyUtil.getPropertyOrNull(node, "rol");
				if (roleAllowedProperty != null) {
					Node role = SessionUtil.getNodeByIdentifier("userroles", roleAllowedProperty.getString());
					// existe el rol (no se ha borrado ni nada deso)
					if (role != null) {
						listado.add(role.getName());
						//log.debug("ROL = " + role.getName());
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
		/*if (MgnlContext.getUser().hasRole(CioneRoles.EMPLEADO_CIONE_BANNER))
			return false;*/
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

	/**
	 * Devuelve la campaña a la que pertenece un usuario es o pt
	 * 
	 * @return
	 */
	public String getUserCatalogosCampana() {
		String result = "es";
		if (MgnlContext.getUser().hasRole(CioneRoles.SICIO_CIONE_PORTUGAL)
				|| MgnlContext.getUser().hasRole(CioneRoles.CLIENTES_PORTUGAL)
				|| MgnlContext.getUser().hasRole(CioneRoles.CLIENTE_PORTUGAL_VCO)
				|| MgnlContext.getUser().hasRole(CioneRoles.PORLENS)) {
			result = "pt";
		}
		return result;
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

	public List<ContentMap> childrenRecursive(Node parent, String nodeType) throws RepositoryException {
		NodeTypePredicate predicate = new NodeTypePredicate(nodeType, false);
		return asContentMapList(NodeUtil.collectAllChildren(parent, predicate));
	}

	private List<ContentMap> asContentMapList(Iterable<Node> nodes) {
		List<ContentMap> childList = new ArrayList<>();
		for (Node child : nodes) {
			childList.add(new ContentMap(child));
		}
		return childList;
	}
	
	
	/**
	 * @author amperez.rivera
	 * Método encargado de comprobar si un usuario determinado ha leído una noticia.
	 * @param userId
	 * @param newId
	 * @return 1 si la noticia no ha sido leida por el usuario. 0 en caso contrario.
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 */
	
	public Integer hasUserReadNew(String userId, String newId) throws PathNotFoundException, RepositoryException {
		Node user = templatingFunctions.nodeById(userId, "users");
		String numSocio = user.getName();
		String firstLevel = numSocio.substring(0,1);
		String secondLevel = numSocio.substring(0,2);
		String path = "/"+CioneConstants.PUBLIC_NODE+ "/" + firstLevel+ "/" + secondLevel+"/"+numSocio;
		Session sessionUsers = MgnlContext.getJCRSession(CioneConstants.CIONE_USERS_WORKSPACE);
		Integer found =  1;
		
		if (sessionUsers.nodeExists(path)) {
			Node userNode = sessionUsers.getNode(path);
			if(userNode.hasNode(CioneConstants.AUDITORIA_USER_NODE_NAME)) {
				Node auditNode = userNode.getNode(CioneConstants.AUDITORIA_USER_NODE_NAME);
				NodeIterator children = auditNode.getNodes();
				while(children.hasNext()) {
					Node current = children.nextNode();
					NodeIterator subchildren = current.getNodes();
					while(subchildren.hasNext()) {
						Node current2 = subchildren.nextNode();
						if(current2.hasProperty(CioneConstants.AUDITORIA_ID_PROPERTY) && current2.getProperty(CioneConstants.AUDITORIA_ID_PROPERTY).getString().equals(newId)) {
							found = 0;
						}
					}
				}
			}
		}
		
		sessionUsers.save();
		return found;
	}
	
	/**
	 * Método encargado de determinar cuántas noticias tiene por leer el usuario, dada una categoría de noticias
	 * @param userId
	 * @param newGeneralId
	 * @return El número de noticias hijas sin leer por parte del usuario de una categoría de noticias concreta
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 */
	
	public Integer hasUserReadNewGeneral(String userId, String idfirstNews) throws PathNotFoundException, RepositoryException {
		Node firstNewsNode = templatingFunctions.nodeById(idfirstNews, CioneConstants.AUDITORIA_NOTICIAS_NAME);
		Node parent = firstNewsNode.getParent().getParent();//Nodo folder noticia
		NodeIterator parentIt = parent.getNodes();
		ArrayList<String> listGeneralId = new ArrayList<>();
		while(parentIt.hasNext()) {
			Node carpetaNode = parentIt.nextNode();
			if (carpetaNode.isNodeType("mgnl:folder")) {
				//año
				NodeIterator nodeAnioIt = carpetaNode.getNodes();
				while(nodeAnioIt.hasNext()) {
					Node newNode = nodeAnioIt.nextNode(); //Nodo con el bloque de noticia para ese anio
					listGeneralId.add(newNode.getIdentifier());
				}
			}
		}
		
		
		Integer foundToRead = 0;
		
		for(String id: listGeneralId) {
			Node generalNewNode = templatingFunctions.nodeById(id, CioneConstants.AUDITORIA_NOTICIAS_NAME);
			if(generalNewNode != null && generalNewNode.hasNode(CioneConstants.AUDITORIA_NOTICIAS_NAME)) {
				Node noticiasNode = generalNewNode.getNode(CioneConstants.AUDITORIA_NOTICIAS_NAME);
				if(noticiasNode.hasNodes()) {
					NodeIterator it = noticiasNode.getNodes();
					
					while(it.hasNext()) {
						Node newsNode = it.nextNode();
						if(newsNode.hasProperty(CioneConstants.NOTICIA_NOTIFICAR_PROPERTY) && newsNode.getProperty(CioneConstants.NOTICIA_NOTIFICAR_PROPERTY).getBoolean()){
							if(hasUserReadNew(userId, newsNode.getIdentifier()) != 0){
								foundToRead++;
							}
						}	
					}
				}
			}
		}
		
		return foundToRead;
	}
	
	/**
	 * Método encargado de determinar cuántas noticias tiene por leer el usuario, dado el id de la noticia
	 * @param userId
	 * @param newGeneralId
	 * @return El número de noticias hijas sin leer por parte del usuario de una categoría de noticias concreta
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 */
	
	private Integer numberOfUnreadUser(String userId, String idfirstNews) throws PathNotFoundException, RepositoryException {
		long tiempoInicio = System.currentTimeMillis();
		Node generalNewNode = templatingFunctions.nodeById(idfirstNews, CioneConstants.AUDITORIA_NOTICIAS_NAME);
		Integer foundToRead = 0;
		if(generalNewNode != null && generalNewNode.hasNode(CioneConstants.AUDITORIA_NOTICIAS_NAME)) {
			Node noticiasNode = generalNewNode.getNode(CioneConstants.AUDITORIA_NOTICIAS_NAME);
			if(noticiasNode.hasNodes()) {
				NodeIterator it = noticiasNode.getNodes();
				
				while(it.hasNext()) {
					Node newsNode = it.nextNode();
					if(newsNode.hasProperty(CioneConstants.NOTICIA_NOTIFICAR_PROPERTY) && newsNode.getProperty(CioneConstants.NOTICIA_NOTIFICAR_PROPERTY).getBoolean()){
						if(hasUserReadNew(userId, newsNode.getIdentifier()) != 0){
							foundToRead++;
						}
					}	
				}
			}
		}
		long tiempoFin = System.currentTimeMillis();
		//log.info("Ha tardado hasUserReadRoles = " + (tiempoFin - tiempoInicio));
		
		return foundToRead;
	}
	
	/**
	 * @author amperez.rivera
	 * Método encargado de determinar cuántas noticias en general tiene sin leer un usuario concreto
	 * @param userId
	 * @return El número de noticias hijas sin leer por parte del usuario
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 */
	
	public Integer hasUserReadRoles(String userId) throws PathNotFoundException, RepositoryException {
		long tiempoInicio = System.currentTimeMillis();
		Node rootNodeNews = templatingFunctions.nodeByPath("/", CioneConstants.AUDITORIA_NOTICIAS_NAME);
		Integer found = 0;
		
		if(rootNodeNews != null && rootNodeNews.hasNodes()) {
			NodeIterator nIt = rootNodeNews.getNodes();
			
			while(nIt.hasNext()) {
				Node carpetaNode = nIt.nextNode();
				if (carpetaNode.isNodeType("mgnl:folder")) {
					NodeIterator nItyears = carpetaNode.getNodes();
					while(nItyears.hasNext()) {
						Node yearNode = nItyears.nextNode();
						if (yearNode.isNodeType("mgnl:folder")) {
							NodeIterator nItnoticias = yearNode.getNodes();
							while(nItnoticias.hasNext()) {
								Node noticiaNode = nItnoticias.nextNode();
								Integer numberOfUnread = this.numberOfUnreadUser(userId, noticiaNode.getIdentifier());
								if(noticiaNode.hasNode(CioneConstants.NOTICIA_ROLES_NODE)) {
									ContentMap rolesContentMap = templatingFunctions.asContentMap(noticiaNode.getNode(CioneConstants.NOTICIA_ROLES_NODE));
									if(this.hasPermissions(rolesContentMap)) {
										if(numberOfUnread != 0) {
											found += numberOfUnread;
										}
									}
								} else {
									if(numberOfUnread != 0) {
										found += numberOfUnread;
									}
								}
							}
						} else {
							log.info("El segundo nodo debe ser de tipo folder con el año");
						}
					}
				} else {
					log.info("El primer nodo debe ser de tipo folder");
				}
			}
		}
		long tiempoFin = System.currentTimeMillis();
		log.info("Ha tardado hasUserReadRoles = " + (tiempoFin - tiempoInicio));
		return found;
	}

	/**
	 * @author amperez.rivera
	 * Método encargado devolver el enlace completo de una página de noticias que se encuentre en su misma jerarquía
	 * @param pageParent
	 * @return El enlace a la página de noticias
	 * @throws RepositoryException
	 */
	
	public String getNoticiasLink(Node pageParent) throws RepositoryException {
		Node parentNode = pageParent.getParent();
		List<Node> children = NodeUtil.asList(NodeUtil.collectAllChildren(parentNode, new NodeNamePredicate(CioneConstants.AUDITORIA_NOTICIAS_NAME)));
		if(children == null || children.isEmpty()) {
			return StringUtils.EMPTY;
		} else {
			return templatingFunctions.link(children.get(0));
		}
		
	}
	
	public String getPriceDisplayConfiguration() {
		String configuration= getPriceDisplayRol();
		
		String idUser = CioneUtils.getIdCurrentClient();
		UserManager um = securitySupport.getUserManager("public");
		User user = um.getUser(idUser);
		//User user = MgnlContext.getUser();
		if (user != null) {
			try {
				Node userNode = NodeUtil.getNodeByIdentifier("users", user.getIdentifier());
				if (userNode.hasNode(MyshopConstants.VIEWPRICES_USER_NODE_NAME)) {
					Node priceNode = userNode.getNode(MyshopConstants.VIEWPRICES_USER_NODE_NAME);
					configuration = priceNode.getProperty(MyshopConstants.PRICE_CONFIGURATION_PROPERTY).getString();
				}
	//			if (userNode.hasProperty(MyshopConstants.PRICE_CONFIGURATION_PROPERTY)) {
	//				configuration = userNode.getProperty(MyshopConstants.PRICE_CONFIGURATION_PROPERTY).getString();
	//			}
			} catch (RepositoryException e) {
				log.error("Exception while obtaining user's node: ", e);
			}
		}

		return configuration;
	}
	
	public String getPriceDisplayRol() {
		String configuration;
		String idUser = CioneUtils.getIdCurrentClient();
		if (!idUser.equals("superuser")) {
			UserManager um = securitySupport.getUserManager("public");
			User user = um.getUser(idUser);
			//Collection<String> userRoles = MgnlContext.getUser().getRoles();
			Collection<String> userRoles = user.getRoles();
			
			if (userRoles.contains(MyshopConstants.PVP_PVO_ROL)) {
				configuration = MyshopConstants.PVP_PVO;
			} else if (userRoles.contains(MyshopConstants.PVO_ROL)) {
				configuration = MyshopConstants.PVO;
			} else if (userRoles.contains(MyshopConstants.PVP_ROL)) {
				configuration = MyshopConstants.PVP;
			} else if (userRoles.contains(MyshopConstants.HIDDEN_PRICES_ROL)) {
				configuration = MyshopConstants.HIDDEN_PRICES;
			} else {
				configuration = MyshopConstants.PVP_PVO;
			}
		} else {
			configuration = MyshopConstants.PVP_PVO;
		}
		return configuration;
	}
	
	public User getUser() {
		String idUser = CioneUtils.getIdCurrentClient();
		UserManager um = securitySupport.getUserManager("public");
		User user = um.getUser(idUser);
		return user;
	}
	
	public UserERPCioneDTO getUserFromERP() {
		UserERPCioneDTO user =null;
		String idUser = CioneUtils.getIdCurrentClient();
		if (!idUser.equals("superuser")) {
			if (idUser.endsWith("00"))
				user =  middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
			else {
				//empleado
				user =  middlewareService.getUserFromERPEmployee(idUser);
			}
		} 
		//es usuario superuser o alguno de magnolia
		return user;
	}
	
	public String encodeURIComponent(String title) {
		
		String res = "";

	    try{
	    	
	    	res = URLEncoder.encode(title, "UTF-8")
	    			.replaceAll("%3D", "=")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
	      
	    }catch (UnsupportedEncodingException e){
	      res = title;
	    }
        
        return res;
	}
	
	public String getNumSocioEncrypt() {
		
		return authService.getNumSocioEncrypt();
	}
	
}