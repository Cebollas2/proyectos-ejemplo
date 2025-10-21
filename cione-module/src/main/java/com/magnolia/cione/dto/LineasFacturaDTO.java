package com.magnolia.cione.dto;

import java.util.ArrayList;
import java.util.List;

public class LineasFacturaDTO {
	private List<LineaFacturaDTO> lineasFactura = new ArrayList<>();

	public List<LineaFacturaDTO> getLineasFactura() {
		return lineasFactura;
	}

	public void setLineasFactura(List<LineaFacturaDTO> lineasFactura) {
		this.lineasFactura = lineasFactura;
	}
}
