package com.example.faculty.controller;

import com.example.faculty.entity.Event;
import com.example.faculty.entity.User;
import com.example.faculty.service.AdminService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/event/pending")
    public ResponseEntity<?> getPendingEvents() {
        return ResponseEntity.ok(adminService.getPendingEvents());
    }

    @GetMapping("/event/checked")
    public ResponseEntity<?> getCheckedEvents() {
        return ResponseEntity.ok(adminService.getCheckedEvents());
    }

    @PatchMapping("/event/update-status/{id}")
    public ResponseEntity<?> updateStatusEvent(@PathVariable("id") String id, @RequestBody Event event) {
        return ResponseEntity.ok(adminService.updateStatusEvent(id, event.getStatus()));
    }

    @GetMapping("/user/pending")
    public ResponseEntity<?> getPendingUsers() {
        return ResponseEntity.ok(adminService.getPendingUsers());
    }

    @GetMapping("/user/checked")
    public ResponseEntity<?> getCheckedUsers() {
        return ResponseEntity.ok(adminService.getCheckedUsers());
    }

    @PatchMapping("user/update-status/{email}")
    public ResponseEntity<?> updateUserStatus(@PathVariable("email") String email, @RequestBody User user) {
        return ResponseEntity.ok(adminService.updateStatusUser(email, user.getStatus()));
    }

    @PatchMapping("user/update-role/{email}")
    public ResponseEntity<?> updateUserRole(@PathVariable("email") String email, @RequestBody User user) {
        return ResponseEntity.ok(adminService.updateRoleUser(email, user.getRole()));
    }


}
