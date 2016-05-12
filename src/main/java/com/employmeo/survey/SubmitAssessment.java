package com.employmeo.survey;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.security.PermitAll;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.employmeo.objects.Account;
import com.employmeo.objects.PositionProfile;
import com.employmeo.objects.Respondant;
import com.employmeo.util.PredictionUtil;

@Path("submitassessment")
public class SubmitAssessment {
	private static final ExecutorService TASK_EXECUTOR = Executors.newCachedThreadPool();

    @PermitAll
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String doPost (
//			@FormParam("finish_time") TimeStamp finishTime,
			@FormParam("respondant_id") Long respondantId
			)
	{
    	Respondant respondant = Respondant.getRespondantById(respondantId);
    	if (respondant.getRespondantStatus() < Respondant.STATUS_COMPLETED) {
    		respondant.setRespondantStatus(Respondant.STATUS_COMPLETED);
    		respondant.setRespondantFinishTime(new Timestamp (new Date().getTime()));
    		respondant.mergeMe();
    	}
		    
		postScores(respondant);
		  
		return respondant.getJSONString();
	 }


	private void postScores(Respondant respondant) {
		TASK_EXECUTOR.submit(new Runnable() {
			@Override
			public void run() {

				// TODO... call the scoring logic for Respondant
				JSONObject scores = respondant.scoreMe();
				PredictionUtil.scoreRespondant(respondant);

				Account account = respondant.getRespondantAccount();
				JSONObject jAccount = new JSONObject();
				JSONObject applicant = new JSONObject();
				
				jAccount.put("account_ats_id", account.getAccountAtsId());
				jAccount.put("account_id", account.getAccountId());
				jAccount.put("account_name", account.getAccountName());

				applicant.put("applicant_ats_id", respondant.getRespondantAtsId());
				applicant.put("applicant_id", respondant.getRespondantAtsId());
				applicant.put("applicant_profile", respondant.getRespondantProfile());
				applicant.put("applicant_profile_a", respondant.getProfileA());
				applicant.put("applicant_profile_b", respondant.getProfileB());
				applicant.put("applicant_profile_c", respondant.getProfileC());
				applicant.put("applicant_profile_d", respondant.getProfileD());
				applicant.put("label_profile_a", PositionProfile.getProfileDefaults(PositionProfile.PROFILE_A).getString("profile_name"));
				applicant.put("label_profile_b", PositionProfile.getProfileDefaults(PositionProfile.PROFILE_B).getString("profile_name"));
				applicant.put("label_profile_c", PositionProfile.getProfileDefaults(PositionProfile.PROFILE_C).getString("profile_name"));
				applicant.put("label_profile_d", PositionProfile.getProfileDefaults(PositionProfile.PROFILE_D).getString("profile_name"));
				
				Iterator<String> it = scores.keys();
				while (it.hasNext()) {
					String label = it.next();
					JSONObject cf = new JSONObject();
					cf.put("corefactor_name", label);
					cf.put("corefactor_score", scores.getDouble(label));
					applicant.accumulate("scores", cf);
				}
				
				applicant.put("portal_link", "link not set");
				applicant.put("render_link", "link not set");
				
				JSONObject message = new JSONObject();
				message.put("account", jAccount);
				message.put("applicant", applicant);
				
				Client client = ClientBuilder.newClient();
				WebTarget target = client.target("http://localhost:8080").path("integration/echo");
				try {
					String result = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(message.toString(),MediaType.APPLICATION_JSON),String.class);
					System.out.println(result);
				} catch (Exception e) {
					System.out.println(e);
				}
				
			}
		});		
	}
}