package com.riwi.OnlineLearning.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.OnlineLearning.domain.entities.User;

@Repository 
public interface UserRepository extends JpaRepository<User, Long> 
{

}
