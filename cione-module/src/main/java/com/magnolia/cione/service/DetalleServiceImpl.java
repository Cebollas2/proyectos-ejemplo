package com.magnolia.cione.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.QueryResult;
//import javax.money.MonetaryAmount;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CustomLineItem;
import com.commercetools.api.models.cart.LineItem;
import com.commercetools.api.models.category.Category;
import com.commercetools.api.models.category.CategoryReference;
import com.commercetools.api.models.common.CentPrecisionMoney;
import com.commercetools.api.models.common.Image;
import com.commercetools.api.models.common.ImageBuilder;
import com.commercetools.api.models.common.ImageDimensions;
import com.commercetools.api.models.common.ImageDimensionsBuilder;
import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.common.Price;
import com.commercetools.api.models.common.TypedMoney;
import com.commercetools.api.models.product.ProductProjection;
import com.commercetools.api.models.product.ProductVariant;
import com.commercetools.api.models.product_type.AttributeDefinition;
import com.commercetools.api.models.product_type.AttributeEnumType;
import com.commercetools.api.models.product_type.AttributePlainEnumValue;
import com.commercetools.api.models.product_type.ProductType;
import com.magnolia.cione.beans.GiftProduct;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dao.PackDao;
import com.magnolia.cione.dao.PromocionesDao;
import com.magnolia.cione.dto.AgrupadorDTO;
import com.magnolia.cione.dto.ColorDTO;
import com.magnolia.cione.dto.DetalleDTO;
import com.magnolia.cione.dto.InfoPackGenericoDTO;
import com.magnolia.cione.dto.PromocionesDTO;
import com.magnolia.cione.dto.VariantDTO;
import com.magnolia.cione.setup.CategoryUtils;
import com.magnolia.cione.setup.CioneEcommerceConectionProvider;
import com.magnolia.cione.utils.CioneUtils;
import com.magnolia.cione.utils.MyShopUtils;

import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.PropertyUtil;

public class DetalleServiceImpl implements DetalleService{
	private static final Logger log = LoggerFactory.getLogger(DetalleServiceImpl.class);
	
	@Inject
	private MiddlewareService middlewareService;
	
	@Inject
	private CommercetoolsService commerceToolsService;
	
	@Inject
	private CommercetoolsServiceAux commerceToolsServiceAux;
	
	@Inject
	private CategoryService categoryService;
	
	@Inject
	private PromocionesDao promocionesDao;
	
	@Inject
	private ContactLensService contactLensService;
	
	@Inject
	private CioneEcommerceConectionProvider cioneEcommerceConectionProvider;
	
	@Inject
	private CartService cartService;
	
	@Inject
	private PackDao packDao;
	
	@Inject
	private CategoryUtils categoryUtils;
	
	private static final String DETALLES_WS = "detalles";
	private static final String DETALLES_NODETYPE = "mgnl:detalle";

	public List<Category> getMarcas(String name) {
        //String idMarcas = commerceToolsService.getCategoryByName(MyshopConstants.esLocale, name).getId();
		String idMarcas = categoryService.getCategoryIdByName(MyshopConstants.esLocale, name);
        String flag = cioneEcommerceConectionProvider.getFlag();
		System.out.print(flag);
        
		List<Category> marcas = 
				cioneEcommerceConectionProvider
				.getApiRoot()
				.categories()
				.get()
				.withWhere("parent(id=\"" + idMarcas + "\")")
				.withSort("orderHint asc")
				.executeBlocking()
				.getBody()
				.getResults();
      
        return marcas;
	}
	
	public List<String> getIdCategories(String name) {
        String idMarcas = categoryService.getCategoryIdByName(MyshopConstants.esLocale, name);
        
        String flag = cioneEcommerceConectionProvider.getFlag();
		System.out.print(flag);
        
		List<Category> categorias = 
				cioneEcommerceConectionProvider
				.getApiRoot()
				.categories()
				.get()
				.withWhere("parent(id=\"" + idMarcas + "\")")
				.withSort("orderHint asc")
				.executeBlocking()
				.getBody()
				.getResults();
        List<String> marcas = new ArrayList<String>();
        
        for (Category id: categorias) {
        	marcas.add(id.getId());
        }
      
        return marcas;
	}


	/*
	 * Funcion que a partir del id de una categoria obtiene el listado de ids de esa categoria y de sus hijas
	 
	public Iterable<String> getCategoryIdsById(String categoryid) {
		ArrayList<String> list = new ArrayList<String>();
				
		String monturas = getCategoryById(categoryid).getId();
        
        String flag = cioneEcommerceConectionProvider.getFlag();
		System.out.print(flag);
        
        QueryPredicate<Category> predicate = CategoryQueryModel.of().parent().id().is(monturas);
        Query<Category> querymarcas = CategoryQuery.of().withPredicates(predicate).withLimit(100);
        
        CompletionStage<PagedQueryResult<Category>> marcas_query = cioneEcommerceConectionProvider.getClient().execute(querymarcas);
        PagedQueryResult<Category> actual = marcas_query.toCompletableFuture().join();
        List<Category> categories = actual.getResults();
        list.add(monturas);
        for (Category categoria: categories) {
        	list.add(categoria.getId());
        }
        Iterable<String> iterable = list;
        
        return iterable;
	}*/
	
	/*
	 * Funcion que a partir del nombre de una categoria obtiene el listado de ids de esa categoria y de sus hijas
	 */
	public Iterable<String> getCategoryIdsByName(String categoryName) {
		ArrayList<String> list = new ArrayList<String>();
		
		//localiza el id de la categoria a partir del nombre		
		String monturas = categoryService.getCategoryIdByName(MyshopConstants.esLocale, categoryName);
        
        String flag = cioneEcommerceConectionProvider.getFlag();
		System.out.print(flag);
        
		List<Category> categories = 
				cioneEcommerceConectionProvider
				.getApiRoot()
				.categories()
				.get()
				.withWhere("parent(id=\"" + monturas + "\")")
				.executeBlocking()
				.getBody()
				.getResults();
        list.add(monturas);
        for (Category categoria: categories) {
        	list.add(categoria.getId());
        }
        Iterable<String> iterable = list;
        
        return iterable;
	}
	
	
	//devuelve si el string es un blank " "
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
	
