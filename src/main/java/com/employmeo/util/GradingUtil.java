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
	
	private static final Range<Double> gradeCurveProfileD = Range.closed(0.0D, 40.0D);
	private static final Range<Double> gradeCurveProfileC = Range.closed(40.001D, 50.0D);
	private static final Range<Double> gradeCurveProfileB = Range.closed(50.001D, 65.0D);
	private static final Range<Double> gradeCurveProfileA = Range.closed(65.001D, 100.0D);
	

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
		
		Double simpleMeanScore = predictions.stream().mapToDouble(p -> p.getScore()).sum() / predictions.size();
		
		if(gradeCurveProfileD.contains(simpleMeanScore)) {
			result.setRecommendedProfile(PositionProfile.PROFILE_D);	
		} else if (gradeCurveProfileC.contains(simpleMeanScore)) {
			result.setRecommendedProfile(PositionProfile.PROFILE_C);
		} else if (gradeCurveProfileB.contains(simpleMeanScore)) {
			result.setRecommendedProfile(PositionProfile.PROFILE_B);	
		} else {
			result.setRecommendedProfile(PositionProfile.PROFILE_A);			
		}
		
		result.setCompositeScore(simpleMeanScore);
		
		log.debug("Grade results for respondant {} determined as {}", respondant.getRespondantId(), result);
		return result;
		
	}
}
