package com.employmeo.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.employmeo.objects.CorefactorScore;
import com.employmeo.objects.LinearRegressionConfig;
import com.employmeo.objects.Location;
import com.employmeo.objects.Position;
import com.employmeo.objects.Respondant;

public class SimpleLinearTenurePredictionModel implements PredictionModelEngine {

	private static final Logger log = LoggerFactory.getLogger(SimpleLinearTenurePredictionModel.class);
	private static final String modelName = "simple_tenure_model";
	
	@Override
	public PredictionResult runPredictions(Respondant respondant, Position position, Location location, List<CorefactorScore> corefactorScores) {
		log.debug("Running predictions for {}", respondant.getRespondantId());

		PredictionResult prediction = new PredictionResult();
		Double targetOutcomeScore = 0.0d;
		List<LinearRegressionConfig> configs = LinearRegressionUtil.getModelConfigs(getModelName(), position);
		
		if(!configs.isEmpty()) {
			Double scoreSigma = 0.0D;
			for(LinearRegressionConfig config : configs) {
				scoreSigma += LinearRegressionUtil.getWeightedScore(config, corefactorScores);
			}
			
			targetOutcomeScore = scoreSigma;
		} else {
			log.warn("No linear regression configs set up for model {} !!", getModelName());
		}

		prediction.setScore(targetOutcomeScore);
		
		log.info("Prediction outcome for respondant {} is {}", respondant.getRespondantId(), targetOutcomeScore);
		return prediction;
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
