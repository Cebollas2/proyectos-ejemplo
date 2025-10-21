package com.magnolia.cione.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FittingBoxSkuDTO {

	@JsonProperty("skuList")
	private List<String> skuList;
	
	public List<String> getSkulist() {
		return skuList;
	}

	public void setSkulist(List<String> skulist) {
		this.skuList = skulist;
	}
}
