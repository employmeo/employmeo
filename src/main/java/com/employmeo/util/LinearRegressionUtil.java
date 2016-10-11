package com.employmeo.util;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.employmeo.objects.CorefactorScore;
import com.employmeo.objects.LinearRegressionConfig;
import com.employmeo.objects.Position;
import com.employmeo.objects.PredictionModel;

import lombok.NonNull;

public class LinearRegressionUtil {
	
	private static final Logger log = LoggerFactory.getLogger(LinearRegressionUtil.class);

	public static List<LinearRegressionConfig> getModelConfigs(@NonNull String modelName, Position position) {
		log.debug("Fetching linear regression configurations for modelName {} and position {}", modelName, position.getPositionName());
		EntityManager em = DBUtil.getEntityManager();
		
		PredictionModel predictionModel = em.createNamedQuery("PredictionModel.findByName", PredictionModel.class)
											.setParameter("name", modelName)
											.getSingleResult();
		
		// attempt to find the specific configs for a given position first
		List<LinearRegressionConfig> configs = em.createNamedQuery("LinearRegressionConfig.findByModelIdAndPositionId", LinearRegressionConfig.class)
												.setParameter("modelId", predictionModel.getModelId())
												.setParameter("positionId", position.getPositionId())
												.getResultList();
		log.debug("LinearRegressionConfigs for position {} : {}", position.getPositionName(), configs);
		
		// else fall back to default configs applicable across any/all positions
		if(configs.isEmpty()) {
			log.debug("Falling back to default configs for position {}", position.getPositionName());
			configs = em.createNamedQuery("LinearRegressionConfig.findDefaultByModelId", LinearRegressionConfig.class)
						.setParameter("modelId", predictionModel.getModelId())
						.getResultList();	
			
			log.debug("LinearRegressionConfigs defaults applying to position {} : {}", position.getPositionName(), configs);
		}
				
		return configs;
	}
	
	public static Double getWeightedScore(LinearRegressionConfig config, List<CorefactorScore> corefactorScores) {
		Double weightedScore = 0.0D;
		Optional<CorefactorScore> cfScore = corefactorScores.stream().filter(cfs -> config.getCorefactorId().equals(cfs.getCorefactor().getCorefactorId())).findFirst();
		if(cfScore.isPresent()) {
			// pls review this
			weightedScore = config.getCoefficient() * cfScore.get().getScore() + config.getSignificance();
			
			log.debug("WeightedScore for corefactorScore {} with config {} = {} ", cfScore.get(), config, weightedScore);
		}
		return weightedScore;
	}	
}
