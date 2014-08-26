	package com.genee.timertask.module.statistics.index.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.genee.timertask.framework.core.base.JdbcTemplateParam;
import com.genee.timertask.module.statistics.index.IndexBase;
import com.genee.timertask.module.statistics.pojo.EquipmentIndexEntity;

/**
 * 
 * @ClassName IndexDelegationCharge
 * @Description 委托测试收费 统计时段内，该仪器所有使用记录中关联送样的使用记录的使用收费总和
 * @author hujinzhe
 * @date 2014年8月22日 下午17:22
 *
 */
@Component("delegation_charge")
public class IndexDelegationCharge extends IndexBase {

	@Override
	public void run(long startDate, long endDate,
			Map<String, EquipmentIndexEntity> equipments) {

		long iEquipmentId; // 仪器ID
		String sUserId; // 用户ID
		BigDecimal amount; // 委托测试收费	
		String key; // 日表记录实体键

		List<Map<String, Object>> results = queryResult(startDate, endDate);
		for (Map<String, Object> result : results) {
			iEquipmentId = (long) result.get("equipmentid");
			sUserId = String.valueOf(result.get("userid"));
			amount = new BigDecimal((double) result.get("amount")).setScale(2, BigDecimal.ROUND_HALF_UP);
			key = String.valueOf(iEquipmentId) + "#" + sUserId;

			if (equipments.containsKey(key)) {
				EquipmentIndexEntity equipmentIndexEntity = equipments.get(key);
				equipmentIndexEntity.setDelegationCharge(equipmentIndexEntity
						.getDelegationCharge() + amount.doubleValue());
			} else {
				EquipmentIndexEntity equipmentIndexEntity = new EquipmentIndexEntity(
						getId(), iEquipmentId, sUserId, startDate);
				equipmentIndexEntity.setDelegationCharge(amount.doubleValue());
				equipments.put(key, equipmentIndexEntity);
			}
		}

	}

	private List<Map<String, Object>> queryResult(long startDate, long endDate) {
		String sql = "select ec.equipment_id as equipmentid, ec.user_id as userid, sum(ec.amount) as amount "
				+ "from eq_charge ec " 
				+ "inner join eq_record er "
				+ "on ec.source_id = er.id "
				+ "and ec.source_name = 'eq_record' "
				+ "inner join eq_sample es " 
				+ "on es.record_id = er.id "
				+ "where ec.ctime between ? and ? "
				+ "group by ec.equipment_id, ec.user_id " 
				+ "order by ec.equipment_id";
		JdbcTemplateParam jdbcTemplateParam = new JdbcTemplateParam(sql,
				new Object[] { startDate, endDate }, new int[] {
						java.sql.Types.INTEGER, java.sql.Types.INTEGER });
		return baseDao.queryForList(jdbcTemplateParam);
	}

}
