package riwi.lastfilter.spring.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import riwi.lastfilter.spring.domain.entities.Buy;

@Repository
public interface BuyRepository  extends JpaRepository<Buy, Long> {
    
}
