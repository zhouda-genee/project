package com.genee.service.framework.core.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ListMapAdapter extends XmlAdapter<Object, List<Map<String, Object>>> {

	@Override
	public List<Map<String, Object>> unmarshal(Object v) throws Exception {
		List<MapConvertor[]> convertors = (List<MapConvertor[]>) v;
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>(convertors.size());
		for (MapConvertor[] mapConvertor : convertors) {
			Map<String, Object> result = new HashMap<String, Object>();
			for (MapConvertor e : mapConvertor) {
				result.put(e.key, e.value);
			}
			results.add(result);
		}
		return results;
	}

	@Override
	public Object marshal(List<Map<String, Object>> v) throws Exception {
//		List<MapConvertor> results = new ArrayList<MapConvertor>(v.size());
		AdaptedHashMap adaptedHashMap = new AdaptedHashMap();
		for (Map<String, Object> map : v) {
			MapConvertor[] convertor = new MapConvertor[map.size()];
			int i = 0;
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				MapConvertor item = new MapConvertor();
	            item.key = entry.getKey();
	            item.value = entry.getValue();
	            convertor[i++] = item;   
			}
			adaptedHashMap.item.add(convertor);
		}
		return adaptedHashMap;
	}
	
	public static class AdaptedHashMap {
        public List<MapConvertor[]> item = new ArrayList<MapConvertor[]>();
    }

}
