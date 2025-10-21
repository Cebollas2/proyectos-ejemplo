package com.magnolia.cione.service;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.AuditarDocumentoDTO;
import com.magnolia.cione.dto.AuditarDocumentosQueryParamsDTO;
import com.magnolia.cione.dto.PaginaAuditarDocumentosDTO;

@ImplementedBy(AuditarDocumentosServiceImpl.class)
public interface AuditarDocumentosService {
	
	public void registrarDescargaDocumento(String codSocio, AuditarDocumentosQueryParamsDTO auditarDocumentosQueryParamsDTO);
	
	public PaginaAuditarDocumentosDTO getAuditorias(AuditarDocumentosQueryParamsDTO auditarDocumentosQueryParamsDTO);

}
