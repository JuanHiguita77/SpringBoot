package com.riwi.operations.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/great")
public class helloController 
{
    @GetMapping
    public String great()
    {
        return "hello world";
    }
}
