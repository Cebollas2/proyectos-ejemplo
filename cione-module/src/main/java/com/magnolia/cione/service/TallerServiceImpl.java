package com.magnolia.cione.service;

import java.util.List;

import javax.inject.Inject;

import com.magnolia.cione.dto.LineaTallerDTO;
import com.magnolia.cione.dto.PaginaTalleresDTO;
import com.magnolia.cione.dto.TallerQueryParamsDTO;
import com.magnolia.cione.utils.CioneUtils;

public class TallerServiceImpl implements TallerService {

	@Inject
	private MiddlewareService middlewareService;

	@Override
	public PaginaTalleresDTO getTalleres(TallerQueryParamsDTO filter) {
		return middlewareService.getTalleres(CioneUtils.getIdCurrentClientERP(), filter);
	}

	@Override
	public List<LineaTallerDTO> getLineasTaller(String idServicio, String numPedido, String historico) {
		return middlewareService.getLineasTaller(CioneUtils.getIdCurrentClientERP(), idServicio, numPedido, historico);
	}

}

