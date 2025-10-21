package com.magnolia.cione.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingListDTO {
	
	String id;
	String total;
	String mailconsumer;
	String effectivedate;
	String customer;
	String importeVenta;
	String lineItemId;
	
	public String getImporteVenta() {
		return importeVenta;
	}
	public void setImporteVenta(String importeVenta) {
		this.importeVenta = importeVenta;
	}
	public String getLineItemId() {
		return lineItemId;
	}
	public void setLineItemId(String lineItemId) {
		this.lineItemId = lineItemId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getMailconsumer() {
		return mailconsumer;
	}
	public void setMailconsumer(String mailconsumer) {
		this.mailconsumer = mailconsumer;
	}
	public String getEffectivedate() {
		return effectivedate;
	}
	public void setEffectivedate(String effectivedate) {
		this.effectivedate = effectivedate;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}

}
