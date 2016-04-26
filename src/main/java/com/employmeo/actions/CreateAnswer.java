package com.employmeo.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.Answer;
import com.employmeo.objects.Question;



public class CreateAnswer extends MPFormAction {
	
	  public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		  
		  Answer answer = new Answer();

		  // Collect expected input fields
		  String questionId = req.getParameter("question_id");
		  String answerDescription = req.getParameter("answer_description");
		  String answerText = req.getParameter("answer_text");
		  String answerValue = req.getParameter("answer_value");
		  String answerDisplayId = req.getParameter("answer_display_id");

		  // Validate input fields
		  if (questionId != null) {
			  answer.setQuestion(Question.getQuestionById(questionId));
		  }
		  if (answerValue != null) {
			  answer.setAnswerValue(new Integer(answerValue));			  
		  }
		  if (answerDisplayId != null) {
			  answer.setAnswerDisplayId(new Long(answerDisplayId));
		  }
		  fRes.setValid(true);
		  
		  // Perform business logic  
		  answer.setAnswerDescription(answerDescription);
		  answer.setAnswerText(answerText);
		  
		  answer.mergeMe();
		  
		  fRes.setSuccess(true);
		  
		  JSONObject json = new JSONObject();
		  json.put("answer", answer.getJSON());
		  json.accumulate("message", "created new answer " + answer.getAnswerId());
		  
		  fRes.setHTML(json.toString());
		  return;
	  }

}
