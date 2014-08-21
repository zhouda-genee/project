package com.genee.timertask.module.statistics.index.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.genee.timertask.framework.core.base.JdbcTemplateParam;
import com.genee.timertask.framework.utils.dateutil.DateUtil;
import com.genee.timertask.framework.utils.timestamp.TimestampUtil;
import com.genee.timertask.module.statistics.index.IndexBase;
import com.genee.timertask.module.statistics.pojo.EquipmentIndexEntity;

/**
 * 
 * @ClassName: IndexValidDuration
 * @Description: 有效机时指标<br>统计时段内，该仪器在设定的阈值范围内的使用机时总和
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
		Date dResultStartDate, dResultEndDate, dStartDate, dEndDate;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long dSecond; // 秒
		String key;
		
		List<Map<String, Object>> results = queryResult(startDate, endDate);
		for (Map<String, Object> result : results) {
			iResultStartDate = (int)result.get("start");
			iResultEndDate = (int)result.get("end");
			iEquipmentId = (long)result.get("equipmentid");
			key = String.valueOf(iEquipmentId);
			
			// 查询结果开始时间
			dResultStartDate = TimestampUtil.timestampToDate(iResultStartDate);
			// 查询结果结束时间
			dResultEndDate = TimestampUtil.timestampToDate(iResultEndDate);
			// 传入开始时间
			dStartDate = TimestampUtil.timestampToDate(startDate);
			// 传入结束时间
			dEndDate = TimestampUtil.timestampToDate(endDate);
			
			// 日期比较
			// 1、如果dResultStartDate和dResultEndDate同一天，则用结束时间－开始时间，计算出秒数
			// 2、如果dStartDate与dResultStartDate的年月日相同，则计算dResultStartDate至23:59:59的秒数
			// 3、如果dEndDate与dResultEndDate的年月日相同，则计算00:00:00至dResultEndDate的秒数
			// 4、如果dStartDate在dResultStartDate和dResultEndDate之间，则为24*60*60的秒数
			// 5、都不满足则为0
			if (sdf.format(dResultStartDate).equals(sdf.format(dResultEndDate))){
				dSecond = dateSubtractSecond(dResultStartDate, dResultEndDate);
			} else if (sdf.format(dStartDate).equals(sdf.format(dResultStartDate))){
				dSecond = dateSubtractSecond(dResultStartDate, dEndDate);
			} else if (sdf.format(dEndDate).equals(sdf.format(dResultEndDate))){
				dSecond = dateSubtractSecond(dStartDate, dResultEndDate);
			} else if (DateUtil.compareDate(dResultStartDate, dStartDate) == -1 && 
					DateUtil.compareDate(dStartDate, dResultEndDate) == -1){
				dSecond = 24 * 60 * 60;
			} else {
				dSecond = 0;
			}
			if (equipments.containsKey(key)){
				EquipmentIndexEntity equipmentIndexEntity = equipments.get(key);
				equipmentIndexEntity.setValidDur(equipmentIndexEntity.getValidDur() + dSecond);
			} else {
				EquipmentIndexEntity equipmentIndexEntity = new EquipmentIndexEntity(getId(), iEquipmentId, startDate);
				equipmentIndexEntity.setValidDur(dSecond);
				equipments.put(key, equipmentIndexEntity);
			}
		}

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

}
