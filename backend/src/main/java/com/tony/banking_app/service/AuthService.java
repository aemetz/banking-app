package com.tony.banking_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tony.banking_app.dto.AuthRequest;
import com.tony.banking_app.dto.AuthResponse;
import com.tony.banking_app.dto.RegisterDTO;
import com.tony.banking_app.entity.User;
import com.tony.banking_app.exception.InvalidCredentialsException;
import com.tony.banking_app.exception.InvalidPasswordException;
import com.tony.banking_app.exception.InvalidUsernameException;
import com.tony.banking_app.repository.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Register a new user - save to repository after completing validation logic
     * @param dto
     * @return JWT token in a response object
     * @throws InvalidUsernameException
     * @throws InvalidPasswordException
     */
    public AuthResponse register(RegisterDTO dto) throws InvalidUsernameException, InvalidPasswordException {
        // Check username/password validity (exception thrown if not valid)
        String username = dto.getUsername();
        isUsernameValid(username);
        isPasswordValid(dto.getPassword());

        User user = new User(username, passwordEncoder.encode(dto.getPassword()), dto.getRole());
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user.getUsername());
        return new AuthResponse(jwtToken);
    }

    /**
     * Authenticate a user
     * @param request
     * @return JWT token in a response object
     */
    public AuthResponse authenticate(AuthRequest request) throws InvalidUsernameException {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            System.out.println(e.getClass());
            throw new InvalidCredentialsException("Incorrect username or password");
        }
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String jwtToken = jwtService.generateToken(user.getUsername());
        return new AuthResponse(jwtToken);
    }

    /**
     * Utility method that checks if username is available and its length is acceptable
     * @param username
     * @throws InvalidUsernameException
     */
    private void isUsernameValid(String username) throws InvalidUsernameException {
        if (username.length() < 3 || username.length() > 20) {
            throw new InvalidUsernameException("Username must have between 3 and 20 characters");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new InvalidUsernameException("Username " + username + " is taken");
        }
    }

    /**
     * Utility method that checks if password is valid
     * @param password
     * @throws InvalidPasswordException
     */
    private void isPasswordValid(String password) throws InvalidPasswordException {
        if (password.length() < 8 || password.length() > 32) {
            throw new InvalidPasswordException("Password must have between 8 and 32 characters");
        }
        if (!password.matches(".*[A-Z].*") || // at least one uppercase letter
            !password.matches(".*[0-9].*") || // at least one number
            !password.matches(".*[!@#$%^&*(),.?:{}|<>].*")) { // at least one allowed special character: !@#$%^&*(),.?:{}|<>
                throw new InvalidPasswordException("Password must contain at least one uppercase letter, one number, and one special character");
        }
    }

}
