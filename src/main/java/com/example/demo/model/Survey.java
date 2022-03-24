package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Survey {
	private @Id @GeneratedValue int id;	
	
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "survey")
    private List<Question> questions;
  	private String name;
  	private boolean closed;
  	
  	public Survey() {
  		questions = new ArrayList<Question>();
  		closed = false;
  	}
  	
    public int getId() {
  		return id;
  	}
  	
  	public void setId(int id) {
  		this.id = id;
  	}

  	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	public boolean equals(Object o) {
		return ((Survey) o).id == this.id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

}