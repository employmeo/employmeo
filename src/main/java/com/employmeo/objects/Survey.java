package com.employmeo.objects;

import java.io.Serializable;
import javax.persistence.*;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.math.BigInteger;
import java.util.List;


/**
 * The persistent class for the surveys database table.
 * 
 */
@Entity
@Table(name="surveys")
@NamedQuery(name="Survey.findAll", query="SELECT s FROM Survey s")
public class Survey extends PersistantObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SURVEY_ID")
	private BigInteger surveyId;

	@Column(name="MODIFIED_DATE")
	private Timestamp modifiedDate;

	@Column(name="SURVEY_NAME")
	private String surveyName;

	@Column(name="SURVEY_STATUS")
	private int surveyStatus;

	@Column(name="SURVEY_TYPE")
	private int surveyType;
	
	@Column(name="SURVEY_RENDER_PAGE")
	private String surveyRenderPage;
	
	@Column(name="SURVEY_REDIRECT_PAGE")
	private String surveyRedirectPage;

	//bi-directional many-to-one association to SurveyQuestion
	@OneToMany(mappedBy="survey")
	private List<SurveyQuestion> surveyQuestions;

	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="SURVEY_ACCOUNT_ID")
	private Account account;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="SURVEY_CREATOR")
	private User user;

	//bi-directional many-to-one association to Position
	@ManyToOne
	@JoinColumn(name="SURVEY_POSITION")
	private Position position;
	
	//bi-directional many-to-one association to Respondant
	@OneToMany(mappedBy="survey")
	private List<Respondant> respondants;

	public Survey() {
	}

	public BigInteger getSurveyId() {
		return this.surveyId;
	}

	public void setSurveyId(BigInteger surveyId) {
		this.surveyId = surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = new BigInteger(surveyId);
	}	
	
	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getSurveyName() {
		return this.surveyName;
	}
	
	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}
	
	public void setSurveyRenderPage(String surveyRenderPage) {
		this.surveyRenderPage = surveyRenderPage;
	}
	
	public String getSurveyRenderPage() {
		return this.surveyRenderPage;
	}

	public String getSurveyRedirectPage() {
		return this.surveyRedirectPage;
	}

	public void setSurveyRedirectPage(String surveyRedirectPage) {
		this.surveyRedirectPage = surveyRedirectPage;
	}
	
	public int getSurveyStatus() {
		return this.surveyStatus;
	}

	public void setSurveyStatus(int surveyStatus) {
		this.surveyStatus = surveyStatus;
	}

	public int getSurveyType() {
		return this.surveyType;
	}

	public void setSurveyType(int surveyType) {
		this.surveyType = surveyType;
	}

	public List<SurveyQuestion> getSurveyQuestions() {
		return this.surveyQuestions;
	}

	public void setSurveyQuestions(List<SurveyQuestion> surveyQuestions) {
		this.surveyQuestions = surveyQuestions;
	}

	public SurveyQuestion addSurveyQuestion(SurveyQuestion surveyQuestion) {
		getSurveyQuestions().add(surveyQuestion);
		surveyQuestion.setSurvey(this);

		return surveyQuestion;
	}

	public SurveyQuestion removeSurveyQuestion(SurveyQuestion surveyQuestion) {
		getSurveyQuestions().remove(surveyQuestion);
		surveyQuestion.setSurvey(null);

		return surveyQuestion;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public List<Respondant> getSurveyRespondants() {
		return this.respondants;
	}

	public static Survey getSurveyById(String lookupId) {
		
		return getSurveyById(new BigInteger(lookupId));
		
	}
	
	public static Survey getSurveyById(BigInteger lookupId) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("employmeo");
		EntityManager em = emf.createEntityManager();
		TypedQuery<Survey> q = em.createQuery("SELECT s FROM Survey s WHERE s.surveyId = :surveyId", Survey.class);
        q.setParameter("surveyId", lookupId);
        Survey survey = null;
        try {
      	  survey = q.getSingleResult();
        } catch (NoResultException nre) {}
        
        return survey;
	}

	@Override
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("survey_id", this.surveyId);
		json.put("survey_name", this.surveyName);
		json.put("survey_status", this.surveyStatus);
		json.put("survey_type", this.surveyType);
		json.put("survey_render_page", this.surveyRenderPage);
		json.put("survey_redirect_page", this.surveyRedirectPage);
		if (this.user != null) json.put("survey_creator", this.user.getJSON());
		if (this.account != null) json.put("survey_account", this.account.getJSON());
		if (this.position != null) json.put("survey_position", this.position.getJSON());
		
		for (int i=0; i<this.surveyQuestions.size();i++) {
			json.accumulate("questions", this.surveyQuestions.get(i).getQuestion().getJSON());
		}
		
		return json;
	}
	

}