package com.employmeo.survey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


import com.employmeo.objects.Respondant;
import com.employmeo.objects.Response;

@Path("response")
public class AcceptResponse {
	
	private static final Logger log = LoggerFactory.getLogger(AcceptResponse.class);
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String doGet(@QueryParam("response_id") Long responseId,
			@QueryParam("response_respondant_id") Long respondantId,
			@QueryParam("response_question_id") Long questionId, @QueryParam("response_value") int responseVal,
			@QueryParam("response_text") String responseText) {

		return saveResponse(responseId, respondantId, questionId, responseVal, responseText);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String doPost(@FormParam("response_id") Long responseId,
			@FormParam("response_respondant_id") Long respondantId, @FormParam("response_question_id") Long questionId,
			@FormParam("response_value") int responseVal, @FormParam("response_text") String responseText) {

		return saveResponse(responseId, respondantId, questionId, responseVal, responseText);
	}

	private String saveResponse(Long responseId, Long respondantId, Long questionId, int responseVal,
			String responseText) {
		log.trace("Processing Respondant: " + respondantId + " Question: " + questionId);
		
		Response response = new Response();
		response.setResponseRespondantId(respondantId);
		response.setResponseQuestionId(questionId);
		response.setResponseValue(responseVal);
		response.setResponseText(responseText);

		if (responseId != null) {
			response.setResponseId(responseId);
			response.mergeMe();
		} else {
			Respondant.getRespondantById(respondantId).addResponse(response);
			response.persistMe();
		}

		return response.getJSONString();
	}
}