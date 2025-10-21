package com.magnolia.cione.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.jcr.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.models.product.ProductProjection;
import com.commercetools.api.models.product.ProductVariant;
import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.AgrupadorDTO;
import com.magnolia.cione.dto.ColorDTO;
import com.magnolia.cione.dto.StockMgnlDTO;
import com.magnolia.cione.dto.VariantDTO;
import com.magnolia.cione.service.CommercetoolsService;
import com.magnolia.cione.service.CommercetoolsServiceAux;
import com.magnolia.cione.service.DetalleService;
import com.magnolia.cione.service.FittingBoxService;
import com.magnolia.cione.service.MiddlewareService;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.cms.security.SecuritySupport;
import info.magnolia.context.MgnlContext;
import info.magnolia.objectfactory.Components;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;


public class MyShopDetailModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {
	
	@Inject
	private MiddlewareService middlewareService;
	
	@Inject
	private DetalleService detalleService;
	
	@Inject
	private FittingBoxService fittingBoxService;
	
	@Inject
	private CommercetoolsService commerceToolsService;
	
	@Inject
	private CommercetoolsServiceAux commerceToolsServiceAux;
	
	private VariantDTO variant;
	
	//private HashMap<String, List<AgrupadorDTO>> infoPackMapSession;//almacena en un hasmap identificado por el skuPackMaster el contenido que tenemos almacenado en el pack
	
	private static final Logger log = LoggerFactory.getLogger(MyShopDetailModel.class);

	public MyShopDetailModel(Node content, RD definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
	}

	@Override
	public String execute() {
		String sku = MgnlContext.getParameter("sku");
    	//String key = MgnlContext.getParameter("key");
    	String skupack = MgnlContext.getParameter("skupack");
    	String skuPackMaster = MgnlContext.getParameter("skuPackMaster");
    	
    	//if (key != null) 
			//sku = detalleService.getSkuByKey(key);
		if (sku != null) {
			//el skupack solo vendrá informado para los casos de los packs de audilogia (implementacion packs antigua)
			variant = detalleService.getInfoVariant(sku, skupack);
			
			setDataGenericPack(skuPackMaster);
			
		} else 
			if (skuPackMaster != null)
				try {
					//es pack generico
					variant = detalleService.getInfoVariantPack(skuPackMaster);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					variant = new VariantDTO();
				}
			else
				variant = new VariantDTO();
		
		
		
		
		log.debug("Máxima memoria: " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + " MB");
		log.debug("Memoria total: " + Runtime.getRuntime().totalMemory()/ 1024 / 1024 + " MB");

    	return super.execute();
	}
    
