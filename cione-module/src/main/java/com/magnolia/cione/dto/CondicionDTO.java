package com.magnolia.cione.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.magnolia.cione.utils.CioneUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CondicionDTO {
	

	private String nombreAcuerdo;
	private String factBrutaGrupo;
	private String factBrutaPVenta;
	private String porcDevengo;
	private String impDevengo;
	private String impLiquidado;
	private String factNetaDevenga;
	private String rappelActual;
	private String cionesAcumuladosPVenta;
	private String cionesAcumuladosGrupo;
	private String facNetaGrupoSubirCateg;
	private String sigRappel;
	private String idAcuerdo;
	private boolean showDetail;
	
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
		if((porcDevengo!=null) && (!porcDevengo.isEmpty())) {
			return CioneUtils.decimalToView(porcDevengo.toString())  + "%";
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
	public String getRappelActual() {
		if((rappelActual!=null) && !rappelActual.isEmpty()) {
			return CioneUtils.decimalToView(rappelActual.toString()) + "%";
		}else {
			return "";
		}
	}
	public void setRappelActual(String rappelActual) {
		this.rappelActual = rappelActual;
	}
	public String getCionesAcumuladosPVenta() {
		if(cionesAcumuladosPVenta!=null) {
			return CioneUtils.decimalToView(cionesAcumuladosPVenta.toString());
		}else {
			return "";
		}
	}
	public String getCionesAcumuladosPVentaDecimalFormat() {
		return cionesAcumuladosPVenta;
	}
	public void setCionesAcumuladosPVenta(String cionesAcumuladosPVenta) {
		this.cionesAcumuladosPVenta = cionesAcumuladosPVenta;
	}
	public String getCionesAcumuladosGrupo() {
		if(cionesAcumuladosGrupo!=null) {
			return CioneUtils.decimalToView(cionesAcumuladosGrupo.toString());
		}else {
			return "";
		}
	}
	public String getCionesAcumuladosGrupoDecimalFormat() {
		return cionesAcumuladosGrupo;
	}
	public void setCionesAcumuladosGrupo(String cionesAcumuladosGrupo) {
		this.cionesAcumuladosGrupo = cionesAcumuladosGrupo;
	}
	public String getFacNetaGrupoSubirCateg() {
		if(facNetaGrupoSubirCateg!=null) {
			return CioneUtils.decimalToView(facNetaGrupoSubirCateg.toString());
		}else {
			return "";
		}
	}
	public void setFacNetaGrupoSubirCateg(String facNetaGrupoSubirCateg) {
		this.facNetaGrupoSubirCateg = facNetaGrupoSubirCateg;
	}
	public String getSigRappel() {
		if((sigRappel!=null) && !sigRappel.isEmpty()) {
			return CioneUtils.decimalToView(sigRappel.toString()) + "%";
		}else {
			return "";
		}
	}
	public void setSigRappel(String sigRappel) {
		this.sigRappel = sigRappel;
	}
	public String getIdAcuerdo() {
		return idAcuerdo;
	}
	public void setIdAcuerdo(String idAcuerdo) {
		this.idAcuerdo = idAcuerdo;
	}
	
	public boolean isShowDetail() {
		return showDetail;
	}
	public void setShowDetail(boolean showDetail) {
		this.showDetail = showDetail;
	}
	
}
