package com.magnolia.cione.dto;

import com.magnolia.cione.utils.CioneUtils;

public class LineaTallerDTO {

	private String descripcion;
	private String tipoTrabajo;
	private String cantidad;
	private String precio;
	private String refSocio;
	private String refTrabajo;
	private String estadoPedido;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTipoTrabajo() {
		return tipoTrabajo;
	}
	public void setTipoTrabajo(String tipoTrabajo) {
		this.tipoTrabajo = tipoTrabajo;
	}
	public String getCantidad() {
		if(!CioneUtils.isEmptyOrNull(cantidad)) {
			cantidad = CioneUtils.changeNumberFormat(cantidad, "%.0f");
		}
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public String getPrecio() {
		if(!CioneUtils.isEmptyOrNull(precio)) {
			precio = CioneUtils.changeNumberFormat(precio, "%.2f");
		}
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public String getRefSocio() {
		return refSocio;
	}
	public void setRefSocio(String refSocio) {
		this.refSocio = refSocio;
	}
	public String getRefTrabajo() {
		return refTrabajo;
	}
	public void setRefTrabajo(String refTrabajo) {
		this.refTrabajo = refTrabajo;
	}
	public String getEstadoPedido() {
		return estadoPedido;
	}
	public void setEstadoPedido(String estadoPedido) {
		this.estadoPedido = estadoPedido;
	}
	
}
