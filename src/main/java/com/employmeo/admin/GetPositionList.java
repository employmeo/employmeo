package com.employmeo.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;

import com.employmeo.objects.Account;
import com.employmeo.objects.Position;
import com.employmeo.objects.User;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("getpositions")
public class GetPositionList {

	private static final Logger log = LoggerFactory.getLogger("com.employmeo.admin");

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

		if (account.getPositions().size() > 0) {
			List<Position> positions = account.getPositions();
			for (int i = 0; i < positions.size(); i++) {
				response.put(positions.get(i).getJSON());
			}
		}
		return response.toString();
	}
}