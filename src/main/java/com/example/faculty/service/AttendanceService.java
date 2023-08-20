package com.example.faculty.service;

import com.example.faculty.dto.attendance.AttendanceResponse;
import com.example.faculty.entity.Event;
import com.example.faculty.entity.EventAttendance;
import com.example.faculty.error.CustomException;
import com.example.faculty.repository.EventAttendanceRepository;
import com.example.faculty.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private EventAttendanceRepository eventAttendanceRepository;

    @Autowired
    private EventRepository eventRepository;

    public AttendanceResponse checkIn(String SID, String ID) {
        Optional<Event> event = eventRepository.findById(ID);

        if (!event.isPresent()) {
            throw new CustomException("Event ID is not existed!");
        }

        Date date = new Date();

        if (date.before(new Date(event.get().getStartDate()))) {
            throw new CustomException("Event has not started!");
        }
        if (date.after(new Date(event.get().getEndDate()))) {
            throw new CustomException("Event has ended!");
        }

        Optional<EventAttendance> eventAttendance = eventAttendanceRepository.findByIdAndSid(ID, SID);
        if (!eventAttendance.isPresent()) {
            throw new CustomException("Can not find Student or Event!");
        }

        if (eventAttendance.get().getCheckIn() == 1) {
            throw new CustomException("You has been already checked in!");
        }

        EventAttendance newEventAttendance = eventAttendance.get();
        newEventAttendance.setCheckIn(1);
        eventAttendanceRepository.save(newEventAttendance);
        return AttendanceResponse
                .builder()
                .msg("Check in successfully!")
                .data(newEventAttendance)
                .build();
    }

    public AttendanceResponse checkOut(String SID, String ID) {
        Optional<Event> event = eventRepository.findById(ID);

        if (!event.isPresent()) {
            throw new CustomException("Event ID is not existed!");
        }

//        Date date = new Date();
//
//        if (date.before(new Date(event.get().getStartDate()))) {
//            throw new CustomException("Event has not started!");
//        }
//        if (date.after(new Date(event.get().getEndDate()))) {
//            throw new CustomException("Event has ended!");
//        }

        Optional<EventAttendance> eventAttendance = eventAttendanceRepository.findByIdAndSid(ID, SID);
        if (!eventAttendance.isPresent()) {
            throw new CustomException("Can not find Student or Event!");
        }

        if (eventAttendance.get().getCheckOut() == 1) {
            throw new CustomException("You has been already checked out!");
        }

        EventAttendance newEventAttendance = eventAttendance.get();
        newEventAttendance.setCheckOut(1);
        eventAttendanceRepository.save(newEventAttendance);
        return AttendanceResponse
                .builder()
                .msg("Check in successfully!")
                .data(newEventAttendance)
                .build();

    }


}