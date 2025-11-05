package com.campus.event.repository;

import com.campus.event.entity.Registration;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findAllByUser_UserId(Long userId);
    List<Registration> findByEventId(Long eventId);
    Optional<Registration> findByUser_UserIdAndEventId(Long userId, Long eventId);
    boolean existsByUser_UserIdAndEvent_Id(Long userId, Long eventId);
}
