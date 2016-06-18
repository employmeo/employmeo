package com.employmeo.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.json.JSONArray;
import org.json.JSONObject;

import com.employmeo.objects.Person;
import com.employmeo.objects.PositionProfile;
import com.employmeo.objects.User;

public class RandomizerUtil {

	// Basic Tool Settings
	public static final String SERVER_NAME = "https://localhost:8443";
	private static final int THREAD_COUNT = 1;
	private static final int LOOPS = 1;
	private static final int DELAY = 2500;
	public static JSONObject account = new JSONObject().put("account_ats_id", "1111");

	private static final ExecutorService TASK_EXECUTOR = Executors.newCachedThreadPool();
	public static Logger logger = Logger.getLogger("TestingUtil");
	public static Random rand = new Random();
	public static JSONArray jMnames = arrayFromRandomizer("names-male.json");
	public static JSONArray jFnames = arrayFromRandomizer("names-female.json");
	public static JSONArray jLnames = arrayFromRandomizer("names-surnames.json");
	public static JSONArray jCities = arrayFromRandomizer("zip-codes.json");
	public static JSONArray jStreets = arrayFromRandomizer("streets.json");
	public static List<String> domains = Arrays.asList(
			  "company.com", "aol.com", "att.net", "comcast.net", "facebook.com", "gmail.com", "gmx.com", "googlemail.com",
			  "google.com", "hotmail.com", "hotmail.co.uk", "mac.com", "me.com", "mail.com", "msn.com",
			  "live.com", "sbcglobal.net", "verizon.net", "yahoo.com", "yahoo.co.uk",
			  "email.com", "games.com", "gmx.net", "hush.com", "hushmail.com", "icloud.com", "inbox.com",
			  "lavabit.com", "love.com", "outlook.com", "pobox.com", "rocketmail.com",
			  "safe-mail.net", "wow.com", "ygm.com", "ymail.com", "zoho.com", "fastmail.fm",
			  "yandex.com", "bellsouth.net", "charter.net", "comcast.net", "cox.net", "earthlink.net", "juno.com"
			  );
	public static NewCookie adminCookie = null;
	public static JSONArray assessments = null;
	public static JSONArray positions = null;
	public static JSONArray locations = null;
	public static int completedthreads = 0;

	public RandomizerUtil() {
	}
	
	public static void main (String[] args) throws Exception {
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, new TrustManager[]{new X509TrustManager() {
	        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
	        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
	        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
	    }}, new java.security.SecureRandom());
		
		Client adminClient = ClientBuilder.newBuilder().sslContext(sslContext).build();
		Client integrationClient = ClientBuilder.newBuilder().sslContext(sslContext).build();
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("icims", "password");
		integrationClient.register(feature);
		
		// Test out logging into the admin server, updating the dash, etc.
logger.info("Starting up with " + THREAD_COUNT + " threads and " + LOOPS + " loops.");
		loginAdminService(adminClient);	
logger.info("Completed admin login attempt.");

		// Get Locations, etc for account...
		locations = arrayFromService(integrationClient, new JSONObject().put("account",account), "/integration/getlocations");
		positions = arrayFromService(integrationClient, new JSONObject().put("account",account), "/integration/getpositions");
		assessments = arrayFromService(integrationClient, new JSONObject().put("account",account), "/integration/getassessments");
		
