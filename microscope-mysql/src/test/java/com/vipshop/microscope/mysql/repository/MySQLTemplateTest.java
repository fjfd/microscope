package com.vipshop.microscope.mysql.repository;

import org.testng.annotations.Test;

public class MySQLTemplateTest {
	
	MySQLTemplate mySQLTemplate = new MySQLTemplate();
	
	@Test
	public void testCreate() {
		String sql = "CREATE TABLE customer (id BIGINT IDENTITY PRIMARY KEY, first_name VARCHAR(255),last_name VARCHAR(255),email_address VARCHAR(255))";
		mySQLTemplate.create(sql);
	}
}
