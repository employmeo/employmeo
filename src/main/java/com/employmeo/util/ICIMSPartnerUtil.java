package com.employmeo.util;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import com.employmeo.objects.Account;
import com.employmeo.objects.AccountSurvey;
import com.employmeo.objects.Location;
import com.employmeo.objects.Partner;
import com.employmeo.objects.Position;
import com.employmeo.objects.Respondant;

public class ICIMSPartnerUtil implements PartnerUtil {
	private static Logger logger = Logger.getLogger("PartnerUtility");
	private Partner partner = null;
	
	public ICIMSPartnerUtil(Partner partner) {
		this.partner = partner;
	}
		
	public String getPrefix() {
		return partner.getPartnerPrefix();
	}

	@Override
	public String trimPrefix(String id) {
		return id.substring(id.indexOf(getPrefix())+getPrefix().length());
	}
	
	public Account getAccountFrom(JSONObject jAccount) {
		Account account = null;
		String accountAtsId = jAccount.optString("account_ats_id");
		if (accountAtsId != null) {
			// lookup account by ATS ID
			EntityManager em = DBUtil.getEntityManager();
			TypedQuery<Account> q = em.createQuery("SELECT a FROM Account a WHERE a.accountAtsId = :accountAtsId",
					Account.class);
			q.setParameter("accountAtsId", partner.getPartnerPrefix() + accountAtsId);
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

	public Location getLocationFrom(JSONObject jLocation, Account account) {
		Location location = null;
		String locationAtsId = null;
		if (jLocation != null)
			locationAtsId = jLocation.optString("location_ats_id");
		if (locationAtsId != null) {
			EntityManager em = DBUtil.getEntityManager();
			TypedQuery<Location> q = em.createQuery(
					"SELECT l FROM Location l WHERE l.locationAtsId = :locationAtsId AND l.locationAccountId = :accountId",
					Location.class);
			q.setParameter("locationAtsId", partner.getPartnerPrefix() + locationAtsId);
			q.setParameter("accountId", account.getAccountId());
			try {
				location = q.getSingleResult();
			} catch (NoResultException nre) {
				location = new Location();
				// Create a new location from address
				JSONObject address = jLocation.getJSONObject("address");
				AddressUtil.validate(address);
				location.setLocationAtsId(partner.getPartnerPrefix() + locationAtsId);
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

	public Position getPositionFrom(JSONObject position, Account account) {

		Position pos = null;
		Long positionId = position.optLong("position_id");
		if (positionId != null) pos = Position.getPositionById(positionId);
		if (pos == null) pos = account.getDefaultPosition();
		return pos;
	}

	public AccountSurvey getSurveyFrom(JSONObject assessment, Account account) {

		AccountSurvey aSurvey = null;
		Long asId = assessment.optLong("assessment_asid");
		if (asId != null) aSurvey = AccountSurvey.getAccountSurveyByASID(asId);
		
		return aSurvey;
	}

	public Respondant getRespondantFrom(JSONObject applicant) {
		Respondant respondant = null;
		String applicantAtsId = applicant.optString("applicant_ats_id");
		if (applicantAtsId != null) {
			// lookup account by ATS ID
			EntityManager em = DBUtil.getEntityManager();
			TypedQuery<Respondant> q = em.createQuery(
					"SELECT r FROM Respondant r WHERE r.respondantAtsId = :respondantAtsId", Respondant.class);
			q.setParameter("respondantAtsId", partner.getPartnerPrefix() + applicantAtsId);
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

}
