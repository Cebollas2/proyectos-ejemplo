package com.magnolia.cione.service;

import javax.jcr.RepositoryException;
import javax.jcr.query.InvalidQueryException;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.RadioBusquedaDTO;

@ImplementedBy(RadioBusquedaServiceImpl.class)
public interface RadioBusquedaService {

	public void saveRadioBusqueda(RadioBusquedaDTO dto);
	
	public long getTotalAnuncios() throws InvalidQueryException, RepositoryException;

}
