package com.vipshop.microscope.sample.dao;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.vipshop.micorscope.framework.span.Category;
import com.vipshop.microscope.sample.domain.Person;
import com.vipshop.microscope.trace.Tracer;

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
	public List<Person> find() {
		return hibernateTemplate.find("from Person as person");
	}

	public void update(Person user) {
		hibernateTemplate.update(user);
		
	}

	public void delete(Person user) {
		hibernateTemplate.delete(user);
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		Tracer.clientSend("hibernate", Category.Method);
		HibernateUserDao dao = new HibernateUserDao();
		Person person = new Person();
		person.setEmail("xufei@vipshop.com");
		person.setPassword("test");
		person.setUsername("xufei");
		dao.insert(person);
		
		dao.update(person);
		dao.delete(person);
		
		Tracer.clientReceive();
		
		TimeUnit.SECONDS.sleep(10);
	}

}
