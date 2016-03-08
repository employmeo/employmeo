package com.employmeo.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.net.URL;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.User;
import com.employmeo.util.EmailServletResponse;
import com.employmeo.util.EmailUtility;
import com.employmeo.util.SecurityUtil;

public class ForgotPasswordEmail extends MPFormAction {
	  public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {

	      String email = req.getParameter("email");

	      if ((email == null) || (!SecurityUtil.isEmailValid(email))) {
	    	  fRes.addMessage("Invalid email address format");
			  fRes.addInvalidField("email");
	    	  fRes.setValid(false);
	    	  fRes.setSuccess(false);
	    	  return;
	      }
	      
	      User user = User.lookupByEmail(email);
	      if (user.getUserId() != null) {// does user exist?
		      try {
		          String hashword = user.getUserPassword();
			      URL link = new URL("http://" + req.getServerName() + "/mp?formname=resetpassword&email=" + user.getUserEmail() + "&hashword=" + hashword);
			      sess.setAttribute("forgot_user", user);
			      sess.setAttribute("link", link.toString());
			      EmailServletResponse htmlpart = new EmailServletResponse();
			      req.getRequestDispatcher("/WEB-INF/emails/forgotpassword.jsp").forward(req, htmlpart);		      
			      EmailUtility.sendMessage(user.getUserEmail(), "eploymeo - Password Reset", htmlpart.toString(), htmlpart);
		    	  fRes.setSuccess(true);
		    	  fRes.addMessage("Sent reset password email to: " + user.getUserEmail());
		      } catch (Exception e) {
		    	  e.printStackTrace(System.out);
		    	  fRes.addMessage(e.getMessage());
		    	  fRes.setSuccess(false);
		      }
	      } else {
	    	  fRes.addMessage("Could not find user associated with email: " + user.getUserEmail());
	    	  fRes.setSuccess(false);
	      }
	      	      
	      return;
	  }

}
