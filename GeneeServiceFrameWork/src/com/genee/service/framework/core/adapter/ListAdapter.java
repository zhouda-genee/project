package com.genee.service.framework.core.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.genee.service.module.constants.ListEntity;
import com.genee.service.module.constants.MapEntity;

public class ListAdapter extends
		XmlAdapter<ListEntity, List<Map<String, Object>>> {

	@Override
	public List<Map<String, Object>> unmarshal(ListEntity listEntity)
			throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		if (listEntity != null) {
			Map<String, Object> hashMap = new HashMap<String, Object>();
			for (MapEntity mapEntity : listEntity.list) {
				hashMap.put(mapEntity.key, mapEntity.value);
			}

			result.add(hashMap);
		}

		return result;
	}

	@Override
	public ListEntity marshal(List<Map<String, Object>> list) throws Exception {
		ListEntity convertor = null;
		if (list != null) {
			convertor = new ListEntity();
			for (Map<String, Object> map : list) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					MapEntity mapEntity = new MapEntity();
					mapEntity.key = entry.getKey();
					mapEntity.value = entry.getValue().toString();
					convertor.list.add(mapEntity);
				}
			}
		}
		return convertor;
	}

}
