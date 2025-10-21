package com.magnolia.cione.dao;

import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.AuditarDocumentoDTO;
import com.magnolia.cione.dto.AuditarDocumentosQueryParamsDTO;
import com.magnolia.cione.dto.PaginaAuditarDocumentosDTO;

/*
create table tbl_auditar_documentos (
	id int NOT NULL AUTO_INCREMENT,
	id_socio varchar(10),
    uuid_documento varchar(36),
    nombre_documento varchar(100),
    ruta_documento varchar(200),
    descargas int,
    fecha_descarga datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY(id_socio,uuid_documento) 
);
 */

@ImplementedBy(AuditarDocumentosDaoImpl.class)
public interface AuditarDocumentosDao {
	
	public void registrarDescargaDocumento(String codSocio, String documentUuid, String documentName, String documentPath) throws NamingException;
	
	public AuditarDocumentoDTO getSingleAudit(String codSocio, String documentUuid) throws NamingException;
	
	public PaginaAuditarDocumentosDTO getAuditorias(AuditarDocumentosQueryParamsDTO auditarDocumentosQueryParamsDTO) throws NamingException;

}
