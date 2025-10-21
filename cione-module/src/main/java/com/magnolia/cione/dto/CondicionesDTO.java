package com.magnolia.cione.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.magnolia.cione.utils.CioneUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CondicionesDTO {
	private List<CondicionDTO> condiciones = new ArrayList<>();
	private Double cionesAcumuladosTotal;
	private Double cionesAcumuladosGrupoTotal;

	public List<CondicionDTO> getCondiciones() {
		return condiciones;
	}

	public void setCondiciones(List<CondicionDTO> condiciones) {
		this.condiciones = condiciones;
	}


	public Double getCionesAcumuladosTotal() {
		return cionesAcumuladosTotal;
	}

	public void setCionesAcumuladosTotal(Double cionesAcumuladosTotal) {
		this.cionesAcumuladosTotal = cionesAcumuladosTotal;
	}
	
	public String getCionesAcumuladosTotalView() {
		if(cionesAcumuladosTotal!=null) {
			return CioneUtils.decimalToView(cionesAcumuladosTotal.toString());
		}else {
			return "";
		}		
	}

	public Double getCionesAcumuladosGrupoTotal() {
		return cionesAcumuladosGrupoTotal;
	}

	public void setCionesAcumuladosGrupoTotal(Double cionesAcumuladosGrupoTotal) {
		this.cionesAcumuladosGrupoTotal = cionesAcumuladosGrupoTotal;
	}
	
	public String getCionesAcumuladosGrupoTotalView() {
		if(cionesAcumuladosGrupoTotal!=null) {
			return CioneUtils.decimalToView(cionesAcumuladosGrupoTotal.toString());
		}else {
			return "";
		}		
	}

}
