package com.employmeo.actions;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.employmeo.EmpFormResponse;
import com.employmeo.util.EmailUtility;

public class ContactUs extends MPFormAction {
	
	  public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {

		  Enumeration<String> parameters = req.getParameterNames();	  
		  StringBuffer html = new StringBuffer("<b>Contact Us Form Submission Details</b><br>");
		  while (parameters.hasMoreElements()) {
			  String paramname = parameters.nextElement();
			  html.append(paramname);
			  html.append(": ");
			  html.append(req.getParameter(paramname));
			  html.append("<br>");
		  }
	  
		  try {
			  EmailUtility.sendMessage("sri@employmeo.com", "employmeo - Contact Us", html.toString());
			  fRes.addMessage("Sent request");
		  } catch (Exception e) {
			  fRes.setSuccess(false);
			  fRes.addMessage("An Error Occurred in sending your request: " + e.getMessage());
		  }
		  return;
	  }

}
