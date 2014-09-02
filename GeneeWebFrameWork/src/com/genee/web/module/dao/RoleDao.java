package com.genee.web.module.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.genee.web.framework.core.base.JdbcTemplateParam;
import com.genee.web.framework.core.base.dao.BaseDao;
import com.genee.web.framework.utils.map.MapToBeanUtil;
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
	
}
