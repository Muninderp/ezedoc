package org.dbs.mydoc.service;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

	@Autowired
	private JavaMailSender emailSender;

	private static Logger logger = Logger.getLogger(EmailSender.class);

	public void sendEmail(String toMailId, String subject, String text) throws Exception {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setFrom("amrendrasing.as@gmail.com");
			helper.setTo(toMailId);
			helper.setText(text);
			helper.setSubject(subject);

			emailSender.send(message);
		} catch (Exception e) {
			logger.error("Error in Sending email  " + e);
			throw e;
		}

	}

}
