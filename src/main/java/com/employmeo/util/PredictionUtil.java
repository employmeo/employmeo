package com.employmeo.util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.employmeo.objects.Corefactor;
import com.employmeo.objects.CorefactorScore;
import com.employmeo.objects.PositionPredictionConfiguration;
import com.employmeo.objects.Prediction;
import com.employmeo.objects.PredictionModel;
import com.employmeo.objects.PredictionTarget;
import com.employmeo.objects.Respondant;
import com.employmeo.objects.RespondantScore;
import com.google.common.collect.Lists;

import lombok.NonNull;

public class PredictionUtil {

	private static final Logger log = LoggerFactory.getLogger(PredictionUtil.class);
	
	public static void predictRespondant(Respondant respondant) {
		log.debug("Predictions requested for respondant {}", respondant.getRespondantId());
		if (respondant.getRespondantStatus() <= Respondant.STATUS_SCORED)
			respondant.refreshMe();
		if (respondant.getRespondantStatus() == Respondant.STATUS_SCORED) {

			// Stage 1
			List<PredictionResult> predictions = runPredictionsStageForAllTargets(respondant);
			
			// Stage 2
			GradingResult gradingResult = GradingUtil.gradeRespondantByPredictions(respondant, predictions);

			// Assimilate results, Update respondant lifecycle, and persist state
			respondant.setRespondantProfile(gradingResult.getRecommendedProfile());
			respondant.setCompositeScore(gradingResult.getCompositeScore());
			respondant.setRespondantStatus(Respondant.STATUS_PREDICTED);
			respondant.mergeMe();
			
			log.debug("Predictions for respondant {} complete", respondant.getRespondantId());
		}

		return;
	}	
	

	private static List<PredictionResult> runPredictionsStageForAllTargets(Respondant respondant) {
		List<PredictionResult> predictions = Lists.newArrayList();
		List<CorefactorScore> corefactorScores = getCorefactorScores(respondant);
		
		List<PositionPredictionConfiguration> positionPredictionConfigs = respondant.getPosition().getPositionPredictionConfigs();
		positionPredictionConfigs.forEach(predictionConfig -> {	
			try {
				PredictionResult predictionResult = predictForTarget(respondant, corefactorScores, predictionConfig);
				predictions.add(predictionResult);
			} catch(Exception e) {
				log.warn("Failed to run predictions for respondant " + respondant.getRespondantId() + " for target " + predictionConfig.getPredictionTarget() + ". Continuing with run.", e);
			}
		});
		
		return predictions;
	}


	private static PredictionResult predictForTarget(Respondant respondant, List<CorefactorScore> corefactorScores,
			PositionPredictionConfiguration predictionConfig) {
		PredictionTarget predictionTarget = predictionConfig.getPredictionTarget();
		PredictionModel predictionModel = predictionConfig.getPredictionModel();			
		PredictionModelEngine predictionEngine = getPredictionModelEngine(predictionModel);
		
		log.debug("Initiating predictions run for respondant {} and target {} with predictionEngine {} for position {} at location {} with corefactorScores as {}",
				respondant.getRespondantId(), predictionTarget.getName(), predictionEngine, respondant.getPosition().getPositionName(), respondant.getLocation().getLocationName(), corefactorScores);

		PredictionResult predictionResult = predictionEngine.runPredictions(respondant, respondant.getPosition(), respondant.getLocation(), corefactorScores);
		predictionResult.setModelName(predictionModel.getName());
		predictionResult.setPredictionTarget(predictionTarget);
		
		log.info("Prediction for respondant {} for position {} : {}",
				respondant.getRespondantId(), respondant.getPosition().getPositionName(), predictionResult);

		savePrediction(respondant, predictionConfig, predictionResult);
		return predictionResult;
	}


	private static void savePrediction(Respondant respondant, 
			PositionPredictionConfiguration predictionConfig,
			PredictionResult predictionResult) {
		
		Prediction prediction = new Prediction();
		prediction.setRespondant(respondant);
		prediction.setPositionPredictionConfig(predictionConfig);
		prediction.setPredictionScore(predictionResult.getScore());
		prediction.persistMe();
		
		log.debug("Prediction persisted: {}", prediction);
	}

	private static PredictionModelEngine getPredictionModelEngine(@NonNull PredictionModel predictionModel) {	
		Optional<PredictionModelEngine> registeredPredictionEngine = PredictionModelRegistry.getPredictionModelEngineByName(predictionModel.getName());
		
		log.debug("Retrieved {} as prediction engine for {}", registeredPredictionEngine, predictionModel.getName() );
		return registeredPredictionEngine.orElseThrow(() -> new IllegalStateException(
				"No prediction engines registered for prediction target: " + predictionModel.getName()));
	}	

	
	private static List<CorefactorScore> getCorefactorScores(Respondant respondant) {
		List<CorefactorScore> corefactorScores = respondant.getRespondantScores().stream()
					.map(rs -> getCorefactorById(rs))
					.collect(Collectors.toList());
		
		log.debug("CorefactorScores for respondant {} are {}", respondant.getRespondantId(), corefactorScores);
		return corefactorScores;				
	}
	
	private static CorefactorScore getCorefactorById(RespondantScore rs) {
		EntityManager em = DBUtil.getEntityManager();
		Corefactor cf = em.createNamedQuery("Corefactor.findById", Corefactor.class)
				.setParameter("cfId", rs.getRsCfId())
				.getSingleResult();
		
		CorefactorScore cfScore = new CorefactorScore(cf, rs.getRsValue());
		return cfScore;
	}
}
