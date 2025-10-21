package com.magnolia.cione.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.QueryResult;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.models.common.CentPrecisionMoney;
import com.commercetools.api.models.common.Image;
import com.commercetools.api.models.common.ImageBuilder;
import com.commercetools.api.models.common.ImageDimensions;
import com.commercetools.api.models.common.ImageDimensionsBuilder;
import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.common.Price;
import com.commercetools.api.models.product.ProductProjection;
import com.commercetools.api.models.product.ProductVariant;
import com.commercetools.api.models.product_type.AttributePlainEnumValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dao.PromocionesDao;
import com.magnolia.cione.dto.ColorDTO;
import com.magnolia.cione.dto.DetalleDTO;
import com.magnolia.cione.dto.PromocionesDTO;
import com.magnolia.cione.dto.VariantDTO;
import com.magnolia.cione.service.CommercetoolsService;
import com.magnolia.cione.service.CommercetoolsServiceAux;
import com.magnolia.cione.service.ContactLensService;

import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.PropertyUtil;

public class PackDaoUtils {
	
	private static final Logger log = LoggerFactory.getLogger(PackDaoUtils.class);
	
	@Inject
	private CommercetoolsService commerceToolsService;
	
	@Inject
	private CommercetoolsServiceAux commerceToolsServiceAux;
	
	@Inject
	private ContactLensService contactLensService;
	
	@Inject
	private PromocionesDao promocionesDao;
	
