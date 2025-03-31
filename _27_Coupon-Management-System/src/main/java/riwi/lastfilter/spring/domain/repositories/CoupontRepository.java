package riwi.lastfilter.spring.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import riwi.lastfilter.spring.domain.entities.Coupon;

@Repository
public interface CoupontRepository  extends JpaRepository<Coupon, Long> {
    
}