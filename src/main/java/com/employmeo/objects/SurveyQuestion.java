package com.employmeo.objects;

import java.io.Serializable;
import javax.persistence.*;

import org.json.JSONObject;


/**
 * The persistent class for the survey_questions database table.
 * 
 */
@Entity
@Table(name="survey_questions")
@NamedQuery(name="SurveyQuestion.findAll", query="SELECT s FROM SurveyQuestion s")
public class SurveyQuestion extends PersistantObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SQ_ID")
	private String sqId;

	@Column(name="SQ_DEPENDENCY")
	private boolean sqDependency;

	@Column(name="SQ_REQUIRED")
	private boolean sqRequired;

	@Column(name="SQ_SEQENCE")
	private int sqSeqence;
	
	@Column(name="SQ_PAGE")
	private int sqPage;

	//bi-directional many-to-one association to Question
	@ManyToOne
	@JoinColumn(name="SQ_QUESTION_ID")
	private Question question;

	//bi-directional many-to-one association to Survey
	@ManyToOne
	@JoinColumn(name="SURVEY_ID")
	private Survey survey;

	public SurveyQuestion() {
	}

	public String getSqId() {
		return this.sqId;
	}

	public void setSqId(String sqId) {
		this.sqId = sqId;
	}

	public Object getSqDependency() {
		return this.sqDependency;
	}

	public void setSqDependency(boolean sqDependency) {
		this.sqDependency = sqDependency;
	}

	public boolean getSqRequired() {
		return this.sqRequired;
	}

	public void setSqRequired(boolean sqRequired) {
		this.sqRequired = sqRequired;
	}

	public int getSqSeqence() {
		return this.sqSeqence;
	}

	public void setSqSeqence(int sqSeqence) {
		this.sqSeqence = sqSeqence;
	}

	public int getSqPage() {
		return this.sqPage;
	}

	public void setSqPage(int sqPage) {
		this.sqPage = sqPage;
	}
	
	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Survey getSurvey() {
		return this.survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	@Override
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		return json;
	}

}