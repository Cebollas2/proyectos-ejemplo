package com.magnolia.cione.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerCTDTO {
	
	private CustomerCT customer;

	public CustomerCT getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerCT customer) {
		this.customer = customer;
	}
	
	
}
