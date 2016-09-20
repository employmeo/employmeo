package com.employmeo.survey;


import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import com.employmeo.objects.AccountSurvey;
import com.employmeo.objects.Survey;
import com.employmeo.util.DBUtil;
import com.employmeo.util.SurveyUtil;

@Path("list")
public class GetSurveyList {

	private static Logger logger = Logger.getLogger("com.employmeo.survey");

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getSurveyList() {
		logger.info("Fetching survey list");

		JSONArray response = new JSONArray();	
		List<Survey> surveys = getSurveys();
		
		surveys.forEach(survey-> {
			JSONObject surveyJson = new JSONObject();
			surveyJson.put("survey_name", survey.getSurveyName());
			surveyJson.put("survey_id", survey.getSurveyId());
			response.put(surveyJson);
		});

		return response.toString();		
	}

	private List<Survey> getSurveys() {
		return DBUtil.getEntityManager().createNamedQuery("Survey.findAll").getResultList();
	}

}
