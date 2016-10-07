package com.employmeo.util;

import java.util.Map;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

import jersey.repackaged.com.google.common.collect.Maps;

public class PredictionModelRegistry {
	private static final Logger log = LoggerFactory.getLogger(PredictionModelRegistry.class);
	// static build-time registry
	private static final PredictionModelAlgorithm simpleLinearAlgorithm = PredictionModelAlgorithm.builder()
			.modelName("simple_linear").modelType("linear").modelVersion(1).build();
	private static final Map<PredictionModelAlgorithm, Predictor> modelRegistry = Maps
			.newHashMap(ImmutableMap.of(simpleLinearAlgorithm, new SimpleLinearPredictor()));

	/*
	 * public static void register(PredictionModelAlgorithm algorithm, Predictor
	 * predictor) { modelRegistry.put(algorithm, predictor);
	 * log.info("Predictor {} registered for algorithm {}", predictor,
	 * algorithm); }
	 */

	public static Optional<Predictor> getPredictorFor(@NotNull PredictionModelAlgorithm algorithm) {
		log.debug("ModelRegistry state: {}", modelRegistry);
		Optional<Predictor> predictor = Optional.ofNullable(modelRegistry.get(algorithm));
		log.debug("Algorithm {} has the predictor registered as {}", algorithm, predictor);

		return predictor;
	}
}
