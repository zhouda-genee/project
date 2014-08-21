package com.genee.timertask.module.statistics.index;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.genee.timertask.framework.core.base.dao.BaseDao;
import com.genee.timertask.module.statistics.pojo.EquipmentIndexEntity;

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
