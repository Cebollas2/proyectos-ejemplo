package com.magnolia.cione.dto;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.utils.CioneUtils;

public class HerramientaDTO {
	private String herramienta;
	private Boolean activo;
	private String compromiso;
	private String consumido;
	private String fechaIni;
	private String fechaFin;

	public String getHerramienta() {
		return herramienta;
	}

	public void setHerramienta(String herramienta) {
		this.herramienta = herramienta;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getCompromiso() {
		return CioneUtils.decimalToView(compromiso);
	}

	public void setCompromiso(String compromiso) {
		this.compromiso = compromiso;
	}

	public String getConsumido() {
		return CioneUtils.decimalToView(consumido);
	}

	public void setConsumido(String consumido) {
		this.consumido = consumido;
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		if(!CioneUtils.isEmptyOrNull(fechaIni)) {
			fechaIni = CioneUtils.changeDateFormat(fechaIni, "yyyy-MM-dd", CioneConstants.OUPUT_DATE_FORMAT);
		}
		this.fechaIni = fechaIni;
	}

	public String getFechaFin() {
		if(!CioneUtils.isEmptyOrNull(fechaFin)) {
			fechaFin = CioneUtils.changeDateFormat(fechaFin, "yyyy-MM-dd", CioneConstants.OUPUT_DATE_FORMAT);
		}
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

}
