package com.magnolia.cione.pur;

import com.google.inject.ImplementedBy;

import org.json.JSONObject;

@ImplementedBy(ResponseServiceImpl.class)
public interface ResponseService {
	
	public String generateOkResponse();
	
	public String generateOkResponseWithNodePath(String path);
	
	public String generateKoResponse();
	
	public String generateOkResponseWithJson(JSONObject json);
	
	public String generateKoResponseWithJson(JSONObject json);
	
	public String generateOkResponseWithMessage(String message);
	
	public String generateKoResponseWithMessage(String message);
	
	public String generateResponseWithCodeAndMessage(String code, String message);

}
