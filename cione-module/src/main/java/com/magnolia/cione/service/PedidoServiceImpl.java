package com.magnolia.cione.service;

import java.util.List;

import javax.inject.Inject;

import com.magnolia.cione.dto.LineaPedidoDTO;
import com.magnolia.cione.dto.MotivosDTO;
import com.magnolia.cione.dto.PaginaDevolucionesDTO;
import com.magnolia.cione.dto.PaginaGestionDevolucionesDTO;
import com.magnolia.cione.dto.PaginaPedidosDTO;
import com.magnolia.cione.dto.PedidoQueryParamsDTO;
import com.magnolia.cione.utils.CioneUtils;

public class PedidoServiceImpl implements PedidoService {

	@Inject
	private MiddlewareService middlewareService;

	@Override
	public PaginaPedidosDTO getPedidos(PedidoQueryParamsDTO filter) {
		return middlewareService.getPedidos(CioneUtils.getIdCurrentClientERP(), filter);
	}

	@Override
	public List<LineaPedidoDTO> getLineasPedido(String numPedido, String historico) {
		return middlewareService.getLineasPedido(CioneUtils.getIdCurrentClientERP(), numPedido, historico);
	}
	
	@Override
	public PaginaDevolucionesDTO getDevoluciones(PedidoQueryParamsDTO filter) {
		return middlewareService.getDevoluciones(CioneUtils.getIdCurrentClientERP(), filter);
	}

	@Override
	public PaginaDevolucionesDTO getDevolucionesAvanzadas(PedidoQueryParamsDTO filter) {
		return middlewareService.getDevolucionesAvanzadas(CioneUtils.getIdCurrentClientERP(), filter);
	}

	@Override
	public PaginaGestionDevolucionesDTO getGestionDevoluciones(PedidoQueryParamsDTO filter) {
		return middlewareService.getGestionDevoluciones(CioneUtils.getIdCurrentClientERP(), filter);
	}

	@Override
	public MotivosDTO getMotivos() {
		return middlewareService.getMotivos();
	}

}

