package com.employmeo.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.json.JSONArray;
import org.json.JSONObject;

import com.employmeo.objects.Account;
import com.employmeo.objects.AccountSurvey;
import com.employmeo.objects.Location;
import com.employmeo.objects.Partner;
import com.employmeo.objects.Person;
import com.employmeo.objects.Position;
import com.employmeo.objects.Respondant;


public class ICIMSPartnerUtil implements PartnerUtil {
	private static Logger logger = Logger.getLogger("ICIMSPartnerUtility");
	private static final String ICIMS_USER = "employmeoapiuser";
	private static final String ICIMS_PASS = "YN9rEQnU";
	//private static final String ICIMS_API = "https://api.icims.com/customers/";
	private static final String JOB_EXTRA_FIELDS = "?fields=jobtitle,assessmenttype,jobtype,joblocation,hiringmanager";
	
	private static String PROXY_URL = "http://63.150.152.151:8080"; //System.getenv("FIXIE_URL");
	private Partner partner = null;
	
	public ICIMSPartnerUtil(Partner partner) {
		this.partner = partner;
	}
		
	public String getPrefix() {
		return partner.getPartnerPrefix();
	}

	@Override
	public String trimPrefix(String id) {
		return id.substring(id.indexOf(getPrefix())+getPrefix().length());
	}
	
	public Account getAccountFrom(JSONObject json) {
		Account account = null;
		// lookup account by ATS ID
		EntityManager em = DBUtil.getEntityManager();
		TypedQuery<Account> q = em.createQuery("SELECT a FROM Account a WHERE a.accountAtsId = :accountAtsId",
				Account.class);
		q.setParameter("accountAtsId", partner.getPartnerPrefix() + json.getString("customerId"));
		try {
			account = q.getSingleResult();
		} catch (NoResultException nre) {
			logger.warning("Can't find account with atsId: " + json.getString("customerId"));
			throw new WebApplicationException(
					Response.status(Status.PRECONDITION_FAILED).entity(json.toString()).build());
		}
		return account;
	}

	public Location getLocationFrom(JSONObject job, Account account) {
		String locationAtsId  = job.getJSONObject("joblocation").getString("address");
		String locationName = job.getJSONObject("joblocation").getString("value");

		Location location = null;
		EntityManager em = DBUtil.getEntityManager();
		TypedQuery<Location> q = em.createQuery(
				"SELECT l FROM Location l WHERE l.locationAtsId = :locationAtsId AND l.locationAccountId = :accountId",
				Location.class);
		q.setParameter("locationAtsId", locationAtsId);
		q.setParameter("accountId", account.getAccountId());
		try {
			location = q.getSingleResult();
		} catch (NoResultException nre) {
			try {
				JSONObject address = new JSONObject();
				address.put("street", locationName);
				// TODO create code to find a location in ICIMS, and create in employmeo
				AddressUtil.validate(address);
logger.info("location post googlemaps: " + address);	
				location = new Location();			
				location.setLocationAtsId(locationAtsId);
				location.setLocationName(locationName);
				location.persistMe();
			} catch (Exception e) {
				logger.severe("Failed to lookup or create new location");
			}
		}

		return account.getDefaultLocation();
	}

	public Position getPositionFrom(JSONObject job, Account account) {
logger.info("Using Account default position and Ignoring job object: " + job);
		Position pos = account.getDefaultPosition();
		return pos;
	}

	public AccountSurvey getSurveyFrom(JSONObject job, Account account) {

		JSONArray assessmenttypes = job.getJSONArray("assessmenttype");
		if (assessmenttypes.length() > 1) logger.warning("More than 1 Assessment in: " + assessmenttypes);

		String assessmentName = assessmenttypes.getJSONObject(0).getString("value");

		AccountSurvey aSurvey = null;
		List<AccountSurvey> assessments = account.getAccountSurveys();
		for (AccountSurvey as : assessments) {
			if (assessmentName.equals(as.getAsDisplayName())) aSurvey = as;
		}
		if (aSurvey == null) aSurvey = account.getDefaultAccountSurvey();
		
		return aSurvey;
	}

	public Respondant getRespondantFrom(JSONObject json) {
		Respondant respondant = null;
		String applicantAtsId = json.getString("userId");
		// lookup account by ATS ID
		EntityManager em = DBUtil.getEntityManager();
		TypedQuery<Respondant> q = em.createQuery(
				"SELECT r FROM Respondant r WHERE r.respondantAtsId = :respondantAtsId", Respondant.class);
		q.setParameter("respondantAtsId", partner.getPartnerPrefix() + applicantAtsId);
		try {
				respondant = q.getSingleResult();
		} catch (NoResultException nre) {
		}
		return respondant;
	}

