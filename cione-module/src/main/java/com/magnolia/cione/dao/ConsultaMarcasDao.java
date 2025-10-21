package com.magnolia.cione.dao;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.beans.MarcaProveedorBBDD;

@ImplementedBy(ConsultaMarcasDaoImpl.class)
public interface ConsultaMarcasDao {
	public List<MarcaProveedorBBDD> getBrandsForSupplier(String cod_proveedor);
	public String getListBrands(String cod_proveedor);
}
