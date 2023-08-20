package com.example.faculty.dto.attendance;

import com.example.faculty.entity.EventAttendance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponse {
    String msg;
    EventAttendance data;
}
