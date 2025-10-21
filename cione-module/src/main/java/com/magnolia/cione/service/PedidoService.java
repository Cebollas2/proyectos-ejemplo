package com.magnolia.cione.service;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.LineaPedidoDTO;
import com.magnolia.cione.dto.MotivosDTO;
import com.magnolia.cione.dto.PaginaDevolucionesDTO;
import com.magnolia.cione.dto.PaginaGestionDevolucionesDTO;
import com.magnolia.cione.dto.PaginaPedidosDTO;
import com.magnolia.cione.dto.PedidoQueryParamsDTO;

@ImplementedBy(PedidoServiceImpl.class)
public interface PedidoService {

	public PaginaPedidosDTO getPedidos(PedidoQueryParamsDTO params);

	public List<LineaPedidoDTO> getLineasPedido(String numPedido, String historico);
	
	public PaginaDevolucionesDTO getDevoluciones(PedidoQueryParamsDTO filter);
	
	public PaginaDevolucionesDTO getDevolucionesAvanzadas(PedidoQueryParamsDTO filter);
	
	public PaginaGestionDevolucionesDTO getGestionDevoluciones(PedidoQueryParamsDTO filter);
	
	public MotivosDTO getMotivos();

}
