package com.vipshop.microscope.test.app.dao;

import java.util.List;

import com.vipshop.microscope.test.app.domain.User;

public interface UserDao {

	public void insert(User user);

	public List<User> find(User user);

	public void update(User user);

	public void delete(User user);

}
