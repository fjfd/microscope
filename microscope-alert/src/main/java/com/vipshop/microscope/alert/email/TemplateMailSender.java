package com.vipshop.microscope.alert.email;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateMailSender extends MailSender{
	
	@Override
	public void sendMail() throws Exception{
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
		messageHelper.setText(getEmailContent(), true);

		File file = new File(mailSenderInfo.getFilepath());
		if (file.exists()) {
			FileSystemResource fileAdd = new FileSystemResource(file);
			messageHelper.addAttachment(MimeUtility.encodeWord(fileAdd.getFilename()), fileAdd);
		}

		javaMailSender.send(mailMessage);
	}
	
	private String getEmailContent() {
		
		try {
			Template template = freemarkerConfiguration.getConfiguration().getTemplate("mail.ftl");

			Map<String, String> map = new HashMap<String, String>();
			map.put("user", "Andy");
			map.put("currentDate", new Date().toString());

			String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
			return content;

		} catch (TemplateException e) {
			logger.error("Error while processing FreeMarker template ", e);
		} catch (FileNotFoundException e) {
			logger.error("Error while open template file ", e);
		} catch (IOException e) {
			logger.error("Error while generate Email Content ", e);
		}
		return "";
	}

}
