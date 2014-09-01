package com.genee.service.module.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.genee.service.framework.core.base.JdbcTemplateParam;
import com.genee.service.framework.core.base.dao.BaseDao;
import com.genee.service.framework.utils.map.MapToBeanUtil;
import com.genee.service.framework.utils.php.PHPSerializer;
import com.genee.service.module.pojo.TagEntity;

@Repository
public class TagDao {

	@Autowired
	@Qualifier("basedao")
	private BaseDao baseDao;

	/**
	 * 查询根节点对应的ID
	 * 
	 * @return 根节点对应的ID
	 * @throws IllegalAccessException
	 * 
	 */
	public String queryRootTagIdentityByKey(String key) {
		try {
			String sql = "select c.val from _config c where c.key = ?";
			JdbcTemplateParam param = new JdbcTemplateParam(sql,
					new Object[] { key }, new int[] { java.sql.Types.VARCHAR });
			Map<String, Object> result = baseDao.queryForMap(param);

			if (result.size() > 0) {
				return PHPSerializer.unserialize(
						result.get("val").toString().getBytes()).toString();
			} else {
				return null;
			}
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 查询Tag的根节点
	 * 
	 * @param id
	 *            tagid
	 * 
	 * @return Tag的根节点
	 * 
	 * @see TagEntity
	 */
	public TagEntity queryRootTag(long id) {
		String sql = "select tp.id, tp.name, count(tc.id) as ccount from tag tp "
				+ "left join tag tc on tp.id = tc.parent_id "
				+ "where tp.id = ? "
				+ "group by tp.id, tp.name "
				+ "order by tp.id";
		JdbcTemplateParam param = new JdbcTemplateParam(sql,
				new Object[] { id }, new int[] { java.sql.Types.BIGINT });
		return MapToBeanUtil.MapToBean(TagEntity.class,
				baseDao.queryForMap(param));
	}

	/**
	 * 根据ID查询所有该Tag节点下的子节点
	 * 
	 * @param id
	 *            tagid
	 * 
	 * @return 该Tag节点下的所有子节点
	 * 
	 * @see TagEntity
	 */
	public List<TagEntity> queryChildTag(long id) {
		String sql = "select tp.id, tp.name, count(tc.id) as ccount from tag tp "
				+ "left join tag tc on tp.id = tc.parent_id "
				+ "where tp.parent_id = ? "
				+ "group by tp.id, tp.name "
				+ "order by tp.id";
		JdbcTemplateParam param = new JdbcTemplateParam(sql,
				new Object[] { id }, new int[] { java.sql.Types.BIGINT });
		return MapToBeanUtil.MapToBean(TagEntity.class,
				baseDao.queryForList(param));
	}
}
