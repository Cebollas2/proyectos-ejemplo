package com.magnolia.cione.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.AuditarDocumentoDTO;
import com.magnolia.cione.dto.AuditarDocumentosQueryParamsDTO;
import com.magnolia.cione.dto.PaginaAuditarDocumentosDTO;

import info.magnolia.dam.templating.functions.DamTemplatingFunctions;


public class AuditarDocumentosDaoImpl implements AuditarDocumentosDao {
	
	private static final Logger log = LoggerFactory.getLogger(AuditarDocumentosDaoImpl.class);
	
	@Inject
	private DamTemplatingFunctions damfn;

	@Override
	public void registrarDescargaDocumento(String codSocio, String documentUuid, String documentName, String documentPath) throws NamingException {
		Context initContext = new InitialContext();	    
		DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
		
		AuditarDocumentoDTO auditarDocumentoDTO = getSingleAudit(codSocio, documentUuid);
		
		if (auditarDocumentoDTO != null && auditarDocumentoDTO.getIdSocio() != null && auditarDocumentoDTO.getUuidDocumento() != null) {
			try (Connection con = ds.getConnection();
					PreparedStatement ps = createUpdatePreparedStatement(con, codSocio, documentName, documentUuid);
					) {
				
				ps.executeUpdate();
				
			} catch (SQLException e) {
				log.error("Error al actualizare la descarga de documento en la BBDD", e);
			}
		} else {
			try (Connection con = ds.getConnection();
					PreparedStatement ps = createInsertPreparedStatement(con, codSocio, documentName, documentUuid, documentPath);
					) {
				
				ps.executeUpdate();
				
			} catch (SQLException e) {
				log.error("Error al registrar la descarga de documento en la BBDD", e);
			}
		}
		
	}

	private PreparedStatement createUpdatePreparedStatement(Connection con, String codSocio, String documentName,
			String documentUuid) throws SQLException {
		String sql = "UPDATE tbl_auditar_documentos "
				+ "SET descargas = descargas + 1 "
				+ "WHERE id_socio LIKE ? AND uuid_documento like ?;"; 								
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, codSocio);
		ps.setString(2, documentUuid);

