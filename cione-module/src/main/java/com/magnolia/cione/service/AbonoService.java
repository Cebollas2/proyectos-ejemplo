package com.magnolia.cione.service;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.AbonoQueryParamsDTO;
import com.magnolia.cione.dto.LineaAbonoDTO;
import com.magnolia.cione.dto.PaginaAbonosDTO;

@ImplementedBy(AbonoServiceImpl.class)
public interface AbonoService {

	public PaginaAbonosDTO getAbonos(AbonoQueryParamsDTO params);

	public List<LineaAbonoDTO> getLineasAbono(String numAbono, String historico);

}
