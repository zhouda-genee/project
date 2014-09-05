package com.genee.timertask.module.statistics.index.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.genee.timertask.framework.core.base.JdbcTemplateParam;
import com.genee.timertask.module.pojo.EquipmentIndexEntity;
import com.genee.timertask.module.statistics.index.IndexBase;

/**
 * 
 * @ClassName: IndexTrainingStudent
 * @Description: 培训教师指标<br>统计时段内，已通过培训+团体中通过培训教师人数的总和
 * 				张枚说：一期只做南大定制的
 * 				南大定制：统计时段内，已通过培训通过培训角色为教师人数的总和
 * @author da.zhou@geneegroup.com
 * @date 2014年8月22日 上午11:46:32
 *
 */
@Component("train_tea")
public class IndexTrainingTeacher extends IndexBase {

	@Override
	public void run(long startDate, long endDate, Map<String, EquipmentIndexEntity> equipments) {
		
		long equipmentId; // 仪器ID
		int num; // 统计人数
		String key;
		
		// 统计用户
		List<Map<String, Object>> userTrainings = queryResult(startDate, endDate);
		for (Map<String, Object> userTraining : userTrainings) {
			equipmentId = (long)userTraining.get("equipmentid");
			num = ((Long)userTraining.get("num")).intValue();
			key = String.valueOf(equipmentId);
			if (equipments.containsKey(key)){
				EquipmentIndexEntity equipment = equipments.get(key);
				equipment.setTrainTea(equipment.getTrainTea() + num);
			} else {
				EquipmentIndexEntity equipment = new EquipmentIndexEntity(getId(), equipmentId, startDate);
				equipment.setTrainTea(num);
				equipments.put(key, equipment);
			}
		}
		
		// 统计团体培训
		/*List<Map<String, Object>> groupTrainings = queryResult2(startDate, endDate);
		for (Map<String, Object> groupTraining : groupTrainings) {
			equipmentId = (long)groupTraining.get("equipmentid");
			num = (int)groupTraining.get("num");
			key = String.valueOf(equipmentId);
			if (equipments.containsKey(String.valueOf(equipmentId))){
				EquipmentIndexEntity equipment = equipments.get(String.valueOf(equipmentId));
				equipment.setTrainTea(equipment.getTrainTea() + num);
			} else {
				EquipmentIndexEntity equipment = new EquipmentIndexEntity(getId(), equipmentId, startDate);
				equipment.setTrainTea(num);
				equipments.put(key, equipment);
			}
		}*/
	}
	
	/**
	 * 
	 * @Title: queryResult 
	 * @Description: 已通过培训用户 
	 * @param startDate
	 * @param endDate
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	private List<Map<String, Object>> queryResult(long startDate, long endDate){
		String sql = "select count(a.id) as num, a.equipment_id as equipmentid "
				+ "from ue_training a, user b "
				+ "where a.mtime between ? and ? "
				+ "and a.status = 2 " // status=2代表通过培训
				+ "and a.user_id = b.id "
				+ "and b.member_type in (10,11,12,13) " //教师：10 课题负责人(PI) 11 科研助理 12 PI助理/实验室管理员 13 其他
				+ "group by a.equipment_id"; 
		JdbcTemplateParam jdbcTemplateParam = new JdbcTemplateParam(sql,
				new Object[] { startDate, endDate }, 
				new int[] { java.sql.Types.INTEGER, java.sql.Types.INTEGER });
		return baseDao.queryForList(jdbcTemplateParam);
	} 
	
	/**
	 * 
	 * @Title: queryResult2 
	 * @Description: 团队中通过培训的人数 
	 * @return List<Map<String,Object>>
	 * @throws
	 
	private List<Map<String, Object>> queryResult2(long startDate, long endDate){
		String sql = "select sum(napproved) as num, equipment_id as equipmentid "
				+ "from ge_training "
				+ "where date between ? and ? "
				+ "group by equipment_id";
		JdbcTemplateParam jdbcTemplateParam = new JdbcTemplateParam(sql,
				new Object[] { startDate, endDate }, 
				new int[] { java.sql.Types.INTEGER, java.sql.Types.INTEGER });
		return baseDao.queryForList(jdbcTemplateParam);
	}*/

}
