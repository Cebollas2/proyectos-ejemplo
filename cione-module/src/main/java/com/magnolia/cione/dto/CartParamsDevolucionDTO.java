package com.magnolia.cione.dto;

import java.util.List;
import java.util.Map;

import javax.money.MonetaryAmount;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CartParamsDevolucionDTO {
	
	private List<DevolucionDTO> devoluciones;

	public List<DevolucionDTO> getDevoluciones() {
		return devoluciones;
	}

	public void setDevoluciones(List<DevolucionDTO> devoluciones) {
		this.devoluciones = devoluciones;
	}
}
