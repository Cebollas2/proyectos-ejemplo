package com.magnolia.cione.dto;

import com.magnolia.cione.utils.CioneUtils;

public class AhorroDTO {
	private Integer id;
	private String concepto;
	private Double importe;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}
	
	public String getImporteView() {
		return CioneUtils.decimalToView(importe.toString());		
	}
	
	@Override
	public String toString() {
		return "AhorroDTO [id=" + id + ", concepto=" + concepto + ", importe=" + importe + "]";
	}
}
