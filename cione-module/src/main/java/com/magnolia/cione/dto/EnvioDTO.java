package com.magnolia.cione.dto;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.utils.CioneUtils;

public class EnvioDTO {
	private String numEnvio;
	private String fecha;
	private String tipoEnvio;
	private String numBultos;
	private String agencia;
	private String trackingPedido;
	private String estado;
	private String urlAgencia;
	
	public String getNumEnvio() {
		return numEnvio;
	}
	public void setNumEnvio(String numEnvio) {
		this.numEnvio = numEnvio;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getTipoEnvio() {
		return tipoEnvio;
	}
	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}
	public String getNumBultos() {
		return numBultos;
	}
	public void setNumBultos(String numBultos) {
		this.numBultos = numBultos;
	}
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public String getTrackingPedido() {
		return trackingPedido;
	}
	public void setTrackingPedido(String trackingPedido) {
		this.trackingPedido = trackingPedido;
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
	public String getUrlAgencia() {
		return urlAgencia;
	}
	public void setUrlAgencia(String urlAgencia) {
		this.urlAgencia = urlAgencia;
	}
	
}
