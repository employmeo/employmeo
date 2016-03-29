package com.employmeo.actions;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.User;
import com.employmeo.EmpAdminServlet;
import com.employmeo.util.FaceBookHelper;

public class FBRegister extends MPFormAction {

	  public static void execute (HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		  String code = req.getParameter("code");
		  String token = null;
		  String graph = null;
		  User user = null;
		  Locale locale = (Locale) sess.getAttribute("locale");

		  if (code == null || code.equals("")) {
	          fRes.addMessage("Facebook Connect Not Approved");
	          fRes.setSuccess(false);
	      } else {
	    	  try {
	        	token = FaceBookHelper.getAccessToken(code, FaceBookHelper.appSignup, req.getServerName());
	        	graph = FaceBookHelper.getGraph(token);
	           	user = FaceBookHelper.getUserFromGraph(graph);
	           	user.setUserLocale(locale.getLanguage());
	           	
	            //helper.createDBObject(user);
	            //user.setAttribute("timezoneOffset", timezoneOffset);
	            //helper.addAccount(user);
	            //user.removeAttribute("timezoneOffset");

				EmpAdminServlet.login(user, sess, res, req);
				
               	sess.setAttribute("fbToken", token);
	     	    sess.setAttribute("registration", "In Progress");
	    	  } catch (Exception e) {
				  fRes.addMessage("Unable to create account for: " + user.getUserEmail());
				  fRes.setSuccess(false);
	    	  }
	      }

		  if (!fRes.wasSuccessful()) {
			  fRes.setRedirectJSP("/sign_up.jsp");
		  }
		  
		  return;
	  }
}
