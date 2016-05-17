package com.employmeo.admin;

import java.sql.Date;
import java.sql.Timestamp;

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

@Path("getrespondants")
@PermitAll
public class GetRespondants {
	
	  @POST
	  @Produces(MediaType.APPLICATION_JSON)
	  public String doPost (
			    @Context final HttpServletRequest reqt,
			    @Context final HttpServletResponse resp,
			    @DefaultValue("-1") @FormParam("location_id") Long locationId,
			    @DefaultValue("-1") @FormParam("position_id") Long positionId,
			    @DefaultValue("2015-01-01") @FormParam("fromdate") String fromDate,
			    @DefaultValue("2020-12-31") @FormParam("todate") String toDate 
			    )
	  {  
		  JSONArray response = new JSONArray();

		  HttpSession sess = reqt.getSession();
		  User user = (User) sess.getAttribute("User");		  
		  if (user == null) {
			  resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			  return null;
	  	  } // else if (false) { // {resp.setStatus(HttpServletResponse.SC_FORBIDDEN); return null;}

  		  Timestamp from = new Timestamp(Date.valueOf(fromDate).getTime());
		  Timestamp to = new Timestamp(Date.valueOf(toDate).getTime());	  // losing rest of day (need to add a day)
		  String locationSQL = "";
		  String positionSQL = "";
		  if (locationId > -1) locationSQL = "AND r.respondantLocationId = :locationId ";
		  if (positionId > -1) positionSQL = "AND r.respondantPositionId = :positionId ";

		  EntityManager em = DBUtil.getEntityManager();		  
		  String dateSQL = "AND r.respondantCreatedDate >= :fromDate AND r.respondantCreatedDate <= :toDate ";
		  String sql = "SELECT r from Respondant r WHERE " +
				  	   "r.respondantStatus >= :status AND r.respondantAccountId = :accountId " +
				  	   locationSQL + positionSQL + dateSQL +
				  	   "ORDER BY r.respondantCreatedDate DESC";
		  TypedQuery<Respondant> query = em.createQuery(sql, Respondant.class);
		  query.setParameter("accountId", user.getAccount().getAccountId());
		  if (locationId > -1) query.setParameter("locationId", locationId);
		  if (positionId > -1) query.setParameter("positionId", positionId);
		  query.setParameter("fromDate", from);
		  query.setParameter("toDate", to);
		  query.setParameter("status", Respondant.STATUS_SCORED);
			    
		  List<Respondant> respondants = query.getResultList();
		  for (int j=0;j<respondants.size();j++) {
			  JSONObject jresp = respondants.get(j).getJSON();
			  jresp.put("scores", respondants.get(j).getAssessmentScore());
			  jresp.put("position", respondants.get(j).getPosition().getJSON());

			  response.put(jresp);
		  }
		   
		  return response.toString();
	  }
	  
}