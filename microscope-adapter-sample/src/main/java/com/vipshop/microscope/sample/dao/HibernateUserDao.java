package com.vipshop.microscope.sample.dao;

import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.vipshop.microscope.sample.domain.Person;
import com.vipshop.microscope.sample.domain.User;

public class HibernateUserDao {
	
	private HibernateTemplate hibernateTemplate;
	
	public HibernateUserDao() {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-sample-database-hibernate.xml", HibernateUserDao.class);
		hibernateTemplate = context.getBean(HibernateTemplate.class);
		context.close();
	}
	
	public void insert(Person person) {
		hibernateTemplate.save(person);
	}

	@SuppressWarnings("unchecked")
	public List<User> find() {
		return hibernateTemplate.find("from Person as person");
	}

	public void update(User user) {
		// TODO Auto-generated method stub
		
	}

	public void delete(User user) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		HibernateUserDao dao = new HibernateUserDao();
		Person person = new Person();
		person.setEmail("xufei@vipshop.com");
		person.setPassword("test");
		person.setUsername("xufei");
		dao.insert(person);
		
		dao.find();
	}

}
