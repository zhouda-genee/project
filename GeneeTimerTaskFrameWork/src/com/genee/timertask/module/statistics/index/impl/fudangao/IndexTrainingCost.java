package com.genee.timertask.module.statistics.index.impl.fudangao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.genee.timertask.framework.core.base.JdbcTemplateParam;
import com.genee.timertask.framework.utils.json.JsonUtil;
import com.genee.timertask.module.pojo.EquipmentIndexEntity;
import com.genee.timertask.module.statistics.index.IndexBase;

/**
 * 
 * @ClassName: IndexUsedDuration
 * @Description: 培训费指标 目前只为复旦高分子使用
 * 				逻辑：去eq_record表读取_extra字段，在里面取training_charge的值进行累加
 * @author da.zhou@geneegroup.com
 * @date 2014年8月18日 下午4:22:28
 *
 */
@Component("train_cost_fudangao")
public class IndexTrainingCost extends IndexBase {
	
	private final static Logger logger = Logger.getLogger("genee");

	@Override
	public void run(long startDate, long endDate, Map<String, EquipmentIndexEntity> equipments) {
		
		long iEquipmentId;
		String key;
		String extra; // 扩展项
		
		// {"training_users":{"1":"技术支持","5":"may"},"training_charge":"400"}
		List<Map<String, Object>> results = queryResult(startDate, endDate);
		for (Map<String, Object> result : results) {
			iEquipmentId = (long)result.get("equipmentid");
			extra = (String)result.get("extra");
			
			if (StringUtils.isNotEmpty(extra)){
				try{
					JSONObject jsonObj = JSONObject.fromObject(extra);
					
					Object oCharge = jsonObj.get("training_charge");
					Object oUserInfos = jsonObj.get("training_users");
					if (oCharge == null || oUserInfos == null) {
						continue;
					}
					// 培训费 元/人
					Double dCharge = Double.parseDouble(String.valueOf(oCharge));
					// 培训人
					Map<String, String> userMap = JsonUtil.getMap4Json(JSONObject.fromObject(oUserInfos));
					//String[] userInfos = String.valueOf(oUserInfos).replaceAll("{", "").replaceAll("}", "").split(",");
					for (String userId : userMap.keySet()) {
						key = String.valueOf(iEquipmentId) + "#" + userId;
						if (equipments.containsKey(key)){
							EquipmentIndexEntity equipmentIndexEntity = equipments.get(key);
							equipmentIndexEntity.setTrainCost(equipmentIndexEntity.getTrainCost() + dCharge);
						} else {
							EquipmentIndexEntity equipmentIndexEntity = new EquipmentIndexEntity(getId(), iEquipmentId, userId, startDate);
							equipmentIndexEntity.setTrainCost(dCharge);
							equipments.put(key, equipmentIndexEntity);
						}
					}
				} catch (Exception ex){
					logger.error("培训费指标\t仪器ID:" + iEquipmentId + " 计算日:" + startDate + " Exception:" + ex.getMessage());
					continue;
				}
			}
		}

	}
	
	private List<Map<String, Object>> queryResult(long startDate, long endDate){
		String sql = "select a.equipment_id as equipmentid, a._extra as extra "
				+ "from eq_record a "
				+ "where a.dtend between ? and ? ";
		JdbcTemplateParam jdbcTemplateParam = new JdbcTemplateParam(sql,
				new Object[] { startDate, endDate }, 
				new int[] { java.sql.Types.INTEGER, java.sql.Types.INTEGER });
		return baseDao.queryForList(jdbcTemplateParam);
	}

}
