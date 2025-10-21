package com.magnolia.cione.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.dto.AhorroDTO;
import com.magnolia.cione.dto.MisAhorrosDTO;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.cms.security.SecuritySupport;
import info.magnolia.cms.security.User;

public class MisAhorrosDaoImpl implements MisAhorrosDao {

	private static final Logger log = LoggerFactory.getLogger(MisAhorrosDaoImpl.class);
	private static final int CONCEPTO_CUOTAS = 5;
	
	@Inject
	private SecuritySupport securitySupport;

	// Monturas y gafas de sol
	public MisAhorrosDTO getMisAhorros(String codSocio, Date fechaDesde, Date fechaHasta) throws NamingException {
		User currentUser = securitySupport.getUserManager("public").getUser(CioneUtils.getIdCurrentClient());
		Context initContext = new InitialContext();	    
		DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
		MisAhorrosDTO result = new MisAhorrosDTO();
		try (Connection con = ds.getConnection();
				PreparedStatement ps = createPreparedStatementMisAhorros(con, codSocio, fechaDesde, fechaHasta);
				ResultSet rs = ps.executeQuery()) {
			List<AhorroDTO> ahorros = new ArrayList<>();
			Double importeTotal = 0.0;
			Date minDate = null;
			while (rs.next()) {								
				AhorroDTO ahorro = new AhorroDTO();
				//ahorro.setId(rs.getInt("id"));
				ahorro.setConcepto(rs.getString("descripcion"));								
				/*Date fecha = rs.getDate("date_ahorro");
				if(minDate != null) {
					if(fecha.before(minDate)) {
						minDate = fecha;
					}					
				}else {
					minDate = fecha;
				}
				if(maxDate != null) {
					if(fecha.after(maxDate)) {
						maxDate = fecha;
					}
				}else {
					maxDate = fecha;
				}*/
				Double importe = rs.getDouble("total");
				importeTotal += importe;
				ahorro.setImporte(importe);
				
				if (currentUser!= null && !currentUser.hasRole(CioneConstants.ROLE_FIDELIZACION)) {
					ahorros.add(ahorro);
				} else {
					if (!ahorro.getConcepto().equals("Fidelización") && !ahorro.getConcepto().equals("* Fidelización"))
						ahorros.add(ahorro);
				}
				
	        }

			if(fechaDesde != null) {
				result.setMinDate(DateFormatUtils.format(fechaDesde, CioneConstants.DATE_FORMAT_MM_YYYY));
			}else {
				result.setMinDate(DateFormatUtils.format(getFechaMinima(codSocio, fechaDesde, fechaHasta), CioneConstants.DATE_FORMAT_MM_YYYY));
			}
			if(fechaHasta != null) {
				result.setMaxDate(DateFormatUtils.format(fechaHasta, CioneConstants.DATE_FORMAT_MM_YYYY));
			}else {
				result.setMaxDate(DateFormatUtils.format(new Date(), CioneConstants.DATE_FORMAT_MM_YYYY));
			}
			
			result.setAhorros(ahorros);
			result.setImporteTotal(importeTotal);
			
		} catch (SQLException e) {
			log.error("Error al obtener los consumos en la BBDD", e);
		}
		return result;
	}
	
