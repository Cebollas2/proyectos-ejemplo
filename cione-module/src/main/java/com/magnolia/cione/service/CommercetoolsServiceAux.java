package com.magnolia.cione.service;

import java.util.List;

import com.commercetools.api.models.common.Price;
import com.commercetools.api.models.product.ProductProjection;
import com.commercetools.api.models.product.ProductVariant;
import com.google.inject.ImplementedBy;

@ImplementedBy(CommercetoolsServiceAuxImpl.class)
public interface CommercetoolsServiceAux {
	
	/**
	 * Consulta un producto a partir de su sku y su grupo de precio
	 *
	 * @param String sku del producto
	 * @param String grupoPrecio al que pertenece el usuario
	 * @return ProductProjection producto 
	 */
	public ProductProjection getProductBySku(String sku, String grupoPrecio);
	
	/**
	 * Consulta un producto filtrando por su sku y su grupo de precio
	 * aplica staged=false y withMarkMatchingVariants
	 *
	 * @param String sku del producto
	 * @param String grupoPrecio al que pertenece el usuario
	 * @return ProductProjection producto 
	 */
	public ProductProjection getProductBySkuFilter(String sku, String grupoPrecio);
	
	/**
	 * Devuelve el grupo de precio en CommerceTools al que pertenece el socio 
	 * o el socio simulado a partir de la tabla de equivalencias (no consulta a través del api)
	 *
	 * @return String Grupo Precio en CommmerceTools
	 */
	public String getGrupoPrecioCommerce();
	
	/**
	 * Devuelve el Price de un listado de precios para ese customerGroup
	 * @param String keycustomergroup (grupo de precio)
	 * @param List<Price> prices de la variante
	 *
	 * @return Price Precio en CommmerceTools
	 */
	public Price getPriceBycustomerGroup(String keycustomergroup, List<Price> prices);
	
	/**
	 * Encuentra una variante por el sku en el producto ya sea master o variante
	 *
	 * @return ProductVariant variante
	 */
	public ProductVariant findVariantBySku(ProductProjection product, String sku);
	
	/**
	 * sustituye al AllVariants del anterior sdk
	 *
	 * @return List<ProductVariant> con todas las variantes incluida la master	 */	
	public List<ProductVariant> getAllVariants(ProductProjection product);
}
