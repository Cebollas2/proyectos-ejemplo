package com.magnolia.cione.pur;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import info.magnolia.cms.security.User;
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.PropertyUtil;

public class NodeUtilities {
	
	public Node getNodeIfPossible(String path, String workspace) {
		try {
			Session sesion = MgnlContext.getJCRSession(workspace);
			return sesion.getRootNode().getNode(getRelPath(path));
		} catch (RepositoryException e) {
			return null;
		}
	}
	
	public String getRelPath (String path) {
		if(!path.startsWith("/")) {
			return path;
		} else {
			return path.replaceFirst("/", "");
		}
	}
	
	public String getAbsPath (String path) {
		if(path.startsWith("/")) {
			return path;
		}
		return "/" + path;
	}
	
	public boolean checkOwner(Node nodeToEvaluate, User user) {
		Property tmpProp = PropertyUtil.getPropertyOrNull(nodeToEvaluate, NodeTypes.Created.CREATED_BY);
		try {
			return tmpProp != null && tmpProp.getString().equals(user.getName());
		} catch (RepositoryException e) {
			return false;
		}
	}
	
	public boolean checkIfNodeExists(String path, String workspace) {
		try {
			Session sesion = MgnlContext.getJCRSession(workspace);
			sesion.getNode(getAbsPath(path));
		} catch(RepositoryException e) {
			return false;
		}
		return true;
	}
	
	public boolean checkIfNodeExistsAndIsNodeType(String path, String workspace, String nodeType) {
		try {
			Session sesion = MgnlContext.getJCRSession(workspace);
			return sesion.getNode(getAbsPath(path)).isNodeType(nodeType);
		} catch(RepositoryException e) {
			return false;
		}
	}

}
