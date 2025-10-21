package com.magnolia.cione.dto;

import com.magnolia.cione.utils.CioneUtils;

public class LineaAlbaranDTO {

	private String numLinea;
	private String descripcion;
	private String marca;
	private String refSocio;
	private String idWeb;
	private String refWeb;
	private String unidades;
	
	public String getNumLinea() {
		return numLinea;
	}
	public void setNumLinea(String numLinea) {
		this.numLinea = numLinea;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getRefSocio() {
		return refSocio;
	}
	public void setRefSocio(String refSocio) {
		this.refSocio = refSocio;
	}
	public String getIdWeb() {
		return idWeb;
	}
	public void setIdWeb(String idWeb) {
		this.idWeb = idWeb;
	}
	public String getRefWeb() {
		return refWeb;
	}
	public void setRefWeb(String refWeb) {
		this.refWeb = refWeb;
	}
	public String getUnidades() {
		if(!CioneUtils.isEmptyOrNull(unidades)) {
			unidades = CioneUtils.changeNumberFormat(unidades.replaceAll(",", "."), "%.0f");
		}
		return unidades;
	}
	public void setUnidades(String unidades) {
		this.unidades = unidades;
	}
	
    @Override
    public String toString()
    {
        return "LineaAlbaranDTO [numLinea = "+numLinea+", descripcion = "+descripcion+", marca = "+marca+", refSocio = "+refSocio+", idWeb = "+idWeb+", refWeb = "+refWeb+", unidades = "+unidades+"]";
    }

}
