package com.riwi.librosYa.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.librosYa.domain.entities.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> 
{
    Page<Loan> findByUserId(Long id, Pageable pageable);
}
