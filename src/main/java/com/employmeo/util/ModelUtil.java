package com.employmeo.util;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.employmeo.objects.LinearRegressionConfig;
import com.employmeo.objects.PredictionModel;

import lombok.NonNull;

public class ModelUtil {
	
	private static final Logger log = LoggerFactory.getLogger(ModelUtil.class);

	public static List<LinearRegressionConfig> getModelConfigs(@NonNull String modelName) {
		log.debug("Fetching linear regression configurations for modelName {}", modelName);
		EntityManager em = DBUtil.getEntityManager();
		
		PredictionModel predictionModel = getModelByName(modelName);
		
		List<LinearRegressionConfig> configs = em.createNamedQuery("LinearRegressionConfig.findByModelId", LinearRegressionConfig.class)
												.setParameter("modelId", predictionModel.getModelId())
												.getResultList();
		log.debug("LinearRegressionConfigs for model {} : {}", modelName, configs);
		return configs;
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
