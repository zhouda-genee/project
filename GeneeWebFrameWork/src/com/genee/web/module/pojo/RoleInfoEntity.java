package com.genee.web.module.pojo;

public class RoleInfoEntity {
	
	// 角色id
	private int roleId;
	
	// 角色名称
	private String roleName;
	
	// 角色权限
	private String roleExtra;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleExtra() {
		return roleExtra;
	}

	public void setRoleExtra(String roleExtra) {
		this.roleExtra = roleExtra;
	}
	
	

}
