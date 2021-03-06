/**
 * 
 */
package com.genee.web.module.service.statistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genee.web.module.dao.IndexDao;
import com.genee.web.module.pojo.IndexEntity;
import com.genee.web.module.pojo.IndexTypeEntity;
import com.genee.web.module.pojo.RoleEntity;

/**
 * @author yanan.che 2014年8月13日
 */

@Service
public class IndexServiceImpl implements IndexService {
	@Autowired
	private IndexDao indexDao;
	
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
	public RoleEntity searchIndexDetailByRole(int roleId) {
		RoleEntity roleEntity = new RoleEntity();
		roleEntity = indexDao.searchRole(roleId);
		List<IndexEntity> indexsList = indexDao.searchIndexDetailByRole(roleId);
		roleEntity.setIndexs(indexsList);
		return roleEntity;
	}

	@Override
	public void updateIndexRoleRelation(int[] arrCkb, int rId) {
		indexDao.deleteIndexRoleRelation(rId);
		for (int i = 0; i < arrCkb.length; i++) {
			indexDao.addIndexRoleRelation(arrCkb[i], rId);
		}
		
	}

}
