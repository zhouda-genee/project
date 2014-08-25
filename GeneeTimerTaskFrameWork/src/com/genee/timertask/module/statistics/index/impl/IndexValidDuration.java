package com.genee.timertask.module.statistics.index.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.genee.timertask.framework.core.base.JdbcTemplateParam;
import com.genee.timertask.module.statistics.index.IndexBase;
import com.genee.timertask.module.statistics.pojo.EquipmentIndexEntity;

/**
 * 
 * @ClassName: IndexValidDuration
 * @Description: 有效机时指标<br>统计时段内，该仪器在设定的阈值范围内的使用机时总和
 * @author da.zhou@geneegroup.com
 * @date 2014年8月21日 下午4:51:54
 *
 */
@Component("valid_dur")
public class IndexValidDuration extends IndexBase {

	@Override
	public void run(long startDate, long endDate, Map<String, EquipmentIndexEntity> equipments) {
		int iResultStartDate, iResultEndDate;
		long iEquipmentId;
		long lSecond; // 秒
		String key;
		
		List<Map<String, Object>> results = queryResult(startDate, endDate);
		for (Map<String, Object> result : results) {
			iResultStartDate = (int)result.get("start");
			iResultEndDate = (int)result.get("end");
			iEquipmentId = (long)result.get("equipmentid");
			key = String.valueOf(iEquipmentId);
			
			lSecond = timestampCompareSecond(iResultStartDate, iResultEndDate, startDate, endDate);
			
			if (equipments.containsKey(key)){
				EquipmentIndexEntity equipmentIndexEntity = equipments.get(key);
				equipmentIndexEntity.setValidDur(equipmentIndexEntity.getValidDur() + lSecond);
			} else {
				EquipmentIndexEntity equipmentIndexEntity = new EquipmentIndexEntity(getId(), iEquipmentId, startDate);
				equipmentIndexEntity.setValidDur(lSecond);
				equipments.put(key, equipmentIndexEntity);
			}
		}

	}
	
	private List<Map<String, Object>> queryResult(long startDate, long endDate){
		String sql = "select b.dtstart as start, b.dtend as end, a.equipment_id as equipmentid "
				+ "from eq_meter a, eq_meter_record b "
				+ "where b.dtstart between ? and ? "
				+ "or b.dtend between ? and ? "
				+ "or ? between b.dtstart and b.dtend "
				+ "or ? between b.dtstart and b.dtend "
				+ "and a.id = b.eq_meter_id";
		JdbcTemplateParam jdbcTemplateParam = new JdbcTemplateParam(sql,
				new Object[] { startDate, endDate, startDate, endDate, startDate, endDate }, 
				new int[] { java.sql.Types.INTEGER, java.sql.Types.INTEGER,
						java.sql.Types.INTEGER, java.sql.Types.INTEGER,
						java.sql.Types.INTEGER, java.sql.Types.INTEGER });
		return baseDao.queryForList(jdbcTemplateParam);
	}

}
