package com.employmeo.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.Account;
import com.employmeo.objects.Person;
import com.employmeo.objects.Respondant;
import com.employmeo.objects.User;
import com.employmeo.util.RandomizerUtil;

public class GetRespondants extends MPFormAction {
	
	  public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		  

		  // Collect expected input fields
		  User user = (User) sess.getAttribute("User");
		  Account account = user.getAccount();	  
		  
		  // Validate input fields
		  fRes.setValid(true);
		  
		  JSONArray response = new JSONArray();
		  

		  List<Respondant> respondants = account.getRespondants();
		  for (int j=0;j<respondants.size();j++) {
			  Person person = respondants.get(j).getPerson();
			  if (person.getPersonEmail() == null){
				  person.setPersonFname(RandomizerUtil.randomFname());
				  person.setPersonLname(RandomizerUtil.randomLname());
				  person.setPersonEmail(RandomizerUtil.randomEmail(person));
				  person.mergeMe();
			  }
			  response.put(respondants.get(j).getJSON());
		  }
		   
		  fRes.setSuccess(true);		  
		  fRes.setHTML(response.toString());
		  return;
	  }

}
