package com.magnolia.cione.dto;

public class FacturaDocQueryParamsDTO {
	private String codSocio;
	private Integer pagina;
	private String tiposDisponibles;
	private String tipoDocumento;
	private String fechaDesde;
	private String fechaHasta;
	private String nifSocio;
	
	public String getNifSocio() {
		return nifSocio;
	}
	public void setNifSocio(String nifSocio) {
		this.nifSocio = nifSocio;
	}
	public String getCodSocio() {
		return codSocio;
	}
	public void setCodSocio(String codSocio) {
		this.codSocio = codSocio;
	}	
	public Integer getPagina() {
		return pagina;
	}
	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}
	public String getTiposDisponibles() {
		return tiposDisponibles;
	}
	public void setTiposDisponibles(String tiposDisponibles) {
		this.tiposDisponibles = tiposDisponibles;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public String getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

}
