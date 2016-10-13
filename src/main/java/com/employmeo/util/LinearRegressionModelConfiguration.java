package com.employmeo.util;

import java.util.List;

import com.employmeo.objects.LinearRegressionConfig;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinearRegressionModelConfiguration {

	private List<LinearRegressionConfig> configEntries;
	
}
