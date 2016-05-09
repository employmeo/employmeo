package com.employmeo.util;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import com.employmeo.objects.Account;
import com.employmeo.objects.Location;
import com.employmeo.objects.Position;
import com.employmeo.objects.Respondant;
import com.employmeo.objects.Survey;

public class PartnerUtil {

	public static Account getAccountFrom(JSONObject jAccount) {
		Account account = null;
		String accountAtsId = jAccount.optString("account_ats_id");
		if (accountAtsId != null) {
			// lookup account by ATS ID
			EntityManager em = DBUtil.getEntityManager();
			TypedQuery<Account> q = em.createQuery("SELECT a FROM Account a WHERE a.accountAtsId = :accountAtsId", Account.class);
	        q.setParameter("accountAtsId", accountAtsId);
	        try {
	      	  account = q.getSingleResult();
	        } catch (NoResultException nre) {
	        	throw new WebApplicationException (Response.status(Status.PRECONDITION_FAILED).entity(jAccount.toString()).build());
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
		if (jLocation != null) locationAtsId = jLocation.optString("location_ats_id");
		if (locationAtsId != null) {
			EntityManager em = DBUtil.getEntityManager();
			TypedQuery<Location> q = em.createQuery("SELECT l FROM Location l WHERE l.locationAtsId = :locationAtsId", Location.class);
	        q.setParameter("locationAtsId", locationAtsId);
	        try {
	      	  location = q.getSingleResult();
	        } catch (NoResultException nre) {
	        	location = new Location();
	        	// Create a new location from address
	        	JSONObject address = jLocation.getJSONObject("address");
	        	AddressUtil.validate(address);
	        	location.setLocationAtsId(locationAtsId);
	        	if (jLocation.has("location_name")) location.setLocationName(jLocation.getString("location_name"));
	        	if (address.has("street")) location.setLocationStreet1(address.getString("street"));
	        	if (address.has("formatted_address"))location.setLocationStreet2(address.getString("formatted_address"));
	        	if (address.has("city")) location.setLocationCity(address.getString("city"));
	        	if (address.has("state")) location.setLocationState(address.getString("state"));
	        	if (address.has("zip")) location.setLocationZip(address.getString("zip"));
	        	if (address.has("lat")) location.setLocationLat(address.getDouble("lat"));
	        	if (address.has("lng")) location.setLocationLong(address.getDouble("lng"));
	        	location.setAccount(account);
	        	location.persistMe();
	        }
			
		} else {
			location = account.getDefaultLocation();
		}
		return location;
	}

	public static Position getPositionFrom(JSONObject position, Account account) {
		// TODO Auto-generated method stub
		return account.getDefaultPosition();
	}

	public static Survey getSurveyFrom(JSONObject assessment, Account account) {
		// TODO Auto-generated method stub
		return account.getDefaultSurvey();
	}

	public static Respondant getRespondantFrom(JSONObject applicant) {
		// TODO Auto-generated method stub
		Respondant respondant = null;
		String applicantAtsId = applicant.optString("applicant_ats_id");
		if (applicantAtsId != null) {
			// lookup account by ATS ID
			EntityManager em = DBUtil.getEntityManager();
			TypedQuery<Respondant> q = em.createQuery("SELECT r FROM Respondant r WHERE r.respondantAtsId = :respondantAtsId", Respondant.class);
	        q.setParameter("respondantAtsId", applicantAtsId);
	        try {
	      	  respondant = q.getSingleResult();
	        } catch (NoResultException nre) {}
		} else {
			// Try to grab account by account_id
			respondant = Respondant.getRespondantById(applicant.optLong("applicant_id"));
		}
		return respondant;
	}

}
