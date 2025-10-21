package com.magnolia.cione.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.commercetools.api.models.category.Category;
import com.google.inject.ImplementedBy;

@ImplementedBy(CategoryServiceImpl.class)
public interface CategoryService {
	
	public Category getCategoryById(String id);
	
	public Category getCategoryByName(Locale locale, String name);
	
	public List<String> getIdCategoriesByParent(String name);
	
	public String getCategoryIdByName(Locale locale, String name);
	
	public List<Category> getMarcas();
	
	public List<Category> getMarcasRelMonturas(String idRelatedCategory, int limit, Map<String, String> filters);
	
	public List<Category> getColeccionesMonturas(String idRelatedCategory, int limit, String categorylabel);
	
	public Category getRecommendedCategory(String SKU);

}
