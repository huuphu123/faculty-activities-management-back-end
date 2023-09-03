package com.example.faculty.controller;

import com.example.faculty.dto.authentication.LoginRequest;
import com.example.faculty.dto.authentication.LoginRespone;
import com.example.faculty.dto.authentication.RegisterRequest;
import com.example.faculty.dto.Response;
import com.example.faculty.security.JwtService;
import com.example.faculty.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        Response response = service.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        LoginRespone response = service.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
