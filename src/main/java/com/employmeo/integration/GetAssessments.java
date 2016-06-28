package com.employmeo.integration;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.json.JSONArray;
import org.json.JSONObject;

import com.employmeo.objects.Account;
import com.employmeo.objects.AccountSurvey;
import com.employmeo.objects.Partner;
import com.employmeo.util.PartnerUtil;

import java.util.List;
import java.util.logging.Logger;

@Path("getassessments")
public class GetAssessments {

	private final Response MISSING_REQUIRED_PARAMS = Response.status(Response.Status.BAD_REQUEST)
			.entity("{ message: 'Missing Required Parameters' }").build();
	private static Logger logger = Logger.getLogger("com.employmeo.integration");
	@Context
	private SecurityContext sc;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String doPost(JSONObject json) {
		logger.info("processing with: " + json.toString());
		PartnerUtil pu = ((Partner) sc.getUserPrincipal()).getPartnerUtil();
		Account account = null;

		try { // the required parameters
			account = pu.getAccountFrom(json.getJSONObject("account"));
		} catch (Exception e) {
			throw new WebApplicationException(e, MISSING_REQUIRED_PARAMS);
		}

		JSONArray response = new JSONArray();

		if (account.getSurveys().size() > 0) {
			List<AccountSurvey> surveys = account.getAccountSurveys();
			for (int i = 0; i < surveys.size(); i++) {
				JSONObject survey = new JSONObject();
				survey.put("assessment_name", surveys.get(i).getSurveyName());
				survey.put("assessment_asid", surveys.get(i).getAsId());
				response.put(survey);
			}
		}
		return response.toString();
	}
}