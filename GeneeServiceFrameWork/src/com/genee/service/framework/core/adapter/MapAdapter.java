package com.genee.service.framework.core.adapter;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class MapAdapter extends XmlAdapter<MapConvertor[], Map<String, Object>> {

	@Override
	public Map<String, Object> unmarshal(MapConvertor[] map) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		if (map != null)
			for (MapConvertor e : map) {
				result.put(e.key, e.value);
			}
		return result;
	}

	@Override
	public MapConvertor[] marshal(Map<String, Object> map) throws Exception {
		MapConvertor[] convertor = null;
		if (map != null){
			convertor = new MapConvertor[map.size()];
			int index = 0;
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				MapConvertor item = new MapConvertor();
	            item.key = entry.getKey();
	            item.value = entry.getValue();
	            convertor[index++] = item;      
			}
		}
		return convertor;
	}

}
