package com.genee.servlet.framework.core.jsonrpc2.pojo;

public class ErrorMessage {
	
	private String code;
	
	private String message;
	
	public ErrorMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public ErrorMessage(String message){
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
