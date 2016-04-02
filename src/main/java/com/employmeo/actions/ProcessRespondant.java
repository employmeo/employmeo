package com.employmeo.actions;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.Corefactor;
import com.employmeo.objects.Position;
import com.employmeo.objects.Question;
import com.employmeo.objects.Respondant;
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
			  List<Response> responses = respondant.getResponses();
			  JSONObject scores = new JSONObject();
			  for (int i=0;i<responses.size();i++) {
				  Response response = responses.get(i);
				  Integer cfId = Question.getQuestionById(response.getResponseQuestionId()).getQuestionCorefactorId();
				  Corefactor corefactor = Corefactor.getCorefactorById(cfId);
				  int score = 0;
				  if (scores.has(corefactor.getCorefactorName())) {
					  score = scores.getInt(corefactor.getCorefactorName()) + response.getResponseValue();
				  } else {
					  score = response.getResponseValue();
				  }
				  scores.put(corefactor.getCorefactorName(),score);				  
			  }
			  Position position = Position.getPositionById(respondant.getRespondantPositionId());
			  JSONObject json = new JSONObject();
			  json.put("respondant", respondant.getJSON());
			  json.put("scores", scores);
			  if (position != null) json.put("position", position.getJSON());
			  fRes.setSuccess(true);
			  fRes.setHTML(json.toString());
		  } else {
			  fRes.setValid(false);
		  }
		  
		  return;
	  }


}
