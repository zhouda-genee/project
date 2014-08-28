package com.genee.service.module.equipment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.genee.service.module.dao.EquipmentDao;
import com.genee.service.module.pojo.BaseEntity;
import com.genee.service.module.pojo.TagEntity;

public class EquipmentServiceImpl implements EquipmentService {

	@Autowired
	private EquipmentDao equipmentDao;

	@Override
	public List<BaseEntity> getEquipmentByName(String name) {
		return equipmentDao.queryEquipment(name);
	}
	
	@Override
	public TagEntity getRootEquipment() {
		return equipmentDao.queryRootEquipment();
	}

	@Override
	public List<TagEntity> getChildEquipment(long id) {
		return equipmentDao.queryChildTag(id);
	}
}