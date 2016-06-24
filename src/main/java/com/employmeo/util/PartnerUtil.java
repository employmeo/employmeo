package com.employmeo.util;

import java.util.HashMap;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.employmeo.objects.Account;
import com.employmeo.objects.AccountSurvey;
import com.employmeo.objects.Location;
import com.employmeo.objects.Partner;
import com.employmeo.objects.Position;
import com.employmeo.objects.Respondant;

public interface PartnerUtil {
	public static Logger logger = Logger.getLogger("PartnerUtility");
	public Partner partner = null;
	public static HashMap<Partner,PartnerUtil> utils = new HashMap<Partner,PartnerUtil>();
	
	public static PartnerUtil getUtilFor(Partner lookupPartner) {
		// TODO make this work for multiple partners
		if (!utils.containsKey(lookupPartner)) {
			if ("ICIMS".equalsIgnoreCase(lookupPartner.getPartnerName())) {
				utils.put(lookupPartner, new ICIMSPartnerUtil(lookupPartner));
			} else {
				utils.put(lookupPartner, new DefaultPartnerUtil(lookupPartner));
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
	public JSONObject getScoresMessage(Respondant respondant);
	public void postScoresToPartner(Respondant respondant, JSONObject message);


}
