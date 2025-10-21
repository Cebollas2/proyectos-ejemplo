package com.magnolia.cione.dto;

import com.magnolia.cione.utils.CioneUtils;

public class CabeceraAlbDTO {

	public String idsocio;
	public String tipo;
    public String fecha;
    public String numAlbaran;
    public String numEnvio;
	public String numPedido;
    public String importeNeto;
    public String url;
    
	public String getIdsocio() {
		return idsocio;
	}
	public void setIdsocio(String idsocio) {
		this.idsocio = idsocio;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getNumAlbaran() {
		return numAlbaran;
	}
	public void setNumAlbaran(String numAlbaran) {
		this.numAlbaran = numAlbaran;
	}
    public String getNumEnvio() {
		return numEnvio;
	}
	public void setNumEnvio(String numEnvio) {
		this.numEnvio = numEnvio;
	}
	public String getNumPedido() {
		return numPedido;
	}
	public void setNumPedido(String numPedido) {
		this.numPedido = numPedido;
	}
	public String getImporteNeto() {
		if(!CioneUtils.isEmptyOrNull(importeNeto)) {
			importeNeto = CioneUtils.changeNumberFormat(importeNeto.replaceAll(",", "."), "%.2f");
		}
		return importeNeto;
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
	
    @Override
    public String toString()
    {
        return "CabeceraAlbDTO [idsocio = "+idsocio+", tipo = "+tipo+", fecha = "+fecha+", numAlbaran = "+numAlbaran+", numEnvio = \"+numEnvio+\", numPedido = "+numPedido+", importeNeto = "+importeNeto+", url = "+url+"]";
    }
}
