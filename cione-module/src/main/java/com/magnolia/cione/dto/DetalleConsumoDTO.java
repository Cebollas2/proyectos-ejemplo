package com.magnolia.cione.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.magnolia.cione.utils.CioneUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DetalleConsumoDTO {

	private Integer id;
	private String nombre;
	private Double cantidad = 0d;
	private Double importe = 0d;
	private String level;
	private String parent;

	// Datos del periodo anterior
	private Double cantidadAnterior = 0d;
	private Double importeAnterior = 0d;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public String getImporteView() {
		return CioneUtils.decimalToView(importe.toString());
	}

	public String getCantidadView() {
		return CioneUtils.integerToView(cantidad.toString());
	}

	public String getImporteAnteriorView() {
		return CioneUtils.decimalToView(importeAnterior.toString());

	}

	public String getCantidadAnteriorView() {
		return CioneUtils.integerToView(cantidadAnterior.toString());

	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Double getCantidadAnterior() {
		return cantidadAnterior;
	}

	public void setCantidadAnterior(Double cantidadAnterior) {
		this.cantidadAnterior = cantidadAnterior;
	}

	public Double getImporteAnterior() {
		return importeAnterior;
	}

	public void setImporteAnterior(Double importeAnterior) {
		this.importeAnterior = importeAnterior;
	}

//	public String getDiferenciaCantidadView() {
//		return CioneUtils.integerToView(String.valueOf(cantidadAnterior - cantidad));
//	}
//
//	public String getDiferenciaImporteView() {
//		return CioneUtils.decimalToView(String.valueOf(importeAnterior - importe));
//	}
	
	public String getDiferenciaCantidadView() {
		return CioneUtils.integerToView(String.valueOf(cantidad - cantidadAnterior));
	}

	public String getDiferenciaImporteView() {
		return CioneUtils.decimalToView(String.valueOf(importe - importeAnterior));
	}
	
//	public String getPorcentajeCantidadView() {
//		if(cantidad != 0)
//			return CioneUtils.decimalToView(String.valueOf(((cantidadAnterior - cantidad)*100) /  cantidad));
//		
//		return CioneUtils.decimalToView(String.valueOf(100));
//	}
//
//	public String getPorcentajeImporteView() {
//		if(importe != 0)
//			return CioneUtils.decimalToView(String.valueOf(((importeAnterior - importe)*100) /  importe));
//		
//		return CioneUtils.decimalToView(String.valueOf(100));
//	}

	public String getPorcentajeCantidadView() {
		if(cantidadAnterior != 0)
			return CioneUtils.decimalToView(String.valueOf(((cantidad - cantidadAnterior)*100) /  cantidadAnterior)) + " %";
		
		return CioneUtils.decimalToView(String.valueOf(100)) + " %";
	}

	public String getPorcentajeImporteView() {
		if(importeAnterior != 0)
			return CioneUtils.decimalToView(String.valueOf(((importe - importeAnterior)*100) /  importeAnterior)) + " %";
		
		return CioneUtils.decimalToView(String.valueOf(100)) + " %";
	}
	
	@Override
	public String toString() {
		return "Detalle Consumo: " + nombre;
	}
}