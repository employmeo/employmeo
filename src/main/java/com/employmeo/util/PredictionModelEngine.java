package com.employmeo.util;

import com.employmeo.objects.Respondant;

public interface PredictionModelEngine {

	/**
	 * Prediction implementations can do local processing, or make requisite
	 * network api calls to run predictions
	 * 
	 * @param respondant
	 * @return
	 */
	public abstract PredictionResults runPredictions(Respondant respondant);
}
