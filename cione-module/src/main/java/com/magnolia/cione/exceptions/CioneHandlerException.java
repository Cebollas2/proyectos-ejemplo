package com.magnolia.cione.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CioneHandlerException  implements ExceptionMapper<CioneException> {

	@Override
	public Response toResponse(CioneException exception) {
		return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build(); 
	}

}
