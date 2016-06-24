package com.employmeo.integration;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.json.JSONObject;

import com.employmeo.objects.Account;
import com.employmeo.objects.Partner;
import com.employmeo.objects.Respondant;
import com.employmeo.util.PartnerUtil;

@Path("atsorder")
public class ATSOrder {

	@Context
	private UriInfo uriInfo;
	@Context
	private SecurityContext sc;

	private static Logger logger = Logger.getLogger("RestService");

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String doPost(JSONObject json) {
		logger.info("ATS Requesting Assessment with: " + json.toString());

		PartnerUtil pu = ((Partner) sc.getUserPrincipal()).getPartnerUtil();
		Account account = pu.getAccountFrom(json.getJSONObject("account"));
		Respondant respondant = pu.createRespondantFrom(json, account);
		JSONObject output = pu.prepOrderResponse(json, respondant);

		logger.info("ATS Request for Assessment Complete: " + respondant.getRespondantAtsId());
		return output.toString();
	}

}