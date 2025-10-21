package com.magnolia.cione.service;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(FittingBoxServiceImpl.class)
public interface FittingBoxService {

	/**
	 * 
	 * Funcion para comprobar en el servicio de Fitting Box
	 * si esta o no el producto disponible para la prueba virtual
	 * 
	 * @param sku del producto
	 * @return true o false si tiene o no prueba virtual
	 * 
	 
	public boolean isInFittingBox(String sku);
	
	/**
	 * 
	 * Funcion para comprobar en el servicio de Fitting Box
	 * un listado de productos.
	 * 
	 * @param skuList listado de skus de productos
	 * @return un mapa que asocia el sku del producto con true o false
	 * segun este o no en el servicio dicho producto 
	 * 
	 
	public Map<String, Boolean> isInFittingBox(List<String> skuList);*/
	
	/**
	 * 
	 * Funcion para comprobar en el servicio de Fitting Box
	 * un listado de productos.
	 * 
	 * @param skuList listado de skus de productos
	 * @return un mapa que asocia el sku del producto con true o false
	 * segun este o no en el servicio dicho producto 
	 * 
	 */
	public Map<String, Boolean> isInFittingBox(List<String> skuList);
	
}
