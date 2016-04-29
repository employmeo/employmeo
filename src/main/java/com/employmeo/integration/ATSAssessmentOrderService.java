package com.employmeo.integration;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.json.JSONObject;

import com.employmeo.objects.Person;
import com.employmeo.objects.Respondant;
import com.employmeo.objects.Survey;
import com.employmeo.util.SecurityUtil;

@Path("atsorder")
public class ATSAssessmentOrderService {
	
    @Context
    private UriInfo uriInfo;
    private static Logger logger = Logger.getLogger("RestService");

    @POST
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String doPost (JSONObject json)
	{
		Person person = new Person();
		Respondant respondant = new Respondant();
		System.out.println("Got in!!!");
    	try {
    	  JSONObject applicant = json.getJSONObject("applicant");
		  JSONObject delivery = json.getJSONObject("delivery");
		  person.setPersonEmail(applicant.getString("email"));
		  person.setPersonFname(applicant.getString("fname"));
		  person.setPersonLname(applicant.getString("lname"));
		  person.setPersonAddress(applicant.getString("address"));
		  person.setPersonLat(applicant.getDouble("lat"));
		  person.setPersonLong(applicant.getDouble("lng"));	  
		  respondant.setRespondantAccountId(applicant.getLong("account_id"));
		  respondant.setRespondantSurveyId(applicant.getLong("assessment_id"));
		  respondant.setRespondantLocationId(applicant.getLong("location_id"));// ok for null location
		  respondant.setRespondantPositionId(applicant.getLong("position_id"));// ok for null location
    	} catch (Exception e) {
    		throw new WebApplicationException(Response.Status.BAD_REQUEST);
    	}
		  // Perform business logic  

	
	//	  person.persistMe();
	//	  respondant.setPerson(person);
	//	  respondant.persistMe();
		  
		  JSONObject output = new JSONObject();
		  output.put("applicant", respondant.getJSON());
		  output.put("delivery", respondant.getJSON());	
	
		  // Perform business logic  
	
		  logger.log(Level.INFO, output.toString());
		  return output.toString();
  }
    
}