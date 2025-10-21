package com.magnolia.cione.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ModifiedOrCreatedByCTDTO {
	
	private boolean isPlatformClient;
	private UserDataCTDTO user;
	
	public boolean isPlatformClient() {
		return isPlatformClient;
	}
	
	public void setPlatformClient(boolean isPlatformClient) {
		this.isPlatformClient = isPlatformClient;
	}
	
	public UserDataCTDTO getUser() {
		return user;
	}
	
	public void setUser(UserDataCTDTO user) {
		this.user = user;
	}

}
