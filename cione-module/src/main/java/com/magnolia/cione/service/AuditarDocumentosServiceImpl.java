package com.magnolia.cione.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dao.AuditarDocumentosDao;
import com.magnolia.cione.dto.AuditarDocumentoDTO;
import com.magnolia.cione.dto.AuditarDocumentosQueryParamsDTO;
import com.magnolia.cione.dto.PaginaAuditarDocumentosDTO;
import com.magnolia.cione.exceptions.CioneRuntimeException;

import info.magnolia.dam.api.Asset;
import info.magnolia.dam.templating.functions.DamTemplatingFunctions;

public class AuditarDocumentosServiceImpl implements AuditarDocumentosService {

	private static final Logger log = LoggerFactory.getLogger(AuditarDocumentosServiceImpl.class);
	
	@Inject
	private AuditarDocumentosDao dao;
	
	@Inject
	private DamTemplatingFunctions damfn;

	@Override
	public void registrarDescargaDocumento(String codSocio, AuditarDocumentosQueryParamsDTO auditarDocumentosQueryParamsDTO) {
		try {
			Asset asset = damfn.getAsset("jcr:" + auditarDocumentosQueryParamsDTO.getUuidDocumento());
			if (asset != null) {
				dao.registrarDescargaDocumento(codSocio, asset.getItemKey().getAssetId(), asset.getName(), asset.getPath());
			} else {
				log.error("Asset con uuid " + auditarDocumentosQueryParamsDTO.getUuidDocumento() + " no encontrado");
			}
		} catch (NamingException e) {
			log.error(e.getMessage(),e);
			throw new CioneRuntimeException("Error al acceder al datasource");
		}
		
	}

	@Override
	public PaginaAuditarDocumentosDTO getAuditorias(AuditarDocumentosQueryParamsDTO auditarDocumentosQueryParamsDTO) {
		PaginaAuditarDocumentosDTO auditorias = new PaginaAuditarDocumentosDTO();
		try {
			auditorias = dao.getAuditorias(auditarDocumentosQueryParamsDTO);
		} catch (NamingException e) {
			log.error(e.getMessage(),e);
			throw new CioneRuntimeException("Error al acceder al datasource");
		}
		return auditorias;
	}
}
