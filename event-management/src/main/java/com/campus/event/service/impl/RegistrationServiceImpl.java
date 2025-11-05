
package com.campus.event.service.impl;

import com.campus.event.constant.RegistrationEnum;
import com.campus.event.constant.RoleEnum;
import com.campus.event.dto.RegistrationDTO;
import com.campus.event.entity.*;
import com.campus.event.exception.*;
import com.campus.event.pojo.RegistrationRequest;
import com.campus.event.repository.EventRepository;
import com.campus.event.repository.RegistrationRepository;
import com.campus.event.repository.UserRepository;
import com.campus.event.service.RegistrationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public RegistrationDTO registerUserForEvent(RegistrationRequest request) {
        try {
            log.info("Registering user {} for event {}", request.getUserId(), request.getEventId());

            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + request.getUserId()));

            Event event = eventRepository.findById(request.getEventId())
                    .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + request.getEventId()));

            if (event.getEndDateTime() != null && event.getEndDateTime().isBefore(LocalDateTime.now())) {
                throw new BadRequestException("Cannot register for an event that has already ended.");
            }

            registrationRepository.findByUser_UserIdAndEventId(user.getUserId(), event.getId())
                    .ifPresent(r -> { throw new ConflictException("User already registered for this event."); });

            Registration registration = Registration.builder()
                    .user(user)
                    .event(event)
                    .status(RegistrationEnum.REGISTERED)
                    .registeredAt(LocalDateTime.now())
                    .build();

            Registration saved;
            try {
                saved = registrationRepository.save(registration);
            } catch (DataIntegrityViolationException ex) {
                throw new ConflictException("Duplicate registration or data conflict.");
            }

            RegistrationDTO dto = mapToDTO(saved);
            log.info("Registration successful: {}", dto);
            return dto;

        } catch (ResourceNotFoundException | BadRequestException | ConflictException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to register user: {}", ex.getMessage(), ex);
            throw new GeneralServiceException("Failed to register user for event", ex);
        }
    }

    @Override
    public RegistrationDTO getRegistrationById(Long id) {
        try {
            log.info("Fetching registration by ID: {}", id);
            Registration reg = registrationRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Registration not found with ID: " + id));
            return mapToDTO(reg);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error fetching registration: {}", ex.getMessage(), ex);
            throw new GeneralServiceException("Failed to fetch registration", ex);
        }
    }

    @Override
    public List<RegistrationDTO> getAllRegistrations() {
        try {
            log.info("Fetching all registrations");
            List<Registration> regs = registrationRepository.findAll();
            return regs.stream()
                    .map(this::mapToDTO)
                    .toList();
        } catch (Exception ex) {
            log.error("Error fetching registrations: {}", ex.getMessage(), ex);
            throw new GeneralServiceException("Failed to fetch registrations", ex);
        }
    }

    @Override
    public List<RegistrationDTO> getRegistrationsByUser(Long userId) {
        try {
            log.info("Fetching registrations for userId: {}", userId);
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
            List<Registration> regs = registrationRepository.findAllByUser_UserId(user.getUserId());
            return regs.stream()
                    .map(this::mapToDTO)
                    .toList();
        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error fetching user registrations: {}", ex.getMessage(), ex);
            throw new GeneralServiceException("Failed to fetch registrations by user", ex);
        }
    }

    @Override
    public List<RegistrationDTO> getRegistrationsByEvent(Long eventId) {
        try {
            log.info("Fetching registrations for eventId: {}", eventId);
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));
            List<Registration> regs = registrationRepository.findByEventId(event.getId());
            return regs.stream()
                    .map(this::mapToDTO)
                    .toList();
        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error fetching event registrations: {}", ex.getMessage(), ex);
            throw new GeneralServiceException("Failed to fetch registrations by event", ex);
        }
    }

    @Override
    public RegistrationDTO cancelRegistration(Long registrationId, Long performedByUserId) {
        try {
            log.info("Cancelling registration {} by user {}", registrationId, performedByUserId);

            Registration reg = registrationRepository.findById(registrationId)
                    .orElseThrow(() -> new ResourceNotFoundException("Registration not found with ID: " + registrationId));

            if (reg.getStatus() == RegistrationEnum.CANCELLED) {
                throw new BadRequestException("Registration already cancelled.");
            }

            User performer = userRepository.findById(performedByUserId)
                    .orElseThrow(() -> new ResourceNotFoundException("User (performer) not found with ID: " + performedByUserId));

            if (!performer.getUserId().equals(reg.getUser().getUserId()) && !performer.getUserRole().equals(RoleEnum.ADMIN)) {
                throw new AccessDeniedException("Only the registrant or an admin can cancel the registration.");
            }

            Event event = reg.getEvent();
            if (event.getEndDateTime() != null && event.getEndDateTime().isBefore(LocalDateTime.now())) {
                throw new BadRequestException("Cannot cancel registration for an event that has already ended.");
            }

            reg.setStatus(RegistrationEnum.CANCELLED);
            Registration saved = registrationRepository.save(reg);

            return mapToDTO(saved);

        } catch (ResourceNotFoundException | BadRequestException | AccessDeniedException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error cancelling registration: {}", ex.getMessage(), ex);
            throw new GeneralServiceException("Failed to cancel registration", ex);
        }
    }

    private RegistrationDTO mapToDTO(Registration reg) {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setId(reg.getId());
        dto.setUserId(reg.getUser().getUserId());
        dto.setEventId(reg.getEvent().getId());
        dto.setStatus(reg.getStatus().name());
        dto.setRegisteredAt(reg.getRegisteredAt());

        dto.setUserName(reg.getUser().getName());
        dto.setEventTitle(reg.getEvent().getTitle());
        return dto;
    }
}
