package com.employmeo.actions;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.Corefactor;
import com.employmeo.objects.Position;
import com.employmeo.objects.PositionProfile;
import com.employmeo.objects.Question;
import com.employmeo.objects.Respondant;
import com.employmeo.objects.RespondantScore;
import com.employmeo.objects.Response;


public class ProcessRespondant extends MPFormAction {
	
	  public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		  
		  Respondant respondant = null;
		  
		  // Collect expected input fields
		  String respondantId = req.getParameter("respondant_id");

		  if (respondantId != null) {
			  respondant = Respondant.getRespondantById(respondantId);
		  }
		  
		  if (respondant != null) {
			  JSONObject json = new JSONObject();
			  JSONObject scores = respondant.scoreMe();
			  if (scores == null) fRes.setValid(false);
			  
			  JSONObject resp = respondant.getJSON();
			  PositionProfile profile = PositionProfile.getProfileDefaults(respondant.getRespondantProfile());
			  resp.put("respondant_profile_icon", profile.get("profile_icon"));
			  resp.put("respondant_profile_class", profile.get("profile_class"));

			  json.put("respondant", resp);
			  json.put("scores", scores);
			  Position position = Position.getPositionById(respondant.getRespondantPositionId());
			  if (position != null) json.put("position", position.getJSON());
			  fRes.setSuccess(true);
			  fRes.setHTML(json.toString());
		  } else {
			  fRes.setValid(false);
		  }
		  
		  return;
	  }
}