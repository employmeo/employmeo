package com.employmeo.util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PredictionModelAlgorithm {
	private String modelName;
	private String modelType;
	private String predictionTarget;
	private Integer modelVersion;
}
