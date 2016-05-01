package com.employmeo.integration;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONObject;

import com.employmeo.objects.Person;
import com.employmeo.objects.Respondant;

@Path("atsorder")
public class ATSOrder {
	
    @Context
    private UriInfo uriInfo;
    @Context
    private Response resp;
    
    private final Response MISSING_REQUIRED_PARAMS = Response.status(Response.Status.BAD_REQUEST).entity("{ message: 'Missing Required Parameters' }").build();
    private static Logger logger = Logger.getLogger("RestService");

    @POST
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String doPost (JSONObject json)
	{
		Person person = new Person();
		Respondant respondant = new Respondant();
		JSONObject applicant = null;
		JSONObject address = null;
		JSONObject account = null;
		JSONObject location = json.optJSONObject("location");
		JSONObject position = json.optJSONObject("position");
		JSONObject assessment = json.optJSONObject("assessment");
	    JSONObject delivery = json.optJSONObject("delivery");
	    String atsclientId = null;
	    
    	try { // the required parameters
    		applicant = json.getJSONObject("applicant");
    		person.setPersonEmail(applicant.getString("email"));
    		person.setPersonFname(applicant.getString("fname"));
    		person.setPersonLname(applicant.getString("lname"));
    		address = applicant.getJSONObject("address");
    	} catch (Exception e) {
    		throw new WebApplicationException(e, MISSING_REQUIRED_PARAMS);
    	}

    	
    	person.setPersonAddress("");
		person.setPersonLat(applicant.getDouble("lat"));
		person.setPersonLong(applicant.getDouble("lng"));
		respondant.setRespondantAccountId((long)123);   	
		respondant.setRespondantSurveyId(applicant.getLong("assessment_id"));
		respondant.setRespondantLocationId(applicant.getLong("location_id"));// ok for null location
		respondant.setRespondantPositionId(applicant.getLong("position_id"));// ok for null location

		// Perform business logic  
	
		// person.persistMe();
		  respondant.setPerson(person);
		// respondant.persistMe();
		  
		JSONObject output = new JSONObject();
		output.put("applicant", respondant.getJSON());
		output.put("delivery", respondant.getJSON());	
	
		// Perform business logic  
	
		logger.log(Level.INFO, output.toString());
		return output.toString();
  }
    
}