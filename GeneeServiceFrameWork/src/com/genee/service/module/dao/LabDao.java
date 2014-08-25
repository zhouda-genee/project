package com.genee.service.module.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.genee.service.framework.core.base.JdbcTemplateParam;
import com.genee.service.framework.core.base.dao.BaseDao;
import com.genee.service.framework.utils.map.MapToBeanUtil;
import com.genee.service.module.pojo.BaseEntity;

@Repository
public class LabDao {

	@Autowired
	@Qualifier("basedao")
	private BaseDao baseDao;

	/**
	 * 根据课题组名称模糊查询课题组
	 * 
	 * @param name
	 *            课题组名称		
	 * 
	 * @return 课题组列表
	 * 
	 * 
	 * @see BaseEntity
	 */
	public List<BaseEntity> queryLab(String name) {
		String sql = "select l.id, l.name from lab l where l.name like ?";
		JdbcTemplateParam param = new JdbcTemplateParam(sql, new Object[] { "%"
				+ name + "%" }, new int[] { java.sql.Types.VARCHAR });
		return MapToBeanUtil.MapToBean(BaseEntity.class,
				baseDao.queryForList(param));
	}
}
