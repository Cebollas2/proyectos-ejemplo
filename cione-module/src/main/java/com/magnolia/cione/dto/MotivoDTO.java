package com.magnolia.cione.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MotivoDTO {
	private String codMotivo;
	private String descMotivo;
	
	public String getCodMotivo() {
		return codMotivo;
	}
	public void setCodMotivo(String codMotivo) {
		this.codMotivo = codMotivo;
	}
	public String getDescMotivo() {
		return descMotivo;
	}
	public void setDescMotivo(String descMotivo) {
		this.descMotivo = descMotivo;
	}
}
