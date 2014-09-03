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
import com.genee.web.framework.utils.map.MapToBeanUtil;
import com.genee.web.module.pojo.IndexEntity;
import com.genee.web.module.pojo.IndexTypeEntity;
import com.genee.web.module.pojo.RoleEntity;

/**
 * @author yanan.che 2014年8月13日
 */
@Repository
public class IndexDao {

	@Autowired
	@Qualifier("basedao")
	private BaseDao baseDao;
	
	/**
	 * 查询指标类型表中的所有记录
	 * @return
	 */
	public List<IndexTypeEntity> searchAllIndexTypeDetail() {
		String sql = "select t_id as tId, t_name as tName from s_index_type";
		JdbcTemplateParam param = new JdbcTemplateParam(sql);
		List<Map<String, Object>> results = baseDao.queryForList(param);
		
		return MapToBeanUtil.MapToBean(IndexTypeEntity.class, results);
	}
	
	/**
	 * 查询某一角色
	 * @param roleId
	 * @return
	 */
	public RoleEntity searchRole(int roleId) {
		String sql = "select r_id as rId, r_name as rName from s_role where r_id = ?";
		JdbcTemplateParam param = new JdbcTemplateParam(sql, new Object[] { roleId }, new int[] { java.sql.Types.INTEGER });
		Map<String, Object> result = baseDao.queryForMap(param);

		return MapToBeanUtil.MapToBean(RoleEntity.class, result);
	}
	
	/**
	 * 根据指标类型查询指标明细
	 * @param typeId
	 * @return
	 */
	public List<IndexEntity> searchIndexDetailByType(int typeId) {
		String sql = "select s_id as sId, t_id as tId, s_sort as sSort, s_name as sName"
				+ " from s_index "
				+ " where s_onoff = '0' and t_id = ?"
				+ " order by s_sort";
		JdbcTemplateParam param = new JdbcTemplateParam(sql, new Object[] { typeId }, new int[] { java.sql.Types.INTEGER });
		List<Map<String, Object>> results = baseDao.queryForList(param);

		return MapToBeanUtil.MapToBean(IndexEntity.class, results);
	}
		
	/**
	 * 根据角色查询指标明细
	 * @param roleId
	 * @return
	 */
	public List<IndexEntity> searchIndexDetailByRole(int roleId) {
		String sql = "select SI.s_id as sId, SI.t_id as tId, SI.s_sort as sSort"
				+ " from s_role SR, s_r_index_role SIR, s_index SI"
				+ " where SI.s_onoff = '0' and SR.r_id = SIR.r_id and  SI.s_id = SIR.s_id and SR.r_id = ?";
		JdbcTemplateParam param = new JdbcTemplateParam(sql, new Object[]{ roleId }, new int[] { java.sql.Types.INTEGER });
		List<Map<String, Object>> results = baseDao.queryForList(param);

		return MapToBeanUtil.MapToBean(IndexEntity.class, results);
	}
	
	/**
	 * 删除某一角色的指标关系
	 * @param roleId
	 */
	public void deleteIndexRoleRelation(int roleId) {
		String sql = "delete from s_r_index_role where r_id = ?";
		JdbcTemplateParam param = new JdbcTemplateParam(sql, new Object[] { roleId }, new int[] { java.sql.Types.INTEGER });
		baseDao.update(param);
	}
	
	/**
	 * 添加某一角色的指标关系
	 * @param roleId
	 */
	public void addIndexRoleRelation(String[] arrCkb, int roleId) {
		for(int i = 0; i < arrCkb.length; i++) {
			String sql = "insert into s_r_index_role(s_id, r_id) values(?, ?)";
			JdbcTemplateParam param = new JdbcTemplateParam(sql, 
					new Object[] { arrCkb[i], roleId }, 
					new int[] { java.sql.Types.INTEGER, java.sql.Types.INTEGER });
			baseDao.update(param);
		}
	}
	
}
