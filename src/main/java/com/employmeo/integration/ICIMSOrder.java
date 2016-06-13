package com.employmeo.integration;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import com.employmeo.objects.Person;
import com.employmeo.objects.Respondant;
import com.employmeo.util.SecurityUtil;

@Path("icimsorder")
public class ICIMSOrder {
	@Context
	private UriInfo uriInfo;
	private static Logger logger = Logger.getLogger("RestService");

	@POST
	@RolesAllowed("atspartner")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String doPost(JSONObject json) {

		String returnUrl = json.getString("returnURL");
		String customerId = json.getString("customerId"); // Customer ID
															// Specifies the ID
															// of the customer
		String userId = json.getString("userId"); // User System ID Specifies
													// the candidate who
													// initiated the request
		JSONArray links = json.getJSONArray("links"); // Specifies the exact GET
														// request necessary to
														// return additional
														// information for an
														// entity.
		String eventType = json.getString("eventType");
		String systemId = json.getString("systemId");

		JSONObject applicant = json.getJSONObject("applicant");
		// JSONObject delivery = json.getJSONObject("delivery");
		// Validate input fields
		if (applicant.has("email")) {

		}

		// Perform business logic
		Person person = new Person();
		Respondant respondant = new Respondant();
		person.setPersonEmail(applicant.getString("email"));
		person.setPersonFname(applicant.getString("fname"));
		person.setPersonLname(applicant.getString("lname"));
		person.setPersonAddress(applicant.getString("address"));
		person.setPersonLat(applicant.getDouble("lat"));
		person.setPersonLong(applicant.getDouble("lng"));
		respondant.setRespondantAccountId(applicant.getLong("account_id"));
		respondant.setRespondantAsid(applicant.getLong("assessment_id"));
		respondant.setRespondantLocationId(applicant.getLong("location_id"));// ok
																				// for
																				// null
																				// location
		respondant.setRespondantPositionId(applicant.getLong("position_id"));// ok
																				// for
																				// null
																				// location

		// person.persistMe();
		// respondant.setPerson(person);
		// respondant.persistMe();

		JSONObject output = new JSONObject();
		output.put("applicant", respondant.getJSON());
		output.put("delivery", respondant.getJSON());

		// Perform business logic

		logger.log(Level.INFO, output.toString());
		return output.toString();
	}
}
