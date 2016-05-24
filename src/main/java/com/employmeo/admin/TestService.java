package com.employmeo.admin;

import javax.annotation.security.PermitAll;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.employmeo.objects.Answer;
import com.employmeo.objects.Question;
import com.employmeo.objects.Survey;
import com.employmeo.objects.SurveyQuestion;
import com.employmeo.util.DBUtil;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@Path("test")
@PermitAll
public class TestService {

	private static Logger logger = Logger.getLogger("TestService");
	private static JSONObject questionTypes = new JSONObject();
	private static HashMap<String, Survey> surveylist = new HashMap<String, Survey>();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String doMethod(String stringdata) {

		EntityManager em = DBUtil.getEntityManager();		
		TypedQuery<Question> query = em.createQuery("SELECT q FROM Question q WHERE q.questionId > :idmin", Question.class);
		query.setParameter("idmin", 200);
		List<Question> questions = query.getResultList();
		for (int i=0; i<questions.size();i++) {
			Survey survey = getSurvey(questions.get(i).getQuestionForeignSource());
			SurveyQuestion sq = new SurveyQuestion();
			sq.setQuestion(questions.get(i));
			sq.setSurvey(survey);
			sq.setSqSeqence(questions.get(i).getQuestionForeignId());
			sq.persistMe();
		}
/*		
		for (int i=0; i<data.length();i++) {
			JSONObject record = data.getJSONObject(i);
			Question q = new Question();
			q.setQuestionText(record.getString("Question Text"));
			q.setQuestionType(questionType(record.getString("Ques Type")));
			q.setQuestionDescription(record.getString("Survey"));
			q.setQuestionDisplayId(record.getLong("Qnum"));
			q.setQuestionCorefactorId(34);
			q.persistMe();
			for (int j=1;j<6;j++) {			
				Object ans = record.get("Answer " + j + " Text");
				String text = ans.toString();
			    if ((text != null) && (!text.isEmpty())) {
			    	Answer a = new Answer();
			    	a.setQuestion(q);
			    	a.setAnswerValue(j);
			    	a.setAnswerDisplayId((long)j);
			    	a.setAnswerText(text);
			    	a.setAnswerDescription("Mercer");
			    	a.persistMe();
			    }
			}
		}
*/		
		logger.info("questionTypes: " + questionTypes);
		
		return stringdata;
	}

	private Survey getSurvey(String surveyName) {
		
		if (surveylist.containsKey(surveyName)) return surveylist.get(surveyName);

		logger.info("Didn't find: " + surveyName);
		Survey survey;
		try {
			EntityManager em = DBUtil.getEntityManager();		
			TypedQuery<Survey> query = em.createQuery("SELECT s FROM Survey s WHERE s.surveyName = :name", Survey.class);
			query.setParameter("name", "Mercer: " + surveyName);
			survey = query.getSingleResult();
			logger.info("Got Survey: " + survey.getJSONString());
		} catch (Exception e) {
			survey = new Survey();
			survey.setSurveyDescription(surveyName);
			survey.setSurveyName("Mercer: " + surveyName);
			survey.setSurveyType(1);
			survey.setSurveyStatus(1);
			survey.persistMe();
			logger.info("Created Survey: " + survey);
		}
		surveylist.put(surveyName, survey);
		return survey;
	}
	
	private int questionType(String type) {
		if (!questionTypes.has(type)) {
			int val = 6;
			switch(type) {
			case "yes-idk-no":
				val = 7;
			case "yes-sometimes-no":
				val = 8;
			case "multi-five-short":
				val = 9;
			case "yes-no-unsure":
				val = 10;
			case "multi-two":
				val = 11;
			case "multi-four-short":
				val = 12;
			case "multi-three-choice":
				val = 13;
			case "rank":
				val = 14;
			case "multichoice":
				val = 6;
			case "alike-unlike":
				val = 15;
			default:
				val = 6;
			}
			questionTypes.put(type, val);
		} 
		return questionTypes.getInt(type);
	}
}	