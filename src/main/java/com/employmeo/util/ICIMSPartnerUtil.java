package com.employmeo.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
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
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
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
	private static final String ICIMS_API = "https://api.icims.com/customers/";
	
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

	public Location getLocationFrom(JSONObject applicant, Account account) {
		Object locationAtsId  = applicant.opt("location");
logger.info("location attr is: " + locationAtsId);
/*
		Location location = null;
		EntityManager em = DBUtil.getEntityManager();
		TypedQuery<Location> q = em.createQuery(
				"SELECT l FROM Location l WHERE l.locationAtsId = :locationAtsId AND l.locationAccountId = :accountId",
				Location.class);
		q.setParameter("locationAtsId", partner.getPartnerPrefix() + locationAtsId);
		q.setParameter("accountId", account.getAccountId());
		try {
			location = q.getSingleResult();
		} catch (NoResultException nre) {
				location = new Location();
				// Create a new location from address
				JSONObject address = new JSONObject();// call ICIMS method for getting a location
				// TODO create code to find a location in ICIMS, and create in employmeo
				
				AddressUtil.validate(address);
				location.setLocationAtsId(partner.getPartnerPrefix() + locationAtsId);
				location.persistMe();
		}
*/
		return account.getDefaultLocation();
	}

	public Position getPositionFrom(JSONObject applicant, Account account) {
		Object jobAtsId  = applicant.opt("job");
logger.info("job attr is: " + jobAtsId);

		//String positionAtsId = json.getString("position_ats_id");
		Position pos = account.getDefaultPosition();
		return pos;
	}

	public AccountSurvey getSurveyFrom(JSONObject job, Account account) {

		Object assessmenttype = job.opt("assessmenttypw");
logger.info("assessment type is: " + assessmenttype);

		AccountSurvey aSurvey = null;
		aSurvey = account.getDefaultAccountSurvey();
		
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
		// TODO Auto-generated method stub
		String applicantAtsId = json.getString("userId");
		JSONObject applicant = getPerson(applicantAtsId, this.trimPrefix(account.getAccountAtsId()));
logger.info("Retrieved: " + applicant.toString());
		Person person = new Person();
		person.setPersonAtsId(this.getPrefix() + applicantAtsId);
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
		Position position = this.getPositionFrom(applicant, account);
		Location location = this.getLocationFrom(applicant, account);
		AccountSurvey aSurvey = this.getSurveyFrom(applicant, account);

		Respondant respondant = new Respondant();
		respondant.setRespondantAtsId(person.getPersonAtsId());
		respondant.setRespondantRedirectUrl(json.getString("returnUrl"));
		//respondant.setRespondantEmailRecipient(delivery.optString("scores_email_address"));
		//respondant.setRespondantScorePostMethod(delivery.optString("scores_post_url"));
		respondant.setAccount(account);
		respondant.setPosition(position);
		respondant.setRespondantLocationId(location.getLocationId());
		respondant.setAccountSurvey(aSurvey);

		person.persistMe();
		respondant.setPerson(person);
		respondant.persistMe();

		return respondant;
	}

	@Override
	public JSONObject prepOrderResponse(JSONObject json, Respondant respondant) {
		// TODO Auto-generated method stub
		return null;
	}

	private JSONObject getPerson(String appId, String accountId) {
		String getString = ICIMS_API + accountId + "/people/" + appId;
		JSONObject response = new JSONObject(icimsGet(getString));
		return response;
	}

	private JSONObject getLocation(String locationId, String accountId) {
		String getString = ICIMS_API + accountId + "/locations/" + locationId;
		JSONObject response = new JSONObject(icimsGet(getString));
		return response;
	}
	
	private JSONObject getJob(String jobId, String accountId) {
		String getString = ICIMS_API + accountId + "/jobs/" + jobId;
		JSONObject response = new JSONObject(icimsGet(getString));
		return response;
	}

	
	public static String icimsGet(String getTarget) {
		
		ClientConfig cc = new ClientConfig();
		cc.property(ApacheClientProperties.PREEMPTIVE_BASIC_AUTHENTICATION, true);
		cc.property(ClientProperties.PROXY_URI, PROXY_URL);
		cc.connectorProvider(new ApacheConnectorProvider());
		Client client = ClientBuilder.newClient(cc);

		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(ICIMS_USER, ICIMS_PASS);
		client.register(feature);

		WebTarget target = client.target(getTarget);
		
		String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
		
		return response;
		
	}

	public static String icimsPost(String postTarget, JSONObject json) {
		
		ClientConfig cc = new ClientConfig();
		cc.property(ApacheClientProperties.PREEMPTIVE_BASIC_AUTHENTICATION, true);
		cc.property(ClientProperties.PROXY_URI, PROXY_URL);
		cc.connectorProvider(new ApacheConnectorProvider());
		Client client = ClientBuilder.newClient(cc);

		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(ICIMS_USER, ICIMS_PASS);
		client.register(feature);

		WebTarget target = client.target(postTarget);
		String response = null;		
		
		response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(json.toString(), MediaType.APPLICATION_JSON), String.class);
		
		return response;
		
	}
	
	
	public static void main (String[] args) throws Exception{
		
	/*	
		Each entry in the “assessmentresults” field contain following fields:
			Date: The date that this assessment’s status was last updated.
			Name: The name of the assessment. Vendor’s name should be included as part of the assessment name as multiple vendors can potentially be used for assessments.
			Notes: Short string used to communicate information to the recruiter.
			Result: Text string used to display results to the recruiter. The values for this field are client and vendor dependent. They can range from names of colors to grading from A through F.
			Score: A decimal used to numerically score the assessment.
			Status: Indicates what stage the assessment is in.
			Complete: The assessment has been completed and reviewed.
			Incomplete: The assessment was not been completed by the candidate. (ie. Due to timeout)
			In-Progress: The assessment is being worked on by the candidate.
			Sent: The assessment has been sent to the candidate.
			URL: Takes the recruiter into the vendor’s platform and displays the results of the assessment. Can also display additional metadata and can be used to modify/cancel the request after the assessment is scheduled. Based on the client’s security requirements, this URL can require the user to login to the vendor’s platform.

			https://api.icims.com/customers/{customerId}/applicantworkflows/{applicantworkflowId}

	*
	*/	
		
		JSONObject json = new JSONObject();
		json.put("userId","1240");
		json.put("customerId","6269");
		json.put("returnUrl","https://www.google.com");
		
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, new TrustManager[]{new X509TrustManager() {
	        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
	        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
	        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
	    }}, new java.security.SecureRandom());
		
		Client client = ClientBuilder.newBuilder().sslContext(sslContext).build();

		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("icims", "FGx4bgfZ!C");
		client.register(feature);

		WebTarget target = client.target("https://localhost/integration/icimsapplicationcomplete");
		
		Response response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(json.toString(), MediaType.APPLICATION_JSON));
		
		System.out.println("Response Status: " + response.getStatus());
		System.out.println("Response Status Phrase: " + response.getStatusInfo().getReasonPhrase());
		System.out.println("Response URI: " + response.getLocation());
		System.out.println("Response Media Type: " + response.getMediaType());
		if (response.hasEntity()) System.out.println("Response Entity: " + response.readEntity(String.class));
	}
	
}
