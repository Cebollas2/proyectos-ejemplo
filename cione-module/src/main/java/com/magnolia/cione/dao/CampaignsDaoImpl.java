package com.magnolia.cione.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.CampaignDTO;
import com.magnolia.cione.dto.CampaignsQueryParamsDTO;
import com.magnolia.cione.dto.PaginaCampaignsDTO;
import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.service.MiddlewareService;

public class CampaignsDaoImpl implements CampaignsDao {
	
	private static final Logger log = LoggerFactory.getLogger(AuditarDocumentosDaoImpl.class);
	
	@Inject
	private MiddlewareService middlewareService;

	@Override
	public void registrarCampaign(CampaignsQueryParamsDTO campaignsQueryParamsDTO)
			throws NamingException {
		Context initContext = new InitialContext();	    
		DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
		

		try (Connection con = ds.getConnection();
				PreparedStatement ps = createInsertPreparedStatement(con, campaignsQueryParamsDTO);
				) {
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			log.error("Error al registrar la campaña en la BBDD", e);
		}
		
	}

	private PreparedStatement createInsertPreparedStatement(Connection con, CampaignsQueryParamsDTO campaignsQueryParamsDTO) throws SQLException {
		String sql = "INSERT INTO tbl_campaigns "
				+ "(nombre, id_socio, nombre_socio, direccion_socio, poblacion, provincia, codigo_postal, opcion) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?);"; 								
		
		String nombreUsuario = "";
		String direccion = "";
		String poblacion = "";
		String provincia = "";
		String codigoPostal = "";
		try {
			UserERPCioneDTO user = middlewareService.getUserFromERP(campaignsQueryParamsDTO.getCodSocio());
			nombreUsuario = user.getNombreComercial();
			direccion = user.getDireccion();
			poblacion = user.getPoblacion();
			provincia = user.getProvincia();
			codigoPostal = user.getCodigoPostal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1, campaignsQueryParamsDTO.getCampaignName());
		ps.setString(2, campaignsQueryParamsDTO.getCodSocio());
		ps.setString(3, nombreUsuario);
		ps.setString(4, direccion);
		ps.setString(5, poblacion);
		ps.setString(6, provincia);
		ps.setString(7, codigoPostal);
		ps.setString(8, campaignsQueryParamsDTO.getOpcion());

		return ps;
	}

	@Override
	public PaginaCampaignsDTO getCampaigns(CampaignsQueryParamsDTO campaignsQueryParamsDTO) throws NamingException {
		Context initContext = new InitialContext();	    
		DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
		
		PaginaCampaignsDTO paginaCampaignsDTO = new PaginaCampaignsDTO();
		List<CampaignDTO> campaignDTOs = new ArrayList<>();
		
		try (Connection con = ds.getConnection();
				PreparedStatement ps = createSelectPreparedStatement(con, campaignsQueryParamsDTO);
				ResultSet rs = ps.executeQuery()) {
			
			while (rs.next()) {	
				CampaignDTO campaignDTO = new CampaignDTO();
				
				campaignDTO.setNombre(rs.getString("nombre"));
				campaignDTO.setIdSocio(rs.getString("id_socio"));
				campaignDTO.setOpcion(rs.getString("opcion"));
				campaignDTO.setNombreSocio(rs.getString("nombre_socio"));
				campaignDTO.setDireccionSocio(rs.getString("direccion_socio"));
				campaignDTO.setPoblacion(rs.getString("poblacion"));
				campaignDTO.setProvincia(rs.getString("provincia"));
				campaignDTO.setCodigoPostal(rs.getString("codigo_postal"));
				campaignDTO.setFecha(new Date(rs.getTimestamp("fecha").getTime()));
				
				//Temporal para registros sin poblacion, provincia y codigo postal
				if (campaignDTO.getPoblacion() == null && campaignDTO.getPoblacion() == null && campaignDTO.getPoblacion() == null) {
					String poblacion = "";
					String provincia = "";
					String codigoPostal = "";
					try {
						UserERPCioneDTO user = middlewareService.getUserFromERP(campaignDTO.getIdSocio());
						poblacion = user.getPoblacion();
						provincia = user.getProvincia();
						codigoPostal = user.getCodigoPostal();
						campaignDTO.setPoblacion(poblacion);
						campaignDTO.setProvincia(provincia);
						campaignDTO.setCodigoPostal(codigoPostal);
						updateCampaignRow(campaignDTO);
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}
				
				
				campaignDTOs.add(campaignDTO);
			}
			
		} catch (SQLException e) {
			log.error("Error al obtener la campaña de la BBDD", e);
		}
		paginaCampaignsDTO.setCampaigns(campaignDTOs);
		paginaCampaignsDTO.setNumRegistros(campaignDTOs.size());
		return paginaCampaignsDTO;
	}

	private void updateCampaignRow(CampaignDTO campaignDTO) throws NamingException {
		Context initContext = new InitialContext();	    
		DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
		

		try (Connection con = ds.getConnection();
				PreparedStatement ps = createUpdatePreparedStatement(con, campaignDTO);
				) {
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			log.error("Error al actualizar la campaña en la BBDD", e);
		}
		
	}

	private PreparedStatement createUpdatePreparedStatement(Connection con, CampaignDTO campaignDTO) throws SQLException {
		String sql = "UPDATE tbl_campaigns "
						+ " SET "
							+ "poblacion = ?, "
							+ "provincia = ?, "
							+ "codigo_postal = ? "
						+ "WHERE "
							+ "id_socio LIKE ? "
							+ "AND nombre LIKE ?;"; 								
		
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1, campaignDTO.getPoblacion());
		ps.setString(2, campaignDTO.getProvincia());
		ps.setString(3, campaignDTO.getCodigoPostal());
		ps.setString(4, campaignDTO.getIdSocio());
		ps.setString(5, campaignDTO.getNombre());

		return ps;
	}

