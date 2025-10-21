package com.magnolia.cione.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.magnolia.cione.utils.CioneUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanesFidelizaDTO {
	private List<PlanFidelizaDTO> tiposPlanFideliza = new ArrayList<>();
	private Double cionesAcumuladosTotal;
	private Double cionesAcumuladosGrupoTotal;

	public List<PlanFidelizaDTO> getTiposPlanFideliza() {
		return tiposPlanFideliza;
	}

	public void setTiposPlanFideliza(List<PlanFidelizaDTO> tiposPlanFideliza) {
		this.tiposPlanFideliza = tiposPlanFideliza;
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
	
	@Override
	public String toString() {
		String toString = "";
		for (PlanFidelizaDTO tipoPlanFideliza: tiposPlanFideliza) {
			toString+=tipoPlanFideliza.toString() + ",";
		}
		
		toString += getCionesAcumuladosTotalView() + getCionesAcumuladosGrupoTotalView();
		
		return toString;
		
	}
	

}
