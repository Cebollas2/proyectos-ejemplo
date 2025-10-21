package com.magnolia.cione.dto;

import java.util.ArrayList;
import java.util.List;

public class LineasPedidoDTO {
	
	private List<LineaPedidoDTO> lineasPedido = new ArrayList<>();

	public List<LineaPedidoDTO> getLineasPedido() {
		return lineasPedido;
	}

	public void setLineasPedido(List<LineaPedidoDTO> lineasPedido) {
		this.lineasPedido = lineasPedido;
	}

}
