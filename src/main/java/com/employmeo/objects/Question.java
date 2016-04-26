package com.employmeo.objects;

import java.io.Serializable;
import javax.persistence.*;

import org.json.JSONObject;

import com.employmeo.util.DBUtil;

import java.util.List;


/**
 * The persistent class for the questions database table.
 * 
 */
@Entity
@Table(name="questions")
@NamedQuery(name="Question.findAll", query="SELECT q FROM Question q")
public class Question extends PersistantObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="QUESTION_ID")
	private Long questionId;

	@Column(name="MODIFIED_DATE")
	private int modifiedDate;

	@Column(name="QUESTION_DESCRIPTION")
	private String questionDescription;

	@Column(name="QUESTION_DISPLAY_ID")
	private Long questionDisplayId;

	@Column(name="QUESTION_TEXT")
	private String questionText;

	@Column(name="QUESTION_TYPE")
	private int questionType;
	
	@Column(name="QUESTION_COREFACTOR_ID")
	private int questionCorefactorId;
	
	@Column(name="QUESTION_DIRECTION")
	private int questionDirection;

	//bi-directional many-to-one association to Answer
	@OneToMany(mappedBy="question",fetch=FetchType.EAGER)
	private List<Answer> answers;

	//bi-directional many-to-one association to Response
	@OneToMany(mappedBy="question")
	private List<Response> responses;

	//bi-directional many-to-one association to SurveyQuestion
	@OneToMany(mappedBy="question")
	private List<SurveyQuestion> surveyQuestions;

	public Question() {
	}

	public Long getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public int getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(int modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getQuestionDescription() {
		return this.questionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}

	public Long getQuestionDisplayId() {
		return this.questionDisplayId;
	}

	public void setQuestionDisplayId(Long questionDisplayId) {
		this.questionDisplayId = questionDisplayId;
	}

	public String getQuestionText() {
		return this.questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public int getQuestionType() {
		return this.questionType;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	public int getQuestionCorefactorId() {
		return this.questionCorefactorId;
	}

	public void setQuestionCorefactorId(int questionCorefactorId) {
		this.questionCorefactorId = questionCorefactorId;
	}

	public int getQuestionDirection() {
		return this.questionDirection;
	}

	public void setQuestionDirection(int questionDirection) {
		this.questionDirection = questionDirection;
	}

	public List<Answer> getAnswers() {
		return this.answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Answer addAnswer(Answer answer) {
		getAnswers().add(answer);
		answer.setQuestion(this);

		return answer;
	}

	public Answer removeAnswer(Answer answer) {
		getAnswers().remove(answer);
		answer.setQuestion(null);

		return answer;
	}

	public List<Response> getResponses() {
		return this.responses;
	}

	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}

	public Response addResponse(Response response) {
		getResponses().add(response);
		response.setQuestion(this);

		return response;
	}

	public Response removeResponse(Response response) {
		getResponses().remove(response);
		response.setQuestion(null);

		return response;
	}

	public List<SurveyQuestion> getSurveyQuestions() {
		return this.surveyQuestions;
	}

	public void setSurveyQuestions(List<SurveyQuestion> surveyQuestions) {
		this.surveyQuestions = surveyQuestions;
	}

	public SurveyQuestion addSurveyQuestion(SurveyQuestion surveyQuestion) {
		getSurveyQuestions().add(surveyQuestion);
		surveyQuestion.setQuestion(this);

		return surveyQuestion;
	}

	public SurveyQuestion removeSurveyQuestion(SurveyQuestion surveyQuestion) {
		getSurveyQuestions().remove(surveyQuestion);
		surveyQuestion.setQuestion(null);

		return surveyQuestion;
	}
	
	public static Question getQuestionById(String lookupId) {
		
		return getQuestionById(new Long(lookupId));
		
	}
	
	public static Question getQuestionById(Long lookupId) {

		EntityManager em = DBUtil.getEntityManager();
		TypedQuery<Question> q = em.createQuery("SELECT q FROM Question q WHERE q.questionId = :questionId", Question.class);
        q.setParameter("questionId", lookupId);
        Question question = null;
        try {
      	  question = q.getSingleResult();
        } catch (NoResultException nre) {}
        
        return question;
	}

	@Override
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("question_id", this.questionId);
		json.put("question_description", this.questionDescription);
		json.put("question_display_id", this.questionDisplayId);
		json.put("question_text", this.questionText);
		json.put("question_type", this.questionType);
		json.put("question_corefactor_id", this.questionCorefactorId);
		json.put("question_direction", this.questionDirection);
		for (int i=0; i<this.answers.size();i++) {
			json.accumulate("answers", this.answers.get(i).getJSON());
		}
		return json;
	}
	

}