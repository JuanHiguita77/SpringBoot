package com.riwi.BeautyCenter.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.BeautyCenter.api.dto.request.EmployeeRequest;
import com.riwi.BeautyCenter.api.dto.response.EmployeeResponse;
import com.riwi.BeautyCenter.infrastructure.abstract_services.IEmployeeService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController 
{
    @Autowired
    private final IEmployeeService iEmployeeService;

    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> listAll(@RequestParam(defaultValue = "1") int page, 
    @RequestParam(defaultValue = "2") int size)
    {
        return ResponseEntity.ok(this.iEmployeeService.list(page - 1, size));
    }

    @PostMapping("/add")
    public ResponseEntity<EmployeeResponse> save(@RequestBody EmployeeRequest employeeRequest)
    {
        return ResponseEntity.ok(this.iEmployeeService.insert(employeeRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeResponse> update(@PathVariable Long id, @RequestBody EmployeeRequest employeeRequest)
    {
        return ResponseEntity.ok(this.iEmployeeService.update(id, employeeRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id)
    {
        EmployeeResponse employee = this.iEmployeeService.findById(id);

        if(employee != null)
        {
            this.iEmployeeService.delete(id);
            return ResponseEntity.ok(true);
        }
        else
        {
            return ResponseEntity.ok(false);
        }
    }
}
