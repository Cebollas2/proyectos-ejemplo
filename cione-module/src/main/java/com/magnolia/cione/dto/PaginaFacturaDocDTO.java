package com.magnolia.cione.dto;

import java.util.List;

public class PaginaFacturaDocDTO {
	
	private Integer pagina;
	private Integer ultimaPagina;
	private Integer numRegistros;
	private List<FacturaDocDTO> documentos;
	
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
	
	public List<FacturaDocDTO> getDocumentos() {
		return documentos;
	}
	public void setDocumentos(List<FacturaDocDTO> documentos) {
		this.documentos = documentos;
	}
	
}
