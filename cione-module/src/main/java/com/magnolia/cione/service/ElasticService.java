package com.magnolia.cione.service;

import java.io.File;
import java.util.List;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.AlbaranQueryParamsDTO;
import com.magnolia.cione.dto.FacturaQueryParamsDTO;
import com.magnolia.cione.dto.LineaAlbaranDTO;
import com.magnolia.cione.dto.PaginaAlbaranesDTO;
import com.magnolia.cione.dto.PaginaFacturasDTO;

@ImplementedBy(ElasticServiceImpl.class)
public interface ElasticService {
	public PaginaAlbaranesDTO getAlbaranes(String idClient, AlbaranQueryParamsDTO filters);
	public List<LineaAlbaranDTO> getLineaAlbaran(String idClient, String numAlbaran);
	public PaginaFacturasDTO getFacturas(String idClient, FacturaQueryParamsDTO filters);
	public File getDocAlbaran(String url);
}
