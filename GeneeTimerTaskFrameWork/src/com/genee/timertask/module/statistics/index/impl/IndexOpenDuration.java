package com.genee.timertask.module.statistics.index.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.genee.timertask.module.statistics.index.IndexBase;
import com.genee.timertask.module.statistics.pojo.EquipmentIndexEntity;

/**
 * 
 * @ClassName: IndexOpenDuration
 * @Description: 开放机时指标	统计时段内，开放机时=该仪器的使用总机时 — 仪器负责人使用总机时
 * @author da.zhou@geneegroup.com
 * @date 2014年8月21日 上午11:14:45
 *
 */
@Component("open_dur")
public class IndexOpenDuration extends IndexBase {

	@Override
	public void run(long startDate, long endDate, Map<String, EquipmentIndexEntity> equipments) {
		
		// 一台仪器的使用总机时和仪器负责人的使用总机时
		// key:仪器ID long[0]:仪器使用总机时 long[1]:仪器负责人使用总机时
		Map<String, long[]> equipmentMap = new HashMap<String, long[]>();
		
		// 循环仪器记录，统计出每台仪器的使用总机时和仪器负责人使用总机时
		for(Iterator<EquipmentIndexEntity> iter = equipments.values().iterator(); iter.hasNext();) {
			EquipmentIndexEntity equipment = iter.next();
			if (equipmentMap.containsKey(String.valueOf(equipment.getEquipmentId()))){
				long[] times = equipmentMap.get(String.valueOf(equipment.getEquipmentId()));
				times[0] = times[0] + equipment.getUsedDur();
				times[1] = times[1] + equipment.getOwnerUsedDur();
			} else {
				long[] times = new long[]{equipment.getUsedDur(), equipment.getOwnerUsedDur()};
				equipmentMap.put(String.valueOf(equipment.getEquipmentId()), times);
			}
		}
		
		// 循环每台仪器的使用机时和仪器负责人使用机时，判断参数equipments是否包含该仪器记录
		// 1､如果包含，则更新该仪器记录
		// 2､如果不包含，则添加该仪器的记录
		for (Iterator<Entry<String, long[]>> iter = equipmentMap.entrySet().iterator(); iter.hasNext();) {
			Entry<String, long[]> entry = iter.next();
			String key = entry.getKey();
			long[] times = entry.getValue();
			if (equipments.containsKey(key)){
				EquipmentIndexEntity equipment = equipments.get(key);
				equipment.setOpenDur(times[0] - times[1]);
			} else {
				EquipmentIndexEntity equipment = new EquipmentIndexEntity(getId(), Long.parseLong(key), startDate);
				equipment.setOpenDur(times[0] - times[1]);
				equipments.put(key, equipment);
			}
		}
		
	}

}
