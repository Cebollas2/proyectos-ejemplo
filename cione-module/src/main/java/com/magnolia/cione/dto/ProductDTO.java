package com.magnolia.cione.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.magnolia.cione.beans.Description;
import com.magnolia.cione.beans.MasterVariant;
import com.magnolia.cione.beans.Name;
import com.magnolia.cione.beans.Type;
import com.magnolia.cione.beans.Variant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {
	
    private Date lastModifiedAt;
    private Date createdAt;
    private Type taxCategory;
    private String key;
    private boolean published;
    private boolean hasStagedChanges;
    private SearchKeywordsDTO searchKeywords;
    private MasterVariant masterVariant;
    private List<Variant> variants;
    private List<Type> categories;
    private Description description;
    private Name name;
    private Type productType;
    private int version;
    private String id;
    
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Type getTaxCategory() {
		return taxCategory;
	}
	public void setTaxCategory(Type taxCategory) {
		this.taxCategory = taxCategory;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}
	public boolean isHasStagedChanges() {
		return hasStagedChanges;
	}
	public void setHasStagedChanges(boolean hasStagedChanges) {
		this.hasStagedChanges = hasStagedChanges;
	}
	public SearchKeywordsDTO getSearchKeywords() {
		return searchKeywords;
	}
	public void setSearchKeywords(SearchKeywordsDTO searchKeywords) {
		this.searchKeywords = searchKeywords;
	}
	public MasterVariant getMasterVariant() {
		return masterVariant;
	}
	public void setMasterVariant(MasterVariant masterVariant) {
		this.masterVariant = masterVariant;
	}
	public List<Variant> getVariants() {
		return variants;
	}
	public void setVariants(List<Variant> variants) {
		this.variants = variants;
	}
	public List<Type> getCategories() {
		return categories;
	}
	public void setCategories(List<Type> categories) {
		this.categories = categories;
	}
	public Description getDescription() {
		return description;
	}
	public void setDescription(Description description) {
		this.description = description;
	}
	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}
	public Type getProductType() {
		return productType;
	}
	public void setProductType(Type productType) {
		this.productType = productType;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
