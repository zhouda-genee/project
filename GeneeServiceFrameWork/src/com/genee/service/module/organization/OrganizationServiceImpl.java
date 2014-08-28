package com.genee.service.module.organization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.genee.service.module.dao.OrganizationDao;
import com.genee.service.module.pojo.TagEntity;

public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationDao organizationDao;

	@Override
	public TagEntity getRootOrganization() {
		return organizationDao.queryRootOrganization();
	}

	@Override
	public List<TagEntity> getChildOrganization(long id) {
		return organizationDao.queryChildTag(id);
	}
}
