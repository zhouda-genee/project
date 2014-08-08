package com.genee.web.framework.core.base.controller;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
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
import org.springframework.web.context.ServletContextAware;

import com.genee.web.framework.core.base.PageSupport;


public abstract class BaseController implements ServletContextAware {
	
	protected final Logger logger = Logger.getLogger(getClass());
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
			outJsonString(response, JSONObject.fromObject(obj, jsonConfig).toString());
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
			pageSupport.getQueryBuilder().put(entry.getKey(), entry.getValue()[0]);
		}
		
		if (!StringUtils.isEmpty(request.getParameter("page")))
			pageSupport.setPage(Integer.parseInt(request.getParameter("page")));
		if (!StringUtils.isEmpty(request.getParameter("size"))) {
			pageSupport.setPageSize(Integer.parseInt(request.getParameter("size")));
		}
		return pageSupport;
	}
	
	/** 
     * 将一个 Map 对象转化为一个 JavaBean 
     * @param type 要转化的类型 
     * @param map 包含属性值的 map 
     * @return 转化出来的 JavaBean 对象 
     * @throws IntrospectionException 如果分析类属性失败 
     * @throws IllegalAccessException 如果实例化 JavaBean 失败 
     * @throws InstantiationException 如果实例化 JavaBean 失败 
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败 
     */ 
	public Object MapToBean(Class<?> type, HttpServletRequest request)  
            throws IntrospectionException, IllegalAccessException,  
            InstantiationException, InvocationTargetException {  
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性   
        Object obj = type.newInstance(); // 创建 JavaBean 对象   
  
        Map<String, String[]> map = request.getParameterMap();
        // 给 JavaBean 对象的属性赋值   
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();  
        for (int i = 0; i< propertyDescriptors.length; i++) {  
            PropertyDescriptor descriptor = propertyDescriptors[i];  
            String propertyName = descriptor.getName().toLowerCase();  
            
            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
            	try {
					String value = request.getParameter(propertyName);
					if (descriptor.getPropertyType().getName().equals("java.lang.String")) {
						descriptor.getWriteMethod().invoke(obj, value);
					} else if (descriptor.getPropertyType().getName().equals("int")) {
						descriptor.getWriteMethod().invoke(obj, Integer.parseInt(value));
					} else if (descriptor.getPropertyType().getName().equals("double")) {
						descriptor.getWriteMethod().invoke(obj, Double.parseDouble(value));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
            }  
        }  
        return obj;  
    } 
    
   /** 
     * 将一个 JavaBean 对象转化为一个  Map 
     * @param bean 要转化的JavaBean 对象 
     * @return 转化出来的  Map 对象 
     * @throws IntrospectionException 如果分析类属性失败 
     * @throws IllegalAccessException 如果实例化 JavaBean 失败 
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败 
     */ 
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Map BeanToMap(Object bean) throws IntrospectionException,
			IllegalAccessException, InvocationTargetException {
		Class<?> type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}  

}
