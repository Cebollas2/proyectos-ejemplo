package com.magnolia.cione.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContactLensServiceImpl implements ContactLensService {
	
	private static final Logger log = LoggerFactory.getLogger(ContactLensServiceImpl.class);

	@Override
	public List<String> getSpheresByCentralCodeAndDesign(String codigocentral, String diseno) {

		List<Integer> spheres = new ArrayList<>();
		Context initContext;
		boolean zeronegative = false;
		
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/cioneLab");
			
			try (Connection con = ds.getConnection();PreparedStatement ps = createPSSpheres(con,codigocentral,diseno);ResultSet rs = ps.executeQuery()){
				
				if (rs.getFetchSize() > 0) {
					
					while (rs.next()) {
						String value = rs.getString(1);
						if (value.equals("-0000")) {
							zeronegative = true;
						}
						spheres.add(rs.getInt(1));
			        }
					
					Set<Integer> stringSet = new HashSet<>(spheres);
					spheres = new ArrayList<Integer>();
					spheres.addAll(stringSet);
					Collections.sort(spheres);
				}
				   
				
			} catch (SQLException e) {
				log.error("ContactLensServiceImpl: Error al obtener las esferas de lentes de contacto en la BBDD", e);
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		spheres = transformList(spheres);
		
		List<String> listResult = spheres.stream().map(n -> String.format("%+05d", n)).collect(Collectors.toList());
		if (zeronegative) {
			Collections.replaceAll(listResult, "+0000","-0000");
		}
		return listResult;
	}

	private PreparedStatement createPSSpheres(Connection con, String codigocentral, String diseno) throws SQLException {
		
		String sql = "SELECT DISTINCT xesfera FROM dbo.cio_mys_lentesnoconfigurables " + 
					 "WHERE xcodigocentral='" + codigocentral + "' AND xtipodepieza='" + diseno + "' " + 
					 "ORDER BY xesfera DESC;";

		return con.prepareStatement(sql);
	}

	@Override
	public List<String> getCylinders(String codigocentral, String diseno, String esfera) {

		List<Integer> cilindros = new ArrayList<>();
		Context initContext;
		
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/cioneLab");
			
			try (Connection con = ds.getConnection();PreparedStatement ps = createPSCylinders(con,codigocentral,diseno,esfera);ResultSet rs = ps.executeQuery()){
				
				while (rs.next()) {
					cilindros.add(rs.getInt(1));
		        }
				
				Set<Integer> stringSet = new HashSet<>(cilindros);
				cilindros = new ArrayList<Integer>();
				cilindros.addAll(stringSet);
				Collections.sort(cilindros);
				
			} catch (SQLException e) {
				log.error("ContactLensServiceImpl: Error al obtener los cilindros de lentes de contacto en la BBDD", e);
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		cilindros = transformList(cilindros);
		
		return cilindros.stream().map(n -> String.format("%+04d", n)).collect(Collectors.toList());
	}

	private PreparedStatement createPSCylinders(Connection con, String codigocentral, String diseno, String esfera) throws SQLException {
		
		String wherecodigocentral = "WHERE xcodigocentral='" + codigocentral + "'";
		String disenonil = diseno==null ? "" : diseno;
		String andwheredisenocentral = "AND xtipodepieza='" + disenonil + "'";
		String esferanil = esfera==null ? "" : esfera;
		String andwhereesferacentral = "AND xesfera='" + esferanil + "'";
		String andwherecylinder = "AND xcilindro !=''";
		
		String order = "ORDER BY xcilindro DESC;";
		
		String sql = "SELECT DISTINCT xcilindro FROM dbo.cio_mys_lentesnoconfigurables " + wherecodigocentral;
		
		if (diseno!=null && !diseno.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwheredisenocentral;
		}
		
		if (esfera!=null && !esfera.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwhereesferacentral;
		}
		
		sql = sql + StringUtils.SPACE + andwherecylinder;
		sql = sql + StringUtils.SPACE + order;

		return con.prepareStatement(sql);
		
	}

	@Override
	public List<String> getAxis(String codigocentral, String diseno, String esfera, String cylinder) {

		List<Integer> ejes = new ArrayList<>();
		Context initContext;
		
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/cioneLab");
			
			try (Connection con = ds.getConnection();PreparedStatement ps = createPSAxis(con,codigocentral,diseno,esfera,cylinder);ResultSet rs = ps.executeQuery()){
				
				while (rs.next()) {
					ejes.add(rs.getInt(1));
		        }
				
				Set<Integer> stringSet = new HashSet<>(ejes);
				ejes = new ArrayList<Integer>();
				ejes.addAll(stringSet);
				Collections.sort(ejes);
				
			} catch (SQLException e) {
				log.error("ContactLensServiceImpl: Error al obtener los ejes de lentes de contacto en la BBDD", e);
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}

		ejes = transformList(ejes);
		
		return ejes.stream().map(n -> String.format("%03d", n)).collect(Collectors.toList());
	}

	private PreparedStatement createPSAxis(Connection con, String codigocentral, String diseno, String esfera, String cilindro) throws SQLException {
		
		String wherecodigocentral = "WHERE xcodigocentral='" + codigocentral + "'";
		String disenonil = diseno==null || diseno.isEmpty() ? "" : diseno;
		String andwherediseno = "AND xtipodepieza='" + disenonil + "'";
		String esferanil = esfera==null ? "" : esfera;
		String andwhereesfera = "AND xesfera='" + esferanil + "'";
		String cilindronil = cilindro==null ? "" : cilindro;
		String andwherecilindro = "AND xcilindro='" + cilindronil + "'";
		String andwhereaxis = "AND xeje !=''";
		String order = "ORDER BY xeje DESC;";
		
		String sql = "SELECT DISTINCT xeje FROM dbo.cio_mys_lentesnoconfigurables " + wherecodigocentral;
		
		if (diseno!=null && !diseno.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwherediseno;
		}
		
		if (esfera!=null && !esfera.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwhereesfera;
		}
		
		if (cilindro!=null && !cilindro.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwherecilindro;
		}
		
		sql = sql + StringUtils.SPACE + andwhereaxis;
		sql = sql + StringUtils.SPACE + order;

		return con.prepareStatement(sql);
	}

	@Override
	public List<String> getDiameters(String codigocentral, String diseno, String esfera, String cylinder, String eje) {

		List<Integer> diametros = new ArrayList<>();
		Context initContext;
		
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/cioneLab");
			
			try (Connection con = ds.getConnection();PreparedStatement ps = createPSDiameters(con,codigocentral,diseno,esfera,cylinder,eje);ResultSet rs = ps.executeQuery()){
				
				while (rs.next()) {
					diametros.add(rs.getInt(1));
		        }
				
				Set<Integer> stringSet = new HashSet<>(diametros);
				diametros = new ArrayList<Integer>();
				diametros.addAll(stringSet);
				Collections.sort(diametros);
				
			} catch (SQLException e) {
				log.error("ContactLensServiceImpl: Error al obtener los diametros de lentes de contacto en la BBDD", e);
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}

		diametros = transformList(diametros);
		
		return diametros.stream().map(n -> String.format("%04d", n)).collect(Collectors.toList());
	}

	private PreparedStatement createPSDiameters(Connection con, String codigocentral, String diseno, String esfera, String cilindro, String eje) throws SQLException {
		
		String wherecodigocentral = "WHERE xcodigocentral='" + codigocentral + "'";
		
		String disenonil = diseno==null || diseno.isEmpty() ? "" : diseno;
		String andwherediseno = "AND xtipodepieza='" + disenonil + "'";
		
		String esferanil = esfera==null ? "" : esfera;
		String andwhereesfera = "AND xesfera='" + esferanil + "'";
		
		String cilindronil = cilindro==null ? "" : cilindro;
		String andwherecilindro = "AND xcilindro='" + cilindronil + "'";
		
		String ejenil = eje==null ? "" : eje;
		String andwhereeje = "AND xeje='" + ejenil + "'";
		
		String andwherediameters = "AND xdiametro !=''";
		String order = "ORDER BY xdiametro DESC;";
		
		String sql = "SELECT DISTINCT xdiametro FROM dbo.cio_mys_lentesnoconfigurables " + wherecodigocentral;
		
		if (diseno!=null && !diseno.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwherediseno;
		}
		
		if (esfera!=null && !esfera.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwhereesfera;
		}
		
		if (cilindro!=null && !cilindro.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwherecilindro;
		}
		
		if (eje!=null && !eje.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwhereeje;
		}
		
		sql = sql + StringUtils.SPACE + andwherediameters;
		sql = sql + StringUtils.SPACE + order;

		return con.prepareStatement(sql);
	}

	@Override
	public List<String> getRadios(String codigocentral, String diseno, String esfera, String cylinder, String eje, String diametro) {

		List<Integer> radios = new ArrayList<>();
		Context initContext;
		
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/cioneLab");
			
			try (Connection con = ds.getConnection();PreparedStatement ps = createPSRadios(con,codigocentral,diseno,esfera,cylinder,eje,diametro);ResultSet rs = ps.executeQuery()){
				
				while (rs.next()) {
					radios.add(rs.getInt(1));
		        }
				
				Set<Integer> stringSet = new HashSet<>(radios);
				radios = new ArrayList<Integer>();
				radios.addAll(stringSet);
				Collections.sort(radios);
				
			} catch (SQLException e) {
				log.error("ContactLensServiceImpl: Error al obtener los radios de lentes de contacto en la BBDD", e);
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}

		radios = transformList(radios);
		
		return radios.stream().map(n -> String.format("%03d", n)).collect(Collectors.toList());
	}

	private PreparedStatement createPSRadios(Connection con, String codigocentral, String diseno, String esfera, String cilindro, String eje, String diametro) throws SQLException {
		
		String wherecodigocentral = "WHERE xcodigocentral='" + codigocentral + "'";
		
		String disenonil = diseno==null || diseno.isEmpty() ? "" : diseno;
		String andwherediseno = "AND xtipodepieza='" + disenonil + "'";
		
		String esferanil = esfera==null ? "" : esfera;
		String andwhereesfera = "AND xesfera='" + esferanil + "'";
		
		String cilindronil = cilindro==null ? "" : cilindro;
		String andwherecilindro = "AND xcilindro='" + cilindronil + "'";
		
		String ejenil = eje==null ? "" : eje;
		String andwhereeje = "AND xeje='" + ejenil + "'";
		
		String diametronil = diametro==null ? "" : diametro;
		String andwherediametro = "AND xdiametro='" + diametronil + "'";
		
		String andwhereradios = "AND xradio !=''";
		String order = "ORDER BY xradio DESC;";
		
		String sql = "SELECT DISTINCT xradio FROM dbo.cio_mys_lentesnoconfigurables " + wherecodigocentral;
		
		if (diseno!=null && !diseno.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwherediseno;
		}
		
		if (esfera!=null && !esfera.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwhereesfera;
		}
		
		if (cilindro!=null && !cilindro.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwherecilindro;
		}
		
		if (eje!=null && !eje.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwhereeje;
		}
		
		if (diametro!=null && !diametro.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwherediametro;
		}
		
		sql = sql + StringUtils.SPACE + andwhereradios;
		sql = sql + StringUtils.SPACE + order;

		return con.prepareStatement(sql);
	}

	@Override
	public List<String> getAdditions(String codigocentral, String diseno, String esfera, String cylinder, String eje, String diametro, String radio) {

		List<String> additions = new ArrayList<>();
		Context initContext;
		
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/cioneLab");
			
			try (Connection con = ds.getConnection();PreparedStatement ps = createPSAdditions(con,codigocentral,diseno,esfera,cylinder,eje,diametro,radio);ResultSet rs = ps.executeQuery()){
				
				while (rs.next()) {
					additions.add(rs.getString(1));
		        }
				
				
			} catch (SQLException e) {
				log.error("ContactLensServiceImpl: Error al obtener las adiciones de lentes de contacto en la BBDD", e);
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		return additions;
	}

	private PreparedStatement createPSAdditions(Connection con, String codigocentral, String diseno, String esfera, String cilindro, String eje, String diametro, String radio) throws SQLException {
		
		String wherecodigocentral = "WHERE xcodigocentral='" + codigocentral + "'";
		
		String disenonil = diseno==null || diseno.isEmpty() ? "" : diseno;
		String andwherediseno = "AND xtipodepieza='" + disenonil + "'";
		
		String esferanil = esfera==null ? "" : esfera;
		String andwhereesfera = "AND xesfera='" + esferanil + "'";
		
		String cilindronil = cilindro==null ? "" : cilindro;
		String andwherecilindro = "AND xcilindro='" + cilindronil + "'";
		
		String ejenil = eje==null ? "" : eje;
		String andwhereeje = "AND xeje='" + ejenil + "'";
		
		String diametronil = diametro==null ? "" : diametro;
		String andwherediametro = "AND xdiametro='" + diametronil + "'";
		
		String radionil = radio==null ? "" : radio;
		String andwhereradio = "AND xradio='" + radionil + "'";
		
		String andwhereaddition = "AND xadicion !=''";
		String order = "ORDER BY xadicion DESC;";
		
		String sql = "SELECT DISTINCT xadicion FROM dbo.cio_mys_lentesnoconfigurables " + wherecodigocentral;
		
		if (diseno!=null && !diseno.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwherediseno;
		}
		
		if (esfera!=null && !esfera.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwhereesfera;
		}
		
		if (cilindro!=null && !cilindro.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwherecilindro;
		}
		
		if (eje!=null && !eje.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwhereeje;
		}
		
		if (diametro!=null && !diametro.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwherediametro;
		}
		
		if (radio!=null && !radio.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwhereradio;
		}
		
		sql = sql + StringUtils.SPACE + andwhereaddition;
		sql = sql + StringUtils.SPACE + order;

		return con.prepareStatement(sql);
	}

	@Override
	public List<String> getColors(String codigocentral, String diseno, String esfera, String cylinder, String eje, String diametro, String radio, String adicion) {

		List<String> colors = new ArrayList<>();
		Context initContext;
		
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/cioneLab");
			
			try (Connection con = ds.getConnection();PreparedStatement ps = createPSColors(con,codigocentral,diseno,esfera,cylinder,eje,diametro,radio, adicion);ResultSet rs = ps.executeQuery()){
				
				while (rs.next()) {
					colors.add(rs.getString(1));
		        }
				
				
			} catch (SQLException e) {
				log.error("ContactLensServiceImpl: Error al obtener los colores de lentes de contacto en la BBDD", e);
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		return colors;
	}

	private PreparedStatement createPSColors(Connection con, String codigocentral, String diseno, String esfera, String cilindro, String eje, String diametro, String radio, String adicion) throws SQLException {
		
		String wherecodigocentral = "WHERE xcodigocentral='" + codigocentral + "'";
		
		String disenonil = diseno==null || diseno.isEmpty() ? "" : diseno;
		String andwherediseno = "AND xtipodepieza='" + disenonil + "'";
		
		String esferanil = esfera==null ? "" : esfera;
		String andwhereesfera = "AND xesfera='" + esferanil + "'";
		
		String cilindronil = cilindro==null ? "" : cilindro;
		String andwherecilindro = "AND xcilindro='" + cilindronil + "'";
		
		String ejenil = eje==null ? "" : eje;
		String andwhereeje = "AND xeje='" + ejenil + "'";
		
		String diametronil = diametro==null ? "" : diametro;
		String andwherediametro = "AND xdiametro='" + diametronil + "'";
		
		String radionil = radio==null ? "" : radio;
		String andwhereradio = "AND xradio='" + radionil + "'";
		
		String adicionnil = adicion==null ? "" : adicion;
		String andwhereadicion = "AND xadicion='" + adicionnil + "'";
		
		String andwherecolor = "AND xcolor !=''";
		String order = "ORDER BY xcolor DESC;";
		
		String sql = "SELECT DISTINCT xcolor FROM dbo.cio_mys_lentesnoconfigurables " + wherecodigocentral;
		
		if (diseno!=null && !diseno.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwherediseno;
		}
		
		if (esfera!=null && !esfera.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwhereesfera;
		}
		
		if (cilindro!=null && !cilindro.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwherecilindro;
		}
		
		if (eje!=null && !eje.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwhereeje;
		}
		
		if (diametro!=null && !diametro.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwherediametro;
		}
		
		if (radio!=null && !radio.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwhereradio;
		}
		
		if (adicion!=null && !adicion.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwhereadicion;
		}
		
		sql = sql + StringUtils.SPACE + andwherecolor;
		sql = sql + StringUtils.SPACE + order;

		return con.prepareStatement(sql);
	}

	@Override
	public String getSku(String codigocentral, String diseno, String esfera, String cylinder, String eje, String diametro, String radio, String adicion, String color) {

		String sku = "";
		Context initContext;
		
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/cioneLab");
			
			try (Connection con = ds.getConnection();PreparedStatement ps = createPSSku(con,codigocentral,diseno,esfera,cylinder,eje,diametro,radio, adicion, color);ResultSet rs = ps.executeQuery()){
				
				if (rs.next()) {
					sku = rs.getString(1);
				}
				
			} catch (SQLException e) {
				log.error("ContactLensServiceImpl: Error al obtener el sku de lentes de contacto en la BBDD", e);
			}
			 
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		return sku;
	}

	private PreparedStatement createPSSku(Connection con, String codigocentral, String diseno, String esfera, String cilindro, String eje, String diametro, String radio, String adicion, String color) throws SQLException {
		
		String wherecodigocentral = "WHERE xcodigocentral='" + codigocentral + "'";
		
		String disenonil = diseno==null || diseno.isEmpty() ? "" : diseno;
		String andwherediseno = "AND xtipodepieza='" + disenonil + "'";
		
		String esferanil = esfera==null ? "" : esfera;
		String andwhereesfera = "AND xesfera='" + esferanil + "'";
		
		String cilindronil = cilindro==null ? "" : cilindro;
		String andwherecilindro = "AND xcilindro='" + cilindronil + "'";
		
		String ejenil = eje==null ? "" : eje;
		String andwhereeje = "AND xeje='" + ejenil + "'";
		
		String diametronil = diametro==null ? "" : diametro;
		String andwherediametro = "AND xdiametro='" + diametronil + "'";
		
		String radionil = radio==null ? "" : radio;
		String andwhereradio = "AND xradio='" + radionil + "'";
		
		String adicionnil = adicion==null ? "" : adicion;
		String andwhereadicion = "AND xadicion='" + adicionnil + "'";
		
		String colornil = color==null ? "" : color;
		String andwherecolor = "AND xcolor='" + colornil + "'";
		
		String andwheresku = "AND xarticulo_id !=''";
		
		String sql = "SELECT DISTINCT xarticulo_id FROM dbo.cio_mys_lentesnoconfigurables " + wherecodigocentral;
		
		if (diseno!=null && !diseno.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwherediseno;
		}
		
		if (esfera!=null && !esfera.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwhereesfera;
		}
		
		if (cilindro!=null && !cilindro.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwherecilindro;
		}
		
		if (eje!=null && !eje.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwhereeje;
		}
		
		if (diametro!=null && !diametro.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwherediametro;
		}
		
		if (radio!=null && !radio.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwhereradio;
		}
		
		if (adicion!=null && !adicion.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwhereadicion;
		}
		
		if (color!=null && !color.isEmpty()) {
			sql = sql + StringUtils.SPACE + andwherecolor;
		}
		
		sql = sql + StringUtils.SPACE + andwheresku;

		return con.prepareStatement(sql);
	}
	

	
	private List<Integer> transformList(List<Integer> list){
		
		if (list != null && !list.isEmpty() && list.size() > 1) {
			
			// Dividimos en dos listas negativos y positivos para ordenar por
			// separado (en un orden absurdo los negativos (0, -0,25, - 0,5, -0,75...))
			List<Integer> positives = list.stream().filter(i -> i >= 0).collect(Collectors.toList());
			List<Integer> negatives = list.stream().filter(i -> i <= 0).collect(Collectors.toList());
			
			//eliminas los repetidos (Ologn)
			HashSet<Integer> uniqueHasSetPositives = new HashSet<>(positives);
			HashSet<Integer> uniqueHasSetNegatives = new HashSet<>(negatives);
			
			//Ordenas (Ologn)
			TreeSet<Integer> uniqueTreeSetPositives = new TreeSet<>(uniqueHasSetPositives);
			TreeSet<Integer> uniqueTreeSetNegatives = new TreeSet<>(uniqueHasSetNegatives);
			TreeSet<Integer> uniqueTreeSetNegativesDes = new TreeSet<>(uniqueTreeSetNegatives.descendingSet());
			
			List<Integer> uniqueJoinList = new ArrayList<>();
			
			if(!uniqueTreeSetNegativesDes.isEmpty()) {
			    for (Integer value : uniqueTreeSetNegativesDes) {
			    	if(!uniqueJoinList.contains(value)) {
			    		uniqueJoinList.add(value);
			    	}
			    }
		    }
			
			if(!uniqueTreeSetPositives.isEmpty()) {
			    for (Integer value : uniqueTreeSetPositives) {
			    	if(!uniqueJoinList.contains(value)) {
			    		uniqueJoinList.add(value);
			    	}
			    }
		    }
			
			return uniqueJoinList;
		}
		
		return list;
		
	}

}
