package com.riwi.BeautyCenter.api.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.BeautyCenter.api.dto.request.ServiceRequest;
import com.riwi.BeautyCenter.api.dto.response.ServiceResponse;
import com.riwi.BeautyCenter.infrastructure.abstract_services.IServiceService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/service")
@AllArgsConstructor
public class ServiceController 
{
    //Inyeccion de dependencias
    @Autowired
    private final IServiceService iServiceService;
    
    //Sin ruta definida por lo tanto /
    @GetMapping
    public ResponseEntity<Page<ServiceResponse>> listAll(
        @RequestParam(defaultValue = "1") int page, 
        @RequestParam(defaultValue = "2") int size) 
    {
        return ResponseEntity.ok(this.iServiceService.list(page - 1, size));
    }

    @PostMapping("/add")
    public ResponseEntity<ServiceResponse> save(@Validated @RequestBody ServiceRequest serviceRequest) {
        
        return ResponseEntity.ok(this.iServiceService.insert(serviceRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ServiceResponse> update(@PathVariable Long id, @Validated @RequestBody ServiceRequest serviceRequest)
    {
        return ResponseEntity.ok(this.iServiceService.update(id, serviceRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id)
    {
        ServiceResponse service = this.iServiceService.findById(id);

        if (service != null) 
        {
            this.iServiceService.delete(id);
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.ok(false);
    }
    
    
}
