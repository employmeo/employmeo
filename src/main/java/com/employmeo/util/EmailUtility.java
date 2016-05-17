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

	public static final String FROM_ADDRESS = "info@employmeo.com";
	private static final String INVITE_TEMPLATE_ID = "ea059aa6-bac6-41e0-821d-98dc4dbfc31d";
	private static final String RESULTS_TEMPLATE_ID = "8e5983ac-913d-4370-8ea9-312ff8665f39";
	private static final ExecutorService TASK_EXECUTOR = Executors.newCachedThreadPool();

	private static Logger logger = Logger.getLogger("EmailUtility");
	private static String SG_USER = System.getenv("SENDGRID_USERNAME");
	private static String SG_PASS = System.getenv("SENDGRID_PASSWORD");
	private static String BASE_SURVEY_URL = System.getenv("BASE_SURVEY_URL");

	public static SendGrid getSendGrid() {
		return new SendGrid(SG_USER, SG_PASS);
	}

	public static void sendMessage(String to, String subject, String content) {
		sendMessage(FROM_ADDRESS, to, subject, content, null);
		return;
	}

	public static void sendMessage(String from, String to, String subject, String content) {
		sendMessage(from, to, subject, content, null);
		return;
	}

	public static void sendMessage(String to, String subject, String content, StringBuffer htmlpart) {
		sendMessage(FROM_ADDRESS, to, subject, content, htmlpart);
		return;
	}

	public static void sendMessage(String from, String to, String subject, String content,
			StringBuffer htmlpart) {
		TASK_EXECUTOR.submit(new Runnable() {
			@Override
			public void run() {
				SendGrid sendGrid = getSendGrid();
				SendGrid.Email email = new SendGrid.Email();
				email.setFrom(from);
				email.addTo(to);
				email.setSubject(subject);
				email.setText(content);

				if (htmlpart != null) {
					email.setHtml(htmlpart.toString());
				}

				try {
					SendGrid.Response response = sendGrid.send(email);
					logger.info("Sent: " + email.getSubject() + "(RC: " + response.getCode() + ") "
							+ response.getMessage());
				} catch (Exception e) {
					// TODO - log and plan to resubmit email on failures.
					logger.log(Level.SEVERE, "Error sending email", e);
				}
			}
		});
		return;
	}

	public static void sendEmailInvitation(Respondant respondant) {
		SendGrid sendGrid = EmailUtility.getSendGrid();
		SendGrid.Email email = new SendGrid.Email();
		email.setFrom(FROM_ADDRESS);
		email.addTo(respondant.getPerson().getPersonEmail());
		email.setTemplateId(INVITE_TEMPLATE_ID);
		email.setSubject("Invitation to Apply");

		String link = EmailUtility.getAssessmentLink(respondant);
		String body = "Dear " + respondant.getPerson().getPersonFname() + ",\n" + "\n"
				+ "Congratulations, we are excited to invite you to complete a preliminary "
				+ "assessment for this position.\nThis assessment can be completed on a "
				+ "mobile device or in a browser at this link: \n" + link;
		email.setText(body);
		email.setHtml(body);

		email.addSubstitution("[LINK_TO_ASSESSMENT]", new String[] { link });
		email.addSubstitution("[APPLICANT_NAME]", new String[] { respondant.getPerson().getPersonFname() });
		email.addSubstitution("[ACCOUNT_NAME]", new String[] { respondant.getRespondantAccount().getAccountName() });

		TASK_EXECUTOR.submit(new Runnable() {
			@Override
			public void run() {

				try {
					SendGrid.Response response = sendGrid.send(email);
					logger.info("Sent: " + email.getSubject() + "(RC: " + response.getCode() + ") "
							+ response.getMessage());

				} catch (Exception e) {
					// TODO - log and plan to resubmit email on failures.
					logger.severe("Error sending email: " + e.getMessage());
				}
			}
		});
	}

	public static void sendResults(Respondant respondant, JSONObject applicant) {

		SendGrid sendGrid = EmailUtility.getSendGrid();
		SendGrid.Email email = new SendGrid.Email();
		email.setFrom(FROM_ADDRESS);
		email.addTo(respondant.getRespondantEmailRecipient());
		email.setTemplateId(RESULTS_TEMPLATE_ID);
		email.setSubject("Assessment Complete");

		String plink = applicant.getString("portal_link");
		String applicantName = respondant.getPerson().getPersonFname() + " " + respondant.getPerson().getPersonLname();
		String body = "Dear User,\n" + "\n" + "The assessment for applicant " + applicantName
				+ " has been submitted and scored. The results are now available on the portal at:\n" + plink + "\n";
		email.setText(body);
		email.setHtml(body);

		email.addSubstitution("[LINK_TO_RESULTS]", new String[] { plink });
		email.addSubstitution("[APPLICANT_NAME]", new String[] { applicantName });
		email.addSubstitution("[ACCOUNT_NAME]", new String[] { respondant.getRespondantAccount().getAccountName() });

		TASK_EXECUTOR.submit(new Runnable() {
			@Override
			public void run() {

				try {
					SendGrid.Response response = sendGrid.send(email);
					logger.info("Sent: " + email.getSubject() + "(RC: " + response.getCode() + ") "
							+ response.getMessage());

				} catch (Exception e) {
					// TODO - log and plan to resubmit email on failures.
					logger.severe("Error sending email: " + e.getMessage());
				}
			}
		});
	}

	/****
	 * Section to generate external links (uses environment variables)
	 */

	public static String getAssessmentLink(Respondant respondant) {
		String link = null;
		try {
			link = new URL(
					BASE_SURVEY_URL + "/take_assessment.html" + "?&respondant_id=" + respondant.getRespondantId())
							.toString();
		} catch (Exception e) {
			link = BASE_SURVEY_URL + "/take_assessment.html" + "?&respondant_id=" + respondant.getRespondantId();
		}
		return link.toString();
	}

	public static String getPortalLink(Respondant respondant) {
		String link = null;
		try {
			link = new URL(
					BASE_SURVEY_URL + "/respondant_score.jsp" + "?&respondant_id=" + respondant.getRespondantId())
							.toString();
		} catch (Exception e) {
			link = BASE_SURVEY_URL + "/respondant_score.jsp" + "?&respondant_id=" + respondant.getRespondantId();
		}
		return link.toString();
	}

	public static String getRenderLink(JSONObject applicant) {
		String link = null;
		try {
			link = new URL(BASE_SURVEY_URL + "/render.html" + "?&scores=" + applicant.getJSONArray("scores").toString())
					.toString();
		} catch (Exception e) {
			link = BASE_SURVEY_URL + "/render.html" + "?&scores=" + applicant.getJSONArray("scores").toString();
		}
		return link.toString();
	}

}
