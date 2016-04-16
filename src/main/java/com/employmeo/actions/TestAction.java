package com.employmeo.actions;


import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.Question;
import com.employmeo.objects.Survey;
import com.employmeo.objects.SurveyQuestion;


public class TestAction extends MPFormAction {
	
	  public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		  
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("employmeo");
		EntityManager em = emf.createEntityManager();		  
		TypedQuery<Question> q = em.createQuery("SELECT q FROM Question q WHERE q.questionDisplayId > 50", Question.class);
	    
		List<Question> questions = q.getResultList();

	    JSONObject basic = new JSONObject();
	    JSONObject cctest = new JSONObject();
	    JSONObject bigshort = new JSONObject();
	    JSONObject biglong = new JSONObject();
	    JSONObject allshort = new JSONObject();
	    JSONObject alllong = new JSONObject();

	    int[] counters = new int[20];
	    
	    for (Question question : questions) {
	    	int cfid = question.getQuestionCorefactorId();
   			counters[cfid]++;
	    	switch (cfid) {
	    	case 2:
	    	case 3:
	    		if (counters[cfid]< 11) basic.accumulate("question", question.getQuestionId());
	    	case 4:
	    	case 9:
	    	case 10:
	    		if (counters[cfid]< 6) bigshort.accumulate("question", question.getQuestionId());
	    		if (counters[cfid]< 11) biglong.accumulate("question", question.getQuestionId());
	    	case 11:
	    	case 13:
	    		if (counters[cfid]< 6) allshort.accumulate("question", question.getQuestionId());
	    		if (counters[cfid]< 11) alllong.accumulate("question", question.getQuestionId());
	    		if (counters[cfid]< 11) cctest.accumulate("question", question.getQuestionId());
	    	break;
	    	case 12:
	    		if (counters[cfid]< 6) allshort.accumulate("question", question.getQuestionId());
	    		if (counters[cfid]< 11) alllong.accumulate("question", question.getQuestionId());
	    		if (counters[cfid]< 11) cctest.accumulate("question", question.getQuestionId());
	    		if (counters[cfid]< 11) basic.accumulate("question", question.getQuestionId());
	    	break;
	    	case 1:
	    	case 5:
	    	case 6:
	    	case 7:
	    	case 8:
	    	default:
	    		break;
	    	
	    	};
	    	
		}
	    
	    JSONObject json = new JSONObject();

	    generateSurvey("basic", json, basic, 1);
	    generateSurvey("cctest", json, cctest, 4);
	    generateSurvey("bigshort", json, bigshort, 5);
	    generateSurvey("biglong", json, biglong, 6);
	    generateSurvey("allshort", json, allshort, 7);
	    generateSurvey("alllong", json, alllong, 8);

	    fRes.setSuccess(true);
	    fRes.setHTML(json.toString());
		  
		return;
	  }

	private static void generateSurvey(String name, JSONObject json, JSONObject list, long id) {
		// TODO Auto-generated method stub
		json.put(name, list);
		JSONArray questionIds = list.getJSONArray("question");
		for (int i=0;i<questionIds.length();i++) {
			SurveyQuestion sq = new SurveyQuestion();
			sq.setQuestion(Question.getQuestionById(BigInteger.valueOf(((long)questionIds.getInt(i)))));
			sq.setSurvey(Survey.getSurveyById(BigInteger.valueOf(id)));
			//sq.persistMe();
		}
		return;
	}

	  
	  
	  
}
