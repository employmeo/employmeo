package com.employmeo.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.employmeo.objects.Respondant;


public class EmailUtility {

	private static final String INVITE_TEMPLATE_ID = "ea059aa6-bac6-41e0-821d-98dc4dbfc31d";
	private static final String RESULTS_TEMPLATE_ID = "8e5983ac-913d-4370-8ea9-312ff8665f39";
	private static final ExecutorService TASK_EXECUTOR = Executors.newCachedThreadPool();
	private static String SG_API = System.getenv("SENDGRID_API");
	private static final String END_POINT = "https://api.sendgrid.com/v3/mail/send";
	private static JSONObject FROM_ADDRESS = new JSONObject().put("email", "info@employmeo.com");
	private static Logger logger = Logger.getLogger("com.employmeo.util.EmailUtility");
	
	public static void sendMessage(String from, String to, String subject, String content, StringBuffer htmlpart) {
		
		JSONObject email = new JSONObject();
		email.put("from", new JSONObject().put("email",from));
		email.put("subject", "Invitation To Apply");
		email.accumulate("content", new JSONObject("{\"type\":\"text/plain\"}").put("value", content));
		if (htmlpart != null) {
			email.accumulate("content", new JSONObject("{\"type\":\"text/html\"}").put("value", htmlpart.toString()));
		} else {
			email.accumulate("content", new JSONObject("{\"type\":\"text/html\"}").put("value", content));			
		}
		JSONObject pers = new JSONObject();
		JSONArray toAddrs = new JSONArray();
		toAddrs.put(new JSONObject().put("email",to));
		pers.put("to", toAddrs);
		email.put("personalizations", new JSONArray().put(pers));

		asynchSend(email);
	
		return;
	}

	public static void sendEmailInvitation(Respondant respondant) {

		String link = ExternalLinksUtil.getAssessmentLink(respondant);
		String body = "Dear " + respondant.getPerson().getPersonFname() + ",\n" + "\n"
				+ "Congratulations, we are excited to invite you to complete a preliminary "
				+ "assessment for this position.\nThis assessment can be completed on a "
				+ "mobile device or in a browser at this link: \n" + link;
		
		// TODO: Figure out respondant.getRespondantAccount().getAccountSentbyText());
		
		JSONObject email = new JSONObject();
		email.put("from", FROM_ADDRESS);
		email.put("subject", "Invitation To Apply");
		email.accumulate("content", new JSONObject("{\"type\":\"text/plain\"}").put("value", body));
		email.accumulate("content", new JSONObject("{\"type\":\"text/html\"}").put("value", body));
		email.put("template_id",INVITE_TEMPLATE_ID);
		JSONObject pers = new JSONObject();
		JSONObject custom = new JSONObject();
		JSONArray toAddrs = new JSONArray();
		toAddrs.put(new JSONObject().put("email",respondant.getPerson().getPersonEmail()));
		custom.put("[LINK]", link );
		custom.put("[FNAME]", respondant.getPerson().getPersonFname());
		custom.put("[ACCOUNT_NAME]",respondant.getRespondantAccount().getAccountName());
		pers.put("to", toAddrs);
		pers.put("substitutions", custom);
		email.put("personalizations", new JSONArray().put(pers));

		asynchSend(email);

		return;
	}
	
	public static void sendResults(Respondant respondant) {

		String link = ExternalLinksUtil.getPortalLink(respondant);
		String body = "Dear User,\n" + "\n" + "The assessment for applicant " 
		        + respondant.getPerson().getPersonFullName()
				+ " has been submitted and scored. The results are now available on the portal at:\n"
		        + link + "\n";
	
		JSONObject email = new JSONObject();
		email.put("from", FROM_ADDRESS);
		email.put("subject", "Assessment Results Available: " + respondant.getPerson().getPersonFullName());
		email.accumulate("content", new JSONObject("{\"type\":\"text/plain\"}").put("value", body));
		email.accumulate("content", new JSONObject("{\"type\":\"text/html\"}").put("value", body));
		email.put("template_id",RESULTS_TEMPLATE_ID);
		JSONObject pers = new JSONObject();
		JSONObject custom = new JSONObject();
		JSONArray toAddrs = new JSONArray();
		toAddrs.put(new JSONObject().put("email",respondant.getRespondantEmailRecipient()));
		custom.put("[LINK]", link );
		custom.put("[FULL_NAME]", respondant.getPerson().getPersonFullName());
		custom.put("[ACCOUNT_NAME]",respondant.getRespondantAccount().getAccountName());
		pers.put("to", toAddrs);
		pers.put("substitutions", custom);
		email.put("personalizations", new JSONArray().put(pers));

		asynchSend(email);
	}
	
	public static void asynchSend(JSONObject jMail) {
		TASK_EXECUTOR.submit(new Runnable() {
			@Override
			public void run() {	
				Client client = ClientBuilder.newClient();
				WebTarget target = client.target(END_POINT);
				javax.ws.rs.core.Response resp = target.request().header("Authorization", "Bearer " + SG_API)
						.post(Entity.entity(jMail.toString(), MediaType.APPLICATION_JSON));		
						logger.info("Sent Email: " + resp.getStatus() + " " + resp.getStatusInfo().getReasonPhrase());
						// TODO handle errors, etc.
			}
		});
	}
	
}