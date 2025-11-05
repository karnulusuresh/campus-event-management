package com.campus.event.service;

import com.campus.event.dto.RegistrationDTO;
import com.campus.event.pojo.RegistrationRequest;

import java.util.List;

public interface RegistrationService {
    RegistrationDTO registerUserForEvent(RegistrationRequest request);
    RegistrationDTO getRegistrationById(Long id);
    List<RegistrationDTO> getAllRegistrations();
    List<RegistrationDTO> getRegistrationsByUser(Long userId);
    List<RegistrationDTO> getRegistrationsByEvent(Long eventId);
    RegistrationDTO cancelRegistration(Long registrationId, Long performedByUserId);
}
