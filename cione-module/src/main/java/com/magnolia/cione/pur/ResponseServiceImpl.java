package com.magnolia.cione.pur;


import org.json.JSONObject;

public class ResponseServiceImpl implements ResponseService {
	
	private String responseMessageKey = "message";

	@Override
	public String generateOkResponse() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", "200");
		jsonObject.put(responseMessageKey, "Everything is ok!");
		return jsonObject.toString();
	}

	@Override
	public String generateKoResponse() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", "500");
		jsonObject.put(responseMessageKey, "Something is wrong!!");
		return jsonObject.toString();
	}

	@Override
	public String generateOkResponseWithNodePath(String path) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", "200");
		jsonObject.put(responseMessageKey, "Everything is ok!");
		jsonObject.put("nodepath", path);
		return jsonObject.toString();
	}

	@Override
	public String generateOkResponseWithJson(JSONObject json) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", "200");
		jsonObject.put("body", json);
		return jsonObject.toString();
	}

	@Override
	public String generateKoResponseWithJson(JSONObject json) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", "500");
		jsonObject.put("body", json);
		return jsonObject.toString();
	}

	@Override
	public String generateOkResponseWithMessage(String message) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", "200");
		jsonObject.put(responseMessageKey, message);
		return jsonObject.toString();
	}

	@Override
	public String generateKoResponseWithMessage(String message) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", "500");
		jsonObject.put(responseMessageKey, message);
		return jsonObject.toString();
	}

	@Override
	public String generateResponseWithCodeAndMessage(String code, String message) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put(responseMessageKey, message);
		return jsonObject.toString();
	}

}
