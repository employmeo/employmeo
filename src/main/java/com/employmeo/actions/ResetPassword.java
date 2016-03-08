package com.employmeo.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.User;
import com.employmeo.util.SecurityUtil;

public class ResetPassword extends MPFormAction {

	public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {

		  String email =req.getParameter("email");
		  String hashword = req.getParameter("hashword");

		  // Validate required fields
		  if (!SecurityUtil.isEmailValid(email)) {
			  fRes.setValid(false);
			  fRes.addInvalidField("email");
		  }	  
		  if ((hashword == null) || (hashword.isEmpty())) {
			  fRes.setValid(false);
			  fRes.addInvalidField("hashword");
		  }

		  if (fRes.wasValid()) {
			  User user = User.loginHashword(email, hashword);
			  if (user.getUserId() != null) {
				  sess.setAttribute("LoggedIn", new Boolean(false));
				  sess.setAttribute("hashword", hashword);
				  sess.setAttribute("User", user);
			  } else {
				  fRes.setSuccess(false);
				  fRes.addMessage("Unable to reset password");
			  }
		  }
		  
		  if ((!fRes.wasValid()) || (!fRes.wasSuccessful())) {
			  fRes.setRedirectJSP("/invalid_link.jsp");
	      }
		  
	      return;

	}

}
