package com.genee.service.module.service.statistics;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.genee.service.framework.core.base.PageSupport;
import com.genee.service.framework.utils.timestamp.TimestampUtil;
import com.genee.service.module.dao.StatisticsDao;
import com.genee.service.module.pojo.EquipmentIndexEntity;

public class StatisticsServiceImpl implements StatisticsService {
	
	@Autowired
	private StatisticsDao statisticsDao;

	@Override
	public PageSupport<EquipmentIndexEntity> queryEquipmentIndex(String eq_name, String eq_type,
			String eq_org, String eq_contact, String eq_incharge,
			String lab_org, String lab, String user, long dstart,
			long dend, int size, int page, String sort_name, String sort) throws Exception {
		
		if (String.valueOf(dstart).length() > 10)
			dstart = TimestampUtil.dateToTimestamp10(new Date(dstart));
		if (String.valueOf(dend).length() > 10)
			dend = TimestampUtil.dateToTimestamp10(new Date(dend));
		
		if (size == 0 || page == 0)
			throw new Exception("分页必须设置size和page值");
		
		PageSupport<EquipmentIndexEntity> pageSupport = new PageSupport<EquipmentIndexEntity>();
		pageSupport.setPage(page);
		pageSupport.setPageSize(size);
		
		statisticsDao.queryEquipmentIndex(eq_name, eq_type, eq_org,
				eq_contact, eq_incharge, lab_org, lab, user, dstart,
				dend, sort_name, sort, pageSupport);
		
		return pageSupport;
	}

	@Override
	public List<EquipmentIndexEntity> queryEquipmentIndex(String eq_name,
			String eq_type, String eq_org, String eq_contact,
			String eq_incharge, String lab_org, String lab, String user,
			long dstart, long dend, String sort_name, String sort) {
		
		if (String.valueOf(dstart).length() > 10)
			dstart = TimestampUtil.dateToTimestamp10(new Date(dstart));
		if (String.valueOf(dend).length() > 10)
			dend = TimestampUtil.dateToTimestamp10(new Date(dend));
		
		return statisticsDao.queryEquipmentIndex(eq_name, eq_type, eq_org,
				eq_contact, eq_incharge, lab_org, lab, user, dstart,
				dend, sort_name, sort, null);
	}

	@Override
	public EquipmentIndexEntity queryEquipmentIndex(String eq_name,
			String eq_type, String eq_org, String eq_contact,
			String eq_incharge, String lab_org, String lab, String user,
			long dstart, long dend) {
		
		if (String.valueOf(dstart).length() > 10)
			dstart = TimestampUtil.dateToTimestamp10(new Date(dstart));
		if (String.valueOf(dend).length() > 10)
			dend = TimestampUtil.dateToTimestamp10(new Date(dend));
		
		return statisticsDao.queryEquipmentIndexCount(eq_name, eq_type, eq_org,
				eq_contact, eq_incharge, lab_org, lab, user, dstart,
				dend);
		
	}

}
