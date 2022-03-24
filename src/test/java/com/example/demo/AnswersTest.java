package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.controller.AnswersController;
import com.example.demo.controller.QuestionsController;
import com.example.demo.controller.SurveyController;
import com.example.demo.model.Answer;
import com.example.demo.model.Survey;

@SpringBootTest
public class AnswersTest {
	@Autowired
	AnswersController controller;

	@Autowired
	SurveyController surveyController;

	Survey survey;
	Answer answer;

	@Test
	void controllerTest() {
		Survey survey = surveyController.create("First Survey");
		answer = new Answer();
		answer.setSurvey(survey);
		controller.create(answer);

		assertEquals(controller.get(survey.getId()).size(), 1);

		controller.delete(answer.getId());
		assertEquals(controller.get(survey.getId()).size(), 0);
		surveyController.delete(survey.getId());
	}

}