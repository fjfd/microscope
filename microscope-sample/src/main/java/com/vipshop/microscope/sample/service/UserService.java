package com.vipshop.microscope.sample.service;

import java.util.List;

import com.vipshop.microscope.sample.domain.User;


public interface UserService {
	
	public void insert(User user);
	
	public List<User> find();
	
	public void update();
	
	public void delete();
}
