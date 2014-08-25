package com.genee.service.module.equipment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.genee.service.module.dao.EquipmentDao;
import com.genee.service.module.pojo.BaseEntity;

public class EquipmentServiceImpl implements EquipmentService {

	@Autowired
	private EquipmentDao equipmentDao;

	@Override
	public List<BaseEntity> getEquipmentByName(String name) {
		return equipmentDao.queryLab(name);
	}
}