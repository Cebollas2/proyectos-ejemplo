package com.magnolia.cione;

import info.magnolia.module.ModuleLifecycle;
import info.magnolia.module.ModuleLifecycleContext;

/**
 * This class is optional and represents the configuration for the cione-module module.
 * By exposing simple getter/setter/adder methods, this bean can be configured via content2bean
 * using the properties and node from <tt>config:/modules/cione-module</tt>.
 * If you don't need this, simply remove the reference to this class in the module descriptor xml.
 */
public class CioneAppModule implements ModuleLifecycle {

    /* you can optionally implement info.magnolia.module.ModuleLifecycle */
    /* (non-Javadoc)
     * @see info.magnolia.module.ModuleLifecycle#start(info.magnolia.module.ModuleLifecycleContext)
     */
    @Override
    public void start(ModuleLifecycleContext moduleLifecycleContext) {
//    	RestHighLevelClient client = new RestHighLevelClient(
//    	        RestClient.builder(
//    	                new HttpHost("localhost", 9200, "http"),
//    	                new HttpHost("localhost", 9201, "http")));
//    											//index, type, id
    	//GetRequest getRequest = new GetRequest("mariasearch","type1","1");


    }

	@Override
	public void stop(ModuleLifecycleContext moduleLifecycleContext) {
		// TODO Auto-generated method stub
		
	}

}
