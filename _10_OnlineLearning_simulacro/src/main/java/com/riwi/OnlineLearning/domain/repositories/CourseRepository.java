package com.riwi.OnlineLearning.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.OnlineLearning.domain.entities.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> 
{
    List<Course> findByInstructorId(Long id);
}