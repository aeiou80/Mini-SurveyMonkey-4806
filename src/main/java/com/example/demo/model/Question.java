package com.example.demo.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Question {
	private @Id @GeneratedValue int id;	
	private String question;

	Question(){}
  	
  	public int getId() {
  		return this.id;
  	}
  	
  	public void setId(int id) {
  		this.id = id;
  	}

  	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

}