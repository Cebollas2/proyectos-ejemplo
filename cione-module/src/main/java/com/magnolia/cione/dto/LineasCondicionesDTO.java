package com.magnolia.cione.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LineasCondicionesDTO {
	private List<LineaCondicionDTO> condicionesDetalle = new ArrayList<>();

	public List<LineaCondicionDTO> getCondicionesDetalle() {
		return condicionesDetalle;
	}

	public void setCondicionesDetalle(List<LineaCondicionDTO> condicionesDetalle) {
		this.condicionesDetalle = condicionesDetalle;
	}

}
