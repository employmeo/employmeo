package com.employmeo.actions;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.User;
import com.employmeo.util.SecurityUtil;
import com.employmeo.EmpAdminServlet;
import com.employmeo.EmpAutoLoginFilter;

public class Login extends MPFormAction {

	public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {

	      // Collect Expected Input Fields From Form:
	      
	      String email = req.getParameter("email");
	      String password = req.getParameter("password");
	      boolean persistLogin = false;
	      String stayloggedin = req.getParameter("rememberme");
		  if (stayloggedin != null) {
			  persistLogin = true;
		  }
		  
		  // Validate required fields
		  if (!SecurityUtil.isEmailValid(email)) {
			  fRes.setValid(false);
			  fRes.addInvalidField("email");
		  }
		  
		  if ((password == null) || (password.isEmpty())) {
			  fRes.setValid(false);
			  fRes.addInvalidField("password");
		  }

		  // Execute business logic (lookup the user by email and password)
		  if (fRes.wasValid()) {
			  User user = User.login(email, password);
	          if (user.getUserId() !=null) {
				  EmpAdminServlet.login(user, sess, res, req);
				  if (persistLogin) {
					  EmpAutoLoginFilter.setLoginCookies(res, user);
				  } else {
					  EmpAutoLoginFilter.removeLoginCookies(res);
				  }
	          } else {
				  fRes.setSuccess(false);
				  fRes.addMessage("Login not successful");	        	  
	          }	  
		  }
		  
		  // Execute business logic (lookup the user by email and password)
		  if (!fRes.wasValid() || !fRes.wasSuccessful()) {
			  fRes.setRedirectJSP("/login.jsp");
	      } else {
	          String deniedPage = (String) sess.getAttribute("deniedPage");
	          if (deniedPage != null) {
	          	fRes.setRedirectJSP(deniedPage);
	          	sess.removeAttribute("deniedPage");
	          }
	      }

	      return;

	}

}