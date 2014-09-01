package com.genee.timertask.module.statistics.index.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.genee.timertask.framework.core.base.JdbcTemplateParam;
import com.genee.timertask.module.pojo.EquipmentIndexEntity;
import com.genee.timertask.module.statistics.index.IndexBase;

/**
 * 
 * @ClassName: IndexOwnerUsedDuration
 * @Description: 机主使用机时	使用机时指标中为机主的用户，则认为是机主使用机时
 * @author da.zhou@geneegroup.com
 * @date 2014年8月21日 上午10:44:01
 *
 */
@Component("owner_used_dur")
public class IndexOwnerUsedDuration extends IndexBase {

	@Override
	public void run(long startDate, long endDate, Map<String, EquipmentIndexEntity> equipments) {
		
		long iEquipmentId;
		String sUserId;
		String skey;
		
		List<Map<String, Object>> results = queryResult(startDate, endDate);
		for (Map<String, Object> result : results) {
			sUserId = String.valueOf(result.get("userid"));
			iEquipmentId = (long)result.get("equipmentid");
			skey = String.valueOf(iEquipmentId) + "#" + sUserId;

			if (equipments.containsKey(skey)){
				EquipmentIndexEntity equipmentIndexEntity = equipments.get(skey);
				// 使用者为机主的使用机时，则认为就是机主使用机时
				equipmentIndexEntity.setOwnerUsedDur(equipmentIndexEntity.getUsedDur());
			}
		}

	}
	
	// 查找时间段内机主使用仪器记录
	private List<Map<String, Object>> queryResult(long startDate, long endDate){
		String sql = "select distinct a.user_id as userid, a.equipment_id as equipmentid "
				+ "from eq_record a, _r_user_equipment b "
				+ "where (a.dtstart between ? and ? "
				+ "or a.dtend between ? and ? "
				+ "or ? between a.dtstart and a.dtend "
				+ "or ? between a.dtstart and a.dtend) "
				+ "and a.equipment_id = b.id2 and a.user_id = b.id1 and b.type = 'incharge'";
		JdbcTemplateParam jdbcTemplateParam = new JdbcTemplateParam(sql,
				new Object[] { startDate, endDate, startDate, endDate, startDate, endDate }, 
				new int[] { java.sql.Types.INTEGER, java.sql.Types.INTEGER,
						java.sql.Types.INTEGER, java.sql.Types.INTEGER,
						java.sql.Types.INTEGER, java.sql.Types.INTEGER });
		return baseDao.queryForList(jdbcTemplateParam);
	}

}
