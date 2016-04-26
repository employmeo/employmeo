package com.employmeo.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.Account;
import com.employmeo.objects.Person;
import com.employmeo.objects.PositionProfile;
import com.employmeo.objects.Respondant;
import com.employmeo.objects.User;
import com.employmeo.util.RandomizerUtil;

public class GetLastTenRespondants extends MPFormAction {
	
	  public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		  

		  // Collect expected input fields
		  User user = (User) sess.getAttribute("User");
		  Account account = user.getAccount();	  
		  
		  // Validate input fields
		  fRes.setValid(true);
		  
		  JSONArray response = new JSONArray();
		  

		  List<Respondant> respondants = account.getRespondants(Respondant.STATUS_SCORED,Respondant.STATUS_HIRED,10);
		  for (int j=0;j<respondants.size();j++) {
			  Person person = respondants.get(j).getPerson();
			  if (person.getPersonEmail() == null){
				  person.setPersonFname(RandomizerUtil.randomFname());
				  person.setPersonLname(RandomizerUtil.randomLname());
				  person.setPersonEmail(RandomizerUtil.randomEmail(person));
				  person.mergeMe();
			  }
			  JSONObject resp = respondants.get(j).getJSON();
			  PositionProfile profile = PositionProfile.getProfileDefaults(respondants.get(j).getRespondantProfile());
			  resp.put("respondant_profile_icon", profile.get("profile_icon"));
			  resp.put("respondant_profile_class", profile.get("profile_class"));
			  response.put(resp);
		  }
		   
		  fRes.setSuccess(true);		  
		  fRes.setHTML(response.toString());
		  return;
	  }

}
