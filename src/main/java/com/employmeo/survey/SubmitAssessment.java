package com.employmeo.survey;

import javax.annotation.security.PermitAll;
import javax.json.JsonObject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.employmeo.objects.Respondant;

@Path("submitassessment")
public class SubmitAssessment {

	  @PermitAll
	  @POST
	  @Produces(MediaType.APPLICATION_JSON)
	  public String doPost (
//			  @FormParam("finish_time") TimeStamp finishTime,
			  @FormParam("respondant_id") Long respondantId
			  )
	  {
		  Respondant respondant = Respondant.getRespondantById(respondantId);
		  if (respondant.getRespondantStatus() < Respondant.STATUS_COMPLETED) {
			  respondant.setRespondantStatus(Respondant.STATUS_COMPLETED);
//				respondant.setFinishTime(finishTime);
			  respondant.mergeMe();
		  }
		  
		  postScores(respondant);
		  
		  return respondant.getJSONString();
	 }

	private void postScores(Respondant respondant) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080").path("integration/echo");
		try {
			String result = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(respondant.getJSONString(),MediaType.APPLICATION_JSON),String.class);
			System.out.println(result);
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
}