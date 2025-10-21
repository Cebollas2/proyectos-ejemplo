package com.magnolia.cione.dto;

import java.util.List;

public class PaginaAuditarDocumentosDTO {

	private Integer numRegistros;
	private List<AuditarDocumentoDTO> auditorias;
	
	public Integer getNumRegistros() {
		return numRegistros;
	}
	public void setNumRegistros(Integer numRegistros) {
		this.numRegistros = numRegistros;
	}
	public List<AuditarDocumentoDTO> getAuditorias() {
		return auditorias;
	}
	public void setAuditorias(List<AuditarDocumentoDTO> auditorias) {
		this.auditorias = auditorias;
	}
	
	
}
