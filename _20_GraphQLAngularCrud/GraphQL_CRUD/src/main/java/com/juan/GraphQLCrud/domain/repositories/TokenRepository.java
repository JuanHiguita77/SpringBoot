package com.juan.GraphQLCrud.domain.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juan.GraphQLCrud.domain.entities.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllValidIsFalseOrRevokedIsFalseByUserId(Long userId);
    Optional<Token> findByToken(String jwt);
}
