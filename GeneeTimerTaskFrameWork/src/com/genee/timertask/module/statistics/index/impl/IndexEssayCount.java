package com.genee.timertask.module.statistics.index.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.genee.timertask.framework.core.base.JdbcTemplateParam;
import com.genee.timertask.module.statistics.index.IndexBase;
import com.genee.timertask.module.statistics.pojo.EquipmentIndexEntity;

@Component("essay_cnt")
public class IndexEssayCount extends IndexBase {

	@Override
	public void run(long startDate, long endDate, Map<String, EquipmentIndexEntity> equipments) {
		
		long iEquipmentId;
		String key;
		
		List<Map<String, Object>> results = queryResult(startDate, endDate);
		for (Map<String, Object> result : results) {
			iEquipmentId = (long)result.get("equipmentid");
			key = String.valueOf(iEquipmentId);
			
			if (equipments.containsKey(key)){
				EquipmentIndexEntity equipmentIndexEntity = equipments.get(key);
				equipmentIndexEntity.setEssayCnt(equipmentIndexEntity.getEssayCnt() + 1);
			} else {
				EquipmentIndexEntity equipmentIndexEntity = new EquipmentIndexEntity(getId(), iEquipmentId, startDate);
				equipmentIndexEntity.setEssayCnt(1);
				equipments.put(key, equipmentIndexEntity);
			}
		}

	}
	
	private List<Map<String, Object>> queryResult(long startDate, long endDate){
		String sql = "select b.id2 as equipmentid "
				+ "from publication a, _r_publication_equipment b "
				+ "where a.id = b.id1 "
				+ "and date between ? and ?";
		JdbcTemplateParam jdbcTemplateParam = new JdbcTemplateParam(sql,
				new Object[] { startDate, endDate }, 
				new int[] { java.sql.Types.INTEGER, java.sql.Types.INTEGER });
		return baseDao.queryForList(jdbcTemplateParam);
	}

}
