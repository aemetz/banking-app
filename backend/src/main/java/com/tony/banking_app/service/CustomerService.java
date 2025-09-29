package com.tony.banking_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tony.banking_app.repository.UserRepository;

@Service
public class CustomerService {

    private final UserRepository userRepository;

    @Autowired
    public CustomerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
}
