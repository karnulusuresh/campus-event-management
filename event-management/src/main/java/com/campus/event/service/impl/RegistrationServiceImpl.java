
package com.campus.event.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campus.event.entity.Registration;
import com.campus.event.repository.RegistrationRepository;
import com.campus.event.service.RegistrationService;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Override
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    @Override
    public List<Registration> getRegistrationsByUser(Long userId) {
        return registrationRepository.findByUser_UserId(userId);
    }

    @Override
    public Registration registerUserForEvent(Registration registration) {
        return registrationRepository.save(registration);
    }

    @Override
    public void cancelRegistration(Long id) {
        registrationRepository.deleteById(id);
    }
}
