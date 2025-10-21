package com.magnolia.cione.dao;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.PackInfoDTO;
import com.magnolia.cione.dto.VariantDTO;

@ImplementedBy(PackDaoImpl.class)
public interface PackDao {
	
	/**
	 * 
	 * Metodo que a partir de un determinado Pack, devuelve un listado de 
	 * productos contenidos en el pack, con la informacion requerida en el front
	 * @param VariantDTO variant objeto con toda la informacion del producto Pack
	 * @return List<VariantDTO> listado de productos contenidos en el pack
	 *
	 */
	public List<VariantDTO> getPackContent(VariantDTO variant);
	
	
	/**
	 * 
	 * Funcion que devuelve el listado de packs donde esta incluido el producto
	 * @param VariantDTO variant objeto con toda la informacion del producto
	 * @return List<String> listado con los skus de los packs donde esta incluido el producto
	 *
	 */
	public List<String> getPacksByProduct(VariantDTO variant);
	
	/**
	 * 
	 * Metodo que a partir de un determinado Pack, devuelve un listado de 
	 * productos contenidos en el pack, con la informacion de la bbdd
	 * @param String sku con el sku del producto Pack
	 * @return List<PackInfoDTO> listado de productos contenidos en el pack
	 *
	 */
	public List<PackInfoDTO> getPackInfoFromSku(String sku);

}
