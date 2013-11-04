package com.vipshop.microscope.mysql.repository;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTemplateFactory {
	
	public static JdbcTemplate jdbcTemplate;
	
	static {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-jdbc.xml", JdbcTemplateFactory.class);
		jdbcTemplate = context.getBean(JdbcTemplate.class);
		context.close();
	}
	
}
