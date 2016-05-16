package com.employmeo.survey;

import java.sql.Timestamp;
import java.util.Date;
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

import com.employmeo.objects.Respondant;
import com.employmeo.util.EmailUtility;
import com.employmeo.util.PartnerUtil;

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
    	logger.info("Survey Submitted for Respondant: " + respondantId);
    	
    	Respondant respondant = Respondant.getRespondantById(respondantId);
    	if (respondant.getRespondantStatus() < Respondant.STATUS_COMPLETED) {
    		respondant.setRespondantStatus(Respondant.STATUS_COMPLETED);
    		respondant.setRespondantFinishTime(new Timestamp (new Date().getTime()));
    		respondant.mergeMe();
    	}
		    
		postScores(respondant);
		  
		return respondant.getJSONString();
	 }


	private static void postScores(Respondant respondant) {
		TASK_EXECUTOR.submit(new Runnable() {
			@Override
			public void run() {
				// Kick off scoring. Future version could involve time consuming external calls
				JSONObject message = PartnerUtil.getScoresMessage(respondant);
				
				// Push to Partner Service if requested
				PartnerUtil.postScoresToPartner(respondant, message);
				
				if (respondant.getRespondantEmailRecipient() != null && 
						!respondant.getRespondantEmailRecipient().isEmpty()) {
					EmailUtility.sendResults(respondant, message.getJSONObject("applicant"));
				}
			}
		});		
	}
}