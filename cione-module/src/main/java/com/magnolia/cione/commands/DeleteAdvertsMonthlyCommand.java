package com.magnolia.cione.commands;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.pur.CommandsUtils;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.cms.util.QueryUtil;
import info.magnolia.commands.impl.BaseRepositoryCommand;
import info.magnolia.context.Context;

public class DeleteAdvertsMonthlyCommand extends BaseRepositoryCommand {

	private static final Logger log = LoggerFactory.getLogger(DeleteAdvertsMonthlyCommand.class);
	
	private static final String RADIO_BUSQUEDA_WS = "radiobusquedas";
	
	@Inject
	private CommandsUtils commandsUtils;
	
	@Override
	public boolean execute(Context context) throws Exception {		
		log.info(String.format("Ejecutando el comando [ejecutDeleteAdvertsMonthlyCommand] > %s", new Date()));	 
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00.000'Z'");		
		Date monthBefore = CioneUtils.addAndSubstractMonthToDate(new Date(), -1);	
		
		String query = "select * from [radiobusqueda] where";
		query += " [mgnl:lastActivated] IS NOT NULL";
		query += " and [mgnl:lastActivated] < CAST('" + sdf.format(monthBefore) + "' AS DATE)";
		//query += " and [mgnl:lastActivated] < CAST('2019-07-25T00:00:00.233Z' AS DATE)";
		
		NodeIterator resultQuery = QueryUtil.search(RADIO_BUSQUEDA_WS, query);
		while (resultQuery.hasNext()) {
			Node node = resultQuery.nextNode();
			log.info(String.format("Nodo a despublicar : %s" , toStringRadioBusquedaNode(node)));
			commandsUtils.unpublishNodeWithoutWorkflow(node.getPath(), RADIO_BUSQUEDA_WS);			
			log.info("Nodo despublicado");
		}
		
		return false;
	}
	
	private String toStringRadioBusquedaNode(Node node) throws PathNotFoundException, RepositoryException {
		String result = "";
		result += "[";
		result += "titulo: " + node.getProperty("titulo").getValue().getString();
		result += ", email: " + node.getProperty("email").getValue().getString();
		result += ", texto: " + node.getProperty("texto").getValue().getString();
		result += ", mgnl:lastActivated (date.getTime()): " + node.getProperty("mgnl:lastActivated").getDate().getTime();		
		result += "]";		
		return result;
	}

}
