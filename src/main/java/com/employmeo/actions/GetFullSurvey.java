package com.employmeo.actions;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.Account;
import com.employmeo.objects.Position;
import com.employmeo.objects.Survey;
import com.employmeo.objects.User;


public class GetFullSurvey extends MPFormAction {
	
	  public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		  
		  Survey survey = null;
		  
		  // Collect expected input fields
		  String surveyId = req.getParameter("survey_id");
		  String positionId = req.getParameter("position_id");

		  if (surveyId != null) {
			  survey = Survey.getSurveyById(surveyId);
		  } else if (positionId != null) {
			  Position position = Position.getPositionById(positionId);
			  if (position.getSurveys().size()>0) {
				  survey = position.getSurveys().get(0);
			  }
		  }
		  
		  if (survey == null) {
			  User user = (User) sess.getAttribute("User");
			  Account account = user.getAccount();
			  if (account.getSurveys().size()>0) {
				  survey = account.getSurveys().get(0);
			  }
		  }
		  
		  // Validate input fields
		  if (survey != null) {
			  fRes.setSuccess(true);
			  fRes.setHTML(survey.getJSON().toString());
		  } else {
			  fRes.setValid(false);
		  }
		  
		  return;
	  }

}