		// Launch multiple invite + complete + score + hire streams using ATS integrations
		for (int i=0;i<THREAD_COUNT;i++) {
			int threadnum = i;
			TASK_EXECUTOR.submit(new Runnable() {
				@Override
				public void run() {
					long waittime = DELAY + (long)(10000.0 * rand.nextDouble());
					for (int j=0;j<LOOPS;j++) {
					Long appId = null;
					String link = null;
						try {
							Thread.sleep(waittime);
							JSONObject result = postJsonToService(integrationClient, randomAtsOrder(), "/integration/atsorder");
							appId = result.getJSONObject("applicant").getLong("applicant_id");
							link = result.getJSONObject("delivery").getString("assessment_url");
logger.info("Thread (" + threadnum + ") Received Assessment (" + appId + ") Link: " + link);
							Thread.sleep(waittime);
							Client surveyClient = ClientBuilder.newBuilder().sslContext(sslContext).build();
							takeSurvey(surveyClient, appId, link);
logger.info("Thread (" + threadnum + ") Completed Assessment: " + appId);
							Thread.sleep(100*DELAY);
							hireDecision(integrationClient, result.getJSONObject("applicant"));
logger.info("Thread (" + threadnum + ") Hire Decision for Applicant: " + appId);
						} catch (Exception e) {
logger.severe("Error Processing Applicant (" + appId + "): " + e.getMessage());
						}
logger.info("Thread (" + threadnum + ") completed " + (j+1) + " of " + LOOPS + " loops.");
					}
					threadComplete(threadnum);
				}});
		}
		
	}
	
	public static void threadComplete(int threadnum) {
		completedthreads++;
logger.info("Thread (" + threadnum + ") closed, " + (THREAD_COUNT - completedthreads) + " threads remaining.");
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
			response.param("response_value", Integer.toString(randomResponse(questions.getJSONObject(j))));
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
	

	public static void loginAdminService(Client client) {

		Form form = new Form();
		form.param("email", "sri@employmeo.com");
		form.param("password", "password");
		form.param("rememberme", "true");
		String postmethod = SERVER_NAME + "/admin/login";
		WebTarget target = client.target(postmethod);
		target.request(MediaType.APPLICATION_JSON)
					.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

		return;

	}
	
	// Section for random field value generation
	
	public static String randomFname() {
		if(rand.nextDouble() > 0.5) {
			return jFnames.getString(rand.nextInt(jFnames.length()));
		}
		return jMnames.getString(rand.nextInt(jMnames.length()));

	}

	public static String randomLname() {
		return jLnames.getString(rand.nextInt(jLnames.length()));
	}

	public static String randomEmail(User user) {
		int i = rand.nextInt(domains.size());
		String email = user.getUserFname() + "_" + user.getUserLname() + "@" + domains.get(i);
		return email;
	}

	public static String randomEmail(Person person) {
		int i = rand.nextInt(domains.size());
		String email = person.getPersonFname() + "_" + person.getPersonLname() + "@" + domains.get(i);
		return email;
	}
	
	public static String randomEmail(String fname, String lname) {
		int i = rand.nextInt(domains.size());
		String email = fname + "_" + lname + "@" + domains.get(i);
		return email;
	}
	
	public static JSONObject randomAddress(double lat, double lng, double dist) {
		return AddressUtil.getAddressFromLatLng(lat, lng);
	}
	
	private static int randomResponse(JSONObject question) {
		
		if (question.has("answers")) {
			JSONObject answers = randomJson(question.getJSONArray("answers"));
			return answers.getInt("answer_value");
		}
		return rand.nextInt(11);
	}
	

	private static JSONObject randomJson(JSONArray array) {
		return array.getJSONObject(rand.nextInt(array.length()));
	}
	
	public static JSONObject randomAtsOrder() {
		JSONObject atsorder = new JSONObject();
		JSONObject delivery = new JSONObject();
		delivery.put("email_applicant", false);
		delivery.put("redirect_url", "http://employmeo.com");
		if (Math.random() > 0.8) {
			delivery.put("scores_email_notify", true);
			delivery.put("scores_email_address", "sridharkaza@hotmail.com");
		} else {
			delivery.put("scores_post_url", "https://employmeo.herokuapp.com/integration/echo");
			
		}
		JSONObject applicant = new JSONObject();
		
		applicant.put("applicant_ats_id", Long.toString(System.currentTimeMillis()));
		applicant.put("fname", randomFname());
		applicant.put("lname", randomLname());
		applicant.put("email", randomEmail(applicant.getString("fname"),applicant.getString("lname")));
		JSONObject address = new JSONObject();

		randomAddress(address);
		applicant.put("address", address);

		atsorder.put("account",account);
		atsorder.put("applicant",applicant);
		atsorder.put("assessment", randomJson(assessments));
		atsorder.put("position", randomJson(positions));
		atsorder.put("location", randomJson(locations));
		atsorder.put("delivery",delivery);
		return atsorder;
	}

	public static String randomStreet() {
		
		int strAdd = rand.nextInt(9999);
		return strAdd + " " + jStreets.getString(rand.nextInt(jStreets.length()));
	}
	
	public static void randomAddress(JSONObject address) {
		JSONObject zipCode = jCities.getJSONObject(rand.nextInt(jCities.length()));
		String cityState = zipCode.getString("detail");
		address.put("street", randomStreet());
		address.put("city", cityState.split(", ")[0]);
		address.put("state", cityState.split(", ")[1]);
		address.put("zip", zipCode.getString("name"));
		
	}
}
