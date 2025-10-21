package com.magnolia.cione.dto;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.utils.CioneUtils;

public class AlbaranAudioDTO {

	private String tipoVenta;
	private String tipoAlbaran;
	private String numAlbaran;
	private String albProveedor;
	private String fecha;
	private String importeNeto;
	private String url;
	
	public String getTipoVenta() {
		return tipoVenta;
	}
	public void setTipoVenta(String tipoVenta) {
		this.tipoVenta = tipoVenta;
	}
	public String getTipoAlbaran() {
		return tipoAlbaran;
	}
	public void setTipoAlbaran(String tipoAlbaran) {
		this.tipoAlbaran = tipoAlbaran;
	}
	public String getNumAlbaran() {
		return numAlbaran;
	}
	public void setNumAlbaran(String numAlbaran) {
		this.numAlbaran = numAlbaran;
	}
	public String getAlbProveedor() {
		return albProveedor;
	}
	public void setAlbProveedor(String albProveedor) {
		this.albProveedor = albProveedor;
	}
	public String getFecha() {
		return CioneUtils.changeDateFormat(fecha, "yyyy-MM-dd", CioneConstants.OUPUT_DATE_FORMAT);
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getImporteNeto() {
		return CioneUtils.decimalToView(importeNeto.toString());
	}
	public void setImporteNeto(String importeNeto) {
		this.importeNeto = importeNeto;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
