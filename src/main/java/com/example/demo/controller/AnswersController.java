package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Answer;
import com.example.demo.model.Survey;
import com.example.demo.repository.AnswersRepository;
import com.example.demo.repository.SurveysRepository;

@RestController
public class AnswersController {
	
	@Autowired
	private AnswersRepository answersRepository;
	
	@Autowired
	private SurveysRepository surveysRepository;
	
	@GetMapping("/survey/{id}/answer")
	public List<Answer> get(@PathVariable int id) {
		return answersRepository.findBySurveyId(id);
	}

	/**
	 * Submit a set of answers to a survey. Only allowed if a survey is published and not closed.
	 * @param answer
	 * @return
	 */
	@PostMapping("/answer")
	public ResponseEntity<Answer> create(@RequestBody Answer answer) {
		Survey survey = surveysRepository.findById(answer.getSurvey().getId()).get();
		if (!survey.isPublished() || survey.isClosed()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(answersRepository.save(answer), HttpStatus.OK);
	}
	
	@DeleteMapping("/answer/{id}")
	public void delete(@PathVariable int id) {
		answersRepository.deleteById(id);
	}
	
}