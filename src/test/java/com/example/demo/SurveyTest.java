package com.example.demo;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.model.Survey;
import com.example.demo.repository.IdNameProjection;
import com.example.demo.controller.SurveyController;

@SpringBootTest
class SurveyTest {
	@Autowired
	SurveyController controller;
	
	@Test
	void controllerTest() {
		assert(controller.get().isEmpty());
		Survey survey = controller.create("First Survey");
		assertEquals(survey.getName(), "First Survey");
		assertEquals(controller.get().size(), 1);
		
		IdNameProjection p = controller.get().get(0);
		assertEquals(p.getName(), survey.getName());
		assertEquals(p.getId(), survey.getId());
		
		controller.delete(survey.getId());
		assert(controller.get().isEmpty());
	}

}