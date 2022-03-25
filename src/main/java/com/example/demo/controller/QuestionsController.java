package com.example.demo.controller;

import com.example.demo.model.Question;
import com.example.demo.model.Survey;
import com.example.demo.repository.QuestionsRepository;
import com.example.demo.repository.SurveysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class QuestionsController {
	
	@Autowired
	private QuestionsRepository<Question> questionsRepository;
	@Autowired
	private SurveysRepository surveysRepository;
	
	@GetMapping("/question")
	public List<Question> get() {
		return questionsRepository.findAll();
	}
	
	@GetMapping("/question/{id}")
	public Question get(@PathVariable int id) {
		return questionsRepository.findById(id).get();
	}
	
	/**
	 * Create a question and add it to a survey. You cannot add a question for closed or published surveys.
	 * Sample post request:
	 * {
     * 		"question": "Test?",
     *		"type": "MC",
     * 		"survey": {
     *	    	"id": 2
     * 		},
     *		"choices": ["1", "2"]
	 * }
	 * @param question
	 * @return
	 */
	@PostMapping("/question")
	public ResponseEntity<Question> create(@RequestBody Question question) {
		Survey survey = surveysRepository.getById(question.getSurvey().getId());
		if (survey.isClosed() || survey.isPublished()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(questionsRepository.save(question), HttpStatus.OK);
	}
	
	@DeleteMapping("/question/{id}")
	public void delete(@PathVariable int id) {
		questionsRepository.deleteById(id);
	}
}