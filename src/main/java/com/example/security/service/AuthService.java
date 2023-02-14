package com.example.security.service;

import com.example.security.model.*;
import com.example.security.repository.CustomerRepository;
import com.example.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
     private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final CustomerRepository customerRepo;
    public AuthenticationResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        System.out.println("fetching user");
        var user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwt)
                .build();
    }

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var customer = Customer.builder()
                .name(registerRequest.getName())
                .birthdate(registerRequest.getBirthdate())
                .phone(registerRequest.getPhone())
                .email(registerRequest.getEmail())
                .lastname(registerRequest.getLastname())
                .build();
        int customer_id = customerRepo.save(customer).getId();
        var user =  User.builder()
        .customer_id(customer_id)
        .role(registerRequest.getRole())
        .email(registerRequest.getEmail())
        .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
        userRepo.save(user);
        var jwt = jwtService.generateToken(user);
        return  AuthenticationResponse
                .builder()
                .token(jwt)
                .build();
    }
}
