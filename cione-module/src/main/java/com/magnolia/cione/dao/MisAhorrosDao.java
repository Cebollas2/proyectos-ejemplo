package com.magnolia.cione.dao;

import java.util.Date;

import javax.naming.NamingException;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.MisAhorrosDTO;

@ImplementedBy(MisAhorrosDaoImpl.class)
public interface MisAhorrosDao {
	public MisAhorrosDTO getMisAhorros(String codSocio, Date fechaDesde, Date fechaHasta) throws NamingException;
	
	public Double getAhorroEnCuotas(String codSocio) throws NamingException;
	
	public MisAhorrosDTO getMisAhorrosDashBoard(String codSocio);
	
}
