package com.genee.service.module.math.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.genee.service.framework.core.base.JdbcTemplateParam;
import com.genee.service.framework.core.base.PageSupport;
import com.genee.service.framework.core.base.dao.BaseDao;

@Repository
public class MathDao {
	
	@Autowired
	@Qualifier("basedao")
	private BaseDao baseDao;
	
	public void queryUsers(PageSupport pageSupport){
		String sql = "select * from user";
		JdbcTemplateParam param = new JdbcTemplateParam(sql);
		baseDao.queryForList(param, pageSupport);
	}

}
