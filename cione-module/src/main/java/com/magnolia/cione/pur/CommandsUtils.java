package com.magnolia.cione.pur;



import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import info.magnolia.commands.CommandsManager;
import info.magnolia.commands.chain.Command;

public class CommandsUtils {
	
	@Inject
	private CommandsManager commandsManager;
	
	@Inject
	private NodeUtilities nodeUtilities;
	
	@Inject
	private ResponseUtils responseUtils;
	
	@Inject
	private ResponseService responseService;
	
	public Response publishNodeWithInstitutionWorkflow(String path, String workspace, String username) {
		return this.publishNodeWithInstitutionWorkflow(path, workspace, true, username);
	}
	
	public Response publishNodeWithInstitutionWorkflow(String path, String workspace, boolean recursive, String username) {
		return executeCommand("workflow", "activateInstitution", path, workspace, recursive, username);
	}
	
	public Response publishNodeWithoutWorkflow(String path, String workspace) {
		return publishNodeWithoutWorkflow(path, workspace, false);
	}
	
	public Response publishNodeWithWorkflow(String path, String workspace) {
		return publishNodeWithWorkflow(path, workspace, false);
	}
	
	public Response publishNodeWithWorkflow(String path, String workspace, boolean recursive) {
		return executeCommand("workflow", "activate", path, workspace, recursive);
	}
	
	public Response publishNodeWithoutWorkflow(String path, String workspace, boolean recursive) {
		return executeCommand("versioned", "publish", path, workspace, recursive);
	}
	
	public Response deleteNodeWithoutWorkflow(String path, String workspace) {
		return deleteNodeWithoutWorkflow(path, workspace, false);
	}
	
	public Response deleteNodeWithoutWorkflow(String path, String workspace, boolean recursive) {
		return executeCommand("default", "delete", path, workspace, recursive);
	}
	
	public Response deleteNodeWithWorkflow(String path, String workspace, String nodeToDelete) {
		return deleteNodeWithWorkflow(path, workspace, false, nodeToDelete);
	}
	
	public Response deleteNodeWithWorkflow(String path, String workspace, boolean recursive, String nodeToDelete) {
		return executeCommand("default", "markAsDeleted", path, workspace, recursive, null, null, nodeToDelete);
	}
	
	public Response unpublishNodeWithoutWorkflow(String path, String workspace) {
		return unpublishNodeWithoutWorkflow(path, workspace, false);
	}
	
	public Response unpublishNodeWithoutWorkflow(String path, String workspace, boolean recursive) {
		return executeCommand("default", "unpublish", path, workspace, recursive);
	}
	
	public Response restorePreviousVersionCommand(String path, String workspace) {
		return executeCommand("default", "restorePreviousVersion", path, workspace, true, null);
	}
	
	public Response executeCommand(String catalogName, String commandName, String path, String workspace, boolean recursive, String username) {
		return executeCommand(catalogName, commandName, path, workspace, recursive, null, null, null);
	}
	
	public Response executeCommand(String catalogName, String commandName, String path, String workspace, boolean recursive) {
		return executeCommand(catalogName, commandName, path, workspace, recursive, null);
	}
	
	public Response executeCommand(String catalogName, String commandName, String path, String workspace, boolean recursive, String username, UserDTO userDTO, String nodeToDelete) {
		Command command = commandsManager.getCommand(catalogName, commandName);
		if(command == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		try {
			Map<String, Object> commandsParams = new HashMap<>();
			if(workspace != null && !workspace.isEmpty())
				commandsParams.put("repository", workspace);
			if(path != null && !path.isEmpty())
				commandsParams.put("path", nodeUtilities.getAbsPath(path));
			commandsParams.put("recursive", Boolean.toString(recursive));
			//commandsParams.put("modifiedOnly", true);
			if(username != null && !username.isEmpty()) {
				commandsParams.put("username", username);
			}
			if(userDTO != null) {
				commandsParams.put("userDTO", userDTO);
			}
			if(nodeToDelete != null && !nodeToDelete.isEmpty())
				commandsParams.put("deleteNode", nodeToDelete);
		
			commandsManager.executeCommand(command, commandsParams);
			return responseUtils.generateResponseBuilder(responseService.generateOkResponseWithNodePath(path)).build();
		} catch(Exception e) {
			return responseUtils.generateResponseBuilder(responseService.generateKoResponse()).build();
		}
	}

	
}
