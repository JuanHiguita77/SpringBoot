package com.riwi.OnlineLearning.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.OnlineLearning.domain.entities.Enrollment;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> 
{
    List<Enrollment> findByUserId(Long id);
    List<Enrollment> findByCourseId(Long id);
}
