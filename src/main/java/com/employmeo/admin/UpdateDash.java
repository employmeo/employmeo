package com.employmeo.admin;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.employmeo.objects.PositionProfile;
import com.employmeo.objects.Respondant;
import com.employmeo.objects.User;
import com.employmeo.util.DBUtil;

import java.util.Arrays;
import java.util.List;

@Path("updatedash")
public class UpdateDash {

	private static final long ONE_DAY = 24*60*60*1000; // one day in milliseconds
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String doPost(@Context final HttpServletRequest reqt, @Context final HttpServletResponse resp,
			@DefaultValue("-1") @FormParam("location_id") Long locationId,
			@DefaultValue("-1") @FormParam("position_id") Long positionId,
			@DefaultValue("2015-01-01") @FormParam("fromdate") String fromDate,
			@DefaultValue("2020-12-31") @FormParam("todate") String toDate) {

		JSONObject response = new JSONObject();

		HttpSession sess = reqt.getSession();
		User user = (User) sess.getAttribute("User");

		if (user == null) {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		} // else if (false) { //
			// {resp.setStatus(HttpServletResponse.SC_FORBIDDEN); return null;}

		Timestamp from = new Timestamp(Date.valueOf(fromDate).getTime());
		Timestamp to = new Timestamp(Date.valueOf(toDate).getTime() + ONE_DAY);

		String locationSQL = "";
		String positionSQL = "";
		if (locationId > -1)
			locationSQL = "AND r.respondantLocationId = :locationId ";
		if (positionId > -1)
			positionSQL = "AND r.respondantPositionId = :positionId ";
		String dateSQL = "AND r.respondantCreatedDate >= :fromDate AND r.respondantCreatedDate < :toDate ";

		EntityManager em = DBUtil.getEntityManager();
		String sql = "SELECT r.respondantStatus, r.respondantProfile, COUNT(r) from Respondant r WHERE r.respondantAccountId = :accountId "
				+ locationSQL + positionSQL + dateSQL + "GROUP BY r.respondantStatus, r.respondantProfile";
		TypedQuery<Object[]> query = em.createQuery(sql, Object[].class);
		query.setParameter("accountId", user.getAccount().getAccountId());
		if (locationId > -1)
			query.setParameter("locationId", locationId);
		if (positionId > -1)
			query.setParameter("positionId", positionId);
		query.setParameter("fromDate", from);
		query.setParameter("toDate", to);

		List<Object[]> results = query.getResultList();
		List<String> labels = Arrays.asList("unscored", PositionProfile.PROFILE_A, PositionProfile.PROFILE_B,
				PositionProfile.PROFILE_C, PositionProfile.PROFILE_D);
		int totalInvited = 0;
		int totalStarted = 0;
		int totalCompleted = 0;
		int totalScored = 0;
		int totalHired = 0;
		int[] scoredByProfile = { 0, 0, 0, 0, 0 };
		int[] hiredByProfile = { 0, 0, 0, 0, 0 };

		for (int i = 0; i < results.size(); i++) {
			Object[] arr = results.get(i);
			int status = (Integer) (arr[0]);
			String label = (String) arr[1];
			long count = (Long) (arr[2]);
			if (label == null)
				label = labels.get(0);
			int index = labels.indexOf(label);
			switch (status) {
			case Respondant.STATUS_TERMINATED:
			case Respondant.STATUS_QUIT:
			case Respondant.STATUS_HIRED:
				totalHired += count;
				hiredByProfile[index] += count;
			case Respondant.STATUS_OFFERED:
			case Respondant.STATUS_DECLINED:
			case Respondant.STATUS_REJECTED:
			case Respondant.STATUS_SCORED:
			case Respondant.STATUS_PREDICTED:
				totalScored += count;
				scoredByProfile[index] += count;
			case Respondant.STATUS_COMPLETED:
				totalCompleted += count;
			case Respondant.STATUS_STARTED:
				totalStarted += count;
			case Respondant.STATUS_INVITED:
				totalInvited += count;
				break;
			default:
				break;
			}
		}

		// Assemble Applicant Data
		JSONObject applicantData = new JSONObject();
		JSONObject appDatasets = new JSONObject();
		for (int i = 0; i < labels.size(); i++) {
			applicantData.accumulate("labels", PositionProfile.getProfileDefaults(labels.get(i)).get("profile_name"));
			appDatasets.accumulate("backgroundColor",
					PositionProfile.getProfileDefaults(labels.get(i)).get("profile_color"));
			appDatasets.accumulate("hoverBackgroundColor",
					PositionProfile.getProfileDefaults(labels.get(i)).get("profile_highlight"));
		}
		appDatasets.put("data", scoredByProfile);
		applicantData.put("datasets", new JSONArray().put(appDatasets));

		// Hire Data
		JSONObject hireData = new JSONObject();
		JSONObject hireDatasets = new JSONObject();
		for (int i = 0; i < labels.size(); i++) {
			hireData.accumulate("labels", PositionProfile.getProfileDefaults(labels.get(i)).get("profile_name"));
			hireDatasets.accumulate("backgroundColor",
					PositionProfile.getProfileDefaults(labels.get(i)).get("profile_color"));
			hireDatasets.accumulate("hoverBackgroundColor",
					PositionProfile.getProfileDefaults(labels.get(i)).get("profile_highlight"));
		}
		hireDatasets.put("data", hiredByProfile);
		hireData.put("datasets", new JSONArray().put(hireDatasets));

		// TODO write code to get Hiring Mix History Data

		// Other Data
		response.put("applicantData", applicantData);
		response.put("hireData", hireData);
		response.put("totalinvited", totalInvited);
		response.put("totalstarted", totalStarted);
		response.put("totalcompleted", totalCompleted);
		response.put("totalscored", totalScored);
		response.put("totalhired", totalHired);
		return response.toString();
	}

}