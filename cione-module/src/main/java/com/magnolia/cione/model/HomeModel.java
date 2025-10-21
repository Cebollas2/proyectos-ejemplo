package com.magnolia.cione.model;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.constants.CioneRoles;
import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.service.AuthService;
import com.magnolia.cione.service.MiddlewareService;
import com.magnolia.cione.utils.CioneUtils;
import com.magnolia.cione.utils.HMAC;

import info.magnolia.cms.security.Permission;
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.ContentMap;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.jcr.util.SessionUtil;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.repository.RepositoryConstants;

public class HomeModel <RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {
	
	private static final Logger log = LoggerFactory.getLogger(HomeModel.class);

	@Inject
	private MiddlewareService middlewareService;
	
	@Inject
	private AuthService authService;


	public HomeModel(Node content, RD definition, RenderingModel<?> parent) {
		super(content, definition, parent);
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
		return null;
	}
	
	public String getNumSocioEncrypt() {
		
		return authService.getNumSocioEncrypt();
	}
	
	public UserERPCioneDTO getUserFromMagnolia() {
		String idUser = CioneUtils.getIdCurrentClient();
		if (!idUser.equals("superuser")) {
			if (idUser.endsWith("00"))
				return middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
			else {
				return middlewareService.getUserFromERPEmployee(idUser);
			}
		}
		//es usuario superuser o alguno de magnolia
		return null;
	}
	
	public String getIdUsuario() {
		 return CioneUtils.getIdCurrentClient();
		
		
		//String idUsuario = MyShopUtils.getUserName();
//		String idUsuario = "00000001";005080000
//		
//		String cabecerajson = MyshopConstants.cabeceraWebAudio;
//		String password = configService.getConfig().getAudioPsw();
//		
//		String cabecera = Base64.getEncoder().encodeToString(cabecerajson.getBytes());
//		String cargaUtiljson = "{\"user_id\":\""+ idUsuario +"\"}";
//		String cargaUtil=Base64.getEncoder().encodeToString(cargaUtiljson.getBytes());
//		cargaUtil = cargaUtil.replaceAll("=", "");
//		cargaUtil = cargaUtil.replaceAll("\\+", "-");
//		cargaUtil = cargaUtil.replaceAll("/", "_");
//		
//		String firma = HMAC.calSHA256(password, cabecera + "." +cargaUtil);
//		firma = firma.replaceAll("=", "");
//		firma = firma.replaceAll("\\+", "-");
//		firma = firma.replaceAll("/", "_");
//		//String firma = "UZedMsYgecBIRCUm-OIH1sLAmUV2DoMcNIYALHK3h3I";
//		
		//String url = "http://audiologia.sfe.es/?rest_route=/mycione/login/autologin&token=" + cabecera + "." + cargaUtil + "." + firma;
		//String url = "http://audiologia.sfe.es/?rest_route=/mycione/login/autologin&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiMDAwMDAwMDEifQ.UZedMsYgecBIRCUm-OIH1sLAmUV2DoMcNIYALHK3h3I";
		
		//return url;
		
		
	}
	