		return ps;
	}

	private PreparedStatement createInsertPreparedStatement(Connection con, String codSocio, String documentName,
			String documentUuid, String documentPath) throws SQLException {
		String sql = "INSERT INTO tbl_auditar_documentos "
				+ "(id_socio, uuid_documento, nombre_documento, ruta_documento, descargas) "
				+ "VALUES (?, ?, ?, ?, ?);"; 								
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, codSocio);
		ps.setString(2, documentUuid);
		ps.setString(3, documentName);
		ps.setString(4, documentPath);
		ps.setInt(5, 1);

		return ps;
	}

	@Override
	public AuditarDocumentoDTO getSingleAudit(String codSocio, String documentUuid) throws NamingException {
		Context initContext = new InitialContext();	    
		DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
		AuditarDocumentoDTO auditarDocumentoDTO = null;
		
		try (Connection con = ds.getConnection();
				PreparedStatement ps = createSelectSinglePreparedStatement(con, codSocio, documentUuid);
				ResultSet rs = ps.executeQuery()) {
			auditarDocumentoDTO = new AuditarDocumentoDTO();
			
			while (rs.next()) {	
				auditarDocumentoDTO.setIdSocio(rs.getString("id_socio"));
				auditarDocumentoDTO.setUuidDocumento(rs.getString("uuid_documento"));
				auditarDocumentoDTO.setNombreDocumento(rs.getString("nombre_documento"));
				auditarDocumentoDTO.setRutaDocumento(rs.getString("ruta_documento"));
				auditarDocumentoDTO.setDescargas(rs.getInt("descargas"));
				auditarDocumentoDTO.setFechaDescarga(rs.getDate("fecha_descarga"));
			}
			
		} catch (SQLException e) {
			log.error("Error al obtener la auditoria de documento en la BBDD", e);
		}
		return auditarDocumentoDTO;
	}

	private PreparedStatement createSelectSinglePreparedStatement(Connection con, String codSocio,
			String documentUuid) throws SQLException {
		String sql = "SELECT * FROM tbl_auditar_documentos WHERE id_socio LIKE ? AND uuid_documento like ?;";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, codSocio);
		ps.setString(2, documentUuid);

		return ps;
	}

	@Override
	public PaginaAuditarDocumentosDTO getAuditorias(AuditarDocumentosQueryParamsDTO auditarDocumentosQueryParamsDTO) throws NamingException {
		Context initContext = new InitialContext();	    
		DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
		
		PaginaAuditarDocumentosDTO paginaAuditarDocumentosDTO = new PaginaAuditarDocumentosDTO();
		List<AuditarDocumentoDTO> auditarDocumentoDTOs = new ArrayList<>();
		
		try (Connection con = ds.getConnection();
				PreparedStatement ps = createSelectPreparedStatement(con, auditarDocumentosQueryParamsDTO);
				ResultSet rs = ps.executeQuery()) {
			
			while (rs.next()) {	
				AuditarDocumentoDTO auditarDocumentoDTO = new AuditarDocumentoDTO();
				
				auditarDocumentoDTO.setIdSocio(rs.getString("id_socio"));
				auditarDocumentoDTO.setUuidDocumento(rs.getString("uuid_documento"));
				auditarDocumentoDTO.setNombreDocumento(rs.getString("nombre_documento"));
				auditarDocumentoDTO.setRutaDocumento(rs.getString("ruta_documento"));
				auditarDocumentoDTO.setDescargas(rs.getInt("descargas"));
				auditarDocumentoDTO.setFechaDescarga(new Date(rs.getTimestamp("fecha_descarga").getTime()));
				
				auditarDocumentoDTOs.add(auditarDocumentoDTO);
			}
			
		} catch (SQLException e) {
			log.error("Error al obtener la auditoria de documento en la BBDD", e);
		}
		paginaAuditarDocumentosDTO.setAuditorias(auditarDocumentoDTOs);
		paginaAuditarDocumentosDTO.setNumRegistros(auditarDocumentoDTOs.size());
		return paginaAuditarDocumentosDTO;
	}

	private PreparedStatement createSelectPreparedStatement(Connection con, AuditarDocumentosQueryParamsDTO auditarDocumentosQueryParamsDTO) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM tbl_auditar_documentos");
		int filtros = 0;
		if (auditarDocumentosQueryParamsDTO.getCodSocio() != null && !auditarDocumentosQueryParamsDTO.getCodSocio().isEmpty()) {
			sql.append(" WHERE id_socio like ?");
			filtros++;
		}
		if (auditarDocumentosQueryParamsDTO.getNombreDocumento() != null && !auditarDocumentosQueryParamsDTO.getNombreDocumento().isEmpty()) {
			sql.append(filtros > 0 ? " AND ": " WHERE ");
			sql.append("nombre_documento like ?");
			filtros++;
		}
		if (auditarDocumentosQueryParamsDTO.getFechaIni() != null) {
			sql.append(filtros > 0 ? " AND ": " WHERE ");
			sql.append("fecha_descarga >= ?");
			filtros++;
		}
		if (auditarDocumentosQueryParamsDTO.getFechaFin() != null) {
			sql.append(filtros > 0 ? " AND ": " WHERE ");
			sql.append("fecha_descarga <= ?");
			filtros++;
		}
		if (auditarDocumentosQueryParamsDTO.getDescargas() > 0) {
			sql.append(filtros > 0 ? " AND ": " WHERE ");
			sql.append("descargas = ?");
			filtros++;
		}
		sql.append(" ORDER BY nombre_documento;");
		
		PreparedStatement ps = con.prepareStatement(sql.toString());

		int pos = 1;
		if (auditarDocumentosQueryParamsDTO.getCodSocio() != null && !auditarDocumentosQueryParamsDTO.getCodSocio().isEmpty()) {
			ps.setString(pos, auditarDocumentosQueryParamsDTO.getCodSocio() + "%");
			pos++;
		}
		if (auditarDocumentosQueryParamsDTO.getNombreDocumento() != null && !auditarDocumentosQueryParamsDTO.getNombreDocumento().isEmpty()) {
			ps.setString(pos, "%" + auditarDocumentosQueryParamsDTO.getNombreDocumento() + "%");
			pos++;
		}
		if (auditarDocumentosQueryParamsDTO.getFechaIni() != null) {
			ps.setDate(pos, new java.sql.Date(auditarDocumentosQueryParamsDTO.getFechaIni().getTime()));
			pos++;
		}
		if (auditarDocumentosQueryParamsDTO.getFechaFin() != null) {
			ps.setDate(pos, new java.sql.Date(auditarDocumentosQueryParamsDTO.getFechaFin().getTime()));
			pos++;
		}
		if (auditarDocumentosQueryParamsDTO.getDescargas() > 0) {
			ps.setInt(pos, auditarDocumentosQueryParamsDTO.getDescargas());
			pos++;
		}

		return ps;
	}
	
	
}
