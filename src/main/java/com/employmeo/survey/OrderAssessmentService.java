package com.employmeo.survey;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.employmeo.objects.Person;
import com.employmeo.objects.Respondant;
import com.employmeo.objects.Survey;

@Path("order")
public class OrderAssessmentService {
  private static Logger logger = Logger.getLogger("RestService");

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public String doPost (
		  @FormParam("email") String to,
		  @FormParam("fname")String fname,
		  @FormParam("lname") String lname,
		  @FormParam("address") String address,
		  @FormParam("lat") Double personLat,
		  @FormParam("lng") Double personLong, 
		  @FormParam("survey_id") Long surveyId,
		  @FormParam("location_id") Long locationId,
		  @FormParam("position_id") Long positionId,
		  @FormParam("account_id") Long accountId  
		  )
  {

	  // Validate input fields
	  
	  // Perform business logic  
	  Person applicant = new Person();
	  applicant.setPersonEmail(to);
	  applicant.setPersonFname(fname);
	  applicant.setPersonLname(lname);
	  applicant.setPersonAddress(address);
	  applicant.setPersonLat(personLat);
	  applicant.setPersonLong(personLong);
	  applicant.persistMe();
	  
	  Respondant respondant = new Respondant();
	  respondant.setPerson(applicant);
	  respondant.setRespondantAccountId(accountId);
	  respondant.setRespondantSurveyId(surveyId);
	  respondant.setRespondantLocationId(locationId);// ok for null location
	  respondant.setRespondantPositionId(positionId);// ok for null location
	  respondant.persistMe();
	  
	  JSONObject json = new JSONObject();
	  json.put("person", applicant.getJSON());
	  json.put("respondant", respondant.getJSON());	
	  json.put("survey", Survey.getSurveyById(surveyId).getJSON());
	  
	  logger.log(Level.INFO, json.toString());
	  return json.toString();
  }
    
}