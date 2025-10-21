package com.magnolia.cione.dao;

import javax.naming.NamingException;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.AuditarDocumentoDTO;
import com.magnolia.cione.dto.AuditarDocumentosQueryParamsDTO;
import com.magnolia.cione.dto.CampaignsQueryParamsDTO;
import com.magnolia.cione.dto.PaginaAuditarDocumentosDTO;
import com.magnolia.cione.dto.PaginaCampaignsDTO;

/*
create table tbl_campaigns (
	id int NOT NULL AUTO_INCREMENT,
    nombre varchar(100),
	id_socio varchar(10),
    nombre_socio varchar(200),
    direccion_socio varchar(200),
    opcion varchar(100),
    fecha datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY ('id')
);
 */
@ImplementedBy(CampaignsDaoImpl.class)
public interface CampaignsDao {

	public void registrarCampaign(CampaignsQueryParamsDTO campaignsQueryParamsDTO) throws NamingException;
	
	public PaginaCampaignsDTO getCampaigns(CampaignsQueryParamsDTO campaignsQueryParamsDTO) throws NamingException;
	
	public PaginaCampaignsDTO getSingleCampaign(CampaignsQueryParamsDTO campaignsQueryParamsDTO) throws NamingException;

}
