package com.magnolia.cione.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginaDevolucionesDTO {
	private Integer pagina;
	private Integer ultimaPagina;
	private Integer numRegistros;
	private List<DevolucionDTO> devoluciones;
	private List<MotivoDTO> motivos;
	
	public Integer getPagina() {
		return pagina;
	}
	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}
	public Integer getUltimaPagina() {
		return ultimaPagina;
	}
	public void setUltimaPagina(Integer ultimaPagina) {
		this.ultimaPagina = ultimaPagina;
	}
	public Integer getNumRegistros() {
		return numRegistros;
	}
	public void setNumRegistros(Integer numRegistros) {
		this.numRegistros = numRegistros;
	}
	public List<DevolucionDTO> getDevoluciones() {
		return devoluciones;
	}
	public void setDevoluciones(List<DevolucionDTO> devoluciones) {
		this.devoluciones = devoluciones;
	}
	public List<MotivoDTO> getMotivos() {
		return motivos;
	}
	public void setMotivos(List<MotivoDTO> motivos) {
		this.motivos = motivos;
	}
}
