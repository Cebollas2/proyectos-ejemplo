package com.magnolia.cione.dto;

import java.util.List;

public class UpdateAddressCustomerCTDTO {
	
	private int version;
	private List<ActionCTDTO> actions;
	
	public int getVersion() {
		return version;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
	
	public List<ActionCTDTO> getActions() {
		return actions;
	}
	
	public void setActions(List<ActionCTDTO> actions) {
		this.actions = actions;
	}
}
