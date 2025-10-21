package com.magnolia.cione.dto.CT;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocaleCategoryCT {
	
	private String es;
	private String pt;
	private String en;
    
    public String getEs() {
		return es;
	}
	public void setEs(String es) {
		this.es = es;
	}
	public String getPt() {
		return pt;
	}
	public void setPt(String pt) {
		this.pt = pt;
	}
	public String getEn() {
		return en;
	}
	public void setEn(String en) {
		this.en = en;
	}

}
