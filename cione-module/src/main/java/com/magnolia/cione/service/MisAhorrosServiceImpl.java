package com.magnolia.cione.service;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dao.MisAhorrosDao;
import com.magnolia.cione.dto.MisAhorrosDTO;
import com.magnolia.cione.dto.MisAhorrosQueryParamsDTO;
import com.magnolia.cione.exceptions.CioneRuntimeException;
import com.magnolia.cione.utils.CioneUtils;

public class MisAhorrosServiceImpl implements MisAhorrosService {
	
	private static final Logger log = LoggerFactory.getLogger(MisAhorrosServiceImpl.class);

	@Inject
	private MisAhorrosDao dao;

	@Override
	public MisAhorrosDTO getMisAhorros(String codSocio, MisAhorrosQueryParamsDTO filter) {		
		try {
			Date fechaDesde = CioneUtils.parseStringToDate(filter.getFechaDesde(), "MM-yyyy");
			Date fechaHasta = CioneUtils.parseStringToDate(filter.getFechaHasta(), "MM-yyyy");
			if(fechaHasta != null) {				
				Calendar c = Calendar.getInstance();
				c.setTime(fechaHasta);
				int lastDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
				fechaHasta.setDate(lastDayOfMonth);
			}
			return dao.getMisAhorros(codSocio, fechaDesde, fechaHasta);
		} catch (NamingException e) {
			log.error(e.getMessage(),e);
			throw new CioneRuntimeException("Error al acceder al datasource");
		}
	}

}
