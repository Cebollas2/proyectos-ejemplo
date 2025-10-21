package com.magnolia.cione.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.utils.CioneUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GestionDevolucionDTO {
	
	private String descripcion;
	private String unidades;
	private String importeVenta;
	private String fecha;
	private String keyAlbaran;
	private String refweb;
	private String refsocio;
	private String estadoDevolucion;
	private String albaranAbono;
	private String fechaAbono;
	private String numRMA;

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
	public String getAlbaranAbono() {
		return albaranAbono;
	}
	public void setAlbaranAbono(String albaranAbono) {
		this.albaranAbono = albaranAbono;
	}
	public String getFechaAbono() {
		return fechaAbono;
	}
	public void setFechaAbono(String fechaAbono) {
		this.fechaAbono = fechaAbono;
	}
	public String getFechaView() {
		if(!CioneUtils.isEmptyOrNull(fecha)) {
			fecha = CioneUtils.changeDateFormat(fecha, "yyyy-MM-dd", CioneConstants.OUPUT_DATE_FORMAT);
		}
		return fecha;
	}
	public String getFechaAbonoView() {
		if(!CioneUtils.isEmptyOrNull(fechaAbono)) {
			fechaAbono = CioneUtils.changeDateFormat(fechaAbono, "yyyy-MM-dd", CioneConstants.OUPUT_DATE_FORMAT);
		}
		return fechaAbono;
	}
	
	public String getNumRMA() {
		return numRMA;
	}
	public void setNumRMA(String numRMA) {
		this.numRMA = numRMA;
	}
}
