package com.genee.timertask.module.statistics.index.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.genee.timertask.framework.core.base.JdbcTemplateParam;
import com.genee.timertask.framework.utils.prop.PropertiesUtil;
import com.genee.timertask.module.statistics.index.IndexBase;
import com.genee.timertask.module.statistics.pojo.EquipmentIndexEntity;

/**
 * 
 * @ClassName IndexOffcampusCharge
 * @Description 校外收费 统计时段内，该仪器的使用者为校外人员使用收费总和
 * @author hujinzhe
 * @date 2014年8月26日 上午09:09
 *
 */
@Component("off_cam_charge")
public class IndexOffcampusCharge extends IndexBase {

	@Override
	public void run(long startDate, long endDate,
			Map<String, EquipmentIndexEntity> equipments) {

		long iEquipmentId; // 仪器ID
		String sUserId; // 用户ID
		BigDecimal amount; // 校外计费
		String key; // 日表记录实体键

		List<Map<String, Object>> results = queryResult(startDate, endDate);
		for (Map<String, Object> result : results) {
			iEquipmentId = (long) result.get("equipmentid");
			sUserId = String.valueOf(result.get("userid"));
			amount = new BigDecimal((double) result.get("amount")).setScale(2, BigDecimal.ROUND_HALF_UP);
			key = String.valueOf(iEquipmentId) + "#" + sUserId;

			if (equipments.containsKey(key)) {
				EquipmentIndexEntity equipmentIndexEntity = equipments.get(key);
				equipmentIndexEntity.setOffCamCharge(equipmentIndexEntity
						.getOffCamCharge() + amount.doubleValue());
			} else {
				EquipmentIndexEntity equipmentIndexEntity = new EquipmentIndexEntity(
						getId(), iEquipmentId, sUserId, startDate);
				equipmentIndexEntity.setOffCamCharge(amount.doubleValue());
				equipments.put(key, equipmentIndexEntity);
			}
		}

	}

	private List<Map<String, Object>> queryResult(long startDate, long endDate) {
		
		List<Map<String, Object>> allTag = this.queryAllTag();
		Object[] params = new Object[allTag.size() + 2];
		int[] paramsType = new int[allTag.size() + 2];
		
		params[0] = startDate;
		params[1] = endDate;
		paramsType[0] = java.sql.Types.INTEGER;
		paramsType[1] = java.sql.Types.INTEGER;
		
		String sql = "select ec.equipment_id as equipmentid, ec.user_id as userid, sum(ec.amount) as amount " 
				+ "from eq_charge ec "
				+ "where ec.ctime between ? and ? "
				+ "and ec.user_id in ("
				+ "select distinct u.id " 
				+ "from user u "
				+ "inner join _r_user_tag rut "
				+ "on u.id = rut.id1 "
				+ "where rut.id2 in (";
		
		for (int i = 0 ; i < allTag.size() ; i ++) {
			sql += "?";
			
			if (i != allTag.size() -1) {
				sql += ", ";
			}
			
			Map<String, Object> tag = allTag.get(i);
			long tagId = (long) tag.get("id");
			params[i + 2] = tagId;
			paramsType[i + 2] = java.sql.Types.BIGINT;
		}
		
		for (int i = 2; i <params.length; i ++) {
			System.out.print(params[i] + ", ");
		}
		
		sql += ")) group by ec.equipment_id, ec.user_id "
				+ "order by ec.equipment_id";
		
		JdbcTemplateParam jdbcTemplateParam = new JdbcTemplateParam(sql,
				params, paramsType);
		return baseDao.queryForList(jdbcTemplateParam);
	}
	
	private List<Map<String, Object>> queryAllTag() {
		List<Map<String, Object>> allTag = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> rootTag = this.queryRootTag();
		allTag.addAll(rootTag);
		
		for (Map<String, Object> tag : rootTag) {
			long tagId = (long) tag.get("id");
			long ccount = (long) tag.get("ccount");
			
			if (ccount > 0) {
				allTag = this.queryChildTag(tagId, allTag);
			}
		}
		
		return allTag;
	}
	
	private List<Map<String, Object>> queryRootTag() {
		String organizationName = PropertiesUtil.getPropertiesValue(
				"statistics.properties", "statistics.organizationName");
		String sql = "select tp.id, tp.name, count(tc.id) as ccount "
				+ "from tag tp "
				+ "left join tag tc "
				+ "on tp.id = tc.parent_id "
				+ "where tp.parent_id = ("
				+ "select id "
				+ "from tag "
				+ "where name = '组织机构'"
				+ ") and tp.name != ? "
				+ "group by tp.id, tp.name "
				+ "order by tp.id";
		JdbcTemplateParam jdbcTemplateParam = new JdbcTemplateParam(sql,
				new Object[] { organizationName}, new int[] {
						java.sql.Types.VARCHAR });
		return baseDao.queryForList(jdbcTemplateParam);
	}
	
	private List<Map<String, Object>> queryChildTag(long id, List<Map<String, Object>> list) {
		String sql = "select tp.id, tp.name, count(tc.id) as ccount from tag tp "
				+ "left join tag tc on tp.id = tc.parent_id "
				+ "where tp.parent_id = ? "
				+ "group by tp.id, tp.name "
				+ "order by tp.id";
		
		JdbcTemplateParam jdbcTemplateParam = new JdbcTemplateParam(sql,
				new Object[] { id}, new int[] {
						java.sql.Types.BIGINT });
		List<Map<String, Object>> childrenTag = baseDao.queryForList(jdbcTemplateParam);
		list.addAll(childrenTag);
		
		for (Map<String, Object> child : childrenTag) {
			long tagId = (long) child.get("id");
			long ccount = (long) child.get("ccount");
			
			if (ccount > 0) {
				list =  queryChildTag(tagId, list);
			}
		}
		
		return list;
	}
}
