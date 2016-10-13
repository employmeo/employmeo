package com.employmeo.util;

import com.employmeo.objects.PredictionModel.ModelType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PredictionModelAlgorithm {
	private String modelName;
	private ModelType modelType;
	private String predictionTarget;
	private Integer modelVersion;
}
