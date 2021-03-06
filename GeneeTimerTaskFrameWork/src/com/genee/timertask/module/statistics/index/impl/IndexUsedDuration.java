package com.genee.timertask.module.statistics.index.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.genee.timertask.framework.core.base.JdbcTemplateParam;
import com.genee.timertask.module.pojo.EquipmentIndexEntity;
import com.genee.timertask.module.statistics.index.IndexBase;

/**
 * 
 * @ClassName: IndexUsedDuration
 * @Description: 使用机时指标
 * @author da.zhou@geneegroup.com
 * @date 2014年8月18日 下午4:22:28
 *
 */
@Component("used_dur")
public class IndexUsedDuration extends IndexBase {

	@Override
	public void run(long startDate, long endDate, Map<String, EquipmentIndexEntity> equipments) {
		
		int iResultStartDate, iResultEndDate;
		long iEquipmentId;
		String sUserId;
		long lSecond; // 秒
		String key;
		
		List<Map<String, Object>> results = queryResult(startDate, endDate);
		for (Map<String, Object> result : results) {
			iResultStartDate = (int)result.get("start");
			iResultEndDate = (int)result.get("end");
			sUserId = String.valueOf(result.get("userid"));
			iEquipmentId = (long)result.get("equipmentid");
			key = String.valueOf(iEquipmentId) + "#" + sUserId;
			
			lSecond = timestampCompareSecond(iResultStartDate, iResultEndDate, startDate, endDate);
			
			if (equipments.containsKey(key)){
				EquipmentIndexEntity equipmentIndexEntity = equipments.get(key);
				equipmentIndexEntity.setUsedDur(equipmentIndexEntity.getUsedDur() + lSecond);
			} else {
				EquipmentIndexEntity equipmentIndexEntity = new EquipmentIndexEntity(getId(), iEquipmentId, sUserId, startDate);
				equipmentIndexEntity.setUsedDur(lSecond);
				equipments.put(key, equipmentIndexEntity);
			}
		}

	}
	
	private List<Map<String, Object>> queryResult(long startDate, long endDate){
		String sql = "select a.dtstart as start, a.dtend as end, a.user_id as userid, a.equipment_id as equipmentid "
				+ "from eq_record a "
				+ "where a.dtstart between ? and ? "
				+ "or a.dtend between ? and ? "
				+ "or ? between a.dtstart and a.dtend "
				+ "or ? between a.dtstart and a.dtend "
				+ "or (a.dtend = 0 and a.dtstart < ?)";
		JdbcTemplateParam jdbcTemplateParam = new JdbcTemplateParam(sql,
				new Object[] { startDate, endDate, startDate, endDate, startDate, endDate, startDate }, 
				new int[] { java.sql.Types.INTEGER, java.sql.Types.INTEGER,
						java.sql.Types.INTEGER, java.sql.Types.INTEGER,
						java.sql.Types.INTEGER, java.sql.Types.INTEGER,
						java.sql.Types.INTEGER});
		return baseDao.queryForList(jdbcTemplateParam);
	}

}
