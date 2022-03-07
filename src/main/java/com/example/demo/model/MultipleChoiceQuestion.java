package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MC")
public class MultipleChoiceQuestion extends Question {

    @ElementCollection
    private List<String> choices;

    public MultipleChoiceQuestion(){
    	this.type = QuestionType.MC;
        choices = new ArrayList<>();
    }

	public List<String> getChoices() {
		return choices;
	}

	public void setChoices(List<String> choices) {
		this.choices = choices;
	}
}