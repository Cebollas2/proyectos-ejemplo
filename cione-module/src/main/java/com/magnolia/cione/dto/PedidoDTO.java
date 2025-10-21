package com.magnolia.cione.dto;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.utils.CioneUtils;

public class PedidoDTO {
	private String numPedido;
	private String idWeb;
	private String fecha;
	private String tipoPedido;
	private String refSocio;
	private String refWeb;
	private String estado;
	private String fechaEstimadaEntrega;
	private String historico;
	public String getNumPedido() {
		return numPedido;
	}
	public void setNumPedido(String numPedido) {
		this.numPedido = numPedido;
	}
	public String getIdWeb() {
		return idWeb;
	}
	public void setIdWeb(String idWeb) {
		this.idWeb = idWeb;
	}
	public String getFecha() {	
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getTipoPedido() {
		return tipoPedido;
	}
	public void setTipoPedido(String tipoPedido) {
		this.tipoPedido = tipoPedido;
	}
	public String getRefSocio() {
		return refSocio;
	}
	public void setRefSocio(String refSocio) {
		this.refSocio = refSocio;
	}
	public String getRefWeb() {
		return refWeb;
	}
	public void setRefWeb(String refWeb) {
		this.refWeb = refWeb;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFechaEstimadaEntrega() {	
		return fechaEstimadaEntrega;
	}
	
	public void setFechaEstimadaEntrega(String fechaEstimadaEntrega) {
		this.fechaEstimadaEntrega = fechaEstimadaEntrega;
	}
	public String getHistorico() {
		return historico;
	}
	public void setHistorico(String historico) {
		this.historico = historico;
	}
	
	public String getFechaEstimadaEntregaView() {
		if(!CioneUtils.isEmptyOrNull(fechaEstimadaEntrega)) {
			fechaEstimadaEntrega = CioneUtils.changeDateFormat(fechaEstimadaEntrega, "yyyy-MM-dd", CioneConstants.OUPUT_DATE_FORMAT);
		}
		return fechaEstimadaEntrega;
	}
	
	public String getFechaView() {
		if(!CioneUtils.isEmptyOrNull(fecha)) {
			fecha = CioneUtils.changeDateFormat(fecha, "yyyy-MM-dd", CioneConstants.OUPUT_DATE_FORMAT);
		}
		return fecha;
	}
	
}
