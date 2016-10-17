package com.employmeo.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.employmeo.objects.PositionProfile;
import com.employmeo.objects.Respondant;
import com.google.common.collect.Range;

/**
 * TODO: Determine Mechanics of grading
 * 
 * @author NShah
 *
 */
public class GradingUtil {
	private static final Logger log = LoggerFactory.getLogger(GradingUtil.class);
	
	private static final Range<Double> gradeCurveProfileD = Range.closed(0.0D, 0.40D);
	private static final Range<Double> gradeCurveProfileC = Range.closed(0.4001D, 0.55D);
	private static final Range<Double> gradeCurveProfileB = Range.closed(0.551D, 0.70D);
	private static final Range<Double> gradeCurveProfileA = Range.closed(0.71D, 1.0D);
	

	/**
	 * TODO: Implement grading logic
	 * For now, doing a simple mean score from all prediction scores and looking at a hypothetical static grade curve.
	 * 
	 * @param respondant
	 * @param predictions
	 * @return grading result
	 */
	public static GradingResult gradeRespondantByPredictions(Respondant respondant, List<PredictionResult> predictions) {
		log.debug("Initiating grading for respondant {}", respondant.getRespondantId());
		
		GradingResult result = new GradingResult();
		PredictionResult hirabilityPrediction = predictions.stream()
						.filter(p -> "hirability".equals(p.getPredictionTarget().getName()))
						.findFirst()
						.orElseThrow(() -> new IllegalStateException("Grading is setup only to review prediction results for hirability. No hirability prediction results found !"));
		
		Double hirabilityPercentile = hirabilityPrediction.getPercentile();
		
		if(gradeCurveProfileD.contains(hirabilityPercentile)) {
			result.setRecommendedProfile(PositionProfile.PROFILE_D);	
		} else if (gradeCurveProfileC.contains(hirabilityPercentile)) {
			result.setRecommendedProfile(PositionProfile.PROFILE_C);
		} else if (gradeCurveProfileB.contains(hirabilityPercentile)) {
			result.setRecommendedProfile(PositionProfile.PROFILE_B);	
		} else {
			result.setRecommendedProfile(PositionProfile.PROFILE_A);			
		}
		
		result.setCompositeScore(hirabilityPrediction.getScore());
		
		log.debug("Grade results for respondant {} determined as {}", respondant.getRespondantId(), result);
		return result;
		
	}
}
