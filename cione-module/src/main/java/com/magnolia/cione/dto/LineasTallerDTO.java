package com.magnolia.cione.dto;

import java.util.ArrayList;
import java.util.List;

public class LineasTallerDTO {
	
	private List<LineaTallerDTO> lineasServicioTaller = new ArrayList<>();

	public List<LineaTallerDTO> getLineasServicioTaller() {
		return lineasServicioTaller;
	}

	public void setLineasPedido(List<LineaTallerDTO> lineasServicioTaller) {
		this.lineasServicioTaller = lineasServicioTaller;
	}

}
