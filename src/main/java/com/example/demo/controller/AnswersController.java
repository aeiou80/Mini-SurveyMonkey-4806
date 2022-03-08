package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Answer;
import com.example.demo.repository.AnswersRepository;

@RestController
public class AnswersController {
	
	@Autowired
	private AnswersRepository answersRepository;
	
	@GetMapping("/survey/{id}/answer")
	public List<Answer> get(@PathVariable int id) {
		return answersRepository.findBySurveyId(id);
	}
	
	//TODO: No checks on answer length, maybe longer than survey itself
	@PostMapping("/answer")
	public Answer create(@RequestBody Answer answer) {
		return answersRepository.save(answer); 
	}
	
	@DeleteMapping("/answer/{id}")
	public void delete(@PathVariable int id) {
		answersRepository.deleteById(id);
	}
	
}