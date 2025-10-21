package com.magnolia.cione.setup;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.models.category.Category;
import com.commercetools.api.models.category.CategoryImpl;
import com.commercetools.api.models.category.CategoryTree;
import com.magnolia.cione.constants.MyshopConstants;


public class CategoryUtils{
	
	private static final Logger log = LoggerFactory.getLogger(CategoryUtils.class);
	
	@Inject
	private CioneEcommerceConectionProvider cioneEcommerceConectionProvider;

	public CategoryUtils() {
		
	}

	public CategoryTree getCategoryTreeByKey(String key) {
 
		/*final CompletionStage<List<Category>> categoriesStage = 
				QueryExecutionUtils.queryAll(cioneEcommerceConectionProvider.getClient(), CategoryQuery.of(), 500);
		final List<Category> categories = SphereClientUtils.blockingWait(categoriesStage, Duration.ofMinutes(1));*/
		
		CategoryTree tree =cioneEcommerceConectionProvider.getCategoryTree();
		
		Category category = tree.findByKey(key).get();
		CategoryTree subtree = tree.getSubtree(Collections.singletonList(category));
		List<Category> listado= subtree.getAllAsFlatList();
		
		for (Category categoryChild: listado) {
			log.debug(categoryChild.getName().get(MyshopConstants.esLocale));
		}
		
		return tree;
    }
	
	public CategoryTree getCategoryTreeById(String categoryId) {
		CategoryTree subtree = null;
		CategoryTree tree =cioneEcommerceConectionProvider.getCategoryTree();
		if ((tree.findById(categoryId) != null) && (tree.findById(categoryId).isPresent())){
			Category category = tree.findById(categoryId).get();
			subtree = tree.getSubtree(Collections.singletonList(category));
		}
		return subtree;
    }
	
	public Category getCategoryByKey(String key) {
		CategoryTree tree =cioneEcommerceConectionProvider.getCategoryTree();
		Category category = new CategoryImpl();
		if (tree.findByKey(key) != null)
			category = tree.findByKey(key).get();
		return category;
    }
	
	public Category getCategoryById(String id) {
		CategoryTree tree =cioneEcommerceConectionProvider.getCategoryTree();
		Category category = tree.findById(id).get();
		return category;
	}

    
    /*private Response request(String definitionName, String connectionName, Function<CartProvider, Response> function) {
        Optional<Connection> connection = ecommerceConnectionProvider.getConnection(definitionName, connectionName);
        if (!connection.isPresent()) {
            return badRequestForNotPresentConnection();
        }
        return null;

//        return connection
//                .map(this::getMatchingProvider)
//                .map(Optional::get)
//                .map(function)
//                .orElse(badRequestForMatchingProvider());
    }
    
    private Response badRequestForNotPresentConnection() {
        return Response.status(BAD_REQUEST).entity("Corresponding connection couldn't be found.").build();
    }

    private Response badRequestForMatchingProvider() {
        return Response.status(BAD_REQUEST)
                .entity("Either ecommerce type or corresponding cart implementation couldn't not be found.")
                .build();
    }
    
//    private Optional<CartProvider> getMatchingProvider(Connection connection) {
//        Optional<String> ecommerceType = getEcommerceType(connection);
//        if (!ecommerceType.isPresent()) {
//            return Optional.empty();
//        }
//
//        List<CartProvider> matchingCartProviders = cartProviders.stream()
//                .filter(mapper -> mapper.getType().equalsIgnoreCase(ecommerceType.get()))
//                .collect(Collectors.toList());
//
//        if (matchingCartProviders.size() > 1) {
//            log.warn("Operate on the first CartProvider but it found [{}] matches, please check your settings", matchingCartProviders.size());
//        }
//
//        return matchingCartProviders.stream().findAny();
//    }
    
    private Optional<Connection> getConnection(String definitionName, String connectionName) {
        return ecommerceConnectionProvider.getConnection(definitionName, connectionName);
    }
    
    /*
     * Metodo que dada un nombre de categoria nos devuelve sus categorias relacionadads
     * 
    public List<Category> getCategoryRelated(String idRelatedCategory) {
    	List<Category> categorias = new ArrayList<Category>();
    	
    	if (idRelatedCategory != null && !idRelatedCategory.isEmpty()) {
    		String relMonturas = "custom(fields(refRelCategories(id=\"" + idRelatedCategory + "\")))";
        	QueryPredicate<Category> predicate = QueryPredicate.of(relMonturas);
        	QuerySort<Category> qs = QuerySort.of("orderHint asc");
        	Query<Category> query = CategoryQuery.of().withPredicates(predicate).withSort(qs);
        	CompletionStage<PagedQueryResult<Category>> marcas_query = cioneEcommerceConectionProvider.getClient().execute(query);
            PagedQueryResult<Category> actualMarcas = marcas_query.toCompletableFuture().join();
            categorias = actualMarcas.getResults();
    	}
        
        
        return categorias;
	}*/
}
