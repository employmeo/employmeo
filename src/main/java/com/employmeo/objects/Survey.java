package com.employmeo.objects;

import java.io.Serializable;
import javax.persistence.*;

import org.json.JSONObject;

import com.employmeo.util.DBUtil;

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
	private Long surveyId;

	@Column(name="SURVEY_NAME")
	private String surveyName;

	@Column(name="SURVEY_STATUS")
	private int surveyStatus;

	@Column(name="SURVEY_TYPE")
	private int surveyType;
	
	//bi-directional many-to-one association to SurveyQuestion
	@OneToMany(mappedBy="survey",fetch=FetchType.EAGER)
	private List<SurveyQuestion> surveyQuestions;
	
	//bi-directional many-to-one association to Respondant
	@OneToMany(mappedBy="survey")
	private List<Respondant> respondants;

	public Survey() {
	}

	public Long getSurveyId() {
		return this.surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = new Long(surveyId);
	}	
	
	public String getSurveyName() {
		return this.surveyName;
	}
	
	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
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

	public List<Respondant> getSurveyRespondants() {
		return this.respondants;
	}

	public static Survey getSurveyById(String lookupId) {
		
		return getSurveyById(new Long(lookupId));
		
	}
	
	public static Survey getSurveyById(Long lookupId) {

		EntityManager em = DBUtil.getEntityManager();
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
		
		for (int i=0; i<this.surveyQuestions.size();i++) {
			json.accumulate("questions", this.surveyQuestions.get(i).getQuestion().getJSON());
		}
		
		return json;
	}
	

}