package com.riwi.OnlineLearning.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.OnlineLearning.domain.entities.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> 
{
    List<Assignment> findByLessonId(Long id);
    List<Assignment> getAssignmentsByLessonId(Long id);
}
