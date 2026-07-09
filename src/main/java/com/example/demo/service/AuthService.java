package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RegisterDTO;
import com.example.demo.entity.PersonnelAccount;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.PersonnelAccountRepository;

@Service
public class AuthService {

    final PersonnelAccountRepository repository;
    final PasswordEncoder passwordEncoder;

    AuthService(PersonnelAccountRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public PersonnelAccount register(RegisterDTO dto) {

        if (repository.findByUsername(dto.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists: " + dto.getUsername());
        }

        PersonnelAccount account = new PersonnelAccount();
        account.setUsername(dto.getUsername());
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
        account.setFullName(dto.getFullName());

        // if no role is given, make the person a RESPONDER by default
        if (dto.getRole() == null || dto.getRole().isBlank()) {
            account.setRole("RESPONDER");
        } else {
            account.setRole(dto.getRole().toUpperCase());
        }

        account.setPhoneNumber(dto.getPhoneNumber());
        account.setRegion(dto.getRegion());

        return repository.save(account);
    }
}
