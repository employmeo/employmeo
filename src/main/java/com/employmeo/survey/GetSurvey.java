package com.employmeo.survey;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

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

	private static Logger logger = Logger.getLogger("SurveyService");

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String doPost(
			// @FormParam("start_time") TimeStamp startTime,
			@FormParam("respondant_id") Long respondantId) {
		logger.info("processing with: " + respondantId);
		JSONObject json = new JSONObject();
		Respondant respondant = Respondant.getRespondantById(respondantId);

		if (respondant != null) {
			respondant.refreshMe(); // make sure to get latest and greatest from
									// DB
			if (respondant.getRespondantStatus() < Respondant.STATUS_STARTED) {
				respondant.setRespondantStatus(Respondant.STATUS_STARTED);
				respondant.setRespondantStartTime(new Timestamp(new Date().getTime()));
				respondant.mergeMe();
			} else if (respondant.getRespondantStatus() >= Respondant.STATUS_COMPLETED) {
				// TODO put in better error handling here.
				json.put("message", "This assessment has already been completed and submitted");
			}

			Survey survey = respondant.getSurvey();
			json.put("survey", survey.getJSON());
			json.put("respondant", respondant.getJSON());
			List<Response> responses = respondant.getResponses();
			for (int i = 0; i < responses.size(); i++)
				json.accumulate("responses", responses.get(i).getJSON());
		} else {
			// TODO put in better error handling here.
			json.put("message", "Unable to associate this link with an assessment.");
		}

		return json.toString();
	}

}