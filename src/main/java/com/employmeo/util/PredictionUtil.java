package com.employmeo.util;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.employmeo.objects.PositionProfile;
import com.employmeo.objects.PositionTarget;
import com.employmeo.objects.PredictionModel;
import com.employmeo.objects.PredictionModelTarget;
import com.employmeo.objects.PredictionTarget;
import com.employmeo.objects.Respondant;

import lombok.NonNull;

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
	private static List<PredictionTarget> findPredictionTargets(Respondant respondant) {
		log.debug("Determining configured predictor for respondant {}", respondant.getRespondantId());
		
		List<PositionTarget> positonTargets = respondant.getPosition().getPositionTargets();
		List<PredictionTarget> predictionTargets = positonTargets.stream().map(pt -> pt.getPredictionTarget()).collect(Collectors.toList());

		if(predictionTargets.isEmpty()) {
			throw new IllegalStateException("No prediction targets configured for position " +  respondant.getPosition() + " for respondant " + respondant.getRespondantId());
		} 

		log.debug("Prediction Targets for respondant: {}", predictionTargets);
		return predictionTargets;
	}
	
	private static PredictionModelEngine getPredictionModelEngine(@NonNull PredictionTarget predictionTarget) {
		PredictionModel predictionModel = getPredictionModelForTarget(predictionTarget);		
		Optional<PredictionModelEngine> registeredPredictionEngine = PredictionModelRegistry
				.getPredictionModelEngineForAlgorithm(predictionModel.getAlgorithm());
		log.debug("For prediction target {}, prediction engine is {}", predictionTarget.getName(), registeredPredictionEngine );

		return registeredPredictionEngine.orElseThrow(() -> new IllegalStateException(
				"No prediction engines registered for prediction target: " + predictionTarget.getName()));
	}

	private static PredictionModel getPredictionModelForTarget(PredictionTarget predictionTarget) {
		EntityManager em = DBUtil.getEntityManager();
		PredictionModelTarget modelTarget = em.createNamedQuery("PredictionModelTarget.findByTargetName", PredictionModelTarget.class)
					.setParameter("targetName", predictionTarget.getName())
					.getSingleResult();
		PredictionModel predictionModel = modelTarget.getModel();
		log.debug("Prediction Model algorithm for target {} is {}", predictionTarget.getName(), predictionModel.getAlgorithm());
		return predictionModel;
	}
	
	public static void predictRespondant(Respondant respondant) {
		log.debug("Predictions requested for respondant {}", respondant.getRespondantId());
		if (respondant.getRespondantStatus() <= Respondant.STATUS_SCORED)
			respondant.refreshMe();
		if (respondant.getRespondantStatus() == Respondant.STATUS_SCORED) {
			List<PredictionTarget> predictionTargets = findPredictionTargets(respondant);
			PredictionModelEngine predictionEngine = getPredictionModelEngine(predictionTargets.get(0));
			PredictionResults prediction = predictionEngine.runPredictions(respondant);
			
			// record and reflect the results against the respondant
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
