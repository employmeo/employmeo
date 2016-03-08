package com.employmeo.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.Account;

public class CreateAccount extends MPFormAction {
	
	  public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		  
		  Account account = new Account();

		  // Collect expected input fields
		  String accountName = req.getParameter("account_name");
		  String accountCurrency = req.getParameter("account_currency");
		  String accountTimezone = req.getParameter("account_timezone");
		  
		  // Validate input fields
		  fRes.setValid(true);
		  
		  // Perform business logic  
		  account.setAccountCurrency(accountCurrency);
		  account.setAccountName(accountName);
		  account.setAccountTimezone(accountTimezone);
		  account.persistMe();
		  fRes.setSuccess(true);
		  
		  JSONObject json = new JSONObject();
		  json.put("account", account.getJSON());
		  json.accumulate("message", "created new account " + account.getAccountId());
		  
		  fRes.setHTML(json.toString());
		  return;
	  }

}
