package com.magnolia.cione.dto;

import com.magnolia.cione.utils.CioneUtils;

public class LineaPedidoDTO {

	private String lineaPedido;
	private String descripcionArticulo;
	private String unidadesPedidas;
	private String unidadesEntregadas;
	private String precioUnitario;
	private String precioTotal;
	private String estadoPedido;
	private String idWeb;
	private String refSocio;
	private String refWeb;
	private String fechaEntregaPrevista;
	
	public String getLineaPedido() {
		return lineaPedido;
	}
	public void setLineaPedido(String lineaPedido) {
		this.lineaPedido = lineaPedido;
	}
	public String getDescripcionArticulo() {
		return descripcionArticulo;
	}
	public void setDescripcionArticulo(String descripcionArticulo) {
		this.descripcionArticulo = descripcionArticulo;
	}
	public String getUnidadesPedidas() {
		if(!CioneUtils.isEmptyOrNull(unidadesPedidas)) {
			unidadesPedidas = CioneUtils.changeNumberFormat(unidadesPedidas, "%.0f");
		}
		return unidadesPedidas;
	}
	public void setUnidadesPedidas(String unidadesPedidas) {
		this.unidadesPedidas = unidadesPedidas;
	}
	public String getUnidadesEntregadas() {
		if(!CioneUtils.isEmptyOrNull(unidadesEntregadas)) {
			unidadesEntregadas = CioneUtils.changeNumberFormat(unidadesEntregadas, "%.0f");
		}
		return unidadesEntregadas;
	}
	public void setUnidadesEntregadas(String unidadesEntregadas) {
		this.unidadesEntregadas = unidadesEntregadas;
	}
	public String getPrecioUnitario() {
		if(!CioneUtils.isEmptyOrNull(precioUnitario)) {
			precioUnitario = CioneUtils.changeNumberFormat(precioUnitario, "%.2f");
		}
		return precioUnitario;
	}
	public void setPrecioUnitario(String precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public String getPrecioTotal() {
		if(!CioneUtils.isEmptyOrNull(precioTotal)) {
			precioTotal = CioneUtils.changeNumberFormat(precioTotal, "%.2f");
		}
		return precioTotal;
	}
	public void setPrecioTotal(String precioTotal) {
		this.precioTotal = precioTotal;
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
	public String getFechaEntregaPrevista() {
		return fechaEntregaPrevista;
	}
	public void setFechaEntregaPrevista(String fechaEntregaPrevista) {
		this.fechaEntregaPrevista = fechaEntregaPrevista;
	}
	
	public String getFechaEntregaPrevistaView() {
//		if(!CioneUtils.isEmptyOrNull(fechaEntregaPrevista)) {
//			fechaEntregaPrevista = CioneUtils.changeDateFormat(fechaEntregaPrevista, "yyyy-MM-dd", CioneConstants.OUPUT_DATE_FORMAT);
//		}
		return fechaEntregaPrevista;
	}
	
}
