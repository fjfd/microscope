package com.vipshop.microscope.alert.email;

import java.io.File;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.MimeMessageHelper;

public class HTMLMailSender extends MailSender{
	
	@Override
	public void sendMail() throws Exception {
		javaMailSender.setHost(mailSenderInfo.getServerHost());
		javaMailSender.setPort(mailSenderInfo.getServerPort());
		javaMailSender.setUsername(mailSenderInfo.getUserName());
		javaMailSender.setPassword(mailSenderInfo.getPassword());

		/**
		 * Create Mime mail(contains attach, html format)
		 */
		MimeMessage mailMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");

		messageHelper.setFrom(mailSenderInfo.getFromAddress());
		messageHelper.setTo(mailSenderInfo.getToAddress());
		messageHelper.setSubject(mailSenderInfo.getSubject());
		/**
		 * true means html format
		 */
		messageHelper.setText("<html><head></head><body><h1>hello!</h1></body></html>", true);

		File file = new File(mailSenderInfo.getFilepath());
		if (file.exists()) {
			FileSystemResource fileAdd = new FileSystemResource(file);
			messageHelper.addAttachment(MimeUtility.encodeWord(fileAdd.getFilename()), fileAdd);
		}
		
		javaMailSender.send(mailMessage);
	}
	
	

}
