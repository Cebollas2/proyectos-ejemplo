package com.magnolia.cione.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.models.product.ProductVariant;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.AgrupadorDTO;
import com.magnolia.cione.dto.MasterProductFrontDTO;
import com.magnolia.cione.dto.ProductFrontDTO;
import com.magnolia.cione.dto.VariantDTO;
import com.magnolia.cione.dto.CT.ProductSearchCT;
import com.magnolia.cione.service.CartService;
import com.magnolia.cione.service.CommercetoolsService;
import com.magnolia.cione.service.FittingBoxService;
import com.magnolia.cione.service.ProductService;
import com.magnolia.cione.utils.MyShopUtils;

import info.magnolia.context.MgnlContext;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class ListadoProductosFilterModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	private static final Logger log = LoggerFactory.getLogger(ListadoProductosFilterModel.class);
	private List<MasterProductFrontDTO> products;
	private ProductSearchCT productsearch;
	private int page;
	private int pagination;
	private int count;
	private String skupadre;
	private String skupack;
	private String skuPackMaster;

	private String agrupadores;
	private String step;

	@Inject
	private CommercetoolsService commercetoolsservice;
	
	@Inject
	private TemplatingFunctions templatingFunctions;
	
	@Inject
	private CartService cartService;
	
	@Inject
	private ProductService productService;
	
	@Inject
	private FittingBoxService fittingBoxService;
	
	public ListadoProductosFilterModel(Node content, RD definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
	}
	
	@Override
	public String execute() {
		
		if (!templatingFunctions.isEditMode()) {
			
			String categoryid = null;
			if (MgnlContext.getWebContext().getRequest().getParameter("category") != null)
				categoryid = MgnlContext.getWebContext().getRequest().getParameter("category");
			else{
				try {
					Node nodePage = templatingFunctions.page(content);
					if (nodePage.hasProperty(MyshopConstants.CATEGORY))
						categoryid = nodePage.getProperty(MyshopConstants.CATEGORY).getValue().getString();
				} catch (RepositoryException e1) {
					log.debug("No tiene categoria asociada a la pagina");
				}
			}
			
			String skupadreparameter = MgnlContext.getWebContext().getRequest().getParameter(MyshopConstants.FF_SKUPADRE);
			page = 0;
			pagination = 12;
			count = 0;
			
			//quitar si eliminadmo el submit
			if ((MgnlContext.getWebContext().getRequest().getParameter("agrupadores") != null) 
				&& !MgnlContext.getWebContext().getRequest().getParameter("agrupadores").isEmpty()
				&& (MgnlContext.getWebContext().getRequest().getParameter("skuPackMaster") != null)
				&& !MgnlContext.getWebContext().getRequest().getParameter("skuPackMaster").isEmpty())
					agrupadores = MgnlContext.getWebContext().getRequest().getParameter("agrupadores") 
						+ "&skuPackMaster=" + MgnlContext.getWebContext().getRequest().getParameter("skuPackMaster") ;
			
			if ((MgnlContext.getWebContext().getRequest().getParameter("step") != null) 
					&& !MgnlContext.getWebContext().getRequest().getParameter("step").isEmpty())
				step = MgnlContext.getWebContext().getRequest().getParameter("step");
			
			if (MgnlContext.getWebContext().getRequest().getParameter("skuPackMaster") != null)
				//almacenamos en session los datos del pack en caso de existir
				skuPackMaster = MgnlContext.getWebContext().getRequest().getParameter("skuPackMaster");
			
			try {
				if (content.hasProperty(MyshopConstants.NUMPRODUCT)) {
					String numProduct = content.getProperty(MyshopConstants.NUMPRODUCT).getValue().getString();
					try {
						pagination = Integer.parseInt(numProduct);
					} catch (NumberFormatException e) {
						pagination = 12;
					}
				}
			} catch (Exception e) {
				log.info("Error obteniendo numero productos");
			}	
			
			String pagestr = MgnlContext.getWebContext().getRequest().getParameter("page");
			String paginationstr = MgnlContext.getWebContext().getRequest().getParameter("pagination");
			String countstr = MgnlContext.getWebContext().getRequest().getParameter("count");
			String strsorting = MgnlContext.getWebContext().getRequest().getParameter("sorting");
			
			String ordenconf = "newaltaekon";
			
			try {
				//orderconf = content.getProperty(MyshopConstants.ORDERCONF).getValue().getBoolean();
				if (content.getProperty(MyshopConstants.ORDERCONF) != null) {
					switch(content.getProperty(MyshopConstants.ORDERCONF).getValue().getString()) {
					case "altaEkon":
						ordenconf = "newaltaekon";
						break;
					case "fechacreacion":
						ordenconf = "new";
						break;
					case "alfabetico":
						ordenconf = "alfabetico";
						break;
					default:
						ordenconf = "newaltaekon";
						break;
					}
				}
				
			} catch (RepositoryException e) {
				log.info("orden no configurado");
			}
			
			if (pagestr != null) {
				page = tryParseInt(pagestr,0);
			} 
			
			if (paginationstr != null) {
				pagination = tryParseInt(paginationstr,12);
			} 
			
			if (countstr != null) {
				count = tryParseInt(countstr,0);
			} 
			
			if (strsorting != null) {
				if (strsorting.equals("alfabetico") || strsorting.equals("newaltaekon") || strsorting.equals("lowtohigh") || strsorting.equals("hightolow")) {
					ordenconf = strsorting;
				}
			}
			
//			if (orderconf && !strsorting.equals("lowtohigh") && !strsorting.equals("hightolow")) {
//				strsorting = "new";
//			}
//			
			if (skupadreparameter != null && !skupadreparameter.isEmpty()) {
				skupadre = skupadreparameter;
			}
			
			skupack = MgnlContext.getWebContext().getRequest().getParameter("skupack");

			if ((categoryid != null) && (!categoryid.isEmpty()))
				categoryid = sanitizerCategoryPath(categoryid);
			
			Map<String, String[]> filters = getAllFilters();
			
			ArrayList<String> skus = new ArrayList<>();
			
			Map<String, String[]> parametros = MgnlContext.getWebContext().getRequest().getParameterMap();
			for (var entry : parametros.entrySet()) {
				String key = entry.getKey();
				if (key.indexOf("variants.sku") != -1) {
					String[] valores = MgnlContext.getWebContext().getRequest().getParameterValues(key);
					for (int i=0; i<valores.length; i++) {
						skus.add(valores[i]);
					}
					
					/*for (int i=0; i<valores.length; i++) {
						String valor = valores[i];
						String[] skuList = valor.split("\\|");
						for (int j=0; j<skuList.length; j++) {
							skus.add(skuList[j]);efrrf
						}
					}*/
				}
			}
			
			try {
				if (content.hasProperty(MyshopConstants.HIDEFILTER)) {
					StringTokenizer st = new StringTokenizer(content.getProperty(MyshopConstants.HIDEFILTER).getValue().getString(), "=");
					if (st.countTokens() == 2) {
						String filtro = st.nextToken();
						String[] value = {st.nextToken()};
						filters.put(filtro, value);
					}
						
				}
			} catch (Exception e) {
				log.info("filtro oculto no configurado");
			}
			productsearch = new ProductSearchCT();
			
			

			//if (((categoryid != null) && !categoryid.isEmpty())) {
				products = getProductWithFilters(categoryid,filters,skus,productsearch,page,pagination,count,ordenconf);
			//}

		}
		
		return super.execute();
		
	}
	
	public int tryParseInt(String value, int defaultVal) {
	    try {
	        return Integer.parseInt(value);
	    } catch (NumberFormatException e) {
	        return defaultVal;
	    }
	}

	/**
	 * Metodo que obtiene todos los filtros aplicados sobre la URL.
	 * No obtiene todos los filtros sistematicamente sino que por
	 * seguridad solo leera los filtros que se hayan incluido
	 * 
	 * @return mapa de nombre del filtro y los aplicados
	 * 
	 */
	private Map<String, String[]> getAllFilters() {
		
		Map<String, String[]> filters = new HashMap<>();
		
		if (MgnlContext.getWebContext().getRequest().getParameterValues(MyshopConstants.FF_PRICE) != null && MgnlContext.getWebContext().getRequest().getParameterValues(MyshopConstants.FF_PRICE).length > 0) {
			filters.put(MyshopConstants.FF_PRICE, MgnlContext.getWebContext().getRequest().getParameterValues(MyshopConstants.FF_PRICE));
		}
		
		Map<String, String[]> parametros = MgnlContext.getWebContext().getRequest().getParameterMap();
		
		for (var entry : parametros.entrySet()) {
			String key = entry.getKey();
			if (key.indexOf("variants.attributes") != -1) {
				String[] valores = MgnlContext.getWebContext().getRequest().getParameterValues(key);
		        String[] parametroAux = new String[valores.length];
		        for (int i = 0; i < valores.length; i++) {
		        	parametroAux[i] = valores[i];
		        } 
		        filters.put(key, parametroAux);
			}
		}
		
		if ((step != null) && (skuPackMaster != null) && (!skuPackMaster.isEmpty())) {
			HashMap<String, List<AgrupadorDTO>> infoPackMapSession = commercetoolsservice.getInfoPackMapSession();
			if ((infoPackMapSession != null) && (infoPackMapSession.get(skuPackMaster) != null)) {
				int pos = Integer.valueOf(MgnlContext.getWebContext().getRequest().getParameter("step"));
				List<String> agrupadorsession = infoPackMapSession.get(skuPackMaster).get(pos).getAgrupadores();
				List<String> agrupadorDTOsessionAux = cloneList(agrupadorsession);
				Map<String, String[]> filtersMandatory = new HashMap<>();
				
				for (String agrupadorobligatorio: agrupadorDTOsessionAux) { //variants.attributes.target.es=niño
					if (agrupadorobligatorio.indexOf("variants.attributes") != -1) {
						String key = agrupadorobligatorio.substring(0, agrupadorobligatorio.indexOf("="));
						String value_str = agrupadorobligatorio.substring(agrupadorobligatorio.indexOf("=")+1, agrupadorobligatorio.length());
						
						if (filtersMandatory.get(key) != null) { //si ya existe lo añadimos
							String[] valueFilterMandatory = filtersMandatory.get(key);
							valueFilterMandatory = addValuesList(valueFilterMandatory, value_str);
							filtersMandatory.put(key, valueFilterMandatory);
							
						} else {
							filtersMandatory.put(key, new String[] {value_str});
						}
						
						filters.put(key, filtersMandatory.get(key)); //pasamos de lo que tuviera y metemos solo estos
					}
				}
			}
		}
		
		/*if ((MgnlContext.getWebContext().getRequest().getParameter(MyshopConstants.MANDATORYFILTERS)!=null) 
			&& (!MgnlContext.getWebContext().getRequest().getParameter(MyshopConstants.MANDATORYFILTERS).isEmpty())) {
			List<String> agrupadoresMandatory = Arrays.asList(MgnlContext.getWebContext().getRequest().getParameter(MyshopConstants.MANDATORYFILTERS).split("#"));
			for (String agrupadorMandatory : agrupadoresMandatory) {
				if (agrupadorMandatory.indexOf("variants.attributes") != -1) {
					String key = agrupadorMandatory.substring(0, agrupadorMandatory.indexOf("="));
					String value_str = agrupadorMandatory.substring(agrupadorMandatory.indexOf("="), agrupadorMandatory.length());
					String[] valueMandatory = value_str.substring(1).split("~");
					if ((filters.get(key)!= null) && (filters.get(key).length >0) ) {						
						filters.put(key, valueMandatory); //lo sustituimos por el obligatorio
					} else {
						//no tenemos el filtro, tenemos que añadirlo
						filters.put(key, valueMandatory);
					}
				}
			}
		}*/
		

		return filters;
	}
	
	private String[] addValuesList(String[] original, String newValue) {
		List<String> list = new ArrayList<>(Arrays.asList(original));
		list.add(newValue);
		return list.toArray(new String[0]);
	}

	/**
	 * 
	 * Magnolia nos devuelve el path completo tal y como
	 * lo almacena en magnolia. Asi que debemos quedarnos
	 * solo con el id de la categoria que corresponde con
	 * la ultima parte del path
	 * 
	 * @param path de la categoria
	 * @return id de la categoria
	 * 
	 */
	private String sanitizerCategoryPath(String path) {
		
		if (path != null) {
		
			String[] parts = path.split("/");
			return parts[parts.length-1];
		} else {
			return null;
		}
		
	}
	
	/**
	 * Metodo que devuele el listado de productos a mostrar segun la paginacion,
	 * ordenacion y filtros aplicados.
	 * 
	 * @param categoryid categoria sobre la que se buscaran los productos
	 * @param filters filtros aplicados sobre la busqueda de productos
	 * @param productsearch total de productos y a mostrar
	 * @param page paginacion
	 * @param strsorting ordenacion
	 * @return lista de productos
	 * 
	 */
	private List<MasterProductFrontDTO> getProductWithFilters(String categoryid, Map<String, String[]> filters, ArrayList<String> skus, ProductSearchCT productsearch, int page, int pagination, int count, String strsorting) {
		
		//String keycustomergroup = commercetoolsservice.getKeyOfCustomerGroupByRoles(MgnlContext.getUser().getAllRoles());
		String keycustomergroup = commercetoolsservice.getGrupoPrecioCommerce();
		
		if (keycustomergroup != null && keycustomergroup.isEmpty()) {
			String customerid = commercetoolsservice.getIdOfCustomerGroupByCostumerId(MyShopUtils.getUserName());
			keycustomergroup = commercetoolsservice.getKeyOfCustomerGroupById(customerid);
		}
		
		if (keycustomergroup != null && !keycustomergroup.isEmpty()) {
			return commercetoolsservice.getProductWithFilters(categoryid,keycustomergroup,filters, skus ,productsearch,page,pagination,count,strsorting);
			
		}
		
		return Collections.emptyList();
	}
	
	public List<MasterProductFrontDTO> getProductsFront() {
		return products;
	}
	
	public ProductSearchCT getProductSearch() {
		return productsearch;
	}
	
	public int getPage() {
		return page;
	}
	
	public Long getCount() {
		return productsearch.getCount();
	}
	
	public VariantDTO getVariantInfoPromociones(String sku) {
		ProductVariant variant = productService.getVariantBySku(sku);
		
		return cartService.getPromocionesByVariant(variant);
	}
	
	public String getSkuPadre() {
		
		String res = "";
		
		if (skupadre != null && !skupadre.isEmpty()) {
			res = skupadre;
		}
		
		return res;
	}
	
	public String getSkuPackMaster() {
		return skuPackMaster;
	}
	
	public String getSkupack() {
		
		String res = null;
		
		if (skupack != null && !skupack.isEmpty()) {
			res = skupack;
		}
		
		return res;
	}
	
	public String encodeURIComponent(String sku) {
		
		String res = "";

	    try{
	    	
	      res = URLEncoder.encode(sku, "UTF-8")
	                         .replaceAll("\\+", "%20")
	                         .replaceAll("\\%21", "!")
	                         .replaceAll("\\%27", "'")
	                         .replaceAll("\\%28", "(")
	                         .replaceAll("\\%29", ")")
	                         .replaceAll("\\%7E", "~");
	      
	    }catch (UnsupportedEncodingException e){
	      res = sku;
	    }
        
        return res;
	}
    
    public String getUuid() {
    	return MyShopUtils.getUuid();
    }
    
    public String getUserName() {
		return MyShopUtils.getUserName();
    }
    
    public Map<String, Boolean> getFittingBoxProducts(){
    	Map<String, Boolean> res = new HashMap<>();
    	try{
    		
    		if(!products.isEmpty()) {
    	    	
    	    	List<String> resMasters = products.stream()
    	    							  .map((MasterProductFrontDTO masterProductFrontDTO) -> masterProductFrontDTO.getMaster().getSku())
    	    							  .collect(Collectors.toList());
    	    	
    	    	List<String> resVariants = products.stream()
    	    							   .map(MasterProductFrontDTO::getVariants)
    	    							   .flatMap(List::stream)
    	    							   .map(ProductFrontDTO::getSku)
    	    							   .collect(Collectors.toList());
    	    	
    	    	List<String> skuList = Stream.concat(resMasters.stream(), resVariants.stream())
    	    						   .collect(Collectors.toList());
    	    	
    	    	res = fittingBoxService.isInFittingBox(skuList);
    	    	
    	    	
    	    	return res;
        	
        	}else {
        		return new HashMap<>();
        	}
    		
    	}catch(Exception e) {
    		return new HashMap<>();
    	}
    	   			   
    }
    
	public boolean isValidForPack() {
		
		boolean isValid = true;
		try {
			/*
			 * Tiene que tener el formato 
			 * ?category=bd4ac9d6-98b1-4f43-b29b-2770fcda99a1
			 * &skuPackMaster=PROMO_MONTURA_JR
			 * &variants.attributes.target.es=NIÑA&variants.attributes.target.es=NIÑO
			 * &variants.attributes.target.es=UNISEX&mandatoryFilters=variants.attributes.target.es%3DNIÑA|NIÑO
			 * */
			
			if (MgnlContext.getWebContext().getRequest().getParameter("skuPackMaster") != null)
				skuPackMaster = MgnlContext.getWebContext().getRequest().getParameter("skuPackMaster");
			
			List<String> parametrosURL = new ArrayList<>();
			
			Map<String, String[]> parametros = MgnlContext.getWebContext().getRequest().getParameterMap();
			for (var entry : parametros.entrySet()) {
				String key = entry.getKey();
				String[] value = parametros.get(key);
				
				for(int i=0; i<value.length; i++) {
					if (key.equals("category")) {
						parametrosURL.add(key + "=" + value[i]);
					}
					if (key.contains("variants.attributes")) {
						parametrosURL.add(key + "=" + value[i]);
					}
					if (key.contains("variants.sku")) {
						parametrosURL.add(key + "=" + value[i]);
					}
				}
			}
			
			
			//revisamos que no se hayan modificado parametros en la url
			HashMap<String, List<AgrupadorDTO>> infoPackMapSession = commercetoolsservice.getInfoPackMapSession();
			if ((infoPackMapSession != null) && (infoPackMapSession.get(skuPackMaster) != null)) {
				
				if (MgnlContext.getWebContext().getRequest().getParameter("step") != null){
					int pos = Integer.valueOf(MgnlContext.getWebContext().getRequest().getParameter("step"));
					List<String> agrupadorsession = infoPackMapSession.get(skuPackMaster).get(pos).getAgrupadores();
					
					List<String> agrupadorDTOsessionAux = cloneList(agrupadorsession);
					
					
					log.debug("COMPARAR PARAMETROS URL = " + parametrosURL.toString() + " " +agrupadorDTOsessionAux.toString());
					for(String parametro: parametrosURL) {
						if (agrupadorDTOsessionAux.contains(parametro)) {
							agrupadorDTOsessionAux.remove(parametro);
						}
					}
					
					if (!agrupadorDTOsessionAux.isEmpty()) {
						isValid = false;
					}
					
				} else { //si no viene el paso no podemos dejar modificarlo
					isValid = false;
				}					
			}
		} catch (NumberFormatException e) {
			log.error(e.getMessage(), e);
		}
		
		return isValid;
		
	}
	
	private List<String> cloneList(List<String> list) {
		
		return new ArrayList<>(list);
	}
	
	public String getAgrupadores() {
		return agrupadores;
	}
	
	public String getStep() {
		return step;
	}

}
