package com.mars.spring.jpa.h2.dto;

import java.util.List;

public class ResponseDTO<T> {


	/**
	 * 
	 */
	private String statusCode;

	private String statusMessage;

	private List<T> result;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}


}
