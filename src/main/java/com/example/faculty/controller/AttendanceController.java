package com.example.faculty.controller;

import com.example.faculty.dto.Response;
import com.example.faculty.dto.attendance.AttendanceRequest;
import com.example.faculty.utils.ExcelHelper;
import com.example.faculty.service.AttendanceService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user/attendance")
@CrossOrigin(origins = "*")
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

    @PostMapping("")
    public ResponseEntity<?> postEventAttendance(@RequestParam("file")MultipartFile file, @RequestParam("ID") String ID) throws IOException {
        //ExcelHelper.excelToAttendance(file.getInputStream());
        return ResponseEntity.ok(eventAttendanceService.postEventAttendance(file, ID));
    }

    @GetMapping("")
    public ResponseEntity<?> getEventAttendance(@RequestParam("ID") String ID) throws IOException {
        return ResponseEntity.ok(eventAttendanceService.getEventAttendanceList(ID));
    }

    @GetMapping("/export/{id}")
    public void exportExcel(HttpServletResponse response, @PathVariable("id") String id) throws Exception {
        eventAttendanceService.exportExcel(response, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAttendance(@PathVariable("id") String id) {
        eventAttendanceService.deleteEventAttendance(id);
        return ResponseEntity.ok("Success");
    }
}
