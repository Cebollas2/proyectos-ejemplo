package com.magnolia.cione.beans;

import java.util.List;

public class MasterVariant {

    private List<Attribute> attributes;
    private List<Object> assets;
    private List<Image> images;
    private List<Price> prices;
    private String key;
    private String sku;
    private int id;
    
	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	
	public List<Object> getAssets() {
		return assets;
	}
	
	public void setAssets(List<Object> assets) {
		this.assets = assets;
	}
	
	public List<Image> getImages() {
		return images;
	}
	
	public void setImages(List<Image> images) {
		this.images = images;
	}
	
	public List<Price> getPrices() {
		return prices;
	}
	
	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getSku() {
		return sku;
	}
	
	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
    
}
