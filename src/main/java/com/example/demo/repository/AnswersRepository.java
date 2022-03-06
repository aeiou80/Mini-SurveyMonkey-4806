package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Answer;

@Repository
public interface AnswersRepository extends JpaRepository<Answer, Integer> {

}