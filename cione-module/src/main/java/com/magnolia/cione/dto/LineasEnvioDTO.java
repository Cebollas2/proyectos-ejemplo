package com.magnolia.cione.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LineasEnvioDTO {
	private List<LineaEnvioDTO> contenidoEnvios = new ArrayList<>();

	public List<LineaEnvioDTO> getContenidoEnvios() {
		return contenidoEnvios;
	}

	public void setContenidoEnvios(List<LineaEnvioDTO> contenidoEnvios) {
		this.contenidoEnvios = contenidoEnvios;
	}

}
