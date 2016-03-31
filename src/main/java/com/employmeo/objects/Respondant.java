package com.employmeo.objects;

import java.io.Serializable;
import javax.persistence.*;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.List;
import java.math.BigInteger;


/**
 * The persistent class for the respondants database table.
 * 
 */
@Entity
@Table(name="respondants")
@NamedQuery(name="Respondant.findAll", query="SELECT r FROM Respondant r")
public class Respondant extends PersistantObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="respondant_id")
	private BigInteger respondantId;

	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="respondant_account_id",insertable=false,updatable=false)
	private Account account;

	@Column(name="respondant_account_id",insertable=true,updatable=false)
	private BigInteger respondantAccountId;

	@Column(name="respondant_created_date", insertable=false, updatable=false)
	private Timestamp respondantCreatedDate;

	@Column(name="respondant_status")
	private int respondantStatus;

	@ManyToOne
	@JoinColumn(name="respondant_survey_id",insertable=false,updatable=false)
	private Survey survey;

	@Column(name="respondant_survey_id",insertable=true,updatable=false)
	private BigInteger respondantSurveyId;
	
	@ManyToOne
	@JoinColumn(name="respondant_position_id",insertable=false,updatable=false)
	private Position position;

	@Column(name="respondant_position_id",insertable=true,updatable=false)
	private BigInteger respondantPositionId;

	@ManyToOne
	@JoinColumn(name="respondant_location_id",insertable=false,updatable=false)
	private Location location;

	@Column(name="respondant_location_id",insertable=true,updatable=false)
	private BigInteger respondantLocationId;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="respondant_person_id")
	private Person person;

	//bi-directional many-to-one association to Responses
	@OneToMany(mappedBy="respondant")
	private List<Response> responses;
	
	public Respondant() {
	}

	public BigInteger getRespondantId() {
		return this.respondantId;
	}

	public void setRespondantId(BigInteger respondantId) {
		this.respondantId = respondantId;
	}
	public BigInteger getRespondantAccountId() {
		return this.respondantAccountId;
	}
	
	public void setRespondantAccountId(BigInteger accountId) {
		this.respondantAccountId = accountId;
	}
	public Account getRespondantAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	public BigInteger getRespondantLocationId() {
		return this.respondantLocationId;
	}
	
	public void setRespondantLocationId(BigInteger locationId) {
		this.respondantLocationId = locationId;
	}
	public BigInteger getRespondantPositionId() {
		return this.respondantPositionId;
	}
	
	public void setRespondantPositionId(BigInteger positionId) {
		this.respondantPositionId = positionId;
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
	
	public BigInteger getRespondantSurveyId() {
		return this.respondantSurveyId;
	}
	
	public void setRespondantSurveyId(BigInteger surveyId) {
		this.respondantSurveyId = surveyId;
	}
	public Survey getSurvey() {
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
	
	
	public static Respondant getRespondantById(String lookupId) {
		
		return getRespondantById(new BigInteger(lookupId));
		
	}
	
	public static Respondant getRespondantById(BigInteger lookupId) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("employmeo");
		EntityManager em = emf.createEntityManager();
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
		if (this.account != null) json.put("respondant_account_id", this.account.getAccountId());
		json.put("respondant_created_date", this.respondantCreatedDate);
		if (this.survey != null) json.put("respondant_survey_id", this.survey.getSurveyId());
		if (this.survey != null) json.put("respondant_survey_name", this.survey.getSurveyName());
		if (this.location != null) json.put("respondant_location_id", this.location.getLocationId());
		if (this.location != null) json.put("respondant_location_name", this.location.getLocationName());
		if (this.position != null) json.put("respondant_position_id", this.position.getPositionId());
		if (this.position != null) json.put("respondant_position_name", this.position.getPositionName());

		json.put("respondant_status", this.respondantStatus);
		if (this.person != null) {
			json.put("respondant_person_fname", this.person.getPersonFname());
			json.put("respondant_person_lname", this.person.getPersonLname());
			json.put("respondant_person_email", this.person.getPersonEmail());			
		} 	
		return json;
	}
}