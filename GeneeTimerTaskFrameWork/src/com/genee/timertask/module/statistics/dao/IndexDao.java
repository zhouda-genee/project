package com.genee.timertask.module.statistics.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.genee.timertask.framework.core.base.JdbcTemplateParam;
import com.genee.timertask.framework.core.base.dao.BaseDao;
import com.genee.timertask.framework.utils.map.MapToBeanUtil;
import com.genee.timertask.module.statistics.pojo.IndexEntity;

@Repository
public class IndexDao {
	
	@Autowired
	@Qualifier("basedao")
	private BaseDao baseDao;
	
	public List<IndexEntity> queryIndexs() {
		String sql = "select s_id as indexId, s_name as indexName, s_code as indexCode, s_flag as indexFlag, s_sort as indexSort, s_onoff as indexOnOff, t_id as indexType from s_index order by s_sort";
		JdbcTemplateParam param = new JdbcTemplateParam(sql);
		List<Map<String, Object>> results = baseDao.queryForList(param);
		return MapToBeanUtil.MapToBean(IndexEntity.class, results);
	}

}
