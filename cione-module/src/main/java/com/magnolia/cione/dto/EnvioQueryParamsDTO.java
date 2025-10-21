package com.magnolia.cione.dto;

public class EnvioQueryParamsDTO {
	private Integer pagina;
	private String codSocio;
	private String numEnvio;
	private String fechaIni;
	private String fechaFin;
	private String trackingPedido;
	private String numAlbaran;
	public Integer getPagina() {
		return pagina;
	}
	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}
	public String getCodSocio() {
		return codSocio;
	}
	public void setCodSocio(String codSocio) {
		this.codSocio = codSocio;
	}
	public String getNumEnvio() {
		return numEnvio;
	}
	public void setNumEnvio(String numEnvio) {
		this.numEnvio = numEnvio;
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
	public String getTrackingPedido() {
		return trackingPedido;
	}
	public void setTrackingPedido(String trackingPedido) {
		this.trackingPedido = trackingPedido;
	}
	public String getNumAlbaran() {
		return numAlbaran;
	}
	public void setNumAlbaran(String numAlbaran) {
		this.numAlbaran = numAlbaran;
	}
	
}
