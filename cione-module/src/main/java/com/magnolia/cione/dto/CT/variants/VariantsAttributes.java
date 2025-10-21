package com.magnolia.cione.dto.CT.variants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.commercetools.api.models.product.FacetTerm;

public class VariantsAttributes {
	
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
	
	public boolean hasTerms() {
		return terms != null && !terms.isEmpty();
	}
	
	public void setTerms(List<Term> terms) {
		this.terms = terms;
	}
	
	public void setTermsStats(List<FacetTerm> terms) {
		List<Term> lt = new ArrayList<>();
		for (FacetTerm term : terms) {
			Term t = new Term();
			t.setTerm(term.getTerm().toString());
			lt.add(t);
		}
		this.terms = lt;
	}
	
//	public void setTermsStats(List<TermStats> terms) {
//		
//		List<Term> lt = new ArrayList<>();
//		for(TermStats term : terms) {
//			Term t = new Term();
//			t.setTerm(term.getTerm());
//			lt.add(t);
//		}
//		this.terms = lt;
//	}
	
	public void setSelected(String[] filters) {
		if (filters != null) {
			List<String> filtersList = new ArrayList<>(Arrays.asList(filters));
			
			Collections.replaceAll(filtersList, "true", "T");
			Collections.replaceAll(filtersList, "false", "F");
			
			for (Term t: terms) {
				if (filtersList.contains(t.getTerm()))
					t.setSelected(true);
			}
		}
	}
	
	public void setSelectedBoolean(String[] filters) {
		
		List<String> filtersList = new ArrayList<>(Arrays.asList(filters));
		Collections.replaceAll(filtersList, "true", "T");
		Collections.replaceAll(filtersList, "false", "F");
		
		for (Term t: terms) {
			if (filtersList.contains(t.getTerm()))
				t.setSelected(true);
		}
		
	}
	
	public void setSelectedByValue(String[] filters) {
		
		List<String> filtersList = new ArrayList<>(Arrays.asList(filters));
		
		for (Term t: terms) {
			if (filtersList.contains(t.getValue().trim()))
				t.setSelected(true);
		}
		
	}
	
}