	private void setGeneralInfo(VariantDTO variantResult, ProductProjection product, ProductVariant vartiant) {
		
		List<String> listMarcas = getIdCategories(MyshopConstants.MARCAS);
		Category categoryRepuestos = categoryService.getCategoryByName(MyshopConstants.esLocale, MyshopConstants.REPUESTOS);
		
		List<CategoryReference> refCategories = product.getCategories();
		for(CategoryReference ref: refCategories) {
			if (ref.getId().equals(categoryRepuestos.getId()))
				variantResult.setRepuesto(true);
			//buscamos en el listado de marcas
			if (listMarcas.contains(ref.getId())) {
				Category categoria = getCategoryById(ref.getId());
				if (categoria.getCustom() != null) {
					variantResult.setLogoMarca(CioneUtils.getURLHttps() + categoria.getCustom().getFields().values().get("logoProducto"));
				}
				else {
					variantResult.setLogoMarca(CioneUtils.getURLHttps() + "/" + MyshopConstants.defaultCategoryLogo(MgnlContext.getLocale()));
				}
			} else {
				//variantResult.setLogoMarca(MyshopConstants.defaultLogo);
			}
		}
		
		if (product.getMasterVariant().getSku().equals(vartiant.getSku())) {
			variantResult.setMaster(true);
		}
		
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
		if (product.getName() != null)
			variantResult.setName(product.getName().get(MyshopConstants.esLocale));
		if (MyShopUtils.hasAttribute("nombreArticulo", vartiant.getAttributes())) {
			variantResult.setNombreArticulo((String) MyShopUtils.findAttribute("nombreArticulo", vartiant.getAttributes()).getValue());
		} else {
			//variantResult.setNombreArticulo(product.getName().get(MgnlContext.getLocale()));
			variantResult.setNombreArticulo(product.getName().get(MyshopConstants.esLocale));
		}
		
		if (MyShopUtils.hasAttribute("tipoProducto", vartiant.getAttributes())) {
			variantResult.setTipoProducto(((LocalizedString) MyShopUtils.findAttribute("tipoProducto", vartiant.getAttributes()).getValue()).get(MyshopConstants.esLocale));
			/*variantResult.setFamiliaProducto(MyShopUtils.getFamiliaProducto(variantResult.getTipoProducto()));
			
			if (variantResult.getFamiliaProducto().equals(MyshopConstants.AUDIFONOS) 
					&& (MyShopUtils.hasAttribute(MyshopConstants.AMEDIDA, vartiant.getAttributes())) 
    				&& ((Boolean)MyShopUtils.findAttribute(MyshopConstants.AMEDIDA, vartiant.getAttributes()).getValue()))
						variantResult.setFamiliaProducto(MyshopConstants.AUDIOLAB); //AUDIOLAB*/
		}
		
		if (MyShopUtils.hasAttribute(MyshopConstants.PLANTILLA, vartiant.getAttributes()))
			variantResult.setFamiliaProducto(((AttributePlainEnumValue) MyShopUtils.findAttribute(MyshopConstants.PLANTILLA, vartiant.getAttributes()).getValue()).getKey());
		else {
			//este else sobra si todos los productos tiene contribuida la familia
			variantResult.setFamiliaProducto(MyShopUtils.getFamiliaProducto(variantResult.getTipoProducto()));
			if (variantResult.getFamiliaProducto().equals(MyshopConstants.AUDIFONOS) 
					&& (MyShopUtils.hasAttribute(MyshopConstants.AMEDIDA, vartiant.getAttributes())) 
    				&& ((Boolean)MyShopUtils.findAttribute(MyshopConstants.AMEDIDA, vartiant.getAttributes()).getValue()))
						variantResult.setFamiliaProducto(MyshopConstants.AUDIOLAB); //AUDIOLAB
		}
		
		if (MyShopUtils.hasAttribute("aliasEkon", vartiant.getAttributes())) {
			variantResult.setAliasEkon((String) MyShopUtils.findAttribute("aliasEkon", vartiant.getAttributes()).getValue());
			variantResult.setStock(middlewareService.getStock(variantResult.getAliasEkon()));
		}
		if (MyShopUtils.hasAttribute("paramsInDB", vartiant.getAttributes())) {
			variantResult.setConfig((Boolean) MyShopUtils.findAttribute("paramsInDB", vartiant.getAttributes()).getValue());
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
		
		if (MyShopUtils.hasAttribute("gestionStock", vartiant.getAttributes()))
			variantResult.setGestionStock((boolean) MyShopUtils.findAttribute("gestionStock", vartiant.getAttributes()).getValue());
	}
	
	private void setContactologiaInfo(VariantDTO variantResult, ProductProjection product, ProductVariant vartiant) {
		//CONTACTOLOGIA
		//if (variantResult.getFamiliaProducto().equals(MyshopConstants.CONTACTOLOGIA)) {
		
		//PROBAR BIEN!! ES UN TIPO STRING
		if (MyShopUtils.hasAttribute("diseno", vartiant.getAttributes())) {
			ArrayList<String> disenojson = (ArrayList<String>) MyShopUtils.findAttribute("diseno", vartiant.getAttributes()).getValue();
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
			variantResult.setBproteccionSolar(((Boolean) MyShopUtils.findAttribute("bproteccionSolar", vartiant.getAttributes()).getValue()).toString());

		if (MyShopUtils.hasAttribute("dkt", vartiant.getAttributes()))
			variantResult.setDkt((String) MyShopUtils.findAttribute("dkt", vartiant.getAttributes()).getValue());
		
		if (MyShopUtils.hasAttribute("equivProveedor", vartiant.getAttributes()))
			variantResult.setEquivProveedor((String) MyShopUtils.findAttribute("equivProveedor", vartiant.getAttributes()).getValue());
		
		if (MyShopUtils.hasAttribute("equivProducto", vartiant.getAttributes()))
			variantResult.setEquivProducto((String) MyShopUtils.findAttribute("equivProducto", vartiant.getAttributes()).getValue());

	}
	
	private void setAudioInfo(VariantDTO variantResult, ProductProjection product, ProductVariant vartiant) {
		if (variantResult.getFamiliaProducto().equals(MyshopConstants.AUDIOLAB)) {
			
			//AUDIOLAB
			variantResult.setFamiliaProducto("audiolab");
			if (MyShopUtils.hasAttribute("colorAudioHexa", vartiant.getAttributes())) {
				ArrayList<String> colorAudifono = (ArrayList<String>) MyShopUtils.findAttribute("colorAudioHexa", vartiant.getAttributes()).getValue();
//			if (vartiant.getAttribute("colorAudioHexa") != null) {
//				JsonNode colorAudifono = vartiant.getAttribute("colorAudioHexa").getValueAsJsonNode();
				Map<String, String> mapColorAudifono = getJsonNodetoHashMap(colorAudifono);
				variantResult.setColorAudifonoAudioLab(mapColorAudifono);
			}
			
			if (MyShopUtils.hasAttribute("colorCodo", vartiant.getAttributes())) {
				ArrayList<String> colorcodojson = (ArrayList<String>) MyShopUtils.findAttribute("colorCodo", vartiant.getAttributes()).getValue();
//			if (vartiant.getAttribute("colorCodo") != null) {
//				JsonNode colorcodojson = vartiant.getAttribute("colorCodo").getValueAsJsonNode();
				Collections.sort(colorcodojson);
				Map<String, String> mapColorCodo = getJsonNodetoHashMap(colorcodojson);
				variantResult.setColorCodoAudioLab(mapColorCodo);
			}
			
			if (MyShopUtils.hasAttribute("colorPlato", vartiant.getAttributes())) {
				ArrayList<String> colorplatojson = (ArrayList<String>) MyShopUtils.findAttribute("colorPlato", vartiant.getAttributes()).getValue();
//			if (vartiant.getAttribute("colorPlato") != null) {
//				JsonNode colorplatojson = vartiant.getAttribute("colorPlato").getValueAsJsonNode();
				Map<String, String> mapColorPlato = getJsonNodetoHashMap(colorplatojson);
				variantResult.setColorPlatoAudioLab(mapColorPlato);
			}
			
			//PROBAR BIEN!! VER SI DEVUELVE UN List<String> o Set<String>
			if (MyShopUtils.hasAttribute("power", vartiant.getAttributes())) {
				ArrayList<String> power = (ArrayList<String>) MyShopUtils.findAttribute("power", vartiant.getAttributes()).getValue();
				variantResult.setPowerList(power);
			}

			if (MyShopUtils.hasAttribute("formatoAudio", vartiant.getAttributes())) {
				ArrayList<String> formatoAudio = (ArrayList<String>) MyShopUtils.findAttribute("formatoAudio", vartiant.getAttributes()).getValue();
				variantResult.setFormatos(formatoAudio);
			}
			
			if (MyShopUtils.hasAttribute("direccionalidad", vartiant.getAttributes()))
				variantResult.setDireccionalidad((Boolean) MyShopUtils.findAttribute("direccionalidad", vartiant.getAttributes()).getValue());
			
			if (MyShopUtils.hasAttribute("conectividad", vartiant.getAttributes()))
				variantResult.setConectividad((Boolean) MyShopUtils.findAttribute("conectividad", vartiant.getAttributes()).getValue());
			
			if (MyShopUtils.hasAttribute("mediaConcha", vartiant.getAttributes()))
				variantResult.setMediaconcha((Boolean) MyShopUtils.findAttribute("mediaConcha", vartiant.getAttributes()).getValue());
			
			//Caracteristicas opcionales
			if (MyShopUtils.hasAttribute("tamanoPila", vartiant.getAttributes()))
				variantResult.setTamanoPila((String) MyShopUtils.findAttribute("tamanoPila", vartiant.getAttributes()).getValue());
			
			if (MyShopUtils.hasAttribute("longitudCanal", vartiant.getAttributes())) {
				ArrayList<String> longitudCanal = (ArrayList<String>) MyShopUtils.findAttribute("longitudCanal", vartiant.getAttributes()).getValue();
				variantResult.setLongitudCanal(longitudCanal);
			}
			
			if (MyShopUtils.hasAttribute("telebobina", vartiant.getAttributes()))
				variantResult.setTelebobina((Boolean) MyShopUtils.findAttribute("telebobina", vartiant.getAttributes()).getValue());
			
			if (MyShopUtils.hasAttribute("filtroCerumen", vartiant.getAttributes())) {
				ArrayList<String> filtroCerumen = (ArrayList<String>) MyShopUtils.findAttribute("filtroCerumen", vartiant.getAttributes()).getValue();
				variantResult.setFiltroCerumen(filtroCerumen);
			}
			
			if (MyShopUtils.hasAttribute("pulsador", vartiant.getAttributes()))
				variantResult.setPulsador((Boolean) MyShopUtils.findAttribute("pulsador", vartiant.getAttributes()).getValue());
			
			if (MyShopUtils.hasAttribute("controlVolumen", vartiant.getAttributes()))
				variantResult.setControlVolumen((Boolean) MyShopUtils.findAttribute("controlVolumen", vartiant.getAttributes()).getValue());
			
			if (MyShopUtils.hasAttribute("hiloExtractor", vartiant.getAttributes()))
				variantResult.setHiloExtractor((Boolean) MyShopUtils.findAttribute("hiloExtractor", vartiant.getAttributes()).getValue());
			
			if (MyShopUtils.hasAttribute("tinnitus", vartiant.getAttributes()))
				variantResult.setTinnitus((Boolean) MyShopUtils.findAttribute("tinnitus", vartiant.getAttributes()).getValue());
			
			if (MyShopUtils.hasAttribute("venting", vartiant.getAttributes()))
				variantResult.setVenting((Boolean) MyShopUtils.findAttribute("venting", vartiant.getAttributes()).getValue());
			
			if (MyShopUtils.hasAttribute("tipoVenting", vartiant.getAttributes())) {
				ArrayList<String> tipoVenting = (ArrayList<String>) MyShopUtils.findAttribute("tipoVenting", vartiant.getAttributes()).getValue();
				variantResult.setTipoVenting(tipoVenting);
			}
			
			if (MyShopUtils.hasAttribute("modVenting", vartiant.getAttributes())) {
				ArrayList<String> modVenting = (ArrayList<String>) MyShopUtils.findAttribute("modVenting", vartiant.getAttributes()).getValue();
				variantResult.setModVenting(modVenting);
			}
    			
    	} //else {
//    		//no entiendo este else, revisar!
//    		if (variantResult.getFamiliaProducto().equals(MyshopConstants.AUDIOLAB)) {
//    			if (MyShopUtils.hasAttribute("colorCodo", vartiant.getAttributes())) {
//    				ArrayList<String> headsetjson = (ArrayList<String>) MyShopUtils.findAttribute("colorCodo", vartiant.getAttributes()).getValue();
//    				headsetjson.removeIf(n -> isBlank(n));
//	    			List<String> colorlistcodo = new ArrayList<String>();
//	    			for (String color : headsetjson) {
//	    				colorlistcodo.add(color.substring(0, color.indexOf('|')));
//	    			}
//	    			Collections.sort(colorlistcodo);
//	    			variantResult.setColorCodo(colorlistcodo);
//	    		}
//    		}
//		}
		if (MyShopUtils.hasAttribute("auriculares", vartiant.getAttributes())) {
			ArrayList<String> headsetjson = (ArrayList<String>) MyShopUtils.findAttribute("auriculares", vartiant.getAttributes()).getValue();
			headsetjson.removeIf(n -> isBlank(n));
			String cheadsetstring = headsetjson.toString().replace("\"", "").replace("[","").replace("]", "");
			List<String> headsetlist = new ArrayList<>(Arrays.asList(cheadsetstring.split(",")));
			variantResult.setAuriculares(getNamesBySKU(headsetlist));
		}
		
		if (MyShopUtils.hasAttribute("acopladores", vartiant.getAttributes())) {
			ArrayList<String> couplerjson = (ArrayList<String>) MyShopUtils.findAttribute("acopladores", vartiant.getAttributes()).getValue();
			couplerjson.removeIf(n -> isBlank(n));
			String couplerstring = couplerjson.toString().replace("\"", "").replace("[","").replace("]", "");
			List<String> couplerlist = new ArrayList<>(Arrays.asList(couplerstring.split(",")));
			variantResult.setAcopladores(getNamesBySKU(couplerlist));
		}

		if (MyShopUtils.hasAttribute("cargadores", vartiant.getAttributes())) {
			ArrayList<String> chargerjson = (ArrayList<String>) MyShopUtils.findAttribute("cargadores", vartiant.getAttributes()).getValue();
			chargerjson.removeIf(n -> isBlank(n));
			String chargerstring = chargerjson.toString().replace("\"", "").replace("[","").replace("]", "");
			List<String> chargerlist = new ArrayList<>(Arrays.asList(chargerstring.split(",")));
			variantResult.setCargadores(getNamesBySKU(chargerlist));
		}

		if (MyShopUtils.hasAttribute("accesorios", vartiant.getAttributes())) {
			ArrayList<String> accessoriesjson = (ArrayList<String>) MyShopUtils.findAttribute("accesorios", vartiant.getAttributes()).getValue();
			accessoriesjson.removeIf(n -> isBlank(n));
			String accessoriesstring = accessoriesjson.toString().replace("\"", "").replace("[","").replace("]", "");
			List<String> accessorieslist = new ArrayList<>(Arrays.asList(accessoriesstring.split(",")));
			variantResult.setAccesorios(getNamesBySKU(accessorieslist));
		}

		if (MyShopUtils.hasAttribute("garantias", vartiant.getAttributes())) {
			ArrayList<String> garantiasjson = (ArrayList<String>) MyShopUtils.findAttribute("garantias", vartiant.getAttributes()).getValue();
			garantiasjson.removeIf(n -> isBlank(n));
			String garantiasstring = garantiasjson.toString().replace("\"", "").replace("[","").replace("]", "");
			List<String> garantiaslist = new ArrayList<>(Arrays.asList(garantiasstring.split(",")));
			variantResult.setGarantia(getNamesBySKU(garantiaslist));
		}

		if (MyShopUtils.hasAttribute("tubosFinos", vartiant.getAttributes())) {
			ArrayList<String> tubosFinosjson = (ArrayList<String>) MyShopUtils.findAttribute("tubosFinos", vartiant.getAttributes()).getValue();
			tubosFinosjson.removeIf(n -> isBlank(n));
			String tubosFinosstring = tubosFinosjson.toString().replace("\"", "").replace("[","").replace("]", "");
			List<String> tubosFinosstringlist = new ArrayList<>(Arrays.asList(tubosFinosstring.split(",")));
			variantResult.setTubosFinos(getNamesBySKU(tubosFinosstringlist));
		}

		if (MyShopUtils.hasAttribute("sujecionesDeportivas", vartiant.getAttributes())) {
			ArrayList<String> sujecionesDeportivasjson = (ArrayList<String>) MyShopUtils.findAttribute("sujecionesDeportivas", vartiant.getAttributes()).getValue();
			sujecionesDeportivasjson.removeIf(n -> isBlank(n));
			String sujecionesDeportivasstring = sujecionesDeportivasjson.toString().replace("\"", "").replace("[","").replace("]", "");
			List<String> sujecionesDeportivasstringlist = new ArrayList<>(Arrays.asList(sujecionesDeportivasstring.split(",")));
			variantResult.setSujecionesDeportivas(getNamesBySKU(sujecionesDeportivasstringlist));
		}
		
		if (MyShopUtils.hasAttribute("filtros", vartiant.getAttributes())) {
			ArrayList<String> filtrosjson = (ArrayList<String>) MyShopUtils.findAttribute("filtros", vartiant.getAttributes()).getValue();
			filtrosjson.removeIf(n -> isBlank(n));
			String filtrosstring = filtrosjson.toString().replace("\"", "").replace("[","").replace("]", "");
			List<String> filtrosstringlist = new ArrayList<>(Arrays.asList(filtrosstring.split(",")));
			variantResult.setFiltros(getNamesBySKU(filtrosstringlist));
		}
    	
		if (MyShopUtils.hasAttribute("canDeposit", vartiant.getAttributes()))
			variantResult.setDeposito((Boolean) MyShopUtils.findAttribute("canDeposit", vartiant.getAttributes()).getValue());
	}
	
	private void setContactologiaDDBBInfo(VariantDTO variantResult) {
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
	}
	
	private List<VariantDTO> getPackContentAudio (VariantDTO variantResult){
		List<VariantDTO> listContenidoPack= new ArrayList<>();
		
		String skuPackPadre = variantResult.getSkupackPadre(); //SKU del producto Pack
		ProductVariant variantProductoPack = commerceToolsService.getProductVariantBysku(skuPackPadre, variantResult.getGruposPrecio());
		
		String skuProductoSeleccionado = variantResult.getSkuPackMasterProduct(); //SKU del producto seleccionado tras la navegacion
		
		//INFO PRODUCTO MASTER
		VariantDTO variantProductoSeleccionado = getInfoVariant(skuProductoSeleccionado, null);
		variantProductoSeleccionado.setPvoPackUD(variantProductoSeleccionado.getPvo());
		
		if (MyShopUtils.hasAttribute("unidades", variantProductoPack.getAttributes())) {
			variantProductoSeleccionado.setUnidadesPack(((Long) MyShopUtils.findAttribute("unidades", variantProductoPack.getAttributes()).getValue()).intValue());
//		if (variantProductoPack.getAttribute("unidades")!= null) {
//			variantProductoSeleccionado.setUnidadesPack(variantProductoPack.getAttribute("unidades").getValueAsInteger());
		} else {
			variantProductoSeleccionado.setUnidadesPack(1);
		}
		variantProductoSeleccionado.setMasterPack(true);
		variantProductoSeleccionado.setSkupackPadre(skuPackPadre);
		listContenidoPack.add(variantProductoSeleccionado);
		
		//INFO PRODUCTOS DE REGALO
		
		if (MyShopUtils.hasAttribute("listadoProductosRegalo", variantProductoPack.getAttributes())) {
		//if (variantProductoPack.getAttribute("listadoProductosRegalo")!= null) {
			Map<String,GiftProduct> productoRegalo = new HashMap<>();
			ArrayList<String> listRegalos = (ArrayList<String>) MyShopUtils.findAttribute("listadoProductosRegalo", variantProductoPack.getAttributes()).getValue();
			//Set<String> listRegalos = variantProductoPack.getAttribute("listadoProductosRegalo").getValueAsStringSet();
			for (String tipoRegalo: listRegalos) {
				VariantDTO regalo = new VariantDTO();
				StringTokenizer it = new StringTokenizer(tipoRegalo, "#");
				if (it.hasMoreElements()) {
					regalo.setTipoRegalo(it.nextToken());
				}
				if (it.hasMoreElements()) {
					regalo.setUnidadesPack(Integer.valueOf(it.nextToken()));
				}else {
					regalo.setUnidadesPack(1);
				}
				regalo.setNombreArticulo(regalo.getTipoRegalo());
				regalo.setPvoPackUD("0.00");
				regalo.setPvo("0.00"); //Modificarlo por el precio real del pack
				
				GiftProduct gift = new GiftProduct();
				gift.setPvo(regalo.getPvoPackUD());
				//gift.setType(regalo.getTipoRegalo().toLowerCase());
				gift.setType(regalo.getTipoRegalo());
				gift.setUnits(regalo.getUnidadesPack());
				
				regalo.setUnidadesPack(regalo.getUnidadesPack());
				
				regalo.setFamiliaProducto(regalo.getTipoRegalo());
				
				productoRegalo.put(gift.getType(), gift);
				
				String url = CioneUtils.getURLHttps() + "/" + MyshopConstants.defaultProductLogo(MgnlContext.getLocale());
				ImageDimensions imageDimensions = ImageDimensionsBuilder.of().h(135).w(210).build();
				Image imagen = ImageBuilder.of().url(url).dimensions(imageDimensions).build();
				//Image imagen = Image.ofWidthAndHeight(url, 210, 135);
				regalo.setMasterImage(imagen);
				listContenidoPack.add(regalo);
			}
			
			variantProductoSeleccionado.setListadoProductosRegalo(productoRegalo);
//			HashMap<String, Integer> map = new HashMap<>();
//			for (String tipoRegalo: listRegalos) {
//				
//				if (map.get(tipoRegalo) != null) {
//					Integer i = map.get(tipoRegalo);
//					map.put(tipoRegalo, i++);
//				}
//			}
//			
//			for (String key : map.keySet()) {
//				VariantDTO regalo = new VariantDTO();
//				regalo.setTipoRegalo(key);
//				regalo.setUnidadesPack(map.get(key));
//				regalo.setNombreArticulo(key);
//				regalo.setPvoPackUD("0.00");
//				listContenidoPack.add(regalo);
//			}
		}
		
		return listContenidoPack;
		
	}
	
	@Override
	public VariantDTO getInfoVariant(String sku, String skupack){
		VariantDTO variantResult = new VariantDTO();
		if (skupack!= null) {
			variantResult.setSku(skupack);
			variantResult.setSkupackPadre(skupack);
			if (sku != null)
				variantResult.setSkuPackMasterProduct(sku);
			else
				variantResult.setSkuPackMasterProduct(skupack);
		} else {
			variantResult.setSku(sku);
		}
		
		variantResult.setGruposPrecio(commerceToolsService.getGrupoPrecioCommerce());
		log.debug("GRUPO PRECIO = " + variantResult.getGruposPrecio());
		try {
			
			ProductProjection product = commerceToolsServiceAux.getProductBySkuFilter(variantResult.getSku(), variantResult.getGruposPrecio());
			if (product != null)
				variantResult.setExist(true);
			else
				//no existe o no tiene permisos para ver el artículo
				return variantResult;
			
			ProductVariant vartiant = commerceToolsServiceAux.findVariantBySku(product, variantResult.getSku()); //product.findVariantBySku(variantResult.getSku()).get();	
			
			setGeneralInfo(variantResult, product, vartiant);
			
			if (variantResult.getFamiliaProducto() == null) {
				variantResult.setExist(false);
				return variantResult;
			}
	    		
			setContactologiaInfo(variantResult, product, vartiant);
			
			setAudioInfo(variantResult, product, vartiant);
    		
    		//COLOR LENTE Y COLOR AUDIFONO
			
			if (MyShopUtils.hasAttribute("color", vartiant.getAttributes())) {
				ArrayList<String> colorjson = (ArrayList<String>) MyShopUtils.findAttribute("color", vartiant.getAttributes()).getValue();
    			//String colorstring = colorjson.toString().replace("\"", "").replace("[","").replace("]", "");
    			//List<String> colorlist = new ArrayList<>(Arrays.asList(colorstring.split(",")));
				colorjson.removeIf(n -> isBlank(n));
    			
    			//Collections.sort(colorlist);
    			if (variantResult.getFamiliaProducto().equals(MyshopConstants.AUDIFONOS) 
    					|| variantResult.getFamiliaProducto().equals(MyshopConstants.AUDIOLAB)) {
    				variantResult.setColoraudifonos(colorjson);
    			} else {
    				variantResult.setColorlente(colorjson);
    			}
    		}
    		
			if (MyShopUtils.hasAttribute("urlVideo", vartiant.getAttributes()))
				variantResult.setUrlVideo((String) MyShopUtils.findAttribute("urlVideo", vartiant.getAttributes()).getValue());
    		
			if (MyShopUtils.hasAttribute("urlPdf", vartiant.getAttributes()))
				variantResult.setUrlPdf((String) MyShopUtils.findAttribute("urlPdf", vartiant.getAttributes()).getValue());
    		
    		if (MyShopUtils.hasAttribute("codigoCentral", vartiant.getAttributes()))
				variantResult.setCodigoCentral((String) MyShopUtils.findAttribute("codigoCentral", vartiant.getAttributes()).getValue());
    		
    		
    		if (variantResult.getFamiliaProducto() != null) {
    			if (variantResult.getFamiliaProducto().equals("monturas")){
    				variantResult.setColors(getListColorsInfo(product, vartiant));
    				variantResult.setVariantsSku(getVariants(product));
    			} else if (variantResult.getFamiliaProducto().equals("liquidos")){
    				variantResult.setTamaniosLiquidos(getListTamaniosLiquidos(product, vartiant));
    			}
    		}
    		if (MyShopUtils.hasAttribute("pertenecePack", vartiant.getAttributes()))
				variantResult.setPertenecePack((Boolean) MyShopUtils.findAttribute("pertenecePack", vartiant.getAttributes()).getValue());
    		
    		if (variantResult.isPertenecePack()) {
    			List<String> listPasksCode = packDao.getPacksByProduct(variantResult);
    			if (!listPasksCode.isEmpty()) {
    			//consulto el listado de productos filtrados por codPack
	    			variantResult.setPacksContienenProducto(
	    					commerceToolsService.getProductBySkuList(listPasksCode));
    			} else
    				variantResult.setPertenecePack(false);
    		}
    		
    		if (MyShopUtils.hasAttribute("ofertaFlash", vartiant.getAttributes()))
				variantResult.setOfertaFlash((Boolean) MyShopUtils.findAttribute("ofertaFlash", vartiant.getAttributes()).getValue());
    		
    		if (MyShopUtils.hasAttribute("statusEkon", vartiant.getAttributes())) {
    			variantResult.setStatusEkon((String) MyShopUtils.findAttribute("statusEkon", vartiant.getAttributes()).getValue());
    			if (variantResult.getStatusEkon().equals("Liquidacion")) {
        			variantResult.setLiquidacion(true);
        		}
    		}
	    		
    		if (variantResult.getFamiliaProducto().equals("packs")) {
    			if (MyShopUtils.hasAttribute("codigoPack", vartiant.getAttributes()))
    				variantResult.setCodigoPack((String) MyShopUtils.findAttribute("codigoPack", vartiant.getAttributes()).getValue());
    			else
    				variantResult.setCodigoPack(vartiant.getSku());
    			if (variantResult.getSkupackPadre()!=null)
    				variantResult.setContenidoPack(getPackContentAudio(variantResult));
    			else //packs por bases de datos
    				variantResult.setContenidoPack(packDao.getPackContent(variantResult));
    			//if (vartiant.getAttribute("pvoSinPack") != null) {
    			//sumamos el precio de cada uno de los productos y se lo seteamos como pvo o precio sin pack
				double precio = 0;
				double descuento = 0;
				for (VariantDTO contenido: variantResult.getContenidoPack()) {
					precio += Double.valueOf(contenido.getPvo().replace(',', '.')) * contenido.getUnidadesPack();
					descuento += Double.valueOf(contenido.getPvoPackUD().replace(',', '.')) * contenido.getUnidadesPack();
				}
				DecimalFormat df = new DecimalFormat("0.00");
				variantResult.setPvo(df.format(precio).replace('.', ','));
    				//variantResult.setPvo(MyShopUtils.formatTypedMoney(vartiant.getAttribute("pvoSinPack").getValueAsMoney()));
    			//} else
    				//variantResult.setPvo(MyShopUtils.formatTypedMoney(vartiant.getPrices().get(0).getValue()));
				
    			//variantResult.setPvoDto(MyShopUtils.formatTypedMoney(vartiant.getPrices().get(0).getValue()));
				variantResult.setPvoDto(df.format(descuento).replace('.', ','));
    			
    			variantResult.setTienePromociones(true);
    			variantResult.setTipoPromocion("pack");
    		} else {
    		
    			Price price = commerceToolsService.getPriceBycustomerGroup(variantResult.getGruposPrecio(), vartiant.getPrices());
				variantResult.setTipoPromocion("sin-promo");
	    		//for (Price price : prices) {
    			//if (price.getCustomerGroup() == null) {
    				//del listado de precios nos quedamos con el señalado con Any
    				//price to Money
    				variantResult.setPvo(MyShopUtils.formatTypedMoney(price.getValue()).replace('.', ','));
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
	    						
	    						if (variantResult.isLiquidacion()) {
	    							if (promo.getDescripcion().equals("OFERTA FLASH")) {
	    								variantResult.setLiquidacion(false);
	    								variantResult.setOfertaFlash(true);
	    							} else if (promo.getDescripcion().equals("PROMOCIONES")) {
	    								variantResult.setLiquidacion(true);
	    							}
	    						}
	    						
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
//    			} else {
//    				/*Comentada la logica en caso de que metan precios por grupo de usuarios 
//    				String customerid = commercetoolsService.getIdOfCustomerGroupByCostumerId(CioneUtils.getIdCurrentClient());
//    				if (price.getCustomerGroup().getId().equals(customerid)) {
//    					variantResult.setPvo(MyShopUtils.formatTypedMoney(price.getValue()));
//    				}*/
//    				variantResult.setPvo(MyShopUtils.formatTypedMoney(price.getValue()));
//    			}
	    		//}
    		}
    		
    		if (MyShopUtils.hasAttribute("pvpRecomendado", vartiant.getAttributes())) {
				CentPrecisionMoney money = (CentPrecisionMoney) MyShopUtils.findAttribute("pvpRecomendado", vartiant.getAttributes()).getValue();
				String pvp = MyShopUtils.formatTypedMoney(money);
	    		variantResult.setPvpRecomendado(pvp.replace('.', ','));
    		}
    		/*Attribute pricesPvp = vartiant.getAttribute("pvpRecomendado");
    		if (pricesPvp!= null){
	    		MonetaryAmount money =pricesPvp.getValueAsMoney();
	    		String pvp = MyShopUtils.formatTypedMoney(money);
	    		variantResult.setPvpRecomendado(pvp);
    		}*/
    		
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
				variantResult.setDimensiones_ancho_ojo(String.valueOf((Long) MyShopUtils.findAttribute("dimensiones_ancho_ojo", vartiant.getAttributes()).getValue()));
    		
    		if (MyShopUtils.hasAttribute("dimensiones_largo_varilla", vartiant.getAttributes()))
				variantResult.setDimensiones_largo_varilla((String) MyShopUtils.findAttribute("dimensiones_largo_varilla", vartiant.getAttributes()).getValue());
    		
    		if (MyShopUtils.hasAttribute("dimensiones_ancho_puente", vartiant.getAttributes()))
				variantResult.setDimensiones_ancho_puente((String) MyShopUtils.findAttribute("dimensiones_ancho_puente", vartiant.getAttributes()).getValue());
    		
    		if (MyShopUtils.hasAttribute("mensajesEspecificos", vartiant.getAttributes())) {
    			ArrayList<String> mensajes = (ArrayList<String>) MyShopUtils.findAttribute("mensajesEspecificos", vartiant.getAttributes()).getValue();
				variantResult.setMensajesEspecificos(getSpecificMessages(mensajes));
			}
    		
    		
    		setContactologiaDDBBInfo(variantResult);
	    		
    		if ((MyShopUtils.hasAttribute(MyshopConstants.REPUESTOALTERNATIVO, vartiant.getAttributes())) 
    			&& !((ArrayList<String>) MyShopUtils.findAttribute(MyshopConstants.REPUESTOALTERNATIVO, vartiant.getAttributes()).getValue()).isEmpty()){
    			variantResult.setHasSubstitute(true);
    		}
    		
		
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
	
	@Override
	public VariantDTO getInfoVariantPack(String skuPackMaster) throws Exception{
		VariantDTO variantDTOPack = new VariantDTO();
		
		variantDTOPack.setGruposPrecio(commerceToolsService.getGrupoPrecioCommerce());
		
		variantDTOPack.setSku(skuPackMaster);
		variantDTOPack.setSkuPack(skuPackMaster);
		variantDTOPack.setSkuPackMaster(skuPackMaster);
		try {
			
			ProductProjection product = commerceToolsServiceAux.getProductBySkuFilter(variantDTOPack.getSkuPack(), variantDTOPack.getGruposPrecio());
			if (product != null)
				variantDTOPack.setExist(true);
			else
				//no existe o no tiene permisos para ver el artículo
				return variantDTOPack;
			
            ProductVariant masterVariant = product.getMasterVariant();//commerceToolsServiceAux.findVariantBySku(product, variantDTOPack.getSkuPack());
            
            //añadimos informacion de name, imagenes (master y variantes), nombre a packInfo
            setGeneralInfo(variantDTOPack, product, masterVariant);
                   
            
            if (product.getDescription() != null)
            	variantDTOPack.setDescripcion(product.getDescription().get(MyshopConstants.esLocale));
            variantDTOPack.setTipoPromocion("pack-generico");
            
            //incializamos infoPack con los atributos a nivel de producto (sameforall)
            InfoPackGenericoDTO infoPack = new InfoPackGenericoDTO(product.getMasterVariant().getAttributes());
            
            //AÑADIR PVO PVO-DTO Y PVP
            switch (infoPack.getTipoPrecioPack()) {
				/*case "CERRADO": {
					
					
					//setear pvoPackCerrado
					if (MyShopUtils.hasAttribute("pvoPackCerrado", product.getMasterVariant().getAttributes())) {
						CentPrecisionMoney money = (CentPrecisionMoney) MyShopUtils.findAttribute("pvoPackCerrado", product.getMasterVariant().getAttributes()).getValue();
						String pvoPackCerrado = MyShopUtils.formatTypedMoney(money);
						variantDTOPack.setPvo(pvoPackCerrado);
		    		}
					/*Price price = commerceToolsService.getPriceBycustomerGroup(variantDTOPack.getGruposPrecio(), product.getMasterVariant().getPrices());
					if (price != null)
						variantDTOPack.setPvo(MyShopUtils.formatTypedMoney(price.getValue()));/
					
					infoPack.setDescripcionPack("Todo el pack por : " + variantDTOPack.getPvo() );
					break;
				}*/
				case "GLOBAL": {
					//en los listados mostramos los mensajes pack con x% de descuento en todos los productos del pack o pack con x€ de descuento en todos los productos del pack
					infoPack.setDescripcionPack("Todo los productos del pack con un  : " + infoPack.getDescuentoGlobal());
					break;
				}
				case "INDIVIDUAL-CERRADO": { //deprecated
					Price price = commerceToolsService.getPriceBycustomerGroup(variantDTOPack.getGruposPrecio(), product.getMasterVariant().getPrices());
					double precioTotal = 0.0;
					if (price != null)
						precioTotal = MyShopUtils.getCentAmountDouble(price.getValue());
					//recorremos las variantes y lo sumamos
					List<ProductVariant> variantesPack = product.getVariants();
					for (ProductVariant variantPack : variantesPack) {
						price = commerceToolsService.getPriceBycustomerGroup(variantDTOPack.getGruposPrecio(), variantPack.getPrices());
						precioTotal += MyShopUtils.getCentAmountDouble(price.getValue());
					}
					
					variantDTOPack.setPvo(String.valueOf(precioTotal));
					infoPack.setDescripcionPack("Todo los productos del pack por  : " + String.valueOf(precioTotal));
					break;
				}
				case "INDIVIDUAL": {					
					//variantDTOPack.setPvo(String.valueOf(precioTotal));
					infoPack.setDescripcionPack("Todo los productos del pack con descuento  : ");
					break;
				}
				default:
					break;
			}
            variantDTOPack.setInfoPack(infoPack);
            
			if (MyShopUtils.hasAttribute("urlVideo", masterVariant.getAttributes()))
				variantDTOPack.setUrlVideo((String) MyShopUtils.findAttribute("urlVideo", masterVariant.getAttributes()).getValue());
    		
			if (MyShopUtils.hasAttribute("urlPdf", masterVariant.getAttributes()))
				variantDTOPack.setUrlPdf((String) MyShopUtils.findAttribute("urlPdf", masterVariant.getAttributes()).getValue());

            //CONTENIDO PACK
			//recorremos todas las variantes, y cada variante se refiere a una linea del pack a configurar
				//cada linea tiene que tener el listado de filtros (agrupadores) a aplicar en la pantalla de listados
			List<AgrupadorDTO> listaAgrupadores = new ArrayList<>();
			HttpSession session = MgnlContext.getWebContext().getRequest().getSession();
			HashMap<String, List<AgrupadorDTO>> infoPackMapSession = commerceToolsService.getInfoPackMapSession();
			if ((infoPackMapSession != null) && (infoPackMapSession.get(skuPackMaster) != null)) {
				listaAgrupadores = infoPackMapSession.get(skuPackMaster);
			} else {
				//lo recuperamos del carrito por si hubiera perdido la sesion de usuario y lo volvemos a almacenar en sesion
				Cart cart = cartService.getCartPackByCustomeridPurchase(commerceToolsService.getCustomerId(), skuPackMaster);
				if (cart != null) {
					//Lo recuperamos del carrito
					if (session.getAttribute(MyshopConstants.ATTR_PACK_SESSION) != null)
						infoPackMapSession = commerceToolsService.getInfoPackMapSession();
					else
						infoPackMapSession = new HashMap<>();
					
					List<AgrupadorDTO> listaAgrupadoresCarrito = new ArrayList<>();
					
					//inicializamos
					inicializarListaAgrupadores(listaAgrupadores, masterVariant, variantDTOPack, infoPack, product);
					
					//actualizamos con los datos del carrito
					boolean carritoCorrupto = false;
					boolean permitirhabilitar= false;
					
					String tipoPrecioPack = ""; //almacenamos el tipoPrecioPack de las lineItems, ya que sera el mismo para las customLineItems de CioneLab
					for(LineItem lineItem: cart.getLineItems()) {
						int unidades = lineItem.getQuantity().intValue();
						for (int i=1; i<=unidades; i++) {
							if ((lineItem.getCustom() != null) && (lineItem.getCustom().getFields().values().get("tipoProductoPack") != null)) {
								AgrupadorDTO agrupador = getAgrupadorCarritoLineItem(lineItem, cart);
								tipoPrecioPack = agrupador.getTipoPrecioPack();
								if (lineItem.getCustom().getFields().values().get("step") == null)
									carritoCorrupto = true;
								//if (agrupador.getTipoProductoPackKey().equals("GG"))
								if (agrupador.getTipoProductoPack().equals("Gafas graduadas")) //si es un montura podemos habilitar la seleccion de cionelab
									permitirhabilitar= true;
								
								listaAgrupadoresCarrito.add(agrupador);
							}
						}
					}
					
					if ((cart.getCustomLineItems() != null) && (!cart.getCustomLineItems().isEmpty())) {
						List<AgrupadorDTO> listaAgrupadoresLentes = new ArrayList<>();
						AgrupadorDTO agrupadorLenteOF = new AgrupadorDTO();
						agrupadorLenteOF.setTipoProductoPack("Lentes Oftalmicas");
						agrupadorLenteOF.setTipoProductoPackKey("LENOF");
						agrupadorLenteOF.setConfigurado(true);
						
						
						//agrupadorLenteOF.setSkuProductoConfigurado(agrupadorCarrito.getSkuProductoConfigurado());
						//agrupadorLenteOF.setPvoOrigin(agrupadorCarrito.getPvoOrigin());
						//
						//agrupadorLenteOF.setNombreProductoConfigurado(agrupadorCarrito.getNombreProductoConfigurado());
						agrupadorLenteOF.setIdCarritoOculto(cart.getId());
						//agrupadorLenteOF.setLineItemIdOculto(agrupadorCarrito.getLineItemIdOculto());
						String step="";
						Double pvoAllCustomLinesPack = 0.0; //Double pvo = Double.valueOf(agrupadordto.getPvoPack().replace(',', '.')) * agrupadordto.getUnidadesProductoPack();
														//agrupadordto.setPvoAllLines(MyShopUtils.formatDoubleString(pvo));
						
						Double pvoAllCustomLinesOrigin = 0.0;
					
						for(CustomLineItem customLineItem: cart.getCustomLineItems()) {
							//añadir funcionalidad para volcar del carrito las customLineItems de CioneLab
							
								String idtype = customLineItem.getCustom().getType().getId();
								String key = cioneEcommerceConectionProvider.getApiRoot().types().withId(idtype).get().executeBlocking().getBody().getKey();
								
								AgrupadorDTO agrupadorLente = new AgrupadorDTO();
								
								agrupadorLente.setTipoProductoPack("Lentes Oftalmicas");
								agrupadorLente.setTipoProductoPackKey("LENOF");
								//agrupador.setTipoProductoPackKey(getTipoProductoPackKey(tipoProductoPack));
								agrupadorLente.setConfigurado(true);
								
									
								//Type fetchedType = cioneEcommerceConectionProvider.getClient().executeBlocking(TypeByIdGet.of(idtype));
								switch (key) {
								case "customlineitem-cionelab-lenses":
									//lentes
									String name = "";
									agrupadorLente.setConfigurado(true);
									if (customLineItem.getCustom().getFields().values().get("LNAM_DESC_R") != null) {
										name = (String)customLineItem.getCustom().getFields().values().get("LNAM_DESC_R");
									} else if (customLineItem.getCustom().getFields().values().get("LNAM_DESC_L") != null) {
										name = (String)customLineItem.getCustom().getFields().values().get("LNAM_DESC_L");
									}
									if (customLineItem.getCustom().getFields().values().get("_REFTALLER") != null)
										agrupadorLente.setRefTaller((String)customLineItem.getCustom().getFields().values().get("_REFTALLER"));
									
									agrupadorLente.setNombreProductoConfigurado(name);
									agrupadorLente.setUrlImagen(CioneUtils.getURLHttps() + "/" + MyshopConstants.defaultLenteGraduadaLogo);
									agrupadorLente.setSkuProductoConfigurado((String)customLineItem.getSlug());
									
									if (customLineItem.getCustom().getFields().values().get("PVO_R") != null) {
										agrupadorLente.setPvoOrigin((String)customLineItem.getCustom().getFields().values().get("PVO_R"));
										
									} else if(customLineItem.getCustom().getFields().values().get("PVO_L") != null) {
										agrupadorLente.setPvoOrigin((String)customLineItem.getCustom().getFields().values().get("PVO_L"));
									}
									
									pvoAllCustomLinesOrigin += MyShopUtils.formatStringDouble(agrupadorLente.getPvoOrigin());
									
									agrupadorLente.setIdCarritoOculto(cart.getId());
									agrupadorLente.setLineItemIdOculto(customLineItem.getId());
									
									if (customLineItem.getCustom().getFields().values().get("pvoConDescuento_R") != null) {
										TypedMoney pvoConDescuento_R = (TypedMoney)customLineItem.getCustom().getFields().values().get("pvoConDescuento_R");
										String pvoConDescuento = MyShopUtils.formatTypedMoney(pvoConDescuento_R);
										agrupadorLente.setPvoPack(pvoConDescuento);
										
										pvoAllCustomLinesPack += MyShopUtils.formatMoneyDouble(pvoConDescuento_R);
										
									} else if (customLineItem.getCustom().getFields().values().get("pvoConDescuento_L") != null) {
										TypedMoney pvoConDescuento_L = (TypedMoney)customLineItem.getCustom().getFields().values().get("pvoConDescuento_L");
										String pvoConDescuento = MyShopUtils.formatTypedMoney(pvoConDescuento_L);
										agrupadorLente.setPvoPack(pvoConDescuento);
										
										pvoAllCustomLinesPack += MyShopUtils.formatMoneyDouble(pvoConDescuento_L);
									}
									
									agrupadorLente.setTipoPrecioPack(tipoPrecioPack);
									
									if (customLineItem.getCustom().getFields().values().get("step") != null) {
										agrupadorLente.setStep((String)customLineItem.getCustom().getFields().values().get("step"));
										step = agrupadorLente.getStep();
									} else {
										carritoCorrupto = true;
									}
									
									listaAgrupadoresLentes.add(agrupadorLente);
									break;
									
								case "customlineitem-cionelab-jobs":
									//trabajos
									String namejob = "";
									agrupadorLente.setConfigurado(true);
									if (customLineItem.getCustom().getFields().values().get("DESCRIPTION") != null) {
										namejob = (String)customLineItem.getCustom().getFields().values().get("DESCRIPTION");
									}
									agrupadorLente.setNombreProductoConfigurado(namejob);
									if (customLineItem.getCustom().getFields().values().get("_REFTALLER") != null)
										agrupadorLente.setRefTaller((String)customLineItem.getCustom().getFields().values().get("_REFTALLER"));
									
									agrupadorLente.setUrlImagen(CioneUtils.getURLHttps() + "/" + MyshopConstants.defaultLenteGraduadaLogo);
									agrupadorLente.setSkuProductoConfigurado((String)customLineItem.getCustom().getFields().values().get("SKU"));									
									
									if (customLineItem.getMoney() != null) {
										agrupadorLente.setPvoOrigin(MyShopUtils.formatTypedMoney(customLineItem.getMoney()));
									}
									
									pvoAllCustomLinesOrigin += MyShopUtils.formatStringDouble(agrupadorLente.getPvoOrigin());
									
									agrupadorLente.setIdCarritoOculto(cart.getId());
									agrupadorLente.setLineItemIdOculto(customLineItem.getId());
									
									if (customLineItem.getCustom().getFields().values().get("pvoConDescuento_R") != null) {
										TypedMoney pvoConDescuento_R = (TypedMoney)customLineItem.getCustom().getFields().values().get("pvoConDescuento_R");
										String pvoConDescuento = MyShopUtils.formatTypedMoney(pvoConDescuento_R);
										agrupadorLente.setPvoPack(pvoConDescuento);
										
										pvoAllCustomLinesPack += MyShopUtils.formatMoneyDouble(pvoConDescuento_R);
										
									} else if (customLineItem.getCustom().getFields().values().get("pvoConDescuento_L") != null) {
										TypedMoney pvoConDescuento_L = (TypedMoney)customLineItem.getCustom().getFields().values().get("pvoConDescuento_L");
										String pvoConDescuento = MyShopUtils.formatTypedMoney(pvoConDescuento_L);
										agrupadorLente.setPvoPack(pvoConDescuento);
										
										pvoAllCustomLinesPack += MyShopUtils.formatMoneyDouble(pvoConDescuento_L);
									}
									
									
									agrupadorLente.setTipoPrecioPack(tipoPrecioPack);
									
									if (customLineItem.getCustom().getFields().values().get("step") != null) {
										agrupadorLente.setStep((String)customLineItem.getCustom().getFields().values().get("step"));
									} else {
										carritoCorrupto = true;
									}
									
									listaAgrupadoresLentes.add(agrupadorLente);
									break;
								default:
									break;
								}
							
						}
						agrupadorLenteOF.setInfoCustomLineItemsCioneLab(listaAgrupadoresLentes);
						agrupadorLenteOF.setStep(step);
						agrupadorLenteOF.setPvoPack(MyShopUtils.formatDoubleString(pvoAllCustomLinesPack));//REVISAR PARA EL RESTO DE CASOS
						agrupadorLenteOF.setPvoOrigin(MyShopUtils.formatDoubleString(pvoAllCustomLinesOrigin));
						listaAgrupadoresCarrito.add(agrupadorLenteOF);
						
					}
						
					if (carritoCorrupto) {
						//borramos el carrito e inicializamos todo
						cartService.deleteCart(cart.getId());
						listaAgrupadoresCarrito = new ArrayList<>();
					}
					if (!listaAgrupadoresCarrito.isEmpty()) {
						//recorremos los productos inicializados para añadir los recuperados del carrito
						//for (AgrupadorDTO agrupadorIni: listaAgrupadores) {
						for (int i=0; i<listaAgrupadores.size(); i++) {
							AgrupadorDTO agrupadorIni = listaAgrupadores.get(i);
							
							if((agrupadorIni.getTipoProductoPackKey()!=null) 
								&& (agrupadorIni.getTipoProductoPackKey().equals("LENOF")))
								agrupadorIni.setHabilitar(permitirhabilitar);
							
							for (int j=0; j<listaAgrupadoresCarrito.size(); j++) {
								AgrupadorDTO agrupadorCarrito = listaAgrupadoresCarrito.get(j);
								//if (agrupadorCarrito.getTipoProductoPack().equals(agrupadorIni.getTipoProductoPack()) ) {
									if ((agrupadorCarrito.getStep() != null) && (!agrupadorCarrito.getStep().isEmpty())) {
										int step = Integer.valueOf(agrupadorCarrito.getStep());
										if (step == i) {
											//volcamos la info del producto del carrito al elemento que acabamos de inicializar
											agrupadorIni.setConfigurado(true);
											agrupadorIni.setSkuProductoConfigurado(agrupadorCarrito.getSkuProductoConfigurado());
											agrupadorIni.setPvoOrigin(agrupadorCarrito.getPvoOrigin());
											
											
											agrupadorIni.setPvoPack(agrupadorCarrito.getPvoPack());//REVISAR PARA EL RESTO DE CASOS
											
											
											//agrupadorIni.setTipoPrecioPack(agrupadorCarrito.getTipoPrecioPack());
											agrupadorIni.setNombreProductoConfigurado(agrupadorCarrito.getNombreProductoConfigurado());
											
											agrupadorIni.setUrlImagen(agrupadorCarrito.getUrlImagen());
											
											agrupadorIni.setIdCarritoOculto(agrupadorCarrito.getIdCarritoOculto());
											agrupadorIni.setLineItemIdOculto(agrupadorCarrito.getLineItemIdOculto());
											
											agrupadorIni.setInfoCustomLineItemsCioneLab(agrupadorCarrito.getInfoCustomLineItemsCioneLab());
											
											listaAgrupadoresCarrito.remove(j);
											break;
										}
									} else {
										log.error("No se ha podido recuperar el campo step del carrito para el producto" + agrupadorCarrito.getSkuProductoConfigurado());
										throw new Exception("No se ha podido recuperar el campo step del carrito para el producto\" + agrupadorCarrito.getSkuProductoConfigurado()");
									}
									
								//}
								
							}
						}
					}
					
					//lo añadimos a la session
					if (!listaAgrupadores.isEmpty()) {
						infoPackMapSession.put(skuPackMaster, listaAgrupadores);
						//HttpSession session = MgnlContext.getWebContext().getRequest().getSession();
						session.setAttribute(MyshopConstants.ATTR_PACK_SESSION, infoPackMapSession);
					}
					
				} else {
					//No existe ni en el carrito ni en session, lo inicializamos
					inicializarListaAgrupadores(listaAgrupadores, masterVariant, variantDTOPack, infoPack, product);
					
					//inicializamos
					//HttpSession session = MgnlContext.getWebContext().getRequest().getSession();
					if (session.getAttribute(MyshopConstants.ATTR_PACK_SESSION) != null)
						infoPackMapSession = commerceToolsService.getInfoPackMapSession();
					else
						infoPackMapSession = new HashMap<>();
					//lo añadimos a la session
					infoPackMapSession.put(skuPackMaster, listaAgrupadores);
					session.setAttribute(MyshopConstants.ATTR_PACK_SESSION, infoPackMapSession);

					log.debug(session.getAttribute(MyshopConstants.ATTR_PACK_SESSION).toString());
				}
			}
			
			variantDTOPack.setContenidoPackListGenerico(listaAgrupadores);
            
            
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
		
		
		return variantDTOPack;
	}
	
	/*Metodo para incluir toda la informacion necesaria de una LineItem del Carrito*/
	private AgrupadorDTO getAgrupadorCarritoLineItem(LineItem lineItem, Cart cart){
		String tipoProductoPack = lineItem.getCustom().getFields().values().get("tipoProductoPack").toString();
		String name = lineItem.getName().get(MyshopConstants.esLocale);
		if (MyShopUtils.hasAttribute("nombreArticulo", lineItem.getVariant().getAttributes())) {
			name = (String) MyShopUtils.findAttribute("nombreArticulo", lineItem.getVariant().getAttributes()).getValue();
		}
		AgrupadorDTO agrupador = new AgrupadorDTO();
		agrupador.setTipoProductoPack(tipoProductoPack);
		//agrupador.setTipoProductoPackKey(getTipoProductoPackKey(tipoProductoPack));
		agrupador.setConfigurado(true);
		agrupador.setNombreProductoConfigurado(name);
		
		String urlImagen = CioneUtils.getURLHttps() + "/" + MyshopConstants.defaultProductLogo(MgnlContext.getLocale());
		if ( (lineItem.getVariant().getImages()!= null) && (lineItem.getVariant().getImages().size() >0))
			urlImagen = lineItem.getVariant().getImages().get(0).getUrl();
		agrupador.setUrlImagen(urlImagen);
		
		agrupador.setSkuProductoConfigurado(lineItem.getVariant().getSku());
		
		boolean tieneRecargo = false;
		String grupoPrecio = commerceToolsService.getGrupoPrecioCommerce();
		if (MyShopUtils.hasAttribute(MyshopConstants.TIENERECARGO, lineItem.getVariant().getAttributes())) 
			tieneRecargo = (Boolean) MyShopUtils.findAttribute(MyshopConstants.TIENERECARGO, lineItem.getVariant().getAttributes()).getValue();
		
		if (tieneRecargo && MyShopUtils.isSocioPortugal())
			agrupador.setPvoOrigin(commerceToolsService.getPvoOriginForPortugal(lineItem.getVariant(), grupoPrecio, lineItem.getPrice()));
		else
			agrupador.setPvoOrigin(MyShopUtils.formatTypedMoney(lineItem.getPrice().getValue()));
		
		agrupador.setIdCarritoOculto(cart.getId());
		agrupador.setLineItemIdOculto(lineItem.getId());
		
		if (lineItem.getCustom().getFields().values().get("pvoConDescuento") != null) {
			String pvoConDescuento = MyShopUtils.formatTypedMoney((TypedMoney)lineItem.getCustom().getFields().values().get("pvoConDescuento"));
			agrupador.setPvoPack(pvoConDescuento);
		}
		
		if (lineItem.getCustom().getFields().values().get("tipoPrecioPack") != null) {
			agrupador.setTipoPrecioPack((String)lineItem.getCustom().getFields().values().get("tipoPrecioPack"));
			
		}
		if (lineItem.getCustom().getFields().values().get("step") != null) {
			agrupador.setStep((String)lineItem.getCustom().getFields().values().get("step"));
		} 
		
		return agrupador;
	}
	
	private String pvoPackFromTipoPrecioPack(int index, AgrupadorDTO agrupadordto, InfoPackGenericoDTO infoPack, String grupoPrecio, 
			ProductProjection product, ProductVariant variantProductoPack, ProductVariant productoPreconfigurado) throws Exception {
		String pvoPack = null;
		String tipoPrecioPack = agrupadordto.getTipoPrecioPack();
		
		boolean tieneRecargo = false;
        if (MyShopUtils.hasAttribute(MyshopConstants.TIENERECARGO, productoPreconfigurado.getAttributes())) 
        	tieneRecargo = (Boolean) MyShopUtils.findAttribute(MyshopConstants.TIENERECARGO, productoPreconfigurado.getAttributes()).getValue();
		
		switch (tipoPrecioPack) {
			/*case "CERRADO": { //deprecated
				if ((index == 0) && agrupadordto.isMaster()) { // si es el primer elemento es al que añadimos el precio de la master
					String price = "0.00";
					if (MyShopUtils.hasAttribute("pvoPackCerrado", product.getMasterVariant().getAttributes())) {
						CentPrecisionMoney money = (CentPrecisionMoney) MyShopUtils.findAttribute("pvoPackCerrado", product.getMasterVariant().getAttributes()).getValue();
						price = MyShopUtils.formatTypedMoney(money);
		    		}
					return price;	
				} else
					return "0.00";
			}*/
			case "GLOBAL": {
				
				Price price = commerceToolsService.getPriceBycustomerGroup(grupoPrecio, productoPreconfigurado.getPrices());
				double pvo = 0.0;
				if (tieneRecargo && MyShopUtils.isSocioPortugal()) 
					pvo = commerceToolsService.getPvoOriginForPortugalDouble(productoPreconfigurado, grupoPrecio, price);
				else
					pvo = MyShopUtils.formatMoneyDouble(price.getValue());
				
				if (infoPack.getValorDescuentoGlobal() != null) {
					double descuento = (pvo * infoPack.getValorDescuentoGlobal()) /100;
					double pvoRound = pvo - descuento;
					return String.valueOf(MyShopUtils.round(pvoRound, 2));
					
				} else {
					throw new Exception("ValorDescuentoGlobal no informado en el pack " + product.getMasterVariant().getSku());
				}
			}
			case "INDIVIDUAL-CERRADO": { //deprecated
				Price price = commerceToolsService.getPriceBycustomerGroup(grupoPrecio, variantProductoPack.getPrices());
				if (tieneRecargo && MyShopUtils.isSocioPortugal()) 
					return commerceToolsService.getPvoOriginForPortugal(variantProductoPack, grupoPrecio, price);
				else
					return MyShopUtils.formatTypedMoney(price.getValue());
			}
			case "INDIVIDUAL": {
				String tipoPrecioVariante = agrupadordto.getTipoPrecioVariante();
				if (tipoPrecioVariante != null) {
					switch (tipoPrecioVariante) {
						case "PVO-CERRADO": { //ponemos el precio de la variante del pack
							Price price = commerceToolsService.getPriceBycustomerGroup(grupoPrecio, variantProductoPack.getPrices());
							if (MyShopUtils.isSocioPortugal()) { //variant es la variante del pack, no va a tener recargo ni estar en la bbdd
								double precioVariantePack = MyShopUtils.formatMoneyDouble(price.getValue());
								double precioVarianteRecargo = precioVariantePack * 1.05; //incrementamos un 5%
								return MyShopUtils.formatDoubleString(precioVarianteRecargo);
								
								//return commerceToolsService.getPvoOriginForPortugal(variantProductoPack, grupoPrecio, price);
							} else
								return MyShopUtils.formatTypedMoney(price.getValue());
						}
						case "DESCUENTO": {
							if ((agrupadordto.getDescuentoVariante()!= null) && (!agrupadordto.getDescuentoVariante().isEmpty())){
								try {
									double descuentoVariante = Double.valueOf(agrupadordto.getDescuentoVariante().substring(0, agrupadordto.getDescuentoVariante().indexOf("%")));
									
									Price price = commerceToolsService.getPriceBycustomerGroup(grupoPrecio, productoPreconfigurado.getPrices());
									double pvo = 0.0;
									if (tieneRecargo && MyShopUtils.isSocioPortugal())
										pvo = commerceToolsService.getPvoOriginForPortugalDouble(productoPreconfigurado, grupoPrecio, price);
									else
										pvo = MyShopUtils.formatMoneyDouble(price.getValue());
									double descuento = (pvo * descuentoVariante) /100;
									double pvoRound = pvo - descuento;
									return String.valueOf(MyShopUtils.round(pvoRound, 2));
									//return String.valueOf(MyShopUtils.round(pvoRound, 4));
									
								} catch (NumberFormatException e) {
									throw new Exception("descuentoVariante no se ha podido transformar a double " + agrupadordto.getDescuentoVariante());
								}
							} else {
								throw new Exception("descuentoVariante no informado en la variante del pack " + product.getMasterVariant().getSku());
							}
						}
						case "CIONELAB": {
							//el valor se calcula en la app de CioneLab
							return null;
						} 
					} 
				} else
					throw new Exception("No se ha informado tipoPrecioVariante en  " + product.getMasterVariant().getSku());
				}
		}
		
		return pvoPack;
	}
	
	/*
	 * inicializa en el objeto agrupadordto los campos PvoOrigin, PvoPack y PvoAllLines*/
	private void inicializarListaAgrupadoresPrecios(int index, AgrupadorDTO agrupadordto, InfoPackGenericoDTO infoPack, String grupoPrecio, ProductProjection product, ProductVariant variant) {
		String tipoPrecioPack = agrupadordto.getTipoPrecioPack();
		
		
		//falta pvoOrigin y PVO Pack
		
		switch (tipoPrecioPack) {
		/*case "CERRADO": {
			//es el mismo precio para todo el pack solo lo podemos en la master variant
//			Price price = commerceToolsService.getPriceBycustomerGroup(variantDTOPack.getGruposPrecio(), masterVariant.getPrices());
			
			//setear pvoPackCerrado
			agrupadordto.setPvoOrigin(null);//aun no se ha seleccionado
			if ((index == 0) && agrupadordto.isMaster()) { // si es el primer elemento es al que añadimos el precio de la master
				String price = "0.00";
				if (MyShopUtils.hasAttribute("pvoPackCerrado", product.getMasterVariant().getAttributes())) {
					CentPrecisionMoney money = (CentPrecisionMoney) MyShopUtils.findAttribute("pvoPackCerrado", product.getMasterVariant().getAttributes()).getValue();
					price = MyShopUtils.formatTypedMoney(money);
	    		}
				agrupadordto.setPvoPack(price);	
			} else
				agrupadordto.setPvoPack("0.00");
			break;
		}*/
		case "GLOBAL": {
			agrupadordto.setPvoOrigin(null);//aun no se ha seleccionado
			//if (agrupadordto.isMaster()) {  //REVISAR!!!!
				agrupadordto.setDescuentoGlobal(infoPack.getDescuentoGlobal());
				agrupadordto.setValorDescuentoGlobal(infoPack.getValorDescuentoGlobal());
				agrupadordto.setPorcentaje(infoPack.isPorcentaje());
				agrupadordto.setValor(infoPack.isValor());
			//}
			
			//agrupadordto.setPvoPack("PRECIO PRODUCTO CON " + infoPack.getDescuentoGlobal());
			break;
		}
		case "INDIVIDUAL-CERRADO": { //deprecated
			Price price = commerceToolsService.getPriceBycustomerGroup(grupoPrecio, variant.getPrices());
			agrupadordto.setPvoOrigin(null);//aun no se ha seleccionado
			agrupadordto.setPvoPack(MyShopUtils.formatTypedMoney(price.getValue()));
			break;
		}
		
		case "INDIVIDUAL": {
			//recuperamos el campo tipoPrecioVariante  PVO-CERRADO, DESCUENTO, CIONELAB
			
			//AÑADIMOS LOS ATRIBUTOS QUE SON A NIVEL DE VARIANTE Y QUE NO HEMOS AÑADIDO EN new InfoPackGenericoDTO
			if (MyShopUtils.hasAttribute("tipoPrecioVariante", variant.getAttributes())) {
				String tipoPrecioVariante = ((AttributePlainEnumValue) MyShopUtils.findAttribute("tipoPrecioVariante", variant.getAttributes()).getValue()).getKey();
				if (tipoPrecioVariante != null) {
					agrupadordto.setTipoPrecioVariante(tipoPrecioVariante);
					switch (tipoPrecioVariante) {
					case "PVO-CERRADO": {
						Price price = commerceToolsService.getPriceBycustomerGroup(grupoPrecio, variant.getPrices());
						agrupadordto.setPvoOrigin(null);//aun no se ha seleccionado
						
//						boolean tieneRecargo = false;
//				        if (MyShopUtils.hasAttribute(MyshopConstants.TIENERECARGO, variant.getAttributes()))
//				        	tieneRecargo = (Boolean) MyShopUtils.findAttribute(MyshopConstants.TIENERECARGO, variant.getAttributes()).getValue();
						if (MyShopUtils.isSocioPortugal()) { //variant es la variante del pack, no va a tener recargo ni estar en la bbdd
							double precioVariantePack = MyShopUtils.formatMoneyDouble(price.getValue());
							double precioVarianteRecargo = precioVariantePack * 1.05; //incrementamos un 5%
							agrupadordto.setPvoPack(MyShopUtils.formatDoubleString(precioVarianteRecargo));
							//agrupadordto.setPvoPack(commerceToolsService.getPvoOriginForPortugal(variant, grupoPrecio, price)); descomentar si añaden recargos a las variantes del pack y lo añaden en bbdd
						}else
							agrupadordto.setPvoPack(MyShopUtils.formatTypedMoney(price.getValue()));
						
						agrupadordto.setTipoPrecioVariante(tipoPrecioVariante);
						break;							
					}
					case "DESCUENTO": {
						if (MyShopUtils.hasAttribute("descuentoVariante", variant.getAttributes())) {
							String descuentoVariante = (String) MyShopUtils.findAttribute("descuentoVariante", variant.getAttributes()).getValue();
							if ((descuentoVariante!= null) && (!descuentoVariante.isEmpty())){
								agrupadordto.setDescuentoVariante(descuentoVariante);
								if (descuentoVariante.contains("%")) {
									agrupadordto.setPorcentajeIndividual(true);
									agrupadordto.setValorDescuentoVariante(Double.valueOf(descuentoVariante.substring(0, descuentoVariante.indexOf("%"))));
								}else {
									agrupadordto.setValorIndividual(true);
									agrupadordto.setValorDescuentoVariante(Double.valueOf(descuentoVariante));
								}
							}
						}
						agrupadordto.setPvoOrigin(null);//aun no se ha seleccionado
						agrupadordto.setTipoPrecioVariante(tipoPrecioVariante);
						break;
					}
					case "CIONELAB": {
						//el valor se calcula en la app de CioneLab
						break;
					} 
					default:
						log.error("Opcion tipoPrecioVariante no esperada (" + tipoPrecioVariante + ")", 
								new IllegalArgumentException("Opcion tipoPrecioVariante no esperada (" + tipoPrecioVariante + ")"));
						break;
					}
				} else {
					log.error("Opcion tipoPrecioVariante no esperada (" + tipoPrecioVariante + ")", 
							new IllegalArgumentException("Opcion tipoPrecioVariante no esperada (" + tipoPrecioVariante + ")"));
				}
			}
			break;
		}
		
		default:
			log.error("Opcion tipoPrecioPack no esperada (" + tipoPrecioPack + ")", 
					new IllegalArgumentException("Opcion tipoPrecioPack no esperada (" + tipoPrecioPack + ")"));
			break;
		}
		if (agrupadordto.getPvoPack() != null) {
			Double pvo = Double.valueOf(agrupadordto.getPvoPack().replace(',', '.')) * agrupadordto.getUnidadesProductoPack();
			agrupadordto.setPvoAllLines(MyShopUtils.formatDoubleString(pvo));
			//agrupadordto.setPvoAllLines(pvo.toString());
		}
	}
	
	public boolean isProductoPackMultiUnidades(ProductVariant variant) {
		if (MyShopUtils.hasAttribute("unidadesProductoPack", variant.getAttributes())) {
			if (MyShopUtils.hasAttribute("agrupadores", variant.getAttributes())) {
				List<String> listaFiltros = (List<String>) MyShopUtils.findAttribute("agrupadores", variant.getAttributes()).getValue();
				if (listaFiltros.size() == 1) {
					if (listaFiltros.get(0).startsWith("variants.sku=")) 
						return true; 
				}
			}
			return false;
		} else {
			log.error("No hay definidas el numero de unidades para el producto " + variant.getSku());
			return false;
		}
	}
	
	public Integer getUnidadesProductoPackAAñadir(ProductVariant variant) {
		if (MyShopUtils.hasAttribute("unidadesProductoPack", variant.getAttributes())) {
			Integer unidadesProductoPack = ((Long) MyShopUtils.findAttribute("unidadesProductoPack", variant.getAttributes()).getValue()).intValue();
			/*por defecto añadimos una unidad de producto por lo que mostraremos una linea por cada unidad del pack para que el usuario 
			 * pueda seleccionar los productos de cada tipo hasta completar las unidadesProductoPack
			 * salvo en los casos en que el agrupador sea por un sku especifico (relacion 1 1) en cuyo caso añadimos 
			 * tantas unidades como unidadesProductoPack*/
			
			//comprobamos si hay un solo sku (packs con relacion 1-1, es decir el producto del pack es uno especifico)
			if (MyShopUtils.hasAttribute("agrupadores", variant.getAttributes())) {
				List<String> listaFiltros = (List<String>) MyShopUtils.findAttribute("agrupadores", variant.getAttributes()).getValue();
				if (listaFiltros.size() == 1) {
					if (listaFiltros.get(0).startsWith("variants.sku=")) 
						return unidadesProductoPack; //solo tiene un sku mostrara una unica linea en la pagina detalle pack y añadira la cantidad de unidadesProductoPack
				}
			}
			return 1; //para el resto de caso mostrara unidadesProductoPack lineas en la pagina detalle pack y añadira la cantidad de 1
		} else {
			log.error("No hay definidas el numero de unidades para el producto " + variant.getSku());
			return null;
		}
	}
	
	public Integer getLineasPaginaDetalle(ProductVariant variant) {
		if (MyShopUtils.hasAttribute("unidadesProductoPack", variant.getAttributes())) {
			Integer unidadesProductoPack = ((Long) MyShopUtils.findAttribute("unidadesProductoPack", variant.getAttributes()).getValue()).intValue();
			/*por defecto añadimos una unidad de producto por lo que mostraremos una linea por cada unidad del pack para que el usuario 
			 * pueda seleccionar los productos de cada tipo hasta completar las unidadesProductoPack
			 * salvo en los casos en que el agrupador sea por un sku especifico (relacion 1 1) en cuyo caso añadimos 
			 * tantas unidades como unidadesProductoPack*/
			
			//comprobamos si hay un solo sku (packs con relacion 1-1, es decir el producto del pack es uno especifico)
			if (MyShopUtils.hasAttribute("agrupadores", variant.getAttributes())) {
				List<String> listaFiltros = (List<String>) MyShopUtils.findAttribute("agrupadores", variant.getAttributes()).getValue();
				if (listaFiltros.size() == 1) {
					if (listaFiltros.get(0).startsWith("variants.sku=")) 
						return 1; //solo tiene un sku mostrara una unica linea en la pagina detalle pack y añadira la cantidad de unidadesProductoPack
				}
			}
			return unidadesProductoPack; //para el resto de caso mostrara unidadesProductoPack lineas en la pagina detalle pack y añadira la cantidad de 1
		} else {
			log.error("No hay definidas el numero de unidades para el producto " + variant.getSku());
			return null;
		}
	}
	
	private void inicializarListaAgrupadores(List<AgrupadorDTO> listaAgrupadores, ProductVariant masterVariant, 
			VariantDTO variantDTOPack, InfoPackGenericoDTO infoPack, ProductProjection product) throws Exception {
		//MASTER DEL PRODUCTO PACK
		if (MyShopUtils.hasAttribute("unidadesProductoPack", masterVariant.getAttributes())) {
			//Integer unidadesProductoPack = ((Long) MyShopUtils.findAttribute("unidadesProductoPack", masterVariant.getAttributes()).getValue()).intValue();
			Integer unidadesProductoPack = getLineasPaginaDetalle(masterVariant);
			for(int i=0; i<unidadesProductoPack; i++) {
				AgrupadorDTO agrupadordto = getAgrupador(masterVariant); //objeto agrupador con los parametros sameforVariant y el listado de agrupadores
				agrupadordto.setMaster(true);
				if (agrupadordto.getSkuProductoPackPreconfigurado() != null) {  //packs con relacion 1-1
					//es un producto que tenemos que preconfiguarar
					ProductVariant productoPreconfigurado = commerceToolsService.getProductVariantBysku(agrupadordto.getSkuProductoPackPreconfigurado(), variantDTOPack.getGruposPrecio());
					if (productoPreconfigurado != null) {
						inicializarProductoPreconfigurado(i, agrupadordto, infoPack, variantDTOPack, product, productoPreconfigurado, masterVariant);
					} else
						throw new Exception("EL SKU CONFIGURADO " + agrupadordto.getSkuProductoPackPreconfigurado() + " EN EL PACK " + product.getMasterVariant().getSku() + " dentro de la variante " + variantDTOPack.getSku() + "  NO EXISTE");
				} else //packs con relacion 1-n
					inicializarListaAgrupadoresPrecios(i, agrupadordto, infoPack, variantDTOPack.getGruposPrecio(), product, masterVariant);
				listaAgrupadores.add(agrupadordto);
			}
		}
		
		
		//VARIANTES DEL PRODUCTO PACK
		for(ProductVariant variant: product.getVariants()) {
			if (MyShopUtils.hasAttribute("unidadesProductoPack", variant.getAttributes())) {
				Integer unidadesProductoPack = getLineasPaginaDetalle(variant);
				for(int i=0; i<unidadesProductoPack; i++) {
					AgrupadorDTO agrupadordto = getAgrupador(variant);
					agrupadordto.setMaster(false);
					
					if (agrupadordto.getSkuProductoPackPreconfigurado() != null) {
						ProductVariant productoPreconfigurado = commerceToolsService.getProductVariantBysku(agrupadordto.getSkuProductoPackPreconfigurado(), variantDTOPack.getGruposPrecio());
						if (productoPreconfigurado != null) {
							inicializarProductoPreconfigurado(i, agrupadordto, infoPack, variantDTOPack, product, productoPreconfigurado, variant);
						} else 
							throw new Exception("EL SKU CONFIGURADO " + agrupadordto.getSkuProductoPackPreconfigurado() + " EN EL PACK " + product.getMasterVariant().getSku() + " dentro de la variante " + variant.getSku() + "  NO EXISTE");
					}else {
						inicializarListaAgrupadoresPrecios(i, agrupadordto, infoPack, variantDTOPack.getGruposPrecio(), product, variant);
					}
					listaAgrupadores.add(agrupadordto);
				}
			}
		}
	}
	
	
	private void inicializarProductoPreconfigurado(int index, AgrupadorDTO agrupadordto, InfoPackGenericoDTO infoPack, 
			VariantDTO variantDTOPack, ProductProjection product, ProductVariant productoPreconfigurado, ProductVariant variantPack) throws Exception {
		agrupadordto.setConfigurado(true);
        
        //elementoPack.setIdCarritoOculto(updatedCart.getId());
        //elementoPack.setLineItemIdOculto(lineItemId);
        
		agrupadordto.setSkuProductoConfigurado(productoPreconfigurado.getSku());
        
        Price price = commerceToolsService.getPriceBycustomerGroup(variantDTOPack.getGruposPrecio(), productoPreconfigurado.getPrices());	
        
        
        boolean tieneRecargo = false;
        String grupoPrecio = commerceToolsService.getGrupoPrecioCommerce();
        if (MyShopUtils.hasAttribute(MyshopConstants.TIENERECARGO, productoPreconfigurado.getAttributes())) 
        	tieneRecargo = (Boolean) MyShopUtils.findAttribute(MyshopConstants.TIENERECARGO, productoPreconfigurado.getAttributes()).getValue();
        
        if (tieneRecargo && MyShopUtils.isSocioPortugal()) {
        	agrupadordto.setPvoOrigin(commerceToolsService.getPvoOriginForPortugal(productoPreconfigurado, grupoPrecio, price));
        } else
        	agrupadordto.setPvoOrigin(MyShopUtils.formatTypedMoney(price.getValue()));
        
        if (MyShopUtils.hasAttribute("tipoPrecioVariante", variantPack.getAttributes()))
        	agrupadordto.setTipoPrecioVariante(((AttributePlainEnumValue) MyShopUtils.findAttribute("tipoPrecioVariante", variantPack.getAttributes()).getValue()).getKey());
		
    	if (MyShopUtils.hasAttribute("descuentoVariante", variantPack.getAttributes())) 
    		agrupadordto.setDescuentoVariante((String) MyShopUtils.findAttribute("descuentoVariante", variantPack.getAttributes()).getValue());
    	
    	agrupadordto.setValorDescuentoGlobal(infoPack.getValorDescuentoGlobal());
        	
        agrupadordto.setPvoPack(pvoPackFromTipoPrecioPack(index, agrupadordto, infoPack, variantDTOPack.getGruposPrecio(), product, variantPack, productoPreconfigurado));
        
        agrupadordto.setTipoPrecioPack(infoPack.getTipoPrecioPack());
        
        if (MyShopUtils.hasAttribute("nombreArticulo", productoPreconfigurado.getAttributes()))
        	agrupadordto.setNombreProductoConfigurado(((String) MyShopUtils.findAttribute("nombreArticulo", productoPreconfigurado.getAttributes()).getValue()));
        
        String urlImagen = CioneUtils.getURLHttps() + "/" + MyshopConstants.defaultProductLogo(MgnlContext.getLocale());
        if ( (productoPreconfigurado.getImages() != null) && (productoPreconfigurado.getImages().size() > 0) )
        	urlImagen = productoPreconfigurado.getImages().get(0).getUrl();
        agrupadordto.setUrlImagen(urlImagen);
        
        //precio total del pack incluidas las unidades
        
        double doubleValue = Double.valueOf(agrupadordto.getPvoPack().replace(',', '.'));
        doubleValue = doubleValue * agrupadordto.getUnidadesProductoPack();
        //Long pvo = Long.valueOf(agrupadordto.getPvoPack().replace(".", ",")) * agrupadordto.getUnidadesProductoPack();
        agrupadordto.setPvoAllLines(MyShopUtils.formatDoubleString(doubleValue));
	}
	
	private List<AgrupadorDTO> cloneAgrupadores(List<AgrupadorDTO> listAgrupadores) {
		List<AgrupadorDTO> listaClonada = new ArrayList<>();
		for(AgrupadorDTO agrupador: listAgrupadores)
			listaClonada.add(agrupador.clone());
		return listaClonada;
	}
	
	/*Devuelve un objeto agrupado con los parametros sameforVariant, el listado de agrupadores y si es o no masterVariant
	 * @param ProductVariant con la variante del producto Pack que vamos a inicializar
	 * */
	private AgrupadorDTO getAgrupador(ProductVariant productVariant) {
		AgrupadorDTO agrupador = new AgrupadorDTO();

		if (MyShopUtils.hasAttribute("tipoProductoPack", productVariant.getAttributes())) {
			agrupador.setTipoProductoPack(((AttributePlainEnumValue) MyShopUtils.findAttribute("tipoProductoPack", productVariant.getAttributes()).getValue()).getLabel());
			agrupador.setTipoProductoPackKey(((AttributePlainEnumValue) MyShopUtils.findAttribute("tipoProductoPack", productVariant.getAttributes()).getValue()).getKey());
		}
		if (MyShopUtils.hasAttribute("tipoPrecioPack", productVariant.getAttributes()))
			agrupador.setTipoPrecioPack(((AttributePlainEnumValue) MyShopUtils.findAttribute("tipoPrecioPack", productVariant.getAttributes()).getValue()).getKey());
		
		if (MyShopUtils.hasAttribute("tipoPrecioVariante", productVariant.getAttributes()))
			agrupador.setTipoPrecioVariante(((AttributePlainEnumValue) MyShopUtils.findAttribute("tipoPrecioVariante", productVariant.getAttributes()).getValue()).getKey());
		
		if (MyShopUtils.hasAttribute("agrupadores", productVariant.getAttributes())) {
			List<String> listaFiltros = (List<String>) MyShopUtils.findAttribute("agrupadores", productVariant.getAttributes()).getValue();
			List<String> listaFiltrosAux = new ArrayList<String>(); 
			List<String> listaSkuProductosPackPreconfigurado = new ArrayList<String>(); 
			//transformar la key de la categoria por id
			listaFiltros.forEach(filtro-> {
				if (filtro.contains("category="))
					listaFiltrosAux.add("category="+ categoryUtils.getCategoryByKey(filtro.substring(9, filtro.length())).getId());
				else if (filtro.startsWith("variants.sku=")) {
					if (listaFiltros.size() == 1) { //si solo tiene un elemento es relacion 1-1
						agrupador.setSkuProductoPackPreconfigurado(filtro.substring(13, filtro.length()));
					} else {
						listaSkuProductosPackPreconfigurado.add(filtro.substring(13, filtro.length()));
					}
					listaFiltrosAux.add(filtro);					
				}else if (filtro.startsWith("pack=")) {
					//es pack lentes + montura
					agrupador.setPackLentes(filtro.substring(5));
					agrupador.setHabilitar(false);
					listaFiltrosAux.add(filtro);
				} else //variants.attributes
					listaFiltrosAux.add(filtro);
			});

			
			if (!listaSkuProductosPackPreconfigurado.isEmpty()) {
				agrupador.setListadoSkuProductosPackPreconfigurados(listaSkuProductosPackPreconfigurado);
				
				//listaFiltrosAux.add("variants.sku=" + joinToString(listaSkuProductosPackPreconfigurado, "//|"));
				
			}
			
			agrupador.setAgrupadores(listaFiltrosAux);
		}
		//agrupador.setUnidadesProductoPack(1);
		agrupador.setUnidadesProductoPack(getUnidadesProductoPackAAñadir(productVariant));
		
		return agrupador;
	}
	
	private String joinToString(List<String> lista, String separador) {
		if ((lista == null) || lista.isEmpty())
			return "";
		else
			return String.join(String.valueOf(separador), lista);
	}
	
	
	private List<String> getVariants(ProductProjection product) {
		List<String> list = new ArrayList<>();
		commerceToolsServiceAux.getAllVariants(product).forEach(pvar->list.add(pvar.getSku()));
		
		//product.getAllVariants().forEach(pvar->list.add(pvar.getSku()));
		return list;
	}
	
	/*
	 * Metedo que a partir de un listado tipo Set devuelve el listado de String Ordenado
	 */
	public List<String> getListFromSetOrder(ArrayList<String> listSet) {
		try {
			Collections.sort(listSet);
		} catch (Exception e) {
			log.debug("ERROR al parsear del tipoSet a la lista");
		}
		return listSet;
	}
	
//	/*
//	 * Metedo que a partir de un listado tipo Set devuelve el listado de String sin ordenar
//	 */
//	public List<String> getListFromSet(ArrayList<String> listSet) {
//		List<String> list = new ArrayList<String>();
//		try {
//			listSet.forEach(power->list.add(power));
//		} catch (Exception e) {
//			log.debug("ERROR al parsear del tipoSet a la lista");
//		}
//		return list;
//	}
	
	
	private Map<String, String> getJsonNodetoHashMap(ArrayList<String> colorcodojson) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		
		//String colorcodostr = colorcodojson.toString();//.replace("\"", "").replace("[","").replace("]", "");
		//List<String> colorcodolist = new ArrayList<>(Arrays.asList(colorcodostr.split(",")));
		colorcodojson.removeIf(n -> isBlank(n));
		
		//Collections.sort(colorcodolist);
		
		colorcodojson.forEach((p)-> {
			StringTokenizer tok = new StringTokenizer(p, "|");
			if (tok.hasMoreElements()) {
				String tok1 = tok.nextToken();
				if (tok.hasMoreElements()) {
					String tok2 = tok.nextToken();
					map.put(tok1, tok2);
				} else {
					map.put(tok1, tok1);
				}
			}
		});
		
		return map;
	}

	/*Revisar este metodo por si podemos pasarle un listado de skus
	 * devuelve un map con sku, name*/
	private Map<String,String> getNamesBySKU(List<String> headsetlist) {
		String grupoPrecio = commerceToolsService.getGrupoPrecioCommerce();
		TreeMap<String,String> res = new TreeMap<>();
		if (headsetlist != null && !headsetlist.isEmpty()) {
			String skusString = headsetlist.stream().map(sku -> "\"" + sku + "\"").collect(Collectors.joining(", "));
			skusString = skusString.replace(" ", "");
			List<ProductProjection> productos = cioneEcommerceConectionProvider.getApiRoot().productProjections().search().get()
					.withFilter("variants.sku:" + skusString)
					.addFilter("variants.attributes.gruposPrecio:\""+ grupoPrecio +"\"")
					.addLimit(100)
					.executeBlocking()
					.getBody()
					.getResults();
			
			if (productos != null && !productos.isEmpty() && productos.get(0) != null) {
				for (ProductProjection product : productos) {
					res.put(product.getMasterVariant().getSku(), product.getName().get(MyshopConstants.esLocale));
				}
				//res.put(headsetname, productos.get(0).getName().get(MyshopConstants.esLocale));
			}
		}
		
		/*if (headsetlist != null && !headsetlist.isEmpty()) {
				QueryPredicate<ProductProjection> predicate = ProductProjectionQueryModel.of().allVariants().where(m -> m.sku().isIn(headsetlist));
				Query<ProductProjection> query = ProductProjectionQuery.ofCurrent().withPredicates(predicate).withLimit(100);
				CompletionStage<PagedQueryResult<ProductProjection>> product_query = cioneEcommerceConectionProvider.getClient().execute(query);
				PagedQueryResult<ProductProjection> queryresult = product_query.toCompletableFuture().join();
				List<ProductProjection> productos = queryresult.getResults();
				
				if (productos != null && !productos.isEmpty() && productos.get(0) != null) {
					for (ProductProjection product : productos) {
						res.put(product.getMasterVariant().getSku(), product.getName().get(MyshopConstants.esLocale));
					}
				}
		}*/
		
		return res;
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
				
			} catch (SQLException e) {
				log.error("DetalleServiceImpl: Error al obtener la diseno de lentillas en la BBDD", e);
			}
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		return disenos.isEmpty() ? null : disenos;
	}
	
	private PreparedStatement createPSDiseno(Connection con, String codigocentral) throws SQLException {
		
		String sql = "SELECT DISTINCT xtipodepieza FROM dbo.cio_mys_lentesnoconfigurables " + 
					 "WHERE xcodigocentral = '" + codigocentral + "' AND xtipodepieza != '' ORDER BY xtipodepieza DESC;";

		return con.prepareStatement(sql);
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

	@Override
	public String getSkuByKey(String key) {
    	String sku = null;
    	/*try {
			final ProductProjectionByKeyGet request = ProductProjectionByKeyGet.ofCurrent(key);
			CompletionStage<ProductProjection> product_query = cioneEcommerceConectionProvider.getClient().execute(request);
			ProductProjection product = product_query.toCompletableFuture().join();
			if (product.getMasterVariant() != null)
			sku = product.getMasterVariant().getSku();
		} catch (Exception e) {
			log.error(e.getMessage());
		}*/
    	
    	//check();
		
    	return sku;
	}
	
	/*private void check() {
		try {
	    	CustomerCT customer = commerceToolsService.getCustomer();
	    	String customerId = customer.getId();
			Cart cart = null;
			QueryPredicate<Cart> predicate1 = CartQueryModel.of().customerId().is(customerId);
			QueryPredicate<Cart> predicate2 = CartQueryModel.of().cartState().is(CartState.ACTIVE);
			QueryPredicate<Cart> predicatePeriodic = CartQueryModel.of().custom().fields().ofString("cartType").is(MyshopConstants.BUDGET);
			Query<Cart> query = CartQuery.of().withPredicates(predicate1.and(predicate2).and(predicatePeriodic));
			
			CompletionStage<PagedQueryResult<Cart>> cart_query = cioneEcommerceConectionProvider.getClient().execute(query);
			PagedQueryResult<Cart> actualCarts = cart_query.toCompletableFuture().join();
			
			cart  = actualCarts.head().get();
			pservice.checkMail(cart, "msanchezp@atsistemas.com; jgarcia.navarro@atsistemas.com; msanchezpostigo@gmail.com; jesuli2000@yahoo.es");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}*/
	
	
	private Category getCategoryById(String id) {
		Category result = null;
		
		result = cioneEcommerceConectionProvider
			.getApiRoot()
			.categories()
			.withId(id)
			.get()
			.executeBlocking()
			.getBody()
			.get();
		
		
        return result;
		
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
		String codigoColorSel = "00";
		if (MyShopUtils.hasAttribute("codigoColor", variantSel.getAttributes()))
			codigoColorSel = (String) MyShopUtils.findAttribute("codigoColor", variantSel.getAttributes()).getValue();
		
		String colorSel = codigoColorSel + colorIconoSel; //color de la variante seleccionada
	
		Map <String, List<ColorDTO>> colors_calibres = new HashMap <String, List<ColorDTO>> ();
		Map <String, List<ColorDTO>> colors_graduaciones = new HashMap <String, List<ColorDTO>> ();
		
		for (ProductVariant variant : listAllVariants) {
			String colorIcono = "#ZZZZZZ";
			if (MyShopUtils.hasAttribute("colorIcono", variant.getAttributes()))
				colorIcono = (String) MyShopUtils.findAttribute("colorIcono", variant.getAttributes()).getValue();
			String codigoColor = "00";
			if (MyShopUtils.hasAttribute("codigoColor", variant.getAttributes()))
				codigoColor = (String) MyShopUtils.findAttribute("codigoColor", variant.getAttributes()).getValue();
			String idColor = codigoColor+colorIcono;
    			if (!colorsAux.contains(idColor)) {
    				
    				ColorDTO colorDto = new ColorDTO();
    				colorDto.setCodigoColor(codigoColor);
    				if (MyShopUtils.hasAttribute("colorMontura", variant.getAttributes()))
    					colorDto.setColorMontura(((LocalizedString) MyShopUtils.findAttribute("colorMontura", variant.getAttributes()).getValue()).get(MyshopConstants.esLocale));
    				colorDto.setIdColor(idColor);
    				colorDto.setColorIcono(colorIcono);
    				if (idColor.equals(colorSel))
    					colorDto.setSelected(true);
    				colorDto.setSku(variant.getSku());
    				colorsDtoAux.add(colorDto);
    				
    				colorsAux.add(idColor);
    				
    				List<ColorDTO> calibres = new ArrayList<ColorDTO>();
    				if (MyShopUtils.hasAttribute("dimensiones_ancho_ojo", variant.getAttributes())
    					&& !(((Long) MyShopUtils.findAttribute("dimensiones_ancho_ojo", variant.getAttributes()).getValue()).toString()).isEmpty()) {
    					
						String calibre = ((Long) MyShopUtils.findAttribute("dimensiones_ancho_ojo", variant.getAttributes()).getValue()).toString();
	    				ColorDTO infoCalibre = new ColorDTO();
	    				infoCalibre.setCalibre(calibre);
	    				infoCalibre.setColorIcono(colorIcono);
	    				infoCalibre.setCodigoColor(codigoColor);
	    				infoCalibre.setIdColor(idColor);
	    				if (MyShopUtils.hasAttribute("colorMontura", variant.getAttributes()))
	    					infoCalibre.setColorMontura(((LocalizedString) MyShopUtils.findAttribute("colorMontura", variant.getAttributes()).getValue()).get(MyshopConstants.esLocale));
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
	    				String graduacion = (String) MyShopUtils.findAttribute("graduacion", variant.getAttributes()).getValue();
	    				ColorDTO infoGraduacion = new ColorDTO();
	    				infoGraduacion.setGraduacion(graduacion);
	    				infoGraduacion.setColorIcono(colorIcono);
	    				infoGraduacion.setCodigoColor(codigoColor);
	    				infoGraduacion.setIdColor(idColor);
	    				if (MyShopUtils.hasAttribute("colorMontura", variant.getAttributes()))
	    					infoGraduacion.setColorMontura(((LocalizedString) MyShopUtils.findAttribute("colorMontura", variant.getAttributes()).getValue()).get(MyshopConstants.esLocale));
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
        					&& !(((Long) MyShopUtils.findAttribute("dimensiones_ancho_ojo", variant.getAttributes()).getValue())).toString().isEmpty()) {
    					String calibre = ((Long) MyShopUtils.findAttribute("dimensiones_ancho_ojo", variant.getAttributes()).getValue()).toString();
    					ColorDTO infoCalibre = new ColorDTO();
	    				infoCalibre.setCalibre(calibre);
	    				infoCalibre.setColorIcono(colorIcono);
	    				infoCalibre.setCodigoColor(codigoColor);
	    				infoCalibre.setIdColor(idColor);
	    				if (MyShopUtils.hasAttribute("colorMontura", variant.getAttributes()))
	    					infoCalibre.setColorMontura(((LocalizedString) MyShopUtils.findAttribute("colorMontura", variant.getAttributes()).getValue()).get(MyshopConstants.esLocale));
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
    					String graduacion = (String) MyShopUtils.findAttribute("graduacion", variant.getAttributes()).getValue(); //variant.getAttribute("graduacion").getValueAsString();
	    				ColorDTO infoGraduacion = new ColorDTO();
	    				infoGraduacion.setGraduacion(graduacion);
	    				infoGraduacion.setColorIcono(colorIcono);
	    				infoGraduacion.setCodigoColor(codigoColor);
	    				infoGraduacion.setIdColor(idColor);
	    				if (MyShopUtils.hasAttribute("colorMontura", variant.getAttributes()))
	    					infoGraduacion.setColorMontura(((LocalizedString) MyShopUtils.findAttribute("colorMontura", variant.getAttributes()).getValue()).get(MyshopConstants.esLocale));
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
				color.setCalibres(colors_calibres.get(color.getIdColor()));
				color.setGraduaciones(colors_graduaciones.get(color.getIdColor()));
				
					colorsDto.add(color);
			}
		
		return colorsDto;  
	}
	
	
	private boolean localizeCaliber(List<ColorDTO> colors, ColorDTO infoCalibre) {
		for (ColorDTO color : colors) {
			if (color.getCalibre().equals(infoCalibre.getCalibre())) {
				return true;
			}
		}
		return false;
	}
	
    private String getTipoProductoPackKey(String tipoProductoPackLabel) {
    	ProductType productType = cioneEcommerceConectionProvider.getApiRoot().productTypes().withKey("generic_packs").get().executeBlocking().getBody();
    	Optional<AttributeDefinition> attributeDefinitionOpt = productType.getAttributes().stream()
                .filter(attr -> attr.getName().equals("tipoProductoPack"))
                .findFirst();
    	if (attributeDefinitionOpt.isPresent()) {
    		AttributeEnumType enumType = (AttributeEnumType) attributeDefinitionOpt.get().getType();
    		AttributePlainEnumValue valueParametro = enumType.getValues().stream().filter(enumValue -> enumValue.getLabel().equals(tipoProductoPackLabel)).findFirst().orElse(null);
    		return valueParametro.getKey();
    	}
    	
    	return tipoProductoPackLabel;
    }
    
	
	/*private String getProductType(ProductProjection product) {
		String productType=MyshopConstants.GENERIC_PRODUCT;
		try {
			CompletionStage<PagedQueryResult<ProductType>> queryResultFuture = 
					cioneEcommerceConectionProvider.getClient().execute(ProductTypeQuery.of());
			PagedQueryResult<ProductType> queryresult = queryResultFuture.toCompletableFuture().join();
			List<ProductType> productTypeAll = queryresult.getResults();
			
			productTypeAll.forEach((type) -> {if (type.getId().equals(product.getProductType().getId())) {
				productType = type.getName();
			}});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}*/

}


