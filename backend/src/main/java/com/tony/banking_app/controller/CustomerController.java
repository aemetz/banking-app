package com.tony.banking_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tony.banking_app.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/welcome")
    public ResponseEntity<String> welcome() {
        System.out.println("INSIDE /welcome ENDPOINT!");
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Welcome!");
    }

    

}
