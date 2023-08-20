package com.example.faculty.controller;

import com.example.faculty.dto.Response;
import com.example.faculty.dto.authentication.LoginRequest;
import com.example.faculty.dto.authentication.RegisterRequest;
import com.example.faculty.dto.user.UserRequest;
import com.example.faculty.dto.user.UserResponse;
import com.example.faculty.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.getUser(userRequest.getEmail());
        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("")
    public ResponseEntity<?> updateUserInfo(HttpServletRequest http) throws IOException{
        String token = http.getHeader("authorization");
        String jsonBody = getRequestBody(http);

        ObjectMapper objectMapper = new ObjectMapper();
        RegisterRequest registerRequest = objectMapper.readValue(jsonBody, RegisterRequest.class);

        Response response = userService.updateUserInfo(registerRequest, token);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(HttpServletRequest http) throws IOException {
        String token = http.getHeader("authorization");
        String jsonBody = getRequestBody(http);

        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequest loginRequest = objectMapper.readValue(jsonBody, LoginRequest.class);

        return ResponseEntity.ok(userService.changePassword(loginRequest.getPassword(), token));
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }
        return requestBody.toString();
    }
}
