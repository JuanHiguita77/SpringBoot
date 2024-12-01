package com.riwi.librosYa.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.librosYa.api.dto.DTOLoan;
import com.riwi.librosYa.infraestructure.abstract_Services.ILoanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/loans")
@Tag(name = "Loans")
public class LoanController {

    @Autowired
    private final ILoanService loanService;

    @Operation(summary = "Create a new loan", description = "Send information to create a new loan")
    @PostMapping
    public ResponseEntity<DTOLoan> save(@Validated @RequestBody DTOLoan loan) {
        return ResponseEntity.ok(this.loanService.create(loan));
    }

    @Operation(summary = "Delete a loan by loan_id", description = "Send the loan loan_id to delete it")
    @DeleteMapping(path = "/{loan_id}")
    public ResponseEntity<Void> delete(@PathVariable Long loan_id){
        this.loanService.delete(loan_id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a loan", description = "Send information to update a loan")
    @PutMapping(path = "/{loan_id}")
    public ResponseEntity<DTOLoan> update(@PathVariable Long loan_id, @Validated @RequestBody DTOLoan loan) {
        return ResponseEntity.ok(this.loanService.update(loan, loan_id));
    }

    @Operation(summary = "Get all loans of a user", description = "Get all loans of a user by user loan_id with pagination")
    @GetMapping("/users/{userId}/loans")
    public Page<DTOLoan> getAllLoansByUser(
            @PathVariable Long userId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        return this.loanService.getLoansByUserId(userId, pageable);
    }

    @Operation(summary = "Get a loan by loan_id", description = "Send the loan loan_id to get loan details")
    @GetMapping("/{loan_id}")
    public ResponseEntity<DTOLoan> findById(@PathVariable Long loan_id) {
        return ResponseEntity.ok(this.loanService.get(loan_id));
    }
}
