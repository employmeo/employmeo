package com.employmeo.survey;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

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
import com.employmeo.util.PartnerUtil;
import com.employmeo.util.PredictionUtil;

@Path("submitassessment")
public class SubmitAssessment {
	private static final ExecutorService TASK_EXECUTOR = Executors.newCachedThreadPool();
    private static Logger logger = Logger.getLogger("SurveyService");

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
				JSONObject message = PartnerUtil.getScoresMessage(respondant);
				String postmethod = respondant.getRespondantScorePostMethod();
				if (postmethod == null) postmethod = "https://employmeo.herokuapp.com/integration/echo";
				Client client = ClientBuilder.newClient();
				WebTarget target = client.target(postmethod);
				try {
					String result = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(message.toString(),MediaType.APPLICATION_JSON),String.class);
					logger.info("posted scores to echo with result:\n" + result);
				} catch (Exception e) {
					logger.severe("failed posting scores to: " + postmethod);
				}
				
			}
		});		
	}
}