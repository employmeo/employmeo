package com.employmeo.actions;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.Account;
import com.employmeo.objects.Survey;
import com.employmeo.objects.User;


public class GetSurveyList extends MPFormAction {
	
	  public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		  
		  JSONArray response = new JSONArray();
		  // Collect expected input fields
		  User user = (User) sess.getAttribute("User");
		  Account account = user.getAccount();
		  if (account.getSurveys().size()>0) {
			  List<Survey> surveys = account.getSurveys();
			  for (int i=0;i<surveys.size();i++) {
				  response.put(surveys.get(i).getJSON());
			  }
		  }
		  
		  fRes.setSuccess(true);
		  fRes.setHTML(response.toString());
		  
		  return;
	  }

}
