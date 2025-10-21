package com.magnolia.cione.service;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.MisAhorrosDTO;
import com.magnolia.cione.dto.MisAhorrosQueryParamsDTO;

@ImplementedBy(MisAhorrosServiceImpl.class)
public interface MisAhorrosService {

	public MisAhorrosDTO getMisAhorros(String codSocio, MisAhorrosQueryParamsDTO filter);
}
