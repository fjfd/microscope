package com.vipshop.microscope.mysql.repository;

import org.springframework.jdbc.core.JdbcTemplate;

public class MySQLTemplate {
	
	private JdbcTemplate jdbcTemplate = JdbcTemplateFactory.jdbcTemplate;
	
	public void create(String sql) {
		jdbcTemplate.execute(sql);
	}
	
	public void save() {
	
	}
	
	
}
