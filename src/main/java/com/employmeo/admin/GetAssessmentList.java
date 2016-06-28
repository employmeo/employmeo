package com.employmeo.admin;

import javax.annotation.security.PermitAll;

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
import com.employmeo.objects.AccountSurvey;
import com.employmeo.objects.User;

import java.util.List;

@Path("getassessments")
@PermitAll
public class GetAssessmentList {

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

		if (account.getAccountSurveys().size() > 0) {
			List<AccountSurvey> aSurveys = account.getAccountSurveys();
			for (int i = 0; i < aSurveys.size(); i++) {
				response.put(aSurveys.get(i).getJSON());
			}
		}
		return response.toString();
	}
}