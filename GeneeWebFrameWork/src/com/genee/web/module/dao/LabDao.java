package com.genee.web.module.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.genee.web.framework.core.base.JdbcTemplateParam;
import com.genee.web.framework.core.base.dao.BaseDao;
import com.genee.web.framework.utils.map.MapToBeanUtil;
import com.genee.web.module.pojo.LabEntity;

@Repository
public class LabDao {
	
	@Autowired
	@Qualifier("basedao")
	private BaseDao baseDao;
	
	/**
	 * 
	 * @Title: queryLabByUser 
	 * @Description: 查找用户所属的课题组 
	 * @param @param userId
	 * @param @return
	 * @return LabEntity
	 * @throws
	 */
	public LabEntity queryLabByUser(int userId){
		String sql = "select id as labid, name as labname, owner_id as labowner from lab where owner_id = ?";
		JdbcTemplateParam param = new JdbcTemplateParam(sql, new Object[]{userId}, new int[]{java.sql.Types.INTEGER});
		Map<String, Object> result = baseDao.queryForMap(param);
		return MapToBeanUtil.MapToBean(LabEntity.class, result);
	}

}
