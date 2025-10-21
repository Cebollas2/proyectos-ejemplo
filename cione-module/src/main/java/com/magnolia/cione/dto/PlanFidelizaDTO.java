package com.magnolia.cione.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.magnolia.cione.utils.CioneUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanFidelizaDTO {
	
	private String tipo;
	private String consumoActualGrupo;
	private String consumoActual;
	private String descuentoActual;
	private String cionesAcumuladosGrupo;
	private String cionesAcumulados;
	private String consumoSigCategoria;
	private String sigDescuento;
	private String consumoMantener;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getConsumoActualGrupo() {
		return CioneUtils.decimalToView(consumoActualGrupo);
	}
	
	public String getConsumoActualGrupoOrigin() {
		return consumoActualGrupo;
	}

	public void setConsumoActualGrupo(String consumoActualGrupo) {
		this.consumoActualGrupo = consumoActualGrupo;
	}

	public String getConsumoActual() {
		return CioneUtils.decimalToView(consumoActual);
	}
	
	public String getConsumoActualOrigin() {
		return consumoActual;
	}

	public void setConsumoActual(String consumoActual) {
		this.consumoActual = consumoActual;
	}

	public String getDescuentoActual() {
		return CioneUtils.decimalToView(descuentoActual);
	}

	public void setDescuentoActual(String descuentoActual) {
		this.descuentoActual = descuentoActual;
	}
	
	public String getCionesAcumuladosGrupo() {
		return cionesAcumuladosGrupo;
	}
	
	public String getCionesAcumuladosGrupoView() {
		return CioneUtils.decimalToView(cionesAcumuladosGrupo);
	}

	public void setCionesAcumuladosGrupo(String cionesAcumuladosGrupo) {
		this.cionesAcumuladosGrupo = cionesAcumuladosGrupo;
	}

	public String getCionesAcumulados() {
		return cionesAcumulados;
	}
	
	public String getCionesAcumuladosView() {
		return CioneUtils.decimalToView(cionesAcumulados);
	}

	public void setCionesAcumulados(String cionesAcumulados) {
		this.cionesAcumulados = cionesAcumulados;
	}

	public String getConsumoSigCategoria() {
		return CioneUtils.decimalToView(consumoSigCategoria);
	}
	
	public String getConsumoSigCategoriaOrigin() {
		return consumoSigCategoria;
	}

	public void setConsumoSigCategoria(String consumoSigCategoria) {
		this.consumoSigCategoria = consumoSigCategoria;
	}

	public String getSigDescuento() {
		return CioneUtils.decimalToView(sigDescuento);
	}

	public void setSigDescuento(String sigDescuento) {
		this.sigDescuento = sigDescuento;
	}
	
	public String getConsumoMantener() {
		return consumoMantener;
	}

	public void setConsumoMantener(String consumoMantener) {
		this.consumoMantener = consumoMantener;
	}
	
	@Override
	public String toString() {
		return "PlanFidelizaDTO [tipo=" + tipo + ", consumoActual=" + consumoActual + ", descuentoActual="
				+ descuentoActual + ", cionesAcumuladosGrupo=" + cionesAcumuladosGrupo + ", cionesAcumulados=" + cionesAcumulados
				+ ", consumoSigCategoria=" + consumoSigCategoria + ", sigDescuento="
				+ sigDescuento + ", consumoMantener=" + consumoMantener + "]";
	}
	

}
