package com.magnolia.cione.dto;

public class CustomerDTO {
	
	private String email;
	private String customerNumber;
	private String firstName;
	private String lastName;
	private String password;
	private EmployeeCustomerGroupDTO customerGroup;
	private CustomCTDTO custom;
	
	public CustomCTDTO getCustom() {
		return custom;
	}

	public void setCustom(CustomCTDTO custom) {
		this.custom = custom;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCustomerNumber() {
		return customerNumber;
	}
	
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public EmployeeCustomerGroupDTO getCustomerGroup() {
		return customerGroup;
	}

	public void setCustomerGroup(EmployeeCustomerGroupDTO customerGroup) {
		this.customerGroup = customerGroup;
	}

}
