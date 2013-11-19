package com.vipshop.microscope.test.app.dao;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vipshop.microscope.test.app.domain.User;

public class DaoFactory {
	
	public static final MyBATISUserDao USER;
	
	static {
		
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-database.xml", DaoFactory.class);
		
		USER = context.getBean(MyBATISUserDao.class);
		
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
		System.out.println(USER.find());
		USER.update();
	}
}
