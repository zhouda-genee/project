package com.genee.timertask.module.statistics.index.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.genee.timertask.framework.core.base.JdbcTemplateParam;
import com.genee.timertask.module.pojo.EquipmentIndexEntity;
import com.genee.timertask.module.statistics.index.IndexBase;

/**
 * 
 * @ClassName OwnerSamCnt
 * @Description 	机主测样数
 * 							统计时段内，该仪器所有使用记录中使用者为机主的样品数总和
 * @author hujinzhe
 * @date 2014年8月21日 下午17:08
 *
 */
@Component("owner_sam_cnt")
public class IndexOwnerSampleCount extends IndexBase {

	@Override
	public void run(long startDate, long endDate,
			Map<String, EquipmentIndexEntity> equipments) {

		long iEquipmentId; // 仪器ID
		String sUserId; // 用户ID
		int samples; // 测样数
		String key; // 日表记录实体键

		List<Map<String, Object>> results = queryResult(startDate, endDate);
		for (Map<String, Object> result : results) {
			iEquipmentId = (long) result.get("equipmentid");
			sUserId = String.valueOf(result.get("userid"));
			samples = (int) result.get("samples");
			key = String.valueOf(iEquipmentId) + "#" + sUserId;

			if (equipments.containsKey(key)) {
				EquipmentIndexEntity equipmentIndexEntity = equipments.get(key);
				equipmentIndexEntity.setOwnerSamCnt(equipmentIndexEntity
						.getOwnerSamCnt() + samples);
			} else {
				EquipmentIndexEntity equipmentIndexEntity = new EquipmentIndexEntity(
						getId(), iEquipmentId, sUserId, startDate);
				equipmentIndexEntity.setOwnerSamCnt(samples);
				equipments.put(key, equipmentIndexEntity);
			}
		}

	}

	private List<Map<String, Object>> queryResult(long startDate, long endDate) {
		String sql = "select er.equipment_id as equipmentid, er.user_id as userid, sum(samples) as samples "
				+ "from eq_record er "
				+ "inner join _r_user_equipment rue on er.user_id = rue.id1 and er.equipment_id = rue.id2 "
				+ "where er.dtend between ? and ? "
				+ "and rue.type = 'incharge' "
				+ "group by er.equipment_id, er.user_id "
				+ "order by er.equipment_id";
		JdbcTemplateParam jdbcTemplateParam = new JdbcTemplateParam(sql,
				new Object[] { startDate, endDate }, new int[] {
						java.sql.Types.INTEGER, java.sql.Types.INTEGER });
		return baseDao.queryForList(jdbcTemplateParam);
	}

}
