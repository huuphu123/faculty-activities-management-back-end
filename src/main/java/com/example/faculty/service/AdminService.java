package com.example.faculty.service;

import com.example.faculty.dto.Response;
import com.example.faculty.dto.event.AdminEventResponse;
import com.example.faculty.dto.user.AdminUserResponse;
import com.example.faculty.entity.Event;
import com.example.faculty.entity.User;
import com.example.faculty.error.CustomException;
import com.example.faculty.repository.EventAttendanceRepository;
import com.example.faculty.repository.EventRepository;
import com.example.faculty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventAttendanceRepository eventAttendanceRepository;

    public AdminEventResponse getPendingEvents() {
        Optional<List<Event>> event = eventRepository.findByStatus("pending");

        return new AdminEventResponse("Success!", event.get());
    }

    public AdminEventResponse getCheckedEvents() {
        Optional<List<Event>> event = eventRepository.findByStatusChecked();
        return new AdminEventResponse("Success!", event.get());
    }

    public Response updateStatusEvent(String id, String status) {
        Optional<Event> event = eventRepository.findById(id);

        if (!event.isPresent()) {
            throw new CustomException("Event does not exist!");
        }

        if (!event.get().getStatus().equals("pending")) {
            throw new CustomException("This event has already been " + event.get().getStatus());
        }

        if (!status.equals("accepted") && !status.equals("rejected")) {
            throw new CustomException("Can not update event status to this value: " + status);
        }

        Event updateEvent = event.get();
        updateEvent.setStatus(status);
        eventRepository.save(updateEvent);
        return new Response("Success update event status to " + status + "!");

    }

    public AdminUserResponse getPendingUsers() {
        Optional<List<User>> user = userRepository.findByStatus("pending");
        return new AdminUserResponse("Success!", user.get());
    }

    public AdminUserResponse getCheckedUsers() {
        Optional<List<User>> user = userRepository.findByStatusChecked();
        return new AdminUserResponse("Success!", user.get());
    }

    public Response updateStatusUser(String email, String status) {
        Optional<User> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            throw new CustomException("User does not exist!");
        }

        if (!user.get().getStatus().equals("pending")) {
            throw new CustomException("This user has already been " + user.get().getStatus());
        }

        if (!status.equals("accepted") && !status.equals("rejected")) {
            throw new CustomException("Can not update user status to this value: " + status);
        }

        User updateUser = user.get();
        updateUser.setStatus(status);
        userRepository.save(updateUser);
        return new Response("Success update user status to " + status + "!");
    }

    public Response updateRoleUser(String email, Integer role) {
        Optional<User> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            throw new CustomException("User does not exist!");
        }

        if (!user.get().getStatus().equals("accepted")) {
            throw new CustomException("This user has not been accepted!");
        }

        if (role != 1 && role != 0) {
            throw new CustomException("Can not update role user to this valuse:" + String.valueOf(role));
        }

        User updateUser = user.get();
        updateUser.setRole(role);
        userRepository.save(updateUser);
        return new Response("Success update user role to " + role + "!");
    }
}
