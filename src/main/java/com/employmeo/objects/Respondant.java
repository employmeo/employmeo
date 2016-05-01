package com.employmeo.objects;

import java.io.Serializable;
import javax.persistence.*;

import org.json.JSONObject;

import com.employmeo.util.DBUtil;

import java.sql.Timestamp;
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
	public static final int STATUS_HIRED = 20;
	public static final int STATUS_REJECTED = 19;

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
	private Timestamp respondantCreatedDate;

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
	
	public void setRespondantAccountId(Long accountId) {
		this.respondantAccountId = accountId;
		this.account = Account.getAccountById(accountId);
	}
	public Account getRespondantAccount() {
		if (this.account == null) this.account = Account.getAccountById(this.respondantAccountId);
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
		this.respondantAccountId = account.getAccountId();
	}
	
	public Long getRespondantLocationId() {
		return this.respondantLocationId;
	}
	
	public void setRespondantLocationId(Long locationId) {
		this.respondantLocationId = locationId;
		this.location = Location.getLocationById(this.respondantLocationId);
	}

	public Location getLocation() {
		if (this.location == null) this.location = Location.getLocationById(this.respondantLocationId);
		return this.location;
	}

	public Long getRespondantPositionId() {
		return this.respondantPositionId;
	}
	
	public void setRespondantPositionId(Long positionId) {
		this.respondantPositionId = positionId;
	}
	
	public Position getPosition() {
		if (this.position == null) this.position = Position.getPositionById(this.respondantPositionId);
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	public Timestamp getRespondantCreatedDate() {
		return this.respondantCreatedDate;
	}

	public void setRespondantCreatedDate(Timestamp respondantCreatedDate) {
		this.respondantCreatedDate = respondantCreatedDate;
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
	
	public List<RespondantScore> getScores() {

		EntityManager em = DBUtil.getEntityManager();
		TypedQuery<RespondantScore> q = em.createQuery("SELECT r FROM RespondantScore r WHERE r.rsRespondantId = :respondantId", RespondantScore.class);
        q.setParameter("respondantId", this.getRespondantId());
        
        return q.getResultList();
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
		return json;
	}
	
	
	public JSONObject scoreMe() {
		  JSONObject scores = new JSONObject();
		  
		  if (this.getRespondantStatus() < Respondant.STATUS_COMPLETED) {
			  System.out.println("CANT SCORE INCOMPLETE ASSESSMENT FOR:\n" + this.getJSONString());
		  } else {
			  if (this.getRespondantStatus() >= Respondant.STATUS_SCORED) {
				  List<RespondantScore> rs = this.getScores();
				  for (int i=0; i<rs.size(); i++) {
					  Corefactor corefactor = Corefactor.getCorefactorById(rs.get(i).getRsCfId());			  
					  scores.put(corefactor.getCorefactorName(),rs.get(i).getRsValue());					  
				  }
			  } else {
		  
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
}