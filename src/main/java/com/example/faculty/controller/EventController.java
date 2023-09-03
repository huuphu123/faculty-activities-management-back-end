package com.example.faculty.controller;

import com.example.faculty.dto.authentication.RegisterRequest;
import com.example.faculty.dto.event.EventInsertRequest;
import com.example.faculty.dto.event.EventInsertResponse;
import com.example.faculty.service.EventSerivce;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private EventSerivce eventSerivce;

    @PostMapping("/user/event")
    public ResponseEntity<?> insertEvent(HttpServletRequest http) throws IOException {
        String token = http.getHeader("authorization");
        String jsonBody = getRequestBody(http);

        ObjectMapper objectMapper = new ObjectMapper();
        EventInsertRequest request = objectMapper.readValue(jsonBody, EventInsertRequest.class);

        EventInsertResponse response = eventSerivce.insertEvent(request, token);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/event")
    public ResponseEntity<?> getUserEvent(HttpServletRequest http) throws IOException {
        String token = http.getHeader("authorization");
        return ResponseEntity.ok(eventSerivce.getUserEvent(token));

    }

    @GetMapping("/event/{id}")
    public ResponseEntity<?> getEventDetails(@PathVariable(name="id") String id){
        return ResponseEntity.ok(eventSerivce.getEventDetails(id));
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
