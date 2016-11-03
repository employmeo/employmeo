package com.employmeo.portal.resources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.springframework.stereotype.Component;

import com.employmeo.objects.Account;
import com.employmeo.objects.Location;
import com.employmeo.objects.User;

import io.swagger.annotations.Api;

import java.util.List;

@Path("getlocations")
@Produces(MediaType.APPLICATION_JSON)
@Api( value="/getlocations", produces=MediaType.APPLICATION_JSON)
public class GetLocationList {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String doPost(@Context final HttpServletRequest reqt, @Context final HttpServletResponse resp) {
		HttpSession sess = reqt.getSession();
		User user = (User) sess.getAttribute("User");
		if (user == null) {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		}

		Account account = user.getAccount();
		JSONArray response = new JSONArray();

		if (account.getLocations().size() > 0) {
			List<Location> locations = account.getLocations();
			for (int i = 0; i < locations.size(); i++) {
				response.put(locations.get(i).getJSON());
			}
		}
		return response.toString();
	}

}