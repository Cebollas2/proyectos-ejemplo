package com.magnolia.cione.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MotivosDTO {
	private List<MotivoDTO> motivos;

	public List<MotivoDTO> getMotivos() {
		return motivos;
	}

	public void setMotivos(List<MotivoDTO> motivos) {
		this.motivos = motivos;
	}
	

}
