package com.magnolia.cione.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.models.category.Category;
import com.commercetools.api.models.product.ProductVariant;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.MasterProductFrontDTO;
import com.magnolia.cione.dto.ProductFrontDTO;
import com.magnolia.cione.dto.VariantDTO;
import com.magnolia.cione.service.CartService;
import com.magnolia.cione.service.CategoryService;
import com.magnolia.cione.service.CommercetoolsService;
import com.magnolia.cione.service.FittingBoxService;
import com.magnolia.cione.service.ProductService;
import com.magnolia.cione.setup.CioneEcommerceConectionProvider;
import com.magnolia.cione.utils.MyShopUtils;

import info.magnolia.context.MgnlContext;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class ListadoProductosHomeModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	private List<MasterProductFrontDTO> products;

	@Inject
	private CommercetoolsService commercetoolsservice;
	
	private static final Logger log = LoggerFactory.getLogger(ListadoProductosHomeModel.class);
	
	@Inject
	private TemplatingFunctions templatingFunctions;
	
	@Inject
	private CategoryService categoryService;
	
	@Inject
	private CartService cartService;
	
	@Inject
	private ProductService productService;
	
	@Inject
	private CioneEcommerceConectionProvider cioneEcommerceConectionProvider;
	
	@Inject
	private FittingBoxService fittingBoxService;
	
	public ListadoProductosHomeModel(Node content, RD definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
	}
	
	@Override
	public String execute() {
		long startTime = System.currentTimeMillis();
		if (!templatingFunctions.isEditMode()) {
			
			String categoryid = "";
			String categoriesid = "";
			
			// Categoria principal
			try {
				if (content.getProperty("category") != null) {
					categoryid = content.getProperty("category").getValue().getString();
					categoryid = sanitizerCategoryPath(categoryid);
				}
			} catch (RepositoryException e) {
				log.debug("Category field is empty");
			}
			
			// Categorias extra
			try {
				if (content.getProperty("extraCategory") != null) {
					Value[] extracategories = content.getProperty("extraCategory").getValues();
					categoriesid = sanitizerCategoriesPath(extracategories);
				}
			} catch (RepositoryException e) {
				log.debug("extraCategory field is empty");
			}
			
			// Pantalla detalle
			String SKU = MgnlContext.getWebContext().getParameter("sku");
			
			//if (SKU != null) {
			boolean recomendados;
			try {
				if (content.hasProperty(MyshopConstants.RECOMENDADOS)) {
					recomendados = content.getProperty(MyshopConstants.RECOMENDADOS).getValue().getBoolean();
					if (recomendados) {
						Category recommended = categoryService.getRecommendedCategory(SKU);
						if (recommended != null)
							categoryid = "\"" + recommended.getId() +"\"";
					} 
				}
			} catch (RepositoryException e) {
				log.info("No tiene categorias las categorias relacionadas");
			}
			
			
			if (!categoryid.isEmpty()) {
				if (!categoriesid.isEmpty()) {
					
					products = getFilteredFrontProducts(categoriesid.concat(categoryid));
					
				}else {
					//HOME
					String key = categoryid + "#" + commercetoolsservice.getGrupoPrecioCommerce();
					List<MasterProductFrontDTO> ifPresent = (List<MasterProductFrontDTO>) cioneEcommerceConectionProvider.getCache().getIfPresent(key);
					if (ifPresent == null) {
						products = getFilteredFrontProducts(categoryid);
						cioneEcommerceConectionProvider.getCache().put(key, products);
					} else {
						products = ifPresent;
					}
					
					
				}
			}
		}
		if (products != null && !products.isEmpty()) {
			log.debug("Productos obtenidos: " + products.size());
		}
		long endTime = System.currentTimeMillis();
		System.out.println("execute: " + String.valueOf(endTime - startTime) + "-" + String.valueOf((endTime - startTime)/1000));
		
		return super.execute();
	}
	
	/**
	 * 
	 * Metodo para obtener un lista prepara para aplicarse a la llamada de la API.
	 * Esta lista se debe unir a la categoria principal. 
	 * 
	 * @param extracategories valores de categorias extras del digalogo del componente
	 * @return lista de categorias<br><br>
	 * Ej: "categoriaextra1", "categoriaextra2", 
	 * <br><br>
	 * La coma final es para ser aniadida la categoria final
	 * 
	 */
	private String sanitizerCategoriesPath(Value[] extracategories) {
		
		
		StringBuilder sb = new StringBuilder();
		
		Arrays.stream(extracategories).forEach(x -> {
			try {
				sb.append(sanitizerCategoryPath(x.getString()) + ",");
			} catch (IllegalStateException | RepositoryException e) {
				e.printStackTrace();
			}
		});
		
		return sb.toString();
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
		
		String[] parts = path.split("/");
		
		return "\"" + parts[parts.length-1] + "\"";
	}
	
	/**
	 * 
	 * Obtenemos la lista de productos filtrados por la categoria y por
	 * el usuario logado. Este DTO esta filtrado con tan solo los datos
	 * que realmente se van a usar en el front.
	 * 
	 * @param id de la categoria
	 * @return lista de producto filtrado y adaptados al front
	 */
	private List<MasterProductFrontDTO> getFilteredFrontProducts(String id) {
		
		//String keycustomergroup = commercetoolsservice.getKeyOfCustomerGroupByRoles(MgnlContext.getUser().getAllRoles());
		String keycustomergroup = commercetoolsservice.getGrupoPrecioCommerce();
		
		if (keycustomergroup.isEmpty()) {
			String customerid = commercetoolsservice.getIdOfCustomerGroupByCostumerId(MyShopUtils.getUserName());
			keycustomergroup = commercetoolsservice.getKeyOfCustomerGroupById(customerid);
		}
		
		if (!keycustomergroup.isEmpty()) {
			List<MasterProductFrontDTO> list = commercetoolsservice.getProductsFrontByCategory(id, keycustomergroup, MyShopUtils.getUserName());
			return list;
		}
		
		return Collections.emptyList();
	}
	
	public List<MasterProductFrontDTO> getProductsFront() {
		return products;
	}
	
	public VariantDTO getVariantInfoPromociones(String sku) {
		ProductVariant variant = productService.getVariantBySku(sku);
		
		return cartService.getPromocionesByVariant(variant);
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
    	
    	if (products != null && !products.isEmpty()) {
    	
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
	    	
	    	return skuList.isEmpty() ? new HashMap<>() : fittingBoxService.isInFittingBox(skuList);
    	
    	}else {
    		return new HashMap<>();
    	}	   
    }

}
