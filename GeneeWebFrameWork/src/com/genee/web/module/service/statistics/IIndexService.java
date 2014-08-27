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

@Transactional
public interface IIndexService {
	/**
	 * 查询指标基本信息表中的所有记录	
	 * @return
	 */
	public List<IndexEntity> searchAllIndexDetail();
		
	/**
	 * 查询指标类型表中的所有记录
	 * @return
	 */
	public List<IndexTypeEntity> searchAllIndexTypeDetail();
	
	/**
	 * 查询指标角色表中的所有记录
	 * @return
	 */
	public List<RoleEntity> searchAllRoleDetail();
		
	/**
	 * 查询以类型划分的所有指标明细
	 * @return
	 */
	public List<IndexTypeEntity> searchIndexDetailByType();
	
	/**
	 * 查询以角色划分的所有指标明细
	 * @return
	 */
	public List<RoleEntity> searchIndexDetailByRole();
	
	/**
	 * 查询某一类指标的指标明细
	 * @param typeId
	 * @return
	 */
	public IndexTypeEntity searchIndexDetailByType(int typeId);
	
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
