package com.employmeo.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;


import com.employmeo.objects.Respondant;
import com.sendgrid.*;

public class EmailUtility {

	public static final String FROM_ADDRESS = "info@employmeo.com";
	private static final String INVITE_TEMPLATE_ID = "ea059aa6-bac6-41e0-821d-98dc4dbfc31d";
	private static final String RESULTS_TEMPLATE_ID = "8e5983ac-913d-4370-8ea9-312ff8665f39";
	private static final ExecutorService TASK_EXECUTOR = Executors.newCachedThreadPool();
	private static Logger logger = Logger.getLogger("EmailUtility");
	private static String SG_API = System.getenv("SENDGRID_API");


	public static SendGrid getSendGrid() {
		return new SendGrid(SG_API);
	}

	public static void sendMessage(String from, String to, String subject, String content, StringBuffer htmlpart) {
		
		Mail email = new Mail(new Email(from), subject, new Email(to), new Content("text/plain", content));
		if (htmlpart != null) {
			Content htmlContent = new Content("text/html", htmlpart.toString());
			email.addContent(htmlContent);
		}	
		asynchSend(email);
		return;
	}

	public static void sendEmailInvitation(Respondant respondant) {

		String link = ExternalLinksUtil.getAssessmentLink(respondant);
		String body = "Dear " + respondant.getPerson().getPersonFname() + ",\n" + "\n"
				+ "Congratulations, we are excited to invite you to complete a preliminary "
				+ "assessment for this position.\nThis assessment can be completed on a "
				+ "mobile device or in a browser at this link: \n" + link;
		
		Email from = new Email("",respondant.getRespondantAccount().getAccountSentbyText());
		from = new Email(FROM_ADDRESS);
		
		Mail email = new Mail(from,
				"Invitation to Apply",
				new Email(respondant.getPerson().getPersonEmail()),
				new Content("text/plain", body));
		email.addContent(new Content("text/html", body));
		email.setTemplateId(INVITE_TEMPLATE_ID);
		Personalization p = new Personalization();
		p.addSubstitution("[LINK_TO_ASSESSMENT]", link );
		p.addSubstitution("[APPLICANT_NAME]", respondant.getPerson().getPersonFname());
		p.addSubstitution("[ACCOUNT_NAME]", respondant.getRespondantAccount().getAccountName());
		email.addPersonalization(p);

		asynchSend(email);
		return;
	}


	
	public static void sendResults(Respondant respondant) {

		String plink = ExternalLinksUtil.getPortalLink(respondant);
		String applicantName = respondant.getPerson().getPersonFname() + " " + respondant.getPerson().getPersonLname();
		String body = "Dear User,\n" + "\n" + "The assessment for applicant " + applicantName
				+ " has been submitted and scored. The results are now available on the portal at:\n" + plink + "\n";

		Mail email = new Mail(new Email(FROM_ADDRESS),
				"Assessment Complete",
				new Email(respondant.getRespondantEmailRecipient()),
				new Content("text/plain", body));
		email.addContent(new Content("text/html", body));
		email.setTemplateId(RESULTS_TEMPLATE_ID);

		Personalization p = new Personalization();

		p.addSubstitution("[LINK_TO_RESULTS]", plink);
		p.addSubstitution("[APPLICANT_NAME]", applicantName);
		p.addSubstitution("[ACCOUNT_NAME]", respondant.getRespondantAccount().getAccountName());
		email.addPersonalization(p);

		asynchSend(email);
	}
	
	public static void asynchSend(Mail email) {
		TASK_EXECUTOR.submit(new Runnable() {
			@Override
			public void run() {
				SendGrid sendGrid = EmailUtility.getSendGrid();
				Request req = new Request();
				try {
					req.method= Method.POST;
					req.endpoint = "mail/send/beta";
					req.body = email.build();
					Response response = sendGrid.api(req);
					logger.info("Sent: " + email.getSubject() + "(RC: " + response.statusCode + ") "
							+ response.body);
				} catch (Exception e) {
					// TODO - log and plan to resubmit email on failures.
					logger.severe("Error sending email: " + e.getMessage());
				}
			}
		});
	}

}
