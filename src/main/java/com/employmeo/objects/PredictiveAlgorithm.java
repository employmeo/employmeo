package com.employmeo.objects;

import javax.validation.constraints.NotNull;

public enum PredictiveAlgorithm {
	SIMPLE_LINEAR("linear");
	
	private String name;
	private Integer version = 1;
	
	private PredictiveAlgorithm(@NotNull String name) {
		this.name = name;
		this.version = 1;
	}

	private PredictiveAlgorithm(@NotNull String name, @NotNull Integer version) {
		this.name = name;
		this.version = version;
	}
	
	
	public String getName() {
		return name;
	}

	public Integer getVersion() {
		return version;
	}

	
}
