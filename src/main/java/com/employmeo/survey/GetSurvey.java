package com.employmeo.survey;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.employmeo.objects.Respondant;
import com.employmeo.objects.Response;
import com.employmeo.objects.Survey;

@Path("getsurvey")
public class GetSurvey {

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public String doPost (
//		  @FormParam("start_time") TimeStamp startTime,
		  @FormParam("respondant_id") Long respondantId )
  {
	  return getSurvey(respondantId);
  }
  
  private String getSurvey(Long respondantId) {
	  JSONObject json = new JSONObject();

	  if (respondantId != null) {
		  Respondant respondant = Respondant.getRespondantById(respondantId);
		  
		  if (respondant.getRespondantStatus() < Respondant.STATUS_STARTED) {
			  respondant.setRespondantStatus(Respondant.STATUS_STARTED);
//				respondant.setStartTime(startTime);
			  respondant.mergeMe();
		  }

		  Survey survey = respondant.getSurvey();
		  json.put("survey", survey.getJSON());
		  json.put("respondant", respondant.getJSON());
		  List<Response> responses = respondant.getResponses();
		  for (int i= 0;i<responses.size();i++) json.accumulate("responses", responses.get(i).getJSON());
	  }
	  return json.toString();
 }
    
}