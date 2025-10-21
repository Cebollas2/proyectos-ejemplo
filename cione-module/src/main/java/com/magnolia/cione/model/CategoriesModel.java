package com.magnolia.cione.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.models.category.Category;
import com.commercetools.api.models.product.ProductProjection;
import com.commercetools.api.models.product.ProductVariant;
import com.magnolia.cione.constants.CioneRoles;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.CategoryDTO;
import com.magnolia.cione.dto.CT.LocaleCT;
import com.magnolia.cione.service.CategoryService;
import com.magnolia.cione.service.CommercetoolsServiceAux;
import com.magnolia.cione.service.ProductService;
import com.magnolia.cione.utils.CioneUtils;
import com.magnolia.cione.utils.MyShopUtils;

import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.ContentMap;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.jcr.util.SessionUtil;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class CategoriesModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {
	
	private static final Logger log = LoggerFactory.getLogger(CategoriesModel.class);
	
	@Inject
	private CategoryService categoryService;
	
	@Inject
	private CommercetoolsServiceAux commercetoolsServiceAux;
	
	@Inject
	private ProductService productService;
	
	@Inject
	private TemplatingFunctions templatingFunctions;
	
	public CategoriesModel(Node content, RD definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
	}
	
	public List<CategoryDTO> getListMarcasRelMonturas(String idRelatedCategory, Integer limit) {
		
		long startTime = System.nanoTime();
		List<CategoryDTO> marcasMonturasListDTO = new ArrayList<CategoryDTO>();
		Map<String, String> filters = new HashMap<>();
		try {
			if (content.hasProperty("attribute")) {
				Value[] values = content.getProperty("attribute").getValues();
				try {
					for (Value value: values) {
						String[] parts = value.getString().split("=");
						String key = parts[0];
						if (filters.get(key) != null) {
							
							filters.put(key, filters.get(key) + ",\"" + parts[1] +"\"");
						} else {
							filters.put(key, "\"" +parts[1] +"\"");
						}
					}
				} catch (IllegalStateException e) {
					log.error("Error al parsear los filtros asignados en el componente " + e.getMessage());
				}
			}
			
			
	    	List<Category> marcasMonturasList = categoryService.getMarcasRelMonturas(getIdFromCategoryComponent(idRelatedCategory), limit, filters);
	    	long endTime = System.nanoTime();
			System.out.println(endTime - startTime);
			
	    	
	    	
	    	String defaultImage = CioneUtils.getURLHttps() + "/" + MyshopConstants.defaultCategoryLogo(new Locale(templatingFunctions.language()));
	    	
	    	for (Category category: marcasMonturasList) {
	    		CategoryDTO dto = new CategoryDTO();
	    		dto.setName(new LocaleCT(category.getName().get("ES"), category.getName().get("PT"), category.getName().get("EN")));
	    		dto.setName_es(category.getName().get("ES"));
	    		
	    		if (category.getCustom() != null) {
	    			
	    			if (category.getCustom().getFields().values().get("logoListado")!= null) {
	    				dto.setLogoListado(CioneUtils.getURLHttps() + category.getCustom().getFields().values().get("logoListado"));
	    			//if (category.getCustom().getFieldAsString("logoListado") != null) {
	    				//dto.setLogoListado(CioneUtils.getURLHttps() + category.getCustom().getFieldAsString("logoListado"));
	    			} else {
	    				dto.setLogoListado(defaultImage);
	    			}
	    			/*if (category.getCustom().getFields().values().get("logoProducto")!= null) {
	    				dto.setLogoListado(CioneUtils.getURLHttps() + category.getCustom().getFields().values().get("logoProducto"));
	    			} else {
	    				dto.setLogoProducto(defaultImage);
	    			}*/
	    		}
	    		
	    		dto.setId(category.getId());
	    		
	    		marcasMonturasListDTO.add(dto);
	    	}
	    	
		} catch (RepositoryException e) {
			log.error(e.getMessage());
		}
		
		

    	return marcasMonturasListDTO;
    }
    
    
    public ProductVariant getVariantByAliasEkon(String aliasEkon) {
    	
    	ProductProjection producto = productService.getProductByAliasEkon(aliasEkon);
    	
    	if (producto != null) {
    		List<ProductVariant> listAllVariants = commercetoolsServiceAux.getAllVariants(producto);
    		for (ProductVariant var: listAllVariants) {
    			if (MyShopUtils.hasAttribute("aliasEkon", var.getAttributes())) {
    				String attr = (String)MyShopUtils.findAttribute("aliasEkon", var.getAttributes()).getValue();
    				if (attr.equals(aliasEkon)) {
    					return var;
    				}
    			}
        		
        	}
    	}
    	
    	return null;
    }
    
    
    public ProductVariant getVariantBySku(String sku) {
    	return productService.getVariantBySku(sku);
    }

	public boolean hasPermissionsRoles(ContentMap contentRolesAllowed) {
		boolean result = false;
		
		try {
			if (MgnlContext.getUser().hasRole(CioneRoles.EMPLEADO_CIONE_BANNER))
				return false;
			if (MgnlContext.getUser().hasRole("superuser")) {
				return true;
			}
			
			List<String> listado = new ArrayList<>();
			Node nodeRolesAllowed = contentRolesAllowed.getJCRNode();
			List<Node> childrenList = NodeUtil.asList(NodeUtil.getNodes(nodeRolesAllowed));
			if (childrenList.isEmpty()) //no tiene roles asignados
				return true;
			for (Node node : childrenList) {
				Property roleAllowedProperty = PropertyUtil.getPropertyOrNull(node, "rol");
				if (roleAllowedProperty != null) {
					Node role = SessionUtil.getNodeByIdentifier("userroles", roleAllowedProperty.getString());
					// existe el rol (no se ha borrado ni nada deso)
					if (role != null) {
						listado.add(role.getName());
						log.debug("ROL = " + role.getName());
						if (MgnlContext.getUser().hasRole(role.getName())) {
							result = true;
						}
					}
				}
			}
			
		} catch (Exception e) {
			log.debug(e.getMessage());
			//log.debug("rolFilter field is empty");
			result = false;
		}
		
		return result;
	}
	
	public boolean hasPermissionsCampana(String campana) {
		boolean result = false;
		String flag = "es";
		//comprobamos si no tiene acceso por rol de empleado
		if (MgnlContext.getUser().hasRole(CioneRoles.EMPLEADO_CIONE_BANNER))
			return false;
		if (MgnlContext.getUser().hasRole("superuser")) {
			return true;
		}
		
		
		//campañas
		if (campana.equals("all")) {
			if (MgnlContext.getUser().hasRole(CioneRoles.OPTCAN) 
				|| MgnlContext.getUser().hasRole(CioneRoles.OPTMAD)
				|| MgnlContext.getUser().hasRole(CioneRoles.CLIENTE_MONTURAS)
				|| MgnlContext.getUser().hasRole(CioneRoles.OPTICAPRO)) {
				return false;
			} else {
				return true;
			}
		}
		
		if (MgnlContext.getUser().hasRole(CioneRoles.SICIO_CIONE_PORTUGAL)
				|| MgnlContext.getUser().hasRole(CioneRoles.CLIENTES_PORTUGAL)
				|| MgnlContext.getUser().hasRole(CioneRoles.CLIENTE_PORTUGAL_VCO)
				|| MgnlContext.getUser().hasRole(CioneRoles.PORLENS)) {
			flag = "pt";
		} else if (MgnlContext.getUser().hasRole(CioneRoles.OPTCAN)
				|| MgnlContext.getUser().hasRole(CioneRoles.OPTMAD)
				|| MgnlContext.getUser().hasRole(CioneRoles.CLIENTE_MONTURAS)) {
			flag = "optofive";
		} else if (MgnlContext.getUser().hasRole(CioneRoles.OPTICAPRO)) {
			flag = "opticapro";
		}
		if (campana.equals(flag)) {
			return true;
		}

		return result;
	}
    
	public boolean hasPermissions() {
		boolean result = false;
		
		try {
			List<String> listado = new ArrayList<>();
			if (content.hasProperty("rolFilter") && (content.getProperty("rolFilter").getValues().length >0)) {
				Value[] values = content.getProperty("rolFilter").getValues();
				
				for (Value value: values) {
					Node role = SessionUtil.getNodeByIdentifier("userroles", value.getString());
					if (role != null) {
						log.debug(role.getName());
						listado.add(role.getName());
					}
				}
				
				log.debug(listado.toString());
				
				if (MgnlContext.getUser().hasRole("superuser")) {
					return true;
				}
	
				int i = 0;
				while (i < listado.size()) {
					if (MgnlContext.getUser().hasRole(listado.get(i))) {
						return true;
					}
					i++;
				}
					
			} else {
				return true;
			}
	
		} catch (Exception e) {
			log.debug(e.getMessage());
			//log.debug("rolFilter field is empty");
			result = true;
		}
		
		return result;
	}
	
    
    public List<CategoryDTO> getListColeccionesMonturas(String categoryRoot, String idRelatedCategory, Integer limit) {
	
    	/*Selector de categorias root desde la conf del componente
    	String categorylabel = MyshopConstants.CATEGORYDEFAULT;
    	
    	try {
			categorylabel = content.getProperty(MyshopConstants.CATEGORYROOT).getValue().getString();
		} catch (IllegalStateException | RepositoryException e) {
			e.printStackTrace();
		}*/
    	
    	List<Category> coleccionesMonturasList = categoryService.getColeccionesMonturas(getIdFromCategoryComponent(idRelatedCategory), limit,getIdFromCategoryComponent(categoryRoot));
    	List<CategoryDTO> colecMonturasDTO = new ArrayList<CategoryDTO>();

    	String defaultImage = CioneUtils.getURLHttps() + "/" + MyshopConstants.defaultCategoryLogo(new Locale(templatingFunctions.language()));

    	for (Category category: coleccionesMonturasList) {
    		CategoryDTO dto = new CategoryDTO();
    		dto.setName(new LocaleCT(category.getName().get("ES"), category.getName().get("PT"), category.getName().get("EN")));
    		dto.setName_es(category.getName().get("ES"));
    		dto.setId(category.getId());

    		if (category.getCustom() != null) {
    			if (category.getCustom().getFields().values().get("logoListado")!= null) {
    				String logoListado = (String) category.getCustom().getFields().values().get("logoListado");
    				if (!logoListado.isEmpty()) {
    					dto.setLogoListado(logoListado);
    				} else {
    					dto.setLogoListado(defaultImage);
    				}
    			} else {
    				dto.setLogoListado(defaultImage);
    			}
    			if (category.getCustom().getFields().values().get("logoProducto")!= null) {
    				String logoProducto = (String) category.getCustom().getFields().values().get("logoProducto");
    				if (!logoProducto.isEmpty()) {
    					dto.setLogoProducto(logoProducto);
    				} else {
    					dto.setLogoProducto(defaultImage);
    				}
    			} else {
    				dto.setLogoProducto(defaultImage);
    			}
    			
    		}

    		colecMonturasDTO.add(dto);
    	}
    	
    	return colecMonturasDTO;
    }
    
    public String addFiltersUrl(String url) {
    	
    	if (MgnlContext.getParameterValues(MyshopConstants.FF_GAMA_COLOR_MONTURA) != null && MgnlContext.getParameterValues(MyshopConstants.FF_GAMA_COLOR_MONTURA).length > 0) {
			url += addFilters(MyshopConstants.FF_GAMA_COLOR_MONTURA,MgnlContext.getParameterValues(MyshopConstants.FF_GAMA_COLOR_MONTURA));
		}
		
		if (MgnlContext.getParameterValues(MyshopConstants.FF_MARCA) != null && MgnlContext.getParameterValues(MyshopConstants.FF_MARCA).length > 0) {
			url += addFilters(MyshopConstants.FF_MARCA,MgnlContext.getParameterValues(MyshopConstants.FF_MARCA));
		}
		
		if (MgnlContext.getParameterValues(MyshopConstants.FF_TIPO_PRODUCTO) != null && MgnlContext.getParameterValues(MyshopConstants.FF_TIPO_PRODUCTO).length > 0) {
			url += addFilters(MyshopConstants.FF_TIPO_PRODUCTO,MgnlContext.getParameterValues(MyshopConstants.FF_TIPO_PRODUCTO));
		}
		
		if (MgnlContext.getParameterValues(MyshopConstants.FF_FAMILIA_EKON) != null && MgnlContext.getParameterValues(MyshopConstants.FF_FAMILIA_EKON).length > 0) {
			url += addFilters(MyshopConstants.FF_FAMILIA_EKON,MgnlContext.getParameterValues(MyshopConstants.FF_FAMILIA_EKON));
		}
		
		if (MgnlContext.getParameterValues(MyshopConstants.FF_GRADUACION) != null && MgnlContext.getParameterValues(MyshopConstants.FF_GRADUACION).length > 0) {
			url += addFilters(MyshopConstants.FF_GRADUACION,MgnlContext.getParameterValues(MyshopConstants.FF_GRADUACION));
		}
		
		if (MgnlContext.getParameterValues(MyshopConstants.FF_DIMENSIONES_ANCHO_OJO) != null && MgnlContext.getParameterValues(MyshopConstants.FF_DIMENSIONES_ANCHO_OJO).length > 0) {
			url += addFilters(MyshopConstants.FF_DIMENSIONES_ANCHO_OJO,MgnlContext.getParameterValues(MyshopConstants.FF_DIMENSIONES_ANCHO_OJO));
		}
		
		if (MgnlContext.getParameterValues(MyshopConstants.FF_MATERIAL) != null && MgnlContext.getParameterValues(MyshopConstants.FF_MATERIAL).length > 0) {
			url += addFilters(MyshopConstants.FF_MATERIAL,MgnlContext.getParameterValues(MyshopConstants.FF_MATERIAL));
		}
		
		if (MgnlContext.getParameterValues(MyshopConstants.FF_TARGET) != null && MgnlContext.getParameterValues(MyshopConstants.FF_TARGET).length > 0) {
			url += addFilters(MyshopConstants.FF_TARGET,MgnlContext.getParameterValues(MyshopConstants.FF_TARGET));
		}
		
		if (MgnlContext.getParameterValues(MyshopConstants.FF_COLECCION) != null && MgnlContext.getParameterValues(MyshopConstants.FF_COLECCION).length > 0) {
			url += addFilters(MyshopConstants.FF_COLECCION,MgnlContext.getParameterValues(MyshopConstants.FF_COLECCION));
		}
		
		if (MgnlContext.getParameterValues(MyshopConstants.FF_TAMANIOS) != null && MgnlContext.getParameterValues(MyshopConstants.FF_TAMANIOS).length > 0) {
			url += addFilters(MyshopConstants.FF_TAMANIOS,MgnlContext.getParameterValues(MyshopConstants.FF_TAMANIOS));
		}
		
		if (MgnlContext.getParameterValues(MyshopConstants.FF_DIMENSIONES_LARGO_VARILLA) != null && MgnlContext.getParameterValues(MyshopConstants.FF_DIMENSIONES_LARGO_VARILLA).length > 0) {
			url += addFilters(MyshopConstants.FF_DIMENSIONES_LARGO_VARILLA,MgnlContext.getParameterValues(MyshopConstants.FF_DIMENSIONES_LARGO_VARILLA));
		}
		
		if (MgnlContext.getParameterValues(MyshopConstants.FF_PRICE) != null && MgnlContext.getParameterValues(MyshopConstants.FF_PRICE).length > 0) {
			url += addFilters(MyshopConstants.FF_PRICE,MgnlContext.getParameterValues(MyshopConstants.FF_PRICE));
		}
		
		if (MgnlContext.getParameterValues(MyshopConstants.FF_ALIASEKON) != null && MgnlContext.getParameterValues(MyshopConstants.FF_ALIASEKON).length > 0) {
			url += addFilters(MyshopConstants.FF_ALIASEKON,MgnlContext.getParameterValues(MyshopConstants.FF_ALIASEKON));
		}
		
		if (MgnlContext.getParameterValues(MyshopConstants.FF_COLOR) != null && MgnlContext.getParameterValues(MyshopConstants.FF_COLOR).length > 0) {
			url += addFilters(MyshopConstants.FF_COLOR,MgnlContext.getParameterValues(MyshopConstants.FF_COLOR));
		}
		
		if (MgnlContext.getParameterValues(MyshopConstants.FF_SIZE) != null && MgnlContext.getParameterValues(MyshopConstants.FF_SIZE).length > 0) {
			url += addFilters(MyshopConstants.FF_SIZE,MgnlContext.getParameterValues(MyshopConstants.FF_SIZE));
		}
		
    	return url;
    }
    
    /**
     * 
     * Metodo para la composicion de la URL. Para un filtro en concreto 
     * aniade N filtros que provengan en la URL
     * 
     * @param filter nombre del filtro
     * @param values valor(es) del filtro
     * @return filtro(s) para aniadir a la URL
     * 
     */
    private String addFilters(String filter, String[] values) {
    	
    	StringBuilder res = new StringBuilder();
	    Arrays.stream(values).forEach(x -> {
			try {
				res.append("&" + filter + "=" + URLEncoder.encode(x,"UTF-8").replace("+", "%20"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
    	
		return res.toString();
	}

	private String getIdFromCategoryComponent(String idRelatedCategory) {
    	StringTokenizer st = new StringTokenizer(idRelatedCategory,"/");
		if (st.hasMoreTokens())
			st.nextToken();
		if (st.hasMoreTokens())
			st.nextToken();
		if (st.hasMoreTokens())
			idRelatedCategory=st.nextToken();
		return idRelatedCategory;
    }
	
	public String encodeURIComponent(String title) {
		
		String res = "";

	    try{
	    	
	    	res = URLEncoder.encode(title, "UTF-8")
	    			.replaceAll("%3D", "=")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
	      
	    }catch (UnsupportedEncodingException e){
	      res = title;
	    }
        
        return res;
	}
    
}
