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
		

	/**
	 * TODO: Implement grading logic
	 * For now, using the average percentile of all predictions.
	 * 
	 * @param respondant
	 * @param predictions
	 * @return grading result
	 */
	public static GradingResult gradeRespondantByPredictions(Respondant respondant, List<PredictionResult> predictions) {
		log.debug("Initiating grading for respondant {}", respondant.getRespondantId());
		
		GradingResult result = new GradingResult();

		Double sum = 0d;
	    for (PredictionResult pred : predictions) {sum += pred.getPercentile();}	    
	    result.setCompositeScore(100d*sum/predictions.size());
		
		Double compositePercentile = sum/predictions.size();
		switch ((int)Math.floor(4d*compositePercentile)) {
		case 0:
			result.setRecommendedProfile(PositionProfile.PROFILE_D);
			break;
		case 1:
			result.setRecommendedProfile(PositionProfile.PROFILE_C);
			break;
		case 2:
			result.setRecommendedProfile(PositionProfile.PROFILE_B);
			break;
		case 3:
			result.setRecommendedProfile(PositionProfile.PROFILE_A);
			break;
		default:
			break;		
		}
		result.setCompositeScore(100*compositePercentile);
		
		log.debug("Grade results for respondant {} determined as {}", respondant.getRespondantId(), result);
		return result;
		
	}
}
