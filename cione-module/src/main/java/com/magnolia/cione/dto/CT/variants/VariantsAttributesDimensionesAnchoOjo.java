package com.magnolia.cione.dto.CT.variants;

import java.util.List;

public class VariantsAttributesDimensionesAnchoOjo {
	
    private String type;
    private String dataType;
    private int missing;
    private int total;
    private int other;
    private List<Term> terms;
    
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public int getMissing() {
		return missing;
	}
	public void setMissing(int missing) {
		this.missing = missing;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getOther() {
		return other;
	}
	public void setOther(int other) {
		this.other = other;
	}
	public List<Term> getTerms() {
		return terms;
	}
	public void setTerms(List<Term> terms) {
		this.terms = terms;
	}
}
