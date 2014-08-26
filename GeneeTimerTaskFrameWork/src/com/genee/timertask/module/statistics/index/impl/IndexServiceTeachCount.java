package com.genee.timertask.module.statistics.index.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.genee.timertask.framework.core.base.JdbcTemplateParam;
import com.genee.timertask.module.statistics.index.IndexBase;
import com.genee.timertask.module.statistics.pojo.EquipmentIndexEntity;

/**
 * 
 * @ClassName ServTeachCnt
 * @Description 	服务教育项目数
 * 							服务项目数=预约（无使用记录）关联项目+所有使用关联项目数 + 送样关联项目数
 * @author hujinzhe
 * @date 2014年8月26日 下午15:35
 *
 */
@Component("serv_teach_cnt")
public class IndexServiceTeachCount extends IndexBase {

	@Override
	public void run(long startDate, long endDate,
			Map<String, EquipmentIndexEntity> equipments) {

		long iEquipmentId; // 仪器ID
		String sUserId; // 用户ID
		int count; // 服务项目数
		String key; // 日表记录实体键

		List<Map<String, Object>> results = queryResult(startDate, endDate);
		for (Map<String, Object> result : results) {
			iEquipmentId = (long) result.get("equipmentid");
			sUserId = String.valueOf(result.get("userid"));
			count = (int) result.get("procount");
			key = String.valueOf(iEquipmentId) + "#" + sUserId;

			if (equipments.containsKey(key)) {
				EquipmentIndexEntity equipmentIndexEntity = equipments.get(key);
				equipmentIndexEntity.setServTeachCnt(equipmentIndexEntity
						.getServTeachCnt() + count);
			} else {
				EquipmentIndexEntity equipmentIndexEntity = new EquipmentIndexEntity(
						getId(), iEquipmentId, sUserId, startDate);
				equipmentIndexEntity.setServTeachCnt(count);
				equipments.put(key, equipmentIndexEntity);
			}
		}

	}

	private List<Map<String, Object>> queryResult(long startDate, long endDate) {
		String sql = "select t.userid, t.equipmentid, sum(t.procount) as procount "
				+ "from ((select res.user_id as userid, res.equipment_id as equipmentid, count(1) as procount from eq_reserv res "
				+ "left join eq_record rec on res.id = rec.reserv_id "
				+ "inner join lab_project pro on res.project_id = pro.id "
				+ "where rec.reserv_id is null and pro.type = 0 and res.dtstart between ? and ? "
				+ "group by res.user_id, res.equipment_id) "
				+ "union "
				+ "(select rec.user_id as userid, rec.equipment_id as equipmentid, count(1) as procount from eq_record rec "
				+ "inner join lab_project pro on rec.project_id = pro.id "
				+ "where pro.type = 0 and rec.dtstart between ? and ? "
				+ "group by rec.user_id, rec.equipment_id) "
				+ "union "
				+ "(select sam.sender_id as userid, sam.equipment_id as equipmentid, count(1) as procount from eq_sample sam "
				+ "inner join lab_project pro on sam.project_id = pro.id "
				+ "where pro.type = 0 and sam.dtstart between ? and ? "
				+ "group by sam.sender_id, sam.equipment_id)) t "
				+ "group by t.userid, t.equipmentid";
		JdbcTemplateParam jdbcTemplateParam = new JdbcTemplateParam(sql,
				new Object[] { startDate, endDate, startDate, endDate, startDate, endDate }, new int[] {
						java.sql.Types.INTEGER, java.sql.Types.INTEGER, java.sql.Types.INTEGER,
						java.sql.Types.INTEGER, java.sql.Types.INTEGER, java.sql.Types.INTEGER});
		return baseDao.queryForList(jdbcTemplateParam);
	}

}
