package com.magnolia.cione.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {

    private Value value;
    private String id;
    private Type customerGroup;
    
	public Value getValue() {
		return value;
	}
	
	public void setValue(Value value) {
		this.value = value;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Type getCustomerGroup() {
		return customerGroup;
	}
	
	public void setCustomerGroup(Type customerGroup) {
		this.customerGroup = customerGroup;
	}
	
	public Double toDouble() {
		return value.toDouble();
	}
}
