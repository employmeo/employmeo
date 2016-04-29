package com.employmeo.integration;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;


@ApplicationPath("integration")
public class ATSIntegrationServiceConfig extends ResourceConfig {

	public ATSIntegrationServiceConfig () {
		packages("com.employmeo.integration");
		register(RolesAllowedDynamicFeature.class);
	}
	
}
