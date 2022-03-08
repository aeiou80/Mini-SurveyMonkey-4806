
package com.example.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Survey;

@Repository
public interface SurveysRepository extends JpaRepository<Survey, Integer> {
	@Query("SELECT s FROM #{#entityName} s")
	List<IdNameProjection> getAllIdsAndNames();
}
