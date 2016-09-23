package com.employmeo.util;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.employmeo.objects.Answer;
import com.employmeo.objects.Question;
import com.employmeo.objects.Survey;
import com.employmeo.objects.SurveySection;

/**
 * Static utility class for survey migrations - serialization/deserialization
 * and persistence)
 * 
 * @author NShah
 *
 */
public class SurveyUtil {

	private static Logger logger = Logger.getLogger("com.employmeo.util.SurveyUtil");

	public static void persistSurvey(Survey survey) throws IllegalStateException {
		logger.info("Proceeding to persist survey with id: " + survey.getSurveyId());
		
		validateSurvey(survey);

		EntityManager em = DBUtil.getEntityManager();
		EntityTransaction txn = em.getTransaction();
		if (!txn.isActive()) 
			txn.begin();
		
		try {		
			persistSurvey(survey, em);
			persistSurveySections(survey, em);
			persistSurveyQuestions(survey, em);

			txn.commit();
			logger.info("Survey with id " + survey.getSurveyId() + " persisted");
		} catch (Exception e) {
			logger.info("Failed to persist survey, rolling back." + e);
			txn.rollback();
			throw new IllegalStateException("Failed to persist survey", e);
		}
	}

	private static void validateSurvey(Survey survey) {
		Survey existingSurvey = Survey.getSurveyById(survey.getSurveyId());
		if (null != existingSurvey) {
			throw new IllegalStateException("Survey with survey id " + survey.getSurveyId() + " already exists");
		}
		
		logger.info("Validated that survey with id " + survey.getSurveyId() + " does not exist, proceeding further");
	}

	private static void persistSurvey(Survey survey, EntityManager em) {
		em.persist(survey);
		logger.info("Survey entity persisted");
	}

	private static void persistSurveyQuestions(Survey survey, EntityManager em) {
		survey.getSurveyQuestions().forEach(surveyQuestion -> {
			Question question = surveyQuestion.getQuestion();
			persistQuestion(question, em);

			surveyQuestion.setSurvey(survey);
			em.persist(surveyQuestion);
			logger.info("SurveyQuestion entity persisted with id: " + surveyQuestion.getSqId());
		});
	}

	private static void persistQuestion(Question question, EntityManager em) {
		Question existingQuestion = Question.getQuestionById(question.getQuestionId());
		if (null != existingQuestion) {
			logger.info("Question already exists, so skipping. Id = " + question.getQuestionId());
		} else {
			em.persist(question);
			logger.info("Question entity persisted with id: " + question.getQuestionId());

			question.getAnswers().forEach(answer -> {
				persistAnswer(answer, em);
			});
		}
	}

	private static void persistAnswer(Answer answer, EntityManager em) {
		Answer existingAnswer = Answer.findById(answer.getAnswerId());
		if (null != existingAnswer) {
			logger.info("Answer already exists, so skipping. Id = " + answer.getAnswerId());
		} else {
			em.persist(answer);
			logger.info("Answer entity persisted with id: " + answer.getAnswerId());
		}
	}

	private static void persistSurveySections(Survey survey, EntityManager em) {
		survey.getSurveySections().forEach(surveySection -> {
			SurveySection existingSurveySection = SurveySection.findById(surveySection.getId().getSsSurveyId(),
					surveySection.getId().getSsSurveySection());
			if (null != existingSurveySection) {
				logger.info("Survey Section already exists, so skipping. surveyId = "
						+ surveySection.getId().getSsSurveyId() + " with section : "
						+ surveySection.getId().getSsSurveySection());
			} else {
				em.persist(surveySection);
				logger.info("Survey Section persisted with id: " + surveySection.getId());
			}
		});
	}
}
