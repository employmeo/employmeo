package com.employmeo.objects;

import java.io.Serializable;
import java.net.URL;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.UriInfo;

import org.json.JSONObject;

import com.employmeo.util.DBUtil;
import com.employmeo.util.EmailServletResponse;
import com.employmeo.util.EmailUtility;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;


/**
 * The persistent class for the respondants database table.
 * 
 */
@Entity
@Table(name="respondants")
@NamedQuery(name="Respondant.findAll", query="SELECT r FROM Respondant r")
public class Respondant extends PersistantObject implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int STATUS_INVITED = 1;
	public static final int STATUS_STARTED = 5;
	public static final int STATUS_COMPLETED = 10;
	public static final int STATUS_SCORED = 15;
	public static final int STATUS_REJECTED = 16;
	public static final int STATUS_OFFERED = 17;
	public static final int STATUS_DECLINED = 18;
	public static final int STATUS_HIRED = 20;
	public static final int STATUS_QUIT = 30;
	public static final int STATUS_TERMINATED = 40;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="respondant_id")
	private Long respondantId;

	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="respondant_account_id",insertable=false,updatable=false)
	private Account account;

	@Column(name="respondant_account_id",insertable=true,updatable=false)
	private Long respondantAccountId;

	@Column(name="respondant_created_date", insertable=false, updatable=false)
	private Date respondantCreatedDate;

	@Column(name="respondant_status")
	private int respondantStatus;

	@ManyToOne
	@JoinColumn(name="respondant_survey_id",insertable=false,updatable=false)
	private Survey survey;

	@Column(name="respondant_survey_id",insertable=true,updatable=false)
	private Long respondantSurveyId;
	
	@ManyToOne
	@JoinColumn(name="respondant_position_id",insertable=false,updatable=false)
	private Position position;

	@Column(name="respondant_position_id",insertable=true,updatable=true)
	private Long respondantPositionId;

	@ManyToOne
	@JoinColumn(name="respondant_location_id",insertable=false,updatable=false)
	private Location location;

	@Column(name="respondant_location_id",insertable=true,updatable=true)
	private Long respondantLocationId;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="respondant_person_id")
	private Person person;

	//bi-directional many-to-one association to Responses
	@OneToMany(mappedBy="respondant")
	private List<Response> responses;
	
	// Scoring info
	@Column(name="respondant_profile")
	private String respondantProfile;

	@Column(name="respondant_profile_a")
	private Double profileA;

	@Column(name="respondant_profile_b")
	private Double profileB;
	
	@Column(name="respondant_profile_c")
	private Double profileC;

	@Column(name="respondant_profile_d")
	private Double profileD;

	@Column(name="respondant_ats_id")
	private String respondantAtsId;

	@Column(name="respondant_payroll_id")
	private String respondantPayrollId;

	@Column(name = "respondant_redirect_page")
	private String respondantRedirectUrl;

	@Column(name="respondant_email_recipient")
	private String respondantEmailRecipient;

	@Column(name="respondant_score_postmethod")
	private String respondantScorePostMethod;

	@Column(name="respondant_start_time")
	private Timestamp respondantStartTime;

	@Column(name="respondant_finish_time")
	private Timestamp respondantFinishTime;
	
	@Column(name="respondant_hire_date")
	private Date respondantHireDate;
	
	
	
	public Respondant() {
	}

	public Long getRespondantId() {
		return this.respondantId;
	}
	public void setRespondantId(Long respondantId) {
		this.respondantId = respondantId;
	}

	public Long getRespondantAccountId() {
		return this.respondantAccountId;
	}
	public Account getRespondantAccount() {
		if (this.account == null) this.account = Account.getAccountById(this.respondantAccountId);
		return this.account;
	}
	public void setRespondantAccountId(Long accountId) {
		this.respondantAccountId = accountId;
		this.account = Account.getAccountById(accountId);
	}
	public void setAccount(Account account) {
		this.account = account;
		this.respondantAccountId = account.getAccountId();
	}
	
	public Long getRespondantLocationId() {
		return this.respondantLocationId;
	}
	public Location getLocation() {
		if (this.location == null) this.location = Location.getLocationById(this.respondantLocationId);
		return this.location;
	}	
	public void setRespondantLocationId(Long locationId) {
		this.respondantLocationId = locationId;
		this.location = Location.getLocationById(this.respondantLocationId);
	}
	
	public Long getRespondantPositionId() {
		if (this.respondantPositionId == null) this.respondantPositionId = this.position.getPositionId();
		return this.respondantPositionId;
	}
	public Position getPosition() {
		if (this.position == null) this.position = Position.getPositionById(this.respondantPositionId);
		return this.position;
	}
	public void setRespondantPositionId(Long positionId) {
		this.respondantPositionId = positionId;
		this.position = Position.getPositionById(positionId);
	}
	public void setPosition(Position position) {
		this.respondantPositionId = position.getPositionId();
		this.position = position;
	}
	
	public Date getRespondantCreatedDate() {
		return this.respondantCreatedDate;
	}
	public void setRespondantCreatedDate(Date respondantCreatedDate) {
		this.respondantCreatedDate = respondantCreatedDate;
	}

	public Date getRespondantHireDate() {
		return this.respondantHireDate;
	}
	public void setRespondantHireDate(Date respondantHireDate) {
		this.respondantHireDate = respondantHireDate;
	}

	public int getRespondantStatus() {
		return this.respondantStatus;
	}
	public void setRespondantStatus(int respondantStatus) {
		this.respondantStatus = respondantStatus;
	}

	public String getRespondantProfile() {
		return this.respondantProfile;
	}
	public void setRespondantProfile(String profile) {
		this.respondantProfile = profile;
	}
	
	public Double getProfileA() {
		return this.profileA;
	}
	public void setProfileA(Double probability) {
		this.profileA = probability;
	}

	public Double getProfileB() {
		return this.profileB;
	}
	public void setProfileB(Double probability) {
		this.profileB = probability;
	}

	public Double getProfileC() {
		return this.profileC;
	}
	public void setProfileC(Double probability) {
		this.profileC = probability;
	}

	public Double getProfileD() {
		return this.profileD;
	}
	public void setProfileD(Double probability) {
		this.profileD = probability;
	}
	
	public Long getRespondantSurveyId() {
		return this.respondantSurveyId;
	}	
	public void setRespondantSurveyId(Long surveyId) {
		this.respondantSurveyId = surveyId;
	}
	public Survey getSurvey() {
		if (this.survey == null) this.survey = Survey.getSurveyById(this.respondantSurveyId);
		return this.survey;
	}
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Person getPerson() {
		return this.person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public List<Response> getResponses() {
		return this.responses;
	}
	
	public void setRespondantAtsId (String atsId) {
		this.respondantAtsId = atsId;
	}
	public String getRespondantAtsId () {
		return this.respondantAtsId;
	}
	
	public void setRespondantPayrollId (String payrollId) {
		this.respondantAtsId = payrollId;
	}	
	public String getRespondantPayrollId() {
		return this.respondantAtsId;
	}
	
	public String getRespondantRedirectUrl() {
		return this.respondantRedirectUrl;
	}
	public void setRespondantRedirectUrl(String respondantRedirectUrl) {
		this.respondantRedirectUrl = respondantRedirectUrl;
	}
	
	public String getRespondantScorePostMethod() {
		return this.respondantScorePostMethod;
	}
	public void setRespondantScorePostMethod(String respondantScorePostMethod) {
		this.respondantScorePostMethod = respondantScorePostMethod;
	}
	
	public String getRespondantEmailRecipient() {
		return this.respondantEmailRecipient;
	}
	public void setRespondantEmailRecipient(String respondantEmailRecipient) {
		this.respondantEmailRecipient = respondantEmailRecipient;
	}
	
	public void setRespondantStartTime(Timestamp start) {
		this.respondantStartTime = start;
	}
	public Timestamp getRespondantStartTime() {
		return this.respondantStartTime;
	}

	public void setRespondantFinishTime (Timestamp finish) {
		this.respondantFinishTime = finish;
	}
	public Timestamp getRespondantFinishTime () {
		return this.respondantFinishTime;
	}

	public static Respondant getRespondantById(String lookupId) {		
		return getRespondantById(new Long(lookupId));
	}
	
	public static Respondant getRespondantById(Long lookupId) {
		EntityManager em = DBUtil.getEntityManager();
		TypedQuery<Respondant> q = em.createQuery("SELECT r FROM Respondant r WHERE r.respondantId = :respondantId", Respondant.class);
        q.setParameter("respondantId", lookupId);
        Respondant respondant = null;
        try {
      	  respondant = q.getSingleResult();
        } catch (NoResultException nre) {}
        
        return respondant;
	}
	
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("respondant_id", this.respondantId);
		json.put("respondant_created_date", this.respondantCreatedDate);
		json.put("respondant_status", this.respondantStatus);
		json.put("respondant_profile", this.respondantProfile);
		json.put("respondant_profile_a", this.profileA);
		json.put("respondant_profile_b", this.profileB);
		json.put("respondant_profile_c", this.profileC);
		json.put("respondant_profile_d", this.profileD);
		json.put("respondant_redirect_url", this.respondantRedirectUrl);

		if (this.account != null) json.put("respondant_account_id", this.account.getAccountId());		
		if (this.survey != null) json.put("respondant_survey_id", this.survey.getSurveyId());
		if (this.survey != null) json.put("respondant_survey_name", this.survey.getSurveyName());
		if (this.getRespondantLocationId() != null) json.put("respondant_location_id", this.getRespondantLocationId());
		if (this.getLocation() != null) json.put("respondant_location_name", this.location.getLocationName());
		if (this.getRespondantPositionId() != null) json.put("respondant_position_id", this.getRespondantPositionId());
		if (this.getPosition() != null) json.put("respondant_position_name", this.position.getPositionName());

		if (this.person != null) {
			json.put("respondant_person_fname", this.person.getPersonFname());
			json.put("respondant_person_lname", this.person.getPersonLname());
			json.put("respondant_person_email", this.person.getPersonEmail());			
			json.put("respondant_person_address", this.person.getPersonAddress());			
		} 	
		
		if (this.respondantProfile != null) {
			PositionProfile profile = PositionProfile.getProfileDefaults(this.getRespondantProfile());
		    json.put("respondant_profile_icon", profile.get("profile_icon"));
		    json.put("respondant_profile_class", profile.get("profile_class"));
		}
		
		return json;
	}
	
	
	/***
	 *  Special section for unique functionality 
	 *  for the Respondant Object
	 */

	
	public JSONObject scoreMe() {
		  JSONObject scores = new JSONObject();
		  
		  if (this.getRespondantStatus() < Respondant.STATUS_COMPLETED) {
			  System.out.println("CANT SCORE INCOMPLETE ASSESSMENT FOR:\n" + this.getJSONString());
		  } else {
			  if (this.getRespondantStatus() >= Respondant.STATUS_SCORED) {
				  System.out.println("Pulling up old scores for:\n" + this.getJSONString());
				  EntityManager em = DBUtil.getEntityManager();
				  TypedQuery<RespondantScore> q = em.createQuery("SELECT r FROM RespondantScore r WHERE r.rsRespondantId = :respondantId", RespondantScore.class);
		          q.setParameter("respondantId", this.getRespondantId());		  
				  List<RespondantScore> rs = q.getResultList();
				  for (int i=0; i<rs.size(); i++) {
					  Corefactor corefactor = Corefactor.getCorefactorById(rs.get(i).getRsCfId());			  
					  scores.put(corefactor.getCorefactorName(),rs.get(i).getRsValue());					  
				  }
			  } else {
				  System.out.println("Do scoring for:\n" + this.getJSONString());
		  
				  List<Response> responses = this.getResponses();
				  int[] count = new int[20];
				  int[] score = new int[20];
				  
				  for (int i=0;i<responses.size();i++) {
					  Response response = responses.get(i);
					  Integer cfId = Question.getQuestionById(response.getResponseQuestionId()).getQuestionCorefactorId();
					  count[cfId]++;
					  score[cfId]+=response.getResponseValue();
				  }
	
				  for (int i=0; i<20; i++) {
					  if (count[i]>0) {
						  RespondantScore rs = new RespondantScore();
						  rs.setPK(i, this.getRespondantId());
						  rs.setRsQuestionCount(count[i]);
						  Corefactor corefactor = Corefactor.getCorefactorById(i);
						  rs.setRsValue((double) Math.round(100.0 *((double)score[i]/(double)count[i]))/ 100.0);
						  rs.mergeMe();
						  scores.put(corefactor.getCorefactorName(),rs.getRsValue());
					  }
				  }
				  this.setRespondantStatus(Respondant.STATUS_SCORED);
				  this.mergeMe();
			  }
		  }
		  return scores;
	}

	public void sendEmailInvitation(HttpServletRequest req) {
		// TODO switch over to a maintainable template on sendgrid.
		String link = EmailUtility.getAssessmentLink(this);
		String body = "Dear " + this.person.getPersonFname() + ",\n" +
		 			"\n" +
		  			"Congratulations, we are excited to invite you to complete a preliminary " +
		  			"assessment for this position.\nThis assessment can be completed on a " + 
		  			"mobile device or in a browser at this link: \n" + link;
		try {
			  req.getSession().setAttribute("applicant", this.person);
			  req.getSession().setAttribute("link", link);
			  EmailServletResponse htmlpart = new EmailServletResponse();
			  req.getRequestDispatcher("/WEB-INF/emails/inviteapplicant.jsp").forward(req, htmlpart);
			  EmailUtility.sendMessage(this.person.getPersonEmail(), "Invitation to Apply", body, htmlpart);
		} catch (Exception e) {
			  // if using the jsp email template fails, just send basic body text.
			  EmailUtility.sendMessage(this.person.getPersonEmail(), "Invitation to Apply", body);		  
		}
	}
	
	public void postScoresToATS() {
		// TODO Stub code to send the final scores somewhere...
	}

}