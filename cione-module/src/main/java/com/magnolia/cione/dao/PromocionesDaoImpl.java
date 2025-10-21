package com.magnolia.cione.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.PromocionesDTO;
import com.magnolia.cione.dto.VariantDTO;

public class PromocionesDaoImpl implements PromocionesDao{

	private static final Logger log = LoggerFactory.getLogger(PromocionesDaoImpl.class);
	
	/*
	 * A partir de un grupoPrecio, AliasEkon y CodigoCentral devuelve el listado de promociones 
	 * */
	public List<PromocionesDTO> getPromociones (VariantDTO variantResult) throws NamingException {
		
		Context initContext = new InitialContext();	    
		DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
		Date fechaDesde = new Date();
		List<PromocionesDTO> listPromos = new ArrayList<PromocionesDTO>();
		

		try (Connection con = ds.getConnection();
				//PreparedStatement ps = createPreparedStatement(con, variantResult.getGruposPrecio(), parBusqueda, fechaDesde);
				PreparedStatement ps = createPreparedStatement(con, variantResult.getAliasEkon(), variantResult.getCodigoCentral(), variantResult.getGruposPrecio(), fechaDesde);
				ResultSet rs = ps.executeQuery()) {
			//ResultSetHandler<PromocionesDTO> h = new BeanHandler<PromocionesDTO>(PromocionesDTO.class);
			while (rs.next()) {
				PromocionesDTO promocion = new PromocionesDTO();
				promocion.setAliasEkon(rs.getString("aliasEkon"));
				promocion.setGrupoPrecio(rs.getString("codigo_central"));
				if (rs.getString("descripcion") == null)
					promocion.setDescripcion("");
				else
					promocion.setDescripcion(rs.getString("descripcion"));
				promocion.setTipo_descuento(rs.getInt("tipo_descuento"));
				promocion.setCantidad_desde(rs.getInt("cantidad_desde"));
				promocion.setCantidad_hasta(rs.getInt("cantidad_hasta"));
				promocion.setValor(rs.getDouble("valor"));
				promocion.setFecha_desde(rs.getDate("fecha_desde"));
				promocion.setFecha_hasta(rs.getDate("fecha_hasta"));
				listPromos.add(promocion);
	        }
		} catch (SQLException e) {
			log.error("Error al obtener los consumos en la BBDD", e);
		}
		
		return listPromos;
	}
	
	@Override
	public PromocionesDTO getRecargo(VariantDTO variantResult) throws NamingException {
		Context initContext = new InitialContext();	    
		DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
		Date fechaDesde = new Date();
		PromocionesDTO recargo = new PromocionesDTO();
		try (Connection con = ds.getConnection();
				PreparedStatement ps = createPreparedStatement(con, variantResult.getAliasEkon(), variantResult.getCodigoCentral(), variantResult.getGruposPrecio(), fechaDesde);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				recargo.setAliasEkon(rs.getString("aliasEkon"));
				recargo.setGrupoPrecio(rs.getString("codigo_central"));
				if (rs.getString("descripcion") == null)
					recargo.setDescripcion("");
				else
					recargo.setDescripcion(rs.getString("descripcion"));
				recargo.setTipo_descuento(rs.getInt("tipo_descuento"));
				recargo.setCantidad_desde(rs.getInt("cantidad_desde"));
				recargo.setCantidad_hasta(rs.getInt("cantidad_hasta"));
				recargo.setValor(rs.getDouble("valor"));
				recargo.setFecha_desde(rs.getDate("fecha_desde"));
				recargo.setFecha_hasta(rs.getDate("fecha_hasta"));
	        }
		} catch (SQLException e) {
			log.error("Error al obtener los consumos en la BBDD", e);
		}
		
