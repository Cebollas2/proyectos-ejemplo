package com.magnolia.cione.pur;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.magnolia.cms.security.SecuritySupport;
import info.magnolia.cms.security.User;
import info.magnolia.cms.security.UserManager;
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.module.form.processors.FormProcessorFailedException;
import info.magnolia.module.publicuserregistration.PublicUserRegistrationConfig;
import info.magnolia.module.publicuserregistration.UserProfile;
import info.magnolia.module.publicuserregistration.processors.PasswordProcessor;
import info.magnolia.module.publicuserregistration.processors.RegistrationProcessor;
import info.magnolia.module.publicuserregistration.processors.TokenPasswordProcessor;
import info.magnolia.module.publicuserregistration.strategy.Mail;
import info.magnolia.objectfactory.Components;
import info.magnolia.repository.RepositoryConstants;

public class PURServiceImpl implements PURService {
	
	private static final Logger log = LoggerFactory.getLogger(PURServiceImpl.class);
	
	private static String findFileErrorLog = "Cannot find file";
	
	private String USERID = "userId";
	
	private String TOKEN = "token";
	
	private String PASSWORD = "password";
	
	@Inject
	private Provider<PublicUserRegistrationConfig> publicUserRegistrationConfigProvider;
	
	@Inject
	private SecuritySupport securitySupport;
	
	@Inject
	private RegistrationProcessor pur;
	
	@Inject
	private PasswordProcessor passwordProcessor;
	
	@Inject
	private TokenPasswordProcessor tokenProcessor;
	
	@Inject
	private ResponseUtils responseUtils;
	
	@Inject
	private ResponseService responseService;
	
	@Inject
	private CommandsUtils commandsUtils;

	@Override
	public Response createUser(UserDTO userDTO) {
		try {
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("newUser", true);
			if(userDTO.getEmail() != null && !userDTO.getEmail().isEmpty())
				parameters.put(UserProfile.EMAIL, userDTO.getEmail());
			if(userDTO.getName() != null && !userDTO.getName().isEmpty())
				parameters.put(UserProfile.FULLNAME, userDTO.getName());						
			if(userDTO.getPassword() != null && !userDTO.getPassword().isEmpty())
				parameters.put(UserProfile.PASSWORD, userDTO.getPassword());
			if(userDTO.getUsername() != null && !userDTO.getUsername().isEmpty())
				parameters.put(UserProfile.USERNAME, userDTO.getUsername());			
			pur.setEnabled(true);
			pur.process(null, parameters);
			pur.setEnabled(false);
			return responseUtils.generateResponseBuilder(responseService.generateOkResponseWithMessage("Successfuly created user, please verify your account through the link that has been provided by mail")).build();
		} catch (FormProcessorFailedException e) {
			log.error(Arrays.toString(e.getStackTrace()));
		}
		return responseUtils.generateResponseBuilder(responseService.generateKoResponse()).build();
	}

	@Override
	public Response activateUser(String userId, String regstamp) throws FormProcessorFailedException{
		final String realm = publicUserRegistrationConfigProvider.get().getConfiguration().getRealmName();

        UserManager um = securitySupport.getUserManager(realm);
        User user = um.getUserById(userId);

        if (user == null) {
            throw new FormProcessorFailedException("No user with id: " + userId + " found in repository");
        }
        if (!StringUtils.equals(user.getProperty(Mail.PROPERTY_REGSTAMP), regstamp)) {
            throw new FormProcessorFailedException("Registration stamps doesn't match.");
        }
        um.setProperty(user, "enabled", Boolean.TRUE.toString());
        um.setProperty(user, Mail.PROPERTY_REGSTAMP, (String) null);
		return responseUtils.generateResponseBuilder(responseService.generateOkResponse()).build();
	}

	@Override
	public Response updateUser(UserDTO userDTO, String username) {
		try {
			User currentUser = securitySupport.getUserManager("public").getUser(username);
			String userIdentifier = currentUser.getIdentifier();
			Node userNode = MgnlContext.getJCRSession(RepositoryConstants.USERS).getNodeByIdentifier(userIdentifier);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("newUser", false);			
			
			if(userDTO.getEmail() != null && !userDTO.getEmail().isEmpty())
				parameters.put(UserProfile.EMAIL, userDTO.getEmail());
			else
				parameters.put(UserProfile.EMAIL, Optional.ofNullable(PropertyUtil.getPropertyOrNull(userNode, UserProfile.EMAIL).getString()).orElse(StringUtils.EMPTY));
			if(userDTO.getName() != null && !userDTO.getName().isEmpty())
				parameters.put(UserProfile.FULLNAME, userDTO.getName());
			else
				parameters.put(UserProfile.FULLNAME, Optional.ofNullable(PropertyUtil.getPropertyOrNull(userNode, UserProfile.FULLNAME).getString()).orElse(StringUtils.EMPTY));						
			if(userDTO.getUsername() != null && !userDTO.getUsername().isEmpty())
				parameters.put(UserProfile.USERNAME, username);
			else
				parameters.put(UserProfile.USERNAME, Optional.ofNullable(PropertyUtil.getPropertyOrNull(userNode, UserProfile.USERNAME).getString()).orElse(StringUtils.EMPTY));			
			pur.setEnabled(true);
			pur.process(null, parameters);
			pur.setEnabled(false);
			return responseUtils.generateResponseBuilder(responseService.generateOkResponseWithMessage("Modifications made successfully")).build();
		} catch (FormProcessorFailedException | RepositoryException e) {
			log.error(Arrays.toString(e.getStackTrace()));
		}
		return responseUtils.generateResponseBuilder(responseService.generateKoResponse()).build();
	}