	private static final String DETALLES_WS = "detalles";
	private static final String DETALLES_NODETYPE = "mgnl:detalle";
	
	
	//funcion reducida con la info de los productos del pack
	public VariantDTO getInfoVariant(String sku) {
		
		VariantDTO variantResult = new VariantDTO();
		variantResult.setSku(sku);
		variantResult.setGruposPrecio(commerceToolsService.getGrupoPrecioCommerce());
		log.debug("GRUPO PRECIO = " + variantResult.getGruposPrecio());
		try {
			
			ProductProjection product = commerceToolsServiceAux.getProductBySkuFilter(sku, variantResult.getGruposPrecio());

			if (product != null)
				variantResult.setExist(true);
			else {
				//no existe o no tiene permisos para ver el artículo
				return variantResult;
			}
			
			//El listado solo debería devolver un unico registro
			
			ProductVariant vartiant = commerceToolsServiceAux.findVariantBySku(product, sku);
				
				variantResult.setListImages(vartiant.getImages());
				
				variantResult.setProductId(product.getId());
	    		variantResult.setVariantId(vartiant.getId().intValue());
				
	    		if (vartiant.getImages().size() > 0) {
	    			Image masterImagen=vartiant.getImages().get(0);
	    			variantResult.setMasterImage(masterImagen);
	    		} else {
	    			String url = CioneUtils.getURLHttps() + "/" + MyshopConstants.defaultProductLogo(MgnlContext.getLocale());
					ImageDimensions imageDimensions = ImageDimensionsBuilder.of().h(168).w(320).build();
	    			Image imagen = ImageBuilder.of().url(url).dimensions(imageDimensions).build();
	    			variantResult.setMasterImage(imagen);
	    		}
	    		
	    		if (MyShopUtils.hasAttribute("nombreArticulo", vartiant.getAttributes())) {
	    			variantResult.setNombreArticulo((String) MyShopUtils.findAttribute("nombreArticulo", vartiant.getAttributes()).getValue());
	    		} else {
	    			//variantResult.setNombreArticulo(product.getName().get(MgnlContext.getLocale()));
	    			variantResult.setNombreArticulo(product.getName().get(MyshopConstants.esLocale));
	    		}
	    		if (MyShopUtils.hasAttribute("paramsInDB", vartiant.getAttributes())) {
	    			variantResult.setConfig(Boolean.valueOf((String) MyShopUtils.findAttribute("paramsInDB", vartiant.getAttributes()).getValue()));
	    		}else {
	    			variantResult.setConfig(false);
	    		}
	    		if (MyShopUtils.hasAttribute("skuPadre", vartiant.getAttributes())) {
	    			variantResult.setSkuPadre((String) MyShopUtils.findAttribute("skuPadre", vartiant.getAttributes()).getValue());
	    		}
	    		if (MyShopUtils.hasAttribute("tienePromociones", vartiant.getAttributes())) {
	    			variantResult.setTienePromociones((boolean) MyShopUtils.findAttribute("tienePromociones", vartiant.getAttributes()).getValue());
	    			
	    			if (MyShopUtils.hasAttribute("tieneRecargo", vartiant.getAttributes())) 
	    			variantResult.setTieneRecargo((boolean) MyShopUtils.findAttribute("tieneRecargo", vartiant.getAttributes()).getValue());
	    		}

	    		if (MyShopUtils.hasAttribute("diseno", vartiant.getAttributes())) {
	    			ArrayList<String> disenojson = (ArrayList<String>) MyShopUtils.findAttribute("aliasEkon", vartiant.getAttributes()).getValue();
	    			//String disenostring = disenojson.toString().replace("\"", "").replace("[","").replace("]", "");
	    			//List<String> disenolist = new ArrayList<>(Arrays.asList(disenostring.split(",")));
	    			variantResult.setDiseno(disenojson);
	    		}
	    		if (MyShopUtils.hasAttribute("esfera1_from", vartiant.getAttributes()))
	    			variantResult.setEsferafrom((String) MyShopUtils.findAttribute("esfera1_from", vartiant.getAttributes()).getValue());

	    		if (MyShopUtils.hasAttribute("esfera1_to", vartiant.getAttributes()))
	    			variantResult.setEsferato((String) MyShopUtils.findAttribute("esfera1_to", vartiant.getAttributes()).getValue());

	    		if (MyShopUtils.hasAttribute("esfera1_step", vartiant.getAttributes()))
	    			variantResult.setEsferastep((String) MyShopUtils.findAttribute("esfera1_step", vartiant.getAttributes()).getValue());
	    		
	    		if (MyShopUtils.hasAttribute("esfera2_from", vartiant.getAttributes()))
	    			variantResult.setEsferafrom2((String) MyShopUtils.findAttribute("esfera2_from", vartiant.getAttributes()).getValue());
	    			
	    		if (MyShopUtils.hasAttribute("esfera2_to", vartiant.getAttributes()))
	    			variantResult.setEsferato2((String) MyShopUtils.findAttribute("esfera2_to", vartiant.getAttributes()).getValue());
	    			
	    		if (MyShopUtils.hasAttribute("esfera2_step", vartiant.getAttributes()))
	    			variantResult.setEsferastep2((String) MyShopUtils.findAttribute("esfera2_step", vartiant.getAttributes()).getValue());
	    		
	    		if (MyShopUtils.hasAttribute("diametro_from", vartiant.getAttributes()))
	    			variantResult.setDiametrofrom((String) MyShopUtils.findAttribute("diametro_from", vartiant.getAttributes()).getValue());
	    		
	    		if (MyShopUtils.hasAttribute("diametro_to", vartiant.getAttributes()))
	    			variantResult.setDiametroto((String) MyShopUtils.findAttribute("diametro_to", vartiant.getAttributes()).getValue());
	    		
	    		if (MyShopUtils.hasAttribute("diametro_step", vartiant.getAttributes()))
	    			variantResult.setDiametrostep((String) MyShopUtils.findAttribute("diametro_step", vartiant.getAttributes()).getValue());
	    			
	    		if (MyShopUtils.hasAttribute("curvaBase_from", vartiant.getAttributes()))
	    			variantResult.setCurvabasefrom((String) MyShopUtils.findAttribute("curvaBase_from", vartiant.getAttributes()).getValue());
	    			
	    		if (MyShopUtils.hasAttribute("curvaBase_to", vartiant.getAttributes()))
	    			variantResult.setCurvabaseto((String) MyShopUtils.findAttribute("curvaBase_to", vartiant.getAttributes()).getValue());
	    		
	    		if (MyShopUtils.hasAttribute("curvaBase_step", vartiant.getAttributes()))
	    			variantResult.setCurvabasestep((String) MyShopUtils.findAttribute("curvaBase_step", vartiant.getAttributes()).getValue());
	    		
	    		if (MyShopUtils.hasAttribute("cilindro_from", vartiant.getAttributes()))
	    			variantResult.setCilindrofrom((String) MyShopUtils.findAttribute("cilindro_from", vartiant.getAttributes()).getValue());
	    			
	    		if (MyShopUtils.hasAttribute("cilindro_to", vartiant.getAttributes()))
	    			variantResult.setCilindroto((String) MyShopUtils.findAttribute("cilindro_to", vartiant.getAttributes()).getValue());
	    		
	    		if (MyShopUtils.hasAttribute("cilindro_step", vartiant.getAttributes()))
	    			variantResult.setCilindrostep((String) MyShopUtils.findAttribute("cilindro_step", vartiant.getAttributes()).getValue());
	    			
	    		if (MyShopUtils.hasAttribute("eje_from", vartiant.getAttributes()))
	    			variantResult.setEjefrom((String) MyShopUtils.findAttribute("eje_from", vartiant.getAttributes()).getValue());
	    			
	    		if (MyShopUtils.hasAttribute("eje_to", vartiant.getAttributes()))
	    			variantResult.setEjeto((String) MyShopUtils.findAttribute("eje_to", vartiant.getAttributes()).getValue());
	    		
	    		if (MyShopUtils.hasAttribute("eje_step", vartiant.getAttributes()))
	    			variantResult.setEjestep((String) MyShopUtils.findAttribute("eje_step", vartiant.getAttributes()).getValue());
	    		
	    		if (MyShopUtils.hasAttribute("adicion", vartiant.getAttributes())) {
	    			ArrayList<String> addicionjson = (ArrayList<String>) MyShopUtils.findAttribute("adicion", vartiant.getAttributes()).getValue();
	    			//String addcionstring = addicionjson.toString().replace("\"", "").replace("[","").replace("]", "");
	    			//List<String> addicionlist = new ArrayList<>(Arrays.asList(addcionstring.split(",")));
	    			variantResult.setAdicion(addicionjson);
	    		}
	    		
	    		if (MyShopUtils.hasAttribute("tipoProducto", vartiant.getAttributes())) {
	    			variantResult.setTipoProducto(((LocalizedString) MyShopUtils.findAttribute("tipoProducto", vartiant.getAttributes()).getValue()).get(MyshopConstants.esLocale));
	    		}
	    		if (MyShopUtils.hasAttribute(MyshopConstants.PLANTILLA, vartiant.getAttributes())) {
	    			variantResult.setFamiliaProducto(((AttributePlainEnumValue) MyShopUtils.findAttribute(MyshopConstants.PLANTILLA, vartiant.getAttributes()).getValue()).getKey());
	    		} else {
	    			//este else sobra si todos los productos tiene contribuida la familia
	    			log.error("Plantilla no configurada para el producto " + vartiant.getSku() + " lo recuperamos del tipo de producto");
	    			variantResult.setFamiliaProducto(MyShopUtils.getFamiliaProducto(variantResult.getTipoProducto()));
	    		}
	    		
	    		//COLOR LENTE Y COLOR AUDIFONO
	    		if (MyShopUtils.hasAttribute("color", vartiant.getAttributes())) {
	    			ArrayList<String> colorjson = (ArrayList<String>) MyShopUtils.findAttribute("color", vartiant.getAttributes()).getValue();
	    			String colorstring = colorjson.toString().replace("\"", "").replace("[","").replace("]", "");
	    			List<String> colorlist = new ArrayList<>(Arrays.asList(colorstring.split(",")));
					colorlist.removeIf(n -> isBlank(n));
	    			
	    			Collections.sort(colorlist);
	    			if (!variantResult.getFamiliaProducto().equals("audifonos")) {
		    			variantResult.setColorlente(colorlist);
	    			} else {
	    				variantResult.setColoraudifonos(colorlist);
	    			}
	    		}
	    		
	    		if (MyShopUtils.hasAttribute("esfera1_mask", vartiant.getAttributes()))
	    			variantResult.setEsfera1mask((String) MyShopUtils.findAttribute("esfera1_mask", vartiant.getAttributes()).getValue());
	    		
	    		if (MyShopUtils.hasAttribute("esfera2_mask", vartiant.getAttributes()))
	    			variantResult.setEsfera2mask((String) MyShopUtils.findAttribute("esfera2_mask", vartiant.getAttributes()).getValue());

	    		if (MyShopUtils.hasAttribute("cilindro_mask", vartiant.getAttributes()))
	    			variantResult.setCilindromask((String) MyShopUtils.findAttribute("cilindro_mask", vartiant.getAttributes()).getValue());
	    		
	    		if (MyShopUtils.hasAttribute("eje_mask", vartiant.getAttributes()))
	    			variantResult.setEjemask((String) MyShopUtils.findAttribute("eje_mask", vartiant.getAttributes()).getValue());
	    		
	    		if (MyShopUtils.hasAttribute("diametro_mask", vartiant.getAttributes()))
	    			variantResult.setDiametromask((String) MyShopUtils.findAttribute("diametro_mask", vartiant.getAttributes()).getValue());
	    		
	    		if (MyShopUtils.hasAttribute("curvaBase_mask", vartiant.getAttributes()))
	    			variantResult.setCurvaBasemask((String) MyShopUtils.findAttribute("curvaBase_mask", vartiant.getAttributes()).getValue());

	    		if (MyShopUtils.hasAttribute("sku_mask", vartiant.getAttributes()))
	    			variantResult.setSkumask((String) MyShopUtils.findAttribute("sku_mask", vartiant.getAttributes()).getValue());
	    			
	    		if (MyShopUtils.hasAttribute("reemplazo", vartiant.getAttributes()))
	    			variantResult.setReemplazo((String) MyShopUtils.findAttribute("reemplazo", vartiant.getAttributes()).getValue());

	    		if (MyShopUtils.hasAttribute("geometria", vartiant.getAttributes()))
	    			variantResult.setGeometria((String) MyShopUtils.findAttribute("geometria", vartiant.getAttributes()).getValue());
	    		
	    		if (MyShopUtils.hasAttribute("formato", vartiant.getAttributes()))
	    			variantResult.setFormato((String) MyShopUtils.findAttribute("formato", vartiant.getAttributes()).getValue());
	    		
	    		if (MyShopUtils.hasAttribute("materialBase", vartiant.getAttributes()))
	    			variantResult.setMaterialBase((String) MyShopUtils.findAttribute("materialBase", vartiant.getAttributes()).getValue());

	    		if (MyShopUtils.hasAttribute("isContactLab", vartiant.getAttributes()))
	    			variantResult.setIsContactLab((Boolean) MyShopUtils.findAttribute("isContactLab", vartiant.getAttributes()).getValue());

	    		if (MyShopUtils.hasAttribute("materialDetalle", vartiant.getAttributes()))
	    			variantResult.setMaterialDetalle((String) MyShopUtils.findAttribute("materialDetalle", vartiant.getAttributes()).getValue());

	    		if (MyShopUtils.hasAttribute("contenidoAgua", vartiant.getAttributes()))
	    			variantResult.setContenidoAgua((String) MyShopUtils.findAttribute("contenidoAgua", vartiant.getAttributes()).getValue());		

	    		if (MyShopUtils.hasAttribute("hidratacion", vartiant.getAttributes()))
	    			variantResult.setHidratacion((String) MyShopUtils.findAttribute("hidratacion", vartiant.getAttributes()).getValue());

	    		if (MyShopUtils.hasAttribute("bproteccionSolar", vartiant.getAttributes()))
	    			variantResult.setBproteccionSolar((String) MyShopUtils.findAttribute("bproteccionSolar", vartiant.getAttributes()).getValue());

	    		if (MyShopUtils.hasAttribute("dkt", vartiant.getAttributes()))
	    			variantResult.setDkt((String) MyShopUtils.findAttribute("dkt", vartiant.getAttributes()).getValue());
	    		
	    		if (MyShopUtils.hasAttribute("equivProveedor", vartiant.getAttributes()))
	    			variantResult.setEquivProveedor((String) MyShopUtils.findAttribute("equivProveedor", vartiant.getAttributes()).getValue());
	    		
	    		if (MyShopUtils.hasAttribute("urlVideo", vartiant.getAttributes()))
	    			variantResult.setUrlVideo((String) MyShopUtils.findAttribute("urlVideo", vartiant.getAttributes()).getValue());
	    		if (MyShopUtils.hasAttribute("urlPdf", vartiant.getAttributes()))
	    			variantResult.setUrlPdf((String) MyShopUtils.findAttribute("urlPdf", vartiant.getAttributes()).getValue());
	    		if (MyShopUtils.hasAttribute("codigoCentral", vartiant.getAttributes()))
	    			variantResult.setCodigoCentral((String) MyShopUtils.findAttribute("codigoCentral", vartiant.getAttributes()).getValue());
	    		
	    		if (variantResult.getFamiliaProducto() != null) {
	    			if (variantResult.getFamiliaProducto().equals("monturas")){
	    				variantResult.setColors(getListColorsInfo(product, vartiant));
	    			} else if (variantResult.getFamiliaProducto().equals("liquidos")){
	    				variantResult.setTamaniosLiquidos(getListTamaniosLiquidos(product, vartiant));
	    			}
	    		}
	    		
					//List<Price> prices = vartiant.getPrices();
	    			
					variantResult.setTipoPromocion("sin-promo");
					Price price = commerceToolsService.getPriceBycustomerGroup(variantResult.getGruposPrecio(), vartiant.getPrices());
		    		//for (Price price : prices) {
	    			
	    				//del listado de precios nos quedamos con el señalado con Any
	    				//price to Money
    				variantResult.setPvo(MyShopUtils.formatTypedMoney(price.getValue()));
    				if (variantResult.isTienePromociones() || variantResult.isTieneRecargo()) {
    					String pvoOrigin = MyShopUtils.formatTypedMoney(price.getValue());
    					List<PromocionesDTO> listPromos = promocionesDao.getPromociones(variantResult);
    					List<PromocionesDTO> listPromosAux = new ArrayList<PromocionesDTO>();
    					if (listPromos.size() > 1) {
    						//aplicamos escalado
    						variantResult.setPvoDto(variantResult.getPvo());
    						variantResult.setTipoPromocion("escalado");
    						int i =0;
    						for (PromocionesDTO promo: listPromos) {
    							promo.setId(i);
    							i++;
    							double pvodto =0;
    							if (promo.getTipo_descuento() == MyshopConstants.dtoPorcentaje) {
    								double dto= ((100-promo.getValor())/100);
	    							pvodto = Double.valueOf(variantResult.getPvo().replace(',', '.')) * dto;
	    							variantResult.setValorDescuento(String.valueOf(promo.getValor()).replace('.', ','));
	    						} else {
	    							pvodto = Double.valueOf(variantResult.getPvo().replace(',', '.')) - promo.getValor();
	    							variantResult.setValorDescuento(String.valueOf(promo.getValor()).replace('.', ','));
	    						}
    							promo.setPvo(pvoOrigin);
    							pvodto = MyShopUtils.round(pvodto, 2);
    							promo.setPvoDto(String.valueOf(pvodto).replace('.', ','));
    							listPromosAux.add(promo);
    							
    							if (promo.getCantidad_desde()==1) {
    								variantResult.setPvoDto(promo.getPvoDto());
    							}
    						}
    						variantResult.setListPromos(listPromosAux);
    					} else if (listPromos.size() == 1) {
    						//si devuelve un unico resultado solo hay una promo
	    						PromocionesDTO promo = listPromos.get(0);
	    						double pvodto =0;
	    						if (promo.getTipo_descuento() == MyshopConstants.dtoPorcentaje) { // si es por porcentaje
	    							variantResult.setTipoPromocion("porcentaje");
	    							double dto= ((100-promo.getValor())/100);
	    							pvodto = Double.valueOf(variantResult.getPvo().replace(',', '.')) * dto;
	    							variantResult.setValorDescuento(String.valueOf(promo.getValor()).replace('.', ','));
	    						} else {
	    							variantResult.setTipoPromocion("valor");
	    							pvodto = Double.valueOf(variantResult.getPvo().replace(',', '.')) - promo.getValor();
	    							variantResult.setValorDescuento(String.valueOf(promo.getValor()).replace('.', ','));
	    						}
	    						pvodto = MyShopUtils.round(pvodto, 2);
	    						variantResult.setPvoDto(String.valueOf(pvodto).replace('.', ','));
    							if (promo.getValor() < 0) { //es un incremento, no lo marcamos como promocion pero actualizamos su pvo con el incremento
    								variantResult.setPvo(String.valueOf(pvodto).replace('.', ','));
    								variantResult.setTienePromociones(false);
    								variantResult.setPvoIncremento(variantResult.getPvo());
    							} 
    					} else { //si no hay promociones para ese grupo de precio 
    						variantResult.setTienePromociones(false);
    					}
    					
    				}
//	    			} else {
//	    				/*Comentada la logica en caso de que metan precios por grupo de usuarios 
//	    				String customerid = commercetoolsService.getIdOfCustomerGroupByCostumerId(CioneUtils.getIdCurrentClient());
//	    				if (price.getCustomerGroup().getId().equals(customerid)) {
//	    					variantResult.setPvo(MyShopUtils.formatTypedMoney(price.getValue()));
//	    				}/*/
//	    				variantResult.setPvo(MyShopUtils.formatTypedMoney(price.getValue()));
//	    			}
		    		//}
	    		
				if (MyShopUtils.hasAttribute("pvpRecomendado", vartiant.getAttributes())) {
					CentPrecisionMoney money = (CentPrecisionMoney) MyShopUtils.findAttribute("pvpRecomendado", vartiant.getAttributes()).getValue();
					String pvp = MyShopUtils.formatTypedMoney(money);
		    		variantResult.setPvpRecomendado(pvp);
	    		}
	    		
				if (MyShopUtils.hasAttribute("skuPadre", vartiant.getAttributes()))
					variantResult.setSkuPadre((String) MyShopUtils.findAttribute("skuPadre", vartiant.getAttributes()).getValue());
	    		
	    		if (MyShopUtils.hasAttribute("plazoEntregaProveedor", vartiant.getAttributes()))
					variantResult.setPlazoEntregaProveedor(((Long) MyShopUtils.findAttribute("plazoEntregaProveedor", vartiant.getAttributes()).getValue()).intValue());
	    		else
	    			variantResult.setPlazoEntregaProveedor(2);
	    		
	    		if (product.getDescription() != null)
	    			variantResult.setDescripcion(product.getDescription().get(MyshopConstants.esLocale));
	    		
	    		if (MyShopUtils.hasAttribute("target", vartiant.getAttributes()))
					variantResult.setTarget(((LocalizedString) MyShopUtils.findAttribute("target", vartiant.getAttributes()).getValue()).get(MyshopConstants.esLocale));
	    		
	    		if (MyShopUtils.hasAttribute("tipoMontaje", vartiant.getAttributes()))
					variantResult.setTipoMontaje(((LocalizedString) MyShopUtils.findAttribute("tipoMontaje", vartiant.getAttributes()).getValue()).get(MyshopConstants.esLocale));
	    		
	    		if (MyShopUtils.hasAttribute("material", vartiant.getAttributes()))
					variantResult.setMaterial((String) MyShopUtils.findAttribute("material", vartiant.getAttributes()).getValue());
	    		
	    		//PROBAR BIEN!! antes lo recuperaba como tipo String
	    		if (MyShopUtils.hasAttribute("dimensiones_ancho_ojo", vartiant.getAttributes()))
					variantResult.setDimensiones_ancho_ojo(String.valueOf((Integer) MyShopUtils.findAttribute("dimensiones_ancho_ojo", vartiant.getAttributes()).getValue()));
	    		
	    		if (MyShopUtils.hasAttribute("dimensiones_largo_varilla", vartiant.getAttributes()))
					variantResult.setDimensiones_largo_varilla((String) MyShopUtils.findAttribute("dimensiones_largo_varilla", vartiant.getAttributes()).getValue());
	    		
	    		if (MyShopUtils.hasAttribute("dimensiones_ancho_puente", vartiant.getAttributes()))
					variantResult.setDimensiones_ancho_puente((String) MyShopUtils.findAttribute("dimensiones_ancho_puente", vartiant.getAttributes()).getValue());
	    		
	    		if (MyShopUtils.hasAttribute("mensajesEspecificos", vartiant.getAttributes())) {
	    			ArrayList<String> mensajes = (ArrayList<String>) MyShopUtils.findAttribute("mensajesEspecificos", vartiant.getAttributes()).getValue();
					variantResult.setMensajesEspecificos(getSpecificMessages(mensajes));
				}
	    		
	    		if (variantResult.getFamiliaProducto().equals("contactologia")) {
		    		if (variantResult.getConfig()) {
		    			variantResult.setDisenoListConfig(getDisenoFromDB(variantResult));
		    		
			    		if ((variantResult.getDisenoListConfig()== null) || (variantResult.getDisenoListConfig().isEmpty())) {
				    		if (isSpheresFromDB(variantResult)) {
				    			variantResult.setEsferaListConfig(getSpheresFromDB(variantResult));
				    			if (variantResult.getEsferaListConfig().size() <= 1) {
				    				String esfera = null;
				    				if (variantResult.getEsferaListConfig().size() == 1)
				    					esfera = variantResult.getEsferaListConfig().get(0);
				    				variantResult.setCilindroListConfig(contactLensService.getCylinders(variantResult.getCodigoCentral(), null, esfera));
				    				if (variantResult.getCilindroListConfig().size() <= 1) {
					    				String cilindro = null;
					    				if (variantResult.getCilindroListConfig().size() == 1)
					    					cilindro = variantResult.getCilindroListConfig().get(0);
					    				variantResult.setEjeListConfig(contactLensService.getAxis(variantResult.getCodigoCentral(), null, esfera, cilindro));
					    				if (variantResult.getEjeListConfig().size() <= 1) {
						    				String eje = null;
						    				if (variantResult.getEjeListConfig().size() == 1)
						    					eje = variantResult.getEjeListConfig().get(0);
						    				variantResult.setDiametroListConfig(contactLensService.getDiameters(variantResult.getCodigoCentral(), null, esfera, cilindro, eje));
						    				if (variantResult.getDiametroListConfig().size() <= 1) {
							    				String diametro = null;
							    				if (variantResult.getDiametroListConfig().size() == 1)
							    					diametro = variantResult.getDiametroListConfig().get(0);
							    				variantResult.setCurvaBaseListConfig(contactLensService.getRadios(variantResult.getCodigoCentral(), null, esfera, cilindro, eje, diametro));
							    				if (variantResult.getCurvaBaseListConfig().size() <= 1) {
								    				String radio = null;
								    				if (variantResult.getCurvaBaseListConfig().size() == 1)
								    					radio = variantResult.getCurvaBaseListConfig().get(0);
								    				variantResult.setAdicionListConfig(contactLensService.getAdditions(variantResult.getCodigoCentral(), null, esfera, cilindro, eje, diametro, radio));
								    				if (variantResult.getAdicionListConfig().size() <= 1) {
									    				String adicion = null;
									    				if (variantResult.getAdicionListConfig().size() == 1)
									    					adicion = variantResult.getAdicionListConfig().get(0);
									    				variantResult.setColorlente(contactLensService.getColors(variantResult.getCodigoCentral(), null, esfera, cilindro, eje, diametro, radio, adicion));
									    				
								    				}
							    				}
						    				}
					    				}
				    				}
				    			}
				    		}
			    		}
		    		}
	    		}
	    		
	    		/*if (variantResult.isRepuesto()) {
	    			variantResult.setSubstitutiveReplacement(commerceToolsService.getSustituveReplacement(vartiant));
	    		}*/
			
			
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		
		/*QueryPredicate<ProductProjection> predicate = ProductProjectionQueryModel.of().allVariants().where(m -> m.sku().is(sku));
		Query<ProductProjection> query = ProductProjectionQuery.ofCurrent().withPredicates(predicate);
		CompletionStage<PagedQueryResult<ProductProjection>> product_query = cioneEcommerceConectionProvider.getClient().execute(query);
		PagedQueryResult<ProductProjection> queryresult = product_query.toCompletableFuture().join();
		List<ProductProjection> productos = queryresult.getResults();*/
		
		return variantResult;
	}
	
	private boolean isBlank(String color) {
		boolean result = false;
		for (int i = 0; i < 1; i++) {
			char value = color.charAt(i);
			if (value ==' ') {
				result = true;
			}
            if ((Character.isWhitespace(value))) {
            	result = true;
            }
        }
		return result;
	}
	
	/*
	 * Recorre todas las variantes para obtener el listado de colores a mostrar en la ficha y almacenar 
	 * en un listado los colores y variantes de calibre y graduacion agrupados
	 * Si la variante seleccionada no tiene el codigoColor o el colorIcono no se almacena ya que 
	 * no nos permitiria cambiar de variante en el front
	 * */
	private List<ColorDTO> getListColorsInfo(ProductProjection product, ProductVariant variantSel ) {
		List<ColorDTO> colorsDtoAux = new ArrayList<ColorDTO>();
		List<ColorDTO> colorsDto = new ArrayList<ColorDTO>();
		List<ProductVariant> listAllVariants = commerceToolsServiceAux.getAllVariants(product);//product.getAllVariants();
		List<String> colorsAux = new ArrayList<String>();
		String colorIconoSel = "#ZZZZZZ";
		
		if (MyShopUtils.hasAttribute("colorIcono", variantSel.getAttributes()))
			colorIconoSel = (String) MyShopUtils.findAttribute("colorIcono", variantSel.getAttributes()).getValue();
//		if (variantSel.getAttribute("colorIcono") != null)
//			colorIconoSel = variantSel.getAttribute("colorIcono").getValueAsString();
		String codigoColorSel = "00";
		if (MyShopUtils.hasAttribute("codigoColor", variantSel.getAttributes()))
			codigoColorSel = (String) MyShopUtils.findAttribute("codigoColor", variantSel.getAttributes()).getValue();
//		if (variantSel.getAttribute("codigoColor") != null)
//			codigoColorSel = variantSel.getAttribute("codigoColor").getValueAsString();
		
		String colorSel = codigoColorSel + colorIconoSel; //color de la variante seleccionada
	
		Map <String, List<ColorDTO>> colors_calibres = new HashMap <String, List<ColorDTO>> ();
		Map <String, List<ColorDTO>> colors_graduaciones = new HashMap <String, List<ColorDTO>> ();
		
		for (ProductVariant variant : listAllVariants) {
			//if ((variant.getAttribute("colorIcono") != null) && variant.getAttribute("codigoColor") != null) {
			String colorIcono = "#ZZZZZZ";
			if (MyShopUtils.hasAttribute("colorIcono", variant.getAttributes()))
				colorIcono = (String) MyShopUtils.findAttribute("colorIcono", variant.getAttributes()).getValue();
//			if (variant.getAttribute("colorIcono") != null)
//				colorIcono = variant.getAttribute("colorIcono").getValueAsString();
			String codigoColor = "00";
			if (MyShopUtils.hasAttribute("codigoColor", variant.getAttributes()))
				codigoColor = (String) MyShopUtils.findAttribute("codigoColor", variant.getAttributes()).getValue();
//			if (variant.getAttribute("codigoColor") != null)
//				codigoColor=variant.getAttribute("codigoColor").getValueAsString();
			//las distintas variantes de color que tienen que aparecer nos solo son por colorIcono sino tambien por codigo de color
			String idColor = codigoColor+colorIcono;
    			if (!colorsAux.contains(idColor)) {
    				
    				ColorDTO colorDto = new ColorDTO();
    				colorDto.setCodigoColor(codigoColor);
    				if (MyShopUtils.hasAttribute("colorMontura", variant.getAttributes()))
    					colorDto.setColorMontura(((LocalizedString) MyShopUtils.findAttribute("colorMontura", variant.getAttributes()).getValue()).get(MyshopConstants.esLocale));
//    				if (variant.getAttribute("colorMontura") != null)
//    					colorDto.setColorMontura(variant.getAttribute("colorMontura").getValueAsLocalizedString().get(MyshopConstants.esLocale));
    				colorDto.setIdColor(idColor);
    				colorDto.setColorIcono(colorIcono);
    				if (idColor.equals(colorSel))
    					colorDto.setSelected(true);
    				colorDto.setSku(variant.getSku());
    				colorsDtoAux.add(colorDto);
    				
    				colorsAux.add(idColor);
    				
    				List<ColorDTO> calibres = new ArrayList<ColorDTO>();
    				if (MyShopUtils.hasAttribute("dimensiones_ancho_ojo", variant.getAttributes())
    					&& !((String) MyShopUtils.findAttribute("dimensiones_ancho_ojo", variant.getAttributes()).getValue()).isEmpty()) {
    				
//    				if (variant.getAttribute("dimensiones_ancho_ojo")!=null 
//							&&  !variant.getAttribute("dimensiones_ancho_ojo").getValueAsString().isEmpty()){
    					
						String calibre = (String) MyShopUtils.findAttribute("dimensiones_ancho_ojo", variant.getAttributes()).getValue();
	    				ColorDTO infoCalibre = new ColorDTO();
	    				infoCalibre.setCalibre(calibre);
	    				infoCalibre.setColorIcono(colorIcono);
	    				infoCalibre.setCodigoColor(codigoColor);
	    				infoCalibre.setIdColor(idColor);
	    				if (MyShopUtils.hasAttribute("colorMontura", variant.getAttributes()))
	    					infoCalibre.setColorMontura(((LocalizedString) MyShopUtils.findAttribute("colorMontura", variant.getAttributes()).getValue()).get(MyshopConstants.esLocale));
	    				//if (variant.getAttribute("colorMontura") != null)
	    					//infoCalibre.setColorMontura(variant.getAttribute("colorMontura").getValueAsLocalizedString().get(MyshopConstants.esLocale));
	    				infoCalibre.setSku(variant.getSku());
	    				if (variant.getSku().equals(variantSel.getSku())) {
	    					infoCalibre.setSelected(true);
	    				} 
	    				calibres.add(infoCalibre);
	    				colors_calibres.put(idColor, calibres);
	    			}
    				
    				List<ColorDTO> graduaciones = new ArrayList<ColorDTO>();
    				if (MyShopUtils.hasAttribute("graduacion", variant.getAttributes()) 
        					&& !((String) MyShopUtils.findAttribute("graduacion", variant.getAttributes()).getValue()).isEmpty()) {
//    				if (variant.getAttribute("graduacion") != null
//    						&&  !variant.getAttribute("graduacion").getValueAsString().isEmpty()){
	    				String graduacion = (String) MyShopUtils.findAttribute("graduacion", variant.getAttributes()).getValue();//variant.getAttribute("graduacion").getValueAsString();
	    				ColorDTO infoGraduacion = new ColorDTO();
	    				infoGraduacion.setGraduacion(graduacion);
	    				infoGraduacion.setColorIcono(colorIcono);
	    				infoGraduacion.setCodigoColor(codigoColor);
	    				infoGraduacion.setIdColor(idColor);
	    				if (MyShopUtils.hasAttribute("colorMontura", variant.getAttributes()))
	    					infoGraduacion.setColorMontura(((LocalizedString) MyShopUtils.findAttribute("colorMontura", variant.getAttributes()).getValue()).get(MyshopConstants.esLocale));
//	    				if (variant.getAttribute("colorMontura") != null)
//	    					infoGraduacion.setColorMontura(variant.getAttribute("colorMontura").getValueAsLocalizedString().get(MyshopConstants.esLocale));
	    				infoGraduacion.setSku(variant.getSku());
	    				if (variant.getSku().equals(variantSel.getSku())) {
	    					infoGraduacion.setSelected(true);
	    				} 
	    				graduaciones.add(infoGraduacion);
	    				colors_graduaciones.put(idColor, graduaciones);
	    			}
    			} else {
    				//si ya existe añadimos solo el calibre en caso de existir
    				List<ColorDTO> calibres = colors_calibres.get(idColor);
    				if (MyShopUtils.hasAttribute("dimensiones_ancho_ojo", variant.getAttributes())
        					&& !((String) MyShopUtils.findAttribute("dimensiones_ancho_ojo", variant.getAttributes()).getValue()).isEmpty()) {
//    				if (variant.getAttribute("dimensiones_ancho_ojo")!=null 
//							&&  !variant.getAttribute("dimensiones_ancho_ojo").getValueAsString().isEmpty()){
	    				//String calibre = variant.getAttribute("dimensiones_ancho_ojo").getValueAsString();
    					String calibre = (String) MyShopUtils.findAttribute("dimensiones_ancho_ojo", variant.getAttributes()).getValue();
    					ColorDTO infoCalibre = new ColorDTO();
	    				infoCalibre.setCalibre(calibre);
	    				infoCalibre.setColorIcono(colorIcono);
	    				infoCalibre.setCodigoColor(codigoColor);
	    				infoCalibre.setIdColor(idColor);
	    				if (MyShopUtils.hasAttribute("colorMontura", variant.getAttributes()))
	    					infoCalibre.setColorMontura(((LocalizedString) MyShopUtils.findAttribute("colorMontura", variant.getAttributes()).getValue()).get(MyshopConstants.esLocale));
//	    				if (variant.getAttribute("colorMontura") != null)
//	    					infoCalibre.setColorMontura(variant.getAttribute("colorMontura").getValueAsLocalizedString().get(MyshopConstants.esLocale));
	    				infoCalibre.setSku(variant.getSku());
	    				if (variant.getSku().equals(variantSel.getSku())) {
	    					infoCalibre.setSelected(true);
	    				} 
	    				if (!localizeCaliber(calibres, infoCalibre)) {
	    					calibres.add(infoCalibre);
		    				colors_calibres.put(idColor, calibres);
	    				}
	    			}
    				//si ya existe añadimos solo la graduacion en caso de existir
    				List<ColorDTO> graduaciones = colors_graduaciones.get(idColor);
    				if (MyShopUtils.hasAttribute("graduacion", variant.getAttributes()) 
        					&& !((String) MyShopUtils.findAttribute("graduacion", variant.getAttributes()).getValue()).isEmpty()) {
//    				if (variant.getAttribute("graduacion") != null
//    						&&  !variant.getAttribute("graduacion").getValueAsString().isEmpty()){
    					String graduacion = (String) MyShopUtils.findAttribute("graduacion", variant.getAttributes()).getValue(); //variant.getAttribute("graduacion").getValueAsString();
	    				ColorDTO infoGraduacion = new ColorDTO();
	    				infoGraduacion.setGraduacion(graduacion);
	    				infoGraduacion.setColorIcono(colorIcono);
	    				infoGraduacion.setCodigoColor(codigoColor);
	    				infoGraduacion.setIdColor(idColor);
	    				if (MyShopUtils.hasAttribute("colorMontura", variant.getAttributes()))
	    					infoGraduacion.setColorMontura(((LocalizedString) MyShopUtils.findAttribute("colorMontura", variant.getAttributes()).getValue()).get(MyshopConstants.esLocale));
//	    				if (variant.getAttribute("colorMontura") != null)
//	    					infoGraduacion.setColorMontura(variant.getAttribute("colorMontura").getValueAsLocalizedString().get(MyshopConstants.esLocale));
	    				infoGraduacion.setSku(variant.getSku());
	    				if (variant.getSku().equals(variantSel.getSku())) {
	    					infoGraduacion.setSelected(true);
	    				} 
	    				graduaciones.add(infoGraduacion);
	    				colors_graduaciones.put(idColor, graduaciones);
	    			}
				}
			} 
			
		
			for (ColorDTO color : colorsDtoAux) {
				//color.setCalibres(colors_calibres.get(color.getColorIcono()));
				//color.setGraduaciones(colors_graduaciones.get(color.getColorIcono()));
				color.setCalibres(colors_calibres.get(color.getIdColor()));
				color.setGraduaciones(colors_graduaciones.get(color.getIdColor()));
				
					colorsDto.add(color);
			}
		
		return colorsDto;  
	}
	
	/*
	 * Recorre todas las variantes para obtener el listado de tamaños para las soluciones de mantenimiento
	 */
	private List<ColorDTO> getListTamaniosLiquidos(ProductProjection product, ProductVariant variantSel ) {
		List<ColorDTO> tamanios = new ArrayList<ColorDTO>();
		List<ProductVariant> listAllVariants = commerceToolsServiceAux.getAllVariants(product); //product.getAllVariants();
		String tamanioSelect ="";
		
		if (MyShopUtils.hasAttribute("tamanios", variantSel.getAttributes()))
			tamanioSelect = ((LocalizedString) MyShopUtils.findAttribute("tamanios", variantSel.getAttributes()).getValue()).get(MyshopConstants.esLocale);
		//if (variantSel.getAttribute("tamanios") != null)
			//tamanioSelect = variantSel.getAttribute("tamanios").getValueAsLocalizedString().get(MyshopConstants.esLocale);
		for (ProductVariant variant : listAllVariants) {
			ColorDTO colorDto = new ColorDTO();
			colorDto.setSku(variant.getSku());
			if (MyShopUtils.hasAttribute("tamanios", variant.getAttributes())) {
				colorDto.setTamanio(((LocalizedString) MyShopUtils.findAttribute("tamanios", variant.getAttributes()).getValue()).get(MyshopConstants.esLocale));
			//if (variant.getAttribute("tamanios") != null) {
				//colorDto.setTamanio(variant.getAttribute("tamanios").getValueAsLocalizedString().get(MyshopConstants.esLocale));
				if (tamanioSelect.equals(colorDto.getTamanio())){
					colorDto.setSelected(true);
				}
				
				tamanios.add(colorDto);
			}
			
		}
		
		return tamanios;
	}
	
	private boolean localizeCaliber(List<ColorDTO> colors, ColorDTO infoCalibre) {
		for (ColorDTO color : colors) {
			if (color.getCalibre().equals(infoCalibre.getCalibre())) {
				return true;
			}
		}
		return false;
	}
	
	private List<DetalleDTO> getSpecificMessages(ArrayList<String> idMesages) {
		List<DetalleDTO> listSpecificsMessageMgnl = new ArrayList<DetalleDTO>();
		try {
//			session = MgnlContext.getJCRSession(DETALLES_WS);
//			Node node = session.getNode("/");
//			Iterable<Node> childrenIterable = NodeUtil.getNodes(node, DETALLES_NODETYPE);//				
//			List<Node> childrenList = NodeUtil.asList(childrenIterable);
			
			
			for (String id : idMesages) {
				String query = "select * from [" + DETALLES_NODETYPE + "] where id ='" + id + "'";
				Session session = MgnlContext.getJCRSession (DETALLES_WS);
				javax.jcr.query.Query q = session.getWorkspace().getQueryManager().createQuery(query, javax.jcr.query.Query.JCR_SQL2);
				QueryResult queryResult = q.execute();
				NodeIterator nodes = queryResult.getNodes();
				while (nodes.hasNext()) {
				    Node node = nodes.nextNode();
				    DetalleDTO detalleDto = new DetalleDTO();
				    //ver locale para recuperarlo
				    String idDetalle = PropertyUtil.getString(node, "id");
				    String title = PropertyUtil.getString(node, "title");
				    String descripcion = PropertyUtil.getString(node, "descripcion");
				    detalleDto.setId(idDetalle);
				    detalleDto.setTitle(title);
				    detalleDto.setDescripcion(descripcion);
				    listSpecificsMessageMgnl.add(detalleDto);
				}
			}
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return listSpecificsMessageMgnl;
	}
	
	private List<String> getDisenoFromDB(VariantDTO variant) {
		
		List<String> disenos = new ArrayList<>();
		Context initContext;
		
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/cioneLab");
			
			try (Connection con = ds.getConnection();PreparedStatement ps = createPSDiseno(con, variant.getCodigoCentral());ResultSet rs = ps.executeQuery()){
				
				while (rs.next()) {
					if(!rs.getString(1).trim().equals("")) {
						disenos.add(rs.getString(1).trim());
					}
		        }
				con.close();
			} catch (SQLException e) {
				log.error("DetalleServiceImpl: Error al obtener la diseno de lentillas en la BBDD", e);
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		return disenos.isEmpty() ? null : disenos;
	}
	
	private List<String> getSpheresFromDB(VariantDTO variant) {
		
		List<Integer> spheres = new ArrayList<>();
		Context initContext;
		boolean zeronegative = false;
		try {
			
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/cioneLab");
			
			try (Connection con = ds.getConnection();PreparedStatement ps = createPSSpheres(con, variant.getCodigoCentral());ResultSet rs = ps.executeQuery()){
				
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
				
				con.close();
			} catch (SQLException e) {
				log.error("DetalleServiceImpl: Error al obtener los esferas de lentillas en la BBDD", e);
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		List<Integer> positives = spheres.stream().filter(i -> i >= 0).collect(Collectors.toList());
		List<Integer> negatives = spheres.stream().filter(i -> i <= 0).collect(Collectors.toList());
		
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
		List<String> listResult = uniqueJoinList.stream().map(n -> String.format("%+05d", n)).collect(Collectors.toList());
		if (zeronegative) {
			Collections.replaceAll(listResult, "+0000","-0000");
		}
		return listResult;
	}
	
	private PreparedStatement createPSSpheres(Connection con, String codigocentral) throws SQLException {
		
		String sql = "SELECT DISTINCT xesfera FROM dbo.cio_mys_lentesnoconfigurables " + 
					 "WHERE xcodigocentral = '" + codigocentral + "' AND xesfera != '' ORDER BY xesfera DESC;";

		return con.prepareStatement(sql);
	}
	
	private boolean isSpheresFromDB(VariantDTO variant) {
		
		boolean res = false;
		
		// Pag. 4 - Tratamiento de lentes de contacto no configurables.docx
		if (variant.getConfig()) {
			if (!StringUtils.isEmpty(variant.getEsfera1mask()) &&
				 StringUtils.isEmpty(variant.getEsferafrom()) &&
				 StringUtils.isEmpty(variant.getEsferato()) &&
				 StringUtils.isEmpty(variant.getEsferastep())) {
				res = true;
			}
		}
		
		return res;
	}
	
	private PreparedStatement createPSDiseno(Connection con, String codigocentral) throws SQLException {
		
		String sql = "SELECT DISTINCT xtipodepieza FROM dbo.cio_mys_lentesnoconfigurables " + 
					 "WHERE xcodigocentral = '" + codigocentral + "' AND xtipodepieza != '' ORDER BY xtipodepieza DESC;";

		return con.prepareStatement(sql);
	}
}
