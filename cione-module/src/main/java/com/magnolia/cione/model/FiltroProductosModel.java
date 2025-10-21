package com.magnolia.cione.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.models.product.FacetResult;
import com.commercetools.api.models.product.TermFacetResult;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.AgrupadorDTO;
import com.magnolia.cione.dto.CT.ProductFilterCT;
import com.magnolia.cione.dto.CT.VariantsFilterCT;
import com.magnolia.cione.dto.CT.variants.VariantsAttributes;
import com.magnolia.cione.service.CommercetoolsService;
import com.magnolia.cione.utils.CioneUtils;
import com.magnolia.cione.utils.MyShopUtils;

import info.magnolia.context.MgnlContext;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class FiltroProductosModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	private VariantsFilterCT facets;
	private List<ProductFilterCT> filtrosFront = new ArrayList<>();
	//private boolean mandatory;
	//private boolean showFilters;
	Map<String, String[]> filtersMandatory = new HashMap<>();
	Map<String, String[]> filtrosObligatorios = new HashMap<>();
	//private List<String> mandatoryFilters = new ArrayList<>(); //los filtros se almanenan con la estructura "variants.attributes.color=value1|value2"
	@Inject
	private CommercetoolsService commercetoolsservice;
	
	@Inject
	private TemplatingFunctions templatingFunctions;
	
	private static final Logger log = LoggerFactory.getLogger(FiltroProductosModel.class);
	private String agrupadores;
	private String step;
	
	public FiltroProductosModel(Node content, RD definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
	}
	
	@Override
	public String execute() {
		
		if (!templatingFunctions.isEditMode()) {
			
			String categoryid = "";
			
			try {
				//prima la categoria con la que accedemos por cabecera, sino la del componente
				if (MgnlContext.getWebContext().getRequest().getParameter(MyshopConstants.CATEGORY)!= null) {
					categoryid = MgnlContext.getWebContext().getRequest().getParameter(MyshopConstants.CATEGORY);
				} else {
					if (content.getProperty(MyshopConstants.CATEGORY) != null) {
						categoryid = content.getProperty(MyshopConstants.CATEGORY).getValue().getString();
						categoryid = sanitizerCategoryPath(categoryid);
					}
				}
			} catch (IllegalStateException | RepositoryException e) {
				categoryid = MgnlContext.getWebContext().getRequest().getParameter(MyshopConstants.CATEGORY);
			}
			
			/*if ((MgnlContext.getWebContext().getRequest().getParameter("mandatory")!= null) && (MgnlContext.getWebContext().getRequest().getParameter("mandatory").equals("true"))) {
				mandatory = true;
			}*/
			
			//eliminar si quitamos el submit
			if ((MgnlContext.getWebContext().getRequest().getParameter("agrupadores") != null) 
				&& !MgnlContext.getWebContext().getRequest().getParameter("agrupadores").isEmpty()
				&& (MgnlContext.getWebContext().getRequest().getParameter("skuPackMaster") != null)
				&& !MgnlContext.getWebContext().getRequest().getParameter("skuPackMaster").isEmpty())
					agrupadores = MgnlContext.getWebContext().getRequest().getParameter("agrupadores") 
						+ "&skuPackMaster=" + MgnlContext.getWebContext().getRequest().getParameter("skuPackMaster") ;
			
			if ((MgnlContext.getWebContext().getRequest().getParameter("step") != null) 
					&& !MgnlContext.getWebContext().getRequest().getParameter("step").isEmpty())
				step = MgnlContext.getWebContext().getRequest().getParameter("step");
			
			Map<String, String[]> filters = getAllFilters();
			Map<String, ProductFilterCT> filtersFront = new HashMap<>();
			ArrayList<String> facetas = new ArrayList<>();
			try {
				
				NodeIterator nodeTemp = content.getNodes();
				
				String idioma = templatingFunctions.language();
				
				while (nodeTemp.hasNext()) {
					Node node = nodeTemp.nextNode();
					if (node.hasProperty("filtro")) {
						ProductFilterCT filtroMagnolia = new ProductFilterCT();
						String filtro = node.getProperty("filtro").getValue().getString();//nombre en commercetools
						String tipo = node.getProperty("tipo").getValue().getString();//tipo
											
						String name = node.getProperty("name").getValue().getString();//nombre que aparece en el filtro
						if (!idioma.equals("es")) {
							if (node.hasProperty("name_"+idioma)) {
								name = node.getProperty("name_"+idioma).getValue().getString();
							}
							
						}
						
						int pos = (int)nodeTemp.getPosition();
						String key = "";
						switch (tipo) {
						case "texto": {
							key = "variants.attributes."+filtro;
							break;
						}
						case "boolean": {
							key = "variants.attributes."+filtro;
							break;
						}
						case "localized": {
							key = "variants.attributes."+filtro+".es";
							break;
						}
						case "number": {
							key = "variants.attributes."+filtro;
							break;
						}
						case "money": {
							key = "variants.attributes."+filtro;
							break;
						}
						case "date": {
							key = "variants.attributes."+filtro;
							break;
						}
						case "list": {
							key = "variants.attributes."+filtro+".key";
							break;
						}
						default:
							log.error("Tipo de filtro no reconocido");
							break;
						}
						
						facetas.add(key);
						filtroMagnolia.setKey("variants.attributes."+filtro);
						filtroMagnolia.setName(name);
						filtroMagnolia.setPos(pos);
						filtersFront.put(key, filtroMagnolia);
					}
				}
				

			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			
			try {
				if ((categoryid!= null) || !filters.isEmpty()) {
					facets = getFacetsName(sanitizerCategoryPath(categoryid),filters, facetas);
					
					if ((facets.getVariantsAttributes() != null) && (!facets.getVariantsAttributes().isEmpty())){
						Map<String, FacetResult> map = facets.getVariantsAttributes();
						for (Map.Entry<String, FacetResult> entry : map.entrySet()) {
							if (!entry.getKey().equals("variants.price.centAmount")) {
								FacetResult resultado = entry.getValue();
								ProductFilterCT productFilter = filtersFront.get(entry.getKey());
								productFilter.setKey(entry.getKey());//nombre en commercetools 
								productFilter.setAttributes(getVariantsAttributes(entry.getKey(), resultado, filters));
								
								filtrosFront.add(productFilter);
								
							} 
						}
					}
					
				}
				
				
				Collections.sort(filtrosFront);
				
				
			} catch (Exception  e) {
				e.printStackTrace();
			}
			
		}
		
		return super.execute();
	}

	
	private VariantsAttributes getVariantsAttributes(String key, FacetResult fr, Map<String, String[]> filters) {
		
		
		
		VariantsAttributes vres = new VariantsAttributes();
		
		if ((fr != null) && (fr.getType().name().equals("TERMS")) ){
			TermFacetResult tfr = (TermFacetResult) fr;
			vres.setTermsStats(tfr.getTerms());
			//recoremos y actualiza para setear el seleccionado
			vres.setSelected(filters.get(key));
		}
		
		
		
		
		return vres;
		
	}

	private Map<String, String[]> getAllFilters() {
		
		Map<String, String[]> filters = new HashMap<>();
		
		if (MgnlContext.getWebContext().getRequest().getParameterValues(MyshopConstants.FF_PRICE) != null && MgnlContext.getWebContext().getRequest().getParameterValues(MyshopConstants.FF_PRICE).length > 0) {
			filters.put(MyshopConstants.FF_PRICE, MgnlContext.getWebContext().getRequest().getParameterValues(MyshopConstants.FF_PRICE));
		}
		
		Map<String, String[]> parametros = MgnlContext.getWebContext().getRequest().getParameterMap();
		
		for (var entry : parametros.entrySet()) {
			String key = entry.getKey();
			if (key.indexOf("variants.attributes") != -1) {
				filters.put(key, MgnlContext.getWebContext().getRequest().getParameterValues(key));
			}
		}
		
		//filtros obligatorios
		String[] valoranterior = null; 
		String keyanterior = null;
		for (var entry : parametros.entrySet()) {
			String key = entry.getKey();
			if (key.equals("mandatory")) {
				filtrosObligatorios.put(keyanterior, valoranterior);
			}
			if (key.indexOf("variants.attributes") != -1) {
				valoranterior = entry.getValue();
				keyanterior = key;
			}
		}
		
		//tenemos que incluir los obligatorios para pertenecer a un pack
		String skuPackMaster = MgnlContext.getWebContext().getRequest().getParameter("skuPackMaster");
		if ((step != null) && (skuPackMaster != null) && (!skuPackMaster.isEmpty())) {
			HashMap<String, List<AgrupadorDTO>> infoPackMapSession = commercetoolsservice.getInfoPackMapSession();
			if ((infoPackMapSession != null) && (infoPackMapSession.get(skuPackMaster) != null)) {
				int pos = Integer.valueOf(MgnlContext.getWebContext().getRequest().getParameter("step"));
				List<String> agrupadorsession = infoPackMapSession.get(skuPackMaster).get(pos).getAgrupadores();
				List<String> agrupadorDTOsessionAux = cloneList(agrupadorsession);
				
				
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
		return filters;
	}
	
	private List<String> cloneList(List<String> list) {
		
		return new ArrayList<>(list);
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
		if (path!= null) {
			String[] parts = path.split("/");
			return parts[parts.length-1];
		} 
		return null;
	}
	
	/**
	 * 
	 * Metodo que devuelve todos los filtros que queremos mostrar
	 * 
	 * @param categoryid categoria sobre la que obtener los filtros
	 * @param filters filtros ya aplicados
	 * @return filtros que mostrar
	 * @throws RepositoryException 
	 * @throws IllegalStateException 
	 * @throws ValueFormatException 
	 * @throws PathNotFoundException 
	 */
	private VariantsFilterCT getFacetsName(String categoryid, Map<String, String[]> filters, ArrayList<String> facetas) throws PathNotFoundException, ValueFormatException, IllegalStateException, RepositoryException {
		
		//String keycustomergroup = commercetoolsservice.getKeyOfCustomerGroupByRoles(MgnlContext.getUser().getAllRoles());
		String keycustomergroup = commercetoolsservice.getGrupoPrecioCommerce();
		
		if (keycustomergroup != null && keycustomergroup.isEmpty()) {
			String customerid = commercetoolsservice.getIdOfCustomerGroupByCostumerId(MyShopUtils.getUserName());
			keycustomergroup = commercetoolsservice.getKeyOfCustomerGroupById(customerid);
		}
		
		if (keycustomergroup != null && !keycustomergroup.isEmpty()) {
			
			if (content.getProperty(MyshopConstants.FILTERSELECT) != null) {
				switch(content.getProperty(MyshopConstants.FILTERSELECT).getValue().getString().toLowerCase()) {
				case "generico":
					return commercetoolsservice.getFacetsGenerico(categoryid,keycustomergroup,filters,facetas);
				case "monturas":
					return commercetoolsservice.getFacets(categoryid,keycustomergroup,filters);
				case "soluciones":
					return commercetoolsservice.getSolutionsFacets(categoryid,keycustomergroup,filters);
				case "accesorios":
					return commercetoolsservice.getAccessoriesFacets(categoryid,keycustomergroup,filters);
				case "contactologia":
					return commercetoolsservice.getContactologiaFacets(categoryid,keycustomergroup,filters);
				case "marketing":
					return commercetoolsservice.getMarketingFacets(categoryid,keycustomergroup,filters);
				case "audiologiastock":
					return commercetoolsservice.getAudiologiaFacets(categoryid,keycustomergroup,filters,"Stock");
				case "audiologiaamedida":
					return commercetoolsservice.getAudiologiaFacets(categoryid,keycustomergroup,filters,"A medida");
				case "audiologiacompleta":
					return commercetoolsservice.getAudiologiaCompletaFacets(categoryid,keycustomergroup,filters);
				case "tapones":
					return commercetoolsservice.getTaponesFacets(categoryid,keycustomergroup,filters);
				case "baterias":
					return commercetoolsservice.getBateriasFacets(categoryid,keycustomergroup,filters);
				case "complementos":
					return commercetoolsservice.getComplementosFacets(categoryid,keycustomergroup,filters);
				case "accesoriosinalambricos":
					return commercetoolsservice.getAccesoriosInalambricosFacets(categoryid,keycustomergroup,filters);
				default:
					return commercetoolsservice.getFacets(categoryid,keycustomergroup,filters);
				}
			}else {
				return commercetoolsservice.getFacets(categoryid,keycustomergroup,filters);	
			}
		}
		
		return null;
	}
	
	public VariantsFilterCT getFacets() {
		return facets;
	}
	
	public List<ProductFilterCT> getFiltrosFront() {
		return filtrosFront;
	}

	
	public boolean hasFacets() {
				
		return hasTerms(facets.getVariantsAttributesColeccionEs())         || hasTerms(facets.getVariantsAttributesGamaColorMonturaEs())      ||
			   hasTerms(facets.getVariantsAttributesDimensionesAnchoOjo()) || hasTerms(facets.getVariantsAttributesDimensionesLargoVarilla()) ||
			   hasTerms(facets.getVariantsAttributesFamiliaEkon())         || hasTerms(facets.getVariantsAttributesGraduacion())              ||
			   hasTerms(facets.getVariantsAttributesMarca())               || hasTerms(facets.getVariantsAttributesMaterial())                ||
			   hasTerms(facets.getVariantsAttributesTamaniosEs())          || hasTerms(facets.getVariantsAttributesTargetEs())                ||
			   hasTerms(facets.getVariantsAttributesTipoProductoEs())      || hasTerms(facets.getVariantsAttributesStatusEkon())              ||
			   hasTerms(facets.getVariantsAttributesSubTipoProducto())     || hasTerms(facets.getVariantsAttributesBlisterocaja())            ||
			   hasTerms(facets.getVariantsAttributesReemplazo())           || hasTerms(facets.getVariantsAttributesMaterialbase())            ||
			   hasTerms(facets.getVariantsAttributesGeometria())           || hasTerms(facets.getVariantsAttributesGama())                    ||
			   hasTerms(facets.getVariantsAttributesProveedor())           || hasTerms(facets.getVariantsAttributesBproteccionSolar())        ||
			   hasTerms(facets.getVariantsAttributesColorMonturaEs())	   || hasTerms(facets.getVariantsAttributesLineaNegocio())			  ||
			   hasTerms(facets.getVariantsAttributesModelo())	   		   || hasTerms(facets.getVariantsAttributesSize())					  ||
			   hasTerms(facets.getVariantsAttributesColorAudio())		   || hasTerms(facets.getVariantsAttributesTienePromociones())		  || 
			   hasTerms(facets.getVariantsAttributesFormatosAudio())	   || hasTermsList(facets.getVariantsAttributes());
	}

	private boolean hasTerms(VariantsAttributes varAttr) {
		return varAttr != null && varAttr.hasTerms();
		
	}
	
	private boolean hasTermsList(Map<String, FacetResult> listAttirbutes) {
		return listAttirbutes != null && !listAttirbutes.isEmpty();
		
	}
	
	/*public boolean isMandatory() {
		return mandatory;
	} */
	
	public Map<String, String[]> getFiltersMandatory() {
		return filtersMandatory;
	}

	public Map<String, String[]> getFiltrosObligatorios() {
		return filtrosObligatorios;
	}
	
	
	public String getAgrupadores() {
		return agrupadores;
	}
	
	public String getStep() {
		return step;
	}


}
