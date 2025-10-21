package com.magnolia.cione.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.magnolia.cione.dto.CT.CustomCT;
import com.magnolia.cione.dto.CT.LocaleCT;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDTO {

    private String id;
	private int version;
    private String key;
    private LocaleCT name;
    private String name_es;
	private LocaleCT slug;
    private LocaleCT description;
    private List<TypeCTDTO> ancestors;
    private TypeCTDTO parent;
    private String orderHint;
    private String externalId;
    private CustomCT custom;
    private String logoListado;
	private String logoProducto;
    private List<Object> assets;
    
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public LocaleCT getName() {
		return name;
	}
	public void setName(LocaleCT name) {
		this.name = name;
	}
    public String getName_es() {
		return name_es;
	}
	public void setName_es(String name_es) {
		this.name_es = name_es;
	}
	public LocaleCT getSlug() {
		return slug;
	}
	public void setSlug(LocaleCT slug) {
		this.slug = slug;
	}
	public LocaleCT getDescription() {
		return description;
	}
	public void setDescription(LocaleCT description) {
		this.description = description;
	}
	public List<TypeCTDTO> getAncestors() {
		return ancestors;
	}
	public void setAncestors(List<TypeCTDTO> ancestors) {
		this.ancestors = ancestors;
	}
	public TypeCTDTO getParent() {
		return parent;
	}
	public void setParent(TypeCTDTO parent) {
		this.parent = parent;
	}
	public String getOrderHint() {
		return orderHint;
	}
	public void setOrderHint(String orderHint) {
		this.orderHint = orderHint;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public CustomCT getCustom() {
		return custom;
	}

	public void setCustom(CustomCT custom) {
		this.custom = custom;
	}
    public String getLogoListado() {
		return logoListado;
	}
	public void setLogoListado(String logoListado) {
		this.logoListado = logoListado;
	}
	public String getLogoProducto() {
		return logoProducto;
	}
	public void setLogoProducto(String logoProducto) {
		this.logoProducto = logoProducto;
	}
	public List<Object> getAssets() {
		return assets;
	}
	public void setAssets(List<Object> assets) {
		this.assets = assets;
	}

	@Override
	public String toString() {
		return "Categoria: " + name.es;
	}

}
