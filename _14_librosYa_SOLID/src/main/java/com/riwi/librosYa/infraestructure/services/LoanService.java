package com.riwi.librosYa.infraestructure.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.riwi.librosYa.api.dto.DTOLoan;
import com.riwi.librosYa.api.mappers.LoanMapper;
import com.riwi.librosYa.domain.entities.Loan;
import com.riwi.librosYa.domain.repository.LoanRepository;
import com.riwi.librosYa.infraestructure.abstract_Services.ILoanService;
import com.riwi.librosYa.util.exceptions.BadRequestException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoanService implements ILoanService {

    @Autowired
    private final LoanRepository loanRepository;

    @Autowired
    private final LoanMapper loanMapper;

    @Override
    public DTOLoan create(DTOLoan request) {
        Loan loanEntity = loanMapper.toEntity(request);
        return loanMapper.toDTOLoan(loanRepository.save(loanEntity));
    }

    @Override
    public DTOLoan get(Long id) {
        Loan loanEntity = find(id);
        return loanMapper.toDTOLoan(loanEntity);
    }

    @Override
    public DTOLoan update(DTOLoan request, Long id) {
        Loan existingEntity = find(id);
        Loan updatedEntity = loanMapper.toEntity(request);
        updatedEntity.setId(existingEntity.getId());
        return loanMapper.toDTOLoan(loanRepository.save(updatedEntity));
    }

    @Override
    public void delete(Long id) {
        Loan loanEntity = find(id);
        loanRepository.delete(loanEntity);
    }

    @Override
    public Page<DTOLoan> getLoansByUserId(Long userId, Pageable pageable) {
        Page<Loan> loans = loanRepository.findByUserId(userId, pageable);
        return loans.map(loanMapper::toDTOLoan);
    }

    private Loan find(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No loan found with the supplied ID"));
    }
}
