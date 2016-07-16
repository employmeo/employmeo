package com.employmeo.admin;


import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.employmeo.objects.CorefactorDescription;
import com.employmeo.objects.Position;
import com.employmeo.objects.User;
import com.employmeo.util.DBUtil;

import java.util.List;
import java.util.logging.Logger;

@Path("updateaccount")
public class UpdateAccount {

	private static Logger logger = Logger.getLogger("com.employmeo.admin");
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String doMethod(@Context final HttpServletRequest reqt, String stringdata) {

User user = (User) reqt.getSession().getAttribute("User");

		List<Position> acctpositions = user.getAccount().getPositions();
		
		for (Position p : acctpositions) {
			System.out.println(Position.getPositionById(p.getPositionId()).getJSONString());			
		}
		
		return stringdata;
	}


}	