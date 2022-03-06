package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.MultipleChoiceQuestion;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionType;
import com.example.demo.model.RangeQuestion;
import com.example.demo.model.TextQuestion;
import com.example.demo.repository.QuestionsRepository;


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
	
	@PostMapping("/question")
	public Question create(@RequestBody Question question) {
		return questionsRepository.save(question); 
	}
	
	@DeleteMapping("/question/{id}")
	public void delete(@PathVariable int id) {
		questionsRepository.deleteById(id);
	}
}