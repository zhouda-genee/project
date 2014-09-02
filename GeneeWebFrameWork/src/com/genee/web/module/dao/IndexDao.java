/**
 * 
 */
package com.genee.web.module.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.genee.web.framework.core.base.JdbcTemplateParam;
import com.genee.web.framework.core.base.dao.BaseDao;
import com.genee.web.module.pojo.IndexEntity;
import com.genee.web.module.pojo.IndexRoleEntity;
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
		String sql = "select t_id, t_name from s_index_type";
		JdbcTemplateParam param = new JdbcTemplateParam(sql);
		List<Map<String, Object>> results = baseDao.queryForList(param);
		
		List<IndexTypeEntity> indexTypeEntities = new ArrayList<IndexTypeEntity>( results.size());
		for (Map<String, Object> result : results) {
			IndexTypeEntity indexTypeEntity = new IndexTypeEntity();
			indexTypeEntity.fromResultSet(result);
			indexTypeEntities.add(indexTypeEntity);
		}
		return indexTypeEntities;
	}
	
	/**
	 * 查询某一角色
	 * @param roleId
	 * @return
	 */
	public RoleEntity searchRole(int roleId) {
		String sql = "select r_id, r_name from s_role where r_id = ?";
		JdbcTemplateParam param = new JdbcTemplateParam(sql, new Object[] { roleId }, new int[] { java.sql.Types.INTEGER });
		Map<String, Object> result = baseDao.queryForMap(param);
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.fromResultSet(result);
		return roleEntity;
	}
	
	/**
	 * 根据指标类型查询指标明细
	 * @param typeId
	 * @return
	 */
	public List<IndexEntity> searchIndexDetailByType(int typeId) {
		String sql = "select s_id, t_id, s_sort, s_name"
				+ " from s_index "
				+ " where s_onoff = '0' and t_id = ?"
				+ " order by s_sort";
		JdbcTemplateParam param = new JdbcTemplateParam(sql, new Object[] { typeId }, new int[] { java.sql.Types.INTEGER });
		List<Map<String, Object>> results = baseDao.queryForList(param);
		List<IndexEntity> indexEntities = new ArrayList<IndexEntity>(results.size());
		
		for (Map<String, Object> result : results) {
			IndexEntity indexEntity = new IndexEntity();
			indexEntity.fromResultSet(result);
			indexEntities.add(indexEntity);
		}
		
		return indexEntities;
	}
		
	/**
	 * 根据角色查询指标明细
	 * @param roleId
	 * @return
	 */
	public List<IndexEntity> searchIndexDetailByRole(int roleId) {
		String sql = "select SI.s_id, SI.t_id, SI.s_sort"
				+ " from s_role SR, s_r_index_role SIR, s_index SI"
				+ " where SI.s_onoff = '0' and SR.r_id = SIR.r_id and  SI.s_id = SIR.s_id and SR.r_id = ?";
		JdbcTemplateParam param = new JdbcTemplateParam(sql, new Object[]{ roleId }, new int[] { java.sql.Types.INTEGER });
		List<Map<String, Object>> results = baseDao.queryForList(param);
		List<IndexEntity> indexEntities = new ArrayList<IndexEntity>( results.size());
		for (Map<String, Object> result : results) {
			IndexEntity indexEntity = new IndexEntity();
			indexEntity.fromResultSet(result);
			indexEntities.add(indexEntity);			
		}
		return indexEntities;
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
