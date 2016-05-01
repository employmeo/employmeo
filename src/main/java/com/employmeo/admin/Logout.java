package com.employmeo.admin;

import javax.annotation.security.PermitAll;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;


@Path("logout")
@PermitAll
public class Logout {
	
	  @POST
	  public void doPost (
			    @Context final HttpServletRequest reqt,
			    @Context final HttpServletResponse resp
			    )
	  {  
		  HttpSession sess = reqt.getSession();
		  sess.invalidate();

		  // Kill the persist login cookie
		  Cookie cookie = new Cookie("email", "");
		  cookie.setMaxAge(5); // kills the cookie after 5 seconds  
		  resp.addCookie(cookie);
		  cookie = new Cookie("hashword", "");
		  cookie.setMaxAge(5); // kills the cookie after 5 seconds  
		  resp.addCookie(cookie);

		  return;
	  }	  
	  
}