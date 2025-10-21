package com.magnolia.cione.dto;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.utils.CioneUtils;

public class TallerDTO {
	
	private String idServicio;
	private String numPedido;
	private String fecha;
	private String refTrabajo;
	private String estadoOptilab;
	private String estadoPedido;
	private String idWeb;
	private String fechaEstimadaEntrega;
	private String historico;
	
	public String getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}

	public String getNumPedido() {
		return numPedido;
	}

	public void setNumPedido(String numPedido) {
		this.numPedido = numPedido;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getRefTrabajo() {
		return refTrabajo;
	}

	public void setRefTrabajo(String refTrabajo) {
		this.refTrabajo = refTrabajo;
	}

	public String getEstadoOptilab() {
		return estadoOptilab;
	}

	public void setEstadoOptilab(String estadoOptilab) {
		this.estadoOptilab = estadoOptilab;
	}

	public String getEstadoPedido() {
		return estadoPedido;
	}

	public void setEstadoPedido(String estadoPedido) {
		this.estadoPedido = estadoPedido;
	}

	public String getIdWeb() {
		return idWeb;
	}

	public void setIdWeb(String idWeb) {
		this.idWeb = idWeb;
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