		return recargo;
	} 

	/*
	 * A partir de un grupoPrecio, AliasEkon y codigo_central devuelve el tipo de promocion (sin-promo, por valor o por escalado)
	 * */
	public String getTipoPromocion(String grupoPrecioCommerce, String aliasEkon, String codigo_central) throws NamingException {
		Context initContext = new InitialContext();	    
		DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
		Date fechaDesde = new Date();
		String tipoPromo = "sin-promo";
		try (Connection con = ds.getConnection();
			//PreparedStatement ps = createPreparedStatement(con, grupoPrecioCommerce, aliasEkon, fechaDesde);
				PreparedStatement ps = createPreparedStatement(con, aliasEkon, codigo_central, grupoPrecioCommerce, fechaDesde);
			ResultSet rs = ps.executeQuery()) {
			if (rs!= null) {
				rs.last();
				if (rs.getRow() == 1) {
					tipoPromo = "valor";
				} else if (rs.getRow() > 1) {
					tipoPromo = "escalado";
				}
			}
		} catch (SQLException e) {
			log.error("Error al obtener los consumos en la BBDD", e);
		}
		
		return tipoPromo;
	}
	
	/*
	 * A partir de un grupoPrecio, AliasEkon y codigo central devuelve el porcentaje de descuento
	 * */
	public double getPrecioEscalado(String grupoPrecio, String aliasEkon, String codigo_central, int cantidad) throws NamingException {
		
		double value = 0.0;
				
		Context initContext = new InitialContext();	    
		DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
		Date fechaDesde = new Date();
		try (Connection con = ds.getConnection();
				PreparedStatement ps = createPreparedStatement(con, aliasEkon, codigo_central, grupoPrecio, fechaDesde, cantidad);
				ResultSet rs = ps.executeQuery()) {
			//ResultSetHandler<PromocionesDTO> h = new BeanHandler<PromocionesDTO>(PromocionesDTO.class);
			while (rs.next()) {
				value = rs.getDouble("valor");
	        }
		} catch (SQLException e) {
			log.error("Error al obtener los consumos en la BBDD", e);
		}
		
		return value;
	}
	
	private PreparedStatement createPreparedStatement(Connection con, String aliasEkon, String codigo_central, String grupoPrecio, Date fechaHoy)
			throws SQLException {
		
		//AliasEkon puede ser nulo, sustituir por *
		if ( aliasEkon == null)
			aliasEkon = "*";
		
		String sql = "select * from mycione.tbl_descuentos where aliasEkon = ?"
				+ " and codigo_central = ?"
				+ " and grupo_precio = ?"; 
		if (fechaHoy != null) {
			sql += " and fecha_desde <= ?";
		}
		if (fechaHoy != null) {
			sql += " and fecha_hasta >= ?";
		}
		sql += " order by cantidad_desde";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, aliasEkon);
		ps.setString(2, codigo_central);
		ps.setString(3, grupoPrecio);
		ps.setDate(4, new java.sql.Date(fechaHoy.getTime()));
		ps.setDate(5, new java.sql.Date(fechaHoy.getTime()));

		return ps;
	} 
	
	private PreparedStatement createPreparedStatement(Connection con, String aliasEkon, String codigo_central, String grupoPrecio, Date fechaDesde, int cantidad)
			throws SQLException {
		//AliasEkon puede ser nulo, sustituir por *
		if ( aliasEkon == null)
			aliasEkon = "*";
		
		String sql = "select * from mycione.tbl_descuentos where aliasEkon = ?"
				+ " and codigo_central = ?"
				+ " and grupo_precio = ?"; 								
		if (fechaDesde != null) {
			sql += " and fecha_hasta >= ?";
		}
		sql += " and cantidad_desde <= ?";
		sql += " and cantidad_hasta >= ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, aliasEkon);
		ps.setString(2, codigo_central);
		ps.setString(3, grupoPrecio);
		ps.setDate(4, new java.sql.Date(fechaDesde.getTime()));
		ps.setInt(5, cantidad);
		ps.setInt(6, cantidad);

		return ps;
	}


}
