package com.genee.web.module.service.statistics;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genee.web.module.dao.ExcelDao;

/**
 * @author jinzhe.hu
 * 
 * @date 2014年8月28日
 */

@Service
public class ExcelServiceImpl implements ExcelService {
	@Autowired
	private ExcelDao excelDao;

	@Override
	public List<Map<String, Object>> getTopHeaders(String identities) {
		return excelDao.queryTopHeaders(identities);
	}

	@Override
	public List<Map<String, Object>> getMidHeaders(String identities) {
		return excelDao.queryMidHeaders(identities);
	}
}
