package com.magnolia.cione.dto;

import java.util.List;

public class PaginaInteraccionesDTO {
	
	private Integer pagina;
	private Integer ultimaPagina;
	private Integer numRegistros;
	private List<InteraccionDTO> interacciones;
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
	public List<InteraccionDTO> getInteracciones() {
		return interacciones;
	}
	public void setInteracciones(List<InteraccionDTO> interacciones) {
		this.interacciones = interacciones;
	}
	
	
}
