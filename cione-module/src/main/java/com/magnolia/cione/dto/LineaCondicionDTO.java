package com.magnolia.cione.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.magnolia.cione.utils.CioneUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LineaCondicionDTO {
	

	private String nombreAcuerdo;
	private String factBrutaGrupo;
	private String factBrutaPVenta;
	private String porcDevengo;
	private String impDevengo;
	private String impLiquidado;
	private String factNetaDevenga;
	
	public String getNombreAcuerdo() {
		return nombreAcuerdo;
	}
	public void setNombreAcuerdo(String nombreAcuerdo) {
		this.nombreAcuerdo = nombreAcuerdo;
	}
	public String getFactBrutaGrupo() {
		if(factBrutaGrupo!=null) {
			return CioneUtils.decimalToView(factBrutaGrupo.toString());
		}else {
			return "";
		}
	}
	public void setFactBrutaGrupo(String factBrutaGrupo) {
		this.factBrutaGrupo = factBrutaGrupo;
	}
	public String getFactBrutaPVenta() {
		if(factBrutaPVenta!=null) {
			return CioneUtils.decimalToView(factBrutaPVenta.toString());
		}else {
			return "";
		}
	}
	public void setFactBrutaPVenta(String factBrutaPVenta) {
		this.factBrutaPVenta = factBrutaPVenta;
	}
	public String getPorcDevengo() {
		if((porcDevengo!=null) && !porcDevengo.isEmpty()) {
			return CioneUtils.decimalToView(porcDevengo.toString()) + "%";
		}else {
			return "";
		}
	}
	public void setPorcDevengo(String porcDevengo) {
		this.porcDevengo = porcDevengo;
	}
	public String getImpDevengo() {
		if(impDevengo!=null) {
			return CioneUtils.decimalToView(impDevengo.toString());
		}else {
			return "";
		}
	}
	public void setImpDevengo(String impDevengo) {
		this.impDevengo = impDevengo;
	}
	public String getImpLiquidado() {
		if(impLiquidado!=null) {
			return CioneUtils.decimalToView(impLiquidado.toString());
		}else {
			return "";
		}
	}
	public void setImpLiquidado(String impLiquidado) {
		this.impLiquidado = impLiquidado;
	}
	public String getFactNetaDevenga() {
		if(factNetaDevenga!=null) {
			return CioneUtils.decimalToView(factNetaDevenga.toString());
		}else {
			return "";
		}
	}
	public void setFactNetaDevenga(String factNetaDevenga) {
		this.factNetaDevenga = factNetaDevenga;
	}

}
