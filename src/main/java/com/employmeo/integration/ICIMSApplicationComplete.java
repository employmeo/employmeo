package com.employmeo.integration;

import java.net.URI;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.json.JSONObject;

import com.employmeo.objects.Account;
import com.employmeo.objects.Partner;
import com.employmeo.objects.Respondant;
import com.employmeo.util.EmailUtility;
import com.employmeo.util.PartnerUtil;;

@Path("icimsapplicationcomplete")
public class ICIMSApplicationComplete {

	@Context
	private SecurityContext sc;
	private static Logger logger = Logger.getLogger("ICIMSService");

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doPost(JSONObject json) {
logger.info("ICIMS Posted this: " +json);
	
		PartnerUtil pu = PartnerUtil.getUtilFor((Partner) sc.getUserPrincipal());
		Account account = pu.getAccountFrom(json);
		Respondant applicant = pu.createRespondantFrom(json, account);
		applicant.refreshMe();

		URI link = null;
		try {
			link = new URI(EmailUtility.getAssessmentLink(applicant));
		} catch (Exception e) {
			logger.severe("Failed to URI-ify link: " + EmailUtility.getAssessmentLink(applicant));			
		}
		
		return Response.seeOther(link).build();

	}
}
