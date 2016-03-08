package com.employmeo.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.Account;
import com.employmeo.objects.User;
import com.employmeo.util.SecurityUtil;


public class CreateUser extends MPFormAction {
	
	  public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		  
		  User user = new User();

		  // Collect expected input fields
		  String userAccountId = req.getParameter("user_account_id");
		  String userEmail = req.getParameter("user_email");
		  String userPassword = req.getParameter("user_password");
		  String userFname = req.getParameter("user_fname");
		  String userLname = req.getParameter("user_lname");
		  String userLocale= req.getParameter("user_locale");
		  String userAvatarUrl= req.getParameter("user_avatar_url");
		  
		  // Validate input fields
		  if (userAccountId != null) {
			  user.setAccount(Account.getAccountById(userAccountId));
		  } else {
			  fRes.setValid(false);
		  }
		  
		  // Perform business logic  
		  user.setUserEmail(userEmail);
		  user.setUserPassword(SecurityUtil.hashPassword(userPassword));
		  user.setUserFname(userFname);
		  user.setUserLname(userLname);
		  user.setUserLocale(userLocale);
		  user.setUserAvatarUrl(userAvatarUrl);
		  user.persistMe();
		  
		  fRes.setSuccess(true);
		  
		  JSONObject json = new JSONObject();
		  json.put("user", user.getJSON());
		  json.accumulate("message", "created new user " + user.getUserId());
		  
		  fRes.setHTML(json.toString());
		  return;
	  }

}
