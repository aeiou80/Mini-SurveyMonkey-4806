package com.example.demo;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.example.demo.model.MultipleChoiceQuestion;
import com.example.demo.model.QuestionType;
import com.example.demo.model.RangeQuestion;
import com.example.demo.model.Survey;
import com.example.demo.model.TextQuestion;
import com.example.demo.controller.QuestionsController;
import com.example.demo.controller.SurveyController;

@SpringBootTest
class QuestionTest {
	TextQuestion text = new TextQuestion();
	RangeQuestion range = new RangeQuestion();
	MultipleChoiceQuestion mc = new MultipleChoiceQuestion();
	Survey survey;

	@Autowired
	QuestionsController controller;

	@Autowired
	SurveyController surveyController;

	@Test
	void testQuestionType() {
		assertEquals(text.getType(), QuestionType.TEXT);
		assertEquals(range.getType(), QuestionType.RANGE);
		assertEquals(mc.getType(), QuestionType.MC);
	}

	@Test
	void controllerTest() {
		survey = surveyController.create("First");
		text.setSurvey(survey);
		range.setSurvey(survey);
		mc.setSurvey(survey);

		assert(controller.get().isEmpty());
		text = (TextQuestion) controller.create(text);
		range = (RangeQuestion) controller.create(range);
		mc = (MultipleChoiceQuestion) controller.create(mc);
		assertEquals(controller.get().size(), 3);

		assertEquals(controller.get(text.getId()), text);
		assertEquals(controller.get(range.getId()), range);
		assertEquals(controller.get(mc.getId()), mc);

		controller.delete(text.getId());
		controller.delete(range.getId());
		controller.delete(mc.getId());
		assert(controller.get().isEmpty());

		surveyController.delete(survey.getId());
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