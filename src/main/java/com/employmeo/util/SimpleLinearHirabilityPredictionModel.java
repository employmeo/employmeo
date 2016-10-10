package com.employmeo.util;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.employmeo.objects.CorefactorScore;
import com.employmeo.objects.Location;
import com.employmeo.objects.Position;
import com.employmeo.objects.Respondant;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class SimpleLinearHirabilityPredictionModel implements PredictionModelEngine {

	private static final Logger log = LoggerFactory.getLogger(SimpleLinearHirabilityPredictionModel.class);
	private static final String modelName = "simple_hirability_model";
	
	// y = intercept + slope*x
	private static final class ModelConfig {
		private Double intercept;
		private Double slope;
		
		private ModelConfig(Double intercept, Double slope) {
			this.intercept = intercept;
			this.slope = slope;
		}
	}

	private static final Map<Integer, ModelConfig> managementCorefactorConfigs = Maps.newHashMap(ImmutableMap.of(
			//Accuracy
			36, new ModelConfig(0.1d, 2.5d),
			// Work Ethic
			14, new ModelConfig(0.2d, 1.8d),
			// Carefulness
			18, new ModelConfig(0.3d, 2.7d),
			// Integrity (Theft)
			17, new ModelConfig(0.4d, 0.8d)
			));
	
	private static final Map<Integer, ModelConfig> defaultCorefactorConfigs = Maps.newHashMap(ImmutableMap.of(
			//Accuracy
			36, new ModelConfig(0.1d, 1.7d),
			// Work Ethic
			14, new ModelConfig(0.2d, 1.5d),
			// Carefulness
			18, new ModelConfig(0.3d, 2.1d),
			// Integrity (Theft)
			17, new ModelConfig(0.4d, 2.0d)
			));	
	
	private static final Map<String, Map<Integer, ModelConfig>> positionConfigs = Maps.newHashMap(ImmutableMap.of(
			"Manager", managementCorefactorConfigs
			));
	

	@Override
	/**
	 * TODO: Refactor/Provide appropriate model implementation for simple linear regression.
	 */
	public PredictionResult runPredictions(Respondant respondant, Position position, Location location, List<CorefactorScore> corefactorScores) {
		log.debug("Running predictions for {}", respondant.getRespondantId());

		PredictionResult prediction = new PredictionResult();		
		Map<Integer, ModelConfig> configs = getModelConfigs(position);

		double d = weightOf(17, corefactorScores, configs);
		double c = weightOf(18, corefactorScores, configs);
		double b = weightOf(14, corefactorScores, configs);
		double a = weightOf(36, corefactorScores, configs);
		double targetOutcomeScore = (1.2 * (a + b + c + d)) + 6;		
		prediction.setScore(targetOutcomeScore);
		
		log.info("Prediction outcome for respondant {} is {}", respondant.getRespondantId(), targetOutcomeScore);
		return prediction;
	}


	private Map<Integer, ModelConfig> getModelConfigs(Position position) {
		Map<Integer, ModelConfig> configs = positionConfigs.get(position.getPositionName()); 
		if(null == configs) {
			configs = defaultCorefactorConfigs;
			log.debug("Using generic role model configs");
		} else {
			log.debug("Using managerial role model configs");
		}
		return configs;
	}

	private double weightOf(Integer coreFactorId, List<CorefactorScore> corefactorScores, Map<Integer, ModelConfig> configs) {
		double weight = 0.0d;
		double scoreValue = scoreOf(coreFactorId, corefactorScores);
		Optional<ModelConfig> config = Optional.ofNullable(configs.get(coreFactorId));
		if(config.isPresent()) {
			weight = config.get().intercept + (config.get().slope * scoreValue);
		}
		log.debug("Corefactor weight for corefactor {} determined as {}", coreFactorId, weight);	
		return weight;
	}
	
	private double scoreOf(Integer coreFactorId, List<CorefactorScore> corefactorScores) {
		double scoreValue = 0.0d;
		Optional<CorefactorScore> cfScore = corefactorScores.stream()
				.filter(cfs -> coreFactorId.equals(cfs.getCorefactor().getCorefactorId()))
				.findFirst();
		if(cfScore.isPresent()) {
			scoreValue = cfScore.get().getScore();
		}
		log.debug("CorefactorScore for corefactor {} determined as {}", coreFactorId, scoreValue);
		return scoreValue;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	@Override
	public String getModelName() {
		return modelName;
	}

}
