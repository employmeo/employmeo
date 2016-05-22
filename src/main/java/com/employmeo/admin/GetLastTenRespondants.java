package com.employmeo.admin;

import javax.annotation.security.PermitAll;
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

import com.employmeo.objects.Respondant;
import com.employmeo.objects.User;
import com.employmeo.util.DBUtil;

import java.util.List;

@Path("getlastten")
@PermitAll
public class GetLastTenRespondants {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String doPost(@Context final HttpServletRequest reqt, @Context final HttpServletResponse resp,
			@DefaultValue("-1") @FormParam("location_id") Long locationId,
			@DefaultValue("-1") @FormParam("position_id") Long positionId) {
		JSONArray response = new JSONArray();

		HttpSession sess = reqt.getSession();
		User user = (User) sess.getAttribute("User");
		if (user == null) {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		} // else if (false) { //
			// {resp.setStatus(HttpServletResponse.SC_FORBIDDEN); return null;}

		String locationSQL = "";
		String positionSQL = "";
		if (locationId > -1)
			locationSQL = "AND r.respondantLocationId = :locationId ";
		if (positionId > -1)
			positionSQL = "AND r.respondantPositionId = :positionId ";

		EntityManager em = DBUtil.getEntityManager();
		String sql = "SELECT r from Respondant r WHERE "
				+ "r.respondantStatus >= :status AND r.respondantAccountId = :accountId " + locationSQL + positionSQL
				+ "ORDER BY r.respondantCreatedDate DESC";
		TypedQuery<Respondant> query = em.createQuery(sql, Respondant.class);
		query.setMaxResults(10);
		query.setParameter("accountId", user.getAccount().getAccountId());
		if (locationId > -1)
			query.setParameter("locationId", locationId);
		if (positionId > -1)
			query.setParameter("positionId", positionId);
		query.setParameter("status", Respondant.STATUS_PREDICTED);

		List<Respondant> respondants = query.getResultList();
		for (int j = 0; j < respondants.size(); j++) {
			respondants.get(j).getAssessmentScore();
			JSONObject jresp = respondants.get(j).getJSON();
			jresp.put("scores", respondants.get(j).getAssessmentScore());

			response.put(jresp);
		}

		return response.toString();
	}

}