package com.vipshop.microscope.sample.dao;

import java.util.List;

import com.vipshop.microscope.sample.domain.User;

public interface MyBatisUserDao {

	public void insert(User user);

	public List<User> find();

	public void update();

	public void delete();

}
