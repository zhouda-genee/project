package com.genee.service.module.service.lab;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.genee.service.module.dao.LabDao;
import com.genee.service.module.pojo.BaseEntity;

public class LabServiceImpl implements LabService {

	@Autowired
	private LabDao labDao;

	@Override
	public List<BaseEntity> getLabByName(String name) {
		return labDao.queryLab(name);
	}
}
