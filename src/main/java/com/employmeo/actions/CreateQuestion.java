package com.employmeo.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.Account;
import com.employmeo.objects.Position;
import com.employmeo.objects.Survey;
import com.employmeo.objects.User;


public class CreateQuestion extends MPFormAction {
	
	  public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		  
		  Survey survey = new Survey();

		  // Collect expected input fields
		  User user = (User) sess.getAttribute("User");
		  Account account = user.getAccount();	  
		  String positionId = req.getParameter("position_id");
		  String surveyName = req.getParameter("survey_name");
		  
		  // Validate input fields
		  if (positionId != null) {
			  survey.setPosition(Position.getPositionById(positionId));
		  }
		  fRes.setValid(true);
		  
		  // Perform business logic  
		  survey.setAccount(account);
		  survey.setUser(user);
		  survey.setSurveyName(surveyName);
		  survey.setSurveyStatus(1);
		  survey.setSurveyType(1);
		  survey.persistMe();
		  
		  fRes.setSuccess(true);
		  
		  JSONObject json = new JSONObject();
		  json.put("survey", survey.getJSON());
		  json.accumulate("message", "created new survey " + survey.getSurveyId());
		  
		  fRes.setHTML(json.toString());
		  return;
	  }

}
