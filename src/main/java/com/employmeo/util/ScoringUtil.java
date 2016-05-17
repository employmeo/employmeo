package com.employmeo.util;

import java.util.List;

import com.employmeo.objects.PositionProfile;
import com.employmeo.objects.Question;
import com.employmeo.objects.Respondant;
import com.employmeo.objects.RespondantScore;
import com.employmeo.objects.Response;

public class ScoringUtil {

	public static void scoreAssessment(Respondant respondant) {

		List<Response> responses = respondant.getResponses();
		if ((responses == null) || (responses.size() == 0)) return; // return nothing when survey incomplete
		int[] count = new int[20];
		int[] score = new int[20];

		for (int i = 0; i < responses.size(); i++) {
			Response response = responses.get(i);
			Integer cfId = Question.getQuestionById(response.getResponseQuestionId()).getQuestionCorefactorId();
			count[cfId]++;
			score[cfId] += response.getResponseValue();
		}
		for (int i = 0; i < 20; i++) {
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

	public static void predictRespondant(Respondant respondant) {

		if (respondant.getRespondantStatus() == Respondant.STATUS_SCORED) {

			// TODO - replace random logic with real scoring algorithm
			// Position position =
			// Position.getPositionById(respondant.getRespondantPositionId());
			// Location location =
			// Location.getLocationById(respondant.getRespondantLocationId());

			double d = Math.random();
			double b = Math.random();
			double c = Math.random();
			double a = Math.random();
			double highest = 0;

			respondant.setProfileD(d / (a + b + c + d));
			highest = d;
			respondant.setRespondantProfile(PositionProfile.PROFILE_D);

			respondant.setProfileC(c / (a + b + c + d));
			if (c > highest) {
				highest = d;
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
