package com.magnolia.cione.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FittingBoxDTO {
	
    @JsonProperty("uid")
    private String uid;
    
    @JsonProperty("available")
    private boolean available;
    
    @JsonProperty("viewerCompatible")
    private boolean viewerCompatible;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isViewerCompatible() {
		return viewerCompatible;
	}

	public void setViewerCompatible(boolean viewerCompatible) {
		this.viewerCompatible = viewerCompatible;
	}
	
	@Override
    public String toString() {
        return "FittingBoxDTO{" +
                "uid='" + uid + '\'' +
                ", available=" + available +
                ", viewerCompatible=" + viewerCompatible +
                '}';
    }

}
