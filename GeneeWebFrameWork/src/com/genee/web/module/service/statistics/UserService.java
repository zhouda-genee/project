package com.genee.web.module.service.statistics;

public interface UserService {
	
	/**
	 * 
	 * @Title: queryRoleByUser 
	 * @Description: 查找用户角色，如果该用户同时多个角色，按照优先级顺序
	 * 				 1、如果同时包含校级管理员角色，则角色为1
	 * 				 2、如果同时包含课题组PI和仪器负责人两个角色，则角色为2,3
	 * 				 3、如果仅包含课题组PI，则角色为2
	 * 				 4、如果仅包含仪器负责人，则角色为3
	 * @param @param userId
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String queryRoleByUser(int userId);

}
