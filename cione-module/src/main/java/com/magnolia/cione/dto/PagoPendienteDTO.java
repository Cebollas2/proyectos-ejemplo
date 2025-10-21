package com.magnolia.cione.dto;

import com.magnolia.cione.utils.CioneUtils;

public class PagoPendienteDTO {
	private String fecha;
	private String numFactura;
	private String descripcion;
	private String vencimiento;
	private String importeInicial;
	private String importePendiente;
	private String saldo;

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNumFactura() {
		return numFactura;
	}

	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(String vencimiento) {
		this.vencimiento = vencimiento;
	}

	public String getImporteInicial() {
		return CioneUtils.decimalToView(importeInicial);		
	}

	public void setImporteInicial(String importeInicial) {
		this.importeInicial = importeInicial;
	}

	public String getImportePendiente() {
		return CioneUtils.decimalToView(importePendiente);		
	}

	public void setImportePendiente(String importePendiente) {
		this.importePendiente = importePendiente;
	}

	public String getSaldo() {
		return CioneUtils.decimalToView(saldo);				
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}
	
	public String getFechaView() {
		if(!CioneUtils.isEmptyOrNull(fecha)) {
			fecha = CioneUtils.changeDateFormat(fecha, "dd/MM/yyyy", "MM/yyyy");
		}
		return fecha;
	}

}
