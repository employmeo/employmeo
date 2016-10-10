package com.employmeo.util;

import java.util.Map;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static final Map<String, PredictionModelEngine> modelRegistry = 
			Maps.newHashMap(ImmutableMap.of(
					"simple_hirability_model",new SimpleLinearHirabilityPredictionModel(),
					"simple_tenure_model", new SimpleLinearTenurePredictionModel()
					)
			);

	public static Optional<PredictionModelEngine> getPredictionModelEngineByName(@NotNull String modelName) {
		log.debug("ModelRegistry state: {}", modelRegistry);
		Optional<PredictionModelEngine> modelEngine = Optional.ofNullable(modelRegistry.get(modelName));
		log.debug("Algorithm {} has the predictor registered as {}", modelName, modelEngine);

		return modelEngine;
	}
	
	
}
