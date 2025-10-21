package com.magnolia.cione.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerCT {
	
	private String id;
	private int version;
	private int lastMessageSequenceNumber;
	private Date createdAt;
	private Date lastModifiedAt;
	private ModifiedOrCreatedByCTDTO lastModifiedBy;
	private ModifiedOrCreatedByCTDTO createdBy;
	private String customerNumber;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private List<Object> addresses;
	private List<Object> shippingAddressIds;
	private List<Object> billingAddressIds;
	private boolean isEmailVerified;
	private TypeCTDTO customerGroup;
	private CustomCTDTO custom;
	private List<Object> stores;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public int getVersion() {
		return version;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
	
	public int getLastMessageSequenceNumber() {
		return lastMessageSequenceNumber;
	}
	
	public void setLastMessageSequenceNumber(int lastMessageSequenceNumber) {
		this.lastMessageSequenceNumber = lastMessageSequenceNumber;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}
	
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}
	
	public ModifiedOrCreatedByCTDTO getLastModifiedBy() {
		return lastModifiedBy;
	}
	
	public void setLastModifiedBy(ModifiedOrCreatedByCTDTO lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
	public ModifiedOrCreatedByCTDTO getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(ModifiedOrCreatedByCTDTO createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getCustomerNumber() {
		return customerNumber;
	}
	
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
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
	
	public List<Object> getAddresses() {
		return addresses;
	}
	
	public void setAddresses(List<Object> addresses) {
		this.addresses = addresses;
	}
	
	public List<Object> getShippingAddressIds() {
		return shippingAddressIds;
	}
	
	public void setShippingAddressIds(List<Object> shippingAddressIds) {
		this.shippingAddressIds = shippingAddressIds;
	}
	
	public List<Object> getBillingAddressIds() {
		return billingAddressIds;
	}
	
	public void setBillingAddressIds(List<Object> billingAddressIds) {
		this.billingAddressIds = billingAddressIds;
	}
	
	public boolean isEmailVerified() {
		return isEmailVerified;
	}
	
	public void setEmailVerified(boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}
	
	public TypeCTDTO getCustomerGroup() {
		return customerGroup;
	}
	
	public void setCustomerGroup(TypeCTDTO customerGroup) {
		this.customerGroup = customerGroup;
	}
	
	public CustomCTDTO getCustom() {
		return custom;
	}
	
	public void setCustom(CustomCTDTO custom) {
		this.custom = custom;
	}
	
	public List<Object> getStores() {
		return stores;
	}
	
	public void setStores(List<Object> stores) {
		this.stores = stores;
	}

}
