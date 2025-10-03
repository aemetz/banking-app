package com.tony.banking_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tony.banking_app.dto.AuthRequest;
import com.tony.banking_app.dto.AuthResponse;
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

    /**
     * Create a new username/password, persist to the database, and return jwt
     * @param dto
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterDTO dto) {
        AuthResponse response = authService.register(dto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response);
    }

    /**
     * Log someone in using their username/password and return jwt
     * @param authRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
        AuthResponse response = authService.authenticate(authRequest);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response);
    }

}
