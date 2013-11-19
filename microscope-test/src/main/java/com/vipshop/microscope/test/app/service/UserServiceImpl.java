package com.vipshop.microscope.test.app.service;

import java.util.List;

import com.vipshop.microscope.test.app.dao.DaoFactory;
import com.vipshop.microscope.test.app.domain.User;

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
