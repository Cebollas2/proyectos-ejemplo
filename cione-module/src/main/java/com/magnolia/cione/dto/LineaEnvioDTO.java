package com.magnolia.cione.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.utils.CioneUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LineaEnvioDTO {

	private String articulo;
	private String unidades;
	private String importeBruto;
	private String descuento;
	private String importeVenta;
	private String referenciaWeb;
	private String referenciaTaller;
	private String albaran;
	private String fechaAlbaran;
	
	public String getArticulo() {
		return articulo;
	}
	public void setArticulo(String articulo) {
		this.articulo = articulo;
	}
	public String getUnidades() {
		return CioneUtils.integerToView(unidades);
	}
	public void setUnidades(String unidades) {
		this.unidades = unidades;
	}
	public String getImporteBruto() {
		return CioneUtils.decimalToView(importeBruto);
	}
	public void setImporteBruto(String importeBruto) {
		this.importeBruto = importeBruto;
	}
	public String getDescuento() {
		return CioneUtils.decimalToView(descuento);
	}
	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}
	public String getImporteVenta() {
		return CioneUtils.decimalToView(importeVenta);
	}
	public void setImporteVenta(String importeVenta) {
		this.importeVenta = importeVenta;
	}
	public String getReferenciaWeb() {
		return referenciaWeb;
	}
	public void setReferenciaWeb(String referenciaWeb) {
		this.referenciaWeb = referenciaWeb;
	}
	public String getReferenciaTaller() {
		return referenciaTaller;
	}
	public void setReferenciaTaller(String referenciaTaller) {
		this.referenciaTaller = referenciaTaller;
	}
	public String getAlbaran() {
		return albaran;
	}
	public void setAlbaran(String albaran) {
		this.albaran = albaran;
	}
	public String getFechaAlbaran() {
		return fechaAlbaran;
	}
	public void setFechaAlbaran(String fechaAlbaran) {
		this.fechaAlbaran = fechaAlbaran;
	}
	public String getFechaAlbaranView() {
		if(!CioneUtils.isEmptyOrNull(fechaAlbaran)) {
			fechaAlbaran = CioneUtils.changeDateFormat(fechaAlbaran, "yyyy-MM-dd", CioneConstants.OUPUT_DATE_FORMAT);
		}
		return fechaAlbaran;
	}
	

	/*public String getCantidad() {
		return CioneUtils.integerToView(cantidad);		
	}
	public void setCantidad(String cantidad) {		
		this.cantidad = cantidad;
	}
	public String getImporte() {
		return CioneUtils.decimalToView(importe);		
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}*/
	
	

}
