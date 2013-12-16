package com.vipshop.microscope.sample.service.impl;

import java.util.List;

import com.vipshop.microscope.sample.dao.DaoFactory;
import com.vipshop.microscope.sample.domain.User;
import com.vipshop.microscope.sample.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public void insert(User user) {
		DaoFactory.USER.insert(user);
		
	}

	@Override
	public List<User> find() {
		return DaoFactory.USER.find();
	}

	@Override
	public void update() {
		DaoFactory.USER.update();
	}

	@Override
	public void delete() {
		DaoFactory.USER.delete();
	}

}
