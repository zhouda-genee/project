package com.genee.web.module.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.genee.web.framework.core.base.JdbcTemplateParam;
import com.genee.web.framework.core.base.dao.BaseDao;
import com.genee.web.framework.utils.map.MapToBeanUtil;
import com.genee.web.module.constants.GlobalStatus;
import com.genee.web.module.pojo.IndexEntity;
import com.genee.web.module.pojo.IndexTypeEntity;
import com.genee.web.module.pojo.RoleInfoEntity;

@Repository
public class RoleDao {
	
	@Autowired
	@Qualifier("basedao")
	private BaseDao baseDao;
	
	/**
	 * 
	 * @Title: queryRoleByUser 
	 * @Description: 查找用户角色基本信息 
	 * @param userId	用户ID
	 * @return List<RoleInfoEntity> 返回角色基本信息集合
	 * @throws
	 */
	public List<RoleInfoEntity> queryRoleByUser(int userId) {
		String sql = "select _r.id as roleid, _r.name as rolename, _r._extra as roleextra "
				+ "from user _u, _r_user_role _r_u, role _r "
				+ "where _u.id = ? "
				+ "and _u.id = _r_u.id1 "
				+ "and _r.id = _r_u.id2";
		JdbcTemplateParam param = new JdbcTemplateParam(sql, 
				new Object[]{userId}, 
				new int[]{java.sql.Types.INTEGER});
		
		List<Map<String, Object>> result = baseDao.queryForList(param); 
		return MapToBeanUtil.MapToBean(RoleInfoEntity.class, result);
	}
	
	/**
	 * 
	 * @Title: queryIndexTypeEntityByRole 
	 * @Description: 根据角色查找对应的所有指标类型
	 * @param roleId	角色ID
	 * @return List<IndexTypeEntity> 返回指标类型集合
	 * 
	 */
	public List<IndexTypeEntity> queryIndexTypeEntityByRole(String roleId) {
		String sql = "select distinct it.t_id as tId, it.t_name as tName "
				+ "from s_index_type it "
				+ "inner join s_index i " 
				+ "on it.t_id = i.t_id " 
				+ "inner join s_r_index_role ir " 
				+ "on i.s_id = ir.s_id ";
		
		if (roleId.indexOf(",") == -1) {
			sql += "where ir.r_id = "
					+ roleId;
		} else {
			sql += "where ir.r_id in ("
					+ roleId
					+ ")";
		}
		
		sql += " and i.s_onoff = '"
				+ GlobalStatus.ENABLED
				+ "' "
				+ "order by i.s_id";
		JdbcTemplateParam param = new JdbcTemplateParam(sql);
		
		List<Map<String, Object>> result = baseDao.queryForList(param); 
		return MapToBeanUtil.MapToBean(IndexTypeEntity.class, result);
	}
	
	/**
	 * 
	 * @Title: queryIndexEntityByRole 
	 * @Description: 根据角色查找对应的所有指标
	 * @param roleId	角色ID
	 * @return List<IndexEntity> 返回指标集合
	 * 
	 */
	public List<IndexEntity> queryIndexEntityByRole(String roleId) {
		String sql = "select distinct i.s_id as sId, i.s_name as sName, i.s_code as sCode, i.t_id as tId "
				+ "from s_index i " 
				+ "inner join s_r_index_role ir " 
				+ "on i.s_id = ir.s_id ";
		
		if (roleId.indexOf(",") == -1) {
			sql += "where ir.r_id = "
					+ roleId;
		} else {
			sql += "where ir.r_id in ("
					+ roleId
					+ ")";
		}
		
		sql += " and i.s_onoff = '"
				+ GlobalStatus.ENABLED
				+ "' "
				+ "order by i.s_id";
		JdbcTemplateParam param = new JdbcTemplateParam(sql);
		
		List<Map<String, Object>> result = baseDao.queryForList(param); 
		return MapToBeanUtil.MapToBean(IndexEntity.class, result);
	}
}
