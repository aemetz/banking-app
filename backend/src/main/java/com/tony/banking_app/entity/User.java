package com.tony.banking_app.entity;

import java.util.List;

import com.tony.banking_app.entity.enums.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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

    @OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<Account> accounts;


    public User() {}

    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }


    /*
     * GETTERS / SETTERS
     */
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public UserRole getRole() {
        return this.role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Account> getAccounts() {
        return this.accounts;
    }

}
