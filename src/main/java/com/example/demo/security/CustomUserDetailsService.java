package com.example.demo.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.PersonnelAccount;
import com.example.demo.repository.PersonnelAccountRepository;

// this class tells Spring Security how to load a user by username
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonnelAccountRepository repository;

    public CustomUserDetailsService(PersonnelAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        PersonnelAccount account = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .roles(account.getRole())
                .build();
    }
}