	@Override
	public Response generateToken(ForgottenPasswordDTO passwordDTO) {
		Map<String, Object> parameters = new HashMap<>();
		if(passwordDTO != null && passwordDTO.getUsername() != null && passwordDTO.getEmail() != null && !passwordDTO.getUsername().isEmpty() && !passwordDTO.getEmail().isEmpty()) {
			parameters.put(UserProfile.USERNAME, passwordDTO.getUsername());
			parameters.put(UserProfile.EMAIL, passwordDTO.getEmail());
		}
		if(!parameters.isEmpty()) {
			passwordProcessor.setEnabled(true);
			try {
				passwordProcessor.process(null, parameters);
			} catch (FormProcessorFailedException e) {
				log.error(e.getLocalizedMessage() + "\n" + e.getStackTrace());
				return responseUtils.generateResponseBuilder(responseService.generateKoResponse()).build();
			}
			passwordProcessor.setEnabled(false);
			return responseUtils.generateResponseBuilder(responseService.generateOkResponse()).build();
		}
		return responseUtils.generateResponseBuilder(responseService.generateKoResponse()).build();
	}

	@Override
	public Response changePassword(ChangePasswordDTO passwordDTO) {
		Map<String, Object> parameters = new HashMap<>();
		if(passwordDTO != null && passwordDTO.getUserId() != null && passwordDTO.getToken() != null && passwordDTO.getPassword() != null && !passwordDTO.getUserId().isEmpty() && !passwordDTO.getToken().isEmpty() && !passwordDTO.getPassword().isEmpty()) {
			parameters.put(USERID, passwordDTO.getUserId());
			parameters.put(TOKEN, passwordDTO.getToken());
			parameters.put(PASSWORD, passwordDTO.getPassword());
		}
		if(!parameters.isEmpty()) {
			tokenProcessor.setEnabled(true);
			try {
				tokenProcessor.process(null, parameters);
			} catch (FormProcessorFailedException e) {
				log.error(e.getLocalizedMessage() + "\n" + e.getStackTrace());
				return responseUtils.generateResponseBuilder(responseService.generateKoResponse()).build();
			}
			tokenProcessor.setEnabled(false);
			return responseUtils.generateResponseBuilder(responseService.generateOkResponseWithMessage("Successful password change!")).build();
		}
		return responseUtils.generateResponseBuilder(responseService.generateKoResponse()).build();
	}

	@Override
	public boolean verifyUser(String userName) {
		User currentUser = Components.getComponent(SecuritySupport.class).getUserManager().getUser(userName);
		return Components.getComponent(SecuritySupport.class).getUserManager().setProperty(currentUser, "verify", "true") != null;
	}

	@Override
	public Response deleteUser(User currentUser) {
		try {
			Session sesion = MgnlContext.getJCRSession(RepositoryConstants.USERS);
			Node currentNode = sesion.getNodeByIdentifier(currentUser.getIdentifier());
			Node parentNode = currentNode.getParent();
			commandsUtils.deleteNodeWithWorkflow(parentNode.getPath(), RepositoryConstants.USERS, currentNode.getName());
			return commandsUtils.publishNodeWithWorkflow(parentNode.getPath(), RepositoryConstants.USERS);
		} catch (RepositoryException e) {
			return responseUtils.generateResponseBuilder(responseService.generateKoResponse()).build();
		}
	}

	@Override
	public boolean assignRole(String username, String rolename) {
		User assignedUser = Components.getComponent(SecuritySupport.class).getUserManager().addRole(Components.getComponent(SecuritySupport.class).getUserManager().getUser(username), rolename);
		return assignedUser != null;
	}
	
	@Override
	public boolean unassignRole(String username, String rolename) {
		User assignedUser = Components.getComponent(SecuritySupport.class).getUserManager().removeRole(Components.getComponent(SecuritySupport.class).getUserManager().getUser(username), rolename);
		return assignedUser != null;
	}

}
