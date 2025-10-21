package com.magnolia.cione.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomFieldCTDTO {
	
	private String id;
	private int version;
	private Date createdAt;
	private Date lastModifiedAt;
	private ModifiedOrCreatedByCTDTO lastModifiedBy;
	private ModifiedOrCreatedByCTDTO createdBy;
	private String key;
	private NameCustomFieldCTDTO name;
    private List<String> resourceTypeIds;
    
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
	
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}
	
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}
	
	public ModifiedOrCreatedByCTDTO getLastModifiedBy() {
		return lastModifiedBy;
	}
	
	public void setLastModifiedBy(ModifiedOrCreatedByCTDTO lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
	public ModifiedOrCreatedByCTDTO getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(ModifiedOrCreatedByCTDTO createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public NameCustomFieldCTDTO getName() {
		return name;
	}
	
	public void setName(NameCustomFieldCTDTO name) {
		this.name = name;
	}
	
	public List<String> getResourceTypeIds() {
		return resourceTypeIds;
	}
	
	public void setResourceTypeIds(List<String> resourceTypeIds) {
		this.resourceTypeIds = resourceTypeIds;
	}

}
