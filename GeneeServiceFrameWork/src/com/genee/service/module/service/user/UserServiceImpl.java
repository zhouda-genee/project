package com.genee.service.module.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.genee.service.module.dao.UserDao;
import com.genee.service.module.pojo.BaseEntity;

public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public List<BaseEntity> getContact(String name) {
		return userDao.queryContact(name);
	}

	@Override
	public List<BaseEntity> getIncharge(String name) {
		return userDao.queryIncharge(name);
	}

	@Override
	public List<BaseEntity> getUserInRecord(String name) {
		return userDao.queryUserInRecord(name);
	}
}
