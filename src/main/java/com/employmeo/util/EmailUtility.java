package com.employmeo.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.sendgrid.*;

public class EmailUtility {


	public static final String FROM_ADDRESS = "info@employmeo.com";	
	private static Logger logger = Logger.getLogger("EmailUtility");
	
	public static SendGrid getSendGrid() {

		return new SendGrid(System.getenv("SENDGRID_USERNAME"),System.getenv("SENDGRID_PASSWORD"));
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
		SendGrid sendGrid = getSendGrid();
		SendGrid.Email email = new SendGrid.Email();
	    email.setFrom(from);
	    email.addTo(to);
	    email.setSubject(subject);
	    email.setText(content);
	    
	    if (htmlpart !=null) {
	    	email.setHtml(htmlpart.toString());
	    }

		try {
			SendGrid.Response response = sendGrid.send(email);
			logger.log(Level.INFO, "Sent: " + email.getSubject() + "(RC: " + response.getCode() + ")");

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error sending email", e);
		}
		
		return;
	}
		
}
