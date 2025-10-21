package com.magnolia.cione.dto;

import java.util.List;

public class DireccionesDTO {
	
	private List<DireccionDTO> direcciones;

	public List<DireccionDTO> getTransportes() {
		return direcciones;
	}

	public void setTransportes(List<DireccionDTO> direcciones) {
		this.direcciones = direcciones;
	}

}
