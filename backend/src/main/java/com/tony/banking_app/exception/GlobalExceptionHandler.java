package com.tony.banking_app.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity<Map<String, String>> handleJwtException(JwtValidationException ex) {
        Map<String, String> body = Map.of("error", ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(body);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRoleException(RoleNotFoundException ex) {
        Map<String, String> body = Map.of("error", ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(body);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRoleException(UsernameNotFoundException ex) {
        Map<String, String> body = Map.of("error", ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(body);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAccountNotFoundException(AccountNotFoundException ex) {
        Map<String, String> body = Map.of("error", ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(body);
    }

    @ExceptionHandler(InvalidAccountException.class)
    public ResponseEntity<Map<String, String>> handleInvalidAccountException(InvalidAccountException ex) {
        Map<String, String> body = Map.of("error", ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(body);
    }

    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<Map<String, String>> handleInvalidAccountException(InvalidTransactionException ex) {
        Map<String, String> body = Map.of("error", ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(body);
    }
    
}
