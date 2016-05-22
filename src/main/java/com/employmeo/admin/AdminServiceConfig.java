package com.employmeo.admin;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("admin")
public class AdminServiceConfig extends ResourceConfig {

	public AdminServiceConfig() {
		packages("com.employmeo.admin");
		register(AdminAuthProvider.class);
	}

}
