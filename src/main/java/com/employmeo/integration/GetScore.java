package com.employmeo.integration;

import java.util.logging.Logger;

import javax.annotation.security.PermitAll;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.employmeo.objects.Account;
import com.employmeo.objects.Position;
import com.employmeo.objects.Respondant;
import com.employmeo.util.PartnerUtil;

@Path("getscore")
@PermitAll
public class GetScore {
    private final Response MISSING_REQUIRED_PARAMS = Response.status(Response.Status.BAD_REQUEST).entity("{ message: 'Missing Required Parameters' }").build();
    private final Response ACCOUNT_MATCH = Response.status(Response.Status.CONFLICT).entity("{ message: 'Applicant ID not found for Account ID' }").build();
    private static Logger logger = Logger.getLogger("RestService");
	
	  @POST
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON)
	  public String doPost (JSONObject json)
	  {  
		  Account account = null;
		  Respondant respondant = null;
		   
	    try { // the required parameters
	    	account = PartnerUtil.getAccountFrom(json.getJSONObject("account"));
	    	respondant = PartnerUtil.getRespondantFrom(json.getJSONObject("applicant"));
	    } catch (Exception e) {
	    	throw new WebApplicationException(e, MISSING_REQUIRED_PARAMS);
	    }
	    	
	      if (account.getAccountId() != respondant.getRespondantAccountId()) {
	    		throw new WebApplicationException(ACCOUNT_MATCH);	    	  
	      }
	      
		  JSONObject jresp = respondant.getJSON();	      
		  jresp.put("scores", respondant.scoreMe());
	  
		  return jresp.toString();
	  }	  
}