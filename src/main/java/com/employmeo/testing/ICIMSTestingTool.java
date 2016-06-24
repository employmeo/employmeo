package com.employmeo.testing;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.json.JSONArray;
import org.json.JSONObject;

import com.employmeo.objects.Person;
import com.employmeo.objects.PositionProfile;
import com.employmeo.objects.Respondant;
import com.employmeo.objects.User;
import com.employmeo.util.AddressUtil;

public class ICIMSTestingTool {

	// Basic Tool Settings
	public static final String SERVER_NAME = "https://localhost";
	public static int DELAY = 100;
	public static JSONObject account = new JSONObject().put("account_ats_id", "6269");

	public static Logger logger = Logger.getLogger("TestingUtil");
	public static Random rand = new Random();

	public static NewCookie adminCookie = null;
	public static JSONArray assessments = null;
	public static JSONArray positions = null;
	public static JSONArray locations = null;
	public static int completedthreads = 0;

	public static void main (String[] args) throws Exception {

		// Get Locations, etc for account...
		locations = arrayFromService(getEmploymeoClient(), new JSONObject().put("account",account), "/integration/getlocations");
		positions = arrayFromService(getEmploymeoClient(), new JSONObject().put("account",account), "/integration/getpositions");
		assessments = arrayFromService(getEmploymeoClient(), new JSONObject().put("account",account), "/integration/getassessments");

		Response response;
		String[] jobs = {"1382","1384","1385"};
		String[] people = {"1239", "1240", "1243"};

  		for (String job : jobs ) {
			for (String person : people) {
				response = postToEmploymeo(
						"https://localhost/integration/icimsapplicationcomplete",
						createAppComplete(person, job, person+job));
				System.out.println("Response Status: " + response.getStatus());
				System.out.println("Response Status Phrase: " + response.getStatusInfo().getReasonPhrase());
				System.out.println("Response URI: " + response.getLocation());
				System.out.println("Response Media Type: " + response.getMediaType());
			}
		}		

	}
	
	public static void takeSurvey(Client client, Long appId, String link) throws InterruptedException {
		Form form = new Form();
		int idx = link.indexOf("=");
		String uuid = link.substring(idx + 1);
		form.param("respondant_uuid", uuid);
		JSONObject survey = postFormToService(client,form, "/survey/getsurvey").getJSONObject("survey");
		JSONArray questions = survey.getJSONArray("questions");

		for (int j=0;j<questions.length();j++) {
			long waittime = DELAY + (long)(DELAY * rand.nextDouble());
			Thread.sleep(waittime);
			Form response = new Form();
			response.param("response_respondant_id", appId.toString());
			response.param("response_question_id", Long.toString(questions.getJSONObject(j).getLong("question_id")));
			response.param("response_value", Integer.toString(RandomizerUtil.randomResponse(questions.getJSONObject(j))));
			postFormToService(client,response,"/survey/response");
		}
		form = new Form();
		form.param("respondant_id", appId.toString());
		postFormToService(client,form, "/survey/submitassessment");

	}

	public static void hireDecision(Client client, JSONObject applicant) {

		JSONObject getScores = new JSONObject();
		getScores.put("account", account);
		getScores.put("applicant", applicant);		
		JSONObject score = postJsonToService(client, getScores, "/integration/getscore");
		String profile = score.getJSONObject("applicant").getString("applicant_profile");

		double prob = 0.05;		
		switch (profile) {
			case PositionProfile.PROFILE_A:
				prob = 0.95;
				break;
			case PositionProfile.PROFILE_B:
				prob = 0.70;
				break;
			case PositionProfile.PROFILE_C:
				prob = 0.45;
				break;
			case PositionProfile.PROFILE_D:
				prob = 0.05;
				break;
			default:
				break;
		}
		
		JSONObject hireNotice = new JSONObject();
		hireNotice.put("account", account);
		Date date = new Date();
		String changedate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		applicant.put("applicant_change_date",changedate);	
		if (prob > rand.nextDouble()) {
			applicant.put("applicant_status", "hired");
		} else {
			applicant.put("applicant_status", "notoffered");
		}
		hireNotice.put("applicant", applicant);
		postJsonToService(client,hireNotice,"/integration/hirenotice");
	}
	

