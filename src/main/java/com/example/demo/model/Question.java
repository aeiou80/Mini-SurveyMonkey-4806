package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;

/**
 * Question Model, with subclasses RangeQuestion, TextQuestion, and MCQuestion, stored using one table.
 * The subclasses are discriminated using question_type in the database
 * The subclasses are discriminated using type when converting json to question
 * 
 * @author frank
 */
@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = JsonTypeInfo.As.EXISTING_PROPERTY,
	property = "type",
	visible = true
)
@JsonSubTypes({
	@JsonSubTypes.Type(value = TextQuestion.class, name = "TEXT"),
	@JsonSubTypes.Type(value = RangeQuestion.class, name = "RANGE"),
	@JsonSubTypes.Type(value = MultipleChoiceQuestion.class, name = "MC")	
})
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="question_type", discriminatorType = DiscriminatorType.STRING)
public class Question {
	private @Id @GeneratedValue int id;	
	private String question;
	protected QuestionType type;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, optional = false)
	@JsonIgnoreProperties({"name", "questions"})
	private Survey survey;
	
	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
  	
  	public int getId() {
  		return id;
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

	public QuestionType getType() {
		return type;
	}
	
	public boolean equals(Object o) {
		return ((Question) o).id == this.id;
	}

}