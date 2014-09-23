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
		String sql = "select l.id, l.name from lab l where l.name like ? limit 0, 10";
		JdbcTemplateParam param = new JdbcTemplateParam(sql, new Object[] { "%"
				+ name + "%" }, new int[] { java.sql.Types.VARCHAR });
		return MapToBeanUtil.MapToBean(BaseEntity.class,
				baseDao.queryForList(param));
	}
	
	/**
	 * 
	 * @Title: queryCreateLab 
	 * @Description: 统计某时间段内新注册的课题组
	 * @param @param startDate
	 * @param @param endDate
	 * @param @param orgIds
	 * @param @return
	 * @return int
	 * @throws
	 */
	public int queryActivateLab(long startDate, long endDate, String orgIds){
		String sql = "select count(l.id) as num "
				+ "from _r_tag_lab _r_u_l, lab l "
				+ "where _r_u_l.id2 = l.id "
				+ "and l.atime between ? and ? "
				+ "and _r_u_l.id1 in (" + orgIds + ")";
		JdbcTemplateParam param = new JdbcTemplateParam(sql, 
				new Object[]{startDate, endDate}, 
				new int[]{java.sql.Types.INTEGER, java.sql.Types.INTEGER});
		return baseDao.queryForInt(param);
	}
}
