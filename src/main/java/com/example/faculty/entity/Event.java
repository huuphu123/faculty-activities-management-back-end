package com.example.faculty.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "events")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String creatorEmail;

    @Column(nullable = false)
    private String status = String.valueOf(Status.pending);

    @Column(nullable = false)
    private String location;

    private String startDate = String.valueOf(new Date());

    private String endDate = String.valueOf(new Date());



}
