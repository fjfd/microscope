package com.vipshop.microscope.test.app.dao;

import java.util.List;

import com.vipshop.microscope.test.app.domain.User;

public interface MyBATISUserDao {

	public void insert(User user);

	public List<User> find();

	public void update();

	public void delete();

}
