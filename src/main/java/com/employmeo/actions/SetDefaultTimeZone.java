package com.employmeo.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.employmeo.EmpFormResponse;

public class SetDefaultTimeZone extends MPFormAction {
	
	 public static void execute (HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		  Double timezoneOffset = new Double (req.getParameter("family_timezone"));
		  sess.setAttribute("timezoneOffset", timezoneOffset);
		  return;
	  }

}
