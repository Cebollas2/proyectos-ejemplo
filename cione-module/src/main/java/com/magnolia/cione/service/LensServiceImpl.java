package com.magnolia.cione.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.LensFilterDTO;
import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.dto.CT.variants.Term;
import com.magnolia.cione.dto.CT.variants.VariantsAttributes;
import com.magnolia.cione.utils.CioneUtils;
import com.magnolia.cione.utils.MyShopUtils;

import info.magnolia.context.MgnlContext;

public class LensServiceImpl implements LensService{
	
	private static final Logger log = LoggerFactory.getLogger(LensServiceImpl.class);

	@Inject
	private CommercetoolsService commercetoolsservice;
	
	@Inject
	private MiddlewareService middlewareService;
	
	@Override
	public LensFilterDTO getFacets(Map<String, String[]> filters, String keycustomergroup) {
		
		LensFilterDTO facets = new LensFilterDTO();
		Context initContext;
		
		//Proveedores
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/cioneLab");
			
			try (Connection con = ds.getConnection();PreparedStatement ps = createPSSupplier(con,keycustomergroup);ResultSet rs = ps.executeQuery()){
				
				VariantsAttributes proveedores = new VariantsAttributes();
				List<Term> terms = new ArrayList<>();
				
				while (rs.next()) {
					
					Term term = new Term();
					term.setTerm(rs.getString(2));
					term.setValue(rs.getString(1));
					terms.add(term);
		        }
				
				proveedores.setTerms(terms);
				facets.setProveedores(proveedores);
				
			} catch (SQLException e) {
				log.error("LensServiceImpl: Error al obtener los proveedores de lentes en la BBDD", e);
			}
			
			//Materiales
			try (Connection con = ds.getConnection();PreparedStatement ps = createPSMaterial(con,keycustomergroup);ResultSet rs = ps.executeQuery()){
				
				VariantsAttributes materiales = new VariantsAttributes();
				List<Term> terms = new ArrayList<>();
				
				while (rs.next()) {
					
					Term term = new Term();
					term.setTerm(rs.getString(2));
					term.setValue(rs.getString(1));
					terms.add(term);
		        }
				
				materiales.setTerms(terms);
				facets.setMateriales(materiales);
				
			} catch (SQLException e) {
				log.error("LensServiceImpl: Error al obtener los materiales de lentes en la BBDD", e);
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_PROVEEDOR)) {
			facets.getProveedores().setSelectedByValue(filters.get(MyshopConstants.FF_PROVEEDOR));
		}
		
