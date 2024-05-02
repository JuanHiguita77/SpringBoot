package com.riwi.BeautyCenter.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.BeautyCenter.api.dto.request.AppointmentRequest;
import com.riwi.BeautyCenter.api.dto.response.AppointmentResponse;
import com.riwi.BeautyCenter.infrastructure.abstract_services.IAppointmentService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@AllArgsConstructor
@RequestMapping("/appointment")
public class AppointmenController 
{
    @Autowired
    private final IAppointmentService iAppointmentService;

    @GetMapping
    public ResponseEntity<Page<AppointmentResponse>> listAll(
        @RequestParam(defaultValue = "1") int page, 
        @RequestParam(defaultValue = "2") int size)
    {
        return ResponseEntity.ok(this.iAppointmentService.list(page - 1, size));
    }

    @PostMapping("/add")
    public ResponseEntity<AppointmentResponse> add(@RequestBody AppointmentRequest request) 
    {        
        return ResponseEntity.ok(this.iAppointmentService.insert(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AppointmentResponse> update(@PathVariable Long id, @RequestBody AppointmentRequest request) 
    {
        return ResponseEntity.ok(this.iAppointmentService.update(id, request));
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        this.iAppointmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
