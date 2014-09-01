package com.genee.timertask.module.statistics.index.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.genee.timertask.framework.core.base.JdbcTemplateParam;
import com.genee.timertask.module.pojo.EquipmentIndexEntity;
import com.genee.timertask.module.statistics.index.IndexBase;

/**
 * 
 * @ClassName: IndexValidDuration
 * @Description: 有效机时指标<br>统计时段内，该仪器在设定的阈值范围内的使用机时总和<br>
 * 				有效机时分为两种，第一种是有硬件的（即在仪器信息界面下，有一个页卡叫做“Gmeter"）按照文件中描述计算，
 * 				另一种是无硬件的，有效机时=非机主的使用记录的使用机时总和+机主的使用记录中关联送样的使用记录的总和
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
		
		// 有meter表，则认为有Gmeter功能
		boolean existMeter = existMeter();
		if (existMeter) {
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
		} else {
			// 有效机时=非机主的使用记录的使用机时总和(开放机时指标)+机主的使用记录中关联送样的使用记录的总和
			List<Map<String, Object>> results = queryResult2(startDate, endDate);
			for (Map<String, Object> result : results) {
				iResultStartDate = (int)result.get("start");
				iResultEndDate = (int)result.get("end");
				iEquipmentId = (long)result.get("equipmentid");
				key = String.valueOf(iEquipmentId);
				
				lSecond = timestampCompareSecond(iResultStartDate, iResultEndDate, startDate, endDate);
				
				if (equipments.containsKey(key)){
					EquipmentIndexEntity equipmentIndexEntity = equipments.get(key);
					equipmentIndexEntity.setValidDur(equipmentIndexEntity.getValidDur() + equipmentIndexEntity.getOpenDur() + lSecond);
				} else {
					EquipmentIndexEntity equipmentIndexEntity = new EquipmentIndexEntity(getId(), iEquipmentId, startDate);
					equipmentIndexEntity.setValidDur(equipmentIndexEntity.getOpenDur() + lSecond);
					equipments.put(key, equipmentIndexEntity);
				}
			}
		}
	}
	
	/**
	 * 
	 * @Title: queryResult2 
	 * @Description: 机主的使用记录中关联送样的使用记录 
	 * @param @param startDate
	 * @param @param endDate
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	private List<Map<String, Object>> queryResult2(long startDate, long endDate){
		String sql = "select distinct a.dtstart as start, a.dtend as end, a.equipment_id as equipmentid "
				+ "from eq_record a, eq_sample b, _r_user_equipment c "
				+ "where (a.dtstart between ? and ? "
				+ "or a.dtend between ? and ? "
				+ "or ? between a.dtstart and a.dtend "
				+ "or ? between a.dtstart and a.dtend) "
				+ "and a.id = b.record_id "
				+ "and c.id1 = a.user_id "
				+ "and c.id2 = a.equipment_id "
				+ "and c.type = 'incharge'";
		JdbcTemplateParam jdbcTemplateParam = new JdbcTemplateParam(sql,
				new Object[] { startDate, endDate, startDate, endDate, startDate, endDate }, 
				new int[] { java.sql.Types.INTEGER, java.sql.Types.INTEGER,
						java.sql.Types.INTEGER, java.sql.Types.INTEGER,
						java.sql.Types.INTEGER, java.sql.Types.INTEGER });
		return baseDao.queryForList(jdbcTemplateParam);
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
	
	/**
	 * 
	 * @Title: existMeter 
	 * @Description: 判断是否有meter表 
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	private boolean existMeter(){
		String sql = "show tables like 'eq_meter'";
		JdbcTemplateParam param = new JdbcTemplateParam(sql);
		List<Map<String, Object>> result = baseDao.queryForList(param);
		if (result.size() > 0)
			return true;
		else 
			return false;
	}

}