	@Override
	public Respondant createRespondantFrom(JSONObject json, Account account) {

		JSONObject candidate  = null; // This is ICIMS "Person"
		JSONObject application = new JSONObject(); // This is ICIMS "workflow" - could have more than one per person
		JSONObject job = null; // ICIMS job applied to (includes location, etc)
		// Get links and objects associated
		JSONArray links = json.getJSONArray("links");
		for (int i=0;i<links.length();i++) {
			JSONObject link = links.getJSONObject(i);
			switch (link.getString("rel")) {
			case "job":
				job = new JSONObject(icimsGet(link.getString("url")+JOB_EXTRA_FIELDS));
				job.put("link", link.getString("url"));
				break;
			case "person":
				candidate = new JSONObject(icimsGet(link.getString("url")));
				candidate.put("link", link.getString("url"));
				break;
			case "applicantWorkflow":
				application.put("link", link.getString("url"));
				break;
			case "user":
				// Dont use this one.
				break;
			default:
				logger.warning("Unexpected Link: " + link);
				break;
			}
		}
		
		Person person = getPerson(candidate, account);

		Position position = this.getPositionFrom(job, account);
		Location location = this.getLocationFrom(job, account);
		AccountSurvey aSurvey = this.getSurveyFrom(application, account);

		Respondant respondant = new Respondant();
		respondant.setRespondantAtsId(application.getString("link"));
		respondant.setRespondantRedirectUrl(json.getString("returnUrl"));
		//respondant.setRespondantEmailRecipient(delivery.optString("scores_email_address"));
		//respondant.setRespondantScorePostMethod(delivery.optString("scores_post_url"));
		respondant.setAccount(account);
		respondant.setPosition(position);
		respondant.setRespondantLocationId(location.getLocationId());
		respondant.setAccountSurvey(aSurvey);

		respondant.setPerson(person);
		respondant.persistMe();

		return respondant;
	}

	@Override
	public JSONObject prepOrderResponse(JSONObject json, Respondant respondant) {
		// TODO Auto-generated method stub
		return null;
	}

	public Person getPerson(JSONObject applicant, Account account) {
		Person person = null;
		
		EntityManager em = DBUtil.getEntityManager();
		TypedQuery<Person> q = em.createQuery(
				"SELECT p FROM Person p WHERE p.personAtsId = :link", Person.class);
		q.setParameter("link", applicant.getString("link"));
		try {
				person = q.getSingleResult();
				return person;
		} catch (NoResultException nre) {
		}
		// If no result, or other error...
		person = new Person();
		person.setPersonAtsId(applicant.getString("link"));
		person.setPersonEmail(applicant.getString("email"));
		person.setPersonFname(applicant.getString("firstname"));
		person.setPersonLname(applicant.getString("lastname"));

		try {
			JSONObject address = applicant.getJSONArray("addresses").getJSONObject(0);
			address.put("street", address.getString("addressstreet1") + " " + address.optString("addressstreet2"));
			address.put("city", address.getString("addresscity"));
			address.put("state", address.getJSONObject("addressstate").getString("abbrev"));
			address.put("zip", address.getString("addresszip"));
			AddressUtil.validate(address);
			person.setPersonAddress(address.optString("formatted_address"));
			person.setPersonLat(address.optDouble("lat"));
			person.setPersonLong(address.optDouble("lng"));
		} catch (Exception e) {
logger.severe("Failed to handle address:" + e.getMessage());
		}
		person.persistMe();
		return person;
	}

	public static WebTarget prepTarget(String target) {
		ClientConfig cc = new ClientConfig();
		cc.property(ApacheClientProperties.PREEMPTIVE_BASIC_AUTHENTICATION, true);
		cc.property(ClientProperties.REQUEST_ENTITY_PROCESSING, "BUFFERED");
		cc.property(ClientProperties.PROXY_URI, PROXY_URL);
		cc.property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
		cc.connectorProvider(new ApacheConnectorProvider());
		Client client = ClientBuilder.newClient(cc);

		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(ICIMS_USER, ICIMS_PASS);
		client.register(feature);
		
		return client.target(target);
	}
	
	public static String icimsGet(String getTarget) {
		
		String response = prepTarget(getTarget).request(MediaType.APPLICATION_JSON).get(String.class);
		return response;
		
	}

