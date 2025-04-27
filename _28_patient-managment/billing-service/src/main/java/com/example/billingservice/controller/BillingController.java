package com.example.billingservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/BillingService")
public class BillingController {

    @PostMapping("/CreateBillingAccount")
    public String createBillingAccount() {
        // Lógica para crear una cuenta de facturación
        return "Billing account created successfully!";
    }
}
