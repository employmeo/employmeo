package com.employmeo.util;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.employmeo.objects.PositionProfile;
import com.employmeo.objects.PredictionModel;
import com.employmeo.objects.Respondant;

public class PredictionUtil {

	private static final Logger log = LoggerFactory.getLogger(PredictionUtil.class);

	/**
	 * For now, always return the same algorithm/predictor unconditionally. Down
	 * the line, we may run multiple algorithms for a/b/c testing of algorithms
	 * for the same assessment
	 * 
	 * @param respondant
	 * @return predictor
	 */
	private static Predictor findConfiguredPredictor(Respondant respondant) {
		log.debug("Determining configured predictor for respondant {}", respondant.getRespondantId());
		EntityManager em = DBUtil.getEntityManager();
		List<PredictionModel> persistedModels = em.createNamedQuery("PredictionModel.findAll").getResultList();
		log.debug("All persisted models configured in the system: {}", persistedModels);
		PredictionModel predictionModel = persistedModels.stream().filter(pm -> "simple_linear".equals(pm.getName())
				&& 1 == pm.getVersion() && "linear".equals(pm.getModelType()) && true == pm.getActive()).findAny()
				.orElse(null);
		log.debug("Configured predictionModel for respondant {} is {}", respondant.getRespondantId(), predictionModel);

		PredictionModelAlgorithm assignedAlgorithm = predictionModel.getAlgorithm();
		log.debug("Assigned algorithm for respondant {} is {}", respondant.getRespondantId(), assignedAlgorithm);

		Optional<Predictor> registeredPredictor = PredictionModelRegistry.getPredictorFor(assignedAlgorithm);
		log.debug("Registered predictor {} for respondant {}", registeredPredictor, respondant.getRespondantId());

		return registeredPredictor.orElseThrow(() -> new IllegalStateException(
				"No predictors registered for respondant configuration: " + respondant.getRespondantId()));
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
