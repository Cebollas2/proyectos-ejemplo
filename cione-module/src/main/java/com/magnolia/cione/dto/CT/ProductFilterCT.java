package com.magnolia.cione.dto.CT;

import com.magnolia.cione.dto.CT.variants.VariantsAttributes;

public class ProductFilterCT implements Comparable<ProductFilterCT>{
	private String name;
	private String key;
	private int pos;
	private VariantsAttributes attributes;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public VariantsAttributes getAttributes() {
		return attributes;
	}
	public void setAttributes(VariantsAttributes attributes) {
		this.attributes = attributes;
	}
	
	
	@Override
	public int compareTo(ProductFilterCT o) {
		int result = this.pos - o.getPos();
		return result;
	}
}
