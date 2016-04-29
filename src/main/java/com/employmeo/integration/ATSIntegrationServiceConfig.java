package com.employmeo.integration;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;


@ApplicationPath("integration")
public class ATSIntegrationServiceConfig extends ResourceConfig {

	public ATSIntegrationServiceConfig () {
		packages("com.employmeo.integration");
		register(RestAuthenticationProvider.class);
	}
	
}
