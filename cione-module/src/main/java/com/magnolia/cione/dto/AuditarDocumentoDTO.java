package com.magnolia.cione.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AuditarDocumentoDTO {

	public String idSocio;
	public String uuidDocumento;
	public String nombreDocumento;
	public String rutaDocumento;
	public int descargas;
	public Date fechaDescarga;
	
	public String getIdSocio() {
		return idSocio;
	}
	public void setIdSocio(String idSocio) {
		this.idSocio = idSocio;
	}
	public String getUuidDocumento() {
		return uuidDocumento;
	}
	public void setUuidDocumento(String uuidDocumento) {
		this.uuidDocumento = uuidDocumento;
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
	public Date getFechaDescarga() {
		return fechaDescarga;
	}
	public void setFechaDescarga(Date fechaDescarga) {
		this.fechaDescarga = fechaDescarga;
	}
	public String getRutaDocumento() {
		return rutaDocumento;
	}
	public void setRutaDocumento(String rutaDocumento) {
		this.rutaDocumento = rutaDocumento;
	}
	
	
}
