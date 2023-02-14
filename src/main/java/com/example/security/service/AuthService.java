package com.example.security.service;

import com.example.security.model.AuthenticationResponse;
import com.example.security.model.LoginRequest;
import com.example.security.model.RegisterRequest;
import com.example.security.model.User;
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
        var user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwt)
                .build();
    }

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        int customer_id = customerRepo.save(registerRequest).getId();
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
