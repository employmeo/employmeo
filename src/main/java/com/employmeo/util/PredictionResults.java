package com.employmeo.util;

import java.util.Map;

import javax.validation.constraints.NotNull;

import com.employmeo.objects.PositionProfile;
import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class PredictionResults {

	@Getter
	private Map<String, Double> positionProfileScores = Maps.newHashMap();
	@Getter
	@Setter
	private String recommendedPositionProfile = PositionProfile.PROFILE_D;
	@Getter
	@Setter
	private Double compositeScore = 0.0D;

	public void addProfileScore(@NotNull String positionProfileName, @NotNull Double score) {
		positionProfileScores.put(positionProfileName, score);
	}

}
