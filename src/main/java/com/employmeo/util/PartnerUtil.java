package com.employmeo.util;

import java.util.Iterator;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import com.employmeo.objects.Account;
import com.employmeo.objects.AccountSurvey;
import com.employmeo.objects.Location;
import com.employmeo.objects.Position;
import com.employmeo.objects.PositionProfile;
import com.employmeo.objects.Respondant;
import com.employmeo.objects.Survey;

public class PartnerUtil {
	private static Logger logger = Logger.getLogger("PartnerUtility");

	public static Account getAccountFrom(JSONObject jAccount) {
		Account account = null;
		String accountAtsId = jAccount.optString("account_ats_id");
		if (accountAtsId != null) {
			// lookup account by ATS ID
			EntityManager em = DBUtil.getEntityManager();
			TypedQuery<Account> q = em.createQuery("SELECT a FROM Account a WHERE a.accountAtsId = :accountAtsId",
					Account.class);
			q.setParameter("accountAtsId", accountAtsId);
			try {
				account = q.getSingleResult();
			} catch (NoResultException nre) {
				logger.warning("Can't find account with atsId: " + accountAtsId);
				throw new WebApplicationException(
						Response.status(Status.PRECONDITION_FAILED).entity(jAccount.toString()).build());
			}
		} else {
			// Try to grab account by account_id
			account = Account.getAccountById(jAccount.optLong("account_id"));
		}
		return account;
	}

	public static Location getLocationFrom(JSONObject jLocation, Account account) {
		Location location = null;
		String locationAtsId = null;
		if (jLocation != null)
			locationAtsId = jLocation.optString("location_ats_id");
		if (locationAtsId != null) {
			EntityManager em = DBUtil.getEntityManager();
			TypedQuery<Location> q = em.createQuery("SELECT l FROM Location l WHERE l.locationAtsId = :locationAtsId",
					Location.class);
			q.setParameter("locationAtsId", locationAtsId);
			try {
				location = q.getSingleResult();
			} catch (NoResultException nre) {
				location = new Location();
				// Create a new location from address
				JSONObject address = jLocation.getJSONObject("address");
				AddressUtil.validate(address);
				location.setLocationAtsId(locationAtsId);
				if (jLocation.has("location_name"))
					location.setLocationName(jLocation.getString("location_name"));
				if (address.has("street"))
					location.setLocationStreet1(address.getString("street"));
				if (address.has("formatted_address"))
					location.setLocationStreet2(address.getString("formatted_address"));
				if (address.has("city"))
					location.setLocationCity(address.getString("city"));
				if (address.has("state"))
					location.setLocationState(address.getString("state"));
				if (address.has("zip"))
					location.setLocationZip(address.getString("zip"));
				if (address.has("lat"))
					location.setLocationLat(address.getDouble("lat"));
				if (address.has("lng"))
					location.setLocationLong(address.getDouble("lng"));
				location.setAccount(account);
				location.persistMe();
			}

		} else {
			location = account.getDefaultLocation();
		}
		return location;
	}

	public static Position getPositionFrom(JSONObject position, Account account) {

		Position pos = null;
		Long positionId = position.optLong("position_id");
		if (positionId != null) pos = Position.getPositionById(positionId);
		if (pos == null) pos = account.getDefaultPosition();
		return pos;
	}

	public static Survey getSurveyFrom(JSONObject assessment, Account account) {

		Survey survey = null;
		Long asId = assessment.optLong("assessment_asid");
		if (asId != null) survey = AccountSurvey.getSurveyByASID(asId);
		if (survey == null) survey = account.getDefaultSurvey();
		
		return survey;
	}

	public static Respondant getRespondantFrom(JSONObject applicant) {
		Respondant respondant = null;
		String applicantAtsId = applicant.optString("applicant_ats_id");
		if (applicantAtsId != null) {
			// lookup account by ATS ID
			EntityManager em = DBUtil.getEntityManager();
			TypedQuery<Respondant> q = em.createQuery(
					"SELECT r FROM Respondant r WHERE r.respondantAtsId = :respondantAtsId", Respondant.class);
			q.setParameter("respondantAtsId", applicantAtsId);
			try {
				respondant = q.getSingleResult();
			} catch (NoResultException nre) {
			}
		} else {
			// Try to grab account by employmeo respondant_id
			respondant = Respondant.getRespondantById(applicant.optLong("applicant_id"));
		}
		return respondant;
	}

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
