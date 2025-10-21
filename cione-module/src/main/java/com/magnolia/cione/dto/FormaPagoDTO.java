package com.magnolia.cione.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FormaPagoDTO {
	private String razonSocial;
	private String nifcif;
	private String cuenta;
	private String plazoPago;
	private String regEquivalencia;
	
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getNifcif() {
		return nifcif;
	}
	public void setNifcif(String nifcif) {
		this.nifcif = nifcif;
	}
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public String getPlazoPago() {
		return plazoPago;
	}
	public void setPlazoPago(String plazoPago) {
		this.plazoPago = plazoPago;
	}
	public String getRegEquivalencia() {
		return regEquivalencia;
	}
	public void setRegEquivalencia(String regEquivalencia) {
		this.regEquivalencia = regEquivalencia;
	}


}
