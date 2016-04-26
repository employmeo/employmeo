package com.employmeo.actions;


import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;


import com.employmeo.EmpFormResponse;
import com.employmeo.objects.Account;
import com.employmeo.objects.Location;
import com.employmeo.objects.Position;
import com.employmeo.objects.PositionProfile;
import com.employmeo.objects.Respondant;
import com.employmeo.objects.User;
import com.employmeo.util.PredictionUtil;



public class TestAction extends MPFormAction {
	
	  public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		  
		  // Collect expected input fields
		  User user = (User) sess.getAttribute("User");
		  Account account = user.getAccount();	  
		  List<Position> positions = account.getPositions();
		  List<Location> locations = account.getLocations();
		  
		  // Validate input fields
		  fRes.setValid(true);
		  
		  JSONArray response = new JSONArray();

		  List<Respondant> respondants = account.getRespondants(Respondant.STATUS_COMPLETED,Respondant.STATUS_HIRED,250);
		  for (int j=0;j<respondants.size();j++) {
			  Respondant respondant = respondants.get(j);
			  
			  if (respondant.getRespondantLocationId() == null){
				  int index = (int) Math.floor((double) locations.size() * Math.random());
				  respondant.setRespondantLocationId(locations.get(index).getLocationId());
				  respondant.mergeMe();
			  }

			  if (respondant.getRespondantPositionId() == null){
				  int index = (int) Math.floor((double) locations.size() * Math.random());
				  respondant.setRespondantPositionId(positions.get(index).getPositionId());
				  respondant.mergeMe();
			  }

			  if (respondant.getRespondantProfile() == null) {
				  PredictionUtil.scoreRespondant(respondant);
			  }
			  
			  if (respondant.getRespondantStatus() == Respondant.STATUS_COMPLETED) {
			  switch(respondant.getRespondantProfile()) {
			  case PositionProfile.PROFILE_A:
				  if (Math.random() >0.05) {
					  respondant.setRespondantStatus(Respondant.STATUS_HIRED);
				  } else {
					  respondant.setRespondantStatus(Respondant.STATUS_REJECTED);					  
				  }
				  break;
			  case PositionProfile.PROFILE_B:
				  if (Math.random() >0.35) {
					  respondant.setRespondantStatus(Respondant.STATUS_HIRED);
				  } else {
					  respondant.setRespondantStatus(Respondant.STATUS_REJECTED);					  
				  }
				  break;
			  case PositionProfile.PROFILE_C:
				  if (Math.random() >0.75) {
					  respondant.setRespondantStatus(Respondant.STATUS_HIRED);
				  } else {
					  respondant.setRespondantStatus(Respondant.STATUS_REJECTED);					  
				  }
				  break;
			  case PositionProfile.PROFILE_D:
				  if (Math.random() >0.95) {
					  respondant.setRespondantStatus(Respondant.STATUS_HIRED);
				  } else {
					  respondant.setRespondantStatus(Respondant.STATUS_REJECTED);					  
				  }
				  break;
			  default:
				  break;
			  }
			  respondant.mergeMe();
			  }
			  
			  response.put(respondants.get(j).getJSON());
		  }
		   
		  fRes.setSuccess(true);		  
		  fRes.setHTML(response.toString());
		  return;
	  }
	  
}
