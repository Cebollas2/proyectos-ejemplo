package com.magnolia.cione.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.utils.CioneUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MisConsumosDTO {

	private Double cantidadTotal = 0d;
	private Double importeTotal = 0d;
	private Date minDate;
	private Date maxDate;
	private List<CategoriaConsumoDTO> categorias = new ArrayList<>();

	// Datos del periodo anterior
	private Double cantidadTotalAnterior = 0d;
	private Double importeTotalAnterior = 0d;
	private Date minDateAnterior;
	private Date maxDateAnterior;

	public Double getCantidadTotal() {
		return cantidadTotal;
	}

	public void setCantidadTotal(Double cantidadTotal) {
		this.cantidadTotal = cantidadTotal;
	}

	public Double getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(Double importeTotal) {
		this.importeTotal = importeTotal;
	}

	public List<CategoriaConsumoDTO> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaConsumoDTO> categorias) {
		this.categorias = categorias;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public String getMinDateView() {
		return DateFormatUtils.format(minDate, CioneConstants.DATE_FORMAT_MM_YYYY);

	}

	public String getMaxDateView() {
		return DateFormatUtils.format(maxDate, CioneConstants.DATE_FORMAT_MM_YYYY);
	}

	public String getImporteTotalView() {
		return CioneUtils.decimalToView(importeTotal.toString());
	}

	public String getCantidadTotalView() {
		return CioneUtils.integerToView(cantidadTotal.toString());
	}

	public Double getCantidadTotalAnterior() {
		return cantidadTotalAnterior;
	}

	public void setCantidadTotalAnterior(Double cantidadTotalAnterior) {
		this.cantidadTotalAnterior = cantidadTotalAnterior;
	}

	public Double getImporteTotalAnterior() {
		return importeTotalAnterior;
	}

	public void setImporteTotalAnterior(Double importeTotalAnterior) {
		this.importeTotalAnterior = importeTotalAnterior;
	}

	public Date getMinDateAnterior() {
		return minDateAnterior;
	}

	public void setMinDateAnterior(Date minDateAnterior) {
		this.minDateAnterior = minDateAnterior;
	}

	public Date getMaxDateAnterior() {
		return maxDateAnterior;
	}

	public void setMaxDateAnterior(Date maxDateAnterior) {
		this.maxDateAnterior = maxDateAnterior;
	}

	public String getMinDateAnteriorView() {
		return DateFormatUtils.format(minDateAnterior, CioneConstants.DATE_FORMAT_MM_YYYY);

	}

	public String getMaxDateAnteriorView() {
		return DateFormatUtils.format(maxDateAnterior, CioneConstants.DATE_FORMAT_MM_YYYY);
	}

	public String getImporteTotalAnteriorView() {
		return CioneUtils.decimalToView(importeTotalAnterior.toString());
	}

	public String getCantidadTotalAnteriorView() {
		return CioneUtils.integerToView(cantidadTotalAnterior.toString());
	}
	
	public String getDiferenciaCantidadTotalView() {
		return CioneUtils.integerToView(String.valueOf(cantidadTotal - cantidadTotalAnterior));
	}

	public String getDiferenciaImporteTotalView() {
		return CioneUtils.decimalToView(String.valueOf(importeTotal - importeTotalAnterior));
	}
	
	public String getPorcentajeCantidadTotalView() {
		if(cantidadTotal != 0)
			return CioneUtils.decimalToView(String.valueOf(((cantidadTotal - cantidadTotalAnterior)*100) /  cantidadTotalAnterior)) + " %";
		return CioneUtils.decimalToView(String.valueOf(100)) + " %";
	}

	public String getPorcentajeImporteTotalView() {
		if(importeTotal != 0)
			return CioneUtils.decimalToView(String.valueOf(((importeTotal - importeTotalAnterior )*100) /  importeTotalAnterior)) + " %";
		
		return CioneUtils.decimalToView(String.valueOf(100)) + " %";
	}

}
