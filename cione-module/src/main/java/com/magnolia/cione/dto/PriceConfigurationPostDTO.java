package com.magnolia.cione.dto;

public class PriceConfigurationPostDTO {
	
	private String newConfiguration;
	private String user;
	private String password;
	
	public String getNewConfiguration() {
		return newConfiguration;
	}
	public void setNewConfiguration(String newConfiguration) {
		this.newConfiguration = newConfiguration;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
}
