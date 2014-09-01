package com.genee.timertask.module.statistics.index.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.genee.timertask.framework.core.base.JdbcTemplateParam;
import com.genee.timertask.module.pojo.EquipmentIndexEntity;
import com.genee.timertask.module.statistics.index.IndexBase;

/**
 * 
 * @ClassName: IndexTestDuration
 * @Description: 委托测试机时<br> 统计时段内，该仪器所有使用记录中关联送样的使用记录使用时长总和
 * @author da.zhou@geneegroup.com
 * @date 2014年8月22日 上午11:45:33
 *
 */
@Component("test_dur")
public class IndexTestDuration extends IndexBase {

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
				equipmentIndexEntity.setTestDur(equipmentIndexEntity.getTestDur() + lSecond);
			} else {
				EquipmentIndexEntity equipmentIndexEntity = new EquipmentIndexEntity(getId(), iEquipmentId, sUserId, startDate);
				equipmentIndexEntity.setTestDur(lSecond);
				equipments.put(key, equipmentIndexEntity);
			}
		}

	}
	
	private List<Map<String, Object>> queryResult(long startDate, long endDate){
		String sql = "select distinct a.dtstart as start, a.dtend as end, a.user_id as userid, a.equipment_id as equipmentid "
				+ "from eq_record a, eq_sample b "
				+ "where (a.dtstart between ? and ? "
				+ "or a.dtend between ? and ? "
				+ "or ? between a.dtstart and a.dtend "
				+ "or ? between a.dtstart and a.dtend) "
				+ "and a.id = b.record_id";
		JdbcTemplateParam jdbcTemplateParam = new JdbcTemplateParam(sql,
				new Object[] { startDate, endDate, startDate, endDate, startDate, endDate }, 
				new int[] { java.sql.Types.INTEGER, java.sql.Types.INTEGER,
						java.sql.Types.INTEGER, java.sql.Types.INTEGER,
						java.sql.Types.INTEGER, java.sql.Types.INTEGER });
		return baseDao.queryForList(jdbcTemplateParam);
	}

}
