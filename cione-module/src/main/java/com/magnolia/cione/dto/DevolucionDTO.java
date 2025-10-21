package com.magnolia.cione.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.utils.CioneUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DevolucionDTO {
	
	private String descripcion;
	private String unidades;
	private String unidadesTotalesLinea;
	private String importeVenta;
	private String fecha;
	private String keyAlbaran;
	private String refweb;
	private String refsocio;
	private String estadoDevolucion;
	private String aliasEkon;
	private String nlinAlbaran;
	
	private String pvo;
	private String motivo;
	private String observaciones;
	
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getUnidades() {
		return unidades;
	}
	public String getUnidadesView() {
		if (unidades.contains("."))
			return unidades.substring(0, unidades.indexOf('.'));
		else
			return unidades;
	}
	public void setUnidades(String unidades) {
		this.unidades = unidades;
	}
	public String getUnidadesTotalesLinea() {
		return unidadesTotalesLinea;
	}
	public void setUnidadesTotalesLinea(String unidadesTotalesLinea) {
		this.unidadesTotalesLinea = unidadesTotalesLinea;
	}
	public String getImporteVenta() {
		if(!CioneUtils.isEmptyOrNull(importeVenta)) {
			importeVenta = CioneUtils.changeNumberFormat(importeVenta, "%.2f");
		}
		return importeVenta;
	}
	public void setImporteVenta(String importeVenta) {
		this.importeVenta = importeVenta;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getKeyAlbaran() {
		return keyAlbaran;
	}
	public void setKeyAlbaran(String keyAlbaran) {
		this.keyAlbaran = keyAlbaran;
	}
	public String getRefweb() {
		return refweb;
	}
	public void setRefweb(String refweb) {
		this.refweb = refweb;
	}
	public String getRefsocio() {
		return refsocio;
	}
	public void setRefsocio(String refsocio) {
		this.refsocio = refsocio;
	}
	public String getEstadoDevolucion() {
		return estadoDevolucion;
	}
	public void setEstadoDevolucion(String estadoDevolucion) {
		this.estadoDevolucion = estadoDevolucion;
	}
	public String getAliasEkon() {
		return aliasEkon;
	}
	public void setAliasEkon(String aliasEkon) {
		this.aliasEkon = aliasEkon;
	}
	public String getNlinAlbaran() {
		return nlinAlbaran;
	}
	public void setNlinAlbaran(String nlinAlbaran) {
		this.nlinAlbaran = nlinAlbaran;
	}
	public String getPvo() {
		return pvo;
	}
	public void setPvo(String pvo) {
		this.pvo = pvo;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getFechaView() {
		if(!CioneUtils.isEmptyOrNull(fecha)) {
			fecha = CioneUtils.changeDateFormat(fecha, "yyyy-MM-dd", CioneConstants.OUPUT_DATE_FORMAT);
		}
		return fecha;
	}
}
