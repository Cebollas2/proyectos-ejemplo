package com.magnolia.cione;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.client.ProjectApiRoot;
import com.magnolia.cione.setup.CioneEcommerceConectionProvider;

import info.magnolia.module.ModuleLifecycle;
import info.magnolia.module.ModuleLifecycleContext;

/**
 * This class is optional and represents the configuration for the cione-module module.
 * By exposing simple getter/setter/adder methods, this bean can be configured via content2bean
 * using the properties and node from <tt>config:/modules/cione-module</tt>.
 * If you don't need this, simply remove the reference to this class in the module descriptor xml.
 */
public class CioneModule implements ModuleLifecycle {
	
	@Inject
	private CioneEcommerceConectionProvider cioneEcommerceConectionProvider;
	
	private static final Logger log = LoggerFactory.getLogger(CioneModule.class);

    @Override
    public void start(ModuleLifecycleContext moduleLifecycleContext) {

    	if (cioneEcommerceConectionProvider!= null) {
    		ProjectApiRoot apiRoot = cioneEcommerceConectionProvider.getApiRoot();
    		String flag = cioneEcommerceConectionProvider.getFlag();
    		log.info(flag);
    		cioneEcommerceConectionProvider.setFlag("CioneModule");
    	} 
    	log.info("####### START");
    }

	@Override
	public void stop(ModuleLifecycleContext moduleLifecycleContext) {
		ProjectApiRoot apiRoot = cioneEcommerceConectionProvider.getApiRoot();
		apiRoot.close();
		log.info("####### STOP");
	}
}
