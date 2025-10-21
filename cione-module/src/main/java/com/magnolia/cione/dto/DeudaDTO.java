package com.magnolia.cione.dto;

import com.magnolia.cione.utils.CioneUtils;

public class DeudaDTO {
	private String deuda;
	private String fecha;

	public String getDeuda() {
		return deuda;
	}

	public void setDeuda(String deuda) {
		this.deuda = deuda;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}	
	
	public String getDeudaView() {
		return CioneUtils.decimalToView(deuda);
	}
}
