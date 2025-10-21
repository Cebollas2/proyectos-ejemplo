package com.magnolia.cione.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.beans.MarcaProveedorBBDD;

public class ConsultaMarcasDaoImpl implements ConsultaMarcasDao {
	
	private static final Logger log = LoggerFactory.getLogger(ConsultaMarcasDaoImpl.class);
	

	public List<MarcaProveedorBBDD> getBrandsForSupplier(String cod_proveedor){
		List<MarcaProveedorBBDD> marcaProveedorBBDDList = new ArrayList<MarcaProveedorBBDD>();
		Context initContext;
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
			try (Connection con = ds.getConnection();PreparedStatement ps = createPreparedStatement(con, cod_proveedor);ResultSet rs = ps.executeQuery()){
				
				while (rs.next()) {
					MarcaProveedorBBDD marcaProveedorBBDD = new MarcaProveedorBBDD();
					marcaProveedorBBDD.setCod_proveedor(rs.getString("cod_proveedor"));
					marcaProveedorBBDD.setDesc_proveedor(rs.getString("desc_proveedor"));
					marcaProveedorBBDD.setCod_familia(rs.getString("cod_familia"));
					marcaProveedorBBDD.setCod_marca(rs.getString("cod_marca"));
					marcaProveedorBBDD.setDesc_marca(rs.getString("desc_marca"));
					marcaProveedorBBDDList.add(marcaProveedorBBDD);
				}
				con.close();
			} catch (SQLException e) {
				log.error("ConsultaMarcasDaoImpl: Error al obtener las marcas por proveedor", e);
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		return marcaProveedorBBDDList;
	}
	
	public String getListBrands(String cod_proveedor) {
		String listBrands = "";
		
		List<MarcaProveedorBBDD> marcaProveedorBBDDList = getBrandsForSupplier(cod_proveedor);
		if (!marcaProveedorBBDDList.isEmpty()) {
			
			int i=1;
			for (MarcaProveedorBBDD marcainfo : marcaProveedorBBDDList) {
				listBrands = listBrands.concat(marcainfo.getDesc_marca());
				if (marcaProveedorBBDDList.size() != i) {
					listBrands = listBrands.concat(", ");
				}
				i++;
			}
			
		}
		return listBrands;
	}
	
	/**
	 * Prepara una consulta sql para recuperar las marcas
	 * @param cod_proveedor
	 * @return listado de marcas de ese proveedor
	 * @throws SQLException
	 */
	private PreparedStatement createPreparedStatement(Connection con, String cod_proveedor)
			throws SQLException {
		String sql = "select * from tbl_brandxprov"
				+ " where cod_proveedor = ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, cod_proveedor);
		
		return ps;
	}

}
