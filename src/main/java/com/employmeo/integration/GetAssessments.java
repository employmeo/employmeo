package com.employmeo.integration;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.employmeo.objects.Account;
import com.employmeo.objects.Survey;
import com.employmeo.util.PartnerUtil;

import java.util.List;
import java.util.logging.Logger;

@Path("getassessments")
public class GetAssessments {

	private final Response MISSING_REQUIRED_PARAMS = Response.status(Response.Status.BAD_REQUEST).entity("{ message: 'Missing Required Parameters' }").build();
    private static Logger logger = Logger.getLogger("RestService");
	
	  @POST
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON)
	  public String doPost (JSONObject json)
	  {  
		    Account account = null;
		    
	    	try { // the required parameters
	    		account = PartnerUtil.getAccountFrom(json.getJSONObject("account"));	    		
	    	} catch (Exception e) {
	    		throw new WebApplicationException(e, MISSING_REQUIRED_PARAMS);
	    	}

	    	JSONArray response = new JSONArray();
		  
		  if (account.getSurveys().size()>0) {
			  List<Survey> surveys = account.getSurveys();
			  for (int i=0;i<surveys.size();i++) {
				  response.put(surveys.get(i).getJSON());
			  }
		  } 
		  return response.toString();
	  }	  
}