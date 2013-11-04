package com.vipshop.microscope.mysql.template;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTemplateFactory {
	
	public static JdbcTemplate JDBCTEMPLATE;
	
	static {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-jdbc.xml", JdbcTemplateFactory.class);
		JDBCTEMPLATE = context.getBean(JdbcTemplate.class);
		context.close();
	}
	
}
