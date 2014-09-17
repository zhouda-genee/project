package com.genee.service.module.jsonrpc2.pojo;

import java.util.List;

public class XMLServiceEntity {
	
	private String name;
	
	private String url;
	
	private String methodType;
	
	private String contentType;
	
	private String returnType;
	
	private List<XMLParamEntity> params;
	
	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public List<XMLParamEntity> getParams() {
		return params;
	}

	public void setParams(List<XMLParamEntity> params) {
		this.params = params;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
