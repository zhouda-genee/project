package com.genee.service.module.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.genee.service.framework.core.base.JdbcTemplateParam;
import com.genee.service.framework.core.base.dao.BaseDao;
import com.genee.service.framework.utils.map.MapToBeanUtil;
import com.genee.service.module.pojo.OrganizationEntity;

@Repository
public class OrganizationDao {

	@Autowired
	@Qualifier("basedao")
	private BaseDao baseDao;

	/**
	 * 查询组织结构的根节点
	 * 
	 * @return 组织结构的根节点
	 * 
	 * 
	 * @see OrganizationEntity
	 */
	public OrganizationEntity queryRootOrganization() {
		String sql = "select tp.id, tp.name, count(tc.id) as ccount from tag tp "
				+ "left join tag tc on tp.id = tc.parent_id "
				+ "where tp.name = '组织机构'"
				+ "group by tp.id, tp.name "
				+ "order by tp.id";
		JdbcTemplateParam param = new JdbcTemplateParam(sql);
		return MapToBeanUtil.MapToBean(OrganizationEntity.class,
				baseDao.queryForMap(param));
	}

	/**
	 * 根据ID查询所有该组织结构节点下的子节点
	 * 
	 * @param id
	 *            组织结构id
	 * 
	 * @return 该组织结构节点下的所有子节点
	 * 
	 * 
	 * @see OrganizationEntity
	 */
	public List<OrganizationEntity> queryChildOrganization(int id) {
		String sql = "select tp.id, tp.name, count(tc.id) as ccount from tag tp "
				+ "left join tag tc on tp.id = tc.parent_id "
				+ "where tp.parent_id = ? "
				+ "group by tp.id, tp.name "
				+ "order by tp.id";
		JdbcTemplateParam param = new JdbcTemplateParam(sql,
				new Object[] { id }, new int[] { java.sql.Types.BIGINT });
		return MapToBeanUtil.MapToBean(OrganizationEntity.class,
				baseDao.queryForList(param));
	}
}
