package com.genee.service.module.organization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.genee.service.module.dao.OrganizationDao;
import com.genee.service.module.pojo.OrganizationEntity;

public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationDao organizationDao;

	@Override
	public OrganizationEntity getRootOrganization() {
		return organizationDao.queryRootOrganization();
	}

	@Override
	public List<OrganizationEntity> getChildOrganization(int id) {
		return organizationDao.queryChildOrganization(id);
	}
}
