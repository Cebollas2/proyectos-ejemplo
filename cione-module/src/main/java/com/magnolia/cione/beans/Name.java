package com.magnolia.cione.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Name {
	
	private String es;
	private String en;
	private String pt;
	
	public Name() {}
	
	public Name(String es, String pt, String en) {
		super();
		this.es = es;
		this.pt = pt;
		this.en = en;
	}

	public String getEs() {
		return es;
	}

	public void setEs(String es) {
		this.es = es;
	}

	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

	public String getPt() {
		return pt;
	}

	public void setPt(String pt) {
		this.pt = pt;
	}
	
}
