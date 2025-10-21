package com.magnolia.cione.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LensParamsDTO {
	
	private String sku;
	private Integer cantidad;
	private String ojo;
	private String cyl;
	private String sph;
	private String crib;
	private String lente;
	private String nivel1;
	private String nivel2;
	private String reftaller;
	private boolean aTaller;
	private String refcliente;
	private String description;
	private String pvo;
	private String name;
	private String slug;
	private String lmattype;
	
	public String getLmattype() {
		return lmattype;
	}
	public void setLmattype(String lmattype) {
		this.lmattype = lmattype;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public String getOjo() {
		return ojo;
	}
	public void setOjo(String ojo) {
		this.ojo = ojo;
	}
	public String getCyl() {
		return cyl;
	}
	public void setCyl(String cyl) {
		this.cyl = cyl;
	}
	public String getSph() {
		return sph;
	}
	public void setSph(String sph) {
		this.sph = sph;
	}
	public String getCrib() {
		return crib;
	}
	public void setCrib(String crib) {
		this.crib = crib;
	}
	public String getLente() {
		return lente;
	}
	public void setLente(String lente) {
		this.lente = lente;
	}
	public String getNivel1() {
		return nivel1;
	}
	public void setNivel1(String nivel1) {
		this.nivel1 = nivel1;
	}
	public String getNivel2() {
		return nivel2;
	}
	public void setNivel2(String nivel2) {
		this.nivel2 = nivel2;
	}
	public String getReftaller() {
		return reftaller;
	}
	public void setReftaller(String reftaller) {
		this.reftaller = reftaller;
	}
	public boolean isaTaller() {
		return aTaller;
	}
	public void setaTaller(boolean aTaller) {
		this.aTaller = aTaller;
	}
	public String getRefcliente() {
		return refcliente;
	}
	public void setRefcliente(String refcliente) {
		this.refcliente = refcliente;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPvo() {
		return pvo;
	}
	public void setPvo(String pvo) {
		this.pvo = pvo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	
}
