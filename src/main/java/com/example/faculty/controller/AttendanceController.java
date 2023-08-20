package com.example.faculty.controller;

import com.example.faculty.dto.attendance.AttendanceRequest;
import com.example.faculty.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService eventAttendanceService;

    @PostMapping("/check_in")
    public ResponseEntity<?> checkIn(@RequestBody AttendanceRequest request) {
        return ResponseEntity.ok(eventAttendanceService.checkIn(request.getSid(), request.getId()));
    }

    @PostMapping("/check_out")
    public ResponseEntity<?> checkOut(@RequestBody AttendanceRequest request) {
        return ResponseEntity.ok(eventAttendanceService.checkOut(request.getSid(), request.getId()));
    }
}
