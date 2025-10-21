package com.magnolia.cione.controller;

import java.io.File;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.magnolia.cione.setup.CioneEcommerceConectionProvider;

//import io.swagger.annotations.Api;

//@Api(value = "/doofinder")
@Path("/doofinder")
public class DooFinderEndPoint {
	
	@Inject
	private CioneEcommerceConectionProvider cioneEcommerceConectionProvider; 

	@GET
	@Path("/csv")
	@Produces("application/csv")
	//@Produces("test/csv")
	public Response getCSV(@QueryParam("file") String fileName) {
		try {
			if (fileName != null) {
				File file = new File(cioneEcommerceConectionProvider.getConfigService().getConfig().getDoofinderPath() + fileName);
				ResponseBuilder response = Response.ok((Object) file);
				response.header("Content-Disposition", "attachment; filename=\""+ file.getName() + "\"");
				return response.build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("File Not Found").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
}
