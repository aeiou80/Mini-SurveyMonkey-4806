package com.example.demo.repository;

import com.example.demo.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveysRepository extends JpaRepository<Survey, Integer> {
	@Query("SELECT s FROM #{#entityName} s")
	List<SurveyProjection> getSurveyProjection();
}
