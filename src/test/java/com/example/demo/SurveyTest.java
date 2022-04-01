package com.example.demo;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Answer;
import com.example.demo.model.MultipleChoiceQuestion;
import com.example.demo.model.QuestionType;
import com.example.demo.model.RangeQuestion;
import com.example.demo.model.Survey;
import com.example.demo.model.TextQuestion;
import com.example.demo.repository.SurveyProjection;
import com.example.demo.controller.AnswersController;
import com.example.demo.controller.QuestionsController;
import com.example.demo.controller.SurveyController;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
class SurveyTest {
	@Autowired
	AnswersController answersController;

	@Autowired
	SurveyController surveyController;
	
	@Autowired
	QuestionsController questionsController;
	
	

//	@Test
//	void controllerTest() {
//		assert(controller.get().isEmpty());
//		Survey survey = controller.create("First Survey");
//		assertEquals(survey.getName(), "First Survey");
//		assertFalse(survey.isClosed());
//		assertEquals(controller.get().size(), 1);
//
//
//		SurveyProjection p = controller.get().get(0);
//		assertEquals(p.getName(), survey.getName());
//		assertEquals(p.getId(), survey.getId());
//
//		controller.closeSurvey(survey.getId());
//		assertTrue(controller.get(survey.getId()).isClosed());
//
//		controller.delete(survey.getId());
//		assert(controller.get().isEmpty());
//	}
	


	@Test
	void surveyTest() {
		Survey survey = surveyController.create("First Survey");
		Answer answer;
		
		TextQuestion text = new TextQuestion();
		RangeQuestion range = new RangeQuestion();
		MultipleChoiceQuestion mc = new MultipleChoiceQuestion();
		
		assertEquals(text.getType(), QuestionType.TEXT);
		assertEquals(range.getType(), QuestionType.RANGE);
		assertEquals(mc.getType(), QuestionType.MC);
		
		text.setSurvey(survey);
		range.setSurvey(survey);
		mc.setSurvey(survey);
		
		questionsController.create(text);
		questionsController.create(range);
		questionsController.create(mc);
		
		surveyController.publishSurvey(survey.getId());
		survey = surveyController.get(survey.getId());
		
		assertEquals(survey.getQuestions().size(), 3);
		answer = new Answer();
		answer.setSurvey(survey);
		List<String> ans = new ArrayList<String>();
		ans.add("text");
		ans.add("3");
		ans.add("MC");
		answer.setAnswer(ans);
		answer = answersController.create(answer).getBody();
		
		surveyController.delete(survey.getId());
		assertEquals(surveyController.get().size(), 0);
		assertEquals(answersController.get(survey.getId()).size(), 0);
		assertEquals(questionsController.get().size(), 0);
		
	}

	
	
	/* 
	 * Unit test ISSUE 1. Order not enforced even with the @Order annotation
	 * 2. BeforeAll must be static, but autowired does not work with static (value becomes null)
		
	@Test
	@Order(1)
	void testAddQuestions() {
		assert(controller.get().isEmpty());
		text = (TextQuestion) controller.create(text);
		range = (RangeQuestion) controller.create(range);
		mc = (MultipleChoiceQuestion) controller.create(mc);
		assertEquals(controller.get().size(), 3);
		
	}
	
	@Test
	@Order(2)
	void testGetQuestions() {
		assertEquals(controller.get(text.getId()), text);
		assertEquals(controller.get(range.getId()), range);
		assertEquals(controller.get(mc.getId()), mc);
	}

	@Test
	@Order(3)
	void testDeleteQuestions() {
		controller.delete(text.getId());
		controller.delete(range.getId());
		controller.delete(mc.getId());
		assert(controller.get().isEmpty());
	}
	*/


}