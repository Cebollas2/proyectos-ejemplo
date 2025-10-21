package com.magnolia.cione.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AuditarDocumentosQueryParamsDTO {

	private String codSocio;
	private String uuidDocumento;
	private String nombreDocumento;
	private int descargas = -1;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date fechaIni;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date fechaFin;
	
	public String getUuidDocumento() {
		return uuidDocumento;
	}
	public void setUuidDocumento(String uuidDocumento) {
		this.uuidDocumento = uuidDocumento;
	}
	public String getCodSocio() {
		return codSocio;
	}
	public void setCodSocio(String codSocio) {
		this.codSocio = codSocio;
	}
	public String getNombreDocumento() {
		return nombreDocumento;
	}
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}
	public int getDescargas() {
		return descargas;
	}
	public void setDescargas(int descargas) {
		this.descargas = descargas;
	}
	public Date getFechaIni() {
		return fechaIni;
	}
	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	
}
