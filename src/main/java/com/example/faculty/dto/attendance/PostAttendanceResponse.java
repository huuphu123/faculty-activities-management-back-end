package com.example.faculty.dto.attendance;

import com.example.faculty.entity.EventAttendance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostAttendanceResponse {

    String msg;
    String EID;
    List<EventAttendance> data;
}
