package com.example.faculty.repository;

import com.example.faculty.entity.EventAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventAttendanceRepository extends JpaRepository<EventAttendance, Integer> {
    @Query("SELECT u from EventAttendance u WHERE u.id = :param1 AND u.sId = :param2")
    Optional<EventAttendance> findByIdAndSid(@Param("param1") String id, @Param("param2") String sid);

    Optional<List<EventAttendance>> findById(String id);

    @Query("DELETE FROM EventAttendance u WHERE u.id = :id")
    @Modifying
    void deleteById(@Param("id") String id);
}
