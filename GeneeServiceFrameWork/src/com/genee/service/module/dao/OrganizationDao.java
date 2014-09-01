package com.genee.service.module.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.genee.service.framework.core.base.dao.BaseDao;
import com.genee.service.module.constants.TagKey;
import com.genee.service.module.pojo.TagEntity;

@Repository
public class OrganizationDao extends TagDao {

	@Autowired
	@Qualifier("basedao")
	private BaseDao baseDao;
	
	/**
	 * 查询组织机构的根节点
	 * 
	 * @param key
	 *            组织机构在_config表中的键
	 * 
	 * @return 组织机构根节点
	 * 
	 * @throws IllegalAccessException
	 * 
	 * @see TagEntity
	 */
	public TagEntity queryRootOrganization() {
		String identity = this
				.queryRootTagIdentityByKey(TagKey.TAG_GROUP_ID);
		TagEntity entity = this.queryRootTag(Long.parseLong(identity));
		return entity;
	}

}
