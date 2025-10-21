package com.magnolia.cione.dto;

import java.util.List;

import com.magnolia.cione.utils.CioneUtils;

public class CuotasDTO {

	private List<CuotaDTO> cuotas;
	
	private String ahorro;

	public List<CuotaDTO> getCuotas() {
		return cuotas;
	}

	public void setCuotas(List<CuotaDTO> cuotas) {
		this.cuotas = cuotas;
	}

	public String getAhorro() {
		if(!CioneUtils.isEmptyOrNull(ahorro)) {
			ahorro = CioneUtils.changeNumberFormat(ahorro, "%.2f");
		}
		return ahorro;
	}

	public void setAhorro(String ahorro) {
		this.ahorro = ahorro;
	}

	
	
}
