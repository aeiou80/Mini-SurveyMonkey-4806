package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Question;

/**
 * Use QuestionsRepository<Question> to manage Question models from all subclasses, but to add a Question,
 * the Question object needs to be casted into the subclasses e.g. repository.save((TextQuestion) q)
 * 
 * Or you can use QuestionRepository<T0 (e.g. QuestionRepository<TextQuestion> to manage particular subclasses
 * @author frank
 *
 * @param <T>
 */
@Repository
public interface QuestionsRepository<T extends Question> extends JpaRepository<T, Integer> {

}