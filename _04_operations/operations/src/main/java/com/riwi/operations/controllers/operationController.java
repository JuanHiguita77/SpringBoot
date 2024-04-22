package com.riwi.operations.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.operations.entiities.Operations;

@RestController
@RequestMapping("/api/v1/operations")
public class operationController 
{
    @PostMapping(path = "/sum") //More specific path
    public String sum(@RequestBody Operations operations)
    {
        String message = String.valueOf(operations.getNum1() + operations.getNum2());
        return "Your Result is: " + message;
    }
    
    @PostMapping(path = "/subs") //More specific path
    public String substract(@RequestBody Operations operations)
    {
        String message = String.valueOf(operations.getNum1() - operations.getNum2());
        return "Your Result is: " + message;
    }

    @PostMapping(path = "/multi") //More specific path
    public String multiply(@RequestBody Operations operations)
    {
        String message = String.valueOf(operations.getNum1() * operations.getNum2());
        return "Your Result is: " + message;
    }

    @PostMapping(path = "/div") //More specific path
    public String divide(@RequestBody Operations operations)
    {
        String message = String.valueOf(operations.getNum1() / operations.getNum2());
        return "Your Result is: " + message;
    }
}
