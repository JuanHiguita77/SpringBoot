package com.riwi.BeautyCenter.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.BeautyCenter.api.dto.request.ClientRequest;
import com.riwi.BeautyCenter.api.dto.response.ClientResponse;

import com.riwi.BeautyCenter.infrastructure.abstract_services.IClientService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@AllArgsConstructor
@RequestMapping("/client")
public class ClientController 
{
    @Autowired
    private final IClientService iclientService;

    @GetMapping
    public ResponseEntity<Page<ClientResponse>> listAll(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "2") int size)
    {
        return ResponseEntity.ok(this.iclientService.list(page - 1, size));
    }

    @PostMapping("/add")
    public ResponseEntity<ClientResponse> add(@Validated @RequestBody ClientRequest clientRequest)
    {
        return ResponseEntity.ok(this.iclientService.insert(clientRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable Long id, @Validated @RequestBody ClientRequest clientRequest) 
    {
        return ResponseEntity.ok(this.iclientService.update(id, clientRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        this.iclientService.delete(id);
        return ResponseEntity.noContent().build();

    }
}
