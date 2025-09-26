package com.tony.banking_app.entity;

import java.util.List;

import com.tony.banking_app.entity.enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role; // can be CUSTOMER or ADMIN

    @OneToMany(mappedBy="user")
    private List<Account> accounts;


    /*
     * GETTERS / SETTERS
     */
    public Long getId() {
        return this.id;
    }
    public void setId(Long newId) {
        this.id = newId;
    }

    public String getUsername() {
        return this.username;
    }
    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public UserRole getRole() {
        return this.role;
    }
    public void setRole(UserRole newRole) {
        this.role = newRole;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

}
