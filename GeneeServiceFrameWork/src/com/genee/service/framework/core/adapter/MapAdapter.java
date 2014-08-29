package com.genee.service.framework.core.adapter;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.genee.service.module.constants.MapEntity;

public class MapAdapter extends XmlAdapter<MapEntity[], Map<String, String>> {

	@Override
	public Map<String, String> unmarshal(MapEntity[] map) throws Exception {
		Map<String, String> result = new HashMap<String, String>();
		if (map != null)
			for (MapEntity e : map) {
				result.put(e.key, e.value);
			}
		return result;
	}

	@Override
	public MapEntity[] marshal(Map<String, String> map) throws Exception {
		MapEntity[] convertor = null;
		if (map != null) {
			convertor = new MapEntity[map.size()];
			int index = 0;
			for (Map.Entry<String, String> entry : map.entrySet()) {
				MapEntity item = new MapEntity();
				item.key = entry.getKey();
				item.value = entry.getValue();
				convertor[index++] = item;
			}
		}
		return convertor;
	}

}
