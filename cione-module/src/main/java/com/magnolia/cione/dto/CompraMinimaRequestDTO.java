package com.magnolia.cione.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompraMinimaRequestDTO {
	public String codSocio;
    public String grupoPrecio;
    public String marca;
    public String proveedor;
    public String familia;
    
    public CompraMinimaRequestDTO(String codSocio, String grupoPrecio, String marca, String proveedor, String familia) {
    	this.codSocio = codSocio;
    	this.grupoPrecio = grupoPrecio;
    	this.marca = marca;
    	this.proveedor = proveedor;
    	this.familia = familia;
    }
    
	public String getCodSocio() {
		return codSocio;
	}
	public void setCodSocio(String codSocio) {
		this.codSocio = codSocio;
	}
	public String getGrupoPrecio() {
		return grupoPrecio;
	}
	public void setGrupoPrecio(String grupoPrecio) {
		this.grupoPrecio = grupoPrecio;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	public String getFamilia() {
		return familia;
	}
	public void setFamilia(String familia) {
		this.familia = familia;
	}
}
