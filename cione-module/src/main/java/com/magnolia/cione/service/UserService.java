package com.magnolia.cione.service;

import java.util.Collection;

import javax.ws.rs.core.Response;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.PriceConfigurationPostDTO;
import com.magnolia.cione.dto.RepuestoPostDTO;

import info.magnolia.cms.security.User;

@ImplementedBy(UserServiceImpl.class)
public interface UserService {

	Response updateUserPriceConfiguration(PriceConfigurationPostDTO dto);
	
	public Collection<String> getRolesCurrentClient();
	
	public User getUserCurrentClient();

}
