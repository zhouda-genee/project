package com.genee.timertask.module.statistics.index.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.genee.timertask.framework.core.base.JdbcTemplateParam;
import com.genee.timertask.module.statistics.index.IndexBase;
import com.genee.timertask.module.statistics.pojo.EquipmentIndexEntity;

/**
 * 
 * @ClassName: IndexUsedTimes
 * @Description: 使用次数指标<br>	
 * 				 	 统计时段内，该仪器的使用总次数，如果仪器使用次数跨天，则算在第一天上
 * @author da.zhou@geneegroup.com
 * @date 2014年8月21日 下午3:58:00
 *
 */
@Component("used_times")
public class IndexUsedTimes extends IndexBase {

	@Override
	public void run(long startDate, long endDate, Map<String, EquipmentIndexEntity> equipments) {
		
		long iEquipmentId;
		String sUserId;
		String key;
		int times; // 使用次数
		
		List<Map<String, Object>> results = queryResult(startDate, endDate);
		for (Map<String, Object> result : results) {
			sUserId = String.valueOf(result.get("userid"));
			iEquipmentId = (long)result.get("equipmentid");
			key = String.valueOf(iEquipmentId) + "#" + sUserId;
			times = ((Long)result.get("times")).intValue();
			if (equipments.containsKey(key)){
				EquipmentIndexEntity equipmentIndex = equipments.get(key);
				equipmentIndex.setUsedTimes(equipmentIndex.getUsedTimes() + times);
			} else {
				EquipmentIndexEntity equipmentIndexEntity = new EquipmentIndexEntity(getId(), iEquipmentId, sUserId, startDate);
				equipmentIndexEntity.setUsedTimes(times);
				equipments.put(key, equipmentIndexEntity);
			}
		}

	}
	
	private List<Map<String, Object>> queryResult(long startDate, long endDate){
		String sql = "select equipment_id as equipmentid, user_id as userid, count(id) as times from eq_record where dtend between ? and ? group by equipment_id, user_id";
		JdbcTemplateParam jdbcTemplateParam = new JdbcTemplateParam(sql,
				new Object[] { startDate, endDate }, 
				new int[] { java.sql.Types.INTEGER, java.sql.Types.INTEGER });
		return baseDao.queryForList(jdbcTemplateParam);
	}

}
