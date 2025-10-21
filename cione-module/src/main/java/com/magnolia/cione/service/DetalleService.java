package com.magnolia.cione.service;

import java.util.List;

import com.commercetools.api.models.category.Category;
import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.VariantDTO;

@ImplementedBy(DetalleServiceImpl.class)
public interface DetalleService {
	
	/**
	 * 
	 * A partir un nombre de una categoria, devuelve el listado de categoria incluidas las hijas 
	 * 
	 * @param String name no puede ser nulo
	 * @return List<Category> listado de categorias
	 *
	 */
	public List<Category> getMarcas(String name);
	
	/**
	 * 
	 * A partir un sku, devuelve toda la informacion para ser tratada desde 
	 * la página de detalle de producto
	 * 
	 * @param String name no puede ser nulo
	 * @param boolean en caso de ser true reduce el numero de operaciones
	 * @return VariantDTO objeto con toda la info de producto
	 *
	 
	public VariantDTO getInfoVariant(String sku);*/
	
	/**
	 * 
	 * A partir un sku, devuelve toda la informacion del producto para ser tratada desde 
	 * la página de detalle de producto
	 * 
	 * @param String sku no puede ser nulo
	 * @param String skupack no puede ser nulo
	 * @return VariantDTO objeto con toda la info de producto
	 *
	 */
	public VariantDTO getInfoVariant(String sku, String skupack);
	
	/**
	 * 
	 * A partir un skupack, devuelve toda la informacion del packs de partida para ser tratada desde 
	 * la página de detalle de producto
	 * @param String skuPackMaster no puede ser nulo
	 * @return VariantDTO objeto con toda la info de producto
	 *
	 */
	public VariantDTO getInfoVariantPack(String skuPackMaster) throws Exception;
	
	
	/**
	 * 
	 * A partir una key, devuelve toda la informacion para ser tratada desde 
	 * la página de detalle de producto
	 * 
	 * @param String key no puede ser nulo
	 * @return VariantDTO objeto con toda la info de producto
	 *
	 */
	public String getSkuByKey(String key);
}
