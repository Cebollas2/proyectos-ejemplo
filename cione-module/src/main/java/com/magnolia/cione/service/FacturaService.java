package com.magnolia.cione.service;

import java.io.File;
import java.util.List;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.CondicionesDTO;
import com.magnolia.cione.dto.FacturaDocQueryParamsDTO;
import com.magnolia.cione.dto.FacturaQueryParamsDTO;
import com.magnolia.cione.dto.LineaCondicionDTO;
import com.magnolia.cione.dto.LineaFacturaDTO;
import com.magnolia.cione.dto.PaginaFacturaDocDTO;
import com.magnolia.cione.dto.PaginaFacturasDTO;

@ImplementedBy(FacturaServiceImpl.class)
public interface FacturaService {
	
	public PaginaFacturaDocDTO getDocumentos(FacturaDocQueryParamsDTO params);
	public File getFileInPDFFormat(String fileName);
	public PaginaFacturasDTO getFacturas(FacturaQueryParamsDTO params);
	public List<LineaFacturaDTO> getLineasFactura(String numFactura);
	public List<LineaCondicionDTO> getLineasCondiciones(String linea);
}
