package com.magnolia.cione.commands;

import java.util.Date;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.service.PeriodicPurchaseService;

import info.magnolia.commands.impl.BaseRepositoryCommand;
import info.magnolia.context.Context;
import info.magnolia.ecommerce.EcommerceConnectionProvider;
import info.magnolia.ecommerce.common.Connection;

public class ActivatePeriodicPurchase extends BaseRepositoryCommand {

	private static final Logger logger = LoggerFactory.getLogger(ActivatePeriodicPurchase.class);

	@Inject
	private EcommerceConnectionProvider ecommerceConnectionProvider;
	
	@Inject
	private PeriodicPurchaseService periodicPurchaseService;
	
	@Override
	public boolean execute(Context context) throws Exception {		
		
		String startlogmsg = String.format("START: Ejecutando el comando [ActivatePeriodicPurchase] > %s", new Date());
		logger.info(startlogmsg);

		periodicPurchaseService.setActivePeriodicPurchase();
		
		String endlogmsg = String.format("END: Ejecucion del comando [ActivatePeriodicPurchase] > %s", new Date());
		logger.info(endlogmsg);
		
		return true;
	}

    private Optional<Connection> getConnection(String definitionName, String connectionName) {
        return ecommerceConnectionProvider.getConnection(definitionName, connectionName);
    }
	

}
