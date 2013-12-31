package com.vipshop.microscope.alert.email;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MailTester {
	
	ApplicationContext context;
	MailSender sender;
	
	@BeforeClass
	public void setUp(){
		context = new ClassPathXmlApplicationContext("/applicationContext-*.xml", MailTester.class);
	}
	
	@Test
	public void sendSimpleMail() throws Exception{
		SimpleMailSender sender = context.getBean(SimpleMailSender.class);
		sender.sendMail();
	}
	
	@Test
	public void sendHTMLMail() throws Exception{
		sender = (MailSender) context.getBean("HTMLMailSender");
		sender.sendMail();
	}
	
	@Test
	public void setTemplateMail() throws Exception{
		sender = (MailSender) context.getBean("templateMailSender");
		sender.sendMail();
	}
}
