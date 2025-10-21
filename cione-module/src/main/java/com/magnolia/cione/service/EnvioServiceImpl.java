package com.magnolia.cione.service;

import java.util.List;

import javax.inject.Inject;

import com.magnolia.cione.dto.EnvioQueryParamsDTO;
import com.magnolia.cione.dto.LineaEnvioDTO;
import com.magnolia.cione.dto.PaginaEnviosDTO;
import com.magnolia.cione.utils.CioneUtils;

public class EnvioServiceImpl implements EnvioService {

	@Inject
	private MiddlewareService middlewareService;

	@Override
	public PaginaEnviosDTO getEnvios(EnvioQueryParamsDTO filter) {
		return middlewareService.getEnvios(CioneUtils.getIdCurrentClientERP(), filter);
	}

	@Override
	public List<LineaEnvioDTO> getLineasEnvio(String numAlbaran, String trackingPedido) {
		return middlewareService.getLineasEnvio(CioneUtils.getIdCurrentClientERP(), numAlbaran, trackingPedido);
	}

}

