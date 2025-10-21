package com.magnolia.cione.dto;

import java.util.List;

import com.magnolia.cione.utils.CioneUtils;

public class DeudasDTO {

	private List<DeudaDTO> deudas;
	
	private Double total;

	public List<DeudaDTO> getDeudas() {
		return deudas;
	}

	public void setDeudas(List<DeudaDTO> deudas) {
		this.deudas = deudas;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	public String getTotalView() {
		return CioneUtils.decimalToView(total.toString());
	}
	
	

}
