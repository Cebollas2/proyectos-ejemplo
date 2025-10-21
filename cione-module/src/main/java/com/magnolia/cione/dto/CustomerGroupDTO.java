package com.magnolia.cione.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerGroupDTO {
	
	public String id;
    public int version;
    public Date createdAt;
    public Date lastModifiedAt;
	private ModifiedOrCreatedByCTDTO lastModifiedBy;
	private ModifiedOrCreatedByCTDTO createdBy;
	private String name;
	private String key;
	
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
}
