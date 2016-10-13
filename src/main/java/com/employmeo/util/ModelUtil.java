package com.employmeo.util;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.employmeo.objects.LinearRegressionConfig;
import com.employmeo.objects.PredictionModel;
import com.employmeo.objects.PredictionModel.ModelType;
import com.google.common.collect.Lists;

import lombok.NonNull;

public class ModelUtil {
	
	private static final Logger log = LoggerFactory.getLogger(ModelUtil.class);

	public static LinearRegressionModelConfiguration getLinearRegressionConfiguration(@NonNull String modelName) {
		log.debug("Fetching linear regression configurations for modelName {}", modelName);
		
		EntityManager em = DBUtil.getEntityManager();
		List<LinearRegressionConfig> configEntries = Lists.newArrayList();
		
		PredictionModel predictionModel = getModelByName(modelName);
		
		if(ModelType.LINEAR_REGRESSION == predictionModel.getModelType()) {
			configEntries = em.createNamedQuery("LinearRegressionConfig.findByModelId", LinearRegressionConfig.class)
												.setParameter("modelId", predictionModel.getModelId())
												.getResultList();
		} else {
			log.warn("Model {} is not a linear regression type model. Please review configurations.", modelName);
			throw new IllegalStateException("Model " + modelName + " is not a linear regression type model. Please review setup.");
		}
		
		LinearRegressionModelConfiguration configuration = new LinearRegressionModelConfiguration(configEntries);
		log.debug("LinearRegressionConfigs for model {} : {}", modelName, configuration);
		
		return configuration;
	}
	
	public static PredictionModel getModelByName(@NonNull String modelName) {
		log.debug("Fetching prediction model by name {}", modelName);
		EntityManager em = DBUtil.getEntityManager();
		
		PredictionModel predictionModel = em.createNamedQuery("PredictionModel.findByName", PredictionModel.class)
											.setParameter("name", modelName)
											.getSingleResult();
		
		log.debug("PredictionModel for modelName {} : {}", modelName, predictionModel);
		return predictionModel;
	}	
	
}
