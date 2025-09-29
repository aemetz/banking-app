package com.tony.banking_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tony.banking_app.dto.AuthRequest;
import com.tony.banking_app.dto.RegisterDTO;
import com.tony.banking_app.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO dto) {
        authService.register(dto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest authRequest) {
        String token = authService.authenticate(authRequest).getToken();
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(token);
    }

}
