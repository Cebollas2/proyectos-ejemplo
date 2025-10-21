package com.magnolia.cione.dao;

import java.util.List;

import javax.naming.NamingException;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.PromocionesDTO;
import com.magnolia.cione.dto.VariantDTO;

@ImplementedBy(PromocionesDaoImpl.class)
public interface PromocionesDao {
	public List<PromocionesDTO> getPromociones (VariantDTO variantResult) throws NamingException;
	
	public String getTipoPromocion(String grupoPrecioCommerce, String aliasEkon, String codigo_central) throws NamingException;
	
	public double getPrecioEscalado(String grupoPrecio, String aliasEkon, String codigo_central, int cantidad) throws NamingException;
	
	public PromocionesDTO getRecargo(VariantDTO variantResult) throws NamingException;
}
