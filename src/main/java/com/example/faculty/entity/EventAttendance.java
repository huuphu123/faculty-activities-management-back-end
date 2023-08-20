package com.example.faculty.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="event_attendance")
public class EventAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rowId;

    @Column(name="ID", nullable = false)
    private String id;

    @Column(name="SID", nullable = false)
    private String sId;

    private String fname = "";

    private String lname = "";

    @Column(name="check_in")
    private Integer checkIn = 0;

    @Column(name="check_out")
    private Integer checkOut = 0;

}
