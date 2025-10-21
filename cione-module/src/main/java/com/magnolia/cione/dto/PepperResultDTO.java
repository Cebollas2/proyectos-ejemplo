package com.magnolia.cione.dto;

import java.util.List;

public class PepperResultDTO {

	private String http_code;
	private List<PepperErrorDTO> errors;
	private String status;
	private String info;

	public String getHttp_code() {
		return http_code;
	}

	public void setHttp_code(String http_code) {
		this.http_code = http_code;
	}

	public List<PepperErrorDTO> getErrors() {
		return errors;
	}

	public void setErrors(List<PepperErrorDTO> errors) {
		this.errors = errors;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
