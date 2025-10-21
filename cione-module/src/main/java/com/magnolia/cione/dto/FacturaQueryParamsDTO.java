package com.magnolia.cione.dto;

public class FacturaQueryParamsDTO {

	private Integer pagina;
	private String codsocio;
	private String numFactura;
	private String fechaIni;
	private String fechaFin;
	private String numAlbaran;
	private String tipo;
	private String notaEntrega;
	
	public Integer getPagina() {
		return pagina;
	}
	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}
	public String getCodsocio() {
		return codsocio;
	}
	public void setCodsocio(String codsocio) {
		this.codsocio = codsocio;
	}
	public String getNumFactura() {
		return numFactura;
	}
	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}
	public String getFechaIni() {
		return fechaIni;
	}
	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getNumAlbaran() {
		return numAlbaran;
	}
	public void setNumAlbaran(String numAlbaran) {
		this.numAlbaran = numAlbaran;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNotaEntrega() {
		return notaEntrega;
	}
	public void setNotaEntrega(String notaEntrega) {
		this.notaEntrega = notaEntrega;
	}
}
