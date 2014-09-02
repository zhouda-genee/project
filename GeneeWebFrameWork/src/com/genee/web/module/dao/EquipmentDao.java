package com.genee.web.module.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.genee.web.framework.core.base.JdbcTemplateParam;
import com.genee.web.framework.core.base.dao.BaseDao;
import com.genee.web.framework.utils.map.MapToBeanUtil;
import com.genee.web.module.pojo.EquipmentEntity;

@Repository
public class EquipmentDao {
	
	@Autowired
	@Qualifier("basedao")
	private BaseDao baseDao;
	
	/**
	 * 
	 * @Title: queryEquipmentByUser 
	 * @Description: 根据用户ID和用户类型查找仪器 
	 * @param @param userId
	 * @param @param type
	 * @param @return
	 * @return List<EquipmentEntity>
	 * @throws
	 */
	public List<EquipmentEntity> queryEquipmentByUser(int userId, String type){
		String sql = "select _e.id as equipmentid, _e.name as equipmentname "
				+ "from _r_user_equipment _r_u_e, equipment _e, user _u "
				+ "where _u.id = ? "
				+ "and _r_u_e.type = ? "
				+ "and _r_u_e.id1 = _u.id "
				+ "and _r_u_e.id2 = _e.id";
		JdbcTemplateParam param = new JdbcTemplateParam(sql, 
					new Object[]{userId, type},
					new int[]{java.sql.Types.INTEGER, java.sql.Types.VARCHAR});
		List<Map<String, Object>> results = baseDao.queryForList(param);
		return MapToBeanUtil.MapToBean(EquipmentEntity.class, results);
	}

}
