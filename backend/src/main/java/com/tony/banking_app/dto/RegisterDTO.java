package com.tony.banking_app.dto;

import com.tony.banking_app.entity.enums.UserRole;

public class RegisterDTO {
    private String username;
    private String password;
    private UserRole role;
    // private String role;

    public RegisterDTO(String username, String password, String role) {
        this.username = username;
        this.password = password;
        if (role.contains("CUSTOMER")) {
            this.role = UserRole.CUSTOMER;
        } else { // ADMIN
            this.role = UserRole.ADMIN;
        }
    }

    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // public String getRole() {
    //     return this.role;
    // }
    // public void setRole(String role) {
    //     this.role = role;
    // }

    public UserRole getRole() {
        return this.role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }
}
