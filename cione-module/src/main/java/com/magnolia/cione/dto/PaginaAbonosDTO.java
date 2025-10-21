package com.magnolia.cione.dto;

import java.util.List;

public class PaginaAbonosDTO {
	
	private Integer pagina;
	private Integer ultimaPagina;
	private Integer numRegistros;
	private List<AbonoDTO> abonos;
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
	public List<AbonoDTO> getAbonos() {
		return abonos;
	}
	public void setAbonos(List<AbonoDTO> abonos) {
		this.abonos = abonos;
	}
	
}
