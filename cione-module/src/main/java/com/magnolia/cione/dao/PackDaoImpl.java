package com.magnolia.cione.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.PackInfoDTO;
import com.magnolia.cione.dto.VariantDTO;
import com.magnolia.cione.service.DetalleService;

public class PackDaoImpl implements PackDao{
	
	private static final Logger log = LoggerFactory.getLogger(PackDaoImpl.class);
	
	@Inject
	private DetalleService detalleService;

	public List<VariantDTO> getPackContent(VariantDTO variantPack){
		List<VariantDTO> contenidoPack = new ArrayList<VariantDTO>();
		Context initContext;
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/myshop");
			try (Connection con = ds.getConnection();PreparedStatement ps = createPreparedStatement(con, variantPack);ResultSet rs = ps.executeQuery()){
				//DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
				//con = ds.getConnection();
				//PreparedStatement ps = createPreparedStatement(con, variantPack);
				//ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					String sku = rs.getString("sku");
					VariantDTO infoPack = detalleService.getInfoVariant(sku, null);
					//VariantDTO infoPack = packDaoUtils.getInfoVariant(sku); --> recuperar de PackDaoUtils en caso de problemas de rendimiento
					infoPack.setSkuPack(sku);
					infoPack.setId_Pack(rs.getInt("idpack"));
					infoPack.setCodigoPack(rs.getString("skupack"));
					
					Double pvopackud = rs.getDouble("pvopackud");
					infoPack.setPvoPackUD(pvopackud.toString());
					infoPack.setUnidadesPack(rs.getInt("unidades"));
					infoPack.setCodigo_centralPack(rs.getString("codigocentral"));
					infoPack.setTipoProductoPack(rs.getString("tipoproducto"));
					if (infoPack.getFamiliaProducto().equals("contactologia") 
							|| infoPack.getFamiliaProducto().equals("audifonos")) {
						infoPack.setMasterPack(true);
						contenidoPack.add(contenidoPack.size(), infoPack);
					} else {
						infoPack.setTipoProductoPack("complemento-pack");
						contenidoPack.add(0, infoPack);
					}
					
				}
				con.close();
			} catch (SQLException e) {
				log.error("PackDaoImpl: Error al obtener el contenido del pack", e);
				
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		return contenidoPack;
	}
	
	@Override
	public List<PackInfoDTO> getPackInfoFromSku(String sku) {
		List<PackInfoDTO> contenidoPack = new ArrayList<PackInfoDTO>();
		Context initContext;
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/myshop");
			try (Connection con = ds.getConnection();PreparedStatement ps = createPreparedStatementSKU(con, sku);ResultSet rs = ps.executeQuery()){
				
				while (rs.next()) {
					PackInfoDTO infoPack = new PackInfoDTO();
					infoPack.setSkuPack(sku);
					infoPack.setId_Pack(rs.getInt("idpack"));
					infoPack.setCodigoPack(rs.getString("skupack"));
					
					Double pvopackud = rs.getDouble("pvopackud");
					infoPack.setPvoPackUD(pvopackud.toString());
					infoPack.setUnidadesPack(rs.getInt("unidades"));
					infoPack.setCodigo_centralPack(rs.getString("codigocentral"));
					infoPack.setTipoProductoPack(rs.getString("tipoproducto"));
					contenidoPack.add(infoPack);
				}
				con.close();
			} catch (SQLException e) {
				log.error("PackDaoImpl: Error al obtener el contenido del pack", e);
				
			}
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		return contenidoPack;
	}
	
	public List<String> getPacksByProduct(VariantDTO variant){
		List<String> packs = new ArrayList<String>();
		Context initContext;
		try {
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/myshop");
			try (Connection con = ds.getConnection();PreparedStatement ps = createPreparedStatementbySku(con, variant);ResultSet rs = ps.executeQuery()){
				while (rs.next()) {
					packs.add(rs.getString("skupack"));
				}
				con.close();
			} catch (SQLException e) {
				log.error("PackDaoImpl: Error al obtener los packs a los que pertenece el producto", e);
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		return packs;
	}
	
	/**
	 * Prepara una consulta sql para recuperar el contenido del pack
	 * @param VariantDTO variant con los datos de la variante informada
	 * @return String Sentencia para consulta con base de datos
	 * @throws SQLException
	 */
	private PreparedStatement createPreparedStatement(Connection con, VariantDTO variant)
			throws SQLException {
		String sql = "select * from dbo.cio_mys_packs"
				+ " where skupack = ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, variant.getCodigoPack());
		
		return ps;
	}
	
	/**
	 * Prepara una consulta sql para recuperar el contenido del pack
	 * @param String sku con el sku del producto
	 * @return String Sentencia para consulta con base de datos
	 * @throws SQLException
	 */
	private PreparedStatement createPreparedStatementSKU(Connection con, String sku)
			throws SQLException {
		String sql = "select * from dbo.cio_mys_packs"
				+ " where skupack = ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, sku);
		
		return ps;
	}
	
	/**
	 * Prepara una consulta sql para recuperar los packs que contiene un determinado producto
	 * @param VariantDTO variant con los datos de la variante informada
	 * @return String Sentencia para consulta con base de datos
	 * @throws SQLException
	 */
	private PreparedStatement createPreparedStatementbySku(Connection con, VariantDTO variant)
			throws SQLException {
		String sql = "select distinct skupack from dbo.cio_mys_packs"
				+ " where sku = ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, variant.getSku());
		
		return ps;
	}
	
	/*private String getPriceDto(ProductVariant variant, String sku, String grupoPrecio) {
		String pvo ="";
		try {
			VariantDTO variantResult = infoVariant(variant, grupoPrecio);
			if (variantResult.isTienePromociones() || variantResult.isTieneRecargo()) {
				pvo = MyShopUtils.formatTypeMoney(variant.getPrices().get(0).getValue());
				List<PromocionesDTO> listPromos = promocionesDao.getPromociones(variantResult);
				//escalado no aplica porque solo queremos el precio para 1 unidad, ver si ponemos precio desde
				if (listPromos.size() > 0) {
					PromocionesDTO promo = listPromos.get(0);
					double pvodto =0;
					if (promo.getTipo_descuento() == MyshopConstants.dtoPorcentaje) { // si es por porcentaje
						double dto= ((100-promo.getValor())/100);
						pvodto = Double.valueOf(variantResult.getPvo().replace(',', '.')) * dto;
					} else {
						pvodto = Double.valueOf(variantResult.getPvo().replace(',', '.')) - promo.getValor();
					}
					pvodto = MyShopUtils.round(pvodto, 2);
					pvo = String.valueOf(pvodto).replace('.', ',');
				}
			}
		} catch (NamingException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return pvo;
	}*/



}
