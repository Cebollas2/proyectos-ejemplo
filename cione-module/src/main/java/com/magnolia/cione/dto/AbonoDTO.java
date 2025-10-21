package com.magnolia.cione.dto;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.utils.CioneUtils;

public class AbonoDTO {
	private String numAbono;
	private String fecha;
	private String tipo;
	private String estado;
	public String getNumAbono() {
		return numAbono;
	}
	public void setNumAbono(String numAbono) {
		this.numAbono = numAbono;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFechaView() {
		if(!CioneUtils.isEmptyOrNull(fecha)) {
			fecha = CioneUtils.changeDateFormat(fecha, "yyyy-MM-dd", CioneConstants.OUPUT_DATE_FORMAT);
		}
		return fecha;
	}
	
	
}
