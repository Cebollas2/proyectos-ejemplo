package com.magnolia.cione.service;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.MisConsumosDTO;
import com.magnolia.cione.dto.MisConsumosQueryParamsDTO;

@ImplementedBy(MisConsumosServiceImpl.class)
public interface MisConsumosService {

	public MisConsumosDTO getMisConsumos(String codSocio, MisConsumosQueryParamsDTO filter);

}
