package com.employmeo.util;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.employmeo.objects.Respondant;
import com.sendgrid.*;


public class EmailUtility {

	private static final ExecutorService TASK_EXECUTOR = Executors.newCachedThreadPool();
	public static final String FROM_ADDRESS = "info@employmeo.com";	
	private static Logger logger = Logger.getLogger("EmailUtility");
	private static String SG_USER = System.getenv("SENDGRID_USERNAME");
	private static String SG_PASS = System.getenv("SENDGRID_PASSWORD");
	private static String BASE_SURVEY_URL = System.getenv("BASE_SURVEY_URL");
	
	public static SendGrid getSendGrid() {
		return new SendGrid(SG_USER,SG_PASS);
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
		TASK_EXECUTOR.submit(new Runnable() {
			@Override
			public void run() {
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
					// TODO - log and plan to resubmit email on failures.
					logger.log(Level.SEVERE, "Error sending email", e);
				}
			}
		});
		return;
	}

	public static String getAssessmentLink(Respondant respondant) {
		String link = null;
		try {
			link = new URL(BASE_SURVEY_URL + "/take_assessment.html" + "?&respondant_id=" + respondant.getRespondantId()).toString();
		} catch (Exception e) {
			link = BASE_SURVEY_URL + "/take_assessment.html" + "?&respondant_id=" + respondant.getRespondantId();
		}
		return link.toString();
	}

	
}
