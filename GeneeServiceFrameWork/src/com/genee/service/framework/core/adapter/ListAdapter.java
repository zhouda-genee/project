package com.genee.service.framework.core.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.genee.service.module.constants.ListEntity;
import com.genee.service.module.constants.MapEntity;

public class ListAdapter extends
		XmlAdapter<ListEntity[], List<HashMap<String, Object>>> {

	@Override
	public List<HashMap<String, Object>> unmarshal(ListEntity[] list)
			throws Exception {
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();

		if (list != null) {
			for (ListEntity e : list) {
				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				for (MapEntity mapEntity : e.list) {
					hashMap.put(mapEntity.key, mapEntity.value);
				}

				result.add(hashMap);
			}
		}

		return result;
	}

	@Override
	public ListEntity[] marshal(List<HashMap<String, Object>> list)
			throws Exception {
		ListEntity[] convertor = null;
		if (list != null) {
			convertor = new ListEntity[list.size()];
			int index = 0;
			for (HashMap<String, Object> map : list) {
				ListEntity listEntity = new ListEntity();

				for (Map.Entry<String, Object> entry : map.entrySet()) {
					MapEntity mapEntity = new MapEntity();
					mapEntity.key = entry.getKey();
					mapEntity.value = entry.getValue().toString();
					listEntity.list.add(mapEntity);
				}
				convertor[index++] = listEntity;
			}
		}
		return convertor;
	}

}
