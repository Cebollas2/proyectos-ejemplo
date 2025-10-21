package com.magnolia.cione.service;

import java.util.List;

import javax.inject.Inject;

import com.magnolia.cione.dto.AbonoQueryParamsDTO;
import com.magnolia.cione.dto.LineaAbonoDTO;
import com.magnolia.cione.dto.PaginaAbonosDTO;
import com.magnolia.cione.utils.CioneUtils;

public class AbonoServiceImpl implements AbonoService {

	@Inject
	private MiddlewareService middlewareService;

	@Override
	public PaginaAbonosDTO getAbonos(AbonoQueryParamsDTO filter) {
		return middlewareService.getAbonos(CioneUtils.getIdCurrentClientERP(), filter);
	}

	@Override
	public List<LineaAbonoDTO> getLineasAbono(String numAbono, String historico) {
		return middlewareService.getLineasAbono(CioneUtils.getIdCurrentClientERP(), numAbono, historico);
	}

}
