package com.employmeo.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.Location;
import com.employmeo.objects.User;

public class CreateLocation extends MPFormAction {
	
	  public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		  
		  Location location = new Location();
		  User user = (User) sess.getAttribute("User");

		  // Collect expected input fields
		  String locationLong = req.getParameter("location_long");
		  if (locationLong != null) {
			 double locLong = Double.valueOf(locationLong);
			 location.setLocationLong(locLong);
		  }
		  String locationLat = req.getParameter("location_lat");
		  if (locationLat != null) {
			 double locLat = Double.valueOf(locationLat);
			 location.setLocationLat(locLat);
		  }
		  
		  location.setLocationName(req.getParameter("location_name"));
		  location.setLocationClientAtsId(req.getParameter("location_client_ats_id"));
		  location.setLocationClientPayrollId(req.getParameter("location_client_payroll_id"));
		  location.setLocationAccountId(user.getAccount().getAccountId());
		  location.setLocationStreet1(req.getParameter("location_street1"));
		  location.setLocationStreet2(req.getParameter("location_street2"));
		  location.setLocationCity(req.getParameter("location_city"));
		  location.setLocationState(req.getParameter("location_state"));
		  location.setLocationZip(req.getParameter("location_zip"));
		  // Validate input fields
		  fRes.setValid(true);
		  
		  // Perform business logic  
		  location.mergeMe();
		  fRes.setSuccess(true);
		  
		  JSONObject json = new JSONObject();
		  json.put("location", location.getJSON());
		  json.accumulate("message", "created new location " + location.getLocationId());
		  
		  fRes.setHTML(json.toString());
		  return;
	  }

}
