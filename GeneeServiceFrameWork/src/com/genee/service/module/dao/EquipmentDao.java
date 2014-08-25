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
public class EquipmentDao {

	@Autowired
	@Qualifier("basedao")
	private BaseDao baseDao;

	/**
	 * 根据仪器名称模糊查询仪器
	 * 
	 * @param name
	 *            仪器名称		
	 * 
	 * @return 仪器列表
	 * 
	 * 
	 * @see BaseEntity
	 */
	public List<BaseEntity> queryLab(String name) {
		String sql = "select e.id, e.name from equipment e where e.name like ?";
		JdbcTemplateParam param = new JdbcTemplateParam(sql, new Object[] { "%"
				+ name + "%" }, new int[] { java.sql.Types.VARCHAR });
		return MapToBeanUtil.MapToBean(BaseEntity.class,
				baseDao.queryForList(param));
	}
}
