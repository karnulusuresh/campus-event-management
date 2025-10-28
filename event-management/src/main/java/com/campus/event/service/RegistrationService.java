package com.campus.event.service;

import java.util.List;

import com.campus.event.entity.Registration;

public interface RegistrationService {
    List<Registration> getAllRegistrations();
    List<Registration> getRegistrationsByUser(Long userId);
    Registration registerUserForEvent(Registration registration);
    void cancelRegistration(Long id);
}
