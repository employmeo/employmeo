package com.employmeo.actions;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.Account;
import com.employmeo.objects.Position;
import com.employmeo.objects.User;


public class CreatePosition extends MPFormAction {
	
	  public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		  

		  // Collect expected input fields
		  User user = (User) sess.getAttribute("User");
		  Account account = user.getAccount();	  
		  String positionName = req.getParameter("position_name");
		  String hireRatio = req.getParameter("position_target_hireratio");
		  String tenure = req.getParameter("position_target_hireratio");
		  
		  // Validate input fields
		  fRes.setValid(true);
		  

		  // Perform business logic  		  
		  Position position = new Position();
		  position.setAccount(account);
		  position.setPositionName(positionName);
		  if (hireRatio != null) {
			  position.setPositionTargetHireratio(new BigDecimal(hireRatio));
		  }
		  if (tenure != null) {
			  position.setPositionTargetTenure(new BigDecimal(tenure));
		  }
		  position.persistMe();  
		  fRes.setSuccess(true);
		  

		  JSONObject json = new JSONObject();
		  json.put("position", position.getJSON());
		  json.accumulate("message", "created new position " + position.getPositionId());
		  
		  fRes.setHTML(json.toString());
		  return;
	  }

}