	public static String icimsPost(String postTarget, JSONObject json) {
		
		String response = prepTarget(postTarget).request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(json.toString(), MediaType.APPLICATION_JSON), String.class);
		return response;
		
	}

	public static Response icimsPatch(String postTarget, JSONObject json) {
		
		Response response = prepTarget(postTarget).request(MediaType.APPLICATION_JSON)
				.method("PATCH",Entity.entity(json.toString(), MediaType.APPLICATION_JSON));
		
		return response;
		
	}
	
	public static Response postScoresToPartner(Respondant respondant, JSONObject message) {

		String method = respondant.getRespondantAtsId();
		JSONObject json = new JSONObject();
		JSONArray resultset = new JSONArray();
		JSONObject results = new JSONObject();
		results.put("assessmentdate", respondant.getRespondantCreatedDate());
		results.put("assessmentname", "Worker Reliability");
		results.put("assessmentscore", 95.5);
		results.put("assessmentresult", "profile_a");
		results.put("assessmentnotes", "innovation: 1, judgment: 3, work ethic: 1");
		results.put("assessmentstatus", "Complete");
		//—  (D37002019001,"Complete", D37002019002,"Incomplete", D37002019003,"In-Progress", D37002019004,"Sent")
		results.put("assessmenturl", "https://portal.employmeo.com/");
		resultset.put(results);
		json.put("assessmentresults", resultset);
		return icimsPatch(method, json);

	}
	
	
	public static void main (String[] args) throws Exception{
		Respondant respondant = new Respondant();
		Date today = new Date(0);
		respondant.setRespondantCreatedDate(today);
		respondant.setRespondantAtsId("https://api.icims.com/customers/6269/applicantworkflows/1481");
		Response response = postScoresToPartner(respondant, null);
		//testAppComplete();
		
		System.out.println("Response Status: " + response.getStatus());
		System.out.println("Response Status Phrase: " + response.getStatusInfo().getReasonPhrase());
		System.out.println("Response URI: " + response.getLocation());
		System.out.println("Response Media Type: " + response.getMediaType());
		if (response.hasEntity()) System.out.println("Response Entity: " + response.readEntity(String.class));		

	}

	
	private static void testAppComplete() throws Exception {
		
		JSONObject json = new JSONObject();
		json.put("systemId","1481");
		json.put("userId","1243");
		json.put("customerId","6269");
		json.put("returnUrl","https://jobs-assessmentsandbox.icims.com/jobs/1385/front-line-job-3/assessment?i=1");
		json.put("eventType","ApplicationCompletedEvent");
		JSONObject wflink = new JSONObject();
		wflink.put("rel", "applicantWorkflow");
		wflink.put("title", "Applicant Workflow");
		wflink.put("url", "https://api.icims.com/customers/6269/applicantworkflows/1481");
		JSONObject jlink = new JSONObject();
		jlink.put("rel", "job");
		jlink.put("title", "Job Profile");
		jlink.put("url", "https://api.icims.com/customers/6269/jobs/1385");
		JSONObject plink = new JSONObject();
		plink.put("rel", "person");
		plink.put("title", "Person Profile");
		plink.put("url", "https://api.icims.com/customers/6269/people/1243");
		JSONObject ulink = new JSONObject();
		ulink.put("rel", "user");
		ulink.put("title", "Posting User");
		ulink.put("url", "https://api.icims.com/customers/6269/people/1243");
		json.accumulate("links", wflink);
		json.accumulate("links", jlink);
		json.accumulate("links", plink);
		json.accumulate("links", ulink);

		postToEmploymeo("https://localhost/integration/icimsapplicationcomplete", json);	
		

	}
	
	private static void postToEmploymeo(String service, JSONObject json) throws Exception {
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, new TrustManager[]{new X509TrustManager() {
	        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
	        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
	        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
	    }}, new java.security.SecureRandom());
		
		Client client = ClientBuilder.newBuilder().sslContext(sslContext).build();

		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("icims", "FGx4bgfZ!C");
		client.register(feature);

		WebTarget target = client.target(service);
		
		Response response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(json.toString(), MediaType.APPLICATION_JSON));
		
		System.out.println("Response Status: " + response.getStatus());
		System.out.println("Response Status Phrase: " + response.getStatusInfo().getReasonPhrase());
		System.out.println("Response URI: " + response.getLocation());
		System.out.println("Response Media Type: " + response.getMediaType());
		if (response.hasEntity()) System.out.println("Response Entity: " + response.readEntity(String.class));		
	}
	
}
