package com.genee.web.framework.core.base.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.ServletContextAware;

import com.genee.web.framework.core.base.PageSupport;

public abstract class BaseController implements ServletContextAware {
	protected List<Map<String, Object>> errResults = new ArrayList<Map<String, Object>>();
	protected final Logger logger = Logger.getLogger("genee");
	protected ServletContext servletContext;
	protected static ObjectMapper mapper = new ObjectMapper();

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void jacksonWriteValue(HttpServletResponse response, Object obj)
			throws JsonGenerationException, JsonMappingException, IOException {
		mapper.writeValue(response.getWriter(), obj);
	}

	public void jacksonReadValue(String jsonContent, Class<?> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		mapper.readValue(jsonContent, clazz);
	}

	public void outJson(HttpServletResponse response, Object obj,
			JsonConfig jsonConfig) {
		if (jsonConfig == null) {
			if (obj instanceof java.util.List)
				outJsonString(response, JSONArray.fromObject(obj).toString());
			else
				outJsonString(response, JSONObject.fromObject(obj).toString());
		} else {
			outJsonString(response, JSONObject.fromObject(obj, jsonConfig)
					.toString());
		}

	}

	public void outJsonArray(HttpServletResponse response, Object array,
			JsonConfig jsonConfig) {
		if (jsonConfig == null) {
			outJsonString(response, JSONArray.fromObject(array).toString());
		} else {
			outJsonString(response, JSONArray.fromObject(array, jsonConfig)
					.toString());
		}
	}

	public void outJsonString(HttpServletResponse response, String json) {
		response.setContentType("text/html;charset=utf-8");
		outString(response, json);
	}

	public void outString(HttpServletResponse response, String json) {
		try {
			PrintWriter out = response.getWriter();
			out.write(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void outXMLString(HttpServletResponse response, String xmlStr) {
		response.setContentType("application/xml;charset=UTF-8");
		outString(response, xmlStr);
	}

	public PageSupport getPageSupport(HttpServletRequest request) {
		PageSupport pageSupport = new PageSupport();
		Map<String, String[]> param = request.getParameterMap();
		for (Entry<String, String[]> entry : param.entrySet()) {
			pageSupport.getQueryBuilder().put(entry.getKey(),
					entry.getValue()[0]);
		}

		if (!StringUtils.isEmpty(request.getParameter("page")))
			pageSupport.setPage(Integer.parseInt(request.getParameter("page")));
		if (!StringUtils.isEmpty(request.getParameter("size"))) {
			pageSupport.setPageSize(Integer.parseInt(request
					.getParameter("size")));
		}
		return pageSupport;
	}

	// 该处理器类中的所有方法抛出的异常都可由此方法捕获并处理
	// 该注解也可制定多个异常类，如@ExceptionHandler(value={IOException.class,SQLException.class})
	@ExceptionHandler(Exception.class)
	public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
		writeError(e);
		Map<String, String> result = new HashMap<String, String>(2);
		result.put("result", e.getMessage());
		result.put("request-status", "failure");
		outJson(response, result, null);
	}
	
	private void writeError(Throwable e) {
		StackTraceElement[] exception = e.getStackTrace();
		StringBuilder sb = new StringBuilder();
		sb.append("\t" + e.getClass().getName() + ":" + e.getMessage());
		StackTraceElement ste = null;
		for (int i = 0; i < exception.length; i++) {
			ste = exception[i];
			sb.append("\n\t    at " + ste.getClassName() + "."
					+ ste.getMethodName() + "(" + ste.getFileName() + ":"
					+ ste.getLineNumber() + ")");
		}
		logger.error(sb.toString());
		moreException(e.getCause());
	}

	private void moreException(Throwable e) {
		if (e != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("\tCaused by: " + e.getClass().getName() + ":"
					+ e.getMessage() + "");
			StackTraceElement[] exception = e.getStackTrace();
			StackTraceElement ste = null;
			for (int i = 0; i < exception.length; i++) {
				ste = exception[i];
				sb.append("\n\t    at " + ste.getClassName() + "."
						+ ste.getMethodName() + "(" + ste.getFileName() + ":"
						+ ste.getLineNumber() + ")");
			}
			logger.error(sb.toString());
			moreException(e.getCause());
		}
	}
}
