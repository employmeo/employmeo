package com.employmeo.actions;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.Respondant;
import com.employmeo.objects.Survey;


public class GetFullSurvey extends MPFormAction {
	
	  public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		  
		  Respondant respondant = null;
		  
		  // Collect expected input fields
		  String respondantId = req.getParameter("respondant_id");

		  if (respondantId != null) {
			  respondant = Respondant.getRespondantById(respondantId);
		  } else {
			  //String accountId = req.getParameter("account_id");
			  //String surveyId = req.getParameter("survey_id");
			  //String positionId = req.getParameter("position_id");
			  //String locationId = req.getParameter("position_id");
			  // add code to create respondant on the fly.
		  }
		  
		  if (respondant != null) {
			  Survey survey = respondant.getSurvey();
			  JSONObject json = new JSONObject();
			  json.put("survey", survey.getJSON());
			  json.put("respondant", respondant.getJSON());
			  fRes.setSuccess(true);
			  fRes.setHTML(json.toString());
		  } else {
			  fRes.setValid(false);
		  }
		  
		  return;
	  }

}