	public byte[] convertirSHA256(String password, String code) {
		byte[] hmacSha256 = null;
		try {
			hmacSha256 = HMAC.calcHmacSha256(password.getBytes("UTF-8"), code.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hmacSha256;
	}
	
	public String convertirSHA256_str(String password, String code) {
		String sha256_str = HMAC.calSHA256(password, code);
		
		return sha256_str;
		
		//String password = '201703281329'

		/*MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			digest.update(password.getBytes("UTF-8")); //mudar para "UTF-8" se for preciso

			byte[] passwordDigest = digest.digest();
			sha256_str = Base64.getEncoder().encodeToString(passwordDigest);
			
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
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
		//if (MgnlContext.getUser().hasRole(CioneRoles.EMPLEADO_CIONE_BANNER))
			//return false;
		if (MgnlContext.getUser().hasRole("superuser")) {
			return true;
		}
		
		
		//campanas
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
	
	/*solo aplica a empleados*/
	private boolean hasPermissionEspecialLink(Node nodePermisos) {
		try {
			if (!CioneUtils.isEmpleado(MgnlContext.getUser().getName()))
				return true; 
			else {
				
					if (nodePermisos.hasNode("especialLink") && nodePermisos.getNode("especialLink").hasProperty("field")) {
						String field = nodePermisos.getNode("especialLink").getProperty("field").getValue().getString();
						switch (field) {
						case "enlaceUniversity": {
							if (MgnlContext.getUser().hasRole("empleado_cione_cione_university"))
								return false;
							else
								return true;
						}
						case "enlaceAudio": {
							if (MgnlContext.getUser().hasRole("empleado_cione_myom"))
								return false;
							else
								return true;
						}
						case "enlaceForo": {
							if (MgnlContext.getUser().hasRole("empleado_cione_comunidad"))
								return false;
							else
								return true;
						}
						case "enlaceServicios": {
							if (MgnlContext.getUser().hasRole("empleado_cione_servicios"))
								return false;
							else
								return true;
						}
						default:
							return true;
						}
					} else
						return true;
				
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return true;
		}
	}
	
	public long getLastPosition() {
		long tiempoInicio = System.currentTimeMillis();
		long lastposition = 0;
		Node nodeMenu;
		try {
			nodeMenu = content.getNodes("menu").nextNode();
			NodeIterator nodes = nodeMenu.getNodes();
			while(nodes.hasNext()) {
				Node current = nodes.nextNode();
				
				if (!current.hasProperty("internalLink") 
					|| (current.getProperty("internalLink") == null)
					|| (current.getProperty("internalLink").getValue().toString().isEmpty())
					|| (hasReadPermission(current.getProperty("internalLink").getValue().getString()))) {
					
					if (current.hasNode("permisos")) {
						Node nodePermisos = current.getNode("permisos");
						String field = nodePermisos.getProperty("field").getValue().getString();
						if (field.equals("campaing")) {
							String campana = nodePermisos.getProperty("campaing").getValue().getString();
							
							if (hasPermissionsCampana(campana) && hasPermissionEspecialLink(current))
								lastposition++;
						} else if (field.equals("roles")) {
							Node nodeRoles = nodePermisos.getNode("roles");
							NodeIterator nodeRol = nodeRoles.getNodes();
							while(nodeRol.hasNext()) {
								Node rol = nodeRol.nextNode();
								Property roleAllowedProperty = PropertyUtil.getPropertyOrNull(rol, "rol");
								Node role = SessionUtil.getNodeByIdentifier("userroles", roleAllowedProperty.getString());
								if (MgnlContext.getUser().hasRole(role.getName()) && hasPermissionEspecialLink(current)) {
									lastposition++;
								}
							}
						}
					}
					
				}
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
		}
		
		//quitar despues de configurar
		
		
		long tiempoFin = System.currentTimeMillis();
		log.info("Ha tardado " + (tiempoFin - tiempoInicio) + " lastposition= " + lastposition);
		return lastposition;
	}
	
	/*private boolean hasPermissionInternalLink(Node current) {
		// && (hasReadPermission(current.getProperty("internalLink").getValue().getString()))
		try {
			if (current.hasProperty("internalLink") && (current.getProperty("internalLink") != null) && !current.getProperty("internalLink").getValue().toString().isEmpty()) {
				return hasReadPermission(current.getProperty("internalLink").getValue().getString());
			} else
				return true;
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			return true;
		}
	}*/
	
    public static boolean hasReadPermission(String pageLink) {
        if (pageLink == null || pageLink.trim().isEmpty()) {
            return false;
        }
        
        try {
            
            Session session = MgnlContext.getJCRSession(RepositoryConstants.WEBSITE);
            Node pageNode = null;
            
            // Intentar obtener el nodo por UUID primero, luego por path
            try {
                pageNode = session.getNodeByIdentifier(pageLink);
            } catch (RepositoryException e) {
                // Si no es UUID, intentar como path
                try {
                    pageNode = session.getNode(pageLink);
                } catch (RepositoryException ex) {
                    return false;
                }
            }
            
            if (pageNode == null) {
                return false;
            }
            
            // Verificar permisos de lectura
            return hasPermission(pageNode, Permission.READ);
            
        } catch (RepositoryException e) {
            log.error("Error verificando permisos para página: {}", pageLink, e);
            return false;
        }
    }
    
    private static boolean hasPermission(Node node, long permission) {
        try {
            String nodePath = NodeUtil.getPathIfPossible(node);
            return MgnlContext.getUser().hasRole("superuser") || 
                   MgnlContext.getAccessManager(RepositoryConstants.WEBSITE)
                           .isGranted(nodePath, permission);
        } catch (Exception e) {
            log.error("Error verificando permiso específico", e);
            return false;
        }
    }

}
