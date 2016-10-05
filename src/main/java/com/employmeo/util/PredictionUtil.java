package com.employmeo.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.employmeo.objects.PositionProfile;
import com.employmeo.objects.PredictiveAlgorithm;
import com.employmeo.objects.Respondant;
import com.google.common.collect.ImmutableMap;

public class PredictionUtil {

	private static final Logger log = LoggerFactory.getLogger(PredictionUtil.class);
	private static final Map<PredictiveAlgorithm, Predictor> predictorRegistry = ImmutableMap
			.<PredictiveAlgorithm, Predictor>builder()
			.put(PredictiveAlgorithm.SIMPLE_LINEAR, new SimpleLinearPredictor()).build();

	/**
	 * For now, always return the same algorithm/predictor unconditionally. Down
	 * the line, we may run multiple algorithms for a/b/c testing of algorithms
	 * for the same assessment
	 * 
	 * @param respondant
	 * @return predictor
	 */
	private static Predictor findConfiguredPredictor(Respondant respondant) {
		Predictor assignedPredictor = predictorRegistry.get(PredictiveAlgorithm.SIMPLE_LINEAR);
		log.debug("Assigning predictor {} for respondant {}", assignedPredictor, respondant.getRespondantId());

		return assignedPredictor;
	}

	public static void predictRespondant(Respondant respondant) {
		log.debug("Predictions requested for respondant {}", respondant.getRespondantId());
		if (respondant.getRespondantStatus() <= Respondant.STATUS_SCORED)
			respondant.refreshMe();
		if (respondant.getRespondantStatus() == Respondant.STATUS_SCORED) {
			PredictionResults prediction = findConfiguredPredictor(respondant).runPredictions(respondant);
			Map<String, Double> positionProfileScores = prediction.getPositionProfileScores();

			respondant.setProfileD(positionProfileScores.get(PositionProfile.PROFILE_D));
			respondant.setProfileC(positionProfileScores.get(PositionProfile.PROFILE_C));
			respondant.setProfileB(positionProfileScores.get(PositionProfile.PROFILE_B));
			respondant.setProfileA(positionProfileScores.get(PositionProfile.PROFILE_A));
			respondant.setRespondantProfile(prediction.getRecommendedPositionProfile());
			respondant.setCompositeScore(prediction.getCompositeScore());

			respondant.setRespondantStatus(Respondant.STATUS_PREDICTED);
			respondant.mergeMe();
			log.debug("Predictions for respondant {} complete", respondant.getRespondantId());
		}

		return;
	}
}
