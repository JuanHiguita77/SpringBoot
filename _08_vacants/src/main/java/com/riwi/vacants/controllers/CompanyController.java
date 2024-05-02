package com.riwi.vacants.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.vacants.services.interfaces.ICompanyService;
import com.riwi.vacants.utils.dto.request.CompanyRequest;
import com.riwi.vacants.utils.dto.response.CompanyResponse;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController 
{
    @Autowired
    private final ICompanyService companyService;

    @GetMapping
    public ResponseEntity<Page<CompanyResponse>> listAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "2") int size) 
    {
        return ResponseEntity.ok(this.companyService.list(page - 1, size));
    }

    @PostMapping("/add")
    public ResponseEntity<CompanyResponse> add(@Validated @RequestBody CompanyRequest companyRequest)
    {
        return ResponseEntity.ok(this.companyService.insert(companyRequest));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<CompanyResponse> findById(@PathVariable String id)
    {
        return ResponseEntity.ok(this.companyService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id)
    {
        this.companyService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> update(@PathVariable String id, @Validated @RequestBody CompanyRequest company)
    {
        return ResponseEntity.ok(this.companyService.update(id, company));
    }
}
