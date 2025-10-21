package com.magnolia.cione.dto;

import java.util.List;

public class PaginaTalleresDTO {

	private Integer pagina;
	private Integer ultimaPagina;
	private Integer numRegistros;
	private List<TallerDTO> serviciosTaller;

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

	public List<TallerDTO> getServiciosTaller() {
		return serviciosTaller;
	}

	public void setServiciosTaller(List<TallerDTO> serviciosTaller) {
		this.serviciosTaller = serviciosTaller;
	}

}
