package com.vipshop.microscope.alert.email;

import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class SimpleMailSender extends MailSender{
	
	@Override
	public void sendMail() {
		javaMailSender.setHost(mailSenderInfo.getServerHost());
		javaMailSender.setPort(mailSenderInfo.getServerPort());
		javaMailSender.setUsername(mailSenderInfo.getUserName());
		javaMailSender.setPassword(mailSenderInfo.getPassword());

		simpleMailMessage.setTo(mailSenderInfo.getToAddress());
		simpleMailMessage.setFrom(mailSenderInfo.getFromAddress());
		simpleMailMessage.setSubject(mailSenderInfo.getSubject());
		simpleMailMessage.setText(mailSenderInfo.getText());

		Properties prop = new Properties();
		prop.put("mail.smtp.auth", mailSenderInfo.isValidate());
		prop.put("mail.smtp.timeout", mailSenderInfo.getTimeout());

		javaMailSender.setJavaMailProperties(prop);
		
		javaMailSender.send(simpleMailMessage);
	}

}
