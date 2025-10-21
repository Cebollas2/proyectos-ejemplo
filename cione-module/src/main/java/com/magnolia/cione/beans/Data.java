package com.magnolia.cione.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {

    @JsonProperty("SKU")
    private String SKU;
    
    @JsonProperty("GLASSESID")
    private int GLASSESID;
    
    @JsonProperty("VTO_FLAG")
    private boolean VTO_FLAG;
    
    @JsonProperty("URLS")
    private List<String> URLS;

	public String getSKU() {
		return SKU;
	}

	public void setSKU(String sKU) {
		SKU = sKU;
	}

	public int getGLASSESID() {
		return GLASSESID;
	}

	public void setGLASSESID(int gLASSESID) {
		GLASSESID = gLASSESID;
	}

	public boolean isVTO_FLAG() {
		return VTO_FLAG;
	}

	public void setVTO_FLAG(boolean vTO_FLAG) {
		VTO_FLAG = vTO_FLAG;
	}

	public List<String> getURLS() {
		return URLS;
	}

	public void setURLS(List<String> uRLS) {
		URLS = uRLS;
	}
}
