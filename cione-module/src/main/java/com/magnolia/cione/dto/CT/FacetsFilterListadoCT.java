package com.magnolia.cione.dto.CT;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FacetsFilterListadoCT {

	private VariantsFilterCT facets;

	public VariantsFilterCT getFacets() {
		return facets;
	}

	public void setFacets(VariantsFilterCT facets) {
		this.facets = facets;
	}
	
}
