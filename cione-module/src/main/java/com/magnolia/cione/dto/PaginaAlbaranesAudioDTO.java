package com.magnolia.cione.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class PaginaAlbaranesAudioDTO {
	
	private Integer pagina;
	private Integer ultimaPagina;
	private Integer numRegistros;
	private List<AlbaranAudioDTO> albaranesAudio;
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
	public List<AlbaranAudioDTO> getAlbaranesAudio() {
		return albaranesAudio;
	}
	public void setAlbaranesAudio(List<AlbaranAudioDTO> albaranesAudio) {
		this.albaranesAudio = albaranesAudio;
	}

	
}
