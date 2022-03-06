package com.example.demo.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TEXT")
public class TextQuestion extends Question {
	public TextQuestion() {
		this.type = QuestionType.TEXT;
	}
}
