package com.magnolia.cione.dto;

import com.magnolia.cione.utils.CioneUtils;

public class LineaFacturaDTO {
	
	private String numFactura;
	private String numAlbaran;
	private String notaEntrega;
	private String descripcion;
	private String importeBruto;
	private String tipoImpositivo;
	private String importeDescuento;
	private String importeNeto;
	private String unidades;
	
	public String getNumFactura() {
		return numFactura;
	}
	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}	
	public String getNumAlbaran() {
		return numAlbaran;
	}
	public void setNumAlbaran(String numAlbaran) {
		this.numAlbaran = numAlbaran;
	}
	public String getNotaEntrega() {
		return notaEntrega;
	}
	public void setNotaEntrega(String notaEntrega) {
		this.notaEntrega = notaEntrega;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getImporteBruto() {
		return CioneUtils.decimalToView(importeBruto);		
	}
	public void setImporteBruto(String importeBruto) {
		this.importeBruto = importeBruto;
	}
	public String getTipoImpositivo() {
		return CioneUtils.decimalToView(tipoImpositivo);			
	}
	public void setTipoImpositivo(String tipoImpositivo) {
		this.tipoImpositivo = tipoImpositivo;
	}
	public String getImporteDescuento() {
		return CioneUtils.decimalToView(importeDescuento);		
	}
	public void setImporteDescuento(String importeDescuento) {
		this.importeDescuento = importeDescuento;
	}
	public String getImporteNeto() {
		return CioneUtils.decimalToView(importeNeto);		
	}
	public void setImporteNeto(String importeNeto) {
		this.importeNeto = importeNeto;
	}
	public String getUnidades() {
		return CioneUtils.integerToView(unidades);		
	}
	public void setUnidades(String unidades) {
		this.unidades = unidades;
	}
}
