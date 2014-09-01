package com.genee.timertask.module.statistics.index;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.genee.timertask.framework.core.base.dao.BaseDao;
import com.genee.timertask.framework.utils.dateutil.DateUtil;
import com.genee.timertask.framework.utils.timestamp.TimestampUtil;
import com.genee.timertask.module.pojo.EquipmentIndexEntity;

/**
 * 
 * @ClassName: IndexBase
 * @Description: 所有指标都继承该类，并实现run方法。
 * @author da.zhou@geneegroup.com
 * @date 2014年8月18日 上午10:45:12
 *
 */
public abstract class IndexBase {
	
	@Autowired
	@Qualifier("basedao")
	public BaseDao baseDao;
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 
	 * @Title: dateSubtractSecond 
	 * @Description: 计算两个时间差秒数，date1 必须要大于 date2
	 * @param date1
	 * @param date2
	 * @return int
	 * @throws
	 */
	protected long dateSubtractSecond(Date date1, Date date2) {
		if (date1.getTime() > date2.getTime())
			return -1;
		return (date2.getTime() - date1.getTime())/1000L;
	}
	
	/**
	 * 
	 * @Title: timestampCompareSecond 
	 * @Description: 计算时间与查询出来的时间进行比较，并计算出相差的秒数 
	 * @param @param iResultStartDate	查询结果的开始时间
	 * @param @param iResultEndDate		查询结果的结束时间
	 * @param @param startDate			计算日的开始时间
	 * @param @param endDate			计算日的结束时间
	 * @return long
	 * @throws
	 */
	protected long timestampCompareSecond(long iResultStartDate, long iResultEndDate, long startDate, long endDate) {
		Date dResultStartDate, dResultEndDate, dStartDate, dEndDate;
		long lSecond;
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
		if (SDF.format(dResultStartDate).equals(SDF.format(dResultEndDate))){
			lSecond = dateSubtractSecond(dResultStartDate, dResultEndDate);
		} else if (SDF.format(dStartDate).equals(SDF.format(dResultStartDate))){
			lSecond = dateSubtractSecond(dResultStartDate, dEndDate);
		} else if (SDF.format(dEndDate).equals(SDF.format(dResultEndDate))){
			lSecond = dateSubtractSecond(dStartDate, dResultEndDate);
		} else if (DateUtil.compareDate(dResultStartDate, dStartDate) == -1 && 
				DateUtil.compareDate(dStartDate, dResultEndDate) == -1){
			lSecond = 24 * 60 * 60;
		} else {
			lSecond = 0;
		}
		return lSecond;
	}
	
	/**
	 * 
	 * @Title: getId 
	 * @Description: 用UUID生成记录唯一标识 
	 * @return String
	 * @throws
	 */
	protected String getId() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 
	 * @Title: run 
	 * @Description: 执行指标入口
	 * @param startDate	开始时间时间戳10位	年月日时分秒
	 * @param endDate	结束时间时间戳10位	年月日时分秒
	 * @param equipments	如果指标是仪器，则key为equipmentId，如果指标是使用者，则key为equipmentId#userId
	 * @return void
	 * @throws
	 */
	public abstract void run(long startDate, long endDate, Map<String, EquipmentIndexEntity> equipments);

}
