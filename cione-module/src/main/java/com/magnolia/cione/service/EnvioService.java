package com.magnolia.cione.service;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.EnvioQueryParamsDTO;
import com.magnolia.cione.dto.LineaEnvioDTO;
import com.magnolia.cione.dto.PaginaEnviosDTO;

@ImplementedBy(EnvioServiceImpl.class)
public interface EnvioService {

	public PaginaEnviosDTO getEnvios(EnvioQueryParamsDTO params);
	public List<LineaEnvioDTO> getLineasEnvio(String numAlbaran, String trackingPedido);

}
