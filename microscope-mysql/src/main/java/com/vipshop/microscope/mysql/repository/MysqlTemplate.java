package com.vipshop.microscope.mysql.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vipshop.microscope.mysql.domain.User;

@Repository
public class MysqlTemplate implements MysqlOperations {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void save() {
		User user = new User();
		user.setName("xufei");
		user.setPassword("xufei");
		
	}
	
	
}
