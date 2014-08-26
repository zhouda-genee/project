package com.genee.servlet.framework.core.jsonrpc2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;

import com.genee.servlet.framework.core.jsonrpc2.pojo.ErrorMessage;
import com.genee.servlet.framework.core.jsonrpc2.pojo.JSONRPC2Request;
import com.genee.servlet.framework.core.jsonrpc2.pojo.JSONRPC2Response;
import com.genee.servlet.framework.core.jsonrpc2.pojo.XMLParamEntity;
import com.genee.servlet.framework.core.jsonrpc2.pojo.XMLServiceEntity;
import com.genee.servlet.framework.utils.http.HttpClientUtil2;
import com.genee.servlet.framework.utils.json.JsonUtil;

public class JSONRPC2Session {
	
	public static JSONRPC2Response send(JSONRPC2Request request, XMLServiceEntity service) {
		
		// jsonrpc请求的参数
		Map<String, Object> params = request.getParams();
		
		JSONRPC2Response response = null;
		
		Map<String, String> header = new HashMap<String, String>();
		// 返回json格式数据
		header.put("Accept", MediaType.APPLICATION_JSON_VALUE);
		if (StringUtils.isNotEmpty(service.getContentType()))
			header.put("content-type", service.getContentType());
		
		String[] result = null;
		
		if (service.getMethodType().equals("get")){
			StringBuilder urlBuilder = new StringBuilder(service.getUrl());
			List<XMLParamEntity> xmlParams = service.getParams();
			if (xmlParams != null && xmlParams.size() > 0) {
				urlBuilder.append("?");
				for (XMLParamEntity xmlparam : xmlParams) {
					urlBuilder.append(xmlparam.getName()).append("=").append(String.valueOf(params.get(xmlparam.getName())));
				}
			}
			result = HttpClientUtil2.get(urlBuilder.toString(), header);
		} else if (service.getMethodType().equals("post")){
			List<XMLParamEntity> xmlParams = service.getParams();
			Map<String, String> reqParam = new HashMap<String, String>(xmlParams.size());
			if (xmlParams != null && xmlParams.size() > 0) {
				for (XMLParamEntity xmlparam : xmlParams) {
					reqParam.put(xmlparam.getName(), String.valueOf(params.get(xmlparam.getName())));
				}
			}
			result = HttpClientUtil2.post(service.getUrl(), reqParam, header);
		}
		
		if (result[0].equals("200")){
			if ("array".equals(service.getReturnType())){
				response = new JSONRPC2Response(request, JsonUtil.getObjectArray4Json(result[1]));
			} else if ("object".equals(service.getReturnType())) {
				response = new JSONRPC2Response(request, JsonUtil.getMap4Json(result[1]));
			} else if ("int".equals(service.getReturnType())) {
				response = new JSONRPC2Response(request, Integer.parseInt(result[1]));
			} else if ("string".equals(service.getReturnType())) {
				response = new JSONRPC2Response(request, result[1]);
			} else if ("double".equals(service.getReturnType())) {
				response = new JSONRPC2Response(request, Double.parseDouble(result[1]));
			} else if ("float".equals(service.getReturnType())) {
				response = new JSONRPC2Response(request, Float.parseFloat(result[1]));
			} else {
				response = new JSONRPC2Response(request, result[1]);
			}
		} else if (result[0].equals("404")) {
			response = new JSONRPC2Response(request, new ErrorMessage("没有找到该请求：" + service.getUrl()));
		} else {
			response = new JSONRPC2Response(request, new ErrorMessage(result[1]));
		}
		
		return response;
	}

	

}
