package riwi.lastfilter.spring.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import riwi.lastfilter.spring.domain.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}