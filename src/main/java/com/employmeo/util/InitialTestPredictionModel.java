package com.employmeo.util;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.employmeo.objects.PositionProfile;
import com.employmeo.objects.Respondant;

public class InitialTestPredictionModel implements PredictionModelEngine {

	private static final Logger log = LoggerFactory.getLogger(InitialTestPredictionModel.class);
/*
	static {
		log.info("Registering self with the PredictionModelRegistry");
		PredictionModelRegistry.register(PredictionModelAlgorithm.builder().modelName("simple_linear")
				.modelType("linear").modelVersion(1).build(), new SimpleLinearPredictor());
	}
*/
	@Override
	public PredictionResults runPredictions(@NotNull final Respondant respondant) {
		// TODO - replace random logic with real scoring algorithm
		// Position position = respondant.getPosition();
		// Location location = respondant.getLocation();

		log.debug("Running predictions for {}", respondant.getRespondantId());

		PredictionResults prediction = new PredictionResults();

		double d = Math.random();
		double c = Math.random() * 2;
		double b = Math.random() * 1.5;
		double a = Math.random() / 1.5;
		double highest = 0;

		highest = d;
		prediction.addProfileScore(PositionProfile.PROFILE_D, (d / (a + b + c + d)));
		prediction.setRecommendedPositionProfile(PositionProfile.PROFILE_D);

		prediction.addProfileScore(PositionProfile.PROFILE_C, (c / (a + b + c + d)));
		if (c > highest) {
			highest = c;
			prediction.setRecommendedPositionProfile(PositionProfile.PROFILE_C);
		}

		prediction.addProfileScore(PositionProfile.PROFILE_B, (b / (a + b + c + d)));
		if (b > highest) {
			highest = b;
			prediction.setRecommendedPositionProfile(PositionProfile.PROFILE_B);
		}

		prediction.addProfileScore(PositionProfile.PROFILE_A, (a / (a + b + c + d)));
		if (a > highest) {
			highest = a;
			prediction.setRecommendedPositionProfile(PositionProfile.PROFILE_A);
		}

		prediction.setCompositeScore(9.677 * (a + b + c + d) + 50.0);

		log.info("Prediction for respondant {} is {}", respondant.getRespondantId(), prediction);
		return prediction;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
