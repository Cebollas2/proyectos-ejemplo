package com.magnolia.cione.dto;

import java.util.List;

public class PaginaCampaignsDTO {

	private Integer numRegistros;
	private List<CampaignDTO> campaigns;
	
	public Integer getNumRegistros() {
		return numRegistros;
	}
	public void setNumRegistros(Integer numRegistros) {
		this.numRegistros = numRegistros;
	}
	public List<CampaignDTO> getCampaigns() {
		return campaigns;
	}
	public void setCampaigns(List<CampaignDTO> campaigns) {
		this.campaigns = campaigns;
	}
	
}
