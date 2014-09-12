package com.genee.web.module.service.statistics;

import java.util.List;

import com.genee.web.module.pojo.IndexEntity;
import com.genee.web.module.pojo.IndexTypeEntity;
import com.genee.web.module.pojo.UserEntity;

public interface UserService {
	
	/**
	 * 
	 * @Title: queryLabByUser 
	 * @Description: 根据课题组负责人查找课题组 
	 * @param @param userId
	 * @param @return
	 * @return int
	 * @throws
	 */
	public int queryLabByUser(int userId);
	
	/**
	 * 
	 * @Title: queryUserByToken 
	 * @Description: 根据token查找用户ID
	 * @param @param token
	 * @param @return
	 * @return int
	 * @throws
	 */
	public UserEntity queryUserByToken(String token);

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
	public String queryRoleByUser(int userId);

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
