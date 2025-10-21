package com.magnolia.cione.controller;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.ValueFormatException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.exceptions.CioneException;
import com.magnolia.cione.pur.CommandsUtils;
import com.magnolia.cione.service.AuthService;
import com.magnolia.cione.service.EmailService;

import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.NodeNameHelper;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.objectfactory.Components;
import info.magnolia.repository.RepositoryManager;
import info.magnolia.repository.definition.WorkspaceMappingDefinition;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;
//import io.swagger.annotations.Api;


//@Api(value = "/users/news/v1")
@Path("/users/news/v1")
public class UserNewsEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {
	
	private static final Logger log = LoggerFactory.getLogger(UserNewsEndpoint.class);

	@Inject
	private TemplatingFunctions templatingFunctions;
	
	@Inject
	private NodeNameHelper nodeNameHelper;
	
	@Inject
	private AuthService authService;
	
	@Inject
	private EmailService emailService;
	
	@Inject
	private CommandsUtils commandsUtils;

	@Inject
	protected UserNewsEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}
	
	/**
	 * @author amperez.rivera
	 * Método encargado de marcar al usuario como lector de una noticia con un id en particular
	 * @param userId
	 * @param newsId
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 */
	
	@GET
	@Path("/read")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public void setUserReadNew(@QueryParam("userId") String userId,@QueryParam("newsId") String newId) {
		
		try {
			Node newsNode = templatingFunctions.nodeById(newId, CioneConstants.NOTICIAS_WORKSPACE);
			
			if((newsNode != null) 
					&& (newsNode.hasProperty("notificar")) 
					&& (newsNode.getProperty("notificar").getString().equals("true"))) {
				
				authService.sendToAuthorAuditoryUser(userId, newsNode.getIdentifier());
				
			}
			
			
			
			/*Session sessionUsers = MgnlContext.getJCRSession(CioneConstants.CIONE_USERS_WORKSPACE);
			
			if (!sessionUsers.nodeExists(path)) {
				createNodeCioneUsers(numSocio);
			}
			
			Node node = sessionUsers.getNode(path);
			
			if((node != null) 
				&& (newsNode != null) 
				&& (newsNode.hasProperty("notificar")) 
				&& (newsNode.getProperty("notificar").getString().equals("true"))) {
			
				Node auditoriaNode = null;
				if(node.hasNode(CioneConstants.AUDITORIA_USER_NODE_NAME)) {
					auditoriaNode = node.getNode(CioneConstants.AUDITORIA_USER_NODE_NAME);
					
					boolean hasNode = alreadyHasNew(newId, auditoriaNode);
					
					if(!hasNode) {
						Node readNewsNode = auditoriaNode.addNode(nodeNameHelper.getUniqueName(auditoriaNode, "0"), CioneConstants.CONTENT_NODE_TYPE);
						readNewsNode.setProperty(CioneConstants.AUDITORIA_ID_PROPERTY, newsNode.getIdentifier());
						authService.sendToAuthorAuditoryUser(userId, newsNode.getIdentifier());
						//userNode.getSession().save();
					}
				} else {
					auditoriaNode = node.addNode(CioneConstants.AUDITORIA_USER_NODE_NAME, CioneConstants.CONTENT_NODE_TYPE);
					Node readNewsNode = auditoriaNode.addNode(nodeNameHelper.getUniqueName(auditoriaNode, "0"), CioneConstants.CONTENT_NODE_TYPE);
					readNewsNode.setProperty(CioneConstants.AUDITORIA_ID_PROPERTY, newsNode.getIdentifier());
					authService.sendToAuthorAuditoryUser(userId, newsNode.getIdentifier());
					//userNode.getSession().save();
				}
			
			}*/
			
			//sessionUsers.save();
		}catch(Exception e) {
			log.error(e.getMessage());
		}		
	}
	
	
	/**
	 * @author amperez.rivera
	 * Método encargado de determinar si un usuario concreto ha leído una noticia
	 * @param userId
	 * @param newId
	 * @return Devuelve "true" si el usuario ha leído la noticia. Devuelve false en caso contrario
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 */
	
	
	@GET
	@Path("/isRead")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response isRead(@QueryParam("userId") String userId,@QueryParam("newsId") String newId) {
		try{	
			Node user = templatingFunctions.nodeById(userId, CioneConstants.USERS_WORKSPACE);
			
			String numSocio = user.getName();
			String firstLevel = numSocio.substring(0,1);
			String secondLevel = numSocio.substring(0,2);
			String path = "/"+CioneConstants.PUBLIC_NODE+ "/" + firstLevel+ "/" + secondLevel+"/"+numSocio;
			Session sessionUsers = MgnlContext.getJCRSession(CioneConstants.CIONE_USERS_WORKSPACE);
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
								return Response.ok(true).build();
							}
						}
					}
				}
			}
			sessionUsers.save();
			return Response.ok(false).build();
		} catch(Exception e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build(); 
		}
	}
	
	
	/**
	 * @author amperez.rivera
	 * Método auxiliar encargado de determinar si, bajo el nodo "auditoría" ya hay una referencia a la noticia que se intenta dar de alta
	 * @param newId
	 * @param auditoriaNode
	 * @return true En caso de que la tenga, false en caso contrario
	 * @throws RepositoryException
	 * @throws ValueFormatException
	 * @throws PathNotFoundException
	 */
	
	private boolean alreadyHasNew(String newId, Node auditoriaNode) {
		
		try {
		
			boolean hasNode = false;
			NodeIterator nIt = auditoriaNode.getNodes();
			while(nIt.hasNext() && !hasNode) {
				Node current = nIt.nextNode();
				if(current.hasProperty(CioneConstants.AUDITORIA_ID_PROPERTY) && current.getProperty(CioneConstants.AUDITORIA_ID_PROPERTY).getString().equals(newId)) {
					hasNode = true;
				}
			}
			return hasNode;
			
		} catch(Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	
	private void createNodeCioneUsers(String numSocio) {
		try {
			
			String firstLevel = numSocio.substring(0,1);
			String secondLevel = numSocio.substring(0,2);
			RepositoryManager repositoryManager = Components.getComponent(RepositoryManager.class);
			WorkspaceMappingDefinition mappingUsers = repositoryManager.getWorkspaceMapping(CioneConstants.CIONE_USERS_WORKSPACE);
			Session sessionUsers = repositoryManager.getSession(mappingUsers.getLogicalWorkspaceName(), new SimpleCredentials("anonymous", "anonymous".toCharArray()));
			
			//Session sessionUsers = MgnlContext.getJCRSession(CioneConstants.CIONE_USERS_WORKSPACE);
			//Node userNode = templatingFunctions.nodeByPath("/", CioneConstants.CIONE_USERS_WORKSPACE);
			Node nodeRoot = sessionUsers.getRootNode();
			
			//Node newsNode = sessionUsers.getNodeByIdentifier(numSocio);
			String path = nodeRoot.getPath();
			if (!nodeRoot.hasNode(CioneConstants.PUBLIC_NODE)) {
				nodeRoot = nodeRoot.addNode(CioneConstants.PUBLIC_NODE, CioneConstants.CONTENT_NODE_TYPE);
				path = nodeRoot.getPath();
			} else {
				nodeRoot = nodeRoot.getNode(CioneConstants.PUBLIC_NODE);
				path = nodeRoot.getPath() + "/" +firstLevel;
			}
			
			
			if (!nodeRoot.hasNode(firstLevel)) {
				nodeRoot = nodeRoot.addNode(firstLevel, CioneConstants.CONTENT_NODE_TYPE);
			} else {
				nodeRoot = nodeRoot.getNode(firstLevel);
				path = nodeRoot.getPath() + "/" +secondLevel;
			}
			
			if (!nodeRoot.hasNode(secondLevel)) {
				nodeRoot = nodeRoot.addNode(secondLevel, CioneConstants.CONTENT_NODE_TYPE);
			} else {
				nodeRoot = nodeRoot.getNode(secondLevel);
				path = nodeRoot.getPath()+"/"+numSocio;
			}
			
			if (!nodeRoot.hasNode(numSocio)) {
				nodeRoot = nodeRoot.addNode(numSocio, CioneConstants.CONTENT_NODE_TYPE);
			}
			nodeRoot.setProperty("legal", false);
			
			sessionUsers.save();
			
			long tiempoInicio = System.currentTimeMillis();
			log.debug("publicando el nodo : " + path);
			commandsUtils.publishNodeWithoutWorkflow(path, CioneConstants.CIONE_USERS_WORKSPACE, true);
			long tiempoFin = System.currentTimeMillis();
			log.info("Ha tardado = " + (tiempoFin - tiempoInicio));
			
		} catch (Exception e) {
			log.error("Error al crear el nodo en cione-users");
			String subject = "Error al crear el nodo en cione-users";
	    	String mailto = "msanchezp@atsistemas.com";
	    	String texto = "Se ha producido un error al añadir al usuario: " + numSocio + " al workspace cione-users para aceptar condiciones comerciales";
	    	try {
				emailService.sendEmail(subject, texto, mailto);
			} catch (CioneException ex) {
				log.error("ERROR en el envio de mail");
			}
		}
	}
}
