package com.magnolia.cione.beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Metadata {
	
    @JsonProperty("sessionTokenExpirationDate")
    private Date sessiontokenexpirationdate;
    @JsonProperty("sessionToken")
    private String sessiontoken;
    private int limit;
    private int offset;
    @JsonProperty("previousOffset")
    private int previousoffset;
    @JsonProperty("nextOffset")
    private String nextoffset;
    @JsonProperty("totalCount")
    private int totalcount;
    
    public void setSessiontokenexpirationdate(Date sessiontokenexpirationdate) {
    	this.sessiontokenexpirationdate = sessiontokenexpirationdate;
    }
    public Date getSessiontokenexpirationdate() {
    	return sessiontokenexpirationdate;
    }

    public void setSessiontoken(String sessiontoken) {
    	this.sessiontoken = sessiontoken;
    }
    public String getSessiontoken() {
    	return sessiontoken;
    }

    public void setLimit(int limit) {
    	this.limit = limit;
    }
    public int getLimit() {
    	return limit;
    }

    public void setOffset(int offset) {
    	this.offset = offset;
    }
    public int getOffset() {
    	return offset;
    }

    public void setPreviousoffset(int previousoffset) {
    	this.previousoffset = previousoffset;
    }
    public int getPreviousoffset() {
    	return previousoffset;
    }

    public void setNextoffset(String nextoffset) {
    	this.nextoffset = nextoffset;
    }
    public String getNextoffset() {
    	return nextoffset;
    }

    public void setTotalcount(int totalcount) {
    	this.totalcount = totalcount;
    }
    public int getTotalcount() {
    	return totalcount;
    }
}
