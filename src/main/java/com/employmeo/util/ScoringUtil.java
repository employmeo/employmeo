package com.employmeo.util;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.json.JSONArray;
import org.json.JSONObject;

import com.employmeo.objects.Corefactor;
import com.employmeo.objects.PositionProfile;
import com.employmeo.objects.Question;
import com.employmeo.objects.Respondant;
import com.employmeo.objects.RespondantScore;
import com.employmeo.objects.Response;

public class ScoringUtil {

	private static Logger logger = Logger.getLogger("ScoringUtil");
	
	public static void scoreAssessment(Respondant respondant) {

		List<Response> responses = respondant.getResponses();
		if ((responses == null) || (responses.size() == 0))	return; // return nothing

		if (respondant.getSurvey().getSurveyName().startsWith("Mercer")) { 
			JSONArray result = postMercerScores(responses);
			for (int i = 0; i < result.length(); i++) {
				RespondantScore rs = new RespondantScore();
				Corefactor cf = Corefactor.getCorefactorById(result.getJSONObject(i).getInt("id"));
				rs.setPK(cf.getCorefactorId(), respondant.getRespondantId());
				rs.setRsQuestionCount(responses.size());
				rs.setRsValue(result.getJSONObject(i).getDouble("value"));
				rs.mergeMe();
				respondant.addRespondantScore(rs);
			
			}
			respondant.setRespondantStatus(Respondant.STATUS_SCORED);
			respondant.mergeMe();
			return;
		}

		int[] count = new int[50];
		int[] score = new int[50];

		for (int i = 0; i < responses.size(); i++) {
			Response response = responses.get(i);
			Integer cfId = Question.getQuestionById(response.getResponseQuestionId()).getQuestionCorefactorId();
			count[cfId]++;
			score[cfId] += response.getResponseValue();
		}
		for (int i = 0; i < 50; i++) {
			if (count[i] > 0) {
				RespondantScore rs = new RespondantScore();
				rs.setPK(i, respondant.getRespondantId());
				rs.setRsQuestionCount(count[i]);
				rs.setRsValue((double) Math.round(100.0 * ((double) score[i] / (double) count[i])) / 100.0);
				rs.mergeMe();
				respondant.addRespondantScore(rs);
			}
		}
		respondant.setRespondantStatus(Respondant.STATUS_SCORED);
		respondant.mergeMe();

		return;
	}

	private static synchronized JSONArray postMercerScores(List<Response> responses) {
		Client client = ClientBuilder.newClient();
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("test", "password");
		client.register(feature);
		String postmethod = "http://localhost:8080/integration/echo";


		JSONArray message = new JSONArray();
		for (int i=0;i<responses.size();i++) {
			JSONObject jResp = new JSONObject();
			Question question = Question.getQuestionById(responses.get(i).getResponseQuestionId());
			jResp.put("response_value", responses.get(i).getResponseValue());
			jResp.put("question_id", question.getQuestionForeignId());
			jResp.put("test_name", question.getQuestionForeignSource());
			message.put(jResp);
		}

logger.info("sending scores to mercer (echo): " + message);

		WebTarget target = client.target(postmethod);
		String result = target.request(MediaType.APPLICATION_JSON)
						.post(Entity.entity(message.toString(), MediaType.APPLICATION_JSON), String.class);

		return new JSONArray(result);
	}

	public static void predictRespondant(Respondant respondant) {
		if (respondant.getRespondantStatus() <= Respondant.STATUS_SCORED) respondant.refreshMe();
		if (respondant.getRespondantStatus() == Respondant.STATUS_SCORED) {

			// TODO - replace random logic with real scoring algorithm
			// Position position =
			// Position.getPositionById(respondant.getRespondantPositionId());
			// Location location =
			// Location.getLocationById(respondant.getRespondantLocationId());

			double d = Math.random();
			double c = Math.random() * 2;
			double b = Math.random() * 1.5;
			double a = Math.random() / 1.5;
			double highest = 0;

			respondant.setProfileD(d / (a + b + c + d));
			highest = d;
			respondant.setRespondantProfile(PositionProfile.PROFILE_D);

			respondant.setProfileC(c / (a + b + c + d));
			if (c > highest) {
				highest = c;
				respondant.setRespondantProfile(PositionProfile.PROFILE_C);
			}

			respondant.setProfileB(b / (a + b + c + d));
			if (b > highest) {
				highest = b;
				respondant.setRespondantProfile(PositionProfile.PROFILE_B);
			}

			respondant.setProfileA(a / (a + b + c + d));
			if (a > highest) {
				highest = a;
				respondant.setRespondantProfile(PositionProfile.PROFILE_A);
			}

			respondant.setRespondantStatus(Respondant.STATUS_PREDICTED);
			respondant.mergeMe();
		}

		return;
	}

}
