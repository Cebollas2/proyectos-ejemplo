package com.magnolia.cione.dto;

import java.util.List;

public class PagosPendientesDTO {

	private List<PagoPendienteDTO> pagosFacturas;
	private String minDate = "";
	private String maxDate = "";

	public List<PagoPendienteDTO> getPagosFacturas() {
		return pagosFacturas;
	}

	public void setPagosFacturas(List<PagoPendienteDTO> pagosFacturas) {
		this.pagosFacturas = pagosFacturas;
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
	
	

}
