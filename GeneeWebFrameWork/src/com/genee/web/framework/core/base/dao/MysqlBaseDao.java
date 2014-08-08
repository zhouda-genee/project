package com.genee.web.framework.core.base.dao;

import java.util.List;
import java.util.Map;

import com.genee.web.framework.core.base.JdbcTemplateParam;
import com.genee.web.framework.core.base.PageSupport;

public class MysqlBaseDao extends BaseDao {
	
	@Override
	protected Object querySequencesId(String seqName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<Map<String, Object>> procedureForList(String productName,
			Object... inparam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Map<String, Object> procedureForMap(String productName,
			Object... inparam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void queryForList(JdbcTemplateParam param, PageSupport page) {
		// TODO Auto-generated method stub

	}

}
