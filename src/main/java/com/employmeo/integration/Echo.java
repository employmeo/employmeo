package com.employmeo.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONObject;

@Path("echo")
public class Echo {

	@Context
	private UriInfo uriInfo;
	@Context
	private Response resp;

	private static final Logger log = LoggerFactory.getLogger(Echo.class);

	@POST
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String doPost(JSONObject json) {
		log.debug("Echo Called with: \n" + json.toString());
		return json.toString();
	}

}