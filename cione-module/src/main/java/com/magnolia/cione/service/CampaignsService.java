package com.magnolia.cione.service;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.CampaignsQueryParamsDTO;
import com.magnolia.cione.dto.PaginaCampaignsDTO;

@ImplementedBy(CampaignsServiceImpl.class)
public interface CampaignsService {

	public void registrarCampaign(CampaignsQueryParamsDTO campaignsQueryParamsDTO);
	
	public PaginaCampaignsDTO getCampaigns(CampaignsQueryParamsDTO campaignsQueryParamsDTO);
	
	public PaginaCampaignsDTO getSingleCampaign(CampaignsQueryParamsDTO campaignsQueryParamsDTO);
}
