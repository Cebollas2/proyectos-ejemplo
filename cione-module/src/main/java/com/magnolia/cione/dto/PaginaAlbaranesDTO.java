package com.magnolia.cione.dto;

import java.util.List;

public class PaginaAlbaranesDTO {
	
	private Integer pagina;
	private Integer ultimaPagina;
	private Integer numRegistros;
	private List<AlbaranDTO> albaranes;
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
	public List<AlbaranDTO> getAlbaranes() {
		return albaranes;
	}
	public void setAlbaranes(List<AlbaranDTO> albaranes) {
		this.albaranes = albaranes;
	}
	
}
