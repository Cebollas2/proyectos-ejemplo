package com.magnolia.cione.pur;

import javax.ws.rs.core.Response;

import com.google.inject.ImplementedBy;

import info.magnolia.cms.security.User;
import info.magnolia.module.form.processors.FormProcessorFailedException;

@ImplementedBy(PURServiceImpl.class)
public interface PURService {
	
	public Response createUser(UserDTO userDTO);
	
	public Response activateUser(String userId, String regstamp) throws FormProcessorFailedException;
	
	public Response updateUser(UserDTO userDTO, String username);
	
	public Response generateToken(ForgottenPasswordDTO passwordDTO);
	
	public Response changePassword(ChangePasswordDTO passwordDTO);
	
	public boolean verifyUser(String userName);
	
	public Response deleteUser(User currentUser);
	
	public boolean assignRole(String username, String rolename);
	
	public boolean unassignRole(String username, String rolename);
	

}
