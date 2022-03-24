package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Answer {
	private @Id @GeneratedValue int id;	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties({"name", "questions", "closed"})
	private Survey survey;
	
    public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	@ElementCollection
	private List<String> answer;

	public Answer() {
		answer = new ArrayList<String>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<String> getAnswer() {
		return answer;
	}

	public void setAnswer(List<String> answer) {
		this.answer = answer;
	}
	
	public boolean equals(Object o) {
		return ((Answer) o).id == this.id;
	}
}
