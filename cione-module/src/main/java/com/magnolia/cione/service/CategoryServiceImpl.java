package com.magnolia.cione.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.models.category.Category;
import com.commercetools.api.models.category.CategoryTree;
import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.common.Reference;
import com.commercetools.api.models.product.FacetResult;
import com.commercetools.api.models.product.FacetTerm;
import com.commercetools.api.models.product.ProductProjection;
import com.commercetools.api.models.product.ProductProjectionPagedSearchResponse;
import com.commercetools.api.models.product.ProductVariant;
import com.commercetools.api.models.product.TermFacetResult;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.setup.CioneEcommerceConectionProvider;
import com.magnolia.cione.utils.MyShopUtils;

//import static org.assertj.core.api.Assertions.*;


public class CategoryServiceImpl implements CategoryService{
	
	private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@Inject
	private CioneEcommerceConectionProvider cioneEcommerceConectionProvider;
	
	@Inject
	private CommercetoolsService commercetoolsservice;	
	
	@Inject
	private CommercetoolsServiceAux commercetoolsserviceaux;	
	
	public Category getCategoryById(String id) {
		CategoryTree tree =cioneEcommerceConectionProvider.getCategoryTree();
		Category category = tree.findById(id).get();
		return category;
	}

	public Category getCategoryByName(Locale locale, String name) {
		
		Category result = null;
		List<Category> categorias = 
				cioneEcommerceConectionProvider
				.getApiRoot()
				.categories()
				.get()
				.withWhere("name("+locale.getLanguage()+"=\""+ name+"\")")
				.executeBlocking()
				.getBody()
				.getResults();
		if ((categorias!= null) && (!categorias.isEmpty()))
			result = categorias.get(0);
        return result;
		
	}

	
	/**devuelve el listado de categorias a partir de una padre*/
	public List<String> getIdCategoriesByParent(String name) {
        String idParentCategory = getCategoryByName(MyshopConstants.esLocale, name).getId();
		List<Category> categorias = 
			cioneEcommerceConectionProvider
			.getApiRoot()
			.categories()
			.get()
			.withWhere("parent(id=\"" + idParentCategory + "\")")
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
	
	public String getCategoryIdByName(Locale locale, String name) {
		String result ="";
		List<Category> categorias = 
				cioneEcommerceConectionProvider
				.getApiRoot()
				.categories()
				.get()
				.withWhere("name("+locale.getLanguage()+"=\""+ name+"\")")
				.executeBlocking()
				.getBody()
				.getResults();
		if ((categorias!= null) && (!categorias.isEmpty()))
			result = categorias.get(0).getId();
		return result;
	}
	

	/*public Category getRecommendedCategory(String SKU) {
		Category categoriaRecomendada = null;
		ProductProjectionSearch search = ProductProjectionSearch
				.ofCurrent()
				.bySku(SKU)
				.plusQueryFilters(m -> m.allVariants().attribute().ofString("gruposPrecio").is(commercetoolsservice.getGrupoPrecioCommerce()))
				.withMarkingMatchingVariants(true);
		PagedSearchResult<ProductProjection> result = cioneEcommerceConectionProvider.getClient().executeBlocking(search);
		if (result.getTotal() > 0) { 
			ProductProjection product = result.getResults().get(0);
			product.getCategories()
		}
		
		return categoriaRecomendada;
	}*/
	
	//PROBAR BIEN!!
	public Category getRecommendedCategory(String sku) {
		Category categoriaRecomendada = null;
		
		String grupoPrecio = commercetoolsserviceaux.getGrupoPrecioCommerce();
		ProductProjection product = commercetoolsserviceaux.getProductBySku(sku, grupoPrecio);
		if (product != null) {
			ProductVariant variant = commercetoolsserviceaux.findVariantBySku(product, sku);
			
			if (MyShopUtils.hasAttribute("tipoProducto", variant.getAttributes())) {
				LocalizedString tipo = 
					(LocalizedString) MyShopUtils.findAttribute("tipoProducto", variant.getAttributes()).getValue();
				if (tipo != null) {
					String categoryType = tipo.get(MyshopConstants.esLocale).toUpperCase();
					Category category = getCategoryByName(MyshopConstants.esLocale, categoryType);
					try {
						if ((category!= null) && (category.getCustom() !=null)) {
							List<Reference> set = (List<Reference>) category.getCustom().getFields().values().get("refRelCatRecomendados");
							if ((set != null) && (set.iterator().hasNext())) {
								categoriaRecomendada = getCategoryById(set.iterator().next().getId());
							}
						}
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				} else {
					log.warn("tipoProducto is Empty in " + sku);
				}
			}
		}
		return categoriaRecomendada;
		
	}	
	
	public List<Category> getMarcas() {
		
        String idMarcas = getCategoryIdByName(MyshopConstants.esLocale, "MARCAS");
        
        String idParentCategory = getCategoryByName(MyshopConstants.esLocale, idMarcas).getId();
		List<Category> marcas = 
			cioneEcommerceConectionProvider
			.getApiRoot()
			.categories()
			.get()
			.withWhere("parent(id=\"" + idParentCategory + "\")")
			.withSort("orderHint asc")
			.executeBlocking()
			.getBody()
			.getResults();
      
        return marcas;
		
	}
 
    public List<Category> getMarcasRelMonturas(String idRelatedCategory, int limit, Map<String, String> filters) {
    	
    	List<Category> marcas = new ArrayList<Category>();
    	String categorylabel = getCategoryIdByName(MyshopConstants.esLocale, "MARCAS");
    	List<String> querys = new ArrayList<>();
		querys.add("parent(id=\"" + categorylabel + "\")");
		querys.add("custom(fields(isInactive=false))");
		if (idRelatedCategory != null && !idRelatedCategory.isEmpty()) 
			querys.add("custom(fields(refRelCategories(id=\"" + idRelatedCategory + "\")))");
		marcas = cioneEcommerceConectionProvider.getApiRoot()
			.categories()
			.get()
			.withWhere(querys)
			.withLimit(limit)
			.withSort("orderHint asc")
			.executeBlocking()
			.getBody()
			.getResults();
        
    	if (!marcas.isEmpty()) {
			String keycustomergroup = commercetoolsservice.getGrupoPrecioCommerce();
	
			Map<String, Category> categoriesMap = new HashMap<>();
			marcas.forEach(category -> categoriesMap.put(category.getId(), category));
			
			String facetCategory = "categories.id";
			//TermFacetExpression<ProductProjection> facetCategory = TermFacetExpression.of("categories.id");
			List<String> listadoFiltros = new ArrayList<>();
			filters.forEach((k,v) -> listadoFiltros.add(k + ":" + v));
			
			String categoryFilter = "";
			
			if (!marcas.isEmpty()) {
				String categories = marcas.stream()
	                    .map(marca -> "\"" + marca.getId() + "\"")
	                    .collect(Collectors.joining(","));
				categoryFilter = "categories.id:" + categories;
			}
			
			ProductProjectionPagedSearchResponse product_search = 
					cioneEcommerceConectionProvider
					.getApiRoot()
					.productProjections()
					.search()
					.get()
					//.withFilter(m -> m.categories().id().containsAny(categoriesMap.keySet())) 
					.withFilterQuery(categoryFilter)
					.addFilterQuery(listadoFiltros)
					.addFilterQuery("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
					//.withMarkMatchingVariants(true)
					.withLimit(1)
					.withFacet(facetCategory)
					.withStaged(false)
					.executeBlocking()
					.getBody();
			
			FacetResult facet = product_search.getFacets().values().get(facetCategory);
			Set<String> facetCategories = new HashSet<>();
			if (facet instanceof TermFacetResult) {
				TermFacetResult termFacet = (TermFacetResult) facet;
				for (FacetTerm term : termFacet.getTerms()) {
					facetCategories.add((String) term.getTerm());
				}
			}
			
			/*List<FilterExpression<ProductProjection>> listFilter = commercetoolsservice.addFiltersSingle(filters);
			
			ProductProjectionSearch result = ProductProjectionSearchBuilder.ofCurrent()
					.plusQueryFilters(m -> m.categories().id().containsAny(categoriesMap.keySet()))
					.plusQueryFilters(listFilter)
					.plusQueryFilters(m -> m.allVariants().attribute().ofString(MyshopConstants.PRICEGROUP).is(keycustomergroup))
					.limit(1)
					.plusFacets(facetCategory)
					.build();
			
			CompletionStage<PagedSearchResult<ProductProjection>> product_search = cioneEcommerceConectionProvider.getClient().execute(result);
			PagedSearchResult<ProductProjection> searchresult = product_search.toCompletableFuture().join();		
			Map<String, FacetResult> facets = searchresult.getFacetsResults();
		
			
			
			for (Map.Entry<String, FacetResult> entry : facets.entrySet()) {
				FacetResult facet = entry.getValue();
				if (facet instanceof TermFacetResult) {
					TermFacetResult termFacet = (TermFacetResult) facet;
					for (TermStats term : termFacet.getTerms()) {
						facetCategories.add(term.getTerm());
					}
				}
			}*/
			
			for (Map.Entry<String, Category> entry : categoriesMap.entrySet()) {
				if (!facetCategories.contains(entry.getKey())) {
					marcas.remove(entry.getValue());
				}
			}
    	}
 
        return marcas;
	}
    
    
    public List<Category> getColeccionesMonturas(String idRelatedCategory,int limit, String categorylabel) {
    	List<Category> colecciones_Monturas = new ArrayList<Category>();
    	try {
			if (!categorylabel.isEmpty()) {
				List<String> querys = new ArrayList<>();
				querys.add("parent(id=\"" + categorylabel + "\")");
				querys.add("custom(fields(isInactive=false))");
				if (idRelatedCategory != null && !idRelatedCategory.isEmpty()) 
					querys.add("custom(fields(refRelCategories(id=\"" + idRelatedCategory + "\")))");
				
				colecciones_Monturas = cioneEcommerceConectionProvider.getApiRoot()
					.categories()
					.get()
					.withWhere(querys)
					.withLimit(limit)
					.withSort("orderHint asc")
					.executeBlocking()
					.getBody()
					.getResults();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
    	if (!colecciones_Monturas.isEmpty()) {
			String keycustomergroup = commercetoolsservice.getGrupoPrecioCommerce();
	
			Map<String, Category> categoriesMap = new HashMap<>();
			colecciones_Monturas.forEach(category -> categoriesMap.put(category.getId(), category));
			
			//filtro para cualquier categoria del listado
			String facetCategory = "categories.id";
			String categoryFilter = "";
			if (!colecciones_Monturas.isEmpty()) {
				String categories = colecciones_Monturas.stream()
	                    .map(marca -> "\"" + marca.getId() + "\"")
	                    .collect(Collectors.joining(","));
				categoryFilter = "categories.id:" + categories;
			}
			ProductProjectionPagedSearchResponse product_search = 
					cioneEcommerceConectionProvider
					.getApiRoot()
					.productProjections()
					.search()
					.get()
					//.withFilter(m -> m.categories().id().containsAny(categoriesMap.keySet())) 
					.withFilterQuery(categoryFilter) //PROBAR BIEN!! revisar si lo hace bien
					.addFilterQuery("variants.attributes.gruposPrecio:\""+ keycustomergroup +"\"")
					//.withMarkMatchingVariants(true)
					.withLimit(1)
					.withFacet(facetCategory)
					.withStaged(false)
					.executeBlocking()
					.getBody();
			
			FacetResult facet = product_search.getFacets().values().get(facetCategory);
			Set<String> facetCategories = new HashSet<>();
			if (facet instanceof TermFacetResult) {
				TermFacetResult termFacet = (TermFacetResult) facet;
				for (FacetTerm term : termFacet.getTerms()) {
					facetCategories.add((String) term.getTerm());
				}
			}
	
			/*ProductProjectionSearch result = ProductProjectionSearchBuilder.ofCurrent()
					.plusQueryFilters(m -> m.categories().id().containsAny(categoriesMap.keySet()))
					.plusQueryFilters(m -> m.allVariants().attribute().ofString(MyshopConstants.PRICEGROUP).is(keycustomergroup)).limit(1).plusFacets(facetCategory)
					.build();
			
			CompletionStage<PagedSearchResult<ProductProjection>> product_search = cioneEcommerceConectionProvider.getClient().execute(result);
			PagedSearchResult<ProductProjection> searchresult = product_search.toCompletableFuture().join();	
			Map<String, FacetResult> facets = searchresult.getFacetsResults();
		
			Set<String> facetCategories = new HashSet<>();
			
			for (Map.Entry<String, FacetResult> entry : facets.entrySet()) {
				FacetResult facet = entry.getValue();
				if (facet instanceof TermFacetResult) {
					TermFacetResult termFacet = (TermFacetResult) facet;
					for (TermStats term : termFacet.getTerms()) {
						facetCategories.add(term.getTerm());
					}
				}
			}*/	
			//elimina las categorias que no ha conseguido encontrar filtro en el listado de productos, es decir no tiene productos asociados
			//tener en cuenta que solo buscamos las categorias asociadas, no si están asociadas a categorías hijas
			for (Map.Entry<String, Category> entry : categoriesMap.entrySet()) {
				if (!facetCategories.contains(entry.getKey())) {
					colecciones_Monturas.remove(entry.getValue());
				}
			}
    	}
        return colecciones_Monturas;
    }
    
    
    /*private CompletionStage<List<Category>> findNext(final CategoryQuery seedQuery, final CategoryQuery query, final List<Category> categorias) {
        final CompletionStage<PagedQueryResult<Category>> pageResult = cioneEcommerceConectionProvider.getClient().execute(query);
        return pageResult.thenCompose(page -> {
            final List<Category> results = page.getResults();
            categorias.addAll(results);
            final boolean isLastQueryPage = results.size() < 10;
            if (isLastQueryPage) {
                return CompletableFuture.completedFuture(categorias);
            } else {
                final String lastId = getIdForNextQuery(page);
                return findNext(seedQuery, seedQuery
                        .plusPredicates(m -> m.id().isGreaterThan(lastId)), categorias);
            }
        });
    }
    
    private <T extends Identifiable<T>> String getIdForNextQuery(final PagedResult<T> pagedResult) {
        final List<T> results = pagedResult.getResults();
        final int indexLastElement = results.size() - 1;
        return results.get(indexLastElement).getId();
    }*/

}