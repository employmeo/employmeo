package com.employmeo.admin;

import java.util.UUID;

import javax.annotation.security.PermitAll;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.employmeo.objects.Position;
import com.employmeo.objects.Respondant;
import com.employmeo.objects.User;

@Path("getscore")
@PermitAll
public class GetRespondantScore {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String doPost(
			@Context final HttpServletRequest reqt,
			@Context final HttpServletResponse resp,
			@FormParam("respondant_id") Long respondantId, 
			@FormParam("respondant_uuid") UUID respondantUuid) {
		JSONObject json = new JSONObject();
		
		HttpSession sess = reqt.getSession();
		User user = (User) sess.getAttribute("User");
		if (user == null) {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		} 
		
		Respondant respondant = null;
		if (respondantId != null) {
			respondant = Respondant.getRespondantById(respondantId);
		} else if (respondantUuid != null) {
			respondant = Respondant.getRespondantByUuid(respondantUuid);
		}

		if (respondant != null) {
			if (!user.canView(respondant)) {
				resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return null;			
			}			json.put("respondant", respondant.getJSON());
			json.put("scores", respondant.getAssessmentScore());
			json.put("detailed_scores", respondant.getAssessmentDetailedScore());
			Position position = Position.getPositionById(respondant.getRespondantPositionId());
			if (position != null)
				json.put("position", position.getJSON());
		}

		return json.toString();
	}
}