	/*Funcion que setea los atributos necesarios para añadir al carrito oculto tipo pack desde la pagina de detalle*/
	private boolean setDataGenericPack(String skuPackMaster) {
		//si tenemos skuPackMaster es que venimos de una pagina de detalle de pack generico
		if (skuPackMaster!= null) {
			variant.setSkuPackMaster(skuPackMaster);
			//obtener el tipoPrecioPack y pvoproductopack
			int step = 0;
			if ((MgnlContext.getWebContext().getRequest().getParameter("step") != null) 
					&& !MgnlContext.getWebContext().getRequest().getParameter("step").isEmpty()) {
				String step_str = MgnlContext.getWebContext().getRequest().getParameter("step");
				try {
					step = Integer.valueOf(step_str).intValue();
					variant.setStep(step_str);
				} catch (NumberFormatException e) {
					log.error(e.getMessage(), e);
					variant.setSkuPackMaster(null);
					return false;
				}
			} else {
				log.error("NO HA PASADO EL STEP COMO PARAMETRO");
				variant.setSkuPackMaster(skuPackMaster);
				return false;
			}
			
			try {
				variant.setTipoPrecioPack(commerceToolsService.getTipoPrecioPackListadoFilter());
				String pvoPack = commerceToolsService.getTipoPvoPackListadoFilter(Double.valueOf(variant.getPvo().replace(",", ".")), variant.getTipoPrecioPack(), variant.getTipoProducto(), step);
				variant.setPvoPack(pvoPack);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				variant.setSkuPackMaster(null);
				return false;
			}
			
			HashMap<String, List<AgrupadorDTO>> infoPackMapSession = commerceToolsService.getInfoPackMapSession();
			if ((infoPackMapSession != null) && (infoPackMapSession.get(skuPackMaster) != null)) {
				List<AgrupadorDTO> productosPackGenerico = infoPackMapSession.get(skuPackMaster);
				AgrupadorDTO filtroObligatorio = productosPackGenerico.get(step);
				
				List<String> listaFiltros = filtroObligatorio.getAgrupadores();
				
				List<String> listaFiltrosAgrupados = agruparFiltrosObligatorios(listaFiltros);
				
				
				ProductProjection product = commerceToolsService.getProductBysku(variant.getSku(), variant.getGruposPrecio());
				ProductVariant variantSelected = commerceToolsServiceAux.findVariantBySku(product, variant.getSku());
				
				//control por si entra a un producto no contenido en el pack
				if (!incluirVariante(listaFiltrosAgrupados, variantSelected)) {
					variant.setSkuPackMaster(null);
					return false;
				}
				List<String> listaSkuExcluido = new ArrayList<>();
				
				
				List<ProductVariant> listProductVariants = commerceToolsServiceAux.getAllVariants(product);
				
				for (ProductVariant variant: listProductVariants) {
					if (!incluirVariante(listaFiltrosAgrupados, variant)) {
						//eliminamos navegacion (color, calibre, graduacion y tamaño)
						listaSkuExcluido.add(variant.getSku());
					}
				}
				
				/*for (String skuvariant: variant.getVariantsSku()) {
					if (!incluirVariante(listaFiltros, skuvariant)) {
						//eliminamos navegacion (color, calibre, graduacion y tamaño)
						listaSkuExcluido.add(skuvariant);
					}
				}*/
				if (!listaSkuExcluido.isEmpty()) {
					List<ColorDTO> colors = variant.getColors();
					List<ColorDTO> colorsAux = cloneList(variant.getColors());
					
					try {
						int indexAux = 0;
						for (int i=0; i<colors.size(); i++) {
							ColorDTO color = colors.get(i);
							if (CioneUtils.containsList(listaSkuExcluido, color.getSku())) {
								boolean encontradocalibreValido = false;
								if (color.getCalibres() != null) {
									for (int j=0; j<color.getCalibres().size(); j++) {
										ColorDTO calibre= color.getCalibres().get(j);
										if (CioneUtils.containsList(listaSkuExcluido, calibre.getSku())) {
											//eliminamos el calibre
											colorsAux.get(indexAux).getCalibres().remove(colorsAux.get(indexAux).getCalibres().get(j));
										} else {
											encontradocalibreValido = true;
											//esta seleccion pasa a la principal, pasamos sus datos a la principal
											colorsAux.get(indexAux).setSku(calibre.getSku());
											colorsAux.get(indexAux).setCalibre(calibre.getCalibre());
										}
									}
									if (!encontradocalibreValido) {
										colorsAux.remove(indexAux);
										indexAux--;
									}
								}
								boolean encontradoGraduacionValido = false;
								if (color.getGraduaciones() != null) {
									for (int j=0; j<color.getGraduaciones().size(); j++) {
										ColorDTO graduacion= color.getGraduaciones().get(j);
										if (CioneUtils.containsList(listaSkuExcluido, graduacion.getSku())) {
											//eliminamos el calibre
											colorsAux.get(indexAux).getGraduaciones().remove(colorsAux.get(indexAux).getGraduaciones().get(j));
										} else {
											encontradoGraduacionValido = true;
											//esta seleccion pasa a la principal, pasamos sus datos a la principal
											colorsAux.get(indexAux).setSku(graduacion.getSku());
											colorsAux.get(indexAux).setGraduacion(graduacion.getGraduacion());
										}
									}
									if (!encontradoGraduacionValido) {
										colorsAux.remove(indexAux);
										indexAux--;
									}
								}
							} else {
								if (color.getCalibres() != null) {
									for (int j=0; j<color.getCalibres().size(); j++) {
										ColorDTO calibre= color.getCalibres().get(j);
										if (CioneUtils.containsList(listaSkuExcluido, calibre.getSku())) {
											//eliminamos el calibre
											colorsAux.get(indexAux).getCalibres().remove(colorsAux.get(indexAux).getCalibres().get(j));
										} 
									}
								}
								if (color.getGraduaciones() != null) {
									for (int j=0; j<color.getGraduaciones().size(); j++) {
										ColorDTO graduacion= color.getGraduaciones().get(j);
										if (CioneUtils.containsList(listaSkuExcluido, graduacion.getSku())) {
											//eliminamos la graduacion
											colorsAux.get(indexAux).getGraduaciones().remove(colorsAux.get(indexAux).getGraduaciones().get(j));
										} 
									}
								}
							}
							indexAux++;
						}
						
						variant.setColors(colorsAux);
					} catch (Exception e) {
						log.error(e.getMessage(), e);
						variant.setSkuPackMaster(null);
						return false;
					}
				}
			}
		}
		return true;
	}
	
	
	private List<String> agruparFiltrosObligatorios (List<String> listaFiltros) {
		
		List<String> listaFiltrosAgrupados = new ArrayList<>();
		Map<String, List<String>> attributeMap = new LinkedHashMap<>();
		for (String filtro:listaFiltros) {
			//recorro la lista y si no existe el agrupador variants.attribute.gamacolor
			String key = filtro.split("=")[0]; //.replace("variants.attributes.", "").replace(".es","").replace(".key","");
        	String value = filtro.split("=")[1];//.replace("\"", "").replace("\\", "");
        	attributeMap.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
        	
		}
		
		listaFiltrosAgrupados = attributeMap.entrySet().stream()
	            .map(entry -> entry.getKey() + "=" + String.join(", ", entry.getValue()))
	            .collect(Collectors.toList());
		
		return listaFiltrosAgrupados;
	}
	
