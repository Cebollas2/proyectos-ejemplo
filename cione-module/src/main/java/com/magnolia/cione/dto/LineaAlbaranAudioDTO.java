package com.magnolia.cione.dto;

import com.magnolia.cione.utils.CioneUtils;

public class LineaAlbaranAudioDTO {

	public String numPedido;
    public String descripcionArticulo;
    public String unidades;
    public String importeLinea;
    public String loteCheque;
    
	public String getNumPedido() {
		return numPedido;
	}
	public void setNumPedido(String numPedido) {
		this.numPedido = numPedido;
	}
	public String getDescripcionArticulo() {
		return descripcionArticulo;
	}
	public void setDescripcionArticulo(String descripcionArticulo) {
		this.descripcionArticulo = descripcionArticulo;
	}
	public String getUnidades() {
		return CioneUtils.decimalToView(unidades.toString());
	}
	public void setUnidades(String unidades) {
		this.unidades = unidades;
	}
	public String getImporteLinea() {
		return CioneUtils.decimalToView(importeLinea.toString());
	}
	public void setImporteLinea(String importeLinea) {
		this.importeLinea = importeLinea;
	}
	public String getLoteCheque() {
		return loteCheque;
	}
	public void setLoteCheque(String loteCheque) {
		this.loteCheque = loteCheque;
	}
    
    
}
