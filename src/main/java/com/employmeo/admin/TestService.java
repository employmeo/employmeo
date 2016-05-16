package com.employmeo.admin;

import javax.annotation.security.PermitAll;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;

import com.employmeo.objects.Account;
import com.employmeo.objects.Survey;
import com.employmeo.objects.User;
import com.employmeo.util.EmailServletResponse;
import com.employmeo.util.EmailUtility;
import com.sendgrid.SendGrid;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("test")
@PermitAll
public class TestService {

	private static Logger logger = Logger.getLogger("TestService");

	@GET
	  public String doMethod ()
	  {

		SendGrid sendGrid = EmailUtility.getSendGrid();
		SendGrid.Email email = new SendGrid.Email();
	    email.setFrom("info@employmeo.com");
	    email.addTo("sridharkaza@gmail.com");
	    email.setTemplateId("8e5983ac-913d-4370-8ea9-312ff8665f39");
	    email.setSubject("Assessment Complete");
	    
	    email.addSubstitution("[APPLICANT_NAME]", new String[] {"Bob Jones"});
	    email.addSubstitution("[LINK_TO_RESULTS]", new String[] {"https://employmeo.herokuapp.com/respondant_score.jsp?respondant_id=468"});
	    email.addSubstitution("[ACCOUNT_NAME]", new String[] {"Test Company"});
	    
	    email.setText("Body of Message Goes Here");
	    email.setHtml("HTML");
			
		try {
			SendGrid.Response response = sendGrid.send(email);
			logger.log(Level.INFO, "Sent: " + email.getSubject() + "(RC: " + response.getCode() + ") "+ response.getMessage());
			
		} catch (Exception e) {
			// TODO - log and plan to resubmit email on failures.
			logger.severe("Error sending email: " + e.getMessage());
		}

		return email.getText();
	  }	  
}