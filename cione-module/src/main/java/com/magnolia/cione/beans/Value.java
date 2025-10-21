package com.magnolia.cione.beans;

public class Value {
	
	private String type;
	private String currencyCode;
	private int centAmount;
	private int fractionDigits;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public int getCentAmount() {
		return centAmount;
	}
	
	public void setCentAmount(int centAmount) {
		this.centAmount = centAmount;
	}
	
	public int getFractionDigits() {
		return fractionDigits;
	}
	
	public void setFractionDigits(int fractionDigits) {
		this.fractionDigits = fractionDigits;
	}
	
	public Double toDouble() {
		
		
		String valueInt = Integer.toString(centAmount);
		String str = "0";
		if (!valueInt.equals("0")) {
			str = new StringBuilder(valueInt).insert(valueInt.length()-fractionDigits, ".").toString();
		}
		
		return Double.parseDouble(str);
	}
    
}
