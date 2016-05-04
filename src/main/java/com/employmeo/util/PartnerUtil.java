package com.employmeo.util;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

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
	        } catch (NoResultException nre) {}
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
	        	// Create a new location from address
	        	JSONObject address = jLocation.getJSONObject("address");
	        	AddressUtil.validate(address);
	        	location = new Location();
	        	location.setAccount(account);
	        	location.setLocationAtsId(locationAtsId);
	        	location.setLocationName(jLocation.optString("location_name"));
	        	location.setLocationStreet1(address.optString("address"));
	        	location.setLocationLat(address.optDouble("lat"));
	        	location.setLocationLong(address.optDouble("lng"));
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
