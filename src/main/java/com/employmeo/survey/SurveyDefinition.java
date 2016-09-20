package com.employmeo.survey;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.json.JSONObject;

import com.employmeo.objects.Survey;
import com.employmeo.util.SurveyUtil;

@Path("definition")
public class SurveyDefinition {

	private static Logger logger = Logger.getLogger("com.employmeo.survey");

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSurveyDefinition(@QueryParam("survey_id") Long surveyId) {
		logger.info("processing with survey_id: " + surveyId);
		
		// initialize as Not Found
		ResponseBuilder responseBuilder = Response.status(Response.Status.NOT_FOUND);
		
		JSONObject json = new JSONObject();
		Survey survey = Survey.getSurveyById(surveyId);
		if(null != survey) {
			survey.refreshMe();
			json.put("survey", survey.getJSON());
			responseBuilder = Response.status(Response.Status.OK).entity(survey.getJSONString());
		}
		
		return responseBuilder.build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createSurveyDefinition(String surveyDefinition) {
		logger.info("Creating new survey definition");
		ResponseBuilder responseBuilder = Response.status(Response.Status.BAD_REQUEST);
		
		if(null != surveyDefinition) {
			JSONObject json = new JSONObject(surveyDefinition);
			logger.info("Hydrated JSONObject: " + json);
			
			Survey survey = Survey.fromJSON(json);
			logger.info("processing new survey definition with id: " + survey.getSurveyId());
			
			// TODO: error handling and propagation
			try {
				SurveyUtil.persistSurvey(survey);
				responseBuilder = Response.status(Response.Status.OK);
			} catch (Exception e) {
				logger.info("Failed to persist survey. " + e);
				responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			}			
		}
		
		return responseBuilder.build();
	}
	
}
