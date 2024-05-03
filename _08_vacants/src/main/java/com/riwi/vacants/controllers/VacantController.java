package com.riwi.vacants.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.vacants.services.interfaces.IVacantService;
import com.riwi.vacants.utils.dto.request.VacantRequest;
import com.riwi.vacants.utils.dto.response.VacantResponse;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


//Rest controller es para responder con respuesta http
@RestController
@RequestMapping("/vacant")
@AllArgsConstructor
public class VacantController 
{
    @Autowired
    private final IVacantService iVacantService;

    @GetMapping
    public ResponseEntity<Page<VacantResponse>> listAll(
        @RequestParam(defaultValue = "1") int page, 
        @RequestParam(defaultValue = "4") int size ) 
    {
        return ResponseEntity.ok(this.iVacantService.list(page - 1, size));
    }
    
    @PostMapping("/add")
    public ResponseEntity<VacantResponse> add(@Validated @RequestBody VacantRequest vacant) {
        return ResponseEntity.ok(this.iVacantService.insert(vacant));
    }
    
}