	private Date getFechaMinima(String codSocio, Date fechaDesde, Date fechaHasta) {
		Date fechaIni = null;
		Context initContext;
		try {
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
			try (Connection con = ds.getConnection();
					PreparedStatement ps = createPreparedStatementDateMin(con, codSocio, fechaDesde, fechaHasta);
					ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					fechaIni = rs.getDate("date_min");
					
				}
				
			} catch (SQLException e) {
				log.error("Error al obtener la fecha Minima de los consumos en la BBDD", e);
			}
		} catch (NamingException e) {
			log.error(e.getMessage(), e);
		}	    
		
		
		return fechaIni;
	}
	
	private PreparedStatement createPreparedStatementDateMin(Connection con, String codSocio, Date fechaDesde, Date fechaHasta)
			throws SQLException {		
		String sql = "SELECT min(a.date_ahorro) as date_min"
				+ "   FROM mycione.tbl_mis_ahorros AS a"
				+ "    WHERE id_socio = ? ";
		if (fechaDesde != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaDesde);
			String anio = String.valueOf(calendar.get(Calendar.YEAR));
			String mes = String.valueOf(calendar.get(Calendar.MONTH) +1);
			sql += " and a.ciclo >= " + anio + "";
			sql += " and a.mes >= " + mes + "";
		}
		if (fechaHasta != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaHasta);
			String anio = String.valueOf(calendar.get(Calendar.YEAR));
			String mes = String.valueOf(calendar.get(Calendar.MONTH) +1);
			sql += " and a.ciclo <= " + anio + "";
			sql += " and a.mes <= " + mes + "";
		}
		sql +=	" ORDER BY"
			+ "      a.concepto";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, codSocio);
		log.debug("CONSULTA AHORROS " + sql);
		return ps;
	}

	/*private PreparedStatement createPreparedStatement(Connection con, String codSocio, Date fechaDesde, Date fechaHasta)
			throws SQLException {
		
		String sql = "select * from (select * from tbl_mis_ahorros a"
				+ " left join tbl_conceptos c on a.concepto = c.id_concepto"
				+ " where id_socio=?"; 								
		if (fechaDesde != null) {
			sql += " and a.date_ahorro >= ?";
		}
		if (fechaHasta != null) {
			sql += " and a.date_ahorro <= ?";
		}
		sql += " order by a.date_ahorro desc) as subtable group by id_concepto";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, codSocio);
		
		
		if (fechaDesde != null) {
			ps.setDate(2, new java.sql.Date(fechaDesde.getTime()));
		}

		if (fechaHasta != null) {
			int pos = fechaDesde == null ? 2 : 3;
			ps.setDate(pos, new java.sql.Date(fechaHasta.getTime()));
		}

		return ps;
	}*/
	
	private PreparedStatement createPreparedStatementMisAhorros(Connection con, String codSocio, Date fechaDesde, Date fechaHasta)
			throws SQLException {		
		String sql = "SELECT"
				+ "      a.id_socio,"
				+ "      c.descripcion,"
				+ "      ROUND(SUM(a.importe),2) total"
				+ "    FROM"
				+ "      mycione.tbl_mis_ahorros AS a"
				+ "      INNER JOIN mycione.tbl_conceptos AS c ON c.id_concepto = a.concepto"
				+ "    WHERE"
				+ "      id_socio = ? ";
		if (fechaDesde != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaDesde);
			String anio = String.valueOf(calendar.get(Calendar.YEAR));
			String mes = String.valueOf(calendar.get(Calendar.MONTH) +1);
			sql += " and a.ciclo >= " + anio + "";
			sql += " and a.mes >= " + mes + "";
		}
		if (fechaHasta != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaHasta);
			String anio = String.valueOf(calendar.get(Calendar.YEAR));
			String mes = String.valueOf(calendar.get(Calendar.MONTH) +1);
			sql += " and a.ciclo <= " + anio + "";
			sql += " and a.mes <= " + mes + "";
		}
		sql +=	" GROUP BY"
				+ "      a.id_socio,"
				+ "      a.concepto,"
				+ "      c.descripcion"
				+ "    ORDER BY"
				+ "      a.concepto";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, codSocio);
		log.debug("CONSULTA AHORROS " + sql);
		return ps;
	}
	
	/*private PreparedStatement createPreparedStatementMisAhorrosAllFields(Connection con, String codSocio, Date fechaDesde, Date fechaHasta)
			throws SQLException {		
		String sql = "SELECT"
				//+ "      a.id,"
				+ "      a.date_ahorro,"
				+ "      a.id_socio,"
				+ "      a.ciclo AS year,"
				+ "		 a.mes,"
				+ "      a.concepto,"
				+ "      c.descripcion,"
				+ "      ROUND(SUM(a.importe),2) total"
				+ "    FROM"
				+ "      mycione.tbl_mis_ahorros AS a"
				+ "      INNER JOIN mycione.tbl_conceptos AS c ON c.id_concepto = a.concepto"
				+ "    WHERE"
				+ "      id_socio = ? ";
		if (fechaDesde != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaDesde);
			String anio = String.valueOf(calendar.get(Calendar.YEAR));
			String mes = String.valueOf(calendar.get(Calendar.MONTH) +1);
			sql += " and a.ciclo >= " + anio + "";
			sql += " and a.mes >= " + mes + "";
		}
		if (fechaHasta != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaHasta);
			String anio = String.valueOf(calendar.get(Calendar.YEAR));
			String mes = String.valueOf(calendar.get(Calendar.MONTH) +1);
			sql += " and a.ciclo <= " + anio + "";
			sql += " and a.mes <= " + mes + "";
		}
		sql +=	" GROUP BY"
				+ "      a.id_socio,"
				+ "      a.ciclo,"
				+ "      a.mes,"
				+ "      a.concepto,"
				+ "      a.date_ahorro,"
				+ "      c.descripcion"
				+ "    ORDER BY"
				+ "      a.concepto,"
				+ "      a.ciclo,"
				+ "      a.mes";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, codSocio);
		log.debug("CONSULTA AHORROS " + sql);
		return ps;
	}*/

	@Override
	public Double getAhorroEnCuotas(String codSocio) throws NamingException {
		Context initContext = new InitialContext();	    
		DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
		Double result = null;
		try (Connection con = ds.getConnection();
				PreparedStatement ps = createPreparedStatementAhorroCuotas(con, codSocio);
				ResultSet rs = ps.executeQuery()) {			
			while (rs.next()) {
				result = rs.getDouble("importe");				
	        }			
		} catch (SQLException e) {
			log.error("Error al obtener los consumos en la BBDD", e);
		}
		return result;
	}
	
	public MisAhorrosDTO getMisAhorrosDashBoard(String codSocio) {
		MisAhorrosDTO misAhorros = new MisAhorrosDTO();
		List<AhorroDTO> ahorros = new ArrayList<>();
		Context initContext;
		try {
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
			
			try (Connection con = ds.getConnection();
				PreparedStatement ps = createPreparedStatementMisAhorrosDashBoard(con, codSocio);
				ResultSet rs = ps.executeQuery()) {
				
				while (rs.next()) {
					AhorroDTO ahorro = new AhorroDTO();
					ahorro.setConcepto(rs.getString("descripcion"));
					ahorro.setImporte(rs.getDouble("total"));
					ahorros.add(ahorro);
				}
				
			} catch (SQLException e) {
				log.error("Error al obtener los consumos en la BBDD", e);
			}
		} catch (NamingException e1) {
			log.error(e1.getMessage(), e1);
		} 
		
		double total  = 0.0;
		for (AhorroDTO ahorro: ahorros) {
			total += ahorro.getImporte();
		}
		
		AhorroDTO ahorro = new AhorroDTO();
		ahorro.setConcepto("TOTAL");
		ahorro.setImporte(total);
		ahorros.add(ahorro);
		
		
		misAhorros.setAhorros(ahorros);
		return misAhorros;
	}
	
	
	private PreparedStatement createPreparedStatementAhorroCuotas(Connection con, String codSocio)
			throws SQLException {		
		String sql = "select * from tbl_mis_ahorros where id_socio = ? and concepto = ? order by date_ahorro desc limit 1";		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, codSocio);
		ps.setInt(2, CONCEPTO_CUOTAS);
		
		return ps;
	}
	
	private PreparedStatement createPreparedStatementMisAhorrosDashBoard(Connection con, String codSocio)
			throws SQLException {		
		String sql = "SELECT"
				+ "      a.id_socio,"
				+ "      a.ciclo AS year,"
				+ "      a.concepto,"
				+ "      c.descripcion,"
				+ "      ROUND(SUM(a.importe),2) total"
				+ "    FROM"
				+ "      mycione.tbl_mis_ahorros AS a"
				+ "      INNER JOIN mycione.tbl_conceptos AS c ON c.id_concepto = a.concepto"
				+ "    WHERE"
				+ "      a.ciclo = YEAR(CURDATE())"
				+ "      AND id_socio = ? "
				+ "    GROUP BY"
				+ "      a.id_socio,"
				+ "      a.ciclo,"
				+ "      a.concepto,"
				+ "      c.descripcion"
				+ "    ORDER BY"
				+ "      a.concepto";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, codSocio);
		
		return ps;
	}


}