	public static void somethingelse (String[] args) throws Exception{

//		Respondant respondant = new Respondant();
//		Long today = Calendar.getInstance().getTimeInMillis();
//		respondant.setRespondantFinishTime(new Timestamp(today));
//		respondant.setRespondantAtsId("https://api.icims.com/customers/6269/applicantworkflows/1486");
//		Response response = postScoresToPartner(respondant, null);
//		System.out.println(response.getEntity().toString());	

	}

	
	private static JSONObject createAppComplete(String userId, String jobId, String workflowId) {
		JSONObject json = new JSONObject();
		json.put("systemId",workflowId);
		json.put("userId", userId);
		json.put("customerId","6269");
		json.put("returnUrl","https://jobs-assessmentsandbox.icims.com/jobs/1385/front-line-job-3/assessment?i=1");
		json.put("eventType","ApplicationCompletedEvent");
		JSONObject wflink = new JSONObject();
		wflink.put("rel", "applicantWorkflow");
		wflink.put("title", "Applicant Workflow");
		wflink.put("url", "https://api.icims.com/customers/6269/applicantworkflows/"+workflowId);
		JSONObject jlink = new JSONObject();
		jlink.put("rel", "job");
		jlink.put("title", "Job Profile");
		jlink.put("url", "https://api.icims.com/customers/6269/jobs/"+jobId);
		JSONObject plink = new JSONObject();
		plink.put("rel", "person");
		plink.put("title", "Person Profile");
		plink.put("url", "https://api.icims.com/customers/6269/people/"+userId);
		JSONObject ulink = new JSONObject();
		ulink.put("rel", "user");
		ulink.put("title", "Posting User");
		ulink.put("url", "https://api.icims.com/customers/6269/people/"+userId);
		json.accumulate("links", wflink);
		json.accumulate("links", jlink);
		json.accumulate("links", plink);
		json.accumulate("links", ulink);
		return json;
	} 
	
	// Utilities for calling web services
	
	public static synchronized JSONObject postJsonToService(Client client, JSONObject message, String service) {

		String postmethod = SERVER_NAME + service;
		WebTarget target = client.target(postmethod);
		String result = target.request(MediaType.APPLICATION_JSON)
					.post(Entity.entity(message.toString(), MediaType.APPLICATION_JSON), String.class);
		
		return new JSONObject(result);

	}

	public static synchronized JSONArray arrayFromService(Client client, JSONObject message, String service) {

		String postmethod = SERVER_NAME + service;
		WebTarget target = client.target(postmethod);
		String result = target.request(MediaType.APPLICATION_JSON)
					.post(Entity.entity(message.toString(), MediaType.APPLICATION_JSON), String.class);
		
		return new JSONArray(result);

	}

	public static synchronized JSONArray arrayFromRandomizer(String service) {
		Client client = ClientBuilder.newClient();
		String postmethod = "https://randomlists.com/data/" + service;
		WebTarget target = client.target(postmethod);
		String result = target.request(MediaType.APPLICATION_JSON).get(String.class);
		
		return new JSONObject(result).getJSONArray("data");

	}
	
	public static synchronized JSONObject postFormToService(Client client, Form form, String service) {

		String postmethod = SERVER_NAME + service;
		WebTarget target = client.target(postmethod);
		String result = target.request(MediaType.APPLICATION_JSON)
					.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED), String.class);
		
		return new JSONObject(result);

	}
	
	private static Client getEmploymeoClient() {
		Client client = null;
		ClientConfig cc = new ClientConfig();
		cc.property(ClientProperties.FOLLOW_REDIRECTS, false);
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[]{new X509TrustManager() {
		        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
		        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
		        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
		    }}, new java.security.SecureRandom());

			client = ClientBuilder.newBuilder().sslContext(sslContext).withConfig(cc).build();
		} catch (Exception e) {
			client = ClientBuilder.newBuilder().withConfig(cc).build();			
		}

		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("icims", "FGx4bgfZ!C");
		client.register(feature);
		return client;
	}
	
	private static Response postToEmploymeo(String service, JSONObject json) throws Exception {

		WebTarget target = getEmploymeoClient().target(service);	
		return target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(json.toString(), MediaType.APPLICATION_JSON));
		
	}
	
}
