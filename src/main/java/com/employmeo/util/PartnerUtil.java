package com.employmeo.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.employmeo.objects.Account;
import com.employmeo.objects.AccountSurvey;
import com.employmeo.objects.Location;
import com.employmeo.objects.Partner;
import com.employmeo.objects.Position;
import com.employmeo.objects.PositionProfile;
import com.employmeo.objects.Respondant;

public interface PartnerUtil {
	public static Logger logger = Logger.getLogger("PartnerUtility");
	public Partner partner = null;
	public static HashMap<Partner,PartnerUtil> utils = new HashMap<Partner,PartnerUtil>();
	
	public static PartnerUtil getUtilFor(Partner lookupPartner) {
		// TODO make this work for multiple partners
		if (!utils.containsKey(lookupPartner)) {
logger.info("Assigning Util for Partner: " + lookupPartner.getJSONString());
			if ("ICIMS".equalsIgnoreCase(lookupPartner.getPartnerName())) {
				utils.put(lookupPartner, new ICIMSPartnerUtil(lookupPartner));
logger.info("ICIMS!");
			} else {
				utils.put(lookupPartner, new DefaultPartnerUtil(lookupPartner));
logger.info(lookupPartner.getPartnerName() + ", so not ICIMS, right? " + lookupPartner.getName());
			}
		}
		return utils.get(lookupPartner);
	}
	
	public String getPrefix();
	public String trimPrefix(String id);
	public Account getAccountFrom(JSONObject jAccount);
	public Location getLocationFrom(JSONObject jLocation, Account account);
	public Position getPositionFrom(JSONObject position, Account account);
	public AccountSurvey getSurveyFrom(JSONObject assessment, Account account);
	public Respondant getRespondantFrom(JSONObject applicant);
	public Respondant createRespondantFrom(JSONObject json, Account account);
	public JSONObject prepOrderResponse(JSONObject json, Respondant respondant);

	public static JSONObject getScoresMessage(Respondant respondant) {

		JSONObject scores = respondant.getAssessmentScore();
		ScoringUtil.predictRespondant(respondant);

		Account account = respondant.getRespondantAccount();
		JSONObject jAccount = new JSONObject();
		JSONObject applicant = new JSONObject();

		jAccount.put("account_ats_id", account.getAccountAtsId());
		jAccount.put("account_id", account.getAccountId());
		jAccount.put("account_name", account.getAccountName());

		applicant.put("applicant_ats_id", respondant.getRespondantAtsId());
		applicant.put("applicant_id", respondant.getRespondantId());
		applicant.put("applicant_profile", respondant.getRespondantProfile());
		applicant.put("applicant_profile_a", respondant.getProfileA());
		applicant.put("applicant_profile_b", respondant.getProfileB());
		applicant.put("applicant_profile_c", respondant.getProfileC());
		applicant.put("applicant_profile_d", respondant.getProfileD());
		applicant.put("label_profile_a",
				PositionProfile.getProfileDefaults(PositionProfile.PROFILE_A).getString("profile_name"));
		applicant.put("label_profile_b",
				PositionProfile.getProfileDefaults(PositionProfile.PROFILE_B).getString("profile_name"));
		applicant.put("label_profile_c",
				PositionProfile.getProfileDefaults(PositionProfile.PROFILE_C).getString("profile_name"));
		applicant.put("label_profile_d",
				PositionProfile.getProfileDefaults(PositionProfile.PROFILE_D).getString("profile_name"));

		Iterator<String> it = scores.keys();
		JSONArray scoreset = new JSONArray();
		while (it.hasNext()) {
			String label = it.next();
			JSONObject cf = new JSONObject();
			cf.put("corefactor_name", label);
			cf.put("corefactor_score", scores.getDouble(label));
			scoreset.put(cf);
		}

		applicant.put("scores", scoreset);
		applicant.put("portal_link", EmailUtility.getPortalLink(respondant));
		applicant.put("render_link", EmailUtility.getRenderLink(applicant));

		JSONObject message = new JSONObject();
		message.put("account", jAccount);
		message.put("applicant", applicant);

		return message;

	}

	public static void postScoresToPartner(Respondant respondant, JSONObject message) {

		String postmethod = respondant.getRespondantScorePostMethod();
		if (postmethod == null || postmethod.isEmpty())
			postmethod = "https://employmeo.herokuapp.com/integration/echo";

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(postmethod);
		try {
			String result = target.request(MediaType.APPLICATION_JSON)
					.post(Entity.entity(message.toString(), MediaType.APPLICATION_JSON), String.class);
			logger.info("posted scores to echo with result:\n" + result);
		} catch (Exception e) {
			logger.severe("failed posting scores to: " + postmethod);
		}

	}

}
