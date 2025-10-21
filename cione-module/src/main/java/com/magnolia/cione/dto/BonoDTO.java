package com.magnolia.cione.dto;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.utils.CioneUtils;

public class BonoDTO {

	private String tipo;
	private Boolean activo;
	private String importeInicial;
	private String saldo;
	private String fechaIni;
	private String fechaFin;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getImporteInicial() {
		return CioneUtils.decimalToView(importeInicial);
	}

	public void setImporteInicial(String importeInicial) {
		this.importeInicial = importeInicial;
	}

	public String getSaldo() {
		return CioneUtils.decimalToView(saldo);
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
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
