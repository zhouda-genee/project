package com.genee.servlet.framework.core.jsonrpc2;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.genee.servlet.framework.core.jsonrpc2.pojo.XMLParamEntity;
import com.genee.servlet.framework.core.jsonrpc2.pojo.XMLServiceEntity;

public class XMLServiceBuilder {
	
	private static final String CONFIG_PATH = "app_config/";
	
	private static final Logger logger = Logger.getLogger("genee");
	
	private XMLServiceBuilder(){}
	
	public static Map<String, XMLServiceEntity> buildService() {
		SAXReader saxReader = new SAXReader();
        Document document = null;
        InputStream in = null;
		try {
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_PATH + "services.xml");
			document = saxReader.read(in);
		} catch (DocumentException e1) {
			logger.error(e1.getMessage());
			return null;
		}

        // 获取根元素
        Element root = document.getRootElement();

        // 获取特定名称的子元素
        List<Element> serviceChilds = root.elements("service");
        Map<String, XMLServiceEntity> services = new HashMap<String, XMLServiceEntity>(serviceChilds.size());
        for (Element serviceChild : serviceChilds) {
        	// service
        	XMLServiceEntity service = new XMLServiceEntity();
        	service.setName(serviceChild.attributeValue("name"));
        	service.setContentType(serviceChild.attributeValue("content-type"));
        	service.setMethodType(serviceChild.attributeValue("methodType"));
        	service.setUrl(serviceChild.attributeValue("url"));
        	service.setReturnType(serviceChild.attributeValue("returnType"));
        	// param
        	Element paramsChild = serviceChild.element("params");
        	if (paramsChild != null){
	        	List<Element> paramChilds = paramsChild.elements("param");
	        	List<XMLParamEntity> params = new ArrayList<XMLParamEntity>(paramChilds.size());
	        	for (Element paramChild : paramChilds ) {
	        		XMLParamEntity param = new XMLParamEntity();
	        		param.setName(paramChild.attributeValue("name"));
	        		param.setType(paramChild.attributeValue("type"));
	        		params.add(param);
	        	}
	        	service.setParams(params);
        	}
        	services.put(service.getName(), service);
        }
        if (in != null){
        	try {
				in.close();
				in = null;
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
        }
        return services;
	}
	

}
