/**
 * 
 */
package com.genee.web.module.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.genee.web.framework.core.base.JdbcTemplateParam;
import com.genee.web.framework.core.base.dao.BaseDao;

/**
 * @author jinzhe.hu
 * @date 2014年8月27日 下午17:56
 */
@Repository
public class ExcelDao {

	@Autowired
	@Qualifier("basedao")
	private BaseDao baseDao;

	/**
	 * 查询根据指标的ID查询指标分类
	 * 
	 * @param identities 指标ID列表
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryTopHeaders(String identities) {
		String sql = "select it.t_id as id, it.t_name as name, count(i.s_id) as ccount "
				+ "from s_index i "
				+ "inner join s_index_type it on i.t_id = it.t_id ";
		
		if (identities.indexOf(",") != -1) {
			sql += "where i.s_id in ("
					+ identities
					+ ")";
		} else {
			sql += "where i.s_id = "
					+ identities;
		}
				
		sql += " group by it.t_id, it.t_name "
				+ "order by it.t_id";
		JdbcTemplateParam param = new JdbcTemplateParam(sql);
		return baseDao.queryForList(param);
	}
	
	/**
	 * 查询根据指标的ID查询指标
	 * 
	 * @param identities 指标ID列表
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryMidHeaders(String identities) {
		String sql = "select i.s_id as id, i.s_name as name, i.s_code as code "
				+ "from s_index i ";
		
		if (identities.indexOf(",") != -1) {
			sql += "where i.s_id in ("
					+ identities
					+ ")";
		} else {
			sql += "where i.s_id = "
					+ identities;
		}
		
		sql += " order by i.s_id";
		JdbcTemplateParam param = new JdbcTemplateParam(sql);
		return baseDao.queryForList(param);
	}
}
