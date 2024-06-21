package com.riwi.librosYa.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.riwi.librosYa.domain.entities.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> 
{
    Page<Loan> findByUserId(Long id, Pageable pageable);
}
