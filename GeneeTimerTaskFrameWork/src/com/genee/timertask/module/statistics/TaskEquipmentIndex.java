package com.genee.timertask.module.statistics;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.genee.timertask.framework.core.context.SpringContext;
import com.genee.timertask.framework.utils.dateutil.DateUtil;
import com.genee.timertask.framework.utils.dateutil.DateUtil.DateType;
import com.genee.timertask.framework.utils.json.JsonUtil;
import com.genee.timertask.framework.utils.timestamp.TimestampUtil;
import com.genee.timertask.module.statistics.dao.EquipmentIndexDao;
import com.genee.timertask.module.statistics.dao.IndexDao;
import com.genee.timertask.module.statistics.index.IndexBase;
import com.genee.timertask.module.statistics.pojo.EquipmentIndexEntity;
import com.genee.timertask.module.statistics.pojo.IndexEntity;

@Component
public class TaskEquipmentIndex {
	
	private final static Logger logger = Logger.getLogger("genee");

	@Autowired
	private IndexDao indexDao;
	
	@Autowired
	private EquipmentIndexDao equipmentIndexDao;
	
	// 天数
	private int days = 30;
	
	public void setDays(int days) {
		this.days = days;
	}

	/**
	 * 
	 * @Title: run 
	 * @Description: 定时任务执行 截止日期为当前系统日期的前一天
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	@Transactional(rollbackFor=Exception.class)
	public void run() {
		// 系统当前时间
		Date currentDate = DateUtil.sysDate();
		// 计算起始日期
		Date startDate = DateUtil.addDate(currentDate, DateType.DAY, -days);
		// 计算结束日期
		Date endDate = DateUtil.addDate(currentDate, DateType.DAY, -1);
		
		equipmentIndex(startDate, endDate);
	}
	
	/**
	 * 
	 * @Title: run 
	 * @Description: 指定时间段 
	 * @param @param startDate
	 * @param @param endDate
	 * @return void
	 * @throws
	 */
	@Transactional(rollbackFor=Exception.class)
	public void run(Date startDate, Date endDate){
		equipmentIndex(startDate, endDate);
	}
	
	private void equipmentIndex(Date startDate, Date endDate) {
		// 待计算的指标集合
		List<IndexEntity> indexs = indexDao.queryIndexs();
		
		int i = 1;
		// 计算日期
		Date calcDate = startDate;
		while (DateUtil.compareDate(calcDate, endDate) <= 0) {
			
			// 一天所有的仪器指标记录
			Map<String, EquipmentIndexEntity> equipments = new HashMap<String, EquipmentIndexEntity>();
			
			String sStartDate = DateUtil.date2String(calcDate) + " 00:00:00";
			String sEndDate = DateUtil.date2String(calcDate) + " 23:59:59";
			long lStartDate = TimestampUtil.dateToTimestamp10(DateUtil.string2Timestamp(sStartDate));
			long lEndDate = TimestampUtil.dateToTimestamp10(DateUtil.string2Timestamp(sEndDate));
			
			for (IndexEntity index : indexs) {
				IndexBase indexBase = (IndexBase)SpringContext.getBean(index.getIndexCode());
				if (indexBase == null){
					logger.error("没有找到[" + index.getIndexCode() + "]对应的指标类");
				} else {
					logger.info("执行[" + index.getIndexCode() + "][" + DateUtil.date2String(calcDate) + "]指标开始");
					logger.info("\t参数:"
							+"\n\tstartdate:" + lStartDate
							+"\n\tenddate:" + lEndDate
							+"\n\tequipments:" + JsonUtil.getJsonString4JavaPOJO(equipments));
					indexBase.run(lStartDate, lEndDate, equipments);
					logger.info("执行[" + index.getIndexCode() + "][" + DateUtil.date2String(calcDate) + "]指标结束");
				}
			}
			
			// 删除计算日的记录
			equipmentIndexDao.deleteEquipmentIndexByDay(TimestampUtil.dateToTimestamp10(calcDate));
			// 数据库插入操作
			Iterator<EquipmentIndexEntity> iter = equipments.values().iterator();
			while (iter.hasNext()) {
				EquipmentIndexEntity equipmentIndex = iter.next();
				equipmentIndexDao.insertEquipmentIndexByUser(equipmentIndex);
			}
			
			calcDate = DateUtil.addDate(startDate, DateType.DAY, i);
			i++;
		}
	}
}
