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

import com.employmeo.objects.Account;
import com.employmeo.objects.Location;
import com.employmeo.objects.Person;
import com.employmeo.objects.Position;
import com.employmeo.objects.Respondant;
import com.employmeo.objects.Survey;
import com.employmeo.util.AddressUtil;
import com.employmeo.util.PartnerUtil;

@Path("atsorder")
public class ATSOrder {
	
    @Context
    private UriInfo uriInfo;
    
    private final Response MISSING_REQUIRED_PARAMS = Response.status(Response.Status.BAD_REQUEST).entity("{ message: 'Missing Required Parameters' }").build();
    private static Logger logger = Logger.getLogger("RestService");

    @POST
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String doPost (JSONObject json)
	{
		JSONObject applicant = null;
	    Account account = null;
	    Person person = new Person();
		Respondant respondant = new Respondant();
	    
    	try { // the required parameters
    		account = PartnerUtil.getAccountFrom(json.getJSONObject("account"));
    		applicant = json.getJSONObject("applicant");
    		person.setPersonSsn(applicant.getString("applicant_ats_id"));
    		person.setPersonEmail(applicant.getString("email"));
    		person.setPersonFname(applicant.getString("fname"));
    		person.setPersonLname(applicant.getString("lname"));
    		
    		JSONObject personAddress = applicant.getJSONObject("address");
    		AddressUtil.validate(personAddress);
    		
        	person.setPersonAddress(personAddress.optString("address"));
    		person.setPersonLat(personAddress.optDouble("lat"));
    		person.setPersonLong(personAddress.optDouble("lng"));
    	} catch (Exception e) {
    		throw new WebApplicationException(e, MISSING_REQUIRED_PARAMS);
    	}

	    Location location = PartnerUtil.getLocationFrom(json.optJSONObject("location"), account);   	
	    Position position = PartnerUtil.getPositionFrom(json.optJSONObject("position"), account);
	    Survey survey = PartnerUtil.getSurveyFrom(json.optJSONObject("assessment"), account);

    	JSONObject delivery = json.optJSONObject("delivery");
    	
		respondant.setRespondantAccountId(account.getAccountId());   	
		respondant.setRespondantSurveyId(survey.getSurveyId());
		respondant.setRespondantLocationId(location.getLocationId());// ok for null location
		respondant.setRespondantPositionId(position.getPositionId());// ok for null location

		// Perform business logic  
	
		person.persistMe();
		respondant.setPerson(person);
		respondant.persistMe();
		  
		JSONObject output = new JSONObject();
		output.put("applicant", respondant.getJSON());
		output.put("delivery", respondant.getJSON());	
	
		// Perform business logic  
	
		logger.log(Level.INFO, output.toString());
		return output.toString();
  }
    
}