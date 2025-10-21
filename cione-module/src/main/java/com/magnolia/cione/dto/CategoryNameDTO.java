package com.magnolia.cione.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.magnolia.cione.dto.CT.LocaleCategoryCT;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryNameDTO {
	
	public LocaleCategoryCT name;

	public LocaleCategoryCT getName() {
		return name;
	}

	public void setName(LocaleCategoryCT name) {
		this.name = name;
	}

}
