package com.tony.banking_app.entity;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tony.banking_app.entity.enums.UserRole;

public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();

        switch (user.getRole()) {
            case UserRole.CUSTOMER:
                this.authorities = Arrays.asList(new SimpleGrantedAuthority(UserRole.CUSTOMER.toString()));
                break;
            case UserRole.ADMIN:
                this.authorities = Arrays.asList(new SimpleGrantedAuthority(UserRole.ADMIN.toString()));
                break;
            default:
                System.out.println("Role not recognized by UserDetails");
                this.authorities = Arrays.asList();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
