package com.genee.web.module.service.statistics;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genee.web.module.dao.EquipmentDao;
import com.genee.web.module.dao.LabDao;
import com.genee.web.module.dao.RoleDao;
import com.genee.web.module.pojo.EquipmentEntity;
import com.genee.web.module.pojo.IndexEntity;
import com.genee.web.module.pojo.IndexTypeEntity;
import com.genee.web.module.pojo.LabEntity;
import com.genee.web.module.pojo.RoleInfoEntity;

@Service("userservice")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private LabDao labDao;
	
	@Autowired
	private EquipmentDao equipmentDao;

	@Override
	public String queryRoleByUser(String token) {
		
		String roleIds = "";
		int userId = roleDao.queryUserIdByToken(token);
		// 判断是否是校级管理员
		// {"perms":{"管理所有内容":"on","管理组织机构":"on"}}
		List<RoleInfoEntity> roles = roleDao.queryRoleByUser(userId);
		for (RoleInfoEntity roleInfoEntity : roles) {
			String extra = roleInfoEntity.getRoleExtra();
			JSONObject obj = JSONObject.fromObject(extra).getJSONObject("perms");
			if (obj.get("管理所有内容") != null && obj.get("管理组织机构") != null){
				return "1";
			}
		}
		// 判断是否是课题组PI
		LabEntity labEntity = labDao.queryLabByUser(userId);
		if (labEntity.getLabOwner() == userId){
			roleIds += "2,";
		}
		// 判断是否是仪器管理员
		List<EquipmentEntity> equipmentEntity = equipmentDao.queryEquipmentByUser(userId, "incharge");
		if (equipmentEntity.size() > 0){
			roleIds += "3,";
		}
		if (roleIds.lastIndexOf(",") > 0)
			roleIds = roleIds.substring(0, roleIds.length() - 1);
		return roleIds;
	}

	@Override
	public List<IndexTypeEntity> queryIndexTypeEntityByRole(String roleId) {
		return roleDao.queryIndexTypeEntityByRole(roleId);
	}

	@Override
	public List<IndexEntity> queryIndexEntityByRole(String roleId) {
		return roleDao.queryIndexEntityByRole(roleId);
	}

}
