package com.example.security.controller;

import com.example.security.model.AuthenticationResponse;
import com.example.security.model.LoginRequest;
import com.example.security.model.RegisterRequest;
import com.example.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest){
        System.out.println(loginRequest.getEmail());
       return ResponseEntity.ok(authService.login(loginRequest));
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.register(registerRequest));
    }
   /*
   *
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String,Object> body){
        System.out.println(body.get("email"));
       return ResponseEntity.ok("login");
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String,Object> body){
        return ResponseEntity.ok("login");
    }
   * */


}
