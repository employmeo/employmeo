package com.employmeo.util;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtility {


	public static final String FROM_ADDRESS = "info@employmeo.com";
	private static final String SMTP_HOST = System.getenv("SMTP_HOST"); //xo7.x10hosting.com;
	private static final String SMTP_PASSWORD  = System.getenv("SMTP_PASSWORD"); //employmeo;
	private static final int SMTP_PORT = Integer.valueOf(System.getenv("SMTP_PORT")); //465;
	public static final String SMTP_USERNAME = System.getenv("SMTP_USERNAME"); //info@employmeo.com; 
	
	private static Logger logger = Logger.getLogger("EmailUtility");
	
	public static Session getSession() {
		Properties props = System.getProperties();

		props.put("mail.smtp.host", SMTP_HOST);
    	props.put("mail.smtp.user", SMTP_USERNAME);
    	props.put("mail.smtp.password", SMTP_PASSWORD);
    	props.put("mail.smtp.port", SMTP_PORT);
    	
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.ssl.enable", "true");

		return Session.getDefaultInstance(props, new Authenticator() { @Override
		protected PasswordAuthentication getPasswordAuthentication() { return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD); }});

	}

	public static void sendMessage(String to, String subject, String content) {
		sendMessage(FROM_ADDRESS, to, subject, content, null);
		return;
	}

	
	public static void sendMessage(String from, String to, String subject, String content) {
		sendMessage(from, to, subject, content, null);
		return;
	}
	
	public static void sendMessage(String to, String subject, String content, EmailServletResponse htmlpart) {
		sendMessage(FROM_ADDRESS, to, subject, content, htmlpart);
		return;
	}
	
	public static void sendMessage(String from, String to, String subject, String content, EmailServletResponse htmlpart) {
		Session session = getSession();
		Message message = new MimeMessage(session);
		Multipart multiPart = new MimeMultipart("alternative");

		try {
		    MimeBodyPart text = new MimeBodyPart();
		    text.setText(content, "utf-8");
	    	MimeBodyPart html = new MimeBodyPart();
		    
		    if (htmlpart !=null) {
		    	html.setContent(htmlpart.toString(), "text/html; charset=utf-8");
		    } else {
		    	html.setContent(content, "text/html; charset=utf-8");
		    }
		    multiPart.addBodyPart(text);
		    multiPart.addBodyPart(html);
		    
			message.setFrom(new InternetAddress(from));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setRecipient(Message.RecipientType.CC, new InternetAddress(from));
			message.setSubject(subject);
			message.setContent(multiPart);
			
			Transport.send(message);
			logger.log(Level.INFO, "Sent Email: " + message.getSubject());

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error sending email", e);
		}
		
		return;
	}
		
}
