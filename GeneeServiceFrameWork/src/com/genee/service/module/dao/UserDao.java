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
public class UserDao {

	@Autowired
	@Qualifier("basedao")
	private BaseDao baseDao;

	/**
	 * 根据负责人姓名模糊查询所有负责人
	 * 
	 * @param name
	 *            负责人姓名
	 * 
	 * @return 负责人列表
	 * 
	 * 
	 * @see BaseEntity
	 */
	public List<BaseEntity> queryIncharge(String name) {
		return this.queryUserByType(name, "incharge");
	}

	/**
	 * 根据联系人姓名模糊查询所有联系人
	 * 
	 * @param name
	 *            联系人姓名
	 * 
	 * @return 联系人列表
	 * 
	 * 
	 * @see BaseEntity
	 */
	public List<BaseEntity> queryContact(String name) {
		return this.queryUserByType(name, "contact");
	}

	/**
	 * 根据使用者姓名模糊查询使用者
	 * 
	 * @param name
	 *            使用者姓名
	 * 
	 * @return 使用者列表
	 * 
	 * 
	 * @see BaseEntity
	 */
	public List<BaseEntity> queryUserInRecord(String name) {
		String sql = "select distinct u.id, u.name from eq_record er "
				+ "inner join user u on er.user_id = u.id where u.name like ? "
				+ "order by u.id";
		JdbcTemplateParam param = new JdbcTemplateParam(sql, new Object[] { "%"
				+ name + "%" }, new int[] { java.sql.Types.VARCHAR });
		return MapToBeanUtil.MapToBean(BaseEntity.class,
				baseDao.queryForList(param));
	}

	/**
	 * 私有方法，根据名称模糊查询负责人和联系人的公共方法
	 * 
	 * @param name
	 *            姓名
	 * 
	 * @param type
	 *            人员类型
	 * 
	 * @return 人员列表
	 * 
	 * 
	 * @see BaseEntity
	 */
	private List<BaseEntity> queryUserByType(String name, String type) {
		String sql = "select distinct u.id, u.name from _r_user_equipment ue "
				+ "inner join user u on ue.id1 = u.id where ue.type = ? and u.name like ? "
				+ "order by u.id";
		JdbcTemplateParam param = new JdbcTemplateParam(sql, new Object[] {
				type, "%" + name + "%" }, new int[] { java.sql.Types.VARCHAR,
				java.sql.Types.VARCHAR });
		return MapToBeanUtil.MapToBean(BaseEntity.class,
				baseDao.queryForList(param));
	}
}
