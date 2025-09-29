package com.tony.banking_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    
    @GetMapping("/hello")
    public ResponseEntity<String> testAdminEndpoint() {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Hello admin!");
    }

}
