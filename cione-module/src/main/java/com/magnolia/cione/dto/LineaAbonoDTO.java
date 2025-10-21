package com.magnolia.cione.dto;

import com.magnolia.cione.utils.CioneUtils;

public class LineaAbonoDTO {
	private String descripcionArticulo;
	private String unidadesPedidas;
	private String unidadesAbonadas;
	private String precioUnitario;
	private String precioTotal;
	private String estadoEnvioProducto;
	private String refSocio;
	private String refWeb;
	private String numPedido;
	private String numAlbaranCargo;

	public String getDescripcionArticulo() {
		return descripcionArticulo;
	}

	public void setDescripcionArticulo(String descripcionArticulo) {
		this.descripcionArticulo = descripcionArticulo;
	}

	public String getUnidadesPedidas() {
		return CioneUtils.integerToView(unidadesPedidas);		
	}

	public void setUnidadesPedidas(String unidadesPedidas) {
		this.unidadesPedidas = unidadesPedidas;
	}

	public String getUnidadesAbonadas() {
		return CioneUtils.integerToView(unidadesAbonadas);		
	}

	public void setUnidadesAbonadas(String unidadesAbonadas) {
		this.unidadesAbonadas = unidadesAbonadas;
	}

	public String getPrecioUnitario() {
		return CioneUtils.decimalToView(precioUnitario);
	}

	public void setPrecioUnitario(String precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public String getPrecioTotal() {		
		return CioneUtils.decimalToView(precioTotal);
	}

	public void setPrecioTotal(String precioTotal) {
		this.precioTotal = precioTotal;
	}

	public String getEstadoEnvioProducto() {
		return estadoEnvioProducto;
	}

	public void setEstadoEnvioProducto(String estadoEnvioProducto) {
		this.estadoEnvioProducto = estadoEnvioProducto;
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

	public String getNumPedido() {
		return numPedido;
	}

	public void setNumPedido(String numPedido) {
		this.numPedido = numPedido;
	}

	public String getNumAlbaranCargo() {
		return numAlbaranCargo;
	}

	public void setNumAlbaranCargo(String numAlbaranCargo) {
		this.numAlbaranCargo = numAlbaranCargo;
	}

}
