package com.vipshop.microscope.sample.dao;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vipshop.microscope.sample.domain.User;


public class DaoFactory {
	
	public static final MyBatisUserDao USER;
	
	static {
		
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-sample-database.xml", DaoFactory.class);
		
		USER = context.getBean(MyBatisUserDao.class);
		
		context.close();
	}
	
	public static void main(String[] args) {
		User user = new User();
		
		user.setName("alex");
		user.setGender(1);
		user.setAdress("shanghai-pudong");
		user.setEducation("anhui");
		user.setHight(175);
		user.setWeight(148);
		user.setAge(25);
		
		USER.insert(user);
	}
}
