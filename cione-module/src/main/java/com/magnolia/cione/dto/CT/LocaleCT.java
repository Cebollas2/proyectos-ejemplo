package com.magnolia.cione.dto.CT;

public class LocaleCT {

	public String es;
	public String pt;
    public String en;
    
    public LocaleCT(String es, String pt, String en) {
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
