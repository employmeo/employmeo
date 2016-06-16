package com.employmeo.objects;

import java.io.Serializable;
import javax.persistence.*;

import org.json.JSONObject;


/**
 * The persistent class for the survey_sections database table.
 * 
 */
@Entity
@Table(name="survey_sections")
@NamedQuery(name="SurveySection.findAll", query="SELECT s FROM SurveySection s")
public class SurveySection extends PersistantObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SurveySectionPK id;

	@Column(name="ss_all_required")
	private Boolean ssAllRequired;

	@Column(name="ss_instructions")
	private String ssInstructions;

	@Column(name="ss_questions_per_page")
	private Integer ssQuestionsPerPage;

	@Column(name="ss_timed")
	private Boolean ssTimed;

	public SurveySection() {
	}

	public SurveySectionPK getId() {
		return this.id;
	}

	public void setId(SurveySectionPK id) {
		this.id = id;
	}

	public Boolean getSsAllRequired() {
		return this.ssAllRequired;
	}

	public void setSsAllRequired(Boolean ssAllRequired) {
		this.ssAllRequired = ssAllRequired;
	}

	public String getSsInstructions() {
		return this.ssInstructions;
	}

	public void setSsInstructions(String ssInstructions) {
		this.ssInstructions = ssInstructions;
	}

	public Integer getSsQuestionsPerPage() {
		return this.ssQuestionsPerPage;
	}

	public void setSsQuestionsPerPage(Integer ssQuestionsPerPage) {
		this.ssQuestionsPerPage = ssQuestionsPerPage;
	}

	public Boolean getSsTimed() {
		return this.ssTimed;
	}

	public void setSsTimed(Boolean ssTimed) {
		this.ssTimed = ssTimed;
	}

	@Override
	public JSONObject getJSON() {
		// TODO Auto-generated method stub
		return null;
	}

}