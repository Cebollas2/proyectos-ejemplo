package com.magnolia.cione.pur;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

public class ResponseUtils {
	
	public ResponseBuilder generateResponseBuilder(String res) {

		ResponseBuilder responseBuilder = Response.ok();

		responseBuilder.type(MediaType.APPLICATION_JSON + "; charset=utf-8")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8"))
				.header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
				.header(HttpHeaders.EXPIRES, "0").entity(res);

		return responseBuilder;
	}

}
