package com.magnolia.cione.dto;

import com.magnolia.cione.dto.CT.variants.VariantsAttributes;

public class LensFilterDTO{
	
    private VariantsAttributes proveedores;
    
    private VariantsAttributes materiales;

	public VariantsAttributes getProveedores() {
		return proveedores;
	}

	public void setProveedores(VariantsAttributes proveedores) {
		this.proveedores = proveedores;
	}

	public VariantsAttributes getMateriales() {
		return materiales;
	}

	public void setMateriales(VariantsAttributes materiales) {
		this.materiales = materiales;
	}
	
}
