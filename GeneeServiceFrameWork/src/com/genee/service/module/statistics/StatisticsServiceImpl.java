package com.genee.service.module.statistics;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.genee.service.framework.core.base.PageSupport;
import com.genee.service.framework.utils.timestamp.TimestampUtil;
import com.genee.service.module.dao.StatisticsDao;

public class StatisticsServiceImpl implements StatisticsService {
	
	@Autowired
	private StatisticsDao statisticsDao;

	@Override
	public PageSupport<Map<String, Object>> queryEquipmentIndex(String eq_name, String eq_type,
			String eq_org, String eq_contact, String eq_incharge,
			String lab_org, String lab, String user, long dstart,
			long dend, int size, int page, String sort_name, String sort) {
		
		if (String.valueOf(dstart).length() > 10)
			dstart = TimestampUtil.dateToTimestamp10(new Date(dstart));
		if (String.valueOf(dend).length() > 10)
			dend = TimestampUtil.dateToTimestamp10(new Date(dend));
		
		return statisticsDao.queryEquipmentIndex(eq_name, eq_type, eq_org,
				eq_contact, eq_incharge, lab_org, lab, user, dstart,
				dend, size, page, sort_name, sort);
	}

}
