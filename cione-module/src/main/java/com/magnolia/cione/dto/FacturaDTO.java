package com.magnolia.cione.dto;

import com.magnolia.cione.utils.CioneUtils;

public class FacturaDTO {
	
	public String idsocio;
	public String numFactura;
    public String importeNeto;
    public String importeTotal;
    public String tipo;
    public String fecha;
    public String url;
    
	public String getIdsocio() {
		return idsocio;
	}
	public void setIdsocio(String idsocio) {
		this.idsocio = idsocio;
	}
	public String getNumFactura() {
		return numFactura;
	}
	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}
	public String getImporteNeto() {
		return CioneUtils.decimalToView(importeNeto.replaceAll(",", "."));		
	}
	public void setImporteNeto(String importeNeto) {
		this.importeNeto = importeNeto;
	}
	public String getImporteTotal() {
		return CioneUtils.decimalToView(importeTotal.replaceAll(",", "."));		
	}
	public void setImporteTotal(String importeTotal) {
		this.importeTotal = importeTotal;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
