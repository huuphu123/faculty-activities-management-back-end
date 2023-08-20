package com.example.faculty.service;

import com.example.faculty.dto.event.EventInsertRequest;
import com.example.faculty.dto.event.EventInsertResponse;
import com.example.faculty.dto.event.UserEventResponse;
import com.example.faculty.entity.Event;
import com.example.faculty.entity.Status;
import com.example.faculty.error.CustomException;
import com.example.faculty.repository.EventRepository;
import com.example.faculty.repository.UserRepository;
import com.example.faculty.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventSerivce {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;
    public EventInsertResponse insertEvent(EventInsertRequest request, String token) {
        String email = jwtService.extractUsername(token);
        String id = UUID.randomUUID().toString();

        Event event = Event
                .builder()
                .creatorEmail(email)
                .status(String.valueOf(Status.pending))
                .startDate(request.getStart_date())
                .endDate(request.getEnd_date())
                .location(request.getLocation())
                .name(request.getName())
                .id(id)
                .build();

        eventRepository.save(event);

        return EventInsertResponse
                .builder()
                .id(event.getId())
                .msg("Success added event " + event.getName())
                .build();

    }

    public UserEventResponse getUserEvent(String token) {
        String email = jwtService.extractUsername(token);
        List<Event> events = eventRepository.findByCreatorEmail(email).get();

        return new UserEventResponse("Success", events);
    }

    public Event getEventDetails(String id) {
        Optional<Event> event = eventRepository.findById(id);
        if (!event.isPresent()) {
            throw new CustomException("This id is not existed!");
        }
        return event.get();
    }


}