	private List<ColorDTO> cloneList(List<ColorDTO> list) {
		List<ColorDTO> clonedList = new ArrayList<>();
		for (ColorDTO color : list) {
			clonedList.add(color.clone());
		}
		
		return clonedList;
	}
	
	
	//decide si incluimos o no esta variante (similar a removeVariants de CommerceToolsService
	private boolean incluirVariante (List<String> listaFiltros, ProductVariant productVariant) {
		//boolean incluir=false;
		boolean incluirAllFilter=true;
		for (String filtro: listaFiltros) {
			if (filtro.contains("variants.sku=")) {
				String skufiltro = filtro.substring(filtro.indexOf("=")+1, filtro.length());
				//if (skufiltro.equals(productVariant.getSku()))
				if (skufiltro.contains(productVariant.getSku()))
					return true;
				else
					incluirAllFilter=false;
			} else if(filtro.contains("variants.attributes.")) {
				//tiene que cumplir todos los filtros, salvo que sea para el mismo tipo, xe gama color {rojo o rosa}
				if (!commerceToolsService.variantMatchesFilter(productVariant, filtro)) {  
					return false;
				} 
			}
		}
		
		return incluirAllFilter;
	}

    public String getUuid() {
    	String uuid ="";
    	String id = MgnlContext.getUser().getName();
		// impersonate
		if (MgnlContext.getUser().hasRole(CioneConstants.ROLE_CIONE_SUPERUSER) || MgnlContext.getUser().hasRole(CioneConstants.ROLE_OPTOFIVE_SUPERUSER)) {
			String idToSimulate = MgnlContext.getUser().getProperty(CioneConstants.IMPERSONATE_FIELD_ID_SOCIO);
			if (idToSimulate != null && !idToSimulate.isEmpty()) {
				log.debug("El socio " + id + " simula a " + idToSimulate);
				id = idToSimulate;
			}
		}
    	uuid = Components.getComponent(SecuritySupport.class).getUserManager().getUser(id).getIdentifier();
    	return uuid;
    }
    
    public String getCustomerId() {
    	return commerceToolsService.getCustomerId();
    }
    
    public String getUserName() {
    	String userName = MgnlContext.getUser().getName();
		// impersonate
		if (MgnlContext.getUser().hasRole(CioneConstants.ROLE_CIONE_SUPERUSER) || MgnlContext.getUser().hasRole(CioneConstants.ROLE_OPTOFIVE_SUPERUSER)) {
			String idToSimulate = MgnlContext.getUser().getProperty(CioneConstants.IMPERSONATE_FIELD_ID_SOCIO);
			if (idToSimulate != null && !idToSimulate.isEmpty()) {
				log.debug("El socio " + userName + " simula a " + idToSimulate);
				userName = idToSimulate;
			}
		}
		return userName;
    }
    

    /**
     * Get category information by id.
     
    public Category getCategoryById(String definitionName, String connectionName, String categoryId) {
    	
        return getConnection(definitionName, connectionName)
                .map(Connection::getCategories)
                .map(Categories::getById)
                .flatMap(byId -> (Optional<Category>) byId.fetch(Category.Id.from(definitionName, connectionName, categoryId)))
                .orElse(null);
    	
    	
    }*/
    
	public String getRefTaller() {
		return CioneUtils.generateRef();
	}
	
	public VariantDTO getVariant() {
		return variant;
	}

	public void setVariant(VariantDTO variant) {
		this.variant = variant;
	}
	
//	public boolean isFittingBox(String sku) {
//		
//		boolean res = false;
//		
//		if (sku != null && !sku.isEmpty()) {
//			res = fittingBoxService.isInFittingBox(sku);
//		}
//		
//		return res;
//	}
	
    public Map<String, Boolean> getFittingBoxProducts(){
    	Map<String, Boolean> res = new HashMap<>();
    	try {
    		res = fittingBoxService.isInFittingBox(variant.getVariantsSku());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
    	return res;
    }
    
    public String getAlmacen() {
    	return MgnlContext.getWebContext().getRequest().getSession().getAttribute(MyshopConstants.ATTR_STOCK_SESSION).toString();
    }
    
    public StockMgnlDTO getStock(String sku) {
    	return middlewareService.getStockDTO(sku);
    }
    

}