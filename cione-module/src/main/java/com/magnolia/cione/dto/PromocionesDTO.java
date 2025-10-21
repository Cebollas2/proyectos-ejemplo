package com.magnolia.cione.dto;

import java.sql.Date;

public class PromocionesDTO {
	private int id;
	private String aliasEkon;
	private String grupoPrecio;
	private String descripcion;
	private int tipo_descuento;
	private int cantidad_desde;
	private int cantidad_hasta;
	private double valor;
	private Date fecha_desde;
	private Date fecha_hasta;
	
	private String pvo;
	private String pvoDto;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public boolean aplicaPromo(int cantidad) {
		if (this.cantidad_hasta> cantidad) {
			return true;
		} else {
			return false;
		}
	}
	
	public double getDtoEscalado(int cantidad) {
		if ((cantidad >= this.cantidad_desde) && (cantidad < cantidad_hasta)) {
			return this.valor;
		} else {
			return 0;
		}
	}
	
	public String getAliasEkon() {
		return aliasEkon;
	}
	public void setAliasEkon(String aliasEkon) {
		this.aliasEkon = aliasEkon;
	}
	public String getGrupoPrecio() {
		return grupoPrecio;
	}
	public void setGrupoPrecio(String grupoPrecio) {
		this.grupoPrecio = grupoPrecio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getTipo_descuento() {
		return tipo_descuento;
	}
	public void setTipo_descuento(int tipo_descuento) {
		this.tipo_descuento = tipo_descuento;
	}
	public int getCantidad_desde() {
		return cantidad_desde;
	}
	public void setCantidad_desde(int cantidad_desde) {
		this.cantidad_desde = cantidad_desde;
	}
	public int getCantidad_hasta() {
		return cantidad_hasta;
	}
	public void setCantidad_hasta(int cantidad_hasta) {
		this.cantidad_hasta = cantidad_hasta;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public Date getFecha_desde() {
		return fecha_desde;
	}
	public void setFecha_desde(Date fecha_desde) {
		this.fecha_desde = fecha_desde;
	}
	public Date getFecha_hasta() {
		return fecha_hasta;
	}
	public void setFecha_hasta(Date fecha_hasta) {
		this.fecha_hasta = fecha_hasta;
	}
	public String getPvo() {
		return pvo;
	}
	public void setPvo(String pvo) {
		this.pvo = pvo;
	}
	public String getPvoDto() {
		return pvoDto;
	}
	public void setPvoDto(String pvoDto) {
		this.pvoDto = pvoDto;
	}	

}
