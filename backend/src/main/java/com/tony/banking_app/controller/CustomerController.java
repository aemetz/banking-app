package com.tony.banking_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    public CustomerController() {}

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome() {
        System.out.println("INSIDE /welcome ENDPOINT!");
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Welcome!");
    }

    

}
