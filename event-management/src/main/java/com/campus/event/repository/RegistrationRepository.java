package com.campus.event.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campus.event.entity.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByUser_UserId(Long userId);
}
