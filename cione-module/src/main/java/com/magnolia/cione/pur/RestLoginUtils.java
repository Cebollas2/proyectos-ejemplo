package com.magnolia.cione.pur;

import java.security.Principal;
import java.util.Iterator;

import info.magnolia.cms.security.SecuritySupport;
import info.magnolia.cms.security.User;
import info.magnolia.cms.security.auth.callback.Base64CallbackHandler;
import info.magnolia.cms.security.auth.callback.CredentialsCallbackHandler;
import info.magnolia.cms.security.auth.login.LoginResult;
import info.magnolia.context.Context;
import info.magnolia.context.MgnlContext;
import info.magnolia.context.UserContext;
import info.magnolia.objectfactory.Components;

public class RestLoginUtils {
	
	private CredentialsCallbackHandler login;
	
	private String currentUserName;
	
	public RestLoginUtils(String base64String) {
		login = new Base64CallbackHandler(base64String);
	}
	
	public boolean doLogin() {
		LoginResult loginResult = Components.getComponent(SecuritySupport.class).authenticate(login, null);
		Iterator<Principal> iterator = loginResult.getSubject().getPrincipals().iterator();
		if(iterator.hasNext())
			currentUserName = iterator.next().getName();
		return loginResult.getStatus() == LoginResult.STATUS_SUCCEEDED;
	}
	
	public User getCurrentUser() {
		return Components.getComponent(SecuritySupport.class).getUserManager().getUser(currentUserName);
	}
	
	public void logout() {
		Context ctx = MgnlContext.getInstance();
		if(ctx instanceof UserContext) {
			((UserContext) ctx).logout();
		}
	}

}
