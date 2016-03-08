package com.employmeo.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.User;
import com.employmeo.util.SecurityUtil;

public class Register extends MPFormAction {
	
	  public static void execute (HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		  User user = new User();
		  
		  // Get user details from registration forms
		  user.setUserEmail(req.getParameter("email"));
		  user.setUserPassword(SecurityUtil.hashPassword(req.getParameter("password")));
		  user.setUserFname(req.getParameter("firstname"));
		  user.setUserLname(req.getParameter("lastname"));
		  user.setUserAvatarUrl(req.getParameter("user_avatar_url"));
		  user.setUserLocale(req.getParameter("user_locale"));

		  user.setUserPersistLogin(false);
		  
	      String stayloggedin = req.getParameter("rememberme");
		  if (stayloggedin != null) {
			   user.setUserPersistLogin(true);
		  }

		  // Validate required fields
		  if (!SecurityUtil.isEmailValid(user.getUserEmail())) {
			  fRes.setValid(false);
			  fRes.addInvalidField("email");
		  }	  
		  if (user.getUserPassword() == null) {
			  fRes.setValid(false);
			  fRes.addInvalidField("password");
		  }
	  
		  if (fRes.wasValid()) {
			  user.persistMe();
			  if (user.getUserId() != null) {
  	     	      	sess.setAttribute("registration", "In Progress");
			  } else {
				  fRes.addMessage("Unable to create account for: " + user.getUserEmail());
				  fRes.setSuccess(false);
			  }
		  }
	 	  
	 	  return;
	  } 

}
