package com.example.faculty.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventInsertRequest {
    private String name;
    private String location;
    private String start_date;
    private String end_date;
}
