package com.employmeo.actions;

import java.math.BigInteger;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.Account;
import com.employmeo.objects.Person;
import com.employmeo.objects.Respondant;
import com.employmeo.objects.User;
import com.employmeo.util.EmailServletResponse;
import com.employmeo.util.EmailUtility;

public class InviteApplicant extends MPFormAction {
	
	  public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {

		  // Collect expected input fields
		  String to = req.getParameter("email");
		  String fname = req.getParameter("fname");
		  String lname = req.getParameter("lname");
		  String address = req.getParameter("address");
		  String formattedAddress = req.getParameter("address");
		  Double personLat = Double.valueOf(req.getParameter("lat"));
		  Double personLong = Double.valueOf(req.getParameter("lng"));;
		  String survey_id = req.getParameter("survey_id");
		  BigInteger surveyId = new BigInteger(survey_id);
		  
		  String location_id = req.getParameter("location_id");
		  String position_id = req.getParameter("position_id");
		  User user = (User)sess.getAttribute("User");
		  Account account = user.getAccount();
		  
		  // Validate input fields
		  /* Todo: complete validation */
		  
		  // Perform business logic  
		  Person applicant = new Person();
		  applicant.setPersonEmail(to);
		  applicant.setPersonFname(fname);
		  applicant.setPersonLname(lname);
		  applicant.setPersonStreet1(address);
		  applicant.setPersonStreet2(formattedAddress);
		  applicant.setPersonLat(personLat);
		  applicant.setPersonLong(personLong);
		  applicant.persistMe();
		  
		  Respondant respondant = new Respondant();
		  respondant.setPerson(applicant);
		  respondant.setRespondantAccountId(account.getAccountId());
		  respondant.setRespondantSurveyId(surveyId);
		  try {respondant.setRespondantLocationId(new BigInteger(location_id));} catch (Exception e) {}// ok for null location
		  try {respondant.setRespondantPositionId(new BigInteger(position_id));} catch (Exception e) {}// ok for null location
		  respondant.persistMe();
		  
		  JSONObject json = new JSONObject();
		  json.put("person", applicant.getJSON());
		  json.put("respondant", respondant.getJSON());	
		  
		  String body = "Dear " + fname + ",\n" +
				  			"\n" +
				  			"Congratulations, we are excited to invite you to complete a preliminary assessment for this position.\n" +
							"Our assessment can be completed on a mobile device or in a browser at this link: \n";
		  
		  try {
			  String prefix = "http://" + req.getServerName();
			  if (req.getServerPort() != 80) {
			  	prefix = prefix + ":" + req.getServerPort();
			  }
		      URL link = new URL(prefix + "/take_survey.jsp?&respondant_id=" + respondant.getRespondantId());
		      sess.setAttribute("applicant", applicant);
		      sess.setAttribute("link", link.toString());
		      EmailServletResponse htmlpart = new EmailServletResponse();
		      req.getRequestDispatcher("/WEB-INF/emails/inviteapplicant.jsp").forward(req, htmlpart);		      
		      EmailUtility.sendMessage(to, "Invitation to Apply", body+link, htmlpart);
			  json.accumulate("message", "Sent link: " + link);
			  
		  } catch (Exception e) {
			  fRes.setSuccess(false);
			  fRes.addMessage("An Error Occurred in sending your request: " + e.getMessage());
		  }
		  
		  fRes.setHTML(json.toString());
		  return;
	  }

}
