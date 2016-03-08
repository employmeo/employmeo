package com.employmeo.actions;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.Account;
import com.employmeo.objects.Position;
import com.employmeo.objects.User;


public class GetPositionList extends MPFormAction {
	
	  public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		  
		  JSONArray response = new JSONArray();
		  // Collect expected input fields
		  User user = (User) sess.getAttribute("User");
		  Account account = user.getAccount();
		  if (account.getPositions().size()>0) {
			  List<Position> positions = account.getPositions();
			  for (int i=0;i<positions.size();i++) {
				  response.put(positions.get(i).getJSON());
			  }
		  }
		  
		  fRes.setSuccess(true);
		  fRes.setHTML(response.toString());
		  
		  return;
	  }

}
