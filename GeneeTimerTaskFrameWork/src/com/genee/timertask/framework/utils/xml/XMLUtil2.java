package com.genee.timertask.framework.utils.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XMLUtil2 {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Object> parseXmlToMap(String xml) throws DocumentException {
		Document doc = DocumentHelper.parseText(xml);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		Map resultMap = new HashMap();
		for (Element e : list) {
			String name = e.getName();
			String text = e.getTextTrim();
			if (e.elements().size() == 0) {
				resultMap.put(name, text);
			} else {
				if (!resultMap.containsKey(name)) {
					resultMap.put(name, parseXmlToMap(e.asXML()));
				} else {
					List<Map> tmplist = null;
					if (resultMap.get(name) instanceof java.util.Map) {
						tmplist = new ArrayList<Map>();
						tmplist.add((Map) resultMap.get(name));
					} else if (resultMap.get(name) instanceof java.util.List) {
						tmplist = (List<Map>) resultMap.get(name);
					}
					tmplist.add(parseXmlToMap(e.asXML()));
					resultMap.put(name, tmplist);
				}
			}
		}
		return resultMap;
	}
}
