package com.magnolia.cione.dto;

import java.util.List;

public class CustomersQueryCTDTO {
	
    private int limit;
    private int offset;
    private int count;
    private int total;
    private List<CustomerCT> results;
    
	public int getLimit() {
		return limit;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public List<CustomerCT> getResults() {
		return results;
	}
	
	public void setResults(List<CustomerCT> results) {
		this.results = results;
	}
}
