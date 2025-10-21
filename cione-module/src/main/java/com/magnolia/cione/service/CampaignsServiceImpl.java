package com.magnolia.cione.service;

import javax.inject.Inject;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dao.CampaignsDao;
import com.magnolia.cione.dto.CampaignsQueryParamsDTO;
import com.magnolia.cione.dto.PaginaCampaignsDTO;
import com.magnolia.cione.exceptions.CioneRuntimeException;

public class CampaignsServiceImpl implements CampaignsService {

	private static final Logger log = LoggerFactory.getLogger(CampaignsServiceImpl.class);
	
	@Inject
	private CampaignsDao dao;

	@Override
	public void registrarCampaign(CampaignsQueryParamsDTO campaignsQueryParamsDTO) {
		try {
			dao.registrarCampaign(campaignsQueryParamsDTO);
		} catch (NamingException e) {
			log.error(e.getMessage(),e);
			throw new CioneRuntimeException("Error al acceder al datasource");
		}
		
	}

	@Override
	public PaginaCampaignsDTO getCampaigns(CampaignsQueryParamsDTO campaignsQueryParamsDTO) {
		PaginaCampaignsDTO campaigns = new PaginaCampaignsDTO();
		try {
			campaigns = dao.getCampaigns(campaignsQueryParamsDTO);
		} catch (NamingException e) {
			log.error(e.getMessage(),e);
			throw new CioneRuntimeException("Error al acceder al datasource");
		}
		return campaigns;
	}

	@Override
	public PaginaCampaignsDTO getSingleCampaign(CampaignsQueryParamsDTO campaignsQueryParamsDTO) {
		PaginaCampaignsDTO campaigns = new PaginaCampaignsDTO();
		try {
			campaigns = dao.getSingleCampaign(campaignsQueryParamsDTO);
		} catch (NamingException e) {
			log.error(e.getMessage(),e);
			throw new CioneRuntimeException("Error al acceder al datasource");
		}
		return campaigns;
	}

	
}
