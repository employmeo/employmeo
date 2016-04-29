package com.employmeo.survey;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("survey")
public class SurveyServiceConfig extends ResourceConfig {
	public SurveyServiceConfig () {
		packages("com.employmeo.survey");
	}
}
