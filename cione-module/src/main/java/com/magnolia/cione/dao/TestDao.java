package com.magnolia.cione.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TestDao {
	
	public void test() {

		Context initContext;
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;

		try {
		    initContext = new InitialContext();
		    DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
		    cn = ds.getConnection();


		    String sqlBusqueda = "select id,name from tabla;";
		    st = cn.createStatement();
		    rs = st.executeQuery(sqlBusqueda);

		    while (rs.next()) {	    	
		    	System.out.println("id: " + rs.getString("id") + "");
		    	System.out.println("Nombre: " + rs.getString("name") + "");
		    }

		} catch (SQLException ex) {
		    //log.error("Error (" + ex.getErrorCode() + "): " + ex.getMessage());
		} catch (NamingException ex) {
		    //log.error("Error al intentar obtener el DataSource: " + ex.getMessage());
		}  finally {
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException ex) {
		            //log.error("Error (" + ex.getErrorCode() + "): " + ex.getMessage());
		        }
		    }

		    if (st != null) {
		        try {
		            st.close();
		        } catch (SQLException ex) {
		            //log.error("Error (" + ex.getErrorCode() + "): " + ex.getMessage());
		        }
		    }

		    if (cn != null) {
		        try {
		            cn.close();
		        } catch (SQLException ex) {
		            //log.error("Error (" + ex.getErrorCode() + "): " + ex.getMessage());
		        }
		    }
		}
	}
	
	public void testSQLServer() {
		Context initContext;
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/cioneLab");
			cn = ds.getConnection();
			
			
		    String sqlBusqueda = "select * from CioneLab.dbo.cio_precio_dto;";
		    st = cn.createStatement();
		    rs = st.executeQuery(sqlBusqueda);
	
		    while (rs.next()) {	    	
		    	System.out.println("id: " + rs.getString("id") + "");
		    	System.out.println("factor: " + rs.getString("factor") + "");
		    }
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
