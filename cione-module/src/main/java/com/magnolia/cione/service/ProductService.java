package com.magnolia.cione.service;

import com.commercetools.api.models.product.ProductProjection;
import com.commercetools.api.models.product.ProductVariant;
import com.google.inject.ImplementedBy;

@ImplementedBy(ProductServiceImpl.class)
public interface ProductService {
	
	public ProductProjection getProductByAliasEkon(String aliasEkon);
	
	public ProductVariant getVariantBySku(String sku);
	
}