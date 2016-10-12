package com.employmeo.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.employmeo.objects.LinearRegressionConfig;
import com.employmeo.objects.PredictionModel;
import com.google.common.collect.ImmutableMap;

import jersey.repackaged.com.google.common.collect.Maps;

/**
 * For now, a static build-time registry of algorithms and model implementations.
 * Subsequently to be moved to a dynamic registry
 * @author NShah
 *
 */
public class PredictionModelRegistry {
	private static final Logger log = LoggerFactory.getLogger(PredictionModelRegistry.class);
	
	private static final Map<String, Class<? extends PredictionModelEngine>> modelRegistry = 
			Maps.newHashMap(new ImmutableMap.Builder<String, Class<? extends PredictionModelEngine>>()
	                   .put("linear_regression", SimpleLinearRegressionEngine.class) 
	                   .build()
			);	
	
	static {
		log.info("ModelRegistry state: {}", modelRegistry);		
	}

	public static Optional<PredictionModelEngine> getPredictionModelEngineByName(@NotNull String modelName) {
		Optional<PredictionModelEngine> modelEngine = Optional.empty();
		
		log.debug("Registry consulted for modelName {}", modelName);
		PredictionModel predictionModel = ModelUtil.getModelByName(modelName);
		
		log.debug("Checking registry for mappings for modelType {}", predictionModel.getModelType());
		Optional<Class<? extends PredictionModelEngine>> modelEngineClass = Optional.ofNullable(modelRegistry.get(predictionModel.getModelType()));
		
		log.debug("Registry is configured for {} with {}", modelName, modelEngineClass);
		if(modelEngineClass.isPresent()) {
			try {			
				log.debug("Attempting to create a new engine instance for {}", modelName);
				// this presumes for any model type, the configs are LinearRegressionConfig,
				// which likely isn't right. But refactor this later.
				// IOW, parametrize the configuration entry by model type.
				List<LinearRegressionConfig> configs = ModelUtil.getModelConfigs(modelName);
				Constructor<? extends PredictionModelEngine> engineConstructor = modelEngineClass.get().getConstructor(modelName.getClass(), List.class);
				
				modelEngine = Optional.of(engineConstructor.newInstance(modelName, configs));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				log.warn("Failed to create new instance of prediction model engine for " + modelName, e);
			} 
		}
		log.debug("Returning modelEngine for {} as {}", modelName, modelEngine);

		return modelEngine;
	}
	
	
}