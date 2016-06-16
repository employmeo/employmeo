package com.employmeo.admin;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.employmeo.objects.CorefactorDescription;
import java.util.logging.Logger;

@Path("test")
@PermitAll
public class TestService {

	private static Logger logger = Logger.getLogger("TestService");
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String doMethod(String stringdata) {

logger.info("received: " + stringdata);

		JSONArray data = new JSONObject(stringdata).getJSONArray("data");
	
		for (int i=0; i<data.length();i++) {
			JSONObject record = data.getJSONObject(i);
			CorefactorDescription cfd = new CorefactorDescription();
			cfd.setCfDescription(record.optString("text"));
			cfd.setCfHighEnd(record.optDouble("rangehigh"));
			cfd.setCfLowEnd(record.optDouble("rangelow"));
			cfd.setCfId(record.optLong("cf_id"));
			if (cfd.getCfLowEnd()>0) cfd.persistMe();
System.out.println(cfd.getCfdescId() + " Saved " + cfd.getCfDescription());
		}
		
		return stringdata;
	}


}	