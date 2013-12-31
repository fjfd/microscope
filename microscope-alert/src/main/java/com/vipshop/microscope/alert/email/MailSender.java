package com.vipshop.microscope.alert.email;

import org.apache.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

public abstract class MailSender {
	
	protected static Logger logger = Logger.getLogger(MailSender.class);
	
	protected JavaMailSenderImpl javaMailSender;

	public void setJavaMailSender(JavaMailSenderImpl javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	protected MailSenderInfo mailSenderInfo;

	public void setMailSenderInfo(MailSenderInfo mailSenderInfo) {
		this.mailSenderInfo = mailSenderInfo;
	}
	
	protected SimpleMailMessage simpleMailMessage;

	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}
	
	protected FreeMarkerConfigurer freemarkerConfiguration;

	public void setFreemarkerConfiguration(FreeMarkerConfigurer freemarkerConfiguration) {
		this.freemarkerConfiguration = freemarkerConfiguration;
	}

	public abstract void sendMail() throws Exception;

}