		if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_MATERIAL)) {
			facets.getMateriales().setSelectedByValue(filters.get(MyshopConstants.FF_MATERIAL));
		}
		
		return facets;
	}
	
	private PreparedStatement createPSSupplier(Connection con, String keycustomergroup) throws SQLException {
		
		String sql = "SELECT proveedor AS cod_proveedor, abreviado AS nom_proveedor " + 
					 "FROM dbo.cio_proveedores WITH (NOLOCK) " +
					 "WHERE proveedor IN (SELECT DISTINCT nproveedor " +
					 	"FROM dbo.tblLENSNAMES WITH (NOLOCK) " +
					 	"WHERE status = '1' and PK_LNAM IN (select DISTINCT PK_LNAM " +
					 		"FROM dbo.cio_precio_fab WITH (NOLOCK) " +
					 		"WHERE GrupoPrecio = '" + keycustomergroup + "'));";
		
		return con.prepareStatement(sql);
	}
	
	private PreparedStatement createPSMaterial(Connection con, String keycustomergroup) throws SQLException {
		
		String sql = "SELECT id AS idMaterial, name AS nameMaterial FROM dbo.tblLMATTYPE WHERE id in" +
					  "(SELECT DISTINCT LMATTYPE FROM dbo.tblLENSNAMES WITH (NOLOCK) WHERE status = '1' and PK_LNAM IN " +
					  "(SELECT DISTINCT PK_LNAM FROM dbo.cio_precio_fab WITH (NOLOCK) WHERE GrupoPrecio = '" + keycustomergroup + "'));";

		return con.prepareStatement(sql);
	}

	@Override
	public List<String> getCilindros(Map<String, String[]> filters) {
		
		List<Integer> cilindros = new ArrayList<>();
		Context initContext;
		
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/cioneLab");
			
			try (Connection con = ds.getConnection();PreparedStatement ps = createPSCilindro(con, filters);ResultSet rs = ps.executeQuery()){
				
				while (rs.next()) {
					cilindros.add(rs.getInt(1));
		        }
				
				//omitimos repetidos en caso de existir y ordenamos los enteros
				//no hacer con arraylist de string dado que no ordena de forma correcta (225,25,110,10...)
				Set<Integer> stringSet = new HashSet<>(cilindros);
				cilindros = new ArrayList<>();
				cilindros.addAll(stringSet);
				Collections.sort(cilindros);   
				
			} catch (SQLException e) {
				log.error("LensServiceImpl: Error al obtener los cilindros de lentes en la BBDD", e);
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		cilindros = transformList(cilindros);
		
		return cilindros.stream().map(n -> String.format("%+04d", n)).collect(Collectors.toList());
	}
	
	private PreparedStatement createPSCilindro(Connection con, Map<String, String[]> filters) throws SQLException {
		
		String proveedores = "";
		String materiales = "";
		
		if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_PROVEEDOR)) {
			proveedores = getProveedoresSQL(filters.get(MyshopConstants.FF_PROVEEDOR));
		}
		
		if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_MATERIAL)) {
			materiales = getMaterialesSQL(filters.get(MyshopConstants.FF_MATERIAL));
		}
		
		String sql = "SELECT DISTINCT CYL FROM dbo.tblLENS WITH (NOLOCK) WHERE status = '1' AND FK_LNAM IN " +
				"(SELECT PK_LNAM FROM dbo.tblLENSNAMES WITH (NOLOCK) WHERE status = '1' " + proveedores + 
				materiales + "AND PK_LNAM IN " +
				"(SELECT DISTINCT PK_LNAM FROM dbo.cio_precio_fab WITH (NOLOCK) WHERE GrupoPrecio = 'VCO')) " +
				"ORDER BY CYL DESC;";

		return con.prepareStatement(sql);
	}

	/**
	 * 
	 * Funcion que calcula la consulta SQL los proveedores que 
	 * deben incluirse en la consulta principal
	 * 
	 * @param proveedores lista de provedores
	 * @return subconsulta con los proveedores
	 */
	private String getProveedoresSQL(String[] proveedores) {
		
		String sql = "AND (";
		
		for(int i = 0; i <= proveedores.length - 1; i++){
			
			String proveedor = proveedores[i];
			
			if (i == proveedores.length - 1) {
				sql = sql.concat("nproveedor='" + proveedor + "') ");
	        }else {
	        	sql = sql.concat("nproveedor='" + proveedor + "' OR ");
	        }
		    
		}
		
		return sql.equals("AND (") ? "" : sql;
	}

	/**
	 * 
	 * Funcion que calcula la consulta SQL con los materiales que 
	 * deben incluirse en la consulta principal
	 * 
	 * @param materiales lista de materiales
	 * @return subconsulta con los materiales
	 */
	private String getMaterialesSQL(String[] materiales) {
		
		String sql = "AND (";
		
		for(int i = 0; i <= materiales.length - 1; i++){
			
			String material = materiales[i];
			
			if (i == materiales.length - 1) {
				sql = sql.concat("LMATTYPE='" + material + "') ");
	        }else {
	        	sql = sql.concat("LMATTYPE='" + material + "' OR ");
	        }
		    
		}
		
		return sql.equals("AND (") ? "" : sql;
	}

	@Override
	public List<String> getSpheresByCylinder(int cylinder) {

		List<Integer> spheres = new ArrayList<>();
		Context initContext;
		
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/cioneLab");
			
			try (Connection con = ds.getConnection();PreparedStatement ps = createPSSpheres(con,cylinder,getAllFilters());ResultSet rs = ps.executeQuery()){
				
				while (rs.next()) {
					spheres.add(rs.getInt(1));
		        }
				
				Set<Integer> stringSet = new HashSet<>(spheres);
				spheres = new ArrayList<Integer>();
				spheres.addAll(stringSet);
				Collections.sort(spheres);   
				
			} catch (SQLException e) {
				log.error("LensServiceImpl: Error al obtener las esferas de lentes en la BBDD", e);
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		spheres = transformList(spheres);
		
		return spheres.stream().map(n -> String.format("%+05d", n)).collect(Collectors.toList());
	}
	
	private Map<String, String[]> getAllFilters() {
		
		Map<String, String[]> filters = new HashMap<>();
		
		if (MgnlContext.getParameterValues(MyshopConstants.FF_PROVEEDOR) != null && MgnlContext.getParameterValues(MyshopConstants.FF_PROVEEDOR).length > 0) {
			filters.put(MyshopConstants.FF_PROVEEDOR, MgnlContext.getParameterValues(MyshopConstants.FF_PROVEEDOR));
		}
		
		if (MgnlContext.getParameterValues(MyshopConstants.FF_MATERIAL) != null && MgnlContext.getParameterValues(MyshopConstants.FF_MATERIAL).length > 0) {
			filters.put(MyshopConstants.FF_MATERIAL, MgnlContext.getParameterValues(MyshopConstants.FF_MATERIAL));
		}
		
		return filters;
	}

	private PreparedStatement createPSSpheres(Connection con, int cylinder, Map<String, String[]> filters) throws SQLException {
		
		UserERPCioneDTO user =middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
		String keycustomergroup = "";
		if (user != null)
			keycustomergroup = user.getGrupoPrecio();
		
		if (keycustomergroup != null && keycustomergroup.isEmpty()) {
			String customerid = commercetoolsservice.getIdOfCustomerGroupByCostumerId(MyShopUtils.getUserName());
			keycustomergroup = commercetoolsservice.getKeyOfCustomerGroupById(customerid);
		}
		
		String proveedores = "";
		String materiales = "";
		
		if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_PROVEEDOR)) {
			proveedores = getProveedoresSQL(filters.get(MyshopConstants.FF_PROVEEDOR));
		}
		
		if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_MATERIAL)) {
			materiales = getMaterialesSQL(filters.get(MyshopConstants.FF_MATERIAL));
		}
		
		String sql = "SELECT DISTINCT SPH FROM dbo.tblLENS WITH (NOLOCK) WHERE status = '1' AND CYL='" + cylinder + "' AND FK_LNAM in " + 
					  "(SELECT PK_LNAM FROM dbo.tblLENSNAMES WITH (NOLOCK) WHERE status = '1' " + proveedores + 
						materiales + " AND PK_LNAM IN " + 
					  "(SELECT DISTINCT PK_LNAM FROM dbo.cio_precio_fab WITH (NOLOCK) WHERE GrupoPrecio = '" + keycustomergroup + "')) " +
					  "ORDER BY SPH DESC;";

		return con.prepareStatement(sql);
	}

	@Override
	public LinkedHashMap<String, String> getLensByCylinderAndSphere(int cylinder, int sphere) {

		LinkedHashMap<String, String> lens = new LinkedHashMap<String, String>();
		Context initContext;
		
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/cioneLab");
			
			try (Connection con = ds.getConnection();PreparedStatement ps = createPSCylinderAndSpheres(con,cylinder,sphere,getAllFilters());ResultSet rs = ps.executeQuery()){
				
				while (rs.next()) {
					String descripcion = rs.getString(3);
					
					String[] stdescripcion = descripcion.split(";");
					
					String ciogrupopreciodescripcion = rs.getString(4);
					switch (ciogrupopreciodescripcion) {
						case "0": {
							if (0 < stdescripcion.length) {
								descripcion = stdescripcion[0];
							}
							break;
						}
						case "1": {
							if (1 < stdescripcion.length) {
								descripcion = stdescripcion[1];
							}
							break;
						}
						case "2": {
							if (2 < stdescripcion.length) {
								descripcion = stdescripcion[2];
							}
							break;
						}
					}
					//nombre de la lente, codigo de la lente
					lens.put(descripcion, rs.getString(2));
		        }
				
				
			} catch (SQLException e) {
				log.error("LensServiceImpl: Error al obtener las esferas de lentes en la BBDD", e);
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
	    /*LinkedHashMap<String, String> sortedMap = new LinkedHashMap<>();
	    lens.entrySet().stream().sorted(Map.Entry.<String, String>comparingByKey())
	        .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));*/
	    
	    
		return lens;
	}
	
    private HashMap<String, String> sortByValue(HashMap<String, String> hm){
        // Create a list from elements of HashMap
        List<Map.Entry<String, String> > list
            = new LinkedList<Map.Entry<String, String> >(
                hm.entrySet());
 
        // Sort the list using lambda expression
        Collections.sort(
            list,
            (i1,
             i2) -> i1.getValue().compareTo(i2.getValue()));
 
        // put data from sorted list to hashmap
        HashMap<String, String> temp
            = new LinkedHashMap<String, String>();
        for (Map.Entry<String, String> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

	private PreparedStatement createPSCylinderAndSpheres(Connection con, int cylinder, int sphere, Map<String, String[]> filters) throws SQLException {
		
		UserERPCioneDTO user =middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
		String keycustomergroup = "";
		if (user != null)
			keycustomergroup = user.getGrupoPrecio();
		
		if (keycustomergroup != null && keycustomergroup.isEmpty()) {
			String customerid = commercetoolsservice.getIdOfCustomerGroupByCostumerId(MyShopUtils.getUserName());
			keycustomergroup = commercetoolsservice.getKeyOfCustomerGroupById(customerid);
		}
		
		String proveedores = "";
		String materiales = "";
		
		if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_PROVEEDOR)) {
			proveedores = getProveedoresSQL(filters.get(MyshopConstants.FF_PROVEEDOR));
		}
		
		if (!filters.isEmpty() && filters.containsKey(MyshopConstants.FF_MATERIAL)) {
			materiales = getMaterialesSQL(filters.get(MyshopConstants.FF_MATERIAL));
		}
		
		
		 
		String sql = "SELECT tblLENSNAMES.LNAM AS nomLente, tblLENSNAMES.PK_LNAM AS codLente, tblLENSNAMES.DESCRIPCION, cio_precio_fab.ciogrupopreciodescripcion " +
				"FROM dbo.tblLENSNAMES "

				+"INNER JOIN dbo.cio_precio_fab "
				+"ON tblLENSNAMES.PK_LNAM = cio_precio_fab.PK_LNAM "
				+"WHERE status = '1' AND cio_precio_fab.GrupoPrecio='" + keycustomergroup +"' AND tblLENSNAMES.PK_LNAM IN "
				+"(SELECT DISTINCT FK_LNAM FROM dbo.tblLENS WITH (NOLOCK) WHERE STATUS='1' AND CYL = '" + cylinder + "' AND SPH = '" + sphere + "' AND FK_LNAM in "
				+"(SELECT PK_LNAM FROM dbo.tblLENSNAMES WITH (NOLOCK) WHERE status = '1' " + proveedores + materiales + " AND PK_LNAM IN "
				+"(SELECT DISTINCT PK_LNAM FROM dbo.cio_precio_fab WITH (NOLOCK) WHERE GrupoPrecio = '" + keycustomergroup + "'))) "

				+"GROUP BY LNAM, tblLENSNAMES.PK_LNAM, DESCRIPCION, ciogrupopreciodescripcion ORDER BY DESCRIPCION ASC;";
		log.debug("CONSULTA PRUEBAS  " + sql); 
		
		/*String sql2= "SELECT LNAM AS nomLente, FK_LNAM AS codLente, DESCRIPCION, ciogrupopreciodescripcion "
				+ "FROM dbo.tblLENSNAMES WITH (NOLOCK) "
				+ "JOIN "
				+ "(SELECT DISTINCT FK_LNAM FROM dbo.tblLENS WITH (NOLOCK) WHERE STATUS='1' AND CYL = '" + cylinder + "' AND SPH = '" + sphere + "' "
				+ "AND FK_LNAM IN (SELECT PK_LNAM FROM dbo.tblLENSNAMES WITH (NOLOCK) WHERE status = '1' " + proveedores + materiales
				+ "AND PK_LNAM IN (SELECT DISTINCT PK_LNAM FROM dbo.cio_precio_fab WITH (NOLOCK) WHERE GrupoPrecio = '" + keycustomergroup + "'))) l "
				+ "ON "
				+ "tblLENSNAMES.PK_LNAM = l.FK_LNAM "
				+ "JOIN "
				+ "dbo.cio_precio_fab WITH (NOLOCK) "
				+ "ON "
				+ "tblLENSNAMES.PK_LNAM = cio_precio_fab.PK_LNAM "
				+ "WHERE tblLENSNAMES.status = '1' "
				+ "GROUP BY LNAM, FK_LNAM, DESCRIPCION, ciogrupopreciodescripcion "
				+ "ORDER BY DESCRIPCION ASC;"; 
				log.debug("CONSULTA PRUEBAS  " + sql2);*/
		
		
		
		//return con.prepareStatement(sql);

		return con.prepareStatement(sql);
	}

	@Override
	public List<String> getDiametersByLens(int cylinder, int sphere, int lens) {

		List<Integer> diameters = new ArrayList<>();
		Context initContext;
		
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/cioneLab");
			
			//try (Connection con = ds.getConnection();PreparedStatement ps = createPSDiameter(con,cylinder,sphere,lens);ResultSet rs = ps.executeQuery()){
			try (Connection con = ds.getConnection();PreparedStatement ps = createPSDiameterCheck(con,cylinder,sphere,lens);ResultSet rs = ps.executeQuery()){
				
				while (rs.next()) {
					diameters.add(rs.getInt("CRIB"));
		        }
				
				Set<Integer> stringSet = new HashSet<>(diameters);
				diameters = new ArrayList<>();
				diameters.addAll(stringSet);
				Collections.sort(diameters);   
				
			} catch (SQLException e) {
				log.error("LensServiceImpl: Error al obtener los diametros de lentes en la BBDD", e);
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		diameters = transformList(diameters);
		
		return diameters.stream().map(n -> n.toString()).collect(Collectors.toList());
	}

	private PreparedStatement createPSDiameter(Connection con, int cylinder, int sphere, int lens) throws SQLException {
		
		String sql = "SELECT DISTINCT CRIB FROM dbo.tblLENS WITH (NOLOCK) WHERE FK_LNAM = '" + lens + "' AND CYL = '" + cylinder + "' AND SPH = '" + sphere + "' ORDER BY CRIB ASC;";

		return con.prepareStatement(sql);
	}
	
	private PreparedStatement createPSDiameterCheck(Connection con, int cylinder, int sphere, int lens) throws SQLException {
		UserERPCioneDTO user =middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
		String keycustomergroup = "";
		if (user != null)
			keycustomergroup = user.getGrupoPrecio();
		
		if (keycustomergroup != null && keycustomergroup.isEmpty()) {
			String customerid = commercetoolsservice.getIdOfCustomerGroupByCostumerId(MyShopUtils.getUserName());
			keycustomergroup = commercetoolsservice.getKeyOfCustomerGroupById(customerid);
		}
		
		String sql = "SELECT DISTINCT OPC_ERP, CRIB FROM dbo.tblLENS WITH (NOLOCK) "
				+ "	WHERE FK_LNAM = '" + lens + "' AND CYL = '" + cylinder + "' AND SPH = '" + sphere + "' AND CRIB IN (SELECT DISTINCT CRIB FROM dbo.tblLENS WITH (NOLOCK) "
				+ "		WHERE FK_LNAM = '" + lens + "' AND CYL = '" + cylinder + "' AND SPH = '" + sphere + "') and OPC_ERP in ("
				+ "			SELECT precio.Articulo FROM dbo.cio_precio_fab precio WHERE GrupoPrecio='" + keycustomergroup + "')";

		return con.prepareStatement(sql);
	}
	
	@Override
	public String getSkuLens(int cylinder, int sphere, int lens, int diameter) {
		
		String sku = "";
		Context initContext;
		
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/cioneLab");
			
			try (Connection con = ds.getConnection();PreparedStatement ps = createPSSku(con,cylinder,sphere, lens, diameter);ResultSet rs = ps.executeQuery()){
				
				while (rs.next()) {
					sku = rs.getString(1);
		        }
				
			} catch (SQLException e) {
				log.error("LensServiceImpl: Error al obtener el sku de lentes en la BBDD", e);
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		return sku;
	}

	private PreparedStatement createPSSku(Connection con, int cylinder, int sphere, int lens, int diameter) throws SQLException {
		
		String sql = "SELECT DISTINCT OPC_ERP FROM dbo.tblLENS WITH (NOLOCK) " +
					 "WHERE FK_LNAM = '" + lens + "' AND CYL = '" + cylinder + "' AND SPH = '" + sphere + "' AND CRIB = '" + diameter + "';";

		return con.prepareStatement(sql);
	}

	@Override
	public Map<String, String> getPriceBySku(String sku) {
		
		Map<String, String> price = new HashMap<String, String>();
		
		Context initContext;
		
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/cioneLab");
			
			try (Connection con = ds.getConnection();PreparedStatement ps = createPSPrice(con,sku);ResultSet rs = ps.executeQuery()){
				
				while (rs.next()) {
					price.put("pk_lnam", rs.getString(1));
					price.put("pvo", rs.getString(4));
					price.put("pvp", rs.getString(5));
		        }
				
			} catch (SQLException e) {
				log.error("LensServiceImpl: Error al obtener el precio de lentes en la BBDD", e);
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		return price;
		
	}

	private PreparedStatement createPSPrice(Connection con, String sku) throws SQLException {
		
		UserERPCioneDTO user =middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
		String keycustomergroup = "";
		if (user != null)
			keycustomergroup = user.getGrupoPrecio();
		
		if (keycustomergroup != null && keycustomergroup.isEmpty()) {
			String customerid = commercetoolsservice.getIdOfCustomerGroupByCostumerId(MyShopUtils.getUserName());
			keycustomergroup = commercetoolsservice.getKeyOfCustomerGroupById(customerid);
		}
		
		String sql = "SELECT * FROM dbo.cio_precio_fab WHERE GrupoPrecio='" + keycustomergroup + "' and Articulo='" + sku + "'";

		return con.prepareStatement(sql);
		
	}

	@Override
	public Map<String, String> getLineDataInfo(int cylinder, int sphere, int lens, int diameter) {
		
		String sku = getSkuLens(cylinder, sphere, lens, diameter);
		Map<String, String> price = getPriceBySku(sku);
		Map<String, String> info = new HashMap<String, String>();
		
		info.put("sku", sku);
		info.put("pvo", price.get("pvo"));
		info.put("pvp", price.get("pvp"));
		
		UserERPCioneDTO user =middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
		String keycustomergroup = "";
		if (user != null)
			keycustomergroup = user.getGrupoPrecio();
		
		Context initContext;
		
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/cioneLab");
			
			try (Connection con = ds.getConnection();PreparedStatement ps = createPSPLineDataInfo(con,price.get("pk_lnam"), keycustomergroup);ResultSet rs = ps.executeQuery()){
				
				while (rs.next()) {
					info.put("nivel1", rs.getString(1).trim());
					info.put("nivel2", rs.getString(2).trim());
					
					String descripcion = rs.getString(3);
					if (descripcion.indexOf(";") > 0) {
						String[] stdescripcion = descripcion.split(";");
						String ciogrupopreciodescripcion = rs.getString(6);
						switch (ciogrupopreciodescripcion) {
							case "0": {
								if (0 < stdescripcion.length) {
									descripcion = stdescripcion[0];
								}
								break;
							}
							case "1": {
								if (1 < stdescripcion.length) {
									descripcion = stdescripcion[1];
									//elimina el primer caracter en blanco
									if (Character.isWhitespace(descripcion.charAt(0)))
										descripcion = descripcion.substring(1);
									
								}
								break;
							}
							case "2": {
								if (2 < stdescripcion.length) {
									descripcion = stdescripcion[2];
								}
								break;
							}
						}
						
					}
					
					info.put("descripcion", descripcion);
					info.put("name", rs.getString(4));
					info.put("lmattype", rs.getString(5));
		        }
				
			} catch (SQLException e) {
				log.error("LensServiceImpl: Error al obtener informacion de campos ocultos de lentes en la BBDD", e);
			}
			
			try (Connection con = ds.getConnection();PreparedStatement ps = createPSLmattype(con,info.get("lmattype"));ResultSet rs = ps.executeQuery()){
				
				while (rs.next()) {
					info.put("lmattype", rs.getString(1));
		        }
				
			} catch (SQLException e) {
				log.error("LensServiceImpl: Error al obtener informacion de lmattype de lentes en la BBDD", e);
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		return info;
	}

	private PreparedStatement createPSPLineDataInfo(Connection con, String pklnam, String grupoPrecio) throws SQLException {
		
		String sql = "SELECT DISTINCT tblLENSNAMES.nivel1, tblLENSNAMES.nivel2, tblLENSNAMES.DESCRIPCION, tblLENSNAMES.LNAM, tblLENSNAMES.LMATTYPE, cio_precio_fab.ciogrupopreciodescripcion\r\n"
				+ "	FROM dbo.tblLENSNAMES \r\n"
				+ "	INNER JOIN dbo.cio_precio_fab\r\n"
				+ "	ON tblLENSNAMES.PK_LNAM = cio_precio_fab.PK_LNAM\r\n"
				+ "	where tblLENSNAMES.PK_LNAM = '" + pklnam + "'"
				+ " and cio_precio_fab.GrupoPrecio='" + grupoPrecio + "';";
		
		return con.prepareStatement(sql);
	}

	private PreparedStatement createPSLmattype(Connection con, String lmattype) throws SQLException {
		
		String sql = "SELECT DISTINCT name FROM dbo.tblLMATTYPE WHERE id='" + lmattype + "';";
		
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
