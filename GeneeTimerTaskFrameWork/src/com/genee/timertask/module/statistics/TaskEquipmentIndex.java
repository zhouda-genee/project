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
import com.genee.timertask.framework.utils.timestamp.TimestampUtil;
import com.genee.timertask.module.dao.EquipmentIndexDao;
import com.genee.timertask.module.dao.IndexDao;
import com.genee.timertask.module.pojo.EquipmentIndexEntity;
import com.genee.timertask.module.pojo.IndexEntity;
import com.genee.timertask.module.statistics.index.IndexBase;

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
		logger.info("任务开始时间:" + System.currentTimeMillis());
		while (DateUtil.compareDate(calcDate, endDate) <= 0) {
			
			// 一天所有的仪器指标记录
			Map<String, EquipmentIndexEntity> equipments = new HashMap<String, EquipmentIndexEntity>();
			
			String sStartDate = DateUtil.date2String(calcDate) + " 00:00:00";
			String sEndDate = DateUtil.date2String(calcDate) + " 23:59:59";
			long lStartDate = TimestampUtil.dateToTimestamp10(DateUtil.string2Timestamp(sStartDate));
			long lEndDate = TimestampUtil.dateToTimestamp10(DateUtil.string2Timestamp(sEndDate));
			
			logger.info("开始计算[" + DateUtil.date2String(calcDate) + "]任务");
			
			IndexBase indexBase = null;
			for (IndexEntity index : indexs) {
				indexBase = (IndexBase)SpringContext.getBean(index.getIndexCode());
				if (indexBase == null){
					logger.error("\t没有找到[" + index.getIndexCode() + "]对应的指标类");
				} else {
					logger.info("\t执行[" + index.getIndexCode() + "]指标开始");
					logger.info("\t\t参数:"
							+"\n\t\tstartdate:" + lStartDate
							+"\n\t\tenddate:" + lEndDate);
					indexBase.run(lStartDate, lEndDate, equipments);
					logger.info("\t执行[" + index.getIndexCode() + "]指标结束");
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
			
			logger.info("结束计算[" + DateUtil.date2String(calcDate) + "]任务，当天共有:" + equipments.size() + "条记录");
			
			calcDate = DateUtil.addDate(startDate, DateType.DAY, i);
			i++;
		}
		logger.info("任务结束时间:" + System.currentTimeMillis());
	}
}
