package com.example.faculty.repository;

import com.example.faculty.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
    Optional<List<Event>> findByCreatorEmail(String email);

    Optional<List<Event>> findByStatus(String status);

    @Query("SELECT u FROM Event u WHERE u.status = 'accepted' OR u.status = 'rejected'")
    Optional<List<Event>> findByStatusChecked();
}
