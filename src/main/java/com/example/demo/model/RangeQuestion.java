package com.example.demo.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RANGE")
public class RangeQuestion extends Question {
	private int min;
	private int max;
	
	public RangeQuestion() {
		this.type = QuestionType.RANGE;
	}
	
	public void setMin(int m) {
		min = m;
	}
	
	public int getMin() {
		return min;
	}
	
	public void setMax(int m) {
		max = m;
	}
	
	public int getMax() {
		return max;
	}
}