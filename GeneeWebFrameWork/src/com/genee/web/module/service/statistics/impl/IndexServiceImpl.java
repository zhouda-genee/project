/**
 * 
 */
package com.genee.web.module.service.statistics.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genee.web.module.dao.IndexDao;
import com.genee.web.module.pojo.IndexEntity;
import com.genee.web.module.pojo.IndexTypeEntity;
import com.genee.web.module.pojo.RoleEntity;
import com.genee.web.module.service.statistics.IndexService;

/**
 * @author yanan.che 2014年8月13日
 */

@Service
public class IndexServiceImpl implements IndexService {
	@Autowired
	private IndexDao indexDao;
	
	@Override
	public List<IndexEntity> searchAllIndexDetail() {
		return indexDao.searchAllIndexDetail();
	}
	
	@Override
	public List<IndexTypeEntity> searchAllIndexTypeDetail() {
		return indexDao.searchAllIndexTypeDetail();
	}
	
	@Override
	public List<RoleEntity> searchAllRoleDetail() {
		return indexDao.searchAllRoleDetail();
	}
	
	@Override
	public List<IndexTypeEntity> searchIndexDetailByType() {
		List<IndexTypeEntity> types = indexDao.searchAllIndexTypeDetail();
		for (IndexTypeEntity type : types) {
			List<IndexEntity> indexs = indexDao.searchIndexDetailByType(type.gettId());
			type.setIndexs(indexs);
		}
		return types;
	}
	
	@Override
	public List<RoleEntity> searchIndexDetailByRole() {
		List<RoleEntity> roles = indexDao.searchAllRoleDetail();
		for (RoleEntity role : roles) {
			List<IndexEntity> indexs = indexDao.searchIndexDetailByRole(role.getrId());
			role.setIndexs(indexs);
		}
		return roles;
	}
	
	@Override
	public IndexTypeEntity searchIndexDetailByType(int typeId) {
		IndexTypeEntity indexTypeEntity = new IndexTypeEntity();
		indexTypeEntity = indexDao.searchIndexType(typeId);
		indexTypeEntity.setIndexs(indexDao.searchIndexDetailByType(typeId));
		return indexTypeEntity;
	}

	@Override
	public RoleEntity searchIndexDetailByRole(int roleId) {
		RoleEntity roleEntity = new RoleEntity();
		roleEntity = indexDao.searchRole(roleId);
		roleEntity.setIndexs(indexDao.searchIndexDetailByRole(roleId));
		return roleEntity;
	}

	@Override
	public void updateIndexRoleRelation(String[] arrCkb, int roleId) {
		indexDao.deleteIndexRoleRelation(roleId);
		indexDao.addIndexRoleRelation(arrCkb, roleId);		
	}

}
