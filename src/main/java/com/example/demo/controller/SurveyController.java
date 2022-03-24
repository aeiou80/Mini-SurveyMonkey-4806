package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Survey;
import com.example.demo.repository.SurveyProjection;
import com.example.demo.repository.SurveysRepository;

@RestController
public class SurveyController {
	@Autowired
	private SurveysRepository repository;
	
	/**
	 * @return a list of surveys with only {id, name, closed} properties
	 */
	@GetMapping("/survey")
	public List<SurveyProjection> get() {
		return repository.getSurveyProjection();
	}
	
	@GetMapping("/survey/{id}")
	public Survey get(@PathVariable int id) {
		return repository.findById(id).get();
	}
	
	@PostMapping("/survey")
	public Survey create(@RequestBody String name) {
		Survey survey = new Survey();
		survey.setName(name);
		return repository.save(survey); 
	}
	
	@DeleteMapping("/survey/{id}")
	public void delete(@PathVariable int id) {
		repository.deleteById(id);
	}
	
	@PostMapping("/survey/{id}/close")
	public Survey closeSurvey(@PathVariable int id) {
		Survey survey = repository.findById(id).get();
		survey.setClosed(true);
		return repository.save(survey);
	}
	
}
