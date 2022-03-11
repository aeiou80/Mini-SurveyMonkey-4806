package com.example.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Answer;

@Repository
public interface AnswersRepository extends JpaRepository<Answer, Integer> {
	public List<Answer> findBySurveyId(int id);
}