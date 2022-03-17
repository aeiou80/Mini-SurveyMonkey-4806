package com.example.demo.controller;

import com.example.demo.model.Question;
import com.example.demo.repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class QuestionsController {
	
	@Autowired
	private QuestionsRepository<Question> questionsRepository;
	
	@GetMapping("/question")
	public List<Question> get() {
		return questionsRepository.findAll();
	}
	
	@GetMapping("/question/{id}")
	public Question get(@PathVariable int id) {
		return questionsRepository.findById(id).get();
	}
	
	/**
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
	public Question create(@RequestBody Question question) {
		return questionsRepository.save(question); 
	}
	
	@DeleteMapping("/question/{id}")
	public void delete(@PathVariable int id) {
		questionsRepository.deleteById(id);
	}
}