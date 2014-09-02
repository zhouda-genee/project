/**
 * 
 */
package com.genee.web.module.service.statistics;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.genee.web.module.pojo.IndexEntity;
import com.genee.web.module.pojo.IndexTypeEntity;
import com.genee.web.module.pojo.RoleEntity;

/**
 * @author yanan.che 2014年8月13日
 */
public interface IndexService {
	/**
	 * 查询以类型划分的所有指标明细
	 * @return
	 */
	public List<IndexTypeEntity> searchIndexDetailByType();
	
	/**
	 * 查询某一角色的指标明细
	 * @param roleId
	 * @return
	 */
	public RoleEntity searchIndexDetailByRole(int roleId);

	/**
	 * 更新指标角色关系表（先删除后添加）
	 * @param roleId indexRoleEntities
	 */
	public void updateIndexRoleRelation(String[] arrCkb, int roleId);
	
}
