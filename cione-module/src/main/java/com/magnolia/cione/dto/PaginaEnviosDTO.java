package com.magnolia.cione.dto;

import java.util.List;

public class PaginaEnviosDTO {

	private Integer pagina;
	private Integer ultimaPagina;
	private Integer numRegistros;
	private List<EnvioDTO> envios;

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

	public List<EnvioDTO> getEnvios() {
		return envios;
	}

	public void setEnvios(List<EnvioDTO> envios) {
		this.envios = envios;
	}


}
