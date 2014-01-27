package com.vipshop.microscope.sample.dao;

import java.util.List;

import com.vipshop.microscope.sample.domain.User;


public interface UserDao {

	public void insert(User user);

	public List<User> find(User user);

	public void update(User user);

	public void delete(User user);

}
