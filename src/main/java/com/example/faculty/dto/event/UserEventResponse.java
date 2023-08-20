package com.example.faculty.dto.event;

import com.example.faculty.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEventResponse {
    private String msg;
    private List<Event> data;
}
