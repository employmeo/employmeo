package com.employmeo.actions;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.employmeo.EmpFormResponse;

public class Logout extends MPFormAction {
	  public static void execute (HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {

		  sess.invalidate();

		  // Kill the persist login cookie
		  Cookie cookie = new Cookie("email", "");
		  cookie.setMaxAge(5); // kills the cookie after 5 seconds  
		  res.addCookie(cookie);
		  cookie = new Cookie("hashword", "");
		  cookie.setMaxAge(5); // kills the cookie after 5 seconds  
		  res.addCookie(cookie);

		  return;

	  }

}
