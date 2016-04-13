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
			  int[] count = new int[20];
			  int[] score = new int[20];
			  
			  for (int i=0;i<responses.size();i++) {
				  Response response = responses.get(i);
				  Integer cfId = Question.getQuestionById(response.getResponseQuestionId()).getQuestionCorefactorId();
				  count[cfId]++;
				  score[cfId]+=response.getResponseValue();
			  }

			  for (int i=0; i<20; i++) {
				  if (count[i]>0) {
					  Corefactor corefactor = Corefactor.getCorefactorById(i);			  
					  scores.put(corefactor.getCorefactorName(),(double) Math.round(100.0 *((double)score[i]/(double)count[i]))/ 100.0);
				  }
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