	private PreparedStatement createSelectPreparedStatement(Connection con, CampaignsQueryParamsDTO campaignsQueryParamsDTO) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM tbl_campaigns");
		int filtros = 0;
		if (campaignsQueryParamsDTO.getCampaignName() != null && !campaignsQueryParamsDTO.getCampaignName().isEmpty()) {
			sql.append(" WHERE nombre like ?");
			filtros++;
		}
		if (campaignsQueryParamsDTO.getCodSocio() != null && !campaignsQueryParamsDTO.getCodSocio().isEmpty()) {
			sql.append(filtros > 0 ? " AND ": " WHERE ");
			sql.append("id_socio like ?");
			filtros++;
		}
		sql.append(";");
		
		PreparedStatement ps = con.prepareStatement(sql.toString());
		
		int pos = 1;
		if (campaignsQueryParamsDTO.getCampaignName() != null && !campaignsQueryParamsDTO.getCampaignName().isEmpty()) {
			ps.setString(pos, "%" + campaignsQueryParamsDTO.getCampaignName() + "%");
			pos++;
		}
		if (campaignsQueryParamsDTO.getCodSocio() != null && !campaignsQueryParamsDTO.getCodSocio().isEmpty()) {
			ps.setString(pos, campaignsQueryParamsDTO.getCodSocio());
			pos++;
		}

		return ps;
	}

	@Override
	public PaginaCampaignsDTO getSingleCampaign(CampaignsQueryParamsDTO campaignsQueryParamsDTO)
			throws NamingException {
		Context initContext = new InitialContext();	    
		DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
		
		PaginaCampaignsDTO paginaCampaignsDTO = new PaginaCampaignsDTO();
		List<CampaignDTO> campaignDTOs = new ArrayList<>();
		
		try (Connection con = ds.getConnection();
				PreparedStatement ps = createSelectSingleQueryPreparedStatement(con, campaignsQueryParamsDTO);
				ResultSet rs = ps.executeQuery()) {
			
			while (rs.next()) {	
				CampaignDTO campaignDTO = new CampaignDTO();
				
				campaignDTO.setNombre(rs.getString("nombre"));
				campaignDTO.setIdSocio(rs.getString("id_socio"));
				campaignDTO.setOpcion(rs.getString("opcion"));
				campaignDTO.setNombreSocio(rs.getString("nombre_socio"));
				campaignDTO.setDireccionSocio(rs.getString("direccion_socio"));
				campaignDTO.setPoblacion(rs.getString("poblacion"));
				campaignDTO.setProvincia(rs.getString("provincia"));
				campaignDTO.setCodigoPostal(rs.getString("codigo_postal"));
				campaignDTO.setFecha(new Date(rs.getTimestamp("fecha").getTime()));
				
				
				campaignDTOs.add(campaignDTO);
			}
			
		} catch (SQLException e) {
			log.error("Error al obtener la campaña de la BBDD", e);
		}
		paginaCampaignsDTO.setCampaigns(campaignDTOs);
		paginaCampaignsDTO.setNumRegistros(campaignDTOs.size());
		return paginaCampaignsDTO;
	}
	
	private PreparedStatement createSelectSingleQueryPreparedStatement(Connection con, CampaignsQueryParamsDTO campaignsQueryParamsDTO) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM tbl_campaigns");
		int filtros = 0;
		if (campaignsQueryParamsDTO.getCampaignName() != null && !campaignsQueryParamsDTO.getCampaignName().isEmpty()) {
			sql.append(" WHERE nombre like ?");
			filtros++;
		}
		if (campaignsQueryParamsDTO.getCodSocio() != null && !campaignsQueryParamsDTO.getCodSocio().isEmpty()) {
			sql.append(filtros > 0 ? " AND ": " WHERE ");
			sql.append("id_socio like ?");
			filtros++;
		}
		sql.append(";");
		
		PreparedStatement ps = con.prepareStatement(sql.toString());
		
		int pos = 1;
		if (campaignsQueryParamsDTO.getCampaignName() != null && !campaignsQueryParamsDTO.getCampaignName().isEmpty()) {
			ps.setString(pos, campaignsQueryParamsDTO.getCampaignName());
			pos++;
		}
		if (campaignsQueryParamsDTO.getCodSocio() != null && !campaignsQueryParamsDTO.getCodSocio().isEmpty()) {
			ps.setString(pos, campaignsQueryParamsDTO.getCodSocio());
			pos++;
		}

		return ps;
	}

}
