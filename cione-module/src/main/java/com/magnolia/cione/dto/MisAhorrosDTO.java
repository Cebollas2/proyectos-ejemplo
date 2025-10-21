package com.magnolia.cione.dto;

import java.util.ArrayList;
import java.util.List;

import com.magnolia.cione.utils.CioneUtils;

public class MisAhorrosDTO {
	private Double importeTotal;
	private List<AhorroDTO> ahorros = new ArrayList<>();
	private String minDate = "";
	private String maxDate = "";

	public Double getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(Double importeTotal) {
		this.importeTotal = importeTotal;
	}

	public List<AhorroDTO> getAhorros() {
		return ahorros;
	}

	public void setAhorros(List<AhorroDTO> ahorros) {
		this.ahorros = ahorros;
	}

	public String getMinDate() {
		return minDate;
	}

	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}

	public String getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}
	
	public String getImporteTotalView() {
		if(importeTotal != null) {
			return CioneUtils.decimalToView(importeTotal.toString());
		}else {
			return "";
		}
	}

}
