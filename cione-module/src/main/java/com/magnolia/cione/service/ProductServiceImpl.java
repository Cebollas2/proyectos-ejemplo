package com.magnolia.cione.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.models.product.ProductProjection;
import com.commercetools.api.models.product.ProductProjectionPagedQueryResponse;
import com.commercetools.api.models.product.ProductVariant;
import com.magnolia.cione.setup.CioneEcommerceConectionProvider;

public class ProductServiceImpl implements ProductService {
	
	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Inject
	private CioneEcommerceConectionProvider cioneEcommerceConectionProvider;
	
	
	public ProductProjection getProductByAliasEkon(String aliasEkon) {
		
		String where = "variants(attributes(name=\"aliasEkon\" and value=\"" + aliasEkon + "\")) or"
				+ " masterVariant(attributes(name=\"aliasEkon\" and value=\"" + aliasEkon + "\"))";
		ProductProjectionPagedQueryResponse productosresponse =  cioneEcommerceConectionProvider.getApiRoot().productProjections()
				.get()
				.withWhere(where)
				//.withQueryParam("variants.attributes.gruposPrecio", "PERSONALOTROS")
			    .withStaged(false)
			    .executeBlocking()
			    .getBody();
		/*QueryPredicate<ProductProjection> predicate = QueryPredicate.of(where);
        Query<ProductProjection> query = ProductProjectionQuery.ofCurrent().withPredicates(predicate);
       
        CompletionStage<PagedQueryResult<ProductProjection>> product_query = cioneEcommerceConectionProvider.getClient().execute(query);
        PagedQueryResult<ProductProjection> queryresult = product_query.toCompletableFuture().join();*/
        List<ProductProjection> productos = productosresponse.getResults();
		
		if (productosresponse.getCount() > 0)
			return productos.get(0);
		else
			return null;
	}
	
	public ProductVariant getVariantBySku(String sku) {
		@NotNull @Valid List<ProductProjection> productos = cioneEcommerceConectionProvider.getApiRoot().productProjections()
				.search().get()
                .withMarkMatchingVariants(true)
                .withStaged(false) // Para obtener solo los productos publicados
                //.withFilterQuery(f -> f.variants().sku().is(sku)) // Filtra por SKU
                .withFilter("variants.sku:\"" + sku + "\"")
                .executeBlocking()
                .getBody()
                .getResults();
				
		
		if(productos.size() > 0) {
			ProductProjection product = productos.get(0);
			Optional<ProductVariant> productVariant= null;
			
			if (product.getMasterVariant().getSku().equals(sku)) {
				productVariant = Optional.of(product.getMasterVariant());
			} else {
				productVariant = product.getVariants().stream().filter(v -> sku.equals(v.getSku())).findFirst();
			}
			
			return productVariant.get();
		} else
			return null;
	}
}
