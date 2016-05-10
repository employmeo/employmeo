package com.employmeo.util;

import com.employmeo.objects.PositionProfile;
import com.employmeo.objects.Respondant;

public class PredictionUtil {

	public static void scoreRespondant(Respondant respondant) {
// TODO - replace random logic with real scoring algorithm
//		Position position = Position.getPositionById(respondant.getRespondantPositionId());
//		Location location = Location.getLocationById(respondant.getRespondantLocationId());

		double d = Math.random();
		double b = Math.random();
		double c = Math.random();
		double a = Math.random();
		double highest = 0;

		respondant.setProfileD(d / (a+b+c+d));
		highest = d;
		respondant.setRespondantProfile(PositionProfile.PROFILE_D);

		respondant.setProfileC(c / (a+b+c+d));
		if (c > highest) {
			highest = d;
		    respondant.setRespondantProfile(PositionProfile.PROFILE_C);
		}

		respondant.setProfileB(b / (a+b+c+d));
		if (b > highest) {
			highest = b;
		    respondant.setRespondantProfile(PositionProfile.PROFILE_B);
		}

		respondant.setProfileA(a / (a+b+c+d));
		if (a > highest) {
			highest = a;
		    respondant.setRespondantProfile(PositionProfile.PROFILE_A);
		}
		
		respondant.mergeMe();

		return;
	}
	
	
}
