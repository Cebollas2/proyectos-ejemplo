package com.magnolia.cione.dto;

public class InteraccionDTO {
	private String id;
	private String fecha;
	private String tipo;
	private String tema;
	private String estado;
	private String solucion;
	private String fechaCierre;
	private String dias;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTema() {
		return tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getSolucion() {
		return solucion;
	}
	public void setSolucion(String solucion) {
		this.solucion = solucion;
	}
	public String getFechaCierre() {
		return fechaCierre;
	}
	public void setFechaCierre(String fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	public String getDias() {
		return dias;
	}
	public void setDias(String dias) {
		this.dias = dias;
	}
	
//	public String getFechaView() {
//		if(!CioneUtils.isEmptyOrNull(fecha)) {
//			fecha = CioneUtils.changeDateFormat(fecha, "yyyy-MM-dd", CioneConstants.OUPUT_DATE_FORMAT);
//		}
//		return fecha;
//	}
//	
//	public String getFechaCierreView() {
//		if(!CioneUtils.isEmptyOrNull(fechaCierre)) {
//			fecha = CioneUtils.changeDateFormat(fechaCierre, "yyyy-MM-dd", CioneConstants.OUPUT_DATE_FORMAT);
//		}
//		return fecha;
//	}
}
