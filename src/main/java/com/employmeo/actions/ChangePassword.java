package com.employmeo.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.User;
import com.employmeo.util.SecurityUtil;

public class ChangePassword extends MPFormAction {
	  public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {

		  User user = (User) sess.getAttribute("User");
		  String hashword = (String) sess.getAttribute("hashword");
		  String oldPassword = req.getParameter("old_password");
		  String newPassword = req.getParameter("new_password");
		  String confirmPass = req.getParameter("confirm_password");
		  
		  if (!newPassword.equals(confirmPass)) {
			  fRes.setValid(false);
			  fRes.addMessage("New password does not match confirm password");
			  fRes.addInvalidField("new_password");
			  fRes.addInvalidField("confirm_password");
		  } else {
			  if ((hashword == null) || (hashword.isEmpty())) {
				  hashword = SecurityUtil.hashPassword(oldPassword);
			  }
		  
			  user = User.loginHashword(user.getUserEmail(), hashword);
	 	  
			  if (user.getUserId() != null) {
				  user.setUserPassword(SecurityUtil.hashPassword(newPassword));
				  user.mergeMe();
				  fRes.addMessage("Successful Update");
			  } else {
				  fRes.addMessage("Old password did not match");
				  fRes.addInvalidField("old_password");
				  fRes.setSuccess(false);
			  }
		  }

		  return;
	  }

}
