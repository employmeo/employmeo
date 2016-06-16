package com.employmeo.integration;

import java.util.logging.Logger;

import javax.annotation.security.PermitAll;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.json.JSONObject;

import com.employmeo.objects.Account;
import com.employmeo.objects.Partner;
import com.employmeo.objects.Respondant;
import com.employmeo.util.PartnerUtil;

@Path("getscore")
@PermitAll
public class GetScore {
	private final Response MISSING_REQUIRED_PARAMS = Response.status(Response.Status.BAD_REQUEST)
			.entity("{ message: 'Missing Required Parameters' }").build();
	private final Response ACCOUNT_MATCH = Response.status(Response.Status.CONFLICT)
			.entity("{ message: 'Applicant ID not found for Account ID' }").build();
	private static Logger logger = Logger.getLogger("RestService");
	@Context
	private SecurityContext sc;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String doPost(JSONObject json) {
		PartnerUtil pu = ((Partner) sc.getUserPrincipal()).getPartnerUtil();
		Account account = null;
		Respondant respondant = null;
		logger.info("processing with: " + json.toString());
		try { // the required parameters
			account = pu.getAccountFrom(json.getJSONObject("account"));
			respondant = pu.getRespondantFrom(json.getJSONObject("applicant"));
			if ((account == null) || (respondant == null)) throw new Exception ("Not Found: " + json);
			
		} catch (Exception e) {
			logger.warning(e.getMessage());
			throw new WebApplicationException(e, MISSING_REQUIRED_PARAMS);
		}

		if (account.getAccountId() != respondant.getRespondantAccountId()) {
			logger.warning("Account does not match Applicant");
			throw new WebApplicationException(ACCOUNT_MATCH);
		}

		return PartnerUtil.getScoresMessage(respondant).toString();
	}
}