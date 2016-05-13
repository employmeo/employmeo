package com.employmeo.util;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

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



	public static void sendEmailInvitation(Respondant respondant) {
		// TODO switch over to a maintainable template on sendgrid.
		String link = EmailUtility.getAssessmentLink(respondant);
		String body = "Dear " + respondant.getPerson().getPersonFname() + ",\n" +
		 			"\n" +
		  			"Congratulations, we are excited to invite you to complete a preliminary " +
		  			"assessment for this position.\nThis assessment can be completed on a " + 
		  			"mobile device or in a browser at this link: \n" + link;

		EmailUtility.sendMessage(respondant.getPerson().getPersonEmail(), "Invitation to Apply", body);		  

	}

	public static void sendResults(String emailto, JSONObject applicant) {
		String plink = applicant.getString("portal_link");
		//String rlink = applicant.getString("render_link");

		String body = "Dear User,\n" +
		 			"\n" +
		  			"The assessment for applicant " + applicant.getString("applicant_id") + 
		  			" has been submitted and scored. The results are now available on the portal at:\n" +
		 			plink + "\n";

		EmailUtility.sendMessage(emailto, "Assessment Complete", body);		  
		
	}
	

/****
 * Section to generate external links (uses environment variables)
 */


	public static String getAssessmentLink(Respondant respondant) {
		String link = null;
		try {
			link = new URL(BASE_SURVEY_URL + "/take_assessment.html" + "?&respondant_id=" + respondant.getRespondantId()).toString();
		} catch (Exception e) {
			link = BASE_SURVEY_URL + "/take_assessment.html" + "?&respondant_id=" + respondant.getRespondantId();
		}
		return link.toString();
	}

	public static String getPortalLink(Respondant respondant) {
		String link = null;
		try {
			link = new URL(BASE_SURVEY_URL + "/respondant_score.jsp" + "?&respondant_id=" + respondant.getRespondantId()).toString();
		} catch (Exception e) {
			link = BASE_SURVEY_URL + "/respondant_score.jsp" + "?&respondant_id=" + respondant.getRespondantId();
		}
		return link.toString();
	}

	public static String getRenderLink(JSONObject applicant) {
		String link = null;
		try {
			link = new URL(BASE_SURVEY_URL + "/render.html" + "?&scores=" + applicant.getJSONArray("scores").toString()).toString();
		} catch (Exception e) {
			link = BASE_SURVEY_URL + "/render.html" + "?&scores=" + applicant.getJSONArray("scores").toString();
		}
		return link.toString();
	}

}
