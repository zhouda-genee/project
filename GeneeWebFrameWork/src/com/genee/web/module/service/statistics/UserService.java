package com.genee.web.module.service.statistics;

import java.util.List;

import com.genee.web.module.pojo.IndexEntity;
import com.genee.web.module.pojo.IndexTypeEntity;

public interface UserService {

	/**
	 * 
	 * @Title: queryRoleByUser
	 * @Description: 查找用户角色，如果该用户同时多个角色，按照优先级顺序 1、如果同时包含校级管理员角色，则角色为1
	 *               2、如果同时包含课题组PI和仪器负责人两个角色，则角色为2,3 3、如果仅包含课题组PI，则角色为2
	 *               4、如果仅包含仪器负责人，则角色为3
	 * @param @param token
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String queryRoleByUser(String token);

	/**
	 * 
	 * @Title: queryIndexTypeEntityByRole
	 * @Description: 根据角色查找对应的所有指标类型
	 * @param roleId
	 *            角色ID
	 * @return List<IndexTypeEntity> 返回指标类型集合
	 * 
	 */
	List<IndexTypeEntity> queryIndexTypeEntityByRole(String roleId);

	/**
	 * 
	 * @Title: queryIndexEntityByRole
	 * @Description: 根据角色查找对应的所有指标
	 * @param roleId
	 *            角色ID
	 * @return List<IndexEntity> 返回指标集合
	 * 
	 */
	List<IndexEntity> queryIndexEntityByRole(String roleId);
}
