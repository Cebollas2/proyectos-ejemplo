package com.magnolia.cione.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.LensFilterDTO;

@ImplementedBy(LensServiceImpl.class)
public interface LensService {
	
	/**
	 * 
	 * Metodo para obtener los filtros de lentes
	 * @param keycustomergroup 
	 * 
	 * @return VariantsFilterCT listado con todo los filtros
	 */
	public LensFilterDTO getFacets(Map<String, String[]> filters, String keycustomergroup);

	public List<String> getCilindros(Map<String, String[]> filters);

	public List<String> getSpheresByCylinder(int cylinder);

	public LinkedHashMap<String, String> getLensByCylinderAndSphere(int cylinder, int sphere);

	public List<String> getDiametersByLens(int cylinder, int sphere, int lens);
	
	public String getSkuLens(int cylinder, int sphere, int lens, int diameter);

	public Map<String, String> getPriceBySku(String sku);

	public Map<String, String> getLineDataInfo(int cylinder, int sphere, int lens, int diameter);

}
