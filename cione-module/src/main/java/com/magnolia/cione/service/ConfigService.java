package com.magnolia.cione.service;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.ConfigCione;


@ImplementedBy(ConfigServiceImpl.class)
public interface ConfigService {

//	/**
//	 * Get configuration from node /cione-module/config
//	 * @return
//	 */
//	public ConfigAuthCione getConfig(); 
	
	
	/**
	 * Get configuration from config.properties
	 * @return
	 */
	public ConfigCione getConfig();
}
